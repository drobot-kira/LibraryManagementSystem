package ua.kpi.ovrmz.lab1;

import java.util.Objects;

public class Book {
    private final int id;
    private final String isbn;
    private String title;
    private String author;
    private int borrowCount;
    private String status;

    public Book(int id, String isbn, String title, String author) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.borrowCount = 0;
        this.status = Const.BOOK_STATUS_AVAILABLE;
    }

    public Book(int id, String isbn, String title, String author, int borrowCount, String status) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.borrowCount = borrowCount;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getId() {
        return id;
    }

    public boolean borrowBook() {
        if (status.equals(Const.BOOK_STATUS_AVAILABLE)) {
            borrowCount++;
            status = Const.BOOK_STATUS_BORROWED;
            return true;
        }
        if (status.equals(Const.BOOK_STATUS_BORROWED) || status.equals(Const.BOOK_STATUS_CHECK_COPY)) {
            return false;
        }
        throw new IllegalArgumentException("Illegal book status: " + status);
    }

    public boolean returnBook() {
        if (status.equals(Const.BOOK_STATUS_BORROWED)) {
            status = Const.BOOK_STATUS_AVAILABLE;
            return true;
        }
        if (status.equals(Const.BOOK_STATUS_CHECK_COPY) || status.equals(Const.BOOK_STATUS_AVAILABLE)) {
            return false;
        }
        throw new IllegalArgumentException("Illegal book status: " + status);
    }

    public String getInfo() {
        String result = isbn + "\n" + author + " \"" + title + "\"" + "\n" + "borrowed: " + borrowCount + "\n" + "status: " + status;
        return result;
    }

    @Override
    public String toString() {
        return id + "\n" + isbn + '\n' + title + '\n' + author + '\n' + borrowCount + '\n' + status + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }
}
