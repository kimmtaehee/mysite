package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoradController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("[BoradController]");

		//텍스트 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파라미터 가져오기
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			System.out.println("[리스트]");
			
			//list
			BoardDao boardDao = new BoardDao();
			List<BoardVo> BoardList = boardDao.getBoardList();
			
			//데이터 넣기 --request 어트리뷰트에 데이터를 넣어준다
			request.setAttribute("list", BoardList);
								//명칭    //값
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
