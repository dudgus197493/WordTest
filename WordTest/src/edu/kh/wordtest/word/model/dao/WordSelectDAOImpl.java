package edu.kh.wordtest.word.model.dao;

import static edu.kh.wordtest.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.wordtest.word.vo.Meaning;
import edu.kh.wordtest.word.vo.Word;

public class WordSelectDAOImpl implements WordSelectDAO{
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public WordSelectDAOImpl() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("SQL/word-query.xml"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Word> selectAllWord(Connection conn) throws Exception {
		List<Word> wordList = new ArrayList<>();
		try {
			String sql = prop.getProperty("selectAllWord");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			int count = 0;
			String prevWord = "";
			while(rs.next()) {
				String currentWord = rs.getString("WORD_NM");
				if(prevWord.equals(currentWord)) {	// 이전 단어와 같다면
					Meaning meaning = new Meaning();						// 새로운 Meaning 객체 생성
					meaning.setPosCode(rs.getString("POS_CODE"));
					meaning.setPosName(rs.getString("POS_NM"));
					meaning.setMeaningNo(rs.getInt("MEANING_NO"));
					meaning.setmeaningName(rs.getString("MEANING_NM"));
					wordList.get(count-1).setMeaning(meaning); 
				} else {							// 새로운 단어라면
					Word word = new Word();
					word.setWordNo(rs.getInt("WORD_NO"));
					word.setWordName(rs.getString("WORD_NM"));
					prevWord = word.getWordName();
					
					Meaning meaning = new Meaning();
					meaning.setPosCode(rs.getString("POS_CODE"));
					meaning.setPosName(rs.getString("POS_NM"));
					meaning.setMeaningNo(rs.getInt("MEANING_NO"));
					meaning.setmeaningName(rs.getString("MEANING_NM"));
					
					word.setMeaning(meaning);
					wordList.add(word);
					count++;
				}
			}
			
		}finally {
			close(rs);
			close(stmt);
		}
		return wordList;
	}

	@Override
	public List<Word> searchWord(Connection conn, String keyword, int condition) throws Exception {
		List<Word> wordList = new ArrayList<>();
		try {
			String sql = prop.getProperty("searchWord1")
					   + prop.getProperty("searchWord2_" + condition)
					   + prop.getProperty("searchWord3");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			
			int count = 0;
			String prevWord = "";
			while(rs.next()) {
				String currentWord = rs.getString("WORD_NM");
				if(prevWord.equals(currentWord)) {	// 이전 단어와 같다면
					Meaning meaning = new Meaning();						// 새로운 Meaning 객체 생성
					meaning.setPosCode(rs.getString("POS_CODE"));
					meaning.setPosName(rs.getString("POS_NM"));
					meaning.setMeaningNo(rs.getInt("MEANING_NO"));
					meaning.setmeaningName(rs.getString("MEANING_NM"));
					wordList.get(count-1).setMeaning(meaning); 
				} else {							// 새로운 단어라면
					Word word = new Word();
					word.setWordNo(rs.getInt("WORD_NO"));
					word.setWordName(rs.getString("WORD_NM"));
					prevWord = word.getWordName();
					
					Meaning meaning = new Meaning();
					meaning.setPosCode(rs.getString("POS_CODE"));
					meaning.setPosName(rs.getString("POS_NM"));
					meaning.setMeaningNo(rs.getInt("MEANING_NO"));
					meaning.setmeaningName(rs.getString("MEANING_NM"));
					
					word.setMeaning(meaning);
					wordList.add(word);
					count++;
				}
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		return wordList;
	}

	@Override
	public Word selectWord(Connection conn, String keyword) throws Exception {
		Word word = null;
		
		try {
			String sql = prop.getProperty("selectWord");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(rs.isFirst()) {
					word = new Word();
					word.setWordNo(rs.getInt("WORD_NO"));
					word.setWordName(rs.getString("WORD_NM"));
				}
				Meaning meaning = new Meaning();
				meaning.setPosCode(rs.getString("POS_CODE"));
				meaning.setPosName(rs.getString("POS_NM"));
				meaning.setMeaningNo(rs.getInt("MEANING_NO"));
				meaning.setmeaningName(rs.getString("MEANING_NM"));
				word.setMeaning(meaning);
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return word;
	}
}
