package stack;

import list.InvalidNodeException;

class StackTest {
    public static void main(String args[]) throws InvalidNodeException {
        Stack items = new Stack();
        for (int i = 0; i < 10; i++) items.push(i);
        System.out.println(items.toString());
        System.out.println(items.peek().toString());
        for (int i = 0; i < 5; i++) items.pop();
        System.out.println(items.toString());
    }
}
