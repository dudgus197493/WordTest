package edu.kh.wordtest.word.model.service;

import static edu.kh.wordtest.common.JDBCTemplate.getConnection;
import static edu.kh.wordtest.common.JDBCTemplate.close;

import java.sql.Connection;
import java.util.List;

import edu.kh.wordtest.word.model.dao.WordSelectDAO;
import edu.kh.wordtest.word.model.dao.WordSelectDAOImpl;
import edu.kh.wordtest.word.vo.Word;

public class WordSelectServiceImpl implements WordSelectService{
	private WordSelectDAO dao = new WordSelectDAOImpl();
	
	@Override
	public List<Word> selectAllWord() throws Exception {
		Connection conn = getConnection();
		
		List<Word> wordList = dao.selectAllWord(conn);
		
		close(conn);
		
		return wordList;
	}

	@Override
	public List<Word> searchWord(String keyword, int condition) throws Exception {
		Connection conn = getConnection();
		
		List<Word> wordList = dao.searchWord(conn, keyword, condition);
		
		close(conn);
		
		return wordList;
	}

	@Override
	public Word selectWord(String keyword) throws Exception {
		Connection conn = getConnection();
		
		Word word = dao.selectWord(conn, keyword);
		
		close(conn);
		
		return word;
	}

	@Override
	public List<Word> selectConquestWord(int memberNo) throws Exception {
		Connection conn = getConnection();
		
		List<Word> wordList = dao.selectConquestWord(conn, memberNo);
		
		close(conn);
		
		return wordList;
	}

}
