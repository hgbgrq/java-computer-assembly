package product;

import java.io.Serializable;

import store.Sellable;

public class MB implements Sellable, Serializable {

	private static final long serialVersionUID = 1L;
	
	static int count = 1;
	int no;
	String maker;
	String series;
	String name;
	String info;
	int price;
	boolean sold = false;
	
	@Override
	public String getName() {
		return String.format("%s %s\n", maker, name);
	}

	@Override
	public int getPrice() {
		return price;
	}

	@Override
	public boolean sell() {
		sold = true;
		return sold;
	}
	
	@Override
	public void setValue(String product, int price) {
		String[] str = product.split("\n");
		no = count;
		count++;
		maker = str[0];
		series = str[1];
		name = str[2];
		info = str[3];
		this.price = price;
	}

	@Override
	public void print() {
		System.out.printf("제품번호: %2d\n제품명: %s %s\n제품정보:%s\n가격: %d\n\n", no, maker, name, info, price);
	}
	
	@Override
	public void printSimple() {
		System.out.printf("제품번호: %2d\t제품명: %s %s\t가격: %d\n", no, maker, name, price);
	}
	
	@Override
	public boolean compare(String kwd) {
		if(String.valueOf(no).equals(kwd))
			return true;
		if(maker.equals(kwd))
			return true;
		if(name.contains(kwd))
			return true;
		if(info.contains(kwd))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s\n", maker, name);
	}

	@Override
	public boolean compareNo(String kwd) {
		if(String.valueOf(no).equals(kwd))
			return true;
		return false;
	}
}
