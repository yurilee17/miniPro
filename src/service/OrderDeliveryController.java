package service;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import equipment.Opener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import order.OrderInfoDTO;
import order.OrderOptionDTO;

public class OrderDeliveryController implements Initializable {
	private OrderDeliveryService service;
	private ArrayList<OrderInfoDTO> settingInfo;
	private Opener opener;

	public void setOpener(Opener opener) {
		this.opener = opener;
	}

	@FXML
	VBox totalVox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new OrderDeliveryService();
		if (service.settingInfo().isEmpty()) {
			Label none = new Label();
			none.setText("주문 정보 없음");
			totalVox.getChildren().add(none);
			totalVox.setAlignment(Pos.CENTER);
		} else {
			this.settingInfo = service.settingInfo();

		}

		setting();
	}

	// < 버튼 눌렀을 때
	public void back() {
		opener.myPageOpen();
	}

	public void setting() {
		if (service.settingInfo().isEmpty()) {
		} else {
			for (OrderInfoDTO dto : settingInfo) {
				ArrayList<OrderOptionDTO> settingOption = service.settingOption(dto.getOrnum());
				VBox numVbox = addNumBox(dto.getOrnum(), dto.getOrdate(), settingOption);
				numVbox.setId("" + dto.getOrnum());
				totalVox.getChildren().add(numVbox);
				VBox.setMargin(numVbox, new Insets(0, 0, 20.0, 0));
			}

		}

	}

	public VBox addNumBox(int num, String date, ArrayList<OrderOptionDTO> settingOption) {
		VBox numVbox = new VBox();
		numVbox.setPrefHeight(237.0);
		numVbox.setPrefWidth(668.0);
		numVbox.setStyle("-fx-background-color: #fff;");

		HBox dateLabelHBox = new HBox();
		dateLabelHBox.setPrefHeight(51.0);
		dateLabelHBox.setPrefWidth(671.0);

		HBox dateLabelInnerHBox = new HBox();
		dateLabelInnerHBox.setAlignment(Pos.CENTER_LEFT);
		dateLabelInnerHBox.setPrefHeight(27.0);
		dateLabelInnerHBox.setPrefWidth(480.0);

		Label dateLabel = new Label(date);
		dateLabel.setPrefHeight(18.0);
		dateLabel.setPrefWidth(102.0);
		dateLabel.setFont(new Font(14.0));
		HBox.setMargin(dateLabel, new Insets(0, 0, 0, 5.0));

		dateLabelInnerHBox.getChildren().add(dateLabel);

		HBox orderDetailsHBox = new HBox();
		orderDetailsHBox.setAlignment(Pos.CENTER_RIGHT);
		orderDetailsHBox.setPrefHeight(100.0);
		orderDetailsHBox.setPrefWidth(200.0);

		// num값 저장
		Label numData = new Label("" + num);
		numData.setVisible(false);

		Label orderDetailsLabel = new Label("주문상세");
		orderDetailsLabel.setAlignment(Pos.CENTER_RIGHT);
		orderDetailsLabel.setMaxHeight(Double.MAX_VALUE);
		orderDetailsLabel.setPrefHeight(55.0);
		orderDetailsLabel.setPrefWidth(59.0);
		orderDetailsLabel.setFont(new Font(14.0));

		Label arrowLabel = new Label(">");
		arrowLabel.setAlignment(Pos.CENTER_RIGHT);
		arrowLabel.setPrefHeight(90.0);
		arrowLabel.setPrefWidth(19.0);
		arrowLabel.setTextFill(Color.web("#777777"));
		arrowLabel.setFont(new Font(14.0));
		HBox.setMargin(arrowLabel, new Insets(0, 5.0, 0, 0));

		orderDetailsHBox.getChildren().addAll(orderDetailsLabel, numData, arrowLabel);
		dateLabelHBox.getChildren().addAll(dateLabelInnerHBox, orderDetailsHBox);
		numVbox.getChildren().add(dateLabelHBox);

		// 옵션값 만큼 추가
		for (OrderOptionDTO dto : settingOption) {
			VBox optionVbox = addoptionVbox(dto);
			optionVbox.setId(dto.getPdsize() + dto.getColor());

			numVbox.getChildren().add(optionVbox);
			VBox.setMargin(optionVbox, new Insets(10.0, 0, 0, 0));
		}

		numVbox.setPadding(new Insets(15.0));

		// 주문상세 눌렀을 때 동작
		orderDetailsLabel.setOnMouseClicked(event -> {
			int tmpNum = Integer.parseInt(numData.getText());
			opener.orderDeliveryDetailOpen(tmpNum);
		});
		// > 눌렀을 때 동작
		arrowLabel.setOnMouseClicked(event -> {
			int tmpNum = Integer.parseInt(numData.getText());
			opener.orderDeliveryDetailOpen(tmpNum);

		});

		return numVbox;

	}

	public VBox addoptionVbox(OrderOptionDTO dto) {

		VBox optionVbox = new VBox();
		optionVbox.setMaxWidth(670.0);
		optionVbox.setPrefHeight(100.0);
		optionVbox.setPrefWidth(638.0);
		optionVbox.setStyle("-fx-background-color: #fff; -fx-border-color: #ddd; -fx-border-width: 1 0 0 0;");

		HBox statusLabelHBox = new HBox();
		statusLabelHBox.setAlignment(Pos.CENTER_LEFT);
		statusLabelHBox.setPrefHeight(51.0);
		statusLabelHBox.setPrefWidth(670.0);

		Label statusLabel = new Label(dto.getStatus());
		if (dto.getStatus().equals("주문취소") || dto.getStatus().equals("교환요청") || dto.getStatus().equals("교환승인")
				|| dto.getStatus().equals("교환불가") || dto.getStatus().equals("환불요청") || dto.getStatus().equals("환불승인")
				|| dto.getStatus().equals("환불불가")) {
			statusLabel.setTextFill(Color.web("#ff6347"));
		} else {
			statusLabel.setTextFill(Color.web("#575aff"));
		}
		statusLabel.setPrefHeight(21.0);
		statusLabel.setPrefWidth(109.0);
		statusLabel.setFont(new Font(14.0));
		HBox.setMargin(statusLabel, new Insets(0));

		statusLabelHBox.getChildren().add(statusLabel);
		statusLabelHBox.setPadding(new Insets(5.0, 0, 5.0, 0));
		VBox.setMargin(statusLabelHBox, new Insets(0, 0, 10.0, 0));

		HBox imageAndLabelHBox = new HBox();
		imageAndLabelHBox.setAlignment(Pos.CENTER_LEFT);
		imageAndLabelHBox.setPrefHeight(31.0);
		imageAndLabelHBox.setPrefWidth(700.0);

		ImageView img = new ImageView(dto.getImg());
		img.setFitHeight(92.0);
		img.setFitWidth(100.0);
		img.setPickOnBounds(true);
		HBox.setMargin(img, new Insets(0));

		VBox nameLabelVBox = new VBox();
		nameLabelVBox.setPrefHeight(102.0);
		nameLabelVBox.setPrefWidth(592.0);

		Label nameLabel = new Label(dto.getOrname());
		nameLabel.setPrefHeight(20.0);
		nameLabel.setPrefWidth(521.0);
		nameLabel.setFont(new Font(16.0));
		nameLabel.setPadding(new Insets(5.0, 0, 5.0, 0));

		HBox priceAndColorAndSizeHBox = new HBox();
		priceAndColorAndSizeHBox.setAlignment(Pos.CENTER_LEFT);
		priceAndColorAndSizeHBox.setPrefHeight(40.0);
		priceAndColorAndSizeHBox.setPrefWidth(513.0);

		Label priceLabel = new Label("" + dto.getUnitPrice());
		priceLabel.setAlignment(Pos.CENTER);
		priceLabel.setPrefHeight(21.0);
		priceLabel.setPrefWidth(109.0);
		priceLabel.setTextFill(Color.web("#0d0d0d"));
		priceLabel.setFont(new Font(14.0));

		Label wonLabel = new Label("원");
		wonLabel.setPrefHeight(21.0);
		wonLabel.setPrefWidth(17.0);
		wonLabel.setTextFill(Color.web("#0d0d0d"));
		wonLabel.setFont(new Font(14.0));

		Label pipeLabel1 = new Label("|");
		pipeLabel1.setAlignment(Pos.CENTER);
		pipeLabel1.setPrefHeight(21.0);
		pipeLabel1.setPrefWidth(25.0);
		pipeLabel1.setTextFill(Color.web("#dddddd"));

		Label colorLabel = new Label(dto.getColor());
		colorLabel.setAlignment(Pos.CENTER);
		colorLabel.setPrefHeight(21.0);
		colorLabel.setPrefWidth(116.0);
		colorLabel.setTextFill(Color.web("#888888"));
		HBox.setMargin(colorLabel, new Insets(0, 0, 0, 30.0));
		colorLabel.setFont(new Font(14.0));

		Label pipeLabel2 = new Label("|");
		pipeLabel2.setAlignment(Pos.CENTER);
		pipeLabel2.setPrefHeight(21.0);
		pipeLabel2.setPrefWidth(25.0);
		pipeLabel2.setTextFill(Color.web("#dddddd"));

		Label sizeLabel = new Label(dto.getPdsize());
		sizeLabel.setAlignment(Pos.CENTER);
		sizeLabel.setPrefHeight(18.0);
		sizeLabel.setPrefWidth(64.0);
		sizeLabel.setTextFill(Color.web("#888888"));
		sizeLabel.setFont(new Font(14.0));

		priceAndColorAndSizeHBox.getChildren().addAll(priceLabel, wonLabel, pipeLabel1, colorLabel, pipeLabel2,
				sizeLabel);
		nameLabelVBox.getChildren().addAll(nameLabel, priceAndColorAndSizeHBox);
		HBox.setMargin(nameLabelVBox, new Insets(0, 0, 0, 10.0));

		imageAndLabelHBox.getChildren().addAll(img, nameLabelVBox);
		VBox.setMargin(imageAndLabelHBox, new Insets(0));
		imageAndLabelHBox.setPadding(new Insets(5.0, 0, 5.0, 0));

		optionVbox.getChildren().addAll(statusLabelHBox, imageAndLabelHBox);
		optionVbox.setPadding(new Insets(10.0, 10.0, 0, 10.0));

		return optionVbox;
	}

}
