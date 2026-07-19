package special;

import j200.ParSets;
import j200.Var;
import java.util.ArrayList;
import uhr.Zeit;

/**
 * Special class that replaces the controllers programs scheduler with a custom one.
 * 
 * @author ilia
 * @version v1.1.0
 */
public class Scheduler {
    class SchedulerEntry {
        public boolean isMonday   ;
        public boolean isTuesday  ;
        public boolean isWednesday;
        public boolean isThursday ;
        public boolean isFriday   ;
        public boolean isSaturday ;
        public boolean isSunday   ;
        public int hour;
        public int minute;
        public int program;

        public void copy(SchedulerEntry source) {
            this.isMonday    = source.isMonday   ;
            this.isTuesday   = source.isTuesday  ;
            this.isWednesday = source.isWednesday;
            this.isThursday  = source.isThursday ;
            this.isFriday    = source.isFriday   ;
            this.isSaturday  = source.isSaturday ;
            this.isSunday    = source.isSunday   ;
            this.hour        = source.hour       ;
            this.minute      = source.minute     ;
            this.program     = source.program    ;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            final SchedulerEntry other = (SchedulerEntry) obj;

            if (this.isMonday != other.isMonday) {
                return false;
            }

            if (this.isTuesday != other.isTuesday) {
                return false;
            }

            if (this.isWednesday != other.isWednesday) {
                return false;
            }

            if (this.isThursday != other.isThursday) {
                return false;
            }

            if (this.isFriday != other.isFriday) {
                return false;
            }

            if (this.isSaturday != other.isSaturday) {
                return false;
            }

            if (this.isSunday != other.isSunday) {
                return false;
            }

            if (this.hour != other.hour) {
                return false;
            }

            if (this.minute != other.minute) {
                return false;
            }

            if (this.program != other.program) {
                return false;
            }

            return true;
        }
    }

    public final static int DAYS_IN_WEEK    =  7;
    public final static int HOURS_IN_DAY    = 24;
    public final static int MINUTES_IN_HOUR = 60;
    public final static int MONDAY    = 0;
    public final static int TUESDAY   = 1;
    public final static int WEDNESDAY = 2;
    public final static int THURSDAY  = 3;
    public final static int FRIDAY    = 4;
    public final static int SATURDAY  = 5;
    public final static int SUNDAY    = 6;
    
    protected Node node;
    protected SchedulerEntry[] paramsSchedule;
    protected ArrayList<ArrayList<SchedulerEntry>> schedule = new ArrayList<ArrayList<SchedulerEntry>>(); 
    protected int entries;
    protected boolean hasChanged;

