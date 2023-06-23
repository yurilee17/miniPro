package everyOneShopPing;

import java.net.URL;
import java.util.ResourceBundle;

import equipment.Opener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.Login;

public class MainController implements Initializable {

    private Opener opener;
    private Parent login;
    private boolean loggedIn = false;
    private boolean isAdmin = false;

    public void setOpener(Opener opener) {
        this.opener = opener;
    }

    @FXML
    private Label loginUserIDLabel; // 사용자 아이디를 출력할 Label
    
    public Label getLoginUserIDLabel() {
        return loginUserIDLabel;
    }
    
    @FXML
    private Label adminLabel;

    private String loginUserID; // 로그인한 사용자 아이디 저장

    public void setLoggedInUserId(String userId) {
    	loginUserID = userId;
    }
    
    @FXML
    private ImageView searchImage;
    
    public void searchProc(MouseEvent event) {
    		if (opener != null) {
                opener.searchOpen();
                Stage stage = (Stage) loginUserIDLabel.getScene().getWindow();
                stage.close();
            } else {
                opener = new Opener();
                opener.searchOpen();
                Stage stage = (Stage) loginUserIDLabel.getScene().getWindow();
                stage.close();
            }
    }
    
    public void wishlistProc() {
        if (Login.isLoggedIn()) {
        } else {
            if (opener != null) {
                opener.loginOpen();
                Stage stage = (Stage) loginUserIDLabel.getScene().getWindow();
                stage.close();
            } else {
                opener = new Opener();
                opener.loginOpen();
                Stage stage = (Stage) loginUserIDLabel.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void myPageProc() {
        if (Login.isLoggedIn()) {
        } else {
            if (opener != null) {       	
                opener.loginOpen();
                Stage stage = (Stage) loginUserIDLabel.getScene().getWindow();
                stage.close();
            } else {
                opener = new Opener();
                opener.loginOpen();
                Stage stage = (Stage) loginUserIDLabel.getScene().getWindow();
                stage.close();
            }
        }
    }
    
    public void logoutProc() {
        if (!Login.isLoggedIn()) {
        	return;
        }   
        Login.setLoggedIn(false);
    	opener = new Opener();
    	opener.mainOpen();
        Stage stage = (Stage) loginUserIDLabel.getScene().getWindow();
        stage.close();
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    

 
}