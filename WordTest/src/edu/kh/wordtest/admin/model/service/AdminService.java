package edu.kh.wordtest.admin.model.service;

import edu.kh.wordtest.word.vo.Meaning;

public interface AdminService {

	/** 단어 삭제 서비스
	 * @param wordNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteWord(int wordNo) throws Exception;

	/** 단어 수정 서비스
	 * @param newSpell
	 * @param wordNo
	 * @return result
	 * @throws Exception
	 */
	public int updateWord(String newSpell, int wordNo) throws Exception;

	/** 단어 뜻 수정 서비스
	 * @param meaning
	 * @return result
	 * @throws Exception
	 */
	public int updateMeaning(Meaning meaning) throws Exception;
	
}
