package Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//check for valid username----> true
//check for valid email address----> true
//check for valid password---->true
//check if the email is used before---->true
//check if the user name is used before
public class SignUpProcess {
	public void signUp(String email, String password, String userName) {
		//example for path parameter : E:\\JavaProject\\Users\\<userName>
		String path = "MailServer/Users/" + email;
		createNewUser(email, path);
		File file = new File("MailServer/All_Users.txt");
		FileWriter addingToSystemOfUsers = null;
		try {
			addingToSystemOfUsers = new FileWriter(file, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			addingToSystemOfUsers.write(email+'\n');
			addingToSystemOfUsers.write(password+'\n');
			addingToSystemOfUsers.write(userName+'\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			addingToSystemOfUsers.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isValidUsername(String userName) {
		//User names cannot contain an ampersand (&)
		//, equals sign (=), underscore (_), apostrophe ('), dash (-), plus sign (+), comma (,), brackets (<,>),
		//or more than one period (.) in a row.
		for(int i = 0; i < userName.length(); i++) {
			if(!Character.isAlphabetic(userName.charAt(i)) && !Character.isDigit(userName.charAt(i)) && userName.charAt(i) != ' ') return false;
		}
		return true;
	}

	public boolean isValidEmail(String email) {
		//checks if it doesn't end with .com
		//if it doesn't contain @
		//if it contains spaces
		//abs@gmail.com
		int characterBeforeAt = 0;
		int characterAfterAt = 0;
		boolean containsAt = false;
		boolean dotsAfterAt = false;
		if(email.length() < 10) return false;
		if(!email.substring(email.length()-4).equals(".com")) return false;
		for(int i=0; i<email.length(); i++)
		{
			if(!Character.isAlphabetic(email.charAt(i)) && !Character.isDigit(email.charAt(i))
					&& email.charAt(i) != '@' && email.charAt(i) != '.') return false;
			else if(Character.isAlphabetic(email.charAt(i)) || Character.isDigit(email.charAt(i)) && email.charAt(i) != '@'
					&& email.charAt(i) != '.') characterBeforeAt++;
			if(email.charAt(i) == '@' && containsAt) return false;
			if(email.charAt(i) == '@') containsAt = true;
			if(email.charAt(i) == '@' && characterBeforeAt < 8) return false;
			if(email.charAt(i) == '@' && !Character.isAlphabetic(email.charAt(i+1))) return false;
			if(email.charAt(i) == '.' && email.charAt(i+1) == '.') return false;
			if(email.charAt(i) == '.' && dotsAfterAt) return false;
			if(containsAt && email.charAt(i) == '.') dotsAfterAt = true;
			if(containsAt && Character.isAlphabetic(email.charAt(i))) characterAfterAt++;
			if(containsAt && Character.isDigit(email.charAt(i))) return false;
		}
		if(characterAfterAt < 7)return false;
		return true;
	}
	public boolean isValidPassword(String password){
		boolean containsCharacters = false;
		boolean containsNumbers = false;
		if(password.length() < 8) return false;
		for(int i=0; i<password.length(); i++)
		{
			if(password.charAt(i) == ' ') return false;
			if(Character.isAlphabetic(password.charAt(i))) containsCharacters = true;
			if(Character.isDigit(password.charAt(i))) containsNumbers = true;
		}
		return containsCharacters && containsNumbers;
	}

	public void createNewUser(String email, String path) {
		//example for path parameter : E:\\JavaProject\\Users\\<userName>
		File userCreated = new File(path);
		boolean created =  userCreated.mkdir();
		/// IF DOESN'T EXIST THEN CREATE THE DATA INSIDE USER!
		File foldersInsideUserFolderInbox = new File(path + "/Inbox");
		created = foldersInsideUserFolderInbox.mkdir();
		File foldersInsideUserFolderDraft = new File(path + "/Draft");
		created = foldersInsideUserFolderDraft.mkdir();
		File foldersInsideUserFolderSent = new File(path + "/Sent");
		created = foldersInsideUserFolderSent.mkdir();
		File foldersInsideUserFolderTrash = new File(path + "/Trash");
		created = foldersInsideUserFolderTrash.mkdir();
		File foldersInsideUserFolderContacts = new File(path + "/Contacts");
		created = foldersInsideUserFolderContacts.mkdir();
		///Then creating the file that contains contacts
		// IT MUST FOLLOW THIS FORM!!
		String pathToUserFolder = "MailServer" + File.separator + "Users" + File.separator + email + File.separator +"All_Contacts.txt";
		File pathToUserFolderCreatingFile = new File(pathToUserFolder);
		pathToUserFolderCreatingFile.getParentFile().mkdirs();
		try {
			pathToUserFolderCreatingFile.createNewFile();
		} catch (IOException e) {
			// we can leave this empty as there's no chance that an error will occur *hopefully*
			e.printStackTrace();
		}
		createFilesInsideFolders(email, "Inbox");
		createFilesInsideFolders(email, "Sent");
		createFilesInsideFolders(email, "Draft");
		createFilesInsideFolders(email, "Trash");
	}

	public boolean userAlreadyExists(boolean doesNotExist) {
		return doesNotExist;
	}

	public boolean doesUserExists(String username){
		File fileName = new File("MailServer/All_Users.txt");
		BufferedReader userChecker = null;
		try {
			userChecker = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String comparing;
		try {
			while((userChecker.readLine()) != null) {
				userChecker.readLine();
				comparing = userChecker.readLine();
				if(comparing != null && comparing.equals(username)) return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean doesEmailExists(String email) {
		File fileName = new File("MailServer/All_Users.txt");
		BufferedReader emailChecker = null;
		try {
			emailChecker = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String comparing;
		try {
			while((comparing = emailChecker.readLine()) != null) {
				if(comparing.equals(email)) return true;
				emailChecker.readLine();
				emailChecker.readLine();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean isConfirmed(String password, String reEnteredPassword){
		boolean confirmed = password.equals(reEnteredPassword);
		return confirmed;
	}

	public void createFilesInsideFolders(String email, String folderName) {
		String pathToUserFolderWords = "MailServer" + File.separator + "Users" + File.separator + email +
				File.separator + folderName + File.separator + "All_Words.txt";
		String pathToUserFolderEmails = "MailServer" + File.separator + "Users" + File.separator + email +
				File.separator + folderName + File.separator + "All_Emails_Info.txt";
		String pathToUserFolderAttachments = "MailServer" + File.separator + "Users" + File.separator + email +
				File.separator + folderName + File.separator + "All_Attachments.txt";
		File pathToUserFolderCreatingFileWords = new File(pathToUserFolderWords);
		File pathToUserFolderCreatingFileEmails = new File(pathToUserFolderEmails);
		File pathToUserFolderCreatingFileAttachments = new File(pathToUserFolderAttachments);
		pathToUserFolderCreatingFileWords.getParentFile().mkdirs();
		pathToUserFolderCreatingFileEmails.getParentFile().mkdirs();
		pathToUserFolderCreatingFileAttachments.getParentFile().mkdirs();
		try {
			pathToUserFolderCreatingFileWords.createNewFile();
			pathToUserFolderCreatingFileEmails.createNewFile();
			pathToUserFolderCreatingFileAttachments.createNewFile();
		} catch (IOException e) {
			// we can leave this empty as there's no chance that an error will occur *hopefully*
			e.printStackTrace();
		}


	}
	public static void main(String[] args) {
		SignUpProcess test = new SignUpProcess();
		test.signUp("mohannad123456@gmail.com", "123Aa113413", "mohannad");
	}

}