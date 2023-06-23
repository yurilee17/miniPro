package service;

import java.util.ArrayList;

import equipment.CommonService;
import equipment.Opener;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import member.MemberDAO;
import member.MemberDTO;

public class RegService {
    private MemberDAO memberDao;
    private Opener opener;
    
    public RegService(MemberDAO memberDao) {
        this.memberDao = memberDao;
    }
    
    public void setOpener(Opener opener) {
        this.opener = opener;
    }
    
    public Opener getOpener() {
        return opener;
    }
    
    public Boolean insert(Parent membership) {
        Button regButton = (Button) membership.lookup("#regButton");
        PasswordField pwFld = (PasswordField) membership.lookup("#pw");
        PasswordField confirmFld = (PasswordField) membership.lookup("#confirm");
        
        if (pwFld.getText().equals(confirmFld.getText())) {
            TextField idFld = (TextField) membership.lookup("#id");
            if (!idFld.getText().isEmpty()) {
                TextField nameFld = (TextField) membership.lookup("#name");
                TextField num = (TextField) membership.lookup("#phonenumber");
                TextField address = (TextField) membership.lookup("#homeaddress");
                int amount = 0;

                if (memberDao != null) {
                    ArrayList<MemberDTO> members = memberDao.selectAll();
                    for (MemberDTO member : members) {
                        if (member.getId().equals(idFld.getText())) {
                            CommonService.msg("이미 존재하는 아이디입니다. 다른 아이디를 입력하세요.");
                            return false;
                        } else if (pwFld.getText().equals("")) {
                       	 CommonService.msg("비밀번호를 입력하세요.");
                         return false;
                        } else if (nameFld.getText().equals("")) {
                          	 CommonService.msg("이름을 입력하세요.");
                             return false;
                            }else if (num.getText().equals("")) {
                             	 CommonService.msg("휴대폰 번호를 입력하세요.");
                                 return false;
                                }
                            else if (address.getText().equals("")) {
                            	 CommonService.msg("주소를 입력하세요.");
                                return false;
                               }
                    }
                    memberDao.insert(idFld.getText(), pwFld.getText(), nameFld.getText(), num.getText(), amount, address.getText());
                    System.out.println("회원정보 저장 완료.");
                    return true;
                } else {
                    CommonService.msg("회원 가입 실패: MemberDAO 객체를 생성할 수 없습니다.");
                    return false;
                }
            } else {
                CommonService.msg("아이디를 입력하고 다시 시도하세요.");
                return false;
            }
        } else {
            CommonService.msg("비밀번호가 일치하지 않습니다.");
            return false;
        }
    }
    
    
    
}
