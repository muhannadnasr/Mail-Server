package Utilities;

public class EmailComponents {
	String ID;
	String subject;
	String bodyText;
	String time;
	String sender;
	SinglyLinkedList receivers = new SinglyLinkedList();
	String priority;
	SinglyLinkedList attachments = new SinglyLinkedList();
}
