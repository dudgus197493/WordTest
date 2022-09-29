package edu.kh.wordtest.member.model.dao;

import java.sql.Connection;

import edu.kh.wordtest.member.vo.Member;

public interface MemberDAO {
	
	/** 비밀번호 인증 DAO
	 * @param conn
	 * @param memberPw
	 * @param memberNo
	 * @return result (존재하면 1)
	 * @throws Exception
	 */
	public int checkMemberPw (Connection conn, String memberPw, int memberNo) throws Exception;
	
	/** 비밀번호 변경 DAO
	 * @param conn
	 * @param newMemberPw
	 * @param memberNo
	 * @return result (성공하면 1)
	 * @throws Exception
	 */
	public int updateMemberPw(Connection conn, String newMemberPw, int memberNo) throws Exception;
	
	/** 회원 탈퇴 DAO
	 * @param conn
	 * @param memberNo
	 * @return result(성공하면 1)
	 * @throws Exception
	 */
	public int secession(Connection conn, int memberNo) throws Exception;

	/** 내 정보 조회 DAO
	 * @param conn
	 * @param memberNo
	 * @return member
	 * @throws Exception
	 */
	public Member selectMyInfo(Connection conn, int memberNo) throws Exception;
	
	/** 내 정보 변경 DAO
	 * @param newMemberName
	 * @param newEmail
	 * @return result
	 * @throws Exception
	 */
	public int updateMember(Connection conn, String newMemberName, String newEmail, int memberNo) throws Exception;
	
}
