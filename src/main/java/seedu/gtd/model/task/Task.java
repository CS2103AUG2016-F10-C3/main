package seedu.gtd.model.task;

import java.util.Objects;

import seedu.gtd.commons.exceptions.IllegalValueException;
import seedu.gtd.commons.util.CollectionUtil;
import seedu.gtd.model.task.ReadOnlyTask;
import seedu.gtd.model.tag.UniqueTagList;

/**
 * Represents a Task in the task list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private DueDate dueDate;
    private DueDate startDate;
    private Address address;
    private Priority priority;
    private boolean isDone;
    private boolean isRecur;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, DueDate startDate, DueDate dueDate, Address address, Priority priority, UniqueTagList tags, boolean isDone) {
        assert !CollectionUtil.isAnyNull(name, dueDate, address, priority, tags);
        this.name = name;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.address = address;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isDone = isDone;
    }
    
    public Task(Name name, DueDate startDate, DueDate dueDate, Address address, Priority priority, boolean isRecur, UniqueTagList tags, boolean isDone) {
        assert !CollectionUtil.isAnyNull(name, dueDate, address, priority, tags);
        this.name = name;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.address = address;
        this.priority = priority;
        this.isRecur = isRecur;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isDone = isDone;
    }
    
    public Task(Name name, DueDate startDate, DueDate dueDate, Address address, Priority priority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, dueDate, address, priority, tags);
        this.name = name;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.address = address;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isDone = false;
    }
    
    public Task(Name name, DueDate startDate, DueDate dueDate, Address address, Priority priority, boolean isRecur, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, dueDate, address, priority, tags);
        this.name = name;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.address = address;
        this.priority = priority;
        this.isRecur = isRecur;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isDone = false;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getDueDate(), source.getAddress(), source.getPriority(), source.getisRecur(), source.getTags(), source.getisDone());
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public DueDate getStartDate() {
        return startDate;
    }

    @Override
    public DueDate getDueDate() {
        return dueDate;
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
    public boolean getisDone() {
        return isDone;
    }
    
    @Override
    public boolean getisRecur() {
        return isRecur;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStartDate(DueDate startDate) {
        this.startDate = startDate;
    }
    
    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public void setisDone(boolean isdone) {
        this.isDone = isdone;
    }
    
    public void setisRecur(boolean isrecur) {
        this.isRecur= isrecur;
    }
    
    public void edit(String detailType, String newDetail) throws IllegalValueException {
    	
    	switch(detailType) {
    	case "startDate": setStartDate(new DueDate(newDetail)); break;
    	case "dueDate": setDueDate(new DueDate(newDetail)); break;
    	case "address": setAddress(new Address(newDetail)); break;
    	case "priority": setPriority(new Priority(newDetail)); break;
    	case "done": setisDone(true); break;
    	default: setName(new Name(newDetail));
    	}
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
