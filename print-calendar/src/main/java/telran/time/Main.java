package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

record MonthYear(int month, int year) {}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            MonthYear monthYear = getMonthYear(scanner, args); //if no arguments current year and month should be applied
            DayOfWeek startDayOfWeek = getStartDayOfWeek(scanner, args);
            printCalendar(monthYear, startDayOfWeek);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printCalendar(MonthYear monthYear, DayOfWeek startDayOfWeek) {
        printTitle(monthYear);
        printWeekDays(startDayOfWeek);
        printDates(monthYear, startDayOfWeek);
    }

    private static void printDates(MonthYear monthYear, DayOfWeek startDayOfWeek) {
        int firstDayOfWeek = getFirstDayOfWeek(monthYear);
        int offset = getOffset(firstDayOfWeek, startDayOfWeek);
        int lastDay = getLastDayOfMonth(monthYear);

        for (int i = 0; i < offset; i++) {
            System.out.print("    ");
        }

        for (int day = 1; day <= lastDay; day++) {
            System.out.printf("%4d", day);
            if ((day + offset) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private static void printWeekDays(DayOfWeek startDayOfWeek) {
        DayOfWeek[] daysOfWeek = DayOfWeek.values();
        int startIndex = startDayOfWeek.getValue() - 1;

        for (int i = 0; i < 7; i++) {
            DayOfWeek day = daysOfWeek[(startIndex + i) % 7];
            System.out.print(day.getDisplayName(TextStyle.SHORT, Locale.US) + " ");
        }
        System.out.println();
    }

    private static void printTitle(MonthYear monthYear) {
        String title = String.format("%d, %s", monthYear.year(), YearMonth.of(monthYear.year(), monthYear.month()).getMonth().getDisplayName(TextStyle.FULL, Locale.US));
        System.out.printf("%" + (((28 - title.length()) / 2) + title.length()) + "s%n", title);
    }

    private static MonthYear getMonthYear(Scanner scanner, String[] args) throws Exception {
        int month = 0;
        int year = 0;
        boolean validMonth = false;
        boolean validYear = false;

        while (!validMonth) {
            if (args.length > 0) {
                month = Integer.parseInt(args[0]);
            } else {
                System.out.print("Введите номер месяца (1-12) или нажмите Enter для текущего месяца: ");
                String monthInput = scanner.nextLine();
                if (monthInput.isEmpty()) {
                    month = LocalDate.now().getMonthValue();
                    validMonth = true;
                } else {
                    month = Integer.parseInt(monthInput);
                }
            }

            if (month < 1 || month > 12) {
                System.out.println("Некорректный номер месяца. Попробуйте снова.");
                args = new String[0];
            } else {
                validMonth = true;
            }
        }

        while (!validYear) {
            if (args.length > 1) {
                year = Integer.parseInt(args[1]);
            } else {
                System.out.print("Введите год (1-9999) или нажмите Enter для текущего года: ");
                String yearInput = scanner.nextLine();
                if (yearInput.isEmpty()) {
                    year = LocalDate.now().getYear();
                    validYear = true;
                } else {
                    year = Integer.parseInt(yearInput);
                }
            }

            if (year < 1 || year > 9999) {
                System.out.println("Некорректный год. Попробуйте снова.");
                args = new String[0];
            } else {
                validYear = true;
            }
        }

        return new MonthYear(month, year);
    }

    private static DayOfWeek getStartDayOfWeek(Scanner scanner, String[] args) {
        int dayOfWeek = 0;
        boolean validDayOfWeek = false;

        while (!validDayOfWeek) {
            if (args.length > 2) {
                dayOfWeek = Integer.parseInt(args[2]);
            } else {
                System.out.print("Введите номер дня недели, с которого начинать (1 - Понедельник, 7 - Воскресенье) или нажмите Enter для начала с Понедельника: ");
                String dayOfWeekInput = scanner.nextLine();
                if (dayOfWeekInput.isEmpty()) {
                    return DayOfWeek.MONDAY;
                } else {
                    dayOfWeek = Integer.parseInt(dayOfWeekInput);
                }
            }

            if (dayOfWeek < 1 || dayOfWeek > 7) {
                System.out.println("Некорректный номер дня недели. Попробуйте снова.");
                args = new String[0];
            } else {
                validDayOfWeek = true;
            }
        }

        return DayOfWeek.of(dayOfWeek);
    }

    private static int getFirstDayOfWeek(MonthYear monthYear) {
        LocalDate firstDay = LocalDate.of(monthYear.year(), monthYear.month(), 1);
        return firstDay.getDayOfWeek().getValue();
    }

    private static int getOffset(int firstWeekDay, DayOfWeek startDayOfWeek) {
        int startDayValue = startDayOfWeek.getValue();
        return (firstWeekDay - startDayValue + 7) % 7;
    }

    private static int getLastDayOfMonth(MonthYear monthYear) {
        YearMonth yearMonth = YearMonth.of(monthYear.year(), monthYear.month());
        return yearMonth.lengthOfMonth();
    }
}