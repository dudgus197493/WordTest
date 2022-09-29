package edu.kh.wordtest.member.model.dao;

import static edu.kh.wordtest.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.wordtest.member.vo.Member;

public class MemberDAOImpl implements MemberDAO{
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;

	public MemberDAOImpl() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("SQL/member-query.xml"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public int checkMemberPw(Connection conn, String memberPw, int memberNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("checkMemberPw");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			pstmt.setString(2, memberPw);
			rs = pstmt.executeQuery();
			
			if(rs.next()) result = rs.getInt("CHECK_PW");
			
		} finally {
			close(rs);
			close(pstmt);
		}

		return result;
	}

	@Override
	public int updateMemberPw(Connection conn, String newMemberPw, int memberNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateMemberPw");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newMemberPw);
			pstmt.setInt(2, memberNo);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}

		return result;
	}


	@Override
	public int secession(Connection conn, int memberNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("secession");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}

		return result;
	}


	@Override
	public Member selectMyInfo(Connection conn, int memberNo) throws Exception {
		Member member = null;
		
		try {
			String sql = prop.getProperty("selectMyInfo");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new Member();
				member.setMemberNo(rs.getInt("MEMBER_NO"));
				member.setMemberId(rs.getString("MEMBER_ID"));
				member.setMemberName(rs.getString("MEMBER_NM"));
				member.setMemberGender(rs.getString("MEMBER_GENDER"));
				member.setTierLevel(rs.getString("TIER_LEVEL"));
				member.setAdminFlag(rs.getString("ADMIN_FL"));
				member.setEmail(rs.getString("EMAIL"));
				member.setEnrollDate(rs.getString("ENROLL_DT"));
				member.setTierName(rs.getString("TIER_NM"));
				member.setConquestCount(rs.getInt("CONQUEST_COUNT"));
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		return member;
	}


	@Override
	public int updateMember(Connection conn, String newMemberName, String newEmail, int memberNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateMember");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, newMemberName);
			pstmt.setString(2, newEmail);
			pstmt.setInt(3, memberNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(conn);
		}
		
		return result;
	}
}
