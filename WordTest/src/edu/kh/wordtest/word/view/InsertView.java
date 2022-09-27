package edu.kh.wordtest.word.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.wordtest.word.model.service.WordInsertService;
import edu.kh.wordtest.word.model.service.WordInsertServiceImpl;
import edu.kh.wordtest.word.model.service.WordSelectService;
import edu.kh.wordtest.word.model.service.WordSelectServiceImpl;
import edu.kh.wordtest.word.vo.Meaning;
import edu.kh.wordtest.word.vo.Word;

public class InsertView {
	private Scanner sc = new Scanner(System.in);
	private WordInsertService insertService = new WordInsertServiceImpl();
	private WordSelectService selectService = new WordSelectServiceImpl();
	public void insertMenu() {
		int input = -1;
		try {
			do {
				System.out.println("\n***** 단어 추가 메뉴 *****\n");
				System.out.println("[1. 새로운 단어 추가]");
				System.out.println("[2. 기존단어 뜻 추가]");
				System.out.println("[0. 이전 화면으로]");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: insertWord(); break;
				case 2: insertMeaning(null); break;
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
	 * 단어 추가
	 */
	private void insertWord() {
		System.out.println("\n[단어 추가]\n");

		try {
			// 단어 중복확인
			System.out.print("단어(eng) : ");
			String word = sc.nextLine();
			Word resultWord = selectService.selectWord(word);
			
			if(resultWord != null) {		// 중복이면
				// 메시지 출력
				System.out.println("\n[이미 존재하는 단어입니다.]\n");
				
				// 해당 단어에 뜻 추가여부 물어보기
				while(true) {
					System.out.print("뜻을 추가하시겠습니까? (Y/N) : ");
					char ch = sc.next().toUpperCase().charAt(0);
					
					if(ch == 'Y') {
						break;
					} else if (ch == 'N') {
						return;
					} else {
						System.out.println("\n[Y 또는 N 중 하나를 입력해주세요.]\n");
					}
				}
				// y 선택시
				// 기존단어 뜻 추가 호출 break;
				insertMeaning(resultWord);
			} else { 						// 중복이 아니면
				// 뜻입력
				// while로
					// 여러개 뜻 받기 meaning객체
				Word insertWord = new Word();
				boolean insertFlag = false;
				List<Meaning> meaningList = new ArrayList<>();
				do {
					Meaning meaning = inputMeaning();
					meaningList.add(meaning);
					
					while(true) {
						System.out.print("뜻을 더 추가하시겠습니까?(Y/N) : ");
						char ch = sc.next().toUpperCase().charAt(0);
						if(ch == 'N') {
							insertFlag = true;
							break;
						} else if (ch == 'Y') {
							System.out.println("\n[뜻을 더 추가합니다.]\n");
							break;
						} else {
							System.out.println("\n[Y 또는 N 중 하나를 입력해주세요.]\n");
						}
					}
				} while(!insertFlag);
				insertWord.setWordName(word); 			
				insertWord.setMeaningList(meaningList);
				
				// 뜻추가 서비스 호출
				int result = insertService.insertWord(insertWord);
				
				if(result > 0) {
					System.out.println("\n[단어가 추가되었습니다.]\n");
				} else {
					System.out.println("\n[단어가 추가 실패]\n");
				}
				
				printWord(insertWord);
			} // end else

		} catch(Exception e) {
			System.out.println("\n<< 단어 추가 중 예외 발생 >>\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * 기존의 단어에 뜻 추가
	 */
	private void insertMeaning(Word word) {
		System.out.println("\n[기존의 단어에 뜻 추가]\n");
		try {
			if(word == null) {	// 매개변수가 null 일 때
				System.out.print("단어(eng) : ");
				String keyword = sc.nextLine();
				word = selectService.selectWord(keyword);
				if(word == null) {
					System.out.println("\n[일치하는 단어가 존재하지 않습니다.]\n");
					return;
				}
			}
			
			List<Meaning> currentMeaningList = word.getMeaningList();		// 현재 가지고 있는 뜻 리스트
			
			printWord(word);											
			List<Meaning> insertMeaningList = inputMeaningList();			// 새롭게 추가할 뜻 리스트
			
			insertMeaningList = compareMeaning(currentMeaningList, insertMeaningList);	// 기존 뜻 리스트와 새롭게 추가할 뜻 리스트 비교 후
																						// 유사한 뜻 삭제한 리스트 반환
			// insertMeaning 메서드 호출
			int result = insertService.insertMeaning(word.getWordNo(), insertMeaningList);
			
			if(result > 0) {
				System.out.println("\n[뜻을 추가하였습니다.]\n");
			} else {
				System.out.println("\n[뜻 추가 실패]\n");
			}
			
		} catch(Exception e) {
			System.out.println("\n<< 기존의 단어에 뜻 추가 중 예외 발생 >>\n");
			e.printStackTrace();
		}
	}
	
	/** 단어 하나 출력
	 * @param word
	 */
	private void printWord(Word word) {
		System.out.print("\n[추가된 단어]\n");
		System.out.printf("new | %s\n", word.getWordName());
		System.out.println("----------------------------");
		for(Meaning m : word.getMeaningList()) {
			System.out.printf("%s | %s\n", m.getPosName(), m.getmeaningName());
		}
	}
	
	/** 뜻 입력 받기
	 * @return meaning
	 */
	private Meaning inputMeaning() {
		String posName = null;
		String meaningName = null;
		while(true) {
			System.out.print("품사 : ");
			posName = sc.next();
			sc.nextLine();
			boolean flag = false;
			switch(posName) {
			case "명사":
			case "형용사":
			case "동사":
			case "부사":
			case "대명사":
			case "전치사":
			case "접속사":
			case "감탄사": flag = true; break;
			default : System.out.println("\n[품사를 입력해주세요.]\n");
			}
			if(flag) break;
		}
		System.out.print("뜻 : ");
		meaningName = sc.nextLine();
			
		Meaning meaning = new Meaning();
		meaning.setPosName(posName);
		meaning.setmeaningName(meaningName);
		
		return meaning;
	}
	
	/** 뜻 리스트 입력 받기
	 * @return meaningList
	 */
	private List<Meaning> inputMeaningList() {
		boolean insertFlag = false;
		List<Meaning> meaningList = new ArrayList<>();
		do {
			Meaning meaning = inputMeaning();
			meaningList.add(meaning);
			
			while(true) {
				System.out.print("뜻을 더 추가하시겠습니까?(Y/N) : ");
				char ch = sc.next().toUpperCase().charAt(0);
				if(ch == 'N') {
					insertFlag = true;
					break;
				} else if (ch == 'Y') {
					System.out.println("\n[뜻을 더 추가합니다.]\n");
					break;
				} else {
					System.out.println("\n[Y 또는 N 중 하나를 입력해주세요.]\n");
				}
			}
		} while(!insertFlag);
		return meaningList;
	}
	
	/** 두 뜻 리스트를 비교해서 같은 뜻을 가진 값을 insertMeaning 리스트에서 제거 후 반환
	 * @param cuurentMeaningList
	 * @param insertMeaningList
	 * @return insertMeaning
	 */
	private List<Meaning> compareMeaning(List<Meaning> currentMeaningList, List<Meaning> insertMeaningList) {
		for(int i = 0; i < currentMeaningList.size(); i++) {
			Meaning currentMeaning = currentMeaningList.get(i);
			String currentPosName = currentMeaning.getPosName();			// 기존 품사
			String currentMeaningName = currentMeaning.getmeaningName();	// 기존 뜻
			for(int j = 0; j < insertMeaningList.size();) {
				Meaning insertMeaning = insertMeaningList.get(j);
				String insertPosName = insertMeaning.getPosName();		// 추가할 품사
				String insertMeaningName = insertMeaning.getmeaningName();	// 추가할 뜻

				if(currentPosName.equals(insertPosName)) {		// 품사가 같으면
					// 내용비교
					double result = getSimilarity(currentMeaningName, insertMeaningName);
					if(result > 0.6) {	// 유사도가 0.6보다 크면
						// 정말 추가할거냐고 물어보기
						char ch = 'A';
						while(true) {
							System.out.println("\n << 경고 >> : 유사한 뜻이 이미 존재합니다.");
							System.out.println("[기존의 뜻]");
							System.out.printf("%s : %s\n", currentPosName, currentMeaningName);
							System.out.println("[추가할 뜻]");
							System.out.printf("%s : %s\n", insertPosName, insertMeaningName);
							System.out.println("-------------------------------");
							System.out.print("정말 이 뜻을 추가하시겠습니까? (Y/N) : ");
							ch = sc.next().toUpperCase().charAt(0);
							if(!(ch == 'N' || ch == 'Y')) {
								System.out.println("\n[Y 또는 N 중 입력해주세요.]\n");
								continue;	
							}
							break;
						}
						if(ch == 'N') {						// 추가하지 않을거면
							insertMeaningList.remove(j);	// 해당 뜻을 지우고 insertMeaningList 인덱스를 증가시키지 않음
						} else if (ch == 'Y') {				// 추가한다면
							j++;							// insertMeaningList 인덱스 증가후 넘어감
							continue;
						}
					} else {	// 유사도가 0.6보다 작으면
						j++;	// insertMeaningList 인덱스 증가후 넘어감
					}
				} else {	// 품사가 다르면
					j++;    // insertMeaningList 인덱스 증가후 넘어감
				}
			}
		}
		return insertMeaningList;
	}
	
	// 문자열 유사성 검사
	private double getSimilarity(String s1, String s2) {
		s1 = s1.replace(" ", "");		// 각 문자열 공백 제거
		s2 = s2.replace(" ", "");
		
		String longer = s1, shorter = s2;
		
		if (s1.length() < s2.length()) {
			longer = s2; 
			shorter = s1;
		}
		
		int longerLength = longer.length();
		if (longerLength == 0) return 1.0;
		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
	}
	private int editDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();
		int[] costs = new int[s2.length() + 1];
		
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0) {
					costs[j] = j;
				} else {
					if (j > 0) {
						int newValue = costs[j - 1];
						
						if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						}
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0) costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	} 
}
