package test.utils;

import static java.lang.Runtime.getRuntime;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PingTest implements Runnable {
	private static final int DELAY = 500;
	private static final String ADR = "8.8.8.8";
	public AtomicBoolean _isReacheble = new AtomicBoolean(false);
	
	
	public boolean getIsReacheble() {
		return _isReacheble.get();
	}

	@Override
	public void run() {
		while (true) {
			try {
				TimeUnit.SECONDS.sleep(1);
				Process p1 = getRuntime().exec("ping -c 1 " + ADR);
				_isReacheble.set(p1.waitFor(DELAY, TimeUnit.MILLISECONDS));
				System.out.println(_isReacheble.get());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
