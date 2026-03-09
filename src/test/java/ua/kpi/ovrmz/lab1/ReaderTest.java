package ua.kpi.ovrmz.lab1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    private void setMaxBooksLimitViaReflection(Reader targetReader, int limit) throws Exception {
        Field limitField = Reader.class.getDeclaredField("maxNumberOfBooksToBorrow");
        limitField.setAccessible(true);
        limitField.set(targetReader, limit);
    }

    private ArrayList<Book> getBorrowedBooksViaReflection(Reader targetReader) throws Exception {
        Field booksField = Reader.class.getDeclaredField("borrowedBooks");
        booksField.setAccessible(true);
        return (ArrayList<Book>) booksField.get(targetReader);
    }

    @Test
    @DisplayName("Успішна видача: ліміт не перевищено, книга доступна")
    void borrowBook_WhenUnderLimitAndBookAvailable_ShouldReturnTrueAndAddBook() throws Exception {
        Reader reader = new Reader(1, "name");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.borrowBook()).thenReturn(true);

        boolean result = reader.borrowBook(mockBook);

        assertTrue(result);
        ArrayList<Book> borrowedBooks = getBorrowedBooksViaReflection(reader);
        assertEquals(1, borrowedBooks.size());
        assertTrue(borrowedBooks.contains(mockBook));
    }

    @Test
    @DisplayName("Відмова у видачі: ліміт книг перевищено")
    void borrowBook_WhenLimitReached_ShouldReturnFalseAndNotCallBook() throws Exception {
        Reader reader = new Reader(1, "name");
        setMaxBooksLimitViaReflection(reader, 0);
        Book mockBook = Mockito.mock(Book.class);

        boolean result = reader.borrowBook(mockBook);

        assertFalse(result);
        Mockito.verify(mockBook, Mockito.never()).borrowBook();

        ArrayList<Book> borrowedBooks = getBorrowedBooksViaReflection(reader);
        assertTrue(borrowedBooks.isEmpty());
    }

    @Test
    @DisplayName("Відмова у видачі: книга недоступна (borrowBook повернув false)")
    void borrowBook_WhenBookNotAvailable_ShouldReturnFalseAndNotAddBook() throws Exception {
        Reader reader = new Reader(1, "name");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.borrowBook()).thenReturn(false);

        boolean result = reader.borrowBook(mockBook);

        assertFalse(result);
        ArrayList<Book> borrowedBooks = getBorrowedBooksViaReflection(reader);
        assertTrue(borrowedBooks.isEmpty());
    }

    @Test
    @DisplayName("Викидання винятку: помилка всередині книги передається далі")
    void borrowBook_WhenBookThrowsException_ShouldThrowException() {
        Reader reader = new Reader(1, "name");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.borrowBook()).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> reader.borrowBook(mockBook));
    }

    @Test
    @DisplayName("Успішне повернення: книга видаляється, статистика оновлюється")
    void returnBook_WhenBookCanBeReturned_ShouldReturnTrueAndUpdateStats() throws Exception {
        Reader reader = new Reader(1, "name");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.returnBook()).thenReturn(true);

        getBorrowedBooksViaReflection(reader).add(mockBook);

        boolean result = reader.returnBook(mockBook);

        assertTrue(result);

        ArrayList<Book> borrowedBooks = getBorrowedBooksViaReflection(reader);
        assertFalse(borrowedBooks.contains(mockBook));

        assertEquals(1, reader.getNumberOfReadBooks());
        assertEquals(Const.STANDARD_MAX_NUMBER_OF_BOOKS_TO_BORROW + 1, reader.getMaxNumberOfBooksToBorrow());
    }

    @Test
    @DisplayName("Відмова у поверненні: книга повертає false")
    void returnBook_WhenBookCannotBeReturned_ShouldReturnFalseAndNotChangeStats() throws Exception {
        Reader reader = new Reader(1, "name");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.returnBook()).thenReturn(false);

        getBorrowedBooksViaReflection(reader).add(mockBook);

        boolean result = reader.returnBook(mockBook);

        assertFalse(result);

        ArrayList<Book> borrowedBooks = getBorrowedBooksViaReflection(reader);
        assertTrue(borrowedBooks.contains(mockBook));

        assertEquals(0, reader.getNumberOfReadBooks());
    }

    @Test
    @DisplayName("Викидання винятку: помилка при поверненні передається далі")
    void returnBook_WhenBookThrowsException_ShouldThrowException() throws Exception {
        Reader reader = new Reader(1, "name");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.returnBook()).thenThrow(new IllegalArgumentException());

        getBorrowedBooksViaReflection(reader).add(mockBook);

        assertThrows(IllegalArgumentException.class, () -> reader.returnBook(mockBook));
    }

    @Test
    @DisplayName("hasBorrowedBooks: true якщо є книги")
    void hasBorrowedBooks_WhenHasBooks_ShouldReturnTrue() throws Exception {
        Reader reader = new Reader(1, "name");
        Book mockBook = Mockito.mock(Book.class);
        getBorrowedBooksViaReflection(reader).add(mockBook);

        assertTrue(reader.hasBorrowedBooks());
    }

    @Test
    @DisplayName("hasBorrowedBooks: false якщо список порожній")
    void hasBorrowedBooks_WhenEmpty_ShouldReturnFalse() {
        Reader reader = new Reader(1, "name");

        assertFalse(reader.hasBorrowedBooks());
    }

    @Test
    @DisplayName("getInfo: форматування без взятих книг")
    void getInfo_WhenNoBooks_ShouldReturnCorrectString() {
        Reader reader = new Reader(1, "name");

        String expected = "1\nname\nmaximum number of books to borrow: " + Const.STANDARD_MAX_NUMBER_OF_BOOKS_TO_BORROW + "\nbooks read: 0\nBorrowed books:\n";

        assertEquals(expected, reader.getInfo());
    }

    @Test
    @DisplayName("getInfo: форматування зі взятими книгами")
    void getInfo_WhenHasBooks_ShouldReturnCorrectString() throws Exception {
        Reader reader = new Reader(1, "name");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.getInfo()).thenReturn("MockBookInfo");
        getBorrowedBooksViaReflection(reader).add(mockBook);

        String expected = "1\nname\nmaximum number of books to borrow: " + Const.STANDARD_MAX_NUMBER_OF_BOOKS_TO_BORROW + "\nbooks read: 0\nBorrowed books:\nMockBookInfo\n";

        assertEquals(expected, reader.getInfo());
    }
}