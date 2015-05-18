package com.biggestnerd.civradar.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

import com.biggestnerd.civradar.CivRadar;
import com.biggestnerd.civradar.WaypointSave;

public class GuiAddWaypoint extends GuiScreen {

	private GuiScreen parent;
	private Minecraft mc;
	private WaypointSave waypoints;
	private GuiTextField waypointNameField;
	private GuiTextField waypointXField;
	private GuiTextField waypointYField;
	private GuiTextField waypointZField;
	private GuiButton saveButton;
	
	public GuiAddWaypoint(GuiScreen parent) {
		this.parent = parent;
		mc = Minecraft.getMinecraft();
		waypoints = CivRadar.instance.getWaypointSave();
	}
	
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		waypointNameField = new GuiTextField(1, fontRendererObj, this.width / 2 - 100, this.height / 4 - 16, 200, 20);
		waypointXField = new GuiTextField(2, fontRendererObj, this.width / 2 - 100, this.height / 4 + 8, 64, 20);
		waypointYField = new GuiTextField(2, fontRendererObj, this.width / 2 - 32, this.height / 4 + 8, 64, 20);
		waypointZField = new GuiTextField(2, fontRendererObj, this.width / 2 + 36, this.height / 4 + 8, 64, 20);
		waypointXField.setText("" + (int) mc.thePlayer.posX);
		waypointYField.setText("" + (int) mc.thePlayer.posY);
		waypointZField.setText("" + (int) mc.thePlayer.posZ);
		this.buttonList.add(saveButton = new GuiButton(100, this.width / 2 - 100, this.height / 4 + 152, "Save"));
		waypointNameField.setFocused(true);
	}
	
	public void updateScreen() {
		boolean validName = waypointNameField.getText().length() > 0;
		boolean validCoords = true;
		try {
			int x = Integer.parseInt(waypointXField.getText());
			int y = Integer.parseInt(waypointYField.getText());
			int z = Integer.parseInt(waypointZField.getText());
		} catch (Exception e) {
			validCoords = false;
		}
		if(!validName || !validCoords) {
			saveButton.enabled = false;
		} else {
			saveButton.enabled = true;
		}
	}
	
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	public void drawScreen(int i, int j, int k) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Add Waypoint", this.width / 2, this.height / 4 - 40, Color.WHITE.getRGB());
		this.waypointNameField.drawTextBox();
		this.waypointXField.drawTextBox();
		this.waypointYField.drawTextBox();
		this.waypointZField.drawTextBox();
		super.drawScreen(i, j, k);
	}
}
