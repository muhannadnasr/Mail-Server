package dataStructures;

public class QueueLinkedBased {
    private class Node{
        Object data;
        Node next;
        Node(Object data){
            this.data = data;
            next = null;
        }
    }
    private Node head;
    private Node tail;
    private int size;

    public QueueLinkedBased(){
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty(){
        return (head == null);
    }

    public void enqueue(Object item){
        Node newNode = new Node(item);
        if(isEmpty()) head = newNode;
        else tail.next = newNode;
        tail = newNode;
        size ++;
    }

    public Object dequeue(){
        if(isEmpty()) return null;
        Object data = head.data;
        size --;
        head = head.next;
        return data;
    }

    public int size(){
        return size;
    }
}
