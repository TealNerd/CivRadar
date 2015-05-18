package com.biggestnerd.civradar.gui;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.biggestnerd.civradar.CivRadar;
import com.biggestnerd.civradar.Config;

public class GuiRepositionRadar extends GuiScreen {
	
	private GuiScreen parentScreen;
	private Config config;
	
	public GuiRepositionRadar(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
		config = CivRadar.instance.getConfig();
	}
	
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 40, "Use the arrow keys to reposition"));
		((GuiButton)this.buttonList.get(0)).enabled = false;
		this.buttonList.add(new GuiButton(1, this.width / 2 + 1, 90, 100, 20, "Snap top right"));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 101, 90, 100, 20, "Snap top left"));
		this.buttonList.add(new GuiButton(3, this.width / 2 - 101, 112, 100, 20, "Snap bottom left"));
		this.buttonList.add(new GuiButton(4, this.width / 2 + 1, 112, 100, 20, "Snap top left"));
		this.buttonList.add(new GuiButton(5, this.width / 2 - 100, 134, "Done"));
	}
	
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		CivRadar.instance.saveConfig();
	}
	
	public void actionPerformed(GuiButton button) {
		if(!button.enabled) {
			return;
		}
		if(button.id == 1) {
			config.setRadarX(0);
			config.setRadarY(0);
		}
		if(button.id == 2) {
			config.setRadarX(-this.width + 130);
			config.setRadarY(0);
		}
		if(button.id == 3) {
			config.setRadarX(-this.width + 130);
			config.setRadarY(this.height - 150);
		}
		if(button.id == 4) {
			config.setRadarX(0);
			config.setRadarY(this.height - 150);
		}
		if(button.id == 5) {
			mc.displayGuiScreen(null);
		}
		CivRadar.instance.saveConfig();
	}
	
	public void updateScreen() {
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			config.setRadarX(config.getRadarX() - 1);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			config.setRadarX(config.getRadarX() + 1);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			config.setRadarY(config.getRadarY() - 1);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			config.setRadarY(config.getRadarY() + 1);
		}
		CivRadar.instance.saveConfig();
	}
	
	public void drawScreen(int i, int j, int k) {
		drawDefaultBackground();
		super.drawScreen(i, j, k);
	}

}
