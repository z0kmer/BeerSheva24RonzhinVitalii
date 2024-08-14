package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

record MonthYear(int month, int year) {}

public class Main {
    public static void main(String[] args) {
        try {
            MonthYear monthYear = getMonthYear(args); //if no arguments current year and month should be applied
            int startDayOfWeek = getStartDayOfWeek(args); // get the start day of the week from args
            printCalendar(monthYear, startDayOfWeek);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printCalendar(MonthYear monthYear, int startDayOfWeek) {
        printTitle(monthYear);
        printWeekDays(startDayOfWeek);
        printDates(monthYear, startDayOfWeek);
    }

    private static void printDates(MonthYear monthYear, int startDayOfWeek) {
        YearMonth yearMonth = YearMonth.of(monthYear.year(), monthYear.month());
        int firstDayOfWeek = getFirstDayOfWeek(monthYear);
        int offset = getOffset(firstDayOfWeek, startDayOfWeek);
        int lastDay = getLastDayOfMonth(monthYear);

        for (int i = 0; i < offset; i++) {
            System.out.print("     ");
        }

        for (int day = 1; day <= lastDay; day++) {
            System.out.printf("%5d", day);
            if ((day + offset) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private static void printWeekDays(int startDayOfWeek) {
        DayOfWeek[] daysOfWeek = DayOfWeek.values();
        for (int i = 0; i < 7; i++) {
            System.out.printf("%5s", daysOfWeek[(i + startDayOfWeek - 1) % 7].getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }
        System.out.println();
    }

    private static void printTitle(MonthYear monthYear) {
        YearMonth yearMonth = YearMonth.of(monthYear.year(), monthYear.month());
        String title = yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ", " + monthYear.year();
        int padding = ((7 * 5) - title.length()) / 2; // 7 days * 5 characters per day
        System.out.printf("%" + padding + "s%s%n", "", title);
    }

    private static MonthYear getMonthYear(String[] args) throws Exception {
        LocalDate now = LocalDate.now();
        int month = args.length > 0 ? Integer.parseInt(args[0]) : now.getMonthValue();
        int year = args.length > 1 ? Integer.parseInt(args[1]) : now.getYear();
        if (month < 1 || month > 12) {
            throw new Exception("Invalid month: " + month);
        }
        if (year < 1) {
            throw new Exception("Invalid year: " + year);
        }
        return new MonthYear(month, year);
    }

    private static int getStartDayOfWeek(String[] args) throws Exception {
        int startDayOfWeek = args.length > 2 ? Integer.parseInt(args[2]) : 1; // default to Monday
        if (startDayOfWeek < 1 || startDayOfWeek > 7) {
            throw new Exception("Invalid start day of the week: " + startDayOfWeek);
        }
        return startDayOfWeek;
    }

    private static int getFirstDayOfWeek(MonthYear monthYear) {
        LocalDate firstDay = LocalDate.of(monthYear.year(), monthYear.month(), 1);
        return firstDay.getDayOfWeek().getValue();
    }

    private static int getOffset(int firstWeekDay, int startDayOfWeek) {
        return (firstWeekDay - startDayOfWeek + 7) % 7;
    }

    private static int getLastDayOfMonth(MonthYear monthYear) {
        YearMonth yearMonth = YearMonth.of(monthYear.year(), monthYear.month());
        return yearMonth.lengthOfMonth();
    }
}
