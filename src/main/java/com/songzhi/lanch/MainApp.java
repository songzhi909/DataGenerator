package com.songzhi.lanch;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.songzhi.generate.Container;
import com.songzhi.model.DatabaseModel;
import com.songzhi.utils.db.DBHelper;
import com.songzhi.view.MainViewController;
import com.songzhi.view.RootLayoutController;
import com.songzhi.view.db.DatabaseController;
import com.songzhi.view.diag.ProgressDialogController;
import com.songzhi.view.dict.DictFileController;
import com.songzhi.view.tablerule.TableRuleController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	private static Logger log = Logger.getLogger(MainApp.class);
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private static Container container;
	
	public Container getContainer() {
		return container;
	}

	
	@Override
	public void start(Stage primaryStage) {
		
		//显示UI
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("数据造假小帮手");
		
		initRootLayout();
		
		//showMainView();
	}

	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getClassLoader().getResource("com/songzhi/view/RootLayout.fxml"));
			rootLayout = loader.load();
			
			Scene scene = new Scene(rootLayout);	//创建初始化场景
			primaryStage.setScene(scene);	//设置场景
			primaryStage.show();
			
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void showMainView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getClassLoader().getResource("com/songzhi/view/MainView.fxml"));
			AnchorPane mainView = loader.load();
			
//			mainView.get
			
			// 设置在 rootLayout的中间布局
			rootLayout.setCenter(mainView);
			
			//得到MainView的控制器，设置容器变量
			MainViewController controller = loader.getController();
			controller.setContainer(this);
			
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	

	public void showTableRule() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getClassLoader().getResource("com/songzhi/view/tablerule/TableRuleView.fxml"));
			AnchorPane mainView = loader.load();
			
			// 设置在 rootLayout的中间布局
			rootLayout.setCenter(mainView);
			
			//得到MainView的控制器，设置容器变量
			TableRuleController controller = loader.getController();
			controller.setContainer(this);
			
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	

	/** 显示进度条*/
	public void showProgressDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getClassLoader().getResource("com/songzhi/view/diag/ProgressDialogView.fxml"));
			AnchorPane dialog = loader.load();
			
			
		// create the dialog stage
					Stage dialogStage = new Stage();
					dialogStage.setTitle("进度条...");
					dialogStage.initModality(Modality.WINDOW_MODAL);
					dialogStage.initOwner(primaryStage);
					Scene scene = new Scene(dialog);
					dialogStage.setScene(scene);
										
					//得到MainView的控制器，设置容器变量
					ProgressDialogController controller = loader.getController();
					controller.setMainApp(this);
					
					//show the dialog and wait until the user closes it
//					dialogStage.showAndWait();
					dialogStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
//			log.error(e.getMessage());
		}
	}
	
	private ObservableList<DatabaseModel> databaseData = FXCollections.observableArrayList();
	
	public ObservableList<DatabaseModel> getDatabaseData() {
		return databaseData;
	}
	
	/**
	 * 从xml文件中读取数据库连接信息
	 */
	public void loadDatabaseFromFile() {
		log.info("读取数据库连接信息...");
		
		List<DatabaseModel> databases = DBHelper.fetchDatabases();
		
		databaseData.clear();
		
		if(databases != null && databases.size() > 0) databaseData.addAll(databases);
	}
	 
	
	public void showDatabase() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getClassLoader().getResource("com/songzhi/view/db/DatabaseView.fxml"));
			VBox page = loader.load();
			
			// create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("数据库连接");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.setOnCloseRequest(value -> DBHelper.saveDatabases(databaseData));	//监听关闭
			
			//set the person into the controller
			DatabaseController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
			
			//show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public void loadDicts() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getClassLoader().getResource("com/songzhi/view/dict/DictFileView.fxml"));
			AnchorPane pane = loader.load();
			
			// 设置在 rootLayout的中间布局
			rootLayout.setCenter(pane);
			
			//得到MainView的控制器，设置容器变量
			DictFileController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private static GenApp genApp;
	
	public GenApp getGenApp() {
		return genApp;
	}
	
	public static void launch(String[] args, GenApp app) {
	  genApp = app;
		container = app.getContainer();
		launch(args);
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}


}
