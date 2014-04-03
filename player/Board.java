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
    public final int N = 0;
    public final int NE = 1;
    public final int E = 2;
    public final int SE = 3;
    public final int S = 4;
    public final int SW = 5;
    public final int W = 6;
    public final int NW = 7;
    private int score = 0;
    public boolean isUpdatingEnemy = false;
   
    
    public Board(MachinePlayer p) {
        table = new DListNode[DIMENSION][DIMENSION];
        owner = p;
        history = new Stack();
    }
    private int opponentColor(){
        if(owner.color == 1){
            return 0;
        }
        else
            return 1;
    }
    	

        public DListNode[] connections(int x, int y){
            
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
                if(table[r][c] == null){
                    testMove = new Move(r,c);
                    if(isValidMove(testMove)){
                        listOMoves.insertBack(testMove);
                    }
                } else {
                    if((Integer)table[r][c].getItem() == owner.color){
                        for(int x = 0; x < DIMENSION; x++){
                            for(int y = 0; y < DIMENSION; y++){
                                if(x == r && y == c){
                                    continue;
                                }
                                else  if(table[x][y] == null){
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

    /** Enforces rules 1 and 2 and 3 **/
    public boolean isValidMove(Move m){
        int x = m.x1;
        int y = m.y1;
        int color;
        if(isUpdatingEnemy)
            color = opponentColor();
        else
            color = owner.color;
        //System.out.println(x + ", "+y);
        if(x < 0 || x > DIMENSION - 1 || y < 0 || y > DIMENSION - 1){
            return false;
        }
        if((x == 0 && y == 0) 
           || (x == DIMENSION - 1 && y == DIMENSION - 1)
           || (x == 0 && y == DIMENSION - 1)
           || (x == DIMENSION - 1 && y == 0)){
            return false;
        }
        
        if(color == 1){
            if(y == 0 || y == DIMENSION - 1){
                return false;
            }
        }       
        else{
            if(x == 0 || x == DIMENSION - 1){
                return false;
            }
        }

        if(table[x][y] != null){
            return false;
        }
        
        return ruleFour(m, m.x1, m.y1, null);
    }

    private boolean ruleFour(Move m, int oX, int oY, DListNode oldNode){
        int color;
        if(isUpdatingEnemy)
            color = opponentColor();
        else
            color = owner.color;
        table[m.x1][m.y1] = new DListNode(color);
        
        if(m.moveKind == 0) {
            return true;
        }
        if(m.moveKind == 2 && (m.x1 == oX && m.y1 == oY)){
            oldNode = table[m.x2][m.y2];
            table[m.x2][m.y2] = null;
         //   System.out.println("Intermediary 1 : \n" + this);
          //  System.out.println("OldNode:" + oldNode + " goes at: " + m.x2 + ", " + m.y2);
        }
        
        int numSurrounds = 0;
        int xToCheck = 0;
        int yToCheck = 0;

        for(int r = -1; r <= 1; r++){
            for(int c = -1; c <= 1; c++){
                if(r == 0 && c == 0) {
                    continue;
                }
                DListNode node = null;
                try{
                    node = table[oX + r][oY+c];
                }
                catch( ArrayIndexOutOfBoundsException e){}
                if(node != null && ((Integer)node.getItem()) == color){
                    numSurrounds++;
                    xToCheck = oX + r;
                    yToCheck = oY + c;
                }
            }
        }
        
        if(numSurrounds > 1){
            //System.out.println("It seems that I was wrong.");
            if(m.moveKind == 2){    
                table[m.x2][m.y2] = oldNode;
                //System.out.println(table[m.x2][m.y2] + " at " + m.x2 + ", " + m.y2);
            }
            table[m.x1][m.y1] = null;
            return false;
        }

        if(numSurrounds == 1){
            if(xToCheck == m.x1 && yToCheck == m.y1){
                if(m.moveKind == 2){    
                    table[m.x2][m.y2] = oldNode;
                }
                table[m.x1][m.y1] = null;
                return true;
            }
            else{
                if(m.moveKind == 2){    
                    table[m.x2][m.y2] = oldNode;
                }
                table[m.x1][m.y1] = null;
                
                return ruleFour(m, xToCheck, yToCheck, oldNode);
            }
        } else {
            if(m.moveKind == 2){    
                table[m.x2][m.y2] = oldNode;
            }
            table[m.x1][m.y1] = null;
            return true;
        }
    }


    public void makeMove(Move m, int color){
        history.push(this);
    	if(m.moveKind == Move.ADD){
    		table[m.x1][m.y1] = new DListNode(color);
    	}else if (m.moveKind == Move.STEP){
            table[m.x2][m.y2] = null;
            table[m.x1][m.y1] = new DListNode(color);
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
        if (start == null) {
            return networks;
        }
        for (int i = 0; i < start.length; i++) {
            //Get a network from each start node.
            DList currNet = new DList();
            currNet.insertBack(start[i]);
            DList network = checkConnections(start, i, end, 1, currNet);
            if (network != null) {
                //If there is a network, add it to the list of networks.
                networks.insertBack(network);
            }
        }
        return networks;
    }
    /** getNetwork recursive helper. **/
    private DList checkConnections(DListNode[]NodeList, int currNodeIndex, DListNode[] endGoal, int step
                                   , DList currNet) {
        DListNode currNode = NodeList[currNodeIndex];
        if(step >= 4){
            score += step; //for every possible connection over 4 points, score increases by that connection's length
        }
        DListNode[] cons;
        if(owner.color == 0){
            cons = connections(currNodeIndex, 0);
        }
        else{
            cons = connections(0, currNodeIndex);
        }
        //Base case. If the current node is an end goal, and is long enough, it is a network.
        if (contains(endGoal, currNode) && step >= 6){
            return currNet;
        } else if (cons.length == 0) { //No connections? No network!
            return null;
        } else { //Look for networks through the following connections.
            for (int i = 0; i < cons.length; i++) {
                DList nextNet = currNet;
                cons[i].setVisit();//Marks a node as visited. Needs to be implemented.
                nextNet.insertBack(cons[i]);
                DList newNet = checkConnections(cons, i, endGoal, step + 1, nextNet);
                if (newNet != null) {
                    return newNet;
                }
            }
            return null;
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
        if (owner.color == 0) {
            //Check the black goal
            for (int x = 0; x < DIMENSION; x++) {
                if (table[x][0] != null && table[x][0].getItem() == new Integer(owner.color)) {
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
                if (table[0][y] != null && table[0][y].getItem() == new Integer(owner.color)) {
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
            score += (nodes.length * 4); //boardEval gets four point for each start node
            return nodes;
        }
    }

    private DListNode[] getEndNodes() {
        int goalSize = DIMENSION - 2;
        DListNode[] nodes = new DListNode[goalSize];
        int added = 0;
        int end = DIMENSION - 1;
        if (owner.color == 0) {
            //Check the black goal
            for (int x = 0; x < DIMENSION; x++) {
                if (table[x][end] != null && table[x][end].getItem() == new Integer(owner.color)) {
                    nodes[x + goalSize] = table[x][end];
                }
            }
        } else {
            //Check the white goals
            for (int y = 0; y < DIMENSION; y++) {
                if (table[end][y] != null && table[end][y].getItem() == new Integer(owner.color)) {
                    nodes[y + goalSize] = table[end][y];
                }
            }
        }
        if (added == 0) {
            return null;
        } else {
            score += (nodes.length * 2); //boardEval gets two point for each end node
            return nodes;
        }
    }

   public double boardEval() {
        for(int x = 0; x < DIMENSION; x++){
            for(int y = 0; y < DIMENSION; y++){
                if(table[x][y] != null){
                    
                    for(int r = -1; r <= 1; r++){
                      for(int c = -1; c <= 1; c++){
                         if(r == 0 && c == 0){
                             continue;
                         } 
                         if(x+r < DIMENSION && x+r > -1 && y+c <DIMENSION && y+c >-1){
                            if(table[x+r][y+c] != null){
                                score += 1; //2 points for each pair 
                            }
                        }
                      }  
                    }
                
                }
            }
        }
        return score;
    }
    @Override
    public String toString(){
      String rv = "[\n";
      for(int x = 0; x < this.DIMENSION; x++){
          for(int y = 0; y < this.DIMENSION; y++){
              if(table[y][x] != null)
                rv += "| " + table[y][x].toString() + " |";
              else
                  rv += "| _ |";
        }
         rv += "\n"; 
      }
      rv += "]";
      
      return rv;
        
    }
    private static void testIsValidMove2(){
        Player black = new MachinePlayer(0);
        System.out.println("0: \n" + black);
        
        black.forceMove(new Move(3,3));
        System.out.println("1: \n" + black);
        
        black.opponentMove(new Move(2,2));
        System.out.println("2: \n" + black);
        
        black.forceMove(new Move(4,3));
        System.out.println("3: \n" + black);
        
        black.opponentMove(new Move(2,3));
        System.out.println("4: \n" + black);
        
        black.forceMove(new Move(5,3));
        
        System.out.println("5: True true true true false: \n" + black);
        
        
    }
    private static void testIsValidMove(){
        Player black = new MachinePlayer(0);

        black.forceMove(new Move(0,0));
        System.out.println("Finishing placing (0,0)");
        System.out.println("(0,0): \n" + black);

        black.forceMove(new Move(3,2));
        System.out.println("Finishing placing (3,2)");                
        System.out.println("(3,2): \n" + black);

        black.forceMove(new Move(3,4));
        System.out.println("Finishing placing (3,4)");
        black.forceMove(new Move(3,3));
        System.out.println("Finishing placing (3,3)");
        System.out.println("Cluster: \n" + black);


        black.forceMove(new Move(2,2,3,4));
        System.out.println("Finishing placing (2, 2)");


        System.out.println("Step: \n" + black);

        black.forceMove(new Move(3,4));
        black.forceMove(new Move(3,3,2,2));


        System.out.println("Step Cluster: \n" + black);

    }
    private static void testValidMoves(){
        MachinePlayer black = new MachinePlayer(1);
        
        DList d = black.board.validMoves();
        System.out.println("Empty: " + d);
        
        black.forceMove(new Move(3,4));
        d = black.board.validMoves();
        System.out.println("(3,4): " + d);
    }
    public static void testIsValidMove3(){
        MachinePlayer black = new MachinePlayer(0);
       // black.forceMove(new Move(0,1));
        black.opponentMove(new Move(0,1));
        System.out.println("(0,1): \n" + black);

        
    }
    public static void testChooseMove(){
        MachinePlayer black = new MachinePlayer(0);
        Move m = black.chooseMove();
        black.forceMove(m);
        
        System.out.println("Choose Board on Empty: \n" + black);
    }
    public static void testIsValidMove4(){
        MachinePlayer black = new MachinePlayer(0);
        black.forceMove(new Move(4,0));
        black.opponentMove(new Move(1,1));
        black.forceMove(new Move(2,1));
        black.opponentMove(new Move(6,1));
        black.forceMove(new Move(3,2));
        black.opponentMove(new Move(2,2));
        black.forceMove(new Move(5,2));
        black.opponentMove(new Move(4,2));
        black.forceMove(new Move(6,3));
        black.opponentMove(new Move(5,3));
        black.forceMove(new Move(2,4));
        black.opponentMove(new Move(1,4));
        black.forceMove(new Move(3,5));
        black.opponentMove(new Move(2,5));
        black.forceMove(new Move(5,5));
        black.opponentMove(new Move(4,5));
        black.forceMove(new Move(6,6));
        black.opponentMove(new Move(5,6));
        black.opponentMove(new Move(0,6));
        black.forceMove(new Move(1,7));
        
        System.out.println("Step 1: \n" + black);
       // black.forceMove(new Move(3,3,3,2));
       // System.out.println("Step 1.1: \n" + black);
        
        black.forceMove(new Move(3,10,3,2));
        System.out.println("Out of Bounds: \n" + black);
        
        
    }
  public static void main (String args[]){
       
   //    testIsValidMove();
    //   testIsValidMove2(); 
     //  testValidMoves();
      //  testIsValidMove3(); 
     
      //testChooseMove();  
      testIsValidMove4();
  }
 }

