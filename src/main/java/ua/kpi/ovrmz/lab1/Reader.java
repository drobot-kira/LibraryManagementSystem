package ua.kpi.ovrmz.lab1;

import java.util.ArrayList;
import java.util.Objects;

public class Reader {
    private final int id;
    private String name;
    private int maxNumberOfBooksToBorrow;
    private int numberOfReadBooks;
    private final ArrayList<Book> borrowedBooks;

    public Reader(int id, String name) {
        this.id = id;
        this.name = name;
        maxNumberOfBooksToBorrow = Const.STANDARD_MAX_NUMBER_OF_BOOKS_TO_BORROW;
        numberOfReadBooks = 0;
        borrowedBooks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxNumberOfBooksToBorrow() {
        return maxNumberOfBooksToBorrow;
    }

    public int getNumberOfReadBooks() {
        return numberOfReadBooks;
    }

    public boolean borrowBook(Book book) {
        if ((borrowedBooks.size() >= maxNumberOfBooksToBorrow)) {
            return false;
        }

        boolean bookCanBeBorrowed;
        try {
            bookCanBeBorrowed = book.borrowBook();
        } catch (IllegalArgumentException e) {
            throw e;
        }

        if (bookCanBeBorrowed) {
            borrowedBooks.add(book);
            return true;
        }

        return false;
    }

    public boolean returnBook(Book book) {
        boolean bookCanBeReturned;
        try {
            bookCanBeReturned = book.returnBook();
        } catch (IllegalArgumentException e) {
            throw e;
        }

        if (bookCanBeReturned) {
            borrowedBooks.remove(book);
            numberOfReadBooks++;
            maxNumberOfBooksToBorrow = Const.STANDARD_MAX_NUMBER_OF_BOOKS_TO_BORROW + numberOfReadBooks % 10;
            return true;
        }

        return false;
    }

    public boolean hasBorrowedBooks() {
        return !borrowedBooks.isEmpty();
    }

    public String getInfo() {
        String result = id + "\n" + name + "\nmaximum number of books to borrow: " + maxNumberOfBooksToBorrow + "\nbooks read: " + numberOfReadBooks + "\nBorrowed books: ";

        for (Book book : borrowedBooks) {
            result += book.getInfo() + "\n";
        }

        return result;
    }

    @Override
    public String toString() {
        return getInfo();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return id == reader.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
