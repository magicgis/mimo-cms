package com.mimo.cms.infrastruture.safe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mimo.util.ExceptionUtils;
import com.mimo.util.ReflectionUtils;

/**
 * 
 * @author loudyn
 * 
 */
public final class HtmlEscapeUtils {
	private static final Map<Character, String> BASIC = Maps.newHashMap();

	static {
		BASIC.put('>', "gt");
		BASIC.put('<', "lt");
		BASIC.put('\"', "quot");
		BASIC.put('\'', "apos");
	}

	/**
	 * 
	 * @param unsafe
	 * @return
	 */
	public static String escapeString(String unsafe) {

		StringBuilder safe = new StringBuilder();

		char[] unsafes = unsafe.toCharArray();
		int length = unsafes.length;
		for (int i = 0; i < length; i++) {

			char c = unsafes[i];
			String escape = BASIC.get(c);
			if (null == escape) {
				if (c > 0x7f) {
					safe.append("&#").append(Integer.toString(c, 10)).append(";");
					continue;
				}

				if (i == (length - 1)) {
					safe.append(c);
					continue;
				}

				if (c == '&' && unsafes[i + 1] == '<') {
					safe.append("&amp;");
					continue;
				}

				safe.append(c);

			}
			else {
				safe.append('&').append(escape).append(';');
			}
		}

		return safe.toString();

	}

	private static Cache<Class<?>, List<Field>> CACHE = CacheBuilder.newBuilder().concurrencyLevel(8)
																				 .expireAfterAccess(60, TimeUnit.MINUTES)
																				 .maximumSize(100)
																				 .build();

	/**
	 * 
	 * @param clazz
	 * @param object
	 */
	public static void escape(Object object) {

		try {
			
			final Class<?> clazz = object.getClass();
			List<Field> fields = CACHE.get(clazz, new Callable<List<Field>>() {

				@Override
				public List<Field> call() throws Exception {
					return findNeccessaryFields(clazz);
				}

			});

			for (Field f : fields) {
				escapeOnField(f, object);
			}
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	private static List<Field> findNeccessaryFields(Class<?> clazz) {

		List<Field> result = Lists.newLinkedList();

		Field[] fields = clazz.getDeclaredFields();
		if (null == fields || fields.length == 0) {
			return result;
		}

		for (Field f : fields) {

			if (!String.class.isAssignableFrom(f.getType())) {
				continue;
			}

			if (!f.isAnnotationPresent(HtmlEscape.class)) {
				continue;
			}

			result.add(f);
		}

		return result;
	}

	private static void escapeOnField(Field f, Object object) throws Exception {
		String fieldName = f.getName();
		String value = ReflectionUtils.getFieldValue(object, fieldName).toString();
		ReflectionUtils.setFieldValue(object, f.getName(), escapeString(value));
	}

	private HtmlEscapeUtils() {
	}
}
