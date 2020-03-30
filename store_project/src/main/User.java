package main;

import java.io.Serializable;
import java.util.ArrayList;

import store.Manageable;
import store.Sellable;

public class User implements Manageable, Serializable {

	private static final long serialVersionUID = 1L;
	String id;
	ArrayList<Sellable> buyList;
	
	User(String id) {
		this.id = id;
		this.buyList = new ArrayList<>();
	}

	@Override
	public void print() {
		System.out.println("ID: " + id);
		System.out.println("구매 목록...");
		if (buyList.isEmpty())
			System.out.println("구매한 품목이 없습니다.");
		else
			printBuyList();
	}
	
	void printBuyList() {
		for (Sellable s : buyList) {
			s.printSimple();
		}
	}

	@Override
	public boolean compare(String kwd) {
		if(id.equals(kwd))
			return true;
		return false;
	}
	
	public void addBuy(Sellable item) {
		buyList.add(item);
	}
	
	@Override
	public String toString() {
		return id;
	}

	public void showInfo() {
		System.out.println("\n" + id + "님의 회원 정보");
		print();
		System.out.println();
	}

	@Override
	public void printSimple() {
		// TODO Auto-generated method stub
		
	}
}
