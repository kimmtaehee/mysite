package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.PersonVo;

public class BoardDao {

	// 0. import java.sql.*;
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;

		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url = "jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "phonedb";
		private String pw = "phonedb";

		private void getConnection() {
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(driver);

				// 2. Connection 얻어오기
				conn = DriverManager.getConnection(url, id, pw);
				// System.out.println("접속성공");

			} catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

		public void close() {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
	// 사람 리스트(검색안할때)
		public List<BoardVo> getBoardList() {
			return getBoardList("");
		}
	
	//리스트
		public List<BoardVo> getBoardList(String keword){
			List<BoardVo> boardList = new ArrayList<BoardVo>();
			
			getConnection();
			
			try {
				
				String query="";
				query += " SELECT  b.no, ";
				query += "       b.title, ";
				query += "       u.name, ";
				query += "        b.hit, ";
				query += "        b.reg_date ";
				query += " FROM board b,users u ";
				query += " where b.user_no = u.no ";
				
				if (keword != "" || keword == null) {
					query += " and(b.title || u.name || b.content) like ? ";
					query += " order by b.reg_date desc ";
					pstmt = conn.prepareStatement(query); // 쿼리로 만들기

					pstmt.setString(1, '%' + keword + '%'); // ?(물음표) 중 1번째, 순서중요
					
				} else {
					query += " order by b.reg_date desc ";
					pstmt = conn.prepareStatement(query); // 쿼리로 만들기
				}
				
				rs = pstmt.executeQuery();

				// 4.결과처리
				while (rs.next()) {
					int no = rs.getInt("no");
					String title = rs.getString("title");
					String name = rs.getString("name");
					int hit = rs.getInt("hit");
					String reg_date = rs.getString("reg_date");
					
					BoardVo boardVo = new BoardVo(no, title, hit, reg_date, name);
					boardList.add(boardVo);
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			close();

			return boardList;

		}
				
				

			
			
			
		}
	
}
