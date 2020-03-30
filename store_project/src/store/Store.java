package store;

import java.io.*;
import java.util.*;

import main.User;

public class Store<T extends Sellable> extends Manager<Manageable> {	
	
	private static final long serialVersionUID = 1L;

	public void sellItem(User u, T item) {
		// item을 사면 그 item의 sell()을 true 반환하도록 만듦
		if (mList.contains(item)) {
			u.addBuy(item);			// user의 buyList에 추가
			item.sell();
			return;
		}
		System.out.printf("'%s'항목을 찾지 못했습니다.\n", item);
	}

	public ArrayList<T> search(Scanner keyin) {
		ArrayList<T> result = null;

		System.out.print("제품 키워드를 입력하세요 > ");
		String k = keyin.next();
		result = (ArrayList<T>) findObjects(k);
		for (Manageable b : result)
			b.print();

		return result;
	}

	public void buy(User u, Scanner keyin) {
		
		ArrayList<T> lst = search(keyin);
		if (lst.isEmpty()) {
			System.out.println("결과를 찾기 못했습니다.");
			return;
		}

		System.out.print("\n사고 싶은 물품의 번호를 입력하세요 > ");
		String n = keyin.next();
		T want = null;
		for (T b : lst)
			if (b.compareNo(n))
				want = b;

		want.print();
		System.out.print("\n위 물품을 구매하시겠습니까?(y/n) > ");
		String yn = keyin.next();
		if (yn.equals("n")) {
			System.out.println("처음으로 돌아갑니다.");
			return;
		} else if (yn.equals("y")) {
			sellItem(u, want);
			System.out.printf("\n\t구매완료\n");
		} else
			System.out.println("잘못된 입력입니다. 처음으로 돌아갑니다.");
	}
	
	public void makeProducts(ArrayList<String> prodList, ArrayList<Integer> priceList, Factory fac) {
		T prod = null;
		for (int i = 0; i < prodList.size(); i++) {
			prod = (T) fac.create();
			prod.setValue(prodList.get(i), priceList.get(i));
			mList.add(prod);
		}
	}
	
	public void save(String fName) {
		try {
			doSerialize(fName, mList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean load(String fName) {
		try {
			undoSerialize(fName);
		} catch (FileNotFoundException e) {
			return false;
		}
		  catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	// Main에서 UserManager에서 재사용을 위해 public static
	public static void doSerialize(String fName, Object obj) throws IOException {
		FileOutputStream fos = new FileOutputStream(fName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.close();
	}

	void undoSerialize(String fName) throws IOException, ClassNotFoundException, EOFException {
		FileInputStream fis = new FileInputStream(fName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		mList = (ArrayList<Manageable>) ois.readObject();
		ois.close();
	}

	public void sellParts(User u, Sellable part) {
		u.addBuy(part);			
		part.sell();
		return;
	}
	public Sellable choice(Scanner keyin) {
		System.out.print("\n 추가하실 제품 번호를 입력하세요> ");
		String num = keyin.next();
		for (Manageable t : mList) {
			if (((Sellable) t).compareNo(num))
				return (Sellable) t;
		}
		return null;
	}
}