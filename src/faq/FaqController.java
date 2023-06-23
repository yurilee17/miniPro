package faq;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import equipment.CommonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.Login;

public class FaqController implements Initializable {
	private FaqService faqService;
	private FaqDAO faqDao;
	
	@FXML
	private Label date;
	
	@FXML
	private Label boardsubject;
	
	@FXML
    private Label boarddate;
	
	@FXML
	private Text boardcontent;
	
	@FXML
	private Button faqwritebutton;
	
	@FXML
	private Button faqwriteButton;
	
	@FXML
	private TableView<FaqDTO> faqtableview;
	
	@FXML
	private TableColumn<FaqDTO, String> subject;
	
	@FXML
	private TableColumn<FaqDTO, String> faqdate;
	
	@FXML
	private TextField faqsubject;
	
	@FXML
	private TextArea faqcontent;
	
    @FXML
    private Button boardwritebutton;
    
    @FXML
    private Button boardmodifybutton;
    
    @FXML
    private Button boarddeletebutton;
    
    @FXML
    private StackPane contentStackPane;
	
	
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	faqService = new FaqService(faqDao);
    	
    	List<FaqDTO> faqList = faqService.getAllFaqs();
    	ObservableList<FaqDTO> observableList = FXCollections.observableArrayList(faqList);
    	
        if (subject == null || faqdate == null || faqtableview == null) {
        	subject = new TableColumn<FaqDTO, String>();
        	faqdate = new TableColumn<FaqDTO, String>();
        	faqtableview = new TableView<FaqDTO>();
        }
    	
    	subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        faqdate.setCellValueFactory(new PropertyValueFactory<>("faqdate"));
        faqtableview.setItems(observableList);
    	
        boolean isLoggedIn = Login.isLoggedIn(); // 로그인 상태 가져오기
        boolean isAdminLoggedIn = isLoggedIn && "admin".equals(Login.getId()); // admin으로 로그인되었다고 가정
        if (faqwritebutton != null) {
            faqwritebutton.setVisible(isAdminLoggedIn);
        }
        
        if (boardwritebutton != null || boardmodifybutton != null || boarddeletebutton != null) {
        	boardwritebutton.setVisible(isAdminLoggedIn);
        	boardmodifybutton.setVisible(isAdminLoggedIn);
        	boarddeletebutton.setVisible(isAdminLoggedIn);
        }
        
