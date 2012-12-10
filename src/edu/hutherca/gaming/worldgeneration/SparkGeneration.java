package edu.hutherca.gaming.worldgeneration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.hutherca.gaming.utility.Cell;
import edu.hutherca.gaming.utility.Coordinates;

public class SparkGeneration {

	/*
	 * Data Members
	 */
	private List<Coordinates> sparkList = new LinkedList<Coordinates>();
	private Cell[][] grid; // Grid of object Cell
	private int xSize; //Size of row
	private int ySize; //Size of col
	private Random random = new Random();
	//
	//params
	private int initialSparks;
	private int landProb = 90;
	//smoothing()
	private int smoothingValue;
	private List<Class<? extends Cell>> neighborType;




	public SparkGeneration() {
		this.xSize = 30;
		this.ySize = 30;
		this.grid = new Cell[xSize][ySize];
	}
	public SparkGeneration(int x, int y)
	{
		this.xSize = x;
		this.ySize = y;
		this.grid = new Cell[xSize][ySize];
	}    

	public void initialize(int initialSparks)
	{
		int xRand, yRand;
		this.setInitialSparks(initialSparks);
		//run the initial amount for times
		for (int i = 0; i < initialSparks; i++)
		{
			//Get random coordinates
			xRand = random.nextInt(xSize - 1);
			yRand = random.nextInt(ySize - 1);

			//mark point as LAND
			grid[xRand][yRand] = new LandCell();
			//Add coordinates to sparkList
			sparkList.add(new Coordinates(xRand, yRand));
		}
		//make the outermost tiles ocean
		for (int xi = 0; xi < xSize; xi++)
			grid[xi][0] = grid[xi][ySize - 1] = new OceanCell();
		for (int yi = 0; yi < ySize; yi++)
			grid[0][yi] = grid[xSize - 1][yi] = new OceanCell();

	}    
	public void initialize(int initialSparks, Cell initSpark)
	{
		int xRand, yRand;
		this.setInitialSparks(initialSparks);
		//run the initial amount for times
		for (int i = 0; i < initialSparks; i++)
		{
			//Get random coordinates
			xRand = random.nextInt(xSize - 2) + 1;
			yRand = random.nextInt(ySize - 2) + 1;

			//mark point as initialSpark
			grid[xRand][yRand] = initSpark;
			//Add coordinates to sparkList
			sparkList.add(new Coordinates(xRand, yRand));
		}
		//make the outermost tiles ocean
		for (int xi = 0; xi < xSize; xi++)
			grid[xi][0] = grid[xi][ySize - 1] = new OceanCell();
		for (int yi = 0; yi < ySize; yi++)
			grid[0][yi] = grid[xSize - 1][yi] = new OceanCell();

	}
	public void initialize(int initialSparks, Cell initSpark, Cell outerWall)
	{
		int xRand, yRand;
		this.setInitialSparks(initialSparks);
		//run the initial ammount fo times
		for (int i = 0; i < initialSparks; i++)
		{
			//Get random coodinates
			xRand = random.nextInt(xSize - 1 + 1) + 1;
			yRand = random.nextInt(ySize - 1 + 1) + 1;

			//mark point as initialSpark
			grid[xRand][yRand] = initSpark;
			//Add coodrinates to sparkList
			sparkList.add(new Coordinates(xRand, yRand));
		}
		//make the outermost tiles equalto Outerwall
		for (int xi = 0; xi < xSize; xi++)
			grid[xi][0] = grid[xi][ySize - 1] = outerWall;
		for (int yi = 0; yi < ySize; yi++)
			grid[0][yi] = grid[xSize - 1][yi] = outerWall;

	}

