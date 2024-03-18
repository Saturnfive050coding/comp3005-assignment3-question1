import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void getAllStudents(Connection connection) {
        try {
        	  //Executes the query that gets every student in the students table
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM students");
            ResultSet resultSet = statement.getResultSet();
						
						//Runs a loop that outputs every student in the students table to the terminal
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
						
						//Obtain student info from the user
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
            		//Prepare the SQL statement
                Statement statement = connection.createStatement();
                String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
                //Add in the parameters provided by the user and execute
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
						
						//Obtain the student id and new email value
            System.out.print("Input student id: ");
            id = scan.nextInt();

            System.out.print("Input new email: ");
            email = scan.next();



            try {
            		//Prepare the SQL statement
                Statement statement = connection.createStatement();
                String insertSQL = "UPDATE students SET email = ? WHERE student_id = ?";
                //Add in the parameters provided by the user and execute
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

						//Obtain the student id
            System.out.print("Input student id: ");
            id = scan.nextInt();



            try {
            		//Prepare the SQL statement
                Statement statement = connection.createStatement();
                String insertSQL = "DELETE FROM students WHERE student_id = ?";
                //Add in the parameters obtained from the user
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
    		//Set all the initial variables
        password pass = new password();
        String url = "jdbc:postgresql://localhost:5432/assignment3";
        String user = "postgres";
        Scanner scan = new Scanner(System.in);
        //Password is stored in another class that is not uploaded to keep it private
        String password = pass.pwd;

				//Try to connect to the database
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            int choice;
						
						//Ask the user which function they would like to run
            System.out.print("Choose One:\n1: getAllStudents()\n2: addStudent()\n3: updateStudentEmail()\n4: deleteStudent()\n> ");
            choice = scan.nextInt();

						//Execute the given result, or end the program if none of the options are selected
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
