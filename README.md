## CivRadar (FML 1.8) [![Build Status](http://dydoisbutts.info:8080/job/CivRadar/badge/icon)](http://dydoisbutts.info:8080/job/CivRadar/)
A radar mod for Civcraft

All the gradle stuff is from [Lunatrius](https://github.com/Lunatrius/Schematica) so ty for that

Installing and Using CivRadar
---
1. Run Minecraft 1.8 at least once (not 1.8.x, just regular 1.8)
2. Download the [Forge 1.8-11.14.1.1404 Installer](http://adfoc.us/serve/sitelinks/?id=271228&url=http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.8-11.14.1.1404/forge-1.8-11.14.1.1404-installer.jar) or [another version](http://files.minecraftforge.net) (OTHER VERSIONS ARE NOT OFFICIALLY SUPPORTED BUT MAY WORK)
3. Run the installer and install forge
4. [Open your .minecraft folder](http://minecraft.gamepedia.com/.minecraft)
5. Download the [latest CivRadar release](http://github.com/tealnerd/civradar/releases)
5. if you don't see a folder called 'mods', create one, then put the CivRadar jar in the mods folder
6. Open the minecraft launcher
7. Create a new profile and select the version 'release Forge 1.8-11.14.1.1404'
8. Run the forge profile and proceed to enjoy the mod!

Compiling from Source
---

This mod is compiled using the Forge Mod Loader (FML) mod pack which includes data from the Minecraft Coder Pack (MCP).

To compile this mod from the source code provided

1. Install the [1.8-11.14.1.1404](http://adfoc.us/serve/?id=27122855377941) build from MinecraftForge.net or compile using [another dependency version](http://files.minecraftforge.net/) (OTHER VERSIONS OF FORGE ARE CURRENTLY NOT SUPPORTED!)
2. Follow the [Forge Source install instrcutions](http://www.minecraftforge.net/wiki/Installation/Source) and set up the FML pack.
3. Remove the src folder provided by FML and replace it with the src folder from this repository
4. Replace the build.gradle file with the one provided
5. Run the command "gradlew build" from the command window you opened earlier (to run the dev client for testing do gradlew runClient)

Happy Hacking!
