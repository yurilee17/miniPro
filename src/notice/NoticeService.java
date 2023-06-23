package notice;

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

public class NoticeService {
	
	private Connection con;
	private NoticeDAO noticeDao;
	private NoticeDTO noticeDto;
	private Opener opener;
	
    public Opener getOpener() {
        return opener;
    }
    
    public void setNoticeDao(NoticeDAO noticeDao) {
        this.noticeDao = noticeDao;
    }
	
	public NoticeService(NoticeDAO noticeDao) {
		this.noticeDao = noticeDao;
		
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
	
    public void insert(NoticeDTO noticeDTO) {
        int lastNumber = noticeDao.NoticeNumber();
        int nextNumber = lastNumber + 1;
        String subject = noticeDTO.getSubject();
        String day = noticeDTO.getDay();
        String content = noticeDTO.getContent();
        noticeDao.insert(subject, day, content);
    }
 
    public List<NoticeDTO> getAllNotices() {
        List<NoticeDTO> notices = new ArrayList<>();

        try {
            String sql = "SELECT num, subject, day, content FROM notice";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int num = rs.getInt("num");
                String subject = rs.getString("subject");
                String day = rs.getString("day");
                String content = rs.getString("content");

                NoticeDTO notice = new NoticeDTO(num, subject, day, content);
                notices.add(notice);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notices;
    }
    
    // 공지사항 삭제하기
    public ObservableList<NoticeDTO> delete(ObservableList<NoticeDTO> currentData, String subject) {
    	Boolean result = false;
    	
    	for (NoticeDTO notice : currentData) {
    		if(notice.getSubject().equals(subject) ) {
    			currentData.remove(notice);
    			noticeDao.delete(notice);
    			
    			CommonService.msg("공지사항이 삭제되었습니다.");
    			result = true;
    			return currentData;
    		}
    	}
    	return null;
    }
	
	
}
