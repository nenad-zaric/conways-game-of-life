import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Scanner;

public class GamePanel extends JPanel implements ActionListener {
	private int columns;
	private int rows;
	private int cellSize;
	private int offset;
	private Grid grid;
	private Timer timer;
	/**
	 * Create the panel.
	 */
	public GamePanel(Grid grid, int cellSize) {
		super();
		this.cellSize = cellSize;
		this.offset = cellSize;
		this.grid = grid;
		columns = grid.getxDimension();
		rows = grid.getyDimension();
		setBounds(offset/2,offset/2,columns*cellSize*2,rows*cellSize*2);
		setVisible(true);
		setBackground(Color.black);
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				//Event Listener koji registruje klikove i azurira matricu celija
				
				//Koordinate klika
				int xCoordinate = e.getX()/cellSize;
				int yCoordinate = e.getY()/cellSize;
				
				try {
					System.out.println("X:" + xCoordinate + " Y:" + yCoordinate);
					
					//Menja stanje celije na koju je kliknuto
					if(!grid.getCellGrid()[yCoordinate][xCoordinate].isAlive()) {
						grid.getCellGrid()[yCoordinate][xCoordinate].setAlive(true);
					}
					else {
						grid.getCellGrid()[yCoordinate][xCoordinate].setAlive(false);
					}
					revalidate();
					repaint();
				} catch (Exception e1) {
				}
			}
		});
	}
	
	
	
	public void writeToFile(File gridFile) throws IOException {
		BufferedWriter br = new BufferedWriter(new FileWriter(gridFile));
		int counter = 0;
		for(int i=0; i<columns; i++) {
	
			for(int j=0; j<rows; j++) {
				if(grid.getCellGrid()[i][j].isAlive()) {
					if(i==columns-1 && j==rows-1) {
						br.append('1');
						counter++;
					}
					else {
						br.append('1');
						br.append(',');
						counter++;
					}
				}
				else if(!grid.getCellGrid()[i][j].isAlive()) {
					if(i==columns-1 && j==rows-1) {
						br.append('0');
						counter++;
					}
					else {
						br.append('0');
						br.append(',');
						counter++;
					}
				}
			}
		}
		System.out.println("Upisano celija: " + counter);
		br.close();
		}
	
	
	
	public void readFromFile(File gridFile) throws FileNotFoundException {
		Cell[][] newCellGrid = new Cell[columns][rows];
		Scanner scan = new Scanner(gridFile);  
		scan.useDelimiter(",");
		int counter = 0;
		int counterb = 0;
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				
				counter++;
				if(scan.hasNextInt()){
					counterb++;
					int current = scan.nextInt();
					if(current==1) {
						Cell c = new Cell(true);
					    newCellGrid[i][j] = c;
					    System.out.println(true);
					}
					else if(current==0) {
						Cell c = new Cell(false);
					    newCellGrid[i][j] = c;
					    System.out.println(false);
					}
				}
			}
		}
		System.out.println("Int:" + counterb);
		System.out.println("Items:" + counter);
		grid.setCellGrid(newCellGrid);;
		revalidate();
		repaint();
		scan.close();
		}
	
	public void step() {
		//Metoda koja azurira stanje celija u gridu nakon svakog koraka
		System.out.println("Step taken");
		Cell[][] newCellGrid = new Cell[columns][rows];
		for(int i=0;i<rows;i++) {
			for(int j=0; j<columns; j++) {
				
				if(i==0 || i== columns-1 || j==0 || j==rows-1) {
					Cell c = new Cell(grid.getCellGrid()[i][j].isAlive());
					newCellGrid[i][j] = c;
					continue;
				}
				
				int neighbourCount = grid.countNeighbours(i,j);
				if(grid.getCellGrid()[i][j].isAlive() && (neighbourCount == 2 || neighbourCount ==3)) {
					Cell c = new Cell(true);
					newCellGrid[i][j] = c;
				}
				else if(!grid.getCellGrid()[i][j].isAlive() && neighbourCount==3) {
					Cell c = new Cell(true);
					newCellGrid[i][j] = c;				
				}
				else {
					Cell c = new Cell(false);
					newCellGrid[i][j] = c;				
				}
			}
		}
		grid.setCellGrid(newCellGrid);;
		revalidate();
		repaint();
	}

	public void startSim() {
		timer = new Timer(200,this);
		timer.start();
	}
	
	public void stopSim() {
		timer.stop();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Iscrtava zive i mrtve celije
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				boolean state;
				try {
					state = grid.getCellGrid()[j][i].isAlive();
				} catch (Exception e) {
					state = true;
				}
				if(!state) {
					g.setColor(Color.black);
				}
				else if(state) {
					g.setColor(Color.green);
				}
				g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);			
			}
		}
		//Iscrtava linije koje cine "mrezu"
		g.setColor(Color.lightGray);
		for(int i=0; i<rows;i++) {
			g.drawLine(0, i*cellSize, rows*cellSize-5, i*cellSize);
		}
		for(int i=0; i<columns;i++) {
			g.drawLine(i*cellSize, 0, i*cellSize, columns*cellSize-5);
		}
		//Iscrtava tamno sive celije koje predstavljaju ivicu
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				if(i==0 || i== columns-1 || j==0 || j==rows-1) {
					g.setColor(Color.darkGray);
					g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);			

				}			
			}
		}	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		step();
	}
}
