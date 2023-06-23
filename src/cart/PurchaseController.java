package cart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import member.MemberDTO;
import service.AdminController;
import service.Login;
import service.MenuController;

public class PurchaseController implements Initializable {
	@FXML
	private CheckBox getInfo;

	@FXML
	private TextField inputName;
	@FXML
	private TextField inputNum;
	@FXML
	private TextField inputAaddress;
	@FXML
	private TextArea inputMemo;
	@FXML
	private ToggleButton naverBT;
	@FXML
	private ToggleButton kakaoBT;
	@FXML
	private ToggleButton bankTransferBT;
	@FXML
	private ToggleButton cardBT;
	@FXML
	private Label totalPriceLabel;
	@FXML
	private Button orderBT;

	private ArrayList<BasketDTO> basketItems;
	private Stage stage;
	private PurchaseService service;
	private BasketDAO basketDAO;
	private MenuController menuCon;
	private AdminController adminCon;
	private MemberDTO memberDTO;
	
	public void setStage (Stage stage) {
		this.stage = stage;
	}
	public void setInfo (ArrayList<BasketDTO> basketItems) {
		this.basketItems = basketItems;
	}

	public void setMenuCon(MenuController con) {
		this.menuCon = menuCon;
	}

	public void setAdminCon(AdminController con2) {
		this.adminCon = adminCon;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new PurchaseService();

	}
	
	public void setting() {
		ToggleGroup toggleGroup = new ToggleGroup();
		naverBT.setToggleGroup(toggleGroup);
		kakaoBT.setToggleGroup(toggleGroup);
		bankTransferBT.setToggleGroup(toggleGroup);
		cardBT.setToggleGroup(toggleGroup);
       
		
		int totalPrice = 0;
		for(BasketDTO basket :basketItems) {
			totalPrice = totalPrice + basket.getPrice();
		}
		totalPriceLabel.setText("" + totalPrice);
	}

	public void changeInfo() {
	    boolean isSelected = getInfo.isSelected();
	    // 선택된 경우
	    if (isSelected) {
	    	inputName.setText(Login.getName());
	    	inputNum.setText(Login.getNum());
	    	inputAaddress.setText(Login.getHomeaddress());
	     
	    	inputName.setDisable(true);
	    	inputNum.setDisable(true);
	    	inputAaddress.setDisable(true);
	    // 선택 해제된 경우
	    } else {
	    	inputName.setDisable(false);
	    	inputNum.setDisable(false);
	    	inputAaddress.setDisable(false);
	    	
	    	inputName.setText("");
	    	inputNum.setText("");
	    	inputAaddress.setText("");
	    }

	}
	
	public void clickOrderBT() {
		Boolean result = service.clickOrderBT(basketItems, inputName.getText(), inputNum.getText(),inputAaddress.getText(), inputMemo.getText(), naverBT.isSelected(), kakaoBT.isSelected(), bankTransferBT.isSelected(), cardBT.isSelected());
		if(result && stage != null)
			stage.close();
	}
	
	public void setMemberDTO(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
	}
	
}