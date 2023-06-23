package service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import equipment.CommonService;
import equipment.Opener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import order.OrderInfoDTO;
import order.OrderOptionDTO;

public class OrderDeliveryDetailController implements Initializable {
	private OrderDeliveryDetailController conODD;
	private OrderDeliveryDetailService service;
	private Opener opener;
	private int num;
	private OrderInfoDTO setting;
	private ArrayList<OrderOptionDTO> settingOption;

	public void setOpener(Opener opener) {
		this.opener = opener;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setOrderDeliveryDetailController(OrderDeliveryDetailController conODD) {
		this.conODD = conODD;
	}

	@FXML
	Label numLabel;
	@FXML
	Label dateLabel;
	@FXML
	Button deleteBT;
	@FXML
	VBox totalBox;
	@FXML
	Label recipientLabel;
	@FXML
	Label phoneNumLabel;
	@FXML
	Label oraddLabel;
	@FXML
	Label memoLabel;
	@FXML
	Label totalPriceLabel;
	@FXML
	Label totalPriceLabel2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new OrderDeliveryDetailService();
//		setting();

	}

	// < 버튼 눌렀을 때
	public void back() {
		opener.orderDeliveryOpen();
	}

	// 초기 셋팅
	public void setting() {
		Boolean bt = true;
		this.setting = service.settingInfo(num);
		this.settingOption = service.settingOption(num);

		// 번호 출력
		numLabel.setText("" + num);

		// 날짜 출력
		dateLabel.setText(setting.getOrdate());

		// 상품 추가
		for (OrderOptionDTO dto : settingOption) {
			VBox optionBox = addOptionBox(setting, dto);
			optionBox.setId(dto.getPdsize() + dto.getColor());
			totalBox.getChildren().add(optionBox);
			if (dto.getStatus().equals("배송준비중") || dto.getStatus().equals("배송중") || dto.getStatus().equals("교환요청")
					|| dto.getStatus().equals("교환승인") || dto.getStatus().equals("교환확정")
					|| dto.getStatus().equals("환불요청") || dto.getStatus().equals("환불승인"))
				bt = false;
		}

		// 내역 삭제 (배송준비중, 배송중) 예외 숨김 처리
		deleteBT.setVisible(bt);

		// 수령인 출력
		recipientLabel.setText(setting.getRecipient());

		// 휴대폰 출력
		phoneNumLabel.setText(setting.getPhoneNum());

		// 주소 출력
		ArrayList<String> addressList = new ArrayList<String>();
		String addressResult = "";

		for (int i = 0; i <= setting.getOradd().length() / 36; i++) {
			int startIndex = i * 36; // 시작하는 코드
			int endIndex = Math.min(startIndex + 36, setting.getOradd().length()); // 둘중 더 작은 값을 선택하는 코드
			addressList.add(setting.getOradd().substring(startIndex, endIndex)); // startIndex부터 endIndex-1까지의 부분 문자열을
																					// 추출
		}

		if (addressList != null) {
			for (String text : addressList) {
				addressResult = addressResult + text + "\n";
			}
		}
		oraddLabel.setText(addressResult);

		// 배송메모 출력
		memoLabel.setText(setting.getMemo());

		// 최종 금액 출력
		totalPriceLabel.setText("" + setting.getTotalPrice());
		totalPriceLabel2.setText("" + setting.getTotalPrice());
	}

	// 사용 내역 눌렀을 때
	public void clickDeleteBT() {
		// 삭제 완료 시
		if (service.clickDeleteBT(num).equals(true)) {
			back();
		}
	}

