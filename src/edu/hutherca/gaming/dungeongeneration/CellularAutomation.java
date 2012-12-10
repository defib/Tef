package edu.hutherca.gaming.dungeongeneration;

import java.util.Random;

import edu.hutherca.gaming.utility.Cell;

/*
 * Some good R1 and R2 values:
 * (8,6): long tunnels, some closed off rooms
 * (7,8): very thin tunnels, good for difficult levels
 * (7,7): a little thicker than ^, seperate tunnels
 * (6,1)/(6,0): open cave (use empty space for the outer walls)
 * (5,2): hectic caves
 * (5,1): ^, more open space
 * (5,0): ^ even more open space
 */

public class CellularAutomation {
	/*
	 * Data members
	 */
	public final boolean TILE_WALL = true;
	public final boolean TILE_FLOOR = false;
	private Random random = new Random();
	private int xSize; //Size of row
	private int ySize; //Size of col
	private int r1_cutoff; // (6,1) - good for floating levels. (5,2) good for caves
	private int r2_cutoff; //
	private int solidProb = 40; //Probablility of getting a solid tile
	private Cell[][] grid; // Grid of object Cell
	private Cell[][] grid2; // Grid of object Cell

	/* 
	 * * Methods
	 */
	//Default Constructor
	public CellularAutomation()
	{
		this.xSize = 60;
		this.ySize = 30;
		grid = new Cell[ySize][xSize];
		grid2 = new Cell[ySize][xSize];
		r1_cutoff = 5;
		r2_cutoff = 2;
	}//default constructor

	//Overloaded Constructor (size)
	public CellularAutomation(int row, int col)
	{
		this.xSize = row;
		this.ySize = col;
		grid = new Cell[ySize][xSize];
		grid2 = new Cell[ySize][xSize];
	}//Overloaded Constructor (size)

	public CellularAutomation(int row, int col, int r1, int r2)
	{
		this.xSize = row;
		this.ySize = col;
		grid = new Cell[ySize][xSize];
		grid2 = new Cell[ySize][xSize];
		this.r1_cutoff = r1;
		this.r2_cutoff = r2;
	}//Overloaded Constructor (size)

	// Initalize the grid
	public void initGrid()
	{
		int xi, yi;
		//Fill grid2 with solid walls
		for (yi = 0; yi < ySize; yi++)
			for (xi = 0; xi < xSize; xi++)
				grid2[yi][xi] = new WallCell();
		//fill grid with rand, excluding the outer most
		for (yi = 1; yi < ySize - 1; yi++)
			for (xi = 1; xi < xSize - 1; xi++)
				if (randSolid())
					grid[yi][xi] = new WallCell();
				else
					grid[yi][xi] = new FloorCell();

		//Fill outer wall with solid walls
		for (yi = 0; yi < ySize; yi++)
			grid[yi][0] = grid[yi][xSize - 1] = new WallCell();
		for (xi = 0; xi < xSize; xi++)
			grid[0][xi] = grid[ySize - 1][xi] = new WallCell();
	}

	//Call to run a generation of the CA
	public void generation()
	{
		int xi, yi, ii, jj;
		//look at all tiles except the outer most tiles
		for(yi=1;yi<ySize-1;yi++)
			for (xi = 1; xi < xSize - 1; xi++)
			{
				int adjcnt_r1 = 0, adjcnt_r2 = 0;

				//Look at the tiles surrounding the current one
				for(ii=-1; ii<=1; ii++)
					for (jj = -1; jj <= 1; jj++)
					{
						//if tile isnt floor, increment adj count
						if (grid[yi + ii][xi + jj].isSolid() != TILE_FLOOR)
							adjcnt_r1++;
					}

				for (ii = yi - 2; ii <= yi + 2; ii++)
					for (jj = xi - 2; jj <= xi + 2; jj++)
					{
						if (Math.abs(ii - yi) == 2 && Math.abs(jj - xi) == 2)
							continue;
						if (ii < 0 || jj < 0 || ii >= ySize || jj >= xSize)
							continue;
						if (grid[ii][jj].isSolid() != TILE_FLOOR)
							adjcnt_r2++;
					}

				if (adjcnt_r1 >= r1_cutoff || adjcnt_r2 <= r2_cutoff)
					grid2[yi][xi] = new WallCell();
				else
					grid2[yi][xi] = new FloorCell();
			}
		//Copy grid2 over grid1
		for (yi = 1; yi < ySize - 1; yi++)
			for (xi = 1; xi < xSize - 1; xi++)
				grid[yi][xi] = grid2[yi][xi];
	}//generation()

