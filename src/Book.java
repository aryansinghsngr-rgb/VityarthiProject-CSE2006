
public class Book {
    private final String bookId;
    private String title;
    private String author;
    private boolean issued;
    private String issuedTo; 

    public Book(String bookId, String title, String author, boolean issued, String issuedTo) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.issued = issued;
        this.issuedTo = issuedTo;
    }

    public Book(String bookId, String title, String author) {
        this(bookId, title, author, false, null);
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return issued;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void markIssued(String memberId) {
        this.issued = true;
        this.issuedTo = memberId;
    }

    public void markReturned() {
        this.issued = false;
        this.issuedTo = null;
    }

    
    public String toDataLine() {
        return String.join("|",
                bookId,
                title,
                author,
                String.valueOf(issued),
                issuedTo == null ? "" : issuedTo);
    }

    
    public static Book fromDataLine(String line) {
        String[] parts = line.split("\\|", -1);
        String id = parts[0];
        String title = parts[1];
        String author = parts[2];
        boolean issued = Boolean.parseBoolean(parts[3]);
        String issuedTo = parts.length > 4 && !parts[4].isEmpty() ? parts[4] : null;
        return new Book(id, title, author, issued, issuedTo);
    }

    @Override
    public String toString() {
        String status = issued ? ("Issued to " + issuedTo) : "Available";
        return String.format("[%s] \"%s\" by %s - %s", bookId, title, author, status);
    }
}
