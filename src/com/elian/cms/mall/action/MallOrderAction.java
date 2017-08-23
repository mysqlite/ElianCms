package com.elian.cms.mall.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.DeliveryAddress;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.model.ShoppingCart;
import com.elian.cms.admin.service.DeliveryAddressService;
import com.elian.cms.admin.service.InstrumentService;
import com.elian.cms.admin.service.MedicineService;
import com.elian.cms.admin.service.ShoppingCartService;
import com.elian.cms.mall.dto.OrderItem;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 商城订单功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class MallOrderAction extends BaseAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 3744498219689067010L;

	// 站点ID
	private Integer siteId;
	// 购物车传递过来的购物车ids
	private String ids;

	// 收货地址列表
	private List<DeliveryAddress> addressList;
	// 新添加的收货地址
	private DeliveryAddress address;
	// 用于收货地址单选
	private Integer addressId;
	// 是否保存收货地址
	private boolean isSave = true;
	// 支付方式
	private String deliveryType = ElianUtils.DELIVERY_TYPE_0;
	// 附言
	private String description;

	// 呈现在前台的订单明细项
	private List<OrderItem> orderItemList;
	// 总个数
	private int totalQuantity = 0;
	// 总金额
	private Integer totalPrice = 0;

	private DeliveryAddressService deliveryAddressService;
	private ShoppingCartService shoppingCartService;
	private MedicineService medicineService;
	private InstrumentService instrumentService;

	public String list() {
		return LIST;
	}

	public void listJson() {
		loadShopCart();
		JSONObject obj = new JSONObject();
		obj.put("list", orderItemList);
		obj.put("totalQuantity", totalQuantity);
		obj.put("totalPrice", totalPrice);
		ApplicationUtils.sendJsonpObj(obj);
	}

	public List<ShoppingCart> loadShopCart() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		List<ShoppingCart> list = shoppingCartService.get(idList);

		if (list != null) {
			orderItemList = new ArrayList<OrderItem>(list.size());
			Medicine m = null;
			Instrument i = null;
			OrderItem item = null;
			String type = null;
			for (ShoppingCart cart : list) {
				item = new OrderItem(cart);
				type = cart.getCommodityType();
				if (ElianUtils.COMMODITY_TYPE_MEDICINE.equals(type)) {
					m = medicineService.get(cart.getCommodityId());
					item.setImage(m.getFrontImg());
					item.setName(m.getCnName());
					item.setSpecification(m.getSpecification());
					item.setUnitPrice(m.getPrice());
					item.setSubTotalPrice(item.getQuantity()
							* item.getUnitPrice());
					item.setCompanyId(m.getCompany().getId());

				}
				else if (ElianUtils.COMMONDITY_TYPE_INSTRUMENT.equals(type)) {
					i = instrumentService.get(cart.getCommodityId());
					item.setImage(i.getFrontImg());
					item.setName(i.getCnName());
					item.setSpecification(i.getSpecifications());
					item.setUnitPrice(i.getPrice());
					item.setSubTotalPrice(item.getQuantity()
							* item.getUnitPrice());
					item.setCompanyId(i.getCompany().getId());
				}
				totalQuantity += item.getQuantity();
				totalPrice += item.getSubTotalPrice();
				orderItemList.add(item);
			}
		}
		return list;
	}
	
	public static List<SelectItem> getDeliveryTypeList() {
		return ElianUtils.getDeliveryTypeList();
	}
	
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getAddressId() {
		getAddressList();
		if (!CollectionUtils.isEmpty(addressList)) {
			addressId = addressList.get(0).getId();
		}
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	
	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	public DeliveryAddress getAddress() {
		return address;
	}

	public void setAddress(DeliveryAddress address) {
		this.address = address;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<DeliveryAddress> getAddressList() {
		if(addressList == null ){
			addressList = deliveryAddressService.findByAll(ApplicationUtils.getUser().getId(), null);
		}
		return addressList;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	@Resource
	public void setDeliveryAddressService(
			DeliveryAddressService deliveryAddressService) {
		this.deliveryAddressService = deliveryAddressService;
	}

	@Resource
	public void setShoppingCartService(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
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
