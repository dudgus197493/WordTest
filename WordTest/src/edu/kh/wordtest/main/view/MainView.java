package edu.kh.wordtest.main.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.wordtest.admin.AdminView;
import edu.kh.wordtest.main.model.service.MainService;
import edu.kh.wordtest.main.model.service.MainServiceImpl;
import edu.kh.wordtest.member.model.service.MemberService;
import edu.kh.wordtest.member.model.service.MemberServiceImpl;
import edu.kh.wordtest.member.view.MemberView;
import edu.kh.wordtest.member.vo.Member;
import edu.kh.wordtest.test.view.TestView;
import edu.kh.wordtest.word.view.SelectView;
import edu.kh.wordtest.word.view.WordView;

public class MainView {
	private MemberView mView = new MemberView(); 
	private WordView wView = new WordView();
	private TestView tView = new TestView();
	
	private SelectView sView = new SelectView();
	private AdminView aView = new AdminView();
	
	private MainService mainService = new MainServiceImpl();
	private MemberService memberService = new MemberServiceImpl();
	
	public static Member loginMember;
	
	private Scanner sc = new Scanner(System.in);
	private int input = -1;
	
	public void mainMenu() {
		
			do {
				try {
				if(loginMember == null) {
					System.out.println("\n***** 메인 메뉴 *****\n");
					System.out.println("1. 로그인");
					System.out.println("2. 회원 가입");
					System.out.println("3. 아이디 찾기");
					System.out.println("4. 비밀번호 찾기");
					System.out.println("0. 프로그램 종료");
					
					System.out.print("메뉴 선택 : ");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1: login(); break;
					case 2: signUp(); break;
					case 3: findMemberId(); break;
					case 4: findMemberPw(); break;
					case 0: System.out.println("\n[프로그램을 종료합니다.]\n");break;
					default:System.out.println("\n[메뉴에 있는 번호를 입력해주세요.]\n");
					}
				
			} else {
					boolean isAdmin = MainView.loginMember.getAdminFlag().equals("Y");
					System.out.printf("\n***** %s의 메뉴 *****\n\n", loginMember.getMemberName());
					System.out.println("1. 회원 기능");
					System.out.println("2. 단어 사전");
					System.out.println("3. 단어 테스트");
					if(isAdmin) {
						System.out.println("4. 관리자 기능");
					}
					System.out.println("0. 로그아웃");
					System.out.println("99. 프로그램 종료");
					
					System.out.print("메뉴 선택 : ");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1: mView.memberMenu(); break;			// 회원 관련 메뉴
					case 2: sView.selectMenu();	break;			// 단어 관련 멘
					case 3: tView.testMenu(); break;
					case 0: 
						loginMember = null; 
						input = -1;
						System.out.println("\n로그아웃 되었습니다.\n");
						break;
					case 99: input = 0; System.out.println("\n[프로그램을 종료합니다.]\n");break;
					case 4: 
						if(isAdmin) aView.adminMenu(); break;
					default:System.out.println("\n[메뉴에 있는 번호를 입력해주세요.]\n");
					}
				}
			}catch(InputMismatchException e) {
				System.out.println("\n<< 입력 형식이 올바르지 않습니다. >>\n");
				sc.nextLine();
				input = -1;
			} 
		} while(input != 0);
	}
	
	private void login() {
		System.out.println("\n[로그인]\n");
		
		System.out.print("아이디 : ");
		String memberId = sc.next();
		
		System.out.print("비밀번호 : ");
		String memberPw = sc.next();
		
		try {
			// 서비스 호출
			Member loginMember = mainService.login(memberId, memberPw);
			// 성공 
			if(loginMember != null) {
				// loginMember필드에 반환값 대입
				MainView.loginMember = loginMember;
				
				// 메시지 출력
				System.out.printf("\n[%s님 안녕하세요!]\n", MainView.loginMember.getMemberName());
			} else { 			// 실패
				// 메시지 출력
				System.out.println("\n[회원 정보가 일치하지 않습니다.]\n");
			}
		} catch (Exception e) {
			System.out.println("\\n<< 로그인 중 예외 발생 >>\\n");
			e.printStackTrace();
		}
	}
	
	private void signUp() {
		System.out.println("\n[회원 가입]\n");
		
		try {
			// 아이디 입력
			String memberId = null;
			while(true) {
				System.out.print("아이디 : ");
				memberId = sc.next();
				
				// 아이디 중복 확인
				// idDupCheck() 서비스 호출 후 결과 받기
				int result = mainService.idDupCheck(memberId);
				
				if(result == 0) {						// 중복이 아니면
					// 메세지 출력
					System.out.println("\n[사용가능한 아이디 입니다.]\n");
					// break;
					break;
				} 	
				// 중복이면
				// 메시지 출력
				System.out.println("\n[이미 사용중인 아이디 입니다.]\n");
				// continue;
			}

			String newPw1 = null;
			String newPw2 = null;
			while(true) {
				// 비밀번호 입력
				System.out.print("비밀번호 : ");
				newPw1 = sc.next();
				
				// 비밀번호 확인 입력
				System.out.print("비밀번호 확인 : ");
				newPw2 = sc.next();
				
				if(newPw1.equals(newPw2)) { 
					System.out.println("\n[비밃번호가 일치합니다.]\n");
					break;
				} 
				System.out.println("\n[비밀번호가 일치하지 않습니다.]\n");
			}

			// 이름 입력
			System.out.print("이름 : ");
			String memberName = sc.next();
			
			// 주민등록 번호 입력
			System.out.print("주민등록번호 (- 포함) : ");
			String memberNno = sc.next();
			
			// 성별 입력
			String memberGender = null;
			while(true) {
				System.out.print("성별 (M / F) : ");
				memberGender = sc.next().toUpperCase();
				
				if(memberGender.equals("M") || memberGender.equals("F")) break;
				System.out.println("\n[M 또는 F 중 입력해주세요.]\n");
			}
			
			Member member = new Member();
			member.setMemberId(memberId);
			member.setMemberPw(newPw1);
			member.setMemberName(memberName);
			member.setMemberNno(memberNno);
			member.setMemberGender(memberGender);
			
			// 회원 가입 서비스 호출
			int result = mainService.signUp(member);
			
			if(result > 0){
				System.out.println("\n[회원가입이 완료되었습니다.]\n");
			} else {
				System.out.println("\n[회원가입에 실패했습니다.]\n");
			}
			
		} catch(Exception e) {
			System.out.println("\n<< 회원가입 중 예외 발생 >>\n");
			e.printStackTrace();
		}
	}
	
	private void findMemberId() {
		System.out.println("\n[아이디 찾기]\n");
		
		try {
			System.out.print("이름 : ");
			String memberName = sc.next();
			
			System.out.print("주민등록번호(-포함) : ");
			String memberNno = sc.next();
			
			Member member = new Member();
			member.setMemberName(memberName);
			member.setMemberNno(memberNno);
			
			String memberId = mainService.findMemberId(member);
			
			if(memberId != null) {
				System.out.printf("\n[아이디 : %s]\n", memberId);
			} else {
				System.out.println("\n[일치하는 아이디가 없습니다.]\n");
			}
		}catch(Exception e) {
			System.out.println("\n<< 아이디 찾기 중 예외 발생 >>\n");
			e.printStackTrace();
		}
	}
	
	private void findMemberPw() {
		System.out.println("\n[비밀번호 찾기]\n");
		
		// 정보 입력
		try {
			System.out.print("아이디 : ");
			String memberId = sc.next();
			
			System.out.print("이름 : ");
			String memberName = sc.next();
			
			System.out.print("주민등록번호(-포함) : ");
			String memberNno = sc.next();
			
			Member member = new Member();
			member.setMemberName(memberName);
			member.setMemberNno(memberNno);
			member.setMemberId(memberId);
			
			int memberNo = mainService.findMemberPw(member);
			
			// 정보에 일치하는 비밀번호가 존재하면
			if(memberNo < 1) {
				System.out.println("\n[정보와 일치하는 계정이 존재하지 않습니다.]\n");
				return;
			} 
			System.out.println("정보와 일치하는 계정이 존재합니다.");
			System.out.println();
			// 비밀번호 변경
			String newMemberPw1 = null;
			String newMemberPw2 = null;
			
			while(true) {
				System.out.print("비밀번호를 변경 하시겠습니까? (Y/N) : ");
				char ch = sc.next().toUpperCase().charAt(0);
				
				if(ch == 'N') {
					System.out.println("\n[비밀번호 변경을 취소했습니다.]\n");
					return;
				} else if(ch == 'Y') {
					break;
				} else {
					System.out.println("\n[Y 또는 N을 입력해주세요.]\n");
				}
			}
			
			while(true) {
				System.out.print("새 비밀번호 (이전 화면으로 0) : ");
				newMemberPw1 = sc.next();
				
				System.out.print("새 비밀번호 확인 (이전 화면으로 0) : ");
				newMemberPw2 = sc.next();
				
				if(newMemberPw1.equals("0") || newMemberPw2.equals("0")) {
					System.out.println("\n[이전화면으로 돌아갑니다.]\n");
					return;
				}
				
				if(newMemberPw1.equals(newMemberPw2)) {
					System.out.println("\n[새 비밀번호가 일치합니다]\n");
					break;
				} else {
					System.out.println("\n[새 비밀번호가 일치하지 않습니다.]\n");
				}
			}
			
			// 비밀번호 변경
			int result = memberService.updateMemberPw(newMemberPw1, memberNo);
			
			if(result > 0) {
				System.out.println("\n[비밀번호가 변경되었습니다.]\n");
			} else {
				System.out.println("\n[비밀번호 변경에 실패했습니다.]\n");
			}
		}catch(Exception e) {
			System.out.println("\n<< 비밀번호 찾기 중 예외 발생 >>\n");
			e.printStackTrace();
		}
	}
}
