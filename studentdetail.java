import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class P9_DatabaseAccess extends HttpServlet {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ise"; // Specify port number if needed
    private static final String USER = "root";
    private static final String PASS = "root";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "Database Result";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
        
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Execute a query
            stmt = conn.createStatement();
            String sql = "SELECT * FROM emp";
            rs = stmt.executeQuery(sql);
            
            // Extract data from result set
            out.println("<table border=1>");
            out.println("<tr><th>ID</th><th>Name</th><th>Age</th></tr>");
            while (rs.next()) {
                int id = rs.getInt("ID"); // Assuming 'ID' is the column name
                String name = rs.getString("Name"); // Assuming 'Name' is the column name
                int age = rs.getInt("Age"); // Assuming 'Age' is the column name
                out.println("<tr><td>" + id + "</td><td>" + name + "</td><td>" + age + "</td></tr>");
            }
            out.println("</table>");
        } catch (SQLException se) {
            // Handle errors for JDBC
            out.println("SQL Exception: " + se.getMessage());
            se.printStackTrace(out);
        } catch (Exception e) {
            // Handle errors for Class.forName
            out.println("Exception: " + e.getMessage());
            e.printStackTrace(out);
        } finally {
            // Clean-up environment
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                out.println("Error closing resources: " + se.getMessage());
                se.printStackTrace(out);
            }
        }

        out.println("</body></html>");
    }
}
