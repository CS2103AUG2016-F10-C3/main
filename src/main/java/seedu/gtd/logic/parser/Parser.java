package seedu.gtd.logic.parser;

import static seedu.gtd.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.gtd.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.gtd.commons.exceptions.IllegalValueException;
import seedu.gtd.commons.util.StringUtil;
import seedu.gtd.logic.commands.*;

/**
 * Parses user input.
 */
public class Parser {
	//@@author addressbook-level4
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

//    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
//            Pattern.compile("(?<name>[^/]+)"
//                    + " d/(?<dueDate>[^/]+)"
//                    + " a/(?<address>[^/]+)"
//                    + " p/(?<priority>[^/]+)"
//                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    private static final Pattern NAME_TASK_DATA_ARGS_FORMAT =
            Pattern.compile("(?<name>[^/]+) (t|p|a|d|e)/.*");
    
    private static final Pattern PRIORITY_TASK_DATA_ARGS_FORMAT =
            Pattern.compile(".* p/(?<priority>[^/]+) (t|a|d|e)/.*");
    
    private static final Pattern ADDRESS_TASK_DATA_ARGS_FORMAT =
            Pattern.compile(".* a/(?<address>[^/]+) (t|p|d|e)/.*");
    
    private static final Pattern DUEDATE_TASK_DATA_ARGS_FORMAT =
            Pattern.compile(".* d/(?<dueDate>[^/]+) (t|a|p|e)/.*");
    
    private static final Pattern TAGS_TASK_DATA_ARGS_FORMAT =
            Pattern.compile(".* t/(?<tagArguments>[^/]+) (d|a|p|e)/.*");
    
    private static final Pattern EDIT_DATA_ARGS_FORMAT =
    		Pattern.compile("(?<targetIndex>\\S+)" 
                    + " (?<newDetail>.*)");

    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);
            
        case EditCommand.COMMAND_WORD:
        	return prepareEdit(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        	return prepareHelp(arguments);
        
        case UndoCommand.COMMAND_WORD:
        	return new UndoCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

	/**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
    	String preprocessedArg = appendEnd(args.trim());
    	
        final Matcher nameMatcher = NAME_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArg);
        final Matcher dueDateMatcher = DUEDATE_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArg);
        final Matcher addressMatcher = ADDRESS_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArg);
        final Matcher priorityMatcher = PRIORITY_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArg);
        final Matcher tagsMatcher = TAGS_TASK_DATA_ARGS_FORMAT.matcher(preprocessedArg);
        
        String nameToAdd = checkEmptyAndAddDefault(nameMatcher, "name", "none");
        String dueDateToAdd = checkEmptyAndAddDefault(dueDateMatcher, "dueDate", "none");
        String addressToAdd = checkEmptyAndAddDefault(addressMatcher, "address", "none");
        String priorityToAdd = checkEmptyAndAddDefault(priorityMatcher, "priority", "1");
//        String tagsToAdd = checkEmptyAndAddDefault(tagsMatcher, "tagsArgument", "");
        
        // format date if due date is specified
        if (dueDateMatcher.matches()) {
        	dueDateToAdd = parseDueDate(dueDateToAdd);
        }
        
        Set<String> tagsProcessed = Collections.emptySet();
        
        if (tagsMatcher.matches()) {
        	tagsProcessed = getTagsFromArgs(tagsMatcher.group("tagArguments"));
        }
        
        // Validate arg string format
        if (!nameMatcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        try {
            return new AddCommand(
                    nameToAdd,
                    dueDateToAdd,
                    addressToAdd,
                    priorityToAdd,
                    tagsProcessed
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private String appendEnd(String args) {
    	return args + " e/";
    }
    
    private String checkEmptyAndAddDefault(Matcher matcher, String groupName, String defaultValue) {
    	if (matcher.matches()) {
    		return matcher.group(groupName);
    	} else {
    		return defaultValue;
    	}
    }
    
    //@@author A0146130W
    private String parseDueDate(String dueDateRaw) {
    	NaturalLanguageProcessor nlp = new DateNaturalLanguageProcessor();
    	return nlp.formatString(dueDateRaw);
    }
    
    //@@author addressbook-level4
    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.split(" "));
        return new HashSet<>(tagStrings);
    }
    
    //@@author A0146130W
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        
        final Matcher matcher = EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        Optional<Integer> index = Optional.of(Integer.parseInt(matcher.group("targetIndex")));
        String newDetail = matcher.group("newDetail");
        System.out.println(newDetail);
        String detailType = extractDetailType(newDetail); 
        
        if(detailType != "name") {
        	newDetail = newDetail.substring(2);
        }
        
        System.out.println(index.get() + " " +  detailType + " " + newDetail);
        
        return new EditCommand(
           (index.get() - 1),
           detailType,
           newDetail
        );
    }
    
    private String extractDetailType(String detailType) {
    	System.out.println(detailType.substring(0, 2));
    	switch(detailType.substring(0, 2)) {
    	case "d/": return "dueDate";
    	case "a/": return "address";
    	case "p/": return "priority";
    	default: return "name";
    	}
    }
    
    //@@author addressbook-level4
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        System.out.println(index);
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] splitKeywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(splitKeywords));
        
        final String keywords = matcher.group("keywords");
        return new FindCommand(keywords, keywordSet);
    }

    /**
     * Parses arguments in the context of the help command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareHelp(String args) {
    	//if no argument
    	if (args.equals("")) {
    		args="help";
    	}
    	
    	final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        return new HelpCommand(commandWord);
    }
}