package edu.kh.wordtest.word.model.dao;

import java.sql.Connection;
import java.util.List;

import edu.kh.wordtest.word.vo.Word;

/**
 * @author Tonic
 *
 */
public interface WordSelectDAO{
	
	/** 모든 단어 검색 DAO
	 * @param conn
	 * @return wordList
	 * @throws Exception
	 */
	public List<Word> selectAllWord(Connection conn) throws Exception; 
	
	/** 단어 한개 조회 DAO
	 * @param conn
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public Word selectWord(Connection conn, String keyword) throws Exception;
	
	
	/** 단어 검색 DAO
	 * @param conn
	 * @param keyword
	 * @return wordList
	 * @throws Exception
	 */
	public List<Word> searchWord(Connection conn, String keyword, int condition) throws Exception;
	
	/** 회원별 정복한 단어 조회 DAO
	 * @param conn
	 * @return wordList
	 * @throws Exception
	 */
	public List<Word> selectConquestWord(Connection conn, int memberNo) throws Exception;
}
