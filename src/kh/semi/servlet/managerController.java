package kh.semi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kh.semi.dao.ManagerDAO;
import kh.semi.dto.BoardDTO;
import kh.semi.dto.MemberDTO;
import kh.semi.dto.PaymentDTO;
import kh.semi.dto.TimePersonCountDTO;

@WebServlet("*.manager")
public class managerController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter write = response.getWriter();


		String requestURI = request.getRequestURI();
		String comtextPath = request.getContextPath();
		String cmd = requestURI.substring(comtextPath.length());
		ManagerDAO dao = new ManagerDAO();

		if (cmd.equals("/Bar.manager")) {
			try {
				request.setAttribute("visitPersonCount", dao.visitPersonCount());
				request.setAttribute("totalMoney", dao.totalMoney());
				request.setAttribute("nowingProjectCount", dao.nowingProjectCount());
				request.setAttribute("joinMemberCount", dao.joinMemberCount());
				request.setAttribute("line", dao.timePersonCount());
				request.setAttribute("pie", dao.recommendTopFour());
				request.setAttribute("bestRecommendCount", dao.bestRecommendCount());
				request.setAttribute("worstRecommendCount", dao.worstRecommendCount());
				request.setAttribute("bestViewCount", dao.bestViewCount());
				request.setAttribute("worstViewCount", dao.worstViewCount());
				request.setAttribute("donationManyPeople", dao.donationTopThree());
				request.setAttribute("memberInfo", dao.memberInfo());
				request.setAttribute("totalDonationProject", dao.totalDonationProject());
				request.getRequestDispatcher("/WEB-INF/basics/manager.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(cmd.equals("/BoardWriteDelete.manager")) {
			String[] value = request.getParameterValues("checkDelete");
			if(value!=null) {
				for(int i=0; i<value.length; i++) {
					try {
						int result = dao.boardWriteDelete(value[i]);
						int resultTitleDelete = dao.titleImgDelete(value[i]);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			request.getRequestDispatcher("List.board?currentPage=1&searchOption=allPages&searchWord=allPages").forward(request, response);//이걸로고침!!창훈
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}