package edu.kh.wordtest.test.model.dao;

import static edu.kh.wordtest.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import edu.kh.wordtest.member.vo.Member;
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
	public List<TestPaper> selectAllTest(Connection conn, int memberNo) throws Exception {
		List<TestPaper> paperList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectAllTest");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, memberNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TestPaper paper = new TestPaper();
				paper.setTestNo(rs.getInt("TEST_NO"));
				paper.setTestScore(rs.getInt("TEST_SCORE"));
				paper.setTestDate(rs.getString("TEST_DT"));
				paper.setMemberName(rs.getString("MEMBER_NM"));
				paperList.add(paper);
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		return paperList;
	}

	@Override
	public TestPaper selectTest(Connection conn, int testNo) throws Exception {
		TestPaper paper = null;
		try {
			String sql = prop.getProperty("selectTest");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, testNo);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.isFirst()) {
					paper = new TestPaper();
					paper.setTestNo(rs.getInt("TEST_NO"));
					paper.setTestScore(rs.getInt("TEST_SCORE"));
					paper.setTestDate(rs.getString("TEST_DT"));
					paper.setMemberNo(rs.getInt("MEMBER_NO"));
					paper.setMemberName(rs.getString("MEMBER_NM"));
				}
				Question question = new Question();
				question.setCorrectAnswer(rs.getInt("CORRECT_ANSWER"));
				question.setUserAnswer(rs.getInt("USER_ANSWER"));
				question.setWordNo(rs.getInt("WORD_NO"));
				question.setWordName(rs.getString("WORD_NM"));
				
				String exampleStr = rs.getString("EXAMPLE");
				String[] arr = exampleStr.split("#");
				List<String> exampleList = Arrays.asList(arr);
				question.setExampleList(exampleList);
				paper.setQuesion(question);
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		return paper;
	}

	@Override
	public int insertWordRecode(Connection conn, List<Integer> wordNoList, int memberNo) throws Exception{
//		INSERT INTO WORD_RECODE (WORD_NO, MEMBER_NO) 
//		WITH CHECK_WORD AS (
//			SELECT WORD_NO, ? MEMBER_NO
//			FROM WORD 
//			WHERE WORD_NO IN (?, ?, ?, ?, ?, ?, ?, ?, ?, ?))
//		SELECT WORD_NO, CHECK_WORD.MEMBER_NO
//		FROM WORD_RECODE
//		FULL JOIN CHECK_WORD USING(WORD_NO)
//		WHERE CHECK_WORD.MEMBER_NO = ?
//		AND ACCURAT_COUNT IS NULL
		int result = 0;
		
		try {
			String sql = prop.getProperty("insertWordRecode");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			for(int i =0; i<wordNoList.size(); i++) {
				pstmt.setInt(i+2, wordNoList.get(i));
			}
			pstmt.setInt(12, memberNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		return result;
	}

	@Override
	public int increaseAccurateCount(Connection conn, List<Integer> wordNoList, int memberNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("increaseAccurateCount");
			
			for(int i =0; i< wordNoList.size(); i++) {
				if(i == wordNoList.size() -1) {
					sql += wordNoList.get(i) + ")";
				} else {
					sql += wordNoList.get(i) + ", ";
				}
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			
			result = pstmt.executeUpdate();
		}
		finally {
			close(pstmt);
		}
		return result;
	}

	@Override
	public int increaseWrongCount(Connection conn, List<Integer> wordNoList, int memberNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("increaseWrongCount");
			
			for(int i =0; i< wordNoList.size(); i++) {
				if(i == wordNoList.size() -1) {
					sql += wordNoList.get(i) + ")";
				} else {
					sql += wordNoList.get(i) + ", ";
				}
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			
			result = pstmt.executeUpdate();
		}
		finally {
			close(pstmt);
		}
		return result;
	}

	@Override
	public int getConquestWordCount(Connection conn, int memberNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("getConquestWordCount");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, memberNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("CONQUEST_COUNT");
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	@Override
	public int updateTier(Connection conn, int memberNo, int conquestWordCount) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("updateTier");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, conquestWordCount);
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
}
