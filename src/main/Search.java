package main;

import java.util.ArrayList;

public class Search extends Command {
	
	private ArrayList<String> strSearchStrings;
	
	public Search(){
		super(CommandType.SEARCH);
		strSearchStrings = new ArrayList<String>();
		 
	}
	
	public ArrayList<String> getSearchStrings(){
		return strSearchStrings;
	}
	
	public void addSearchString(String searchString){
		strSearchStrings.add(searchString);
	}

}
