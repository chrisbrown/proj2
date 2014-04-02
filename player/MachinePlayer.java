/* MachinePlayer.java */

package player;

public int depth;
public int color;
public Board board;
public final boolean COMPUTER = true;
public final boolean HUMAN = false; 

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */

public class MachinePlayer extends Player {

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
      this.color = color;
      depth = 3; //change this
      board = new Board();
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
      depth = searchDepth;
      this.color = color;
      board = new Board();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
      Best bestchoice = moveHelp(COMPUTER, DOUBLE.NEGATIVE_INFINITY, DOUBLE.POSITIVE_INFINITY, this.depth);
      return bestchoice.move();
  } 

    private Best moveHelp(boolean side, double  a, double b, int searchdepth){
        Best myBest = new Best();
        Best reply;
        double alpha = a;
        double beta = b;
        DList moveset = this.board.validMoves();
        if(this.board.hasNetworks()){
            return myBest;
        }
        if(side == COMPUTER){
            myBest.score = alpha;
        }else{
            myBest.score = beta;
        }
        DListNode node = moveset.front();
        myBest.move = node;
        while(node != null && searchdepth >0){
            this.board.makeMove(node);
            reply = moveHelp(!side, alpha, beta, searchdepth-1);
            this.board.undo();
            if((side == COMPUTER) && (reply.score() >  myBest.score())){
                myBest.move = node;
                myBest.score = reply.score();
                alpha = reply.score();
            }else if ((side == HUMAN) && (reply.score() < myBest.score())){
                myBest.move = node;
                myBest.score = reply.score();
                beta = reply.score();
            }
            if(alpha >= beta){
                return myBest;
            }
            node = node.next();
        }
        return myBest;  
    }
                    
    private int otherPlayer(){
        if(color == 0){
            return 1;
        }return 0;
    }

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
      if(this.board.isValidMove(m)){
          this.board.makeMove(m, otherPlayer(this.color);
          return true;
      }else{    
          return false;
      }
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
      if(this.board.isValidMove(m)){
          this.board.makeMove(m, this.color);
          return true;
      }else{
          return false;
      }
  }

}
