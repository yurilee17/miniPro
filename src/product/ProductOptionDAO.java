package product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProductOptionDAO {
private Connection con;
	
	public ProductOptionDAO() {
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
	
	// DB Code별 모든 값 가져오기
	public ArrayList<ProductOptionDTO> selectCode(String code) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProductOptionDTO> options = new ArrayList<ProductOptionDTO>();
		try {
			ps = con.prepareStatement("SELECT * FROM options WHERE code=? ORDER BY code DESC");
			ps.setString(1, code);
			rs = ps.executeQuery();
			while(rs.next()) {
				ProductOptionDTO option = new ProductOptionDTO();
				option.setOtCode(rs.getString("code"));
				option.setOtColor(rs.getString("color"));
				option.setOtSize(rs.getString("pdsize"));
				option.setOtQuantity(rs.getInt("quantity"));

				options.add(option);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return options;
	}
	
	// 옵션 추가하기
		public void otAdd(ProductOptionDTO dto) {
			String sql = "INSERT INTO options VALUES(?,?,?,?)";
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(sql);
				ps.setString(1, dto.getOtCode());
				ps.setString(2, dto.getOtSize());
				ps.setString(3, dto.getOtColor());
				ps.setInt(4, dto.getOtQuantity());
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	// 옵션 수정하기
	public void otUpdate(String currentSize, String currentColor, ProductOptionDTO dto) {
		String sql = "UPDATE options SET pdsize=?, color=?, quantity=? WHERE code=? AND pdsize=? AND color=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getOtSize());
			ps.setString(2, dto.getOtColor());
			ps.setInt(3, dto.getOtQuantity());
			ps.setString(4, dto.getOtCode());
			ps.setString(5, currentSize);
			ps.setString(6, currentColor);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 특정 코드 옵션 모두 삭제하기
	public void otDeleteCode(String code){
		String sql = "DELETE FROM options WHERE code=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, code);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 옵션 삭제하기
	public void otDelete(ProductOptionDTO dto){
		String sql = "DELETE FROM options WHERE code=? AND pdsize=? AND color=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getOtCode());
			ps.setString(2, dto.getOtSize());
			ps.setString(3, dto.getOtColor());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//DB code, color 2개 모두 만족할때 모든 값 가져오기(사이즈 가져오기)
	public ArrayList<ProductOptionDTO> getSize(String code, String color) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProductOptionDTO> options = new ArrayList<ProductOptionDTO>();
		try {
			ps = con.prepareStatement("SELECT * FROM options WHERE code=? AND color=?");
			ps.setString(1, code);
			ps.setString(2, color);
			rs = ps.executeQuery();
			while(rs.next()) {
				ProductOptionDTO option = new ProductOptionDTO();
				option.setOtCode(rs.getString("code"));
				option.setOtColor(rs.getString("color"));
				option.setOtSize(rs.getString("pdsize"));
				option.setOtQuantity(rs.getInt("quantity"));

				options.add(option);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return options;
	}
	
	//DB code, color, size 3개 모두 만족할때 모든 값 가져오기(재고 수량 가져오기)
	public ProductOptionDTO getQuantity(String code, String color, String size) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProductOptionDTO option = new ProductOptionDTO();
		try {
			ps = con.prepareStatement("SELECT * FROM options WHERE code=? AND color=? AND pdsize=?");
			ps.setString(1, code);
			ps.setString(2, color);
			ps.setString(3, size);
			rs = ps.executeQuery();
			while(rs.next()) {
				option.setOtCode(rs.getString("code"));
				option.setOtColor(rs.getString("color"));
				option.setOtSize(rs.getString("pdsize"));
				option.setOtQuantity(rs.getInt("quantity"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return option;
	}
	

}
