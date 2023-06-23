package product;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import cart.BasketController;
import cart.BasketDAO;
import cart.BasketDTO;
import cart.BasketService;
import cart.PurchaseController;
import equipment.CommonService;
import equipment.Opener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import member.MemberDTO;
import service.MenuController;
import service.AdminController;
import service.Login;




public class ProductDetailController implements Initializable{
	
		private AdminController adminCon;
		private MenuController menuCon;
		private Opener opener;
		private Stage productDetailStage;
		private ProductDetail detail;
//		private String pdCode = "03";
		private String pdCode;
		
		@FXML
	    private Label pdName;
		@FXML
	    private ImageView img;
		@FXML
	    private Label notImg;
		@FXML
	    private Label parentCategory;
		@FXML
	    private Label subCategory;
		@FXML
	    private Label view;
		@FXML
	    private Label review;
		@FXML
	    private Label explanation;
		@FXML
	    private ComboBox<String> color;
		@FXML
	    private ComboBox<String> size;
		@FXML
	    private Label quantity;
		@FXML
	    private Label price;
		@FXML
	    private VBox buyBox;
		@FXML
		private Button basketadd;
		@FXML
		private ImageView noticeimage;
		
		private ProductDAO productDAO;
		public void setAdminCon(AdminController adminCon) {
		    this.adminCon = adminCon;
		}

		public void setMenuCon(MenuController menuCon) {
		    this.menuCon = menuCon;
		}
		public void setOpener(Opener opener) {
		    this.opener = opener;
		}

	    public ProductDetailController() {
	        productDAO = new ProductDAO();
	    }
	    
	    public String getPdNameByPdCode(String pdCode) {
	        return productDAO.getPdNameByPdCode(pdCode);
	    }
		
		public void setCode(String pdCode) {
			this.pdCode = pdCode;
		}
			
