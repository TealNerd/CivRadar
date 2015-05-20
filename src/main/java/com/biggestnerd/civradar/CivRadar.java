package com.biggestnerd.civradar;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

import org.lwjgl.input.Keyboard;

import com.biggestnerd.civradar.gui.GuiAddWaypoint;
import com.biggestnerd.civradar.gui.GuiRadarOptions;
import com.biggestnerd.civradar.gui.GuiRepositionRadar;

@Mod(modid=CivRadar.MODID, name=CivRadar.MODNAME, version=CivRadar.VERSION)
public class CivRadar {
	public final static String MODID = "civradar";
	public final static String MODNAME = "CivRadar";
	public final static String VERSION = "beta-1.0.7";
	private RenderHandler renderHandler;
	private Config radarConfig;
	private File configFile;
	private KeyBinding radarOptions = new KeyBinding("CivRadar Settings", Keyboard.KEY_R, "CivRadar");
	private KeyBinding addWaypoint = new KeyBinding("Add Waypoint", Keyboard.KEY_P, "CivRadar");
	Minecraft mc;
	public static CivRadar instance;
	private WaypointSave currentWaypoints;
	private File saveFile;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		mc = Minecraft.getMinecraft();
		instance = this;
		File configDir = event.getModConfigurationDirectory();
		if(!configDir.isDirectory()) {
			configDir.mkdir();
		}
		configFile = new File(configDir, "civRadar.json");
		if(!configFile.isFile()) {
			try {
				configFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			radarConfig = new Config();
			radarConfig.save(configFile);
		} else {
			radarConfig = Config.load(configFile);
			if(radarConfig == null) {
				radarConfig = new Config();
			}
			radarConfig.save(configFile);
		}
		renderHandler = new RenderHandler();
		FMLCommonHandler.instance().bus().register(renderHandler);
		MinecraftForge.EVENT_BUS.register(renderHandler);
		FMLCommonHandler.instance().bus().register(this);
		ClientRegistry.registerKeyBinding(radarOptions);
		ClientRegistry.registerKeyBinding(addWaypoint);
	}
	
	@SubscribeEvent
	public void keyPress(KeyInputEvent event) {
		if(radarOptions.isKeyDown()) {
			mc.displayGuiScreen(new GuiRadarOptions(mc.currentScreen));
		}
		if(addWaypoint.isKeyDown()) {
			mc.displayGuiScreen(new GuiAddWaypoint(mc.currentScreen));
		}
	}
	
	//@SubscribeEvent
	public void connectToServer(ClientConnectedToServerEvent event) {
		File waypointsFile = new File(mc.mcDataDir, "/waypoints/");
		if(!waypointsFile.isDirectory()) {
			waypointsFile.mkdir();
		}
		if(mc.getCurrentServerData() == null) {
			return;
		}
		String ip = mc.getCurrentServerData().serverIP;
		saveFile = new File(waypointsFile, ip + ".json");
		if(!saveFile.isFile()) {
			try {
				saveFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			currentWaypoints = new WaypointSave();
			currentWaypoints.save(saveFile);
		} else {
			try {
				currentWaypoints.load(saveFile);
			} catch (Exception e) {
				currentWaypoints = new WaypointSave();
			}
			currentWaypoints.save(saveFile);
		}
	}
	
	public Config getConfig() {
		return radarConfig;
	}
	
	public void saveConfig() {
		radarConfig.save(configFile);
	}
	
	public WaypointSave getWaypointSave() {
		return currentWaypoints;
	}
	
	public void saveWaypoints() {
		currentWaypoints.save(saveFile);
	}
}
