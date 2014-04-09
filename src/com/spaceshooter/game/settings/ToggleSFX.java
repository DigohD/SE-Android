package com.spaceshooter.game.settings;

public class ToggleSFX {
	void sfxSwitch(boolean sfxStatus) {

		if (sfxStatus == true) {
			sfxStatus = false;
		} else {
			sfxStatus = true;
		}
	}
}
