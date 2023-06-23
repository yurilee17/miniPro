package notice;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.Login;

public class NoticeController implements Initializable {
	private NoticeService noticeService;
	private NoticeDAO noticeDao;
	
    @FXML
    private Label date;
    
    @FXML
    private Label boardsubject;
    
    @FXML
    private Label boarddate;
    
    @FXML
    private Text boardcontent;
    
    @FXML
    private Button noticewritebutton;
    
    @FXML
    private Button noticewriteButton;
    
    @FXML
    private TableView<NoticeDTO> noticetableview;
    
    @FXML
    private TableColumn<NoticeDTO, String> subject;

    @FXML
    private TableColumn<NoticeDTO, String> noticedate;
	
	@FXML
    private TextField noticesubject;
	
	@FXML
    private TextArea noticecontent;
	
    @FXML
    private Button boardwritebutton;
    
    @FXML
    private Button boardmodifybutton;
    
    @FXML
    private Button boarddeletebutton;
	
    @FXML
    private StackPane contentStackPane;
    
    public void setContentStackPane(StackPane contentStackPane) {
        this.contentStackPane = contentStackPane;
    }
 
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	noticeService = new NoticeService(noticeDao);

        List<NoticeDTO> noticeList = noticeService.getAllNotices();
        ObservableList<NoticeDTO> observableList = FXCollections.observableArrayList(noticeList);
        
        if (subject == null || noticedate == null || noticetableview == null) {
        	subject = new TableColumn<NoticeDTO, String>();
        	noticedate = new TableColumn<NoticeDTO, String>();
        	noticetableview = new TableView<NoticeDTO>();
        }
        
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        noticedate.setCellValueFactory(new PropertyValueFactory<>("day"));
        noticetableview.setItems(observableList);

        boolean isLoggedIn = Login.isLoggedIn(); 
        boolean isAdminLoggedIn = isLoggedIn && "admin".equals(Login.getId());
        
        if (noticewritebutton != null) {
            noticewritebutton.setVisible(isAdminLoggedIn);
        }
        
        if (boardwritebutton != null || boardmodifybutton != null || boarddeletebutton != null) {
        	boardwritebutton.setVisible(isAdminLoggedIn);
        	boardmodifybutton.setVisible(isAdminLoggedIn);
        	boarddeletebutton.setVisible(isAdminLoggedIn);
        }
               
