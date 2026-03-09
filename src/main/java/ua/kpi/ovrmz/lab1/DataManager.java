package ua.kpi.ovrmz.lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

public class DataManager {
    public static boolean exportLibrary(Library library, String libraryFilePath, String readersFilePath, String booksFilePath){
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(libraryFilePath)))        {
            writer.write(library.toString());
        } catch (Exception e) {
            return false;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(readersFilePath))){
            ArrayList<Reader> readers = library.getReaders();
            readers.sort(Comparator.comparing(Reader::getNumberOfReadBooks));
            for (Reader reader : readers.reversed())
            {
                writer.write(reader.toString() + '\n');
            }
            writer.write("*");
        } catch (Exception e) {
            return false;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(booksFilePath))){
            ArrayList<Book> books = library.getBooks();
            books.sort(Comparator.comparing(Book::getBorrowCount));
            for (Book book : books.reversed()) {
                writer.write(book.toString() + '\n');
            }
            writer.write("*");
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static Library importLibrary(String libraryFilePath, String readersFilePath, String booksFilePath){
        String libraryName, currentLibrarianName, openingHours;

        try (BufferedReader reader = Files.newBufferedReader(Path.of(libraryFilePath))){
            libraryName = reader.readLine().strip();
            currentLibrarianName = reader.readLine().strip();
            openingHours = reader.readLine().strip();
        } catch (Exception e) {
            return null;
        }

        Library library = new Library(libraryName, currentLibrarianName, openingHours);

        ArrayList<Book> books = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(booksFilePath))) {
            String line = "";
            while (line != null){
                line = reader.readLine().strip();
                if (line.equals("*")){
                    break;
                }
                int id = Integer.parseInt(line);
                String isbn = reader.readLine().strip();
                String title = reader.readLine().strip();
                String author = reader.readLine().strip();
                int borrowCount = Integer.parseInt(reader.readLine().strip());
                String status = reader.readLine().strip();
                books.add(new Book(id, isbn, title, author, borrowCount, status));
                line = reader.readLine().strip();
            }
        } catch (Exception e) {
            return null;
        }

        for (Book book : books) {
            library.addBook(book);
        }

        ArrayList<Reader> readers = new ArrayList<>();

        try (BufferedReader fileReader = Files.newBufferedReader(Path.of(readersFilePath))){
            String line = " ";
            while (line != null){
                line = fileReader.readLine().strip();
                if (line.equals("*")){
                    break;
                }
                int id = Integer.parseInt(line);
                String name = fileReader.readLine().strip();
                int maxNumberOfReadBooks = Integer.parseInt(fileReader.readLine().strip());
                int numberOfReadBooks = Integer.parseInt(fileReader.readLine().strip());
                ArrayList<Book> borrowedBooks = new ArrayList<>();
                Reader reader = new Reader(id, name, maxNumberOfReadBooks, numberOfReadBooks, borrowedBooks);
                line = fileReader.readLine().strip();
                while (!line.equals("")){
                    int idBook = Integer.parseInt(line);
                    for (Book book : books) {
                        if (book.getId() == idBook) {
                            borrowedBooks.add(book);
                            break;
                        }
                    }
                    line = fileReader.readLine().strip();
                }
                readers.add(reader);
            }
        } catch (Exception e) {
            return null;
        }

        for (Reader reader : readers) {
            library.addReader(reader);
        }

        return library;
    }
}
