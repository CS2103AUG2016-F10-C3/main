package seedu.gtd.logic;

import javafx.collections.ObservableList;
import seedu.gtd.logic.commands.CommandResult;
import seedu.gtd.model.task.ReadOnlyTask;

//@@author A0146130W
/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
