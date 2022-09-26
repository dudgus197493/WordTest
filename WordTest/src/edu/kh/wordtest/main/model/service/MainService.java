package edu.kh.wordtest.main.model.service;

import edu.kh.wordtest.member.vo.Member;

public interface MainService {
	
	
	/** 로그인 서비스
	 * @param memberId
	 * @param memberPw
	 * @return loginMember (가입된 정보가 없으면 null)
	 * @throws Exception
	 */
	public Member login(String memberId, String memberPw) throws Exception;
	
	/** 회원 가입 서비스
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Member member) throws Exception;
	
	/** 아이디 중복확인 서비스
	 * @param memberId
	 * @return result (0 : 사용가능)
	 * @throws Exception
	 */
	public int idDupCheck(String memberId) throws Exception;
	
	/** 아이디 찾기 서비스
	 * @param member
	 * @return memberId
	 * @throws Exception
	 */
	public String findMemberId(Member member) throws Exception; 
	
	/** 비밀번호 찾기 서비스
	 * @param member 
	 * @return memberNo
	 * @throws Exception
	 */
	public int findMemberPw(Member member) throws Exception;
}
