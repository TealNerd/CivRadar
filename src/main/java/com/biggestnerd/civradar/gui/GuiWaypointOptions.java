package com.biggestnerd.civradar.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import com.biggestnerd.civradar.CivRadar;
import com.biggestnerd.civradar.Config;

public class GuiWaypointOptions extends GuiScreen {

	private GuiScreen parent;
	private GuiSlider opacitySlider;
	private GuiSlider distanceSlider;
	private GuiButton enabledButton;
	private Minecraft mc;
	private Config config;
	
	public GuiWaypointOptions(GuiScreen parent) {
		this.parent = parent;
		mc = Minecraft.getMinecraft();
		config = CivRadar.instance.getConfig();
	}
	
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(enabledButton = new GuiButton(0, this.width / 2 - 100, this.height / 4 - 16, "Waypoints: " + (config.isRenderWaypoints() ? "On" : "Off")));
		this.buttonList.add(opacitySlider = new GuiSlider(1, this.width / 2 -100, this.height / 4 + 8, 1.0F, 0.0F, "Waypoint Opacity", config.getRadarOpacity()));
		float currentMaxDist = config.getMaxWaypointDistance() / 15200f;
		if(config.getMaxWaypointDistance() < 0) {
			currentMaxDist = 1.0F;
		}
		this.buttonList.add(distanceSlider = new GuiSlider(2, this.width / 2 - 100, this.height / 4 + 32, 1.0F, 0.0F, "Waypoint Max Distance", currentMaxDist));
		this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 56, "Edit Waypoints"));
		this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height / 4 + 80, "Done"));
	}
	
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		CivRadar.instance.saveConfig();
	}
	
	public void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
				config.setRenderWaypoints(!config.isRenderWaypoints());
				CivRadar.instance.saveConfig();
			}
			if(button.id == 4) {
				mc.displayGuiScreen(new GuiWaypointList(this));
			}
			if(button.id == 100) {
				mc.displayGuiScreen(parent);
			}
		}
	}
	
	public void updateScreen() {
		opacitySlider.updateDisplayString();
		if(distanceSlider.getCurrentValue() == 1.0F) {
			distanceSlider.setDisplayString("Infinite");
			config.setMaxWaypointDistance(-1);
		} else {
			int  maxDist = Math.max((int) (distanceSlider.getCurrentValue() * 15200), 1);
			distanceSlider.setDisplayString(String.valueOf(maxDist));
			config.setMaxWaypointDistance(maxDist);
		}
		enabledButton.displayString = "Waypoints: " + (config.isRenderWaypoints() ? "On" : "Off");
		config.setWaypointOpcaity(opacitySlider.getCurrentValue());
		CivRadar.instance.saveConfig();
	}
	
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
		drawCenteredString(mc.fontRendererObj, "Waypoint Options", this.width / 2, 30, Color.WHITE.getRGB());
		super.drawScreen(i, j, f);
	}
}
