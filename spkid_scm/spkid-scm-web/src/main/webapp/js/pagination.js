function Pagination(currPageC, pageSizeC, totalCountC) {
	var currPage;
	var pageSize;
	var totalCount;
	var totalPageCount;
	var params = new Array();
	var init = function() {
		// 构造函数
		currPage = currPageC;
		pageSize = pageSizeC;
		totalCount = totalCountC;
		totalPageCount = (totalCount - 1) / pageSize + 1;
	};
	/**
	 * 切换每页显示条数
	 * 
	 * @param data
	 */
	function switchPageSize(data) {
		if (data > 0) {
			pageSize = data;
		}
		commitPageForm();
	}
	/**
	 * 下一页
	 */
	function nextPage() {
		if (currPage < totalPageCount) {
			currPage += 1;
		} else {
			currPage = totalPageCount;
		}
		commitPageForm();
	}
	/**
	 * 上一页
	 */
	function beforePage() {
		if (currPage > 1) {
			currPage -= 1;
		} else {
			currPage = 1;
		}
		commitPageForm();
	}
	/**
	 * 第一页
	 */
	function firstPage() {
		currPage = 1;
		commitPageForm();
	}
	/**
	 * 最后一页
	 */
	function lastPage() {
		currPage = totalPageCount;
		commitPageForm();
	}
	/**
	 * 跳转到某页
	 * 
	 * @param lab
	 *            输入框ID
	 */
	function gotoPage(lab) {
		var data = document.getElementById(lab).value;
		if (data != null && data > 0 && data <= totalPageCount) {
			currPage = data;
			commitPageForm();
		}
	}
	/**
	 * 设置分页表单参数
	 * 
	 * @param key
	 *            名称
	 * @param value
	 *            值
	 */
	function setParam(_key, _value) {
		if (_key != null && key != "") {
			for (i = 0; i < this.params.length; i++) {
				if (this.params[i].key == _key) {
					this.params[i].value = _value;
					return;
				}
			}
			this.params.push({
				key : _key,
				value : _value
			});
		}
	}

	/**
	 * 提交分页form表单
	 */
	function commitPageForm() {
		refurbish();
		var pageForm = getPageForm();
		if (pageForm != null)
			pageForm.submit();
	}
	/**
	 * 刷新内部数据
	 */
	function refurbish() {
		setParam("currPage", this.currPage);
		setParam("pageSize", this.pageSize);
	}
	function getPageForm() {
		var pageForm = document.forms["pageForm"];
		if (pageForm != null) {
			var elements=pageForm.elements;
			for(var i=0;i<elements.length;i++){
				var tmpName=elements[i].name;
				if(tmpName!=null && tmpName =="currPage"){
					elements[i].value=this.currPagep;
				}
				if(tmpName!=null && tmpName =="pageSize"){
					elements[i].value=this.pageSize;
				}
			}
			return pageForm;
		} else {
			var tempForm = document.createElement("form");
			tempForm.action = url;
			tempForm.method = "post";
			tempForm.style.display = "none";
			for ( var x in this.params) {
				var opt = document.createElement("textarea");
				opt.name = x;
				opt.value = params[x];
				tempForm.appendChild(opt);
			}
			document.body.appendChild(tempForm);
			return tempForm;
		}

	}
}
