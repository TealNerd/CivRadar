package com.biggestnerd.civradar.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;

import com.biggestnerd.civradar.CivRadar;
import com.biggestnerd.civradar.Entity;
import com.biggestnerd.civradar.Waypoint;

public class GuiWaypointList extends GuiScreen {
	
	private final GuiScreen parent;
	private final ArrayList<Waypoint> waypointList;
	private int selected = -1;
	private GuiButton enableButton;
	private GuiButton disableButton;
	private GuiButton editButton;
	private WaypointList waypointListContainer;
	
	public GuiWaypointList(GuiScreen parent) {
		this.parent = parent;
		this.waypointList = CivRadar.instance.getWaypointSave().getWaypoints();
	}
	
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(enableButton = new GuiButton(0, this.width / 2 - 100, this.height - 44, 64, 20, "Enable"));
		this.buttonList.add(disableButton = new GuiButton(1, this.width / 2 - 32, this.height - 44, 64, 20, "Disable"));
		this.buttonList.add(editButton = new GuiButton(2, this.width / 2 + 36, this.height - 44, 64, 20, "Edit"));
		this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height - 22, "Done"));
		this.waypointListContainer = new WaypointList(this.mc);
		this.waypointListContainer.registerScrollButtons(4, 5);
	}
	
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.waypointListContainer.handleMouseInput();
	}
	
	private void enableOrDisableSelectedWaypoint(boolean enabled) {
		Waypoint point = waypointList.get(selected);
		CivRadar.instance.getWaypointSave().setEnabled(point, enabled);
		CivRadar.instance.saveWaypoints();
		mc.displayGuiScreen(new GuiWaypointList(parent));
	}
	
	protected void actionPerformed(GuiButton button) throws IOException	 {
		if(button.enabled) {
			if(button.id == 0) {
				enableOrDisableSelectedWaypoint(true);
			}
			if(button.id == 1) {
				enableOrDisableSelectedWaypoint(false);
			}
			if(button.id == 2) {
				mc.displayGuiScreen(new GuiEditWaypoint(waypointList.get(selected)));
			}
			if(button.id == 100) {
				mc.displayGuiScreen(parent);
			}
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.waypointListContainer.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, "Waypoint List", this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
	
	class WaypointList extends GuiSlot {
		
		public WaypointList(Minecraft mc) {
			super(mc, GuiWaypointList.this.width, GuiWaypointList.this.height, 32, GuiWaypointList.this.height - 64, 36);
		}
		
		protected int getSize() {
			return GuiWaypointList.this.waypointList.size();
		}
		
		protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
			GuiWaypointList.this.selected = slotIndex;
			boolean isValidSlot = slotIndex >= 0 && slotIndex < getSize();
			GuiWaypointList.this.enableButton.enabled = isValidSlot;
			GuiWaypointList.this.disableButton.enabled = isValidSlot;
			GuiWaypointList.this.editButton.enabled = isValidSlot;
		}
		
		protected boolean isSelected(int slotIndex) {
			return slotIndex == GuiWaypointList.this.selected;
		}
		
		protected int getContentHeight() {
			return getSize() * 36;
		}
		
		protected void drawBackground() {
			GuiWaypointList.this.drawDefaultBackground();
		}
		
		protected void drawSlot(int entryId, int par2, int par3, int par4, int par5, int par6) {
			Waypoint point = GuiWaypointList.this.waypointList.get(entryId);
			GuiWaypointList.this.drawString(mc.fontRendererObj, point.getName(), par2 + 1, par3 + 1, Color.WHITE.getRGB());
			String coords = "(" + point.getX() + "," + point.getY() + "," + point.getZ() + ")";
			GuiWaypointList.this.drawString(mc.fontRendererObj, coords, par2 + 1, par3 + 11, point.getColor().getRGB());
			GuiWaypointList.this.drawString(mc.fontRendererObj, point.isEnabled() ? "Enabled" : "Disabled", par2 + 215 - mc.fontRendererObj.getStringWidth("Disabled"), par3 + 1, point.isEnabled() ? Color.GREEN.getRGB() : Color.RED.getRGB());
		}
	}
}
