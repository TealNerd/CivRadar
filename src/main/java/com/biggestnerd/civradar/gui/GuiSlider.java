package com.biggestnerd.civradar.gui;

import org.lwjgl.opengl.GL11;

import com.biggestnerd.civradar.CivRadar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiSlider extends GuiButton {
	private float sliderValue = 0.5F;
	private boolean dragging;
	private float minValue;
	private float maxValue;
	private String name;
	
	public GuiSlider(int par1, int par2, int par3, float maxValue, float minValue, String name, float currentValue) {
		super(par1, par2, par3, 150, 20, name);
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.sliderValue = currentValue;
		this.name = name;
		this.width = 200;
	}
	public int getHoverState(boolean par1) {
		return 0;
	}

	protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
		if (this.visible) {
			if (this.dragging) {
				this.sliderValue = ((par2 - (float) (this.xPosition + 4)) / (float) (this.width - 8)) + minValue;
				if (this.sliderValue < minValue) {
					this.sliderValue = minValue;
				} else if (this.sliderValue > maxValue) {
					this.sliderValue = maxValue;
				}
			}
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			drawTexturedModalRect(this.xPosition + (int) ((this.sliderValue - minValue) * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
			drawTexturedModalRect(this.xPosition + (int) ((this.sliderValue - minValue) * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
		}
	}

	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
		if (super.mousePressed(par1Minecraft, par2, par3)) {
			this.sliderValue = ((par2 - (float) (this.xPosition + 4)) / (float) (this.width - 8)) + minValue;
			if (this.sliderValue < minValue) {
				this.sliderValue = minValue;
			} else if (this.sliderValue > maxValue) {
				this.sliderValue = maxValue;
			}
			this.dragging = true;
			return true;
		}
		return false;
	}

	public void mouseReleased(int par1, int par2) {
		this.dragging = false;
		CivRadar.instance.saveConfig();
	}

	public void setDisplayString(String string) {
		this.displayString = (this.name + ": " + string);
	}
	
	public void updateDisplayString() {
		this.displayString = (this.name + ": " + sliderValue);
	}
	
	public float getCurrentValue() {
		return this.sliderValue;
	}
}
