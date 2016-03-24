<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>
<script>
$(document).ready(function() {
	$(".select3").uedSelect({
		width : 150
	});
});

<#-- 删除 -->
function doDelete(id){
	if(confirm("确定需要删除？")){
		location.href='system-mcode-delete.html?id=${id! }&ids=' + id;
	}
}
<#-- 全选 -->
function checkAll(){
	var b = $("input[id='allIds']").attr('checked');
	$("input[name='ids']").attr('checked', b == "checked");
}
</script>
<@plugins.msg />
</head>

<body>
	<div class="place">
	    <span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">管理信息</a></li>
		    <li><a href="#">系统管理</a></li>
    	</ul>
    </div>
    
    <div class="formbody">
	    <div id="usual1" class="usual"> 
	    <div class="itab">
	  	<ul> 
		    <li><a href="system.html">参数管理</a></li> 
		    <li><a href="system-call.html">接口调用</a></li> 
		    <li><a href="#tab1" class="selected">微代码</a></li>
		    <li><a href="system-database.html">数据库连接池</a></li>  
	  	</ul>
    </div>
    
    <div class="rightinfo" style="overflow:auto;height:500px;" id="contentDiv">
	    <div class="tools">
	    	<ul class="toolbar">
	        	<#if id?default("") != ""><li onclick="location.href='system-mcode-detail.html?id=${id! }'"><span><img src="${staticPath! }/static/images/t01.png" /></span>新增</li></#if>
	        		<div style="float:left"><select class="select3" name="mcode" onchange="location.href='system-mcode.html?id=' + this.value">
		    		<#list list?if_exists as item>
		    			<option value="${item.thisId! }" <#if item.thisId == (id)?default('')>selected</#if>>${item.name! }</option>
		    		</#list>
	    		</select></div>
	        </ul>
	    </div>
    	
	    <table class="tablelist">
	    	<thead>
		    	<tr>
			        <th><input id="allIds" type="checkbox" value="" onclick="checkAll()"/></th>
			        <th>KEY</th>
			        <th>VALUE</th>
			        <th>启用</th>
			        <th>排序号<i class="sort"><img src="${staticPath! }/static/images/px.gif${staticVersion! }" /></i></th>
			        <th>操作</th>
		        </tr>
	        </thead>
	        <tbody>
	        
	        <#list details?if_exists as item>
	        <tr title="${item.thisId! }">
		        <td><input name="ids" type="checkbox" value="${item.id! }" /></td>
		        <td>${item.thisId! }</td>
		        <td><@plugins.cutOff cutStr="${item.content! }" cutLen="40"/></td>
		        <td>${item.isUsing?string("启用", "<span style='color:red'>禁用</span>")}</td>
		        <td>${item.displayOrder! }</td>
		        <td>
		        	<a href="system-mcode-detail.html?entity.id=${item.id! }&id=${id! }" class="tablelink">修改</a>
		        </td>
	        </tr>
	        </#list>
	        </tbody>
	    </table>
	</div>
	
    <script type="text/javascript">
    jQuery("#contentDiv").height(jQuery("#mainFrame", window.parent.parent.document).height() - 120);
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
    </div>
</body>
</html>
    