package edu.kh.wordtest.admin;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.wordtest.admin.model.service.AdminService;
import edu.kh.wordtest.admin.model.service.AdminServiceImpl;
import edu.kh.wordtest.main.view.MainView;
import edu.kh.wordtest.member.model.service.MemberService;
import edu.kh.wordtest.member.model.service.MemberServiceImpl;
import edu.kh.wordtest.word.model.service.WordSelectService;
import edu.kh.wordtest.word.model.service.WordSelectServiceImpl;
import edu.kh.wordtest.word.view.InsertView;
import edu.kh.wordtest.word.vo.Meaning;
import edu.kh.wordtest.word.vo.Word;

public class AdminView {
	private Scanner sc = new Scanner(System.in);
	private MemberService service = new MemberServiceImpl();
	private InsertView iView = new InsertView();
	private WordSelectService sService = new WordSelectServiceImpl();
	private AdminService aService = new AdminServiceImpl();
	private int input = -1;
	/**
	 * 회원 메뉴
	 */
	public void adminMenu() {
		do {
			try {
				System.out.println("\n***** 관리자 기능 *****\n");
				System.out.println("1. 단어 추가");
				System.out.println("2. 단어 수정");
				System.out.println("3. 단어 삭제");
				System.out.println("0. 메인 메뉴로");
				System.out.print("\n메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: iView.insertMenu(); break;
				case 2: updateWord(); break;
				case 3: deleteWord(); break;
				case 0: System.out.println("[메인 메뉴로 이동합니다.]"); break;
				default : System.out.println("메뉴에 작성된 번호만 입력해주세요.");
				}
				
			}catch(InputMismatchException e) {
				System.out.println("\n <<입력 형식이 올바르지 않습니다.>>");
				sc.nextLine();
				input = -1;
			}			
		}while(input != 0);
	}
	
	void printWord(Word word) {
		System.out.printf("   %d.  | %s\n", word.getWordNo(), word.getWordName());
		if(word.getAccurateCount() != -1) {
			System.out.printf("정답 횟수  | %d번\n", word.getAccurateCount());
			System.out.printf("오답 횟수  | %d번\n", word.getWrongCount());
		}
		System.out.println("--------------------------");
		int count = 1;
		for(Meaning m :word.getMeaningList()) {
			System.out.printf("%d. |  %s[%s] | %s\n", count++, m.getPosName(), m.getPosCode(), m.getmeaningName());
		}
		System.out.println("\n-----------------------------------------------");
	}
	
	private void deleteWord() {
		System.out.println("\n[단어 삭제]\n");
		try {
			System.out.print("삭제할 단어 : ");
			String keyword = sc.nextLine();
			
			Word word = sService.selectWord(keyword);
			if(word != null) { 
				printWord(word);
				while(true) {
					System.out.print("\n정말 삭제하시겠습니까? (Y/N) : ");
					char deleteFlag = sc.next().toUpperCase().charAt(0);
					
					if(deleteFlag == 'Y') {
						int result = aService.deleteWord(word.getWordNo());
						
						if(result > 0) {	// 탈퇴 성공
							System.out.println("\n[삭제 되었습니다.]\n");
							break;
						} else {			// 실패 
							System.out.println("\n[삭제 실패..]\n");
						}
					} else {
						System.out.println("\n[삭제 취소]\n");
						break;
					}
				}
			} else {
				System.out.println("\n[해당 단어는 존재하지 않습니다]\n");
			}			
		}catch(Exception e) {
			System.out.println("\n<< 단어 삭제 중 예외 발생 >>\n");
			e.printStackTrace();
		}
	}
	
	private void updateWord() {
		System.out.println("\n[단어 수정]\n");
		try {
			System.out.print("수정할 단어 입력 : ");
			String keyword = sc.nextLine();
			Word word = sService.selectWord(keyword);
			if(word != null) { 
				printWord(word);
				while(true) {
					try {
						System.out.println("1) 스펠링");
						System.out.println("2) 뜻");
						System.out.print("메뉴 입력 (취소 0) : ");
						int input = sc.nextInt();
						sc.nextLine();					
						
						switch(input) {
						case 1: 
							System.out.print("수정할 스펠링 : ");
							String newSpell = sc.nextLine();
							int result = aService.updateWord(newSpell, word.getWordNo());
							if(result > 0) {
								System.out.println("\n[수정 성공]\n");
							} else {
								System.out.println("\n[수정 실패...]\n");
							}
							return;
						case 2:
							while(true) {
								System.out.print("수정할 뜻의 번호 입력 : ");
								int wordIdx = sc.nextInt();
								sc.nextLine();
								
								if(wordIdx > 0 && wordIdx < word.getMeaningList().size() + 1) {
									Meaning meaning = inputMeaning();
									meaning.setMeaningNo(word.getMeaningList().get(wordIdx - 1).getMeaningNo());
									for(int i = 0; i< word.getMeaningList().size(); i++) {
										Meaning currentMeaning = word.getMeaningList().get(i);
										if(currentMeaning.getPosName().equals(meaning.getPosName())) {	// 품사가 같으면
											double compareResult = getSimilarity(currentMeaning.getmeaningName(), meaning.getmeaningName());
											if(compareResult > 0.6) {
												char ch = 'A';
												System.out.println("\n << 경고 >> : 유사한 뜻이 이미 존재합니다.");
												System.out.println("[기존의 뜻]");
												System.out.printf("%s : %s\n", currentMeaning.getPosName(), currentMeaning.getmeaningName());
												System.out.println("[추가할 뜻]");
												System.out.printf("%s : %s\n", meaning.getPosName(), meaning.getmeaningName());
												System.out.println("-------------------------------");
												System.out.print("정말 이 뜻을 추가하시겠습니까? (Y/N) : ");
												ch = sc.next().toUpperCase().charAt(0);
												if(ch == 'Y') {
													continue;	
												} else {
													System.out.println("\n[Y 또는 N 중 입력해주세요.]\n");
													return;
												}
											}
										}
									}
									meaning.setWordNo(word.getWordNo());
									int updateResult = aService.updateMeaning(meaning);
									if(updateResult > 0) {
										System.out.println("\n[뜻 수정 성공]\n");
									} else {
										System.out.println("\n[뜻 수정 실패]\n");
									}
									return;
								} else {
									System.out.println("\n[해당 번호의 뜻은 존재하지 않습니다.]\n");
								}
							}
							
						case 0: System.out.println("\n[단어 수정 취소]\n"); return;
						default: System.out.println("\n[메뉴에 존재하는 번호를 입력해주세요.]\n");
						}
					} catch(InputMismatchException e) {
						System.out.println("\n<< 올바른 입력이 아닙니다. >>\n");
						sc.nextLine();
					}	
				}
			} else {
				System.out.println("\n[해당 단어는 존재하지 않습니다]\n");
			}		
			
		} catch(Exception e) {
			System.out.println("\n<< 단어 삭제 중 예외 발생 >>\n");
			e.printStackTrace();
		}
	}
	
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
