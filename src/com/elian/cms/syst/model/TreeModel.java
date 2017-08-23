package com.elian.cms.syst.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树结构模型
 * 
 * @author Joe
 * 
 */
public class TreeModel<T extends Tree> implements Serializable {
	private static final long serialVersionUID = -7164245370788896576L;
	/** 树结构数据源 */
	private List<T> dataList = null;
	/** 存取树结构Map(key: parentId, value: valueList) */
	private Map<Integer, List<T>> treeMap = null;

	public TreeModel(List<T> list) {
		this.dataList = list;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, List<T>> getTreeMap() {
		if (treeMap == null) {
			if (dataList == null || dataList.size() < 1) {
				return null;
			}
			Sorter sort = new Sorter();
			sort.addPropertyName("depth", false);
			Collections.sort(dataList, sort);

			treeMap = new HashMap<Integer, List<T>>();
			for (T t : dataList) {
				List<T> list = treeMap.get(t.getParentId());
				if (list == null)
					list = new ArrayList<T>();
				list.add(t);
				treeMap.put(t.getParentId(), list);
			}
		}
		return treeMap;
	}
}
