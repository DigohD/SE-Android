package com.spaceshooter.game.level.sequence;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;

import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.util.BitmapHandler;
import com.spaceshooter.game.util.Color;
import com.spaceshooter.game.util.Randomizer;
import com.spaceshooter.game.util.Vector2f;
import com.spaceshooter.game.view.GameView;

/**
 * Class for creating the enemies in a sequence. You create a image with a black background
 * then place out pixels of different colors which will represent the position of the enemy on the screen.
 * 
 * @author Anders
 *
 */
public class Sequence {
	//the width and height of the sequence image
	private int width, height;
	//the width and height ratio between the sequence image and the view
	private int widthRatio, heightRatio;
	//the integer values for each pixel in the sequence image
	private int[] pixels;
	//list of strings which has the names of the different sequence images
	private ArrayList<String> sequences;
	//maps a specific pixel in the sequence image to a certain type of enemy 
	protected HashMap<Integer, Enemy> enemies;
	//maps pixelcolor to an arraylist containing the positions of all the pixels with that specific color 
	private HashMap<Integer, ArrayList<Vector2f>> colorPosMap;
	
	public Sequence(){
		enemies = new HashMap<Integer, Enemy>();
		colorPosMap = new HashMap<Integer, ArrayList<Vector2f>>();
	}
	
	/**
	 * First loops through the colorPosMap to see if an enemy is mapped to the same color key
	 * then loops through the pixel positions and sets all enemies with that color key to their correct positions
	 * Also calls the addtomanagerlists method which simply adds the enemies to one list for collision detection and one for 
	 * drawing and updating
	 */
	public void runSequence(){
		for(Integer i : colorPosMap.keySet()){
			if(enemies.containsKey(i)){
				for(Vector2f v : colorPosMap.get(i)){
					Enemy e;
					try {
						e = enemies.get(i).getClass().newInstance();
						e.addToManagerLists();
						e.setPosition(v);
					} catch (InstantiationException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * Loops through all pixels of the sequence image and stores all non black pixels as keys in a map and their positions in
	 * arraylists which are the values of the map. 
	 */
	protected void scanSequence(){
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++){
				int pixIndex = x + (y*width);
				//x = the pixels x position , y * width = the pixels y position
				if(pixels[pixIndex] != Color.BLACK)
					//if the color already exists as a key in the map, simply add that pixels position in the arraylist
					//which holds all positions
					if(colorPosMap.containsKey(pixels[pixIndex]))
						colorPosMap.get(pixels[pixIndex]).add(
								new Vector2f((x * widthRatio) + 800, y * heightRatio));
					else{
						//if the color dont exist as a key put it in the map with a new arraylist as value to store all positions
						colorPosMap.put(pixels[pixIndex], new ArrayList<Vector2f>());
						//and finally add the position of the pixel
						colorPosMap.get(pixels[pixIndex]).add(
								new Vector2f((x * widthRatio) + 800, y * heightRatio));
					}	
			}
	}
	
	/**
	 * loads all the pixels of a sequence image into an array 
	 * which can then be looped through and used for manipulating/checking individual pixels
	 * @param path the name of the sequence image
	 */
	protected void loadSequence(String path){
		Bitmap bmp = BitmapHandler.loadBitmap("sequences/" + path);
		width = bmp.getWidth();
		height = bmp.getHeight();
		widthRatio = 800 / width;
		heightRatio = 480 / height;
		pixels = new int[width*height];
		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		bmp.recycle();
	}
	
	/**
	 * loads a random sequence image from a collection of images 
	 * which are located in the assets/images/sequences folder
	 * @param seq the name of the sequence image collection 
	 * @param numOfSeq number of sequence images in the collection
	 */
	protected void loadRandomSequence(String seq, int numOfSeq){
		sequences = new ArrayList<String>();
		for(int i = 0; i < numOfSeq; i++)
			sequences.add(seq + i);
		int i = Randomizer.getInt(0, sequences.size());
		loadSequence(sequences.get(i));
	}
	
	public ArrayList<String> getSequences() {
		return sequences;
	}

}
