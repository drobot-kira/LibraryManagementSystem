package ua.kpi.ovrmz.lab1;

public class Main {
    public static void main(String[] args) {
        Library library = new Library("libraryName", "librarianName", "openingHours");
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
        library = DataManager.importLibrary("library.txt", "readers.txt", "books.txt");
    }
}