package ua.kpi.ovrmz.lab1;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello, World!");

        Book book = new Book(1, "isbn1", "title1", "author1");

        System.out.println(book);
        System.out.println(book.returnBook());
        System.out.println(book.borrowBook());
        System.out.println(book);
        System.out.println(book.borrowBook());
        System.out.println(book.returnBook());
        System.out.println(book);
        System.out.println(book.equals(book));
        System.out.println(book.hashCode());
        System.out.println(book.getInfo());
    }
}