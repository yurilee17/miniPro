package service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cart.BasketController;
import equipment.Opener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import search.SearchController;

public class AdminController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (Login.isLoggedIn() && Login.getId().equals("admin")) {
			adminLabel.setVisible(true);
			adminLabel.setText("관리자 모드입니다.");
		}
	}

	private Opener opener;
	private AdminController con;

	@FXML
	private Label adminLabel;
    
	@FXML
	private StackPane contentStackPane;

	public void setContentStackPane(StackPane contentStackPane) {
		this.contentStackPane = contentStackPane;
	}

	public void setOpener(Opener opener) {
		this.opener = opener;
	}

	public void setAdminCon(AdminController con) {
		this.con = con;
	}

	@FXML
	public void basketProc(MouseEvent event) {
		loadbasket();
	}
	
	private void loadbasket() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../cart/basket.fxml"));
			Parent basketView = loader.load();
			BasketController basketCon = loader.getController();
			basketCon.setAdminCon(con);
			contentStackPane.getChildren().clear();
			contentStackPane.getChildren().add(basketView);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			searchCon.setAdminCon(con);
			contentStackPane.getChildren().clear();
			contentStackPane.getChildren().add(searchView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void noticeImageClicked(MouseEvent event) {
		loadNotice();
	}

	private void loadNotice() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../notice/notice.fxml"));
			Parent noticeView = loader.load();
			contentStackPane.getChildren().clear();
			contentStackPane.getChildren().add(noticeView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchImageResult(SearchController searchResultController, Parent searchResultParent) {
		searchResultController.setAdminCon(con);
		searchResultController.setOpener(opener);
		contentStackPane.getChildren().clear();
		contentStackPane.getChildren().add(searchResultParent);
	}

	public void logihImageResult(SearchController searchResultController, Parent searchResultParent) {
		searchResultController.setAdminCon(con);
		searchResultController.setOpener(opener);
		contentStackPane.getChildren().clear();
		contentStackPane.getChildren().add(searchResultParent);
	}

	public void cancel() {
		contentStackPane.getChildren().clear();
	}

	@FXML
	private void faqImageClicked(MouseEvent event) {
		loadFAQ();
	}

	private void loadFAQ() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../faq/faq.fxml"));
			Parent faqView = loader.load();
			contentStackPane.getChildren().clear();
			contentStackPane.getChildren().add(faqView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    
//	OrderManage
	public void orderManageProc() {
		if (opener == null) {
			opener = new Opener();
		}
		opener.orderManageOpen();
	}
	
//	ProductManage
	public void productManageProc() {
		if (opener == null) {
			opener = new Opener();
		}
		opener.productManageOpen();    	
    }
    
    
// MemberManage
	public void memberManageProc() {
		if (opener == null) {
			opener = new Opener();
		}
		opener.memberManageOpen();     	
	}

	public void setAdminMode(boolean isAdminMode) {
		adminLabel.setVisible(isAdminMode);
	}

	public void logoutProc() {
		if (!Login.isLoggedIn()) {
			return;
		}
		Login.setLoggedIn(false);
		opener.mainOpen();
	}

	// 로그인 페이지 진입(주문하기때 사용)
	public void inLogin() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent loginView = loader.load();
			LoginController loginCon = loader.getController();
			loginCon.setOpener(opener);
			loginCon.setAdminCon(con);
			contentStackPane.getChildren().clear();
			contentStackPane.getChildren().add(loginView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 회원가입 진입
			public void inReg(RegController setCon, Parent parent) {
				setCon.setAdminCon(con);
				setCon.setOpener(opener);
				contentStackPane.getChildren().clear();
				contentStackPane.getChildren().add(parent);
			}

}
