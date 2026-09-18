import java.util.*;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Library library = new Library("data/books.txt", "data/members.txt");

    public static void main(String[] args) {
        library.loadData();
        System.out.println("=== Library Management System ===");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addBookFlow();
                    case "2" -> addMemberFlow();
                    case "3" -> issueBookFlow();
                    case "4" -> returnBookFlow();
                    case "5" -> listBooks();
                    case "6" -> listMembers();
                    case "7" -> searchBooksFlow();
                    case "8" -> removeBookFlow();
                    case "0" -> {
                        running = false;
                        library.saveData();
                        System.out.println("Data saved. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (LibraryException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid number.");
            }

           
            if (running) {
                library.saveData();
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Add a book");
        System.out.println("2. Register a member");
        System.out.println("3. Issue a book");
        System.out.println("4. Return a book");
        System.out.println("5. List all books");
        System.out.println("6. List all members");
        System.out.println("7. Search books by title");
        System.out.println("8. Remove a book");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addBookFlow() throws LibraryException {
        System.out.print("Book ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Author: ");
        String author = scanner.nextLine().trim();
        library.addBook(id, title, author);
        System.out.println("Book added successfully.");
    }

    private static void addMemberFlow() throws LibraryException {
        System.out.print("Member ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        library.addMember(id, name);
        System.out.println("Member registered successfully.");
    }

    private static void issueBookFlow() throws LibraryException {
        System.out.print("Book ID to issue: ");
        String bookId = scanner.nextLine().trim();
        System.out.print("Member ID: ");
        String memberId = scanner.nextLine().trim();
        library.issueBook(bookId, memberId);
        System.out.println("Book issued successfully.");
    }

    private static void returnBookFlow() throws LibraryException {
        System.out.print("Book ID to return: ");
        String bookId = scanner.nextLine().trim();
        library.returnBook(bookId);
        System.out.println("Book returned successfully.");
    }

    private static void removeBookFlow() throws LibraryException {
        System.out.print("Book ID to remove: ");
        String bookId = scanner.nextLine().trim();
        library.removeBook(bookId);
        System.out.println("Book removed successfully.");
    }

    private static void listBooks() {
        Collection<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the catalog yet.");
            return;
        }
        System.out.println("--- Book Catalog ---");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static void listMembers() {
        Collection<Member> members = library.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members registered yet.");
            return;
        }
        System.out.println("--- Registered Members ---");
        for (Member member : members) {
            System.out.println(member);
        }
    }

    private static void searchBooksFlow() {
        System.out.print("Enter title keyword: ");
        String keyword = scanner.nextLine().trim();
        List<Book> results = library.searchByTitle(keyword);
        if (results.isEmpty()) {
            System.out.println("No books matched \"" + keyword + "\".");
            return;
        }
        System.out.println("--- Search Results ---");
        for (Book book : results) {
            System.out.println(book);
        }
    }
}
