import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class GameFrame extends JFrame {

	private JPanel contentPane;
	private int dimension = 30;
	private int cellSize = 20;
	private int width = dimension*cellSize+15;
	private int height = dimension*cellSize+105;
	private Grid grid = new Grid(dimension, dimension);
	private GamePanel g;
	private boolean running = false;
	
	//Definisanje boja
	public Color gray = Color.decode("#676966");
	public Color dark = Color.decode("#242220");
	public Color light = Color.decode("#FAFAF8");


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame frame = new GameFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 0, width, height);
		setResizable(false);

		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFil = new JMenu("File");
		menuBar.add(mnFil);
		
		JMenu mnNew = new JMenu("New");
		mnFil.add(mnNew);
		
		//Resetovanje grida
		JMenuItem mntmEmpty = new JMenuItem("Empty");
		mntmEmpty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(running) {
					g.stopSim();
				}				
				grid.resetCells();
				g.revalidate();
				g.repaint();
			}
		});
		mnNew.add(mntmEmpty);
		
		//Generisanje nasumicnih celija
		JMenuItem mntmRandom = new JMenuItem("Random");
		mntmRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(running) {
					g.stopSim();
				}
				grid.generateRandom();
				g.revalidate();
				g.repaint();
			}
		});
		mnNew.add(mntmRandom);
		
		JMenu mnOpen = new JMenu("Open");
		mnFil.add(mnOpen);
		
		//Citanje matrice iz fajla
		JMenuItem mntmFromFile = new JMenuItem("From file");
		mntmFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to open"); 
				//Filter za default ekstenziju
				FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("csv files (*.csv)", "csv");
		
				fileChooser.addChoosableFileFilter(csvFilter);
				fileChooser.setFileFilter(csvFilter);
		
				int userSelection = fileChooser.showOpenDialog(rootPane);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToOpen = fileChooser.getSelectedFile();
				    System.out.println("Save as file: " + fileToOpen.getAbsolutePath());
				    
				    try {
				    	g.readFromFile(fileToOpen);
				    } catch (FileNotFoundException e1) {
				    	// TODO Auto-generated catch block
				    	e1.printStackTrace();
				    }
				}
			}
		});
		mnOpen.add(mntmFromFile);
		
		JMenu mnBuiltIn = new JMenu("Built In");
		mnOpen.add(mnBuiltIn);
		
		JMenu mnStillLives = new JMenu("Still lives");
		mnBuiltIn.add(mnStillLives);
		
		JMenuItem mntmBlock = new JMenuItem("Block");
		mntmBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fileToLoad = new File("src/Patterns/Still lives/Block.csv");
				try {
					g.readFromFile(fileToLoad);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnStillLives.add(mntmBlock);
		
		JMenuItem mntmBeehive = new JMenuItem("Beehive");
		mntmBeehive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fileToLoad = new File("src/Patterns/Still lives/Beehive.csv");
				try {
					g.readFromFile(fileToLoad);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnStillLives.add(mntmBeehive);
		
		JMenuItem mntmBoat = new JMenuItem("Boat");
		mntmBoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fileToLoad = new File("src/Patterns/Still lives/Boat.csv");
				try {
					g.readFromFile(fileToLoad);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnStillLives.add(mntmBoat);
		
		JMenuItem mntmLoaf = new JMenuItem("Loaf");
		mntmLoaf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fileToLoad = new File("src/Patterns/Still lives/Loaf.csv");
				try {
					g.readFromFile(fileToLoad);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnStillLives.add(mntmLoaf);
		
		JMenu mnOscilators = new JMenu("Oscilators");
		mnBuiltIn.add(mnOscilators);
		
		JMenuItem mntmBeacon = new JMenuItem("Beacon");
		mntmBeacon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fileToLoad = new File("src/Patterns/Oscilators/Beacons.csv");
				try {
					g.readFromFile(fileToLoad);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnOscilators.add(mntmBeacon);
		
		JMenuItem mntmBlinker = new JMenuItem("Blinker");
		mntmBlinker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fileToLoad = new File("src/Patterns/Oscilators/Blinker.csv");
				try {
					g.readFromFile(fileToLoad);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnOscilators.add(mntmBlinker);
		
		JMenuItem mntmToad = new JMenuItem("Toad");
		mntmToad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fileToLoad = new File("src/Patterns/Oscilators/Toads.csv");
				try {
					g.readFromFile(fileToLoad);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnOscilators.add(mntmToad);
		
		JMenuItem mntmPulsar = new JMenuItem("Pulsar");
		mntmPulsar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fileToLoad = new File("src/Patterns/Oscilators/Pulsar.csv");
				try {
					g.readFromFile(fileToLoad);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnOscilators.add(mntmPulsar);
		
		JMenuItem mntmPentadecathlon = new JMenuItem("Pentadecathlon");
		mntmPentadecathlon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fileToLoad = new File("src/Patterns/Oscilators/Pentadecathlon.csv");
				try {
					g.readFromFile(fileToLoad);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnOscilators.add(mntmPentadecathlon);
		
		JMenu mnSpaceShips = new JMenu("Spaceships");
		mnBuiltIn.add(mnSpaceShips);
		
		JMenuItem mntmGlider = new JMenuItem("Glider");
		mntmGlider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fileToLoad = new File("src/Patterns/Spaceships/Glider.csv");
				try {
					g.readFromFile(fileToLoad);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnSpaceShips.add(mntmGlider);
		
		JMenuItem mntmLWSS = new JMenuItem("Lightweight Spaceship");
		mntmLWSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File fileToLoad = new File("src/Patterns/Spaceships/LightweightSpaceShip.csv");
				try {
					g.readFromFile(fileToLoad);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnSpaceShips.add(mntmLWSS);
		
		//Pisanje matrice u fajl
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save"); 
	
				//Postavljanje default ekstenzije
				FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("csv files (*.csv)", "csv");
				fileChooser.addChoosableFileFilter(csvFilter);
				fileChooser.setFileFilter(csvFilter);
				int userSelection = fileChooser.showSaveDialog(rootPane);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				    //Provera ekstenzije
				    if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
				        fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
				    }
					try {
						g.writeToFile(fileToSave);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
	
			}
		});
		mnFil.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		
		//Eksportovanje stanja u sliku
		JMenuItem mntmExport = new JMenuItem("Export to PNG");
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save"); 
				//Filter za default ekstenziju
				FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("png files (*.png)", "png");
		
				fileChooser.addChoosableFileFilter(pngFilter);
				fileChooser.setFileFilter(pngFilter);
				int userSelection = fileChooser.showSaveDialog(rootPane);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    //Provera ekstenzije
				    if (!fileToSave.getAbsolutePath().endsWith(".png")) {
				        fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
				    }
					g.ExportPNG(fileToSave);
				}

			}
		});
		mnFil.add(mntmExport);
		mnFil.add(mntmExit);
		
		g = new GamePanel(grid,cellSize);
		getContentPane().add(g);
		g.setLayout(null);
		
		//Dugme za jedan korak
		JButton btnStep = new JButton("Step");
		btnStep.setBackground(light);
		btnStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.step();
			}
		});
		btnStep.setBounds(145, 608, 117, 25);
		g.add(btnStep);
		
		//Dugme za pokretanje simulacije
		JButton btnStart = new JButton("Start");
		btnStart.setBackground(light);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!running) {
					g.startSim();
					running = true;
					btnStart.setText("Stop");
				}
				else {
					g.stopSim();
					running = false;
					btnStart.setText("Start");

				}
			}
		});
		btnStart.setBounds(331, 608, 117, 25);
		g.add(btnStart);
	}
}