      // FAQ 게시판에서 게시글을 더블클릭했을 때  
      faqtableview.setOnMousePressed(event -> {
    	  if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
    		  // 더블클릭 이벤트 처리
    		  FaqDTO selectedItem = faqtableview.getSelectionModel().getSelectedItem();
    		  if (selectedItem != null) {
    			  // 선택된 항목의 글제목과 글내용을 다음 페이지로 전달하고 페이지 전환 수행
    			  String title = selectedItem.getSubject();
    			  String content = selectedItem.getContent();
    			  String date = selectedItem.getDay();
    			  openFaqBoard(title, content, date);
    		  }
    	  }
      });
    }
    
    // 게시판에서 게시글을 더블클릭했을 때 이어지는~
	private void openFaqBoard(String title, String content, String day) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("faqboard.fxml"));
			Parent faqBoardView = loader.load();
			FaqController faqController = loader.getController();
			faqController.setFaqData(title, content, day);
			StackPane contentStackPane = (StackPane) faqwritebutton.getScene().lookup("#contentStackPane");
			
            if (contentStackPane != null) {
                contentStackPane.getChildren().clear();
                contentStackPane.getChildren().add(faqBoardView);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
      
    public FaqController() {
    	faqDao = new FaqDAO();
    	faqService = new FaqService(faqDao);
    }
	
    // FAQ 첫 창(faq.fxml)에서 글쓰기 버튼을 눌렀을 때
    public void adminwriteProc(MouseEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("faqmanage.fxml"));
            Parent faqwriteView = loader.load();
            StackPane contentStackPane = (StackPane) faqwritebutton.getScene().lookup("#contentStackPane");
            Label date = (Label) faqwriteView.lookup("#date");
            if (contentStackPane != null && date != null) {
            	contentStackPane.getChildren().clear();
            	contentStackPane.getChildren().add(faqwriteView);

            	Date currentDate = new Date();
            	SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
            	String outputDate = simpledate.format(currentDate);
            	date.setText(outputDate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setFaqService(FaqService faqService) {
    	this.faqService = faqService;
    }

    // FAQ 관리 페이지(faqmanage.fxml)에서 글작성 버튼을 눌렀을 때
    public void faqwriteProc(MouseEvent event) {
        String subject = faqsubject.getText();
        String content = faqcontent.getText();
        String publishDate = date.getText();

        if (!subject.isEmpty() && !content.isEmpty()) {
        	FaqDTO faqDTO = new FaqDTO(0, subject, publishDate, content);
            faqService.insert(faqDTO);
            System.out.println("FAQ 작성 완료.");
            faqtableview.getItems().add(faqDTO);
                  
            // 글쓰기 버튼을 누르면 다시 faq.fxml로 돌아감
	    	try {
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("faq.fxml"));
	            Parent faqView = loader.load();
	            StackPane contentStackPane = (StackPane) faqwriteButton.getScene().lookup("#contentStackPane");
	            if (contentStackPane != null) {
	            	contentStackPane.getChildren().clear();
	            	contentStackPane.getChildren().add(faqView);
	            }
	            
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}

        } else if (subject.isEmpty()) {
            CommonService.msg("글제목을 정확히 입력하세요.");
        } else if (!subject.isEmpty() && content.isEmpty()) {
            CommonService.msg("글내용을 정확히 입력하세요.");
        }
    }
    
    // 게시글을 클릭해서 들어갔을때(faqboard.fxml) 뒤로가기 버튼
    @FXML
    private void boardbackProc(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("faq.fxml"));
            Parent faqView = loader.load();
            StackPane contentStackPane = (StackPane) boardwritebutton.getScene().lookup("#contentStackPane");
            if (contentStackPane != null) {
                contentStackPane.getChildren().clear();
                contentStackPane.getChildren().add(faqView);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // 게시판 목록에서 게시글을 클릭할 때 게시물 컨텐츠를 뽑아오는 메서드
    public void setFaqData(String title, String content, String day) {
        boardsubject.setText(title);
        boardcontent.setText(content);
        boarddate.setText(day);
    }
    
    public void writeProc(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("faqmanage.fxml"));
            Parent faqwriteView = loader.load();
            StackPane contentStackPane = (StackPane) boardwritebutton.getScene().lookup("#contentStackPane");
            Label date = (Label) faqwriteView.lookup("#date"); // faqmanage.fxml에서 date에 해당하는 Label 찾기
            if (contentStackPane != null && date != null) {
                contentStackPane.getChildren().clear();
                contentStackPane.getChildren().add(faqwriteView);
                
                Date currentDate = new Date();
                SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
                String outputDate = simpledate.format(currentDate);
                date.setText(outputDate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }	   	
    }
    
    // 게시글 안에서 수정 버튼을 누를때 게시물 컨텐츠를 뽑아오는 메서드
    public void setFaqData2(String title, String content, String day) {
        faqsubject.setText(title);
        faqcontent.setText(content);
        date.setText(day);
    }
    
 // 게시글을 클릭해서 들어갔을때 수정 버튼
    public void modifyProc(MouseEvent event) {
        String subject = boardsubject.getText();
        String content = boardcontent.getText();
        String publishDate = boarddate.getText();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("faqmanage.fxml"));
            Parent faqBoardView = loader.load();

            FaqController faqController = loader.getController();
            faqController.setFaqData2(subject, content, publishDate);

            StackPane contentStackPane = (StackPane) boardwritebutton.getScene().lookup("#contentStackPane");
            if (contentStackPane != null) {
                contentStackPane.getChildren().clear();
                contentStackPane.getChildren().add(faqBoardView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    public void deleteProc(MouseEvent event) {
	    boolean result = CommonService.showConfirmationDialog("확인", "정말로 삭제하시겠습니까?");
	    if (result) {
	        ObservableList<FaqDTO> currentData = faqtableview.getItems();
	        String subject = boardsubject.getText();
	        ObservableList<FaqDTO> updateData = faqService.delete(currentData, subject);
	        if (updateData != null) {
	        	faqtableview.setItems(updateData);
	        	faqtableview.refresh();
	        }
	        
	    	try {
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("faq.fxml"));
	            Parent faqView = loader.load();
	            StackPane contentStackPane = (StackPane) boarddeletebutton.getScene().lookup("#contentStackPane");
	            if (contentStackPane != null) {
	            	contentStackPane.getChildren().clear();
	            	contentStackPane.getChildren().add(faqView);
	            }
	            
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
	    	
	    } else {
	        return;
	    }
    }
}
