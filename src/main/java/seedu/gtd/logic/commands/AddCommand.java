package seedu.gtd.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.gtd.commons.exceptions.IllegalValueException;
import seedu.gtd.logic.parser.DateNaturalLanguageProcessor;
import seedu.gtd.logic.parser.NaturalLanguageProcessor;
import seedu.gtd.model.task.*;
import seedu.gtd.model.tag.Tag;
import seedu.gtd.model.tag.UniqueTagList;

/**
 * Adds a task to the task list.
 */
//@@author A0130677A
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the GTD. "
            + "Parameters: NAME [s/STARTDATE d/DUEDATE a/ADDRESS p/PRIORITY t/TAGS]...\n"
    		+ "All parameters except name is optional.\n"
            + "Example: " + COMMAND_WORD
            + " Do CS2103 Tutorial 4 d/noon a/NUS p/3 t/CS2103 tutorial fun";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startDate, String dueDate, String address, String priority, Boolean recurring, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        
        this.toAdd = new Task(
                new Name(name),
                new DueDate(startDate),
                new DueDate(dueDate),
                new Address(address),
                new Priority(priority),
                recurring,
                new UniqueTagList(tagSet)
        );
    }
    
    //@@author addressbook-level4
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
