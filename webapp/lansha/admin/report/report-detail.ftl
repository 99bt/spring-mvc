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
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">统计报表</a></li>
		    <li><a href="#">直播报表详情</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;height:500px" id="contentDiv">
			<form action="live.html?pageIndex=${pageIndex! }&roomId=${roomId!}&id=${id! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&startHour=${startHour! }&endHour=${endHour! }&examine=${examine!}" method="post" name="form1" id="form1">
		    	<ul class="seachform">
		        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="返回报表" onclick="form1.submit();"/></li>
		    		<!-- <input name="" type="button" class="scbtn" value="导出" onclick="exportData()"/> -->
		    		<li><label style="color:red">${(startTime?date)! }至${(endTime?date)! } 有效时间：${startHour! }点-${endHour! }点</label></li>
		        </ul>
			    <table class="tablelist">
			    	<thead>
				    	<tr>
					        <th>房间名称</th>
					        <th>房间号</th>
					        <th>主播名称</th>
					        <th>游戏名称</th>
					        <th>有效时长</th>
					        <th>开始时间</th>
					        <th>结束时间</th>
				        </tr>
			        </thead>
			        <tbody>
				        <#list list?if_exists as item>
					        <tr title="${item.name! }">
						        <td>${item.name! }</td>
						        <td>${item.idInt! }</td>
						        <td>${item.nickname! }</td>
						        <td>${item.gameName! }</td>
						        <td>${item.playTime! }</td>
						        <td>${(item.startTime?datetime)! }</td>
						        <td>${(item.endTime?datetime)! }</td>
					        </tr>
				        </#list>
			        </tbody>
			    </table>
		    </form>
		</div>
    </div>
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
		
		function exportData(){
			location.href='live-detailExport.html?id=${id! }&roomId=${roomId! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&startHour=${startHour! }&endHour=${endHour! }';
		}
	</script>
</body>
</html>