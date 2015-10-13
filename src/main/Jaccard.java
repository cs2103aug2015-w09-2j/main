package main;

import java.util.*;

/***
 *
 * @author Muhammad Razali
 *
 * This Class is based on Jaccard index, it  compares two strings' similarity.
 * It determines the similarity of two words based on a Jaccard index coefficient from 0.0 to 1.0
 */
public final class Jaccard {

	public static final double DEFAULT_JACCARD_COEFFICIENT = 0.4;

	private Set<String> inputWordsSet;
	private Set<String> wordsSet;
	private Set<String> intersectionSet;
	private Set<String> unionSet;
	private double  minJaccardCoefficient;


	/*** CONSTRUCTORS ***/

	public Jaccard(String inputString, String wordsString, double minJaccardCoefficient){
		wordsSet = createSetFromString(wordsString);
		inputWordsSet = createSetFromString(inputString);

		if(minJaccardCoefficient > 1 || minJaccardCoefficient < 0){
			this.minJaccardCoefficient = DEFAULT_JACCARD_COEFFICIENT;
		} else{
			this.minJaccardCoefficient = minJaccardCoefficient;
		}
	}

	public Jaccard(String inputString, String wordsString){
		this(inputString, wordsString, DEFAULT_JACCARD_COEFFICIENT);
	}

	/*** PUBLIC METHODS ***/
	public double findJaccardCoefficient(){
		 intersectionSet = findIntersection(inputWordsSet, wordsSet);
		 unionSet = findUnion(inputWordsSet, wordsSet);

		 double intersectionLength = intersectionSet.size();
		 double unionLength = unionSet.size();
		 double jaccardCoefficient = intersectionLength/unionLength;

		 return jaccardCoefficient;
	}

	public boolean isNearSimilar(){
		return minJaccardCoefficient <= findJaccardCoefficient();
	}
	/*** PRIVATE METHODS ***/

	private Set<String> findIntersection(Set<String> set1, Set<String> set2){
		Set<String> intersectionSet = createEmptySet();
		intersectionSet.addAll(set1);
		intersectionSet.retainAll(set2);

		return intersectionSet;
	}

	private Set<String> findUnion(Set<String> set1, Set<String> set2){
		Set<String> unionSet = createEmptySet();

		unionSet.addAll(set1);
		unionSet.addAll(set2);

		return unionSet;
	}

	private Set<String> createSetFromString(String string){
		Set<String> set = createEmptySet();
		ArrayList<String> words = splitString(string);

		for(String word : words){
			set.add(word);
		}

		return set;
	}

	private ArrayList<String> splitString(String string){
		char[] array = string.toCharArray();
		ArrayList<String> stringList = new ArrayList<String>();

		//Insert first & last char as String
		stringList.add(array[0] + "");
		stringList.add(array[array.length-1] + "");

		//Add strings of max length 2
		//eg. "potato" = "po", "ot", "ta", "at", "to"
		for(int i = 0; i < array.length - 1; i++){
			stringList.add("" + array[i] + array[i+1]);
		}

		return stringList;
	}

	private Set<String> createEmptySet(){
		return new HashSet<String>();
	}

}
