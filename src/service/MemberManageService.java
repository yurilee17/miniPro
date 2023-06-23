package service;

import equipment.CommonService;
import javafx.collections.ObservableList;
import member.MemberDAO;
import member.MemberDTO;

public class MemberManageService {
private MemberDAO dao;


public MemberManageService() {
	dao = new MemberDAO();
}

// 입력한 값 추가하기
public MemberDTO add(ObservableList<MemberDTO> currentData, String id, String pw, String name, String num, int amount, String homeaddress) {
	System.out.println(id + "회원이 DB에 추가되었습니다.");
	//입력 값 없을 경우
	if(id.equals("")) {
		CommonService.msg("아이디를 입력하세요.");
		return null;
		
	//동일한 아이디 존재
	}else{
		 for (MemberDTO member : currentData) {
	         if(member.getId().equals(id)) {
	        	 CommonService.msg("동일한 아이디가 존재합니다.");
	        	 return null;
	         }
	     }
		 // 필수 입력 값 없을 경우
		 if(pw.equals("") || name.equals("") || num.equals("") || homeaddress.equals("")) {
			 CommonService.msg("필수 항목을 입력해주세요.");
			 return null;
		 }
		 
		 //누적금액의 값이 0보다 아래일 경우 (음수일 경우)
		 if(amount < 0) {
			 CommonService.msg("누적금액은 0 이상의 숫자만 입력 가능합니다.");
			 return null;
		 }
		 
		 else{
			 MemberDTO member = new MemberDTO();
		     	member.setId(id);
		     	member.setPw(pw);
		     	member.setName(name);
		     	member.setNum(num);
		     	member.setAmount(0);
		     	member.setHomeaddress(homeaddress);
		     	dao.mbAdd(member);
		     	
		     	CommonService.msg(id + "회원이 DB에 추가되었습니다.");
		     	return member;
		 }
	}

}


// 입력한 값 수정하기
// 등록된 회원이 없을 경우, 필수 입력 값 없을 경우, 누적금액 음수로 입력시 수정하지 않고 알림 발생 예외 추가함
public ObservableList<MemberDTO> update(ObservableList<MemberDTO> currentData, String id, String pw, String name, String num, int amount, String homeaddress) {

	Boolean result = false;
	
	// 등록한 상품이 없을 경우
	if(currentData.isEmpty()) {
		 CommonService.msg("회원 등록 후 이용하세요.");
		 return null;
	}else {
		for (MemberDTO member : currentData) {
	         if(member.getId().equals(id)) {
	        	 
	        	 // 필수 입력 값 없을 경우
	        	 if(id.equals("") || pw.equals("") || name.equals("") || num.equals("") || homeaddress.equals("")) {
					 CommonService.msg("필수 항목을 입력해주세요.");
					 return null;
					 
				// 누적금액 음수로 입력시
				 }else if(amount < 0){
					 CommonService.msg("누적금액은 0 이상의 숫자만 입력 가능합니다.");
					 return null;
					 
				 }
				 else{
	        	member.setId(id);
	        	member.setPw(pw);
	        	member.setName(name);
	        	member.setNum(num);
	        	member.setAmount(amount);
	        	member.setHomeaddress(homeaddress);
	 	     	dao.mbUpdate(member);
	 	     	
	 	     	CommonService.msg(id + "회원의 정보가 수정되었습니다.");
	 	     	result = true;
	         	return currentData;
				 }
	         }
		 }
		
		//동일한 상품명 존재하지 않을 때
		if(result == false) {
			 CommonService.msg("존재하지 않는 회원의 정보입니다.");
		}
		return null;
	}
	
}

// 입력한 값 삭제하기
// 동일한 상품명 존재하지 않을 때 삭제하지 않고 알림 발생 예외 추가함
public ObservableList<MemberDTO> delete(ObservableList<MemberDTO> currentData, String id) {
	Boolean result = false;
	
	for (MemberDTO member : currentData) {
        if(member.getId().equals(id)) {
        	currentData.remove(member);
        	dao.mbDelete(member);
        	
        	CommonService.msg(id + "회원의 정보가 삭제되었습니다.");
 	     	result = true;
        	return currentData;
        }

    }         
	//동일한 상품명 존재하지 않을 때
	if(result == false) {
		 CommonService.msg("존재하지 않는 아이디입니다.");
	}
	return null;
}

}