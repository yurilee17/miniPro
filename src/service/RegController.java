package service;

import java.net.URL;
import java.util.ResourceBundle;

import equipment.Opener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import member.MemberDAO;

public class RegController implements Initializable {
	private Stage regStage;
	private Parent membership;
	private RegService regService;
	private AdminController adminCon;
	private MenuController menuCon;

	@FXML
	private Button regButton;
	
	public void setMenuCon(MenuController menuCon) {
	    this.menuCon = menuCon;
	}

	public void setAdminCon(AdminController adminCon) {
	    this.adminCon = adminCon;
	}
	public void setRegStage(Stage regStage) {
		this.regStage = regStage;
	}

	public void setRegForm(Parent membership) {
	        this.membership = membership;
	}
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MemberDAO memberDao = new MemberDAO(); 
        regService = new RegService(memberDao);
        regService.setOpener(new Opener());
    }
	
	private Opener opener;
	
	public void setOpener(Opener opener) {
        this.opener = opener;
    }

	public void regProc() {
	    Parent membership = regButton.getScene().getRoot();
	    Boolean result = regService.insert(membership);
	    if(result) {
	    	regStage = (Stage) regButton.getScene().getWindow();
	        regStage.close();
	        opener.setOpener(opener);
	        opener.mainOpen();
	    }
	}
	
    public void cancelProc() {
        regStage = (Stage) regButton.getScene().getWindow();
        regStage.close();
        opener.setOpener(opener);
        opener.mainOpen();
    }
}
