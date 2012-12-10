package edu.hutherca.gaming.utility;

import java.util.List;

import edu.hutherca.gaming.logic.Character;

public class DisplayFactory {
	char[][] map;

	public DisplayFactory(){

	}

	public void setBaseMap(Cell[][] dungeonMap) {
		map = new char[dungeonMap.length][dungeonMap[0].length];
		for(int y = 0; y < dungeonMap.length; y++){
			for (int x = 0; x < dungeonMap[y].length; x++){
				map[y][x] = dungeonMap[y][x].getSymbol();
			}
		}
	}

	public void setCharacterLocation(List<Character> list) {
		for(Character c : list){
			map[c.getLocation().getYCord()][c.getLocation().getXCord()] 
					= c.getSymbol();
		}
	}

	public void printMap() {
		for(int y = 0; y < map.length; y++){
			for (int x = 0; x < map[y].length; x++){
				System.out.print(map[y][x]);
			}
			System.out.print('\n');
		}
		
	}
	
}
