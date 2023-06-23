package service;

import equipment.CommonService;
import javafx.scene.Parent;
import member.MemberDAO;

public class LoginService {
    private static LoginService instance;
    private MemberDAO memberDao;
    private Parent login;

    public LoginService() {
        memberDao = new MemberDAO();
    }

    public boolean login(String id, String pw) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        if (pw == null || pw.isEmpty()) {
            return false;
        }
        // 아이디는 5~20
        if (id.length() > 20) {
            return false;
        }

        String dbPw = memberDao.login(id);

        if (dbPw != null && dbPw.equals(pw)) {
            // 로그인 성공
            Login.setId(id);
            CommonService.msg("로그인 성공");
            return true;
        } else {
            // 아이디 또는 비밀번호가 틀렸습니다.
            CommonService.msg("아이디 또는 비밀번호가 틀렸습니다.");
            return false;
        }
    }
}
