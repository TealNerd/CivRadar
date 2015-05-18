package com.biggestnerd.civradar;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

import org.lwjgl.opengl.GL11;

public class RenderHandler extends Gui {

	private Config config = CivRadar.instance.getConfig();
	private Minecraft mc = Minecraft.getMinecraft();
	private Color radarColor;
	private ResourceLocation icons = new ResourceLocation("civRadar/icons/horse.png");
	private double pingDelay = 63.0D;
	
	@SubscribeEvent
	public void renderRadar(RenderGameOverlayEvent event) {
		if(event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && !mc.thePlayer.isRiding())
			return;
		if(config.isEnabled()) {
			drawRadar();
		}
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
		if(event.phase == TickEvent.Phase.START && mc.theWorld != null) {
			if(pingDelay <= -10.0D) {
				pingDelay = 63.0D;
			}
			pingDelay -= 1.0D;
		}
	}
	
	@SubscribeEvent
	public void renderWaypoints(RenderWorldLastEvent event) {
		if(CivRadar.instance.getWaypointSave() == null) {
			return;
		}
		if(config.isRenderWaypoints()) {
			for(Waypoint point : CivRadar.instance.getWaypointSave().getWaypoints()) {
				if(point.getWorldName().equals(mc.theWorld.getWorldInfo().getWorldName()) && point.isEnabled()) {
					//renderWaypoint(point);
				}
			}
		}
	}
	
	private void drawRadar() {
		radarColor = config.getRadarColor();
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		int width = res.getScaledWidth();
		GL11.glPushMatrix();
		GL11.glTranslatef(width - 65 + (config.getRadarX()), 65 + (config.getRadarY()), 0.0F);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		if(config.isRenderCoordinates()) {
			String coords = "(" + (int) mc.thePlayer.posX + "," + (int) mc.thePlayer.posY + "," + (int) mc.thePlayer.posZ + ")";
			mc.fontRendererObj.drawStringWithShadow(coords, -(mc.fontRendererObj.getStringWidth(coords) / 2), 65, 14737632);
		}
		GL11.glRotatef(-mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
		drawCircle(0, 0, 63.0D, radarColor, true);
		GL11.glLineWidth(2.0F);
		drawCircle(0, 0, 63.0D, radarColor, false);
		GL11.glLineWidth(1.0F);
		/*
		drawCircle(0, 0, 63.0D, teal, false);
		drawCircle(0, 0, 43.0D, teal, false);
		drawCircle(0, 0, 22.0D, teal, false);
		*/
		
		if(pingDelay > 0) {
			drawCircle(0, 0, 63.0D - pingDelay, radarColor, false);
		}
		GL11.glLineWidth(2.0F);
		GL11.glDisable(3553);
		GL11.glDisable(2896);
		GL11.glBegin(1);
		GL11.glColor4f(radarColor.getRed() / 255.0F, radarColor.getGreen() / 255.0F, radarColor.getBlue() / 255.0F, config.getRadarOpacity() + 0.5F);
		GL11.glVertex2d(0.0D, -63.0D);
		GL11.glVertex2d(0.0D, 63.0D);
		GL11.glVertex2d(-63.0D, 0.0D);
		GL11.glVertex2d(63.0D, 0.0D);
		GL11.glVertex2d(-44.5D, -44.5D);
		GL11.glVertex2d(44.5D, 44.5D);
		GL11.glVertex2d(-44.5D, 44.5D);
		GL11.glVertex2d(44.5D, -44.5D);
		GL11.glEnd();
		GL11.glDisable(3042);
		GL11.glEnable(3553);
		
		drawRadarIcons();
		
		GL11.glRotatef(mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
		
		drawTriangle(0, 0, Color.WHITE);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glPopMatrix();
	}
	
	private void drawCircle(int x, int y, double radius, Color c, boolean filled) {
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, filled ? config.getRadarOpacity() : config.getRadarOpacity() + 0.5F);
		GL11.glBegin(filled ? 6 : 2);
		for (int i = 0; i <= 360; i++) {
			double x2 = Math.sin(i * Math.PI / 180.0D) * radius;
			double y2 = Math.cos(i * Math.PI / 180.0D) * radius;
			GL11.glVertex2d(x + x2, y + y2);
		}
		GL11.glEnd();
		GL11.glDisable(2848);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
	}
	
	private void drawTriangle(int x, int y, Color c) {
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, 1.0F);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glBlendFunc(770, 771);
		GL11.glBegin(4);
		GL11.glVertex2d(x, y + 3);
		GL11.glVertex2d(x + 3, y - 3);
		GL11.glVertex2d(x - 3, y - 3);
		GL11.glEnd();
		GL11.glDisable(2848);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glRotatef(-180.0F, 0.0F, 0.0F, 1.0F);
	}
	
