package com.spaceshooter.game.level;

import java.util.ArrayList;
import java.util.HashMap;

import com.spaceshooter.game.engine.GameThread;
import com.spaceshooter.game.level.sequence.Sequence;
import com.spaceshooter.game.object.enemy.Enemy;
import com.spaceshooter.game.util.Randomizer;

/**
 * Class used for generating sequences of enemies or single enemies to a timeline. 
 * The idea is to create a timeline where you can choose to generate sequences to it randomly or manually. 
 * There is also an option to add single enemies to a parallell timeline.
 * @author Anders
 *
 */
public class EnemyGenerator {
	//the timer for the timeline
	private int timelineTime = 0;
	//as long as update is true the generation of sequences/enemies will continue
	private boolean update = true;
	//the total time in seconds for the timeline
	private int TIME;
	//how many times the game is updated every second
	private int TPS = (int) GameThread.TARGET_TPS;
	//list of sequences which is used for randomly generating a timeline
	private ArrayList<Sequence> sequences;
	//the timeline map which has the times that a specific sequence should be generated stored as keys
	//and the specific sequence as value
	private HashMap<Integer, Sequence> sequenceTimeline;
	//the timeline map which has the times that a specific enemy should be generated stored as keys
	//and the specific enemy as value
	private HashMap<Integer, Enemy> enemyTimeline;
	
	/**
	 * Creates a sequence generator
	 * @param time the time in seconds of the timeline used for sequence/enemy generations
	 */
	public EnemyGenerator(int time){
		this.TIME = time*TPS;
		sequenceTimeline = new HashMap<Integer, Sequence>();
		enemyTimeline = new HashMap<Integer, Enemy>();
		sequences = new ArrayList<Sequence>();
	}
	
	/**
	 * Adds a sequence to the list which will be used for generating a random timeline
	 * @param seq the sequence to add
	 */
	public void addSequence(Sequence seq){
		sequences.add(seq);
	}
	
	/**
	 * Adds a sequence to the timeline
	 * @param seq the sequence to add to the timeline
	 * @param timeStamp the time in seconds that the sequence shall appear
	 */
	public void addSequenceToTimeline(Sequence seq, int timeStamp){
		//a min and max time to ensure that the added sequence wont be too close to 
		//another sequence in the timeline
		int min = (timeStamp * TPS) - (5*TPS);
		int max = (timeStamp * TPS) + (5*TPS);
		
		//remove a sequence if it is in the time interval defined by min and max
		for(int i = min; i < max; i++)
			if(sequenceTimeline.containsKey(i))
				sequenceTimeline.remove(i);
				
		sequenceTimeline.put(timeStamp*TPS, seq);
	}
	
	/**
	 * Adds an enemy to the timeline
	 * @param e the enemy that will be added to the timeline
	 * @param timeStamp the time in seconds that the enemy shall appear
	 */
	public void addEnemyToTimeline(Enemy e, int timeStamp){
//		int min = (timeStamp * TPS) - (1*TPS);
//		int max = (timeStamp * TPS) + (1*TPS);
//		
//		//remove a sequence if it is in the time interval defined by min and max
//		for(int i = min; i < max; i++)
//			if(enemyTimeline.containsKey(i))
//				enemyTimeline.remove(i);
				
		enemyTimeline.put(timeStamp*TPS, e);
	}
	
	/**
	 * Generates a random timeline of sequences.
	 * Timestamps are simply the specific time in seconds at which a sequence should get generated
	 */
	public void generateRandomTimeLine(){
		//the min and max values are used to avoid generating sequences to close to each other
		//mininum timestamp
		int min = 3;
		//maximum timestamo
		int max = min + 3;
		//starting timestamp
		int i = 2;
		while(i < TIME){
			int r = Randomizer.getInt(0, sequences.size());
			//if the there is only one sequences then there is no need to make a new instance
			if(sequences.get(r).getSequences().size() == 1){
				sequenceTimeline.put(i*TPS, sequences.get(r));
				int timeStamp = Randomizer.getInt2(min, max);
				i += timeStamp;
			//if however there are several sequences to choose from then we must make a new instance
			//in order to randomly pick one of the sequences available	
			}else{
				Sequence seq = null;
				try {
					seq = sequences.get(r).getClass().newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				sequenceTimeline.put(i*TPS, seq);
				int timeStamp = Randomizer.getInt2(min, max);
				i += timeStamp;
			}
		}
	}
	
	/**
	 * Updates the timeline.
	 * The timelinetime variable keeps track of where in the timelines we are and when it matches an entry
	 * in the sequencetimeline map the sequence stored at that time will run. If it matches an entry in the enemytimeline map
	 * the enemy at that time will be initiated and added to the manager lists, so it can be updated, drawn and checked for collisions 
	 */
	public void tick(){
		if(update){
			timelineTime++;
			if(enemyTimeline.containsKey(timelineTime)){
				enemyTimeline.get(timelineTime).addToManagerLists();
				enemyTimeline.remove(timelineTime);
			}
			if(sequenceTimeline.containsKey(timelineTime)){
				sequenceTimeline.get(timelineTime).runSequence();
				sequenceTimeline.remove(timelineTime);
			}
		}
	}
	
	public void setUpdate(boolean update){
		this.update = update;
	}
	
	public void setTime(int time){
		TIME = time;
	}
	
	public int getTime(){
		return TIME;
	}
	
}
