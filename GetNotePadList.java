package com.example;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.google.gson.reflect.TypeToken;

public class GetNotePadList extends HttpServlet {
   final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   final static String URL = "jdbc:mysql://116.62.123.4/linux_final";
   final static String USER = "miaomiao";
   final static String PASS = "xxxxxxx";
   final static String SQL_QURERY_ALL_NOTEPAD = "SELECT * FROM t_notepad;";

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("application/json;charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();

      List<Notepad> stuList = getAllNotepad();
      Gson gson = new Gson();
      String json = gson.toJson(stuList, new TypeToken<List<Notepad>>() {
      }.getType());
      out.println(json);
      out.flush();
      out.close();
   }

   private List<Notepad> getAllNotepad() {
      List<Notepad> padList = new ArrayList<Notepad>();
      Connection conn = null;
      Statement stmt = null;
      try {
         Class.forName(JDBC_DRIVER);
         conn = DriverManager.getConnection(URL, USER, PASS);
         stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(SQL_QURERY_ALL_NOTEPAD);
         while (rs.next()) {
            Notepad note = new Notepad();
            note.setId(rs.getInt("id"));
            note.setNotepadContent(rs.getString("notepad_content"));
            note.setNotepadTime(rs.getString("notepad_time"));
            padList.add(note);
         }
         rs.close();
         stmt.close();
         conn.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (stmt != null)
               stmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }

      return padList;
   }
}
