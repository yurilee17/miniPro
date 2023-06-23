package order;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import equipment.CommonService;
import equipment.Opener;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import product.ProductDTO;
import product.ProductOptionDTO;

public class OrderManagerController implements Initializable {

	private Opener opener;

	private String currentSize, currentColor;

	private OrderInfoDAO infoDao;
	private OrderOptionDAO otDao;
	private OrderManagerDAO managerDao;
	private OrderManagerService service;

	// 테이블
	@FXML
	private TableView<OrderManagerDTO> orders;

	// 칼럼 값들
	@FXML
	private TableColumn<OrderManagerDTO, Integer> ornumCol;
	@FXML
	private TableColumn<OrderManagerDTO, String> idCol;
	@FXML
	private TableColumn<OrderManagerDTO, String> ordateCol;
	@FXML
	private TableColumn<OrderManagerDTO, Integer> totalPriceCol;
	@FXML
	private TableColumn<OrderManagerDTO, String> codeCol;
	@FXML
	private TableColumn<OrderManagerDTO, String> ornameCol;
	@FXML
	private TableColumn<OrderManagerDTO, String> colorCol;
	@FXML
	private TableColumn<OrderManagerDTO, String> pdsizeCol;
	@FXML
	private TableColumn<OrderManagerDTO, Integer> quantityCol;
	@FXML
	private TableColumn<OrderManagerDTO, String> statusCol;

	// 정보
	@FXML
	private Label inputOrnum;
	@FXML
	private Label inputOrname;
	@FXML
	private Label inputColor;
	@FXML
	private Label inputPdsize;
	@FXML
	private Label inputCode;
	@FXML
	private Label inputQuantity;
	@FXML
	private Label inputUnitPrice;
	@FXML
	private Label inputId;
	@FXML
	private Label inputOrdate;
	@FXML
	private Label inputTotalPrice;
	@FXML
	private Label inputPayment;
	@FXML
	private Label inputRecipient;
	@FXML
	private Label inputPhoneNum;
	@FXML
	private Label inputMemo;
	@FXML
	private Label inputOradd;
	@FXML
	private Label inputTextMemo;
	@FXML
	private ComboBox<String> status;

	@FXML
	private Button updateStatus;
	@FXML
	private Button updateDelivery;

	public void setOpener(Opener opener) {
		this.opener = opener;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		infoDao = new OrderInfoDAO();
		otDao = new OrderOptionDAO();
		managerDao = new OrderManagerDAO();

		service = new OrderManagerService();

		// orders(몇개만 나타냄)
		ornumCol.setCellValueFactory(new PropertyValueFactory<OrderManagerDTO, Integer>("ornum"));
		idCol.setCellValueFactory(new PropertyValueFactory<OrderManagerDTO, String>("id"));
		ordateCol.setCellValueFactory(new PropertyValueFactory<OrderManagerDTO, String>("ordate"));
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<OrderManagerDTO, Integer>("totalPrice"));
		codeCol.setCellValueFactory(new PropertyValueFactory<OrderManagerDTO, String>("pdCode"));
		ornameCol.setCellValueFactory(new PropertyValueFactory<OrderManagerDTO, String>("orname"));
		colorCol.setCellValueFactory(new PropertyValueFactory<OrderManagerDTO, String>("color"));
		pdsizeCol.setCellValueFactory(new PropertyValueFactory<OrderManagerDTO, String>("pdsize"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<OrderManagerDTO, Integer>("quantity"));
		statusCol.setCellValueFactory(new PropertyValueFactory<OrderManagerDTO, String>("status"));

		setupOrdersTable();

	}

	// X버튼 선택 시 창 종료
	public void cancel() {
		opener.adminOpen();
	}

	// orders DB의 값 나타내기
	private void setupOrdersTable() {

		ArrayList<OrderManagerDTO> setData = managerDao.selectAll();
		for (OrderManagerDTO info : setData) {
			orders.getItems().add(info);
		}

	}
	
