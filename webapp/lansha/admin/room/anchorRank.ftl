<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主播积分排行</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<@plugins.msg />
<script type="text/javascript">

</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">主播管理</a></li>
		    <li><a href="#">主播积分排行</a></li>
	    </ul>
    </div>
    <div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="anchorRank.html" method="post" name="form1" id="form1">
		<ul class="seachform">
        	<li>
        		<label>积分排名周期(天)：</label>
	        	<label><font color="red">${(startTime?date)! }~${(endTime?date)! }</font></label>
	        	
        	积分排名周期时间：<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
		        	<input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
        	</li>
        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询" onclick="form1.submit();"/></li>
        </ul>
	    <table class="tablelist">
	    	<thead>
		    	<tr>
			        <th>排名</th>
			        <th>用户ID</th>
			        <th>房间ID</th>
			        <th>直播时长</th>
			        <th>每日虾米数量</th>
			        <th>每日关注</th>
                    <th>每日取消关注</th>
			        <th>每日鲜花数</th>
                    <th>每日日票</th>
			        <th>每日真实人气</th>
			        <th>积分</th>
			        <th>操作</th>
		        </tr>
	        </thead>
	        <tbody>
	        <#list rankRooms?if_exists as item>
	        <tr title="${item.roomName! }">		 
	        	<td>${(item_index + 1)}</td>
		        <td>${item.userName! }</td>		
		        <td>${item.roomIdint! }</td>    
		        <td>${item.playTime! }</td>        
		        <td>${item.xiamiNumber! }</td>
		        <td>${item.relationNumber! }</td>
                <td>${item.cancelNumber! }</td>
		        <td>${item.flowerNubmer! }</td>
                <td>${item.ticketNumber! }</td>
		        <td>${item.audience! }</td>
		        <td>${item.score!}</td>
		        <td>
		        	<a href="anchorRank-detail.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&entity.userId=${item.userId! }&pageIndex=${pageIndex! }" class="tablelink">详情</a> 
				</td>
	        </tr>
	        </#list>
	        </tbody>
	    </table>
	    </form>
	      <div class="pagin">
	        <@plugins.page action="recordManage.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=" />
	    </div>
	</div>
	<script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>