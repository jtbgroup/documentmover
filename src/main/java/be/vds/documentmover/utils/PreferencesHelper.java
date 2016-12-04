package be.vds.documentmover.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import be.vds.documentmover.vm.PreferencesViewModel;

public class PreferencesHelper {

	private static final String CONFIG_FILE = System.getProperty("user.home") + File.separatorChar + ".jtb"
			+ File.separatorChar + "documentMover" + File.separatorChar + "config.properties";

	public static void loadPreferences(PreferencesViewModel prefs ) throws IOException {
		File cf = new File(CONFIG_FILE);
		if (cf.exists()) {
			Properties properties = new Properties();
			properties.load(new FileInputStream(cf));

			prefs.detinationFolderProperty().setValue(properties.getProperty("dest.folder"));
			prefs.sourceFolderProperty().setValue(properties.getProperty("src.folder"));
			loadSender(properties.getProperty("sender"), prefs);
		}
	}

	private static void loadSender(String property, PreferencesViewModel prefs) {
		if (null != property) {
			String[] senderArray = property.split(";");
			Arrays.sort(senderArray);
			for (String sender : senderArray) {
				prefs.itemsProperty().add(sender);
			}
			
		}
	}

	public static void savePreferences(PreferencesViewModel preferences) throws IOException {
		File cf = new File(CONFIG_FILE);
		if (!cf.exists()) {
			File parent = cf.getParentFile();
			parent.mkdirs();
			cf.createNewFile();
		}

		Properties properties = new Properties();
		properties.setProperty("dest.folder", preferences.detinationFolderProperty().getValue());
		properties.setProperty("src.folder", preferences.sourceFolderProperty().getValue());
		properties.setProperty("sender", createSender(preferences));

		properties.store(new FileOutputStream(cf), "Preferences for Document Mover application provided by Jt'B");
	}

	private static String createSender(PreferencesViewModel preferences) {
		StringBuilder sb = new StringBuilder();
		for (String sender : preferences.itemsProperty()) {
			sb.append(sender).append(";");
		}
		;
		return sb.toString();
	}
}
