package com.tertioptus.rss.director.xml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ATextFileEngineer implements TextFileEngineer {

	@Override
	public String readAll(Class cls, String path) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(cls.getResourceAsStream(path)));
        String contents = reader.lines().collect(Collectors.joining("\n"));
        return contents;
	}
}
