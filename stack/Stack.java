/*Stack.java*/

package stack;

import list.InvalidNodeException;
import list.SList;
import list.SListNode;

public class Stack{

    SList stack;
    public Stack(){
        stack = new SList();
    }

    public Object push(Object o){
        stack.insertFront(o);
        return o;
    }

    public Object pop() throws InvalidNodeException{
        Object o = stack.front();
        stack.front().remove();
        return ((SListNode)o).getItem();
    }

    public Object peek(){
        return stack.front();
    }

    public String toString() {
        return stack.toString();
    }
}
