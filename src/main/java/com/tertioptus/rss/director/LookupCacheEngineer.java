package com.tertioptus.rss.director;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class LookupCacheEngineer implements LookupEngineer {

	private Map<String, String> lookup = new HashMap<>();

	public String value(String resource, String key) throws Exception {
		if (lookup.isEmpty() && new File(resource).exists()) {
			File file = new File(resource);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
			while ((st = br.readLine()) != null) {
				String[] tokens = st.split(" ");
				lookup.put(tokens[0], tokens[1]);
			}
			br.close();
		}
		String value = lookup.get(key);
		return value == null ? "" : value;
	}

	public void record(String resource, String key, String value) throws Exception {
		FileWriter fw = new FileWriter(resource, true); // the true will append the new data
		fw.write(key + " " + value + "\n");// appends the string to the file
		fw.close();
	}
}
