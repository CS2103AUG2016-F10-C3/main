package seedu.gtd.logic.commands;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the Task List.";
    public static final String MESSAGE_SUCCESS = "Task List has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.clearTaskList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
