package edu.kh.wordtest.test.model.dao;

import java.sql.Connection;
import java.util.List;

import edu.kh.wordtest.member.vo.Member;
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
	
	/** 모든 시험 기록 조회 DAO
	 * @param conn
	 * @param memberNo
	 * @return paperList
	 * @throws Exception
	 */
	public List<TestPaper> selectAllTest(Connection conn, int memberNo) throws Exception;

	/** 시험 기록 상세 조회 DAO
	 * @param conn
	 * @param testNo
	 * @return paper
	 * @throws Exception
	 */
	public TestPaper selectTest(Connection conn, int testNo) throws Exception;

	/** 단어 기록 등록 DAO
	 * @param conn
	 * @param wordNoList
	 * @param memberNo
	 */
	public int insertWordRecode(Connection conn, List<Integer> wordNoList, int memberNo) throws Exception;
	
	/** 정답 카운트 증가 DAO
	 * @param conn
	 * @param wordNoList
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int increaseAccurateCount(Connection conn, List<Integer> wordNoList, int memberNo) throws Exception;
	
	/** 오답 카운트 증가
	 * @param conn
	 * @param wordNoList
	 * @param memberNo
	 * @return resulta
	 * @throws Exception
	 */
	public int increaseWrongCount(Connection conn, List<Integer> wordNoList, int memberNo) throws Exception;
	
	/** 정복한 단어 갯수 구하기 DAO
	 * @param conn
	 * @param memberNo
	 * @return conquestWordCount
	 * @throws Exception
	 */
	public int getConquestWordCount(Connection conn, int memberNo)  throws Exception;
	
	/** 회원 티어 승급 DAO
	 * @param memberNo
	 * @return member
	 * @throws Exception
	 */
	public int updateTier(Connection conn, int memberNo, int conquestWordCount) throws Exception;
}