    public Scheduler(Node node) {
        this.node = node;
        paramsSchedule = new SchedulerEntry[64];
        for (int i = 0; i < paramsSchedule.length; i++) {
            paramsSchedule[i] = new SchedulerEntry();
        }
        entries = 0;
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            schedule.add(new ArrayList<SchedulerEntry>());
        }
    }
    
    private static int entriesCount = 0;
    
    public void addProgramToSchedule(
            int hour, int minute, int program,
            boolean isMonday,
            boolean isTuesday,
            boolean isWednesday,
            boolean isThursday,
            boolean isFriday,
            boolean isSaturday,
            boolean isSunday) {
        int daysOfWeek = 0x00;
        if (hour < 0 || hour >= HOURS_IN_DAY) {
            return;
        }
        
        if (minute < 0 || minute >= MINUTES_IN_HOUR) {
            return;
        }
        entriesCount++;
        
        SchedulerEntry entry = new SchedulerEntry();
        entry.hour = hour;
        entry.minute = minute;
        entry.program = program;
        entry.isMonday = isMonday;
        entry.isTuesday = isTuesday;
        entry.isWednesday = isWednesday;
        entry.isThursday = isThursday;
        entry.isFriday = isFriday;
        entry.isSaturday = isSaturday;
        entry.isSunday = isSunday;
        
        if (isMonday) {
            insertScheduleEntry(schedule.get(MONDAY), entry);
            daysOfWeek |= 0x01;
        }
        
        if (isTuesday) {
            insertScheduleEntry(schedule.get(TUESDAY), entry);
            daysOfWeek |= 0x02;
        }
        
        if (isWednesday) {
            insertScheduleEntry(schedule.get(WEDNESDAY), entry);
            daysOfWeek |= 0x04;
        }
        
        if (isThursday) {
            insertScheduleEntry(schedule.get(THURSDAY), entry);
            daysOfWeek |= 0x08;
        }
        
        if (isFriday) {
            insertScheduleEntry(schedule.get(FRIDAY), entry);
            daysOfWeek |= 0x10;
        }
        
        if (isSaturday) {
            insertScheduleEntry(schedule.get(SATURDAY), entry);
            daysOfWeek |= 0x20;
        }
        
        if (isSunday) {
            insertScheduleEntry(schedule.get(SUNDAY), entry);
            daysOfWeek |= 0x40;
        }
        
        ParSets.scheduler.set(0, entriesCount);
        ParSets.scheduler.set(1 + (entriesCount - 1) * 4 + 0, daysOfWeek   );
        ParSets.scheduler.set(1 + (entriesCount - 1) * 4 + 1, entry.hour   );
        ParSets.scheduler.set(1 + (entriesCount - 1) * 4 + 2, entry.minute );
        ParSets.scheduler.set(1 + (entriesCount - 1) * 4 + 3, entry.program);
    }

    public void readScheduleFromParameters() {
        if (ParSets.scheduler == null)
            return;
        
        int newEntries = ParSets.scheduler.get(0);
        hasChanged = newEntries != entries;
        // set new entries
        SchedulerEntry newEntry = new SchedulerEntry();
        for (int i = 0; i < Math.min(newEntries, paramsSchedule.length); i++) {
            int entryStartIndex = 1 + i * 4;
            int daysOfWeek   = ParSets.scheduler.get(entryStartIndex + 0);
            newEntry.hour    = ParSets.scheduler.get(entryStartIndex + 1);
            newEntry.minute  = ParSets.scheduler.get(entryStartIndex + 2);
            newEntry.program = ParSets.scheduler.get(entryStartIndex + 3);
            newEntry.isMonday    = (daysOfWeek & 0x01) > 0;
            newEntry.isTuesday   = (daysOfWeek & 0x02) > 0;
            newEntry.isWednesday = (daysOfWeek & 0x04) > 0;
            newEntry.isThursday  = (daysOfWeek & 0x08) > 0;
            newEntry.isFriday    = (daysOfWeek & 0x10) > 0;
            newEntry.isSaturday  = (daysOfWeek & 0x20) > 0;
            newEntry.isSunday    = (daysOfWeek & 0x40) > 0;
            hasChanged |= !paramsSchedule[i].equals(newEntry);
            paramsSchedule[i].copy(newEntry);
        }
        // no need to "reset" other entries since checks will always be limited by the "entries" field
    }

    public void updateScheduleFromParameters() {
        if (ParSets.scheduler == null || !hasChanged)
            return;

        int dayIndex, entryIndex;
        
        for (dayIndex = 0; dayIndex < schedule.size(); dayIndex++) {
            schedule.get(dayIndex).clear();
        }
        
        for (entryIndex = 0; entryIndex < paramsSchedule.length; entryIndex++) {
            SchedulerEntry entry = paramsSchedule[entryIndex];
            if (entry.isMonday) {
                insertScheduleEntry(schedule.get(MONDAY), entry);
            }
            if (entry.isTuesday) {
                insertScheduleEntry(schedule.get(TUESDAY), entry);
            }
            if (entry.isWednesday) {
                insertScheduleEntry(schedule.get(WEDNESDAY), entry);
            }
            if (entry.isThursday) {
                insertScheduleEntry(schedule.get(THURSDAY), entry);
            }
            if (entry.isFriday) {
                insertScheduleEntry(schedule.get(FRIDAY), entry);
            }
            if (entry.isSaturday) {
                insertScheduleEntry(schedule.get(SATURDAY), entry);
            }
            if (entry.isSunday) {
                insertScheduleEntry(schedule.get(SUNDAY), entry);
            }
        }
    }
    
    protected void insertScheduleEntry(ArrayList<SchedulerEntry> dayPlan, SchedulerEntry entry) {
        if (dayPlan.size() > 0) {
            for (int i = dayPlan.size() - 1; i >= 0; i--) {
                SchedulerEntry currentEntry = dayPlan.get(i);
                if ( entry.hour >  currentEntry.hour || 
                    (entry.hour == currentEntry.hour && entry.minute > currentEntry.minute)) {
                    dayPlan.add(i + 1, entry);
                    return;
                } else if (entry.hour == currentEntry.hour && entry.minute == currentEntry.minute) {
                    dayPlan.set(i, entry);
                    return;
                }
            }
        }
        dayPlan.add(0, entry);
    }
    
    private int getFixedDayIndex(int dayIndex) {
        while (dayIndex >= DAYS_IN_WEEK) {
            dayIndex -= DAYS_IN_WEEK;
        }
        
        while (dayIndex < 0) {
            dayIndex += DAYS_IN_WEEK;
        }
        
        return dayIndex;
    }
    
    public void setPlanByDateTime() {
        int dayIndex = getFixedDayIndex(Zeit.getWochentag() - 2);
        int hour = Zeit.getStunde();
        int minute = Zeit.getMinute();
        ArrayList<SchedulerEntry> dayPlan = schedule.get(dayIndex);
        int program = -1; // default program if schedule is "bad"
        
        for (int i = dayPlan.size() - 1; i >= 0 && program < 0; i--) {
            SchedulerEntry entry = dayPlan.get(i);
            if (hour > entry.hour || (hour == entry.hour && minute >= entry.minute)) {
                program = entry.program;
            }
        }
        
        node.clockProgram = program;
        node.isClockReset = true;
    }
    
    public void executeScheduler() {
        if (node.getProgZeit() % Var.ONE_SEC == 0 || node.firstSec)
        {
            readScheduleFromParameters();
            updateScheduleFromParameters();
            setPlanByDateTime();
        }
    }
    
    public void printDayPlan(int dayIndex, ArrayList<SchedulerEntry> dayPlan) {
        if (dayPlan == null || dayPlan.size() == 0)
            return;
        
        String day = "";
        switch (dayIndex) {
            case MONDAY:    day = "Monday"; break;
            case TUESDAY:   day = "Tuesday"; break;
            case WEDNESDAY: day = "Wednesday"; break;
            case THURSDAY:  day = "Thursday"; break;
            case FRIDAY:    day = "Friday"; break;
            case SATURDAY:  day = "Saturday"; break;
            case SUNDAY:    day = "Sunday"; break;
        }
        
        System.out.println();
        System.out.println("Daily schedule for " + day + ":");
        for (int i = 0; i < dayPlan.size(); i++) {
            String hour = Integer.toString(dayPlan.get(i).hour);
            String minute = Integer.toString(dayPlan.get(i).minute);
            if (hour.length() < 2)
                hour = "0" + hour;
            if (minute.length() < 2)
                minute = "0" + minute;
            System.out.println(" - " + hour + ":" + minute + "\t" + dayPlan.get(i).program);
        }
    }
}
