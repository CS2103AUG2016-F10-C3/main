package seedu.gtd.model;

import javafx.collections.transformation.FilteredList;
import seedu.gtd.commons.core.ComponentManager;
import seedu.gtd.commons.core.LogsCenter;
import seedu.gtd.commons.core.UnmodifiableObservableList;
import seedu.gtd.commons.events.model.AddressBookChangedEvent;
import seedu.gtd.commons.util.StringUtil;
import seedu.gtd.model.task.Task;
import seedu.gtd.model.task.ReadOnlyTask;
import seedu.gtd.model.task.UniqueTaskList;
import seedu.gtd.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
	//@@author addressbook-level4
	
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private AddressBook addressBook;
    private final FilteredList<Task> filteredTasks;
    private AddressBook previousAddressBook;
    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(AddressBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new AddressBook(src);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
        previousAddressBook = new AddressBook(addressBook);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyAddressBook initialData, UserPrefs userPrefs) {
        addressBook = new AddressBook(initialData);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
        previousAddressBook = addressBook;
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }
    
    private void savePreviousAddressBook() {
    	previousAddressBook = new AddressBook(addressBook);
    }
    
    @Override
    public void undoAddressBookChange() {
    	resetData(previousAddressBook);
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
    	savePreviousAddressBook();
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
    	savePreviousAddressBook();
        addressBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    //@@author A0146130W
    @Override
    public synchronized void editTask(int targetIndex, Task task) throws TaskNotFoundException {
    	savePreviousAddressBook();
        addressBook.editTask(targetIndex, task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    //@@author addressbook-level4
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(String keywords, Set<String> keywordSet){
        updateFilteredTaskList(new PredicateExpression(new orderedNameQualifier(keywords, keywordSet)));
    }
    
    @Override
    public void updateFilteredTaskList(Set<String> keywordSet) {
    	updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywordSet)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }
    
    private class NameQualifier implements Qualifier {
        protected Set<String> keywordSet;

        NameQualifier(Set<String> keywordSet) {
            this.keywordSet = keywordSet;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return keywordSet.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", keywordSet);
        }
    }
    
    private class orderedNameQualifier extends NameQualifier implements Qualifier {
		private String nameKeyWords;

        orderedNameQualifier(String keywords, Set<String> keywordSet) {
        	super(keywordSet);
            this.nameKeyWords = keywords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	String taskFullNameLowerCase = task.getName().fullName.toLowerCase();
        	boolean orderMatch = taskFullNameLowerCase.contains(nameKeyWords.toLowerCase());
        	
        	boolean eachWordMatch = keywordSet.stream()
            .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
            .findAny()
            .isPresent();
        	
            return eachWordMatch && orderMatch;
        }
    }
}
