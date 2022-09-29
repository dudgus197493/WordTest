package edu.kh.wordtest.word.model.service;

import java.util.List;

import edu.kh.wordtest.word.vo.Word;

public interface WordSelectService {
	
	/** 모든 단어 조회 서비스
	 * @return wordList
	 * @throws Exception
	 */
	public List<Word> selectAllWord() throws Exception;
	
	/**	단어 한개 조회 서비스
	 * @return word (없으면 null)
	 * @throws Exception
	 */
	public Word selectWord(String keyword) throws Exception;
	
	/** 단어 검색 서비스
	 * @return Word
	 * @throws Exception
	 */
	public List<Word> searchWord(String keyword, int condition) throws Exception;
	
	/** 정복한 단어 검색 서비스 
	 * @return WordList
	 * @throws Exception
	 */
	public List<Word> selectConquestWord(int memberNo) throws Exception;
}
