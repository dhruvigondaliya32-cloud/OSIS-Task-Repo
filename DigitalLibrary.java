import java.sql.*;
import java.util.Scanner;

public class DigitalLibrary {
    static final String DB_URL = "jdbc:mysql://localhost:3306/librarydb";
    static final String DB_USER = "dhruvi";
    static final String DB_PASS = "dhruvi123";

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println(" Connected to Digital Library");

            System.out.print("Login as (admin/user): ");
            String role = scanner.nextLine().trim().toLowerCase();

            if (role.equals("admin")) {
                adminMenu(con);
            } else if (role.equals("user")) {
                userMenu(con);
            } else {
                System.out.println("Invalid role.");
            }

            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void adminMenu(Connection con) throws SQLException {
        while (true) {
            System.out.println("\n Admin Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. View All Books");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Category: ");
                    String category = scanner.nextLine();

                    String insert = "INSERT INTO books (title, author, category) VALUES (?, ?, ?)";
                    try (PreparedStatement ps = con.prepareStatement(insert)) {
                        ps.setString(1, title);
                        ps.setString(2, author);
                        ps.setString(3, category);
                        ps.executeUpdate();
                        System.out.println(" Book added.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Book ID to delete: ");
                    int bookId = scanner.nextInt();
                    String delete = "DELETE FROM books WHERE id = ?";
                    try (PreparedStatement ps = con.prepareStatement(delete)) {
                        ps.setInt(1, bookId);
                        int rows = ps.executeUpdate();
                        System.out.println(rows > 0 ? " Book deleted." : " Book not found.");
                    }
                    break;

                case 3:
                    showBooks(con);
                    break;

                case 4:
                    return;

                default:
                    System.out.println(" Invalid choice.");
            }
        }
    }

    static void userMenu(Connection con) throws SQLException {
        while (true) {
            System.out.println("\n User Menu:");
            System.out.println("1. View Books");
            System.out.println("2. Search Book by Title");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showBooks(con);
                    break;

                case 2:
                    System.out.print("Enter title to search: ");
                    String title = scanner.nextLine();
                    String search = "SELECT * FROM books WHERE title LIKE ?";
                    try (PreparedStatement ps = con.prepareStatement(search)) {
                        ps.setString(1, "%" + title + "%");
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title") +
                                    ", Author: " + rs.getString("author") + ", Category: " + rs.getString("category"));
                        }
                    }
                    break;

                case 3:
                    return;

                default:
                    System.out.println(" Invalid choice.");
            }
        }
    }

    static void showBooks(Connection con) throws SQLException {
        String query = "SELECT * FROM books";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n Available Books:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") + ", Category: " + rs.getString("category"));
            }
        }
    }
}