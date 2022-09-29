package edu.kh.wordtest.test.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.wordtest.main.view.MainView;
import edu.kh.wordtest.member.vo.Member;
import edu.kh.wordtest.test.model.service.TestService;
import edu.kh.wordtest.test.model.service.TestServiceImpl;
import edu.kh.wordtest.test.vo.Question;
import edu.kh.wordtest.test.vo.TestPaper;

public class TestView {
	private Scanner sc = new Scanner(System.in);
	private TestService service = new TestServiceImpl();
	
	public void testMenu() {
		int input = -1;
		do {
			try {
				System.out.println("\n***** 단어 테스트 메뉴 *****\n");
				System.out.println("1. 테스트 보기");
				System.out.println("2. 랜덤 단어 첼린지");
				System.out.println("3. 내 성적 조회");
				System.out.println("0. 이전 화면으로");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: startTest(); break;
				case 2: wordChallenge(); break;
				case 3: selectAllTest(); break;
				case 0: System.out.println("\n[이전 화면으로 돌아갑니다.]\n"); break;
				default:System.out.println("\n[메뉴에 있는 번호를 입력해주세요]\n");
				}

			}catch(InputMismatchException e) {
				System.out.println("입력 형식이 올바르지 않습니다.");
				e.printStackTrace();
			}
		} while(input != 0);
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
			
			// 사용자 티어 갱신
			String prevTier = MainView.loginMember.getTierLevel();
			Member member = service.updateTier(MainView.loginMember.getMemberNo());
			
			if(member != null) {
				System.out.println("\n[티어가 상승하였습니다.]\n");
				System.out.printf("<< %s ===> %s[%s] >>", prevTier, member.getTierLevel(), member.getTierName());
				MainView.loginMember = member;
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
		System.out.printf("\n%d번 문제 | %s[%d]\n", idx + 1 ,question.getWordName(), question.getWordNo());
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
	
	/**
	 * 모든 테스트 기록 조회
	 */
	private void selectAllTest() {
		System.out.println("\n[내 성적 조회]\n");
		
		try {
			List<TestPaper> paperList = service.selectAllTest();
			printSelectPaper(paperList, 1);
		}catch(Exception e) {
			System.out.println("\n<< 시험 중 예외 발생 >>\n");
			e.printStackTrace();
		}
	}
	
	private void printSelectPaper(List<TestPaper> paperList, int page) {
		int start = (page - 1) * 10 + 1;
		int end = page * 10;
		int listSize = paperList.size();
		System.out.printf("\n\n[%d 페이지]\n\n", page);
		System.out.println("번호 | 응시자 | 정답갯수 | 점수 | 응시날짜");
		System.out.println("------------------------------------");
		for(int i = start; i <= end; i ++) {
			if(i <= listSize &&  paperList.get(i- 1) != null) {
				TestPaper paper = paperList.get(i - 1); 
				System.out.println("--------------------------");
				System.out.printf("%2d | %5s | %d/10 | %d | %s\n"
						,i, paper.getMemberName(), paper.getTestScore() / 10, paper.getTestScore(), paper.getTestDate());
			}
		}

		int loop = listSize % 10 == 0 ? listSize / 10 : listSize / 10 + 1;
		for(int i = 1; i <= loop; i++) {
			if(i == page) {
				System.out.printf("[%d] ", i);
			} else {
				System.out.printf("%d ", i);				
			}
		}
		
		int endPage = listSize % 10 == 0 ? listSize / 10 : listSize / 10 + 1;
		
		int input = 0;
		try {
			do {
				System.out.println();
				System.out.println("1. 페이지 입력");
				System.out.println("2. 기록 상세 조회");
				System.out.print("메뉴 입력 (이전으로 0) :");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: 
					while(true) {
						System.out.print("\n페이지 입력 (이전으로 0): ");
						int input2 = sc.nextInt();
						
						if(input2 == 0) {
							System.out.println("\n[이전 화면으로 돌아갑니다.]\n");
							break;
						}
						
						if (input2 > endPage) {
							System.out.println("\n[해당 페이지가 존재하지 않습니다.]\n");
						} else {
							printSelectPaper(paperList, input2);
							break;
						}
					}
				break;
				case 2: 
					System.out.print("상세 조회할 기록 번호 입력 : ");
					int input2 = sc.nextInt();
					sc.nextLine();
					
					// 서비스 호출
					TestPaper paper = service.selectTest(paperList.get(input2-1).getTestNo());
					printPaper(paper);
					input = 0;
					printSelectPaper(paperList, 1);
					break;
				case 0 : break;
				default: System.out.println("\n[메뉴에 존재하는 번호를 선택해주세요.]\n");
				}
			}while(input != 0);			
		}
		catch(InputMismatchException e) {
			System.out.println("\n<< 올바른 입력이 아닙니다. >>\n");
			input = -1;
			sc.nextLine();
		} catch(Exception e) {
			System.out.println("\n<< 상세 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	private void wordChallenge() {
		System.out.println("\n[단어 첼린지]\n");
		int userAnswer = -1;
		int accurateCount = 0;
		int totalQuestion = 0;
		try {
			do {
				TestPaper paper = service.creatTestPaper();
				List<Question> questionList = paper.getQuestionList();
				for(int i =0; i< questionList.size(); i++) {
					printQuestion(questionList.get(i), totalQuestion);
					while(true) {
						try {
							System.out.print("답 (종료 0) >> ");
							userAnswer = sc.nextInt();							// 사용자 답 받기
							sc.nextLine();
							if(userAnswer > -1 && userAnswer < 6) break;
							System.out.println("\n[1 ~ 5 번 중 입력해주세요]\n");
						} catch(InputMismatchException e) {
							System.out.println("\n<< 올바른 입력이 아닙니다. >>\n");
							sc.nextLine();
							userAnswer = -1;
						}
					}
					if(userAnswer != 0) {
						Question question = questionList.get(i);
						if(question.getCorrectAnswer() == userAnswer) {
							System.out.println("\n[정답!!!!]\n");
						} else {
							System.out.println("\n[오답...]\n");
							System.out.println("정답 >> " + question.getCorrectAnswer());
							accurateCount++;
						}
						totalQuestion++;
					} else {
						System.out.println("\n[첼린지를 종료합니다.]\n");
						System.out.printf("첼린지 성적 : %d/%d\n",accurateCount, totalQuestion);
						return;
					}
				}
			} while(userAnswer != 0);
		}catch(Exception e) {
				e.printStackTrace();
		}
	}
}