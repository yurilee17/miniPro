package search;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.ResourceBundle;

import equipment.CommonService;
import equipment.Opener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import product.ProductDAO;
import product.ProductDTO;
import service.AdminController;
import service.Login;
import service.MenuController;

public class SearchController implements Initializable{
	
    @FXML
    private Label loginUserIDLabel;
    
    @FXML
    private StackPane contentStackPane;
    
    @FXML
    private TextField searchfield;
    
    @FXML
    private FlowPane searchResultPane;
    
    private ProductDAO productDAO;

	private Opener opener;
	private Connection con;
	
	private AdminController adminCon;
	private MenuController menuCon;
	    
	public void setMenuCon(MenuController menuCon) {
	    this.menuCon = menuCon;
	}
	
	public void setAdminCon(AdminController adminCon) {
	    this.adminCon = adminCon;
	}
	
	 public void setOpener(Opener opener) {
	        this.opener = opener;
	    }
    // 검색기능
    @FXML
    public void searchProc(MouseEvent event) {
        String searchText = searchfield.getText();
        
        // 검색어 제한
        if(searchText.length() < 2) {
        	 CommonService.msg("검색어는 최소 2글자 이상이어야 합니다.");
        	 return;
        }

        SearchService searchService = new SearchService();
        ArrayList<ProductDTO> products = searchService.searchProducts(searchText);
        
        // 검색 결과가 없을 때
        if (products.isEmpty()) {
            CommonService.msg("검색 결과가 없습니다.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchresult.fxml"));
            Parent searchResultParent = loader.load();

            SearchController searchResultController = loader.getController();
            searchResultController.displayResults(products, searchText);
            
            if(Login.getId() != null && Login.getId().equals("admin")) {
            	System.out.println(adminCon);
            	adminCon.searchImageResult(searchResultController, searchResultParent);
            } else {
            	menuCon.searchImageResult(searchResultController, searchResultParent);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void displayResults(ArrayList<ProductDTO> products, String searchText) {
    	searchResultPane.getChildren().clear(); // 이전에 표시된 결과를 지우기
        searchfield.setText(searchText);
        for (ProductDTO product : products) {
            // 각 상품을 표시하는 컨테이너 생성
            VBox productContainer = new VBox();
            productContainer.setPrefWidth(335.0);
            productContainer.setPrefHeight(240.0);
            productContainer.setAlignment(Pos.CENTER); 
            double topMargin = 20.0; 
            VBox.setMargin(productContainer, new Insets(topMargin, 0, 0, 0));
            String code = product.getPdCode();
            ImageView imageView = new ImageView(new Image(product.getPdImage()));
            imageView.setFitWidth(140); 
            imageView.setFitHeight(140);

            // 상품명 Label
            Label nameLabel = new Label(product.getPdName());

            // 가격 Label
            Label priceLabel = new Label("가격: " + product.getPrice());
            productContainer.getChildren().addAll(imageView, nameLabel, priceLabel);

            // 상품 컨테이너를 FlowPane에 추가
            searchResultPane.getChildren().add(productContainer);

            // 상세페이지 이동
            productContainer.setOnMouseClicked(event -> {
                opener.setMenuCon(menuCon);
                opener.product1DetailOpen(code);
            });
        }

    }

    // 상단 취소버튼
    public void cancelProc() {
    	if(Login.getId() != null && Login.getId().equals("admin")) {
    		adminCon.cancel();
    	} else {
            menuCon.cancel();		
    	}
    }
	
	public void ProductDAO() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "oracle";
		String password = "oracle";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
    	
    }
       