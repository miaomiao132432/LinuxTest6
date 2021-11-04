import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;

public class Helloworld extends HttpServlet {

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/json;charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      Gson gson = new Gson();
      Student stu = new Student("dddd");
      String json = gson.toJson(stu);
      out.println(json);
      out.flush();
      out.close();
   }
}
