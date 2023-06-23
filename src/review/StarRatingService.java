package review;

import java.util.ArrayList;

import equipment.CommonService;
import order.OrderOptionDTO;
import product.ProductDAO;
import service.Login;

public class StarRatingService {
	private ReviewDAO dao;

	public StarRatingService() {
		dao = new ReviewDAO();
	}

	// 버튼 선택 시
	public Boolean clickAddBT(String setDate, OrderOptionDTO optionDto, double valueResult) {
		if (dao.selectReview(setDate, optionDto)) {
			CommonService.showInformationAlert("리뷰 완료", "이미 작성한 리뷰 입니다.");
			return true;
		} else if (valueResult == 0) {
			CommonService.showInformationAlert("리뷰 작성", "별점을 입력해주세요.");
			return false;
		} else {
			ReviewDTO dto = new ReviewDTO();
			dto.setOrnum(optionDto.getOrnum());
			dto.setId(Login.getId());
			dto.setOrdate(setDate);
			dto.setCode(optionDto.getPdCode());
			dto.setReview(valueResult);
			dao.addReivew(dto);
			ArrayList<Double> reviews = dao.selectPT(dto);
			int num = 0;
			double reviewsTotal = 0;
			for (Double value : reviews) {
				num++;
				reviewsTotal = reviewsTotal + value;
			}
			Double optionReview = reviewsTotal / num;
			ProductDAO pdDAO = new ProductDAO();
			pdDAO.updateReview(optionDto.getPdCode(), optionReview);
			CommonService.showInformationAlert("리뷰 작성", "리뷰 작성 완료하였습니다.");
			return true;
		}

	}

}