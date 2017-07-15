package com.base.engine;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard extends GLFWKeyCallback {

	private final int NUM_KEYCODES = 256;

	private boolean[] keys = new boolean[NUM_KEYCODES];
	private boolean[] currentKeys = new boolean[NUM_KEYCODES];
	private boolean[] downKeys = new boolean[NUM_KEYCODES];
	private boolean[] upKeys = new boolean[NUM_KEYCODES];

	private long window;
	
	public void init() {
		window=GLFW.glfwGetCurrentContext();
		glfwSetKeyCallback(window, this);		
		
	}
	
	public void update() {

		for (int i = 0; i < NUM_KEYCODES; i++) {

			upKeys[i] = false;
			if (!getKey(i) && currentKeys[i])
				upKeys[i] = true;


			downKeys[i] = false;
			if (getKey(i) && !currentKeys[i])
				downKeys[i] = true;

			currentKeys[i] = false;
			if (getKey(i))
				currentKeys[i] = true;
		}

	}

	@Override
	public void invoke(long window, int key, int scancode, int action,
			int mods) {

		keys[key] = action != GLFW_RELEASE;

	}

	public boolean getKey(int keycode) {
		return keys[keycode];
	}

	public boolean getKeyDown(int keyCode) {
		return downKeys[keyCode];

	}

	public boolean getKeyUp(int keyCode) {
		return upKeys[keyCode];

	}


}