package main;

import java.util.Set;
import java.util.TreeSet;

public class ParserUtils {
	
	public String getEnclosedDescription(String text){
		int firstOccurrence, lastOccurrence;
		
		firstOccurrence = text.indexOf('"');
		lastOccurrence = text.lastIndexOf('"');
		
		if(firstOccurrence == -1 || lastOccurrence == -1){
			return null;
		}
		
		String subString = text.substring(firstOccurrence+1, lastOccurrence);
		
		return subString;
	}
	
	public Set<Integer> getRangeSet(String text){
		String[] strSplit = text.split(",");
		Set<Integer> parsedIDs = new TreeSet<Integer>();

		for (String str : strSplit) {
			str = str.replaceAll(" ", ""); // remove all white spaces

			if (str.matches("\\d+-\\d+")) {
				int firstDigit = Integer.valueOf(str.split("-")[0]);
				int secondDigit = Integer.valueOf(str.split("-")[1]);

				// make first smaller than second
				if (firstDigit > secondDigit) {
					firstDigit ^= secondDigit;
					secondDigit = firstDigit;
					firstDigit ^= secondDigit;

				}

				for (int i = firstDigit; i <= secondDigit; i++) {
					parsedIDs.add(i);
				}
			} else if (str.matches("\\d+")) {
				parsedIDs.add(Integer.valueOf(str));
			}
		}
		
		return parsedIDs.size() == 0 ? null : parsedIDs; 
	}
	
}
