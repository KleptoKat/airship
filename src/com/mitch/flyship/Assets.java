package com.mitch.flyship;

import java.io.IOException;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;

import com.mitch.framework.Audio;
import com.mitch.framework.Graphics;
import com.mitch.framework.Graphics.ImageFormat;
import com.mitch.framework.Image;
import com.mitch.framework.Music;
import com.mitch.framework.Sound;

public class Assets {
	
	static boolean loaded = false;
	static HashMap<String, Image> images = new HashMap<String, Image>();
	static HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	static HashMap<String, Music> music = new HashMap<String, Music>();
	
	public static void loadFromXML(XmlResourceParser xrp, Graphics g, Audio a)
	{
		// TODO: Load assets from XML
		while (true) {
			int eventType = -1;
			try {
				eventType = xrp.next();
				if (eventType == XmlResourceParser.END_DOCUMENT) {
					break;
				}
			} catch (XmlPullParserException e) {
				e.printStackTrace();
				continue;
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			
			String type   = xrp.getName();
			String key    = (String) xrp.getProperty("key");
			String path   = (String) xrp.getProperty("path");
			String locale = (String) xrp.getProperty("locale");
			boolean isLocale = locale == getLocale();
			
			if (key == null || path == null) {
				continue;
			}
			else if (type == "image" && ( !images.containsKey(key) || isLocale) ) {
				Image image = g.newImage(path, ImageFormat.ARGB4444);
				images.put(key, image);
			} 
			else if (type == "sound" && ( !sounds.containsKey(key) || isLocale) ) {
				Sound sound = a.createSound(path);
				sounds.put(key, sound);
			} 
			else if (type == "music" && ( !music.containsKey(key) || isLocale) ) {
				Music sound = a.createMusic(path);
				music.put(key, sound);
			}
		}
		
		loaded = true;
	}
	
	public static boolean isLoaded()
	{
		return loaded;
	}
	
	public static Image getImage(String name)
	{
		return images.get(name);
	}
	
	public static Sound getSound(String name)
	{
		return sounds.get(name);
	}
	
	public static Music getMusic(String name)
	{
		return music.get(name);
	}
	
	public static String getLocale()
	{
		return "en_us";
	}
	
}
