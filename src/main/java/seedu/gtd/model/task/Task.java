package seedu.gtd.model.task;

import java.util.Objects;

import seedu.gtd.commons.util.CollectionUtil;
import seedu.gtd.model.task.ReadOnlyTask;
import seedu.gtd.model.tag.UniqueTagList;

/**
 * Represents a Task in the task list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private StartDate startDate;
    private DueDate dueDate;
    private Address address;
    private Priority priority;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
<<<<<<< HEAD
    public Task(Name name, StartDate startDate, DueDate dueDate, Address address, Priority priority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, startDate, dueDate, address, priority, tags);
=======
    public Task(Name name, DueDate dueDate, Address address, Priority priority, UniqueTagList tags) {
        //dueDate and address can be blank
    	//name is necessary
    	//priority will be automatically set and taglist is already handled
    	assert !CollectionUtil.isAnyNull(name, priority, tags);
>>>>>>> origin/C3/support/use-model.task
        this.name = name;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.address = address;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getDueDate(), source.getAddress(), source.getPriority(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public DueDate getDueDate() {
        return dueDate;
    }
    
    @Override
    public StartDate getStartDate() {
        return startDate;
    }
    
    @Override
    public Address getAddress() {
        return address;
    }
    
    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, dueDate, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
}