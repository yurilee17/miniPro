package cart;

public class BasketService {
	private BasketDAO basketDao;
	
	public BasketService() {
		basketDao = new BasketDAO();
	}

	public BasketDTO basketInfo() {
		BasketDTO basketsetting = basketDao.getBasketInfomation();
		return basketsetting;
	}
}
