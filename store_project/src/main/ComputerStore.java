package main;

import java.util.*;

import craw.CPUCrawler;
import craw.Crawler;
import product.*;
import store.Factory;
import store.Sellable;
import store.Store;

public class ComputerStore {
	
	Vector<Store<?>> storeList = new Vector<>();

	// .ser 파일들을 역직렬화해서 저장.
	void load() {
		
		Store<CPU> cpuStore = new Store<>();
		Store<RAM> ramStore = new Store<>();
		Store<MB> mbStore = new Store<>();
		Store<VGA> vgaStore = new Store<>();
		Store<HDD> hddStore = new Store<>();
		Store<PSU> psuStore = new Store<>();
		Store<Case> caseStore = new Store<>();
		
		// 데이터파일 로드 시도, 파일이 없을 시 새로 생성
		CPUCrawler crw = new CPUCrawler();
		if (!cpuStore.load("CPU_list.ser")) {
			crw.craw(
					"http://youngilcom.co.kr/product/productList.php?cate_c1=2&cate_c2=14&cate_c3=&cate_c4=&nDepth=2&nFirst=0&nSecond=1&nThird=&nForth=");
			cpuStore.makeProducts(crw.crawProductList(), crw.crawPriceList(), new Factory() {
				public Sellable create() {
					return new CPU();
				}
			});
			cpuStore.save("CPU_list.ser");
		}
		
		Crawler crw2 = new Crawler();
		if (!mbStore.load("MB_list.ser")) {
			crw2.craw("http://youngilcom.co.kr/product/productList.php?cate_c1=2&cate_c2=14&cate_c3=&cate_c4=&nDepth=2&nFirst=0&nSecond=1&nThird=&nForth=");
			mbStore.makeProducts(crw2.crawProductList(), crw2.crawPriceList(), new Factory() {
				public Sellable create() {
					return new MB();
				}
			});
			mbStore.save("MB_list.ser");
		}
		
		storeList.add(0, cpuStore);
		//storeList.add(1, ramStore);
		
		//******** RAM 추가되면 mbStore 인덱스를 2로 바꿀 것 !!********//
		storeList.add(1, mbStore);
		
		//storeList.add(3, vgaStore);
		//storeList.add(4, hddStore);
		//storeList.add(5, psuStore);
		//storeList.add(6, caseStore);
	}

	boolean loginMenu(User nowUser, Scanner keyin) {
		
		while (true) {
			String input = "";
			System.out.printf("\t\t\t로그인 ID: %s\n", nowUser);

			System.out.print("\t1. 부품 구매\n\t2. 조립\n\t3. 회원정보\n\t4. 로그아웃\n\t0. 종료\n > ");
			input = keyin.next();

			// admin 으로 로그인하고 clear_user_manager를 입력하면 user 정보들을 초기화 한다.
			if (input.equals("clear_user_manager") && nowUser.compare("admin")) {
				Main.uMgr = new UserManager();
				System.out.println("UserManager를 초기화 했습니다.\n");
				continue;
			}
			
			if (input.equals("clear_info")) {
				nowUser.buyList.clear();
				System.out.println("회원정보를 초기화 했습니다.\n");
				continue;
			}

			if (input.equals("0")) // 종료
				break;

			switch (Integer.valueOf(input)) {
			case 1:
				productBuy(nowUser, keyin);
				break;
			case 2:
				makeComputer(nowUser, keyin);
				break;
			case 3:
				nowUser.showInfo();
				break;
			case 4:
				nowUser = logout();
				/*
				 * 로그아웃 시에 다시 비로그인 메뉴로 돌아가기 위해
				 * 종료가 아닌 로그아웃 했음을 표시하기 위해
				 * true를 리턴.
				 */
				return true;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
		// 로그아웃이 아닌 프로그램 종료 시
		return false;
	}

	private void productBuy(User u, Scanner keyin) {
		String input = "";
		input = productMenu(keyin);
		if (input.equals("0")) // 종료
			return;
		
		try {
			storeList.get(Integer.valueOf(input) - 1).buy(u, keyin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	User logout() {
		System.out.println("로그아웃 되었습니다.");
		return null;
	}
	
	String productMenu(Scanner keyin) {
		System.out.printf("\n부품을 선택하세요... \t1. CPU\t2. RAM\t3. MainBoard\t4. VGA\n"
				+ "\t\t5. HDD\t6. PSU\t7. Case\t\t8. 구매\t0. 종료  > ");
		String input = keyin.next();
		return input;
	}
	
	Sellable addList(String num, Scanner keyin){
		storeList.get(Integer.valueOf(num) - 1).printSimpleAll();
		return storeList.get(Integer.valueOf(num)-1).choice(keyin);
	}
	
	void buyAllParts(User u, Sellable[] parts) {
		for (int i = 0; i < 7; i++) {
			if (parts[i] == null) {
				continue;
			}
			storeList.get(i).sellParts(u, parts[i]);
		}
		System.out.println("/t구매완료 되었습니다./n");
	}


	void makeComputer(User u, Scanner keyin) {
		// 컴퓨터 부품 조립. 부품들을 하나씩 구매해서
		// 하나의 컴퓨터를 구성.

		Sellable[] parts = new Sellable[7];
		while (true) {
			System.out.printf(
					"\tCPU: %s\n\tRAM: %s\n\tMainBoard: %s\n\tVGA: %s\n\tHDD: %s\n\tPower: %s\n\tCase: %s\n\t", 
					parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
			String input = productMenu(keyin);
			if (input.equals("0")) // 종료
				break;
			
			switch (Integer.valueOf(input)) {
			case 1:
				parts[Integer.valueOf(input) - 1] = addList(input, keyin);
				break;
			case 2:
				parts[Integer.valueOf(input) - 1] = addList(input, keyin);
				break;
			case 3:
				parts[Integer.valueOf(input) - 1] = addList(input, keyin);
				break;
			case 4:
				parts[Integer.valueOf(input) - 1] = addList(input, keyin);
				break;
			case 5:
				parts[Integer.valueOf(input) - 1] = addList(input, keyin);
				break;
			case 6:
				parts[Integer.valueOf(input) - 1] = addList(input, keyin);
				break;
			case 7:
				parts[Integer.valueOf(input) - 1] = addList(input, keyin);
				break;
			case 8:
				buyAllParts(u, parts);
				break;
			}
		
	}
	}
}

