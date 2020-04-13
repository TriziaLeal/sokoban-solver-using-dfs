import java.util.*;

public class Solver{
	State state;

	Solver(GameProper game){
		this.state = new State(game.getLayout(),game.getKeeperXpos(),game.getKeeperYpos());
	}

	ArrayList<String> DFS(){
		System.out.println("solve by dfs");
		Stack<State> frontier = new Stack<State>();	
		ArrayList<char[][]> explored = new ArrayList<char[][]>();
		ArrayList<String> path = new ArrayList<String>();
		ArrayList<String> possibleActions ;
		char[][] s;
		char[][] newBoard;
		frontier.push(this.state);

		State currentState;		

		while(!(frontier.isEmpty())){	
			currentState = frontier.pop();
			path = currentState.getAction();
	
			s = currentState.getBoard();

			explored.add(s);
			
			if (currentState.isWinner()) return path;	
			possibleActions = Action(currentState);

			for (String a: possibleActions){
				newBoard = Result(currentState,a);
				// printBoard(newBoard);
				if (!(inExplored(newBoard,explored)) && !(inFrontier(newBoard,frontier))){
					State addState = new State();
					addState.board = newBoard;				
					addState.keeper = addState.findKeeper(newBoard);
					addState.addAction(path,a);
					frontier.push(addState);
				}
			}
		}
		return null;
	}

	private boolean inExplored(char[][] board, ArrayList<char[][]> explored){	
		if (explored.isEmpty()) return false;
		for(char[][] e: explored){
			for (int i = 0; i < 10; i++){
				for (int j = 0; j < 10; j++){
					if (board[i][j] != e[i][j])
						return false;
				}
			}
		}return true;
	}

	private boolean inFrontier(char[][] board, Stack<State> frontier){
		if (frontier.isEmpty()) return false;
		for(State f: frontier){
			for (int i = 0; i < 10; i++){
				for (int j = 0; j < 10; j++){
					if (board[i][j] != f.board[i][j])
						return false;
				}
			}
		}return true;
	}


	private char[][] Result(State state, String m){
		switch(m){
			case GameProper.UP:
				System.out.println("Move up");
				return state.move(-1,0, GameProper.UP);
			case GameProper.DOWN:
				System.out.println("Move down");
				return state.move(1,0, GameProper.DOWN);
			case GameProper.LEFT:
				System.out.println("Move left");
				return state.move(0,-1, GameProper.LEFT);
			case GameProper.RIGHT:
				System.out.println("Move right");
				return state.move(0,1, GameProper.RIGHT);
		}
		return null; //state
	}

	private ArrayList<String> Action(State s){
		ArrayList<String> possibleActions = new ArrayList<String>();
		int xTile=s.keeper.x;
		int yTile=s.keeper.y;
	
		//UP
		if(s.isBox(xTile-1,yTile)){
			xTile--;
			if(!(s.isWall(xTile-1,yTile)) && !(s.isBox(xTile-1,yTile)))
				possibleActions.add(GameProper.UP);
			xTile=s.keeper.x;
		}
		else{
			if(!(s.isWall(xTile-1,yTile)))
				possibleActions.add(GameProper.UP);			
		}
		//DOWN
		if(s.isBox(xTile+1,yTile)){
			xTile++;
			if(!(s.isWall(xTile+1,yTile)) && !(s.isBox(xTile+1,yTile)))
				possibleActions.add(GameProper.DOWN);
			xTile=s.keeper.x;
		}
		else{
			if(!(s.isWall(xTile+1,yTile)))
				possibleActions.add(GameProper.DOWN);			
		}
		 //LEFT
		if(s.isBox(xTile,yTile-1)){
			yTile--;
			if(!(s.isWall(xTile,yTile-1)) && !(s.isBox(xTile,yTile-1)))
				possibleActions.add(GameProper.LEFT);
			yTile=s.keeper.y;
		}
		else{
			if(!(s.isWall(xTile,yTile-1)))
				possibleActions.add(GameProper.LEFT);			
		}

		 //RIGHT
		if(s.isBox(xTile,yTile+1)){
			yTile++;
			if(!(s.isWall(xTile,yTile+1)) && !(s.isBox(xTile,yTile+1)))
				possibleActions.add(GameProper.RIGHT);
			yTile=s.keeper.y;
		}
		else{
			if(!(s.isWall(xTile,yTile+1)))
				possibleActions.add(GameProper.RIGHT);			
		}
		return possibleActions;
	}

	static void printBoard(char[][] board){
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		for(int i = 0; i<10; i++){
			for(int j = 0; j<10; j++){
				System.out.print(board[i][j] + " ");
			}
		System.out.println();
		}
	}
}