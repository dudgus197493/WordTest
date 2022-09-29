package edu.kh.wordtest.word.vo;

import java.util.ArrayList;
import java.util.List;

// Word VO
public class Word {
	private int wordNo;				// 단어 번호
	private String wordName;		// 단어 이름
	private List<Meaning> meaningList; // 뜻
	private int accurateCount;		// 정답 횟수
	private int wrongCount;			// 오답 횟수
	public Word() {
		meaningList = new ArrayList<>();
		this.accurateCount = -1;
		this.wrongCount = -1;
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

	public List<Meaning> getMeaningList() {
		return meaningList;
	}
	
	public Meaning getRandomMeaning() {
		if(this.meaningList.size() == 0) {
			System.out.println("\n[뜻 리스트가 비어있습니다.]\n");
			return null;
		}
		int ranIdx = (int)(Math.random() * this.meaningList.size());  // 랜덤한 난수 생성 ( 0 ~ meaningList의 크기 - 1 )
		return this.meaningList.get(ranIdx);						  // meaningList에서 랜덤 인덱스 객체 반환
	}

	public void setMeaningList(List<Meaning> meaningList) {
		this.meaningList = meaningList;
	}
	
	public void setMeaning(Meaning meaning) { 
		this.meaningList.add(meaning);
	}

	public int getAccurateCount() {
		return accurateCount;
	}

	public void setAccurateCount(int accurateCount) {
		this.accurateCount = accurateCount;
	}

	public int getWrongCount() {
		return wrongCount;
	}

	public void setWrongCount(int wrongCount) {
		this.wrongCount = wrongCount;
	}
}
