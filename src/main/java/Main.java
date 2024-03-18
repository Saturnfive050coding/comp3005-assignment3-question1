import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void getAllStudents(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM students");
            ResultSet resultSet = statement.getResultSet();

            while(resultSet.next()) {
                System.out.print(resultSet.getInt("student_id") + " \t");
                System.out.print(resultSet.getString("first_name") + " ");
                System.out.print(resultSet.getString("last_name") + " \t");
                System.out.print(resultSet.getString("email") + " \t");
                System.out.println(resultSet.getString("enrollment_date"));

            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void addStudent(Connection connection) {
        String fName, lName, email;
        Date eDate;

        try {
            Scanner scan = new Scanner(System.in);

            System.out.print("Input first name: ");
            fName = scan.next();

            System.out.print("Input last name: ");
            lName = scan.next();

            System.out.print("Input email: ");
            email = scan.next();

            System.out.print("Input enrollment date: ");
            String dateString = scan.next();
            eDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

            try {
                Statement statement = connection.createStatement();
                String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                    pstmt.setString(1, fName);
                    pstmt.setString(2, lName);
                    pstmt.setString(3, email);
                    pstmt.setDate(4, new java.sql.Date(eDate.getTime()));
                    pstmt.executeUpdate();
                    System.out.println("New student added to the database");
                }

            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void updateStudentEmail(Connection connection) {
        int id;
        String email;

        try {
            Scanner scan = new Scanner(System.in);

            System.out.print("Input student id: ");
            id = scan.nextInt();

            System.out.print("Input new email: ");
            email = scan.next();



            try {
                Statement statement = connection.createStatement();
                String insertSQL = "UPDATE students SET email = ? WHERE student_id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                    pstmt.setString(1, email);
                    pstmt.setInt(2, id);
                    pstmt.executeUpdate();
                    System.out.println("Student email updated in database");
                }

            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void deleteStudent(Connection connection) {
        int id;

        try {
            Scanner scan = new Scanner(System.in);

            System.out.print("Input student id: ");
            id = scan.nextInt();



            try {
                Statement statement = connection.createStatement();
                String insertSQL = "DELETE FROM students WHERE student_id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                    System.out.println("Student deleted from database");
                }

            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        password pass = new password();
        String url = "jdbc:postgresql://localhost:5432/assignment3";
        String user = "postgres";
        Scanner scan = new Scanner(System.in);
        String password = pass.pwd;

        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            int choice;

            System.out.print("Choose One:\n1: getAllStudents()\n2: addStudent()\n3: updateStudentEmail()\n4: deleteStudent()\n> ");
            choice = scan.nextInt();

            switch(choice) {
                case 1:
                    getAllStudents(connection);
                    break;
                case 2:
                    addStudent(connection);
                    break;
                case 3:
                    updateStudentEmail(connection);
                    break;
                case 4:
                    deleteStudent(connection);
                    break;
                default:
                    System.out.println("No valid choice selected.");
            }

        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
