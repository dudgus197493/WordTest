package edu.kh.wordtest.test.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.wordtest.test.model.service.TestService;
import edu.kh.wordtest.test.model.service.TestServiceImpl;
import edu.kh.wordtest.test.vo.Question;
import edu.kh.wordtest.test.vo.TestPaper;

public class TestView {
	private Scanner sc = new Scanner(System.in);
	private TestService service = new TestServiceImpl();
	
	public void testMenu() {
		int input = -1;
		try {
			do {
				System.out.println("\n***** 단어 테스트 메뉴 *****\n");
				System.out.println("1. 테스트 보기");
				System.out.println("2. 랜덤 단어 첼린지");
				System.out.println("3. 성적 조회");				// 회원 기능으로 빼기
				System.out.println("4. 오답 노트");
				System.out.println("0. 이전 화면으로");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: startTest(); break;
				case 2: break;
				case 3: break;
				case 4: break;
				case 0: System.out.println("\n[이전 화면으로 돌아갑니다.]\n"); break;
				default:System.out.println("\n[메뉴에 있는 번호를 입력해주세요]\n");
				}
			} while(input != 0);
			
		}catch(InputMismatchException e) {
			System.out.println("입력 형식이 올바르지 않습니다.");
			e.printStackTrace();
		}
	}
	
	/**
	 * 시험 시작
	 */
	private void startTest() {
		System.out.println("\n[시험 시작]\n");
		// 시험 문제 만들기
		try {
			System.out.println("\n[시험지 생성중...]\n");
			TestPaper paper = service.creatTestPaper();
			System.out.println("\n[시험지 생성 완료]\n");
			
			// 시험 풀기
			paper = solvePaper(paper);
			
			// 시험 체점
			System.out.println("\n[체점중...]\n");
			gradePaper(paper);
			
			// 시험 기록
			int result = service.recodeTest(paper);
			if(result > 0) {
				System.out.println("\n[성적 기록이 완료되었습니다.]\n");
			} else {
				System.out.println("\n[성적 기록 실패]\n");
			}
			// 시험결과 확인
			printPaper(paper);
			
		}catch(Exception e) {
			System.out.println("\n<< 시험 중 예외 발생 >>\n");
			e.printStackTrace();
		}
	}
	
	private TestPaper solvePaper(TestPaper paper) {
		
		for(int i =0; i < paper.getQuestionList().size(); i++) {
			Question question = paper.getQuestionList().get(i);		// i+1번 문제
			printQuestion(question, i);								// 문제 출력
			System.out.print("답 >> ");
			int userAwnser = 0; 
			while(true) {
				userAwnser = sc.nextInt();							// 사용자 답 받기
				sc.nextLine();
				if(userAwnser > 0 && userAwnser < 6) break;
				System.out.println("\n[1 ~ 5 번 중 입력해주세요]\n");
			}
			question.setUserAnswer(userAwnser);
		}
		
		int input = -1;
		do {
			try {
				System.out.println("\n1. 제출");
				System.out.println("2. 답안 수정");
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				switch(input) {
				case 1: System.out.println("\n[시험지를 제출합니다.]\n") ;break;
				case 2: 
					System.out.print("답안을 수정할 문제번호를 입력하세요 : ");
					int questionNo = sc.nextInt() - 1;
					sc.nextLine();
					if(questionNo > -1 && questionNo < 11) {
						Question question = paper.getQuestionList().get(questionNo);
						printQuestion(question, questionNo);
						int userAwnser = 0; 
						while(true) {
							System.out.print("답 >> ");
							userAwnser = sc.nextInt();							// 사용자 답 받기
							sc.nextLine();
							if(userAwnser > 0 && userAwnser < 6) break;
							System.out.println("\n[1 ~ 5 번 중 입력해주세요]\n");
						}
						question.setUserAnswer(userAwnser);
					}
					break;
				default: System.out.println("\n[메뉴에 존재하는 번호를 입력해주세요.]\n");
				}
				
			}catch(InputMismatchException e) {
				System.out.println("\n<< 입력이 올바르지 않습니다. >>\n");
				input = -1;
				sc.nextLine();
			}
			
		} while(input != 1);

		return paper;
	}
	
	/** 문제 하나 출력
	 * @param question
	 * @param idx
	 */
	private void printQuestion(Question question, int idx) {
		System.out.printf("\n%d번 문제 [%d] | %s[%d]\n", idx + 1,question.getCorrectAnswer() ,question.getWordName(), question.getWordNo());
		System.out.println("---------------");
		List<String> exampleList = question.getExampleList();
		for(int i=0; i<exampleList.size(); i++) {
			System.out.printf("%d. %s\n", i+1, exampleList.get(i));
		}
	}

	/** 시험 결과 출력
	 * @param paper
	 */
	private void printPaper(TestPaper paper) {
		System.out.println("\n[시험 결과]\n");
		List<Question> questionList = paper.getQuestionList();
		
		for(int i =0; i< questionList.size(); i++) {
			Question question = questionList.get(i);
			printQuestion(question, i);
			System.out.printf("정답 : %d번\n", question.getCorrectAnswer());
			System.out.printf("내가 제출한 답 : %d\n", question.getUserAnswer());
		}
		
		System.out.printf("\n[총 점수 : %d점]\n",paper.getTestScore());
	}
	
	/** 시험 체점
	 * @param paper
	 */
	private void gradePaper(TestPaper paper) {
		List<Question> questionList = paper.getQuestionList();
		
		for(int i = 0; i< questionList.size(); i++) {
			Question question = questionList.get(i);
			if(question.getCorrectAnswer() == question.getUserAnswer()) {
				paper.setTestScore(paper.getTestScore() + 10);
				question.setCorrect(true);
			} else {
				question.setCorrect(false);
			}
		}
	}
}
