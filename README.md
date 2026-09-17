# Library Management System

A simple command-line **Library Management System** written in core Java.
It lets you add books, register members, issue and return books, search the
catalog, and persists everything to plain text files so your data survives
between runs.

## Features

- Add / remove books from the catalog
- Register library members
- Issue a book to a member (enforces a 3-book-per-member limit)
- Return a book
- Search books by title keyword
- List all books / all members
- Data is saved automatically to `data/books.txt` and `data/members.txt`
- Custom checked exception (`LibraryException`) used for all business-rule
  violations (duplicate IDs, book already issued, member limit reached, etc.)

## Project Structure

```
LibraryManagementSystem/
├── src/
│   ├── Main.java              # CLI entry point / menu
│   ├── Library.java           # Core business logic + file persistence
│   ├── Book.java               # Book model
│   ├── Member.java             # Member model
│   └── LibraryException.java   # Custom checked exception
├── data/                       # Created automatically at runtime
│   ├── books.txt
│   └── members.txt
└── README.md
```

## Requirements

- **Java Development Kit (JDK) 17 or later** (uses the arrow-style
  `switch` syntax introduced in Java 14+).
  Check your version with:
  ```
  java -version
  ```
  If you don't have a JDK installed:
  - **Windows/macOS/Linux:** download from
    [https://adoptium.net](https://adoptium.net) (Temurin JDK 17+) and
    follow the installer instructions.
  - **Ubuntu/Debian:** `sudo apt install openjdk-17-jdk`
  - **macOS (Homebrew):** `brew install openjdk@17`

No external libraries or build tools are required — this project uses only
the Java standard library.

## Setup & Run (Command Line)

1. **Clone the repository**
   ```
   git clone https://github.com/<your-username>/<your-repo-name>.git
   cd <your-repo-name>
   ```

2. **Compile the source files**
   ```
   mkdir -p bin
   javac -d bin src/*.java
   ```
   This compiles all `.java` files in `src/` and places the resulting
   `.class` files in `bin/`.

3. **Run the program**
   ```
   cd bin
   java Main
   ```
   > Note: the program reads/writes data relative to the directory you run
   > it from (it looks for a `data/` folder there). Run it from the project
   > root instead if you'd like `data/` to stay at the top level, e.g.:
   > ```
   > javac -d bin src/*.java
   > java -cp bin Main
   > ```

4. **Use the menu**
   On startup you'll see a numbered menu, e.g.:
   ```
   === Library Management System ===

   1. Add a book
   2. Register a member
   3. Issue a book
   4. Return a book
   5. List all books
   6. List all members
   7. Search books by title
   8. Remove a book
   0. Exit
   Choose an option:
   ```
   Enter the number of the action you want and follow the prompts. Data is
   saved to `data/books.txt` and `data/members.txt` after every action, and
   reloaded automatically the next time you run the program.

## Example Session

```
Choose an option: 1
Book ID: B001
Title: The Pragmatic Programmer
Author: Hunt & Thomas
Book added successfully.

Choose an option: 2
Member ID: M001
Name: Asha Verma
Member registered successfully.

Choose an option: 3
Book ID to issue: B001
Member ID: M001
Book issued successfully.

Choose an option: 5
--- Book Catalog ---
[B001] "The Pragmatic Programmer" by Hunt & Thomas - Issued to M001
```

## Notes on Design

- **Persistence** uses simple pipe-delimited (`|`) text files rather than a
  database, keeping the project dependency-free and easy to run anywhere a
  JDK is installed.
- **Exception handling**: all invalid operations (duplicate IDs, issuing an
  already-issued book, exceeding the borrow limit, etc.) throw a custom
  `LibraryException`, which `Main` catches and reports without crashing.
- **Collections**: `LinkedHashMap` is used to store books and members so
  catalog/member listing order matches insertion order.
