import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
	
	//Definisanje boja
	public Color primary = Color.decode("#F2CB05");
	public Color gray = Color.decode("#676966");
	public Color dark = Color.decode("#242220");
	public Color light = Color.decode("#FAFAF8");

	
	public GamePanel(Grid grid, int cellSize) {
		super();
		this.cellSize = cellSize;
		this.offset = cellSize;
		this.grid = grid;
		this.columns = grid.getxDimension();
		this.rows = grid.getyDimension();
		
		setBounds(offset/2,offset/2,columns*cellSize*2,rows*cellSize*2);
		setVisible(true);
		setBackground(dark);
		
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
				playSound();
				registerClick(e);
				
			}
		});
	}

	public void playSound() {
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("src/pop.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void registerClick(MouseEvent e) {
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
	
	
	
	public void writeToFile(File gridFile) throws IOException {
		//Metoda za upisivanje matrice u fajl
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
		//Metoda za citanje matrice iz fajla
		
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
	
	public void ExportPNG(File temp) {
		//Metoda koja eksportuje sadrzaj panela u sliku
		BufferedImage image = new BufferedImage(getWidth(), getHeight()-50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		paint(g);
		try{
			ImageIO.write(image, "png", temp); 
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Vec postoji slika sa tim imenom");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void step() {
		//Metoda koja azurira stanje celija u gridu nakon svakog koraka
		playSound();
		Cell[][] newCellGrid = new Cell[columns][rows];
		for(int i=0;i<rows;i++) {
			for(int j=0; j<columns; j++) {
				//Zanemaruje celije na ivici grida
				if(i==0 || i== columns-1 || j==0 || j==rows-1) {
					Cell c = new Cell(grid.getCellGrid()[i][j].isAlive());
					newCellGrid[i][j] = c;
					continue;
				}
				
				int neighbourCount = grid.countNeighbours(i,j);
				
				//Ako je celija ziva i ima dve ili tri zive komsijske celije ona ostaje ziva
				if(grid.getCellGrid()[i][j].isAlive() && (neighbourCount == 2 || neighbourCount ==3)) {
					Cell c = new Cell(true);
					newCellGrid[i][j] = c;
				}
				//Ako je celija mrtva i ima tacno tri zive komsijske celije ona postaje ziva
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
					g.setColor(dark);
				}
				else if(state) {
					g.setColor(primary);
				}
				g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);			
			}
		}
		//Iscrtava linije koje cine "mrezu"
		g.setColor(light);
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
					g.setColor(gray
							);
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
