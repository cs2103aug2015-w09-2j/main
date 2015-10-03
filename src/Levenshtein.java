/***
 *  @author Razali
 *  
 *  Levenshtein distance, is a string metric for measuring the
 *  difference between two string sequences.
 *  It computes the minimum number of single character edits required
 *  to change one word into the other.
 *  These edits include: Insertion, Deletion and substitution of characters
 *  The bigger the distance, the lesser the similarity of the two words.
 ***/

public class Levenshtein {
	public static final int DEFAULT_MAX_EDIT_DISTANCE = 3;
	
	private String firstWord;
	private String secondWord;
	
	private int lengthFirstWord;
	private int lengthSecondWord;
	private int maxEditDistance;
	
	/*** CONSTRUCTOR ***/
	public Levenshtein(String word1, String word2){
		this(word1, word2, DEFAULT_MAX_EDIT_DISTANCE);
	}
	
	public Levenshtein(String word1, String word2, int maxEditDistance){
		init(word1, word2, maxEditDistance);
	}
	
	/*** PUBLIC METHODS ***/
	public int computeEditDistance(){
		int[][] distanceArray = create2DArray();
		
		for(int row = 1; row < lengthFirstWord + 1; row++){
			for(int col = 1; col < lengthSecondWord + 1; col++){
				if(firstWord.charAt(row-1) == secondWord.charAt(col-1)){
					distanceArray[row][col] = distanceArray[row-1][col-1];
				} else{
					int min = findMin(distanceArray[row-1][col-1], distanceArray[row][col-1],distanceArray[row-1][col]);
					
					distanceArray[row][col] = min + 1; //add 1 edit distance
				}
			}
		}
		
		return distanceArray[lengthFirstWord][lengthSecondWord];
	}
	
	public boolean isNearSimilar(){
		return computeEditDistance() <= maxEditDistance;
	}
	/*** PRIVATE METHODS ***/
	
	private int findMin(int num1, int num2, int num3){
		int min = Math.min(num1, num2);
		
		return Math.min(min, num3);
	}
	private int[][] create2DArray() {
	    int[][] distanceArray = new int[lengthFirstWord+1][lengthSecondWord+1];
	    
	    for(int row = 0; row < lengthFirstWord+1; row++){
	    	distanceArray[row][0] = row;
	    }
	    
	    for(int col = 0; col < lengthSecondWord+1; col++){
	    	distanceArray[0][col] = col;
	    }
	    
	    return distanceArray;
	}

	
	private void init(String firstWord, String secondWord, int maxEditDistance){
		this.firstWord = firstWord;
		this.secondWord = secondWord;
		
		lengthFirstWord = getLengthOfWord(firstWord);
		lengthSecondWord = getLengthOfWord(secondWord);
		
		this.maxEditDistance = maxEditDistance;
	}
	
	
	private  int getLengthOfWord(String word){
		return word.length();
	}
	
	
	
}
