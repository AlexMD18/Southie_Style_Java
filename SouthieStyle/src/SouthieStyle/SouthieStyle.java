package SouthieStyle;

import java.io.File;
import java.io.*;
import java.io.PrintStream;
import java.util.Scanner;

public class SouthieStyle {
	
	//main function that calls southieFiles with handles everything.
	public static void main(String args[]) throws FileNotFoundException {
		southieFiles();
	}
	
	//Simple file i/o method siliar to what we did in class
	public static void southieFiles() throws FileNotFoundException {
		File input = new File("jaws.txt");
		Scanner sc = new Scanner(input);
		PrintStream outfile = new PrintStream(new File("JawsScriptUpdate.txt"));
		System.setOut(outfile);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			System.out.println(makeSouthie(line));
		}
	}
	
	//Method that uses stringBuilder to read through the script and find words that fit the rules from earlier. This allows me to append the rules to the words which they apply to.
	public static String makeSouthie(String newWords) {
		StringBuilder sb = new StringBuilder();
		String[] words = newWords.split(" ");
	    for (String end : words) {
	    	if (!end.equals("")) {
	    		sb.append(Rules(end));
	    		sb.append(" ");
	    	} else
	    		sb.append(" ");
	    }
	    return sb.toString();
	}

	//If there is an 'r' following a vowel, replace 'r' with 'h'. 
	//I struggled to get this to work for days. It would only read a couple paragraphs, but I found someone use something similar to this with Character on StackOverflow so I am giving credit here. 
	public static String replaceR(String word) {
		String[] vowels = { "a", "e", "i", "o", "u" };

		while (word.toLowerCase().contains("ar") || word.toLowerCase().contains("er")|| word.toLowerCase().contains("ir") || word.toLowerCase().contains("or") || word.toLowerCase().contains("ur")) {
			for (int i = 0; i < vowels.length; i++) {
				if (word.toLowerCase().contains(vowels[i] + "r")) {
					int j = word.toLowerCase().indexOf(vowels[i] + "r");
				if (j > -1) {
						if (Character.isLowerCase(word.charAt(j)) && Character.isLowerCase(word.charAt(j + 1)))
						word = word.replace(vowels[i].toLowerCase() + "r", vowels[i].toLowerCase() + "h");
						else if (Character.isUpperCase(word.charAt(j)) && Character.isUpperCase(word.charAt(j + 1)))
							word = word.replace(vowels[i].toUpperCase() + "R", vowels[i].toUpperCase() + "H");
						else if (Character.isUpperCase(word.charAt(j)) && Character.isLowerCase(word.charAt(j + 1)))
							word = word.replace(vowels[i].toUpperCase() + "r", vowels[i].toUpperCase() + "h");
						else
							word = word.replace(vowels[i] + "R", vowels[i].toLowerCase() + "H");
					}
				}
			}
		}
		return word;
	}

	//If a word ends in 'a', append an 'r'.
	public static String appendR(String word) {
		String lastLetter;
		if (word.length() > 1) {
			lastLetter = word.substring(word.length() - 1);
		} else
			return word;
		if (lastLetter.equalsIgnoreCase("a")) {
			return word + "r";
		}
		return word;
	}

	//Replaces the word "very" with "wicked". (I would like to start a petition to bring that back into use)
	public static String wicked(String word) {
		if (word.equalsIgnoreCase("very")) {
			return "wicked";
		} else
			return word;
	}

	//If there is an eer  or an ir at the end of the word the 'r' gets replaced with "yah".
	public static String yah(String word) {
		String lastLetters;
		if (word.length() > 3) {
			lastLetters = word.substring(word.length() - 3, word.length());
		} else {
			return word;
		}
		if (lastLetters.equalsIgnoreCase("eer")|| (lastLetters.substring(1).equalsIgnoreCase("ir"))) {
			return word.substring(0, word.length() - 1) + "yah";
		}
		return word;
	}

	//If there is an oor, the 'r' gets replaced by "wah".
	public static String wah(String word) {
		String lastLetters;
		if (word.length() > 3) {
			lastLetters = word.substring(word.length() - 3, word.length());
		} else {
			return word;
		}
		if (lastLetters.equalsIgnoreCase("oor"))
			return word.substring(0, word.length() - 1) + "wah";
		return word;
	}

	//method that collects all the rules to be used in the "makeSouthie" method. 
	public static String Rules(String newWords) {
		String words;
		words = wicked(newWords);
		words = yah(words);
		words = wah(words);		
		words = replaceR(words);
		words = appendR(words);
		return words;
	}

}
