package Utilities;

import Interfaces.IContact;
import dataStructures.SinglyLinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Contact implements IContact {
    public void createAndAddEmailsContacts(String loggedInEmail , String userName, SinglyLinkedList emailList) {
        String fromUserNameToTxt = userName + ".txt";
        String pathToUsersFile = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator +
                "Contacts" + File.separator + fromUserNameToTxt;
        File pathToUserFolderCreatingFile = new File(pathToUsersFile);
        pathToUserFolderCreatingFile.getParentFile().mkdirs();
        try {
            pathToUserFolderCreatingFile.createNewFile();
        } catch (IOException e) {
            // we can leave this empty as there's no chance that an error will occur *hopefully*
            e.printStackTrace();
        }

        File fileToUserNameToAddContacts = new File("MailServer/Users/" + loggedInEmail + "/Contacts/" + fromUserNameToTxt);
        FileWriter writeInUserFile = null;
        try {
            writeInUserFile = new FileWriter(fileToUserNameToAddContacts, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int size = emailList.size();
        for(int i=0; i<size; i++)
        {
            try {
                writeInUserFile.write(String.valueOf(emailList.get(i)) + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writeInUserFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(doesContactExists(loggedInEmail, userName)) {
            return;
        }
        File storeUserName = new File("MailServer/Users/" + loggedInEmail + "/All_Contacts.txt");
        FileWriter addingUserNameToAllContacts = null;
        try {
            addingUserNameToAllContacts = new FileWriter(storeUserName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            addingUserNameToAllContacts.write(userName + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            addingUserNameToAllContacts.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public boolean doesContactExists(String loggedInEmail, String userName)
    {
        File pathToAllContacts = new File("MailServer/Users/" + loggedInEmail + "/All_Contacts.txt");
        BufferedReader contactsChecker = null;
        try {
            contactsChecker = new BufferedReader(new FileReader(pathToAllContacts));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        String comparing = new String();
        try {
            while((comparing = contactsChecker.readLine()) != null) {
                if(userName.equals(comparing)) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String[] getEmailAddressesFromUserName(String loggedInEmail, String userName)
    {
        String userNameToTxt = userName + ".txt";
        SinglyLinkedList temp = new SinglyLinkedList();
        File pathToAllContacts = new File("MailServer/Users/" + loggedInEmail + "/Contacts/" + userNameToTxt);
        BufferedReader contactsChecker = null;
        try {
            contactsChecker = new BufferedReader(new FileReader(pathToAllContacts));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        String emailAddress = new String();
        try {
            while((emailAddress = contactsChecker.readLine()) != null) {
                temp.add(String.valueOf(emailAddress));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        int size = temp.size();
        String[] emails = new String[size];
        for(int i=0; i<size; i++)
        {
            if(String.valueOf(temp.get(i)).length() == 0) continue;
            emails[i] = String.valueOf(temp.get(i));
        }
        return emails;
    }

    public void deleteContact(String loggedInEmail, String userName)
    {
        File fileBefore = new File("MailServer/Users/" + loggedInEmail + "/All_Contacts.txt");
        SinglyLinkedList temp = new SinglyLinkedList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileBefore));
        }catch(Exception e)
        {

        }
        String holder = new String();
        try {
            while((holder = br.readLine()) != null)
            {
                temp.add(String.valueOf(holder));
            }
        }catch(Exception e) {

        }
        try {
            br.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String userNameToTxt = userName + ".txt";
        temp.remove(userName);
        fileBefore.delete();
        int size = temp.size();
        String fileAfter = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator + "All_Contacts.txt";
        File pathToUserFolderCreatingFile = new File(fileAfter);
        pathToUserFolderCreatingFile.getParentFile().mkdirs();
        try {
            pathToUserFolderCreatingFile.createNewFile();
        } catch (IOException e) {
            // we can leave this empty as there's no chance that an error will occur *hopefully*
        }
        fileAfter = "MailServer/Users/" + loggedInEmail + "/All_Contacts.txt";
        File fileName = new File(fileAfter);
        FileWriter writeInUserFile = null;
        try {
            writeInUserFile = new FileWriter(fileName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0; i<size; i++)
        {
            try {
                writeInUserFile.write(String.valueOf(temp.get(i)) + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writeInUserFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File userFile = new File("MailServer/Users/" + loggedInEmail + "/Contacts/" + userNameToTxt);
        userFile.delete();
    }

    public void deleteEmail(String loggedInEmail, String userName, String email) {
        String userNameToTxt = userName + ".txt";
        File filePath = new File("MailServer/Users/" + loggedInEmail + "/Contacts/" + userNameToTxt);
        SinglyLinkedList temp = new SinglyLinkedList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
        }catch(Exception e)
        {

        }
        String holder = new String();
        try {
            while((holder = br.readLine()) != null)
            {
                temp.add(String.valueOf(holder));
            }
        }catch(Exception e) {

        }
        try {
            br.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        filePath.delete();
        temp.remove(String.valueOf(email));
        String afterChange = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator + "Contacts" +
                File.separator + userNameToTxt;
        File pathToUserFolderCreatingFile = new File(afterChange);
        pathToUserFolderCreatingFile.getParentFile().mkdirs();
        try {
            pathToUserFolderCreatingFile.createNewFile();
        } catch (IOException e) {
            // we can leave this empty as there's no chance that an error will occur *hopefully*
            e.printStackTrace();
        }
        int size = temp.size();
        File filePathAfter = new File("MailServer/Users/" + loggedInEmail + "/Contacts/"+userNameToTxt);
        FileWriter writeInUserFile = null;
        try {
            writeInUserFile = new FileWriter(filePathAfter, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0; i<size; i++)
        {
            try {
                writeInUserFile.write(String.valueOf(temp.get(i)) + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writeInUserFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editEmailAddress(String loggedInEmail, String userName, String oldEmail, String newEmail)
    {
        deleteEmail(loggedInEmail, userName, oldEmail);
        SinglyLinkedList temp = new SinglyLinkedList();
        temp.add(String.valueOf(newEmail));
        createAndAddEmailsContacts(loggedInEmail, userName, temp);
    }

    public void editUser(String loggedInEmail, String oldUserName, String newUserName)
    {
        String oldUserNameToTxt = oldUserName + ".txt";
        File file = new File("MailServer/Users/" + loggedInEmail + "/Contacts/" + oldUserNameToTxt);
        SinglyLinkedList temp = new SinglyLinkedList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        }catch(Exception e)
        {

        }
        String holder = new String();
        try {
            while((holder = br.readLine()) != null)
            {
                temp.add(String.valueOf(holder));
            }
        }catch(Exception e) {

        }
        try {
            br.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        deleteContact(loggedInEmail, oldUserName);
        createAndAddEmailsContacts(loggedInEmail, newUserName, temp);
    }

    public String[] getAllContacts(String loggedInEmail)
    {
        SinglyLinkedList contacts = new SinglyLinkedList();
        File file = new File("MailServer/Users/" + loggedInEmail + "/All_Contacts.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        }catch(Exception e)
        {

        }
        String holder = new String();
        try {
            while((holder = br.readLine()) != null)
            {
                contacts.add(String.valueOf(holder));
            }
        }catch(Exception e) {

        }
        try {
            br.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if(contacts.size() == 0) return null;
        int size = contacts.size();
        String[] allContacts = new String[size];
        for(int i=0; i<size; i++)
        {
            allContacts[i] = String.valueOf(contacts.get(i));
        }
        return allContacts;
    }

    public static void main(String[] args) {

    }

}