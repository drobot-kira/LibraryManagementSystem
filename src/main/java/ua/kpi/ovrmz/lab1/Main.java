package ua.kpi.ovrmz.lab1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*Library library = new Library("libraryName", "librarianName", "openingHours");
        Reader reader1 = new Reader(1, "readerName1");
        Reader reader2 = new Reader(2, "readerName2");
        library.addReader(reader1);
        library.addReader(reader2);
        Book book1 = new Book(1, "isbn1", "title1", "author1");
        Book book2 = new Book(2, "isbn1", "title2", "author2");
        Book book3 = new Book(3, "isbn3", "title3", "author3");
        Book book4 = new Book(4, "isbn4", "title4", "author4");
        Book book5 = new Book(5, "isbn5", "title5", "author5");
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);
        library.borrowBook(reader1.getId(), book1.getIsbn(), book1.getId());
        library.borrowBook(reader2.getId(), book2.getIsbn(), book2.getId());
        library.borrowBook(reader1.getId(), book3.getIsbn(), book3.getId());
        library.borrowBook(reader2.getId(), book4.getIsbn(), book4.getId());
        DataManager.exportLibrary(library, "library.txt", "readers.txt", "books.txt");
        library = DataManager.importLibrary("library.txt", "readers.txt", "books.txt");*/
        Scanner scanner = new Scanner(System.in);
        Library library = DataManager.importLibrary("files/defaultLibrary.txt", "files/defaultReaders.txt", "files/defaultBooks.txt");
        while (true) {
            displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть номер команди...");
            String answer = scanner.nextLine();
            switch (answer) {
                case "1": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть нову назву поточної бібліотеки...");
                    library.setName(scanner.nextLine().strip());
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Назву бібліотеки успішно змінено. Натисніть Enter, щоб продовжити...");
                    scanner.nextLine();
                    break;
                }
                case "2": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ім'я нового бібліотекаря...");
                    library.setCurrentLibrarianName(scanner.nextLine().strip());
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Ім'я бібліотекаря успішно змінено. Натисніть Enter, щоб продовжити...");
                    scanner.nextLine();
                    break;
                }
                case "3": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ідентифікатор читача (ціле число)...");
                    int id;
                    while (true) {
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ціле число...");
                        }
                    }
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ім'я читача...");
                    String name = scanner.nextLine().strip();
                    library.addReader(new Reader(id, name));
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Користувача збережено. Натисніть Enter, щоб продовжити...");
                    scanner.nextLine();
                    break;
                }
                case "4": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ідентифікатор книги (ціле число)...");
                    int id;
                    while (true) {
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ціле число...");
                        }
                    }
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ISBN книги...");
                    String isbn = scanner.nextLine().strip();
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть назву книги...");
                    String title = scanner.nextLine().strip();
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ім'я автора книги...");
                    String author = scanner.nextLine().strip();
                    library.addBook(new Book(id, isbn, title, author));
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Книгу збережено. Натисніть Enter, щоб продовжити...");
                    scanner.nextLine();
                    break;
                }
                case "5": {
                    displayInfoMessage(library.getName(), library.getCurrentLibrarianName(), library.getInfo());
                    scanner.nextLine();
                    break;
                }
                case "6": {
                    displayInfoMessage(library.getName(), library.getCurrentLibrarianName(), library.getAllReadersInfo());
                    scanner.nextLine();
                    break;
                }
                case "7": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ідентифікатор читача (ціле число)...");
                    int id;
                    while (true) {
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ціле число...");
                        }
                    }

                    displayInfoMessage(library.getName(), library.getCurrentLibrarianName(), library.getReaderInfo(id));
                    scanner.nextLine();

                    break;
                }
                case "8": {
                    displayInfoMessage(library.getName(), library.getCurrentLibrarianName(), library.getAllBooksInfo());
                    scanner.nextLine();
                    break;
                }
                case "9": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ISBN потрібної книги...");
                    String isbn = scanner.nextLine().strip();
                    displayInfoMessage(library.getName(), library.getCurrentLibrarianName(), library.getBookInfo(isbn));
                    scanner.nextLine();
                    break;
                }
                case "10": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ідентифікатор читача (ціле число)...");
                    int id;
                    while (true) {
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ціле число...");
                        }
                    }
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть нове ім'я користувача...");
                    String newName = scanner.nextLine().strip();
                    if (library.updateReader(id, newName)) {
                        displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Дані успішно оновлено. Натисніть Enter, щоб продовжити...");
                    } else {
                        displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Користувача за таким id не знайдено. Натисніть Enter, щоб продовжити....");
                    }
                    scanner.nextLine();
                    break;
                }
                case "11": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ідентифікатор книги (ціле число)...");
                    int id;
                    while (true) {
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ціле число...");
                        }
                    }
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ISBN бажаної книги...");
                    String isbn = scanner.nextLine().strip();
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть нову назву книги...");
                    String newTitle = scanner.nextLine().strip();
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть нового автора книги...");
                    String newAuthor = scanner.nextLine().strip();
                    if (library.updateBook(isbn, id, newTitle, newAuthor)) {
                        displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Інформацію про книгу успішно оновлено. Натисніть Enter, щоб продовжити...");
                    } else {
                        displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Книгу не знайдено. Натисніть Enter, щоб продовжити...");
                    }
                    scanner.nextLine();
                    break;
                }
                case "12": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ідентифікатор читача (ціле число)...");
                    int id;
                    while (true) {
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ціле число...");
                        }
                    }
                    try {
                        library.deleteReader(id);
                        displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Читача успішно видалено. Натисніть Enter, щоб продовжити...");
                    } catch (IllegalStateException e) {
                        displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Читач не віддав книги, його не можна видалити. Натисніть Enter, щоб продрвжити...");
                    }
                    scanner.nextLine();
                    break;
                }
                case "13": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ідентифікатор книги (ціле число)...");
                    int id;
                    while (true) {
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ціле число...");
                        }
                    }
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ISBN шуканої книги...");
                    String isbn = scanner.nextLine().strip();
                    try {
                        library.deleteBook(isbn, id);
                        displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Книгу успішно видалено. Натисніть Enter, щоб продовжити...");
                    } catch (IllegalStateException e) {
                        displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Книга не в бібліотеці, її не можна видалити. Натисніть Enter, щоб продовжити...");
                    }
                    scanner.nextLine();
                    break;
                }
                case "14": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ідентифікатор читача (ціле число)...");
                    int readerId;
                    while (true) {
                        try {
                            readerId = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ціле число...");
                        }
                    }
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ідентифікатор книги (ціле число)...");
                    int bookId;
                    while (true) {
                        try {
                            bookId = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ціле число...");
                        }
                    }
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ISBN книги...");
                    String isbn = scanner.nextLine().strip();

                    try {
                        if (library.borrowBook(readerId, isbn, bookId)) {
                            displayInfoMessage(library.getName(), library.getCurrentLibrarianName(), "Успіх. Натисніть Enter, щоб продовжити...");
                        } else {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Даний читач не може взяти обрану книгу, або книги чи читача не існує. Натисніть Enter, щоб продовжити...");
                        }
                    } catch (IllegalArgumentException e) {
                        displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Виникла помилка. Натисніть Enter, щоб продовжити...");
                    }
                    scanner.nextLine();
                    break;
                }
                case "15": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ідентифікатор читача (ціле число)...");
                    int readerId;
                    while (true) {
                        try {
                            readerId = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ціле число...");
                        }
                    }
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ідентифікатор книги (ціле число)...");
                    int bookId;
                    while (true) {
                        try {
                            bookId = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ціле число...");
                        }
                    }
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть ISBN книги...");
                    String isbn = scanner.nextLine().strip();

                    try {
                        if (library.returnBook(readerId, isbn, bookId)) {
                            displayInfoMessage(library.getName(), library.getCurrentLibrarianName(), "Успіх. Натисніть Enter, щоб продовжити...");
                        } else {
                            displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Даний читач не може повернути обрану книгу, або книги чи читача не існує. Натисніть Enter, щоб продовжити...");
                        }
                    } catch (IllegalArgumentException e) {
                        displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Виникла помилка. Натисніть Enter, щоб продовжити...");
                    }
                    scanner.nextLine();
                    break;
                }
                case "16": {
                    if (DataManager.exportLibrary(library, "files/" + library.getName() + "Library.txt",
                            "files/" + library.getName() + "Readers.txt",
                            "files/" + library.getName() + "Books.txt")) {
                        displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Дані бібліотеки успішно збережено. Натисніть Enter, щоб продовжити...");
                    } else {
                        displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Виникла помилка. Натисніть Enter, щоб продовжити...");
                    }
                    scanner.nextLine();
                    break;
                }
                case "17": {
                    displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Введіть назву бібліотеки...");
                    String libraryName = scanner.nextLine().strip();
                    Library newLibrary = DataManager.importLibrary("files/" + libraryName + "Library.txt",
                            "files/" + libraryName + "Readers.txt", "files/" + libraryName + "Books.txt");
                    if (newLibrary != null) {
                        library = newLibrary;
                        displayMenuWithMessage(library.getName(), library.getCurrentLibrarianName(), "Дані бібліотеки успішно імпортовано. Натисніть Enter, щоб продовжити...");
                    } else {
                        displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Виникла помилка. Натисніть Enter, щоб продовжити...");
                    }
                    scanner.nextLine();
                    break;
                }
                case "18": {
                    return;
                }
                default: {
                    displayMenuWithErrorMessage(library.getName(), library.getCurrentLibrarianName(), "Невідома команда. Натисніть Enter, щоб продовжити...");
                    scanner.nextLine();
                    break;
                }
            }
        }
    }

    public static void fakeConsoleClear() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void displayMenuWithMessage(String libraryName, String currentLibrarianName, String message) {
        fakeConsoleClear();
        System.out.println(Const.HIDE_CURSOR + Const.WHITE + libraryName);
        System.out.println("Поточний бібліотекар: " + currentLibrarianName);

        for (int i = 0; i < Const.MENU_OPTIONS.length; i++) {
            System.out.println(Const.CYAN + (i + 1) + ". " + Const.MENU_OPTIONS[i]);
        }
        System.out.println(Const.GREEN + message);
        System.out.println(Const.WHITE + "===============================================");
        System.out.print(Const.SHOW_CURSOR + ">>> ");
    }

    public static void displayMenuWithErrorMessage(String libraryName, String currentLibrarianName, String errorMessage) {
        fakeConsoleClear();
        System.out.println(Const.HIDE_CURSOR + Const.WHITE + libraryName);
        System.out.println("Поточний бібліотекар: " + currentLibrarianName);

        for (int i = 0; i < Const.MENU_OPTIONS.length; i++) {
            System.out.println(Const.CYAN + (i + 1) + ". " + Const.MENU_OPTIONS[i]);
        }
        System.out.println(Const.RED + errorMessage);
        System.out.println(Const.WHITE + "===============================================");
        System.out.print(Const.SHOW_CURSOR + ">>> ");
    }

    public static void displayInfoMessage(String libraryName, String currentLibrarianName, String infoMessage) {
        fakeConsoleClear();
        System.out.println(Const.HIDE_CURSOR + Const.WHITE + libraryName);
        System.out.println("Поточний бібліотекар: " + currentLibrarianName);

        System.out.println(Const.CYAN + infoMessage);
        System.out.println(Const.GREEN + "Натисніть Enter, щоб продовжити...");

        System.out.println(Const.WHITE + "===============================================");
        System.out.print(Const.SHOW_CURSOR + ">>> ");
    }
}