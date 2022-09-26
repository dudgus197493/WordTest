package edu.kh.wordtest.word.model.service;

import java.util.List;

import edu.kh.wordtest.word.vo.Meaning;
import edu.kh.wordtest.word.vo.Word;

public interface WordInsertService {
	
	
	/** 새로운 단어 추가
	 * @param word
	 * @return result
	 * @throws Exception
	 */
	public int insertWord(Word word) throws Exception;
	
	
	/** 기존 단어에 새로운 뜻 추가
	 * @param wordNo
	 * @param meaningList
	 * @return result
	 * @throws Exception
	 */
	public int insertMeaning(int wordNo, List<Meaning> meaningList) throws Exception;
	
}
