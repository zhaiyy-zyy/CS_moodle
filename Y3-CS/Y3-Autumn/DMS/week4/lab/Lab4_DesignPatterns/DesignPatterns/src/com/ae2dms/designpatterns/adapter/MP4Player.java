package com.ae2dms.designpatterns.adapter;

public class MP4Player implements AdvancedMediaPlayer{
	public void play(String videoType, String filename) {
		System.out.println("Playing MP4 video: "+filename);
	}
}
