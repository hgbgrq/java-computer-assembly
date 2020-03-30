package store;

public interface Sellable extends Manageable{
	
	void setValue(String product, int price);
	String getName();
	int getPrice();
	boolean sell();
	boolean compareNo(String kwd);
}
