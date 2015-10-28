package main;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonArray {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray eventJsonArray(JSONArray event){
		
		
		JSONArray eventArray = new JSONArray();
			
		try{	
			JSONParser jsonParser = new JSONParser();
			
			for(int i=0; i<event.size(); i++){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(event.get(i).toString().replace("\\", ""));
				
			
				//Solved
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return eventArray;
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return deadlineArray;
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return floatingArray;
	}
}
