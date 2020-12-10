package dataStructures;

public class DLList {
    class Node {

        public Node next = null;

        public Node prev = null;

        public Object value;

        public Node(Object element) {
            this.value =element ;
        }
    }

    public Node head = null;

    public int size = 0;

    public void add(int index, Object element) {
        Node node = new Node(element);
        if ((head == null && index != 0) || index < 0 || index > size) {
            throw null;
        }
        if (index == 0) {
            node.next = head;
            node.prev = null;
            head = node;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            node.value = element;
            if (current.next != null) {
                Node temp = current.next;
                current.next = node;
                node.next = temp;
                temp.prev = node;
                node.prev = current;
            } else {
                current.next = node;
                node.next = null;
                node.prev = current;
            }
        }
        size++;
    }


    public void add(Object element) {
        Node node = new Node(element);
        if (head == null) {
            node.value = element;
            head = node;
            node.next = null;
            node.prev = null;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            node.value = element;
            node.next = null;
            node.prev = current;
            current.next = node;
        }
        size++;
    }


    public Object get(int index) {
        if (head == null || index < 0 || index >=size) {
            throw null;
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }


    public void set(int index, Object element) {
        if (head == null || index < 0 || index >= size) {
            throw null;
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.value = element;
    }


    public void clear() {
        head = null;
        size = 0;

    }


    public boolean isEmpty() {
        return head == null;
    }


    public void remove(int index) {
        if (head == null || index < 0 || index >=size) {
            throw null;
        } else {
            int c;
            if (head != null) {
                if (index == 0) {
                    head = head.next;

                } else {
                    Node i = head;
                    Node j = i.next;
                    for (c = 0; c < index- 1 && i != null; c++) {
                        i = i.next;
                        j = j.next;
                    }
                    i.next = j.next;
                    j.next = null;
                }
            }
            size--;
        }
    }

    public int size() {
        return size;
    }


    public boolean contains(Object o) {
        Node Now = head;
        while (Now != null) {
            if (Now.value.equals(o))
                return true;
            Now = Now.next;
        }
        return false;
    }
}
