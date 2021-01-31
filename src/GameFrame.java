import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class GameFrame extends JFrame {

	private JPanel contentPane;
	private int dimension = 30;
	private int cellSize = 20;
	private int width = dimension*cellSize+10;
	private int height = dimension*cellSize+100;
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
		
		JMenuItem mntmCustomDimensions = new JMenuItem("Custom dimensions");
		mnNew.add(mntmCustomDimensions);
		
		JMenu mnOpen = new JMenu("Open");
		mnFil.add(mnOpen);
		
		JMenuItem mntmBuiltin = new JMenuItem("Built-in");
		mnOpen.add(mntmBuiltin);
		
		JMenuItem mntmFromFile = new JMenuItem("From file");
		mnOpen.add(mntmFromFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFil.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		mnFil.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmExplenation = new JMenuItem("Explenation");
		mnHelp.add(mntmExplenation);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
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
