package MailServerGUI;

import Utilities.*;
import dataStructures.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class GUIController {
    //current Email
    private String loggedInEmail = "";
    //Adding contact popUp
    private int TextFieldYLayout = 10;
    private int extraPaneH = 70;
    private int buttonYLayout = 15;
    private SinglyLinkedList TextFieldIds = new SinglyLinkedList();
    private SinglyLinkedList buttonsIds = new SinglyLinkedList();
    private int idCount = 1;
    private int count = 0;
    //contact page
    private SinglyLinkedList checkBoxes = new SinglyLinkedList();
    private SinglyLinkedList selectedCheckBoxes = new SinglyLinkedList();
    private SinglyLinkedList selectedContacts = new SinglyLinkedList();
    private SinglyLinkedList AllContactsCards = new SinglyLinkedList();
    private SinglyLinkedList AllContactsButtons = new SinglyLinkedList();
    //Sent page
    private int sentLowerIndex = 0;
    private int sentUpperIndex = 9;
    //Draft page
    private int draftLowerIndex = 0;
    private int draftUpperIndex = 9;
    //Inbox page
    private int inboxLowerIndex = 0;
    private int inboxUpperIndex = 9;
    //Trash page
    private int trashLowerIndex = 0;
    private int trashUpperIndex = 9;
    //showContactsPage
    private SinglyLinkedList AllTextFields = new SinglyLinkedList();
    private SinglyLinkedList AllTextFieldsCheckBoxes = new SinglyLinkedList();
    private SinglyLinkedList AllEmailCards = new SinglyLinkedList();
    private SinglyLinkedList selectedContactEmails = new SinglyLinkedList();
    //setup
    private String oldContactName;
    private Button currentContact;
    private SinglyLinkedList oldData = new SinglyLinkedList();
    private int editOrSave = 0; //0 for edit and 1 for save
    private String activePage = "Inbox";
    private AtomicInteger sortTrigger = new AtomicInteger(1);
    private AtomicInteger filterTrigger = new AtomicInteger(1);
    private int sortingOrFilteringOrSearchingOrNormal = 0;//0--Normal, 1--Sorting, 2--filtering, 3--Searching
    boolean firstTime = true;
    String lastSortChoice = "oldest first";
    String lastFilterChoice = "Show all";
    ComboBox<String> sortChoice = new ComboBox<>();
    ComboBox<String> tempComboBox;
    String currentViewedEmailID = "";
    Label searchMessage = new Label();
    int settingsToggle = 0; //0 for showing the menu, 1 for hiding the menu
    //composeHelper
    private SinglyLinkedList AllCards = new SinglyLinkedList();
    private SinglyLinkedList AlldropDownInfo = new SinglyLinkedList();
    private SinglyLinkedList AllReceiversCards = new SinglyLinkedList();
    private SinglyLinkedList AllReceiversEmails = new SinglyLinkedList();
    //attachmenats
    private AtomicReference<String> emailPriority = new AtomicReference<>("none");
    private SinglyLinkedList allAttachmentsPaths = new SinglyLinkedList();
    private SinglyLinkedList allAttachmentsNames = new SinglyLinkedList();
    private SinglyLinkedList allAttachmentsCards = new SinglyLinkedList();
    //Showing Pane
    private SinglyLinkedList showPageCheckBoxes = new SinglyLinkedList();
    private SinglyLinkedList showPageSelectedCheckBoxes = new SinglyLinkedList();
    private SinglyLinkedList showPageSelectedEmailsIDs = new SinglyLinkedList();
    private SinglyLinkedList showPageAllEmailsCards = new SinglyLinkedList();
    private SinglyLinkedList showPageAllEmailsButtons = new SinglyLinkedList();
    private EmailComponents[] emailsList = null;

    @FXML
    private Pane show_pane, compose_pane, login_pane, signup_pane, contacts_pane, newContactPopup_pane;
    @FXML
    private Button inbox_button, sent_button, draft_button, trash_button, compose_button, signup_button, login_button, settings_button, contacts_button, addReceiver;
    @FXML
    private Button addanotheremail_button, add_contact_button, popupCancel_button, saveContact_button, attachmentButton, sendButton, composePageCancel, saveToDraftButton;
    @FXML
    private Button gotologin_button, gotosignup_button, deleteContactButton, sendToContactsButton, showContactCancelButton, showContactDeleteButton, showContactEditButton, showContactAddButton;
    @FXML
    private TextField signup_username_input, signup_email_input, login_email_input, newContactName, newEmailTextField_1, contactNameLabel, addReceiverEmail, emailSubject;
    @FXML
    private TextArea newEmailTextBody;
    @FXML
    private Label signup_username_info, signup_email_info, signup_pass_info, confirmation_info, login_email_info, login_pass_info, error_label, userNameLabel;
    @FXML
    private PasswordField signup_pass_input, signup_password_confirmation, login_pass_input;
    @FXML
    private AnchorPane popup_scrollPane, contactsList, MainRightSide, contactEmailsList, composeHelperContactsList, receiversPanel, attachmentPanel;
    @FXML
    private ScrollPane scroll, contactsScroll_pane, contactEmailsScroll, composeHelperScroll, receiversPaneScroll, attachmentScroll;
    @FXML
    private CheckBox selectAll;
    @FXML
    private Label selectAll_label, priorityInfo;
    @FXML
    private Pane smallMenu, selectAll_pane, showContactsPane, composePaneControlPane, showEmail_pane;
    @FXML
    private Button showNextPage, showPrevPage, showDeleteEmailButton;
    @FXML
    private CheckBox showSelectAllButton;
    @FXML
    private Label showSelectAll_label, showPageLabel;
    @FXML
    private ScrollPane showScroll_pane;
    @FXML
    private AnchorPane showEmailsList;
    @FXML
    private Pane showSmallMenu, showSelectAll, controlBar, settingsMenu;
    @FXML
    private ScrollPane showreceiversPaneScroll, showAttachmentScroll;
    @FXML
    private AnchorPane showreceiversPanel, showAttachmentsPanel;
    @FXML
    private Label senderLabel, fromLabel, toLabel, subjectLabel;
    @FXML
    private TextArea showEmailBodyText;
    @FXML
    private Button showEmailCancelButton, showEmailDeleteButton, searchButton, logOutButton, createAccountButton, reloadButton;
    @FXML
    private TextField searchBar;
    @FXML
    private void handleButtonAction(ActionEvent event) throws InterruptedException {
        if(event.getSource() == inbox_button){
            activePage = "Inbox";
            showPageLabel.setText(activePage);
            draftLowerIndex = 0;
            draftUpperIndex = 9;
            sentLowerIndex = 0;
            sentUpperIndex = 9;
            trashLowerIndex = 0;
            trashUpperIndex = 9;
            show_pane.toFront();
            controlBar.toFront();
            inbox_button.setStyle("-fx-background-color: #1663be");
            sent_button.setStyle("-fx-background-color: 0");
            draft_button.setStyle("-fx-background-color: 0");
            trash_button.setStyle("-fx-background-color: 0");
            contacts_button.setStyle("-fx-background-color: 0");
            searchBar.setPromptText("Search for words");
            EmailFunctions process1 = new EmailFunctions();
            controlBar.getChildren().remove(sortChoice);
            Sort process2 = new Sort();
            Filter process3 = new Filter();
            addSortAndFilterFeature();
            if(sortingOrFilteringOrSearchingOrNormal == 0) {
                emailsList = process1.displayEmails(loggedInEmail, "Inbox", inboxLowerIndex, inboxUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Inbox");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 1) {
                if(sortTrigger.get() == 1) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Inbox", 1, inboxLowerIndex, inboxUpperIndex);
                else if(sortTrigger.get() == 2) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Inbox", 0, inboxLowerIndex, inboxUpperIndex);
                else if(sortTrigger.get() == 3) emailsList = process2.getSortedEmailsByTime(loggedInEmail, "Inbox", 1, inboxLowerIndex, inboxUpperIndex);
                else if(sortTrigger.get() == 4) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Inbox", 0, inboxLowerIndex, inboxUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Inbox");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 2){
                if(filterTrigger.get() == 1) emailsList = process3.getFilteredEmails(loggedInEmail, "Inbox", "d", 1, inboxLowerIndex, inboxUpperIndex);
                else if(filterTrigger.get() == 2) emailsList = process3.getFilteredEmails(loggedInEmail, "Inbox", "d", 0, inboxLowerIndex, inboxUpperIndex);
                else if(filterTrigger.get() == 3) emailsList = process3.getFilteredEmails(loggedInEmail, "Inbox", "c", 1, inboxLowerIndex, inboxUpperIndex);
                else if(filterTrigger.get() == 4) emailsList = process3.getFilteredEmails(loggedInEmail, "Inbox", "c", 0, inboxLowerIndex, inboxUpperIndex);
                else if(filterTrigger.get() == 5) emailsList = process3.getFilteredEmails(loggedInEmail, "Inbox", "b", 1, inboxLowerIndex, inboxUpperIndex);
                else if(filterTrigger.get() == 6) emailsList = process3.getFilteredEmails(loggedInEmail, "Inbox", "b", 0, inboxLowerIndex, inboxUpperIndex);
                else if(filterTrigger.get() == 7) emailsList = process3.getFilteredEmails(loggedInEmail, "Inbox", "a", 1, inboxLowerIndex, inboxUpperIndex);
                else if(filterTrigger.get() == 8) emailsList = process3.getFilteredEmails(loggedInEmail, "Inbox", "a", 0, inboxLowerIndex, inboxUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Inbox");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 3){
                Search searchingProcess = new Search();
                EmailComponents[] searchResults = searchingProcess.emailsContainingWord(loggedInEmail, "Inbox", searchBar.getText());
                if(searchResults == null){
                    EmailComponents[] emptySearchResults = new EmailComponents[0];
                    showEmailsList.getChildren().remove(searchMessage);
                    showEmailsListAccordingToArray(emptySearchResults, "Inbox");
                    searchMessage.setPrefWidth(500);
                    searchMessage.setPrefHeight(60);
                    showEmailsList.getChildren().add(searchMessage);
                    searchMessage.setLayoutX(450);
                    searchMessage.setLayoutY(238);
                    searchMessage.setStyle("-fx-background-color: 0; -fx-text-fill: grey");
                    searchMessage.setFont(Font.font("Microsoft Sans Serif", 22));
                    searchMessage.setText("No Matching Search Results");
                }
                else showEmailsListAccordingToArray(searchResults, "Inbox");
                addExitSearchButton();
            }
            firstTime = false;
        }
        else if(event.getSource() == sent_button){
            activePage = "Sent";
            showPageLabel.setText(activePage);
            draftLowerIndex = 0;
            draftUpperIndex = 9;
            inboxLowerIndex = 0;
            inboxUpperIndex = 9;
            trashLowerIndex = 0;
            trashUpperIndex = 9;
            show_pane.toFront();
            controlBar.toFront();
            sent_button.setStyle("-fx-background-color: #1663be");
            inbox_button.setStyle("-fx-background-color: 0");
            draft_button.setStyle("-fx-background-color: 0");
            trash_button.setStyle("-fx-background-color: 0");
            contacts_button.setStyle("-fx-background-color: 0");
            searchBar.setPromptText("Search for words");
            EmailFunctions process1 = new EmailFunctions();
            controlBar.getChildren().remove(sortChoice);
            Sort process2 = new Sort();
            Filter process3 = new Filter();
            addSortAndFilterFeature();
            if(sortingOrFilteringOrSearchingOrNormal == 0) {
                emailsList = process1.displayEmails(loggedInEmail, "Sent", sentLowerIndex, sentUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Sent");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 1) {
                if(sortTrigger.get() == 1) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Sent", 1, sentLowerIndex, sentUpperIndex);
                else if(sortTrigger.get() == 2) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Sent", 0, sentLowerIndex, sentUpperIndex);
                else if(sortTrigger.get() == 3) emailsList = process2.getSortedEmailsByTime(loggedInEmail, "Sent", 1, sentLowerIndex, sentUpperIndex);
                else if(sortTrigger.get() == 4) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Sent", 0, sentLowerIndex, sentUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Sent");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 2){
                if(filterTrigger.get() == 1) emailsList = process3.getFilteredEmails(loggedInEmail, "Sent", "d", 1, sentLowerIndex, sentUpperIndex);
                else if(filterTrigger.get() == 2) emailsList = process3.getFilteredEmails(loggedInEmail, "Sent", "d", 0, sentLowerIndex, sentUpperIndex);
                else if(filterTrigger.get() == 3) emailsList = process3.getFilteredEmails(loggedInEmail, "Sent", "c", 1, sentLowerIndex, sentUpperIndex);
                else if(filterTrigger.get() == 4) emailsList = process3.getFilteredEmails(loggedInEmail, "Sent", "c", 0, sentLowerIndex, sentUpperIndex);
                else if(filterTrigger.get() == 5) emailsList = process3.getFilteredEmails(loggedInEmail, "Sent", "b", 1, sentLowerIndex, sentUpperIndex);
                else if(filterTrigger.get() == 6) emailsList = process3.getFilteredEmails(loggedInEmail, "Sent", "b", 0, sentLowerIndex, sentUpperIndex);
                else if(filterTrigger.get() == 7) emailsList = process3.getFilteredEmails(loggedInEmail, "Sent", "a", 1, sentLowerIndex, sentUpperIndex);
                else if(filterTrigger.get() == 8) emailsList = process3.getFilteredEmails(loggedInEmail, "Sent", "a", 0, sentLowerIndex, sentUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Sent");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 3){
                Search searchingProcess = new Search();
                EmailComponents[] searchResults = searchingProcess.emailsContainingWord(loggedInEmail, "Sent", searchBar.getText());
                if(searchResults == null){
                    EmailComponents[] emptySearchResults = new EmailComponents[0];
                    showEmailsList.getChildren().remove(searchMessage);
                    showEmailsListAccordingToArray(emptySearchResults, "Sent");
                    searchMessage.setPrefWidth(500);
                    searchMessage.setPrefHeight(60);
                    showEmailsList.getChildren().add(searchMessage);
                    searchMessage.setLayoutX(450);
                    searchMessage.setLayoutY(238);
                    searchMessage.setStyle("-fx-background-color: 0; -fx-text-fill: grey");
                    searchMessage.setFont(Font.font("Microsoft Sans Serif", 22));
                    searchMessage.setText("No Matching Search Results");
                }
                else showEmailsListAccordingToArray(searchResults, "Sent");
                addExitSearchButton();
            }
            firstTime = false;
        }
        else if(event.getSource() == draft_button){
            activePage = "Draft";
            showPageLabel.setText(activePage);
            sentLowerIndex = 0;
            sentUpperIndex = 9;
            inboxLowerIndex = 0;
            inboxUpperIndex = 9;
            trashLowerIndex = 0;
            trashUpperIndex = 9;
            show_pane.toFront();
            controlBar.toFront();
            draft_button.setStyle("-fx-background-color: #1663be");
            sent_button.setStyle("-fx-background-color: 0");
            inbox_button.setStyle("-fx-background-color: 0");
            trash_button.setStyle("-fx-background-color: 0");
            contacts_button.setStyle("-fx-background-color: 0");
            searchBar.setPromptText("Search for words");
            EmailFunctions process1 = new EmailFunctions();
            controlBar.getChildren().remove(sortChoice);
            Sort process2 = new Sort();
            Filter process3 = new Filter();
            addSortAndFilterFeature();
            if(sortingOrFilteringOrSearchingOrNormal == 0) {
                emailsList = process1.displayEmails(loggedInEmail, "Draft", draftLowerIndex, draftUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Draft");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 1) {
                if(sortTrigger.get() == 1) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Draft", 1, draftLowerIndex, draftUpperIndex);
                else if(sortTrigger.get() == 2) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Draft", 0, draftLowerIndex, draftUpperIndex);
                else if(sortTrigger.get() == 3) emailsList = process2.getSortedEmailsByTime(loggedInEmail, "Draft", 1, draftLowerIndex, draftUpperIndex);
                else if(sortTrigger.get() == 4) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Draft", 0, draftLowerIndex, draftUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Draft");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 2){
                if(filterTrigger.get() == 1) emailsList = process3.getFilteredEmails(loggedInEmail, "Draft", "d", 1, draftLowerIndex, draftUpperIndex);
                else if(filterTrigger.get() == 2) emailsList = process3.getFilteredEmails(loggedInEmail, "Draft", "d", 0, draftLowerIndex, draftUpperIndex);
                else if(filterTrigger.get() == 3) emailsList = process3.getFilteredEmails(loggedInEmail, "Draft", "c", 1, draftLowerIndex, draftUpperIndex);
                else if(filterTrigger.get() == 4) emailsList = process3.getFilteredEmails(loggedInEmail, "Draft", "c", 0, draftLowerIndex, draftUpperIndex);
                else if(filterTrigger.get() == 5) emailsList = process3.getFilteredEmails(loggedInEmail, "Draft", "b", 1, draftLowerIndex, draftUpperIndex);
                else if(filterTrigger.get() == 6) emailsList = process3.getFilteredEmails(loggedInEmail, "Draft", "b", 0, draftLowerIndex, draftUpperIndex);
                else if(filterTrigger.get() == 7) emailsList = process3.getFilteredEmails(loggedInEmail, "Draft", "a", 1, draftLowerIndex, draftUpperIndex);
                else if(filterTrigger.get() == 8) emailsList = process3.getFilteredEmails(loggedInEmail, "Draft", "a", 0, draftLowerIndex, draftUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Draft");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 3){
                Search searchingProcess = new Search();
                EmailComponents[] searchResults = searchingProcess.emailsContainingWord(loggedInEmail, "Draft", searchBar.getText());
                if(searchResults == null){
                    EmailComponents[] emptySearchResults = new EmailComponents[0];
                    showEmailsList.getChildren().remove(searchMessage);
                    showEmailsListAccordingToArray(emptySearchResults, "Draft");
                    searchMessage.setPrefWidth(500);
                    searchMessage.setPrefHeight(60);
                    showEmailsList.getChildren().add(searchMessage);
                    searchMessage.setLayoutX(450);
                    searchMessage.setLayoutY(238);
                    searchMessage.setStyle("-fx-background-color: 0; -fx-text-fill: grey");
                    searchMessage.setFont(Font.font("Microsoft Sans Serif", 22));
                    searchMessage.setText("No Matching Search Results");
                }
                else showEmailsListAccordingToArray(searchResults, "Draft");
                addExitSearchButton();
            }
            firstTime = false;
        }
        else if(event.getSource() == trash_button){
            activePage = "Trash";
            showPageLabel.setText(activePage);
            sentLowerIndex = 0;
            sentUpperIndex = 9;
            inboxLowerIndex = 0;
            inboxUpperIndex = 9;
            draftLowerIndex = 0;
            draftUpperIndex = 9;
            show_pane.toFront();
            controlBar.toFront();
            trash_button.setStyle("-fx-background-color: #1663be");
            sent_button.setStyle("-fx-background-color: 0");
            draft_button.setStyle("-fx-background-color: 0");
            inbox_button.setStyle("-fx-background-color: 0");
            contacts_button.setStyle("-fx-background-color: 0");
            searchBar.setPromptText("Search for words");
            EmailFunctions process1 = new EmailFunctions();
            sortChoice.getItems().removeAll();
            controlBar.getChildren().remove(sortChoice);
            Sort process2 = new Sort();
            Filter process3 = new Filter();
            addSortAndFilterFeature();
            if(sortingOrFilteringOrSearchingOrNormal == 0) {
                emailsList = process1.displayEmails(loggedInEmail, "Trash", trashLowerIndex, trashUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Trash");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 1) {
                if(sortTrigger.get() == 1) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Trash", 1, trashLowerIndex, trashUpperIndex);
                else if(sortTrigger.get() == 2) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Trash", 0, trashLowerIndex, trashUpperIndex);
                else if(sortTrigger.get() == 3) emailsList = process2.getSortedEmailsByTime(loggedInEmail, "Trash", 1, trashLowerIndex, trashUpperIndex);
                else if(sortTrigger.get() == 4) emailsList = process2.getSortedEmailsByPriority(loggedInEmail, "Trash", 0, trashLowerIndex, trashUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Trash");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 2){
                if(filterTrigger.get() == 1) emailsList = process3.getFilteredEmails(loggedInEmail, "Trash", "d", 1, trashLowerIndex, trashUpperIndex);
                else if(filterTrigger.get() == 2) emailsList = process3.getFilteredEmails(loggedInEmail, "Trash", "d", 0, trashLowerIndex, trashUpperIndex);
                else if(filterTrigger.get() == 3) emailsList = process3.getFilteredEmails(loggedInEmail, "Trash", "c", 1, trashLowerIndex, trashUpperIndex);
                else if(filterTrigger.get() == 4) emailsList = process3.getFilteredEmails(loggedInEmail, "Trash", "c", 0, trashLowerIndex, trashUpperIndex);
                else if(filterTrigger.get() == 5) emailsList = process3.getFilteredEmails(loggedInEmail, "Trash", "b", 1, trashLowerIndex, trashUpperIndex);
                else if(filterTrigger.get() == 6) emailsList = process3.getFilteredEmails(loggedInEmail, "Trash", "b", 0, trashLowerIndex, trashUpperIndex);
                else if(filterTrigger.get() == 7) emailsList = process3.getFilteredEmails(loggedInEmail, "Trash", "a", 1, trashLowerIndex, trashUpperIndex);
                else if(filterTrigger.get() == 8) emailsList = process3.getFilteredEmails(loggedInEmail, "Trash", "a", 0, trashLowerIndex, trashUpperIndex);
                showEmailsListAccordingToArray(emailsList, "Trash");
            }
            else if(sortingOrFilteringOrSearchingOrNormal == 3){
                Search searchingProcess = new Search();
                EmailComponents[] searchResults = searchingProcess.emailsContainingWord(loggedInEmail, "Trash", searchBar.getText());
                if(searchResults == null){
                    EmailComponents[] emptySearchResults = new EmailComponents[0];
                    showEmailsList.getChildren().remove(searchMessage);
                    showEmailsListAccordingToArray(emptySearchResults, "Trash");
                    searchMessage.setPrefWidth(500);
                    searchMessage.setPrefHeight(60);
                    showEmailsList.getChildren().add(searchMessage);
                    searchMessage.setLayoutX(450);
                    searchMessage.setLayoutY(238);
                    searchMessage.setStyle("-fx-background-color: 0; -fx-text-fill: grey");
                    searchMessage.setFont(Font.font("Microsoft Sans Serif", 22));
                    searchMessage.setText("No Matching Search Results");
                }
                else showEmailsListAccordingToArray(searchResults, "Trash");
                addExitSearchButton();
            }
            firstTime = false;
        }
        else if(event.getSource() == contacts_button){
            activePage = "Contacts";
            controlBar.toFront();
            controlBar.getChildren().remove(tempComboBox);
            sentLowerIndex = 0;
            sentUpperIndex = 9;
            inboxLowerIndex = 0;
            inboxUpperIndex = 9;
            draftLowerIndex = 0;
            draftUpperIndex = 9;
            trashLowerIndex = 0;
            trashUpperIndex = 9;
            contacts_button.setStyle("-fx-background-color: #1663be");
            sent_button.setStyle("-fx-background-color: 0");
            draft_button.setStyle("-fx-background-color: 0");
            trash_button.setStyle("-fx-background-color: 0");
            inbox_button.setStyle("-fx-background-color: 0");
            searchBar.setPromptText("Search for a contact");
            contacts_pane.setPrefWidth(1240);
            contacts_pane.toFront();
            showContactsPane.toBack();
            Contact newContactPage = new Contact();
            addContactSortFeature();
            Sort sortProcess = new Sort();
            String[] contactNames = sortProcess.AToZContacts(loggedInEmail);
            if (contactNames != null) {
                selectAll_pane.setVisible(true);
                constructContacts(contactNames);
            }
            else {
                selectAll_pane.setVisible(false);
                contactNames = new String[]{};
                constructContacts(contactNames);
            }
            //select All checkBox action
            EventHandler<ActionEvent> selectAllAction = e -> {
                if(selectAll.isSelected()){
                    selectAll_label.setStyle("-fx-text-fill: #006bd4");
                    selectedCheckBoxes.clear();
                    for(int a = 0; a < checkBoxes.size(); a++){
                        CheckBox checkBox = (CheckBox)checkBoxes.get(a);
                        checkBox.setSelected(true);
                        Pane contactCard = (Pane) AllContactsCards.get(a);
                        Button contactAccess = (Button) AllContactsButtons.get(a);
                        selectedCheckBoxes.add(checkBox);
                        selectedContacts.add(contactAccess.getText());
                        contactAccess.setStyle("-fx-background-color: derive(#006bd4, 60%);");
                        contactCard.setStyle("-fx-background-color: derive(#006bd4, 60%);" +
                                "-fx-border-color: transparent transparent derive(#006bd4, 60%) transparent;"+
                                "-fx-border-width: 1.5;");
                    }
                    if(selectedCheckBoxes.size() >= 1) smallMenu.setVisible(true);
                    else smallMenu.setVisible(false);
                }else{
                    selectAll_label.setStyle("-fx-text-fill: lightgrey;");
                    selectedCheckBoxes.clear();
                    selectedContacts.clear();
                    for(int a = 0; a < checkBoxes.size(); a++){
                        CheckBox checkBox = (CheckBox)checkBoxes.get(a);
                        Pane contactCard = (Pane) AllContactsCards.get(a);
                        Button contactAccess = (Button) AllContactsButtons.get(a);
                        checkBox.setSelected(false);
                        contactAccess.setStyle("-fx-background-color: white");
                        contactCard.setStyle("-fx-background-color: white;" +
                                "-fx-border-color: transparent transparent lightgrey transparent;"+
                                "-fx-border-width: 1.5;");
                    }
                    smallMenu.setVisible(false);
                }
            };
            selectAll.setOnAction(selectAllAction);

            //deleteContactButton action
            Image greyIcon = new Image(getClass().getResourceAsStream("/Resources/greybin.png"));
            ImageView greyTrashIcon = new ImageView(greyIcon);
            greyTrashIcon.setFitHeight(20);
            greyTrashIcon.setFitWidth(20);
            deleteContactButton.setGraphic(greyTrashIcon);
            deleteContactButton.setOnMouseEntered(e->{
                Image blueIcon = new Image(getClass().getResourceAsStream("/Resources/bluebin.png"));
                ImageView blueTrashIcon = new ImageView(blueIcon);
                blueTrashIcon.setFitHeight(20);
                blueTrashIcon.setFitWidth(20);
                deleteContactButton.setGraphic(blueTrashIcon);
            });
            deleteContactButton.setOnMouseExited(e->{
                deleteContactButton.setGraphic(greyTrashIcon);
            });
            deleteContactButton.setOnMouseClicked(e->{
                contacts_pane.setStyle("-fx-background-color: whitesmoke;");
                contacts_pane.setEffect(new GaussianBlur());
                contactsList.setStyle("-fx-background-color: whitesmoke;");
                contactsList.setEffect(new GaussianBlur());
                Pane confirmationMessage = new Pane();
                MainRightSide.getChildren().add(confirmationMessage);
                confirmationMessage.toFront();
                confirmationMessage.setPrefHeight(150);
                confirmationMessage.setPrefWidth(350);
                confirmationMessage.setLayoutX(445);
                confirmationMessage.setLayoutY(300);
                confirmationMessage.setStyle("-fx-border-width: 1;" +
                        "    -fx-border-radius: 2em;" +
                        "    -fx-border-color: #ffffff;" +
                        "    -fx-effect: dropshadow(three-pass-box, #eaeaea, 5, 0, 5, 8);" +
                        "    -fx-background-color: #ffffff");
                Label message = new Label();
                message.setText("Sure to delete " + selectedContacts.size() + " Contacts?");
                message.setFont(Font.font("Microsoft Sans Serif",22 ));
                message.setAlignment(Pos.CENTER);
                message.setPrefWidth(300);
                message.setPrefHeight(40);
                confirmationMessage.getChildren().add(message);
                message.setLayoutX(25);
                message.setLayoutY(20);
                Button confirm = new Button();
                confirm.setText("Delete");
                confirm.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
                confirm.setFont(Font.font("Microsoft Sans Serif",18 ));
                confirm.setPrefHeight(50);
                confirm.setPrefWidth(100);
                confirmationMessage.getChildren().add(confirm);
                confirm.setLayoutY(80);
                confirm.setLayoutX(200);
                confirm.setOnMouseEntered(x ->{
                    confirm.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
                });
                confirm.setOnMouseExited(x ->{
                    confirm.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
                });
                confirm.setOnMouseClicked(x ->{
                    contacts_pane.setStyle("-fx-background-color: white;");
                    contacts_pane.setEffect(null);
                    contactsList.setStyle("-fx-background-color: white;");
                    contactsList.setEffect(null);
                    MainRightSide.getChildren().remove(confirmationMessage);
                    Contact deleteProcess = new Contact();
                    for(int a = 0; a < selectedContacts.size(); a++){
                        String contactName = (String)selectedContacts.get(a);
                        deleteProcess.deleteContact(loggedInEmail, contactName);
                    }
                    String[] updatedContactList = deleteProcess.getAllContacts(loggedInEmail);
                    if (updatedContactList != null) {
                        selectAll_pane.setVisible(true);
                        constructContacts(updatedContactList);
                    }
                    else {
                        selectAll_pane.setVisible(false);
                        updatedContactList = new String[]{};
                        constructContacts(updatedContactList);
                    }
                });

                Button cancel = new Button();
                cancel.setText("cancel");
                cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
                cancel.setFont(Font.font("Microsoft Sans Serif",18 ));
                cancel.setPrefHeight(50);
                cancel.setPrefWidth(100);
                confirmationMessage.getChildren().add(cancel);
                cancel.setLayoutY(80);
                cancel.setLayoutX(50);
                cancel.setOnMouseEntered(x ->{
                    cancel.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
                });
                cancel.setOnMouseExited(x ->{
                    cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
                });
                cancel.setOnMouseClicked(x ->{
                    contacts_pane.setStyle("-fx-background-color: white;");
                    contacts_pane.setEffect(null);
                    contactsList.setStyle("-fx-background-color: white;");
                    contactsList.setEffect(null);
                    MainRightSide.getChildren().remove(confirmationMessage);
                });
            });
        }
        else if(event.getSource() == compose_button){
            composePageCancel.fire();
            int down = 0;
            int up = 1;
            for(int a = 0; a < AllCards.size(); a++){
                Pane card = (Pane)AllCards.get(a);
                composeHelperContactsList.getChildren().remove(card);
            }
            for(int a = 0; a < AlldropDownInfo.size(); a++){
                Pane card = (Pane)AlldropDownInfo.get(a);
                composeHelperContactsList.getChildren().remove(card);
            }
            AllCards.clear();
            AlldropDownInfo.clear();
            receiversPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            receiversPaneScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            attachmentScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            attachmentScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            //comboBox
            ComboBox<String> priorityChoice = new ComboBox<>();
            priorityChoice.getItems().addAll("Urgent", "High", "Medium", "Low");
            priorityChoice.setStyle("-fx-font: 18px \"Microsoft Sans Serif\";");
            priorityChoice.setPromptText("Priority");
            priorityChoice.setPrefHeight(40);
            priorityChoice.setPrefWidth(150);
            composePaneControlPane.getChildren().add(priorityChoice);
            priorityChoice.setLayoutX(10);
            priorityChoice.setLayoutY(5);
            priorityChoice.setPromptText("Priority");

            priorityChoice.setOnAction(e -> {
                String priorityChoiceValue = priorityChoice.getValue();
                System.out.println(priorityChoice.getValue());
                if(priorityChoiceValue == "Low") emailPriority.set("d");
                else if(priorityChoiceValue == "Medium") emailPriority.set("c");
                else if(priorityChoiceValue == "High") emailPriority.set("b");
                else emailPriority.set("a");
            });
            ///////////////
            receiversPanel.setPrefHeight(58);
            compose_pane.toFront();
            Contact helperContacts = new Contact();
            int contactCardYLayout = 0;
            composeHelperScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            composeHelperScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            composeHelperContactsList.setPrefHeight(728);
            String[] contactNames = helperContacts.getAllContacts(loggedInEmail);
            for(int i = 0; i < contactNames.length; i++){
                Pane contactCard = new Pane();
                contactCard.setPrefWidth(426);
                contactCard.setPrefHeight(50);
                composeHelperContactsList.getChildren().add(contactCard);
                AllCards.add(contactCard);
                contactCard.setId(Integer.toString(i));
                contactCard.setLayoutX(0);
                contactCard.setLayoutY(contactCardYLayout);
                contactCardYLayout += 50;
                Label contactName = new Label();
                contactName.setPrefWidth(296);
                contactName.setPrefHeight(40);
                contactCard.getChildren().add(contactName);
                contactName.setLayoutX(20);
                contactName.setLayoutY(5);
                contactName.setText(contactNames[i]);
                contactName.setStyle("-fx-background-color: white;");
                contactName.setFont(Font.font("Microsoft Sans Serif",18 ));
                Pane dropDownInfo = new Pane();
                dropDownInfo.setId(Integer.toString(i));
                AlldropDownInfo.add(dropDownInfo);
                Button dropDown = new Button();
                dropDown.setPrefSize(30,30);
                contactCard.getChildren().add(dropDown);
                dropDown.setId(Integer.toString(down));
                dropDown.setLayoutY(10);
                dropDown.setLayoutX(376);
                dropDown.setStyle("-fx-background-color: 0");
                Image icon = new Image(getClass().getResourceAsStream("/Resources/arrowDown.png"));
                ImageView arrowDown = new ImageView(icon);
                arrowDown.setFitHeight(15);
                arrowDown.setFitWidth(15);
                dropDown.setGraphic(arrowDown);
                dropDown.setOnMouseClicked(x->{
                    int toggle = Integer.parseInt(dropDown.getId());
                    String[] contactEmails = helperContacts.getEmailAddressesFromUserName(loggedInEmail, contactName.getText());
                    int height = (contactEmails.length *30) + 5;
                    if(toggle == 0){
                        dropDown.setId(Integer.toString(up));
                        contactCard.setStyle("-fx-background-color: #eaeaea; " +
                                "-fx-border-color: #eaeaea #eaeaea #eaeaea #eaeaea;"+
                                "-fx-border-width: 1.5;");
                        contactName.setStyle("-fx-background-color: #eaeaea;");
                        Image icon2 = new Image(getClass().getResourceAsStream("/Resources/up-arrow.png"));
                        ImageView arrowUp = new ImageView(icon2);
                        arrowUp.setFitHeight(15);
                        arrowUp.setFitWidth(15);
                        dropDown.setGraphic(arrowUp);
                        int currentCardID = Integer.parseInt(contactCard.getId());
                        dropDownInfo.setPrefWidth(424);
                        dropDownInfo.setPrefHeight(height);
                        composeHelperContactsList.getChildren().add(dropDownInfo);
                        dropDownInfo.setLayoutX(0);
                        dropDownInfo.setLayoutY(contactCard.getLayoutY() + 50);
                        dropDownInfo.setStyle("-fx-background-color: white; " +
                                "-fx-border-color: transparent #eaeaea #eaeaea #eaeaea;"+
                                "-fx-border-width: 1.5;");
                        int emailLabelylayout = 0;
                        for(int a = 0; a < contactEmails.length; a++){
                            Pane emailCard = new Pane();
                            emailCard.setPrefHeight(30);
                            emailCard.setPrefWidth(424);
                            emailCard.setStyle("-fx-background-color: 0;");
                            dropDownInfo.getChildren().add(emailCard);
                            emailCard.setLayoutX(0);
                            emailCard.setLayoutY(emailLabelylayout);
                            emailLabelylayout +=30;
                            //email Label
                            Label email = new Label();
                            email.setPrefHeight(30);
                            email.setPrefWidth(296);
                            email.setText(contactEmails[a]);
                            emailCard.getChildren().add(email);
                            email.setLayoutX(20);
                            email.setLayoutY(0);
                            email.setStyle("-fx-background-color: white");
                            email.setFont(Font.font("Microsoft Sans Serif",14 ));
                            //add to Receivers Button
                            Button addToReceivers = new Button();
                            addToReceivers.setPrefSize(20,20);
                            addToReceivers.setStyle("-fx-background-color: 0");
                            emailCard.getChildren().add(addToReceivers);
                            addToReceivers.setLayoutX(376);
                            addToReceivers.setLayoutY(5);
                            Image plusIcon= new Image(getClass().getResourceAsStream("/Resources/plus.png"));
                            ImageView addIcon = new ImageView(plusIcon);
                            addIcon.setFitHeight(10);
                            addIcon.setFitWidth(10);
                            addToReceivers.setGraphic(addIcon);
                            addToReceivers.setOnMouseClicked(y ->{
                                if(!AllReceiversEmails.contains(email.getText())){
                                    Pane newReceiver = new Pane();
                                    newReceiver.setPrefWidth(300);
                                    newReceiver.setPrefHeight(20);
                                    newReceiver.setStyle("-fx-background-color: #eaeaea; -fx-background-radius: 5;") ;
                                    receiversPanel.getChildren().add(newReceiver);
                                    AllReceiversCards.add(newReceiver);
                                    AllReceiversEmails.add(email.getText());
                                    newReceiver.setId(Integer.toString(AllReceiversCards.size() -1));
                                    if(Integer.parseInt(newReceiver.getId()) % 2 == 0) newReceiver.setLayoutX(20);
                                    else newReceiver.setLayoutX(330);
                                    double yLayout = 0;
                                    if(AllReceiversCards.size() == 1 || AllReceiversCards.size() == 2) yLayout = 3;
                                    else {
                                        double h = Math.ceil((double) AllReceiversCards.size()/2);
                                        yLayout = (h -1)*20 + (3 * h);
                                    }
                                    newReceiver.setLayoutY(yLayout);

                                    Label newReceiverName = new Label();
                                    newReceiverName.setPrefWidth(260);
                                    newReceiverName.setPrefHeight(20);
                                    newReceiver.getChildren().add(newReceiverName);
                                    newReceiverName.setStyle("-fx-background-color: 0");
                                    newReceiverName.setFont(Font.font("Microsoft Sans Serif",14 ));
                                    newReceiverName.setLayoutX(10);
                                    newReceiverName.setLayoutY(0);
                                    newReceiverName.setText(email.getText());

                                    Button newReceiverRemove = new Button();
                                    newReceiverRemove.setPrefSize(20, 20);
                                    newReceiverRemove.setStyle("-fx-background-color: 0");
                                    newReceiver.getChildren().add(newReceiverRemove);
                                    newReceiverRemove.setLayoutX(270);
                                    newReceiverRemove.setLayoutY(-3);
                                    Image cancelIcon= new Image(getClass().getResourceAsStream("/Resources/blackCancel.png"));
                                    ImageView removeIcon = new ImageView(cancelIcon);
                                    removeIcon.setFitHeight(10);
                                    removeIcon.setFitWidth(10);
                                    newReceiverRemove.setGraphic(removeIcon);
                                    newReceiverRemove.setOnMouseClicked(e ->{
                                        receiversPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                                        receiversPaneScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                                        for(int j = 0; j < AllReceiversCards.size(); j++){
                                            Pane pane = (Pane)AllReceiversCards.get(j);
                                            receiversPanel.getChildren().remove(pane);
                                        }
                                        AllReceiversCards.remove(newReceiver);
                                        AllReceiversEmails.remove(newReceiverName.getText());
                                        SinglyLinkedList temp = new SinglyLinkedList();
                                        for(int j = 0; j < AllReceiversCards.size(); j++){
                                            Pane receiver = (Pane)AllReceiversCards.get(j);
                                            receiversPanel.getChildren().add(receiver);
                                            temp.add(receiver);
                                            receiver.setId(Integer.toString(temp.size() -1));
                                            if(Integer.parseInt(receiver.getId()) % 2 == 0) receiver.setLayoutX(20);
                                            else receiver.setLayoutX(330);
                                            double tempY = 0;
                                            if(temp.size() == 1 || temp.size() == 2) tempY = 3;
                                            else {
                                                double h = Math.ceil((double) temp.size()/2);
                                                tempY = (h -1)*20 + (3 * h);
                                            }
                                            receiver.setLayoutY(tempY);
                                        }
                                        double panelHeight = Math.ceil((double) temp.size()/2) * 26;
                                        if(panelHeight < receiversPanel.getPrefHeight()){
                                            receiversPanel.setPrefHeight(58);
                                        }
                                    });

                                    double panelHeight = Math.ceil((double) AllReceiversCards.size()/2) * 26;
                                    if(panelHeight > receiversPanel.getPrefHeight()){
                                        receiversPanel.setPrefHeight(panelHeight + 10);
                                    }
                                }
                            });
                        }
                        for(int a = 0; a < AllCards.size(); a++){
                            Pane card = (Pane)AllCards.get(a);
                            int cardId = Integer.parseInt(card.getId());
                            if(cardId > currentCardID){
                                card.setLayoutY(card.getLayoutY() + height);
                            }
                        }
                        for(int a = 0; a < AlldropDownInfo.size(); a++){
                            Pane card = (Pane)AlldropDownInfo.get(a);
                            int cardId = Integer.parseInt(card.getId());
                            if(cardId > currentCardID){
                                card.setLayoutY(card.getLayoutY() + height);
                            }
                        }
                    }
                    else{
                        composeHelperContactsList.getChildren().remove(dropDownInfo);
                        AlldropDownInfo.remove(dropDownInfo);
                        dropDown.setId(Integer.toString(down));
                        contactCard.setStyle("-fx-background-color: white; " +
                                "-fx-border-color: transparent #eaeaea transparent transparent;"+
                                "-fx-border-width: 1.5;");
                        contactName.setStyle("-fx-background-color: white");
                        dropDown.setGraphic(arrowDown);
                        int currentCardID = Integer.parseInt(contactCard.getId());
                        for(int a = 0; a < AllCards.size(); a++){
                            Pane card = (Pane)AllCards.get(a);
                            int cardId = Integer.parseInt(card.getId());
                            if(cardId > currentCardID){
                                card.setLayoutY(card.getLayoutY() - height);
                            }
                        }
                        for(int a = 0; a < AlldropDownInfo.size(); a++){
                            Pane card = (Pane)AlldropDownInfo.get(a);
                            int cardId = Integer.parseInt(card.getId());
                            if(cardId > currentCardID){
                                card.setLayoutY(card.getLayoutY() - height);
                            }
                        }
                    }
                });

                contactCard.setStyle("-fx-background-color: white; " +
                        "-fx-border-color: transparent transparent #eaeaea transparent;"+
                        "-fx-border-width: 1.5;");
                contactCard.setOnMouseEntered(x->{
                    if(Integer.parseInt(dropDown.getId()) == 0){
                        contactCard.setStyle("-fx-background-color: #eaeaea; " +
                                "-fx-border-color: transparent transparent #eaeaea transparent;"+
                                "-fx-border-width: 1.5;");
                        contactName.setStyle("-fx-background-color: #eaeaea");
                    }
                });
                contactCard.setOnMouseExited(x->{
                    if(Integer.parseInt(dropDown.getId()) == 0){
                        contactCard.setStyle("-fx-background-color: white; " +
                                "-fx-border-color: transparent transparent #eaeaea transparent;"+
                                "-fx-border-width: 1.5;");
                        contactName.setStyle("-fx-background-color: white");
                    }
                });
            }
            if(contactNames.length * 50 > composeHelperContactsList.getPrefHeight()){
                contactsList.setPrefHeight(contactNames.length*50 + 20);
            }
        }
    }

    @FXML
    private void SignUpForm(ActionEvent event){
        if(event.getSource() == signup_button){
            SignUpProcess newProcess = new SignUpProcess();
            String username = signup_username_input.getText();
            String email = signup_email_input.getText();
            String password = signup_pass_input.getText();
            String confirmationPassword = signup_password_confirmation.getText();
            boolean usernameIsEmpty = username == null || username.length() == 0;
            boolean emailIsEmpty = email == null || email.length() == 0;
            boolean passwordIsEmpty = password == null || password.length() == 0;
            boolean confirmationPasswordIsEmpty = confirmationPassword == null || confirmationPassword.length() == 0;

            boolean usernameIsValid = true;
            boolean emailIsValid = true;
            boolean passwordIsValid = true;
            boolean confirmationIsValid = true;

            if(!usernameIsEmpty && !newProcess.isValidUsername(username)){
                usernameIsValid = false;
                signup_username_info.setText("User name is invalid");
                signup_username_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                        "-fx-border-width: 1.5;");
            }else if(!usernameIsEmpty && newProcess.doesUserExists(username)){
                usernameIsValid = false;
                signup_username_info.setText("User name can't be used");
                signup_username_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                        "-fx-border-width: 1.5;");
            }
            else if(usernameIsEmpty){
                usernameIsValid = false;
                signup_username_info.setText("User name is empty");
                signup_username_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                        "-fx-border-width: 1.5;");
            }else{
                signup_username_info.setText("");
                signup_username_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #26AB7B transparent;\n" +
                        "-fx-border-width: 1.5;");
            }

            if(!emailIsEmpty && (!newProcess.isValidEmail(email) || newProcess.doesEmailExists(email))){
                emailIsValid = false;
                signup_email_info.setText("Email is invalid");
                signup_email_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                        "-fx-border-width: 1.5;");
            }
            else if(emailIsEmpty){
                emailIsValid = false;
                signup_email_info.setText("Email is empty");
                signup_email_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                        "-fx-border-width: 1.5;");
            }else{
                signup_email_info.setText("");
                signup_email_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #26AB7B transparent;\n" +
                        "-fx-border-width: 1.5;");
            }

            if(!passwordIsEmpty && !newProcess.isValidPassword(password)){
                passwordIsValid = false;
                signup_pass_info.setText("Password is invalid");
                signup_pass_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                        "-fx-border-width: 1.5;");
                signup_pass_input.clear();
            }else if(passwordIsEmpty){
                passwordIsValid = false;
                signup_pass_info.setText("Password is empty");
                signup_pass_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                        "-fx-border-width: 1.5;");
            }else{
                signup_pass_info.setText("");
                signup_pass_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #26AB7B transparent;\n" +
                        "-fx-border-width: 1.5;");
            }


            if(!passwordIsEmpty && passwordIsValid && !confirmationPasswordIsEmpty && !newProcess.isConfirmed(password, confirmationPassword)) {
                confirmationIsValid = false;
                confirmation_info.setText("Re entered password doesn't match");
                signup_password_confirmation.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                        "-fx-border-width: 1.5;");
                signup_password_confirmation.clear();
            }
            else if(!passwordIsValid){
                confirmationIsValid = false;
                confirmation_info.setText("");
                signup_password_confirmation.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #006bd4 transparent;\n" +
                        "-fx-border-width: 1.5;");
                signup_password_confirmation.clear();
            }
            else if(confirmationPasswordIsEmpty && !passwordIsEmpty){
                confirmationIsValid = false;
                confirmation_info.setText("Please re enter the password");
                signup_password_confirmation.setStyle("-fx-background-color: #ffffff;\n" +
                    "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                    "-fx-border-width: 1.5;");
            }else if(passwordIsEmpty){
                confirmationIsValid = false;
                confirmation_info.setText("Password is empty");
                signup_password_confirmation.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                        "-fx-border-width: 1.5;");
            }
            else{
                confirmation_info.setText("");
                signup_password_confirmation.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #26AB7B transparent;\n" +
                        "-fx-border-width: 1.5;");
            }
            if(usernameIsValid && passwordIsValid && emailIsValid && confirmationIsValid){
                newProcess.signUp(email, password, username);
                sortingOrFilteringOrSearchingOrNormal = 0;
                signup_pane.toBack();
                login_pane.toBack();
                inbox_button.fire();
                userNameLabel.setText(username);
                loggedInEmail = email;
            }
        }
        else if(event.getSource() == gotologin_button){
            login_pane.toFront();
        }
    }
    @FXML
    private void LoginForm(ActionEvent event){
        if(event.getSource() == login_button){
            String email = login_email_input.getText();
            String password = login_pass_input.getText();
            boolean emailIsEmpty = email == null || email.length() == 0;
            boolean passwordIsEmpty = password == null || password.length() == 0;
            if(emailIsEmpty){
                login_email_info.setText("Email is empty");
                login_email_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                        "-fx-border-width: 1.5;");
            }else{
                login_email_info.setText("");
                login_email_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #006bd4 transparent;\n" +
                        "-fx-border-width: 1.5;");
            }

            if(passwordIsEmpty){
                login_pass_info.setText("Email is empty");
                login_pass_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                        "-fx-border-width: 1.5;");
            }else{
                login_pass_info.setText("");
                login_pass_input.setStyle("-fx-background-color: #ffffff;\n" +
                        "-fx-border-color: transparent transparent #006bd4 transparent;\n" +
                        "-fx-border-width: 1.5;");
            }

            if(!emailIsEmpty && !passwordIsEmpty){
                LogInProcess newProcess = new LogInProcess();
                String username = newProcess.isValidLogin(email, password);
                if(username == null){
                    login_pass_info.setTextAlignment(TextAlignment.CENTER);
                    login_pass_info.setText("Email or password is not correct");
                    login_pass_input.clear();
                    login_email_input.setStyle("-fx-background-color: #ffffff;\n" +
                            "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                            "-fx-border-width: 1.5;");
                    login_pass_input.setStyle("-fx-background-color: #ffffff;\n" +
                            "-fx-border-color: transparent transparent #f35b5b transparent;\n" +
                            "-fx-border-width: 1.5;");
                }else{
                    userNameLabel.setText(username);//edit
                    sortingOrFilteringOrSearchingOrNormal = 0;
                    loggedInEmail = email;
                    login_pane.toBack();
                    EmailFunctions updatingTrash = new EmailFunctions();
                    updatingTrash.deleteEmailAutomatically(loggedInEmail);
                    signup_pane.toBack();
                    login_pane.toBack();
                    inbox_button.fire();
                }
            }
        }
        else if(event.getSource() == gotosignup_button){
            signup_pane.toFront();
        }
    }

    @FXML
    public void addAnotherEmailTextField(ActionEvent event){
        if(event.getSource() == addanotheremail_button){
            TextField anotherEmail = new TextField();
            Button removeButton = new Button();
            scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            TextFieldYLayout += 80;
            buttonYLayout += 80;
            extraPaneH += 80;
            popup_scrollPane.getChildren().add(anotherEmail);
            popup_scrollPane.getChildren().add(removeButton);
            if(extraPaneH > 198){
                popup_scrollPane.setPrefHeight(extraPaneH);
            }
            anotherEmail.setLayoutX(10);
            anotherEmail.setLayoutY(TextFieldYLayout);
            anotherEmail.setPromptText("Contact Email");
            anotherEmail.setPrefWidth(320);
            anotherEmail.setPrefHeight(45);
            anotherEmail.setFont(Font.font("Microsoft Sans Serif",18 ));
            anotherEmail.setId(Integer.toString(idCount));
            TextFieldIds.add(anotherEmail);

            removeButton.setLayoutX(340);
            removeButton.setLayoutY(buttonYLayout);
            removeButton.setPrefWidth(35);
            removeButton.setPrefHeight(35);
            removeButton.setStyle(
                    "-fx-background-color: 0;" +
                    "-fx-border-color: #f35b5b;" +
                    "-fx-border-radius: 2em;" +
                    "-fx-border-width: 2;"
            );
            Image Icon = new Image(getClass().getResourceAsStream("/Resources/minus.png"));
            ImageView minusIcon = new ImageView(Icon);
            minusIcon.setFitHeight(10);
            minusIcon.setFitWidth(12);
            removeButton.setGraphic(minusIcon);
            removeButton.setId(Integer.toString(idCount));
            buttonsIds.add(removeButton);
            idCount++;
            count++;

            removeButton.setOnAction(e -> {
                TextField temp = anotherEmail;
                popup_scrollPane.getChildren().remove(anotherEmail);
                TextFieldIds.remove(anotherEmail);
                popup_scrollPane.getChildren().remove(removeButton);
                TextFieldYLayout -= 80;
                buttonYLayout -= 80;
                extraPaneH -= 80;
                if(extraPaneH < 198)
                    popup_scrollPane.setPrefHeight(198);
                for (int i = 0; i < TextFieldIds.size(); i++){
                    TextField textField = (TextField) TextFieldIds.get(i);
                    int textFieldId = Integer.parseInt(textField.getId());
                    int tempId = Integer.parseInt(temp.getId());
                    if(textFieldId > tempId){
                        textField.setLayoutY(textField.getLayoutY() - 80);
                    }
                }
                for(int i = 0; i < buttonsIds.size(); i++) {
                    Button button = (Button) buttonsIds.get(i);
                    if(Integer.parseInt(button.getId()) > Integer.parseInt(temp.getId()))
                        button.setLayoutY(button.getLayoutY() - 80);
                }
                if(Integer.parseInt(temp.getId()) == idCount-1 && popup_scrollPane.getPrefHeight() > 198) {
                    popup_scrollPane.setPrefHeight(popup_scrollPane.getPrefHeight() - 80);
                    System.out.println("in");
                }
                count--;
            });
        }
    }
    @FXML
    public void showNewContactPopUP(ActionEvent event){
        if(event.getSource() == add_contact_button){
            newEmailTextField_1.clear();
            newContactName.clear();
            newContactPopup_pane.toFront();
            scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            contacts_pane.setStyle("-fx-background-color: whitesmoke");
            contacts_pane.setEffect(new GaussianBlur());
            contactsList.setStyle("-fx-background-color: whitesmoke");
            contactsList.setEffect(new GaussianBlur());
            for(int i = 0; i < TextFieldIds.size(); i++){
                TextField textField = (TextField)TextFieldIds.get(i);
                popup_scrollPane.getChildren().remove(textField);
                TextFieldIds.remove(textField);
            }
            for(int i = 0; i < buttonsIds.size(); i++){
                Button button = (Button)buttonsIds.get(i);
                popup_scrollPane.getChildren().remove(button);
                TextFieldIds.remove(button);
            }
            popup_scrollPane.setPrefHeight(198);
            TextFieldYLayout = 10;
            extraPaneH = 70;
            buttonYLayout = 15;
            idCount = 1;
            count = 0;
        }
        else if(event.getSource() == popupCancel_button){
            for(int i = 0; i < TextFieldIds.size(); i++){
                TextField textField = (TextField)TextFieldIds.get(i);
                popup_scrollPane.getChildren().remove(textField);
                TextFieldIds.remove(textField);
            }
            for(int i = 0; i < buttonsIds.size(); i++){
                Button button = (Button)buttonsIds.get(i);
                popup_scrollPane.getChildren().remove(button);
                TextFieldIds.remove(button);
            }
            popup_scrollPane.setPrefHeight(198);
            TextFieldYLayout = 10;
            extraPaneH = 70;
            buttonYLayout = 15;
            idCount = 1;
            count = 0;
            contacts_pane.setStyle("-fx-background-color: white;");
            contacts_pane.setEffect(null);
            contactsList.setStyle("-fx-background-color: white;");
            contactsList.setEffect(null);
            newContactPopup_pane.toBack();
        }
        else if(event.getSource() == saveContact_button){
            String contactName = newContactName.getText();
            boolean emptyContactNameField = false;
            boolean emptyEmailTextField = false;
            boolean validContactName = true;
            boolean validEmail = true;
            if(contactName.length() == 0){
                emptyContactNameField = true;
                newContactName.setStyle("-fx-background-color: #ffffff;" +
                        "    -fx-border-color: transparent transparent #f35b5b transparent;" +
                        "    -fx-border-width: 1.5;"+ "-fx-text-fill: #f35b5b");
                newContactName.setPromptText("Please Enter Contact name");
            }else{
                newContactName.setStyle("-fx-background-color: #ffffff;" +
                        "    -fx-border-color: transparent transparent #eaeaea transparent;" +
                        "    -fx-border-width: 1.5;"+ "-fx-text-fill: black");
            }

            if(newEmailTextField_1.getText().length() == 0){
                    emptyEmailTextField = true;
                    newEmailTextField_1.setStyle("-fx-background-color: #ffffff;" +
                            "    -fx-border-color: transparent transparent #f35b5b transparent;" +
                            "    -fx-border-width: 1.5;"+ "-fx-text-fill: #f35b5b");
                    newEmailTextField_1.setPromptText("Please Enter an Email Address");
            }else{
                    newEmailTextField_1.setStyle("-fx-background-color: #ffffff;" +
                            "    -fx-border-color: transparent transparent #eaeaea transparent;" +
                            "    -fx-border-width: 1.5;"+ "-fx-text-fill: black");
            }

            for(int i = 0; i < TextFieldIds.size(); i++){
                TextField textField = (TextField) TextFieldIds.get(i);
                if(textField.getText().length() == 0){
                    emptyEmailTextField = true;
                    textField.setStyle("-fx-background-color: #ffffff;" +
                            "    -fx-border-color: transparent transparent #f35b5b transparent;" +
                            "    -fx-border-width: 1.5;"+ "-fx-text-fill: #f35b5b");
                    textField.setPromptText("Please Enter an Email Address");
                }else{
                    textField.setStyle("-fx-background-color: #ffffff;" +
                            "    -fx-border-color: transparent transparent #eaeaea transparent;" +
                            "    -fx-border-width: 1.5;"+ "-fx-text-fill: black");
                }
            }

            SignUpProcess check = new SignUpProcess();
            for(int i = 0; i < TextFieldIds.size(); i++){
                TextField textField = (TextField) TextFieldIds.get(i);
                if(check.doesEmailExists(textField.getText())){
                    validEmail = false;
                    textField.setStyle("-fx-background-color: #ffffff;" +
                            "    -fx-border-color: transparent transparent #f35b5b transparent;" +
                            "    -fx-border-width: 1.5;"+ "-fx-text-fill: #f35b5b");
                    textField.clear();
                    textField.setPromptText("Email is in valid");
                }else{
                    textField.setStyle("-fx-background-color: #ffffff;" +
                            "    -fx-border-color: transparent transparent #eaeaea transparent;" +
                            "    -fx-border-width: 1.5;"+ "-fx-text-fill: black");
                }
            }


            Contact newContact = new Contact();
            if(newContact.doesContactExists(loggedInEmail, contactName)) {
                validContactName = false;
                newContactName.setStyle("-fx-background-color: #ffffff;" +
                        "    -fx-border-color: transparent transparent #f35b5b transparent;" +
                        "    -fx-border-width: 1.5;" + "-fx-text-fill: #f35b5b");
                newContactName.setPromptText("You already have this contact in your contacts List");
            }

            if(!emptyContactNameField && !emptyEmailTextField && validContactName && validEmail){
                SinglyLinkedList contactEmails = new SinglyLinkedList();
                contactEmails.add(newEmailTextField_1.getText());
                for(int i = 0; i < TextFieldIds.size(); i++){
                    TextField textField = (TextField) TextFieldIds.get(i);
                    contactEmails.add(textField.getText());
                }
                newContact.createAndAddEmailsContacts(loggedInEmail, contactName, contactEmails);

                for(int i = 0; i < TextFieldIds.size(); i++){
                    TextField textField = (TextField)TextFieldIds.get(i);
                    popup_scrollPane.getChildren().remove(textField);
                    TextFieldIds.remove(textField);
                }
                for(int i = 0; i < buttonsIds.size(); i++){
                    Button button = (Button)buttonsIds.get(i);
                    popup_scrollPane.getChildren().remove(button);
                    TextFieldIds.remove(button);
                }
                popup_scrollPane.setPrefHeight(198);
                TextFieldYLayout = 10;
                extraPaneH = 70;
                buttonYLayout = 15;
                idCount = 1;
                count = 0;
                contacts_pane.setStyle("-fx-background-color: white;");
                contacts_pane.setEffect(null);
                contactsList.setStyle("-fx-background-color: white;");
                contactsList.setEffect(null);
                newContactPopup_pane.toBack();

                String[] contactNames = newContact.getAllContacts(loggedInEmail);
                if(contactNames != null) {
                    selectAll_pane.setVisible(true);
                    constructContacts(contactNames);
                }
                else{
                    selectAll_pane.setVisible(true);
                    contactNames = new String[]{};
                    constructContacts(contactNames);
                }
            }
        }
    }

    private void constructContacts(String[] contactsNames){
        smallMenu.setVisible(false);
        //reset
        for(int a = 0; a < AllContactsCards.size(); a++){ //clear the contacts pane
            contactsList.getChildren().remove(AllContactsCards.get(a));
        }
        checkBoxes.clear();
        selectedContacts.clear();
        selectedCheckBoxes.clear();
        AllContactsCards.clear();
        AllContactsButtons.clear();
        //
        contacts_pane.setPrefWidth(1240);
        contactsScroll_pane.setPrefWidth(1220);
        contactsList.setPrefWidth(1220);
        int contactCardYLayout = 10;
        newContactPopup_pane.toBack();
        contactsScroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        contactsScroll_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        for(int i = 0; i < contactsNames.length; i++){
            add_contact_button.toFront();
            Pane contactCard = new Pane();
            contactsList.getChildren().add(contactCard);
            AllContactsCards.add(contactCard);
            contactCard.setPrefWidth(1210);
            contactCard.setPrefHeight(60);
            contactCard.setLayoutX(0);
            contactCard.setLayoutY(contactCardYLayout);
            contactCardYLayout += 60;
            contactCard.setStyle("-fx-background-color: #ffffff;" +
                    "-fx-border-color: transparent transparent lightgrey transparent;"+
                    "-fx-border-width: 1;");
            //checkBox
            CheckBox selectContact = new CheckBox();
            contactCard.getChildren().add(selectContact);
            checkBoxes.add(selectContact);
            selectContact.setLayoutX(10);
            selectContact.setLayoutY(20);
            selectContact.setPrefSize(20,20);
            selectContact.setStyle("selected-box-color: #006bd4; box-color: white; mark-color: #006bd4;");
            //ContactField
            Button contactAccess = new Button();
            contactCard.getChildren().add(contactAccess);
            AllContactsButtons.add(contactAccess);
            contactAccess.setLayoutX(35);
            contactAccess.setLayoutY(5);
            contactAccess.setPrefWidth(1160);
            contactAccess.setPrefHeight(50);
            contactAccess.setText(contactsNames[i]);
            contactAccess.setFont(Font.font("Microsoft Sans Serif",20 ));
            contactAccess.setAlignment(Pos.BASELINE_LEFT);
            contactAccess.setStyle("-fx-background-color: 0");
            contactAccess.setOnMouseEntered(e ->{
                if(!selectContact.isSelected()){
                    contactAccess.setStyle("-fx-background-color: whitesmoke");
                    contactCard.setStyle("-fx-background-color: whitesmoke;" +
                            "-fx-border-color: transparent transparent lightgrey transparent;"+
                            "-fx-border-width: 1.5;");
                }
            });
            contactAccess.setOnMouseExited(e -> {
                if(!selectContact.isSelected()){
                    contactAccess.setStyle("-fx-background-color: 0");
                    contactCard.setStyle("-fx-background-color: #ffffff;" +
                            "-fx-border-color: transparent transparent lightgrey transparent;"+
                            "-fx-border-width: 1;");
                }
            });
            contactAccess.setOnAction(e -> {
                showContactCancelButton.fire();
                currentContact = contactAccess;
                String contactName = contactAccess.getText();
                Contact newProcess = new Contact();
                String[] contactEmails = newProcess.getEmailAddressesFromUserName(loggedInEmail, contactName);
                showContactInfo(contactName, contactEmails);
            });
            //checkBox action
            EventHandler<ActionEvent> checkboxEvent = e -> {
                if (selectContact.isSelected()){
                    contactAccess.setStyle("-fx-background-color: derive(#006bd4, 60%);");
                    contactCard.setStyle("-fx-background-color: derive(#006bd4, 60%);" +
                            "-fx-border-color: transparent transparent lightgrey transparent;"+
                            "-fx-border-width: 1.5;");
                    selectedCheckBoxes.add(selectContact);
                    selectedContacts.add(contactAccess.getText());
                }
                else{
                    contactAccess.setStyle("-fx-background-color: white");
                    contactCard.setStyle("-fx-background-color: white;" +
                            "-fx-border-color: transparent transparent lightgrey transparent;"+
                            "-fx-border-width: 1.5;");
                    selectedCheckBoxes.remove(selectContact);
                    selectedContacts.remove(contactAccess.getText());
                }
                if(selectedCheckBoxes.size() >= 1) smallMenu.setVisible(true);
                else {
                    smallMenu.setVisible(false);
                    selectAll.setSelected(false);
                    selectAll_label.setStyle("-fx-text-fill: lightgrey");
                }
            };
            selectContact.setOnAction(checkboxEvent);
            //fixing the size
            if((contactsNames.length * 60) > contactsList.getPrefHeight())
                contactsList.setPrefHeight(contactsNames.length*60 + 20);

        }
    }

    public void showContactInfo(String contactName, String[] contactEmails){
        for(int a = 0; a < AllEmailCards.size(); a++){
            Pane card = (Pane)AllEmailCards.get(a);
            contactEmailsList.getChildren().remove(card);
        }
        AllTextFields.clear();
        AllTextFieldsCheckBoxes.clear();
        AllEmailCards.clear();
        selectedContactEmails.clear();
        oldData.clear();
        contactNameLabel.setEditable(false);
        contactNameLabel.setText(contactName);
        editOrSave = 0;
        showContactDeleteButton.setVisible(false);
        showContactAddButton.setVisible(false);
        contacts_pane.setPrefWidth(1240);
        contactsScroll_pane.setPrefWidth(1220);
        contactsList.setPrefWidth(1220);
        for(int b = 0; b < AllContactsCards.size(); b++){
            Pane card = (Pane) AllContactsCards.get(b);
            card.setPrefWidth(1210);
        }
        for(int c = 0; c < AllContactsButtons.size(); c++){
            Button button = (Button) AllContactsButtons.get(c);
            button.setPrefWidth(1160);
        }
        contactsScroll_pane.setPrefWidth(contactsScroll_pane.getPrefWidth() - 784);
        contactsList.setPrefWidth(contactsList.getPrefWidth() - 784);
        contacts_pane.setPrefWidth(contacts_pane.getPrefWidth() - 784);
        for(int b = 0; b < AllContactsCards.size(); b++){
            Pane card = (Pane) AllContactsCards.get(b);
            card.setPrefWidth(card.getPrefWidth() - 784);
        }
        for(int c = 0; c < AllContactsButtons.size(); c++){
            Button button = (Button) AllContactsButtons.get(c);
            button.setPrefWidth(button.getPrefWidth() - 784);
        }
        for(int d = 0; d < checkBoxes.size(); d++){
            CheckBox checkBox = (CheckBox)checkBoxes.get(d);
            checkBox.setVisible(false);
        }
        showContactsPane.toFront();
        selectAll_pane.setVisible(false);
        contactEmailsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        contactEmailsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        int emailCardYLayout = 0;
        for(int a = 0; a < contactEmails.length; a++){
            Pane emailCard = new Pane();
            contactEmailsList.getChildren().add(emailCard);
            AllEmailCards.add(emailCard);
            emailCard.setPrefWidth(682);
            emailCard.setPrefHeight(60);
            emailCard.setLayoutX(0);
            emailCard.setLayoutY(emailCardYLayout);
            emailCardYLayout += 60;
            emailCard.setStyle("-fx-background-color: #ffffff;" +
                    "-fx-border-color: transparent transparent transparent transparent;"+
                    "-fx-border-width: 1;");
            //checkBox
            CheckBox selectEmail = new CheckBox();
            emailCard.getChildren().add(selectEmail);
            AllTextFieldsCheckBoxes.add(selectEmail);
            selectEmail.setVisible(false);
            selectEmail.setLayoutX(10);
            selectEmail.setLayoutY(20);
            selectEmail.setPrefSize(20,20);
            selectEmail.setStyle("selected-box-color: #006bd4; box-color: white; mark-color: #006bd4;");
            //ContactField
            TextField email = new TextField();
            emailCard.getChildren().add(email);
            AllTextFields.add(email);
            email.setLayoutX(35);
            email.setLayoutY(0);
            email.setPrefWidth(540);
            email.setPrefHeight(50);
            email.setText(contactEmails[a]);
            email.setEditable(false);
            email.setFont(Font.font("Microsoft Sans Serif",18 ));
            email.setAlignment(Pos.BASELINE_LEFT);
            email.setStyle("-fx-background-color: 0");
            email.setOnMouseEntered(x ->{
                if(!selectEmail.isSelected()){
                    email.setStyle("-fx-background-color: whitesmoke");
                    emailCard.setStyle("-fx-background-color: whitesmoke;" +
                            "-fx-border-color: transparent transparent transparent transparent;"+
                            "-fx-border-width: 1.5;");
                }
            });
            email.setOnMouseExited(x -> {
                if(!selectEmail.isSelected()){
                    email.setStyle("-fx-background-color: 0");
                    emailCard .setStyle("-fx-background-color: #ffffff;" +
                            "-fx-border-color: transparent transparent transparent transparent;"+
                            "-fx-border-width: 1;");
                }
            });
            //checkBox Action
            EventHandler<ActionEvent> selectEmailEvent = x -> {
                if (selectEmail.isSelected()){
                    email.setStyle("-fx-background-color: derive(#006bd4, 60%);");
                    emailCard.setStyle("-fx-background-color: derive(#006bd4, 60%);" +
                            "-fx-border-color: transparent transparent transparent transparent;"+
                            "-fx-border-width: 1.5;");
                    selectedContactEmails.add(email.getText());
                }
                else{
                    email.setStyle("-fx-background-color: white");
                    emailCard.setStyle("-fx-background-color: white;" +
                            "-fx-border-color: transparent transparent transparent transparent;"+
                            "-fx-border-width: 1.5;");
                    selectedContactEmails.remove(email.getText());
                }
            };
            selectEmail.setOnAction(selectEmailEvent);
        }
        //fixing the size
        if((contactEmails.length * 60) > contactEmailsList.getPrefHeight())
            contactEmailsList.setPrefHeight(contactEmails.length*60 + 20);
    }
    @FXML
    private void showContactPaneHandle(ActionEvent event){
        if(event.getSource() == showContactCancelButton){
            editOrSave = 0;
            Image icon2 = new Image(getClass().getResourceAsStream("/Resources/pencil.png"));
            ImageView editIcon = new ImageView(icon2);
            editIcon.setFitHeight(20);
            editIcon.setFitWidth(20);
            showContactEditButton.setGraphic(editIcon);
            Contact newProcess= new Contact();
            showContactsPane.toBack();
            contacts_pane.toFront();
            selectAll_pane.setVisible(true);
            for(int n = 0; n < AllEmailCards.size(); n++){
                Pane card = (Pane)AllEmailCards.get(n);
                showContactsPane.getChildren().remove(card);
            }
            AllTextFields.clear();
            AllTextFieldsCheckBoxes.clear();
            AllEmailCards.clear();
            contacts_pane.setPrefWidth(1240);
            contactsScroll_pane.setPrefWidth(1220);
            contactsList.setPrefWidth(1220);
            for(int b = 0; b < AllContactsCards.size(); b++){
                Pane card = (Pane) AllContactsCards.get(b);
                card.setPrefWidth(1210);
            }
            for(int c = 0; c < AllContactsButtons.size(); c++){
                Button button = (Button) AllContactsButtons.get(c);
                button.setPrefWidth(1160);
            }
            for(int d = 0; d < checkBoxes.size(); d++){
                CheckBox checkBox = (CheckBox)checkBoxes.get(d);
                checkBox.setVisible(true);
            }
            String[] updatedContacts = newProcess.getAllContacts(loggedInEmail);
            constructContacts(updatedContacts);
        }

        else if(event.getSource() == showContactEditButton){
            if(editOrSave == 0){
                editOrSave = 1;
                oldContactName = contactNameLabel.getText();
                for(int a = 0; a < AllTextFields.size(); a++){
                    TextField textField = (TextField) AllTextFields.get(a);
                    oldData.add(textField.getText());
                }
                showContactDeleteButton.setVisible(true);
                showContactAddButton.setVisible(true);
                Image icon = new Image(getClass().getResourceAsStream("/Resources/save.png"));
                ImageView saveIcon = new ImageView(icon);
                saveIcon.setFitHeight(20);
                saveIcon.setFitWidth(20);
                showContactEditButton.setGraphic(saveIcon);
                contactNameLabel.setEditable(true);
                for(int b = 0; b < AllTextFieldsCheckBoxes.size(); b++){
                    CheckBox checkBox = (CheckBox)AllTextFieldsCheckBoxes.get(b);
                    checkBox.setVisible(true);
                }
                for(int b = 0; b < AllTextFields.size(); b++) {
                    TextField textField = (TextField) AllTextFields.get(b);
                    textField.setEditable(true);
                }
            }
            else{
                //checkValidity
                Contact savingProcess = new Contact();
                boolean emptyContactName = false;
                boolean emptyEmailField = false;
                boolean validContactName = true;

                if(contactNameLabel.getText().length() == 0){
                    emptyContactName = true;
                    error_label.setText("Contact Name is empty!");
                    contactNameLabel.setStyle("-fx-background-color: #ffffff;\n" +
                            "    -fx-border-color: transparent transparent #f35b5b transparent;\n" +
                            "    -fx-border-width: 1.5;");
                }
                else if(!oldContactName.equals(contactNameLabel.getText()) && savingProcess.doesContactExists(loggedInEmail, contactNameLabel.getText())){
                    validContactName = false;
                    error_label.setText("This contact Name Already exists");
                    contactNameLabel.setStyle("-fx-background-color: #ffffff;\n" +
                            "    -fx-border-color: transparent transparent #f35b5b transparent;\n" +
                            "    -fx-border-width: 1.5;");
                }else{
                    error_label.setText("");
                    contactNameLabel.setStyle("-fx-background-color: #ffffff;\n" +
                            "    -fx-border-color: transparent transparent transparent transparent;\n" +
                            "    -fx-border-width: 1.5;");
                }

                for(int a = 0; a < AllTextFields.size(); a++){
                    TextField textField = (TextField) AllTextFields.get(a);
                    if(textField.getText().length() == 0){
                        emptyEmailField = true;
                        textField.setPromptText("please enter an email");
                        textField.setStyle("-fx-background-color: #ffffff;\n" +
                                "    -fx-border-color: transparent transparent #f35b5b transparent;\n" +
                                "    -fx-border-width: 1.5;" + "-fx-text-fill: #f35b5b");
                    }
                }

                if(!emptyContactName && !emptyEmailField && validContactName){
                    editOrSave = 0;
                    Pane confirmationMessage = new Pane();
                    showContactsPane.getChildren().add(confirmationMessage);
                    confirmationMessage.toFront();
                    confirmationMessage.setPrefHeight(150);
                    confirmationMessage.setPrefWidth(400);
                    confirmationMessage.setLayoutX(192);
                    confirmationMessage.setLayoutY(300);
                    confirmationMessage.setStyle("-fx-border-width: 1;" +
                            "    -fx-border-radius: 2em;" +
                            "    -fx-border-color: #ffffff;" +
                            "    -fx-effect: dropshadow(three-pass-box, #eaeaea, 5, 0, 5, 8);" +
                            "    -fx-background-color: #ffffff");
                    Label message = new Label();
                    message.setText("Sure to save changes?");
                    message.setFont(Font.font("Microsoft Sans Serif",22 ));
                    message.setAlignment(Pos.CENTER);
                    message.setPrefWidth(300);
                    message.setPrefHeight(40);
                    confirmationMessage.getChildren().add(message);
                    message.setLayoutX(50);
                    message.setLayoutY(20);
                    Button confirm = new Button();
                    confirm.setText("Save");
                    confirm.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
                    confirm.setFont(Font.font("Microsoft Sans Serif",18 ));
                    confirm.setPrefHeight(50);
                    confirm.setPrefWidth(100);
                    confirmationMessage.getChildren().add(confirm);
                    confirm.setLayoutY(80);
                    confirm.setLayoutX(280);
                    confirm.setOnMouseEntered(x ->{
                        confirm.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
                    });
                    confirm.setOnMouseExited(x ->{
                        confirm.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
                    });
                    confirm.setOnMouseClicked(x ->{
                        showContactsPane.getChildren().remove(confirmationMessage);
                        Contact editingProcess = new Contact();
                        String newContactName = contactNameLabel.getText();
                        SinglyLinkedList contactEmails = new SinglyLinkedList();
                        for(int a = 0; a < AllTextFields.size(); a++){
                            TextField textField = (TextField) AllTextFields.get(a);
                            contactEmails.add(textField.getText());
                        }
                        editingProcess.deleteContact(loggedInEmail, oldContactName);
                        editingProcess.createAndAddEmailsContacts(loggedInEmail, newContactName, contactEmails);
                        Image icon = new Image(getClass().getResourceAsStream("/Resources/pencil.png"));
                        ImageView editIcon = new ImageView(icon);
                        editIcon.setFitHeight(20);
                        editIcon.setFitWidth(20);
                        showContactEditButton.setGraphic(editIcon);
                        currentContact.setText(newContactName);
                        String[] editedContactsEmails = editingProcess.getEmailAddressesFromUserName(loggedInEmail, newContactName);
                        showContactInfo(newContactName, editedContactsEmails);
                    });

                    Button revert = new Button();
                    revert.setText("revert Changes");
                    revert.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
                    revert.setFont(Font.font("Microsoft Sans Serif",11 ));
                    revert.setPrefHeight(50);
                    revert.setPrefWidth(100);
                    confirmationMessage.getChildren().add(revert);
                    revert.setLayoutY(80);
                    revert.setLayoutX(150);
                    revert.setOnMouseEntered(x ->{
                        revert.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
                    });
                    revert.setOnMouseExited(x ->{
                        revert.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
                    });
                    revert.setOnMouseClicked(x ->{
                        showContactsPane.getChildren().remove(confirmationMessage);
                        contactNameLabel.setText(oldContactName);
                        for(int a = 0; a < AllTextFields.size(); a++){
                            TextField textField = (TextField) AllTextFields.get(a);
                            textField.setText((String)oldData.get(a));
                        }
                        Image icon2 = new Image(getClass().getResourceAsStream("/Resources/pencil.png"));
                        ImageView editIcon = new ImageView(icon2);
                        editIcon.setFitHeight(20);
                        editIcon.setFitWidth(20);
                        showContactEditButton.setGraphic(editIcon);
                    });

                    Button cancel = new Button();
                    cancel.setText("cancel");
                    cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
                    cancel.setFont(Font.font("Microsoft Sans Serif",18 ));
                    cancel.setPrefHeight(50);
                    cancel.setPrefWidth(100);
                    confirmationMessage.getChildren().add(cancel);
                    cancel.setLayoutY(80);
                    cancel.setLayoutX(20);
                    cancel.setOnMouseEntered(x ->{
                        cancel.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
                    });
                    cancel.setOnMouseExited(x ->{
                        cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
                    });
                    cancel.setOnMouseClicked(x ->{
                        showContactsPane.getChildren().remove(confirmationMessage);
                    });
                }
            }
        }
        else if(event.getSource() == showContactDeleteButton && selectedContactEmails.size() > 0){
            int choice = 0; //0 for deleting some emails and 1 for deleting the whole contact
            Contact newProcess = new Contact();
            String contactName = contactNameLabel.getText();
            Pane confirmationMessage = new Pane();
            showContactsPane.getChildren().add(confirmationMessage);
            confirmationMessage.toFront();
            confirmationMessage.setPrefHeight(150);
            confirmationMessage.setPrefWidth(350);
            confirmationMessage.setLayoutX(242);
            confirmationMessage.setLayoutY(300);
            confirmationMessage.setStyle("-fx-border-width: 1;" +
                    "    -fx-border-radius: 2em;" +
                    "    -fx-border-color: #ffffff;" +
                    "    -fx-effect: dropshadow(three-pass-box, #eaeaea, 5, 0, 5, 8);" +
                    "    -fx-background-color: #ffffff");
            Label message = new Label();
            if(selectedContactEmails.size() == AllTextFields.size()){
                message.setText("Sure to delete Contact!");
                choice = 1;
            }
            else{
                message.setText("Sure to delete " + selectedContactEmails.size() + " emails?");
            }
            message.setFont(Font.font("Microsoft Sans Serif",22 ));
            message.setAlignment(Pos.CENTER);
            message.setPrefWidth(300);
            message.setPrefHeight(40);
            confirmationMessage.getChildren().add(message);
            message.setLayoutX(25);
            message.setLayoutY(20);
            Button confirm = new Button();
            confirm.setText("Delete");
            confirm.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            confirm.setFont(Font.font("Microsoft Sans Serif",18 ));
            confirm.setPrefHeight(50);
            confirm.setPrefWidth(100);
            confirmationMessage.getChildren().add(confirm);
            confirm.setLayoutY(80);
            confirm.setLayoutX(200);
            confirm.setOnMouseEntered(x ->{
                confirm.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            confirm.setOnMouseExited(x ->{
                confirm.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            int finalChoice = choice;
            confirm.setOnMouseClicked(x ->{
                showContactsPane.getChildren().remove(confirmationMessage);
                if(finalChoice == 0){
                    for(int a = 0; a < selectedContactEmails.size(); a++){
                        String email = (String) selectedContactEmails.get(a);
                        newProcess.deleteEmail(loggedInEmail, contactNameLabel.getText(), email);
                    }
                    String[] contactEmails = newProcess.getEmailAddressesFromUserName(loggedInEmail, contactName);
                    showContactInfo(contactName, contactEmails);
                }
                else{
                    newProcess.deleteContact(loggedInEmail, contactName);

                    showContactCancelButton.fire();
                }
            });

            Button cancel = new Button();
            cancel.setText("cancel");
            cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            cancel.setFont(Font.font("Microsoft Sans Serif",18 ));
            cancel.setPrefHeight(50);
            cancel.setPrefWidth(100);
            confirmationMessage.getChildren().add(cancel);
            cancel.setLayoutY(80);
            cancel.setLayoutX(50);
            cancel.setOnMouseEntered(x ->{
                cancel.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            cancel.setOnMouseExited(x ->{
                cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            cancel.setOnMouseClicked(x ->{
                showContactsPane.getChildren().remove(confirmationMessage);
            });
        }
        else if(event.getSource() == showContactAddButton){
            Contact newProcess = new Contact();
            String contactName = contactNameLabel.getText();
            Pane AddingWindow = new Pane();
            showContactsPane.getChildren().add(AddingWindow);
            AddingWindow.toFront();
            AddingWindow.setPrefHeight(150);
            AddingWindow.setPrefWidth(350);
            AddingWindow.setLayoutX(242);
            AddingWindow.setLayoutY(300);
            AddingWindow.setStyle("-fx-border-width: 1;" +
                    "    -fx-border-radius: 2em;" +
                    "    -fx-border-color: #ffffff;" +
                    "    -fx-effect: dropshadow(three-pass-box, #eaeaea, 5, 0, 5, 8);" +
                    "    -fx-background-color: #ffffff");
            TextField newEmail = new TextField();
            newEmail.setPromptText("Add the new email");
            newEmail.setFont(Font.font("Microsoft Sans Serif",22 ));
            newEmail.setPrefWidth(300);
            newEmail.setPrefHeight(40);
            AddingWindow.getChildren().add(newEmail);
            newEmail.setLayoutX(25);
            newEmail.setLayoutY(20);
            Button confirm = new Button();
            confirm.setText("Save");
            confirm.setStyle("-fx-background-color: #26AB7B; -fx-text-fill: white; -fx-border-radius: 3;");
            confirm.setFont(Font.font("Microsoft Sans Serif",18 ));
            confirm.setPrefHeight(50);
            confirm.setPrefWidth(100);
            AddingWindow.getChildren().add(confirm);
            confirm.setLayoutY(80);
            confirm.setLayoutX(200);
            confirm.setOnMouseEntered(x ->{
                confirm.setStyle("-fx-background-color: forestgreen; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            confirm.setOnMouseExited(x ->{
                confirm.setStyle("-fx-background-color: #26AB7B; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            confirm.setOnMouseClicked(x ->{
                if(newEmail.getText().length() == 0){
                    newEmail.setPromptText("please enter an email");
                    newEmail.setStyle("-fx-background-color: #ffffff;\n" +
                            "    -fx-border-color: transparent transparent #f35b5b transparent;\n" +
                            "    -fx-border-width: 1.5;" + "-fx-text-fill: #f35b5b");
                }else{
                    showContactsPane.getChildren().remove(AddingWindow);
                    SinglyLinkedList contactEmails = new SinglyLinkedList();
                    for(int a = 0; a < AllTextFields.size(); a++){
                        TextField textField = (TextField) AllTextFields.get(a);
                        contactEmails.add(textField.getText());
                    }
                    contactEmails.add(newEmail.getText());
                    newProcess.deleteContact(loggedInEmail, contactName);
                    newProcess.createAndAddEmailsContacts(loggedInEmail, contactName, contactEmails);
                    Image icon = new Image(getClass().getResourceAsStream("/Resources/pencil.png"));
                    ImageView editIcon = new ImageView(icon);
                    editIcon.setFitHeight(20);
                    editIcon.setFitWidth(20);
                    showContactEditButton.setGraphic(editIcon);
                    String[] updatedContactEmails = newProcess.getEmailAddressesFromUserName(loggedInEmail, contactName);
                    showContactInfo(contactName, updatedContactEmails);
                }
            });

            Button cancel = new Button();
            cancel.setText("cancel");
            cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            cancel.setFont(Font.font("Microsoft Sans Serif",18 ));
            cancel.setPrefHeight(50);
            cancel.setPrefWidth(100);
            AddingWindow.getChildren().add(cancel);
            cancel.setLayoutY(80);
            cancel.setLayoutX(50);
            cancel.setOnMouseEntered(x ->{
                cancel.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            cancel.setOnMouseExited(x ->{
                cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            cancel.setOnMouseClicked(x ->{
                showContactsPane.getChildren().remove(AddingWindow);
            });
        }
    }

    @FXML
    private void composeHandle(ActionEvent event){
        if(event.getSource() == addReceiver && addReceiverEmail.getText().length() != 0){
            receiversPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            receiversPaneScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            String email = addReceiverEmail.getText();
            email = email.trim();
            addReceiverEmail.clear();
            if(!AllReceiversEmails.contains(email)){
                Pane newReceiver = new Pane();
                newReceiver.setPrefWidth(300);
                newReceiver.setPrefHeight(20);
                newReceiver.setStyle("-fx-background-color: #eaeaea; -fx-background-radius: 5;") ;
                receiversPanel.getChildren().add(newReceiver);
                AllReceiversCards.add(newReceiver);
                AllReceiversEmails.add(email);
                newReceiver.setId(Integer.toString(AllReceiversCards.size() -1));
                if(Integer.parseInt(newReceiver.getId()) % 2 == 0) newReceiver.setLayoutX(20);
                else newReceiver.setLayoutX(330);
                double yLayout = 0;
                if(AllReceiversCards.size() == 1 || AllReceiversCards.size() == 2) yLayout = 3;
                else {
                    double h = Math.ceil((double) AllReceiversCards.size()/2);
                    yLayout = (h -1)*20 + (3 * h);
                }
                newReceiver.setLayoutY(yLayout);

                Label newReceiverName = new Label();
                newReceiverName.setPrefWidth(260);
                newReceiverName.setPrefHeight(20);
                newReceiver.getChildren().add(newReceiverName);
                newReceiverName.setStyle("-fx-background-color: 0");
                newReceiverName.setFont(Font.font("Microsoft Sans Serif",14 ));
                newReceiverName.setLayoutX(10);
                newReceiverName.setLayoutY(0);
                newReceiverName.setText(email);

                Button newReceiverRemove = new Button();
                newReceiverRemove.setPrefSize(20, 20);
                newReceiverRemove.setStyle("-fx-background-color: 0");
                newReceiver.getChildren().add(newReceiverRemove);
                newReceiverRemove.setLayoutX(270);
                newReceiverRemove.setLayoutY(-3);
                Image cancelIcon= new Image(getClass().getResourceAsStream("/Resources/blackCancel.png"));
                ImageView removeIcon = new ImageView(cancelIcon);
                removeIcon.setFitHeight(10);
                removeIcon.setFitWidth(10);
                newReceiverRemove.setGraphic(removeIcon);
                newReceiverRemove.setOnMouseClicked(e ->{
                    receiversPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    receiversPaneScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                    for(int j = 0; j < AllReceiversCards.size(); j++){
                        Pane pane = (Pane)AllReceiversCards.get(j);
                        receiversPanel.getChildren().remove(pane);
                    }
                    AllReceiversCards.remove(newReceiver);
                    AllReceiversEmails.remove(newReceiverName.getText());
                    SinglyLinkedList temp = new SinglyLinkedList();
                    for(int j = 0; j < AllReceiversCards.size(); j++){
                        Pane receiver = (Pane)AllReceiversCards.get(j);
                        receiversPanel.getChildren().add(receiver);
                        temp.add(receiver);
                        receiver.setId(Integer.toString(temp.size() -1));
                        if(Integer.parseInt(receiver.getId()) % 2 == 0) receiver.setLayoutX(20);
                        else receiver.setLayoutX(330);
                        double tempY = 0;
                        if(temp.size() == 1 || temp.size() == 2) tempY = 3;
                        else {
                            double h = Math.ceil((double) temp.size()/2);
                            tempY = (h -1)*20 + (3 * h);
                        }
                        receiver.setLayoutY(tempY);
                    }
                    double panelHeight = Math.ceil((double) temp.size()/2) * 26;
                    if(panelHeight < receiversPanel.getPrefHeight()){
                        receiversPanel.setPrefHeight(58);
                    }
                });
                double panelHeight = Math.ceil((double) AllReceiversCards.size()/2) * 26;
                if(panelHeight > receiversPanel.getPrefHeight()){
                    receiversPanel.setPrefHeight(panelHeight + 10);
                }
            }
        }
    }

    @FXML
    private void attachmentHandle(ActionEvent event){
        if(event.getSource() == attachmentButton){
            String email = addReceiverEmail.getText();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(compose_pane.getScene().getWindow());

            if (file != null && !allAttachmentsPaths.contains(file.getAbsolutePath())) {
                 Pane newAttachment = new Pane();
                 newAttachment.setPrefWidth(300);
                 newAttachment.setPrefHeight(20);
                 newAttachment.setStyle("-fx-background-color: #eaeaea; -fx-background-radius: 5;") ;
                 attachmentPanel.getChildren().add(newAttachment);
                 allAttachmentsCards.add(newAttachment);
                 allAttachmentsPaths.add(file.getAbsolutePath());
                 allAttachmentsNames.add(file.getName());
                 newAttachment.setId(Integer.toString(allAttachmentsCards.size() -1));
                 if(Integer.parseInt(newAttachment.getId()) % 2 == 0) newAttachment.setLayoutX(20);
                 else newAttachment.setLayoutX(330);
                 double yLayout = 0;
                 if(allAttachmentsCards.size() == 1 || allAttachmentsCards.size() == 2) yLayout = 3;
                 else {
                     double h = Math.ceil((double) allAttachmentsCards.size()/2);
                     yLayout = (h -1)*20 + (3 * h);
                 }
                 newAttachment.setLayoutY(yLayout);

                Label newAttachmentName = new Label();
                newAttachmentName.setPrefWidth(260);
                newAttachmentName.setPrefHeight(20);
                newAttachment.getChildren().add(newAttachmentName);
                newAttachmentName.setStyle("-fx-background-color: 0");
                newAttachmentName.setFont(Font.font("Microsoft Sans Serif",14 ));
                newAttachmentName.setLayoutX(10);
                newAttachmentName.setLayoutY(0);
                newAttachmentName.setText(file.getName());

                Button newAttachmentRemove = new Button();
                newAttachmentRemove.setPrefSize(20, 20);
                newAttachmentRemove.setStyle("-fx-background-color: 0");
                newAttachment.getChildren().add(newAttachmentRemove);
                newAttachmentRemove.setLayoutX(270);
                newAttachmentRemove.setLayoutY(-3);
                Image cancelIcon= new Image(getClass().getResourceAsStream("/Resources/blackCancel.png"));
                ImageView removeIcon = new ImageView(cancelIcon);
                removeIcon.setFitHeight(10);
                removeIcon.setFitWidth(10);
                newAttachmentRemove.setGraphic(removeIcon);
                newAttachmentRemove.setOnMouseClicked(e ->{
                    attachmentScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    attachmentScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                    for(int j = 0; j < allAttachmentsCards.size(); j++){
                        Pane pane = (Pane)allAttachmentsCards.get(j);
                        attachmentPanel.getChildren().remove(pane);
                    }
                    allAttachmentsCards.remove(newAttachment);
                    allAttachmentsPaths.remove(file.getAbsolutePath());
                    allAttachmentsNames.remove(file.getName());
                    SinglyLinkedList temp = new SinglyLinkedList();
                    for(int j = 0; j < allAttachmentsCards.size(); j++){
                        Pane attachment = (Pane)allAttachmentsCards.get(j);
                        attachmentPanel.getChildren().add(attachment);
                        temp.add(attachment);
                        attachment.setId(Integer.toString(temp.size() -1));
                        if(Integer.parseInt(attachment.getId()) % 2 == 0) attachment.setLayoutX(20);
                        else attachment.setLayoutX(330);
                        double tempY = 0;
                        if(temp.size() == 1 || temp.size() == 2) tempY = 3;
                        else {
                            double h = Math.ceil((double) temp.size()/2);
                            tempY = (h -1)*20 + (3 * h);
                        }
                        attachment.setLayoutY(tempY);
                        }
                    double panelHeight = Math.ceil((double) temp.size()/2) * 26;
                    if(panelHeight < attachmentPanel.getPrefHeight()){
                        attachmentPanel.setPrefHeight(58);
                    }
                });
                double panelHeight = Math.ceil((double) allAttachmentsCards.size()/2) * 26;
                if(panelHeight > attachmentPanel.getPrefHeight()){
                    attachmentPanel.setPrefHeight(panelHeight + 10);
                }
            }
        }
    }

    @FXML
    private void sendEmailHandle(ActionEvent event){
        if(event.getSource() == sendButton){
            String subject = emailSubject.getText();
            String bodyText = newEmailTextBody.getText();
            boolean subjectIsEmpty = false;
            boolean bodyTextIsEmpty = false;
            boolean receiversIsEmpty = false;
            boolean priorityIsEmpty = false;
            if(subject.length() == 0){
                subjectIsEmpty = true;
                emailSubject.setPromptText("Enter an email Subject");
                emailSubject.setStyle("-fx-border-color: transparent transparent #f35b5b transparent");
            }
            else{
                emailSubject.setPromptText("Email Subject");
                emailSubject.setStyle("-fx-border-color: transparent transparent #eaeaea transparent");
            }

            if(bodyText.length() == 0){
                bodyTextIsEmpty = true;
                newEmailTextBody.setStyle("-fx-border-color: #f35b5b");
            }
            else{
                newEmailTextBody.setStyle("-fx-border-color: #eaeaea");
            }

            if(AllReceiversEmails.size() == 0){
                receiversIsEmpty = true;
                addReceiverEmail.setPromptText("Enter a receiver Email");
                addReceiverEmail.setStyle("-fx-border-color: transparent transparent #f35b5b transparent");
            }
            else{
                addReceiverEmail.setPromptText("add a Receiver");
                addReceiverEmail.setStyle("-fx-border-color: transparent transparent #eaeaea transparent");
            }

            if(emailPriority.get() == "none"){
                priorityIsEmpty = true;
                priorityInfo.setText("Choose a priority");
            }
            else{
                priorityInfo.setText("");
            }

            if(!subjectIsEmpty && !bodyTextIsEmpty && !receiversIsEmpty && !priorityIsEmpty){
                QueueLinkedBased allReceiversInfo = new QueueLinkedBased();
                SinglyLinkedList allAttachmentsInfo = new SinglyLinkedList();
                for(int i = 0; i < allAttachmentsCards.size(); i++){
                    AttachmentComponents attachment = new AttachmentComponents();
                    attachment.setFileName((String)allAttachmentsNames.get(i));
                    attachment.setFilePath((String)allAttachmentsPaths.get(i));
                    allAttachmentsInfo.add(attachment);
                }
                for(int i = 0; i < AllReceiversEmails.size(); i++){
                    allReceiversInfo.enqueue(AllReceiversEmails.get(i));
                }

                EmailFunctions newSendingProcess = new EmailFunctions();
                boolean sentSuccessfully;
                if(allAttachmentsInfo.size() == 0){
                    sentSuccessfully = newSendingProcess.sendEmail(loggedInEmail, allReceiversInfo, subject, bodyText, emailPriority.get(), null);
                }else{
                    sentSuccessfully = newSendingProcess.sendEmail(loggedInEmail, allReceiversInfo, subject, bodyText, emailPriority.get(), allAttachmentsInfo);
                }
                if(!sentSuccessfully){
                    for(int a = 0; a < AllReceiversEmails.size(); a++){
                        String email = (String) AllReceiversEmails.get(a);
                        Pane card = (Pane)AllReceiversCards.get(a);
                        if(!newSendingProcess.emailExists(email)){
                            card.setStyle("-fx-background-color: #f35b5b; -fx-background-radius: 3");
                        }
                        else{
                            card.setStyle("-fx-background-color: #eaeaea; -fx-background-radius: 3");
                        }
                    }
                }else{
                    composePageCancel.fire();
                    compose_button.fire();
                    composePageCancel.fire();
                }
            }
        }
    }


    @FXML
    private void cancelComposePage(ActionEvent event){
        if(event.getSource() == composePageCancel){
            compose_pane.toBack();
            for(int i = 0; i < allAttachmentsCards.size(); i++){
                Pane card = (Pane)allAttachmentsCards.get(i);
                attachmentPanel.getChildren().remove(card);
            }
            for (int i = 0; i< AllCards.size(); i++){
                Pane card = (Pane)AllCards.get(i);
                composeHelperContactsList.getChildren().remove(card);
            }
            for(int i = 0; i < AllReceiversCards.size(); i++){
                Pane card = (Pane)AllReceiversCards.get(i);
                receiversPanel.getChildren().remove(card);
            }
            for(int i = 0; i < AlldropDownInfo.size(); i++){
                Pane card = (Pane)AlldropDownInfo.get(i);
                composeHelperContactsList.getChildren().remove(card);
            }
            AllCards.clear();
            AlldropDownInfo.clear();
            AllReceiversCards.clear();
            AllReceiversEmails.clear();
            emailPriority.set("none");
            allAttachmentsPaths.clear();
            allAttachmentsNames.clear();
            allAttachmentsCards.clear();
            newEmailTextBody.clear();
            emailSubject.clear();
        }
    }

    @FXML
    private void saveToDraftHandle(ActionEvent event){
        if(event.getSource() == saveToDraftButton) {
            Pane confirmationMessage = new Pane();
            compose_pane.getChildren().add(confirmationMessage);
            confirmationMessage.toFront();
            confirmationMessage.setPrefHeight(150);
            confirmationMessage.setPrefWidth(400);
            confirmationMessage.setLayoutX(192);
            confirmationMessage.setLayoutY(300);
            confirmationMessage.setStyle("-fx-border-width: 1;" +
                    "    -fx-border-radius: 2em;" +
                    "    -fx-border-color: #ffffff;" +
                    "    -fx-effect: dropshadow(three-pass-box, #eaeaea, 5, 0, 5, 8);" +
                    "    -fx-background-color: #ffffff");
            Label message = new Label();
            message.setText("Save to Draft?");
            message.setFont(Font.font("Microsoft Sans Serif", 22));
            message.setAlignment(Pos.CENTER);
            message.setPrefWidth(300);
            message.setPrefHeight(40);
            confirmationMessage.getChildren().add(message);
            message.setLayoutX(50);
            message.setLayoutY(20);
            Button confirm = new Button();
            confirm.setText("Save");
            confirm.setStyle("-fx-background-color: #26AB7B; -fx-text-fill: white; -fx-border-radius: 3;");
            confirm.setFont(Font.font("Microsoft Sans Serif", 18));
            confirm.setPrefHeight(50);
            confirm.setPrefWidth(100);
            confirmationMessage.getChildren().add(confirm);
            confirm.setLayoutY(80);
            confirm.setLayoutX(250);
            confirm.setOnMouseEntered(x -> {
                confirm.setStyle("-fx-background-color: forestgreen; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            confirm.setOnMouseExited(x -> {
                confirm.setStyle("-fx-background-color: #26AB7B; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            confirm.setOnMouseClicked(x -> {
                compose_pane.getChildren().remove(confirmationMessage);
                String subject = emailSubject.getText();
                String bodyText = newEmailTextBody.getText();
                boolean subjectIsEmpty = false;
                boolean bodyTextIsEmpty = false;
                boolean priorityIsEmpty = false;
                if (subject.length() == 0) {
                    subjectIsEmpty = true;
                    emailSubject.setPromptText("Enter an email Subject");
                    emailSubject.setStyle("-fx-border-color: transparent transparent #f35b5b transparent");
                } else {
                    emailSubject.setPromptText("Email Subject");
                    emailSubject.setStyle("-fx-border-color: transparent transparent #eaeaea transparent");
                }

                if (bodyText.length() == 0) {
                    bodyTextIsEmpty = true;
                    newEmailTextBody.setStyle("-fx-border-color: #f35b5b");
                } else {
                    newEmailTextBody.setStyle("-fx-border-color: #eaeaea");
                }

                if (emailPriority.get() == "none") {
                    priorityIsEmpty = true;
                    priorityInfo.setText("Choose a priority");
                } else {
                    priorityInfo.setText("");
                }

                if (!subjectIsEmpty && !bodyTextIsEmpty && !priorityIsEmpty) {
                        SinglyLinkedList allAttachmentsInfo = new SinglyLinkedList();
                    for (int i = 0; i < allAttachmentsCards.size(); i++) {
                        AttachmentComponents attachment = new AttachmentComponents();
                        attachment.setFileName((String) allAttachmentsNames.get(i));
                        attachment.setFilePath((String) allAttachmentsPaths.get(i));
                        allAttachmentsInfo.add(attachment);
                    }
                    EmailFunctions newProcess = new EmailFunctions();
                    if (allAttachmentsInfo.size() == 0) {
                        newProcess.moveToDraft(loggedInEmail, subject, bodyText, emailPriority.get(), null);
                    } else {
                        newProcess.moveToDraft(loggedInEmail, subject, bodyText, emailPriority.get(), allAttachmentsInfo);
                    }
                }
                composePageCancel.fire();
            });

            Button cancel = new Button();
            cancel.setText("cancel");
            cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            cancel.setFont(Font.font("Microsoft Sans Serif", 18));
            cancel.setPrefHeight(50);
            cancel.setPrefWidth(100);
            confirmationMessage.getChildren().add(cancel);
            cancel.setLayoutY(80);
            cancel.setLayoutX(20);
            cancel.setOnMouseEntered(x -> {
                cancel.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            cancel.setOnMouseExited(x -> {
                cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            cancel.setOnMouseClicked(x -> {
                compose_pane.getChildren().remove(confirmationMessage);
            });
        }
    }

    public void showEmailsList(EmailComponents[] emailsList){
        showSmallMenu.setVisible(false);
        //reset
        for(int a = 0; a < showPageAllEmailsCards.size(); a++){ //clear the contacts pane
            showEmailsList.getChildren().remove(showPageAllEmailsCards.get(a));
        }
        showPageCheckBoxes.clear();
        showPageSelectedEmailsIDs.clear();
        showPageSelectedCheckBoxes.clear();
        showPageAllEmailsCards.clear();
        showPageAllEmailsButtons.clear();
        //
        show_pane.setPrefWidth(1240);
        showScroll_pane.setPrefWidth(1220);
        showEmailsList.setPrefWidth(1220);
        int sentEmailCardYLayout = 10;
        showScroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        showScroll_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        for(int i = 0; i < emailsList.length; i++){
            Pane showEmailCard = new Pane();
            showEmailsList.getChildren().add(showEmailCard);
            showPageAllEmailsCards.add(showEmailCard);
            showEmailCard.setPrefWidth(1210);
            showEmailCard.setPrefHeight(80);
            showEmailCard.setLayoutX(0);
            showEmailCard.setLayoutY(sentEmailCardYLayout);
            sentEmailCardYLayout += 80;
            showEmailCard.setStyle("-fx-background-color: #ffffff;" +
                    "-fx-border-color: transparent transparent lightgrey transparent;"+
                    "-fx-border-width: 1;");
            //checkBox
            CheckBox selectEmail = new CheckBox();
            showEmailCard.getChildren().add(selectEmail);
            showPageCheckBoxes.add(selectEmail);
            selectEmail.setLayoutX(10);
            selectEmail.setLayoutY(30);
            selectEmail.setPrefSize(20,20);
            selectEmail.setStyle("selected-box-color: #006bd4; box-color: white; mark-color: #006bd4;");
            //ContactField
            Button emailAccess = new Button();
            showEmailCard.getChildren().add(emailAccess);
            showPageAllEmailsButtons.add(emailAccess);
            emailAccess.setLayoutX(35);
            emailAccess.setLayoutY(5);
            emailAccess.setPrefWidth(1160);
            emailAccess.setPrefHeight(70);
            emailAccess.setStyle("-fx-background-color: 0");
            emailAccess.setId(emailsList[i].ID);

            Label subject = new Label();
            subject.setPrefHeight(30);
            subject.setPrefWidth(600);
            showEmailCard.getChildren().add(subject);
            subject.setLayoutX(40);
            subject.setLayoutY(10);
            subject.setText(emailsList[i].subject);
            subject.setStyle("-fx-background-color: 0");
            subject.setAlignment(Pos.BASELINE_LEFT);
            subject.setFont(Font.font("Microsoft Sans Serif", 20));

            Label time = new Label();
            time.setPrefHeight(20);
            time.setPrefWidth(400);
            showEmailCard.getChildren().add(time);
            time.setLayoutX(40);
            time.setLayoutY(50);
            time.setText(emailsList[i].time);
            time.setStyle("-fx-background-color: 0");
            time.setAlignment(Pos.BASELINE_LEFT);
            time.setFont(Font.font("Microsoft Sans Serif", 15));

            Label priorityLabel = new Label();
            priorityLabel.setPrefHeight(30);
            priorityLabel.setPrefWidth(100);
            showEmailCard.getChildren().add(priorityLabel);
            priorityLabel.setLayoutX(650);
            priorityLabel.setLayoutY(10);
            priorityLabel.setText("Priority: ");
            priorityLabel.setFont(Font.font("Microsoft Sans Serif", 20));
            priorityLabel.setStyle("-fx-background-color: 0");

            Label priority = new Label();
            priority.setStyle("-fx-background-color: 0");
            priority.setPrefHeight(30);
            priority.setPrefWidth(150);
            showEmailCard.getChildren().add(priority);
            priority.setLayoutX(730);
            priority.setLayoutY(10);
            String emailPriority = emailsList[i].priority;
            if(emailPriority.equals("a")){
                priority.setText("Urgent");
                priority.setStyle("-fx-text-fill: red");
            }else if(emailPriority.equals("b")){
                priority.setText("High");
                priority.setStyle("-fx-text-fill: orangered");
            }else if(emailPriority.equals("c")){
                priority.setText("Medium");
                priority.setStyle("-fx-text-fill: yellow");
            }else{
                priority.setText("Low");
                priority.setStyle("-fx-text-fill: green");
            }
            priority.setAlignment(Pos.BASELINE_LEFT);
            priority.setFont(Font.font("Microsoft Sans Serif", 20));

            emailAccess.setOnMouseEntered(e ->{
                if(!selectEmail.isSelected()){
                    emailAccess.setStyle("-fx-background-color: whitesmoke");
                    subject.setStyle("-fx-background-color: whitesmoke");
                    if(emailPriority.equals("a")){
                        priority.setText("Urgent");
                        priority.setStyle("-fx-text-fill: red");
                    }else if(emailPriority.equals("b")){
                        priority.setText("High");
                        priority.setStyle("-fx-text-fill: orangered");
                    }else if(emailPriority.equals("c")){
                        priority.setText("Medium");
                        priority.setStyle("-fx-text-fill: yellow");
                    }else{
                        priority.setText("Low");
                        priority.setStyle("-fx-text-fill: green");
                    }
                    time.setStyle("-fx-background-color: whitesmoke");
                    showEmailCard.setStyle("-fx-background-color: whitesmoke;" +
                            "-fx-border-color: transparent transparent lightgrey transparent;"+
                            "-fx-border-width: 1.5;");
                }
            });
            emailAccess.setOnMouseExited(e -> {
                if(!selectEmail.isSelected()){
                    emailAccess.setStyle("-fx-background-color: 0");
                    subject.setStyle("-fx-background-color: 0");
                    if(emailPriority.equals("a")){
                        priority.setText("Urgent");
                        priority.setStyle("-fx-text-fill: red");
                    }else if(emailPriority.equals("b")){
                        priority.setText("High");
                        priority.setStyle("-fx-text-fill: orangered");
                    }else if(emailPriority.equals("c")){
                        priority.setText("Medium");
                        priority.setStyle("-fx-text-fill: yellow");
                    }else{
                        priority.setText("Low");
                        priority.setStyle("-fx-text-fill: green");
                    }
                    time.setStyle("-fx-background-color: 0");
                    showEmailCard.setStyle("-fx-background-color: #ffffff;" +
                            "-fx-border-color: transparent transparent lightgrey transparent;"+
                            "-fx-border-width: 1;");
                }
            });
            emailAccess.setOnAction(e -> {
                EmailFunctions viewingEmail = new EmailFunctions();
                String emailId = emailAccess.getId();
                currentViewedEmailID = emailId;
                EmailComponents toShowEmail = viewingEmail.showEmail(loggedInEmail, activePage, emailId, emailsList);
                String sender = toShowEmail.sender;
                if(activePage.equals("Draft")){
                    composePageCancel.fire();
                    compose_button.fire();
                    newEmailTextBody.setText(toShowEmail.bodyText);
                    emailSubject.setText(toShowEmail.subject);
                    if(toShowEmail.attachments != null){
                        SinglyLinkedList allAttachments = new SinglyLinkedList();
                        allAttachments = toShowEmail.attachments;
                        SinglyLinkedList tempName = new SinglyLinkedList();
                        attachmentScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                        attachmentScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                        for(int y = 0; y < allAttachments.size(); y++){
                            tempName.clear();
                            String attachmentPath =  (String) allAttachments.get(y);
                            String attachmentFileName = "";
                            Stack name = new Stack();
                            for(int x = attachmentPath.length()-1; x >= 0; x--){
                                if(attachmentPath.charAt(x) == '/') break;
                                else name.push(attachmentPath.charAt(x));
                            }
                            while(!name.isEmpty()){
                                attachmentFileName += name.pop();
                            }
                            tempName.add(attachmentFileName);
                            Pane newAttachment = new Pane();
                            newAttachment.setPrefWidth(300);
                            newAttachment.setPrefHeight(20);
                            newAttachment.setStyle("-fx-background-color: #eaeaea; -fx-background-radius: 5;") ;
                            attachmentPanel.getChildren().add(newAttachment);
                            allAttachmentsCards.add(newAttachment);
                            allAttachmentsPaths.add(attachmentPath);
                            allAttachmentsNames.add(attachmentFileName);
                            newAttachment.setId(Integer.toString(allAttachmentsCards.size() -1));
                            if(Integer.parseInt(newAttachment.getId()) % 2 == 0) newAttachment.setLayoutX(20);
                            else newAttachment.setLayoutX(330);
                            double yLayout = 0;
                            if(allAttachmentsCards.size() == 1 || allAttachmentsCards.size() == 2) yLayout = 3;
                            else {
                                double h = Math.ceil((double) allAttachmentsCards.size()/2);
                                yLayout = (h -1)*20 + (3 * h);
                            }
                            newAttachment.setLayoutY(yLayout);

                            Label newAttachmentName = new Label();
                            newAttachmentName.setPrefWidth(260);
                            newAttachmentName.setPrefHeight(20);
                            newAttachment.getChildren().add(newAttachmentName);
                            newAttachmentName.setStyle("-fx-background-color: 0");
                            newAttachmentName.setFont(Font.font("Microsoft Sans Serif",14 ));
                            newAttachmentName.setLayoutX(10);
                            newAttachmentName.setLayoutY(0);
                            newAttachmentName.setText(attachmentFileName);

                            Button newAttachmentRemove = new Button();
                            newAttachmentRemove.setPrefSize(20, 20);
                            newAttachmentRemove.setStyle("-fx-background-color: 0");
                            newAttachment.getChildren().add(newAttachmentRemove);
                            newAttachmentRemove.setLayoutX(270);
                            newAttachmentRemove.setLayoutY(-3);
                            Image cancelIcon= new Image(getClass().getResourceAsStream("/Resources/blackCancel.png"));
                            ImageView removeIcon = new ImageView(cancelIcon);
                            removeIcon.setFitHeight(10);
                            removeIcon.setFitWidth(10);
                            newAttachmentRemove.setGraphic(removeIcon);
                            newAttachmentRemove.setOnMouseClicked(z ->{
                                attachmentScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                                attachmentScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                                for(int j = 0; j < allAttachmentsCards.size(); j++){
                                    Pane pane = (Pane)allAttachmentsCards.get(j);
                                    attachmentPanel.getChildren().remove(pane);
                                }
                                allAttachmentsCards.remove(newAttachment);
                                allAttachmentsPaths.remove(attachmentPath);
                                allAttachmentsNames.remove(tempName.get(0));
                                SinglyLinkedList temp = new SinglyLinkedList();
                                for(int j = 0; j < allAttachmentsCards.size(); j++){
                                    Pane attachment = (Pane)allAttachmentsCards.get(j);
                                    attachmentPanel.getChildren().add(attachment);
                                    temp.add(attachment);
                                    attachment.setId(Integer.toString(temp.size() -1));
                                    if(Integer.parseInt(attachment.getId()) % 2 == 0) attachment.setLayoutX(20);
                                    else attachment.setLayoutX(330);
                                    double tempY = 0;
                                    if(temp.size() == 1 || temp.size() == 2) tempY = 3;
                                    else {
                                        double h = Math.ceil((double) temp.size()/2);
                                        tempY = (h -1)*20 + (3 * h);
                                    }
                                    attachment.setLayoutY(tempY);
                                }
                                double panelHeight = Math.ceil((double) temp.size()/2) * 26;
                                if(panelHeight < attachmentPanel.getPrefHeight()){
                                    attachmentPanel.setPrefHeight(58);
                                }
                            });
                            double panelHeight = Math.ceil((double) allAttachmentsCards.size()/2) * 26;
                            if(panelHeight > attachmentPanel.getPrefHeight()){
                                attachmentPanel.setPrefHeight(panelHeight + 10);
                            }

                        }
                    }
                }
                else{
                showEmail_pane.toFront();
                showreceiversPaneScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                showreceiversPaneScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                showAttachmentScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                showAttachmentScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                if(sender == null){
                    fromLabel.setVisible(false);
                    senderLabel.setVisible(false);
                }else{
                    fromLabel.setVisible(true);
                    senderLabel.setVisible(true);
                    senderLabel.setText(sender);
                }

                if(toShowEmail.receivers == null){
                    showreceiversPaneScroll.setVisible(false);
                    toLabel.setVisible(false);
                }
                else {
                    showreceiversPaneScroll.setVisible(true);
                    toLabel.setVisible(true);
                    SinglyLinkedList receivers = toShowEmail.receivers;
                    SinglyLinkedList temp = new SinglyLinkedList();
                    for (int a = 0; a < receivers.size(); a++) {
                        Pane receiver = new Pane();
                        receiver.setStyle("-fx-background-color: #eaeaea; -fx-background-radius: 3");
                        Label receiverName = new Label();
                        receiverName.setStyle("-fx-background-color: 0");
                        receiverName.setText((String) receivers.get(a));
                        receiver.getChildren().add(receiverName);
                        receiverName.setLayoutY(3);
                        receiverName.setLayoutX(5);
                        receiver.setPrefHeight(26);
                        receiver.setPrefWidth(300);
                        showreceiversPanel.getChildren().add(receiver);
                        temp.add(receiver);
                        receiver.setId(Integer.toString(temp.size() - 1));
                        if (Integer.parseInt(receiver.getId()) % 2 == 0) receiver.setLayoutX(20);
                        else receiver.setLayoutX(330);
                        double tempY = 0;
                        if (temp.size() == 1 || temp.size() == 2) tempY = 3;
                        else {
                            double h = Math.ceil((double) temp.size() / 2);
                            tempY = (h - 1) * 20 + (3 * h);
                        }
                        receiver.setLayoutY(tempY);
                    }

                    double panelHeight = Math.ceil((double) temp.size() / 2) * 26;
                    if (panelHeight < showreceiversPanel.getPrefHeight()) {
                        showreceiversPanel.setPrefHeight(58);
                    }
                }

                if(toShowEmail.attachments == null){
                    showAttachmentScroll.setVisible(false);
                }else{
                    showAttachmentScroll.setVisible(true);
                    SinglyLinkedList allAttachments = new SinglyLinkedList();
                    allAttachments = toShowEmail.attachments;
                    SinglyLinkedList temp = new SinglyLinkedList();
                    for (int a = 0; a < allAttachments.size(); a++) {
                        Pane attachment = new Pane();
                        attachment.setStyle("-fx-background-color: #eaeaea; -fx-background-radius: 3");
                        Button attachmentAccess = new Button();
                        attachmentAccess.setStyle("-fx-background-color: 0");
                        String attachmentPath =  (String) allAttachments.get(a);
                        String attachmentFileName = "";
                        Stack name = new Stack();
                        for(int x = attachmentPath.length()-1; x >= 0; x--){
                            if(attachmentPath.charAt(x) == '/') break;
                            else name.push(attachmentPath.charAt(x));
                        }
                        while(!name.isEmpty()){
                            attachmentFileName += name.pop();
                        }
                        attachmentAccess.setText(attachmentFileName);
                        attachmentAccess.setAlignment(Pos.BASELINE_LEFT);
                        attachment.getChildren().add(attachmentAccess);
                        attachmentAccess.setLayoutY(3);
                        attachmentAccess.setLayoutX(5);
                        attachmentAccess.setPrefWidth(270);
                        attachmentAccess.setPrefHeight(20);
                        attachment.setPrefHeight(26);
                        attachment.setPrefWidth(300);
                        showAttachmentsPanel.getChildren().add(attachment);
                        temp.add(attachment);
                        attachment.setId(Integer.toString(temp.size() - 1));
                        if (Integer.parseInt(attachment.getId()) % 2 == 0) attachment.setLayoutX(20);
                        else attachment.setLayoutX(330);
                        double tempY = 0;
                        if (temp.size() == 1 || temp.size() == 2) tempY = 3;
                        else {
                            double h = Math.ceil((double) temp.size() / 2);
                            tempY = (h - 1) * 20 + (3 * h);
                        }
                        attachment.setLayoutY(tempY);
                        attachmentAccess.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent arg0) {
                                // TODO Auto-generated method stub
                                File attachmentFile = new File(attachmentPath);
                                if (Desktop.isDesktopSupported()) {
                                    try {
                                        Desktop.getDesktop().open(attachmentFile);
                                    }
                                    catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    System.out.println("Awt Desktop is not supported!");
                                }
                            }
                        });
                    }

                    double panelHeight = Math.ceil((double) temp.size() / 2) * 26;
                    if (panelHeight < showreceiversPanel.getPrefHeight()) {
                        showreceiversPanel.setPrefHeight(58);
                    }
                }

                String emailSubject = toShowEmail.subject;
                subjectLabel.setText(emailSubject);
                String bodyText = toShowEmail.bodyText;
                showEmailBodyText.setText(bodyText);
                }
            });
            //checkBox action
            EventHandler<ActionEvent> checkboxEvent = e -> {
                if (selectEmail.isSelected()){
                    emailAccess.setStyle("-fx-background-color: derive(#006bd4, 60%);");
                    subject.setStyle("-fx-background-color: derive(#006bd4, 60%);");
                    priority.setStyle("-fx-background-color: derive(#006bd4, 60%);");
                    time.setStyle("-fx-background-color: derive(#006bd4, 60%);");
                    showEmailCard.setStyle("-fx-background-color: derive(#006bd4, 60%);" +
                            "-fx-border-color: transparent transparent lightgrey transparent;"+
                            "-fx-border-width: 1.5;");
                    showPageSelectedCheckBoxes.add(selectEmail);
                    showPageSelectedEmailsIDs.add(emailAccess.getId());
                }
                else{
                    emailAccess.setStyle("-fx-background-color: white");
                    subject.setStyle("-fx-background-color: white;");
                    priority.setStyle("-fx-background-color: white;");
                    time.setStyle("-fx-background-color: white;");
                    showEmailCard.setStyle("-fx-background-color: white;" +
                            "-fx-border-color: transparent transparent lightgrey transparent;"+
                            "-fx-border-width: 1.5;");
                    showPageSelectedCheckBoxes.remove(selectEmail);
                    showPageSelectedEmailsIDs.remove(emailAccess.getId());
                }
                if(showPageSelectedEmailsIDs.size() >= 1) showSmallMenu.setVisible(true);
                else {
                    showSmallMenu.setVisible(false);
                    showSelectAllButton.setSelected(false);
                    showSelectAll_label.setStyle("-fx-text-fill: lightgrey");
                }
            };
            selectEmail.setOnAction(checkboxEvent);
            //fixing the size
            if((emailsList.length * 80) > showEmailsList.getPrefHeight())
                showEmailsList.setPrefHeight(emailsList.length*80 + 20);

        }
    }

    public void showEmailsListAccordingToArray(EmailComponents[] emailsList, String fileName){
        showSmallMenu.setVisible(false);
        if (emailsList != null) {
            showSelectAll.setVisible(true);
            showEmailsList(emailsList);
        }
        else {
            showSelectAll.setVisible(false);
            EmailComponents[] emptyEmailsList = new EmailComponents[]{};
            showEmailsList(emptyEmailsList);
        }
        //select All checkBox action
        EventHandler<ActionEvent> selectAllAction = e -> {
            if(showSelectAllButton.isSelected()){
                showSelectAll_label.setStyle("-fx-text-fill: #006bd4");
                showPageSelectedCheckBoxes.clear();
                for(int a = 0; a < showPageCheckBoxes.size(); a++){
                    CheckBox checkBox = (CheckBox)showPageCheckBoxes.get(a);
                    checkBox.setSelected(true);
                    Pane emailCard = (Pane) showPageAllEmailsCards.get(a);
                    Button emailAccess = (Button) showPageAllEmailsButtons.get(a);
                    showPageSelectedCheckBoxes.add(checkBox);
                    showPageSelectedEmailsIDs.add(emailAccess.getId());
                    emailAccess.setStyle("-fx-background-color: derive(#006bd4, 60%);");
                    emailCard.setStyle("-fx-background-color: derive(#006bd4, 60%);" +
                            "-fx-border-color: transparent transparent derive(#006bd4, 60%) transparent;"+
                            "-fx-border-width: 1.5;");
                }
                if(showPageSelectedCheckBoxes.size() >= 1) showSmallMenu.setVisible(true);
                else showSmallMenu.setVisible(false);
            }else{
                showSelectAll_label.setStyle("-fx-text-fill: lightgrey;");
                showPageSelectedCheckBoxes.clear();
                showPageSelectedEmailsIDs.clear();
                for(int a = 0; a < showPageCheckBoxes.size(); a++){
                    CheckBox checkBox = (CheckBox)showPageCheckBoxes.get(a);
                    Pane emailCard = (Pane) showPageAllEmailsCards.get(a);
                    Button emailAccess = (Button) showPageAllEmailsButtons.get(a);
                    checkBox.setSelected(false);
                    emailAccess.setStyle("-fx-background-color: white");
                    emailCard.setStyle("-fx-background-color: white;" +
                            "-fx-border-color: transparent transparent lightgrey transparent;"+
                            "-fx-border-width: 1.5;");
                }
                showSmallMenu.setVisible(false);
            }
        };
        showSelectAllButton.setOnAction(selectAllAction);

        //deleteContactButton action
        Image greyIcon = new Image(getClass().getResourceAsStream("/Resources/greybin.png"));
        ImageView greyTrashIcon = new ImageView(greyIcon);
        greyTrashIcon.setFitHeight(20);
        greyTrashIcon.setFitWidth(20);
        showDeleteEmailButton.setGraphic(greyTrashIcon);
        showDeleteEmailButton.setOnMouseEntered(e->{
            Image blueIcon = new Image(getClass().getResourceAsStream("/Resources/bluebin.png"));
            ImageView blueTrashIcon = new ImageView(blueIcon);
            blueTrashIcon.setFitHeight(20);
            blueTrashIcon.setFitWidth(20);
            showDeleteEmailButton.setGraphic(blueTrashIcon);
        });
        showDeleteEmailButton.setOnMouseExited(e->{
            showDeleteEmailButton.setGraphic(greyTrashIcon);
        });
        showDeleteEmailButton.setOnMouseClicked(e->{
            show_pane.setStyle("-fx-background-color: whitesmoke;");
            show_pane.setEffect(new GaussianBlur());
            showEmailsList.setStyle("-fx-background-color: whitesmoke;");
            showEmailsList.setEffect(new GaussianBlur());
            Pane confirmationMessage = new Pane();
            MainRightSide.getChildren().add(confirmationMessage);
            confirmationMessage.toFront();
            confirmationMessage.setPrefHeight(150);
            confirmationMessage.setPrefWidth(350);
            confirmationMessage.setLayoutX(445);
            confirmationMessage.setLayoutY(300);
            confirmationMessage.setStyle("-fx-border-width: 1;" +
                    "    -fx-border-radius: 2em;" +
                    "    -fx-border-color: #ffffff;" +
                    "    -fx-effect: dropshadow(three-pass-box, #eaeaea, 5, 0, 5, 8);" +
                    "    -fx-background-color: #ffffff");
            Label message = new Label();
            if(activePage.equals("Trash")) message.setText("Sure to delete " + showPageSelectedEmailsIDs.size() + " Emails forever");
            else message.setText("Sure to delete " + showPageSelectedEmailsIDs.size() + " Emails?");
            message.setFont(Font.font("Microsoft Sans Serif",22 ));
            message.setAlignment(Pos.CENTER);
            message.setPrefWidth(300);
            message.setPrefHeight(40);
            confirmationMessage.getChildren().add(message);
            message.setLayoutX(25);
            message.setLayoutY(20);
            Button confirm = new Button();
            confirm.setText("Delete");
            confirm.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            confirm.setFont(Font.font("Microsoft Sans Serif",18 ));
            confirm.setPrefHeight(50);
            confirm.setPrefWidth(100);
            confirmationMessage.getChildren().add(confirm);
            confirm.setLayoutY(80);
            confirm.setLayoutX(200);
            confirm.setOnMouseEntered(x ->{
                confirm.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            confirm.setOnMouseExited(x ->{
                confirm.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            confirm.setOnMouseClicked(x ->{
                show_pane.setStyle("-fx-background-color: white;");
                show_pane.setEffect(null);
                showEmailsList.setStyle("-fx-background-color: white;");
                showEmailsList.setEffect(null);
                MainRightSide.getChildren().remove(confirmationMessage);
                EmailFunctions deleteProcess = new EmailFunctions();
                for(int a = 0; a < showPageSelectedEmailsIDs.size(); a++){
                    String emailID = (String)showPageSelectedEmailsIDs.get(a);
                    if(activePage.equals("Trash")) deleteProcess.deleteFromTrash(loggedInEmail, emailID);
                    else deleteProcess.deleteEmail(loggedInEmail, fileName, emailID);
                }
                showSelectAllButton.setSelected(false);
                if(activePage.equals("Sent")) sent_button.fire();
                else if(activePage.equals("Inbox")) inbox_button.fire();
                else if(activePage.equals("Draft")) draft_button.fire();
                else if(activePage.equals("Trash")) trash_button.fire();
            });

            Button cancel = new Button();
            cancel.setText("cancel");
            cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            cancel.setFont(Font.font("Microsoft Sans Serif",18 ));
            cancel.setPrefHeight(50);
            cancel.setPrefWidth(100);
            confirmationMessage.getChildren().add(cancel);
            cancel.setLayoutY(80);
            cancel.setLayoutX(50);
            cancel.setOnMouseEntered(x ->{
                cancel.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            cancel.setOnMouseExited(x ->{
                cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            cancel.setOnMouseClicked(x ->{
                show_pane.setStyle("-fx-background-color: white;");
                show_pane.setEffect(null);
                showEmailsList.setStyle("-fx-background-color: white;");
                showEmailsList.setEffect(null);
                MainRightSide.getChildren().remove(confirmationMessage);
            });
        });
    }


    @FXML
    private void navigationHandle(ActionEvent event){
        if(event.getSource() == showNextPage){
            EmailFunctions process = new EmailFunctions();
            int upperLimit = 0;
            if(activePage.equals("Inbox")){
                upperLimit = process.allEmailsNumber(loggedInEmail, "Inbox") - 1;
                if(inboxLowerIndex+10 <= upperLimit){
                    inboxLowerIndex += 10;
                    inboxUpperIndex += 10;
                }
                inbox_button.fire();
            }
            else if(activePage.equals("Sent")){
                upperLimit = process.allEmailsNumber(loggedInEmail, "Sent") - 1;
                if(sentLowerIndex+10 <= upperLimit){
                    sentLowerIndex += 10;
                    sentUpperIndex += 10;
                }
                sent_button.fire();
            }
            else if(activePage.equals("Draft")){
                upperLimit = process.allEmailsNumber(loggedInEmail, "Draft") - 1;
                if(draftLowerIndex+10 <= upperLimit){
                    draftLowerIndex += 10;
                    draftUpperIndex += 10;
                }
                draft_button.fire();
            }
            else if(activePage.equals("Trash")){
                upperLimit = process.allEmailsNumber(loggedInEmail, "Trash") - 1;
                if(trashLowerIndex+10 <= upperLimit){
                    trashLowerIndex += 10;
                    trashUpperIndex += 10;
                }
                trash_button.fire();
            }
        }
        else if(event.getSource() == showPrevPage){
            if(activePage.equals("Inbox")){
                if(inboxLowerIndex-10 >= 0 && inboxUpperIndex-10 >= 0){
                    inboxLowerIndex -= 10;
                    inboxUpperIndex -= 10;
                }
                inbox_button.fire();
            }
            else if(activePage.equals("Sent")){
                if(sentLowerIndex-10 >= 0 && sentUpperIndex-10 >= 0){
                    sentLowerIndex -= 10;
                    sentUpperIndex -= 10;
                }
                sent_button.fire();
            }
            else if(activePage.equals("Draft")){
                if(draftLowerIndex-10 >= 0 && draftUpperIndex-10 >= 0){
                    draftLowerIndex -= 10;
                    draftUpperIndex -= 10;
                }
                draft_button.fire();
            }
            else if(activePage.equals("Trash")){
                if(trashLowerIndex-10 >= 0 && trashUpperIndex-10 >= 0){
                    trashLowerIndex -= 10;
                    trashUpperIndex -= 10;
                }
                trash_button.fire();
            }
        }
    }



    public void addSortAndFilterFeature(){
            //ComboBox<String> sortChoice = new ComboBox<>();
            sortChoice.setPrefHeight(40);
            sortChoice.setPrefWidth(230);
            controlBar.getChildren().add(sortChoice);
            sortChoice.setPromptText(lastSortChoice);
            sortChoice.setLayoutY(10);
            sortChoice.setLayoutX(910);
            if(firstTime){
            sortChoice.getItems().addAll("priority from lowest to highest",
                    "priority from highest to lowest",
                    "date - newest first",
                    "date - oldest first");
            }
            sortChoice.setStyle("-fx-font: 15px \"Microsoft Sans Serif\";");


            sortChoice.setOnAction(e ->{
                sortingOrFilteringOrSearchingOrNormal = 1;
                String choice = sortChoice.getValue();
                lastSortChoice = choice;
                if(choice.equals("priority from lowest to highest")){
                    sortTrigger.set(1);
                    if(activePage.equals("Sent")){
                        sentLowerIndex = 0;
                        sentUpperIndex = 9;
                        sent_button.fire();
                    }
                    else if(activePage.equals("Inbox")){
                        inboxLowerIndex = 0;
                        inboxUpperIndex = 9;
                        inbox_button.fire();
                    }
                    else if(activePage.equals("Draft")){
                        draftLowerIndex = 0;
                        draftUpperIndex = 9;
                        draft_button.fire();
                    }
                    else if(activePage.equals("Trash")){
                        trashLowerIndex = 0;
                        trashUpperIndex = 9;
                        trash_button.fire();
                    }
                }
                else if(choice.equals("priority from highest to lowest")){
                    sortTrigger.set(2);
                    if(activePage.equals("Sent")){
                        sentLowerIndex = 0;
                        sentUpperIndex = 9;
                        sent_button.fire();
                    }
                    else if(activePage.equals("Inbox")){
                        inboxLowerIndex = 0;
                        inboxUpperIndex = 9;
                        inbox_button.fire();
                    }
                    else if(activePage.equals("Draft")){
                        draftLowerIndex = 0;
                        draftUpperIndex = 9;
                        draft_button.fire();
                    }
                    else if(activePage.equals("Trash")){
                        trashLowerIndex = 0;
                        trashUpperIndex = 9;
                        trash_button.fire();
                    }
                }
                else if(choice.equals("date - newest first")){
                    sortTrigger.set(3);
                    if(activePage.equals("Sent")){
                        sentLowerIndex = 0;
                        sentUpperIndex = 9;
                        sent_button.fire();
                    }
                    else if(activePage.equals("Inbox")){
                        inboxLowerIndex = 0;
                        inboxUpperIndex = 9;
                        inbox_button.fire();
                    }
                    else if(activePage.equals("Draft")){
                        draftLowerIndex = 0;
                        draftUpperIndex = 9;
                        draft_button.fire();
                    }
                    else if(activePage.equals("Trash")){
                        trashLowerIndex = 0;
                        trashUpperIndex = 9;
                        trash_button.fire();
                    }
                }
                else if(choice.equals("date - oldest first")){
                    sortTrigger.set(4);
                    if(activePage.equals("Sent")){
                        sentLowerIndex = 0;
                        sentUpperIndex = 9;
                        sent_button.fire();
                    }
                    else if(activePage.equals("Inbox")){
                        inboxLowerIndex = 0;
                        inboxUpperIndex = 9;
                        inbox_button.fire();
                    }
                    else if(activePage.equals("Draft")){
                        draftLowerIndex = 0;
                        draftUpperIndex = 9;
                        draft_button.fire();
                    }
                    else if(activePage.equals("Trash")){
                        trashLowerIndex = 0;
                        trashUpperIndex = 9;
                        trash_button.fire();
                    }
                }
            });

        ComboBox<String> filterChoice = new ComboBox<>();
        tempComboBox = filterChoice;
        filterChoice.setPrefHeight(40);
        filterChoice.setPrefWidth(230);
        filterChoice.setPromptText(lastFilterChoice);
        controlBar.getChildren().add(filterChoice);
        filterChoice.setLayoutY(10);
        filterChoice.setLayoutX(650);
        filterChoice.getItems().addAll(
                "Show all Emails",
                "Low - newest first",
                "Low - oldest first",
                "Medium - newest first",
                "Medium - oldest first",
                "High - newest first",
                "High - oldest first",
                "Urgent - newest first",
                "Urgent - oldest first"
        );
        filterChoice.setStyle("-fx-font: 15px \"Microsoft Sans Serif\";");

        filterChoice.setOnAction(e ->{
            sortingOrFilteringOrSearchingOrNormal = 2;
            String choice = filterChoice.getValue();
            lastFilterChoice = choice;
            if(choice.equals("Show all Emails")){
                sortingOrFilteringOrSearchingOrNormal = 0;
                sortChoice.setVisible(true);
                if(activePage.equals("Sent")){
                    sentLowerIndex = 0;
                    sentUpperIndex = 9;
                    sent_button.fire();
                }
                else if(activePage.equals("Inbox")){
                    inboxLowerIndex = 0;
                    inboxUpperIndex = 9;
                    inbox_button.fire();
                }
                else if(activePage.equals("Draft")){
                    draftLowerIndex = 0;
                    draftUpperIndex = 9;
                    draft_button.fire();
                }
                else if(activePage.equals("Trash")){
                    trashLowerIndex = 0;
                    trashUpperIndex = 9;
                    trash_button.fire();
                }
            }
            else if(choice.equals("Low - newest first")){
                filterTrigger.set(1);
                sortChoice.setVisible(false);
                if(activePage.equals("Sent")){
                    sentLowerIndex = 0;
                    sentUpperIndex = 9;
                    sent_button.fire();
                }
                else if(activePage.equals("Inbox")){
                    inboxLowerIndex = 0;
                    inboxUpperIndex = 9;
                    inbox_button.fire();
                }
                else if(activePage.equals("Draft")){
                    draftLowerIndex = 0;
                    draftUpperIndex = 9;
                    draft_button.fire();
                }
                else if(activePage.equals("Trash")){
                    trashLowerIndex = 0;
                    trashUpperIndex = 9;
                    trash_button.fire();
                }
            }
            else if(choice.equals("Low - oldest first")){
                filterTrigger.set(2);
                sortChoice.setVisible(false);
                if(activePage.equals("Sent")){
                    sentLowerIndex = 0;
                    sentUpperIndex = 9;
                    sent_button.fire();
                }
                else if(activePage.equals("Inbox")){
                    inboxLowerIndex = 0;
                    inboxUpperIndex = 9;
                    inbox_button.fire();
                }
                else if(activePage.equals("Draft")){
                    draftLowerIndex = 0;
                    draftUpperIndex = 9;
                    draft_button.fire();
                }
                else if(activePage.equals("Trash")){
                    trashLowerIndex = 0;
                    trashUpperIndex = 9;
                    trash_button.fire();
                }
            }
            else if(choice.equals("Medium - newest first")){
                filterTrigger.set(3);
                sortChoice.setVisible(false);
                if(activePage.equals("Sent")){
                    sentLowerIndex = 0;
                    sentUpperIndex = 9;
                    sent_button.fire();
                }
                else if(activePage.equals("Inbox")){
                    inboxLowerIndex = 0;
                    inboxUpperIndex = 9;
                    inbox_button.fire();
                }
                else if(activePage.equals("Draft")){
                    draftLowerIndex = 0;
                    draftUpperIndex = 9;
                    draft_button.fire();
                }
                else if(activePage.equals("Trash")){
                    trashLowerIndex = 0;
                    trashUpperIndex = 9;
                    trash_button.fire();
                }
            }
            else if(choice.equals("Medium - oldest first")){
                filterTrigger.set(4);
                sortChoice.setVisible(false);
                if(activePage.equals("Sent")){
                    sentLowerIndex = 0;
                    sentUpperIndex = 9;
                    sent_button.fire();
                }
                else if(activePage.equals("Inbox")){
                    inboxLowerIndex = 0;
                    inboxUpperIndex = 9;
                    inbox_button.fire();
                }
                else if(activePage.equals("Draft")){
                    draftLowerIndex = 0;
                    draftUpperIndex = 9;
                    draft_button.fire();
                }
                else if(activePage.equals("Trash")){
                    trashLowerIndex = 0;
                    trashUpperIndex = 9;
                    trash_button.fire();
                }
            }
            else if(choice.equals("High - newest first")){
                filterTrigger.set(5);
                sortChoice.setVisible(false);
                if(activePage.equals("Sent")){
                    sentLowerIndex = 0;
                    sentUpperIndex = 9;
                    sent_button.fire();
                }
                else if(activePage.equals("Inbox")){
                    inboxLowerIndex = 0;
                    inboxUpperIndex = 9;
                    inbox_button.fire();
                }
                else if(activePage.equals("Draft")){
                    draftLowerIndex = 0;
                    draftUpperIndex = 9;
                    draft_button.fire();
                }
                else if(activePage.equals("Trash")){
                    trashLowerIndex = 0;
                    trashUpperIndex = 9;
                    trash_button.fire();
                }
            }
            else if(choice.equals("High - oldest first")){
                filterTrigger.set(6);
                sortChoice.setVisible(false);
                if(activePage.equals("Sent")){
                    sentLowerIndex = 0;
                    sentUpperIndex = 9;
                    sent_button.fire();
                }
                else if(activePage.equals("Inbox")){
                    inboxLowerIndex = 0;
                    inboxUpperIndex = 9;
                    inbox_button.fire();
                }
                else if(activePage.equals("Draft")){
                    draftLowerIndex = 0;
                    draftUpperIndex = 9;
                    draft_button.fire();
                }
                else if(activePage.equals("Trash")){
                    trashLowerIndex = 0;
                    trashUpperIndex = 9;
                    trash_button.fire();
                }
            }
            else if(choice.equals("Urgent - newest first")){
                filterTrigger.set(7);
                sortChoice.setVisible(false);
                if(activePage.equals("Sent")){
                    sentLowerIndex = 0;
                    sentUpperIndex = 9;
                    sent_button.fire();
                }
                else if(activePage.equals("Inbox")){
                    inboxLowerIndex = 0;
                    inboxUpperIndex = 9;
                    inbox_button.fire();
                }
                else if(activePage.equals("Draft")){
                    draftLowerIndex = 0;
                    draftUpperIndex = 9;
                    draft_button.fire();
                }
                else if(activePage.equals("Trash")){
                    trashLowerIndex = 0;
                    trashUpperIndex = 9;
                    trash_button.fire();
                }
            }
            else if(choice.equals("Urgent - oldest first")){
                filterTrigger.set(8);
                sortChoice.setVisible(false);
                if(activePage.equals("Sent")){
                    sentLowerIndex = 0;
                    sentUpperIndex = 9;
                    sent_button.fire();
                }
                else if(activePage.equals("Inbox")){
                    inboxLowerIndex = 0;
                    inboxUpperIndex = 9;
                    inbox_button.fire();
                }
                else if(activePage.equals("Draft")){
                    draftLowerIndex = 0;
                    draftUpperIndex = 9;
                    draft_button.fire();
                }
                else if(activePage.equals("Trash")){
                    trashLowerIndex = 0;
                    trashUpperIndex = 9;
                    trash_button.fire();
                }
            }
        });
    }

    public void addContactSortFeature(){
        ComboBox<String> sortChoice = new ComboBox<>();
        sortChoice.setPrefHeight(40);
        sortChoice.setPrefWidth(230);
        sortChoice.setPromptText("A To Z");
        controlBar.getChildren().add(sortChoice);
        sortChoice.setLayoutY(10);
        sortChoice.setLayoutX(910);
        sortChoice.getItems().addAll("A to Z", "Z to A");
        sortChoice.setStyle("-fx-font: 15px \"Microsoft Sans Serif\";");
        sortChoice.setOnAction(e ->{
            String choice = sortChoice.getValue();
            String[] sortedContacts = null;
            Sort process = new Sort();
            if(choice.equals("A to Z")){
                sortedContacts = process.ZToAContacts(loggedInEmail);
            }
            else if(choice.equals("Z to A")){
                sortedContacts = process.AToZContacts(loggedInEmail);
            }
            constructContacts(sortedContacts);
        });
    }

    @FXML
    private void showEmailButtonHandle(ActionEvent event){
        if(event.getSource() == showEmailCancelButton){
            showEmail_pane.toBack();
            senderLabel.setText("");
            subjectLabel.setText("");
            showEmailBodyText.clear();
        }
        else if(event.getSource() == showEmailDeleteButton){
            Pane confirmationMessage = new Pane();
            MainRightSide.getChildren().add(confirmationMessage);
            confirmationMessage.toFront();
            confirmationMessage.setPrefHeight(150);
            confirmationMessage.setPrefWidth(350);
            confirmationMessage.setLayoutX(445);
            confirmationMessage.setLayoutY(300);
            confirmationMessage.setStyle("-fx-border-width: 1;" +
                    "    -fx-border-radius: 2em;" +
                    "    -fx-border-color: #ffffff;" +
                    "    -fx-effect: dropshadow(three-pass-box, #eaeaea, 5, 0, 5, 8);" +
                    "    -fx-background-color: #ffffff");
            Label message = new Label();
            message.setText("Sure to delete Email?");
            message.setFont(Font.font("Microsoft Sans Serif",22 ));
            message.setAlignment(Pos.CENTER);
            message.setPrefWidth(300);
            message.setPrefHeight(40);
            confirmationMessage.getChildren().add(message);
            message.setLayoutX(25);
            message.setLayoutY(20);
            Button confirm = new Button();
            confirm.setText("Delete");
            confirm.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            confirm.setFont(Font.font("Microsoft Sans Serif",18 ));
            confirm.setPrefHeight(50);
            confirm.setPrefWidth(100);
            confirmationMessage.getChildren().add(confirm);
            confirm.setLayoutY(80);
            confirm.setLayoutX(200);
            confirm.setOnMouseEntered(y ->{
                confirm.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            confirm.setOnMouseExited(y ->{
                confirm.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            confirm.setOnMouseClicked(y ->{
                MainRightSide.getChildren().remove(confirmationMessage);
                EmailFunctions deleteProcess = new EmailFunctions();
                deleteProcess.deleteEmail(loggedInEmail, activePage, currentViewedEmailID);
                if(activePage.equals("Sent")) sent_button.fire();
                else if(activePage.equals("Inbox")) inbox_button.fire();
                else if(activePage.equals("Draft")) draft_button.fire();
                else if(activePage.equals("Trash")) trash_button.fire();
            });

            Button cancel = new Button();
            cancel.setText("cancel");
            cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            cancel.setFont(Font.font("Microsoft Sans Serif",18 ));
            cancel.setPrefHeight(50);
            cancel.setPrefWidth(100);
            confirmationMessage.getChildren().add(cancel);
            cancel.setLayoutY(80);
            cancel.setLayoutX(50);
            cancel.setOnMouseEntered(y ->{
                cancel.setStyle("-fx-background-color: #f35b5b; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            cancel.setOnMouseExited(y ->{
                cancel.setStyle("-fx-background-color: indianred; -fx-text-fill: white; -fx-border-radius: 3;");
            });
            cancel.setOnMouseClicked(y ->{
                MainRightSide.getChildren().remove(confirmationMessage);
            });
        }
    }

    @FXML
    private void searchBarHandle(ActionEvent event){
        if(event.getSource() == searchButton){
            String searchText = searchBar.getText();
            if(searchText.length() > 0){
                sortingOrFilteringOrSearchingOrNormal = 3;
                if(activePage.equals("Sent")) sent_button.fire();
                else if(activePage.equals("Inbox")) inbox_button.fire();
                else if(activePage.equals("Draft")) draft_button.fire();
                else if(activePage.equals("Trash")) trash_button.fire();
                else if(activePage.equals("Contacts")){
                    Search searchingProcess = new Search();
                    Contact contact = new Contact();
                    String[] contactNames = contact.getAllContacts(loggedInEmail);
                    boolean found = searchingProcess.searchContacts(contactNames, searchText);
                    if(found){
                        contactNames = new String[]{searchText};
                        addContactExitSearchButton();
                    }else{
                        contactNames = new String[]{};
                        contactsList.getChildren().remove(searchMessage);
                        searchMessage.setPrefWidth(500);
                        searchMessage.setPrefHeight(60);
                        contactsList.getChildren().add(searchMessage);
                        searchMessage.setLayoutX(450);
                        searchMessage.setLayoutY(238);
                        searchMessage.setStyle("-fx-background-color: 0; -fx-text-fill: grey");
                        searchMessage.setFont(Font.font("Microsoft Sans Serif", 22));
                        searchMessage.setText("No Matching Search Results");
                        addContactExitSearchButton();
                    }
                    constructContacts(contactNames);
                }
                if(!activePage.equals("Contacts")){
                    showNextPage.setVisible(false);
                    showPrevPage.setVisible(false);
                }
            }
        }
    }

    private void addExitSearchButton(){
        Button exitSearch = new Button();
        exitSearch.setText("Exit Search");
        exitSearch.setPrefHeight(40);
        exitSearch.setPrefWidth(150);
        show_pane.getChildren().add(exitSearch);
        exitSearch.setLayoutX(700);
        exitSearch.setLayoutY(20);
        exitSearch.setStyle("-fx-background-color: 0; -fx-underline: true; -fx-text-fill: #f35b5b");
        exitSearch.setFont(Font.font("Microsoft Sans Serif", 18));
        exitSearch.setOnMouseClicked(e->{
            sortingOrFilteringOrSearchingOrNormal = 0;
            show_pane.getChildren().remove(exitSearch);
            showEmailsList.getChildren().remove(searchMessage);
            searchBar.clear();
            showNextPage.setVisible(true);
            showPrevPage.setVisible(true);
            if(activePage.equals("Sent")) sent_button.fire();
            else if(activePage.equals("Inbox")) inbox_button.fire();
            else if(activePage.equals("Draft")) draft_button.fire();
            else if(activePage.equals("Trash")) trash_button.fire();
        });
    }

    private void addContactExitSearchButton(){
        Button exitSearch = new Button();
        exitSearch.setText("Exit Search");
        exitSearch.setPrefHeight(40);
        exitSearch.setPrefWidth(150);
        contacts_pane.getChildren().add(exitSearch);
        exitSearch.setLayoutX(700);
        exitSearch.setLayoutY(20);
        exitSearch.setStyle("-fx-background-color: 0; -fx-underline: true; -fx-text-fill: #f35b5b");
        exitSearch.setFont(Font.font("Microsoft Sans Serif", 18));
        exitSearch.setOnMouseClicked(e->{
            sortingOrFilteringOrSearchingOrNormal = 0;
            contacts_pane.getChildren().remove(exitSearch);
            contactsList.getChildren().remove(searchMessage);
            searchBar.clear();
            contacts_button.fire();
        });
    }

    @FXML
    private void settingsMenuHandle(ActionEvent event){
        if(event.getSource() == settings_button) {
            if (settingsToggle == 0) {
                settingsToggle = 1;
                settingsMenu.toFront();
                logOutButton.setOnAction(e -> {
                    reset();
                    login_pane.toFront();
                });
                createAccountButton.setOnAction(e -> {
                    reset();
                    signup_pane.toFront();
                });
            } else if (settingsToggle == 1) {
                settingsToggle = 0;
                settingsMenu.toBack();
            }
        }
    }

    private void reset(){
        //current Email
        loggedInEmail = "";
        //Adding contact popUp
        TextFieldYLayout = 10;
        extraPaneH = 70;
        buttonYLayout = 15;
        TextFieldIds.clear();
        buttonsIds.clear();
        idCount = 1;
        count = 0;
        //contact page
        checkBoxes.clear();
        selectedCheckBoxes.clear();
        selectedContacts.clear();
        AllContactsCards.clear();
        AllContactsButtons.clear();
        //Sent page
        sentLowerIndex = 0;
        sentUpperIndex = 9;
        //Draft page
        draftLowerIndex = 0;
        draftUpperIndex = 9;
        //Inbox page
        inboxLowerIndex = 0;
        inboxUpperIndex = 9;
        //Trash page
        trashLowerIndex = 0;
        trashUpperIndex = 9;
        //showContactsPage
        AllTextFields.clear();
        AllTextFieldsCheckBoxes.clear();
        AllEmailCards.clear();
        selectedContactEmails.clear();
        //setup
        oldData.clear();
        editOrSave = 0; //0 for edit and 1 for save
        activePage = "Inbox";
        sortTrigger = new AtomicInteger(1);
        filterTrigger = new AtomicInteger(1);
        sortingOrFilteringOrSearchingOrNormal = 0;//0--Normal, 1--Sorting, 2--filtering, 3--Searching
        firstTime = true;
        lastSortChoice = "oldest first";
        lastFilterChoice = "Show all";
        currentViewedEmailID = "";
        settingsToggle = 0; //0 for showing the menu, 1 for hiding the menu
        //composeHelper
        AllCards.clear();
        AlldropDownInfo.clear();
        AllReceiversCards.clear();
        AllReceiversEmails.clear();
        //attachmenats
        AtomicReference<String> emailPriority = new AtomicReference<>("none");
        allAttachmentsPaths.clear();
        allAttachmentsNames.clear();
        allAttachmentsCards.clear();
        //Showing Pane
        showPageCheckBoxes.clear();
        showPageSelectedCheckBoxes.clear();
        showPageSelectedEmailsIDs.clear();
        showPageAllEmailsCards.clear();
        showPageAllEmailsButtons.clear();
        emailsList = null;
    }

    @FXML
    private void reloadHandle(ActionEvent event){
        if(event.getSource() == reloadButton){
            sortingOrFilteringOrSearchingOrNormal = 0;
            if(activePage.equals("Sent")) sent_button.fire();
            else if(activePage.equals("Inbox")) inbox_button.fire();
            else if(activePage.equals("Draft")) draft_button.fire();
            else if(activePage.equals("Trash")) trash_button.fire();
            else if(activePage.equals("Contacts")) contacts_button.fire();
        }
    }
}

