package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.model.OrderDetail;
import com.elian.cms.admin.service.InstrumentService;
import com.elian.cms.admin.service.MedicineService;
import com.elian.cms.admin.service.OrderDetailService;
import com.elian.cms.admin.service.OrderService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 订单明细功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class OrderDetailAction extends BaseAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 3744498219689067010L;

	private Integer orderId = null;
	private Pagination<OrderDetail> pagination = new Pagination<OrderDetail>();
	private List<SelectItem> commodityTypeList = null;

	private OrderService orderService;
	private OrderDetailService orderDetailService;
	private MedicineService medicineService;
	private InstrumentService instrumentService;

	public String list() {
		orderDetailService.findByAll(orderId, pagination);
		if (pagination.getList() != null) {
			Medicine m = null;
			Instrument i = null;
			for (OrderDetail od : pagination.getList()) {
				String type = od.getCommodityType();
				if (ElianUtils.COMMODITY_TYPE_MEDICINE.equals(type)) {
					m = medicineService.get(od.getCommodityId());
					od.setCommodityName(m == null ? null : m.getCnName());
				}
				else if (ElianUtils.COMMONDITY_TYPE_INSTRUMENT.equals(type)) {
					i = instrumentService.get(od.getCommodityId());
					od.setCommodityName(i == null ? null : i.getCnName());
				}
			}
		}
		return LIST;
	}
	
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			OrderDetail item = null;
			for (Integer id : idList) {
				item = orderDetailService.get(id);
				if (item == null)
					continue;
				orderDetailService.delete(item);
			}
		}
	}
	
	public String getOrderCode() {
		if (orderId == null || orderId < 0)
			return "";
		else
			return orderService.get(orderId).getOrderCode();
	}

	public List<SelectItem> getCommodityTypeList() {
		if (commodityTypeList == null) {
			commodityTypeList = new ArrayList<SelectItem>(2);
			commodityTypeList.add(new SelectItem(ElianUtils.COMMODITY_TYPE_MEDICINE,
					ElianUtils.COMMODITY_TYPE_MEDICINE_CN));
			commodityTypeList.add(new SelectItem(ElianUtils.COMMONDITY_TYPE_INSTRUMENT,
					ElianUtils.COMMONDITY_TYPE_INSTRUMENT_CN));
		}
		return commodityTypeList;
	}
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Pagination<OrderDetail> getPagination() {
		return pagination;
	}
	
	@Resource
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Resource
	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	@Resource
	public void setMedicineService(MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@Resource
	public void setInstrumentService(InstrumentService instrumentService) {
		this.instrumentService = instrumentService;
	}
}
