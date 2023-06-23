package order;

import java.util.ArrayList;

public class OrderManagerDAO {
	private OrderInfoDAO infoDao;
	private OrderOptionDAO otDao;

	public OrderManagerDAO() {
		infoDao = new OrderInfoDAO();
		otDao = new OrderOptionDAO();
	}

	public ArrayList<OrderManagerDTO> selectAll() {
//		infoDao.selectOne();
		ArrayList<OrderOptionDTO> option = otDao.selectAll();

		ArrayList<OrderManagerDTO> results = new ArrayList<OrderManagerDTO>();

		for (OrderOptionDTO tmp : option) {
			OrderManagerDTO result = new OrderManagerDTO();
			result.setOrnum(tmp.getOrnum());
			result.setId(tmp.getId());
			result.setStatus(tmp.getStatus());
			result.setOrname(tmp.getOrname());
			result.setPdsize(tmp.getPdsize());
			result.setColor(tmp.getColor());
			result.setQuantity(tmp.getQuantity());
			result.setUnitPrice(tmp.getUnitPrice());
			result.setText(tmp.getText());
			result.setPdCode(tmp.getPdCode());
			OrderInfoDTO dto = infoDao.selectOne(tmp.getOrnum());
			result.setOrdate(dto.getOrdate());
			result.setTotalPrice(dto.getTotalPrice());
			result.setPayment(dto.getPayment());
			result.setRecipient(dto.getRecipient());
			result.setPhoneNum(dto.getPhoneNum());
			result.setOradd(dto.getOradd());
			result.setMemo(dto.getMemo());

			if (tmp.getStatus().equals("구매확정") || tmp.getStatus().equals("배송완료") || tmp.getStatus().equals("주문취소")
					|| tmp.getStatus().equals("교환불가") || tmp.getStatus().equals("환불불가") || tmp.getStatus().equals("환불확정")) {
				// 미출력
			} else
				results.add(result);
		}

		return results;
	}

}