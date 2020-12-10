package Utilities;

import Interfaces.IFilter;
import dataStructures.SinglyLinkedList;

public class Filter implements IFilter {
    private SinglyLinkedList filterEmailsByPriority(String loggedInEmail, String fileName, String priority, int key){
        Sort process = new Sort();
        //key is 1 for the newest and 0 for the oldest
        EmailComponents[] sortedEmails = process.getSortedEmails(loggedInEmail, fileName, key);
        SinglyLinkedList filteredEmails = new SinglyLinkedList();
        for(int i = 0; i < sortedEmails.length; i++){
            EmailComponents email = sortedEmails[i];
            if((email.priority).equals(priority)){
                filteredEmails.add(email);
            }
        }
        return filteredEmails;
    }

    public EmailComponents[] getFilteredEmails(String loggedInEmail, String fileName, String priority, int key ,int start , int end){
        SinglyLinkedList allFilteredEmail = filterEmailsByPriority(loggedInEmail, fileName, priority, key);
        if(end > allFilteredEmail.size()) end = allFilteredEmail.size() - 1;
        EmailComponents[] outputEmails = new EmailComponents[end - start + 1];
        int a = 0;
        for(int i = 0; i <= end; i++){
            if(i >= start){
                outputEmails[a] = (EmailComponents) allFilteredEmail.get(i);
                a++;
            }
        }
        return outputEmails;
    }
}
