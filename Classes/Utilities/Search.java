package Utilities;

import Interfaces.ISearch;
import dataStructures.SinglyLinkedList;
import dataStructures.Stack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Search implements ISearch {
    public EmailComponents[] emailsContainingWord(String loggedInEmail, String folder, String words) {
        String[] wordsToSearchFor = words.split(" ");
        SinglyLinkedList emailsHolder = new SinglyLinkedList();;
        EmailFunctions process = new EmailFunctions();
        EmailComponents[] allEmails = process.displayEmails(loggedInEmail, folder, 0, Integer.MAX_VALUE);
        File pathToAllWords = new File("MailServer/Users/" + loggedInEmail + '/' + folder + "/All_Words.txt");
        BufferedReader br = null;
        for(int i=0; i<wordsToSearchFor.length; i++)
        {
            boolean readID = true;
            String IDholder = new String();
            String temp = new String();
            try {
                br = new BufferedReader(new FileReader(pathToAllWords));
                while((temp = br.readLine()) != null)
                {
                    if(readID) {
                        IDholder = temp;
                        readID = false;
                        continue;
                    }
                    if(temp.equals("###")) {
                        readID = true;
                        continue;
                    }
                    if(temp.equals(wordsToSearchFor[i])) {
                        emailsHolder.remove(IDholder);
                        emailsHolder.add(IDholder);
                    }
                }
            } catch (Exception e) {e.printStackTrace();}
        }
        if(emailsHolder.size() == 0) return null;
        EmailComponents[] emailsContainingWords = new EmailComponents[emailsHolder.size()];
        int size = emailsHolder.size();
        for(int i=0; i<size; i++)
        {
            emailsContainingWords[i] = new EmailComponents();
            for(int j=0; j<allEmails.length; j++)
            {
                if(allEmails[j].ID.equals(emailsHolder.get(i)))
                {
                    emailsContainingWords[i] = allEmails[j];
                    break;
                }
            }
        }
        return emailsContainingWords;
    }

    public boolean searchContacts(String[] contacts, String key) {
        Sort process = new Sort();
        String[] Sorted = process.quickSortString(contacts,0,contacts.length-1,0);
        Stack s = new Stack();
        int left = 0;
        int right = Sorted.length - 1;
        int mid = (left + right) / 2;
        s.push(mid);
        while (!s.isEmpty()) {
            mid = (int) s.pop();
            String x =  Sorted[mid];
            String y = key;

            if (x.compareTo(y) == 0) {
                return true;
            } else if (x.compareTo(y) > 0 && left<=right) {
                right = mid - 1;
                mid = (left + right) / 2;
                s.push(mid);
            } else if (x.compareTo(y) < 0 && left<=right) {
                left = mid + 1;
                mid = (left + right) / 2;
                s.push(mid);
            }

        }
        return false;
    }
}
