package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FunderManagementAPI
 */
@WebServlet("/FunderManagementAPI")
public class FunderManagementAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	FunderManagement funderObject = new FunderManagement();
	
    public FunderManagementAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String outputString = funderObject.createPost(
				request.getParameter("title"), 
				request.getParameter("content"),
				request.getParameter("pdate"), 
				request.getParameter("ptime")
				);
		response.getWriter().write(outputString);
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Map paras = getParasMap(request); 
		
		String output = funderObject.updateItem(
				paras.get("id").toString(), 
				paras.get("title").toString(), 
				paras.get("content").toString(), 
				paras.get("pdate").toString(), 
				paras.get("ptime").toString()); 
			
				response.getWriter().write(output);
			
	}

	
	private Map getParasMap(HttpServletRequest request) {
		// TODO Auto-generated method stub
		 Map<String, String> map = new HashMap<String, String>(); 
		 
			try
			 { 
			 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
			 String queryString = scanner.hasNext() ? 
					 
			 scanner.useDelimiter("\\A").next() : ""; 
			 scanner.close();
			 
			 String[] params = queryString.split("&"); 
			 for (String param : params) 
			 { 
				 String[] p = param.split("=");
				 map.put(p[0], p[1]); 
				 } 
				 } 
				catch (Exception e) 
				 { 
					
				 } 
				return map; 
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		
		 String output = funderObject.deleteItem(paras.get("id").toString()); 
		response.getWriter().write(output);
	}

}
