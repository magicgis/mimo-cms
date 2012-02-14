package com.mimo.cms.infrastruture.safe;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 
 * @author loudyn
 *
 */
public class MD5HashUtils {
	/**
	 * 
	 * @param source
	 * @param salt
	 * @return
	 */
	public static String asMD5(Object source, Object salt) {
		return new Md5Hash(source, salt).toBase64();
	}
}
