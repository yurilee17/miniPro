package product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import review.ReviewDAO;


public class ProductDAO {
private Connection con;
	
	public ProductDAO() {
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
	public ArrayList<ProductDTO> selectAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProductDTO> products = new ArrayList<ProductDTO>();
		try {
			ps = con.prepareStatement("SELECT * FROM products ORDER BY name DESC");
			rs = ps.executeQuery();
			while(rs.next()) {
				ProductDTO product = new ProductDTO();
				product.setPdCode(rs.getString("code"));
				product.setCategory(rs.getString("category"));
				product.setPdName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setPdImage(rs.getString("image"));
				product.setView(rs.getInt("pdview"));
				product.setReview(rs.getDouble("review"));
				product.setExplanation(rs.getString("explanation"));
				
				products.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	// 특정 상품의 설명 가져오기
	public String getExplanation(String code) {
		ArrayList<ProductDTO> setData = selectAll();
    	for (ProductDTO product : setData) {
    		if(product.getPdCode().equals(code)) {
    			return product.getExplanation();
    		}
        }
		return null;
	}
	
	
	// 상품 추가하기
		public void pdAdd(ProductDTO dto) {
			String sql = "INSERT INTO products VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(sql);
				ps.setString(1, dto.getPdCode());
				ps.setString(2, dto.getCategory());
				ps.setString(3, dto.getPdName());
				ps.setInt(4, dto.getPrice());
				ps.setString(5, dto.getPdImage());
				ps.setInt(6, dto.getView());
				ps.setDouble(7, dto.getReview());
				ps.setString(8, dto.getExplanation());
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	// 상품 수정하기
	public void pdUpdate(ProductDTO dto) {
		String sql = "UPDATE products SET category=?, name=?, price=?,image=?, pdview=?, review=?, explanation=? WHERE code=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getCategory());
			ps.setString(2, dto.getPdName());
			ps.setInt(3, dto.getPrice());
			ps.setString(4, dto.getPdImage());
			ps.setInt(5, dto.getView());
			ps.setDouble(6, dto.getReview());
			ps.setString(7, dto.getExplanation());
			ps.setString(8, dto.getPdCode());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	// 상품 삭제하기
	public void pdDelete(ProductDTO dto){
		String sql = "DELETE FROM products WHERE code=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getPdCode());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ProductOptionDAO opDao = new ProductOptionDAO();
		ReviewDAO dao = new ReviewDAO();
		opDao.otDeleteCode(dto.getPdCode());
		dao.reviewDataDelete(dto.getPdCode());
	}

	// 특정 상품의 정보 가져오기
	public ProductDTO getProduct(String pdCode){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM products WHERE code=?");
			ps.setString(1, pdCode);
			rs = ps.executeQuery();
			while(rs.next()) {
				ProductDTO product = new ProductDTO();
				product.setPdCode(rs.getString("code"));
				product.setCategory(rs.getString("category"));
				product.setPdName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setPdImage(rs.getString("image"));
				product.setView(rs.getInt("pdview"));
				product.setReview(rs.getDouble("review"));
				product.setExplanation(rs.getString("explanation"));
				
				return product;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// 리뷰 추가
		public void updateReview(String code, Double review) {
			String sql = "UPDATE products SET review=? WHERE code=?";
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(sql);
				ps.setDouble(1, review);
				ps.setString(2, code);
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	public String getPdNameByPdCode(String pdCode) {
	    String pdName = null;
	    try {
	        ProductDTO product = getProduct(pdCode);
	        if (product != null) {
	            pdName = product.getPdName();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return pdName;
	}
	
	
}
