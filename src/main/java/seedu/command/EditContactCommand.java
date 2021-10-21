package seedu.command;

import seedu.contact.Contact;
import seedu.contact.ContactList;
import seedu.exception.InvalidFlagException;
import seedu.ui.TextUi;
import seedu.ui.ExceptionTextUi;
import seedu.ui.UserInputTextUi;

import java.util.ArrayList;

public class EditContactCommand extends Command {
    public static final int NAME_INDEX = 0;
    public static final int GITHUB_INDEX = 1;
    public static final int LINKEDIN_INDEX = 2;
    public static final int TELEGRAM_INDEX = 3;
    public static final int TWITTER_INDEX = 4;
    public static final int EMAIL_INDEX = 5;
    public static final int NUMBER_OF_FIELDS = 6;
    String[] contactDetails;
    int contactIndex;

    public EditContactCommand(String[] contactDetails, int contactIndex) {
        this.contactDetails = contactDetails;
        this.contactIndex = contactIndex;
    }

    public void execute() {
        try {
            Contact preEditContact = duplicateContact(contactList.getContactAtIndex(contactIndex));
            Contact postEditContact = editTempContact(contactDetails,preEditContact);
            if (hasDuplicates(postEditContact, contactList, contactIndex)) {
                TextUi.ignoreContact("edit");
            } else {
                contactList.editContact(contactDetails, contactIndex);
                TextUi.editContactMessage(postEditContact);
            }
        } catch (IndexOutOfBoundsException | InvalidFlagException e) {
            ExceptionTextUi.invalidIndexMessage();
        }
    }

    public Boolean duplicateCatcher(Contact postEditContact, ContactList contactList, int contactIndex) {
        ArrayList<Integer> duplicatedIndex = new ArrayList<>();
        for (int i = 0; i < contactList.getListSize(); i++) {
            if (i == contactIndex) {
                continue;
            }
            Contact currentContact = contactList.getContactAtIndex(i);
            if (duplicateField(postEditContact.getName(), currentContact.getName())) {
                duplicatedIndex.add(i);
                continue;
            }
            if (postEditContact.getGithub() != null && currentContact.getGithub() != null) {
                if (duplicateField(postEditContact.getGithub(), currentContact.getGithub())) {
                    duplicatedIndex.add(i);
                    continue;
                }
            }
            if (postEditContact.getEmail() != null && currentContact.getEmail() != null) {
                if (duplicateField(postEditContact.getEmail(), currentContact.getEmail())) {
                    duplicatedIndex.add(i);
                    continue;
                }
            }
            if (postEditContact.getTelegram() != null && currentContact.getTelegram() != null) {
                if (duplicateField(postEditContact.getTelegram(), currentContact.getTelegram())) {
                    duplicatedIndex.add(i);
                    continue;
                }
            }
            if (postEditContact.getLinkedin() != null && currentContact.getLinkedin() != null) {
                if (duplicateField(postEditContact.getLinkedin(), currentContact.getLinkedin())) {
                    duplicatedIndex.add(i);
                    continue;
                }
            }
            if (postEditContact.getTwitter() != null && currentContact.getTwitter() != null) {
                if (duplicateField(postEditContact.getTwitter(), currentContact.getTwitter())) {
                    duplicatedIndex.add(i);
                }
            }
        }
        if (!duplicatedIndex.isEmpty()) {
            TextUi.confirmEditDuplicateMessage(duplicatedIndex, contactList);
            String userEditConfirmation = UserInputTextUi.getUserConfirmation();
            return userEditConfirmation.equalsIgnoreCase("n");
        }
        return false;
    }

    public Contact duplicateContact(Contact contact) {
        String name = contact.getName();
        String github = contact.getGithub();
        String linkedin = contact.getLinkedin();
        String telegram = contact.getTelegram();
        String twitter = contact.getTwitter();
        String email = contact.getEmail();
        return new Contact(name, github, linkedin, telegram, twitter, email);
    }

    private Boolean hasDuplicateField(String input, String saved) {
        return stringCleaner(saved).equals(stringCleaner(input));
    }

    private String stringCleaner(String input) {
        return input.replace(" ", "").toLowerCase();
    }


    private Contact editTempContact(String[] contactDetails, Contact preEditContact) throws InvalidFlagException {
        Contact tempContact = duplicateContact(preEditContact);
        for (int i = 0; i < contactDetails.length; i++) {
            if (contactDetails[i] != null) {
                switch (i) {
                case NAME_INDEX:
                    tempContact.setName(contactDetails[i]);
                    break;
                case GITHUB_INDEX:
                    tempContact.setGithub(contactDetails[i]);
                    break;
                case LINKEDIN_INDEX:
                    tempContact.setLinkedin(contactDetails[i]);
                    break;
                case TELEGRAM_INDEX:
                    tempContact.setTelegram(contactDetails[i]);
                    break;
                case TWITTER_INDEX:
                    tempContact.setTwitter(contactDetails[i]);
                    break;
                case EMAIL_INDEX:
                    tempContact.setEmail(contactDetails[i]);
                    break;
                default:
                    assert false;
                    throw new InvalidFlagException();
                }
            }
        }
        return tempContact;
    }

    private String[] extractContactDetails(Contact contact) throws InvalidFlagException {
        String[] contactDetails = new String[NUMBER_OF_FIELDS];
        for (int i = 0; i < NUMBER_OF_FIELDS; i++) {
            switch (i) {
            case NAME_INDEX:
                contactDetails[NAME_INDEX] = contact.getName();
                break;
            case GITHUB_INDEX:
                contactDetails[GITHUB_INDEX] = contact.getGithub();
                break;
            case LINKEDIN_INDEX:
                contactDetails[LINKEDIN_INDEX] = contact.getLinkedin();
                break;
            case TELEGRAM_INDEX:
                contactDetails[TELEGRAM_INDEX] = contact.getTelegram();
                break;
            case TWITTER_INDEX:
                contactDetails[TWITTER_INDEX] = contact.getTwitter();
                break;
            case EMAIL_INDEX:
                contactDetails[EMAIL_INDEX] = contact.getEmail();
                break;
            default:
                assert false;
                throw new InvalidFlagException();
            }
        }
        return contactDetails;
    }
}
