package com.example.test;

import java.util.Arrays;
import java.util.List;

public class ReplaceWords {
	public static void main(String[] args) {
		List<String> input = Arrays.asList("cat", "bat", "rat");
		String sentence = "the cattle was rattled by the battery";
		System.out.println(replaceWords(input, sentence));
	}

	public static String replaceWords(List<String> dictionary, String sentence) {
		StringBuilder builder = new StringBuilder();
		String[] words = sentence.split(" ");
		for (String word : words) {
			boolean replace = false;
			for (String dic : dictionary) {
				if (dic.length() <= 3) {
					if (word.startsWith(dic)) {
						builder.append(dic.toLowerCase()).append(" ");
						replace = true;
						break;
					}
				}
			}
			if (!replace) {
				builder.append(word).append(" ");
			}
		}
		return builder.toString().trim();
	}
}
