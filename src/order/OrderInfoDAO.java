package order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import service.Login;

public class OrderInfoDAO {
	private Connection con;

	public OrderInfoDAO() {
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

//	// DB의 모든 값 가져오기
	public ArrayList<OrderInfoDTO> selectAll2() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OrderInfoDTO> products = new ArrayList<OrderInfoDTO>();
		try {
			ps = con.prepareStatement("SELECT * FROM orderInfo");
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderInfoDTO product = new OrderInfoDTO();
				product.setOrnum(rs.getInt("ornum"));
				product.setId(rs.getString("id"));
				product.setOrdate(rs.getString("ordate"));
				product.setTotalPrice(rs.getInt("totalPrice"));
				product.setPayment(rs.getString("payment"));
				product.setRecipient(rs.getString("recipient"));
				product.setPhoneNum(rs.getString("phoneNum"));
				product.setOradd(rs.getString("oradd"));
				product.setMemo(rs.getString("memo"));
				product.setCourier(rs.getString("courier"));
				product.setTrackingNum(rs.getInt("trackingNum"));
				product.setRevivew(rs.getDouble("revivew"));

				products.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	// 현재 로그인한 ID의 DB의 모든 값 가져오기
	public ArrayList<OrderInfoDTO> selectAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OrderInfoDTO> products = new ArrayList<OrderInfoDTO>();
		try {
			ps = con.prepareStatement("SELECT * FROM orderInfo WHERE id=? ORDER BY ornum DESC");
			ps.setString(1, Login.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderInfoDTO product = new OrderInfoDTO();
				product.setOrnum(rs.getInt("ornum"));
				product.setId(rs.getString("id"));
				product.setOrdate(rs.getString("ordate"));
				product.setTotalPrice(rs.getInt("totalPrice"));
				product.setPayment(rs.getString("payment"));
				product.setRecipient(rs.getString("recipient"));
				product.setPhoneNum(rs.getString("phoneNum"));
				product.setOradd(rs.getString("oradd"));
				product.setMemo(rs.getString("memo"));
				product.setCourier(rs.getString("courier"));
				product.setTrackingNum(rs.getInt("trackingNum"));
				product.setRevivew(rs.getDouble("revivew"));

				products.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	// 특정번호의 값 가져오기
	public OrderInfoDTO selectOne(int num) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM orderInfo WHERE ornum=?");
			ps.setInt(1, num);
			rs = ps.executeQuery();
			if (rs.next()) {
				OrderInfoDTO product = new OrderInfoDTO();
				product.setOrnum(rs.getInt("ornum"));
				product.setId(rs.getString("id"));
				product.setOrdate(rs.getString("ordate"));
				product.setTotalPrice(rs.getInt("totalPrice"));
				product.setPayment(rs.getString("payment"));
				product.setRecipient(rs.getString("recipient"));
				product.setPhoneNum(rs.getString("phoneNum"));
				product.setOradd(rs.getString("oradd"));
				product.setMemo(rs.getString("memo"));
				product.setCourier(rs.getString("courier"));
				product.setTrackingNum(rs.getInt("trackingNum"));
				product.setRevivew(rs.getDouble("revivew"));

				return product;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 특정 주문 번호 정보 지우기
	public void orderDataDelete(int num) {
		String sql = "DELETE FROM orderInfo WHERE ornum=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 주문 추가하기
	public int orderAdd(OrderInfoDTO dto) {
		String sql = "INSERT INTO orderInfo (ornum, id, ordate, totalPrice, payment, recipient, phoneNum, oradd, memo) VALUES (?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		int num = ornumMax() + 1;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.setString(2, dto.getId());
			ps.setString(3, dto.getOrdate());
			ps.setInt(4, dto.getTotalPrice());
			ps.setString(5, dto.getPayment());
			ps.setString(6, dto.getRecipient());
			ps.setString(7, dto.getPhoneNum());
			ps.setString(8, dto.getOradd());
			ps.setString(9, dto.getMemo());
			ps.executeUpdate();
			return num;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	// 주문 번호 최대값 구하기
	public int ornumMax() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT max(ornum) FROM orderInfo");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
}
