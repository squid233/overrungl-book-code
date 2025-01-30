package org.example.overrunglbook;

import overrungl.glfw.GLFW;
import overrungl.glfw.GLFWErrorCallback;
import overrungl.glfw.GLFWErrorFun;
import overrungl.glfw.GLFWVidMode;
import overrungl.opengl.GL;
import overrungl.util.MemoryStack;
import overrungl.util.MemoryUtil;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static overrungl.glfw.GLFW.*;
import static overrungl.opengl.GL10.GL_COLOR;

public class SimpleGame {
    private static final int INIT_WIDTH = 800;
    private static final int INIT_HEIGHT = 600;
    private MemorySegment window = MemorySegment.NULL;
    private GL gl = null;

    public void start() {
        init();
        mainLoop();
    }

    private void init() {
        glfwSetErrorCallback(GLFWErrorFun.alloc(Arena.global(), GLFWErrorCallback.createPrint()));
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }
        GLFWVidMode vidMode = GLFWVidMode.ofNative(glfwGetVideoMode(glfwGetPrimaryMonitor()));
        if (vidMode != null) {
            glfwWindowHint(GLFW_POSITION_X, (vidMode.width() - INIT_WIDTH) / 2);
            glfwWindowHint(GLFW_POSITION_Y, (vidMode.height() - INIT_HEIGHT) / 2);
        }
        try (MemoryStack stack = MemoryStack.pushLocal()) {
            window = glfwCreateWindow(INIT_WIDTH, INIT_HEIGHT, stack.allocateFrom("Simple Game"), MemorySegment.NULL, MemorySegment.NULL);
        }
        if (MemoryUtil.isNullPointer(window)) {
            throw new IllegalStateException("Failed to create the window");
        }
        initGL();
    }

    private void initGL() {
        glfwMakeContextCurrent(window);
        gl = new GL(GLFW::glfwGetProcAddress);
    }

    private void mainLoop() {
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
            render();
        }
    }

    private void render() {
        try (MemoryStack stack = MemoryStack.pushLocal()) {
            gl.ClearNamedFramebufferfv(0, GL_COLOR, 0, stack.floats(0.4f, 0.6f, 0.9f, 1.0f));
        }
        glfwSwapBuffers(window);
    }

    public void dispose() {
        if (!MemoryUtil.isNullPointer(window)) {
            glfwDestroyWindow(window);
        }
        glfwTerminate();
    }

    public static void main(String[] args) {
        SimpleGame game = new SimpleGame();
        try {
            game.start();
        } finally {
            game.dispose();
        }
    }
}
