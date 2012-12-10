package edu.hutherca.gaming;

import edu.hutherca.gaming.utility.DisplayFactory;

public class Main {

	private static MapFactory MAP;
	private static IOFactory LOGIC;
	private static DisplayFactory DISPLAY;
	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		MAP = new MapFactory();
		LOGIC = new IOFactory();
		DISPLAY = new DisplayFactory();
		
		//add 4 players
		MAP.generateDungeon();
		LOGIC.setDungeonMap(MAP.getDungeonMap());
		DISPLAY.setBaseMap(MAP.getDungeonMap());
		LOGIC.addPlayer();
		LOGIC.addPlayer();
		LOGIC.addPlayer();
		LOGIC.addPlayer();
		LOGIC.placePlayers();

		boolean game = true;
		
		while(game){
			LOGIC.playerTurn();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		for(int i = 5; i <= 8; i++){
			for(int j = 0; j <= 8; j++){
				CellularAutomation cellTest = new CellularAutomation(120, 45);
				cellTest.initGrid();
		       	cellTest.generation(50, i, j);
		       	System.out.println("R1 = " + i + ". R2 = " + j);
		        cellTest.printMap();
			}
		}
		*/
		
		/*SparkGeneration sparkTest = new SparkGeneration(79, 79);
		sparkTest.initialize(50, new MountainCell());
		sparkTest.generateV2(new MountainCell(), new LandCell(), 20, 250);
		sparkTest.generateV2(new LandCell(), new CoastCell(), 70, 150);
		sparkTest.generateV2(new LandCell(), new OceanCell(), 80);
		sparkTest.printMap();
		System.out.println("\n");
		sparkTest.smoothing(6);
		sparkTest.printMap();*/
		
		
	}
}
