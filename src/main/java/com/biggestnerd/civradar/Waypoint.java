package com.biggestnerd.civradar;

import java.awt.Color;

import net.minecraft.client.Minecraft;

public class Waypoint {

	private int x, y, z;
	private Color c;
	private String name;
	private String worldName;
	private boolean enabled;
	
	public Waypoint(int x, int y, int z, String name, Color c) {
		this.x = x;
		this.z = z;
		this.y = y;
		this.name = name;
		this.c = c;
		this.worldName = Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldName();
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getWorldName() {
		return worldName;
	}
	
	public Color getColor() {
		return c;
	}
	
	public void setColor(Color c) {
		this.c = c;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public int getDistance() {
		int px = (int) Minecraft.getMinecraft().thePlayer.posX;
		int py = (int) Minecraft.getMinecraft().thePlayer.posY;
		int pz = (int) Minecraft.getMinecraft().thePlayer.posZ;
		double dx = Math.pow((px - x), 2);
		double dy = Math.pow((py - y), 2);
		double dz = Math.pow((pz - z), 2);
		return (int) Math.sqrt(dx + dy + dz);
	}
}
