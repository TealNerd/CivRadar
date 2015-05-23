package com.biggestnerd.civradar;

import java.awt.Color;
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
		waypoints.add(new Waypoint(0, 0, 0, "0,0", Color.BLACK, false));
	}
	
	public ArrayList<Waypoint> getWaypoints() {
		if(waypoints == null) {
			waypoints = new ArrayList<Waypoint>();
		}
		return waypoints;
	}
	
	public void addWaypoint(Waypoint point) {
		if(waypoints == null) {
			waypoints = new ArrayList<Waypoint>();
		}
		waypoints.add(point);
	}
	
	public void setEnabled(Waypoint point, boolean enabled) {
		for(Waypoint w : waypoints) {
			if(w.equals(point)) {
				w.setEnabled(enabled);
			}
		}
	}
	
	public void removeWaypoint(Waypoint point) {
		for(int i = 0; i < waypoints.size(); i++) {
			if(waypoints.get(i).equals(point)) {
				waypoints.remove(i);
			}
		}
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
	
	public static WaypointSave load(File file) {
		Gson gson = new Gson();
		try {
			return (WaypointSave) gson.fromJson(new FileReader(file), WaypointSave.class);
		} catch (Exception e) {
			return new WaypointSave();
		}
	}
}
