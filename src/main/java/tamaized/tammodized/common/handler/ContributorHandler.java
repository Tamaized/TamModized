package tamaized.tammodized.common.handler;


import tamaized.tammodized.TamModized;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class ContributorHandler {

	private static final String URL_FLUFF = "https://raw.githubusercontent.com/Tamaized/TamModized/1.12/contributors/fluff.properties";

	public static volatile Map<UUID, Integer> fluff = new HashMap<UUID, Integer>();

	private static boolean started = false;

	public static void start() {
		if (!started) {
			TamModized.instance.logger.info("Starting Contributor Handler");
			started = true;
			new ThreadContributor();
		}
	}

	public static void loadFluff(Properties props) {
		fluff.clear();
		for (String s : props.stringPropertyNames()) {
			UUID key = UUID.fromString(s);
			String value = props.getProperty(s);
			try {
				int color = Integer.parseUnsignedInt(value, 16);
				TamModized.instance.logger.info(key + " -> " + color);
				fluff.put(key, color);
			} catch (NumberFormatException e) {
				TamModized.instance.logger.error("Error loading Fluff Color: " + value);
			}
		}
	}

	private static class ThreadContributor extends Thread {

		public ThreadContributor() {
			setName("TamModized Contributor Loader");
			setDaemon(true);
			start();
		}

		@Override
		public void run() {
			try {
				{
					TamModized.instance.logger.info("Loading Fluff Properties");
					URL url = new URL(URL_FLUFF);
					Properties props = new Properties();
					InputStreamReader reader = new InputStreamReader(url.openStream());
					props.load(reader);
					loadFluff(props);
				}
			} catch (IOException e) {
				TamModized.instance.logger.error("Could not load contributors");
			}
		}

	}

}
