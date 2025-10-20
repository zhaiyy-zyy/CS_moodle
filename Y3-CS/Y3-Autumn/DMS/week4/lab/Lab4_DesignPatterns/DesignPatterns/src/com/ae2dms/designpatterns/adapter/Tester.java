/* Adapter pattern example in Lab03
 * Note -- Some class names are modified to provide better understanding
 *    -- AudioPlayer (in Lab03) -> MP3Player (Here)
 *     -- MediaAdapter (in Lab03) -> MP3PlayerAdapter (Here)
*/

package com.ae2dms.designpatterns.adapter;

public class Tester {

	public static void main(String[] args) {

		MP3Player mp3 = new MP3Player();
		mp3.play("MP3", "Song");
		mp3.play("MP4", "TV");
		mp3.play("VLC", "Movie");
		mp3.play("MPG", "Hello");
	}
}
