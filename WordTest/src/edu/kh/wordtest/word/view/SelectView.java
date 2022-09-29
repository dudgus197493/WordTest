package edu.kh.wordtest.word.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.wordtest.main.view.MainView;
import edu.kh.wordtest.word.model.service.WordSelectService;
import edu.kh.wordtest.word.model.service.WordSelectServiceImpl;
import edu.kh.wordtest.word.vo.Meaning;
import edu.kh.wordtest.word.vo.Word;

public class SelectView {
	private Scanner sc = new Scanner(System.in);
	private WordSelectService service = new WordSelectServiceImpl(); 
	
	/**
	 * 검색 메뉴 출력
	 */
	public void selectMenu() {
		int input = -1;
		try {
			do {
				System.out.println("\n***** 검색 메뉴 *****\n");
				System.out.println("[1. 모든 단어 보기]");
				System.out.println("[2. 단어 검색]");
				System.out.println("[3. 정복한 단어 보기]");
				System.out.println("[0. 이전 화면으로]");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: selectAllWord(); break;
				case 2: searchWord(); break;
				case 3: selectConquestWord(); break;
				case 0: System.out.println("\n[이전 화면으로 돌아갑니다.]\n"); break;
				default: System.out.println("\n[메뉴에 있는 번호를 입력해주세요]\n");
				}
			} while(input != 0);
			
		}catch(InputMismatchException e) {
			System.out.println("입력 형식이 올바르지 않습니다.");
			sc.nextLine();
			input = -1;
			e.printStackTrace();
		}
	}
	
	/**
	 * 모든 단어 보기
	 */
	private void selectAllWord() {
		System.out.println("\n[모든 단어 보기]\n");
		
		try {
			List<Word> wordList = service.selectAllWord();
			
			if(wordList.isEmpty()) {		// 비어있으면
				System.out.println("\n[조회된 단어가 존재하지 않습니다.]\n");
			} else {						// 검색 결과 존재하면
				printAllWord(wordList, 1);
			}
		}catch(Exception e) {
			System.out.println("\n<< 모든 단어 조회 중 예외 발생 >>\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * 단어 검색
	 */
	private void searchWord() {
		System.out.println("\n[단어 검색]\n");
		int condition = -1;
		try {
			while(true) {
				try {
					System.out.println("1) 단어");
					System.out.println("2) 뜻");
					System.out.println("3) 품사");
					
					System.out.print("메뉴 : ");
					condition = sc.nextInt();
					sc.nextLine();
					
					if(condition > 0 && condition < 4) {
						break;
					} else {			
						System.out.println("\n[존재하는 메뉴를 입력해주세요.]\n");
					}
				}catch(InputMismatchException e) {
					System.out.println("\n<< 올바른 입력이 아닙니다 >>\n");
					sc.nextLine();
				}
			}
			
			// 검색어 입력 받기
			System.out.print("검색어 : ");
			String keyword = sc.next();
			
			// 검색 서비스 호출해 결과 받기
			List<Word> wordList = service.searchWord(keyword, condition);
			

			if(wordList.isEmpty()) {				// 검색 결과가 없으면
				// "검색 결과가 없습니다" 출력
				System.out.println("\n[검색 결과가 없습니다.]\n");
			} else {								// 있으면
				// 검색한 단어 출력
				printAllWord(wordList, 1);
			}
			
		}catch(Exception e) {
			System.out.println("\n<< 단어 검색 중 예외 발생 >>\n");	
			e.printStackTrace();
		}
	}
	
	private void printAllWord(List<Word> wordList, int page) {
		int start = (page - 1) * 5 + 1;
		int end = page * 5;
		int listSize = wordList.size();
		System.out.printf("\n\n[%d 페이지]\n\n", page);
		for(int i = start; i <= end; i ++) {
			if(i <= listSize &&  wordList.get(i- 1) != null) {
				System.out.printf("   %d.  | %s\n", i, wordList.get(i - 1).getWordName());
				if(wordList.get(i-1).getAccurateCount() != -1) {
					System.out.printf("정답 횟수  | %d번\n", wordList.get(i-1).getAccurateCount());
					System.out.printf("오답 횟수  | %d번\n", wordList.get(i-1).getWrongCount());
				}
				System.out.println("--------------------------");
				for(Meaning m : wordList.get(i - 1).getMeaningList()) {
					System.out.printf("%s[%s] | %s\n", m.getPosName(), m.getPosCode(), m.getmeaningName());
				}
				System.out.println("\n-----------------------------------------------");				
			}
		}
		
		int loop = listSize % 5 == 0 ? listSize / 5 : listSize / 5 + 1;
		for(int i = 1; i <= loop; i++) {
			if(i == page) {
				System.out.printf("[%d] ", i);
			} else {
				System.out.printf("%d ", i);				
			}
		}
		
		int endPage = listSize % 5 == 0 ? listSize / 5 : listSize / 5 + 1;
		while(true) {
			System.out.print("\n페이지 입력 (이전으로 0): ");
			int input = sc.nextInt();
			
			if(input == 0) {
				System.out.println("\n[이전 화면으로 돌아갑니다.]\n");
				break;
			}
			
			if (input > endPage) {
				System.out.println("\n[해당 페이지가 존재하지 않습니다.]\n");
			} else {
				printAllWord(wordList, input);
				break;
			}
		}
	}
	
	/**
	 * 내가 정복한 단어 조회
	 */
	private void selectConquestWord() {
		System.out.println("\n[내가 정복한 단어 조회]\n");
		
		try {
			// 서비스 호출
			List<Word> wordList = service.selectConquestWord(MainView.loginMember.getMemberNo());
			if(wordList.isEmpty()) {
				System.out.println("\n[조회된 결과가 없습니다.]\n");
			} else {
				printAllWord(wordList, 1);
			}
		}catch(Exception e) { 
			System.out.println("\n<< 내가 정복한 단어 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
}
