package edu.kh.wordtest.word.model.dao;

import java.sql.Connection;

import edu.kh.wordtest.word.vo.Meaning;
import edu.kh.wordtest.word.vo.Word;

public interface WordInsertDAO {
	
	/** 다음 단어 번호 구하기 DAO
	 * @param conn
	 * @return wordNo
	 * @throws Exception
	 */
	public int nextWordNo(Connection conn) throws Exception;
	
	/** 단어 추가(ENG) DAO
	 * @param conn
	 * @param word
	 * @return result
	 * @throws Exception
	 */
	public int insertWord(Connection conn, Word word) throws Exception;
	
	
	/** 뜻 추가 DAO
	 * @param conn
	 * @param meaning
	 * @param wordNo
	 * @return result;
	 * @throws Exception
	 */
	public int insertMeaning(Connection conn, Meaning meaning, int wordNo) throws Exception;
}
