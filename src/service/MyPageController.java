package service;

import java.net.URL;
import java.util.ResourceBundle;

import equipment.Opener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

public class MyPageController implements Initializable{
	
	private MyPageService service;
	
	private MenuController con;
	private Opener opener;
	
	@FXML
    private Label myName;
	@FXML
	private Label myId;
	@FXML
	private Label myMobile;
	@FXML
	private TextField myAdd;
	@FXML
	private Button orderDelivery;
	@FXML
	private Button qnA;
	@FXML
	private Button notice;
	@FXML
	private Button fQA;
	@FXML
	private Button withdrawal;
	@FXML
	private ToggleButton updateAdd;
	
	// 버튼 마우스 드래그 설정
	@FXML
	private void onMouseEnteredOrderDelivery(MouseEvent event) {
	    orderDelivery.setStyle("-fx-background-color: #f4f4f4;");
	}
		
	@FXML
	private void onMouseExitedOrderDelivery(MouseEvent event) {
	    orderDelivery.setStyle("-fx-background-color: #fff;");
	}
	
	@FXML
	private void onMouseEnteredQnA(MouseEvent event) {
		qnA.setStyle("-fx-background-color: #f4f4f4;");
	}
		
	@FXML
	private void onMouseExitedQnA(MouseEvent event) {
		qnA.setStyle("-fx-background-color: #fff;");
	}
	
	@FXML
	private void onMouseEnteredNotice(MouseEvent event) {
		notice.setStyle("-fx-background-color: #f4f4f4;");
	}
		
	@FXML
	private void onMouseExitedNotice(MouseEvent event) {
		notice.setStyle("-fx-background-color: #fff;");
	}
	
	@FXML
	private void onMouseEnteredFQA(MouseEvent event) {
		fQA.setStyle("-fx-background-color: #f4f4f4;");
	}
		
	@FXML
	private void onMouseExitedFQA(MouseEvent event) {
		fQA.setStyle("-fx-background-color: #fff;");
	}
	
	@FXML
	private void onMouseEnteredWithdrawal(MouseEvent event) {
		withdrawal.setStyle("-fx-background-color: #f4f4f4;");
	}
		
	@FXML
	private void onMouseExitedWithdrawal(MouseEvent event) {
		withdrawal.setStyle("-fx-background-color: #fff;");
	}
	
	
	public void setMenuCon(MenuController con) {
	      this.con = con;
	  }
	
	
	
	
	
	
	public void setOpener(Opener opener) {
	      this.opener = opener;
	  }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new MyPageService();
		
	}
	
	// X버튼 선택 시 창 종료
    public void cancel() {
    	opener.mainOpen();
	}
	
    // 초기 셋팅
	public void setting() {
		// 이름 가져오기
		myName.setText(Login.getName());
		
		// ID 가져오기
		myId.setText(Login.getId());
		
		// 번호 가져오기
		myMobile.setText(Login.getNum());
		
		// 주소 가져오기
		myAdd.setText(Login.getHomeaddress());
		
		// 
		
	}

	// 주소 수정 버튼 동작
	public void updateHomeaddress(ActionEvent event) {
		boolean isSelected = updateAdd.isSelected();
		if(isSelected == true) {
			myAdd.setDisable(false);
			myAdd.requestFocus();
			updateAdd.setText("수정 하기");
			updateAdd.setStyle("-fx-background-color: #f4f4f4; -fx-background-radius: 30; -fx-border-color: #ddd; -fx-border-radius: 30;");
		}else if(isSelected == false){
			System.out.println(myAdd.getText());
			String text = "";
			
			// 주소 수정 동작 필요(주소 미 입력 시 버튼 다시 변경)
			if(myAdd.getText().equals("")) {	
				service.updateHomeaddress(text);
				updateAdd.setSelected(true);
			}else {
				service.updateHomeaddress(myAdd.getText());
				myAdd.setDisable(true);
				updateAdd.setText("주소 수정");
				updateAdd.setStyle("-fx-background-color: #fff; -fx-background-radius: 30; -fx-border-color: #ddd; -fx-border-radius: 30;");
			}
		}
	}
	
	// 주문&배송 눌렀을 때
	public void clickOrderDelivery() {
		opener.orderDeliveryOpen();
	}
	
	// 상품별 문의 내역 눌렀을 때
	public void clickQnA() {
		// 미구현
		System.out.println("미구현");
	}
	
	// NOTICE 눌렀을 때
	public void clickNotice() {
		opener.mainConOpen("notice");
	}
	
	// FAQ 눌렀을 때
	public void clickFQA() {
		opener.mainConOpen("FAQ");
	}
	
	// 탈퇴하기 눌렀을 때
	public void clickWithdrawal() {
		boolean result = service.withdrawal();
		
		// 탈퇴 완료 되었을 때 메인 화면을 이동
		if(result == false)
			opener.mainOpen();
	}
	

}