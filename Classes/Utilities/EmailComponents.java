package Utilities;

import Interfaces.IMail;
import dataStructures.SinglyLinkedList;

public class EmailComponents implements IMail {
    public String ID;
    public String subject;
    public String bodyText;
    public String time;
    public String sender;
    public SinglyLinkedList receivers = new SinglyLinkedList();
    public String priority;
    public SinglyLinkedList attachments = new SinglyLinkedList();

    String get(String component) {
        if (component.equals("time")) {
            return this.time;
        }

        if (component.equals("priority")) {
            return this.priority;
        }
        return null;
    }
}