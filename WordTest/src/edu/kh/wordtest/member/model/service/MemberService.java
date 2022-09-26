package edu.kh.wordtest.member.model.service;

import edu.kh.wordtest.member.vo.Member;

public interface MemberService {
	
	/** 비밀번호 인증 서비스
	 * @param memberPw
	 * @param memberNo
	 * @return result (존재하면 1)
	 * @throws Exception
	 */
	public int checkMemberPw(String memberPw, int MemberNo) throws Exception;
	
	/** 비밀번호 변경 서비스
	 * @param newMemberPw
	 * @param memberNo
	 * @return result (성공하면 1)
	 * @throws Exception
	 */
	public int updateMemberPw(String newMemberPw, int memberNo) throws Exception;
	
	/** 회원 탈퇴 서비스
	 * @param memberNo
	 * @return result (성공하면 1)
	 * @throws Exception
	 */
	public int secession(int memberNo) throws Exception;
}
