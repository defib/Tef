package edu.hutherca.gaming.dungeongeneration;

import edu.hutherca.gaming.utility.Cell;

public class WallCell extends Cell {
    public WallCell()
    {
        this.symbol = '#';
        this.solid = true;
    }
}
