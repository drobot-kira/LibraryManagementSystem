package ua.kpi.ovrmz.lab1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private void setStatusViaReflection(Book targetBook, String newStatus) throws Exception {
        Field statusField = Book.class.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(targetBook, newStatus);
    }

    private void setBorrowCountViaReflection(Book targetBook, int newCount) throws Exception {
        Field countField = Book.class.getDeclaredField("borrowCount");
        countField.setAccessible(true);
        countField.set(targetBook, newCount);
    }

    @Test
    @DisplayName("Успішна видача книги: статус AVAILABLE")
    void borrowBook_WhenAvailable_ShouldReturnTrueAndChangeState() {
        Book book = new Book(1, "978-3-16-148410-0", "Clean Code", "Robert C. Martin");

        boolean result = book.borrowBook();

        assertTrue(result);
        assertEquals(1, book.getBorrowCount());
        assertEquals(Const.BOOK_STATUS_BORROWED, book.getStatus());
    }

    @Test
    @DisplayName("Відмова у видачі: книга вже видана (статус BORROWED)")
    void borrowBook_WhenAlreadyBorrowed_ShouldReturnFalse() throws Exception {
        Book book = new Book(1, "978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        setStatusViaReflection(book, Const.BOOK_STATUS_BORROWED);
        setBorrowCountViaReflection(book, 1);

        boolean result = book.borrowBook();

        assertFalse(result);
        assertEquals(1, book.getBorrowCount());
    }

    @Test
    @DisplayName("Відмова у видачі: контрольний примірник (статус CHECK_COPY)")
    void borrowBook_WhenCheckCopy_ShouldReturnFalse() throws Exception {
        Book book = new Book(1, "978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        setStatusViaReflection(book, Const.BOOK_STATUS_CHECK_COPY);

        boolean result = book.borrowBook();

        assertFalse(result);
        assertEquals(0, book.getBorrowCount());
    }

    @Test
    @DisplayName("Викидання винятку: некоректний статус книги")
    void borrowBook_WhenStatusIsInvalid_ShouldThrowException() throws Exception {
        Book book = new Book(1, "978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        String invalidStatus = "LOST_IN_SPACE";
        setStatusViaReflection(book, invalidStatus);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> book.borrowBook());
        assertTrue(exception.getMessage().contains(invalidStatus));
    }

    @Test
    @DisplayName("Успішне повернення: книга була видана (статус BORROWED)")
    void returnBook_WhenBorrowed_ShouldReturnTrueAndChangeState() throws Exception {
        Book book = new Book(1, "978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        setStatusViaReflection(book, Const.BOOK_STATUS_BORROWED);

        boolean result = book.returnBook();

        assertTrue(result);
        assertEquals(Const.BOOK_STATUS_AVAILABLE, book.getStatus());
    }

    @Test
    @DisplayName("Відмова у поверненні: книга вже в бібліотеці (статус AVAILABLE)")
    void returnBook_WhenAlreadyAvailable_ShouldReturnFalse() {
        Book book = new Book(1, "978-3-16-148410-0", "Clean Code", "Robert C. Martin");

        boolean result = book.returnBook();

        assertFalse(result);
        assertEquals(Const.BOOK_STATUS_AVAILABLE, book.getStatus());
    }

    @Test
    @DisplayName("Відмова у поверненні: контрольний примірник (статус CHECK_COPY)")
    void returnBook_WhenCheckCopy_ShouldReturnFalse() throws Exception {
        Book book = new Book(1, "978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        setStatusViaReflection(book, Const.BOOK_STATUS_CHECK_COPY);

        boolean result = book.returnBook();

        assertFalse(result);
        assertEquals(Const.BOOK_STATUS_CHECK_COPY, book.getStatus());
    }

    @Test
    @DisplayName("Викидання винятку: некоректний статус при поверненні")
    void returnBook_WhenStatusIsInvalid_ShouldThrowException() throws Exception {
        Book book = new Book(1, "978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        setStatusViaReflection(book, "UNKNOWN_STATUS");

        assertThrows(IllegalArgumentException.class, () -> book.returnBook());
    }

    @Test
    @DisplayName("Отримання інформації: нова книга (статус AVAILABLE, лічильник 0)")
    void getInfo_WhenNewBook_ShouldReturnCorrectFormattedString() {
        Book book = new Book(1, "978-3-16-148410-0", "Clean Code", "Robert C. Martin");

        String actualInfo = book.getInfo();

        String expectedInfo = "978-3-16-148410-0\n" +
                "Robert C. Martin \"Clean Code\"\n" +
                "borrowed: 0\n" +
                "status: " + Const.BOOK_STATUS_AVAILABLE;

        assertEquals(expectedInfo, actualInfo);
    }

    @Test
    @DisplayName("Отримання інформації: видана книга (статус BORROWED, лічильник 1)")
    void getInfo_WhenBorrowedBook_ShouldReturnCorrectFormattedString() throws Exception {
        Book book = new Book(1, "978-3-16-148410-0", "Clean Code", "Robert C. Martin");
        setStatusViaReflection(book, Const.BOOK_STATUS_BORROWED);
        setBorrowCountViaReflection(book, 1);

        String actualInfo = book.getInfo();

        String expectedInfo = "978-3-16-148410-0\n" +
                "Robert C. Martin \"Clean Code\"\n" +
                "borrowed: 1\n" +
                "status: " + Const.BOOK_STATUS_BORROWED;

        assertEquals(expectedInfo, actualInfo);
    }
}