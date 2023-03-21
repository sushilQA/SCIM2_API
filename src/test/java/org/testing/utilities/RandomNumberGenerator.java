package org.testing.utilities;

import java.util.Random;
public class RandomNumberGenerator {
	
	public static String randomNumber() {
		Random random = new  Random();
		Integer randomNumber = random.nextInt(1000);
		randomNumber = randomNumber+1;
		return randomNumber.toString();
	}

}
