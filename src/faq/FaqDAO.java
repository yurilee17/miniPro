package faq;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import notice.NoticeDTO;

public class FaqDAO {
	
private Connection con;
	
    public FaqDAO() {
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

    
    // FAQ 작성하기
    public void insert(String subject, String day, String content) {
    	int lastNumber = FaqNumber();
        int nextNumber = lastNumber + 1;
    	
        String sql = "INSERT INTO faq (num, subject, day, content) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;  
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, nextNumber);
            ps.setString(2, subject);
            ps.setString(3, day);
            ps.setString(4, content);
            ps.executeUpdate();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }
    
    // 공지사항 수정하기
    public void modify(NoticeDTO dto) {
    	String sql = "UPDATE faq SET subject=?, content=? WHERE num=?";
    	PreparedStatement ps = null;
    	try {
    		ps = con.prepareStatement(sql);
    		ps.setString(1, dto.getSubject());
    		ps.setString(2, dto.getSubject());
    		ps.setInt(3, dto.getNum());
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    // 공지사항 삭제하기
    public void delete(FaqDTO dto) {
    	String sql = "DELETE FROM faq WHERE subject=?";
    	PreparedStatement ps = null;
    	try {
    		ps = con.prepareStatement(sql);
    		ps.setString(1, dto.getSubject());
    		ps.executeUpdate();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
	public int FaqNumber() {
	    String sql = "SELECT MAX(num) FROM faq";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0; // 번호를 조회할 수 없는 경우 0 반환
	}
}
