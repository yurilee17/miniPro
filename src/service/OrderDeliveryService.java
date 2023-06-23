package service;

import java.util.ArrayList;

import order.OrderInfoDAO;
import order.OrderInfoDTO;
import order.OrderOptionDAO;
import order.OrderOptionDTO;

public class OrderDeliveryService{
	private OrderInfoDAO daoInfo;
	private OrderOptionDAO daoOption;
	
	public OrderDeliveryService() {
		daoInfo = new OrderInfoDAO();
		daoOption = new OrderOptionDAO();
	}

	public ArrayList<OrderInfoDTO> settingInfo() {
		ArrayList<OrderInfoDTO> setting = daoInfo.selectAll();
		return setting;
	}
	
	public ArrayList<OrderInfoDTO> allsettingInfo() {
		ArrayList<OrderInfoDTO> allsetting = daoInfo.selectAll2();
		return allsetting;
	}
	
	public ArrayList<OrderOptionDTO> settingOption(int num) {
		ArrayList<OrderOptionDTO> option = daoOption.selectNum(num);
		return option;
	}
	
    public ArrayList<OrderOptionDTO> selectAll() {
        return daoOption.selectAll();
    }
}
