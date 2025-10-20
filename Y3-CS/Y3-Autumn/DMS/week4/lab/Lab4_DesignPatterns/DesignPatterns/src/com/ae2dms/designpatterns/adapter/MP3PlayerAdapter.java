package com.ae2dms.designpatterns.adapter;

public class MP3PlayerAdapter implements MediaPlayer{
	AdvancedMediaPlayer vp;
	
	public void play(String audioType, String filename) {
		if(audioType.equalsIgnoreCase("MP4")) {
			vp = new MP4Player();
			vp.play(audioType, filename);
		}
		else if (audioType.equalsIgnoreCase("VLC")) {
			vp = new VLCPlayer();
			vp.play(audioType, filename);
		}
	}
}
