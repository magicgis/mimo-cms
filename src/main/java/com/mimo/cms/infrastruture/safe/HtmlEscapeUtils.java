package com.mimo.cms.infrastruture.safe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mimo.util.ExceptionUtils;
import com.mimo.util.ReflectionUtils;

/**
 * 
 * @author loudyn
 * 
 */
public class HtmlEscapeUtils {
	private static final Map<Character, String> BASIC = new HashMap<Character, String>();

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
	public static String escape(String unsafe) {

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

	private static final ConcurrentHashMap<Class<?>, List<Field>> CACHE = new ConcurrentHashMap<Class<?>, List<Field>>();

	/**
	 * 
	 * @param clazz
	 * @param object
	 */
	public static void escape(Class<?> clazz, Object object) {

		List<Field> fields = CACHE.get(clazz);

		if (null == fields) {
			fields = findNeccessaryFields(clazz);
			CACHE.putIfAbsent(clazz, fields);
		}

		try {

			for (Field f : fields) {
				escapeOnField(f, object);
			}
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	private static List<Field> findNeccessaryFields(Class<?> clazz) {

		List<Field> result = new ArrayList<Field>();

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
		ReflectionUtils.setFieldValue(object, f.getName(), escape(value));
	}
}
