# Project Report: Library Management System

## 1. Introduction

The Library Management System is a command-line Java application built to
simulate the core operations of a small library: cataloging books,
registering members, and handling the issue/return workflow. It was built
as a course project for Programming in Java, with the goal of applying
object-oriented design, the Java Collections Framework, exception handling,
and file-based persistence in a single cohesive, runnable program.

## 2. Objectives

- Model real-world entities (books, members) using classes and objects.
- Practice encapsulation by keeping fields private and exposing behavior
  through methods.
- Use a custom checked exception to enforce business rules cleanly.
- Persist application state across runs without relying on a database.
- Build a usable, fully text-based (no GUI) command-line interface.

## 3. System Design

### 3.1 Class Overview

| Class              | Responsibility                                                        |
|---------------------|-------------------------------------------------------------------------|
| `Book`               | Represents a book (ID, title, author, issue status, borrower).          |
| `Member`              | Represents a library member (ID, name, count of books currently held). |
| `Library`              | Core service layer: manages collections of books/members, enforces rules, reads/writes data files. |
| `LibraryException`      | Custom checked exception for all business-rule violations.          |
| `Main`                    | Command-line interface: menu loop, user input, delegates to `Library`. |

### 3.2 Data Flow

1. On startup, `Main` calls `Library.loadData()`, which reads
   `data/books.txt` and `data/members.txt` (if present) into in-memory
   `LinkedHashMap` collections keyed by ID.
2. The user interacts through a numbered menu loop in `Main`.
3. Each action (add book, issue book, etc.) is delegated to a corresponding
   method on `Library`, which validates the request and either performs the
   action or throws a `LibraryException` describing what went wrong.
4. After each successful action, `Library.saveData()` writes the current
   state back to the text files, so no data is lost if the program exits
   unexpectedly.

### 3.3 Business Rules Enforced

- A book ID / member ID must be unique when added.
- A book cannot be issued if it is already issued to someone else.
- A member cannot borrow more than 3 books at a time.
- A book cannot be removed from the catalog while it is currently issued.
- Returning or removing a book that doesn't exist raises a clear error
  instead of a crash.

## 4. Implementation Details

- **Language / Version:** Java 17+ (standard library only, no external
  dependencies).
- **Persistence format:** plain-text, pipe-delimited (`|`) rows — one per
  book or member — chosen for simplicity and human-readability over a full
  database or serialized binary format.
- **Collections used:** `LinkedHashMap<String, Book>` and
  `LinkedHashMap<String, Member>` for O(1) lookup by ID while preserving
  insertion order for listing.
- **Exception handling:** all validation failures throw `LibraryException`
  (a checked exception), which is caught centrally in `Main`'s menu loop so
  the program never crashes on invalid input; `NumberFormatException` is
  also caught defensively.

## 5. Testing

The application was tested manually by exercising each menu option in
sequence: adding books and members, issuing a book to a member, attempting
to issue an already-issued book (correctly rejected), issuing books past
the 3-book limit (correctly rejected), returning a book, removing an
unissued book, attempting to remove an issued book (correctly rejected),
and verifying that data persisted correctly across program restarts by
inspecting `data/books.txt` and `data/members.txt`.

## 6. Limitations & Future Work

- No due-date or fine-calculation logic (could be added with `java.time`).
- No authentication/roles (e.g., librarian vs. member).
- Data files are not thread-safe; the application assumes single-user,
  single-process use.
- Could be extended with a search-by-author feature or CSV export.

## 7. Conclusion

The project meets its objective of demonstrating core Java concepts —
classes and objects, encapsulation, collections, exception handling, and
file I/O — in a small but complete, runnable command-line application.
