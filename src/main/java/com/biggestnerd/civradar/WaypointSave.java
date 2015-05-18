package com.biggestnerd.civradar;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WaypointSave {

	private ArrayList<Waypoint> waypoints;
	
	public WaypointSave() {
		waypoints = new ArrayList<Waypoint>();
	}
	
	public ArrayList<Waypoint> getWaypoints() {
		if(waypoints == null) {
			waypoints = new ArrayList<Waypoint>();
		}
		return waypoints;
	}
	
	public void save(File file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			String json = gson.toJson(this);
			
			FileWriter fw = new FileWriter(file);
			fw.write(json);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static WaypointSave load(File file) throws Exception {
		Gson gson = new Gson();
		return (WaypointSave) gson.fromJson(new FileReader(file), WaypointSave.class);
	}
}
