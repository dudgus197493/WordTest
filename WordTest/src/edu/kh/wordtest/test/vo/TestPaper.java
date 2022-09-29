package edu.kh.wordtest.test.vo;

import java.util.ArrayList;
import java.util.List;

// 시험지 VO
public class TestPaper {
	private int testNo;				// 시험 번호
	private int testScore;			// 시험 점수
	private String testDate;		// 시험 날짜
	private int memberNo;			// 시험 본 회원 번호
	private String memberName;		// 응시자
	// 시험 문제 리스트 (10개)
	private List<Question> questionList;
	
	public TestPaper() {
		questionList = new ArrayList<>();
	}

	public int getTestNo() {
		return testNo;
	}

	public void setTestNo(int testNo) {
		this.testNo = testNo;
	}

	public int getTestScore() {
		return testScore;
	}

	public void setTestScore(int testScore) {
		this.testScore = testScore;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}
	public void setQuesion(Question question) {
		this.questionList.add(question);
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
}
