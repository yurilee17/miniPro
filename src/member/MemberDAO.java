package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import service.Login;

public class MemberDAO {
private Connection con;

    public MemberDAO() {
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

    public String login(String id) {
        String sql = "SELECT pw FROM scene WHERE id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("pw");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }
        return null;
    }
    
    // 로그인 성공 시 로그인 된 값 Login.java에 넣어둠
    public void loginSucceed(String id) {
        String sql = "SELECT * FROM scene WHERE id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
            	 Login.setName(rs.getString("name"));
                 Login.setPw(rs.getString("pw"));
                 Login.setNum(rs.getString("num"));
                 Login.setHomeaddress(rs.getString("homeaddress"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }
    }
    
    
    // 현재 로그인 된 사람의 주소 값 수정하기
    public void updateOne(String text) {
		String sql = "UPDATE scene SET homeaddress=? WHERE id=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, text);
			ps.setString(2,  Login.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public void insert(String id, String pw, String name, String num, int amount, String homeaddress) {
        String sql = "INSERT INTO scene VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, pw);
            ps.setString(3, name);
            ps.setString(4, num);
            ps.setInt(5, 0);
            ps.setString(6, homeaddress);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(ps);
        }
    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
	public ArrayList<MemberDTO> selectAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MemberDTO> members = new ArrayList<MemberDTO>();
		try {
			ps = con.prepareStatement("SELECT * FROM scene ORDER BY name DESC");
			rs = ps.executeQuery();
			while(rs.next()) {
				MemberDTO member = new MemberDTO();
				member.setId(rs.getString("id"));
				member.setPw(rs.getString("pw"));
				member.setName(rs.getString("name"));
				member.setNum(rs.getString("num"));
				member.setAmount(rs.getInt("amount"));
				member.setHomeaddress(rs.getString("homeaddress"));
				members.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}
	
	
	// 아이디와 일치하는 회원정보 가져오기
	public MemberDTO getMember (String id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM scene WHERE id=?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				MemberDTO member = new MemberDTO();
				member.setPw(rs.getString("pw"));
				member.setName(rs.getString("name"));
				member.setNum(rs.getString("num"));
				member.setAmount(rs.getInt("amount"));
				member.setHomeaddress(rs.getString("homeaddress"));
				
				return member;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 관리자모드에서 회원추가하기
	public void mbAdd(MemberDTO dto) {
		String sql = "INSERT INTO scene VALUES(?,?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getPw());
			ps.setString(3, dto.getName());
			ps.setString(4, dto.getNum());
			ps.setInt(5, dto.getAmount());
			ps.setString(6, dto.getHomeaddress());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 관리자모드에서 회원수정하기
	public void mbUpdate(MemberDTO dto) {
		String sql = "UPDATE scene SET pw=?, name=?, num=?, amount=?, homeaddress=? WHERE id=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getPw());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getNum());
			ps.setInt(4, dto.getAmount());
			ps.setString(5, dto.getHomeaddress());
			ps.setString(6, dto.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 관리자모드에서 회원삭제하기
	public void mbDelete(MemberDTO dto){
		String sql = "DELETE FROM scene WHERE id=?";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
