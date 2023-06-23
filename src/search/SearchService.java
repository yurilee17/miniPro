package search;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import product.ProductDTO;

public class SearchService {
	
	private Connection con;
	
	public SearchService() {
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

	// 상품명을 기준으로 검색 키워드와 일치하는 상품명 가져오기
	public ArrayList<ProductDTO> searchProducts(String searchText) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ArrayList<ProductDTO> products = new ArrayList<>();

	    try {
	        ps = con.prepareStatement("SELECT * FROM products WHERE name LIKE ?");
	        ps.setString(1, "%" + searchText + "%");
	        rs = ps.executeQuery();

	        while (rs.next()) {
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
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (ps != null) {
	                ps.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return products;
	}
	
}
