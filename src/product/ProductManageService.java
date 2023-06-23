package product;

import equipment.CommonService;
import javafx.collections.ObservableList;

public class ProductManageService {
	private ProductDAO pdDao;
	private ProductOptionDAO otDao;
	
	public ProductManageService() {
		pdDao = new ProductDAO();
		otDao = new ProductOptionDAO();
	}

	// 입력한 상품 값 추가하기
	// 입력 값 없을 경우, 동일한 상품코드 존재, 필수 입력 값 없을 경우, 가격 0원 이하, 임의 값 입력 시 추가하지 않고 알림 발생 예외 추가함
	public ProductDTO addPd(ObservableList<ProductDTO> currentData, String pdCode, String pdName,String category, int price, String pricenum, String pdImage, String explanation) {
		
		//입력 값 없을 경우
		if(pdCode.equals("")) {
			CommonService.msg("상품코드를 입력하세요.");
			return null;
			
		//동일한 상품코드 존재
		}else{
			 for (ProductDTO product : currentData) {
		         if(product.getPdCode().equals(pdCode)) {
		        	 CommonService.msg("동일한 상품코드가 존재합니다.");
		        	 return null;
		         }
		     }
			 // 필수 입력 값 없을 경우
			 if(pdName.equals("") || category.equals("") || pricenum == "null" || pdImage.equals("")) {
				 CommonService.msg("필수 항목을 입력해주세요.");
				 return null;
			// 가격 0원 이하, 임의 값 입력 시
			 }else if(price < 0 || pricenum == "notnum"){
				 CommonService.msg("가격은 0 이상의 숫자만 입력 가능합니다.");
				 return null;
			 }
			 else{
				 ProductDTO product = new ProductDTO();
				 
				 	product.setPdCode(pdCode);
			     	product.setPdName(pdName);
			     	product.setCategory(category);
			     	product.setPrice(price);
			     	product.setPdImage(pdImage);
			     	product.setView(0);
			     	product.setReview(0.0);
			     	product.setExplanation(explanation);
			     	pdDao.pdAdd(product);
			     	
			     	CommonService.msg("\"" + pdCode + "\" 상품 정보가 추가되었습니다.");
			     	return product;
			 }
		}

	}
	
	
	// 입력한 상품 값 수정하기    ,String size,String color,int pdQuantity   size.equals("") || color.equals("") || pdQuantity == -1 ||
	// 등록한 상품이 없을 경우, 입력 값 없을 경우, 동일한 상품코드 존재하지 않을 때, 필수 입력 값 없을 경우, 가격 0원 이하, 임의 값 입력 시 수정하지 않고 알림 발생 예외 추가함 
	public ObservableList<ProductDTO> updatePd(ObservableList<ProductDTO> currentData, String pdCode, String pdName,String category, int price, String pricenum, String pdImage, String explanation) {

		Boolean result = false;
		
		// 등록한 상품이 없을 경우
		if(currentData.isEmpty()) {
			 CommonService.msg("상품 등록 후 이용하세요.");
			 return null;
			 
		//입력 값 없을 경우
		}else if(pdCode.equals("")) {
			CommonService.msg("상품코드를 입력하세요.");
			return null;
		}else {
			for (ProductDTO product : currentData) {
		         if(product.getPdCode().equals(pdCode)) {
		        	 
		        	 // 필수 입력 값 없을 경우
		        	 if(pdName.equals("") || category.equals("") || pricenum == "null" || pdImage.equals("")) {
						 CommonService.msg("필수 항목을 입력해주세요.");
						 return null;
						 
						// 가격 0원 이하, 임의 값 입력 시
					 }else if(price < 0 || pricenum == "notnum"){
						 CommonService.msg("가격은 0 이상의 숫자만 입력 가능합니다.");
						 return null;
					 }
					 else{
						product.setPdCode(pdCode);
			        	product.setPdName(pdName);
			 	     	product.setCategory(category);
			 	     	product.setPrice(price);
			 	     	product.setPdImage(pdImage);
			 	     	product.setExplanation(explanation);
			 	     	pdDao.pdUpdate(product);
			 	     	
			 	     	CommonService.msg("\"" + pdCode + "\" 상품 정보가 수정되었습니다.");
			 	     	result = true;
			         	return currentData;
					 }
		         }
			 }
			
			//동일한 상품코드 존재하지 않을 때
			if(result == false) {
				 CommonService.msg("존재하지 않는 상품 코드입니다.");
			}
			return null;
		}
		
	}
	
	// 입력한 상품 값 삭제하기
	// 입력 값 없을 경우, 동일한 상품명 존재하지 않을 때 삭제하지 않고 알림 발생 예외 추가함
	public ObservableList<ProductDTO> delete(ObservableList<ProductDTO> currentData, String pdCode) {
		Boolean result = false;
		
		//입력 값 없을 경우
		if(pdCode.equals("")) {
			CommonService.msg("상품코드를 입력하세요.");
			return null;
		}else{
			for (ProductDTO product : currentData) {
			     if(product.getPdCode().equals(pdCode)) {
			          currentData.remove(product);
			          pdDao.pdDelete(product);
			            	
			            	
			           CommonService.msg("\"" + pdCode + "\" 상품 정보가 삭제되었습니다.");
				 	    result = true;
			           return currentData;
			          }

			      }         
		}
		//동일한 상품명 존재하지 않을 때
		if(result == false) {
			 CommonService.msg("존재하지 않는 상품 코드입니다.");
		}
		return null;
	}
	
	
	
