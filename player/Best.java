/*Best.java*/
package player;

public class Best {
    public Move bestmove;
    public double bestscore;

    // Create a Best with a Move and score
    public Best(Move m, double s){
        bestmove = m;
        bestscore = s;
    }

    // Create an empty Best
    public Best(){
    }
    
    // Return the Best move
    public Move move(){
        return bestmove;
    }

    // Return the Best score
    public double score(){
        return bestscore;
    }
}
