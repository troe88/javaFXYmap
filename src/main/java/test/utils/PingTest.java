package test.utils;

import static java.lang.Runtime.getRuntime;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PingTest implements Runnable {
	private static final int DELAY = 500;
	private static final String ADR = "8.8.8.8";
	private static final String[] MESH = { "echo", "'MESH is up'" };
	private static final String[] LTE = { "echo", "'MESH is up'" };
	private int _flag = 0;

	public AtomicBoolean _isReacheble = new AtomicBoolean(false);

	LinkedBlockingDeque<Boolean> _q = new LinkedBlockingDeque<>();

	public boolean checkChange() throws IOException {
		for (Boolean i : _q) {
			if (i) {
				if (_flag != 1) {
					System.out.println(ProcessHelper.exec(LTE));
					_flag = 1;
				}
				return true;
			}
		}
		if (_flag != 2) {
			_flag = 2;
			System.out.println(ProcessHelper.exec(MESH));
		}
		return false;
	}

	public boolean getIsReacheble() {
		return _isReacheble.get();
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
				// _isReacheble.set(res);
				// System.out.println(_isReacheble.get());

				_isReacheble.set(checkChange());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
