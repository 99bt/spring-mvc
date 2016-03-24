<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>礼物管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>

<@plugins.msg />
<script type="text/javascript">
<#-- 全选 -->
function checkAll(){
	var b = $("input[id='allIds']").attr('checked');
	$("input[name='ids']").attr('checked', b == "checked");
}

<#-- 删除 -->
function doDelete(id){
	if(confirm("确定需要删除？")){
		location.href='gift-delete.html?pageIndex=${pageIndex! }&name=${(name)! }&ids=' + id;
	}
}

function deleteAll(){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要删除的数据");
	}else{
		if(confirm("确定需要删除？")){
			var form1 = document.getElementById("form1");
			form1.action = "gift-delete.html?pageIndex=${pageIndex! }";
			form1.submit();
		}
	}
}
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">游戏管理</a></li>
		    <li><a href="#">礼物详情</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="gift.html" method="post" name="form1" id="form1">
	    	<ul class="seachform">
	    		<li onclick="location.href='gift-detail.html?pageIndex=${pageIndex! }&name=${(name)! }'" class=".toolbar li"><span><img src="${staticPath! }/static/images/t01.png${staticVersion! }" /></span>新增</li>
	        	<li onclick="deleteAll()" class=".toolbar li"><span><img src="${staticPath! }/static/images/t03.png${staticVersion! }" /></span>删除</li>
	        	<li>
	        		<label>名称：</label><input name="name" type="text" value="${(name)! }" class="scinput-150" />
	        	</li>
	        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询" onclick="form1.submit();"/></li>
	        </ul>
		    <table class="tablelist">
		    	<thead>
			    	<tr>
				        <th><input id="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
				        <th>名称</th>
				        <th>状态</th>
				        <th>币值</th>
				        <th>图标URL</th>
				        <th>排序号</th>
				        <th>创建日期</th>
				        <th>操作</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list list?if_exists as item>
		        <tr title="${item.name! }">
			        <td><input name="ids" type="checkbox" value="${item.id! }" /></td>
			        <td>${item.name! }</td>
			        <td><#if item.status == '0'><font color="red">停用</font><#elseif item.status == '1'><font color="green">启用</font></#if></td>
			        <td>${item.bi! }</td>
			        <td>${item.img! }</td>
			        <td>${item.orderid! }</td>
			        <td>${(item.createTime?datetime)! }</td>
			        <td>
			        	<a href="gift-detail.html?pageIndex=${pageIndex! }&id=${item.id! }&name=${(name)! }" class="tablelink">修改</a> |
						<a href="javascript:doDelete('${item.id! }')" class="tablelink">删除</a>
					</td>
		        </tr>
		        </#list>
		        </tbody>
		    </table>
	    </form>
	    <div class="pagin">
	        <@plugins.page action="gift.html?name=${name! }&pageIndex=" />
	    </div>
	</div>
    
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>