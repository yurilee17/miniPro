package equipment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import order.OrderManagerController;
import order.OrderOptionDTO;
import product.ProductDetailController;
import product.ProductManageController;
import review.StarRatingController;
import service.AdminController;
import service.LoginController;
import service.MenuController;
import service.MemberManageController;
import service.MyPageController;
import service.OrderDeliveryController;
import service.OrderDeliveryDetailController;
import service.RegController;

public class Opener {
	private Opener opener;
	
	 public void setOpener(Opener opener) {
	        this.opener = opener;
	    }
	 private StackPane contentStackPane;
	private Stage primaryStage;
	private Stage loginStage;
	private Stage memberManageStage;
	private MenuController menuCon;
	private Stage regStage;
	private Stage searchStage;
	private Parent login;
	private Parent membership;
    @FXML
    private TextField idFld;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public Stage getPrimarySTage() {
		return primaryStage;
	}
	
	public void setMenuCon(MenuController menuCon) {
	    this.menuCon = menuCon;
	}
	
	public void searchOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../search/search.fxml"));
	    searchStage = new Stage();
		Parent searchForm;
		try {
			searchForm = loader.load();
			searchStage.setScene(new Scene(searchForm));
			searchStage.setTitle("메인 화면");
			searchStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	 즐겨찾기 or 마이페이지 클릭시 로그인 -> 성공시 다시 메인화면이 되어야함
	public void mainOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../service/menu.fxml"));
		BorderPane mainForm;
		try {
			mainForm = loader.load();  
			
			StackPane contentStackPane = (StackPane) loader.getNamespace().get("contentStackPane");
			
			primaryStage.setScene(new Scene(mainForm));
			primaryStage.setTitle("메인 화면");
			primaryStage.show();
			
			MenuController menuController = loader.getController();
            menuController.setContentStackPane(contentStackPane);
            menuController.setMenuCon(menuController);
            menuController.setOpener(opener);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//마이 페이지에서 임의 페이지 열기
		public void mainConOpen(String con) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../service/menu.fxml"));
			BorderPane mainForm;
			try {
				mainForm = loader.load();  

				StackPane contentStackPane = (StackPane) loader.getNamespace().get("contentStackPane");

				primaryStage.setScene(new Scene(mainForm));
				primaryStage.setTitle("메인 화면");
				primaryStage.show();

				MenuController menuController = loader.getController();
	            menuController.setContentStackPane(contentStackPane);
	            menuController.setMenuCon(menuController);
	            menuController.setOpener(opener);
	            if(con.equals("notice"))
	            	menuController.loadNotice();
	            else if(con.equals("FAQ"))
	            	menuController.loadFAQ();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	
	//로그인 화면을 실행하는 기능
	public void loginOpen() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("../service/login.fxml"));
			loginStage = new Stage();
			
			login = loader.load();
			LoginController logCon = loader.getController();
			logCon.setLoginStage(loginStage);
			logCon.setLoginForm(login);
			
			loginStage.setScene(new Scene(login));
			loginStage.setTitle("로그인 화면");
			loginStage.show();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void adminOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../service/admin.fxml"));
//	    adminStage = new Stage();

		Parent adminForm;
		try {
			adminForm = loader.load();
			
			StackPane contentStackPane = (StackPane) loader.getNamespace().get("contentStackPane");
			AdminController adminCon = loader.getController();
			adminCon.setOpener(opener);
			adminCon.setContentStackPane(contentStackPane);
            adminCon.setAdminCon(adminCon);
			primaryStage.setScene(new Scene(adminForm));
			primaryStage.setTitle("관리자 화면");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 배송 관리 페이지 진입
	public void orderManageOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../order/orderManage.fxml"));
		Parent orderManageForm;
		try {
			orderManageForm = loader.load();
			OrderManagerController oMController = loader.getController();
			oMController.setOpener(opener);
			
			primaryStage.setScene(new Scene(orderManageForm));
			primaryStage.setTitle("관리자 배송관리 페이지");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 상품 관리 페이지 진입 
	public void productManageOpen() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../product/productManage.fxml"));

		Parent productManageForm;
		try {
			productManageForm = loader.load();
			ProductManageController proMaCon = loader.getController();
			proMaCon.setOpener(opener);
			
			ComboBox<String> ageCombo = (ComboBox<String>) productManageForm.lookup("#inputCategory");
			ageCombo.getItems().addAll("티셔츠", "셔츠", "후드", "맨투맨", "코트", "패딩", "블레이저", "청바지", "면바지", "반바지", "롱원피스", "미니원피스", "롱스커트", "미니스커트");
			
			primaryStage.setScene(new Scene(productManageForm));
			primaryStage.setTitle("관리자 상품관리 페이지");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// 상품 상세 페이지 진입
	public void product1DetailOpen(String code) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../product/productDetail.fxml"));
		Stage productDetailStage = new Stage();
		Parent productDetailForm;
		
		try {
			productDetailForm = loader.load();
			ProductDetailController pdcontroller = loader.getController();
			pdcontroller.setCode(code);
			pdcontroller.setMenuCon(menuCon);
			pdcontroller.setStage(productDetailStage);
			pdcontroller.setOpener(opener);
			pdcontroller.setting();
			
			productDetailStage.setScene(new Scene(productDetailForm));
			productDetailStage.setTitle("상품 상세 페이지");
			productDetailStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//마이 페이지 진입
	public void myPageOpen() {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../service/myPage.fxml"));
			Parent myPageForm;
			
			try {
				myPageForm = loader.load();
				MyPageController myPageCon = loader.getController();
				myPageCon.setOpener(opener);
				myPageCon.setMenuCon(menuCon);
				myPageCon.setting();
				primaryStage.setScene(new Scene(myPageForm));
				primaryStage.setTitle("관리자 상품관리 페이지");
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
	
		}
	//리뷰 입력 진입
		public void openReview(String data, OrderOptionDTO settingOption, OrderDeliveryDetailController conODD) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../review/starRating.fxml"));

			Parent form;
			Stage newStage = new Stage();
			try {
				form = loader.load();
				StarRatingController con = loader.getController();
				con.setOpener(opener);
				con.setDto(settingOption);
				con.setDate(data);
				con.setStage(newStage);
				con.setOrderDeliveryDetailController(conODD);
				newStage.setScene(new Scene(form));
				newStage.setTitle("리뷰 작성");
				newStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	
	//주문 내역 상세 진입
	public void orderDeliveryDetailOpen(int num) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../service/orderDeliveryDetail.fxml"));

		Parent orderDeliveryDetailForm;
		try {
			orderDeliveryDetailForm = loader.load();
			OrderDeliveryDetailController con = loader.getController();
			con.setOpener(opener);
			con.setNum(num);
			con.setting();
			con.setOrderDeliveryDetailController(con);
			primaryStage.setScene(new Scene(orderDeliveryDetailForm));
			primaryStage.setTitle("주문 내역");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//주문 내역 진입
	public void orderDeliveryOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../service/orderDelivery.fxml"));

		Parent form;
		try {
			form = loader.load();
			OrderDeliveryController con = loader.getController();
			con.setOpener(opener);
			primaryStage.setScene(new Scene(form));
			primaryStage.setTitle("주문 상세 내역");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	// 회원 관리 페이지 진입 
	public void memberManageOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../service/memberManage.fxml"));
		Parent memberManageForm;
		
		try {
			memberManageForm = loader.load();
			MemberManageController con = loader.getController();
			con.setOpener(opener);
			primaryStage.setScene(new Scene(memberManageForm));
			primaryStage.setTitle("관리자 회원관리 페이지");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void regOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../service/membership.fxml"));
		
		regStage = new Stage();
		try {
			membership = loader.load();
			RegController regCon = loader.getController();
			regCon.setRegStage(regStage);
			regCon.setRegForm(membership);

			regStage.setScene(new Scene(membership));
			regStage.setTitle("회원가입 화면");
			regStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
