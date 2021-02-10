import java.awt.BorderLayout;
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
	private int width = dimension*cellSize+25;
	private int height = dimension*cellSize+110;
	private Grid grid = new Grid(dimension, dimension);
	private GamePanel g;
	private boolean running = false;
	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
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
		
		JMenuItem mntmFromFile = new JMenuItem("From file");
		mntmFromFile.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to open"); 

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
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save"); 

		FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("csv files (*.csv)", "csv");

		fileChooser.addChoosableFileFilter(csvFilter);
		fileChooser.setFileFilter(csvFilter);
		int userSelection = fileChooser.showSaveDialog(rootPane);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		    
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
		mnFil.add(mntmExit);
		
		g = new GamePanel(grid,cellSize);
		getContentPane().add(g);
		g.setLayout(null);
		
		JButton btnStep = new JButton("Step");
		btnStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.step();
			}
		});
		btnStep.setBounds(128, 608, 117, 25);
		g.add(btnStep);
		
		JButton btnStart = new JButton("Start");
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
		btnStart.setBounds(312, 608, 117, 25);
		g.add(btnStart);
	}
}
