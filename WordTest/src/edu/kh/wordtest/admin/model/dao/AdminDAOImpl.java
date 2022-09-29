package edu.kh.wordtest.admin.model.dao;

import static edu.kh.wordtest.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.wordtest.word.vo.Meaning;

public class AdminDAOImpl implements AdminDAO {
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public AdminDAOImpl() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("SQL/admin-query.xml"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int deleteWord(Connection conn, int wordNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("deleteWord");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, wordNo);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}

	@Override
	public int updateWord(Connection conn, String newSpell, int wordNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("updateWord");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newSpell);
			pstmt.setInt(2, wordNo);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}

	@Override
	public int updateMeaning(Connection conn, Meaning meaning) throws Exception {

		int result = 0;
		try {
			String sql = prop.getProperty("updateMeaning");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, meaning.getmeaningName());
			pstmt.setString(2, meaning.getPosName());
			pstmt.setInt(3, meaning.getWordNo());
			pstmt.setInt(4, meaning.getMeaningNo());
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}
}
