<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户抽奖详情</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>

<@plugins.msg />
<script type="text/javascript">
<#-- 全选 -->
function checkAll(){
	var b = $("input[id='allIds']").attr('checked');
	$("input[name='ids']").attr('checked', b == "checked");
}


</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">活动管理 </a></li>
		    <li><a href="#">用户抽奖</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="activity-details.html" method="post" name="form1" id="form1">
			<input type="hidden" name="name" value="${(name)!}" />
			<input type="hidden" name="entity.itemName" value="${(entity.itemName)!}" />
			<input type="hidden" name="entity.itemId" value="${(entity.itemId)!}" />
			<input type="hidden" name="currentIndex" value="${currentIndex! }" />
			<ul class="seachform">
	        	<li>
	        		活动名称：<font color="red">${(entity.itemName)! }</font> &nbsp; &nbsp; &nbsp; 
	        		礼品：</label><input name="entity.giftName" type="text" value="${(entity.giftName)! }" class="scinput-150" />
	        		领取人：</label><input name="entity.userName" type="text" value="${(entity.userName)! }" class="scinput-150" />
	        		
	        		中奖时间：<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
			        	<input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
	        	</li>
	        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询" onclick="form1.submit();"/></li>
	        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="返回活动" onclick="location.href='user.html?name=${name! }&pageIndex=${currentIndex! }'"/></li>
	        </ul>
		    <table class="tablelist">
		    	<thead>
			    	<tr>
			    		<th>序号</th>
				        <th>礼品</th>
				        <th>领取人</th>
				        <th>QQ</th>
				        <th>状态</th>
				        <th>类型</th>
				        <th>操作人</th>
				        <th>中奖时间</th>
				        <th>备注</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list list?if_exists as item>
		        <tr title="">
		        	<td>${(item_index+1)+(pageIndex?number-1)*15 }</td>
			        <td>${item.giftName!}</td>
			        <td>${item.userName!}</td>
			        <td>${item.qq!}</td>
			        <td><#if item.status == '0'>等待发货
			        	<#elseif item.status == '1'><font color="green">已发货</font>
			        	<#elseif item.status == '2'><font color="red">审核不通过</font></#if></td>
			        <td>${item.typeName! }</td>
			        <td>${item.adminName!}</td>
			        <td>${item.createTime!}</td>
			        <td title="${item.remark! }"><@plugins.cutOff cutStr="${item.remark! }" cutLen="10" /></td>
		        </tr>
		        </#list>
		        </tbody>
		    </table>
	    </form>
	    <div class="pagin">
	        <@plugins.page action="activity-details.html?currentIndex=${currentIndex! }&startTime=${(startTime?date)! }&entity.itemId=${(entity.itemId)! }&name=${name! }&endTime=${(endTime?date)! }&entity.giftName=${(entity.giftName)! }&entity.userName=${(entity.userName)! }&entity.itemName=${(entity.itemName)! }&pageIndex=" />
	    </div>
	</div>
    
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>