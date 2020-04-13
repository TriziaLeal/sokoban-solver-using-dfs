import java.util.*;
public class State{
	char[][] board;
	Path keeper;
	ArrayList<String> action;

	State(){
		this.keeper = new Path();
		this.action = new ArrayList<String>();

	}

	State(char[][] layout, int x ,int y){
		this.board = layout;
		this.keeper = new Path(x,y);
		this.action = new ArrayList<String>();
	} 

	Path findKeeper(char[][] board){ 
		Path keeper = new Path();
		for(int i = 0; i<10; i++){
			for(int j = 0; j<10; j++){
				if (board[i][j] == GameProper.KEEPER || board[i][j] == GameProper.KOS){					
					keeper.x = i;
					keeper.y = j;
					return keeper;
				}
			}
		}return null;
	}

	boolean isWinner(){
		for(int i = 0; i<10; i++){
			for(int j = 0; j<10; j++){
				if (this.board[i][j] == GameProper.BOX)
					return false;
			}
		}
		return true;
	}

	char[][] getBoard(){
		return board;
	}

	char[][] move(int stepX,int stepY, String move){
		char[][] board = copyBoard(this.board);
		Path keeper = findKeeper(board);
		if (isBox(keeper.x+stepX,keeper.y+stepY)){
			int boxXpos = keeper.x+stepX;
			int boxYpos = keeper.y+stepY;
			switchTiles(boxXpos, boxYpos, boxXpos+stepX, boxYpos+stepY,board);
			switchTiles(keeper.x,keeper.y,keeper.x+stepX,keeper.y+stepY,board);
		}
		else switchTiles(keeper.x,keeper.y,keeper.x+stepX,keeper.y+stepY,board);
		// this.action.add(move);	
		return board;
	}


	ArrayList<String> getAction(){
		return this.action;
	}

	void addAction(ArrayList<String> path, String a){
		for(String p : path){
			this.action.add(p);
		}
		this.action.add(a);
	}

	private char[][] switchTiles(int prevX,int prevY,int nextX, int nextY, char[][] board){
		char prevTile = board[prevX][prevY];
		char nextTile = board[nextX][nextY];

		switch (nextTile){
			case GameProper.FLOOR:
					nextTile = Character.toLowerCase(prevTile);
				break;
			case GameProper.STORAGE:
					nextTile = Character.toUpperCase(prevTile);
				break;
			
		}
		if (Character.isLowerCase(prevTile)) prevTile = GameProper.FLOOR;
		else prevTile = GameProper.STORAGE;

		board[prevX][prevY] = prevTile;
		board[nextX][nextY] = nextTile;

		return board;
	}


	boolean isBox(int isBoxX,int isBoxY){
		if(this.board[isBoxX][isBoxY] == GameProper.BOX || this.board[isBoxX][isBoxY] == GameProper.BOS){
			return true;
		}
		else
			return false;		
	}
	boolean isWall(int isWallX,int isWallY){
		if(this.board[isWallX][isWallY] == GameProper.WALL || this.board[isWallX][isWallY] == GameProper.NONE)
			return true;
		return false;
	}

	char[][] copyBoard(char[][] board){
		char[][] newBoard = new char[10][10];
		for(int i = 0; i<10; i++){
			for(int j = 0; j<10; j++){
				newBoard[i][j] = board[i][j];
			}
		}

		return newBoard;
	}


}
