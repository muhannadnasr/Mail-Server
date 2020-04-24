package Utilities;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ComposeFunctions {
	// to get current date
	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	   LocalDateTime now = LocalDateTime.now(); 
	// end

	public boolean sendEmail(String loggedInEmail, QueueLinkedBased sendToEmails, String subject, String bodyText, String priority, SinglyLinkedList attachments) {
		QueueLinkedBased emailsToSendTo = new QueueLinkedBased();
		int size = sendToEmails.size();
		// to check if all emails exist first before sending anything.
		String holder = new String();
		for(int i=0; i<size; i++)
		{
			holder = String.valueOf(sendToEmails.dequeue());
			if(!emailExists(holder)) return false;
			emailsToSendTo.enqueue(holder);
		}
		//end
		
		//create folder for sent email with another folder for attachments and the subject and bodyText files and write the corresponding data.
		String uniqueID = UUID.randomUUID().toString();
		File insertEmailInSentFolder = new File("MailServer/Users/" + loggedInEmail + "/Sent/" + uniqueID);
		insertEmailInSentFolder.mkdir();
		File attachmentsFolder = new File("MailServer/Users/" + loggedInEmail + "/Sent/" + uniqueID + "/Attachments");
		attachmentsFolder.mkdir();
		int numberAttachments = 0;
		if(attachments != null) numberAttachments = attachments.size();
		Attachments obj = new Attachments();
		File toAllAttachments = new File("MailServer/Users/" + loggedInEmail + "/Sent/All_Attachments.txt" );
		FileWriter writeInSentAllAttachments = null;
		if(numberAttachments > 0) {
			try {
				writeInSentAllAttachments = new FileWriter(toAllAttachments, true);
				writeInSentAllAttachments.write(uniqueID + '\n');
				for(int i=0; i<numberAttachments; i++)
				{
					AttachmentComponents item = (AttachmentComponents) attachments.get(i);
					writeInSentAllAttachments.write(item.fileName + '\n' + item.filePath + '\n');
					try {
						String idk = "MailServer/Users/" + loggedInEmail + "/Sent/" + uniqueID + "/Attachments/" + item.fileName;
						obj.copyFile(item.filePath, idk);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				writeInSentAllAttachments.write("###" + '\n');
				writeInSentAllAttachments.close();
			}catch(Exception e) {}
		}
		
		String subjectFile = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator + "Sent"
				+ File.separator + uniqueID + File.separator + "subject.txt";
		String bodyTextFile = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator + "Sent"
				+ File.separator + uniqueID + File.separator + "bodyText.txt";
		
		File subjectFileCreate = new File(subjectFile);
		File bodyTextCreate = new File(bodyTextFile);
		
		subjectFileCreate.getParentFile().mkdirs();
		bodyTextCreate.getParentFile().mkdirs();
		
		try {
			subjectFileCreate.createNewFile();
			bodyTextCreate.createNewFile();
		}catch(Exception e)
		{
			
		}
		
		File toSubjectWrite = new File("MailServer/Users/" + loggedInEmail + "/Sent/" + uniqueID + "/subject.txt");
		FileWriter writeInFile = null;
		File toBodyTextWrite = new File("MailServer/Users/" + loggedInEmail + "/Sent/" + uniqueID + "/bodyText.txt");
		FileWriter writeInFile2 = null;
		File toAllEmailInfo = new File("MailServer/Users/" + loggedInEmail + "/Sent/All_Emails_Info.txt");
		FileWriter writeInFileInfo = null;
		try {
			writeInFile = new FileWriter(toSubjectWrite, true);
			writeInFile2 = new FileWriter(toBodyTextWrite, true);
			writeInFileInfo = new FileWriter(toAllEmailInfo, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			writeInFile2.write(bodyText);
			writeInFile.write(subject);
			writeInFileInfo.write(uniqueID + '\n' + priority + '\n' + dtf.format(now) + '\n' + subject + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			writeInFile2.close();
			writeInFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//end
		
		//work on all words text file for sender
		File pathToAllWords = new File("MailServer/Users/" + loggedInEmail + "/Sent/All_Words.txt");
		FileWriter writeInAllWordsSender = null;
		try {
			writeInAllWordsSender = new FileWriter(pathToAllWords, true);
			writeInAllWordsSender.write(uniqueID + '\n');
			String[] allWordsHolder;
			allWordsHolder = bodyText.split(" ");
			for(int z=0; z<allWordsHolder.length; z++) writeInAllWordsSender.write(allWordsHolder[z] + '\n');
			writeInAllWordsSender.write("###" + '\n');
			writeInAllWordsSender.close();
		}catch(Exception e) {}
		
		//end
		
		//create received emails and their files and folders as the previous method
		for(int i=0; i<size; i++)
		{
			String receiver = String.valueOf(emailsToSendTo.dequeue());
			String pathToAttachGeneral = "MailServer/Users/" + receiver + "/Inbox/All_Attachments.txt";
			File inAttach = new File(pathToAttachGeneral);
			FileWriter writeInAttachGeneral = null;
			try {
				if(numberAttachments>0) {
					writeInAttachGeneral = new FileWriter(inAttach, true);
					writeInAttachGeneral.write(uniqueID + '\n');
					writeInFileInfo.write(receiver + '\n');
				}
			}catch(Exception e)
			{
				
			}
			File insertEmailInInboxFolder = new File("MailServer/Users/" + receiver + "/Inbox/" + uniqueID);
			insertEmailInInboxFolder.mkdir();
			File attachmentFolderReceived = new File("MailServer/Users/" + receiver + "/Inbox/" + uniqueID + "/Attachments");
			attachmentFolderReceived.mkdir();
			
			subjectFile = "MailServer" + File.separator + "Users" + File.separator + receiver + File.separator + "Inbox"
					+ File.separator + uniqueID + File.separator + "subject.txt";
			bodyTextFile = "MailServer" + File.separator + "Users" + File.separator + receiver + File.separator + "Inbox"
					+ File.separator + uniqueID + File.separator + "bodyText.txt";

			subjectFileCreate = new File(subjectFile);
			bodyTextCreate = new File(bodyTextFile);
			
			subjectFileCreate.getParentFile().mkdirs();
			bodyTextCreate.getParentFile().mkdirs();
			
			try {
				subjectFileCreate.createNewFile();
				bodyTextCreate.createNewFile();
			}catch(Exception e)
			{
				
			}
			toSubjectWrite = new File("MailServer/Users/" + receiver + "/Inbox/" + uniqueID + "/subject.txt");
			writeInFile = null;
			toBodyTextWrite = new File("MailServer/Users/" + receiver + "/Inbox/" + uniqueID + "/bodyText.txt");
			writeInFile2 = null;
			File toAllEmailsInfoReceived = new File("MailServer/Users/" + receiver + "/Inbox/All_Emails_Info.txt");
			FileWriter toAllEmailsReceived = null;
			File pathToAllWordsReceiver = new File("MailServer/Users/" + receiver + "/Inbox/All_Words.txt");
			FileWriter writeInAllWordsReceiver = null;
			try {
				writeInAllWordsReceiver = new FileWriter(pathToAllWordsReceiver, true);
				String[] bodyTextWordsHolder;
				bodyTextWordsHolder = bodyText.split(" ");
				writeInAllWordsReceiver.write(uniqueID+'\n');
				for(int z=0; z<bodyTextWordsHolder.length; z++) writeInAllWordsReceiver.write(bodyTextWordsHolder[z]+'\n');
				writeInAllWordsReceiver.write("###"+'\n');
				writeInAllWordsReceiver.close();
				writeInFile = new FileWriter(toSubjectWrite, true);
				writeInFile2 = new FileWriter(toBodyTextWrite, true);
				toAllEmailsReceived = new FileWriter(toAllEmailsInfoReceived, true);
				Attachments objx = new Attachments();
				for(int j=0; j<numberAttachments; j++)
				{
					AttachmentComponents item = (AttachmentComponents) attachments.get(j);
					writeInAttachGeneral.write(item.fileName + '\n');
					try {
						String idk = "MailServer/Users/" + receiver + "/Inbox/" + uniqueID + "/Attachments/" + item.fileName;
						objx.copyFile(item.filePath, idk);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(numberAttachments > 0) {
					writeInAttachGeneral.write("###" + '\n');
					writeInAttachGeneral.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				toAllEmailsReceived.write(uniqueID + '\n' + priority +'\n'+ dtf.format(now) + '\n' + subject + '\n' + loggedInEmail + '\n' + "###" + '\n');
				writeInFile2.write(bodyText);
				writeInFile.write(subject);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				toAllEmailsReceived.close();
				writeInFile2.close();
				writeInFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		//end
		
		try {
			writeInFileInfo.write("###" + '\n');
			writeInFileInfo.close();
		}catch(Exception e){}

		return true;
	}
	
	public void moveToDraft(String loggedInEmail, String subject, String bodyText, String priority, SinglyLinkedList attachments)
	{
		int numberAttachments = 0;
		if(attachments != null) numberAttachments = attachments.size();
		String uniqueID = UUID.randomUUID().toString();
		File emailToDraft = new File("MailServer/Users/" + loggedInEmail + "/Draft/" + uniqueID);
		emailToDraft.mkdir();
		String subjectFile = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator + "Draft"
				+ File.separator + uniqueID + File.separator + "subject.txt";
		String bodyTextFile = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator + "Draft"
				+ File.separator + uniqueID + File.separator + "bodyText.txt";
		File subjectFileCreate = new File(subjectFile);
		File bodyTextCreate = new File(bodyTextFile);
		File pathToAttachmentsFolder = new File("MailServer/Users/" + loggedInEmail + "/Draft/" + uniqueID + "/Attachments");
		pathToAttachmentsFolder.mkdir();
		
		subjectFileCreate.getParentFile().mkdirs();
		bodyTextCreate.getParentFile().mkdirs();
		
		try {
			subjectFileCreate.createNewFile();
			bodyTextCreate.createNewFile();
		}catch(Exception e)
		{
			
		}
		File pathToAllWords = new File("MailServer/Users/" + loggedInEmail + "/Draft/All_Words.txt");
		FileWriter writeInAllWords = null;
		File toSubjectWrite = new File("MailServer/Users/" + loggedInEmail + "/Draft/" + uniqueID + "/subject.txt");
		FileWriter writeInFile = null;
		File toBodyTextWrite = new File("MailServer/Users/" + loggedInEmail + "/Draft/" + uniqueID + "/bodyText.txt");
		FileWriter writeInFile2 = null;
		File toAllEmailInfo = new File("MailServer/Users/" + loggedInEmail + "/Draft/All_Emails_Info.txt");
		FileWriter writeInFileInfo = null;
		try {
			writeInAllWords = new FileWriter(pathToAllWords, true);
			String[] wordsHolder = bodyText.split(" ");
			writeInAllWords.write(uniqueID + '\n');
			for(int z=0; z<wordsHolder.length; z++) writeInAllWords.write(wordsHolder[z] + '\n');
			writeInAllWords.write("###" + '\n');
			writeInAllWords.close();
			writeInFile = new FileWriter(toSubjectWrite, true);
			writeInFile2 = new FileWriter(toBodyTextWrite, true);
			writeInFileInfo = new FileWriter(toAllEmailInfo, true);
			if(numberAttachments > 0)
			{
				File pathToAllAttachments = new File("MailServer/Users/" + loggedInEmail + "/Draft/All_Attachments.txt");
				FileWriter writeInAllAttachments = null;
				try {
					writeInAllAttachments = new FileWriter(pathToAllAttachments, true);
					writeInAllAttachments.write(uniqueID + '\n');
					Attachments objx = new Attachments();
					for(int j=0; j<numberAttachments; j++)
					{
						AttachmentComponents item = (AttachmentComponents) attachments.get(j);
						writeInAllAttachments.write(item.fileName + '\n');
						writeInAllAttachments.write(item.filePath + '\n');
						try {
							String idk = "MailServer/Users/" + loggedInEmail + "/Draft/" + uniqueID + "/Attachments/" + item.fileName;
							objx.copyFile(item.filePath, idk);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					writeInAllAttachments.write("###" + '\n');
					writeInAllAttachments.close();
					
				}catch(Exception e) {}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			writeInFile2.write(bodyText);
			writeInFile.write(subject);
			writeInFileInfo.write(uniqueID + '\n' + dtf.format(now) + '\n' + subject + '\n' + "###" + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			writeInFileInfo.close();
			writeInFile2.close();
			writeInFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEmail(String loggedInEmail, String from, String ID)
	{
		//First make folder and files needed for deleted email
		File trashPath = new File("MailServer/Users/" + loggedInEmail + "/Trash/" + ID);
		File fromLocation = new File("MailServer/Users/" + loggedInEmail + '/'  +from +'/'+ID);
		File fromLocationSubject = new File("MailServer/Users/" + loggedInEmail + '/'  +from +'/'+ID + "/subject.txt");
		File fromLocationBodyText = new File("MailServer/Users/" + loggedInEmail + '/'  +from +'/'+ID + "/bodyText.txt");
		File fromLocationInfo = new File("MailServer/Users/" + loggedInEmail + '/' + from + "/All_Emails_Info.txt");
		trashPath.mkdir();
		
		String createSubject = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator + "Trash" + File.separator
				+ ID + File.separator + "subject.txt";
		String createBodyText = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator + "Trash" + File.separator
				+ ID + File.separator + "bodyText.txt";
		File creatorSubject = new File(createSubject);
		File creatorBodyText = new File(createBodyText);
		
		creatorSubject.getParentFile().mkdirs();
		creatorBodyText.getParentFile().mkdirs();
		
		try {
			creatorSubject.createNewFile();
			creatorBodyText.createNewFile();
		}catch(Exception e) {};
		//end
		
		//fill the files with the data previously stored and delete unwanted files
		BufferedReader brSubject = null;
		BufferedReader brBodyText = null;
		BufferedReader brInfo = null;
		String holdSubject = null;
		SinglyLinkedList holdBody = new SinglyLinkedList();
		SinglyLinkedList holdInfo = new SinglyLinkedList();
		boolean found = false;
		try {
			brSubject = new BufferedReader(new FileReader(fromLocationSubject));
			brBodyText = new BufferedReader(new FileReader(fromLocationBodyText));
			brInfo = new BufferedReader(new FileReader(fromLocationInfo));
			holdSubject = brSubject.readLine();
			String infoHolder = new String();
			while((infoHolder = brInfo.readLine()) != null) {
				if(infoHolder.equals(ID) && !found) {
					found = true;
				}
				if(infoHolder.equals("###") && found) {
					holdInfo.add(infoHolder);
					break;
				}
				if(found) holdInfo.add(infoHolder);
			}
			String holdBodyS = new String();
			while((holdBodyS = brBodyText.readLine()) != null) {
				holdBody.add(String.valueOf(holdBodyS));
			}
			brInfo.close();
			brSubject.close();
			brBodyText.close();
		}catch(Exception e) {}
		File toAllEmailInfo = new File("MailServer/Users/" + loggedInEmail + "/Trash/All_Emails_Info.txt");
		FileWriter allEmailInfoWriter = null;
		File toTrashSubject = new File("MailServer/Users/" + loggedInEmail + "/Trash/"+ID+"/subject.txt");
		FileWriter subjectWriter = null;
		File toTrashBody = new File("MailServer/Users/" + loggedInEmail + "/Trash/" + ID+ "/bodyText.txt");
		FileWriter textBodyWriter = null;
		int size = holdBody.size();
		int sizeInfo = holdInfo.size();
		try {
			subjectWriter = new FileWriter(toTrashSubject, true);
			textBodyWriter = new FileWriter(toTrashBody, true);
			allEmailInfoWriter = new FileWriter(toAllEmailInfo, true);
			subjectWriter.write(holdSubject + '\n');
			for(int i=0; i<sizeInfo; i++)
			{
				allEmailInfoWriter.write(String.valueOf(holdInfo.get(i)) + '\n');
			}
			for(int i=0; i<size; i++)
			{
				String temp = String.valueOf(holdBody.get(i));
				textBodyWriter.write(temp + '\n');
			}
			allEmailInfoWriter.close();
			textBodyWriter.close();
			subjectWriter.close();
			fromLocationSubject.delete();
			fromLocationBodyText.delete();
			fromLocation.delete();
		}catch(Exception e) {}
		//end
		
		//erase info from all emails info for deletedEmails
		SinglyLinkedList eraser = new SinglyLinkedList();
		boolean skip = false;
		BufferedReader brErase = null;
		try {
			brErase = new BufferedReader(new FileReader(fromLocationInfo));
			String checker = new String();
			while((checker = brErase.readLine()) != null)
			{
				if(checker.equals(ID) && !skip) skip = true;
				if(!skip) eraser.add(checker);
				if(checker.equals("###") && skip) skip = false;
			}
			brErase.close();
			fromLocationInfo.delete();
		}catch(Exception e) {}
		String newInfoPath = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator + from + File.separator
				+ "All_Emails_Info.txt";
		File newInfoFile = new File(newInfoPath);
		newInfoFile.getParentFile().mkdirs();
		FileWriter writeFromEraseList = null;
		try {
			writeFromEraseList = new FileWriter(newInfoFile, true);
			size = eraser.size();
			newInfoFile.createNewFile();
			for(int i=0; i<size; i++)
			{
				writeFromEraseList.write(String.valueOf(eraser.get(i)) + '\n');
			}
			writeFromEraseList.close();
		}catch(Exception e) {}
		
		//end
		
 	}
	public EmailComponents[] displayEmails(String loggedInEmail, String folder, int start, int end) {
		boolean draftOrSent = false;
		if(folder.equals("Draft") || folder.equals("Sent")) draftOrSent = true;
		// filling the doubly linked list with all the IDs in the folder
		DLList IDholder = new DLList();
		File pathForDoubly = new File("MailServer/Users/" + loggedInEmail + '/' + folder + "/All_Emails_Info.txt");
		BufferedReader brDoubly = null;
		try {
			brDoubly = new BufferedReader(new FileReader(pathForDoubly));
			String IDgrabber = new String();
			boolean hitHashes = true;
			while((IDgrabber = brDoubly.readLine())!=null)
			{
				if(hitHashes) {
					IDholder.add(IDgrabber);
					hitHashes = false;
				}
				if(IDgrabber.equals("###")) hitHashes = true;
			}
			brDoubly.close();
		}catch(Exception e) {}
		//end
		
		//filling the array
		int arraySize;
		if(end > IDholder.size()) arraySize = IDholder.size() - start;
		else arraySize = end - start + 1;
		EmailComponents emails[] = new EmailComponents[arraySize];
		for(int i=0; i<arraySize; i++) {
			emails[i] = new EmailComponents();
			if(draftOrSent) emails[i].sender = null;
		}
		BufferedReader brArray=null;
		try {	
			for(int i=0; i<arraySize; i++)
			{
				brArray = new BufferedReader(new FileReader(pathForDoubly));
				emails[i].ID = String.valueOf(IDholder.get(start+i));
				String temp = new String();
				while((temp = brArray.readLine()) != null)
				{
					if(temp.equals(emails[i].ID))
					{
						emails[i].priority = String.valueOf(brArray.readLine());
						emails[i].time = brArray.readLine();
						emails[i].subject = brArray.readLine();
						if(!draftOrSent)emails[i].sender = brArray.readLine();
						break;
					}
				}
				brArray.close();
				/*File pathToBodyText = new File("MailServer/Users/" + loggedInEmail + '/' + folder + '/' + emails[i].ID + "/bodyText.txt");
				brArray = new BufferedReader(new FileReader(pathToBodyText));
				emails[i].bodyText = "";
				String placeHolder = new String();
				while((placeHolder = brArray.readLine()) != null)
				{
					emails[i].bodyText += placeHolder + '\n';
				}
				brArray.close();*/
			}
		}catch(Exception e) {}
		//end
		
		return emails;
	}
	public boolean emailExists(String email)
	{
		File users = new File("MailServer/All_Users.txt");
		BufferedReader readUsers = null;
		try {
			readUsers = new BufferedReader(new FileReader(users));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		String comparator = new String();
		try {
			while((comparator = readUsers.readLine()) != null)
			{
				readUsers.readLine();
				readUsers.readLine();
				if(email.equals(comparator)) return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	public static void main(String[] args) {
		ComposeFunctions test = new ComposeFunctions();
		QueueLinkedBased queueTest = new QueueLinkedBased();
		SinglyLinkedList testAttach = new SinglyLinkedList();
		AttachmentComponents obj1 = new AttachmentComponents();
		AttachmentComponents obj2 = new AttachmentComponents();
		AttachmentComponents obj3 = new AttachmentComponents();

		obj1.fileName = "first pic.jpg";
		obj1.filePath = "E:/JavaProject/first pic.jpg";
		testAttach.add(obj1);
		obj2.fileName = "second pic.jpg";
		obj2.filePath = "E:/JavaProject/second pic.jpg";
		testAttach.add(obj2);
		obj3.fileName = "Tables.pdf";
		obj3.filePath = "E:/JavaProject/Tables.pdf";
		testAttach.add(obj3);
		queueTest.enqueue("nasr1234@gmail.com");
		queueTest.enqueue("ali123456@gmail.com");
		//test.deleteEmail("nasr1234@gmail.com", "Inbox", "ae6da8d0-624e-4b10-802c-80fd3ee5e41d");
		//test.sendEmail("mohannad123456@gmail.com", queueTest, "hello my friend", "Hello friend,\n how are you?", String.valueOf('a'), null);
		//EmailComponents[] testArray = new EmailComponents[10];
		//testArray = test.displayEmails("nasr1234@gmail.com", "Inbox", 0, 1);
		//for(int i=0; i<testArray.length; i++) System.out.println(testArray[i].ID);
		/*String split = "hello world my name is";
		String delimitter = " ";
		String[] temp;
		temp = split.split(delimitter);*/
		test.moveToDraft("mohannad123456@gmail.com", "Hello old friend", "This is a body text example", "a", null);
	}

}
