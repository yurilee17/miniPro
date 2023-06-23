package everyOneShopPing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Admin extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("../service/admin.fxml"));
	    Parent form = loader.load();
	    
	    MainController mainController = loader.getController();
	    
	    primaryStage.setScene(new Scene(form));
	    primaryStage.setTitle("관리자 화면");
	    primaryStage.show();
	}

}
