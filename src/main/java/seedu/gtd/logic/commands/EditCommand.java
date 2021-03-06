package seedu.gtd.logic.commands;

import java.util.Hashtable;
import java.util.Set;

import seedu.gtd.commons.core.Messages;
import seedu.gtd.commons.core.UnmodifiableObservableList;
import seedu.gtd.commons.exceptions.IllegalValueException;
import seedu.gtd.model.task.ReadOnlyTask;
import seedu.gtd.model.task.Task;
import seedu.gtd.model.task.UniqueTaskList.TaskNotFoundException;
 
//@@author A0146130W
 /**
  * Adds a task to the address book.
  */
 public class EditCommand extends Command {
 
     public static final String COMMAND_WORD = "edit";
 
     public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
             + "Edits the task identified by the index number used in the last task listing.\n\t"
             + "Parameters: [INDEX] (must be a positive integer) prefix/[NEW DETAIL]\n\t"
             + "Example: " + COMMAND_WORD
             + " 1 p/3";
 
     public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task updated: %1$s";
     
     private int targetIndex;
     private Hashtable<String, String> newDetails;
 
     public EditCommand(int targetIndex, Hashtable<String, String> newDetails) {
         this.targetIndex = targetIndex;
         this.newDetails = newDetails;
     }
     
 
     @Override
     public CommandResult execute() {

         UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

         if (lastShownList.size() < targetIndex) {
             indicateAttemptToExecuteIncorrectCommand();
             return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
         }
         
        ReadOnlyTask toEdit = lastShownList.get(targetIndex);
        Task taskToUpdate = new Task(toEdit);
        
        assert model != null;
		try {
			Set<String> detailTypes = newDetails.keySet();
			for(String detailType : detailTypes) {
				System.out.println("from edit command: " + detailType + " " + newDetails.get(detailType));
			    taskToUpdate = updateTask(taskToUpdate, detailType, newDetails.get(detailType));
			    model.editTask(targetIndex, taskToUpdate);
			}
		} catch (IllegalValueException ive) {
			return new CommandResult(ive.getMessage());
		} catch (TaskNotFoundException e) {
			assert false : "The target task cannot be missing";
		}
         return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToUpdate));
     }
     
     private Task updateTask(Task taskToUpdate, String detailType, String newDetail) throws IllegalValueException {
    	 taskToUpdate.edit(detailType, newDetail);
    	 return taskToUpdate;
     }
 
 }
