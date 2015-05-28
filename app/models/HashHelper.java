package models;

import org.mindrot.jbcrypt.BCrypt;

import play.Logger;
import play.db.ebean.Model;

public class HashHelper extends Model {
	
	private static String salt = "$2a$10$KrIA0hwvvVelMHRa411sXO";

	public static String createPassword(String input) {
		return BCrypt.hashpw(input, salt);
	}

	public static boolean checkPassword(String candidate,
			String encryptedPassword) {
		if (candidate == null) {
			return false;
		}
		if (encryptedPassword == null) {
			return false;
		}
		return BCrypt.checkpw(candidate, encryptedPassword);
	}
}