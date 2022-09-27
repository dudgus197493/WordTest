package edu.kh.wordtest.test.vo;

import java.util.ArrayList;
import java.util.List;

// 시험 문제 (객관식) VO
public class Question {
	private int questionNo;				// 문제 번호
	private int wordNo;					// 단어 번호
	private String wordName;			// 단어 이름
	private List<String> exampleList;	// 보기 (1 ~ 5)
	private String exampleStr;			// 저장용 보기 문자열
	private int correctAnswer;			// 정답 번호
	private int userAnswer;				// 사용자 답변 번호
	private int testNo;					// 시험 번호
	private boolean isCorrect;			// 정답 여부
	
	
	public Question() { 
		exampleList = new ArrayList<>();
	}

	public int getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
	}

	public int getWordNo() {
		return wordNo;
	}

	public void setWordNo(int wordNo) {
		this.wordNo = wordNo;
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	public List<String> getExampleList() {
		return exampleList;
	}

	public void setExampleList(List<String> exampleList) {
		this.exampleList = exampleList;
	}

	// setter 오버로딩
	// 랜덤인덱스로 정답이 보기리스트에 들어가는 setter
	public void setExampleList(String example) {
		int ranIdx = (int)(Math.random() * 5);			// 랜덤 난수 생성 (0 ~ 4)
		this.exampleList.add(ranIdx, example);			// 랜덤한 인덱스로 정답보기 삽입
		setCorrectAnswer(ranIdx + 1);						// 정답 세팅
	}
	
	public String getExampleStr() {
		return exampleStr;
	}

	public void setExampleStr(String exampleStr) {
		this.exampleStr = exampleStr;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public int getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(int userAnswer) {
		this.userAnswer = userAnswer;
	}

	public int getTestNo() {
		return testNo;
	}

	public void setTestNo(int testNo) {
		this.testNo = testNo;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	
}
