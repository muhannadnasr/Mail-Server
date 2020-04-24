package Utilities;

public class QueueLinkedBased{
    private SinglyLinkedList queue = new SinglyLinkedList();

    public boolean isEmpty(){
        return (queue.isEmpty());
    }

    public void enqueue(Object data){
        queue.add(data);
    }

    public Object dequeue(){
        if(queue.isEmpty()) return null;
        Object data = queue.get(0);
        queue.remove(0);
        return data;
    }

    public int size(){
        return queue.size();
    }
}
