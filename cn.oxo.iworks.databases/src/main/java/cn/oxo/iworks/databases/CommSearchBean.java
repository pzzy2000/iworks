package cn.oxo.iworks.databases;

public class CommSearchBean {


//	@ApiModelProperty(name = "commSearchBean.sort",example="desc")
	private String sort;
//	@ApiModelProperty(name = "commSearchBean.dir",example="id")
	private String dir;
//	@ApiModelProperty(name = "commSearchBean.pageNum",example="1")
	private Integer pageNum;
//	@ApiModelProperty(name = "commSearchBean.pageSize",example="10")
	private Integer pageSize;

	// //主要是用在hbase
	// private String startRow;

//	public Long getSum() {
//		return sum;
//	}
//
//	public void setSum(Long sum) {
//		this.sum = sum;
//	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

//	public Integer getStart() {
//		return start;
//	}
//
//	public void setStart(Integer start) {
//		this.start = start;
//	}
//
//	public Integer getLimit() {
//		return limit;
//	}

//	public void setLimit(Integer limit) {
//		this.limit = limit;
//	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
