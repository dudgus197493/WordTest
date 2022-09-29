package edu.kh.wordtest.test.model.service;

import java.util.List;

import edu.kh.wordtest.member.vo.Member;
import edu.kh.wordtest.test.vo.TestPaper;

public interface TestService {
	
	
	/**	시험지 생성 서비스
	 * @return paper
	 */
	public TestPaper creatTestPaper() throws Exception;
	
	/** 시험 결과 기록 서비스
	 * @return result
	 * @throws Exception
	 */
	public int recodeTest(TestPaper paper) throws Exception;
	
	/** 모든 시험 기록 조회 서비스
	 * @return paperList
	 * @throws Exception
	 */
	public List<TestPaper> selectAllTest() throws Exception;

	/** 시험 기록 상세 조회 서비스
	 * @param testNo
	 * @return paper
	 * @throws Exception
	 */
	public TestPaper selectTest(int testNo) throws Exception;
	
	/** 회원 티어 승급 서비스
	 * @param memberNo
	 * @return member (tierLevel, tierName)
	 * @throws Exception
	 */
	public Member updateTier(int memberNo) throws Exception;
}
