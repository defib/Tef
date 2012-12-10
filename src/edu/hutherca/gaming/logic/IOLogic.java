package edu.hutherca.gaming.logic;

import java.util.ArrayList;
import java.util.List;

import edu.hutherca.gaming.utility.Cell;
import edu.hutherca.gaming.utility.Coordinates;

/*
 * This class controls all turn based and input output. 
 */
public class IOLogic {

	private List<Character> players;
	private List<Character> npcs;
	private List<Coordinates> occupiedCoordinates;

	
	public IOLogic(){
		this.players = new ArrayList<Character>();
		this.npcs = new ArrayList<Character>();
		this.occupiedCoordinates = new ArrayList<Coordinates>();
	}

	public List<Character> getPlayers() {
		return players;
	}


	public void setPlayers(List<Character> players) {
		this.players = players;
	}


	public List<Character> getNpcs() {
		return npcs;
	}


	public void setNpcs(List<Character> npcs) {
		this.npcs = npcs;
	}








	public void addPlayer(int i) {
		this.players.add(new Player(i));
		
	}

	public void placePlayer(Character p, Cell[][] dungeonMap) {
		//scan the dungeon for first unoccupied/floor tile square
		for(int y = 0; y < dungeonMap.length; y++){
			for(int x = 0; x < dungeonMap[y].length; x++){
				if (!dungeonMap[y][x].isSolid()){
					//see if open tile is occupied
					if (!spotOccupied(x,y)){
						p.setLocation(x, y);
						occupiedCoordinates.add(new Coordinates(x,y));
						return;
					}
					
				}
			}
		}	
	}

	private boolean spotOccupied(int x, int y) {
		for(Coordinates c : occupiedCoordinates){
			if(c.getXCord() == x && c.getYCord() == y){
				return true;
			}
		}
		return false;
	}
}
