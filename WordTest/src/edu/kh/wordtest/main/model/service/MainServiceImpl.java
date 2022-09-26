package edu.kh.wordtest.main.model.service;

import static edu.kh.wordtest.common.JDBCTemplate.*;

import java.sql.Connection;

import edu.kh.wordtest.main.model.dao.MainDAO;
import edu.kh.wordtest.main.model.dao.MainDAOImpl;
import edu.kh.wordtest.member.vo.Member;

public class MainServiceImpl implements MainService{
	private MainDAO dao = new MainDAOImpl();
	
	@Override
	public Member login(String memberId, String memberPw) throws Exception {
		Connection conn = getConnection();
		
		Member loginMember = dao.login(conn, memberId, memberPw); 
		
		close(conn);
		return loginMember;
	}

	@Override
	public int signUp(Member member) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.signUp(conn, member);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		return result;
	}

	@Override
	public int idDupCheck(String memberId) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.idDupCheck(conn, memberId);
		
		close(conn);
		
		return result;
	}

	@Override
	public String findMemberId(Member member) throws Exception {
		Connection conn = getConnection();
		
		String memberId = dao.findMemberId(conn, member);
		
		close(conn);
		
		return memberId;
	}

	@Override
	public int findMemberPw(Member member) throws Exception {
		Connection conn = getConnection();
		
		int memberNo = dao.findMemberPw(conn, member);
		
		close(conn);
		
		return memberNo;
	}
}
