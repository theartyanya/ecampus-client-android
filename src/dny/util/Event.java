package dny.util;

import java.util.ArrayList;

public class Event implements Runnable {
	
	private final ArrayList<Runnable> actions = new ArrayList<Runnable>();
	
	public void addAction(Runnable action) {
		actions.add(action);
	}
	
	@Override public void run() {
		for (Runnable action : actions) {
			action.run();
		}
	}
	
}
