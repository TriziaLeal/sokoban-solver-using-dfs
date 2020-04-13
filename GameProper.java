import java.util.*;
import java.io.FileWriter;  

public class GameProper{
	static final String UP = "UP";
	static final String DOWN = "DOWN";
	static final String LEFT = "LEFT";
	static final String RIGHT = "RIGHT";

	private GameArea gameArea;
	private int keeperXpos;
	private int keeperYpos;
	private char[][] layout;
	private Solver solver;

	static final char BOX = 'b';
	static final char BOS = 'B';
	static final char KEEPER = 'k';
	static final char KOS = 'K';
	static final char FLOOR = 'e';
	static final char STORAGE = 's';
	static final char WALL = 'w';
	static final char NONE = 'x';

	public GameProper(GameArea gameArea){
		this.gameArea = gameArea;
		this.keeperXpos = this.gameArea.getKeeperXpos();
		this.keeperYpos = this.gameArea.getKeeperYpos();
		this.layout = this.gameArea.getlayout();
	}

	public void getKey(String move){
		switch(move){
			case UP:
				move(-1,0);
				break;
			case DOWN:
				move(1,0);
				break;
			case LEFT:
				move(0,-1);
				break;
			case RIGHT:
				move(0,+1);
				break;
			case "S":
				solver = new Solver(this);
				System.out.println("hello");
				ArrayList<String> solution = solver.DFS();
				if(solution == null)
					System.out.println("No Solution!");
				System.out.println("Solution found!");
				System.out.println(solution);
				writeSolution(solution);
				System.out.println("File written successfully.");

				break;

		}
		this.gameArea.repaint();
		if (isWinner()){
			System.out.println("YOU WIN");
			this.gameArea.win();
		}
	}

	private void writeSolution(ArrayList<String> solution){
		String sol = new String();

		for (String s: solution){
			System.out.println(s);
			sol += s.charAt(0);
			sol += " ";
		}
		System.out.println("Solution " + sol);
		try{    
        	FileWriter fw=new FileWriter("puzzle.out");    
        	fw.write(sol);    
        	fw.close();    
        }catch(Exception e){System.out.println(e);}    

	}
	private void move(int stepX,int stepY){
		if (isWall(this.keeperXpos+stepX,this.keeperYpos+stepY)){
			System.out.println("Cannot move to the wall");
		}
		else{
			if (isBox(this.keeperXpos+stepX,this.keeperYpos+stepY)){
				int boxXpos = this.keeperXpos+stepX;
				int boxYpos = this.keeperYpos+stepY;
				if (isWall(boxXpos +stepX,boxYpos+stepY) || isBox(boxXpos +stepX,boxYpos+stepY))
					System.out.println("Cannot push the box to wall or another box");
				else{
					switchTiles(boxXpos, boxYpos, boxXpos+stepX, boxYpos+stepY);
					switchTiles(this.keeperXpos,this.keeperYpos,this.keeperXpos+stepX,this.keeperYpos+stepY);

				}
			}
			else switchTiles(this.keeperXpos,this.keeperYpos,this.keeperXpos+stepX,this.keeperYpos+stepY);
		}
	}

	private void switchTiles(int prevX,int prevY,int nextX, int nextY){
		char prevTile = this.layout[prevX][prevY];
		char nextTile = this.layout[nextX][nextY];

		switch (nextTile){
			case FLOOR:
					nextTile = Character.toLowerCase(prevTile);
				break;
			case STORAGE:
					nextTile = Character.toUpperCase(prevTile);
				break;
			
		}
		if (Character.isLowerCase(prevTile)) prevTile = FLOOR;
		else prevTile = STORAGE;

		this.layout[prevX][prevY] = prevTile;
		this.layout[nextX][nextY] = nextTile;

		findKeeper();
	}
	boolean isBox(int isBoxX,int isBoxY){
		if(this.layout[isBoxX][isBoxY] == BOX || this.layout[isBoxX][isBoxY] == BOS){
			return true;
		}
		else
			return false;		
	}


	boolean isWall(int isWallX,int isWallY){
		if(this.layout[isWallX][isWallY] == WALL || this.layout[isWallX][isWallY] == NONE)
			return true;
		return false;
	}

	private void findKeeper(){
		for(int i = 0; i<10; i++){
			for(int j = 0; j<10; j++){
				if (this.layout[i][j] == KEEPER || this.layout[i][j] == KOS){					
					this.keeperXpos = i;
					this.keeperYpos = j;
				break;
				}
			}
		}
	}

	boolean isWinner(){
		for(int i = 0; i<10; i++){
			for(int j = 0; j<10; j++){
				if (this.layout[i][j] == BOX)
					return false;
			}
		}
		return true;
	}

	GameArea getGameArea(){
		return this.gameArea;
	}

	char[][] getLayout(){
		return this.layout;
	}

	int getKeeperXpos(){
		return keeperXpos;
	}

	int getKeeperYpos(){
		return keeperYpos;
	}
}