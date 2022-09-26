package edu.kh.wordtest.main.model.dao;

import java.sql.Connection;

import edu.kh.wordtest.member.vo.Member;

public interface MainDAO {
	
	
	/** 로그인 DAO
	 * @param memberId
	 * @param memberPw
	 * @return loginMember (가입된 정보가 없으면 null)
	 * @throws Exception
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception;
	
	/** 아이디 중복확인 DAO
	 * @param conn
	 * @param memberId
	 * @return result (0 : 사용가능)
	 * @throws Exception
	 */
	public int idDupCheck(Connection conn, String memberId) throws Exception;
	
	/** 회원 가입 DAO
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Connection conn, Member member) throws Exception;
	
	/** 아이디 찾기 DAO
	 * @param member
	 * @return memberId
	 * @throws Exception
	 */
	public String findMemberId(Connection conn, Member member) throws Exception;
	
	/** 비밀번호 찾기 DAO
	 * @param member
	 * @return memberNo
	 * @throws Excpetion
	 */
	public int findMemberPw(Connection conn, Member member) throws Exception;
}
