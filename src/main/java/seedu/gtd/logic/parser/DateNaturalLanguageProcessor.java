package seedu.gtd.logic.parser;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

//@@author A0146130W

/**
 * Uses natty API: http://natty.joestelmach.com to parse natural language into dates or string
 */
public class DateNaturalLanguageProcessor implements NaturalLanguageProcessor {
	
	private static final com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
	
	@Override
	public String formatString(String naturalLanguageDate) {
		List<DateGroup> dateGroups = parser.parse(naturalLanguageDate);
		Date parsedDate;
		try {
			parsedDate = refineDateGroupList(dateGroups);
		} catch (NaturalLanguageException e) {
			return "";
		}
		if (dateGroups.get(0).isRecurring()) {
			return "Recurring " + formatDateToString(parsedDate);
		}
		else {
			return formatDateToString(parsedDate);
		}
	}
	
	//@@author A0139158X
	@Override
	public Date formatDate(String naturalLanguageDate) {
		List<DateGroup> dateGroups = parser.parse(naturalLanguageDate);
		Date parsedDate;
		try {
			parsedDate = refineDateGroupList(dateGroups);
		} catch (NaturalLanguageException e) {
			return null;
		}
		return parsedDate;
	}
	
	/** 
	 * Chooses the first date from a list of dates that Natty has parsed from the natural language string
	 * @throws NaturalLanguageException 
	 * */
	private Date refineDateGroupList(List<DateGroup> groups) throws NaturalLanguageException {
		if(groups.size() == 0) throw new NaturalLanguageException();
		return groups.get(0).getDates().get(0); 
	}
	
	private String formatDateToString(Date date) {
		Format formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.format(date);
	}
}
