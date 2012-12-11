package edu.hutherca.gaming;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.*;

import edu.hutherca.gaming.utility.DisplayFactory;

public class Main {

	private static MapFactory MAP;
	private static IOFactory LOGIC;
	private static DisplayFactory DISPLAY;
	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {
		BufferedReader inStream = new BufferedReader(
				new InputStreamReader(System.in));


		System.out.println("Welcome to Tef, " +
				"press 1 for single player, " +
				"2 for host mode, or 3 for client mode :");
		String line = inStream.readLine();
		
		
		
		if (line.equals("1")){
			//Single Player
			MAP = new MapFactory();
			LOGIC = new IOFactory();
			DISPLAY = new DisplayFactory();
			MAP.generateDungeon();
			LOGIC.setDungeonMap(MAP.getDungeonMap());
			LOGIC.addPlayer();
			LOGIC.addPlayer();
			LOGIC.placePlayers();
			boolean game = true;
			while(game){
				DISPLAY.setBaseMap(MAP.getDungeonMap());
				DISPLAY.setCharacterLocation(LOGIC.getGameLogic().getPlayers());
				DISPLAY.printMap();
				LOGIC.playerTurn();
			}
		} else if(line.equals("2")) {
			//Host mode
			MAP = new MapFactory();
			LOGIC = new IOFactory();
			Server server = new Server();
			//register classes
			Kryo kryo = server.getKryo();
			kryo.register(String.class);
			//
			server.start();
			server.bind(54555,  54777); //starts server on TCP 54555 and UDP 54777
			
			System.out.println("Waiting for connections on TCP 54555");
			//now wait for a client to connect
			server.addListener(new Listener() {
				public void received (Connection connection, Object object) {
					if (object instanceof String) {
						String request = (String)object;
						System.out.println(request);
						
						String response = new String("You are Player 2");
						connection.sendTCP(response);
					}
				}
			});
			
			System.out.println("Message Sent");
			
			
			
		} else if ( line.equals("3")) {
			//Client mode
			Client client = new Client();
			//register classes
			Kryo kryo = client.getKryo();
			kryo.register(String.class);
			//
			client.start();
			System.out.println("Attemping to find server on network....");
			InetAddress hostIP = client.discoverHost(54777, 5000);
			System.out.println("Found host at " + hostIP + ". Now connecting");
			client.connect(5000, hostIP, 54555, 54777);
			
			String request = new String("Can I play?");
			client.sendTCP(request);
			
			client.addListener(new Listener() {
				public void received (Connection connection, Object object) {
					if (object instanceof String) {
						String response = (String)object;
						System.out.println(response);
					}
				}
			});
			
			
			
		} else {
			System.out.println("You messed up and picked the wrong thing" +
					" butthead");
		}















		/*
		for(int i = 5; i <= 8; i++){
			for(int j = 0; j <= 8; j++){
				CellularAutomation cellTest = new CellularAutomation(120, 45);
				cellTest.initGrid();
		       	cellTest.generation(50, i, j);
		       	System.out.println("R1 = " + i + ". R2 = " + j);
		        cellTest.printMap();
			}
		}
		 */

		/*SparkGeneration sparkTest = new SparkGeneration(79, 79);
		sparkTest.initialize(50, new MountainCell());
		sparkTest.generateV2(new MountainCell(), new LandCell(), 20, 250);
		sparkTest.generateV2(new LandCell(), new CoastCell(), 70, 150);
		sparkTest.generateV2(new LandCell(), new OceanCell(), 80);
		sparkTest.printMap();
		System.out.println("\n");
		sparkTest.smoothing(6);
		sparkTest.printMap();*/


	}
	
}
