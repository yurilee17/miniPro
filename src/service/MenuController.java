package service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cart.BasketController;
import equipment.CommonService;
import equipment.Opener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import search.SearchController;
import everyOneShopPing.Main;

public class MenuController implements Initializable {
	private Opener opener;
	private Stage stage;
	private MenuController con;
	private BasketController BasketCon;
	private static MenuController menucontroller;

	public void setMenuCon(MenuController con) {
		this.con = con;
	}

	public void setBasketCon(BasketController basketCon) {
		this.BasketCon = basketCon;
		}

		public void setOpener(Opener opener) {
			this.opener = opener;
		}

		@FXML
		private Label loginUserIDLabel; // 사용자 아이디를 출력할 Label

		public Label getLoginUserIDLabel() {
			return loginUserIDLabel;
		}

		@FXML
		private Label adminLabel;

		@FXML
		private StackPane contentStackPane;

		public void setContentStackPane(StackPane contentStackPane) {
			this.contentStackPane = contentStackPane;
		}

		@FXML
		private StackPane centerPane;

		@FXML
		private ImageView searchImage;

		@FXML
		private StackPane mainStackPane;

		public void wishlistProc(MouseEvent event) {
			loadwishList();
		}

		private void loadwishList() {
			if (Login.isLoggedIn()) {
				System.out.println("즐겨찾기 페이지로 이동해야 함.");
			} else {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
					Parent loginView = loader.load();
					LoginController loginCon = loader.getController();
					loginCon.setOpener(opener);
					loginCon.setMenuCon(con);
					contentStackPane.getChildren().clear();
					contentStackPane.getChildren().add(loginView);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    	}
    	

		public void basketProc(MouseEvent event) {
			loadbasket();
		}

		public void loadbasket() {
			if (Login.isLoggedIn()) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../cart/basket.fxml"));
					Parent basketView = loader.load();
					BasketController basketCon = loader.getController();
					basketCon.setMenuCon(con);
					contentStackPane.getChildren().clear();
					contentStackPane.getChildren().add(basketView);

				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
					Parent loginView = loader.load();
					LoginController loginCon = loader.getController();
					loginCon.setOpener(opener);
					loginCon.setMenuCon(con);
					contentStackPane.getChildren().clear();
					contentStackPane.getChildren().add(loginView);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 마이페이 진입
		public void myPageProc() {
			loadmyPage();
		}

		private void loadmyPage() {
			if (Login.isLoggedIn()) {
				opener.setOpener(opener);
				opener.setMenuCon(con);
				opener.myPageOpen();
			} else {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
					Parent loginView = loader.load();
					LoginController loginCon = loader.getController();
					loginCon.setOpener(opener);	
					loginCon.setMenuCon(con);
					contentStackPane.getChildren().clear();
					contentStackPane.getChildren().add(loginView);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public void logoutProc() {
			if (!Login.isLoggedIn()) {
				return;
			}
			Login.setLoggedIn(false);
			opener.mainOpen();
			CommonService.msg("로그아웃 되었습니다.");
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			menucontroller = this;
			if (Login.isLoggedIn() && Login.getId().equals("admin")) {
			} else if (Login.isLoggedIn()) {
				loginUserIDLabel.setVisible(true); // loginUserIDLabel을 보이도록 설정
				loginUserIDLabel.setText(Login.getId() + "님, 환영합니다.");
			} else {
				loginUserIDLabel.setVisible(false); // loginUserIDLabel을 숨기도록 설정
			}
		}

		public static MenuController getinstance() {
			return menucontroller;
		}

		@FXML
		private void searchImageClicked(MouseEvent event) {
			loadSearch();
		}

		private void loadSearch() {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../search/search.fxml"));
				Parent searchView = loader.load();
				SearchController searchCon = loader.getController();
				searchCon.setMenuCon(con);
				contentStackPane.getChildren().clear();
				contentStackPane.getChildren().add(searchView);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void searchImageResult(SearchController searchResultController, Parent searchResultParent) {
			searchResultController.setMenuCon(con);
			searchResultController.setOpener(opener);
			contentStackPane.getChildren().clear();
			contentStackPane.getChildren().add(searchResultParent);
		}

		public void cancel() {
			contentStackPane.getChildren().clear();
		}

		@FXML
		private void noticeImageClicked(MouseEvent event) {
			loadNotice();
		}

		public void loadNotice() {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../notice/notice.fxml"));
				Parent noticeView = loader.load();
				contentStackPane.getChildren().clear();
				contentStackPane.getChildren().add(noticeView);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@FXML
		private void faqImageClicked(MouseEvent event) {
			loadFAQ();
		}

		public void loadFAQ() {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../faq/faq.fxml"));
				Parent faqView = loader.load();
				contentStackPane.getChildren().clear();
				contentStackPane.getChildren().add(faqView);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void setStage(Stage stage) {
			this.stage = stage;
		}
		
		@FXML
		private void noticeWriteClicked(MouseEvent event) {
			loadNoticeWrite();
		}

		private void loadNoticeWrite() {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("noticemanage.fxml"));
				Parent noticeWriteView = loader.load();
				contentStackPane.getChildren().add(noticeWriteView);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public StackPane getContentStackPane() {
			return null;
		}

		// 로그인 페이지 진입(주문하기때 사용)
		public void inLogin() {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
				Parent loginView = loader.load();
				LoginController loginCon = loader.getController();
				loginCon.setOpener(opener);
				loginCon.setMenuCon(con);
				contentStackPane.getChildren().clear();
				contentStackPane.getChildren().add(loginView);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 회원가입 진입
		public void inReg(RegController setCon, Parent parent) {
			setCon.setMenuCon(con);
			setCon.setOpener(opener);
			contentStackPane.getChildren().clear();
			contentStackPane.getChildren().add(parent);
		}
}
