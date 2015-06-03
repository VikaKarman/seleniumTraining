package com.training.JenkinsTest;

import java.util.Random;

public class CommonMethods {
	public static String getUniqueId() {
		Random rand = new Random();
		return String.valueOf(rand.nextInt(1000));
	}

}
