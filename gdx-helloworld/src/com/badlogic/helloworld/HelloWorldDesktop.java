package com.badlogic.helloworld;
import com.badlogic.gdx.backends.jogl.JoglApplication;

public class HelloWorldDesktop {
	public static void main(String[] argv) {
		new JoglApplication(new HelloWorld(), "Hello World", 480, 320, false);
	}
}