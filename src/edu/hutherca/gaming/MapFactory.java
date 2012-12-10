package edu.hutherca.gaming;

import edu.hutherca.gaming.dungeongeneration.*;
import edu.hutherca.gaming.utility.*;

public class MapFactory {
	private Cell[][] dungeonMap;
	private CellularAutomation dungeonGenerator;
	
	public MapFactory(){
		this.setDungeonGenerator(new CellularAutomation());
	}

	public void generateDungeon(){
		this.dungeonGenerator.initGrid();
		this.dungeonGenerator.generation(25, 5, 1);
		this.setDungeonMap(this.dungeonGenerator.getGrid());
	}
	
	
	
	
	
	public Cell[][] getDungeonMap() {
		return dungeonMap;
	}

	private void setDungeonMap(Cell[][] dungeonMap) {
		this.dungeonMap = dungeonMap;
	}

	public CellularAutomation getDungeonGenerator() {
		return dungeonGenerator;
	}

	public void setDungeonGenerator(CellularAutomation dungeonGenerator) {
		this.dungeonGenerator = dungeonGenerator;
	}
	

}
