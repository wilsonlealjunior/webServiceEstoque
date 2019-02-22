package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenerateHashPasswordUtil {
public static String generateHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}
		String passwordHex = hexString.toString();
		return passwordHex;
	}

}