	// 상품에 나타낼 옵션 박스
	public VBox addOptionBox(OrderInfoDTO setting, OrderOptionDTO settingOption) {

		VBox optionBox = new VBox();
		optionBox.setId("optionBox");
		optionBox.setMaxWidth(670.0);
		optionBox.setPrefHeight(250.0);
		optionBox.setPrefWidth(638.0);
		optionBox.setStyle("-fx-background-color: #fff; -fx-border-color: #ddd; -fx-border-width: 1 0 0 0;");

		HBox hbox1 = new HBox();
		hbox1.setAlignment(Pos.CENTER_LEFT);
		hbox1.setPrefHeight(51.0);
		hbox1.setPrefWidth(670.0);

		Label statusLabel = new Label(settingOption.getStatus());
		if (settingOption.getStatus().equals("주문취소") || settingOption.getStatus().equals("교환요청")
				|| settingOption.getStatus().equals("교환승인") || settingOption.getStatus().equals("교환불가")
				|| settingOption.getStatus().equals("환불요청") || settingOption.getStatus().equals("환불승인")
				|| settingOption.getStatus().equals("환불불가")) {
			statusLabel.setTextFill(Color.web("#ff6347"));
		} else {
			statusLabel.setTextFill(Color.web("#575aff"));
		}

		statusLabel.setFont(new Font(14.0));
		HBox.setMargin(statusLabel, new Insets(0.0));
		hbox1.getChildren().add(statusLabel);
		hbox1.setPadding(new Insets(5.0, 0.0, 5.0, 0.0));

		HBox hbox2 = new HBox();
		hbox2.setAlignment(Pos.CENTER_LEFT);
		hbox2.setPrefHeight(31.0);
		hbox2.setPrefWidth(700.0);

		ImageView imageView = new ImageView(settingOption.getImg());
		imageView.setFitHeight(100.0);
		imageView.setFitWidth(100.0);
		imageView.setPickOnBounds(true);
		imageView.setPreserveRatio(true);
		HBox.setMargin(imageView, new Insets(0.0));
		hbox2.getChildren().add(imageView);

		VBox vbox = new VBox();
		vbox.setPrefHeight(102.0);
		vbox.setPrefWidth(592.0);

		Label label2 = new Label(settingOption.getOrname());
		label2.setFont(new Font(16.0));
		label2.setPrefHeight(20.0);
		label2.setPrefWidth(521.0);
		label2.setPadding(new Insets(5.0, 0.0, 5.0, 0.0));
		vbox.getChildren().add(label2);

		HBox hbox3 = new HBox();
		hbox3.setAlignment(Pos.CENTER_LEFT);
		hbox3.setPrefHeight(25.0);
		hbox3.setPrefWidth(513.0);

		Label label3 = new Label(settingOption.getColor());
		label3.setId("myMobile");
		label3.setAlignment(Pos.CENTER);
		label3.setTextFill(Color.web("#888888"));
		label3.setFont(new Font(14.0));
		HBox.setMargin(label3, new Insets(0.0));
		hbox3.getChildren().add(label3);

		Label label4 = new Label("|");
		label4.setAlignment(Pos.CENTER);
		label4.setPrefHeight(21.0);
		label4.setPrefWidth(25.0);
		label4.setTextFill(Color.web("#dddddd"));
		hbox3.getChildren().add(label4);

		Label label5 = new Label(settingOption.getPdsize());
		label5.setAlignment(Pos.CENTER);
		label5.setPrefHeight(18.0);
		label5.setPrefWidth(64.0);
		label5.setTextFill(Color.web("#888888"));
		label5.setFont(new Font(14.0));
		hbox3.getChildren().add(label5);

		vbox.getChildren().add(hbox3);

		HBox hbox4 = new HBox();
		hbox4.setAlignment(Pos.CENTER_LEFT);
		hbox4.setPrefHeight(25.0);
		hbox4.setPrefWidth(513.0);

		Label label6 = new Label("" + settingOption.getQuantity());
		label6.setId("myMobile1");
		label6.setAlignment(Pos.CENTER);
		label6.setTextFill(Color.web("#888888"));
		label6.setFont(new Font(14.0));
		HBox.setMargin(label6, new Insets(0.0));
		hbox4.getChildren().add(label6);

		Label label7 = new Label("개");
		label7.setAlignment(Pos.CENTER);
		label7.setPrefHeight(18.0);
		label7.setPrefWidth(18.0);
		label7.setTextFill(Color.web("#888888"));
		label7.setFont(new Font(14.0));
		hbox4.getChildren().add(label7);

		vbox.getChildren().add(hbox4);

		HBox hbox5 = new HBox();
		hbox5.setAlignment(Pos.CENTER_LEFT);
		hbox5.setPrefHeight(25.0);
		hbox5.setPrefWidth(513.0);

		Label label8 = new Label("" + settingOption.getUnitPrice());
		label8.setId("myId1");
		label8.setAlignment(Pos.CENTER);
		label8.setPrefHeight(21.0);
		label8.setPrefWidth(109.0);
		label8.setTextFill(Color.web("#0d0d0d"));
		label8.setFont(new Font(18.0));
		hbox5.getChildren().add(label8);

		Label label9 = new Label("원");
		label9.setId("myId12");
		label9.setPrefHeight(21.0);
		label9.setPrefWidth(17.0);
		label9.setTextFill(Color.web("#0d0d0d"));
		label9.setFont(new Font(18.0));
		hbox5.getChildren().add(label9);

		vbox.getChildren().add(hbox5);

		hbox2.getChildren().add(vbox);

		Label label10 = new Label("|");
		label10.setAlignment(Pos.CENTER);
		label10.setPrefHeight(21.0);
		label10.setPrefWidth(25.0);
		label10.setTextFill(Color.web("#dddddd"));
		hbox2.getChildren().add(label10);

		VBox.setMargin(hbox2, new Insets(0.0));
		hbox2.setPadding(new Insets(5.0, 0.0, 5.0, 0.0));

		HBox hbox6 = new HBox();
		hbox6.setAlignment(Pos.CENTER_LEFT);
		hbox6.setPrefHeight(31.0);
		hbox6.setPrefWidth(700.0);

		VBox.setMargin(hbox6, new Insets(0.0, 0.0, 0.0, 20.0));

		Button confirmButton = new Button("구매 확정");
		confirmButton.setId("confirmBT");
		if (settingOption.getStatus().equals("배송완료")) {
			confirmButton.setVisible(true);
		} else {
			confirmButton.setVisible(false);
		}
		confirmButton.setMnemonicParsing(false);
		confirmButton.setPrefHeight(42.0);
		confirmButton.setPrefWidth(618.0);
		confirmButton.setStyle(
				"-fx-background-color: #fff; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
		VBox.setMargin(confirmButton, new Insets(0.0, 0.0, 10.0, 0.0));

		StackPane stackPane = addGridPane(settingOption.getStatus(), settingOption);
		stackPane.setPrefHeight(40.0);
		stackPane.setPrefWidth(618.0);

		optionBox.getChildren().addAll(hbox1, hbox2, hbox6, confirmButton, stackPane);
		optionBox.setPadding(new Insets(10.0, 10.0, 0.0, 10.0));
		VBox.setMargin(optionBox, new Insets(10.0, 0.0, 20.0, 0.0));

		// "구매 확정"" 버튼 선택 시 동작
		confirmButton.setOnAction(event -> {
			Boolean result = service.clickButton("구매 확정", "구매 확정하시겠습니까?", "구매확정", "아니오", settingOption);
			if (result == true) {
				totalBox.getChildren().clear();
				setting();
			}
		});

		return optionBox;

	}

	// 상품에 나타낼 버튼
	public StackPane addGridPane(String status, OrderOptionDTO settingOption) {
		StackPane stackPane = new StackPane();
		stackPane.setPrefHeight(40.0);
		stackPane.setPrefWidth(618.0);

		// 배송준비중
		GridPane preparingGridPane = new GridPane();
		preparingGridPane.setId("preparing");

		if (status.equals("배송준비중")) {
			preparingGridPane.setVisible(true);
		} else {
			preparingGridPane.setVisible(false);
		}

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setHgrow(Priority.SOMETIMES);
		col1.setMinWidth(10.0);
		col1.setPrefWidth(100.0);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setHgrow(Priority.SOMETIMES);
		col2.setMinWidth(10.0);
		col2.setPrefWidth(100.0);
		preparingGridPane.getColumnConstraints().addAll(col1, col2);
		RowConstraints row1 = new RowConstraints();
		row1.setMinHeight(10.0);
		row1.setPrefHeight(30.0);
		row1.setVgrow(Priority.SOMETIMES);
		preparingGridPane.getRowConstraints().add(row1);

		Button cancelButton = new Button("주문 취소");
		cancelButton.setId("cancelButton");
		cancelButton.setMnemonicParsing(false);
		cancelButton.setPrefHeight(32.0);
		cancelButton.setPrefWidth(335.0);
		cancelButton.setStyle(
				"-fx-background-color: #fff; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
		GridPane.setRowIndex(cancelButton, 0);
		GridPane.setColumnIndex(cancelButton, 0);
		Button inquiryButton1 = new Button("문의 하기");
		inquiryButton1.setId("inquiryButton1");
		inquiryButton1.setMnemonicParsing(false);
		inquiryButton1.setPrefHeight(32.0);
		inquiryButton1.setPrefWidth(362.0);
		inquiryButton1.setStyle(
				"-fx-background-color: #fff; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
		GridPane.setRowIndex(inquiryButton1, 0);
		GridPane.setColumnIndex(inquiryButton1, 1);

		preparingGridPane.getChildren().addAll(cancelButton, inquiryButton1);

		// 배송중
		GridPane deliveringGridPane = new GridPane();
		deliveringGridPane.setId("delivering");

		if (status.equals("배송중")) {
			deliveringGridPane.setVisible(true);
		} else {
			deliveringGridPane.setVisible(false);
		}

		ColumnConstraints colum1 = new ColumnConstraints();
		colum1.setHgrow(Priority.SOMETIMES);
		colum1.setMinWidth(10.0);
		colum1.setPrefWidth(100.0);
		ColumnConstraints colum2 = new ColumnConstraints();
		colum2.setHgrow(Priority.SOMETIMES);
		colum2.setMinWidth(10.0);
		colum2.setPrefWidth(100.0);
		ColumnConstraints colum3 = new ColumnConstraints();
		colum3.setHgrow(Priority.SOMETIMES);
		colum3.setMinWidth(10.0);
		colum3.setPrefWidth(100.0);
		deliveringGridPane.getColumnConstraints().addAll(colum1, colum2, colum3);
		RowConstraints rowCon1 = new RowConstraints();
		rowCon1.setMinHeight(10.0);
		rowCon1.setPrefHeight(30.0);
		rowCon1.setVgrow(Priority.SOMETIMES);
		deliveringGridPane.getRowConstraints().add(rowCon1);

		Button refundButton = new Button("환불 요청");
		refundButton.setId("refundButton");
		refundButton.setMnemonicParsing(false);
		refundButton.setPrefHeight(82.0);
		refundButton.setPrefWidth(206.0);
		refundButton.setStyle(
				"-fx-background-color: #fff; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
		GridPane.setRowIndex(refundButton, 0);
		GridPane.setColumnIndex(refundButton, 1);
		Button exchangeButton = new Button("교환 요청");
		exchangeButton.setId("exchangeButton");
		exchangeButton.setMnemonicParsing(false);
		exchangeButton.setPrefHeight(82.0);
		exchangeButton.setPrefWidth(206.0);
		exchangeButton.setStyle(
				"-fx-background-color: #fff; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
		GridPane.setRowIndex(exchangeButton, 0);
		GridPane.setColumnIndex(exchangeButton, 0);
		Button inquiryButton2 = new Button("문의 하기");
		inquiryButton2.setId("inquiryButton2");
		inquiryButton2.setMnemonicParsing(false);
		inquiryButton2.setPrefHeight(82.0);
		inquiryButton2.setPrefWidth(206.0);
		inquiryButton2.setStyle(
				"-fx-background-color: #fff; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
		GridPane.setRowIndex(inquiryButton2, 0);
		GridPane.setColumnIndex(inquiryButton2, 2);

		deliveringGridPane.getChildren().addAll(refundButton, exchangeButton, inquiryButton2);

		// 배송완료
		GridPane deliveredGridPane = new GridPane();
		deliveredGridPane.setId("delivered");

		if (status.equals("배송완료")) {
			deliveredGridPane.setVisible(true);
		} else {
			deliveredGridPane.setVisible(false);
		}

		ColumnConstraints col4 = new ColumnConstraints();
		col4.setHgrow(Priority.SOMETIMES);
		col4.setMinWidth(10.0);
		col4.setPrefWidth(100.0);
		ColumnConstraints col5 = new ColumnConstraints();
		col5.setHgrow(Priority.SOMETIMES);
		col5.setMinWidth(10.0);
		col5.setPrefWidth(100.0);
		ColumnConstraints col6 = new ColumnConstraints();
		col6.setHgrow(Priority.SOMETIMES);
		col6.setMinWidth(10.0);
		col6.setPrefWidth(100.0);
		deliveredGridPane.getColumnConstraints().addAll(col4, col5, col6);
		RowConstraints row2 = new RowConstraints();
		row2.setMinHeight(10.0);
		row2.setPrefHeight(30.0);
		row2.setVgrow(Priority.SOMETIMES);
		deliveredGridPane.getRowConstraints().add(row2);

		Button refundButton2 = new Button("환불 요청");
		refundButton2.setId("refundButton2");
		refundButton2.setMnemonicParsing(false);
		refundButton2.setPrefHeight(82.0);
		refundButton2.setPrefWidth(206.0);
		refundButton2.setStyle(
				"-fx-background-color: #fff; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
		GridPane.setRowIndex(refundButton2, 0);
		GridPane.setColumnIndex(refundButton2, 1);
		Button exchangeButton2 = new Button("교환 요청");
		exchangeButton2.setId("exchangeButton2");
		exchangeButton2.setMnemonicParsing(false);
		exchangeButton2.setPrefHeight(82.0);
		exchangeButton2.setPrefWidth(206.0);
		exchangeButton2.setStyle(
				"-fx-background-color: #fff; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
		GridPane.setRowIndex(exchangeButton2, 0);
		GridPane.setColumnIndex(exchangeButton2, 0);
		Button inquiryButton3 = new Button("문의 하기");
		inquiryButton3.setId("inquiryButton3");
		inquiryButton3.setMnemonicParsing(false);
		inquiryButton3.setPrefHeight(82.0);
		inquiryButton3.setPrefWidth(206.0);
		inquiryButton3.setStyle(
				"-fx-background-color: #fff; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
		GridPane.setRowIndex(inquiryButton3, 0);
		GridPane.setColumnIndex(inquiryButton3, 2);

		deliveredGridPane.getChildren().addAll(refundButton2, exchangeButton2, inquiryButton3);

		// 구매확정
		GridPane confirmedGridPane = new GridPane();
		confirmedGridPane.setId("confirmed");

		if (status.equals("구매확정")) {
			confirmedGridPane.setVisible(true);
		} else {
			confirmedGridPane.setVisible(false);
		}

		ColumnConstraints col7 = new ColumnConstraints();
		col7.setHgrow(Priority.SOMETIMES);
		col7.setMinWidth(10.0);
		col7.setPrefWidth(100.0);
		ColumnConstraints col8 = new ColumnConstraints();
		col8.setHgrow(Priority.SOMETIMES);
		col8.setMinWidth(10.0);
		col8.setPrefWidth(100.0);
		confirmedGridPane.getColumnConstraints().addAll(col7, col8);
		RowConstraints row3 = new RowConstraints();
		row3.setMinHeight(10.0);
		row3.setPrefHeight(30.0);
		row3.setVgrow(Priority.SOMETIMES);
		confirmedGridPane.getRowConstraints().add(row3);

		// 리뷰 존재 여부
		Boolean revuewTmp = service.clickReview(setting.getOrdate(), settingOption);

		Button reviewButton = new Button("리뷰(별점)");

		if (revuewTmp == false) {
			reviewButton.setDisable(true);
		} else {
			reviewButton.setDisable(false);
		}

		reviewButton.setId("reviewButton");
		reviewButton.setMnemonicParsing(false);
		reviewButton.setPrefHeight(32.0);
		reviewButton.setPrefWidth(335.0);
		reviewButton.setStyle(
				"-fx-background-color: #fff; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
		GridPane.setRowIndex(reviewButton, 0);
		GridPane.setColumnIndex(reviewButton, 0);
		Button inquiryButton4 = new Button("문의 하기");
		inquiryButton4.setId("inquiryButton4");
		inquiryButton4.setMnemonicParsing(false);
		inquiryButton4.setPrefHeight(32.0);
		inquiryButton4.setPrefWidth(362.0);
		inquiryButton4.setStyle(
				"-fx-background-color: #fff; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
		GridPane.setRowIndex(inquiryButton4, 0);
		GridPane.setColumnIndex(inquiryButton4, 1);

		confirmedGridPane.getChildren().addAll(reviewButton, inquiryButton4);

		stackPane.getChildren().addAll(preparingGridPane, deliveringGridPane, deliveredGridPane, confirmedGridPane);

		// "주문 취소" 버튼 선택 시 동작
		cancelButton.setOnAction(event -> {
			Boolean result = service.clickButton("주문 취소", "주문의 취소하시겠습니까?", "취소하기", "아니오", settingOption);
			if (result == true) {
				totalBox.getChildren().clear();
				setting();
			}
		});

		// "교환 요청" 버튼 선택 시 동작
		exchangeButton.setOnAction(event -> {
			Boolean result = service.clickButton("교환 요청", "상품을 교환하시겠습니까?", "교환하기", "아니오", settingOption);
			if (result == true) {
				totalBox.getChildren().clear();
				setting();
			}
		});
		exchangeButton2.setOnAction(event -> {
			Boolean result = service.clickButton("교환 요청", "상품을 교환하시겠습니까?", "교환하기", "아니오", settingOption);
			if (result == true) {
				totalBox.getChildren().clear();
				setting();
			}
		});

		// "환불 요청" 버튼 선택 시 동작
		refundButton.setOnAction(event -> {
			Boolean result = service.clickButton("환불 요청", "상품을 환불하시겠습니까?", "환불하기", "아니오", settingOption);
			if (result == true) {
				totalBox.getChildren().clear();
				setting();
			}
		});
		refundButton2.setOnAction(event -> {
			Boolean result = service.clickButton("환불 요청", "상품을 환불하시겠습니까?", "환불하기", "아니오", settingOption);
			if (result == true) {
				totalBox.getChildren().clear();
				setting();
			}
		});

		// "리뷰(별점)" 버튼 선택 시 동작
		reviewButton.setOnAction(event -> {
			Boolean result = service.clickReview(setting.getOrdate(), settingOption);
			if (result == true) {
				opener.openReview(setting.getOrdate(), settingOption, conODD);

			}

		});

		return stackPane;

	}

	public void resultReview() {
		totalBox.getChildren().clear();
		setting();
	}

}
