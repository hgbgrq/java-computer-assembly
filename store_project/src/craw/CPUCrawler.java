package craw;

import java.io.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CPUCrawler {
	ArrayList<String> products = new ArrayList<>();
	ArrayList<Integer> prices = new ArrayList<>();
	
	public void craw(String url) {
		Document doc = null;
		String str = "";
		int cnt = 0;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//가격,제품 추출, 필요없는 정보들도 포함되며 아래의 for 문에서 걸러냄
		Elements priceElements = doc.select("td>font[color]");
		Elements nameElements = doc.select("tr>td>a>font[color]");

		//필요없는거 걸러내기
		cnt = doc.select("table.outtable>tbody>tr>td>a").size();
		for (Element el : priceElements) { 
			if (el.text().contains("원")) {
				if (cnt != 0) {
					cnt--;
					continue;
				}
				str = el.text();
				str = str.replaceAll(",", "");
				str = str.replace("원", "");
				prices.add(Integer.parseInt(str));
			}
		}

		cnt = 0;
		str = "";
		for (Element el : nameElements) {
			if (el.text().equals("인텔") || el.text().equals("AMD"))
				cnt = 4;
			if (cnt != 0) {
				str = str.concat(el.text() + "\n");
				cnt--;
				if (cnt == 0) {
					products.add(str);
					str = "";
				}
			}
		}
	}
	
	public ArrayList<Integer> crawPriceList() {
		return prices;
	}
	
	public ArrayList<String> crawProductList() {
		return products;
	}
}
