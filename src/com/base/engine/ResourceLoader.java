package com.base.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.base.math.Vector3f;
import com.dep.lwjgl3.Mesh;

public class ResourceLoader {

	public static String loadShader(String fileName) {

		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader;
		try {
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));

			String line;
			while ((line = shaderReader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}

			shaderReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return shaderSource.toString();
	}

	public static Mesh loadMesh(String filename) {

		String[] splitArray = filename.split("\\.");
		String extension = splitArray[splitArray.length - 1];

		if (!extension.equals("obj")) {
			System.err.println("Error: File format" + extension + " not supported for mesh data: " + filename);
			new Exception().printStackTrace();
			System.exit(1);
		}

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();

		BufferedReader meshReader = null;
		try {

			meshReader = new BufferedReader(new FileReader("./res/mesh/" + filename));
			String line;

			while ((line = meshReader.readLine()) != null) {

				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);

				if (tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if (tokens[0].equals("v")) {

					Vector3f vertex = new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3]));
					vertices.add(new Vertex(vertex));
					
				}

				else if (tokens[0].equals("f")) {

					indices.add(Integer.parseInt(tokens[1])-1);
					indices.add(Integer.parseInt(tokens[2])-1);
					indices.add(Integer.parseInt(tokens[3])-1);
					
				}

			}

			meshReader.close();

			Mesh mesh = new Mesh();

			Vertex[] vertexData = new Vertex[vertices.size()];
			vertices.toArray(vertexData);
			
			Integer[] indexData = new Integer[indices.size()];
			indices.toArray(indexData);
			
			mesh.addVertices(vertexData, Util.toIntArray(indexData));
			
			return mesh;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;

	}

}
