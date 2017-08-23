package com.elian.cms.mall.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.action.LoginAction;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.User;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 商城登陆功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class MallLoginAction extends LoginAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 3744498219689067010L;

	@Override
	public int checkLogin() {
		List<User> userList = userService.findByAll(user.getAccount(), user
				.getPassword(), null);
		if (userList != null && userList.size() == 1) {
			User user = userList.get(0);
			if (user.getStatus() != ElianUtils.STATUS_1)
				return 1;
			ApplicationUtils.setUser(user);
			List<Site> siteList = siteService.findByUser(user.getId());
			if (!CollectionUtils.isEmpty(siteList)) {
				setDataInfoInSession(user, siteList);
			}
			return 2;
		}
		else
			return 0;
	}

	public void loginJson() {
		validateLogin();
		Map<String, List<String>> map = getFieldErrors();
		JSONObject json = new JSONObject();
		if (CollectionUtils.isEmpty(map)) {
			login();
			json.put("STATUS", 1);
			json.put("MSG", ApplicationUtils.getUser().getRealName());
		}
		else {
			json.put("STATUS", 0);
			json.put("MSG", map.values().toArray()[0]);
		}
		ApplicationUtils.sendJsonStr(json.toString());
	}
}
