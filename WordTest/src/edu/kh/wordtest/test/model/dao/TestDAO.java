package edu.kh.wordtest.test.model.dao;

import java.sql.Connection;
import java.util.List;

import edu.kh.wordtest.test.vo.Question;
import edu.kh.wordtest.test.vo.TestPaper;
import edu.kh.wordtest.word.vo.Word;

public interface TestDAO {
	
	/** 랜덤한 단어 10개 조회 DAO
	 * @return wordList(size == 10)
	 */
	public List<Word> selectRandomWord(Connection conn) throws Exception;
	
	/** 랜덤한 뜻 40개 조회 DAO
	 * @return meaningList(size == 40)
	 */
	public List<String> selectRandomMeaning(Connection conn, List<Word> wordList) throws Exception;
	
	/** 다음 시험번호 구하기 DAO
	 * @param conn
	 * @return testNo (성공 > 0)
	 */
	public int nextTestNo(Connection conn) throws Exception;
	
	/** 시험기록 등록 DAO
	 * @return result (성공 1)
	 */
	public int insertTestRecode(Connection conn, TestPaper paper, int memberNo) throws Exception;
	
	/** 시험문제 등록 DAO
	 * @param conn
	 * @return result (성공 10)
	 */
	public int insertQuestion(Connection conn, List<Question> questionList, int testNo) throws Exception;
	
	
	/** 테이블에 존재하지 않는 단어 번호 반환 DAO
	 * @param conn
	 * @param wordNo
	 * @param memberNo
	 * @return notExWordNoList
	 */
	public int checkWordRecode(Connection conn, int wordNo, int memberNo) throws Exception;
	
	/** 첫 기록인지 확인하는 DAO
	 * @param conn
	 * @param memberNo
	 * @return result (처음이면 0)
	 */
	public int checkFirstRecode(Connection conn, int memberNo) throws Exception;
	
	
	/** 단어 기록 등록 DAO
	 * @param conn
	 * @param memberNo
	 * @param wordNo
	 * @return result
	 * @throws Exception
	 */
	public int insertWordRecode(Connection conn, int memberNo, int wordNo) throws Exception;
	
	/** 회원별 단어 기록 카운트 증가 DAO
	 * @param conn
	 * @param question	맞은 문제면 correctCount + 1, 틀린문제면 wrongCount + 1
	 * @param memberNo
	 * @return result (성공 10)
	 * @throws Exception
	 */
	public int updateWordCount(Connection conn, Question question, int memberNo) throws Exception;
}