		public void setStage(Stage productDetailStage) {
			this.productDetailStage = productDetailStage;
		}
		
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
	
		}
		 // X버튼 선택 시 창 종료
	    public void cancel() {
	    	productDetailStage.close();
		}
		
		public void setting() {
			// 조회수 상승
			ProductDAO pdDao = new ProductDAO();
			ProductDTO pdDto = pdDao.getProduct(pdCode);
			pdDto.setView(pdDto.getView()+1);
			pdDao.pdUpdate(pdDto);
			
			detail = new ProductDetail(pdCode);
			
			// 이름 설정
			pdName.setText(detail.getPdName());
			
			// 이미지 설정
			try {
				img.setImage(new Image(detail.getPdImage()));
			} catch (Exception e) {
				notImg.setVisible(true);
			}
			
	        // 카테고리 설정
			if(detail.getCategory().equals("티셔츠") || detail.getCategory().equals("셔츠") || detail.getCategory().equals("후드") || detail.getCategory().equals("맨투맨")) {
				parentCategory.setText("상의");
			}else if(detail.getCategory().equals("코트") || detail.getCategory().equals("패딩") || detail.getCategory().equals("블레이저")) {
				parentCategory.setText("아우터");
			}else if(detail.getCategory().equals("청바지") || detail.getCategory().equals("면바지") || detail.getCategory().equals("반바지")) {
				parentCategory.setText("하의");
			}else if(detail.getCategory().equals("롱원피스") || detail.getCategory().equals("미니원피스") || detail.getCategory().equals("미니스커트")) {
				parentCategory.setText("원피스/스커트");
			}
			subCategory.setText(detail.getCategory());
			
			
			// 조회수 설정
			view.setText("" + detail.getView());
			
			// 가격 설정
			price.setText("" + detail.getPrice());
			
			// 별점 설정
			review.setText("" + detail.getReview());
			
			// 설명 설정
			ArrayList<String> explanationList = new ArrayList<String>();
			String explanationResult = "";
			
			for (int i = 0; i <= detail.getExplanation().length() / 45; i++) {
				int startIndex = i * 45; //시작하는 코드
				int endIndex = Math.min(startIndex + 45, detail.getExplanation().length()); // 둘중 더 작은 값을 선택하는 코드
				explanationList.add(detail.getExplanation().substring(startIndex, endIndex)); // startIndex부터 endIndex-1까지의 부분 문자열을 추출
			}
			
			if(explanationList != null) {
				for(String text : explanationList) {
					explanationResult = explanationResult + text + "\n";
				}
			}	
			explanation.setText(explanationResult);
			
			// 색상 옵션 설정
			ArrayList<String> colors = detail.getColors();
			if(colors.isEmpty()) {
				color.getItems().add("기본");
			}else {
				for(String colorOne : colors) {
					color.getItems().add(colorOne);
				}
			}
		}
		
		
		// 색상 선택 시 사이즈 옵션 설정(품절 표시 함)
		public void clickColor(ActionEvent event) {
			size.setVisible(true);
			size.getItems().clear();
			if(color.getValue().equals("기본")) {
				size.getItems().add("기본");
			}else {
				ArrayList<String> sizes = detail.getSize(pdCode, color.getValue());
				for(String sizeOne : sizes) {
					int quantityOt = detail.getQuantity(pdCode, color.getValue(), sizeOne);
					if(quantityOt == 0) {
						size.getItems().add(sizeOne + " (품절)");
					}else
						size.getItems().add(sizeOne);
				}
			}
			

		}

		
		// 사이즈까지 선택 시 선택한 옵션에 대한 박스 추가(동일 옵션 선택 시 갯수만 추가함)
		public void addOption() {
			String selectedColor = color.getValue();
			String selectedSize = size.getValue();
			if (selectedColor != null && selectedSize != null) {
				int quantityOt = detail.getQuantity(pdCode, selectedColor, selectedSize);
				quantity.setText("" + quantityOt);
				String boxId = selectedColor + selectedSize;
				// 이미 추가한 박스 아이디가 존재 하는지 확인
				Pane node =(Pane) buyBox.lookup("#" + boxId);
				// 존재할 경우
				if (node != null) {
//					// 선택한 수량 라벨을 찾아서
					Label label = (Label) node.lookup("#" + boxId + boxId);
					int labelText = Integer.parseInt(label.getText()); 
					labelText ++;
					label.setText("" + labelText);
				// 존재하지 않을 경우 박스 추가
				} else {
					// 재고 값이 0이 아닐 때
					if(quantityOt != 0) {
						VBox newBox = chooseOption(selectedColor, selectedSize, quantityOt);
				    	newBox.setId(selectedColor+selectedSize);
					    buyBox.getChildren().add(newBox);
					    VBox.setMargin(newBox, new Insets(0, 25, 20, 10));
					}else {
						// 서비스에서 품절이라고 알림 발생 예정
					}
				}
			}
		}

		int num = 1;
		 
		// 옵션 박스 설정 값
		public VBox chooseOption(String color, String size, int quantityOt) {
			String name = color + size;
			VBox chooseOption = new VBox();
			chooseOption.setPrefSize(660, 111);
			chooseOption.setStyle("-fx-background-color: #fff;");
			chooseOption.setPadding(new Insets(10));
			
			 // 첫 번째 줄 HBox
	        HBox hBox1 = new HBox();
	        hBox1.setPrefSize(640, 22);

	        HBox hBox1_1 = new HBox();
	        hBox1_1.setPrefSize(608, 36);

	        // 선택한 색상 표시
	        Label labelcolor = new Label(color);
	        labelcolor.setPrefSize(76, 18);
	        labelcolor.setAlignment(javafx.geometry.Pos.CENTER);
	        labelcolor.setFont(new Font(14));
	        labelcolor.setId(name+"color");

	        Label label2 = new Label("/");
	        label2.setPrefSize(22, 18);
	        label2.setAlignment(javafx.geometry.Pos.CENTER);
	        label2.setFont(new Font(14));

	        // 선택한 사이즈 표시
	        Label label3 = new Label(size);
	        label3.setPrefSize(76, 18);
	        label3.setAlignment(javafx.geometry.Pos.CENTER);
	        label3.setFont(new Font(14));
	        label3.setId(name+"size");

	        hBox1_1.getChildren().addAll(labelcolor, label2, label3);

	        HBox hBox1_2 = new HBox();
	        hBox1_2.setPrefSize(35, 22);

	        Label label4 = new Label("X");
	        label4.setPrefSize(36, 18);
	        label4.setAlignment(javafx.geometry.Pos.CENTER);
	        label4.setFont(new Font(14));

	        hBox1_2.getChildren().add(label4);

	        hBox1.getChildren().addAll(hBox1_1, hBox1_2);

	        // 두 번째 줄 HBox
	        HBox hBox2 = new HBox();
	        hBox2.setAlignment(javafx.geometry.Pos.CENTER);
	        hBox2.setPrefSize(660, 130);

	        HBox hBox2_1 = new HBox();
	        hBox2_1.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
	        hBox2_1.setPrefSize(637, 130);

	        Button minusButton = new Button("-");
	        minusButton.setMnemonicParsing(false);

	        // 선택한 수량
	        Label numLabel = new Label("" + num);
	        numLabel.setPrefSize(76, 18);
	        numLabel.setAlignment(javafx.geometry.Pos.CENTER);
	        numLabel.setFont(new Font(14));
	        numLabel.setId(name+name);

	        Button plusButton = new Button("+");
	        plusButton.setMnemonicParsing(false);

	        hBox2_1.getChildren().addAll(minusButton, numLabel, plusButton);

	        HBox hBox2_2 = new HBox();
	        hBox2_2.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
	        hBox2_2.setPrefSize(499, 130);

	        Label amountLabel = new Label("금액   " + detail.getPrice()*num );
	        amountLabel.setPrefSize(165, 18);
	        amountLabel.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
	        amountLabel.setFont(new Font(14));

	        Label currencyLabel = new Label("원");
	        currencyLabel.setPrefSize(23, 18);
	        currencyLabel.setAlignment(javafx.geometry.Pos.CENTER);
	        currencyLabel.setFont(new Font(14));

	        hBox2_2.getChildren().addAll(amountLabel, currencyLabel);

	        hBox2.getChildren().addAll(hBox2_1, hBox2_2);

	        chooseOption.getChildren().addAll(hBox1, hBox2);

	        VBox.setMargin(hBox2, new Insets(0, 25, 20, 10));
	        
	        // 마이너스 버튼이 클릭되었을 때 실행되는 코드
	        minusButton.setOnAction(event -> {
	        	int tmpNum = Integer.parseInt(numLabel.getText());
	           if(tmpNum > 1) {
	        	   tmpNum --;
	        	   numLabel.setText("" + tmpNum);
	        	   amountLabel.setText("금액   " + detail.getPrice()*tmpNum );
	           }
	        });
	        
	        // 플러스 버튼이 클릭되었을 때 실행되는 코드
	        plusButton.setOnAction(event -> {
	        	int tmpNum = Integer.parseInt(numLabel.getText());
	        	
	        	 if(tmpNum < quantityOt) {
	        		tmpNum ++;
	 	        	numLabel.setText("" + tmpNum);
	 	        	amountLabel.setText("금액   " + detail.getPrice()*tmpNum );
		           }
	        	
	        });
	        
	        // X 눌렀을 때 동작
	        label4.setOnMouseClicked(event -> {
	        	HBox parentBox = (HBox) label4.getParent();
	        	HBox parentHBox = (HBox) parentBox.getParent();
	            VBox parentVBox = (VBox) parentHBox.getParent();

	            buyBox.getChildren().remove(parentVBox);
	        });
			
			return chooseOption;
		}
		
		public void clickBasket() {
			Boolean tmp = false;
			ObservableList<Node> children = buyBox.getChildren();
			for (Node child : children) {
			    if (child instanceof VBox) {
			        VBox vbox = (VBox) child;
			        String vboxId = vbox.getId();
			        String name = vboxId + vboxId;
			        Label labelNum = (Label) buyBox.lookup("#" + name);
			        Label labelColor = (Label) buyBox.lookup("#" + vboxId + "color");
			        Label labelSize = (Label) buyBox.lookup("#" + vboxId + "size");
			        int resultPrice = Integer.parseInt(labelNum.getText())*detail.getPrice(); 
			        
			        String user = Login.getId();
		            BasketDTO basketItem = new BasketDTO();
		            basketItem.setId(user);
		            basketItem.setPdCode(pdCode);
		            basketItem.setProductName(pdName.getText());
		            basketItem.setSize(labelSize.getText());
		            basketItem.setPrice(resultPrice);
		            basketItem.setImage(detail.getPdImage());
		            basketItem.setColor(labelColor.getText());
		            basketItem.setQuantity(Integer.parseInt(labelNum.getText()));
		            
		            BasketDAO basketDAO = new BasketDAO();
		            try {
		                basketDAO.connectToDatabase();
		                if (basketDAO.addItem(basketItem)) {
		                	tmp = true;
		                	
		                 
		                }
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		           
			}
			}
			 if (tmp) {
		            System.out.println("장바구니에 추가되었습니다.");
                 boolean result = CommonService.showConfirmationDialog("확인", "장바구니에 물품이 추가되었습니다. \n장바구니 페이지로 이동하시겠습니까?");
                 if (result) {
                       // B 창 닫기
                       productDetailStage.close();
                       menuCon.loadbasket();
               } else {
                   // B 창 닫기
                   productDetailStage.close();
               }
                 //여기까진 되지만 여기 밑으로 창이 전환되는 과정에서 에러
                 }
  		// basket.fxml로 이동하면서 현재 페이지의 데이터 장바구니 DB에 저장 + 사용자 로그인 아이디를 키값으로
	    }

		

		public void clickBuy() {
			ArrayList<BasketDTO> basketItems = new ArrayList<BasketDTO>();
			ObservableList<Node> children = buyBox.getChildren();
			for (Node child : children) {
			    if (child instanceof VBox) {
			        VBox vbox = (VBox) child;
			        String vboxId = vbox.getId();
			        String name = vboxId + vboxId;
			        Label labelNum = (Label) buyBox.lookup("#" + name);
			        Label labelColor = (Label) buyBox.lookup("#" + vboxId + "color");
			        Label labelSize = (Label) buyBox.lookup("#" + vboxId + "size");
			        int resultPrice = Integer.parseInt(labelNum.getText())*detail.getPrice(); 
			        
			        System.out.println("VBox ID: " + vboxId);
			        System.out.println("코드" + pdCode + "/색상 : "+ labelColor.getText() +"/사이즈 : "+ labelSize.getText() +"/수량 : "+ labelNum.getText() +"/이미지 : "+ detail.getPdImage() +"/금액 : "+ resultPrice);
			
				        BasketDTO basketItem = new BasketDTO();
		                basketItem.setPdCode(pdCode); // 상품 코드 설정
		                basketItem.setProductName(pdName.getText()); // 상품명 설정
		                basketItem.setSize(labelSize.getText()); // 사이즈 설정
		                basketItem.setPrice(resultPrice); // 상품 가격 설정
		                basketItem.setImage(detail.getPdImage()); // 이미지 설정
		                basketItem.setColor(labelColor.getText()); // 색상 설정
		                basketItem.setQuantity(Integer.parseInt(labelNum.getText()));// 수량 설정
		                basketItems.add(basketItem);
		            }
			    }
			 
            if (Login.isLoggedIn()) {
    			try {
    				Stage stage = new Stage();
		                FXMLLoader loader = new FXMLLoader(getClass().getResource("../cart/purchase.fxml"));
		                Parent basketpage = loader.load();
		                PurchaseController  basketController = loader.getController();
		                basketController.setInfo(basketItems);
		                basketController.setting();
		                basketController.setStage(stage);

		               // Scene 생성 및 보여주기
	                Scene scene = new Scene(basketpage);

	                stage.setScene(scene);
	                stage.show();

    			} catch (IOException e) {
		            e.printStackTrace();
		        }

    		} else {
    			productDetailStage.close();

    				if(Login.getId() != null && Login.getId().equals("admin")) {
    	            	adminCon.inLogin();
    	            } else {
    	            	menuCon.inLogin();
    	            }


    		}
			}
		}
