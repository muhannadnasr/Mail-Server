package Utilities;

import Interfaces.ISort;
import dataStructures.PriorityQueue;
import dataStructures.Stack;

public class Sort implements ISort {
    private static int PivotLoc(EmailComponents[] emails, int low, int high, String Key) {
        String pivot = emails[high].get(Key);
        int i = (low-1); // Index of smaller element
        for (int j=low ; j<high ; j++) {
            // If present element is smaller than or
            // equal to pivot
            String x = emails[j].get(Key);
            if (x.compareTo(pivot) < 0 || x.compareTo(pivot) == 0) {
                i++;

                // swapping
                EmailComponents t = emails[i];
                emails[i] = emails[j];
                emails[j] = t;
            }
        }

        // swapping emails[i+1] and pivot
        EmailComponents temp = emails[i+1];
        emails[i+1] = emails[high];
        emails[high] = temp;

        return i+1;
    }

    private static EmailComponents[] quickSortEmails(EmailComponents[] emails, int low, int high , String Key, int identifier) {
        Stack s = new Stack();
        s.push(0);
        s.push(-1);
        while (!s.isEmpty()) {
            while (low <= high) {
                int pivot = PivotLoc(emails,low,high,Key);
                s.push(pivot + 1);
                s.push(high);
                high = pivot-1;
            }
            high = (int) s.pop();
            low = (int) s.pop();

        }

        if (identifier==1) {
            EmailComponents temp = null;

            for (int i=0; i<emails.length/2;i++) {
                temp = emails[i];
                emails[i]= emails[emails.length-i- 1];
                emails[emails.length-i-1] = temp;
            }


        }

        return emails;
    }

    private int PivotLocString(String arr[],int low,int high) {

        String pivot = arr[high];
        int i = (low-1); // Index of smaller element
        for (int j=low ; j<high ; j++) {
            // If present element is smaller than or
            // equal to pivot
            String x = arr[j];
            if (x.compareTo(pivot) < 0 || x.compareTo(pivot) == 0) {
                i++;

                // swapping
                String t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }

        // swapping arr[i+1] and pivot
        String temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }

    public String[] quickSortString(String arr[], int low, int high,int identifier) {
        Stack s = new Stack();
        s.push(0);
        s.push(-1);
        while (!s.isEmpty()) {
            while (low <= high) {
                int pivot = PivotLocString(arr,low,high);
                s.push(pivot + 1);
                s.push(high);
                high = pivot-1;
            }
            high = (int) s.pop();
            low = (int) s.pop();

        }

        if (identifier==1) {
            String temp = null;
            for (int i=0; i<arr.length/2;i++) {
                temp = arr[i];
                arr[i]= arr[arr.length-i- 1];
                arr[arr.length-i-1] = temp;
            }
        }
        return arr;
    }

    public EmailComponents[] getSortedEmails(String loggedInEmail, String fileName, int key){
        EmailFunctions process = new EmailFunctions();
        int maxIndex = process.allEmailsNumber(loggedInEmail, fileName) -1;
        EmailComponents[] allEmails = process.displayEmails(loggedInEmail, fileName, 0, maxIndex);
        //sort
        EmailComponents[] allEmailsSorted = null;
        if(key == 0){ //0 for descending sorting
            allEmailsSorted = quickSortEmails(allEmails, 0, maxIndex, "time", 0);
        }
        else if(key == 1){ //1 for ascending sorting
            allEmailsSorted = quickSortEmails(allEmails, 0, maxIndex, "time", 1);
        }
        return allEmailsSorted;
    }
    //Sort Emails By Date
    public EmailComponents[] getSortedEmailsByTime(String loggedInEmail, String fileName, int key, int start, int end){
        EmailComponents[] allEmails = getSortedEmails(loggedInEmail, fileName, key);
        int maxIndex = allEmails.length - 1;
        if(end > maxIndex) end = maxIndex;
        EmailComponents[] outputEmails = new EmailComponents[end - start + 1];
        int a = 0;
        for(int i = start; i <= end; i++){
            outputEmails[a] = allEmails[i];
            a++;
        }
        return outputEmails;
    }
    //Sort Emails By Priority
    public EmailComponents[] getSortedEmailsByPriority(String loggedInEmail, String fileName, int key, int start, int end){
        EmailFunctions process = new EmailFunctions();
        int maxIndex = process.allEmailsNumber(loggedInEmail, fileName) -1;
        EmailComponents[] allEmails = process.displayEmails(loggedInEmail, fileName, 0, maxIndex);
        PriorityQueue priorities = new PriorityQueue();
        System.out.println(allEmails.length);
        for(int i = 0; i < allEmails.length; i++){
            EmailComponents email = allEmails[i];
            String priority = email.priority;
            int priorityKey = 0;
            if(priority.equals("a")) priorityKey = 1;
            else if(priority.equals("b")) priorityKey = 2;
            else if(priority.equals("c")) priorityKey = 3;
            else if(priority.equals("d")) priorityKey = 4;
            priorities.insert(email, priorityKey);
        }
        if(end > maxIndex) end = maxIndex;
        System.out.println(priorities.size());
        EmailComponents[] outputEmails = new EmailComponents[end - start +1];
        int a = 0;
        for(int i = 0; i <= end; i++){
            if(i >= start){
                System.out.println(i);
                System.out.println(priorities.size());
                if(key == 0) outputEmails[a] = (EmailComponents)priorities.removeMin(); //from highest to lowest
                else if(key == 1) outputEmails[a] = (EmailComponents)priorities.removeMax(); //from lowest to highest
                a++;
            }else{
                if(key == 0) priorities.removeMin();
                else if(key == 1) priorities.removeMax();
            }
        }
        return outputEmails;
    }

    public String[] AToZContacts(String loggedInEmail){
        Contact process = new Contact();
        String[] allContacts = process.getAllContacts(loggedInEmail);
        String[] sortedContacts = quickSortString(allContacts, 0, allContacts.length-1, 1);
        return sortedContacts;
    }

    public String[] ZToAContacts(String loggedInEmail){
        Contact process = new Contact();
        String[] allContacts = process.getAllContacts(loggedInEmail);
        String[] sortedContacts = quickSortString(allContacts, 0, allContacts.length-1, 0);
        return sortedContacts;
    }

}
