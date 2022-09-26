package edu.kh.wordtest.word.model.service;

import static edu.kh.wordtest.common.JDBCTemplate.close;
import static edu.kh.wordtest.common.JDBCTemplate.commit;
import static edu.kh.wordtest.common.JDBCTemplate.getConnection;
import static edu.kh.wordtest.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import edu.kh.wordtest.word.model.dao.WordInsertDAO;
import edu.kh.wordtest.word.model.dao.WordInsertDAOImpl;
import edu.kh.wordtest.word.vo.Meaning;
import edu.kh.wordtest.word.vo.Word;

public class WordInsertServiceImpl implements WordInsertService{
	private WordInsertDAO dao = new WordInsertDAOImpl();
	
	
	@Override
	public int insertWord(Word word) throws Exception {
		Connection conn = getConnection();
		int result = 0;
		// 추가될 wordNo 반환
		try {
			int wordNo = dao.nextWordNo(conn);
			
			// word추가 (word_no 반환)
			word.setWordNo(wordNo);
			result = dao.insertWord(conn, word);
			int meaningResult = 0;
			if(result > 0) {		// 추가 성공 시
				for(Meaning m : word.getMeaningList()) {
					meaningResult += dao.insertMeaning(conn, m, word.getWordNo());
				}
			}
			if(result > 0 && meaningResult == word.getMeaningList().size()){			// 제대로 추가 하지 못했다면
				commit(conn);
			} else {
				rollback(conn);
			}
		} catch (Exception e) {
			rollback(conn);
			e.printStackTrace();
			throw new Exception("\n<< 단어 추가 중 예외 발생 >>\n");
		} finally {
			close(conn);
		}
		return result;
	}

	@Override
	public int insertMeaning(int wordNo, List<Meaning> meaningList) throws Exception {
		Connection conn = getConnection();
		int meaningResult = 0;
		try {
			for(Meaning m : meaningList) {
				meaningResult += dao.insertMeaning(conn, m, wordNo);
			}
			
			if(meaningResult == meaningList.size()) {
				commit(conn);
			} else {
				rollback(conn);
			}
		}catch(Exception e) {
			rollback(conn);
			e.printStackTrace();
			throw new Exception("\n<< 단어 추가 중 예외 발생 >>\n");
		} finally {
			close(conn);
		}

		return meaningResult;
	}
}
