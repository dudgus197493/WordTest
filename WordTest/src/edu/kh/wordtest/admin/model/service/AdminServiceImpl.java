package edu.kh.wordtest.admin.model.service;

import static edu.kh.wordtest.common.JDBCTemplate.*;

import java.sql.Connection;

import edu.kh.wordtest.admin.model.dao.AdminDAO;
import edu.kh.wordtest.admin.model.dao.AdminDAOImpl;
import edu.kh.wordtest.word.vo.Meaning;

public class AdminServiceImpl implements AdminService{
	private AdminDAO dao = new AdminDAOImpl();
	
	
	@Override
	public int deleteWord(int wordNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.deleteWord(conn, wordNo);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		return result;
	}


	@Override
	public int updateWord(String newSpell, int wordNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updateWord(conn, newSpell,  wordNo);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		return result;
	}


	@Override
	public int updateMeaning(Meaning meaning) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updateMeaning(conn, meaning);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		return result;
	}

	
}
