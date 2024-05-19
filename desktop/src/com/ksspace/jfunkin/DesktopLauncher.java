package com.ksspace.jfunkin;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ksspace.jfunkin.Funkin;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setIdleFPS(60);
		config.useVsync(true);

		config.setWindowedMode(1280, 720);
		config.setResizable(false);
		config.setTitle("jFunkin");
		new Lwjgl3Application(new Funkin(), config);
	}
}
