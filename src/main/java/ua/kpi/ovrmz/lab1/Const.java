package ua.kpi.ovrmz.lab1;

public class Const {
    public static final String BOOK_STATUS_AVAILABLE = "AVAILABLE";
    public static final String BOOK_STATUS_BORROWED = "BORROWED";
    public static final String BOOK_STATUS_CHECK_COPY = "CHECK COPY";
    public static final int STANDARD_MAX_NUMBER_OF_BOOKS_TO_BORROW = 2;
    public static final String WHITE = "\u001B[37m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String CYAN = "\u001B[36m";
    public static final String HIDE_CURSOR = "\u001B[?25l";
    public static final String SHOW_CURSOR = "\u001B[?25h";
    public static final String[] MENU_OPTIONS = {"Змінити назву бібліотеки", "Змінити бібліотекаря", "Додати читача",
            "Додати книгу", "Отримати інформацію про бібліотеку", "Отримати інформацію про читачів",
            "Отримати інформацію про конкретного читача", "Отримати інформацію про книги",
            "Отримати інформацію про конкретну книгу", "Змінити інформацію про читача",
            "Змінити інформацію про книгу", "Видалити читача", "Видалити книгу", "Взяти книгу", "Повернути книгу",
            "Експортувати бібліотеку", "Імпортувати бібліотеку", "Вийти"};
}
