package test.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessHelper {
	public static String exec(final String[] cmd) throws IOException {
		String res = null;
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(cmd);

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(
				proc.getErrorStream()));

		// read the output from the command
		String s = null;
		while ((s = stdInput.readLine()) != null) {
			res += s;
			res += "\n";
		}

		// read any errors from the attempted command
		while ((s = stdError.readLine()) != null) {
			res += s;
			res += "\n";
		}
		return res;
	}
}
