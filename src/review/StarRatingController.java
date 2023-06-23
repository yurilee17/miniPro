package review;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import order.OrderOptionDTO;
import service.OrderDeliveryDetailController;

import java.net.URL;
import java.util.ResourceBundle;

import equipment.CommonService;
import equipment.Opener;

public class StarRatingController implements Initializable {
	private OrderDeliveryDetailController conODD;
	private double valueResult;
	private Opener opener;
	private OrderOptionDTO dto;
	private String setDate;
	private StarRatingService service;
	private Stage newStage;

	public void setOpener(Opener opener) {
		this.opener = opener;
	}

	public void setOrderDeliveryDetailController(OrderDeliveryDetailController conODD) {
		this.conODD = conODD;
	}

	public void setDto(OrderOptionDTO dto) {
		this.dto = dto;
	}

	public void setDate(String setDate) {
		this.setDate = setDate;
	}

	public void setStage(Stage newStage) {
		this.newStage = newStage;
	}

	@FXML
	private Slider ratingSlider;

	@FXML
	private ImageView starImageView1;
	@FXML
	private ImageView starImageView2;
	@FXML
	private ImageView starImageView3;
	@FXML
	private ImageView starImageView4;
	@FXML
	private ImageView starImageView5;

	@FXML
	private Label value;
	@FXML
	private Button addBT;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		service = new StarRatingService();

		// 별 이미지 생성
		Image emptyStarImage = new Image(getClass().getResourceAsStream("empty_star.png"));
		Image filledStarImage = new Image(getClass().getResourceAsStream("filled_star.png"));

		// 슬라이더 값이 변경될 때 이미지 업데이트
		ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			int rating = newValue.intValue();
			value.setText("" + rating);

			if (rating > 0 && rating <= 1) {
				starImageView1.setImage(filledStarImage);
				starImageView2.setImage(emptyStarImage);
				starImageView3.setImage(emptyStarImage);
				starImageView4.setImage(emptyStarImage);
				starImageView5.setImage(emptyStarImage);
			} else if (rating > 1 && rating <= 2) {
				starImageView1.setImage(filledStarImage);
				starImageView2.setImage(filledStarImage);
				starImageView3.setImage(emptyStarImage);
				starImageView4.setImage(emptyStarImage);
				starImageView5.setImage(emptyStarImage);
			} else if (rating > 2 && rating <= 3) {
				starImageView1.setImage(filledStarImage);
				starImageView2.setImage(filledStarImage);
				starImageView3.setImage(filledStarImage);
				starImageView4.setImage(emptyStarImage);
				starImageView5.setImage(emptyStarImage);
			} else if (rating > 3 && rating <= 4) {
				starImageView1.setImage(filledStarImage);
				starImageView2.setImage(filledStarImage);
				starImageView3.setImage(filledStarImage);
				starImageView4.setImage(filledStarImage);
				starImageView5.setImage(emptyStarImage);
			} else if (rating > 4 && rating <= 5) {
				starImageView1.setImage(filledStarImage);
				starImageView2.setImage(filledStarImage);
				starImageView3.setImage(filledStarImage);
				starImageView4.setImage(filledStarImage);
				starImageView5.setImage(filledStarImage);
			} else {
				starImageView1.setImage(emptyStarImage);
				starImageView2.setImage(emptyStarImage);
				starImageView3.setImage(emptyStarImage);
				starImageView4.setImage(emptyStarImage);
				starImageView5.setImage(emptyStarImage);
			}
		});

		// Slider에 CSS 파일 적용
		ratingSlider.getStylesheets()
				.add(StarRatingController.class.getResource("/review/starRating.css").toExternalForm());
		ratingSlider.getStyleClass().add("custom-slider");

	}

	Boolean result;

	// 버튼 선택 시
	public void clickAddBT() {
		valueResult = ratingSlider.getValue();
		result = service.clickAddBT(setDate, dto, valueResult);
		if (result) {
			newStage.close();
			conODD.resultReview();
		}

	}

}