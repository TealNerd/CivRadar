package com.biggestnerd.civradar.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiYesNoCallback;

import com.biggestnerd.civradar.CivRadar;
import com.biggestnerd.civradar.Entity;

public class GuiEntityList extends GuiScreen implements GuiYesNoCallback {

	private final GuiScreen parent;
	private final ArrayList<Entity> entityList;
	private int selected = -1;
	private GuiButton enableButton;
	private GuiButton disableButton;
	private EntityList entityListContainer;
	
	public GuiEntityList(GuiScreen parent) {
		this.parent = parent;
		this.entityList = CivRadar.instance.getConfig().getEntities();
	}
	
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(enableButton = new GuiButton(0, this.width / 2 - 100, this.height - 44, 99, 20, "Enable"));
		this.buttonList.add(disableButton = new GuiButton(1, this.width / 2 + 1, this.height - 44, 99, 20, "Disable"));
		this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height - 22, "Done"));
		this.entityListContainer = new EntityList(this.mc);
		this.entityListContainer.registerScrollButtons(4, 5);
	}
	
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		this.entityListContainer.handleMouseInput();
	}
	
	private void enableOrDisableSelectedEntity(boolean enabled) {
		Class selectedEntityClass = entityList.get(selected).getEntityClass();
		CivRadar.instance.getConfig().setRender(selectedEntityClass, enabled);
		CivRadar.instance.saveConfig();
	}
	
	protected void actionPerformed(GuiButton button) throws IOException	 {
		if(button.enabled) {
			if(button.id == 0) {
				enableOrDisableSelectedEntity(true);
			}
			if(button.id == 1) {
				enableOrDisableSelectedEntity(false);
			}
			if(button.id == 100) {
				mc.displayGuiScreen(parent);
			}
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.entityListContainer.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, "Edit enabled entities", this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
	
	class EntityList extends GuiSlot {
		
		public EntityList(Minecraft mc) {
			super(mc, GuiEntityList.this.width, GuiEntityList.this.height, 32, GuiEntityList.this.height - 64, 36);
		}
		
		protected int getSize() {
			return GuiEntityList.this.entityList.size();
		}
		
		protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
			GuiEntityList.this.selected = slotIndex;
			boolean isValidSlot = slotIndex >= 0 && slotIndex < getSize();
			GuiEntityList.this.enableButton.enabled = isValidSlot;
			GuiEntityList.this.disableButton.enabled = isValidSlot;
		}
		
		protected boolean isSelected(int slotIndex) {
			return slotIndex == GuiEntityList.this.selected;
		}
		
		protected int getContentHeight() {
			return getSize() * 36;
		}
		
		protected void drawBackground() {
			GuiEntityList.this.drawDefaultBackground();
		}
		
		protected void drawSlot(int entryId, int par2, int par3, int par4, int par5, int par6) {
			Entity entity = GuiEntityList.this.entityList.get(entryId);
			GuiEntityList.this.drawString(mc.fontRendererObj, entity.getEntityName(), par2 + 1, par3 + 1, Color.WHITE.getRGB());
			GuiEntityList.this.drawString(mc.fontRendererObj, entity.isEnabled() ? "Enabled" : "Disabled", par2 + 215 - mc.fontRendererObj.getStringWidth("Disabled"), par3 + 1, entity.isEnabled() ? Color.GREEN.getRGB() : Color.RED.getRGB());
		}
	}
}
