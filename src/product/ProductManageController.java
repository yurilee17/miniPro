package product;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import equipment.Opener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProductManageController implements Initializable{
	
		private Opener opener;
	
		private String currentSize, currentColor;
	
		private ProductDAO pdDao;
		private ProductOptionDAO otDao;
		private ProductManageService service;
		
		// 테이블들
	    @FXML
	    private TableView<ProductDTO> products;
	    @FXML
	    private TableView<ProductOptionDTO> options;
	    
	    // products 칼럼 값들
	    @FXML
	    private TableColumn<ProductDTO, String> pdCodeCol;
	    @FXML
	    private TableColumn<ProductDTO, String> categoryCol;
	    @FXML
	    private TableColumn<ProductDTO, String> pdNameCol;
	    @FXML
	    private TableColumn<ProductDTO, Integer> priceCol;
	    @FXML
	    private TableColumn<ProductDTO, String> pdImageCol;
	    @FXML
	    private TableColumn<ProductDTO, Integer> viewCol;
	    @FXML
	    private TableColumn<ProductDTO, Double> reviewCol;
	    
	    // options 칼럼 값들
	    @FXML
	    private TableColumn<ProductOptionDTO, String> optionCodeCol;
	    @FXML
	    private TableColumn<ProductOptionDTO, String> optionSizeCol;  
	    @FXML
	    private TableColumn<ProductOptionDTO, String> optionColorCol;
	    @FXML
	    private TableColumn<ProductOptionDTO, Integer> optionQuantityCol;
	    
	    // products 입력 창
	    @FXML
	    private TextField inputPdCode;
	    @FXML
	    private TextField inputPdName;
	    @FXML
	    private TextField inputPdImage;
	    @FXML
	    private ComboBox<String> inputCategory;
	    @FXML
	    private TextField inputPrice;
	    @FXML
	    private TextField inputView;
	    @FXML
	    private TextField inputReview;
	    @FXML
	    private TextField inputExplanation;

	    // options 입력 창
	    @FXML
	    private TextField inputSize;
	    @FXML
	    private TextField inputColor;
	    @FXML
	    private TextField inputPdQuantity;
	    
	    
	    public void setOpener(Opener opener) {
	        this.opener = opener;
	    }
	    
	    @Override
	    public void initialize(URL url, ResourceBundle resourceBundle) {
	    	pdDao = new ProductDAO();
	    	otDao = new ProductOptionDAO();
	    	service = new ProductManageService();
	    	
	    	// products(explanation:설명은 테이블에 나타내지 않음)
	    	pdCodeCol.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("pdCode"));
	    	categoryCol.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("category"));
	    	pdNameCol.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("pdName"));
	    	priceCol.setCellValueFactory(new PropertyValueFactory<ProductDTO, Integer>("price"));
	    	pdImageCol.setCellValueFactory(new PropertyValueFactory<ProductDTO, String>("pdImage"));
	    	viewCol.setCellValueFactory(new PropertyValueFactory<ProductDTO, Integer>("view"));
	    	reviewCol.setCellValueFactory(new PropertyValueFactory<ProductDTO, Double>("review"));
	    	setupProductsTable();
	    	
	    	// options
	 	    optionCodeCol.setCellValueFactory(new PropertyValueFactory<ProductOptionDTO, String>("otCode"));
	 	    optionSizeCol.setCellValueFactory(new PropertyValueFactory<ProductOptionDTO, String>("otSize"));
	 	    optionColorCol.setCellValueFactory(new PropertyValueFactory<ProductOptionDTO, String>("otColor"));
	 	    optionQuantityCol.setCellValueFactory(new PropertyValueFactory<ProductOptionDTO, Integer>("otQuantity"));
	 	    
	    }
	    
	    // X버튼 선택 시 창 종료
	    public void cancel() {
	    	opener.adminOpen();
		}
	    
	    // products DB의 값 나타내기
	    private void setupProductsTable(){
	    	
	    	ArrayList<ProductDTO> setData = pdDao.selectAll();
	    	for (ProductDTO product : setData) {
	    		products.getItems().add(product);
	        }
	    	
	    	
	    }
	    
	    // options DB의 값 나타내기
	    private void setupOptionsTable(String code){
	    	
	    	ObservableList<ProductOptionDTO> currentData = options.getItems();
	    	currentData.clear();
	    	ArrayList<ProductOptionDTO> setData = otDao.selectCode(code);
	    	for (ProductOptionDTO option : setData) {
	    		currentData.add(option);
	        }
	    	
	    	options.setItems(currentData);
	    	options.refresh();
	    	
	    }
	    
	    
	    
	    // products 입력한 값 추가하기
	    @FXML
	    public void addPd(ActionEvent event) {
	    	
	        ObservableList<ProductDTO> currentData = products.getItems();
	        String pdCode = inputPdCode.getText();
	        String pdName = inputPdName.getText();
	        String category;
	        if(inputCategory.getValue() == null)
	        	category = "";
	        else
	        	category = inputCategory.getValue();
     
	        int price;
	        String priceNum = "";
	        if (inputPrice.getText().equals("")) {
	        	price = 0;
				priceNum = "null";
	        }else {
	        	try {
	        		price = Integer.parseInt(inputPrice.getText());
	        		
		        } catch (Exception e) {
		        	price = 0;
					priceNum = "notnum";
				}
	        }
	        
	        
	        String pdImage = inputPdImage.getText();
	        String explanation = inputExplanation.getText();
	        
	        
	        ProductDTO dto = service.addPd(currentData, pdCode, pdName, category, price, priceNum, pdImage, explanation);
	        
	        if (dto != null){
            	
            	products.getItems().add(dto);
            	products.refresh();
	        }
  
	    }
	    
	    
	 // products 입력한 값 수정하기
	    @FXML
	    public void updatePd(ActionEvent event) {
	        ObservableList<ProductDTO> currentData = products.getItems();
	        String pdCode = inputPdCode.getText();
	        String pdName = inputPdName.getText();
	        String category = inputCategory.getValue();
        
	        int price;
	        String priceNum = "";
	        if (inputPrice.getText().equals("")) {
	        	price = 0;
				priceNum = "null";
	        }else {
	        	try {
	        		price = Integer.parseInt(inputPrice.getText());
	        		
		        } catch (Exception e) {
		        	price = 0;
					priceNum = "notnum";
				}
	        }
	        
	        
	        String pdImage = inputPdImage.getText();
	        String explanation = inputExplanation.getText();

	        ObservableList<ProductDTO> updateData = service.updatePd(currentData, pdCode, pdName, category, price, priceNum, pdImage, explanation);
	        
	        if (updateData != null){
	        	products.setItems(updateData);
	        	products.refresh();
	        }
	    }
	    
	    // products 입력한 값 삭제하기
	    @FXML
	    public void deletePd(ActionEvent event) {
	        ObservableList<ProductDTO> currentData = products.getItems();
	        String pdCode = inputPdCode.getText();

	        ObservableList<ProductDTO> updateData = service.delete(currentData, pdCode);
	        
	        if (updateData != null){
		        products.setItems(updateData);
	        	products.refresh();
	        }
         
	    }
	    
	 // options 입력한 값 추가하기
	    @FXML
	    public void addOt(ActionEvent event) {
	        ObservableList<ProductOptionDTO> currentData = options.getItems();
	        String size = inputSize.getText();
	        String color = inputColor.getText();
	        
	        int pdQuantity;
	        String quantityNum = "";
	        if (inputPdQuantity.getText().equals("")) {
	        	pdQuantity = 0;
	        	quantityNum = "null";
	        }else {
		        try {
	        		pdQuantity = Integer.parseInt(inputPdQuantity.getText());
				} catch (Exception e) {
					pdQuantity = 0;
					quantityNum = "notnum";
				}
	        }
	        // 코드 존재 여부 확인
	    	ObservableList<ProductDTO> pdData = products.getItems();
	    	Boolean codeIn = false;
	    	String pdCode = inputPdCode.getText();
	    	for (ProductDTO product : pdData) {
	            if(product.getPdCode().equals(pdCode)) {
	            	codeIn = true;
	            }
	        }
	    	
	        ProductOptionDTO dto = service.addOt(currentData, pdCode, codeIn, size, color, pdQuantity, quantityNum);
	        
	        if (dto != null){
            	
	        	options.getItems().add(dto);
	        	options.refresh();
	        }
  
	    }
	    
	    
	 // options 입력한 값 수정하기
	    @FXML
	    public void updateOt(ActionEvent event) {
	    	// 선택한 값 받아오기
	    	ProductOptionDTO clickeOption= options.getSelectionModel().getSelectedItem();
	    	if(clickeOption != null) {
		    	this.currentSize = String.valueOf(clickeOption.getOtSize());
			    this.currentColor = String.valueOf(clickeOption.getOtColor());
	    	}
	    	
	        ObservableList<ProductOptionDTO> currentData = options.getItems();
	        String size = inputSize.getText();
	        String color = inputColor.getText();
	        
	        int pdQuantity;
	        String quantityNum = "";
	        if (inputPdQuantity.getText().equals("")) {
	        	pdQuantity = 0;
	        	quantityNum = "null";
	        }else {
	        	try {
	        		pdQuantity = Integer.parseInt(inputPdQuantity.getText());
	        		
		        } catch (Exception e) {
					pdQuantity = 0;
					quantityNum = "notnum";
				}
	        }

	        
	     // 코드 존재 여부 확인
	    	ObservableList<ProductDTO> pdData = products.getItems();
	    	Boolean codeIn = false;
	    	String pdCode = inputPdCode.getText();
	    	for (ProductDTO product : pdData) {
	            if(product.getPdCode().equals(pdCode)) {
	            	codeIn = true;
	            }
	        }
	    	
	        ObservableList<ProductOptionDTO> updateData = service.updateOt(currentData, pdCode, codeIn, currentSize, currentColor, size, color, pdQuantity, quantityNum);
	        
	        if (updateData != null){
	        	options.setItems(updateData);
	        	options.refresh();
	        }

	    }
	    
	    // options 입력한 값 삭제하기
	    @FXML
	    public void deleteOt(ActionEvent event) {
	        ObservableList<ProductOptionDTO> currentData = options.getItems();
	        String size = inputSize.getText();
	        String color = inputColor.getText();
	        
	     // 코드 존재 여부 확인
	    	ObservableList<ProductDTO> pdData = products.getItems();
	    	Boolean codeIn = false;
	    	String pdCode = inputPdCode.getText();
	    	for (ProductDTO product : pdData) {
	            if(product.getPdCode().equals(pdCode)) {
	            	codeIn = true;
	            }
	        }

	        ObservableList<ProductOptionDTO> updateData = service.deleteOt(currentData, pdCode, codeIn, size, color);
	        
	        if (updateData != null){
	        	options.setItems(updateData);
	        	options.refresh();
	        }
         
	    }
	    


	    // products 선택 시 값 가져오기
	    @FXML
	    void rowClickedProduct(MouseEvent event) {
	    		ProductDTO clickeProduct= products.getSelectionModel().getSelectedItem();
	    		
	    	
	    	if(clickeProduct != null){
	    		String code = String.valueOf(clickeProduct.getPdCode());
		    	String explanation = pdDao.getExplanation(code);
		    	
	    		inputPdCode.setText(String.valueOf(clickeProduct.getPdCode()));
	    		inputPdName.setText(String.valueOf(clickeProduct.getPdName()));
		    	inputPdImage.setText(String.valueOf(clickeProduct.getPdImage()));
			    inputCategory.setValue(String.valueOf(clickeProduct.getCategory()));
			    inputPrice.setText(String.valueOf(clickeProduct.getPrice()));
			    inputView.setText(String.valueOf(clickeProduct.getView()));
			    inputReview.setText(String.valueOf(clickeProduct.getReview()));
			    inputExplanation.setText(explanation);
			    
			    this.currentSize = "";
			    this.currentColor = "";
			    
			    setupOptionsTable(code);
	    	}
	    	
	    	
	    }
	    
	 // options 선택 시 값 가져오기
	    @FXML
	    void rowClickedOption(MouseEvent event) {
	    	ProductOptionDTO clickeOption= options.getSelectionModel().getSelectedItem();
	    	
		    
	    	if(clickeOption != null) {
			    inputSize.setText(String.valueOf(clickeOption.getOtSize()));
			    inputColor.setText(String.valueOf(clickeOption.getOtColor()));
			    inputPdQuantity.setText(String.valueOf(clickeOption.getOtQuantity()));
			    
	    	}
	    	
	    	
	    }

	    

	    
	}