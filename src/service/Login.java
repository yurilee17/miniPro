package service;

public class Login {
	private static String id;
	private static String pw;
	private static String name;
	private static String num;
	private static String homeaddress;
    static boolean loggedIn = false;
	public static String userID;

	public static boolean isLoggedIn() {
		return loggedIn;
	}

	public static void setLoggedIn(boolean loggedIn) {
		if(loggedIn == false) {
			Login.id = null;
		}
		Login.loggedIn = loggedIn;
	}

	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		Login.id = id;
	}

	public static String getPw() {
		return pw;
	}

	public static void setPw(String pw) {
		Login.pw = pw;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		Login.name = name;
	}

	public static String getNum() {
		return num;
	}

	public static void setNum(String num) {
		Login.num = num;
	}

	public static String getHomeaddress() {
		return homeaddress;
	}

	public static void setHomeaddress(String homeaddress) {
		Login.homeaddress = homeaddress;
	}

}
