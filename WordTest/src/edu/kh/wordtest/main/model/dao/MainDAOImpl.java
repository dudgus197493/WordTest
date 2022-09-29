package edu.kh.wordtest.main.model.dao;

import static edu.kh.wordtest.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.wordtest.member.vo.Member;

public class MainDAOImpl implements MainDAO{
	private Properties prop;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public MainDAOImpl() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("SQL/main-query.xml"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Member login(Connection conn, String memberId, String memberPw) throws Exception {
		Member loginMember = null;
		
		try {
			String sql = prop.getProperty("login");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				loginMember = new Member();
				loginMember.setMemberNo(rs.getInt("MEMBER_NO"));
				loginMember.setMemberId(rs.getString("MEMBER_ID"));
				loginMember.setMemberName(rs.getString("MEMBER_NM"));
				loginMember.setMemberGender(rs.getString("MEMBER_GENDER"));
				loginMember.setTierLevel(rs.getString("TIER_LEVEL"));
				loginMember.setAdminFlag(rs.getString("ADMIN_FL"));
				loginMember.setMemberNno(rs.getString("MEMBER_NNO"));
				loginMember.setEmail(rs.getString("EMAIL"));
				loginMember.setEnrollDate(rs.getString("ENROLL_DT"));
				loginMember.setTierName(rs.getString("TIER_NM"));
				loginMember.setConquestCount(rs.getInt("CONQUEST_COUNT"));
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return loginMember;
	}

	@Override
	public int idDupCheck(Connection conn, String memberId) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("idDupCheck");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("ID_CHECK");
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}

	@Override
	public int signUp(Connection conn, Member member) throws Exception {
		int result = 0;
		// MEMBER_ID, MEMBER_PW, MEMBER_NM, MEMBER_NNO ,MEMBER_GENDER
		try {
			String sql = prop.getProperty("signUp");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberNno());
			pstmt.setString(5, member.getMemberGender());
			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	@Override
	public String findMemberId(Connection conn, Member member) throws Exception {
		String memberId = null;
		
		try {
			String sql = prop.getProperty("findMemberId");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberName());
			pstmt.setString(2, member.getMemberNno());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memberId = rs.getString("MEMBER_ID");
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return memberId;
	}

	@Override
	public int findMemberPw(Connection conn, Member member) throws Exception {
		int memberNo = 0;
		
		try {
			String sql = prop.getProperty("findMemberPw");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberName());
			pstmt.setString(2, member.getMemberNno());
			pstmt.setString(3, member.getMemberId());
			rs = pstmt.executeQuery();
			
			if(rs.next()) memberNo = rs.getInt("MEMBER_NO");
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return memberNo;
	}
}
