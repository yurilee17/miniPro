package product;

import java.util.HashMap;

public class ProductDTO {

//	상품 코드(키값) / 하부 카테고리 / 상품명 / 가격 / 이미지 / 조회수 / 평균별점(리뷰) / 상품설명
	
		private String pdCode; // 상품 코드(키값)
		private String category; // 하부 카테고리
	    private String pdName; // 상품명
	    private int price;//가격
	    private String pdImage; // 이미지
	    private int view; // 조회수
	    private Double review; // 평균별점(리뷰)
	    private String explanation; // 상품설명
	    private HashMap<String, String> codeNameMap;
	    public ProductDTO() {};
	    
		public ProductDTO(String pdCode, String category, String pdName, int price, String pdImage, int view, Double review, String explanation) {
			this.pdCode = pdCode;
	        this.category = category;
	        this.pdName = pdName;
	        this.price = price;
	        this.pdImage = pdImage;
	        this.view = view;
	        this.review = review;
	        this.explanation = explanation;
	        codeNameMap = new HashMap<>();
	    }

		public String getPdCode() {
			return pdCode;
		}

		public void setPdCode(String pdCode) {
			this.pdCode = pdCode;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getPdName() {
			return pdName;
		}

		public void setPdName(String pdName) {
			this.pdName = pdName;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public String getPdImage() {
			return pdImage;
		}

		public void setPdImage(String pdImage) {
			this.pdImage = pdImage;
		}

		public int getView() {
			return view;
		}

		public void setView(int view) {
			this.view = view;
		}

		public Double getReview() {
			return review;
		}

		public void setReview(Double review) {
			this.review = review;
		}

		public String getExplanation() {
			return explanation;
		}

		public void setExplanation(String explanation) {
			this.explanation = explanation;
		}
		

	}
