package se.chalmers.spaceshooter.game;

import se.chalmers.spaceshooter.game.object.loot.HealthPack;
import se.chalmers.spaceshooter.game.object.loot.Loot;
import se.chalmers.spaceshooter.game.object.loot.SlowTimePack;

public class LootManager {
	
	private static int slotOffset = 600;
	private static Loot lootArray[] = new Loot[3];
	private static int size = lootArray.length;
	
	public static void addLoot(Loot loot, int index){
		lootArray[index] = loot;
	}
	
	public static void removeLoot(int index){
		lootArray[index] = null;
	}
	
	public static void clear(){
		for(int i = 0; i < size; i++)
			lootArray[i] = null;
	}
	
	public static void initLootSlots(Loot... loots){
		for(int i = 0; i < loots.length; i++)
			lootArray[i] = loots[i];
	}
	
	/**
	 * Handles what will happen when player press one of the three lootslots
	 * @param x
	 *            x position of the touchevent area
	 * @param y
	 *            y position of the touchevent area
	 */
	public static void manageLootSlots(float x, float y) {
		if (x >= slotOffset && x <= slotOffset + 50 && y >= 380 && y <= 430) {
			Loot loot = lootArray[0];
			if (loot instanceof HealthPack) {
				HealthPack hp = (HealthPack) loot;
				GameObjectManager.getPlayer().incHp(hp.getHp());
				lootArray[0] = null;
			}
			if (loot instanceof SlowTimePack) {
				GameObjectManager.setSlowTime(true);
				lootArray[0] = null;
			}
		}
		if (x >= slotOffset + 70 && x <= slotOffset + 70 + 50 && y >= 380 && y <= 430) {
			Loot loot = lootArray[1];
			if (loot instanceof HealthPack) {
				HealthPack hp = (HealthPack) loot;
				GameObjectManager.getPlayer().incHp(hp.getHp());
				lootArray[1] = null;
			}
			if (loot instanceof SlowTimePack) {
				GameObjectManager.setSlowTime(true);
				lootArray[1] = null;
			}
		}
		if (x >= slotOffset + 70 * 2 && x <= slotOffset + (70 * 2) + 50 && y >= 380 && y <= 430) {
			Loot loot = lootArray[2];
			if (loot instanceof HealthPack) {
				HealthPack hp = (HealthPack) loot;
				GameObjectManager.getPlayer().incHp(hp.getHp());
				lootArray[2] = null;
			}
			if (loot instanceof SlowTimePack) {
				GameObjectManager.setSlowTime(true);
				lootArray[2] = null;
			}
		}
	}
	
	public static void expandLootArray(int n){
		lootArray = new Loot[3 + n];
		size = lootArray.length;
	}
	
	public static Loot getLootAt(int index){
		return lootArray[index];
	}

	public static int getSize() {
		return size;
	}
	
	

}
