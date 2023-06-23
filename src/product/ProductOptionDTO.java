package product;

public class ProductOptionDTO {

//	상품 코드 / 사이즈 (키값) / 색상 (키값) / 재고수량 / 이미지 / 조회수 / 판매량 / 평균별점 
	
		private String otCode; // 상품 코드
	    private String otSize; // 사이즈 (키값)
	    private String otColor; // 색상 (키값)
	    private int otQuantity; // 재고수량
	    
	    public ProductOptionDTO() {};
	    
		public ProductOptionDTO(String otCode, String otSize, String otColor, int otQuantity) {
			this.otCode = otCode;
	        this.otSize = otSize;
	        this.otColor = otColor;
	        this.otQuantity = otQuantity;
	    }

		public String getOtCode() {
			return otCode;
		}

		public void setOtCode(String otCode) {
			this.otCode = otCode;
		}

		public String getOtSize() {
			return otSize;
		}

		public void setOtSize(String otSize) {
			this.otSize = otSize;
		}

		public String getOtColor() {
			return otColor;
		}

		public void setOtColor(String otColor) {
			this.otColor = otColor;
		}

		public int getOtQuantity() {
			return otQuantity;
		}

		public void setOtQuantity(int otQuantity) {
			this.otQuantity = otQuantity;
		}

		
		

	}
