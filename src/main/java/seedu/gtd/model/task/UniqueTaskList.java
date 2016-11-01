package seedu.gtd.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.gtd.commons.exceptions.DuplicateDataException;
import seedu.gtd.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {
	//@@author addressbook-level4
    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }
    
    //@@author A0146130W
    /**
     * Edits an equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public void edit(int targetIndex, Task toEdit) throws TaskNotFoundException {
        assert toEdit != null;
        if (invalidIndex(targetIndex)) {
            throw new TaskNotFoundException();
        }
        internalList.set(targetIndex, toEdit);
    }
    
    public void done(int targetIndex, Task taskdone) throws TaskNotFoundException {
    	System.out.println("in uniquetasklist");
    	System.out.println(taskdone.getName() + " " + taskdone.getisDone());
    	System.out.println("index at final:" + targetIndex);
    	assert taskdone != null;
        if (invalidIndex(targetIndex)) {
            throw new TaskNotFoundException();
        }
        System.out.println("marked done in model");
        internalList.set(targetIndex, taskdone);  
    }
    
    private boolean invalidIndex(int i) {
    	if(i < 0 || i >= internalList.size()) return true;
    	return false;
    }
    
    //@@author addressbook-level4
    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
