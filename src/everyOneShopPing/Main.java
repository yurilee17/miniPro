package everyOneShopPing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import service.MenuController;

import java.io.IOException;

import equipment.Opener;

public class Main extends Application {
	private StackPane root;
    private static BorderPane menuPane;
    private StackPane centerPane;
    private StackPane contentStackPane;

    private StackPane mainStackPane; 
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public StackPane getMainStackPane() {
        return mainStackPane;
    }
    
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("메인 화면");
        try {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("../service/menu.fxml"));
            BorderPane menuPane = menuLoader.load();        

            StackPane contentStackPane = (StackPane) menuLoader.getNamespace().get("contentStackPane");
            
            Scene scene = new Scene(menuPane);
            primaryStage.setScene(scene);
            primaryStage.show();

            MenuController menuController = menuLoader.getController();
            menuController.setContentStackPane(contentStackPane);
            menuController.setMenuCon(menuController);
            Opener opener = new Opener();
            menuController.setOpener(opener);
            opener.setPrimaryStage(primaryStage);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
