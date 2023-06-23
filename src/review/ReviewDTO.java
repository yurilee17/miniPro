package review;

public class ReviewDTO {

//	주문번호, 사용자아이디, 구매 날짜, 상품 코드, 리뷰 점수
	private int ornum; // 주문번호(키 값)
	private String id; // 사용자아이디
	private String ordate; // 구매 날짜
	private String code; // 상품 코드
	private double review; // 리뷰 점수

	public int getOrnum() {
		return ornum;
	}

	public void setOrnum(int ornum) {
		this.ornum = ornum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrdate() {
		return ordate;
	}

	public void setOrdate(String ordate) {
		this.ordate = ordate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getReview() {
		return review;
	}

	public void setReview(double review) {
		this.review = review;
	}

}