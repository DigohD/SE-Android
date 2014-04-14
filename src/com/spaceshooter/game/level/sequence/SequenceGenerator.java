package com.spaceshooter.game.level.sequence;

import java.util.ArrayList;
import java.util.HashMap;

import com.spaceshooter.game.engine.GameThread;
import com.spaceshooter.game.util.Randomizer;

/**
 * Class used for generating sequences of enemies. The idea is to create a timeline where you can choose
 * generate sequences to it randomly or manually.
 * @author Anders
 *
 */
public class SequenceGenerator {
	//the timer for the timeline
	private int timelineTime = 0;
	//as long as update is true the generation of sequences will continue
	private boolean update = true;
	//the total time for the timeline
	private int TIME;
	//how many times the game is updated every second
	private final int TPS = (int) GameThread.TARGET_TPS;
	//list of sequences which is used for randomly generating a timeline
	private ArrayList<Sequence> sequences;
	//the timeline map which has the times that a specific sequence should be generated stored as keys
	//and the specific sequence as value
	private HashMap<Integer, Sequence> timeline;
	
	/**
	 * Creates a sequence generator
	 * @param time the time of the timeline used for sequence generations
	 */
	public SequenceGenerator(int time){
		this.TIME = time;
		timeline = new HashMap<Integer, Sequence>();
		sequences = new ArrayList<Sequence>();
	}
	
	/**
	 * Adds a sequence to the timeline
	 * @param seq the sequence to add to the timeline
	 * @param timeStamp the time in seconds at which the sequence shall appear
	 */
	public void addSequenceToTimeline(Sequence seq, int timeStamp){
		//a min and max time to ensure that the added sequence wont be too close to 
		//another sequence in the timeline
		int min = (timeStamp * TPS) - (5*TPS);
		int max = (timeStamp * TPS) + (5*TPS);
		
		//remove a sequence if it is in the time interval defined by min and max
		for(int i = min; i < max; i++)
			if(timeline.containsKey(i))
				timeline.remove(i);
				
		timeline.put(timeStamp*TPS, seq);
	}
	
	/**
	 * Adds a sequence to the list which will be used for generating a random timeline
	 * @param seq the sequence to add
	 */
	public void addSequence(Sequence seq){
		sequences.add(seq);
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
				timeline.put(i*TPS, sequences.get(r));
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
				timeline.put(i*TPS, seq);
				int timeStamp = Randomizer.getInt2(min, max);
				i += timeStamp;
			}
		}
	}
	
	/**
	 * As long as the update variable is set to true the timeline proceeds and
	 * if the time matches a entry in the timeline map the sequence stored at time will get executed
	 * and then removed from the timeline
	 */
	public void tick(){
		if(update){
			timelineTime++;
			if(timeline.containsKey(timelineTime)){
				timeline.get(timelineTime).runSequence();
				timeline.remove(timelineTime);
			}
		}
	}
	
	public void setUpdate(boolean t){
		update = t;
	}
	
	public void setTime(int time){
		TIME = time;
	}
	
	public int getTime(){
		return TIME;
	}
	
}
