/* rchome.java.server.Scheduler.java */
/*
 * RCHome - For more moderns homes
 * 
 * Copyright (C) 2011 Monica Nelly   <monica.araujo@itec.ufpa.br>
 * Copyright (C) 2011 Willian Paixao <willian@ufpa.br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package rchome.java.server;

import java.util.*;

/**
 * 
 * @author willian
 * @see java.util
 * @since 0.01
 */
public class Scheduler {

	private Calendar          taskCalendar;
	private GregorianCalendar curGregCalendar;
	private HouseContents     contents;
	private Locale            locale;
	private String            timeZone;
	private Timer             task            = null;

	public Scheduler() {

		contents        = new HouseContents("server");
		locale          = new Locale(contents.getContent("language"), contents.getContent("country"));
		task            = new Timer();
		timeZone        = contents.getContent("timeZone");
		curGregCalendar = new GregorianCalendar(TimeZone.getTimeZone(timeZone), locale);
	}

	public Scheduler(String timeZone, String language, String country) {

		locale          = new Locale(language, country);
		curGregCalendar = new GregorianCalendar(TimeZone.getTimeZone(timeZone), locale);
	}

	public void addTaskDaily(int hour, int minute, final String arg) {

		taskCalendar = GregorianCalendar.getInstance();
		try {
			taskCalendar.set(Calendar.HOUR_OF_DAY, hour);
			taskCalendar.set(Calendar.MINUTE, minute);
			taskCalendar.set(Calendar.SECOND, 0);
		} catch(ArrayIndexOutOfBoundsException e) {
			HandlerLog.logger.throwing("Scheduler", "addTaskDaily", e);
			HandlerLog.logger.warning("This time doesn't exist.");
		} finally {
			try {
				task.schedule(new TimerTask() {
					public void run() {
						HandlerSerial.write(arg);
					}
				}, taskCalendar.getTime(), 1000*60*60*24);
			} catch(IllegalArgumentException e) {
				HandlerLog.logger.throwing("Scheduler", "addTaskDaily", e);
				HandlerLog.logger.warning("This time doesn't exist.");
			} catch(IllegalStateException e) {
				HandlerLog.logger.throwing("Scheduler", "addTaskDaily", e);
			}
		}
	}

	public void addTaskHourly(int hour, int minute, final String arg) {

		taskCalendar = GregorianCalendar.getInstance();
		try {
			taskCalendar.set(Calendar.HOUR_OF_DAY, hour);
			taskCalendar.set(Calendar.MINUTE, minute);
			taskCalendar.set(Calendar.SECOND, 0);
		} catch(ArrayIndexOutOfBoundsException e) {
			HandlerLog.logger.throwing("Scheduler", "addTaskHourly", e);
			HandlerLog.logger.warning("This time doesn't exist.");
		} finally {
			try {
				task.schedule(new TimerTask() {
					public void run() {
						HandlerSerial.write(arg);
					}
				}, taskCalendar.getTime(), 1000*60*60);
			} catch(IllegalArgumentException e) {
				HandlerLog.logger.throwing("Scheduler", "addTaskHourly", e);
				HandlerLog.logger.warning("This time doesn't exist.");
			} catch(IllegalStateException e) {
				HandlerLog.logger.throwing("Scheduler", "addTaskHourly", e);
			}
		}
	}

	public Date getTime() {
		return curGregCalendar.getTime();
	}
}
