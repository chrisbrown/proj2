/*Best.java*/
package player;

public class Best {
    public Move bestmove;
    public double bestscore;

    public Best(Move m, double s){
        bestmove = m;
        bestscore = s;
    }
    public Best(){
    }
    
    public Move move(){
        return bestmove;
    }
    public double score(){
        return bestscore;
    }
}