	public void generate() //generate(this.sparkList, new LandCell(), new OceanCell(), this.landProb, 0)
	{
		//repeat until list is empty
		while (sparkList.size() > 0)
		{
			//pick a random element from the spark list
			int randSpark = random.nextInt(sparkList.size());
			Coordinates randSparkCoord = sparkList.get(randSpark);
			//Check each adjacent neighbor in turn.
			for (int ii = -1; ii <= 1; ii++)
			{
				for (int jj = -1; jj <= 1; jj++)
				{
					//if Uninitialized, 
					if (grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] == null)
					{
						//make either land or Sea based on landProb
						int randLand = random.nextInt(100);
						if (randLand < landProb)
							grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = new LandCell();
						else
							grid[randSparkCoord.getXCord() + ii][randSparkCoord.getXCord() + jj] = new OceanCell();
						//add the neighboring tile to the sparklist
						sparkList.add(new Coordinates(randSparkCoord.getXCord() + ii, randSparkCoord.getYCord() + jj));
					}
				}
			}
			//now remove the spark from the list
			sparkList.remove(randSparkCoord);
		}
	}
	public void generate(List<Coordinates> initialSparkList, Cell chanceType, Cell defaultType, int chanceProb, int sparkLimit)
	{
		this.sparkList = initialSparkList;
		//repeat until list is below sparkLimit
		{
			//repeat until list is empty
			while (sparkList.size() > sparkLimit)
			{
				int randSpark = random.nextInt(sparkList.size());
				Coordinates randSparkCoord = sparkList.get(randSpark);
				//Check each adjacent neighbor in turn.
				for (int ii = -1; ii <= 1; ii++)
				{
					for (int jj = -1; jj <= 1; jj++)
					{
						//if Uninitialized, 
						if (grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] == null)
						{
							//make either land or Sea based on landProb
							int randLand = random.nextInt(100);
							if (randLand < chanceProb)
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = chanceType;
							else
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getXCord() + jj] = defaultType;
							//add the neighboring tile to the sparklist
							sparkList.add(new Coordinates(randSparkCoord.getXCord() + ii, randSparkCoord.getYCord() + jj));
						}
					}
				}
				//now remove the spark from the list
				sparkList.remove(randSparkCoord);
			}
		}
	}

	public void generateV2() //generateV2(this.sparkList, new LandCell(), new OceanCell(), this.landProb, 0)
	{
		//repeat until list is empty
		while (sparkList.size() > 0)
		{
			//pick a random element from the spark list
			int randSpark = random.nextInt(sparkList.size());
			Coordinates randSparkCoord = sparkList.get(randSpark);
			//Check to see if chosen spark is Land, if it is, make %land neighbors, if Ocean, make all Ocean.
			boolean isLand;
			if (grid[randSparkCoord.getXCord()][randSparkCoord.getYCord()] instanceof LandCell)
				isLand = true;
			else
				isLand = false;
			//Check each adjacent neighbor in turn.
			for (int ii = -1; ii <= 1; ii++)
			{
				for (int jj = -1; jj <= 1; jj++)
				{
					//if Uninitialized, 
					if (grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] == null)
					{

						if (isLand) //make either land or Sea based on landProb
						{
							int randLand = random.nextInt(100);
							if (randLand < landProb)
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = new LandCell();
							else
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = new OceanCell();
						}
						else
							grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = new OceanCell();
						//add the neighboring tile to the sparklist
						sparkList.add(new Coordinates(randSparkCoord.getXCord() + ii, randSparkCoord.getYCord() + jj));
					}
				}
			}
			//now remove the spark from the list
			sparkList.remove(randSparkCoord);
		}
	}
	public void generateV2(Cell chanceType, Cell defaultType, int chanceProb)
	{
		//repeat until list is empty, or has run 
		while (sparkList.size() > 0)
		{
			//pick a random element from the spark list
			int randSpark = random.nextInt(sparkList.size());
			Coordinates randSparkCoord = sparkList.get(randSpark);
			//Check to see if chosen spark is Land, if it is, make %land neighbors, if Ocean, make all Ocean.
			boolean isChance;
			if (grid[randSparkCoord.getXCord()][randSparkCoord.getYCord()].getClass() == chanceType.getClass())
				isChance = true;
			else
				isChance = false;
			//Check each adjacent neighbor in turn.
			for (int ii = -1; ii <= 1; ii++)
			{
				for (int jj = -1; jj <= 1; jj++)
				{
					//if Uninitialized, 
					if (grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] == null)
					{

						if (isChance) //make either land or Sea based on landProb
						{
							int randLand = random.nextInt(100);
							if (randLand < chanceProb)
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = chanceType;
							else
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = defaultType;
						}
						else
							grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = defaultType;
						//add the neighboring tile to the sparklist
						sparkList.add(new Coordinates(randSparkCoord.getXCord() + ii, randSparkCoord.getYCord() + jj));
					}
				}
			}
			//now remove the spark from the list
			sparkList.remove(randSparkCoord);
		}

	}//generateV2()
	public void generateV2(List<Coordinates> initialSparkList, Cell chanceType, Cell defaultType, int chanceProb)
	{
		this.sparkList = initialSparkList;
		//repeat until list is empty, or has run 
		while (sparkList.size() > 0)
		{
			//pick a random element from the spark list
			int randSpark = random.nextInt(sparkList.size());
			Coordinates randSparkCoord = sparkList.get(randSpark);
			//Check to see if chosen spark is Land, if it is, make %land neighbors, if Ocean, make all Ocean.
			boolean isChance;
			if (grid[randSparkCoord.getXCord()][randSparkCoord.getYCord()].getClass() == chanceType.getClass())
				isChance = true;
			else
				isChance = false;
			//Check each adjacent neighbor in turn.
			for (int ii = -1; ii <= 1; ii++)
			{
				for (int jj = -1; jj <= 1; jj++)
				{
					//if Uninitialized, 
					if (grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] == null)
					{

						if (isChance) //make either land or Sea based on landProb
						{
							int randLand = random.nextInt(100);
							if (randLand < chanceProb)
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = chanceType;
							else
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = defaultType;
						}
						else
							grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = defaultType;
						//add the neighboring tile to the sparklist
						sparkList.add(new Coordinates(randSparkCoord.getXCord() + ii, randSparkCoord.getYCord() + jj));
					}
				}
			}
			//now remove the spark from the list
			sparkList.remove(randSparkCoord);
		}

	}//generateV2()
	public void generateV2(Cell chanceType, Cell defaultType, int chanceProb, int timesToRun)
	{
		
		int timesRun = 0;
		boolean runWhile = true;
		//repeat until list is empty, or has run 
		while (sparkList.size() > 0 && runWhile)
		{
			//pick a random element from the spark list
			int randSpark = random.nextInt(sparkList.size());
			Coordinates randSparkCoord = sparkList.get(randSpark);
			//Check to see if chosen spark is Land, if it is, make %land neighbors, if Ocean, make all Ocean.
			boolean isChance;
			if (grid[randSparkCoord.getXCord()][randSparkCoord.getYCord()].getClass() == chanceType.getClass())
				isChance = true;
			else
				isChance = false;
			//Check each adjacent neighbor in turn.
			for (int ii = -1; ii <= 1; ii++)
			{
				for (int jj = -1; jj <= 1; jj++)
				{
					//if Uninitialized, 
					if (grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] == null)
					{

						if (isChance) //make either land or Sea based on landProb
						{
							int randLand = random.nextInt(100);
							if (randLand < chanceProb)
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = chanceType;
							else
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = defaultType;
						}
						else
							grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = defaultType;
						//add the neighboring tile to the sparklist
						sparkList.add(new Coordinates(randSparkCoord.getXCord() + ii, randSparkCoord.getYCord() + jj));
					}
				}
			}
			//now remove the spark from the list
			sparkList.remove(randSparkCoord);
			timesRun++;
			if (timesRun >= timesToRun)
				runWhile = false;
		}

	}//generateV2()
	public void generateV2(List<Coordinates> initialSparkList, Cell chanceType, Cell defaultType, int chanceProb, int timesToRun)
	{
		this.sparkList = initialSparkList;
		int timesRun = 0;
		boolean runWhile = true;
		this.sparkList = initialSparkList;
		//repeat until list is empty, or has run 
		while (sparkList.size() > 0 && runWhile)
		{
			//pick a random element from the spark list
			int randSpark = random.nextInt(sparkList.size());
			Coordinates randSparkCoord = sparkList.get(randSpark);
			//Check to see if chosen spark is Land, if it is, make %land neighbors, if Ocean, make all Ocean.
			boolean isChance;
			if (grid[randSparkCoord.getXCord()][randSparkCoord.getYCord()].getClass() == chanceType.getClass())
				isChance = true;
			else
				isChance = false;
			//Check each adjacent neighbor in turn.
			for (int ii = -1; ii <= 1; ii++)
			{
				for (int jj = -1; jj <= 1; jj++)
				{
					//if Uninitialized, 
					if (grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] == null)
					{

						if (isChance) //make either land or Sea based on landProb
						{
							int randLand = random.nextInt(100);
							if (randLand < chanceProb)
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = chanceType;
							else
								grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = defaultType;
						}
						else
							grid[randSparkCoord.getXCord() + ii][randSparkCoord.getYCord() + jj] = defaultType;
						//add the neighboring tile to the sparklist
						sparkList.add(new Coordinates(randSparkCoord.getXCord() + ii, randSparkCoord.getYCord() + jj));
					}
				}
			}
			//now remove the spark from the list
			sparkList.remove(randSparkCoord);
			timesRun++;
			if (timesRun >= timesToRun)
				runWhile = false;
		}

	}//generateV2()

	public void smoothing() throws InstantiationException, IllegalAccessException
	{
		//smoothing value
		this.smoothingValue = 7;
		//check every cell in the grid
		for (int x = 1; x < grid.length - 1; x++)
		{
			for (int y = 1; y < grid[0].length - 1; y++)
			{
				//mark down type of the cell, initialize neighborType list;
				Class<? extends Cell> cellType = grid[x][y].getClass();
				this.neighborType = new LinkedList<Class<? extends Cell>>();

				//check its neighbors
				for (int ii = -1; ii <= 1; ii++)
				{
					for (int jj = -1; jj <= 1; jj++)
					{
						//if not same type as Cell[x,y]
						if (cellType != grid[x + ii][y + jj].getClass())
						{
							//add to neighborType
							neighborType.add(grid[x + ii][y + jj].getClass());
						}

					}//for jj
				}//for ii

				//if number of neighborType is > smoothingValue
				if (neighborType.size() > smoothingValue)
				{
					//find most common type in neighborType
					Class<? extends Cell> commonType = null;
					HashMap<Class<? extends Cell>, Integer> sortMap = new HashMap<Class<? extends Cell>, Integer>();
					int maxOccurances = 1;
					for(int i = 0; i < neighborType.size(); i++){
						Class<? extends Cell> currentIndexVal = neighborType.get(i);
						if( sortMap.containsKey(currentIndexVal)){
							int currentOccurancesNum = (Integer) sortMap.get(currentIndexVal);
							currentOccurancesNum++;
							sortMap.put(currentIndexVal, currentOccurancesNum);
							if(currentOccurancesNum >= maxOccurances){
								commonType = currentIndexVal;
								maxOccurances = currentOccurancesNum;
							}
						} else{
							sortMap.put(currentIndexVal, 1);
						}
					}
					//make Cell[x,y] into most common type in neighborType
					grid[x][y] = commonType.newInstance();

				}
			}//for y

		}//for x

	}
	public void smoothing(int smoothingValue) throws InstantiationException, IllegalAccessException
	{
		//smoothing value
		this.smoothingValue = smoothingValue;
		//check every cell in the grid
		for (int x = 1; x < grid.length - 1; x++)
		{
			for (int y = 1; y < grid[0].length - 1; y++)
			{
				//mark down type of the cell, initialize neighborType list;
				Class<? extends Cell> cellType = grid[x][y].getClass();
				this.neighborType = new LinkedList<Class<? extends Cell>>();

				//check its neighbors
				for (int ii = -1; ii <= 1; ii++)
				{
					for (int jj = -1; jj <= 1; jj++)
					{
						//if not same type as Cell[x,y]
						if (cellType != grid[x + ii][y + jj].getClass())
						{
							//add to neighborType
							neighborType.add(grid[x + ii][y + jj].getClass());
						}

					}//for jj
				}//for ii

				//if number of neighborType is > smoothingValue
				if (neighborType.size() > smoothingValue)
				{
					//find most common type in neighborType
					Class<? extends Cell> commonType = null;
					HashMap<Class<? extends Cell>, Integer> sortMap = new HashMap<Class<? extends Cell>, Integer>();
					int maxOccurances = 1;
					for(int i = 0; i < neighborType.size(); i++){
						Class<? extends Cell> currentIndexVal = neighborType.get(i);
						if( sortMap.containsKey(currentIndexVal)){
							int currentOccurancesNum = (Integer) sortMap.get(currentIndexVal);
							currentOccurancesNum++;
							sortMap.put(currentIndexVal, currentOccurancesNum);
							if(currentOccurancesNum >= maxOccurances){
								commonType = currentIndexVal;
								maxOccurances = currentOccurancesNum;
							}
						} else{
							sortMap.put(currentIndexVal, 1);
						}
					}
					//make Cell[x,y] into most common type in neighborType
					grid[x][y] = commonType.newInstance();

				}
			}//for y

		}//for x

	}
	
	public int getInitialSparks() {
		return initialSparks;
	}
	public void setInitialSparks(int initialSparks) {
		this.initialSparks = initialSparks;
	}

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
				System.out.print(grid[xi][yi].getSymbol());
			}
			System.out.print('\n');
		}
	}

}
