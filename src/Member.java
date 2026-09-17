/**
 * Represents a registered library member.
 */
public class Member {
    private final String memberId;
    private String name;
    private int booksIssuedCount;

    public Member(String memberId, String name, int booksIssuedCount) {
        this.memberId = memberId;
        this.name = name;
        this.booksIssuedCount = booksIssuedCount;
    }

    public Member(String memberId, String name) {
        this(memberId, name, 0);
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public int getBooksIssuedCount() {
        return booksIssuedCount;
    }

    public void incrementIssuedCount() {
        booksIssuedCount++;
    }

    public void decrementIssuedCount() {
        if (booksIssuedCount > 0) {
            booksIssuedCount--;
        }
    }

    /**
     * Format: id|name|booksIssuedCount
     */
    public String toDataLine() {
        return String.join("|", memberId, name, String.valueOf(booksIssuedCount));
    }

    public static Member fromDataLine(String line) {
        String[] parts = line.split("\\|", -1);
        return new Member(parts[0], parts[1], Integer.parseInt(parts[2]));
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %d book(s) issued", memberId, name, booksIssuedCount);
    }
}
