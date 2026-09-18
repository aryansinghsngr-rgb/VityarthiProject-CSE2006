import java.io.*;
import java.util.*;


public class Library {

    private static final int MAX_BOOKS_PER_MEMBER = 3;

    private final Map<String, Book> books = new LinkedHashMap<>();
    private final Map<String, Member> members = new LinkedHashMap<>();

    private final String booksFilePath;
    private final String membersFilePath;

    public Library(String booksFilePath, String membersFilePath) {
        this.booksFilePath = booksFilePath;
        this.membersFilePath = membersFilePath;
    }

    

    public void loadData() {
        loadBooks();
        loadMembers();
    }

    private void loadBooks() {
        File file = new File(booksFilePath);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                Book book = Book.fromDataLine(line);
                books.put(book.getBookId(), book);
            }
        } catch (IOException e) {
            System.out.println("Warning: could not read books file (" + e.getMessage() + ")");
        }
    }

    private void loadMembers() {
        File file = new File(membersFilePath);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                Member member = Member.fromDataLine(line);
                members.put(member.getMemberId(), member);
            }
        } catch (IOException e) {
            System.out.println("Warning: could not read members file (" + e.getMessage() + ")");
        }
    }

    public void saveData() {
        saveBooks();
        saveMembers();
    }

    private void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(booksFilePath))) {
            for (Book book : books.values()) {
                writer.write(book.toDataLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save books file (" + e.getMessage() + ")");
        }
    }

    private void saveMembers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(membersFilePath))) {
            for (Member member : members.values()) {
                writer.write(member.toDataLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save members file (" + e.getMessage() + ")");
        }
    }

   

    public void addBook(String id, String title, String author) throws LibraryException {
        if (books.containsKey(id)) {
            throw new LibraryException("A book with ID '" + id + "' already exists.");
        }
        books.put(id, new Book(id, title, author));
    }

    public void removeBook(String id) throws LibraryException {
        Book book = books.get(id);
        if (book == null) {
            throw new LibraryException("No book found with ID '" + id + "'.");
        }
        if (book.isIssued()) {
            throw new LibraryException("Cannot remove book '" + id + "' — it is currently issued.");
        }
        books.remove(id);
    }

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    public List<Book> searchByTitle(String keyword) {
        List<Book> results = new ArrayList<>();
        String lower = keyword.toLowerCase();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(lower)) {
                results.add(book);
            }
        }
        return results;
    }

    
    public void addMember(String id, String name) throws LibraryException {
        if (members.containsKey(id)) {
            throw new LibraryException("A member with ID '" + id + "' already exists.");
        }
        members.put(id, new Member(id, name));
    }

    public Collection<Member> getAllMembers() {
        return members.values();
    }

    

    public void issueBook(String bookId, String memberId) throws LibraryException {
        Book book = books.get(bookId);
        if (book == null) {
            throw new LibraryException("No book found with ID '" + bookId + "'.");
        }
        Member member = members.get(memberId);
        if (member == null) {
            throw new LibraryException("No member found with ID '" + memberId + "'.");
        }
        if (book.isIssued()) {
            throw new LibraryException("Book '" + book.getTitle() + "' is already issued to member " + book.getIssuedTo() + ".");
        }
        if (member.getBooksIssuedCount() >= MAX_BOOKS_PER_MEMBER) {
            throw new LibraryException("Member '" + member.getName() + "' has reached the limit of "
                    + MAX_BOOKS_PER_MEMBER + " books.");
        }

        book.markIssued(memberId);
        member.incrementIssuedCount();
    }

    public void returnBook(String bookId) throws LibraryException {
        Book book = books.get(bookId);
        if (book == null) {
            throw new LibraryException("No book found with ID '" + bookId + "'.");
        }
        if (!book.isIssued()) {
            throw new LibraryException("Book '" + book.getTitle() + "' is not currently issued.");
        }

        Member member = members.get(book.getIssuedTo());
        book.markReturned();
        if (member != null) {
            member.decrementIssuedCount();
        }
    }
}
