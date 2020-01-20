package com.parvanpajooh.cargoarchive.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Config {

	public final static String REPO_BASE_PATH = "repo.base.path";

	private static Config instance;
	private Properties props;

	/**
	 * 
	 */
	private Config() {

		props = new Properties();
		String userDirectory = System.getProperty("user.home");
		File configFile = new File(userDirectory, "config.properties");
		FileInputStream fis;
		try {
			fis = new FileInputStream(configFile);
			props.load(fis);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	private static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}

		return instance;
	}

	/**
	 * 
	 * @param key
	 */
	public static String getProperty(String key) {
		return getInstance().props.getProperty(key);
	}

}
