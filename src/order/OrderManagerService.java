package order;

import equipment.CommonService;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class OrderManagerService {

	private OrderInfoDAO infoDao;
	private OrderOptionDAO otDao;
	private OrderManagerDAO managerDao;

	public OrderManagerService() {
		infoDao = new OrderInfoDAO();
		otDao = new OrderOptionDAO();
		managerDao = new OrderManagerDAO();
	}

	public void updateStatusDelivery(String status, OrderOptionDTO dto) {
		if (status.equals("배송준비중") || status.equals("교환확정")) {
			otDao.updateStatus("배송중", dto);

			PauseTransition pause = new PauseTransition(Duration.seconds(20));
			pause.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (otDao.getStatus(dto) == "배송중")
						otDao.updateStatus("배송완료", dto);
				}
			});

			pause.play();

			// 원래는 7일 이후지만 우리는 확인을 위해 1분 이후로 변경
			PauseTransition pause2 = new PauseTransition(Duration.seconds(60));
			pause.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (otDao.getStatus(dto) == "배송완료")
						otDao.updateStatus("구매확정", dto);
				}
			});
			pause2.play();
		}else
			CommonService.msg("발송 가능한 상태가 아닙니다.");
	}

}
