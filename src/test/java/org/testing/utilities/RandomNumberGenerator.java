package org.testing.utilities;

import java.util.Random;

public class RandomNumberGenerator {

	public static String randomNumber() {
		Random random = new Random();
		Integer randomNumber = random.nextInt(999999) + 1;
		return randomNumber.toString();
	}
}