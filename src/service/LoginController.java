package service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import equipment.Opener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import member.MemberDAO;

public class LoginController implements Initializable {
    @FXML
    private TextField idFld;
    @FXML
    private PasswordField pwFld;
    private Stage loginStage;
    private Parent login;
	private Parent regForm;
    private LoginService service;
	private RegService regService;
	private AdminController adminCon;
	private MenuController menuCon;
	
    public void setMenuCon(MenuController menuCon) {
        this.menuCon = menuCon;
    }
    public void setAdminCon(AdminController adminCon) {
	    this.adminCon = adminCon;
	}

    @FXML
    private StackPane contentStackPane;

    private Opener opener;
    
    private void loginForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent form = loader.load();
            LoginController controller = loader.getController();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static boolean loggedIn = false;
    private static String userID;

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        Login.loggedIn = loggedIn;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        Login.userID = userID;
    }
    
    public void setOpener(Opener opener) {
        this.opener = opener;
    }

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    public void setLoginForm(Parent login) {
        this.login = login;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = new LoginService();
    }
    
    

    // 로그인 버튼을 클릭하면 동작하는 메서드
    public void loginProc() {
        String id = idFld.getText();
        String password = pwFld.getText();

        if (id.isEmpty() || password.isEmpty()) {
            // 아이디 또는 비밀번호가 입력되지 않은 경우 경고 메시지를 표시하고 리턴
            System.out.println("아이디와 비밀번호를 입력해주세요.");
            return;
        }

        boolean loginSuccess = service.login(id, password);

        if (loginSuccess) {
            Login.setLoggedIn(true);
            Login.setId(id);
            System.out.println("로그인 성공");
            Stage currentStage = (Stage) idFld.getScene().getWindow();
            currentStage.close();
            Login.setLoggedIn(true);
            if (Login.getId() != null && Login.getId().equals("admin")) {
                if (opener != null) {
                    opener.setOpener(opener);
                    opener.adminOpen();
                } else {
                    opener = new Opener();
                    opener.adminOpen();
                }
            } else {
                if (opener != null) {
                	opener.setOpener(opener);
        			MemberDAO dao = new MemberDAO();
        			dao.loginSucceed(Login.getId());
                    opener.mainOpen();    
                } else {
                	System.out.println(opener + "??");
                    opener = new Opener();
                    opener.mainOpen();
                }
            }
        } else {
            System.out.println("아이디 또는 비밀번호가 올바르지 않습니다.");

        }
    }

    // 취소버튼을 클릭하면 동작하는 메서드
    public void cancelProc() {
        Stage currentStage = (Stage) idFld.getScene().getWindow();
        currentStage.close();
        if (opener != null) {
            opener.mainOpen();
        } else {
            opener = new Opener();
            opener.setPrimaryStage(currentStage); 
            opener.mainOpen();
        }
    }
    


    
    
    
    
    
//    @FXML
//    private void faqImageClicked(MouseEvent event) {
//    	loadFAQ();
//    }
//    
//    private void loadFAQ() {
//    	try {
//    		FXMLLoader loader = new FXMLLoader(getClass().getResource("faq.fxml"));
//            Parent faqView = loader.load();
//            contentStackPane.getChildren().clear();
//            contentStackPane.getChildren().add(faqView);
//    	} catch (IOException e) {
//    		e.printStackTrace();
//    	}
//    }
    
 // 회원가입 버튼을 클릭하면 동작하는 메서드
    @FXML
    public void regProc(ActionEvent event) {
    	loadRegister();
    }

	  private void loadRegister() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("membership.fxml"));
	        Parent registerView = loader.load();
	        RegController con = loader.getController();

	        if(Login.getId() != null && Login.getId().equals("admin")) {
            	System.out.println(adminCon);
            	adminCon.inReg(con, registerView);
            } else {
            	menuCon.inReg(con, registerView);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
}
    
}

//try {
//FXMLLoader loader = new FXMLLoader(getClass().getResource("membership.fxml"));
//Parent register = loader.load();
//RegController controller = loader.getController();
//// 여기서 필요한 추가적인 초기화 작업 수행
//
//// 예시: register에 어떤 작업을 수행하거나 컨트롤러에 추가적인 설정을 할 경우
//// controller.setOpener(opener);
//
//// 회원가입 창을 새로운 Stage로 표시
//Stage registerStage = new Stage();
//registerStage.setTitle("회원가입");
//registerStage.setScene(new Scene(register));
//registerStage.show();
//
//// 현재 창을 닫기 위해 Stage를 가져옴
//Stage currentStage = (Stage) idFld.getScene().getWindow();
//currentStage.close();
//
//} catch (Exception e) {
//e.printStackTrace();
//}