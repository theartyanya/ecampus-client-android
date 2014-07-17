package dny.parallel;

import java.util.ArrayList;

public class Run {

    private Thread thread;

    private Runnable program;
	public Runnable getProgram() {return program;}

	private boolean active;
	public boolean isActive() {return active;}

	private static ArrayList<Run> runs = new ArrayList<Run>();
	public static Run getCurrentRun() {
		Thread thread = Thread.currentThread();
		for (int i = 0; i < runs.size(); i++) {
			Run run = runs.get(i);
			if (run.thread == thread) {
				return run;
			}
		}
		Run run = new Run(thread);
		return run;
	}

	public static void sleep() {
		Run currentRun = getCurrentRun();
		currentRun.active = false;
		synchronized (currentRun) {
			try {
				currentRun.wait();
			} catch (IllegalMonitorStateException e) {
			} catch (InterruptedException e) {}
		}
		currentRun.active = true;
	}
	public static void sleep(long time) {
		Run currentRun = getCurrentRun();
		currentRun.active = false;
		synchronized (currentRun) {
			try {
				currentRun.wait(time);
			} catch (IllegalMonitorStateException e) {
			} catch (InterruptedException e) {}
		}
		currentRun.active = true;
	}
	public static void sleep(double sec) {
		sleep((long)(sec * 1000));
	}

	public void wakeUp() {
		synchronized (this) {
            try {
                this.notify();
            } catch (IllegalMonitorStateException e) {}
		}
	}

	public void open() {
		active = true;
		thread.start();
	}

	/**
	 * Called from another run
	 */
	public void pause(long time) {
		try {
			thread.sleep(time);
		} catch (InterruptedException e) {}
	}


    public Run(Thread thread) {
        this.thread = thread;
        runs.add(this);
    }
	public Run(Runnable program) {
        this(new Thread(program));
		this.program = program;
	}
	protected Run(Thread thread, Runnable program) {
		this(thread);
		this.program = program;
	}

}
