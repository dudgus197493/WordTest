package edu.kh.wordtest.test.model.service;

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
}
