package craw;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
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
		// 가격,제품 추출, 필요없는 정보들도 포함되며 아래의 for 문에서 걸러냄
		Elements priceElements = doc.select("td>font[color]");
		Elements nameElements = doc.select("tr>td>a>font[color]");

		// 필요없는거 걸러내기

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

		cnt = 4;
		str = "";
		for (Element el : nameElements) {
			if (cnt != 0) {
				str = str.concat(el.text() + "\n");
				cnt--;
				if (cnt == 0) {
					products.add(str);
					cnt = 4;
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