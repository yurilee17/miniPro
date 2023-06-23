package order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import service.Login;

public class OrderOptionDAO {
	private Connection con;

	public OrderOptionDAO() {
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

	// DB의 모든 값 가져오기
	public ArrayList<OrderOptionDTO> selectAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OrderOptionDTO> products = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT * FROM orderOption");
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderOptionDTO product = new OrderOptionDTO();
				product.setOrnum(rs.getInt("ornum"));
				product.setId(rs.getString("id"));
				product.setStatus(rs.getString("status"));
				product.setImg(rs.getString("img"));
				product.setOrname(rs.getString("orname"));
				product.setPdsize(rs.getString("pdsize"));
				product.setColor(rs.getString("color"));
				product.setQuantity(rs.getInt("quantity"));
				product.setUnitPrice(rs.getInt("unitPrice"));
				product.setText(rs.getString("textMemo"));
				product.setPdCode(rs.getString("code"));
				
				products.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	// 현재 로그인한 ID의 num에 맞는 DB의 옵션 값 가져오기
	public ArrayList<OrderOptionDTO> selectNum(int num) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OrderOptionDTO> products = new ArrayList<OrderOptionDTO>();
		try {
			ps = con.prepareStatement("SELECT * FROM orderOption WHERE id=? AND ornum=?");
			ps.setString(1, Login.getId());
			ps.setInt(2, num);
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderOptionDTO product = new OrderOptionDTO();
				product.setOrnum(rs.getInt("ornum"));
				product.setId(rs.getString("id"));
				product.setStatus(rs.getString("status"));
				product.setImg(rs.getString("img"));
				product.setOrname(rs.getString("orname"));
				product.setPdsize(rs.getString("pdsize"));
				product.setColor(rs.getString("color"));
				product.setQuantity(rs.getInt("quantity"));
				product.setUnitPrice(rs.getInt("unitPrice"));
				product.setText(rs.getString("textMemo"));
				product.setPdCode(rs.getString("code"));

				products.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}

	// 특정 주문 번호 정보 지우기
	public void orderDataDelete(int num) {
		String sql = "DELETE FROM orderOption WHERE ornum=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 특정 주문 번호, 상품 코드, 옵션의 상태 변경하기
	public void updateStatus(String status, OrderOptionDTO settingOption) {
		String sql = "UPDATE orderOption SET status=? WHERE ornum=? AND code=? AND pdsize=? AND color = ?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setInt(2, settingOption.getOrnum());
			ps.setString(3, settingOption.getPdCode());
			ps.setString(4, settingOption.getPdsize());
			ps.setString(5, settingOption.getColor());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	// 특정 주문 번호, 상품 코드, 옵션의 상태 값 가져오기
	public String getStatus(OrderOptionDTO settingOption) {
		String sql = "SELECT status FROM orderOption WHERE ornum=? AND code=? AND pdsize=? AND color = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, settingOption.getOrnum());
			ps.setString(2, settingOption.getPdCode());
			ps.setString(3, settingOption.getPdsize());
			ps.setString(4, settingOption.getColor());
			ps.executeUpdate();
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("status");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 특정 주문 번호, 상품 코드, 옵션의 메모 추가하기
	public void addMemo(String text, OrderOptionDTO settingOption) {
		String sql = "UPDATE orderOption SET textMemo=? WHERE ornum=? AND code=? AND pdsize=? AND color = ?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, text);
			ps.setInt(2, settingOption.getOrnum());
			ps.setString(3, settingOption.getPdCode());
			ps.setString(4, settingOption.getPdsize());
			ps.setString(5, settingOption.getColor());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 주문(옵션) 추가하기
			public void orderAdd(OrderOptionDTO dto) {
				String sql = "INSERT INTO orderOption (ornum, id, status, img, orname, pdsize, color, quantity, unitPrice, code) VALUES (?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = null;
				try {
					ps = con.prepareStatement(sql);
					ps.setInt(1, dto.getOrnum());
					ps.setString(2, dto.getId());
					ps.setString(3, dto.getStatus());
					ps.setString(4, dto.getImg());
					ps.setString(5, dto.getOrname());
					ps.setString(6, dto.getPdsize());
					ps.setString(7, dto.getColor());
					ps.setInt(8, dto.getQuantity());
					ps.setInt(9, dto.getUnitPrice());
					ps.setString(10, dto.getPdCode());

					ps.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 특정 주문 번호, 상품 코드, 옵션의 정보 가져오기
 			public OrderOptionDTO selectOrderMana(int num, String code,  String color, String pdsize) {
 				PreparedStatement ps = null;
 				ResultSet rs = null;
 				try {
 					ps = con.prepareStatement("SELECT * FROM orderOption WHERE ornum=? AND code=? AND color=? AND pdsize=?");
 					ps.setInt(1, num);
 					ps.setString(2, code);
 					ps.setString(3, color);
 					ps.setString(4, pdsize);
 					rs = ps.executeQuery();
 					OrderOptionDTO product = new OrderOptionDTO();
 					if (rs.next()) {
 						
 						product.setOrnum(rs.getInt("ornum"));
 						product.setId(rs.getString("id"));
 						product.setStatus(rs.getString("status"));
 						product.setImg(rs.getString("img"));
 						product.setOrname(rs.getString("orname"));
 						product.setPdsize(rs.getString("pdsize"));
 						product.setColor(rs.getString("color"));
 						product.setQuantity(rs.getInt("quantity"));
 						product.setUnitPrice(rs.getInt("unitPrice"));
 						product.setText(rs.getString("textMemo"));
 						product.setPdCode(rs.getString("code"));

 						return product;
 					}
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 				return null;
			}
}
