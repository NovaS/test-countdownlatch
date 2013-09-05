package mns.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author novas
 */
@Component
public class Dispatcher extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	private static final int SERVICE_COUNT = 5;
	private CountDownLatch cycle;
	private boolean active;
	private LinkedBlockingQueue<String> pool;
	
	public Dispatcher() {
		active = false;
		pool = new LinkedBlockingQueue<String>();
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void pool(String data) throws InterruptedException {
		pool.put(data);
	}
	
	@Override
	public void run() {
		active = true;
		while(active){
			try {
				logger.info("pool size: {}",pool.size());
				String word = pool.take();
				cycle = new CountDownLatch(SERVICE_COUNT);
				Worker wk1 = new Worker("wk1");
				wk1.set(word, cycle);
				wk1.start();
				Worker wk2 = new Worker("wk2");
				wk2.set(word, cycle);
				wk2.start();
				Worker wk3 = new Worker("wk3");
				wk3.set(word, cycle);
				wk3.start();
				Worker wk4 = new Worker("wk4");
				wk4.set(word, cycle);
				wk4.start();
				Worker wk5 = new Worker("wk5");
				wk5.set(word, cycle);
				wk5.start();
				logger.info("Start countdown for word: {}",word);
				cycle.await();
			} catch (InterruptedException e) {
				logger.error("InterruptedException: {}",e.getMessage());
			}
		}
	}
}
