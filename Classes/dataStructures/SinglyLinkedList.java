package dataStructures;

import Interfaces.ILinkedList;

public class SinglyLinkedList{
    private class SNode {
        Object data;
        SNode next;
        SNode(Object data){
            this.data = data;
            this.next = null;
        }
    }

    private SNode head;
    private int listSize;

    public SinglyLinkedList(){
        head  = null;
        listSize = 0;
    }

    public void add(int index, Object element){
        if(index < 0) return;
        int pos = 0;
        SNode currentNode = head;
        SNode newNode = new SNode(element);
        if(index == 0){
            newNode.next = head;
            head = newNode;
        }else{
            while(currentNode.next != null && pos++ < index-1) currentNode = currentNode.next;
            if(currentNode.next == null) return;
            newNode.next = currentNode.next;
            currentNode.next = newNode;
        }
        listSize ++;
    }

    public void add(Object new_data) {
        SNode newNode = new SNode(new_data);
        if (head == null) {
            head = newNode;
            listSize++;
            return;
        }
        newNode.next = null;
        SNode tail = head;
        while (tail.next != null) tail = tail.next;
        tail.next = newNode;
        listSize++;
        return;
    }
    public void push(Object new_data) {
        SNode new_node = new SNode(new_data);
        new_node.next = head;
        head = new_node;
        listSize++;
    }

    public Object get(int index) {
        SNode current = head;
        int count = 0;
        while (current != null) {
            if (count == index) return current.data;
            count++;
            current = current.next;
        }
        return null;
    }


    public void set(int index, Object element){
        if(index < 0) return;
        int pos = 0;
        SNode currentNode = head;
        while(currentNode != null && pos++ < index) currentNode = currentNode.next;
        if(currentNode == null) return;
        currentNode.data = element;
    }

    public void clear() {
        this.head = null;
        listSize = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void removeByIndex(int index){
        if(index < 0) return;
        int pos = 0;
        SNode currentNode = head;
        if(head == null) return; //empty list
        if(index == 0) head = head.next;
        else{
            while(currentNode != null && pos++ < index-1) currentNode = currentNode.next;
            if(currentNode == null) return;
            currentNode.next = currentNode.next.next;
        }
        listSize --;
    }

    public void remove(Object element){
        while (head != null && head.data.equals(element)) {
            head = head.next;
            listSize --;
        }

        if (head == null) return;

        SNode current = head;
        while (current.next != null) {
            if (current.next.data.equals(element)) {
                current.next = current.next.next;
                listSize --;
            } else {
                current = current.next;
            }
        }
    }

    public int size() {
        return listSize;
    }

    public boolean contains(Object o) {
        SNode currentNode = head;
        while(currentNode != null && !currentNode.data.equals(o)) currentNode = currentNode.next;
        if(currentNode == null) return false;
        return true;
    }

}
