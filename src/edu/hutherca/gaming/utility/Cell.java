package edu.hutherca.gaming.utility;

public abstract class Cell {

	/*
     * Data members
     */
	protected boolean solid;
     protected char symbol;
     /* 
      * * Methods
      */

 	public boolean isSolid() {
		return solid;
	}
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
     

}
