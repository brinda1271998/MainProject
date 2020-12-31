package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import com.helper.Validate;
import com.model.BookingModel;


/**
 * Servlet implementation class BookTicketController
 */

@WebServlet("/BookTicketFormController")
public class BookTicketFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookTicketFormController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BookingModel model = new BookingModel();
		HttpSession session = request.getSession();
		if (!String.valueOf(session.getAttribute("type")).equals("user")) {
			model.setBusID(request.getParameter("busid"));
			model.setDepartureDate(request.getParameter("deptdate"));
			model.setDepartureTime(request.getParameter("depttime"));
			if (session.getAttribute("userid") == null) {
				model.setUserID(request.getParameter("email"));
			} else {
				model.setUserID(String.valueOf(session.getAttribute("email")));
			}

			String bookingID = "";
			try {

				bookingID = model.insertNewGuestBooking(request
						.getParameter("noOfseats"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (!bookingID.equals("")) {
				if (session.getAttribute("userid") == null) {
					ResultSet rs = null;
					BookingModel bm = new BookingModel();
					bm.setBookingID(bookingID);
					String email = request.getParameter("email");

					bm.setUserID(email);
					rs = bm.getBookingInfo("user", "na");

					RequestDispatcher rd = request
							.getRequestDispatcher("PrintTicket.jsp");
					request.setAttribute("ticket", rs);
					rd.forward(request, response);

				} else {
					response.sendRedirect("GetBookingInformation.jsp?msg=Success&booking="
							+ bookingID);
				}
			} else {
				PrintWriter out = response.getWriter();
				out.print("error");
			}
		} else {
			model.setBusID(request.getParameter("busid"));
			model.setDepartureDate(request.getParameter("deptdate"));
			model.setDepartureTime(request.getParameter("depttime"));
			String email = String.valueOf(session.getAttribute("userid"));
			model.setUserID(email);
			String bookingID = "";

			try {
				bookingID = model.insertNewGuestBooking(request
						.getParameter("noOfseats"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (!bookingID.equals("")) {
				PrintWriter out = response.getWriter();
				response.sendRedirect("GetBookingInformation.jsp?msg=Success&booking="
						+ bookingID);
			} else {
				PrintWriter out = response.getWriter();
				out.print("error");
			}

		}
	}
}
