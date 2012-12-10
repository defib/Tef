package edu.hutherca.gaming.logic;

import edu.hutherca.gaming.utility.Coordinates;

public abstract class Character {

	protected char symbol;
	protected String name;
	protected boolean isPlayer;
	protected int turnNumber;
	protected Coordinates location;
	
	
	public char getSymbol() {
		return symbol;
	}
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	public boolean isPlayer() {
		return isPlayer;
	}
	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}
	public int getTurnNumber() {
		return turnNumber;
	}
	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}
	public Coordinates getLocation() {
		return location;
	}
	public void setLocation(int x, int y) {
		this.location.setXCord(x);
		this.location.setYCord(y);
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
