package edu.hutherca.gaming;

import edu.hutherca.gaming.logic.*;
import edu.hutherca.gaming.logic.Character;
import edu.hutherca.gaming.utility.Cell;

public class IOFactory {

	private IOLogic gameLogic;
	private int currentTurn;
	private int nextTurn;
	
	private Cell[][] dungeonMap;
	
	
	
	public IOFactory(){
		this.gameLogic = new IOLogic();
		this.currentTurn = 1;
		this.nextTurn = 1;
	}
	
	public int addPlayer(){
		int numberOfPlayers = gameLogic.getPlayers().size();
		switch (numberOfPlayers) {
		case 0:	gameLogic.addPlayer(1);
		break;
		case 1: gameLogic.addPlayer(2);
		break;
		case 2: gameLogic.addPlayer(3);
		break;
		case 3: gameLogic.addPlayer(4);
		break;
		default: break;
		}
		return (gameLogic.getPlayers().size());
	}
	/*
	 * Places the players in the dungeon from gameLogic.getPlayers();
	 */
	public void placePlayers(){
		
		for(Character p : gameLogic.getPlayers()){
			gameLogic.placePlayer(p, dungeonMap);
		}
	}

	public void playerTurn() {
		// TODO Auto-generated method stub
		
	}
	
	
	public Cell[][] getDungeonMap() {
		return dungeonMap;
	}

	public void setDungeonMap(Cell[][] dungeonMap) {
		this.dungeonMap = dungeonMap;
	}

	public IOLogic getGameLogic() {
		return gameLogic;
	}

	public void setGameLogic(IOLogic gameLogic) {
		this.gameLogic = gameLogic;
	}
}