	//Call to run a X generations of the CA
	public void generation(int x)
	{
		for(int i = 0; i < x; i++) {
			int xi, yi, ii, jj;
			//look at all tiles except the outer most tiles
			for(yi=1;yi<ySize-1;yi++)
				for (xi = 1; xi < xSize - 1; xi++)
				{
					int adjcnt_r1 = 0, adjcnt_r2 = 0;

					//Look at the tiles surrounding the current one
					for(ii=-1; ii<=1; ii++)
						for (jj = -1; jj <= 1; jj++)
						{
							//if tile isnt floor, increment adj count
							if (grid[yi + ii][xi + jj].isSolid() != TILE_FLOOR)
								adjcnt_r1++;
						}

					for (ii = yi - 2; ii <= yi + 2; ii++)
						for (jj = xi - 2; jj <= xi + 2; jj++)
						{
							if (Math.abs(ii - yi) == 2 && Math.abs(jj - xi) == 2)
								continue;
							if (ii < 0 || jj < 0 || ii >= ySize || jj >= xSize)
								continue;
							if (grid[ii][jj].isSolid() != TILE_FLOOR)
								adjcnt_r2++;
						}

					if (adjcnt_r1 >= r1_cutoff || adjcnt_r2 <= r2_cutoff)
						grid2[yi][xi] = new WallCell();
					else
						grid2[yi][xi] = new FloorCell();
				}
			//Copy grid2 over grid1
			for (yi = 1; yi < ySize - 1; yi++)
				for (xi = 1; xi < xSize - 1; xi++)
					grid[yi][xi] = grid2[yi][xi];
		}
	}//generation(int x)
	
	//Call to run a X generations of the CA
	public void generation(int x, int r1Cutoff, int r2Cutoff)
		{
			this.r1_cutoff = r1Cutoff;
			this.r2_cutoff = r2Cutoff;
			for(int i = 0; i < x; i++) {
				int xi, yi, ii, jj;
				//look at all tiles except the outer most tiles
				for(yi=1;yi<ySize-1;yi++)
					for (xi = 1; xi < xSize - 1; xi++)
					{
						int adjcnt_r1 = 0, adjcnt_r2 = 0;

						//Look at the tiles surrounding the current one
						for(ii=-1; ii<=1; ii++)
							for (jj = -1; jj <= 1; jj++)
							{
								//if tile isnt floor, increment adj count
								if (grid[yi + ii][xi + jj].isSolid() != TILE_FLOOR)
									adjcnt_r1++;
							}

						for (ii = yi - 2; ii <= yi + 2; ii++)
							for (jj = xi - 2; jj <= xi + 2; jj++)
							{
								if (Math.abs(ii - yi) == 2 && Math.abs(jj - xi) == 2)
									continue;
								if (ii < 0 || jj < 0 || ii >= ySize || jj >= xSize)
									continue;
								if (grid[ii][jj].isSolid() != TILE_FLOOR)
									adjcnt_r2++;
							}

						if (adjcnt_r1 >= r1_cutoff || adjcnt_r2 <= r2_cutoff)
							grid2[yi][xi] = new WallCell();
						else
							grid2[yi][xi] = new FloorCell();
					}
				//Copy grid2 over grid1
				for (yi = 1; yi < ySize - 1; yi++)
					for (xi = 1; xi < xSize - 1; xi++)
						grid[yi][xi] = grid2[yi][xi];
			}
		}//generation(int x)
	private boolean randSolid() {
		int randNum = random.nextInt(100);
		if (randNum < solidProb)
			return TILE_WALL;
		else
			return TILE_FLOOR;
	}//initGrid()

	/*
	 * Testing only
	 */
	public void printMap()
	{
		int yi,xi;
		for (yi = 0; yi < ySize; yi++)
		{
			for (xi = 0; xi < xSize; xi++)
			{
				System.out.print(grid[yi][xi].getSymbol());
			}
			System.out.print('\n');
		}
	}

	public int getxSize() {
		return xSize;
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public int getySize() {
		return ySize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	public int getR1_cutoff() {
		return r1_cutoff;
	}

	public void setR1_cutoff(int r1_cutoff) {
		this.r1_cutoff = r1_cutoff;
	}

	public int getR2_cutoff() {
		return r2_cutoff;
	}

	public void setR2_cutoff(int r2_cutoff) {
		this.r2_cutoff = r2_cutoff;
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public void setGrid(Cell[][] grid) {
		this.grid = grid;
	}
}
