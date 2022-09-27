package edu.kh.wordtest.test.model.dao;

import static edu.kh.wordtest.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.wordtest.test.vo.Question;
import edu.kh.wordtest.test.vo.TestPaper;
import edu.kh.wordtest.word.vo.Meaning;
import edu.kh.wordtest.word.vo.Word;

public class TestDAOImpl implements TestDAO{
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public TestDAOImpl() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("SQL/test-query.xml"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<Word> selectRandomWord(Connection conn) throws Exception {
		List<Word> wordList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectRandomWord");
			
			stmt = conn.createStatement();
			rs= stmt.executeQuery(sql);
			
			while(rs.next()) {
				Word word = new Word();
				word.setWordNo(rs.getInt("WORD_NO"));
				word.setWordName(rs.getString("WORD_NM"));
				
				Meaning meaning = new Meaning();
				meaning.setmeaningName(rs.getString("MEANING_NM"));
				
				word.setMeaning(meaning);
				wordList.add(word);
			}
			
		}finally {
			close(rs);
			close(stmt);
		}
		return wordList;
	}

	@Override
	public List<String> selectRandomMeaning(Connection conn, List<Word> wordList) throws Exception {
		List<String> meaningList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectRandomMeaning");
			
			pstmt = conn.prepareStatement(sql);
			
			for(int i = 0; i < wordList.size(); i++) {
				pstmt.setInt(i + 1, wordList.get(i).getWordNo());
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				meaningList.add(rs.getString("MEANING_NM"));
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return meaningList;
	}

	@Override
	public int nextTestNo(Connection conn) throws Exception{
		int result = 0;
		
		try {
			String sql = prop.getProperty("nextTestNo");
			stmt =conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				result = rs.getInt("RECODE_NO");
			}
		} finally {
			close(rs);
			close(stmt);
		}
		
		return result;
	}

	@Override
	public int insertTestRecode(Connection conn, TestPaper paper, int memberNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("insertTestRecode");
			pstmt =conn.prepareStatement(sql);
			pstmt.setInt(1, paper.getTestNo());
			pstmt.setInt(2, paper.getTestScore());
			pstmt.setInt(3, memberNo);
			
			result = pstmt.executeUpdate();
		
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	@Override
	public int insertQuestion(Connection conn, List<Question> questionList, int testNo) throws Exception {
		// WORD_NO, TEST_NO, EXAMPLE, CORRECT_ANSWER, USER_ANSWER
		int result = 0;
		
		try {
			String sql = "INSERT INTO QUESTION (WORD_NO, TEST_NO, EXAMPLE, CORRECT_ANSWER, USER_ANSWER)";
			// SELECT 1, '23', '124' FROM DUAL UNION ALL
			for(int i =0; i<questionList.size(); i++) {
				Question question = questionList.get(i);
				sql += " SELECT "+ question.getWordNo() + ", " + testNo + ", " + "'"+ question.getExampleStr()+"'"
						+ ", " + question.getCorrectAnswer() + ", " + question.getUserAnswer() + " FROM DUAL";
				if(!(i == questionList.size() - 1)) {
					sql += " UNION ALL";
				}
			} // SQL 준비
			
		stmt = conn.createStatement();
		result = stmt.executeUpdate(sql);
			
		} finally {
			close(stmt);
		}
		return result;
	}

	@Override
	public int checkWordRecode(Connection conn, int wordNo, int memberNo) throws Exception {
		int result = 0; 
		
		try {
			String sql = prop.getProperty("checkWordRecode");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, wordNo);
			pstmt.setInt(2, memberNo);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt("CHECK");
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}

	@Override
	public int checkFirstRecode(Connection conn, int memberNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("checkFirstRecode");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("CHECK_FIRST");
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	@Override
	public int insertWordRecode(Connection conn, int memberNo, int wordNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("insertWordRecode");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, wordNo);
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}

	@Override
	public int updateWordCount(Connection conn, Question question, int memberNo) throws Exception {
		int result = 0;
		
		try {
			String sql = null;
			if(question.isCorrect()) {		// 정답이면
				sql = prop.getProperty("updateWordCount1");
			} else {
				sql = prop.getProperty("updateWordCount2");
			}
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, question.getWordNo());
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		return result;
	}
}
