package engineTester;

import java.awt.Font;
import java.io.File;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import entities.Camera;
import entities.Entity;
import entities.Light;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Player;

public class MainGame {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();

		TextMaster.init(loader);
		
		FontType font = new FontType(loader.loadTexture("font"),new File ("res/font.fnt"));
		float[] speed = {0,0};
		float accelaration =1;
		Vector3f position;
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("index"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("road"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,rTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("map"));
		
		/*TrueTypeFont font;
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24); //name, style (PLAIN, BOLD, or ITALIC), size
		font = new TrueTypeFont(awtFont, false);
		
		font.drawString(100, 50, "ABC123", Color.yellow);*/

		//Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("index")));

		
		
		RawModel model = OBJLoader.loadObjModel("camero", loader);
		//RawModel model1 = OBJLoader.loadObjModel("FullTrack", loader);
		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("camero")));
		//TexturedModel staticModel1 = new TexturedModel(model1,new ModelTexture(loader.loadTexture("Main")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Player player = new Player(staticModel, new Vector3f(20,0,-20),0,-180,0,3);
		
		//Entity entity = new Entity(staticModel, new Vector3f(-60,2,-30),0,-180,0,3);
		//Entity entity1 = new Entity(staticModel1, new Vector3f(0,-17,-70),0,0,0,100);
		
		GUIText text = new GUIText("0.0",1,font,new Vector2f(0.2f,0.0f),0.2f, true);
		GUIText text2 = new GUIText("SPEED : ",1,font,new Vector2f(0.0f,0.0f),0.2f, true);
		GUIText text3 = new GUIText("Use keys 'W', 'A', 'S' and 'D' to move the car.",1,font,new Vector2f(0.0f,0.2f),0.2f, true);
		TextMaster.loadText(text);
		TextMaster.loadText(text2);
		TextMaster.loadText(text3);
		
		Light light = new Light(new Vector3f(0,40,-150) , new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,-1,loader,texturePack, blendMap);
		Terrain terrain2 = new Terrain(-1,-1,loader,texturePack, blendMap);
		
		
		Camera camera = new Camera(player);
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()){
			/*if(Keyboard.isKeyDown(Keyboard.KEY_W)){
				entity.increasePosition(0, 0, -0.16f);
				if(entity.getRotY() > -180){
					entity.increaseRotation(0, -0.16f, 0);
				}
				if(entity.getRotY() < -180){
					entity.increaseRotation(0, 0.16f, 0);
				}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				if(Keyboard.isKeyDown(Keyboard.KEY_W)){
					entity.increaseRotation(0, -0.32f, 0);
					entity.increasePosition(0.16f, 0, 0);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_S)){
					entity.increaseRotation(0, 0.32f, 0);
					entity.increasePosition(0.16f, 0, 0);
				}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_A)){
				if(Keyboard.isKeyDown(Keyboard.KEY_W)){
					entity.increaseRotation(0, +0.32f, 0);
					entity.increasePosition(-0.16f, 0, 0);
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_S)){
					entity.increaseRotation(0, -0.32f, 0);
					entity.increasePosition(-0.16f, 0, 0);
				}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_S)){
				entity.increasePosition(0, 0, 0.16f);
				if(entity.getRotY() > -180){
					entity.increaseRotation(0, -0.16f, 0);
				}
				if(entity.getRotY() < -180){
					entity.increaseRotation(0, 0.16f, 0);
				}
			}*/
			//entity.increasePosition(0, 0, 0);
			//entity.increaseRotation(0, 1, 0);
			
			text.setTextString(Integer.toString((int)player.currentSpeed));
			position = player.getPosition();
			if(position.x > 60){
				position.x = 60;
				player.setPosition(position);
			}
			if(position.x < 5){
				position.x = 5;
				player.setPosition(position);
			}
			if(position.z < -790){
				position.z = -790;
				player.setPosition(position);
			}
			if(position.z > -5){
				position.z = -5;
				player.setPosition(position);
			}
			TextMaster.loadText(text);
			camera.move();
			player.move();
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			//renderer.processEntity(entity1);
			renderer.render(light, camera);
			TextMaster.render();
			DisplayManager.updateDisplay();
			TextMaster.removeText(text);
			
			
		}
		
		TextMaster.cleanUP();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
