package com.mimo.cms;

import junit.framework.Assert;

import org.junit.Test;

import com.mimo.cms.domain.guestbook.Guestbook;
import com.mimo.cms.infrastruture.safe.HtmlEscapeUtils;

public class HtmlEscapeTest {

	@Test
	public void testEscape() {
		String unsafe = "&lt;&gt;&amp;&quot;&apos;";
		Assert.assertEquals("&lt;&gt;&amp;&quot;&apos;", HtmlEscapeUtils.escapeString(unsafe));
		unsafe = "<>\"\'";
		Assert.assertEquals("&lt;&gt;&quot;&apos;", HtmlEscapeUtils.escapeString(unsafe));
		unsafe = "&<>\"\'";
		Assert.assertEquals("&amp;&lt;&gt;&quot;&apos;", HtmlEscapeUtils.escapeString(unsafe));
	}

	@Test
	public void testEscapeOnObject() {
		Guestbook entity = new Guestbook().setEmail("<>").setContent("\"'");
		HtmlEscapeUtils.escape(entity);
		Assert.assertEquals("&lt;&gt;", entity.getEmail());
		Assert.assertEquals("&quot;&apos;", entity.getContent());

		entity = new Guestbook().setEmail("<>\"\'").setContent("&<>\"\'");
		HtmlEscapeUtils.escape(entity);
		Assert.assertEquals("&lt;&gt;&quot;&apos;", entity.getEmail());
		Assert.assertEquals("&amp;&lt;&gt;&quot;&apos;", entity.getContent());
	}
}
