package faq;

public class FaqDTO {
	private int num;
	private String subject;
	private String day;
	private String content;
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
    public FaqDTO(int num, String subject, String day, String content) {
        this.num = num;
        this.subject = subject;
        this.day = day;
        this.content = content;
    }
	
}
