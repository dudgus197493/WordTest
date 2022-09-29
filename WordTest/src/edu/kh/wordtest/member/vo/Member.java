package edu.kh.wordtest.member.vo;

// MemberVO
public class Member {
	private int memberNo;		// 회원 번호
	private String memberId;	// 회원 아이디
	private String memberPw;	// 회원 비밀번호
	private String memberName;	// 회원 이름
	private String memberNno;   // 회원 주민등록번호
	private String email;		// 이메일
	private String memberGender;// 회원 성별
	private String tierLevel;	// 티어 레벨
	private String tierName;	// 티어 이름
	private String enrollDate;	// 회원 가입일
	private String adminFlag;	// 관리자 여부
	private int conquestCount;  // 정복한 단어 갯수
	
	
	public Member() { }

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberNno() {
		return memberNno;
	}

	public void setMemberNno(String memberNno) {
		this.memberNno = memberNno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public String getTierLevel() {
		return tierLevel;
	}

	public void setTierLevel(String tierLevel) {
		this.tierLevel = tierLevel;
	}

	public String getTierName() {
		return tierName;
	}

	public void setTierName(String tierName) {
		this.tierName = tierName;
	}

	public String getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}

	public int getConquestCount() {
		return conquestCount;
	}

	public void setConquestCount(int conquestCount) {
		this.conquestCount = conquestCount;
	}
	
}