	private void drawRadarIcons() {
		for(Object o : mc.theWorld.loadedEntityList) {
			Entity e = (Entity) o;
			int playerPosX = (int) mc.thePlayer.posX;
			int playerPosZ = (int) mc.thePlayer.posZ;
			int entityPosX = (int) e.posX;
			int entityPosZ = (int) e.posZ;
			int displayPosX = playerPosX - entityPosX;
			int displayPosZ = playerPosZ - entityPosZ;
			if(Math.hypot(displayPosX, displayPosZ) < 130.0D && e != mc.thePlayer) {
				if(e instanceof EntityItem) {
					EntityItem item = (EntityItem) e;
					if(config.isRender(EntityItem.class)) {
						renderItemIcon(displayPosX, displayPosZ, item.getEntityItem());
					}
					return;
				}
				if(e instanceof EntityOtherPlayerMP) {
					if(config.isRender(EntityPlayer.class)) {
						EntityOtherPlayerMP eop = (EntityOtherPlayerMP) e;
						renderPlayerHeadIcon(displayPosX, displayPosZ, eop);
					}
					return;
				}
				if(e instanceof EntityMinecart) {
					if(config.isRender(EntityMinecart.class)) {
						ItemStack cart = new ItemStack(Items.minecart);
						renderItemIcon(displayPosX, displayPosZ, cart);
					}
					return;
				}
				if(config.isRender(o.getClass())) {
					renderIcon(displayPosX, displayPosZ, config.getMob(o.getClass()).getResource());
				}
			}
		}
	}
	
	private void renderItemIcon(int x, int y, ItemStack item) {
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(x +1, y +1, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, config.getRadarOpacity() + 0.5F);
		GL11.glRotatef(mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
		mc.getRenderItem().renderItemIntoGUI(item, -8, -8);
		GL11.glTranslatef(-x -1, -y -1, 0.0F);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glDisable(2896);
		GL11.glPopMatrix();
	}
	
	private void renderPlayerHeadIcon(int x, int y, EntityOtherPlayerMP player) {
		NetworkPlayerInfo info = mc.thePlayer.sendQueue.getPlayerInfo(player.getUniqueID());
		if(info != null) {
			mc.getTextureManager().bindTexture(info.getLocationSkin());
		} else {
			mc.getTextureManager().bindTexture(new ResourceLocation("civRadar/icons/player.png"));
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, config.getRadarOpacity() + 0.5F);
		GL11.glEnable(3042);
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(x + 1, y + 1, 0.0F);
		GL11.glRotatef(mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
		drawModalRectWithCustomSizedTexture(-8, -8, 0, 0, 16, 16, 16, 16);
		GL11.glTranslatef(-x -1, -y -1, 0.0F);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glDisable(2896);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
		if(config.isPlayerNames()) {
			GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glTranslatef(x, y, 0.0F);
			GL11.glRotatef(mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-x, -y, 0.0F);
			String playerName = player.getName();
			if(config.isExtraPlayerInfo()) {
				playerName += " (" + (int) mc.thePlayer.getDistanceToEntity(player) + "m)(Y" + (int) player.posY + ")";
			}
			drawCenteredString(mc.fontRendererObj, playerName, x + 8, y + 10, Color.WHITE.getRGB());
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			GL11.glPopMatrix();
		}
	}
	
	private void renderIcon(int x, int y, ResourceLocation resource) {
		mc.getTextureManager().bindTexture(resource);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, config.getRadarOpacity() + 0.5F);
		GL11.glEnable(3042);
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(x + 1, y + 1, 0.0F);
		GL11.glRotatef(mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
		drawModalRectWithCustomSizedTexture(-8, -8, 0, 0, 16, 16, 16, 16);
		GL11.glTranslatef(-x -1, -y -1, 0.0F);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glDisable(2896);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}
	
	private void renderWaypoint(Waypoint point) {

	}
}