	// orders 선택 시 값 가져오기
	@FXML
	void rowClickedProduct(MouseEvent event) {
		OrderManagerDTO clickeOrder = orders.getSelectionModel().getSelectedItem();

		if (clickeOrder != null) {
			int num = Integer.valueOf(clickeOrder.getOrnum());
			OrderInfoDTO infoDto = infoDao.selectOne(num);
			OrderOptionDTO optionDto = otDao.selectOrderMana(num, clickeOrder.getPdCode(), clickeOrder.getColor(),
					clickeOrder.getPdsize());

			inputOrnum.setText(String.valueOf(clickeOrder.getOrnum()));
			inputOrname.setText(String.valueOf(clickeOrder.getOrname()));
			inputColor.setText(String.valueOf(clickeOrder.getColor()));
			inputPdsize.setText(String.valueOf(clickeOrder.getPdsize()));
			inputCode.setText(String.valueOf(clickeOrder.getPdCode()));
			inputQuantity.setText(String.valueOf(clickeOrder.getQuantity()));
			inputId.setText(String.valueOf(clickeOrder.getId()));
			inputOrdate.setText(String.valueOf(clickeOrder.getOrdate()));
			inputTotalPrice.setText(String.valueOf(clickeOrder.getTotalPrice()));
			status.setValue(String.valueOf(clickeOrder.getStatus()));

			inputRecipient.setText(infoDto.getRecipient());
			inputPhoneNum.setText(infoDto.getPhoneNum());
			inputOradd.setText(infoDto.getOradd());
			inputMemo.setText(infoDto.getMemo());
			inputPayment.setText(infoDto.getPayment());

			inputUnitPrice.setText("" + optionDto.getUnitPrice());
			inputTextMemo.setText(optionDto.getText());

			if (status.getValue().equals("배송준비중")) {
				statusSetItems1("배송준비중");
			} else if (status.getValue().equals("배송중")) {
				statusSetItems1("배송중");
			} else if (status.getValue().equals("교환요청")) {
				statusSetItems2("교환승인", "교환불가");
			} else if (status.getValue().equals("교환승인")) {
				statusSetItems2("교환확정", "교환불가");
			} else if (status.getValue().equals("교환확정")) {
				statusSetItems1("교환확정");
			} else if (status.getValue().equals("환불요청")) {
				statusSetItems2("환불승인", "환불불가");
			} else if (status.getValue().equals("환불승인")) {
				statusSetItems1("환불확정");
			}

		}

	}

	// 1개의 콤보 박스 값
	public void statusSetItems1(String status1) {
		// 콤보 박스 초기화
		ObservableList<String> items = FXCollections.observableArrayList();
		items.add(status1);

		status.setItems(items);
	}

	// 2개의 콤보 박스 값
	public void statusSetItems2(String status1, String status2) {
		// 콤보 박스 초기화
		ObservableList<String> items = FXCollections.observableArrayList();
		items.add(status1);
		items.add(status2);

		status.setItems(items);
	}

	// 상태값 수동 변경
	public void updateStatus() {
		OrderOptionDTO dto = new OrderOptionDTO();
		dto.setOrnum(Integer.valueOf(inputOrnum.getText()));
		dto.setPdCode(inputCode.getText());
		dto.setColor(inputColor.getText());
		dto.setPdsize(inputPdsize.getText());
		updateTable(status.getValue());

		otDao.updateStatus(status.getValue(), dto);
		updateTable(status.getValue());
		CommonService.msg("수정완료");

	}

	// 상태 배송중으로 변경(발송 버튼)
	public void updateStatusDelivery() {
		OrderOptionDTO dto = new OrderOptionDTO();
		dto.setOrnum(Integer.valueOf(inputOrnum.getText()));
		dto.setPdCode(inputCode.getText());
		dto.setColor(inputColor.getText());
		dto.setPdsize(inputPdsize.getText());

		service.updateStatusDelivery(status.getValue(), dto);
		if (status.getValue().equals("배송준비중") || status.getValue().equals("교환확정")) {
			// 테이블 값 변경
			updateTable("배송중");
			CommonService.msg("수정완료");

			PauseTransition pause = new PauseTransition(Duration.seconds(20));
			pause.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					if (otDao.getStatus(dto) == "배송중")
						updateTable("배송완료");
				}
			});

			pause.play();

			// 원래는 7일 이후지만 우리는 확인을 위해 1분 이후로 변경
			PauseTransition pause2 = new PauseTransition(Duration.seconds(60));
			pause.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (otDao.getStatus(dto) == "배송완료")
						updateTable("구매확정");
				}
			});

			pause2.play();
		}

	}

	// 테이블 값 변경
	public void updateTable(String status) {

		ObservableList<OrderManagerDTO> date = orders.getItems();

		for (OrderManagerDTO Order : date) {
			if (Order.getOrnum() == (Integer.valueOf(inputOrnum.getText()))
					&& Order.getPdCode().equals(inputCode.getText()) && Order.getColor().equals(inputColor.getText())
					&& Order.getPdsize().equals(inputPdsize.getText())) {

				Order.setStatus(status);

			}
		}

		if (date != null) {
			orders.setItems(date);
			orders.refresh();
		}

	}

}