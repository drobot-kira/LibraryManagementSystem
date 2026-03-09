package ua.kpi.ovrmz.lab1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private HashMap<Integer, Reader> getReadersViaReflection(Library library) throws Exception {
        Field field = Library.class.getDeclaredField("readers");
        field.setAccessible(true);
        return (HashMap<Integer, Reader>) field.get(library);
    }

    private HashMap<String, ArrayList<Book>> getBooksViaReflection(Library library) throws Exception {
        Field field = Library.class.getDeclaredField("books");
        field.setAccessible(true);
        return (HashMap<String, ArrayList<Book>>) field.get(library);
    }

    @Test
    @DisplayName("addReader: adds new reader")
    void addReader_ShouldAddReaderToMap() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Reader mockReader = Mockito.mock(Reader.class);
        Mockito.when(mockReader.getId()).thenReturn(1);

        library.addReader(mockReader);

        assertTrue(getReadersViaReflection(library).containsKey(1));
    }

    @Test
    @DisplayName("addBook: new ISBN")
    void addBook_WhenNewIsbn_ShouldAddListAndBook() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.getIsbn()).thenReturn("isbn1");

        library.addBook(mockBook);

        assertTrue(getBooksViaReflection(library).containsKey("isbn1"));
        assertTrue(getBooksViaReflection(library).get("isbn1").contains(mockBook));
    }

    @Test
    @DisplayName("addBook: existing ISBN")
    void addBook_WhenExistingIsbn_ShouldAddToList() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Book mockBook1 = Mockito.mock(Book.class);
        Book mockBook2 = Mockito.mock(Book.class);
        Mockito.when(mockBook1.getIsbn()).thenReturn("isbn1");
        Mockito.when(mockBook2.getIsbn()).thenReturn("isbn1");
        getBooksViaReflection(library).put("isbn1", new ArrayList<>());
        getBooksViaReflection(library).get("isbn1").add(mockBook1);

        library.addBook(mockBook2);

        assertEquals(2, getBooksViaReflection(library).get("isbn1").size());
        assertTrue(getBooksViaReflection(library).get("isbn1").contains(mockBook2));
    }

    @Test
    @DisplayName("deleteReader: successfully deletes")
    void deleteReader_WhenNoBorrowedBooks_ShouldDelete() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Reader mockReader = Mockito.mock(Reader.class);
        Mockito.when(mockReader.hasBorrowedBooks()).thenReturn(false);
        getReadersViaReflection(library).put(1, mockReader);

        library.deleteReader(1);

        assertFalse(getReadersViaReflection(library).containsKey(1));
    }

    @Test
    @DisplayName("deleteReader: throws exception when has books")
    void deleteReader_WhenHasBorrowedBooks_ShouldThrowException() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Reader mockReader = Mockito.mock(Reader.class);
        Mockito.when(mockReader.hasBorrowedBooks()).thenReturn(true);
        getReadersViaReflection(library).put(1, mockReader);

        assertThrows(IllegalStateException.class, () -> library.deleteReader(1));
    }

    @Test
    @DisplayName("deleteBook: successfully deletes")
    void deleteBook_WhenAvailable_ShouldDelete() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.getId()).thenReturn(1);
        Mockito.when(mockBook.getStatus()).thenReturn(Const.BOOK_STATUS_AVAILABLE);
        ArrayList<Book> list = new ArrayList<>();
        list.add(mockBook);
        getBooksViaReflection(library).put("isbn1", list);

        library.deleteBook("isbn1", 1);

        assertFalse(getBooksViaReflection(library).get("isbn1").contains(mockBook));
    }

    @Test
    @DisplayName("deleteBook: throws exception when borrowed")
    void deleteBook_WhenBorrowed_ShouldThrowException() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.getId()).thenReturn(1);
        Mockito.when(mockBook.getStatus()).thenReturn(Const.BOOK_STATUS_BORROWED);
        ArrayList<Book> list = new ArrayList<>();
        list.add(mockBook);
        getBooksViaReflection(library).put("isbn1", list);

        assertThrows(IllegalStateException.class, () -> library.deleteBook("isbn1", 1));
    }

    @Test
    @DisplayName("UpdateReader: returns true and updates")
    void updateReader_WhenExists_ShouldUpdateAndReturnTrue() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Reader mockReader = Mockito.mock(Reader.class);
        getReadersViaReflection(library).put(1, mockReader);

        boolean result = library.UpdateReader(1, "newReaderName");

        assertTrue(result);
        Mockito.verify(mockReader).setName("newReaderName");
    }

    @Test
    @DisplayName("UpdateReader: returns false when not exists")
    void updateReader_WhenNotExists_ShouldReturnFalse() {
        Library library = new Library("libraryName", "librarianName", "hours");

        boolean result = library.UpdateReader(1, "newReaderName");

        assertFalse(result);
    }

    @Test
    @DisplayName("UpdateBook: returns true and updates")
    void updateBook_WhenExists_ShouldUpdateAndReturnTrue() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.getId()).thenReturn(1);
        ArrayList<Book> list = new ArrayList<>();
        list.add(mockBook);
        getBooksViaReflection(library).put("isbn1", list);

        boolean result = library.UpdateBook("isbn1", 1, "newTitle", "newAuthor");

        assertTrue(result);
        Mockito.verify(mockBook).setTitle("newTitle");
        Mockito.verify(mockBook).setAuthor("newAuthor");
    }

    @Test
    @DisplayName("UpdateBook: returns false when not exists")
    void updateBook_WhenIsbnNotExists_ShouldReturnFalse() {
        Library library = new Library("libraryName", "librarianName", "hours");

        boolean result = library.UpdateBook("isbn1", 1, "newTitle", "newAuthor");

        assertFalse(result);
    }

    @Test
    @DisplayName("getInfo: returns formatted string")
    void getInfo_ShouldReturnCorrectString() {
        Library library = new Library("libraryName", "librarianName", "hours");

        String expected = "libraryName\nCurrent librarian: librarianName\nOpening hours:\nhours";

        assertEquals(expected, library.getInfo());
    }

    @Test
    @DisplayName("getReaderInfo: returns info when exists")
    void getReaderInfo_WhenExists_ShouldReturnInfo() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Reader mockReader = Mockito.mock(Reader.class);
        Mockito.when(mockReader.getInfo()).thenReturn("readerInfo");
        getReadersViaReflection(library).put(1, mockReader);

        assertEquals("readerInfo", library.getReaderInfo(1));
    }

    @Test
    @DisplayName("getReaderInfo: returns error when not exists")
    void getReaderInfo_WhenNotExists_ShouldReturnError() {
        Library library = new Library("libraryName", "librarianName", "hours");

        assertEquals("No reader found with id 1", library.getReaderInfo(1));
    }

    @Test
    @DisplayName("getAllReadersInfo: returns all info")
    void getAllReadersInfo_ShouldReturnAllInfo() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Reader mockReader = Mockito.mock(Reader.class);
        Mockito.when(mockReader.getInfo()).thenReturn("readerInfo");
        getReadersViaReflection(library).put(1, mockReader);

        assertEquals("readerInfo\n\n", library.getAllReadersInfo());
    }

    @Test
    @DisplayName("getBookInfo: returns error when not exists")
    void getBookInfo_WhenNotExists_ShouldReturnError() {
        Library library = new Library("libraryName", "librarianName", "hours");

        assertEquals("No book found with ISBN isbn1", library.getBookInfo("isbn1"));
    }

    @Test
    @DisplayName("getBookInfo: returns info of available book")
    void getBookInfo_WhenAvailable_ShouldReturnInfo() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.getStatus()).thenReturn(Const.BOOK_STATUS_AVAILABLE);
        Mockito.when(mockBook.getInfo()).thenReturn("bookInfo");
        ArrayList<Book> list = new ArrayList<>();
        list.add(mockBook);
        getBooksViaReflection(library).put("isbn1", list);

        assertEquals("bookInfo", library.getBookInfo("isbn1"));
    }

    @Test
    @DisplayName("getAllBooksInfo: returns all info")
    void getAllBooksInfo_ShouldReturnAllInfo() throws Exception {
        Library library = new Library("libraryName", "librarianName", "hours");
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBook.getInfo()).thenReturn("bookInfo");
        ArrayList<Book> list = new ArrayList<>();
        list.add(mockBook);
        getBooksViaReflection(library).put("isbn1", list);

        assertEquals("bookInfo\n\n", library.getAllBooksInfo());
    }
}