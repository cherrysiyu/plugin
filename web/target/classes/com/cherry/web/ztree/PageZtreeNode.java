package com.cherry.web.ztree;
/**
 *带分页的ZTree节点信息类(树上节点满足一定数量时需要分页用,没有这种需求的时候不推荐用)
 * <p></p>
 * <ul><b>使用树节点分页实例:</b><ul>
 * <li>页面:<br>
 * 
   1.引入树需要的样式
	.ztree li span.button.firstPage {float:right; margin-left:2px; margin-right: 0; background-position:-144px -16px; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.prevPage {float:right; margin-left:2px; margin-right: 0; background-position:-144px -48px; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.nextPage {float:right; margin-left:2px; margin-right: 0; background-position:-144px -64px; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.lastPage {float:right; margin-left:2px; margin-right: 0; background-position:-144px -32px; vertical-align:top; *vertical-align:middle}
   <br>
   2.JSP页面或者js中
	<body>
    <div class="zTreeDemoBackground left"> 
		<ul id="treeDemo" class="ztree"></ul> 
	</div> 
  <script type="text/javascript">
  	var setting = {
			async: {
				enable: true,
				url: getUrl
			},
			check: {
				enable: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				addDiyDom: addDiyDom
			},
			callback: {
				beforeExpand: beforeExpand,
				onAsyncSuccess: onAsyncSuccess
			}
		};
 
		var zNodes =[];
		//全局变量，如果输的下一层节点小于这个pageSize则不会有分页
 		var pageSize = 500;
		function getUrl(treeId, treeNode) {
			var other="";
			if(treeNode != null && treeNode!="undefined" && treeNode!=undefined){
				var pageEnd =  Math.round(treeNode.counts/pageSize - .5) + (treeNode.counts%pageSize == 0 ? 0:1);
				if(treeNode.counts>pageSize){
					var pageEnd =  Math.round(treeNode.counts/pageSize - .5) + (treeNode.counts%pageSize == 0 ? 0:1);
					var aObj = $("#" + treeNode.tId + "_a");
					aObj.children("span:last").text(treenode.name+"("+(treenode.pageIndex+"/"+pageEnd)+")");
					aObj.attr("title", "当前第 " + treeNode.pageIndex + " 页 / 共 " + pageEnd + " 页")
				}
				 other="&parentId="+treeNode.id;
			}else{
				other="&isRoot=true"
			}
			var param = "pageIndex="+pageIndex+"&pageSize="+pageSize+other;
			return "<%=request.getContextPath()%>/user/tree!getDataList2.action?" + param;
		}
		function beforeExpand(treeId, treeNode) {
			if (treeNode.pageIndex == 0){ 
				treeNode.pageIndex = 1;
				treeNode.curPage=1 
			}
			return !treeNode.isAjaxing;
		}
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			if (!msg || msg.length == 0) {
				return;
			}
		}
		
		function addDiyDom(treeId, treeNode) {
		
			if (treeNode.level>0)
			 return;
			if(treeNode.counts>pageSize){
				var aObj = $("#" + treeNode.tId + "_a");
				if ($("#addBtn_"+treeNode.id).length>0)
					 return;
					 
				var addStr = "<span class='button lastPage' id='lastBtn_" + treeNode.id
					+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtn_" + treeNode.id
					+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtn_" + treeNode.id
					+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtn_" + treeNode.id
					+ "' title='first page' onfocus='this.blur();'></span>";
				aObj.after(addStr);
				var first = $("#firstBtn_"+treeNode.id);
				var prev = $("#prevBtn_"+treeNode.id);
				var next = $("#nextBtn_"+treeNode.id);
				var last = $("#lastBtn_"+treeNode.id);
				
				var pageEnd =  Math.round(treeNode.counts/pageSize - .5) + (treeNode.counts%pageSize == 0 ? 0:1);
				var aObj = $("#" + treeNode.tId + "_a");
				aObj.children("span:last").text(treeNode.name+"("+(treeNode.pageIndex+"/"+pageEnd)+")");
				aObj.attr("title",getPageInfo(treeNode.pageIndex,pageEnd));
				
				first.bind("click", function(){
					if (!treeNode.isAjaxing) {
						goPage(treeNode, 1);
					}
				});
				last.bind("click", function(){
					if (!treeNode.isAjaxing) {
						var pageEnd =  Math.round(treeNode.counts/pageSize - .5) + (treeNode.counts%pageSize == 0 ? 0:1);
						goPage(treeNode,pageEnd);
					}
				});
				prev.bind("click", function(){
					if (!treeNode.isAjaxing) {
						goPage(treeNode, treeNode.pageIndex-1);
					}
				});
				next.bind("click", function(){
					if (!treeNode.isAjaxing) {
						goPage(treeNode, treeNode.pageIndex+1);
					}
				});
			
			}
			
		}
		
		function goPage(treeNode, pageIndex) {
			treeNode.pageIndex = pageIndex;
			if (treeNode.pageIndex<1) 
				treeNode.pageIndex = 1;
			var pageEnd =  Math.round(treeNode.counts/pageSize - .5) + (treeNode.counts%pageSize == 0 ? 0:1);	
			if (treeNode.pageIndex>pageEnd) 
				treeNode.pageIndex = pageEnd;
			if (treeNode.curPage == treeNode.pageIndex) 
				return;
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			//更新当前页
			treeNode.curPage = treeNode.pageIndex;
			//zTree.updateNode(treeNode);
			
			zTree.reAsyncChildNodes(treeNode, "refresh");
		}
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
  
   </script>
  </body>
	
 * 
 * <li>
 * <li>后台:<br>
 * int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
   int pageSize = Integer.parseInt(request.getParameter("pageSize"));
   Page page = new Page(pageIndex,pageSize);
   //用来计算本次请求需要拿到的分页数据
   List<PageZtreeNode> treeDataList = serviceImpl.getPageZtreeNode(page,Map<String,Object> queryMap);
   之后ajax把数据转换成json后输出到前台
   
 * 
 * <li>
 * @author Cherry
   @date Oct 25, 2013
 *
 */
public class PageZtreeNode extends ZtreeNode{
	/**
	 * 此树节点<font color='red'>下一层子节点</font>的数目
	 */
	private long counts;
	/**
	 * 开始页(页面分页计算用,同一层可能有很多节点需要分页，故放在需要分页的树节点属性中，作为局部变量),不需要赋值
	 */
	private int pageIndex;
	/**
	 * 当前页(页面分页计算用,同一层可能有很多节点需要分页，故放在需要分页的树节点属性中，作为局部变量),不需要赋值
	 */
	private int curPage;

	public long getCounts() {
		return counts;
	}
	/**
	 * 此树节点<font color='red'>下一层子节点</font>的数目
	 * @param counts
	 */
	public void setCounts(long counts) {
		this.counts = counts;
		if(counts >0)
			setIsParent(true);
			
	}

	public int getPageIndex() {
		return pageIndex;
	}


	public int getCurPage() {
		return curPage;
	}


	
}
