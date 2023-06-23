package faq;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import equipment.CommonService;
import equipment.Opener;
import javafx.collections.ObservableList;

public class FaqService {
	
	private Connection con;
	private FaqDAO faqDao;
	private FaqDTO faqDto;
	private Opener opener;
	
    public Opener getOpener() {
        return opener;
    }
    
    public void setFaqDao(FaqDAO faqDao) {
    	this.faqDao = faqDao;
    }
	
	public FaqService(FaqDAO faqDao) {
		this.faqDao = faqDao;
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "oracle";
		String password = "oracle";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

    public void insert(FaqDTO faqDTO) {
        int lastNumber = faqDao.FaqNumber();
        int nextNumber = lastNumber + 1;
        String subject = faqDTO.getSubject();
        String day = faqDTO.getDay();
        String content = faqDTO.getContent();
        faqDao.insert(subject, day, content);
    }
    
    public List<FaqDTO> getAllFaqs() {
        List<FaqDTO> faqs = new ArrayList<>();

        try {
            String sql = "SELECT num, subject, day, content FROM faq";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int num = rs.getInt("num");
                String subject = rs.getString("subject");
                String day = rs.getString("day");
                String content = rs.getString("content");

                FaqDTO faq = new FaqDTO(num, subject, day, content);
                faqs.add(faq);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return faqs;
    }
	
    // FAQ 삭제하기
    public ObservableList<FaqDTO> delete(ObservableList<FaqDTO> currentData, String subject) {
    	Boolean result = false;
    	
    	for (FaqDTO faq : currentData) {
    		if(faq.getSubject().equals(subject) ) {
    			currentData.remove(faq);
    			faqDao.delete(faq);
    			
    			CommonService.msg("공지사항이 삭제되었습니다.");
    			result = true;
    			return currentData;
    		}
    	}
    	return null;
    }
    
}
