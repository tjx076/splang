package com.olcap.schedule;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public interface Schedulable {
	
	public void schedule(Runnable command, long delay, TimeUnit unit);

	public <V> ScheduledFuture<V> schedule(Callable<V> callable, 
			long delay, TimeUnit unit);

	public void schedule(Runnable command,
            long initialDelay,
            long period,
            TimeUnit unit);
}
