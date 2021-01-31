import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

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
					if(!grid.getCellGrid()[xCoordinate][yCoordinate].isAlive()) {
						grid.getCellGrid()[xCoordinate][yCoordinate].setAlive(true);
					}
					else {
						grid.getCellGrid()[xCoordinate][yCoordinate].setAlive(false);
					}
					revalidate();
					repaint();
				} catch (Exception e1) {
				}
			}
		});
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
		//Iscrtava zive i mrtve celije
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				if(!grid.getCellGrid()[i][j].isAlive()) {
					g.setColor(Color.black);
				}
				else if(grid.getCellGrid()[i][j].isAlive()) {
					g.setColor(Color.green);
				}
				g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);			
			}
		}
		//Iscrtava linije koje cine "mrezu"
		g.setColor(Color.lightGray);
		for(int i=0; i<rows;i++) {
			g.drawLine(0, i*cellSize, rows*cellSize, i*cellSize);
		}
		for(int i=0; i<columns;i++) {
			g.drawLine(i*cellSize, 0, i*cellSize, columns*cellSize);
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
