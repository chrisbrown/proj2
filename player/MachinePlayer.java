/* MachinePlayer.java */

package player;

import list.DList;
import list.DListNode;
import list.ListNode;
import list.InvalidNodeException;


/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */

public class MachinePlayer extends Player {
	
	public int depth;
	public int color;
	public Board board;
  public Player opponent;
	public final boolean COMPUTER = true;
	public final boolean HUMAN = false; 

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
      this.color = color;
      depth = 3; //change this
      board = new Board(this);
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
      depth = searchDepth;
      this.color = color;
      board = new Board(this);
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
      Best bestchoice = moveHelp(COMPUTER, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, this.depth);
      return bestchoice.move();
  } 
    //Assists in choosing the move by implementing minimax with alpha-beta pruning
  private Best moveHelp(boolean side, double  a, double b, int searchdepth){
      Best myBest = new Best();
      Best reply;
      double alpha = a;
      double beta = b;
      DList moveset = this.board.validMoves();
      if(this.board.hasNetworks()){
          return myBest;
      }
      // if at the bottom of the search, applies the score of the board to alpha/beta //
      if(searchdepth == 0){
        if(side == COMPUTER){
          alpha = this.board.boardEval();
        } else {
          beta = this.board.boardEval();
        }
      }
      //determines scores within the searchdepth //
      if(side == COMPUTER){
          myBest.bestscore = alpha;
      }else{
          myBest.bestscore = beta;
      }
      //create a set of valid moves and find a move
      ListNode node = moveset.front();
      Move mv = (Move) node.getItem();
      myBest.bestmove = mv;
      //minimax wtih alpha/beta pruning to find the best move
      while(mv != null && searchdepth >0){
          this.board.makeMove(mv);
          //go one level deeper in search
          reply = moveHelp(!side, alpha, beta, searchdepth-1);
          this.board.undo();
          //alpha-beta pruning: set alpha or beta dependent on what the search layer is
          if((side == COMPUTER) && (reply.score() >  myBest.score())){
              myBest.bestmove = mv;
              myBest.bestscore = reply.score();
              alpha = reply.score();
          }else if ((side == HUMAN) && (reply.score() < myBest.score())){
              myBest.bestmove = mv;
              myBest.bestscore = reply.score();
              beta = reply.score();
          }
          //returns the Best when alpha is >= beta
          if(alpha >= beta){
              return myBest;
          }
          try {
          node = node.next();
          } catch (InvalidNodeException e) {}
      }
      //returns a move even if there is no Best move
      return myBest;  
  }

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
      this.board.isUpdatingEnemy = true;
      if(this.board.isValidMove(m)){
          this.board.isUpdatingEnemy = false;
      
          if(color == 0){
            this.board.makeMove(m,1);
          }
          else{
            this.board.makeMove(m,0);
          }
          
          return true;
      }else{    
          this.board.isUpdatingEnemy = false;
      
          return false;
      }
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
      if(this.board != null && this.board.isValidMove(m)){
          this.board.makeMove(m);
          return true;
      }else{
          return false;
      }
  }

}
