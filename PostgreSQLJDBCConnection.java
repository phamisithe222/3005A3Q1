import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PostgreSQLJDBCConnection {
    static Connection conn = null;
    public static void main(String[] args) {
        // JDBC & Database credentials
        String url = "jdbc:postgresql://localhost:5432/a3q1";
        String user = "postgres";
        String password = "postgres";
        
        try {
            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
            
            // Connect to the database
            conn = DriverManager.getConnection(url, user, password);
            
            if (conn != null) {
                System.out.println("Connected to PostgreSQL successfully!");
            } else {
                System.out.println("Failed to establish connection.");
            }
            
            // Close the connection (in a real scenario, do this in a finally block)
            //conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        //getAllStudents();
        //addStudent("Ethan", "P", "asdasldjklas@gmail.com", Date.valueOf("2024-12-12"));
        //getAllStudents();
        //updateStudentEmail(6,"updated@gmail.com");
        //getAllStudents();
        deleteStudent(6);
        getAllStudents();
        
    }

    public static void getAllStudents(){
        String SQL = "SELECT * FROM students";

        try{
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int studentId = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                Date enrollmentDate = rs.getDate("enrollment_date");

                // print formatted information
                System.out.println(
                        studentId + " " + firstName+ " " +  lastName+ " " +  email+ " " +  enrollmentDate);
            }

        } catch (SQLException e){
            e.printStackTrace();

        }
    }

    public static void addStudent(String firstName, String lastName, String email, Date enrollmentDate) {
            String SQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
            try  {
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, email);
                pstmt.setDate(4, enrollmentDate);
                pstmt.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();

        }
    }   

    public static void updateStudentEmail(int studentId, String email){
        String SQL = "UPDATE students SET email = ? WHERE student_id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, email);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }


    }

    public static void deleteStudent(int studentId){
        String SQL = "DELETE FROM students WHERE student_id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();

        }

    }

}
