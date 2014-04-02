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
    //*find all connections at node on (x,y) *//	
    public DListNode[] connections(DListNode node){
            
        int x = -1;
        int y = -1;
           
        for(int r = 0; r < DIMENSION; r++){
            for(int c = 0; c < DIMENSION; c++){
                if(table[r][c] == node)
                    x = r;
                    y = c;
           }
        }
           
        DListNode[] connected = new DListNode[8];
        if(table[x][y] == null || table[x][y].getItem() == null){
                return connected;
        }
        int color = (Integer)table[x][y].getItem();
        int[] directions = {N, NE, E, SE, S, SW, W, NW};
        for(int i : directions){
            switch(i) {
                case N: connected = castN(x, y, color, connected);
                break;
     
                case NE: connected = castNE(x, y, color, connected);
                break;
     
                case E: connected = castE(x, y, color, connected);
                break;
     
                case SE: connected = castSE(x, y, color, connected);
                break;
                           
                case S: connected = castS(x, y, color, connected);
                break;
                           
                case SW: connected = castSW(x, y, color, connected);
                break;
     
                case W: connected = castW(x, y, color, connected);
                break;
     
                case NW: connected = castNW(x, y, color, connected);
                break;
                           
                default: break;
            }
        }
        return connected;
    }
               
    private DListNode[] castN(int x, int y, int color, DListNode[] connected){
        for(int j = y+1 ; j < DIMENSION; j++){
            if(table[x][j] == null && table[x][j].getItem() == null){
                continue;
            }else if((Integer)table[x][j].getItem() == color){
                connected[N]= table[x][j];
            }break;
        }
        return connected;
    }
       
    private DListNode[] castS(int x, int y, int color, DListNode[] connected){
        for(int j = 0 ; j < y ; j++){
            if(table[x][j] == null && table[x][j].getItem() == null){
                continue;
            }else if((Integer)table[x][j].getItem() == color){
                connected[S]=table[x][j];
            }break;
        }
        return connected;
    }
       
    private DListNode[] castE(int x, int y, int color, DListNode[] connected){
        for(int i = x+1 ; i < DIMENSION; i++){
            if(table[i][y] == null && table[i][y].getItem() == null){
                continue;
            }else if((Integer)table[i][y].getItem() == color){
                connected[E]= table[i][y];
            }break;
        }
        return connected;
    }
       
    private DListNode[] castW(int x, int y, int color, DListNode[] connected){
        for(int i = 0 ; i < x ; i++){
            if(table[i][y] == null && table[i][y].getItem() == null){
                continue;
            }else if((Integer)table[i][y].getItem() == color){
                connected[W] = table[i][y];
            }break;
        }
        return connected;
    }
       
    private DListNode[] castNE(int x, int y, int color, DListNode[] connected){
        for(int i = x + 1, j = y + 1 ; i < DIMENSION && j < DIMENSION; i++, j++){
            if(table[i][j] == null && table[i][j].getItem() == null){
                continue;
            }else if((Integer)table[i][j].getItem() == color){
                connected[NE] = table[i][j];
            }break;
        }
        return connected;
    }
    private DListNode[] castSE(int x, int y, int color, DListNode[] connected){
        for(int i = x + 1, j = 0 ; i < DIMENSION && j < y; i++, j++){
            if(table[i][j] == null && table[i][j].getItem() == null){
                continue;
            }else if((Integer)table[i][j].getItem() == color){
                connected[SE] = table[i][j];          
            }break;
        }
        return connected;
    }
    private DListNode[] castNW(int x, int y, int color, DListNode[] connected){
        for(int i = 0, j = y + 1 ; i < x && j < DIMENSION; i++, j++){
            if(table[i][j] == null && table[i][j].getItem() == null){
                continue;
            }else if((Integer)table[i][j].getItem() == color){
                connected[NW] = table[i][j];
            }break;
        }
        return connected;
    }
    private DListNode[] castSW(int x, int y, int color, DListNode[] connected){    
        for(int i = 0, j = 0 ; i < x && j < y; i++, j++){
            if(table[i][j] == null && table[i][j].getItem() == null){
                continue;
            }else if((Integer)table[i][j].getItem() == color){
                connected[SW] = table[i][j];
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
    /** Gets any current networks in the board. Assumes connections does
     *  not give any nodes in the same direction.
     **/
    public DList getNetworks() {
        DList networks = new DList();
        DListNode[] start = getStartNodes();
        DListNode[] end = getEndNodes();
        if (goals == null) {
            return networks;
        }
        for (int i = 0; i < start.length; i++) {
            //Get a network from each start node.
            DList network = checkConnections(start[i], end, 1);
            if (network != null) {
                //If there is a network, add it to the list of networks.
                networks.insertBack(network);
            }
        }
        return networks;
    }
    /** getNetwork recursive helper. **/
    private DList checkConnections(DListNode currNode, DListNode[] endGoal, int step
                                   , DList currNet) {
        DListNode[] cons = connections(currNode);
        //Base case. If the current node is an end goal, and is long enough, it is a network.
        if (contains(endGoal, currNode) && step >= 6){
            return currNet;
        } else if (cons.length() == 0) { //No connections? No network!
            return null;
        } else { //Look for networks through the following connections.
            for (int i = 0; i < cons.length; i++) {
                DList nextNet = currNet;
                cons[i].setVisit();//Marks a node as visited. Needs to be implemented.
                nextNet.insertBack(cons[i]);
                DList newNet = checkConnections(cons[i], endGoal, step + 1, nextNet);
                if (newNet != null) {
                    return newNet;
                }
            }
        }
    }
    /** Checks if a Node is in a DListNode array. */
    private boolean contains(DListNode[] items, DListNode item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                return true;
            }
        }
        return false;
    }

    /** Gets the nodes in a goal for the current player. */
    private DListNode[] getStartNodes() {
        int goalSize = DIMENSION - 2;
        DListNode[] nodes = new DListNode[goalSize];
        int added = 0;
        int end = DIMENSION - 1;
        if (color == 0) {
            //Check the black goal
            for (int x = 0; x < DIMENSION; x++) {
                if (table[x][0] != null && table[x][0].getItem() == color) {
                    added++;
                    nodes[x] = table[x][0];
                }
                /*if (table[x][end] != null && table[x][end].getItem() == color) {
                    nodes[x + goalSize] = table[x][end];
                    }*/
            }
        } else {
            //Check the white goals
            for (int y = 0; y < DIMENSION; y++) {
                if (table[0][y] != null && table[0][y].getItem() == color) {
                    added++;
                    nodes[y] = table[0][y];
                }
                /*if (table[end][y] != null && table[end][y].getItem() == color) {
                    nodes[y + goalSize] = table[end][y];
                    }*/
            }
        }
        if (added == 0) {
            return null;
        } else {
            return nodes;
        }
    }

    private DListNode[] getStartNodes() {
        int goalSize = DIMENSION - 2;
        DListNode[] nodes = new DListNode[goalSize];
        int added = 0;
        int end = DIMENSION - 1;
        if (color == 0) {
            //Check the black goal
            for (int x = 0; x < DIMENSION; x++) {
                if (table[x][end] != null && table[x][end].getItem() == color) {
                    nodes[x + goalSize] = table[x][end];
                }
            }
        } else {
            //Check the white goals
            for (int y = 0; y < DIMENSION; y++) {
                if (table[end][y] != null && table[end][y].getItem() == color) {
                    nodes[y + goalSize] = table[end][y];
                }
            }
        }
        if (added == 0) {
            return null;
        } else {
            return nodes;
        }
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

