package com.example;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@WebServlet(urlPatterns = "/GetStudentList")
public class GetStudentList extends HttpServlet {
   final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   final static String URL = "jdbc:mysql://116.62.123.4/linux_final";
   final static String USER = "miaomiao";
   final static String PASS = "Mgh=1/2Mv2";
   final static String SQL_QURERY_ALL_STUDENT = "SELECT * FROM t_student;";
   Connection conn = null;

   public void init() {
      try {
         Class.forName(JDBC_DRIVER);
         conn = DriverManager.getConnection(URL, USER, PASS);
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

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();

      List<Student> stuList = getAllStudent();
      Gson gson = new Gson();
      String json = gson.toJson(stuList, new TypeToken<List<Student>>() {
      }.getType());
      out.println(json);
      out.flush();
      out.close();
   }

   private List<Student> getAllStudent() {
      List<Student> stuList = new ArrayList<Student>();
      Statement stmt = null;
      try {
         stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL_QURERY_ALL_STUDENT);
         while (rs.next()) {
            Student stu = new Student();
            stu.id = rs.getInt("id");
            stu.name = rs.getString("name");
            stu.age = rs.getInt("age");
            stuList.add(stu);
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

      return stuList;
   }
}
