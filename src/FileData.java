import java.util.HashMap;


public class FileData {

	private HashMap<String, Integer> originalMap;
	private HashMap<Integer, String> displayMap;
	
	public FileData(){
		this.originalMap = null;
		this.displayMap = null;
	}
	
	/**
	 * This constructors sets the originalMap and displayMap
	 * @param originalMap	content in it's original sequence
	 * @param displayMap	content in sorted sequence in ascending order 	
	 */
	public FileData(HashMap<String, Integer> originalMap, HashMap<Integer, String> displayMap){
		this.originalMap = originalMap;
		this.displayMap = displayMap;
	}
	
	/**
	 * This methods returns the hash map contains the content in it's original sequence 
	 * @return the HashMap<String, Integer>
	 */
	public HashMap<String, Integer> getOriginalMap(){
		return originalMap;
	}
	
	/**
	 * This methods returns the hash map contains the content in the sorted sequence in ascending order
	 * @return the HashMap<Integer, String>
	 */
	public HashMap<Integer, String> getDisplayMap(){
		return displayMap;
	}
}
