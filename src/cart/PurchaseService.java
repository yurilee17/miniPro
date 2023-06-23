package cart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import equipment.CommonService;
import order.OrderInfoDAO;
import order.OrderInfoDTO;
import order.OrderOptionDAO;
import order.OrderOptionDTO;
import product.ProductDAO;
import product.ProductOptionDAO;
import service.Login;

public class PurchaseService {

	private OrderInfoDAO daoInfo;
	private OrderOptionDAO daoOption;

	public PurchaseService() {
		daoInfo = new OrderInfoDAO();
		daoOption = new OrderOptionDAO();
	}

	public Boolean clickOrderBT(ArrayList<BasketDTO> basketItems, String name, String num, String address,String memo, Boolean naverBT, Boolean kakaoBT, Boolean bankTransferBT, Boolean cardBT) {
		
		if(name == "") {
			CommonService.showInformationAlert("알림", "이름을 입력하세요.");
		}else if(num == "") {
			CommonService.showInformationAlert("알림", "전화번호를 입력하세요.");
		}else if(address == "") {
			CommonService.showInformationAlert("알림", "주소를 입력하세요.");
		}if(naverBT == false && kakaoBT == false && bankTransferBT == false && cardBT == false) {
			CommonService.showInformationAlert("알림", "결제 수단을 선택하세요.");
		}else {
			OrderInfoDTO pdDTO = new OrderInfoDTO();
			OrderOptionDTO optionDTO = new OrderOptionDTO();
			int totalPrice = 0;
			for(BasketDTO basketItem : basketItems) {
				totalPrice = totalPrice + basketItem.getPrice();
			};
			pdDTO.setId(Login.getId());
			 LocalDate currentDate = LocalDate.now();
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
		        String formattedDate = currentDate.format(formatter);
		        
			pdDTO.setOrdate(formattedDate);
			pdDTO.setTotalPrice(totalPrice);
			
			if(naverBT == true) {
				pdDTO.setPayment("NAVER PAY");
			}else if(kakaoBT == false) {
				pdDTO.setPayment("KAKAO PAY");
			}else if(bankTransferBT == false) {
				pdDTO.setPayment("무통장 입금");
			}else if(cardBT == false){
				pdDTO.setPayment("카드 결제");
			}
			pdDTO.setRecipient(name);
			pdDTO.setPhoneNum(num);	
			pdDTO.setOradd(address);
			pdDTO.setMemo(memo);
			int ornum = daoInfo.orderAdd(pdDTO);
			for(BasketDTO basketItem : basketItems) {
				optionDTO.setOrnum(ornum);
				optionDTO.setId(Login.getId());
				optionDTO.setStatus("배송준비중");
				optionDTO.setImg(basketItem.getImage());
				optionDTO.setOrname(basketItem.getProductName());
				optionDTO.setPdsize(basketItem.getSize());
				optionDTO.setColor(basketItem.getColor());
				optionDTO.setQuantity(basketItem.getQuantity());
				optionDTO.setUnitPrice(basketItem.getPrice());
				optionDTO.setPdCode(basketItem.getPdCode());
				
				daoOption.orderAdd(optionDTO);
			};
			CommonService.showInformationAlert("주문 완료", "주문 되었습니다. 주문 상태는 주문 내역에서 확인해주세요.");
			return true;
		}return false;
	}
}