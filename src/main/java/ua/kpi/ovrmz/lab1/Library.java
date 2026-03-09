package ua.kpi.ovrmz.lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Library {
    private final HashMap<String, ArrayList<Book>> books;
    private final HashMap<Integer, Reader> readers;
    private String name;
    private String currentLibrarianName;
    private String openingHours;

    public Library(String name, String currentLibrarianName, String openingHours) {
        this.name = name;
        this.currentLibrarianName = currentLibrarianName;
        this.openingHours = openingHours;
        books = new HashMap<>();
        readers = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentLibrarianName() {
        return currentLibrarianName;
    }

    public void setCurrentLibrarianName(String currentLibrarianName) {
        this.currentLibrarianName = currentLibrarianName;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public ArrayList<Book> getBooks() {
        ArrayList<Book> booksList = new ArrayList<>();
        for (ArrayList<Book> list : books.values()) {
            booksList.addAll(list);
        }
        return booksList;
    }

    public ArrayList<Reader> getReaders() {
        ArrayList<Reader> readersList = new ArrayList<>();
        readersList.addAll(readers.values());
        return readersList;
    }

    public void addReader(Reader reader) {
        readers.putIfAbsent(reader.getId(), reader);
    }

    public void addBook(Book book) {
        if (!books.containsKey(book.getIsbn())) {
            ArrayList<Book> bookList = new ArrayList<>();
            bookList.add(book);
            books.put(book.getIsbn(), bookList);
            return;
        }

        books.get(book.getIsbn()).add(book);
    }

    public String getInfo() {
        String result = name + "\nCurrent librarian: " + currentLibrarianName + "\nOpening hours:\n" + openingHours;
        return result;
    }

    public String getAllReadersInfo() {
        String result = "";
        for (Map.Entry<Integer, Reader> entry : readers.entrySet()) {
            result += entry.getValue().getInfo() + "\n\n";
        }

        return result;
    }

    public String getReaderInfo(int readerId) {
        if (!readers.containsKey(readerId)) {
            return "No reader found with id " + readerId;
        }

        return readers.get(readerId).getInfo();
    }

    public String getAllBooksInfo() {
        String result = "";
        for (Map.Entry<String, ArrayList<Book>> entry : books.entrySet()) {
            for (Book book : entry.getValue()) {
                result += book.getInfo() + "\n\n";
            }
        }

        return result;
    }

    public String getBookInfo(String isbn) {
        if (!books.containsKey(isbn)) {
            return "No book found with ISBN " + isbn;
        }

        Book result = books.get(isbn).get(0);
        int index = 1;

        while (!result.getStatus().equals(Const.BOOK_STATUS_AVAILABLE) && index < books.get(isbn).size()) {
            result = books.get(isbn).get(index);
            index++;
        }

        return result.getInfo();
    }

    public boolean updateReader(int id, String newName) {
        if (!readers.containsKey(id)) {
            return false;
        }

        readers.get(id).setName(newName);
        return true;
    }

    public boolean updateBook(String isbn, int id, String newTitle, String newAuthor) {
        if (!books.containsKey(isbn)) {
            return false;
        }

        for (Book book : books.get(isbn)) {
            if (book.getId() == id) {
                book.setTitle(newTitle);
                book.setAuthor(newAuthor);
                return true;
            }
        }

        return false;
    }

    public void deleteReader(int readerId) {
        if (!readers.containsKey(readerId)) {
            return;
        }
        if (readers.get(readerId).hasBorrowedBooks()) {
            throw new IllegalStateException("Reader has borrowed books");
        }

        readers.remove(readerId);
    }

    public void deleteBook(String isbn, int id) {
        if (!books.containsKey(isbn)) {
            return;
        }

        for (Book book : books.get(isbn)) {
            if (book.getId() == id) {
                if (book.getStatus().equals(Const.BOOK_STATUS_BORROWED)) {
                    throw new IllegalStateException("Book is borrowed");
                }
                books.get(isbn).remove(book);
                return;
            }
        }
    }

    public boolean borrowBook(int readerId, String isbn, int bookId) {
        if (!readers.containsKey(readerId) || !books.containsKey(isbn)) {
            return false;
        }
        for (Book book : books.get(isbn)) {
            if (book.getId() == bookId) {
                try {
                    return readers.get(readerId).borrowBook(book);
                } catch (IllegalArgumentException e) {
                    throw e;
                }
            }
        }
        return false;
    }

    public boolean returnBook(int readerId, String isbn, int bookId) {
        if (!readers.containsKey(readerId) || !books.containsKey(isbn)) {
            return false;
        }
        for (Book book : books.get(isbn)) {
            if (book.getId() == bookId) {
                try {
                    return readers.get(readerId).returnBook(book);
                } catch (IllegalStateException e){
                    throw e;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name + '\n' + currentLibrarianName + '\n' + openingHours + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(name, library.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
