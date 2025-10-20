package com.ae2dms.designpatterns.adapter;

public class MP3Player implements MediaPlayer{
	MP3PlayerAdapter mpa = new MP3PlayerAdapter();
	
	public void play(String type, String filename) {
		if(type.equalsIgnoreCase("MP4")||type.equalsIgnoreCase("VLC")) {
			mpa.play(type, filename);
		}
		else if(type.equalsIgnoreCase("MP3"))
			System.out.println("Playing MP3 file: "+filename);
		else 
			System.out.println("Cannot play your file format.");
	}
}
