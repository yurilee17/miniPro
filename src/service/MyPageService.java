package service;

import java.util.Optional;

import equipment.CommonService;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import member.MemberDAO;
import member.MemberDTO;

public class MyPageService {
	
	private MemberDAO dao;
	private MemberDTO dto;
	
	public MyPageService() {
		dao = new MemberDAO();
		dto = new MemberDTO();
	}

	public void updateHomeaddress(String text) {
		// 빈칸 입력 시
		if(text == "") {
			CommonService.showInformationAlert("주소 변경", "주소를 입력하세요");
		// 기존의 주소와 동일할 경우
		}else if(Login.getHomeaddress().equals(text)) {
		// 주소 변경
		}else {
			dao.updateOne(text);
			Login.setHomeaddress(text);
			CommonService.showInformationAlert("주소 변경", "주소가 변경되었습니다.");
		}
	}
	
	public Boolean withdrawal() {
		// 버튼 텍스트 변경
        ButtonType yesButton = new ButtonType("확인", ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("취소", ButtonData.CANCEL_CLOSE);
        
     // 알림창 표시 및 사용자의 선택 처리
		Optional<ButtonType> result = CommonService.showConfirmationAlert("탈퇴 확인","탈퇴 하시겠습니까?", yesButton, noButton);
		
		// 탈퇴 버튼 선택 시
		if (result.isPresent() && result.get().equals(yesButton)) {  
			while(true) {
				
				  // 비밀번호 확인
				   Optional<String> resultPw = CommonService.showTextInAlert("비밀번호 확인", "비밀번호를 입력하세요.", "비밀번호 :");
				   
				   // 확인 버튼 선택 시
				   if (resultPw.isPresent()) {
				       String pw = resultPw.get();
				       
				       // 비밀번호 미입력 시
				       if(pw.equals("")) {
				    	   CommonService.showInformationAlert("비밀번호 확인", "비밀번호를 입력하세요."); 
				    	   
				    	// 비밀번호 동일할 때
				       }else if(Login.getPw().equals(pw)) {
				    	   dto.setId(Login.getId());
				    	   dao.mbDelete(dto);
				    	   CommonService.showInformationAlert("탈퇴 완료", "탈퇴 완료되었습니다.");  
					        Login.setLoggedIn(false);
					        return false;
					        
					     // 비밀번호 다를 때
				       }else {
				    	   CommonService.showInformationAlert("비밀번호 확인", "비밀번호가 다릅니다.");
				       }
				       
				       // 취소 버튼 선택 시
				   }else {
					   break;
				   }
			}

		 
		} 
		return true;
	}
}