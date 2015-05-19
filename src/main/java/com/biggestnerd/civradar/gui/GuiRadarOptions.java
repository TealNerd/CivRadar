package com.biggestnerd.civradar.gui;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import com.biggestnerd.civradar.CivRadar;

public class GuiRadarOptions extends GuiScreen {

	private GuiScreen parentScreen;
	
	public GuiRadarOptions(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}
	
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 - 16, "Reposition Radar"));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 8, "Edit Enabled Icons"));
		this.buttonList.add(new GuiRadarOpacitySlider(3, this.width / 2 -100, this.height / 4 + 32, 1.0F, "Radar Opacity", CivRadar.instance.getConfig().getRadarOpacity()));
		this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 56, "Edit Radar Color"));
		this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height /4 + 80, "Edit Player Options"));
		this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height / 4 + 152, "Done"));
	}
	
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	public void actionPerformed(GuiButton guiButton) {
		if(!guiButton.enabled)
			return;
		int id = guiButton.id;
		if(id == 0) {
			mc.displayGuiScreen(new GuiRepositionRadar(this));
		}
		if(id == 1) {
			mc.displayGuiScreen(new GuiEntitySettings(this));
		}
		if(id == 4) {
			mc.displayGuiScreen(new GuiEditRadarColor(this));
		}
		if(id == 5) {
			mc.displayGuiScreen(new GuiPlayerOptions(this));
		}
		if(id == 100) {
			mc.displayGuiScreen(parentScreen);
		}
	}
	
	public void drawScreen(int i, int j, int k) {
		drawDefaultBackground();
		drawCenteredString(this.fontRendererObj, "CivRadar Options", this.width / 2, this.height / 4 - 40, Color.WHITE.getRGB());
		super.drawScreen(i, j, k);
	}
}
