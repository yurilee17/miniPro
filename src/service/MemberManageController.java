package service;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import equipment.Opener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import member.MemberDAO;
import member.MemberDTO;

public class MemberManageController implements Initializable{
	
	private Opener opener;
	private MemberDAO dao;
	private MemberManageService service;
	
    @FXML
    private TableView<MemberDTO> members;
    
    @FXML
    private TableColumn<MemberDTO, String> mbIdCol;
    
    @FXML
    private TableColumn<MemberDTO, String> mbPwCol;
    
    @FXML
    private TableColumn<MemberDTO, String> mbNameCol;
    
    @FXML
    private TableColumn<MemberDTO, String> mbNumCol;
    
    @FXML
    private TableColumn<MemberDTO, Integer> mbAmountCol;
    
    @FXML
    private TableColumn<MemberDTO, String> mbAddressCol;
    
    @FXML
    private TextField inputMbid;
    
    @FXML
    private TextField inputMbpw;
    
    @FXML
    private TextField inputMbname;
    
    @FXML
    private TextField inputMbnum;
    
    @FXML
    private TextField inputMbamount;
    
    @FXML
    private TextField inputMbaddress;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dao = new MemberDAO();
		service = new MemberManageService();
		
		mbIdCol.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("id"));
		mbPwCol.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("pw"));
		mbNameCol.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("name"));
		mbNumCol.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("num"));
		mbAmountCol.setCellValueFactory(new PropertyValueFactory<MemberDTO, Integer>("amount"));
		mbAddressCol.setCellValueFactory(new PropertyValueFactory<MemberDTO, String>("homeaddress"));
		setupTable();
	}
	
    public void setOpener(Opener opener) {
        this.opener = opener;
    }

	// DB의 값 나타내기
    private void setupTable(){
    	ArrayList<MemberDTO> setData = dao.selectAll();
    	for (MemberDTO product : setData) {
    		members.getItems().add(product);
        }
    } 
    
    
 // 입력한 값 추가하기
    @FXML
    public void insertProc(ActionEvent event) {
    	
        ObservableList<MemberDTO> currentData = members.getItems();
        String id = inputMbid.getText();   
        String pw = inputMbpw.getText();
        String name = inputMbname.getText();
        String num = inputMbnum.getText();
        String amounttext = inputMbamount.getText(); // TextField에서 텍스트 가져오기
        int amount = Integer.parseInt(amounttext); // 텍스트를 정수로 변환
        inputMbamount.setText(String.valueOf(amount)); // 변환된 정수 값을 다시 TextField에 설정
        String address = inputMbaddress.getText();

        
        MemberDTO dto = service.add(currentData, id, pw, name, num, amount, address);
        
        if (dto != null){
        	members.getItems().add(dto);
        	members.refresh();
        }

    }
    
    
 // 입력한 값 수정하기
    @FXML
    public void updateProc(ActionEvent event) {
        ObservableList<MemberDTO> currentData = members.getItems();
        String id = inputMbid.getText();   
        String pw = inputMbpw.getText();
        String name = inputMbname.getText();
        String num = inputMbnum.getText();
        String amounttext = inputMbamount.getText(); // TextField에서 텍스트 가져오기
            int amount = Integer.parseInt(amounttext); // 텍스트를 정수로 변환
            inputMbamount.setText(String.valueOf(amount)); // 변환된 정수 값을 다시 TextField에 설정
        String address = inputMbaddress.getText();
 
        ObservableList<MemberDTO> updateData = service.update(currentData, id, pw, name, num, amount, address);
        
        if (updateData != null){
        	members.setItems(updateData);
        	members.refresh();
        }
    }
    
    // 입력한 값 삭제하기
    @FXML
    void deleteProc(ActionEvent event) {
        ObservableList<MemberDTO> currentData = members.getItems();
        String id = inputMbid.getText();

        ObservableList<MemberDTO> updateData = service.delete(currentData, id);
        
        if (updateData != null){
	        members.setItems(updateData);
	        members.refresh();
        }
     
    }

    // 회원정보 선택 시 값 가져오기
    @FXML
    void rowClicked(MouseEvent event) {
    	MemberDTO clickeProduct = members.getSelectionModel().getSelectedItem();
        if (clickeProduct != null) {
    	inputMbid.setText(String.valueOf(clickeProduct.getId()));
    	inputMbpw.setText(String.valueOf(clickeProduct.getNum()));
	    inputMbname.setText(String.valueOf(clickeProduct.getName()));
	    inputMbnum.setText(String.valueOf(clickeProduct.getNum()));
	    inputMbamount.setText(String.valueOf(clickeProduct.getAmount()));
	    inputMbaddress.setText(String.valueOf(clickeProduct.getHomeaddress()));

        }
    }
	
    
    // X버튼 선택 시 창 종료
    public void cancel(MouseEvent event) {
    	opener.adminOpen();
	}
	
}
