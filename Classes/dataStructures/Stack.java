package dataStructures;

public class Stack {
    class Node{
        Object data;
        Node next;
        Node(Object data){
            this.data = data;
            next = null;
        }
    }

    private Node top;
    int size = 0;

    public boolean isEmpty(){
        return (top == null);
    }

    public void push(Object operator){
        Node newNode = new Node(operator);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public Object pop(){
        if(isEmpty()) return null;
        Object data = top.data;
        top = top.next;
        size --;
        return data;
    }

    public Object peek(){
        if(isEmpty()) return null;
        return top.data;
    }
    public int size(){
        return size;
    }

}