		// 입력한 옵션 값 추가하기
		// 상품 선택하지 않은 경우, 상품 코드 존재 하지 않을 때, 동일한 옶션값 존재, 필수 입력 값 없을 경우, 재고 수량 0원 이하, 임의 값 입력 시 추가하지 않고 알림 발생 예외 추가함
		public ProductOptionDTO addOt(ObservableList<ProductOptionDTO> currentData, String pdCode, Boolean codeIn, String size, String color,int pdQuantity, String quantityNum) {
			
			// 상품 선택하지 않은 경우
			if(pdCode.equals("")) {
				CommonService.msg("상품을 선택하세요.");
			return null;
			// 상품 코드 존재 하지 않을 때
			}else if(codeIn == false){
				CommonService.msg("존재하지 않는 상품 코드 입니다.");
			return null;
			}else{
				//동일한 옶션값 존재
				 for (ProductOptionDTO option : currentData) {
			         if(option.getOtColor().equals(color) && option.getOtSize().equals(size)) {
			        	 CommonService.msg("동일한 옵션이 존재합니다.");
			        	 return null;
			         }
			     }
				 // 필수 입력 값 없을 경우
				 if(size.equals("") || color.equals("") ||  quantityNum == "null" ) {
					 CommonService.msg("필수 항목을 입력해주세요.");
					 return null;
				// 재고 수량 0원 이하, 임의 값 입력 시
				 }else if(pdQuantity < 0 || quantityNum == "notnum"){
					 CommonService.msg("재고수량은 0 이상의 숫자만 입력 가능합니다.");
					 return null;
				 }
				 else{
					 ProductOptionDTO option = new ProductOptionDTO();
					 
					 	option.setOtCode(pdCode);
				     	option.setOtSize(size);
				     	option.setOtColor(color);
				     	option.setOtQuantity(pdQuantity);
				     	otDao.otAdd(option);
				     	
				     	CommonService.msg("\"" + pdCode + "\" 옵션 정보가 추가되었습니다.");
				     	return option;
				 }
			}

		}
		

		// 입력한 옵션 값 수정하기 
		// 상품 선택하지 않은 경우, 동일한 옵션값 존재하지 않을 때, 필수 입력 값 없을 경우, 재고 수량 0원 이하, 임의 값 입력 시 수정하지 않고 알림 발생 예외 추가함 
		public ObservableList<ProductOptionDTO> updateOt(ObservableList<ProductOptionDTO> currentData, String pdCode, Boolean codeIn, String currentSize, String currentColor, String size, String color,int pdQuantity, String quantityNum) {
			Boolean result = false;

			// 상품 선택하지 않은 경우
			if(pdCode.equals("")) {
				CommonService.msg("상품을 선택하세요.");
			return null;
			// 상품 코드 존재 하지 않을 때
			}else if(codeIn == false){
				CommonService.msg("존재하지 않는 상품 코드 입니다.");
			return null;
			}else {
				for (ProductOptionDTO option : currentData) {
			         if(option.getOtColor().equals(currentColor) && option.getOtSize().equals(currentSize) ) {
			        	 // 필수 입력 값 없을 경우
			        	 if(size.equals("") || color.equals("") ||  quantityNum == "null" ) {
							 CommonService.msg("필수 항목을 입력해주세요.");
							 return null;
							 
							// 재고 수량 0원 이하, 임의 값 입력 시
			        	 }else if(pdQuantity < 0 || quantityNum == "notnum"){
							 CommonService.msg("가격은 0 이상의 숫자만 입력 가능합니다.");
							 return null;
						 }
						 else{
							 option.setOtCode(pdCode);
						     option.setOtSize(size);
						     option.setOtColor(color);
						     option.setOtQuantity(pdQuantity);
						     otDao.otUpdate(currentSize, currentColor, option);
				 	     	
				 	     	CommonService.msg("\"" + pdCode + "\" 상품 정보가 수정되었습니다.");
				 	     	result = true;
				         	return currentData;
						 }
			         }
				 }
				
				//동일한 옵션값 존재하지 않을 때
				if(result == false) {
					 CommonService.msg("수정할 옵션을 선택하세요.");
				}
				return null;
			}
			
		}
		
		// 입력한 옵션 값 삭제하기
		// 동일한 옵션값 존재하지 않을 때 삭제하지 않고 알림 발생 예외 추가함
		public ObservableList<ProductOptionDTO> deleteOt(ObservableList<ProductOptionDTO> currentData, String pdCode, Boolean codeIn, String size, String color) {
			Boolean result = false;
			// 상품 선택하지 않은 경우
			if(pdCode.equals("")) {
				CommonService.msg("상품을 선택하세요.");
			return null;
			// 상품 코드 존재 하지 않을 때
			}else if(codeIn == false){
				CommonService.msg("존재하지 않는 상품 코드 입니다.");				
				return null;
			}else if(color.equals("") || size.equals("")){
				CommonService.msg("옵션값(색상, 사이즈)을 입력하세요.");				
				return null;
			}else {
				for (ProductOptionDTO option : currentData) {
		            if(option.getOtCode().equals(pdCode) && option.getOtColor().equals(color) && option.getOtSize().equals(size)) {
		            	currentData.remove(option);
		            	otDao.otDelete(option);
		            	
		            	
		            	CommonService.msg("\"" + pdCode + "\" 상품 정보가 삭제되었습니다.");
			 	     	result = true;
		            	return currentData;
		            }
				}
	        }         
			//동일한 옵션값 존재하지 않을 때
			if(result == false) {
				 CommonService.msg("존재하지 않는 옵션입니다.");
			}
			return null;
		}
		
	
}
