<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>直播报表</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

<@plugins.msg />
<script type="text/javascript">
<#-- 全选 -->
function checkAll(){
	var b = $("input[name='allIds']").attr('checked');
	$("input[name='ids']").attr('checked', b == "checked");
}

<#-- 删除 -->
function doDelete(id){
	if(confirm("确定需要删除？")){
		location.href='activity-gift-delete.html?pageIndex=${pageIndex! }&name=${(name)! }&activityId=${activityId! }&ids=' + id;
	}
}

function deleteAll(){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要删除的数据");
	}else{
		if(confirm("确定需要删除？")){
			var form1 = document.getElementById("form1");
			form1.action = "activity-gift-delete.html?pageIndex=${pageIndex! }&name=${(name)! }&activityId=${activityId! }";
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
		    <li><a href="#">活动</a></li>
		    <li><a href="#">礼品管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;height:500px" id="contentDiv">
			<form action="activity.html" method="post" name="form1" id="form1">
		    	<ul class="seachform">
		    		<li onclick="location.href='activity-gift-detail.html?pageIndex=${pageIndex! }&name=${name! }&activityId=${activityId! }'" class=".toolbar li"><span><img src="${staticPath! }/static/images/t01.png${staticVersion! }" /></span>新增</li>
	        		<li onclick="deleteAll()" class=".toolbar li"><span><img src="${staticPath! }/static/images/t03.png${staticVersion! }" /></span>删除</li>
		        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="返回活动" onclick="location.href='activity.html?name=${name! }'"/></li>
		        </ul>
			    <table class="tablelist">
			    	<thead>
				    	<tr>
				    		<th><input name="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
					        <th>活动名称</th>
					        <th>礼品名称</th>
					        <th>价值</th>
					        <th>类型</th>
					        <th>奖品数量</th>
					        <th>总数量</th>
					        <th>库存</th>
					        <th>状态</th>
					        <th>排序号</th>
					        <th>创建时间</th>
					        <th>操作</th>
				        </tr>
			        </thead>
			        <tbody>
				        <#list list?if_exists as item>
					        <tr title="${(item.name)! }">
					        	<td><input name="ids" type="checkbox" value="${(item.id)! }" /></td>
						        <td>${(item.itemName)! }</td>
						        <td>${(item.name)! }</td>
						        <td>${(item.money)! }</td>
						        <td>${(item.typeName)! }</td>
						        <td>${(item.bi)! }</td>
						        <td>${(item.number)! }</td>
						        <td>${(item.stock)! }</td>
						        <td>
						        	<#if item.status?default("1") == "1">
						        		<font style="color:green;">上线</font>
						        	<#elseif item.status?default("1") == "0">
						        		<font style="color:red;">下线</font>
						        	</#if>
						        </td>
						        <td>${(item.orderId)! }</td>
						        <td>${(item.createTime?datetime)! }</td>
						        <td>
						        	<a href="activity-gift-detail.html?pageIndex=${pageIndex! }&name=${name! }&activityId=${activityId! }&id=${(item.id)! }" class="tablelink">修改</a> |
									<a href="javascript:doDelete('${(item.id)! }')" class="tablelink">删除</a> |
									<#if item.status?default("1") == "1">
						        		<a href="activity-gift-offline.html?pageIndex=${pageIndex! }&name=${name! }&activityId=${activityId! }&id=${(item.id)!}" class="tablelink">下线</a>
						        	<#elseif item.status?default("1") == "0">
						        		<a href="activity-gift-online.html?pageIndex=${pageIndex! }&name=${name! }&activityId=${activityId! }&id=${(item.id)!}" class="tablelink">上线</a>
						        	</#if>
								</td>
					        </tr>
				        </#list>
			        </tbody>
			    </table>
		    </form>
		    <div class="pagin">
		        <@plugins.page action="activity-gift-list.html?name=${name! }&pageIndex=" />
		    </div>
		</div>
    </div>
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>