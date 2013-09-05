package mns.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author novas
 */
@Component
public class StartApp {
	private static final Logger logger = LoggerFactory.getLogger(StartApp.class);
	@Autowired Dispatcher disp;
	
	public void start() {
		for(int x=1;x<=100;x++){
			try {
				disp.pool("process-"+x);
			} catch (InterruptedException e) {
				logger.error("InterruptedException: ",e.getMessage());
			}
		}
		disp.start();
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
		StartApp app = ctx.getBean(StartApp.class);
		app.start();
	}
}
