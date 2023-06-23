package review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import order.OrderOptionDTO;
import product.ProductDTO;
import service.Login;

public class ReviewDAO {
	private Connection con;

	public ReviewDAO() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "oracle";
		String password = "oracle";

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 특정 코드 리뷰 정보 지우기
	public void reviewDataDelete(String code) {
		String sql = "DELETE FROM reviewTable WHERE code=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, code);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 특정 시간, 주문코드, 사용자id, 주문 번호 일치하는 리부 있는지 확인
	public Boolean selectReview(String date, OrderOptionDTO settingOption) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM reviewTable WHERE ornum=? AND id=? AND ordate=? AND code=?");
			ps.setInt(1, settingOption.getOrnum());
			ps.setString(2, Login.getId());
			ps.setString(3, date);
			ps.setString(4, settingOption.getPdCode());
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 리뷰 추가
	public void addReivew(ReviewDTO dto) {
		String sql = "INSERT INTO reviewTable (ornum, id, ordate, code, review) VALUES (?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getOrnum());
			ps.setString(2, Login.getId());
			ps.setString(3, dto.getOrdate());
			ps.setString(4, dto.getCode());
			ps.setDouble(5, dto.getReview());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 특정 상품 리뷰 값 가져오기
	public ArrayList<Double> selectPT(ReviewDTO dto) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Double> products = new ArrayList<Double>();
		try {
			ps = con.prepareStatement("SELECT * FROM reviewTable WHERE code=?");
			ps.setString(1, dto.getCode());
			rs = ps.executeQuery();
			while (rs.next()) {
				products.add(rs.getDouble("review"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

}