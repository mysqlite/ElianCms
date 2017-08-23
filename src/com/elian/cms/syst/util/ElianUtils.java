package com.elian.cms.syst.util;

import java.util.ArrayList;
import java.util.List;

import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.model.TrueFalseItem;

public class ElianUtils {
	/** 公司类型 */
	public static final String COMP_TYPE_HOSP_CN = "医院";
	public static final String COMP_TYPE_HOSP = "hospital";
	public static final String COMP_TYPE_COMP_CN = "企业";
	public static final String COMP_TYPE_COMP = "company";
	public static final String COMP_TYPE_MEDICINE_COMP_CN = "药品企业";
	public static final String COMP_TYPE_MEDICINE_COMP = "company_medicine";
	public static final String COMP_TYPE_INSTRUMENT_COMP_CN = "器械企业";
	public static final String COMP_TYPE_INSTRUMENT_COMP = "company_instrument";
	public static final String COMP_TYPE_SUBS_CN = "分站";
	public static final String COMP_TYPE_SUBS = "substation";
	public static final String COMP_TYPE_MAIN_CN = "总站";
	public static final String COMP_TYPE_MAIN = "mainstation";
	public static final String COMP_TYPE_PUBL_CN = "公有";
	public static final String COMP_TYPE_PUBL = "public";

	/** 未分组角色 */
	public static final String UNGROUPED_ROLE = "未分组";
	/** 状态 */
	public static final String STATUS_0_CN = "未开通";
	public static final int STATUS_0 = 0;
	public static final String STATUS_1_CN = "已开通";
	public static final int STATUS_1 = 1;
	public static final String STATUS_2_CN = "注销";
	public static final int STATUS_2 = 2;
	public static final String STATUS_3_CN = "删除";
	public static final int STATUS_3 = 3;
	public static final String STATUS_4_CN = "退回";
	public static final int STATUS_4 = 4;
	public static final String STATUS_5_CN = "注册";
	public static final int STATUS_5 = 5;

	public static final int DEL_TEMP_FILE_TIME = 1;

	/** 内容状态 */
	public static final String CONTENT_STATUS_0_CN = "草稿";
	public static final int CONTENT_STATUS_0 = 0;
	public static final String CONTENT_STATUS_1_CN = "未审";
	public static final int CONTENT_STATUS_1 = 1;
	public static final String CONTENT_STATUS_2_CN = "待审";
	public static final int CONTENT_STATUS_2 = 2;
	public static final String CONTENT_STATUS_3_CN = "通过";
	public static final int CONTENT_STATUS_3 = 3;
	public static final String CONTENT_STATUS_4_CN = "未通过";
	public static final int CONTENT_STATUS_4 = 4;

	/** 静态化状态 */
	public static final String STATIC_STATUS_0_CN = "未发布";
	public static final int STATIC_STATUS_0 = 0;
	public static final String STATIC_STATUS_1_CN = "已发布";
	public static final int STATIC_STATUS_1 = 1;
	public static final String STATIC_STATUS_2_CN = "需重新发布";
	public static final int STATIC_STATUS_2 = 2;

	/** 置顶级别 */
	public static final String TOP_STATUS_0_CN = "不置顶";
	public static final int TOP_STATUS_0 = 0;
	public static final String TOP_STATUS_1_CN = "一级";
	public static final int TOP_STATUS_1 = 1;
	public static final String TOP_STATUS_2_CN = "二级";
	public static final int TOP_STATUS_2 = 2;

	/** 类型的类型 */
	public static final String HOSP_NATURE = "hosp_nature";
	public static final String HOSP_TYPE = "hosp_type";
	public static final String HOSP_RANK = "hosp_rank";
	public static final String DEPT_NATURE = "dept_nature";
	public static final String FTP_TYPE = "ftp_type";
	public static final String MEDICINE_TYPE = "medicine_type";
	public static final String INSTRUMENT_TYPE = "instruType";

	public static final String HOSP_TYPES = "hospType";
	public static final String DEPT_NATURES = "deptNature";
	public static final String FTP_TYPES = "ftpType";
	public static final String INIV_TYPE = "inviType";
	public static final String MEDICINE_TYPES = "medicineType";

	/** 树级类型:科室类型 */
	public static final String DEPT_TYPE = "deptType";
	/** 医生评分 */
	public static final String DOCTOR_SCORE = "score";
	
	/**
	 * 商品类型
	 */
	public static final String COMMODITY_TYPE_MEDICINE = "medicine";
	public static final String COMMODITY_TYPE_MEDICINE_CN = "药品";
	public static final String COMMONDITY_TYPE_INSTRUMENT = "instrument";
	public static final String COMMONDITY_TYPE_INSTRUMENT_CN = "器械";

