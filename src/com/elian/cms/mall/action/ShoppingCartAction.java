package com.elian.cms.mall.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.model.ShoppingCart;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.ShoppingCartService;
import com.elian.cms.front.dto.CartItemCookie;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.ShoppingCartCodes;
import com.elian.cms.syst.util.ShoppingCartUtil;

/**
 * 购物车功能
 * 
 * @author thy
 * 
 */
@Component
@Scope("prototype")
public class ShoppingCartAction extends BaseAction{
	private static final long serialVersionUID = 6345702559030474944L;
	
	private Integer siteId=null;//站点id
	private Integer id=null; //商品id
	private String type=null;//商品类型
	private int quantity=0;//数量
	private int totalQuantity=0;// 商品总数
	private int totalPrice=0;// 总计商品价格
	
	private ShoppingCartService shoppingCartService=null;
	private ContentService contentService=null;
	
	private final String MESSAGE="message";
	private final String STATUS="status";
	
	//列表页结果
	private List<Map<String, String>> cartList=new ArrayList<Map<String,String>>(10);
	//ajax前台信息
	private Map<String, String> jsonMap = new HashMap<String, String>();
	
	public void ajaxAdd(){
		BaseContent baseContent=shoppingCartService.checkMarketable(type,id);
		if(baseContent==null){
			addMessageToJosnMap(ShoppingCartCodes.COMMODITY_STATUS_ERROR,"该商品已下架");
			return ;
		}
		quantity=(quantity<1?1:quantity);
		
		User user = ApplicationUtils.getUser();
		if (user == null) {//未登录处理
			List<CartItemCookie> cartItemCookieList = new ArrayList<CartItemCookie>();
			boolean isExist = false;
			Cookie[] cookies = ApplicationUtils.getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							List<CartItemCookie> previousCartItemCookieList = getListFromCookie(CartItemCookie.class,cookie);
							for (CartItemCookie previousCartItemCookie : previousCartItemCookieList) {
								if (previousCartItemCookie.getI().equals(id+"") && previousCartItemCookie.getT().endsWith(type)) { //是否存在相同记录
									isExist = true;
									previousCartItemCookie.setQ(previousCartItemCookie.getQ() + quantity);
									//以下欠缺   检测是否还有商品
								} 
								cartItemCookieList.add(previousCartItemCookie);
								//检测商品价格
								Integer price=shoppingCartService.getGoodsPrice( previousCartItemCookie.getT(),
										Integer.parseInt(previousCartItemCookie.getI()));
								//totalQuantity += previousCartItemCookie.getQ();
								totalQuantity ++;
								totalPrice +=price*previousCartItemCookie.getQ(); 
							}
						}
					}
				}
			}
			if (!isExist) {
				int price=shoppingCartService.getGoodsPrice(type, id);
				if(price>0){
					CartItemCookie cartItemCookie = new CartItemCookie(id+"",siteId.toString(),type,quantity,new Date());
					cartItemCookieList.add(cartItemCookie);
					//totalQuantity += quantity;
					totalQuantity ++;
					totalPrice +=price;
				}else{
					addMessageToJosnMap(ShoppingCartCodes.COMMODITY_STATUS_ERROR,"该商品已下架");
					return;
				}
				//以下欠缺   检测是否还有商品
			}
			//以下欠缺   检测是否还有商品
			addToCookie(cartItemCookieList);
		} else {
			boolean isExist = false;
			List<ShoppingCart> previousCartItemList =shoppingCartService.getShoppingCartList(user);
			if (previousCartItemList != null) {
				for (ShoppingCart previousCartItem : previousCartItemList) {
					if (previousCartItem.getCommodityId().equals(id) 
							&& previousCartItem.getCommodityType().equals(type)) {
						isExist = true;
						previousCartItem.setCommodityCount(previousCartItem.getCommodityCount() + quantity);
						//以下欠缺   检测是否还有商品
						shoppingCartService.save(previousCartItem);
					}
					//totalQuantity += previousCartItem.getCommodityCount();
					totalQuantity ++;
					totalPrice +=shoppingCartService.getGoodsPrice(type, id)*previousCartItem.getCommodityCount();
				}
			}
			if (!isExist) {
				ShoppingCart shoppingCart = new ShoppingCart(siteId,user.getId(),type,id,quantity,new Date());
				shoppingCartService.save(shoppingCart);
				//totalQuantity += quantity;
				totalQuantity ++;
				totalPrice += shoppingCartService.getGoodsPrice(type, id)*quantity;
			}
		}
		
		addMessageToJosnMap(ShoppingCartCodes.COMMODITY_STATUS_SUCCESS,"添加成功！");
		JSONObject jsonObj=JSONObject.fromObject(jsonMap); 
		ApplicationUtils.sendJsonpObj(jsonObj);
	}

	public String list(){
		User user=ApplicationUtils.getUser();
		if(user==null){//未登录
			Cookie[] cookies = ApplicationUtils.getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)){
						List<CartItemCookie> cartItemCookieList = getListFromCookie(CartItemCookie.class,cookie);
						for (CartItemCookie cartItemCookie : cartItemCookieList) {
							BaseContent baseContent=shoppingCartService.getGoods(cartItemCookie.getT()
									,Integer.parseInt(cartItemCookie.getI()));
							if (baseContent != null) {
								Map<String, String> item=new HashMap<String, String>();
								addT(cartItemCookie.getT(),Integer.parseInt(cartItemCookie.getsId()), baseContent, item);
								item.put(ShoppingCartCodes.COMMODITY_COUNT, cartItemCookie.getQ()+"");
								cartList.add(item);
							}
						}
					}
				}
			}
		}else{
			List<ShoppingCart> cartItemList =shoppingCartService.getShoppingCartList(user);
			if(!CollectionUtils.isEmpty(cartItemList)){
				for(ShoppingCart cartItem:cartItemList){
					BaseContent baseContent=shoppingCartService.getGoods(cartItem.getCommodityType()
							,cartItem.getCommodityId());
					if (baseContent != null) {
						Map<String, String> item=new HashMap<String, String>();
						item.put(ShoppingCartCodes.COMMODITY_SHOPPING_CART_Id, cartItem.getId().toString());
						addT(cartItem.getCommodityType(),cartItem.getSiteId(), baseContent, item);
						item.put(ShoppingCartCodes.COMMODITY_COUNT, cartItem.getCommodityCount()+"");
						cartList.add(item);
					}
				}
			}
		}
		return LIST;
	}

	private void addT(String type,Integer siteId, BaseContent baseContent,
			Map<String, String> item) {
		Content content=null;
		String path="#";
		item.put(ShoppingCartCodes.COMMODITY_TYPE, type);
		item.put(ShoppingCartCodes.COMMODITY_Id, baseContent.getId()+"");
		if(ElianUtils.COMMONDITY_TYPE_INSTRUMENT.equals(type)){
			Instrument instrumen=(Instrument) baseContent;
			item.put(ShoppingCartCodes.COMMODITY_IMG, FilePathUtils.setOutFilePath(instrumen.getFrontsImg()));
			item.put(ShoppingCartCodes.COMMODITY_NAEM, instrumen.getTitle());
			item.put(ShoppingCartCodes.COMMODITY_SPECIFICATIONS, instrumen.getSpecifications());
			item.put(ShoppingCartCodes.COMMODITY_PRICE, instrumen.getPrice()+"");
			content=contentService.getByEntityId(siteId, Instrument.class, instrumen.getId());
			
		}else if(ElianUtils.COMMODITY_TYPE_MEDICINE.equals(type)){
			Medicine medicine=(Medicine) baseContent;
			item.put(ShoppingCartCodes.COMMODITY_IMG, FilePathUtils.setOutFilePath(medicine.getFrontsImg()));
			item.put(ShoppingCartCodes.COMMODITY_NAEM, medicine.getTitle());
			item.put(ShoppingCartCodes.COMMODITY_SPECIFICATIONS, medicine.getSpecification());
			item.put(ShoppingCartCodes.COMMODITY_PRICE, medicine.getPrice()+"");
			content=contentService.getByEntityId(siteId, Medicine.class, medicine.getId());
		}
		if(content!=null) {
			String prePath="http://comp"+FreemarkerCodes.DOMAIN_SUFFIX;
			path=prePath+ElianCodes.SPRIT +content.getSite().getId()+content.getChannel().getPath()
			+ ElianCodes.SPRIT + content.getId() + ElianCodes.SUFFIX_SHTML;
		} 
		
		item.put(ShoppingCartCodes.COMMODITY_PATH,path);
		
	}
	
	/**
	 * 登陆后将cookie中数据添加到数据库中
	 */
	public void ajaxLogin(){
		User user=ApplicationUtils.getUser();
		if(user==null) return;
		totalQuantity= shoppingCartService.getTotleRowByUser(user);
		Cookie[] cookies = ApplicationUtils.getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
					if (StringUtils.isNotEmpty(cookie.getValue())) {
						List<CartItemCookie> cartItemCookieList = getListFromCookie(CartItemCookie.class,cookie);
						String ids=addToDb(user, cartItemCookieList);
						jsonMap.put("ids", ids);
					}
				}
			}
		}
		addToCookie(null);
		jsonMap.put(ShoppingCartCodes.COMMODITY_TOTAL_COUNT
				, totalQuantity+"");
		jsonMap.put(STATUS, ShoppingCartCodes.COMMODITY_STATUS_SUCCESS);
		JSONObject jsonObj=JSONObject.fromObject(jsonMap); 
		ApplicationUtils.sendJsonpObj(jsonObj);		
	}

	//将cookies添加到
	private String addToDb(User user, List<CartItemCookie> cartItemCookieList) {
		if(CollectionUtils.isEmpty(cartItemCookieList)) return "";
		StringBuffer ids=new StringBuffer();
		Iterator<CartItemCookie> it=cartItemCookieList.iterator();
		while (it.hasNext()) {
			CartItemCookie c = it.next();
			ShoppingCart s=shoppingCartService.getShoppingCart(user.getId(), Integer.parseInt(c.getI()), c.getT());
			if(s!=null){
				s.setCommodityCount(s.getCommodityCount()+c.getQ());
				shoppingCartService.save(s);
				ids.append(s.getId()+",");
			}else{
				totalQuantity++;
				BaseContent baseContent=shoppingCartService.getGoods(c.getT(),Integer.parseInt(c.getI()));
				if (baseContent != null) {
					ShoppingCart shoppingCart = new ShoppingCart(
							Integer.parseInt(c.getsId()),
							user.getId(),
							c.getT(),
							Integer.parseInt(c.getI()),
							c.getQ(), c.getD());
					shoppingCartService.save(shoppingCart);
					ids.append(shoppingCart.getId()+",");
				}
			}
		}
		return ids.substring(0,ids.length()-1);
	}
	
	public void ajaxList(){
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		User user=ApplicationUtils.getUser();
		if(user==null){//没登陆
			HttpServletRequest request=ApplicationUtils.getRequest();
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							List<CartItemCookie> cartItemCookieList = getListFromCookie(CartItemCookie.class,cookie);
							for (CartItemCookie cartItemCookie : cartItemCookieList) {
								Map<String, Object> params=new HashMap<String, Object>(3);
								params.put(ShoppingCartCodes.COMMODITY_TYPE, cartItemCookie.getT());
								params.put(ShoppingCartCodes.COMMODITY_COUNT, cartItemCookie.getQ());
								params.put(ShoppingCartCodes.COMMODITY_SITE_ID, cartItemCookie.getsId());
								
								BaseContent baseContent=shoppingCartService.getGoods(cartItemCookie.getT()
										,Integer.parseInt(cartItemCookie.getI()));
								if(baseContent!=null){
									Map<String, String> jsonMap = new HashMap<String, String>();
									Map<String, Object> result=ShoppingCartUtil.getInstance().addInfor(params,baseContent,jsonMap);
									//totalQuantity += cartItemCookie.getQ();
									totalQuantity ++;
									totalPrice +=(Integer)(result==null?0:result.get(ShoppingCartCodes.COMMODITY_PRICE))
										*cartItemCookie.getQ();
									jsonList.add(jsonMap);
								}
							}
						}
					}else{	//cookie中没有购物车信息      可能是跨域后的结果
//						String shoppingCartDetail=getShoppingCartFromSession();
//						if(StringUtils.isNotBlank(shoppingCartDetail)){
//							addShoppingCartDetailToCookie(shoppingCartDetail);
//						}
					}
				}
			}
		}else{
			List<ShoppingCart> cartItemList = shoppingCartService.getShoppingCartList(user);
			if (cartItemList != null) {
				for (ShoppingCart cartItem : cartItemList) {
					Map<String, Object> params=new HashMap<String, Object>(3);
					params.put(ShoppingCartCodes.COMMODITY_TYPE, cartItem.getCommodityType());
					params.put(ShoppingCartCodes.COMMODITY_COUNT, cartItem.getCommodityCount());
					params.put(ShoppingCartCodes.COMMODITY_SITE_ID, cartItem.getSiteId());
					
					Map<String, String> jsonMap = new HashMap<String, String>();
					BaseContent baseContent=shoppingCartService.
						getGoods(cartItem.getCommodityType(), cartItem.getCommodityId());
					
					Map<String, Object> result=ShoppingCartUtil.getInstance().addInfor(params,baseContent,jsonMap);
					
					//totalQuantity += cartItem.getCommodityCount();
					totalQuantity ++;
					totalPrice += Integer.parseInt(result.get(ShoppingCartCodes.COMMODITY_PRICE).toString())
						*cartItem.getCommodityCount();
					jsonList.add(jsonMap);
				}
			}
		}
		addMessageToJosnMap(ShoppingCartCodes.COMMODITY_STATUS_SUCCESS, "成功获取列表！");
		jsonList.add(0, jsonMap);
		ApplicationUtils.sendJsonpList(jsonList);
	}

	// 删除购物车项
	public void ajaxDelete() {
		User user =ApplicationUtils.getUser();
		if (user == null) {
			HttpServletRequest request=ApplicationUtils.getRequest();
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							List<CartItemCookie> cartItemCookieList =getListFromCookie(CartItemCookie.class,cookie);
							Iterator<CartItemCookie> iterator = cartItemCookieList.iterator();
							while (iterator.hasNext()) {
								CartItemCookie cartItemCookie = iterator.next();
								if (cartItemCookie.getI().equals(id+"")) {
									iterator.remove();
								} else {
									int price= shoppingCartService.
										getGoodsPrice(cartItemCookie.getT(), Integer.parseInt(cartItemCookie.getI()));
									//totalQuantity += cartItemCookie.getQ();
									totalQuantity ++;
									totalPrice += price*cartItemCookie.getQ();
								}
							}
							addToCookie(cartItemCookieList);
						}
					}
				}
			}
		} else {
			List<ShoppingCart> cartItemList = shoppingCartService.getShoppingCartList(user);
			if (cartItemList != null) {
				for (ShoppingCart cartItem : cartItemList) {
					if (cartItem.getCommodityId().equals(id)&&cartItem.getCommodityType().equals(type)) {
						shoppingCartService.delete(cartItem);
					} else {
						int price= shoppingCartService.
							getGoodsPrice(cartItem.getCommodityType(), cartItem.getCommodityId());
						//totalQuantity += cartItem.getCommodityCount();
						totalQuantity ++;
						totalPrice += price*cartItem.getCommodityCount();
					}
				}
			}
		}
		addMessageToJosnMap(ShoppingCartCodes.COMMODITY_STATUS_SUCCESS,"删除成功！");
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		ApplicationUtils.sendJsonpObj(jsonObject);
	}
	
	// 清空购物车项
	public void ajaxClear() {
		User user=ApplicationUtils.getUser();
		if (user == null) {//未登录
			addToCookie(null);
		} else {
			shoppingCartService.clear(user);
		}
		addMessageToJosnMap(ShoppingCartCodes.COMMODITY_STATUS_SUCCESS,"清空成功");
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		ApplicationUtils.sendJsonpObj(jsonObject);
	}
	
	// 修改购物车项
	public void ajaxEdit() {
		quantity=(quantity<1?1:quantity);
		User user=ApplicationUtils.getUser();
		if (user == null) {
			Cookie[] cookies = ApplicationUtils.getRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (StringUtils.equalsIgnoreCase(cookie.getName(), CartItemCookie.CART_ITEM_LIST_COOKIE_NAME)) {
						if (StringUtils.isNotEmpty(cookie.getValue())) {
							List<CartItemCookie> cartItemCookieList=getListFromCookie(CartItemCookie.class,cookie);
							Iterator<CartItemCookie> iterator = cartItemCookieList.iterator();
							while (iterator.hasNext()) {
								CartItemCookie cartItemCookie = iterator.next();
								if (cartItemCookie.getI().equals(id.toString()) && 
										cartItemCookie.getT().equals(type)) {
									cartItemCookie.setQ(quantity);
								}
								Integer price=shoppingCartService.getGoodsPrice(
										cartItemCookie.getT(), Integer.parseInt(cartItemCookie.getI()));
								totalQuantity ++;
								totalPrice += price*cartItemCookie.getQ();
							}
							addToCookie(cartItemCookieList);
						}
					}
				}
			}
		} else {
			List<ShoppingCart> cartItemList = shoppingCartService.getShoppingCartList(user);
			if (cartItemList != null) {
				for (ShoppingCart cartItem: cartItemList) {
					if(cartItem.getCommodityType().equals(type) &&
							cartItem.getCommodityId().equals(id)){
						//检测商品库存
						cartItem.setCommodityCount(quantity);
						shoppingCartService.save(cartItem);
					}
					Integer price=shoppingCartService.getGoodsPrice(
							cartItem.getCommodityType(), cartItem.getCommodityId());
					//totalQuantity += cartItem.getCommodityCount();
					totalQuantity ++;
					totalPrice += price*cartItem.getCommodityCount();
				}
			}
		}
		addMessageToJosnMap(ShoppingCartCodes.COMMODITY_STATUS_SUCCESS,"修改成功！");
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		ApplicationUtils.sendJsonpObj(jsonObject);
	}
	
	public void ajaxCkeckLogin(){
		User user=ApplicationUtils.getUser();
		String status=ShoppingCartCodes.COMMODITY_STATUS_ERROR;
		String msg="您还未登陆，请登陆";
		if(user!=null) {
			status=ShoppingCartCodes.COMMODITY_STATUS_SUCCESS;
			msg="已经登陆";
		}
		jsonMap.put(STATUS, status);
		jsonMap.put(MESSAGE, msg);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		ApplicationUtils.sendJsonpObj(jsonObject);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public List<Map<String, String>> getCartList() {
		return cartList;
	}
	
	@Resource
	public void setShoppingCartService(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	@SuppressWarnings("unchecked")
	public<T> List<T> getListFromCookie(Class<T> objClass,Cookie cookie) {
		if(StringUtils.isBlank(cookie.getValue())) return new ArrayList<T>(1);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(objClass);
		JSONArray previousJsonArray = JSONArray.fromObject(cookie.getValue());
		List<T> cartItemCookieList = (List<T>) JSONSerializer.toJava(previousJsonArray, jsonConfig);
		return cartItemCookieList;
	}

	/**
	 * 添加到cookie
	 * @param cartItemCookieList
	 */
	private void addToCookie(List<CartItemCookie> cartItemCookieList) {
		String jsonStr=null;
		if(!CollectionUtils.isEmpty(cartItemCookieList)){
			JSONArray jsonArray = JSONArray.fromObject(cartItemCookieList);
			jsonStr=jsonArray.toString();
		}
		Cookie cookie = new Cookie(CartItemCookie.CART_ITEM_LIST_COOKIE_NAME,jsonStr);
		cookie.setPath(ApplicationUtils.getRequest().getContextPath() + "/");
		cookie.setMaxAge(CartItemCookie.CART_ITEM_LIST_COOKIE_MAX_AGE);
		ApplicationUtils.getResponse().addHeader("P3P", "CP=CAO PSA OUR");//解决iecookie问题
		ApplicationUtils.getResponse().addCookie(cookie);
	}
	
	/**
	 * 添加反馈到前台的json信息
	 * @param status error 发送信息到前台
	 */
	private void addMessageToJosnMap(String status,String msg){
		jsonMap.put(STATUS, status);
		jsonMap.put(MESSAGE, msg);
		if(ShoppingCartCodes.COMMODITY_STATUS_SUCCESS.equals(status)){
			jsonMap.put(ShoppingCartCodes.COMMODITY_TOTAL_COUNT
					, totalQuantity+"");
			jsonMap.put(ShoppingCartCodes.COMMODITY_TOTAL_PRICE
					, totalPrice+"");
		}else{
			JSONObject jsonObj=JSONObject.fromObject(jsonMap); 
			ApplicationUtils.sendJsonpObj(jsonObj);
		}
	}
}