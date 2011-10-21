package com.badlogic.helloworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
	public Sound thruster;
	
	public Sounds() {
		thruster = Gdx.audio.newSound(Gdx.files.internal("thruster.ogg"));
	}
}
