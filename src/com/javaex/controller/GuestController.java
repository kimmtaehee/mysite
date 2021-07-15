package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/Guest")
public class GuestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("[GuestController]");
		
		//텍스트 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파라미터 가져오기
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addList".equals(action)) { //방명록
			System.out.println("[리스트]");
			
			//addList
			GuestDao guestDao = new GuestDao();
			List<GuestVo> guestbookList =  guestDao.getGuestbookList();
			
			//데이터 넣기 --request 어트리뷰트에 데이터를 넣어준다
			request.setAttribute("addList", guestbookList);
			
			//방명록 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		}else if("add".equals(action)) {
			System.out.println("[추가]");
			
			//add
			GuestDao guestDao = new GuestDao();
			
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			
			GuestVo guestVo = new GuestVo(name, password, content);
			
			guestDao.guestbookInsert(guestVo);
			
			WebUtil.redirect(request, response, "/mysite/Guest?action=addList");
			
		}else if("deleteForm".equals(action)) {
			System.out.println("[삭제폼]");
			
			//방명록 삭제 포워드 ( 폼처럼 들어가는거(정보보내는거) )
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		}else if("delete".equals(action)) {
			
			System.out.println("[삭제]");
			
			//delete
			GuestDao guestDao = new GuestDao();

			int no = Integer.parseInt(request.getParameter("no"));
			
			String password = request.getParameter("pass");
			guestDao.guestDelete(no, password);
			
			//실행 후 다시 돌아와는거(새로고침)
			WebUtil.redirect(request, response, "/mysite/Guest?action=addList");
			
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
