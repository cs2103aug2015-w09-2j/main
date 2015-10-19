package main;
import java.util.*;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
public class TryPreetyTime {
	 public static void main(String[] args)
	   {
	      List<Date> dates = new PrettyTimeParser().parse("I'm going to the beach in three days!");
	      System.out.println(dates);
	      // Prints: "[Sun Dec 12 13:45:12 CET 2013]"
	   }
}
