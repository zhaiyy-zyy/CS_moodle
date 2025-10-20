package com.ae2dms.designpatterns.adapter;

public class VLCPlayer implements AdvancedMediaPlayer{
	public void play(String videoType, String filename) {
		System.out.println("Playing VLC video: "+filename);
	}
}
