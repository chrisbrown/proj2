/*Board.java*/

package player;

import stack.Stack;
import list.DList;
import list.DListNode;
import list.InvalidNodeException;

public class Board{

    protected final int DIMENSION = 8;
    public DListNode[][] table;
    private Stack history;
    public MachinePlayer owner;
    
    public Board(){
        table = new DListNode[DIMENSION][DIMENSION];
    }
    
    public Board(MachinePlayer p) {
        table = new DListNode[DIMENSION][DIMENSION];
        owner = p;
    }

    public DList connections(int x, int y){
        DList connected = new DList();
        if(table[x][y] == null || table[x][y].getItem() == null){
            return connected;
        }
        int color = (int)table[x][y].getItem();
        for(int i = x+1 ; i < DIMENSION; i++){
            if(table[i][y] == null && table[i][y].getItem() == null){
                continue;
            }else if((int)table[i][y].getItem() == color){
                connected.insertFront(table[i][y].getItem());
            }break;
        }
        for(int i = 0 ; i < x ; i++){
            if(table[i][y] == null && table[i][y].getItem() == null){
                continue;
            }else if((int)table[i][y].getItem() == color){
                connected.insertFront(table[i][y].getItem());
            }break;
        }
        for(int j = y+1 ; j < DIMENSION; j++){
            if(table[x][j] == null && table[x][j].getItem() == null){
                continue;
            }else if((int)table[x][j].getItem() == color){
                connected.insertFront(table[x][j].getItem());
            }break;
        }
        for(int j = 0 ; j < y ; j++){
            if(table[x][j] == null && table[x][j].getItem() == null){
                continue;
            }else if((int)table[x][j].getItem() == color){
                connected.insertFront(table[x][j].getItem());
            }break;
        }
        for(int i = x + 1, j = y + 1 ; i < DIMENSION && j < DIMENSION; i++, j++){
            if(table[i][j] == null && table[i][j].getItem() == null){
                continue;
            }else if((int)table[i][j].getItem() == color){
                connected.insertFront(table[i][j].getItem());
            }break;
        }for(int i = x + 1, j = 0 ; i < DIMENSION && j < y; i++, j++){
            if(table[i][j] == null && table[i][j].getItem() == null){
                continue;
            }else if((int)table[i][j].getItem() == color){
                connected.insertFront(table[i][j].getItem());
            }break; 
        }for(int i = 0, j = y + 1 ; i < x && j < DIMENSION; i++, j++){
            if(table[i][j] == null && table[i][j].getItem() == null){
                continue;
            }else if((int)table[i][j].getItem() == color){
                connected.insertFront(table[i][j].getItem());
            }break;
        }
        
        for(int i = 0, j = 0 ; i < x && j < y; i++, j++){
            if(table[i][j] == null && table[i][j].getItem() == null){
                continue;
            }else if((int)table[i][j].getItem() == color){
                connected.insertFront(table[i][j].getItem());
            }break;
        }
        return connected;
    }

    public DList validMoves(){
        DList listOMoves = new DList();
        Move testMove;
        
        for(int r = 0; r < DIMENSION; r++){
            for(int c = 0; c < DIMENSION; c++){
                if(table[r][c].getItem() == null){
                    testMove = new Move(r,c);
                    if(isValidMove(testMove)){
                        listOMoves.insertBack(testMove);
                    }
                } else {
                    if((int)table[r][c].getItem() == owner.color){
                        for(int x = 0; x < DIMENSION; x++){
                            for(int y = 0; y < DIMENSION; y++){
                                if(x == r && y == c){
                                    continue;
                                }
                                else  if(table[r][c].getItem() == null){
                                    testMove = new Move(x,y,r,c);
                                    if(isValidMove(testMove)){
                                        listOMoves.insertBack(testMove);
                                    }
                                }                 
                            }
                        }
                    }
                }
            }
        }
        return listOMoves;
    }

    /** Enforces rules 1 and 2 **/
    public boolean isValidMove(Move m){
        int x = m.x1;
        int y = m.y1;
        
        if((x == 0 && y == 0) 
           || (x == DIMENSION-1 && y == DIMENSION-1)
           || (x == 0 && y == DIMENSION - 1)
           || (x == DIMENSION - 1 && y == 0)){
            return false;
        }
        
        if(owner.color == 1){
            if(y == 0 || y == DIMENSION - 1){
                return false;
            }
        }       
        else{
            if(x == 0 || x == DIMENSION - 1){
                return false;
            }
        }

        return ruleThree(m, m.x1, m.y1);
    }

    private boolean ruleThree(Move m, int oX, int oY){
        DListNode history = null;
        if(m.moveKind == 0) {
            return true;
        }
        if(m.moveKind == 2){
            history = table[oX][oY];
            table[m.x2][m.y2] = null;
        }
        int numSurrounds = 0;
        int xToCheck = 0;
        int yToCheck = 0;

        for(int r = -1; r <= 1; r++){
            for(int c = -1; c <= 1; c++){
                if(r == 0 && c == 0) {
                    continue;
                }
                if(table[oX + r][oY + c].getItem() != null){
                        numSurrounds++;
                        xToCheck = oX + r;
                        yToCheck = oY + c;
                }
            }
        }
        
        if(numSurrounds > 1){
            table[m.x2][m.y2] = history;
            return false;
        }

        if(numSurrounds == 1){
            if(xToCheck == m.x1 && yToCheck == m.x2){
                table[m.x2][m.y2] = history;
                return true;
            }
            else{
                table[m.x2][m.y2] = history;
                return ruleThree(m, xToCheck, yToCheck);
            }
        } else {
            table[m.x2][m.y2] = history;
            return true;
        }
    }

    private int checkColor(){
        return owner.color;
    }

    public void makeMove(Move m){
        history.push(this);
    	if(m.moveKind == Move.ADD){
    		table[m.x1][m.y1] = new DListNode(owner.color);
    	}else if (m.moveKind == Move.STEP){
            table[m.x2][m.y2] = null;
            table[m.x1][m.y1] = new DListNode(owner.color);
        }
    }

    private void copy(Board board){
        table = board.table;
        owner = board.owner;
    }
    public void undo(){
        Board b = this;
        try {
        b = (Board) history.pop();
        } catch (InvalidNodeException e){
        
        }
        this.copy(b);
    }
    public boolean hasNetworks(){
        return (getNetworks().length() != 0);  
    }

    public DList getNetworks() {
        DList networks = new DList();
        return networks;
    }

    public double boardEval() {
        double score = 0.0;
        return score;
    }
    public static void main (String args[]){
		Player black = new MachinePlayer(0);
		Player white = new MachinePlayer(1);
		
		black.forceMove(new Move(0,0));
		white.forceMove(new Move(0,0));
    }

 }

