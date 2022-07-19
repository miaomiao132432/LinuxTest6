import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import redis.clients.jedis.Jedis;

@WebServlet(urlPatterns = "/Text3")
public class Text3 extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://116.62.123.4/linux_final";
    static final String USER = "miaomiao";
    static final String PASS = "";
 //阿巴阿巴
   static Connection conn = null;
   static Jedis jedis = null;
   public void init() {
      try {
         Class.forName(JDBC_DRIVER);
         conn = DriverManager.getConnection(DB_URL, USER, PASS);
         jedis = new Jedis("116.62.123.4"); 

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void destroy() {
      try {
         conn.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          String name = jedis.get("name"); 
     
          if (name == null) {
            Student stu = getStudent();
            jedis.set("name", stu.name);
 
            out.println("<h1>hello world, " + stu.name + "</h1>");
          } else {
             out.println("<h1>hello world, " + name + "</h1>");
           }
    }
    
    public Student getStudent() {
        Student stu = new Student();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM t_student WHERE id=4";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                stu.id = rs.getInt("id");
                stu.name = rs.getString("name");
            }
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    return stu;
   
    }

}


class Student {

    public String name;
    public int id;

}
