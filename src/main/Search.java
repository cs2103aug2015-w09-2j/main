package main;

public class Search extends Command {

	private String strSearchString;
	
	public Search(String searchString){
		super(CommandType.SEARCH);
		this.strSearchString = searchString;
	}
	
	public String getSearchString(){
		return strSearchString;
	}
}
