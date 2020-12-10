package dataStructures;

public class PriorityQueue {

    DLList PriorityQ = new DLList();

    public void insert(Object item,int key) {

        if (key < 1) {
            throw new RuntimeException();
        }
        PqueueNode element = new PqueueNode(item, key);
        PqueueNode tail = new PqueueNode(0, 0);
        int flag = 0;
        int SameKey = 0;
        if (!PriorityQ.isEmpty()) {
            tail = (PqueueNode) PriorityQ.get(PriorityQ.size() - 1);
        }
        if (PriorityQ.isEmpty()) {
            PriorityQ.add(element);
        } else if (key > tail.key) {
            PriorityQ.add(element);
        } else {
            for (int i = 0; i < PriorityQ.size(); i++) {
                PqueueNode current = (PqueueNode) PriorityQ.get(i);

                if (key < current.key ) {
                    PriorityQ.add(i, element);
                    break;
                }
                if (i == PriorityQ.size()-1 ) {

                    PriorityQ.add(i+1, element);
                    break;
                }

            }

        }
    }


    public Object removeMin() {
        PqueueNode head = (PqueueNode) PriorityQ.get(0);
        PriorityQ.remove(0);
        return head.Item;
    }

    public Object removeMax() {
        PqueueNode tail = (PqueueNode) PriorityQ.get(PriorityQ.size() - 1);
        PriorityQ.remove(PriorityQ.size() - 1);
        return tail.Item;
    }



    public Object min() {

        PqueueNode head = (PqueueNode) PriorityQ.get(0);
        return head.Item;
    }

    public Object max() {

        PqueueNode tail = (PqueueNode) PriorityQ.get(PriorityQ.size() - 1);
        return tail.Item;
    }



    public boolean isEmpty() {

        return PriorityQ.isEmpty();
    }


    public int size() {

        return PriorityQ.size();
    }
}

