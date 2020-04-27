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
			writeInFileInfo.write(uniqueID + '\n' + priority + '\n' + dtf.format(now) + '\n' + subject + '\n' + "###" + '\n');
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
		File fromLocationAllWords = new File("MailServer/Users/"+loggedInEmail +'/' +from + "/All_Words.txt");
		File fromLocationAllAttachments = new File("MailServer/Users/" + loggedInEmail + '/' + from + "/All_Attachments.txt");
		File fromLocationAttachments = new File("MailServer/Users/" + loggedInEmail + '/' + from + '/' + ID + "/Attachments");
		trashPath.mkdir();
		File pathToAttachmentFolder = new File("MailServer/Users/" + loggedInEmail + "/Trash/" + ID + "/Attachments");
		pathToAttachmentFolder.mkdir();
		
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
		BufferedReader brAllWords = null;
		String holdSubject = null;
		SinglyLinkedList holdBody = new SinglyLinkedList();
		SinglyLinkedList holdInfo = new SinglyLinkedList();
		SinglyLinkedList holdAllWordsBig = new SinglyLinkedList();
		SinglyLinkedList holdAllWordsSmall = new SinglyLinkedList();
		
		boolean found = false;
		try {
			brAllWords = new BufferedReader(new FileReader(fromLocationAllWords));
			brSubject = new BufferedReader(new FileReader(fromLocationSubject));
			brBodyText = new BufferedReader(new FileReader(fromLocationBodyText));
			brInfo = new BufferedReader(new FileReader(fromLocationInfo));
			//work on all words
			String allWordsHolder = null;
			boolean falseForBig_TrueForSmall = false;
			while((allWordsHolder = brAllWords.readLine()) != null)
			{
				if(allWordsHolder.equals(ID)) falseForBig_TrueForSmall = true;
				if(allWordsHolder.equals("###") && falseForBig_TrueForSmall) {
					falseForBig_TrueForSmall = false;
					holdAllWordsSmall.add(allWordsHolder);
					continue;
				}
				if(falseForBig_TrueForSmall) holdAllWordsSmall.add(allWordsHolder);
				else holdAllWordsBig.add(allWordsHolder);
			}
			brAllWords.close();
			fromLocationAllWords.delete();
			//end
			
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
		//make a new all words and fill it with big linked list and append the small linked list to all words in trash
		String newAllWordsString = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator + from + File.separator
				+ "All_Words.txt";
		File newAllWords = new File(newAllWordsString);
		newAllWords.getParentFile().mkdirs();
		try {
			newAllWords.createNewFile();
		}catch(Exception e) {}
		File pathToNewAllWords = new File("MailServer/Users/" + loggedInEmail + '/' + from +"/All_Words.txt");
		FileWriter writeInNewAllWords = null;
		try {
			writeInNewAllWords = new FileWriter(pathToNewAllWords, true);
			int sizeOfBig = holdAllWordsBig.size();
			for(int i=0; i<sizeOfBig; i++) {
				writeInNewAllWords.write(String.valueOf(holdAllWordsBig.get(i)) + '\n');
			}
			writeInNewAllWords.close();
		}catch(Exception e) {}
		
		//write in all words in trash
		File pathOfAllWordsInTrash = new File("MailServer/Users/" + loggedInEmail + "/Trash/All_Words.txt");
		FileWriter writeInAllWordsTrash = null;
		try {
			writeInAllWordsTrash = new FileWriter(pathOfAllWordsInTrash, true);
			int sizeSmall = holdAllWordsSmall.size();
			for(int i=0; i<sizeSmall; i++)
			{
				writeInAllWordsTrash.write(String.valueOf(holdAllWordsSmall.get(i)) + '\n');
			}
			
			writeInAllWordsTrash.close();
		}catch(Exception e) {}
		
		//end
		
		//end
		
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
			boolean wroteFrom = false;
			for(int i=0; i<sizeInfo; i++)
			{
				allEmailInfoWriter.write(String.valueOf(holdInfo.get(i)) + '\n');
				if(!wroteFrom) {
					allEmailInfoWriter.write(from + '\n');
					wroteFrom = true;
				}
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
		}catch(Exception e) {}
		//end
		
		//erase all info, all attachments from all emails info for deletedEmails
		SinglyLinkedList eraser = new SinglyLinkedList();
		File pathToAllAttachments = new File("MailServer/Users/" + loggedInEmail + "/Trash/All_Attachments.txt");
		FileWriter writeInAllAttachments = null;
		try {
			Attachments toCopy = new Attachments();
			writeInAllAttachments = new FileWriter(pathToAllAttachments, true);
			String[] tempToCheckSize = fromLocationAttachments.list();
			boolean hasAttachments = false;
			if(tempToCheckSize.length > 0) {
				writeInAllAttachments.write(ID + '\n');
				hasAttachments = true;
			}
			for(String fileName : fromLocationAttachments.list()) {
				writeInAllAttachments.write(fileName + '\n');
				toCopy.copyFile("MailServer/Users/" + loggedInEmail + '/' + from + '/' + ID + "/Attachments/"+fileName,
						"MailServer/Users/" + loggedInEmail + "/Trash/" + ID + "/Attachments/" + fileName);
				File toBeDeleted = new File("MailServer/Users/" + loggedInEmail + '/' + from + '/' + ID + "/Attachments/"+fileName);
				toBeDeleted.delete();
			}
			if(hasAttachments)writeInAllAttachments.write("###" + '\n');
			writeInAllAttachments.close();
			fromLocationAttachments.delete();
		}catch(Exception e) {e.printStackTrace();}
		BufferedReader brAttachments = null;
		SinglyLinkedList holdAttachments = new SinglyLinkedList();
		try {
			brAttachments = new BufferedReader(new FileReader(fromLocationAllAttachments));
			boolean skipAttach = false;
			String checker;
			while((checker = brAttachments.readLine()) != null)
			{
				if(checker.equals(ID)) skipAttach = true;
				if(checker.equals("###") && skipAttach) {
					skipAttach = false;
					continue;
				}
				if(!skipAttach) holdAttachments.add(checker);
			}
			brAttachments.close();
			fromLocationAllAttachments.delete();
		}catch(Exception e) {}
		String newPathToAllAttachments = "MailServer" + File.separator + "Users" + File.separator + loggedInEmail + File.separator + from
				+ File.separator + "All_Attachments.txt";
		File newAllAttachmentsFile = new File(newPathToAllAttachments);
		newAllAttachmentsFile.getParentFile().mkdirs();
		FileWriter writeInNewAllAttachments = null;
		try {
			newAllAttachmentsFile.createNewFile();
			writeInNewAllAttachments = new FileWriter(newAllAttachmentsFile, true);
			int sizeBigAttachments = holdAttachments.size();
			for(int i=0; i<sizeBigAttachments; i++) writeInNewAllAttachments.write(String.valueOf(holdAttachments.get(i)) + '\n');
			writeInNewAllAttachments.close();
		}catch(Exception e) {}
		
		//end
		
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
			fromLocation.delete();
		}catch(Exception e) {}
		
		//end
		
 	}
	public EmailComponents[] displayEmails(String loggedInEmail, String folder, int start, int end) {
		
		boolean isTrash = false, isInbox = false, isDraft = false, isSent = false;

		//to check for kind of folder
		switch(folder) {
		case "Trash":
			isTrash = true;
			break;
		case "Inbox":
			isInbox = true;
			break;
		case "Sent":
			isSent = true;
			break;
		case "Draft":
			isDraft = true;
			break;
		}
		//end
		
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

		BufferedReader brArray=null;
		try {	
			for(int i=0; i<arraySize; i++)
			{
				emails[i] = new EmailComponents();
				brArray = new BufferedReader(new FileReader(pathForDoubly));
				emails[i].ID = String.valueOf(IDholder.get(start+i));
				String temp = new String();
				String folderTypeForTrash = new String();
				while((temp = brArray.readLine()) != null)
				{
					if(temp.equals(emails[i].ID))
					{
						if(isTrash) folderTypeForTrash = brArray.readLine();
						if((isTrash && folderTypeForTrash.equals("Inbox")) || isInbox) {
							isInbox = true;
							emails[i].receivers = null;
						}
						else if((isTrash && folderTypeForTrash.equals("Sent")) || isSent) {
							isSent = true;
							emails[i].sender = null;
						}
						else if((isTrash && folderTypeForTrash.equals("Draft")) || isDraft) {
							emails[i].sender = null;
							emails[i].receivers = null;
						}
						emails[i].priority = String.valueOf(brArray.readLine());
						emails[i].time = brArray.readLine();
						emails[i].subject = brArray.readLine();
						if(isInbox) emails[i].sender = brArray.readLine();
						while(isSent && !((temp = brArray.readLine()).equals("###")))emails[i].receivers.add(temp);
						break;
					}
				}
				
				brArray.close();
				String pathToAttachments = "MailServer/Users/" + loggedInEmail + '/' + folder + '/' + emails[i].ID + "/Attachments";
				File reachFileToEmptyIt = new File(pathToAttachments);
				boolean attachmentsExist = false;
				for(String attachHolder : reachFileToEmptyIt.list()) {
					attachmentsExist = true;
					emails[i].attachments.add(pathToAttachments + '/' + attachHolder);
				}
				if(!attachmentsExist) emails[i].attachments = null;
			}
		}catch(Exception e) {e.printStackTrace();}
		//end
	
		return emails;
	}
	public EmailComponents showEmail(String loggedInEmail, String folder, String ID, EmailComponents[] emails)
	{
		//shows us at what index the neeeded email exists
		int index=0;
		while(index < emails.length)
		{
			if(ID.equals(emails[index].ID)) break;
			index++;
		}
		//end
		
		//making an object and fill its attributes with the already saved ones
		EmailComponents email = emails[index];
		email.bodyText = "";
		File bodyPath = new File("MailServer/Users/" + loggedInEmail + '/' + folder + '/' + ID + "/bodyText.txt");
		BufferedReader brBody = null;
		try {
			brBody = new BufferedReader(new FileReader(bodyPath));
			String reader = new String();
			while((reader = brBody.readLine()) != null)
			{
				email.bodyText += reader + '\n';
			}
			brBody.close();
		}catch(Exception e) {e.printStackTrace();}
		//end
		
		return email;
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
				//to skip username and passwords to be able to get to the emails
				readUsers.readLine();
				readUsers.readLine();
				//end
				if(email.equals(comparator)) return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	public static void main(String[] args) {
		/*ComposeFunctions test = new ComposeFunctions();
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
		queueTest.enqueue("ali123456@gmail.com");*/
		//test.deleteEmail("nasr1234@gmail.com", "Inbox", "ae6da8d0-624e-4b10-802c-80fd3ee5e41d");
		//test.sendEmail("mohannad123456@gmail.com", queueTest, "hello my friend", "Hello friend,\nhow are you?", "c", testAttach);
		//EmailComponents[] testArray;
		//testArray = test.displayEmails("mohannad123456@gmail.com", "Sent", 0, 100);
		//for(int i=0; i<testArray.length; i++) System.out.println(testArray[i].receivers.get(0));
		/*EmailComponents email = test.showEmail("mohannad123456@gmail.com", "Sent", "36daffc7-937f-4b03-91a7-297f2f4f3501", testArray);
		System.out.println("Email ID: " + email.ID);
		System.out.println("Email subject: " + email.subject);
		System.out.println("Email body: \n" +email.bodyText);
		System.out.println("Email sent on: " + email.time);
		System.out.println("Email Sender: "+email.sender);
		System.out.println("Email receivers: ");
		for(int i=0; i<email.receivers.size(); i++)System.out.println(email.receivers.get(i));
		System.out.println("Email priority: "+email.priority);
		
		System.out.println("Email attachments: ");
		int size = email.attachments.size();
		for(int i=0; i<size; i++) System.out.println(email.attachments.get(i));*/
		//test.moveToDraft("mohannad123456@gmail.com", "Hello old friend", "This is a body text example", "a", testAttach);
		//test.deleteEmail("mohannad123456@gmail.com", "Draft", "703de836-613f-4113-9b6a-650f6156b24f");
	}
}
