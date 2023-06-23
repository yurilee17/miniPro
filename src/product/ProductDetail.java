package product;

import java.util.ArrayList;

public class ProductDetail {

	private ProductDAO pdDao;
	private ProductDTO Product;
	private ProductOptionDAO otDao;
	private ArrayList<ProductOptionDTO> options;
	private ArrayList<ProductOptionDTO> getSizes;
	private ProductOptionDTO getQuantitys;
	
	private String pdCode; // 상품 코드(키값)
	private String category; // 하부 카테고리
    private String pdName; // 상품명
    private int price;//가격
    private String pdImage; // 이미지
    private int view; // 조회수
    private Double review; // 평균별점(리뷰)
    private String explanation; // 상품설명
    
    private ArrayList<String> colors; // 상품 색상
    private int otQuantity; // 재고수량
    
	public ProductDetail(String pdCode) {
		pdDao = new ProductDAO();
		Product = pdDao.getProduct(pdCode);
		otDao = new ProductOptionDAO();
		options = otDao.selectCode(pdCode);
		colors = new ArrayList<>();
		
		// 상품 정보
		this.pdCode = pdCode;
		this.category = Product.getCategory();
		this.pdName = Product.getPdName();
		this.price = Product.getPrice();
		this.pdImage = Product.getPdImage();
		this.view = Product.getView();
		this.review = Product.getReview();
		this.explanation = Product.getExplanation();
		
		// 상품 옵션들 정리
		// 상품 색상들
		for(ProductOptionDTO dto : options) {
			boolean same = false;
		
			for(String color : colors) {
				if(dto.getOtColor().equals(color)) {
					same = true;
				}
			}
			if(same == false) {
				colors.add(dto.getOtColor());
			}	
		}
		
}
	// 상품 색상에 따른 사이즈들 가져오기
	public ArrayList<String> getSize(String pdCode, String color){
		ArrayList<String> sizes = new ArrayList<>();
		getSizes = otDao.getSize(pdCode, color);
		for(ProductOptionDTO dto : getSizes) {
			sizes.add(dto.getOtSize());
	
		}
		return sizes;	
	}
	
	// 상품 색상, 사이즈에 따른 재고 수량 가져오기	
	public int getQuantity(String pdCode, String color, String size){
		getQuantitys = otDao.getQuantity(pdCode, color, size);
		otQuantity = getQuantitys.getOtQuantity();
		return otQuantity;	
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

	public ArrayList<String> getColors() {
		return colors;
	}

	public void setColors(ArrayList<String> colors) {
		this.colors = colors;
	}


	
}
