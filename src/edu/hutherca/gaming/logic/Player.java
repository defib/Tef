package edu.hutherca.gaming.logic;

import edu.hutherca.gaming.utility.Coordinates;

public class Player extends Character {

	public Player(){
		this.isPlayer = true;
	}

	public Player(Integer i) {
		// TODO Auto-generated constructor stub
		this.symbol = (char) ('0' + i);
		this.name = "Player " + i.toString();
		this.isPlayer = true;
		this.turnNumber = i;
		this.location = new Coordinates(0,0);
	}
}
