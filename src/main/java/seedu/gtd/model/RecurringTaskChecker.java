package seedu.gtd.model;

import java.util.Date;

import seedu.gtd.logic.parser.DateNaturalLanguageProcessor;
import seedu.gtd.logic.parser.NaturalLanguageProcessor;
import seedu.gtd.model.task.Task;

/**
 * Checks recurring tasks and their duedate before undoneing them.
 */
//@@author A0139158X
public class RecurringTaskChecker implements Runnable{
	
	private AddressBook addressbook;
	private Date adate = new Date();
	private NaturalLanguageProcessor nlp = new DateNaturalLanguageProcessor();
	
	public RecurringTaskChecker(AddressBook addressbook) {
		this.addressbook = addressbook;
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		for(Task t: addressbook.getUniqueTaskList()) {
			if (t.getisRecur() && !adate.equals(nlp.getDate(t.getDueDate().toString())) 
			&& adate.getDay()==nlp.getDate(t.getDueDate().toString()).getDay()) {
				t.setisDone(false);
			}
			
		}
	}
}