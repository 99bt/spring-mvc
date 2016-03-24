<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主播积分详情</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

<@plugins.msg />
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">主播管理</a></li>
		    <li><a href="#">主播积分详情</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;height:500px" id="contentDiv">
			<form action="anchorRank.html" method="post" name="form1" id="form1">
			<input type="hidden" name="startTime" id="startTime" value="${(startTime?date)! }" />
		        	<input type="hidden" name="endTime" id="endTime" value="${(endTime?date)! }" />
		    	<ul class="seachform">
		        	<li><input name="" type="button" class="scbtn" style="width:200px" value="返回主播积分排行" onclick="form1.submit();"/></li>
		        </ul>
			    <table class="tablelist">
			    	<thead>
				    	<tr>
					        <th>用户名</th>
			        		<th>房间ID</th>
			        		<th>直播时长</th>
			        		<th>每日虾米数量</th>
			        		<th>每日关注</th>
                            <th>每日取消关注</th>
			        		<th>每日鲜花数</th>
                            <th>每日日票数</th>
			        		<th>每日薪资阶级</th>
                            <th>当日收益</th>
			        		<th>创建时间</th>
				        </tr>
			        </thead>
			        <tbody>
				        <#list rankDetailRooms?if_exists as item>
					        <tr title="${item.name! }">
						        <td>${item.userName! }</td>		
		        				<td>${item.roomIdint! }</td>    
		        				<td>${item.playTime! }</td>        
		        				<td>${item.xiamiNumber! }</td>
		        				<td>${item.relationNumber! }</td>
                                <td>${item.cancelNumber! }</td>
		        				<td>${item.flowerNubmer! }</td>
                                <td>${item.ticketNumber! }</td>
		        				<td>${item.priceInfo! }</td>
                                <td>${item.salary! }</td>
		        				<td>${(item.createTime?string("yyyy-MM-dd"))!}</td>
					        </tr>
				        </#list>
			        </tbody>
			    </table>
		    </form>
	    	<div class="pagin">
	        	<@plugins.page action="anchorRank-detail.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&entity.userId=${entity.userId! }&pageIndex=" />
	    	</div>
		</div>
    </div>
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>