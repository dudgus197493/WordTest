package edu.kh.wordtest.member.model.service;

import static edu.kh.wordtest.common.JDBCTemplate.close;
import static edu.kh.wordtest.common.JDBCTemplate.commit;
import static edu.kh.wordtest.common.JDBCTemplate.getConnection;
import static edu.kh.wordtest.common.JDBCTemplate.rollback;

import java.sql.Connection;

import edu.kh.wordtest.member.model.dao.MemberDAO;
import edu.kh.wordtest.member.model.dao.MemberDAOImpl;
import edu.kh.wordtest.member.vo.Member;

public class MemberServiceImpl implements MemberService{
	private MemberDAO dao = new MemberDAOImpl();
	
	
	@Override
	public int checkMemberPw(String memberPw, int MemberNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.checkMemberPw(conn, memberPw, MemberNo);
		
		close(conn);
		
		return result;	
	}

	@Override
	public int updateMemberPw(String newMemberPw, int memberNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updateMemberPw(conn, newMemberPw, memberNo);
		
		if(result > 0) commit(conn);
		else 		   rollback(conn);
		
		close(conn);
		
		return result;	
	}

	@Override
	public int secession(int memberNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.secession(conn, memberNo);
		
		if(result > 0) commit(conn);
		else 		   rollback(conn);
		
		close(conn);
		
		return result;	
	}
}
