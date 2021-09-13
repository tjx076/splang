package com.olcap.schedule;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Usage:
 * <br/>
 * 
 * <pre>
 * new TimerScheduler().schedule(() -> {
 * 
 * 		// your business code
 * 
 * }, 1000, TimeUnit.MILLISECONDS);
 * </pre>
 * 
 * @author tangdou
 *
 */
public class TimerScheduler implements  Schedulable{

	private Timer timer;
	
	public TimerScheduler(){
		timer = new Timer();
	}
	
	@Override
	public void schedule(Runnable command, long delay, TimeUnit unit) {
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				command.run();
			}
		}, unit.toMillis(delay));
	}

	@Override
	public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
		
		return null;
	}

	@Override
	public void schedule(Runnable command, long initialDelay, long period, TimeUnit unit) {		
	}

}
