package mns.test;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author novas
 */
public class Worker extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(Worker.class);
	private String id;
	private String data;
	private CountDownLatch latch;
	
	public Worker(String name) {
		id = name;
	}
	
	public void set(String data,CountDownLatch cdl) throws InterruptedException {
		this.latch = cdl;
		this.data = data;
	}
	
	@Override
	public void run() {
		try {
			long time = (long) (Math.random()*1000);
			sleep(time);
			logger.info("slept for {} miliseconds!",time);
		} catch (InterruptedException e) {
			logger.error("InterruptedException-{}: {}",id,e.getMessage());
		}
		logger.warn("{}-word: {}",id,data);
		latch.countDown();
	}
}
