package main.FileStorage;


import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//@@author A0125531R
public class JsonArray {

	/**
	 * Arrange and sort the JSONArray in the format of {"task", "start-date", "start-time", "end-date", "end-time"} 
	 * and remove the '\' appending in front of '/'
	 * @param event the JSONArray to be formatted
	 * @return a JSONArray in the format of {"task", "start-date", "start-time", "end-date", "end-time"}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray eventJsonArray(JSONArray event){
		
		JSONArray eventArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<event.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(event.get(i).toString().replace("\\", ""));
				
				Map eventMap =new LinkedHashMap();
				eventMap.put("task", jsonObject.get("task"));
				eventMap.put("start-date", jsonObject.get("start-date"));
				eventMap.put("start-time", jsonObject.get("start-time"));
				eventMap.put("end-date", jsonObject.get("end-date"));
				eventMap.put("end-time", jsonObject.get("end-time"));
								
				eventArray.add(eventMap);				
			}		
		}catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
	
		return eventArray;
	}
	
	/**
	 * Arrange and sort the JSONArray in the format of {"task", "end-date", "end-time"} 
	 * and remove the '\' appending in front of '/'
	 * @param deadline the JSONArray to be formatted
	 * @return a JSONArray in the format of {"task", "end-date", "end-time"}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray deadlineJsonArray(JSONArray deadline){
		
		JSONArray deadlineArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<deadline.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(deadline.get(i).toString().replace("\\", ""));
				
				Map deadlineMap =new LinkedHashMap();
				deadlineMap.put("task", jsonObject.get("task"));
				deadlineMap.put("end-date", jsonObject.get("end-date"));
				deadlineMap.put("end-time", jsonObject.get("end-time"));
			
				deadlineArray.add(deadlineMap);
			}
			
		}catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		return deadlineArray;
	}
	
	/**
	 * Arrange and sort the JSONArray in the format of {"task"} and remove the '\' appending in front of '/'
	 * @param floating the JSONArray to be formatted
	 * @return a JSONArray in the format of {"task"}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONArray floatingJsonArray(JSONArray floating){
		
		JSONArray floatingArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<floating.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(floating.get(i).toString().replace("\\", ""));
				
				Map floatingMap =new LinkedHashMap();
				floatingMap.put("task", jsonObject.get("task"));				
			
				floatingArray.add(floatingMap);
			}
			
		}catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		
		return floatingArray;
	}
}
