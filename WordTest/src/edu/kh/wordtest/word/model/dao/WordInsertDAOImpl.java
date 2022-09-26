package edu.kh.wordtest.word.model.dao;

import static edu.kh.wordtest.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.wordtest.word.vo.Meaning;
import edu.kh.wordtest.word.vo.Word;

public class WordInsertDAOImpl implements WordInsertDAO{
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	
	
	public WordInsertDAOImpl() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("SQL/word-query.xml"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public int nextWordNo(Connection conn) throws Exception {
		int wordNo = 0;
		
		try {
			String sql = prop.getProperty("nextWordNo");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) wordNo = rs.getInt("WORD_NO");
		}finally {
			close(rs);
			close(stmt);
		}
		return wordNo;
	}

	@Override
	public int insertWord(Connection conn, Word word) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("insertWord");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, word.getWordNo());
			pstmt.setString(2, word.getWordName());
			result = pstmt.executeUpdate();
			
		}finally {
			close(rs);
			close(pstmt);
		}

		return result;
	}


	@Override
	public int insertMeaning(Connection conn, Meaning meaning, int wordNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("isertMeaning");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, meaning.getmeaningName());
			pstmt.setInt(2, wordNo);
			pstmt.setString(3, meaning.getPosName());
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}

}
