package edu.kh.wordtest.test.model.service;

import static edu.kh.wordtest.common.JDBCTemplate.*;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import edu.kh.wordtest.main.view.MainView;
import edu.kh.wordtest.test.model.dao.TestDAO;
import edu.kh.wordtest.test.model.dao.TestDAOImpl;
import edu.kh.wordtest.test.vo.Question;
import edu.kh.wordtest.test.vo.TestPaper;
import edu.kh.wordtest.word.vo.Word;

public class TestServiceImpl implements TestService{
	private TestDAO dao = new TestDAOImpl();
	
	@Override
	public TestPaper creatTestPaper() throws Exception {
		Connection conn = getConnection();
		List<Question> questionList = new ArrayList<>();
		
		// 랜덤 단어 10개 가져오기
		List<Word> wordList = dao.selectRandomWord(conn);
		if(wordList.size() != 10) {	
			return null;
		}
		// 랜덤 뜻 40개 가져오기			
		List<String> meaningList = dao.selectRandomMeaning(conn, wordList);
		if(meaningList.size() != 40) {	
			return null;
		}
		// questionList 완성하기
		for(int i =0; i< 10; i++) {							// meaningList가 빌 때까지
			List<String> tempList = new ArrayList<>();			// 임시 리스트 생성
			Question question = new Question();					// question 객체 생성
			for(int j =0; j<4; j++) {
				tempList.add(meaningList.remove(0));			// 임시 리스트에 meaningList의 앞 노드부터 4개 옮기기
			}
			question.setExampleList(tempList);
			
			question.setWordNo(wordList.get(i).getWordNo());
			question.setWordName(wordList.get(i).getWordName());
			question.setExampleList(wordList.get(i).getMeaningList().get(0).getmeaningName());
			
			questionList.add(question);
		}
		TestPaper paper = new TestPaper();
		
		paper.setQuestionList(questionList);
		
		close(conn);
		
		return paper;
	}

	@Override
	public int recodeTest(TestPaper paper) throws Exception {
		Connection conn = getConnection();
		int result1 = 0;
		int result2 = 0;
		int result3 = 0;
		int result4 = 0;
		try {
			int memberNo = MainView.loginMember.getMemberNo();
			List<Question> questionList = paper.getQuestionList();
			List<Integer> wordNoList = new ArrayList<>();
			
			// 다음 시험번호 가져오기
			int testNo = dao.nextTestNo(conn);
			paper.setTestNo(testNo);
			// 시험 점수 등록
			result1 = dao.insertTestRecode(conn, paper, memberNo);
			
			// 보기 리스트 문자열로 변환
			for(int i =0; i< questionList.size(); i++) {
				Question question = questionList.get(i);
				wordNoList.add(question.getWordNo());
				List<String> exampleList = question.getExampleList();
				String str = "";
				for(int j =0; j<exampleList.size(); j++) {
					if(j==0) {
						str += exampleList.get(j);
					}else {
						str += "#" + exampleList.get(j);
					}
				}
				question.setExampleStr(str);
			}
			
			// 문제 등록
			result2 = dao.insertQuestion(conn, paper.getQuestionList(), paper.getTestNo());
			
			// 첫 기록인지 확인
//			int isfirst = dao.checkFirstRecode(conn, memberNo);  
//			// 첫 기록이면
//			if(isfirst == 0) {
//				// 모든 단어 INSERT
//				for (int i =0; i<wordNoList.size(); i++) {
//					result3 += dao.insertWordRecode(conn, memberNo, wordNoList.get(i));
//				}
//			} else {
//				for(int i =0; i<wordNoList.size(); i++) {
//					System.out.println("loop");
//					int result = dao.checkWordRecode(conn, wordNoList.get(i), memberNo);
//					if(result == 0) {	// 존재하지 않으면
//						result += dao.insertWordRecode(conn, memberNo, wordNoList.get(i));
//					}
//				}
//			}
//			for(int i =0; i<questionList.size(); i++) {
//				result4 += dao.updateWordCount(conn, questionList.get(i), memberNo);
//			}
			if(result2 == 10) commit(conn);
			else 		      rollback(conn);
		}catch(Exception e) {
			e.printStackTrace();
			rollback(conn);
			throw new Exception("\n<< 시험 기록중 예외 발생 >>\n");
			
		} finally {
			close(conn);
		}
		return result2;
	}
}
