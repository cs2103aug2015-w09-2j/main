
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/***
 * 
 * @author Muhammad Razali
 * This is a wrapper class to perform autocorrect related functions
 * 
 * @Dependencies Levenshtein.java, Jaccard.java
 */
 
 
public  class AutoCorrect {
	
	/***
	 * The following are overloaded methods of findClosestMatch. It finds the closest matches
	 * from a given wordsList.
	 * @param inputWord Finds the closest match to this word
	 * @param wordsList The word list dictionary
	 * @return List of matches found
	 * 
	 * @Note: Default levenshtein max edit distance is 3. Means a max of 3 edits{substitution/deletion/addition} are allowed
	 * 
	 * @Note: Default Jaccard coefficient: 0.4. Helps filter out redundant/hard-to-match words from large dictionary lists
	 * 			The higher the coefficient, 1.0(Max), the more stringent is the similarity required.
	 */
	public static ArrayList<String> findClosestMatch(String inputWord, ArrayList<String> wordsList){
		return findClosestMatch(inputWord, wordsList, Jaccard.DEFAULT_JACCARD_COEFFICIENT, Levenshtein.DEFAULT_MAX_EDIT_DISTANCE);
	}
	
	public static ArrayList<String> findClosestMatch(String inputWord, ArrayList<String> wordsList, double minJaccardIndex, int maxEditDistance){
		Set<String> set = createUniqueWordsSet(wordsList);
		ArrayList<String> jaccardMatchWordsList = getJaccardMatches(inputWord, set, minJaccardIndex);
		ArrayList<String> levenshteinMatchWordsList = getLevenshteinMatches(inputWord, jaccardMatchWordsList, maxEditDistance);
		
		return levenshteinMatchWordsList;
	}
	
	public static ArrayList<String> findClosestMatch(String inputWord, ArrayList<String> wordsList, double minJaccardIndex){
		return findClosestMatch(inputWord, wordsList,minJaccardIndex, Levenshtein.DEFAULT_MAX_EDIT_DISTANCE);
	}
	
	public static ArrayList<String> findClosestMatch(String inputWord, ArrayList<String> wordsList, int maxEditDistance){
		return findClosestMatch(inputWord, wordsList, Jaccard.DEFAULT_JACCARD_COEFFICIENT, maxEditDistance);
	}
	
	/***
	 * Using the jaccard algorithm, this function filters out hard-to-match words
	 * @param inputWord The word to match
	 * @param set A set of words which are all unique
	 * @param minJaccardIndex The jaccard coefficient
	 * @return A list of unique words which have a higher similarity to the input word
	 */
	private static ArrayList<String> getJaccardMatches(String inputWord, Set<String> set, double minJaccardIndex){
		Jaccard jaccard;
		ArrayList<String> jaccardMatchWordsList = new ArrayList<String>();
		
		for(String word : set){
			jaccard = new Jaccard(inputWord, word, minJaccardIndex);
			if(jaccard.isNearSimilar()){
				jaccardMatchWordsList.add(word);
			}
		}
		
		return jaccardMatchWordsList;
	}
	
	/***
	 * Finds the matches based on  max allowed edits {substitutions/deletions/additions}
	 * @param inputWord The word to match
	 * @param wordsList A list of words
	 * @param maxEditDistance Max allowed edits
	 * @return A list of possible word matches
	 */
	private static ArrayList<String> getLevenshteinMatches(String inputWord, ArrayList<String> wordsList, int maxEditDistance){
		Levenshtein levenshtein;
		ArrayList<String> levenshteinMatchWordsList = new ArrayList<String>();
		for(String word : wordsList){
			levenshtein = new Levenshtein(inputWord, word, maxEditDistance);
			if(levenshtein.isNearSimilar()){
				levenshteinMatchWordsList.add(word);
			}
			
		}
		
		return levenshteinMatchWordsList;
	}
	
	/***
	 * Filters out repetitive words
	 * @param wordsList A list of strings to filter
	 * @return Filtered unique word list
	 */
	private static Set<String> createUniqueWordsSet(ArrayList<String> wordsList){
		Set<String> set = new HashSet<String>();
		
		for(String word : wordsList){
			set.add(word);
		}
		
		return set;
	}
}