	/** 订单状态 */
	public static final String ORDER_STATUS_0_CN = "未处理";
	public static final int ORDER_STATUS_0 = 0;
	public static final String ORDER_STATUS_1_CN = "已处理";
	public static final int ORDER_STATUS_1 = 1;
	public static final String ORDER_STATUS_2_CN = "已完成";
	public static final int ORDER_STATUS_2 = 2;
	public static final String ORDER_STATUS_3_CN = "已作废";
	public static final int ORDER_STATUS_3 = 3;

	/** 付款状态 */
	public static final String PAYMENT_STATUS_0_CN = "未支付";
	public static final int PAYMENT_STATUS_0 = 0;
	public static final String PAYMENT_STATUS_1_CN = "部分支付";
	public static final int PAYMENT_STATUS_1 = 1;
	public static final String PAYMENT_STATUS_2_CN = "已支付";
	public static final int PAYMENT_STATUS_2 = 2;
	public static final String PAYMENT_STATUS_3_CN = "部分退款";
	public static final int PAYMENT_STATUS_3 = 3;
	public static final String PAYMENT_STATUS_4_CN = "全额退款";
	public static final int PAYMENT_STATUS_4 = 4;
	
	/** 支付类型 */
	public static final String DELIVERY_TYPE_0_CN = "网上支付";
	public static final String DELIVERY_TYPE_0 = "0";
	public static final String DELIVERY_TYPE_1_CN = "线下支付";
	public static final String DELIVERY_TYPE_1 = "1";

	public static List<SelectItem> getHospTypeList() {
		return SysXmlUtils.getXMLSelect(HOSP_TYPES);
	}

	public static List<SelectItem> getDoctorScore() {
		return SysXmlUtils.getXMLSelect(DOCTOR_SCORE);
	}

	public static List<SelectItem> getTypeList() {
		List<SelectItem> list = getHospTypeList();
		list.addAll(SysXmlUtils.getXMLSelect(DEPT_NATURES));
		list.addAll(SysXmlUtils.getXMLSelect(FTP_TYPES));
		list.addAll(SysXmlUtils.getXMLSelect(INIV_TYPE));
		list.addAll(SysXmlUtils.getXMLSelect(MEDICINE_TYPES));
		return list;
	}

	public static SelectItem getDeptType() {
		SelectItem item = new SelectItem();
		item.setKey(SysXmlUtils.getXMLSelect(DEPT_NATURES).get(0).getKey());
		item.setValue(SysXmlUtils.getXMLSelect(DEPT_NATURES).get(0).getValue());
		return item;
	}

	/** 栏目类型 */
	public static final String CHANNEL_PARENT_CN = "父栏目";
	public static final String CHANNEL_PARENT = "parent";
	public static final String CHANNEL_CONTENT_CN = "内容栏目";
	public static final String CHANNEL_CONTENT = "content";
	public static final String CHANNEL_OUT_CN = "外部链接";
	public static final String CHANNEL_OUT = "out";
	public static final String CHANNEL_SPECIAL_CN = "特殊页";
	public static final String CHANNEL_SPECIAL = "special";
	public static final String CHANNEL_INDEX_CN = "首页";
	public static final String CHANNEL_INDEX = "index";

	/** 内容类型 */
	public static final String CONTENT_SINGLE_CN = "单页";
	public static final String CONTENT_SINGLE = "single";
	public static final String CONTENT_LIST_CN = "列表页";
	public static final String CONTENT_LIST = "list";

	/** 是否启用状态 */
	public static final String DISABLE_TRUE_CN = "启用";
	public static final String DISABLE_FALSE_CN = "未启用";

	/** 是否推荐 */
	public static final String FALSE_0_CN = "否";
	public static final String TRUE_1_CN = "是";

	/** 树结构 */
	public static final String ROOT_URL = "根目录";
	public static final String TRUE_STR = "true";
	public static final String FALSE_STR = "false";

	/** 性别 */
	public static final String MALE_CN = "男";
	public static final String MALE = "male";
	public static final String FEMALE_CN = "女";
	public static final String FEMALE = "female";
	public static final String SECRET_CN = "保密";
	public static final String SECRET = "secret";
	public static final String UNLIMITED_CN = "不限";
	public static final String UNLIMITED = "Unli";

