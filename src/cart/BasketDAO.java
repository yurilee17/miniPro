package cart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import product.ProductDAO;
import product.ProductDTO;
import service.Login;
import member.MemberDTO;

public class BasketDAO {
    private List<BasketDTO> basketItems;
    private Connection con;

    public BasketDAO() {
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
	

    // DB연결 설정
    public void connectToDatabase() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "oracle";
        String password = "oracle";

        // JDBC
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addItem(BasketDTO item) throws SQLException {
        // DB 연결 확인
        if (con == null) {
            throw new SQLException("Not connected to database. Call connectToDatabase() first.");
        }
        
    	int basketlastNumber = BasketNumber();
        int basketnextNumber = basketlastNumber + 1;
        
        // PreparedStatement를 사용하여 INSERT 쿼리 실행
        String query = "INSERT INTO basketitems (basketnum, code, id, productname, size1, price, image, color, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, basketnextNumber);
        statement.setString(2, item.getPdCode());
        statement.setString(3, item.getId());
        statement.setString(4, item.getProductName());
        statement.setString(5, item.getSize());
        statement.setDouble(6, item.getPrice());
        statement.setString(7, item.getImage());
        statement.setString(8, item.getColor());
        statement.setInt(9, item.getQuantity());
        statement.executeUpdate();
        statement.close();
        
        if (basketItems == null) {
            basketItems = new ArrayList<>();  // basketItems 초기화
        }

        return basketItems.add(item);
    }
    
    // 장바구니 물품 삭제하기
	public void basketDelete(BasketDTO dto) {
		String sql = "DELETE FROM basketitems WHERE basketnum=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getBasketnum());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public List<BasketDTO> getBasketItems() throws SQLException {
        // DB 연결 확인
        if (!isConnected()) {
            throw new SQLException("Not connected to database. Call connectToDatabase() first.");
        }
        String query = "SELECT * FROM basketitems";
        PreparedStatement statement = con.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();
        basketItems.clear();

        // ResultSet을 이용하여 결과 가져와서 BasketItems에 추가하기
        while (resultSet.next()) {
        	int basketnum = resultSet.getInt(1);
        	String pdCode = resultSet.getString("code");
            String id = resultSet.getString("id");
            String productName = resultSet.getString("productName");
            String size = resultSet.getString("size1");
            int price = resultSet.getInt("price");
            String image = resultSet.getString("image");
            String color = resultSet.getString("color");
            int quantity = resultSet.getInt("quantity");

            BasketDTO item = new BasketDTO(basketnum, pdCode, id, productName, size, price, image, color, quantity);
            basketItems.add(item);
        }
        resultSet.close();
        statement.close();

        return basketItems;
    }

	private boolean isConnected() throws SQLException {
        return con != null && !con.isClosed();
    }

	public int BasketNumber() {
	    String sql = "SELECT MAX(basketnum) FROM basketitems";
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

	// 특정 상품의 정보 가져오기
	public BasketDTO getBasketInfomation(){
		String users = Login.getId();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM basketitems WHERE id=?");
			ps.setString(1, users);
			rs = ps.executeQuery();
			while(rs.next()) {
				BasketDTO basket = new BasketDTO();
				basket.setBasketnum(rs.getInt("basketnum"));
				basket.setPdCode("code");
				basket.setProductName(rs.getString("productname"));
				basket.setSize(rs.getString("size1"));
				basket.setPrice(rs.getInt("price"));
				basket.setImage(rs.getString("image"));
				basket.setColor(rs.getString("color"));
				basket.setQuantity(rs.getInt("quantity"));
				
				return basket;
			}
		} catch (Exception e) {
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
		
		return null;
	}
	

	
	public ArrayList<BasketDTO> getBasketInformation(String users) {
	    List<BasketDTO> basketList = new ArrayList<>();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        ps = con.prepareStatement("SELECT * FROM basketitems WHERE id=? ORDER BY basketnum DESC");
	        ps.setString(1, users);
	        rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            BasketDTO basket = new BasketDTO();
	            basket.setBasketnum(rs.getInt("basketnum"));
	            basket.setPdCode(rs.getString("code"));
	            basket.setProductName(rs.getString("productname"));
	            basket.setSize(rs.getString("size1"));
	            basket.setPrice(rs.getInt("price"));
	            basket.setImage(rs.getString("image"));
	            basket.setColor(rs.getString("color"));
	            basket.setQuantity(rs.getInt("quantity"));
	            basketList.add(basket);
	        }
	    } catch (Exception e) {
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
	    
	    return (ArrayList<BasketDTO>) basketList;
	}






	
	

}
    
    
