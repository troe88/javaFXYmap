package test.model;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import test.Main;

public class HTMLBuilder {
	static Configuration cfg;

	static {
		cfg = new Configuration();
		cfg.setClassForTemplateLoading(Main.class, "");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(Locale.US);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	private static void putDigitWithSign(final Map<String, Object> input, final String key, final Double digit) {
		input.put(key, Math.abs(digit));
		if (digit >= 0) {
			if (!key.equals("A")) {
				input.put("sign_" + key, "+");
			} else {
				input.put("sign_" + key, "");
			}
		} else {
			input.put("sign_" + key, "-");
		}
	}

	public static String getFormattedQE(Double A, Double B, Double C, Double D, Double X1, Double X2) {
		String res;
		try {
			Template template = cfg.getTemplate("qe.ftl");
			Map<String, Object> input = new HashMap<String, Object>();

			putDigitWithSign(input, "A", A);
			putDigitWithSign(input, "B", B);
			putDigitWithSign(input, "C", C);

			input.put("D", D.toString());
			input.put("X1", X1.toString());
			input.put("X2", X2.toString());

			Writer out = new StringWriter();
			template.process(input, out);
			res = out.toString();

		} catch (IOException | TemplateException e) {
			e.printStackTrace();
			res = "error";
		}
		return res;
	}
}
