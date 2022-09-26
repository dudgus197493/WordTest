package edu.kh.wordtest.word.vo;

import java.util.Objects;

public class Meaning {
	private int meaningNo;		// 뜻 번호
	private String meaningName;	// 뜻 이름
	private int wordNo;			// 단어 번호
	private String posCode; 	// 품사 코드
	private String posName;		// 품사 이름
	
	public Meaning() { }

	public int getMeaningNo() {
		return meaningNo;
	}

	public void setMeaningNo(int meaningNo) {
		this.meaningNo = meaningNo;
	}

	public String getmeaningName() {
		return meaningName;
	}

	public void setmeaningName(String meaningName) {
		this.meaningName = meaningName;
	}

	public int getWordNo() {
		return wordNo;
	}

	public void setWordNo(int wordNo) {
		this.wordNo = wordNo;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(meaningName, posCode);
	}
}

