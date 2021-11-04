package cn.oxo.iworks.builds.test.bean.search;

import cn.oxo.iworks.builds.test.bean.CustomerBean;
import java.util.List;
public class CustomerSearchBean extends CustomerBean {

 private List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
}
