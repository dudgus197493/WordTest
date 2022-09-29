package edu.kh.wordtest.admin.model.dao;

import java.sql.Connection;

import edu.kh.wordtest.word.vo.Meaning;

public interface AdminDAO {

	/** 단어 삭제 DAO
	 * @param conn
	 * @param wordNo
	 * @return result
	 * @throws Exception
	 */
	int deleteWord(Connection conn, int wordNo) throws Exception;

	
	/** 단어 수정 DAO
	 * @param conn
	 * @param wordNo
	 * @return result
	 * @throws Exception
	 */
	int updateWord(Connection conn, String newSpell, int wordNo) throws Exception;

	/** 단어 뜻 수정 DAO
	 * @param conn
	 * @param meaning
	 * @return result
	 * @throws Exception
	 */
	int updateMeaning(Connection conn, Meaning meaning) throws Exception;
}
