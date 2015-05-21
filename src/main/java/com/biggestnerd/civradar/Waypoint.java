package com.biggestnerd.civradar;

import java.awt.Color;

import net.minecraft.client.Minecraft;

public class Waypoint {

	private double x, y, z;
	private Color color;
	private String name;
	private int dimension;
	private boolean enabled;
	
	public Waypoint(int x, int y, int z, String name, Color c, boolean enabled) {
		this.x = x + 0.5D;
		this.z = z + 0.5D;
		this.y = y;
		this.name = name;
		this.color = c;
		this.dimension = Minecraft.getMinecraft().theWorld.provider.getDimensionId();
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public double getDistance() {
		double px = Minecraft.getMinecraft().thePlayer.posX;
		double py = Minecraft.getMinecraft().thePlayer.posY;
		double pz = Minecraft.getMinecraft().thePlayer.posZ;
		double dx = Math.pow((px - x), 2);
		double dy = Math.pow((py - y), 2);
		double dz = Math.pow((pz - z), 2);
		return Math.sqrt(dx + dy + dz);
	}
	
	public boolean equals(Waypoint p) {
		return x == p.getX() && y == p.getY() && z == p.getZ() && name.equals(p.getName()) && dimension == p.getDimension();
	}
}