        // 공지사항 게시판에서 게시글을 더블클릭했을 때
        noticetableview.setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                // 더블클릭 이벤트 처리
                NoticeDTO selectedItem = noticetableview.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // 선택된 항목의 글제목과 글내용을 다음 페이지로 전달하고 페이지 전환 수행
                    String title = selectedItem.getSubject();
                    String content = selectedItem.getContent();
                    String date = selectedItem.getDay();
                    openNoticeBoard(title, content, date);
                }
            }
        });
    }
    
    // 게시판 목록에서 게시글을 클릭할 때 게시물 컨텐츠를 뽑아오는 메서드
    public void setNoticeData(String title, String content, String day) {
        boardsubject.setText(title);
        boardcontent.setText(content);
        boarddate.setText(day);
    }

    // 공지사항 게시판에서 게시글을 더블클릭했을 때 이어지는~
    private void openNoticeBoard(String title, String content, String day) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("noticeboard.fxml"));
            Parent noticeBoardView = loader.load();
            NoticeController noticeController = loader.getController();
            noticeController.setNoticeData(title, content, day);
            StackPane contentStackPane = (StackPane) noticewritebutton.getScene().lookup("#contentStackPane");
            
            if (contentStackPane != null) {
                contentStackPane.getChildren().clear();
                contentStackPane.getChildren().add(noticeBoardView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public NoticeController() {
        noticeDao = new NoticeDAO();
        noticeService = new NoticeService(noticeDao);
    }
    
    // 공지사항 첫 창(notice.fxml)에서 글쓰기 버튼을 눌렀을 때
    public void adminwriteProc(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("noticemanage.fxml"));
            Parent noticewriteView = loader.load();
            StackPane contentStackPane = (StackPane) noticewritebutton.getScene().lookup("#contentStackPane");
            Label date = (Label) noticewriteView.lookup("#date"); // noticemanage.fxml에서 date에 해당하는 Label 찾기
            if (contentStackPane != null && date != null) {
                contentStackPane.getChildren().clear();
                contentStackPane.getChildren().add(noticewriteView);
                
                Date currentDate = new Date();
                SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
                String outputDate = simpledate.format(currentDate);
                date.setText(outputDate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
    
    // 공지사항 관리 페이지(noticemanage.fxml)에서 글작성 버튼을 눌렀을 때
    public void noticewriteProc(MouseEvent event) {
        String subject = noticesubject.getText();
        String content = noticecontent.getText();
        String publishDate = date.getText();

        if (!subject.isEmpty() && !content.isEmpty()) {
        	NoticeDTO noticeDTO = new NoticeDTO(0, subject, publishDate, content);
            noticeService.insert(noticeDTO);
            System.out.println("공지사항 작성 완료.");
            noticetableview.getItems().add(noticeDTO);
            // 글쓰기 버튼을 누르면 다시 notice.fxml로 돌아감
	    	try {
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("notice.fxml"));
	            Parent noticeView = loader.load();
	            StackPane contentStackPane = (StackPane) noticewriteButton.getScene().lookup("#contentStackPane");
	            if (contentStackPane != null) {
	            	contentStackPane.getChildren().clear();
	            	contentStackPane.getChildren().add(noticeView);
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
    
    
 // 게시글을 클릭해서 들어갔을때(noticeboard.fxml) 뒤로가기 버튼
    @FXML
    private void boardbackProc(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("notice.fxml"));
            Parent noticeView = loader.load();
            StackPane contentStackPane = (StackPane) boardwritebutton.getScene().lookup("#contentStackPane");
            if (contentStackPane != null) {
                contentStackPane.getChildren().clear();
                contentStackPane.getChildren().add(noticeView);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    // 게시글을 클릭해서 들어갔을때 글쓰기 버튼
    public void writeProc(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("noticemanage.fxml"));
            Parent noticewriteView = loader.load();
            StackPane contentStackPane = (StackPane) boardwritebutton.getScene().lookup("#contentStackPane");
            Label date = (Label) noticewriteView.lookup("#date"); // noticemanage.fxml에서 date에 해당하는 Label 찾기
            if (contentStackPane != null && date != null) {
                contentStackPane.getChildren().clear();
                contentStackPane.getChildren().add(noticewriteView);
                
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
    public void setNoticeData2(String title, String content, String day) {
        noticesubject.setText(title);
        noticecontent.setText(content);
        date.setText(day);
    }
    
    // 게시글을 클릭해서 들어갔을때 수정 버튼
    public void modifyProc(MouseEvent event) {
        String subject = boardsubject.getText();
        String content = boardcontent.getText();
        String publishDate = boarddate.getText();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("noticemanage.fxml"));
            Parent noticeBoardView = loader.load();

            NoticeController noticeController = loader.getController();
            noticeController.setNoticeData2(subject, content, publishDate);

            StackPane contentStackPane = (StackPane) boardwritebutton.getScene().lookup("#contentStackPane");
            if (contentStackPane != null) {
                contentStackPane.getChildren().clear();
                contentStackPane.getChildren().add(noticeBoardView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 

    // 게시글을 클릭해서 들어갔을때 삭제 버튼
    public void deleteProc(MouseEvent event) {
	    boolean result = CommonService.showConfirmationDialog("확인", "정말로 삭제하시겠습니까?");
	    if (result) {
	        ObservableList<NoticeDTO> currentData = noticetableview.getItems();
	        String subject = boardsubject.getText();
	        ObservableList<NoticeDTO> updateData = noticeService.delete(currentData, subject);
	        if (updateData != null) {
	        	noticetableview.setItems(updateData);
	        	noticetableview.refresh();
	        }
	        
	    	try {
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("notice.fxml"));
	            Parent noticeView = loader.load();
	            StackPane contentStackPane = (StackPane) boarddeletebutton.getScene().lookup("#contentStackPane");
	            if (contentStackPane != null) {
	            	contentStackPane.getChildren().clear();
	            	contentStackPane.getChildren().add(noticeView);
	            }
	            
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
	    	
	    } else {
	        return;
	    }
    }
}

