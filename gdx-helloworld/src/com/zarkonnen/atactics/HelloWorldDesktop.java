package com.zarkonnen.atactics;
import java.io.IOException;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class HelloWorldDesktop {
	public static void main(String[] argv) throws IOException {
		new JoglApplication(new HelloWorld(), "Hello World", 320, 480, false);
	}
}