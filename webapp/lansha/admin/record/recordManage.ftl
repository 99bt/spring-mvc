<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>广告管理</title>
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
		    <li><a href="#">会员管理 </a></li>
		    <li><a href="#">观看记录</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="recordManage.html" method="post" name="form1" id="form1">
			<ul class="seachform">
	        	<li>
	        		用户名：</label><input name="entity.houseOwner" type="text" value="${(entity.houseOwner)! }" class="scinput-150" />
	        		房间ID：</label><input name="entity.roomIdInts" type="text" value="${(entity.roomIdInts)! }" class="scinput-150" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  autocomplete="off" />
	        		
	        		时间：<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
			        	<input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
	        	</li>
	        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询" onclick="form1.submit();"/></li>
	        </ul>
		    <table class="tablelist">
		    	<thead>
			    	<tr>
			    		<th>序号</th>
				        <th>用户名</th>
				        <th>房间ID</th>
				        <th>状态</th>
				        <th>礼物</th>
				        <th>时间</th>
				        <th>观看直播时长</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list list?if_exists as item>
		        <tr title="">
		        	<td>${(item_index+1)+(pageIndex?number-1)*15 }</td>
			        <td>${item.houseOwner! }</td>
			        <td>${item.roomIdInt! }</td>
			        <td><#if item.status == 0><font>删除</font>
			        	<#elseif item.status == 1><font color="red">未领取</font>
			        	<#elseif item.status == 2><font color="green">已领取</font></#if></td>
			         <td>${item.bi! }${item.giftName! }</td>
			        <td>${item.createTime!}</td>
			         <td>${item.timeLengths!}</td>
		        </tr>
		        </#list>
		        </tbody>
		    </table>
	    </form>
	    <div class="pagin">
	        <@plugins.page action="recordManage.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&entity.houseOwner=${(entity.houseOwner)! }&entity.roomIdInts=${(entity.roomIdInts)! }&pageIndex=" />
	    </div>
	</div>
    
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>