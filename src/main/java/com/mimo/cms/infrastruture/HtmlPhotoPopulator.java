package com.mimo.cms.infrastruture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author loudyn
 *
 */
public final class HtmlPhotoPopulator {

	/**
	 * 
	 * @param html
	 * @return
	 */
	public static List<String> populate(String html) {
		
		if (StringUtils.isBlank(html)) {
			return Collections.emptyList();
		}

		Document doc = Jsoup.parse(html);
		Elements eles = doc.select("img[src]");
		if (eles.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> photos = new ArrayList<String>();
		for (Element ele : eles) {
			photos.add(ele.attr("src"));
		}
		
		return photos;
	}

	private HtmlPhotoPopulator() {
	}
}
