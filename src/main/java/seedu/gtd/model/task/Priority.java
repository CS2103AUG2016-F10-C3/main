package seedu.gtd.model.task;

import seedu.gtd.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final Integer PRIORITY_LOW = 1;
    public static final Integer PRIORITY_HIGH = 5;
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Invalid value: Task priority set to " + PRIORITY_LOW;
    public static final String PRIORITY_VALIDATION_REGEX = "[1-5]{1}";
    
    public final String value;

    /**
     * Validates given priority number.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        //assert priority != null;
    	if(priority!=null){
	        priority = priority.trim();
	        if (!isValidPriority(priority)){
	        	//TODO: Find a way to print message to UI that priority has been autoset
	        	this.value = returnDefaultPriority();
	        }else{
	        	this.value = priority;
	        }
    	}else{
    		this.value = returnDefaultPriority();
    	}
    }

    /**
     * Returns true if a given string is a valid task priority number.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    /**
     * Returns true if the entered priority is within the defined range.
     * From PRIORITY_LOW to PRIORITY_HIGH inclusive.
     */
    public static boolean isPriorityInRange(Integer test) {
    	return (test >= PRIORITY_LOW && test <= PRIORITY_HIGH);
    }

    /**
     * Returns a String of the default priority to set to if the entered priority is invalid.
     */
    public static String returnDefaultPriority(){
    	return PRIORITY_LOW.toString();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}