import java.util.Random;

public class Grid {
	private int xDimension;
	private int yDimension;
	private Cell[][] cellGrid;
	private Random random = new Random();
	
	public Grid(int xDimension, int yDimension) {
		super();
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		cellGrid = new Cell[xDimension][yDimension];
		initCells();
	}
	
	public int getxDimension() {
		return xDimension;
	}

	public void setxDimension(int xDimension) {
		this.xDimension = xDimension;
	}

	public int getyDimension() {
		return yDimension;
	}

	public void setyDimension(int yDimension) {
		this.yDimension = yDimension;
	}

	public Cell[][] getCellGrid() {
		return cellGrid;
	}

	public void setCellGrid(Cell[][] cellGrid) {
		this.cellGrid = cellGrid;
	}

	public void initCells() {
		for(int i=0;i<xDimension;i++) {
			for(int j=0;j<yDimension;j++) {
				Cell c = new Cell(false);
				cellGrid[i][j] = c;
			}
		}
	}
	
	public void resetCells() {
		for(int i=0;i<xDimension;i++) {
			for(int j=0;j<yDimension;j++) {
				cellGrid[i][j].setAlive(false);
			}
		}
	}
	
	public void generateRandom() {
		boolean temp;
		double randomDouble;
		for(int i=0;i<xDimension;i++) {
			for(int j=0;j<yDimension;j++) {
				randomDouble = Math.random();
				if(randomDouble < 0.8) {
					temp = false;
				}
				else {
					temp = true;
				}
				cellGrid[i][j].setAlive(temp);
			}
		}
	}
	
	public void printGrid() {
		for(int i=0;i<xDimension;i++) {
			for(int j=0;j<yDimension;j++) {
				if(cellGrid[i][j].isAlive()) {
					System.out.print("1   ");
				}
				else {
					System.out.print("0   ");
				}
			}
			System.out.println('\n');
		}
	}
	
	public int countNeighbours(int a, int b) {
		//Broji zive komsijske celije za celiju sa zadatim indeksima
		int counter = 0;
		for(int i=a-1; i<a+2; i++) {
			for(int j=b-1; j<b+2; j++) {
				if(i == a && j==b) {
					continue;
				}
				if(cellGrid[i][j].isAlive()) {
					counter++;
				}
			}
		}
		return counter;
	}
}
