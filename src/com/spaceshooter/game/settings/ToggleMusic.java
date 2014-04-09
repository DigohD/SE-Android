package com.spaceshooter.game.settings;

public class ToggleMusic {
	void musicSwitch(boolean musicStatus) {

		if (musicStatus == true) {
			musicStatus = false;
		} else {
			musicStatus = true;
		}
	}
}
