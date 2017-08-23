package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Action;
import com.elian.cms.admin.model.Menu;
import com.elian.cms.admin.service.ActionService;
import com.elian.cms.admin.service.LogService;
import com.elian.cms.admin.service.MenuService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class MenuAction extends BaseAction {
	private static final long serialVersionUID = -674663291683748766L;
	
	private Pagination<Menu> pagination = new Pagination<Menu>(SearchParamUtils.getMenuConditionMap());
	private MenuService menuService;
	private boolean edit;
	private Menu menu;
	private Integer id;
	private boolean disable;
	private LogService logService;
	private String parentName;
	private boolean actionChild;
	private ActionService actionService;
	private boolean addAction;
	private String compType;
	private Integer actionId;
	private Integer parentId;
	public LogService getLogService() {
		return logService;
	}

	public List<Menu> findMenuByAll() {
		return menuService.findMenuByAll();
	}

	public Menu getMenuById(Integer id) {
		return menuService.get(id);
	}

	/** 根据父id获取所有子节点 */
	public void findMenuByParentId() {
		JSONObject obj = new JSONObject();
		obj.put("menu", getactionMenu(menuService.findMenuByParentId(parentId)));
		ApplicationUtils.sendJsonpObj(obj);
	}

	/** 获取菜单头部 */
	public void findMenuByRoot() {
		JSONObject obj = new JSONObject();
		obj.put("menus", getactionMenu(menuService.findMenuByParentId()));
		ApplicationUtils.sendJsonpObj(obj);
	}

	private List<Menu> getactionMenu(List<Menu> menuList) {
		Set<String> actionSet = ApplicationUtils.getActionSet();
		List<Menu> actionHeadMenu = new ArrayList<Menu>();
		if (null != menuList&actionSet!=null) {
			for (Menu menu : menuList) {
				if (menu.getParentId() == 0) {
					if (actionSet.contains(headMenuTrim(menu.getMenuUrl())))
						actionHeadMenu.add(menu);
				}
				else {
					if (actionSet.contains(menu.getMenuUrl()))
						actionHeadMenu.add(menu);
				}
			}
		}
		return actionHeadMenu;
	}

	private String headMenuTrim(String url) {
		return url.substring(url.lastIndexOf("&") + 1, url.length());
	}

	public List<Menu> findMenuNavigationById(Integer id) {
		return menuService.findMenuNavigationById(id);
	}

	public String list() {
		menuService.findMenuByPage(pagination);
		return LIST;
	}

	public String edit() {
		if (edit && id > 0) {
			menu = menuService.get(id);
			Menu menuPar=menuService.get(menu.getParentId());
			if(menuPar!=null) 
				parentName=menuPar.getMenuName();
			Action actionIds=actionService.getActionByActionName(null,menu.getMenuUrl());
			   actionId=actionIds!=null?actionIds.getId():null;  
		}
		return EDIT;
	}

	public String save() {
		Menu  dep=menuService.get(menu.getParentId());
		if (null== menu.getId()) 
			menu.setCreateTime(new Date());
		if(null==menu.getDepth()||edit) {	
			if(menu.getParentId()==0) {
				menu.setDepth(1);
			}
			else {
				if(null==dep)
					menu.setDepth(1);
				else
					menu.setDepth(dep.getDepth()+1);
			}
		}
		if (edit) {
			if(actionId!=null){
				Action action=actionService.get(actionId);
				action.setActionUrl(menu.getMenuUrl());
				action.setActionName(menu.getMenuName());
				actionService.save(action);
			}
		}
		else {
			Action action=new Action();
			if(addAction) {
				action.setActionName(menu.getMenuName());
				action.setActionUrl(menu.getMenuUrl());
				if(menu.getDepth()==1) {
					action.setActionUrl(menu.getMenuUrl().substring(menu.getMenuUrl().lastIndexOf("&")+1,menu.getMenuUrl().length()));
				}
				action.setDisable(menu.isDisable());
				action.setActionDesc(menu.getMenuDesc());
				action.setDepth(menu.getDepth());
				Action parAction=null!=dep?actionService.getActionByActionName(dep.getMenuName(),null):null;
				action.setParentId(null!=parAction?parAction.getId():0);
				action.setActionSort(menu.getMenuSort());
				actionService.save(action);
			}
			if(actionChild) {
				List<Action> subActionList = new ArrayList<Action>(5);
				subActionList.add(createSubAction("查看", "show",action));
				subActionList.add(createSubAction("添加", "add",action));
				subActionList.add(createSubAction("修改", "update",action));
				subActionList.add(createSubAction("删除", "delete",action));
				subActionList.add(createSubAction("审核", "check",action));
				for (Action act : subActionList) {
				    act.setParentId(action.getId());
					actionService.save(act);
				}
			}
		}
		menuService.save(menu);
		return SAVE;
	}
	public Action createSubAction(String actionStr, String url,Action action) {
			Action act = new Action();
			act.setActionName(action.getActionName()+ actionStr);
			act.setActionUrl(action.getActionUrl().substring(action.getActionUrl().lastIndexOf("/")+1,action.getActionUrl().indexOf("!")+1)+ url);
			act.setActionDesc(action.getActionName()+"下的子权限;");
			act.setParentId(action.getId());
			act.setDisable(action.isDisable());
			act.setDepth(action.getDepth()+1);
			return act;
	}
	
	public List<SelectItem> getCompTypeList() {
		return ElianUtils.getCompTypeList();
	}
	
	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			menu = menuService.get(id);
			if (menu == null)
				return NONE;
			menu.setDisable(!disable);
			menuService.save(menu);
		}
		return NONE;
	}

	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			menu = menuService.get(id);
			if (menu == null)
				return NONE;
			menu.setMenuSort(sort);
			menuService.save(menu);
		}
		return NONE;
	}

	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				menu = menuService.get(id);
				menuService.delete(menu);
			}
		return NONE;
	}
	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				menu = menuService.get(id);
				menu.setDisable(!menu.isDisable());
				menuService.save(menu);
			}
	}

	public void validateSave() {
		if (!ValidateUtils.strLenthAll(menu.getMenuName(), 1, 20))
			this.addFieldError("menu.menuName", "菜单名称长度为1-20之间");
		if (ValidateUtils.isLengOut(menu.getMenuDesc(), 255))
			this.addFieldError("menu.menuDesc", "菜单描述字符长度小于256个字");
		if (!ValidateUtils.strLenthAll(menu.getMenuUrl(), 5,255))
			this.addFieldError("menu.menuUrl", "菜单路径长度于5-255之间 ");
		if (menu.getParentId() == null)
			this.addFieldError("menu.parentId", "请选择菜单级别");
		if (menu.getMenuSort() == null
				|| ValidateUtils.isNumber(menu.getMenuSort().toString())
				|| menu.getMenuSort() > 9999) {
			this.addFieldError("menu.menuSort", "请输入1-9999的正整数");
			menu.setMenuSort(1);
		}
		if(actionChild) {
			if(!menu.getMenuUrl().contains(".action")) {
				this.addFieldError("menu.menuUrl", "您选择了[添加增删查改审核权限],请填写正确的菜单路径");
			}
		}
	}
	
	public TrueFalseItem getDisableItem() {
		return ElianUtils.getDisableItem();
	}

	public void dwrDelete(Integer id) {
		menu = menuService.get(id);
		menuService.delete(menu);
	}

	public void updateName(Integer id, String name) {
		menu = menuService.get(id);
		menu.setMenuName(name);
		menuService.save(menu);
	}
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Menu getMenu(Integer parentId) {
		return menuService.get(parentId);
	}

	@Resource
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	@Resource
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	@Resource
	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public Pagination<Menu> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Menu> pagination) {
		this.pagination = pagination;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public boolean isActionChild() {
		return actionChild;
	}

	public void setActionChild(boolean actionChild) {
		this.actionChild = actionChild;
	}

	public boolean isAddAction() {
		return addAction;
	}

	public void setAddAction(boolean addAction) {
		this.addAction = addAction;
	}

	public String getCompType() {
		return compType;
	}

	public void setComType(String compType) {
		this.compType = compType;
	}

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
