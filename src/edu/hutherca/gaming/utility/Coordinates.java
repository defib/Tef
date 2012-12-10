package edu.hutherca.gaming.utility;

public class Coordinates {


        //data members
        private int xCord;
        private int yCord;

        public Coordinates(int x, int y)
        {
            setXCord(x);
            setYCord(y);
        }

		public int getXCord() {
			return xCord;
		}

		public void setXCord(int xCord) {
			this.xCord = xCord;
		}

		public int getYCord() {
			return yCord;
		}

		public void setYCord(int yCord) {
			this.yCord = yCord;
		}

        
    
}
