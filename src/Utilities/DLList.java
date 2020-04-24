package Utilities;


public class DLList{
	public class Node{
		Object data;
		Node next;
		Node prev;
	}
	Node head = null;
	Node tail = null;
	int size=0;
	public void add(Object element)
	{
		Node n = new Node();
		n.data = element;
		size++;
		if(head == null)
		{
			head = tail = n;
			head.prev = null;
			tail.next = null;
		}else
		{
			tail.next = n;
			n.prev = tail;
			tail = n;
		}
	}
	public void add(int index, Object element)
	{
		Node n = new Node();
		n.data = element;
		Node node = head;
		if(index == 0)
		{
			n.next = head;
			n.prev = null;
			head.prev = n;
			head = n;
			size++;
			
		}else if(index == 1)
		{
			n.next = head.next;
			head.next.prev = n;
			head.next = n;
			n.prev = head;
			size++;
		}else if(index == size)
		{
			for(int i=0; i<index-1; i++)
			{
				node = node.next;
			}
			node.next = n;
			n.next = null;
			n.prev = node;
			tail = n;
			size++;
		}
		
		else if(index > size && index != size+1)
		{
			System.out.println("The node before it is null");

		}else
		{
			for(int i=0; i<index-1; i++)
			{
				node = node.next;
			}
			n.next = node.next;
			node.next.prev = n;
			node.next = n;
			n.prev = node;
			size++;
		}
	}
	public Object get(int index)
	{
		Node node = head;
		for(int i=0; i<index; i++)
		{
			node = node.next;
		}
		return node.data;
	}
	public void set(int index, Object element)
	{
		if(index >= size)
		{
			System.out.println("Node doesn't exist !");
			return;
		}
		Node n = new Node();
		n.data = element;
		Node node = head;
		for(int i=0; i<index; i++)
		{
			node = node.next;
		}
		node.data = n.data;
	}
	public void clear()
	{
		head = null;
		tail = null;
	}
	public boolean isEmpty()
	{
		if(head == null)
		{
			return true;
		}else
		{
			return false;
		}
	}
	public void remove(int index)
	{
		if(index >= size)
		{
			System.out.println("Element doesn't exist");
			return;
		}
		Node node = head;
		for(int i=0; i<index; i++)
		{
			node = node.next;
		}
		node.next.prev = node.prev;
		node.prev.next = node.next;
		size--;
	}
	public int size() {
		return size;
	}
	public boolean contains(Object element)
	{
		Node n = new Node();
		n.data = element;
		boolean flag = false;
		Node node = head;
		while(node != null)
		{
			if(node.data == n.data)
			{
				flag = true;
				break;
			}
			node = node.next;
		}
		return flag;
	}

	


}
