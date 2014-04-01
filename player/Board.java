/*Board.java*/

package player;

public class Board{

    protected final int DIMENSION = 8;
    public DListNode[][] table;
    private Stack history;
    
    
    public Board(){
        table = new DListNode[DIMENSION][DIMENSION];
    }
    

    public DList connections(int x, int y){
        DList connected = new DList();
        if(table[x][y] == null && table[x][y].item == null){
            return connected;
        }
        int color = (int)table[x][y].item;
        for(int i = x+1 ; i < DIMENSION; i++){
            if(table[i][y] == null && table[i][y].item == null){
                continue;
            }elif(table[i][y].item == color){
                connected.insertFront(table[i][y].item);
            }break;
        }
        for(int i = 0 ; i < x ; i++){
            if(table[i][y] == null && table[i][y].item == null){
                continue;
            }elif(table[i][y].item == color){
                connected.insertFront(table[i][y].item);
            }break;
        }
        for(int j = y+1 ; j < DIMENSION; j++){
            if(table[x][j] == null && table[x][j].item == null){
                continue;
            }elif(table[x][j].item == color){
                connected.insertFront(table[x][j].item);
            }break;
        }
        for(int j = 0 ; j < y ; j++){
            if(table[x][j] == null && table[x][j].item == null){
                continue;
            }elif(table[x][j].item == color){
                connected.insertFront(table[x][j].item);
            }break;
        }
        for(int i = x + 1, j = y + 1 ; i < DIMENSION j < DIMENSION; i++, j++){
            if(table[i][j] == null && table[i][j].item == null){
                continue;
            }elif(table[i][j].item == color){
                connected.insertFront(table[i][j].item);
            }break;
        }for(int i = x + 1, j = 0 ; i < DIMENSION, j < y; i++, j++){
            if(table[i][j] == null && table[i][j].item == null){
                continue;
            }elif(table[i][j].item == color){
                connected.insertFront(table[i][j].item);
            }break; 
        }for(int i = 0, j = y + 1 ; i < x , j < DIMENSION; i++, j++){
            if(table[i][j] == null && table[i][j].item == null){
                continue;
            }elif(table[i][j].item == color){
                connected.insertFront(table[i][j].item);
            }break;
        }
        
        for(int i = 0, j = 0 ; i < x j < y; i++, j++){
            if(table[i][j] == null && table[i][j].item == null){
                continue;
            }elif(table[i][j].item == color){
                connected.insertFront(table[i][j].item);
            }break;
        }
       
        for(int i = x+1 ; i < DIMENSION ; i++){
            for(int j = 0 ; j < y ; j++){
                if(table[i][j] == null && table[i][j].item == null){
                    continue;
                }elif(table[i][j].item == color){
                    connected.insertFront(table[i][j].item);
                }break;
            }
        }
        
        


    public boolean hasNetworks(){
        return (getNetworks().size() != 0);  
    }
}
