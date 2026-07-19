package core;

import m0547.Var;
import java.util.ArrayList;
import uhr.Uhr;
import uhr.Zeit;
import vt.StgEbene;
import vt.Vt;
import vtvar.VtVarStrukt;

/**
 * Special class that replaces the controllers programs scheduler with a custom one. 
 * @author Ilia Butvinnik
 * @version 2.0.0
 * @since 01/07/2025
 */
public class Scheduler {
    class SchedulerEntry implements Comparable<SchedulerEntry> {
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

        /**
         * Duplicates a SchedulerEntry object's field into this object
         * @param source
         */
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

        public int compareTo(SchedulerEntry o) {
            if (o == null) {
                return 1;
            }
            
            if (this.hour < o.hour) {
                return -1;
            } else if (this.hour > o.hour) {
                return 1;
            } else {
                if (this.minute < o.minute) {
                    return -1;
                } else if (this.minute > o.minute) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
    
    class SpecialDateEntry implements Comparable<SpecialDateEntry> {
        public int dayOfMonth;
        public int month;
        public int year;
        public SchedulerEntry entry;
        
        public SpecialDateEntry() {
            entry = new SchedulerEntry();
        }

        public void copy(SpecialDateEntry source) {
            if (source == null) {
                return;
            }
            
            this.dayOfMonth = source.dayOfMonth;
            this.month      = source.month;
            this.year       = source.year;
            this.entry      = new SchedulerEntry();
            this.entry.copy(source.entry);
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (obj.getClass() != this.getClass()) {
                return false;
            }

            final SpecialDateEntry other = (SpecialDateEntry) obj;

            if (this.dayOfMonth != other.dayOfMonth) {
                return false;
            }

            if (this.month != other.month) {
                return false;
            }

            if (this.year != other.year) {
                return false;
            }

            if (!this.entry.equals(other.entry)) {
                return false;
            }

            return true;
        }

        public int compareTo(SpecialDateEntry o) {
            if (o == null) {
                return 1;
            }
            
            if (this.year < o.year) {
                return -1;
            } else if (this.year > o.year) {
                return 1;
            } else {
               if (this.month < o.month) {
                   return -1;
               } else if (this.month > o.month) {
                   return 1;
               } else {
                   if (this.dayOfMonth < o.dayOfMonth) {
                       return -1;
                   } else if (this.dayOfMonth > o.dayOfMonth) {
                       return 1;
                   } else {
                       if (entry == null && o.entry != null) {
                           return -1;
                       } else {
                           return entry.compareTo(o.entry);
                       }
                   }
               }
            }
        }
        
        public String toString() {
            return dayOfMonth + "/" + month + "/" + year + " " + entry.hour + ":" + entry.minute + " " + entry.program; 
        }
    }
    
    /**
     * This method checks whether there are any requests to set a program on the CLOCK level
     * If so, it activates the request.
     * Otherwise, it rolls back the demand according to the controller's UHR table
     * Note: this method should always be used to set CLOCK level programs,
     *       even if the application uses the ACTROS's default scheduler
     * @param node - the node for which the program request needs to be processed
     */
    public static void activateProgram(Node node) {
        if (node.clockProgram > Var.NONE) {
            if (node.getProgWunsch(node, StgEbene.STG_UHR) == Vt.KEIN_PROGRAMMWUNSCH ||
                node.getProgWunsch(node, StgEbene.STG_UHR).getNummer() != node.clockProgram) {
                node.setProgWunsch(Vt.findProgByNum(node.clockProgram, node.getNummer()), StgEbene.STG_UHR);
            }
        } else {
            if (node.isClockReset) {
                Uhr.update();
                node.isClockReset = false;
            }
        }
    }

    public final static int DAYS_IN_WEEK         =  7;
    public final static int HOURS_IN_DAY         = 24;
    public final static int MINUTES_IN_HOUR      = 60;
    public final static int DAY_IN_MONTH_FIRST   =  1;
    public final static int DAY_IN_MONTH_LAST    = 31;
    public final static int MONTH_IN_YEAR_FIRST  =  1;
    public final static int MONTH_IN_YEAR_LAST   = 12;
    public final static int YEAR_MAX             = 99;
    public final static int ENTRY_SIZE_SCHEDULE  =  4;
    public final static int ENTRY_SIZE_DATE      =  6;
    public final static int ENTRY_LIMIT_SCHEDULE = 64;
    public final static int ENTRY_LIMIT_DATE     = 64;
    
    public final static int MONDAY    = 0;
    public final static int TUESDAY   = 1;
    public final static int WEDNESDAY = 2;
    public final static int THURSDAY  = 3;
    public final static int FRIDAY    = 4;
    public final static int SATURDAY  = 5;
    public final static int SUNDAY    = 6;
    
    public static int[] schedulerParametersValues;
    public static VtVarStrukt schedulerParameters;

    private static int entriesCount      = 0; // current amount of entries in the scheduler
    private static int specialDatesCount = 0; // current amount of special dates in the scheduler
    
    protected Node node;
    protected SchedulerEntry[] paramsSchedule;
    protected ArrayList<ArrayList<SchedulerEntry>> schedule = new ArrayList<ArrayList<SchedulerEntry>>();
    protected SpecialDateEntry[] paramsSepcialDates;
    protected ArrayList<SpecialDateEntry> specialDates = new ArrayList<SpecialDateEntry>();
//    protected int entries;
    protected boolean hasChanged;

    
    public Scheduler(Node node) {
        this.node = node;

        paramsSchedule = new SchedulerEntry[ENTRY_LIMIT_SCHEDULE];
        for (int i = 0; i < paramsSchedule.length; i++) {
            paramsSchedule[i] = new SchedulerEntry();
        }
        
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            schedule.add(new ArrayList<SchedulerEntry>());
        }
        
        if (hasSpecialDates()) {
            paramsSepcialDates = new SpecialDateEntry[ENTRY_LIMIT_DATE];
            for (int i = 0; i < paramsSepcialDates.length; i++) {
                paramsSepcialDates[i] = new SpecialDateEntry();
            }
        }
    }
    
    /**
     * Adds an entry to the scheduler, if the entry is valid
     * @param hour - the hour when this entry should be activated (0 - 23)
     * @param minute - the minute of the hour when this entry should be activated (0 - 59)
     * @param program - the program number that this entry should activate
     * @param isMonday - whether this entry should be activated on Mondays
     * @param isTuesday - whether this entry should be activated on Tuesdays
     * @param isWednesday - whether this entry should be activated on Wednesdays
     * @param isThursday - whether this entry should be activated on Thursdays
     * @param isFriday - whether this entry should be activated on Fridays
     * @param isSaturday - whether this entry should be activated on Saturdays
     * @param isSunday - whether this entry should be activated on Sundays
     */
    public void addProgramToSchedule(
            int hour, int minute,
            int program,
            boolean isMonday,
            boolean isTuesday,
            boolean isWednesday,
            boolean isThursday,
            boolean isFriday,
            boolean isSaturday,
            boolean isSunday) {
        
        if (schedulerParametersValues == null) {
            schedulerParametersValues = new int[getParametersArraySize()];
        }
        
        int daysOfWeek = 0x00; // mask for the days of the week during which this entry will be active
        
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

        schedulerParametersValues[0] = entriesCount;
        schedulerParametersValues[1 + (entriesCount - 1) * ENTRY_SIZE_SCHEDULE + 0] = daysOfWeek;
        schedulerParametersValues[1 + (entriesCount - 1) * ENTRY_SIZE_SCHEDULE + 1] = entry.hour;
        schedulerParametersValues[1 + (entriesCount - 1) * ENTRY_SIZE_SCHEDULE + 2] = entry.minute;
        schedulerParametersValues[1 + (entriesCount - 1) * ENTRY_SIZE_SCHEDULE + 3] = entry.program;
    }
    
    /**
     * 
     * @param hour - the hour at which the program has to start [0 - 23]
     * @param minute - the minute of the hour at which the program has to start [0 - 59]
     * @param program - the number of program to be activated
     * @param dayOfMonth - the day of the month in which the program has to start [1 - 31]
     * @param month - the month in which the program has to start [1 - 12]
     * @param year - the year in which the program has to start [0 - 99]
     */
    public void addSpecialDateToSchedule(
            int hour, int minute,
            int program,
            int dayOfMonth,
            int month,
            int year) {
        
        if (schedulerParametersValues == null) {
            schedulerParametersValues = new int[getParametersArraySize()];
        }
        
        if (!MathHelper.isInRange(hour, 0, HOURS_IN_DAY - 1)) {
            return;
        }
        
        if (!MathHelper.isInRange(minute, 0, MINUTES_IN_HOUR - 1)) {
            return;
        }
        
        if (!MathHelper.isInRange(dayOfMonth, DAY_IN_MONTH_FIRST, DAY_IN_MONTH_LAST)) {
            return;
        }
        
        if (!MathHelper.isInRange(month, MONTH_IN_YEAR_FIRST, MONTH_IN_YEAR_LAST)) {
            return;
        }
        
        if (!MathHelper.isInRange(year, 0, YEAR_MAX)) {
            return;
        }
        specialDatesCount++;
        
        SpecialDateEntry entry = new SpecialDateEntry();
        entry.dayOfMonth = dayOfMonth;
        entry.month = month;
        entry.year = year;
        entry.entry.hour = hour;
        entry.entry.minute = minute;
        entry.entry.program = program;
        
        insertSpecialDateEntry(entry);

        schedulerParametersValues[1 + entriesCount * ENTRY_SIZE_SCHEDULE + 0] = specialDatesCount;
        schedulerParametersValues[1 + entriesCount * ENTRY_SIZE_SCHEDULE + 1 + (specialDatesCount - 1) * ENTRY_SIZE_DATE + 0] = dayOfMonth;
        schedulerParametersValues[1 + entriesCount * ENTRY_SIZE_SCHEDULE + 1 + (specialDatesCount - 1) * ENTRY_SIZE_DATE + 1] = month;
        schedulerParametersValues[1 + entriesCount * ENTRY_SIZE_SCHEDULE + 1 + (specialDatesCount - 1) * ENTRY_SIZE_DATE + 2] = year;
        schedulerParametersValues[1 + entriesCount * ENTRY_SIZE_SCHEDULE + 1 + (specialDatesCount - 1) * ENTRY_SIZE_DATE + 3] = hour;
        schedulerParametersValues[1 + entriesCount * ENTRY_SIZE_SCHEDULE + 1 + (specialDatesCount - 1) * ENTRY_SIZE_DATE + 4] = minute;
        schedulerParametersValues[1 + entriesCount * ENTRY_SIZE_SCHEDULE + 1 + (specialDatesCount - 1) * ENTRY_SIZE_DATE + 5] = program;
    }

    /**
     * This method reads the parameters from the controller in order to check for updates
     * made either manually or via a control center interface
     */
    public void readScheduleFromParameters() {
        if (schedulerParameters == null)
            return;
        
        int newEntriesCount = schedulerParameters.get(0);
        int newSpecialDatesCount = 0;
        
        hasChanged = newEntriesCount != entriesCount;
        
        // Iterating over the regular entries
        SchedulerEntry newEntry = new SchedulerEntry();
        for (int i = 0; i < Math.min(newEntriesCount, paramsSchedule.length); i++) {
            int entryStartIndex = 1 + i * ENTRY_SIZE_SCHEDULE;
            int daysOfWeek   = schedulerParameters.get(entryStartIndex + 0);
            newEntry.hour    = schedulerParameters.get(entryStartIndex + 1);
            newEntry.minute  = schedulerParameters.get(entryStartIndex + 2);
            newEntry.program = schedulerParameters.get(entryStartIndex + 3);
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
        
        int specialDatesCountIndex = 1 + newEntriesCount * ENTRY_SIZE_SCHEDULE;
        if (!hasSpecialDates() || schedulerParameters.getSize() <= specialDatesCountIndex) {
            return;
        }
        
        newSpecialDatesCount = schedulerParameters.get(specialDatesCountIndex);
        
        SpecialDateEntry newDate = new SpecialDateEntry();
        for (int i = 0; i < Math.min(newSpecialDatesCount, paramsSepcialDates.length); i++) {
            int entryStartIndex = specialDatesCountIndex + 1 + i * ENTRY_SIZE_DATE;
            newDate.dayOfMonth    = schedulerParameters.get(entryStartIndex + 0);
            newDate.month         = schedulerParameters.get(entryStartIndex + 1);
            newDate.year          = schedulerParameters.get(entryStartIndex + 2);
            newDate.entry.hour    = schedulerParameters.get(entryStartIndex + 3);
            newDate.entry.minute  = schedulerParameters.get(entryStartIndex + 4);
            newDate.entry.program = schedulerParameters.get(entryStartIndex + 5);
            hasChanged |= !paramsSepcialDates[i].equals(newDate);
            paramsSepcialDates[i].copy(newDate);
        }
        
        // no need to "reset" other entries since checks will always be limited by the "entries" field
    }

    public void updateScheduleFromParameters() {
        if (schedulerParameters == null || !hasChanged)
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
        
        if (!hasSpecialDates()) {
            return;
        }
        specialDates.clear();
        for (entryIndex = 0; entryIndex < paramsSepcialDates.length; entryIndex++) {
            SpecialDateEntry entry = paramsSepcialDates[entryIndex];
            if (entry.entry.program == Var.NONE) {
                specialDates.add(specialDates.size() - 1, entry);
            } if (specialDates.size() == 0) {
                specialDates.add(0, entry);
            } else {
                for (int i = specialDates.size() - 1; i >= 0; i--) {
                    SpecialDateEntry currentEntry = specialDates.get(i);
                    if (entry.compareTo(currentEntry) >= 0 && currentEntry.entry.program > Var.NONE) {
                        specialDates.add(i + 1, entry);
                        break;
                    } else if (i == 0) {
                        specialDates.add(0, entry);
                    }
                }
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
    
    protected void insertSpecialDateEntry(SpecialDateEntry entry) {
        if (specialDates.size() > 0) {
            for (int i = specialDates.size() - 1; i >= 0; i--) {
                SpecialDateEntry currentEntry = specialDates.get(i);
                if (entry.compareTo(currentEntry) >= 0 && currentEntry.entry.program > Var.NONE) {
                    specialDates.add(i + 1, entry);
                    break;
                } else if (i == 0) {
                    specialDates.add(0, entry);
                }
            }
        } else {
            specialDates.add(0, entry);
        }
        
        if (specialDates.size() > ENTRY_LIMIT_DATE) {
            specialDates.remove(specialDates.size() - 1);
        }
    }
    
    /**
     * Auxiliary method that takes ACTROS's day indexing and corrects it
     * to the ordered defined in the FDPP protocol 
     * @param dayIndex - the day index as defined by the ACTROS
     * @return the day index as defined by the FDPP protocol
     */
    private int getFixedDayIndex(int dayIndex) {
        while (dayIndex >= DAYS_IN_WEEK) {
            dayIndex -= DAYS_IN_WEEK;
        }
        
        while (dayIndex < 0) {
            dayIndex += DAYS_IN_WEEK;
        }
        
        return dayIndex;
    }
    
    /**
     * Auxiliary method that checks what the active program should be according to the scheduler:
     *   - checks daily schedule
     *   - checks special dates calendar (if defined)
     * and then sets the corresponding program request to the node
     */
    protected void setPlanByDateTime() {
        int dayIndex = getFixedDayIndex(Zeit.getWochentag() - 2);
        int hour   = Zeit.getStunde()      ; // protocol values: 0 - 23
        int minute = Zeit.getMinute()      ; // protocol values: 0 - 59
        int day    = Zeit.getTagDesMonats(); // protocol values: 1 - 31
        int month  = Zeit.getMonat()       ; // protocol values: 1 - 12
        int year   = Zeit.getJahr() % 100  ; // protocol values: 0 - 99
        ArrayList<SchedulerEntry> dayPlan = schedule.get(dayIndex);
        int program = Var.NONE, specialProgram = Var.NONE; // default program if schedule is "bad"
        
        for (int i = dayPlan.size() - 1; i >= 0 && program < 0; i--) {
            SchedulerEntry entry = dayPlan.get(i);
            if (hour > entry.hour || (hour == entry.hour && minute >= entry.minute)) {
                program = entry.program;
            }
        }
        
        if (hasSpecialDates()) {
            for (int i = specialDates.size() - 1; i >= 0 && specialProgram < 0; i--) {
                SpecialDateEntry entry = specialDates.get(i);
                if (entry.year == year
                        && entry.month == month
                        && entry.dayOfMonth == day
                        && (hour > entry.entry.hour || (hour == entry.entry.hour && minute >= entry.entry.minute))
                        && entry.entry.program > Var.NONE) {
                    specialProgram = entry.entry.program;
                }
            }
        }

        if (specialProgram > 0) {
            node.clockProgram = specialProgram;
        } else if (program > 0) {
            node.clockProgram = program;
        } else {
            node.clockProgram = 1;
        }
        
        if (specialProgram > Var.NONE || program > Var.NONE) {
            node.isClockReset = true;
        }
    }
    
    public void printSpecialDates() {
        for (int i = 0; i < specialDates.size(); i++) {
            System.out.println(specialDates.get(i));
        }
        System.out.println();
    }
    
    /**
     * 
     */
    public void executeScheduler() {
        if (node.isFullSecond() || node.firstSec)
        {
            readScheduleFromParameters();
            updateScheduleFromParameters();
            setPlanByDateTime();
        }
    }

    /**
     * Initializes the scheduler parameters array
     */
    public void initializeParameters() {
        if (schedulerParameters == null
                && (Var.controller.isAppJerusalem() || Var.controller.isAppNetiveiIsrael())) {
            schedulerParameters = VtVarStrukt.init(Var.tk1, "DVI35_PLAN01", schedulerParametersValues, true, true, true);
        }
    }
    
    /**
     * Auxiliary method to return the required number of bytes in the parameters array needed for the Scheduler 
     * @return the number of bytes required for the parameters array
     */
    private int getParametersArraySize() {
        if (Var.controller.isAppJerusalem()) {
            // 1 byte for entries count + 64 entries of 4 bytes = 1 + 64 * 4 = 1 + 256 = 257
            return 257; 
        }
        
        if (Var.controller.isAppNetiveiIsrael()) {
            // 1 byte for scheduler entries count
            // + 64 entries of 4 bytes (for scheduler)
            // + 1 byte for special dates entries count
            // + 64 entries of 6 bytes (for special dates)
            // = 1 + 64 * 4 + 1 + 64 * 6 = 2 + 64 * 10 = 2 + 640 = 642
            return 642;
        }
        
        return 0;
    }
    
    /**
     * Auxiliary method that prints the scheduler of the day
     * @param dayIndex - the index of the day (M T W T F S S)
     * @param dayPlan - the entries for the requested day
     */
    protected void printDayPlan(int dayIndex, ArrayList<SchedulerEntry> dayPlan) {
        if (!Var.isPrintDebug) {
            return;
        }
        
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
    
    /**
     * Auxiliary method that checks whether this version of the scheduler supports special dates
     * Currently, the only version that supports special dates is Netivei-Israel
     * @return true if this version supports special dates
     */
    protected boolean hasSpecialDates() {
        return Var.controller.isAppNetiveiIsrael();
    }
    
    public static boolean isUseCustomScheduler() {
        return Var.controller.isAppJerusalem() || Var.controller.isAppNetiveiIsrael();
    }
}
