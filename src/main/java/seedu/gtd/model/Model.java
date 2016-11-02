package seedu.gtd.model;

import java.util.Set;

import seedu.gtd.commons.core.UnmodifiableObservableList;
import seedu.gtd.model.task.ReadOnlyTask;
import seedu.gtd.model.task.Task;
import seedu.gtd.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Marks the given task as done. */
    void doneTask(int targetIndex, Task target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Adds the given task */
    void editTask(int targetIndex, Task task) throws UniqueTaskList.TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowRemoved();

    /** Updates the filter of the filtered task list to filter by the given exact phrase
     * @param keywordSet */
    void updateFilteredTaskList(String keywords, Set<String> keywordSet);
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
	void updateFilteredTaskList(Set<String> keywordSet);
	
	/** Updates the filter of the filtered task list to filter by the right parameter*/
	void updateFilteredTaskList(String keywords, String cmd);
	/** Returns only the tasks that are yet to be done */
	UnmodifiableObservableList<ReadOnlyTask> getStartTaskList();
}
