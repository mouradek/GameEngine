package game.modul;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.util.HashMap;

import game.math.Matrix4f;
import game.math.Vector3f;
import game.util.Util;




public class ShaderModul {

	private int program;
	private HashMap<String, Integer> uniforms;
	
	
	public ShaderModul() {
		program=glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		
		
		if(program == 0 ){
			System.out.println("Shader creation failed.");
			System.exit(1);
		}
	}
	
	public void bind() {
		glUseProgram(program);
		
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void addUniform(String uniform) {
		int uniformLocatoin = glGetUniformLocation(program, uniform);
		
		if(uniformLocatoin == 0xFFFFFFFF) {
			System.err.println("Error: Could not find uniform" + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocatoin);
		
	}
	
	public void addVertexShader(String text) {
		addProgramm(text,  GL_VERTEX_SHADER);		
	}

	public void addGeometryShader(String text) {
		addProgramm(text,  GL_GEOMETRY_SHADER);
	}

	public void addFragmentShader(String text) {
		addProgramm(text,  GL_FRAGMENT_SHADER);
	}
	
	public void compileShader() {
		glLinkProgram(program);
		
		if(glGetProgrami(program, GL_LINK_STATUS)==0) {
			System.err.println(glGetShaderInfoLog(program,1024));
			System.exit(1);
			
		}
		
		glValidateProgram(program);
		if(glGetProgrami(program, GL_VALIDATE_STATUS)==0) {
			System.err.println(glGetShaderInfoLog(program,1024));
			System.exit(1);
			
		}
	}
	
	private void addProgramm(String text, int type) {
		
		int shader = glCreateShader(type);
		if(shader==0) {
			System.out.println("Adding Shader failed.");
			System.exit(1);
		}
		
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if(glGetShaderi(shader, GL_COMPILE_STATUS)==0) {
			System.err.println(glGetShaderInfoLog(shader,1024));
			System.exit(1);
			
		}
		
		glAttachShader(program, shader);
		
	}

	public void setUniformi(String uniformName, int value) {
		glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniformf(String uniformName, float value) {
		glUniform1f(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, Vector3f value) {
		glUniform3f(uniforms.get(uniformName), value.getX(),value.getY(),value.getZ());
	}
	
	public void setUniform(String uniformName, Matrix4f value) {
		glUniformMatrix4fv(uniforms.get(uniformName), true, Util.createFlippedBuffer(value));
	}
	
	
	
}