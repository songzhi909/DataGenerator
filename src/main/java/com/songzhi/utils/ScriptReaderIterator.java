package com.songzhi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * 文件读取脚本
 */
public class ScriptReaderIterator implements Iterator<String> {
	private static Logger logger = Logger.getLogger(ScriptReaderIterator.class);

	private LineNumberReader reader;
	private String nextLine;	//行内容
	
	public ScriptReaderIterator(String script) {
		this.reader = new LineNumberReader(new StringReader(script));
		prepareNextLine();
	}
	
	public ScriptReaderIterator(Reader reader) {
		this.reader = new LineNumberReader(new BufferedReader(reader));
		prepareNextLine();
	}

	public ScriptReaderIterator(File file) {
		try {
			this.reader = new LineNumberReader(new BufferedReader(new FileReader(file)));
			prepareNextLine();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public boolean hasNext() {
		return nextLine != null;
	}

	@Override
	public String next() {
		String s = nextLine;
		prepareNextLine();
		return s;
	}

	@Override
	public void remove() {
		
	}

	/** 读取文件的一行信息，直到有信息为止 */
	private void prepareNextLine() {
		try {
			nextLine = reader.readLine();
			if(nextLine != null) {
				if("".equals(nextLine.trim())) {
					prepareNextLine();
				}
			}
		} catch (IOException e) {
			logger.error("Could not read line: " + e.getMessage());
		}
	}
}
