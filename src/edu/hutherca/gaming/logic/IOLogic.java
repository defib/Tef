package edu.hutherca.gaming.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.hutherca.gaming.utility.Cell;
import edu.hutherca.gaming.utility.Coordinates;

/*
 * This class controls all turn based and input output. 
 */
public class IOLogic {

	private List<Character> players;
	private List<Character> npcs;
	private List<Coordinates> occupiedCoordinates;

	private Scanner iScanner = new Scanner(System.in);
	private String iString;
	
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

	
	public boolean takeTurn(int playerTurn, Cell[][] dungeonMap) {
		System.out.print("It is " + players.get(playerTurn).name
				+ "'s turn to move, press wsad then enter to go: ");
		iString = iScanner.nextLine();
		int x = this.players.get(playerTurn).location.getXCord();
		int y = this.players.get(playerTurn).location.getYCord();
		switch (iString){
		
		case "w" : if( spotOccupied(x,y-1) || dungeonMap[y-1][x].isSolid()){
			System.out.println("You can't move there");
			return false;
		} else {
			players.get(playerTurn).setLocation(x, y-1);
			occupiedCoordinates.get(playerTurn).setCoords(x,y-1);
			return true;
		}
		case "s" : if( spotOccupied(x,y+1) || dungeonMap[y+1][x].isSolid()){
			System.out.println("You can't move there");
			return false;
		} else {
			players.get(playerTurn).setLocation(x, y+1);
			occupiedCoordinates.get(playerTurn).setCoords(x,y+1);
			return true;
		}
		case "a" : if( spotOccupied(x-1,y) || dungeonMap[y][x-1].isSolid()){
			System.out.println("You can't move there");
			return false;
		} else {
			players.get(playerTurn).setLocation(x-1, y);
			occupiedCoordinates.get(playerTurn).setCoords(x-1,y);
			return true;
		}
		case "d" : if( spotOccupied(x+1,y) || dungeonMap[y][x+1].isSolid()){
			System.out.println("You can't move there");
			return false;
		} else {
			players.get(playerTurn).setLocation(x+1, y);
			occupiedCoordinates.get(playerTurn).setCoords(x+1,y);
			return true;
		}
		default : System.out.println("Sorry, but " + iString + 
				" is not valid input, try again");
		return false;
		}
	}
}
