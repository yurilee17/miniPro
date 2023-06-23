package service;

import java.util.ArrayList;
import java.util.Optional;

import equipment.CommonService;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import order.OrderInfoDAO;
import order.OrderInfoDTO;
import order.OrderOptionDAO;
import order.OrderOptionDTO;
import review.ReviewDAO;

public class OrderDeliveryDetailService {
	private OrderInfoDAO daoInfo;
	private OrderOptionDAO daoOption;

	public OrderDeliveryDetailService() {
		daoInfo = new OrderInfoDAO();
		daoOption = new OrderOptionDAO();
	}

	// 번호에 따른 정보 가져오기
	public OrderInfoDTO settingInfo(int num) {
		OrderInfoDTO setting = daoInfo.selectOne(num);
		return setting;
	}

	// 번호에 따른 옵션들 정보 가져오기
	public ArrayList<OrderOptionDTO> settingOption(int num) {
		ArrayList<OrderOptionDTO> option = daoOption.selectNum(num);
		return option;
	}

	// 내역 삭제 선택 시
	public Boolean clickDeleteBT(int num) {
		// 버튼 텍스트 변경
		ButtonType yesButton = new ButtonType("삭제", ButtonData.OK_DONE);
		ButtonType noButton = new ButtonType("취소", ButtonData.CANCEL_CLOSE);

		// 알림창 표시 및 사용자의 선택 처리
		Optional<ButtonType> result = CommonService.showConfirmationAlert("삭제 확인", "해당 주문내역을 삭제 하시겠습니까?", yesButton,
				noButton);

		// 삭제 버튼 선택 시
		if (result.isPresent() && result.get().equals(yesButton)) {
			daoInfo.orderDataDelete(num);
			daoOption.orderDataDelete(num);
			CommonService.showInformationAlert("삭제 알림", "삭제하였습니다");
			return true;
		} else
			return false;
	}

	// 버튼 선택 시 동작(구매 확정/주문 취소/교환 요청/환불요청)
	public Boolean clickButton(String title, String text, String yesBT, String noBT, OrderOptionDTO settingOption) {
		// 버튼 텍스트 변경
		ButtonType yesButton = new ButtonType(yesBT, ButtonData.OK_DONE);
		ButtonType noButton = new ButtonType(noBT, ButtonData.CANCEL_CLOSE);

		// 알림창 표시 및 사용자의 선택 처리
		Optional<ButtonType> result = CommonService.showConfirmationAlert(title, text, yesButton, noButton);

		// yesBT 버튼 선택 시
		if (result.isPresent() && result.get().equals(yesButton)) {

			// 구매 확정 버튼 선택 시
			if (title.equals("구매 확정")) {
				daoOption.updateStatus("구매확정", settingOption);
				return true;

				// 주문 취소 버튼 선택 시
			} else if (title.equals("주문 취소")) {
				daoOption.updateStatus("주문취소", settingOption);
				CommonService.showInformationAlert("주문 취소", "주문 취소를 완료하였습니다.");
				return true;

				// 교환 요청 버튼 선택 시
			} else if (title.equals("교환 요청")) {
				// 교환 사유 작성 창
				Optional<String> resultReason = CommonService.showTextInAlert("교환 사유 작성",
						"교환 사유를 작성해주세요.\n\n" + "* 교환 절차\n"
								+ " 마이페이지 주문내역에서 교환 신청 > 관리자 사유 및 재고 확인 후 승인 > 택배사 교환수거 > 입고 후 교환 확정 및 배송 \n\n"
								+ " * 주의사항\n" + " 교환은 제품이 입고 된 후 검수 완료된 뒤 확정되어 처리가 진행됩니다.\n\n" + "* 교환 불가 안내\n"
								+ " 상품 수령 후 7일이 지난 경우\n" + " 처음 배송된 상품 상태가 다른 경우(훼손, 오염, 세탁, 향기, 수선 등)\n"
								+ " 착용 흔적, 오염, 라벨제거 등\n" + " 그 외 전자상거래 등에서 소비자 보호에 관한 법률이 정하는 청약철회에 해당하는 경우",
						"교환 사유 : ");
				// 확인 버튼 선택 시
				if (resultReason.isPresent()) {
					String reason = resultReason.get();

					// 교환 사유 미입력 시
					if (reason.equals("")) {
						CommonService.showInformationAlert("교환 사유 작성 확인", "교환 사유를 작성하세요.");
					} else {
						daoOption.addMemo(reason, settingOption);
						daoOption.updateStatus("교환요청", settingOption);
						CommonService.showInformationAlert("교환 요청", "교환 요청하였습니다.");
						return true;
					}
					// 취소 버튼 선택 시
				} else {
					return false;
				}

				// 교환 요청 버튼 선택 시
			} else if (title.equals("환불 요청")) {
				// 환불 사유 작성 창
				Optional<String> resultReason = CommonService.showTextInAlert("환불 사유 작성",
						"환불 사유를 작성해주세요.\n\n" + "* 환불 절차\n"
								+ " 마이페이지 주문내역에서 환불 신청 > 관리자 확인 후 승인 > 택배사 환불수거 > 입고 후 환불 확정 및 진행 \n\n" + " * 주의사항\n"
								+ " 환불은 제품이 입고 된 후 검수 완료된 뒤 확정되어 처리가 진행됩니다.\n\n" + "* 환불 불가 안내\n"
								+ " 상품 수령 후 7일이 지난 경우\n" + " 처음 배송된 상품 상태가 다른 경우(훼손, 오염, 세탁, 향기, 수선 등)\n"
								+ " 착용 흔적, 오염, 라벨제거 등\n" + " 그 외 전자상거래 등에서 소비자 보호에 관한 법률이 정하는 청약철회에 해당하는 경우",
						"환불 사유 : ");
				// 확인 버튼 선택 시
				if (resultReason.isPresent()) {
					String reason = resultReason.get();

					// 환불 사유 미입력 시
					if (reason.equals("")) {
						CommonService.showInformationAlert("환불 사유 작성 확인", "환불 사유를 작성하세요.");
					} else {
						daoOption.addMemo(reason, settingOption);
						daoOption.updateStatus("환불요청", settingOption);
						CommonService.showInformationAlert("화불 요청", "환불 요청하였습니다.");
						return true;
					}
					// 취소 버튼 선택 시
				} else {
					return false;
				}
			} else {
				return false;
			}

//			return true;
		}
		return false;
	}

	// 리뷰(별점) 버튼 선택 시
	public Boolean clickReview(String date, OrderOptionDTO settingOption) {
		ReviewDAO dao = new ReviewDAO();
		if (dao.selectReview(date, settingOption)) {
			return false;
		} else {
			return true;
		}
	}
}
