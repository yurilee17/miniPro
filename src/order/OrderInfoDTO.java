package order;

public class OrderInfoDTO {
//	주문번호, 사용자아이디,  구매날짜 , 배송상태, 최종금액, 결제방법, 수령인, 전화번호, 주소, 배송메모, 택배사, 송장번호, 리뷰(별점)

	private int ornum; //주문번호(키 값)
	private String id; //사용자아이디
	private String ordate; //구매날짜
	private int totalPrice; //최종금액
	private String payment; //결제방법
	private String recipient; //수령인
	private String phoneNum; //전화번호
	private String oradd; //주소
	private String memo; //배송메모
	private String courier; //택배사
	private int trackingNum; //송장번호
	private Double revivew; //리뷰(별점)
	
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
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getOradd() {
		return oradd;
	}
	public void setOradd(String oradd) {
		this.oradd = oradd;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	public int getTrackingNum() {
		return trackingNum;
	}
	public void setTrackingNum(int trackingNum) {
		this.trackingNum = trackingNum;
	}
	public Double getRevivew() {
		return revivew;
	}
	public void setRevivew(Double revivew) {
		this.revivew = revivew;
	}
	


}
