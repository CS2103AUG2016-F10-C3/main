package seedu.gtd.model.task;

import seedu.gtd.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS = "Task startdate numbers should only contain numbers";
    //public static final String STARTDATE_VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)"; //dd-mm-yyyy
    public static final String STARTDATE_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given start date string is invalid.
     */
    public StartDate(String startdate) throws IllegalValueException {
        assert startdate != null;
        startdate = startdate.trim();
        if (!isValidDueDate(startdate)) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        }
        this.value = startdate;
    }

    /**
     * Returns true if a given string is a valid task due date number.
     */
    public static boolean isValidStartDate(String test) {
        return test.matches(STARTDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.value.equals(((StartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