	/** 星期 */
	public static final String SUNDAY_CN = "星期日";
	public static final int SUNDAY = 0;
	public static final String MONDAY_CN = "星期一";
	public static final int MONDAY = 1;
	public static final String TUESDAY_CN = "星期二";
	public static final int TUESDAY = 2;
	public static final String WEDNESDAY_CN = "星期三";
	public static final int WEDNESDAY = 3;
	public static final String THURSDAY_CN = "星期四";
	public static final int THURSDAY = 4;
	public static final String FRIDAY_CN = "星期五";
	public static final int FRIDAY = 5;
	public static final String SATURDAY_CN = "星期六";
	public static final int SATURDAY = 6;

	public static List<SelectItem> getStatusList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(6);
		itemList.add(new SelectItem(ElianUtils.STATUS_0,
				ElianUtils.STATUS_0_CN, ElianCodes.COLOR_RED));
		itemList.add(new SelectItem(ElianUtils.STATUS_1,
				ElianUtils.STATUS_1_CN, ElianCodes.COLOR_GREEN));
		itemList.add(new SelectItem(ElianUtils.STATUS_2,
				ElianUtils.STATUS_2_CN, ElianCodes.COLOR_RED));
		itemList
				.add(new SelectItem(ElianUtils.STATUS_3, ElianUtils.STATUS_3_CN));
		itemList.add(new SelectItem(ElianUtils.STATUS_4,
				ElianUtils.STATUS_4_CN, ElianCodes.COLOR_GRAY));
		itemList.add(new SelectItem(ElianUtils.STATUS_5,
				ElianUtils.STATUS_5_CN, ElianCodes.COLOR_RED));
		return itemList;
	}

	public static List<SelectItem> getAvailableList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(6);
		itemList.add(new SelectItem(ElianUtils.STATUS_0,
				ElianUtils.STATUS_0_CN, ElianCodes.COLOR_RED));
		itemList.add(new SelectItem(ElianUtils.STATUS_1,
				ElianUtils.STATUS_1_CN, ElianCodes.COLOR_GREEN));
		itemList.add(new SelectItem(ElianUtils.STATUS_2,
				ElianUtils.STATUS_2_CN, ElianCodes.COLOR_RED));
		itemList
				.add(new SelectItem(ElianUtils.STATUS_3, ElianUtils.STATUS_3_CN));
		return itemList;
	}

	public static List<SelectItem> getDisableStatus() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(2);
		itemList.add(new SelectItem(STATUS_4, STATUS_4_CN));
		itemList.add(new SelectItem(STATUS_5, STATUS_5_CN));
		return itemList;
	}

	public static List<SelectItem> getCompTypeList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(6);
		itemList.add(new SelectItem(COMP_TYPE_MAIN, COMP_TYPE_MAIN_CN));
		itemList.add(new SelectItem(COMP_TYPE_SUBS, COMP_TYPE_SUBS_CN));
		itemList.add(new SelectItem(COMP_TYPE_HOSP, COMP_TYPE_HOSP_CN));
		itemList.add(new SelectItem(COMP_TYPE_COMP, COMP_TYPE_COMP_CN));
		itemList.add(new SelectItem(COMP_TYPE_MEDICINE_COMP, COMP_TYPE_MEDICINE_COMP_CN));
		itemList.add(new SelectItem(COMP_TYPE_INSTRUMENT_COMP, COMP_TYPE_INSTRUMENT_COMP_CN));
		return itemList;
	}

	public static List<SelectItem> getContentStatusList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(5);
		itemList.add(new SelectItem(CONTENT_STATUS_0, CONTENT_STATUS_0_CN));
		itemList.add(new SelectItem(CONTENT_STATUS_1, CONTENT_STATUS_1_CN));
		itemList.add(new SelectItem(CONTENT_STATUS_2, CONTENT_STATUS_2_CN));
		itemList.add(new SelectItem(CONTENT_STATUS_3, CONTENT_STATUS_3_CN));
		itemList.add(new SelectItem(CONTENT_STATUS_4, CONTENT_STATUS_4_CN));
		return itemList;
	}

	public static List<SelectItem> getStaticStatusList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(3);
		itemList.add(new SelectItem(STATIC_STATUS_0, STATIC_STATUS_0_CN));
		itemList.add(new SelectItem(STATIC_STATUS_1, STATIC_STATUS_1_CN));
		itemList.add(new SelectItem(STATIC_STATUS_2, STATIC_STATUS_2_CN));
		return itemList;
	}

	public static List<SelectItem> getTopmostList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(4);
		itemList.add(new SelectItem(TOP_STATUS_0, TOP_STATUS_0_CN));
		itemList.add(new SelectItem(TOP_STATUS_1, TOP_STATUS_1_CN));
		itemList.add(new SelectItem(TOP_STATUS_2, TOP_STATUS_2_CN));
		return itemList;
	}

	public static List<SelectItem> getChannelTypeList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(2);
		itemList.add(new SelectItem(ElianUtils.CHANNEL_PARENT,
				ElianUtils.CHANNEL_PARENT_CN));
		itemList.add(new SelectItem(ElianUtils.CHANNEL_CONTENT,
				ElianUtils.CHANNEL_CONTENT_CN));
		itemList.add(new SelectItem(ElianUtils.CHANNEL_OUT,
				ElianUtils.CHANNEL_OUT_CN));
		itemList.add(new SelectItem(ElianUtils.CHANNEL_INDEX,
				ElianUtils.CHANNEL_INDEX_CN));
		return itemList;
	}

	public static List<SelectItem> getContentTypeList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(2);
		itemList.add(new SelectItem(ElianUtils.CONTENT_SINGLE,
				ElianUtils.CONTENT_SINGLE_CN));
		itemList.add(new SelectItem(ElianUtils.CONTENT_LIST,
				ElianUtils.CONTENT_LIST_CN));
		return itemList;
	}

	public static List<SelectItem> getSexList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(3);
		itemList.add(new SelectItem(ElianUtils.MALE, ElianUtils.MALE_CN));
		itemList.add(new SelectItem(ElianUtils.FEMALE, ElianUtils.FEMALE_CN));
		itemList.add(new SelectItem(ElianUtils.SECRET, ElianUtils.SECRET_CN));
		return itemList;
	}

	public static List<SelectItem> getSex() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(2);
		itemList.add(new SelectItem(ElianUtils.MALE, ElianUtils.MALE_CN));
		itemList.add(new SelectItem(ElianUtils.FEMALE, ElianUtils.FEMALE_CN));
		return itemList;
	}

	public static List<SelectItem> getJobSex() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(3);
		itemList.add(new SelectItem(ElianUtils.MALE, ElianUtils.MALE_CN));
		itemList.add(new SelectItem(ElianUtils.FEMALE, ElianUtils.FEMALE_CN));
		itemList.add(new SelectItem(ElianUtils.UNLIMITED,
				ElianUtils.UNLIMITED_CN));
		return itemList;
	}

	public static TrueFalseItem getTrueFalseItem() {
		return new TrueFalseItem(TRUE_1_CN, ElianCodes.COLOR_GREEN, FALSE_0_CN,
				ElianCodes.COLOR_RED);
	}

	public static TrueFalseItem getDisableItem() {
		return new TrueFalseItem(DISABLE_TRUE_CN, ElianCodes.COLOR_GREEN,
				DISABLE_FALSE_CN, ElianCodes.COLOR_RED);
	}

	public static double getMaxSize() {
		return ElianCodes.MAX_SIZE / (1024 * 1024);
	}
	
	public static double getMaxSize(long size) {
		return size / (1024 * 1024);
	}

	public static String getCompTypeCnName(String CompTypeEnName) {
		List<SelectItem> enName = ElianUtils.getCompTypeList();
		for (SelectItem item : enName) {
			if (item.getKey().equals(CompTypeEnName)) {
				if (ElianUtils.COMP_TYPE_COMP.equals(CompTypeEnName))
					return ElianUtils.COMP_TYPE_COMP_CN;
				if (ElianUtils.COMP_TYPE_MEDICINE_COMP.equals(CompTypeEnName))
					return ElianUtils.COMP_TYPE_MEDICINE_COMP_CN;
				if (ElianUtils.COMP_TYPE_INSTRUMENT_COMP.equals(CompTypeEnName))
					return ElianUtils.COMP_TYPE_INSTRUMENT_COMP_CN;
				if (ElianUtils.COMP_TYPE_HOSP.equals(CompTypeEnName))
					return ElianUtils.COMP_TYPE_HOSP_CN;
				if (ElianUtils.COMP_TYPE_MAIN.equals(CompTypeEnName))
					return ElianUtils.COMP_TYPE_MAIN_CN;
				if (ElianUtils.COMP_TYPE_SUBS.equals(CompTypeEnName))
					return ElianUtils.COMP_TYPE_SUBS_CN;
			}
		}
		return null;
	}

	public static String getChannelTypeCnName(String ChannelTypeEnName) {
		List<SelectItem> enName = ElianUtils.getChannelTypeList();
		for (SelectItem item : enName) {
			if (item.getKey().equals(ChannelTypeEnName)) {
				if (ElianUtils.CHANNEL_PARENT.equals(ChannelTypeEnName))
					return ElianUtils.CHANNEL_PARENT_CN;
				if (ElianUtils.CHANNEL_CONTENT.equals(ChannelTypeEnName))
					return ElianUtils.CHANNEL_CONTENT_CN;
				if (ElianUtils.CHANNEL_OUT.equals(ChannelTypeEnName))
					return ElianUtils.CHANNEL_OUT_CN;
				if (ElianUtils.CHANNEL_INDEX.equals(ChannelTypeEnName))
					return ElianUtils.CHANNEL_INDEX_CN;
			}
		}
		return null;
	}

	public static String getSexCnName(String sexEnName) {
		List<SelectItem> enName = ElianUtils.getSexList();
		for (SelectItem item : enName) {
			if (item.getKey().equals(sexEnName)) {
				if (ElianUtils.MALE.equals(sexEnName))
					return ElianUtils.MALE_CN;
				if (ElianUtils.FEMALE.endsWith(sexEnName))
					return ElianUtils.FEMALE_CN;
				if (ElianUtils.SECRET.equals(sexEnName))
					return ElianUtils.SECRET_CN;
			}
		}
		return null;
	}

	public static String getContentTypeCnName(String contentTypeEnName) {
		List<SelectItem> enName = ElianUtils.getContentTypeList();
		for (SelectItem item : enName) {
			if (item.getKey().equals(contentTypeEnName)) {
				if (ElianUtils.CONTENT_LIST.equals(contentTypeEnName))
					return ElianUtils.CONTENT_LIST_CN;
				if (ElianUtils.CONTENT_SINGLE.endsWith(contentTypeEnName))
					return ElianUtils.CONTENT_SINGLE_CN;
			}
		}
		return null;
	}

	public static List<SelectItem> getRegTypeList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(6);
		itemList.add(new SelectItem(1, "普通门诊"));
		itemList.add(new SelectItem(2, "专家门诊"));
		itemList.add(new SelectItem(3, "特需门诊"));
		itemList.add(new SelectItem(4, "专科门诊"));
		itemList.add(new SelectItem(5, "会诊中心"));
		itemList.add(new SelectItem(6, "夜间门诊"));
		return itemList;
	}

	public static List<SelectItem> getRankList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(3);
		itemList.add(new SelectItem(1, "上午"));
		itemList.add(new SelectItem(2, "下午"));
		itemList.add(new SelectItem(3, "晚上"));
		return itemList;
	}

	public static List<SelectItem> getWeekList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(7);
		itemList.add(new SelectItem(0, "日"));
		itemList.add(new SelectItem(1, "一"));
		itemList.add(new SelectItem(2, "二"));
		itemList.add(new SelectItem(3, "三"));
		itemList.add(new SelectItem(4, "四"));
		itemList.add(new SelectItem(5, "五"));
		itemList.add(new SelectItem(6, "六"));
		return itemList;
	}

	public static List<SelectItem> getOrderStatusList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(4);
		itemList.add(new SelectItem(ORDER_STATUS_0, ORDER_STATUS_0_CN));
		itemList.add(new SelectItem(ORDER_STATUS_1, ORDER_STATUS_1_CN));
		itemList.add(new SelectItem(ORDER_STATUS_2, ORDER_STATUS_2_CN));
		itemList.add(new SelectItem(ORDER_STATUS_3, ORDER_STATUS_3_CN));
		return itemList;
	}

	public static List<SelectItem> getPaymentStatusList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(5);
		itemList.add(new SelectItem(PAYMENT_STATUS_0, PAYMENT_STATUS_0_CN));
		itemList.add(new SelectItem(PAYMENT_STATUS_1, PAYMENT_STATUS_1_CN));
		itemList.add(new SelectItem(PAYMENT_STATUS_2, PAYMENT_STATUS_2_CN));
		itemList.add(new SelectItem(PAYMENT_STATUS_3, PAYMENT_STATUS_3_CN));
		itemList.add(new SelectItem(PAYMENT_STATUS_4, PAYMENT_STATUS_4_CN));
		return itemList;
	}
	
	public static List<SelectItem> getDeliveryTypeList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(2);
		itemList.add(new SelectItem(DELIVERY_TYPE_0, DELIVERY_TYPE_0_CN));
		itemList.add(new SelectItem(DELIVERY_TYPE_1, DELIVERY_TYPE_1_CN));
		return itemList;
	}
	
	public static List<SelectItem> getCompanyTypeList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(2);
		itemList.add(new SelectItem(COMP_TYPE_MEDICINE_COMP,COMP_TYPE_MEDICINE_COMP_CN));
		itemList.add(new SelectItem(COMP_TYPE_INSTRUMENT_COMP, COMP_TYPE_INSTRUMENT_COMP_CN));
		return itemList;
	}
}
