package order;

public class OrderManagerDTO {

	private int ornum; //주문번호(키 값)
	private String id; //사용자아이디
	private String ordate; //구매날짜
	private int totalPrice; //최종금액
	private String payment; //결제방법
	private String recipient; //수령인
	private String phoneNum; //전화번호
	private String oradd; //주소
	private String memo; //배송메모
	private String status; // 배송상태
	private String orname; // 상품이름
	private String pdsize; // 선택한옵션(사이즈)
	private String color; // 선택한옵션(색상)
	private int quantity; // 갯수
	private int unitPrice; // 따른금액
	private String text; // 취소 ,환불 ,교환 메시지
	private String pdCode; // 상품 코드
	
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
	
	public String getOrname() {
		return orname;
	}
	public void setOrname(String orname) {
		this.orname = orname;
	}
	public String getPdsize() {
		return pdsize;
	}
	public void setPdsize(String pdsize) {
		this.pdsize = pdsize;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getPdCode() {
		return pdCode;
	}
	public void setPdCode(String pdCode) {
		this.pdCode = pdCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}