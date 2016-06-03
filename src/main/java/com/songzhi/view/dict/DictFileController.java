package com.songzhi.view.dict;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import com.songzhi.lanch.MainApp;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class DictFileController {
	private static final Logger log = Logger.getLogger(DictFileController.class);
	
	@FXML
	private TreeTableView<String[]> dictsTable;
	
	@FXML
	private TreeTableColumn<String[], String> dictNameColumn;
	
	@FXML
	private TextArea textArea;
	
	private MainApp mainApp;
		
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
    final TreeItem<String[]> root = new TreeItem<String[]>(new String[]{"root", ""});
    root.setExpanded(true); 
    
    try {
			File file = new File( URLDecoder.decode(DictFileController.class.getResource("/dict").getPath(),"utf-8"));
			List<File> files = Arrays.asList(file.listFiles());
			files.stream().forEach(f -> {
				 String[] strs = new String[]{f.getName().split("\\.")[0], f.getAbsolutePath()};
				 root.getChildren().add(new TreeItem<String[]>(strs));
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		dictsTable.setRoot(root);
	}
	 
	
	@FXML
	private void initialize() {
		dictNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()[0]));
		
		dictsTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> textArea.setText(loadDict(newValue.getValue())));
	}

	private String loadDict(String[]  arr) {
		if("root".equals(arr[0])) return "";
		
		StringBuffer text = new StringBuffer();
		try {
			InputStream is = null;
			String filename = arr[1];
			ZipFile zf = null;
			if(filename.endsWith(".zip")) {
				zf = new ZipFile(filename);
				ZipEntry ze = zf.entries().nextElement();
				is = zf.getInputStream(ze);
			}else if(filename.endsWith(".gz")) {
				is = new GZIPInputStream(new FileInputStream(filename));
			}else {
				is = new FileInputStream(filename);
			}
			
			LineNumberReader reader = new LineNumberReader(new InputStreamReader(is, "UTF-8"));
			String line = null;
			while((line=reader.readLine()) != null) {
				text.append(line).append("\n");
			}
			
			reader.close();
			if(zf != null) zf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text.toString();
	}

}
