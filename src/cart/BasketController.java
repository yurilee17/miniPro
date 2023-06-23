package cart;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import equipment.CommonService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import notice.NoticeDTO;
import product.ProductOptionDAO;
import product.ProductOptionDTO;
import member.MemberDAO;
import member.MemberDTO;
import service.AdminController;
import service.Login;
import service.MenuController;

public class BasketController implements Initializable {

	@FXML
	private Label totalPriceLabel;
	
	@FXML
	private HBox priceHBox;
	
	@FXML
	private CheckBox nikeCheckBox;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Button purchaseButton;

	@FXML
	private VBox basketBox;
	
	@FXML
	private CheckBox basketCheckBox;
	
	private Connection con;
	private MenuController menuCon;
	private AdminController adminCon;
	private BasketDAO basketDao;
	private MemberDAO memberDao;
    private MemberDTO memberDTO;
    private List<BasketDTO> basketItem;
	private ObservableList<BasketDTO> basketItems;
	private ProductOptionDAO otDao;
	private CommonService commonService;
	
	public void setMenuCon(MenuController con) {
		this.menuCon = menuCon;
	}

	public void setAdminCon(AdminController con2) {
		this.adminCon = adminCon;
	}

    public void setMemberDTO(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

    public void setBasketItems(List<BasketDTO> basketItems) {
        this.basketItem = basketItem;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadBasketItems();
	}
	
    public BasketController() {
        basketDao = new BasketDAO();
        commonService = new CommonService();
    }
 
    @FXML
    private void allCheckBoxClicked() {
        for (Node child : basketBox.getChildren()) {
            if (child instanceof HBox) {
                HBox hbox = (HBox) child;
                for (Node node : hbox.getChildren()) {
                    if (node instanceof CheckBox) {
                        CheckBox basketCheckBox = (CheckBox) node;
                        basketCheckBox.setSelected(true);
                    }
                }
            }
        }
    }
    
    @FXML
    private void deleteButtonClicked() {
    	if (basketCheckBox.isSelected()) {
    		boolean result = CommonService.showConfirmationDialog("확인", "정말로 삭제하시겠습니까?");
    	    if (result) {
    	        BasketDTO dto = new BasketDTO();
    	        dto.setId(Login.getId());
    	        basketDao.basketDelete(dto);
    	        basketBox.getChildren().clear();
    	    }
    	}
    }
 
	public void loadBasketItems() {
		String loggedInUserId = Login.getId();
		BasketDAO basketDAO = new BasketDAO();
		List<BasketDTO> basketList = basketDAO.getBasketInformation(loggedInUserId);

		if (!basketList.isEmpty()) {
			for (BasketDTO basket : basketList) {
				VBox itemVBox = createBasketItemVBox(basket); // BasketDTO를 기반으로 VBox 생성
				VBox.setMargin(itemVBox, new Insets(10.0));
				basketBox.getChildren().add(itemVBox); // VBox에 아이템 추가
			}
		} else {
			System.out.println("No basket information found for the logged-in user.");
		}
	}
	
	public VBox basketBox(String user, ArrayList<BasketDTO> getBasketInfomation) {

		return null;
	}

	private VBox createBasketItemVBox(BasketDTO basketItem) {
		VBox basketBox = new VBox();
		basketBox.setPrefHeight(180.0);
		basketBox.setPrefWidth(690.0);
		basketBox.setStyle("-fx-background-color: #fff;");

		HBox basketVBox = new HBox();
		basketVBox.setPrefHeight(37.0);
		basketVBox.setPrefWidth(700.0);
		basketVBox.setAlignment(Pos.CENTER_LEFT);

		HBox innerHBox = new HBox();
		innerHBox.setMaxHeight(30.0);
		innerHBox.setPrefHeight(30.0);
		innerHBox.setPrefWidth(80.0);
		HBox.setMargin(innerHBox, new Insets(0, 0, 0, 50.0));
		basketVBox.getChildren().add(innerHBox);

		// 상품 정보 표시
		VBox productInfoVBox = new VBox();
		productInfoVBox.setPrefHeight(100.0);
		productInfoVBox.setPrefWidth(350.0);
		productInfoVBox.setSpacing(10.0);

		Label productNameLabel = new Label("상품명 : " + basketItem.getProductName());
		productNameLabel.setMaxHeight(35.0);
		productNameLabel.setMaxWidth(450.0);

		Label productDetailLabel = new Label("사이즈 및 색상 : " + basketItem.getSize() + " / " + basketItem.getColor());
		productDetailLabel.setMaxHeight(350.0);
		productDetailLabel.setMaxWidth(450.0);

		// 스피너 값 범위 설정
		Spinner<Integer> productSpinner = new Spinner<>();
		ProductOptionDAO productOptionDAO = new ProductOptionDAO();
		ProductOptionDTO option = productOptionDAO.getQuantity(basketItem.getPdCode(), basketItem.getColor(),
				basketItem.getSize());
		int otQuantity = option.getOtQuantity();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, (otQuantity+1),
				basketItem.getQuantity());
		productSpinner.setValueFactory(valueFactory);
		
		// 스피너 값 변경 리스너 등록
		productSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue < 1) {
				CommonService.SpinnerAlert("알림", "0보다 큰 값을 입력해주셔야 합니다.");
				 productSpinner.getValueFactory().setValue(1);
			} else if (newValue > otQuantity) {
				CommonService.SpinnerAlert("알림", "재고수량보다 큰 값을 입력할 수 없습니다.");
				valueFactory.setValue(otQuantity);
			}
			System.out.println("선택된 값: " + newValue);
		});

		productSpinner.setPrefHeight(35.0);
		productSpinner.setPrefWidth(100.0);
		VBox.setMargin(productSpinner, new Insets(0, 0, 0, 0));

		productInfoVBox.getChildren().addAll(productNameLabel, productDetailLabel, productSpinner);

		HBox hbox = new HBox();
		hbox.setPrefHeight(100.0);
		hbox.setPrefWidth(200.0);
		VBox.setMargin(hbox, new Insets(0, 0, 0, 50.0));

		CheckBox basketCheckBox = new CheckBox();
		HBox checkboxHBox = new HBox();
		checkboxHBox.setPrefHeight(100.0);
		checkboxHBox.setPrefWidth(21.0);
		HBox.setMargin(basketCheckBox, new Insets(15.0, 0, 0, 0));

		checkboxHBox.getChildren().add(basketCheckBox);

		ImageView basketImageView = new ImageView(basketItem.getImage());
		basketImageView.setFitHeight(100.0);
		basketImageView.setFitWidth(130.0);

		HBox imageHBox = new HBox();
		imageHBox.setPrefHeight(100.0);
		imageHBox.setPrefWidth(130.0);
		imageHBox.setPickOnBounds(true);

		imageHBox.getChildren().add(basketImageView);

		HBox priceHBox = new HBox();
		priceHBox.setPrefHeight(100.0);
		priceHBox.setPrefWidth(150.0);
		HBox.setMargin(priceHBox, new Insets(0, 2.0, 0, 0));

		VBox buttonVBox = new VBox();
		buttonVBox.setAlignment(Pos.TOP_CENTER);
		buttonVBox.setPrefHeight(100.0);
		buttonVBox.setPrefWidth(148.0);

		Button productDeleteButton = new Button("X");
		productDeleteButton.setMaxHeight(39.0);
		productDeleteButton.setMaxWidth(45.0);
		productDeleteButton.setMinWidth(45.0);
		VBox.setMargin(productDeleteButton, new Insets(15.0, 0, 0, 0));
		
		productDeleteButton.setOnMouseClicked(event -> {
			int basketNum = basketItem.getBasketnum();
			boolean result = CommonService.showConfirmationDialog("확인", "정말로 삭제하시겠습니까?");
		    if (result) {
		        BasketDTO dto = new BasketDTO();
		        dto.setBasketnum(basketNum);
		        basketDao.basketDelete(dto);
		        basketBox.getChildren().clear();
		    }
		});
		
		String userId = Login.getId();
		System.out.println(userId);
		BasketDAO basketDAO = new BasketDAO();
		List<BasketDTO> basketItems = basketDAO.getBasketInformation(userId);
		Label productPriceLabel = new Label(basketItem.getPrice() + "원");
		productPriceLabel.setMaxWidth(150.0);
		productPriceLabel.setPrefWidth(150.0);
		VBox.setMargin(productPriceLabel, new Insets(15.0, 0, 0, 0));

	    updateTotalPrice(basketItems);

		buttonVBox.getChildren().addAll(productDeleteButton, productPriceLabel);
		priceHBox.getChildren().add(buttonVBox);
		hbox.getChildren().addAll(checkboxHBox, imageHBox, productInfoVBox, priceHBox);

		basketBox.getChildren().addAll(basketVBox, hbox);
		return basketBox;
		
	}
	
	private void updateTotalPrice(List<BasketDTO> basketItems) {
		int totalPrice = 0;
	    for (BasketDTO basketItem : basketItems) {
	        totalPrice += basketItem.getPrice();
	    }
	    totalPriceLabel.setText(totalPrice + "원");
	}
	
    @FXML
	public void purchaseButtonClicked(ActionEvent event) throws IOException{
        try {
            String users = Login.getId();
            BasketDAO basketDAO = new BasketDAO();
            ArrayList<BasketDTO> userBasketItems = basketDAO.getBasketInformation(users);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("purchase.fxml"));
            Parent purchasePage = loader.load();
            PurchaseController basketController = loader.getController();
            basketController.setInfo(userBasketItems);
            basketController.setting();
            StackPane contentStackPane = (StackPane) purchaseButton.getScene().lookup("#contentStackPane");
            if (contentStackPane != null) {
                contentStackPane.getChildren().clear();
                contentStackPane.getChildren().add(purchasePage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}