package equipment;

import java.util.Optional;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CommonService {
	
	 public static void msg(String contextText) {
	        showInformationAlert("알림", contextText);
	    }

	    public static void windowClose(Stage stage) {
	        stage.close();
	    }

	    public static void windowClose(Parent form) {
	        Stage stage = (Stage) form.getScene().getWindow();
	        stage.close();
	    }

	    public static void showInformationAlert(String title, String message) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        // 버튼 css
	        DialogPane dialogPane = alert.getDialogPane();
	        dialogPane.getStylesheets().add(CommonService.class.getResource("/equipment/AlertStyle.css").toExternalForm()); // CSS 파일 경로
	        
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);

	        // 알림창이 메인 창보다 더 위에 뜨도록 설정
	        alert.initModality(Modality.APPLICATION_MODAL);
	        alert.initStyle(StageStyle.UTILITY);

	        // 알림창 표시
	        alert.showAndWait();
	    }
	    
	    
	    public static void SpinnerAlert(String title, String message) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        // 버튼 css
	        DialogPane dialogPane = alert.getDialogPane();
	        dialogPane.getStylesheets().add(CommonService.class.getResource("/equipment/AlertStyle.css").toExternalForm()); // CSS 파일 경로
	        
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);

	        // 알림창이 메인 창보다 더 위에 뜨도록 설정
	        alert.initModality(Modality.APPLICATION_MODAL);
	        alert.initStyle(StageStyle.UTILITY);

	        // 알림창 표시
	        alert.show();
	    }

	    //확인, 취소를 위한 버튼
	    public static Optional<ButtonType> showConfirmationAlert(String title, String message, ButtonType yesButton, ButtonType noButton) {
	        Alert alert = new Alert(AlertType.CONFIRMATION);

	        // 버튼 css
	        DialogPane dialogPane = alert.getDialogPane();
	        dialogPane.getStylesheets().add(CommonService.class.getResource("/equipment/AlertStyle.css").toExternalForm()); // CSS 파일 경로
	        
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        
	        alert.getButtonTypes().setAll(yesButton, noButton);

	        // 알림창이 메인 창보다 더 위에 뜨도록 설정
	        alert.initModality(Modality.APPLICATION_MODAL);
	        alert.initStyle(StageStyle.UTILITY);

	        // 알림창 표시
	        Optional<ButtonType> result = alert.showAndWait();
	        return result;
	    }
	    
	    // 입력 값 받아오기 위한 알림 창
	    public static Optional<String> showTextInAlert(String title, String headerText,String ContentText) {
	    	TextInputDialog dialog = new TextInputDialog();
			   
			   // 버튼 css
		       DialogPane dialogPane = dialog.getDialogPane();
		       dialogPane.getStylesheets().add(CommonService.class.getResource("/equipment/AlertStyle.css").toExternalForm()); // CSS 파일 경로
		        
			   dialog.setTitle(title);
			   dialog.setHeaderText(headerText);
			   dialog.setContentText(ContentText);

			   // 비밀번호 확인창 표시 및 값 받아오기
			   Optional<String> resultPw = dialog.showAndWait();
			  
			return resultPw;
	    }
	    public static boolean showConfirmationDialog(String title, String message) {
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);

	        // 알림창이 메인 창보다 더 위에 뜨도록 설정
	        alert.initModality(Modality.APPLICATION_MODAL);
	        alert.initStyle(StageStyle.UTILITY);

	        // 버튼 설정
	        ButtonType yes = new ButtonType("예");
	        ButtonType no = new ButtonType("아니오");
	        alert.getButtonTypes().setAll(yes, no);

	        // 알림창 표시 및 사용자 응답 기다리기
	        Optional<ButtonType> result = alert.showAndWait();
	        return result.orElse(ButtonType.NO) == yes;
	    }
}
