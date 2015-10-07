package test.utils;

import static java.lang.Runtime.getRuntime;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PingTest implements Runnable {
	private static final int DELAY = 500;
	private static final String ADR = "8.8.8.8";
	public AtomicBoolean _isReacheble = new AtomicBoolean(false);

	LinkedBlockingDeque<Boolean> _q = new LinkedBlockingDeque<>();

	public boolean getIsReacheble() {
		for (Boolean i : _q) {
			if(i) return true;
		}
		return false;
	}

	@Override
	public void run() {
		while (true) {
			try {
				TimeUnit.SECONDS.sleep(1);
				Process p1 = getRuntime().exec("ping -c 1 " + ADR);
				boolean res = p1.waitFor(DELAY, TimeUnit.MILLISECONDS);

				if (_q.size() >= 3) {
					_q.removeLast();
				}

				_q.addFirst(res);
				_isReacheble.set(res);
				System.out.println(_isReacheble.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
