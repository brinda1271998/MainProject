package com.Admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import com.connection.DatabaseConnection;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("uname");
		String password = request.getParameter("pswd");
		try {
			HttpSession hs = request.getSession();
			Connection con = DatabaseConnection.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from admin where userid='" + username + "' and password='" + password + "'");
			if (rs.next()) {
				hs.setAttribute("uname", username);
				response.sendRedirect("AdminLogin.jsp");
			} else {
				response.sendRedirect("adminLogin.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
