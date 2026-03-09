package ua.kpi.ovrmz.lab1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {

    @Test
    void exportLibrary_WhenSuccess_ShouldReturnTrueAndWriteData(@TempDir Path tempDir) throws Exception {
        Path libPath = tempDir.resolve("lib.txt");
        Path readersPath = tempDir.resolve("readers.txt");
        Path booksPath = tempDir.resolve("books.txt");

        Library mockLibrary = Mockito.mock(Library.class);
        Mockito.when(mockLibrary.toString()).thenReturn("libraryName\nlibrarianName\nhours");

        Reader mockReader1 = Mockito.mock(Reader.class);
        Mockito.when(mockReader1.getNumberOfReadBooks()).thenReturn(5);
        Mockito.when(mockReader1.toString()).thenReturn("reader1");

        Reader mockReader2 = Mockito.mock(Reader.class);
        Mockito.when(mockReader2.getNumberOfReadBooks()).thenReturn(10);
        Mockito.when(mockReader2.toString()).thenReturn("reader2");

        ArrayList<Reader> readers = new ArrayList<>();
        readers.add(mockReader1);
        readers.add(mockReader2);
        Mockito.when(mockLibrary.getReaders()).thenReturn(readers);

        Book mockBook1 = Mockito.mock(Book.class);
        Mockito.when(mockBook1.getBorrowCount()).thenReturn(2);
        Mockito.when(mockBook1.toString()).thenReturn("book1");

        ArrayList<Book> books = new ArrayList<>();
        books.add(mockBook1);
        Mockito.when(mockLibrary.getBooks()).thenReturn(books);

        boolean result = DataManager.exportLibrary(mockLibrary, libPath.toString(), readersPath.toString(), booksPath.toString());

        assertTrue(result);
        assertEquals("libraryName\nlibrarianName\nhours", Files.readString(libPath));
        assertEquals("reader2\nreader1\n*", Files.readString(readersPath));
        assertEquals("book1\n*", Files.readString(booksPath));
    }

    @Test
    void exportLibrary_WhenException_ShouldReturnFalse() {
        Library mockLibrary = Mockito.mock(Library.class);

        boolean result = DataManager.exportLibrary(mockLibrary, null, null, null);

        assertFalse(result);
    }

    @Test
    void importLibrary_WhenSuccess_ShouldReturnLibrary(@TempDir Path tempDir) throws Exception {
        Path libPath = tempDir.resolve("lib.txt");
        Path readersPath = tempDir.resolve("readers.txt");
        Path booksPath = tempDir.resolve("books.txt");

        Files.writeString(libPath, "libraryName\nlibrarianName\nhours");

        String booksData = "1\nisbn1\ntitle1\nauthor1\n5\nAVAILABLE\n\n*";
        Files.writeString(booksPath, booksData);

        String readersData = "1\nname1\n10\n5\n1\n\n*";
        Files.writeString(readersPath, readersData);

        Library result = DataManager.importLibrary(libPath.toString(), readersPath.toString(), booksPath.toString());

        assertNotNull(result);
        assertEquals("libraryName", result.getName());
        assertEquals("librarianName", result.getCurrentLibrarianName());
        assertEquals("hours", result.getOpeningHours());
        assertEquals(1, result.getBooks().size());
        assertEquals(1, result.getReaders().size());
    }

    @Test
    void importLibrary_WhenException_ShouldReturnNull() {
        Library result = DataManager.importLibrary("invalidPath", "invalidPath", "invalidPath");

        assertNull(result);
    }
}