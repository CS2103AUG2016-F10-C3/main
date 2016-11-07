package seedu.gtd.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

/**
 * executes at everyday midnight.
 */
//@@author A0139158X
public class Scheduler implements Runnable{
	
	private AddressBook addressbook;
	
	public Scheduler(AddressBook addressbook) {
		this.addressbook=addressbook;
	}
	
	public void run() {
		LocalDateTime localNow = LocalDateTime.now();
		ZoneId currentZone = ZoneId.of("Singapore");
		ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
		ZonedDateTime zonedNext = zonedNow.withHour(0).withMinute(0).withSecond(0);
		
		if(zonedNow.compareTo(zonedNext) > 0) {
			zonedNext = zonedNext.plusDays(1);
		}
		
		Duration duration = Duration.between(zonedNow, zonedNext);
		long initalDelay = duration.getSeconds();

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);            
		scheduler.scheduleAtFixedRate(new Thread(new RecurringTaskChecker(addressbook)), initalDelay,
                                  	24*60*60, TimeUnit.SECONDS);
	}
}
