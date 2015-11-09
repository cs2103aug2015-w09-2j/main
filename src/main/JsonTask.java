package main;

import java.util.ArrayList;

import main.Deadline;
import main.Event;
import main.Floating;
import main.Task;
import main.DateClass;
import main.TimeClass;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Collections;

//@@author A0125531R
public class JsonTask {

	/**
	 * Covert a JSONArray to an ArrayList<Task>
	 * @param event JSONArray of event task to be converted
	 * @return ArrayList<Task> of event tasks
	 */
	public ArrayList<Task> eventTaskArray(JSONArray event){

		ArrayList<Task> eventTaskList = new ArrayList<Task>();
		JSONParser jsonParser = new JSONParser();

		for(int i=0; i<event.size(); i++){

			try{
				JSONObject obj = (JSONObject) jsonParser.parse(event.get(i).toString().replace("\\", ""));

				String strStartDate = obj.get("start-date").toString();
				String strEndDate = obj.get("end-date").toString();
				String strStartTime = obj.get("start-time").toString();
				String strEndTime = obj.get("end-time").toString();

				String task = obj.get("task").toString();
				DateClass startDate = new DateClass(strStartDate);
				DateClass endDate = new DateClass(strEndDate);
				TimeClass startTime = new TimeClass(strStartTime);
				TimeClass endTime = new TimeClass(strEndTime);

				Event eventTask = new Event(task, startDate, startTime, endDate, endTime);

				eventTaskList.add(eventTask);

			}catch (ParseException e1) {

				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {

				e1.printStackTrace();
			} catch (java.text.ParseException e1) {

				e1.printStackTrace();
			}
		}
		Collections.sort(eventTaskList);
		return eventTaskList;
	}

	/**
	 * Covert a JSONArray to an ArrayList<Task>
	 * @param deadline JSONArray of deadline task to be converted
	 * @return ArrayList<Task> of deadline tasks
	 */
	public ArrayList<Task> deadlineTaskArray(JSONArray deadline){

		ArrayList<Task> deadlineTaskList = new ArrayList<Task>();
		JSONParser jsonParser = new JSONParser();

		for(int i=0; i<deadline.size(); i++){

			try{
				JSONObject obj = (JSONObject) jsonParser.parse(deadline.get(i).toString().replace("\\", ""));


				String strEndDate = obj.get("end-date").toString();
				String strEndTime = obj.get("end-time").toString();

				String task = obj.get("task").toString();
				DateClass endDate = new DateClass(strEndDate);
				TimeClass endTime = new TimeClass(strEndTime);

				Deadline deadlineTask = new Deadline(task,endDate, endTime);

				deadlineTaskList.add(deadlineTask);

			}catch (ParseException e1) {

				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {

				e1.printStackTrace();
			} catch (java.text.ParseException e1) {

				e1.printStackTrace();
			}
		}
		Collections.sort(deadlineTaskList);
		return deadlineTaskList;
	}

	/**
	 * Covert a JSONArray to an ArrayList<Task>
	 * @param floating JSONArray of event task to be converted
	 * @return ArrayList<Task> of floating tasks
	 */
	public ArrayList<Task> floatingTaskArray(JSONArray event){
		ArrayList<Task> floatingTaskList = new ArrayList<Task>();
		JSONParser jsonParser = new JSONParser();

		for(int i=0; i<event.size(); i++){

			try{
				JSONObject obj = (JSONObject) jsonParser.parse(event.get(i).toString().replace("\\", ""));

				String task = obj.get("task").toString();

				Floating floatingTask = new Floating(task);

				floatingTaskList.add(floatingTask);

			}catch (ParseException e1) {

				e1.printStackTrace();
			}
		}
		Collections.sort(floatingTaskList);
		return floatingTaskList;

	}

	/**
	 * Convert all JSONArray event, deadline, floating to an ArrayList<Task>
	 * @param content ArrayList<JSONArray> contain event, deadline, floating JSONArray
	 * @return ArrayList<Task> of event, deadline and floating tasks
	 */
	public ArrayList<Task> allTaskArray(ArrayList<JSONArray> content){

		ArrayList<Task> event = eventTaskArray(content.get(0));
		ArrayList<Task> deadline = deadlineTaskArray(content.get(1));
		ArrayList<Task> floating = floatingTaskArray(content.get(2));

		ArrayList<Task> allTask = new ArrayList<Task>();

		for(int i=0; i<event.size(); i++)
		{
			allTask.add(event.get(i));
		}
		for(int i=0; i<deadline.size(); i++)
		{
			allTask.add(deadline.get(i));
		}
		for(int i=0; i<floating.size(); i++)
		{
			allTask.add(floating.get(i));
		}

		return allTask;
	}
}
