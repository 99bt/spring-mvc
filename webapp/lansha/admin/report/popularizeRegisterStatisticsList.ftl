<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统计报表</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>

<@plugins.msg />

</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">统计报表 </a></li>
		    <li><a href="#">推广注册统计</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="user.html" method="post" name="form1" id="form1">
			<ul class="seachform">
	        	<li>
	        		邀请者ID：</label><input name="idInt" type="text" value="${(idInt)! }" class="scinput-150" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" autocomplete="off" />
	        		邀请者房间ID：</label><input name="roomId" type="text" value="${(roomId)! }" class="scinput-150" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" autocomplete="off" />
	        		被邀请者ID：</label><input name="userIdInt" type="text" value="${(userIdInt)! }" class="scinput-150" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" autocomplete="off" />
	        	</li>
	        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询" onclick="form1.submit();"/></li>
	        </ul>
		    <table class="tablelist">
		    	<thead>
			    	<tr>
			    		<th>邀请者ID</th>
				        <th>邀请者房间ID</th>
				        <th>被邀请者ID</th>
				        <th>被邀请者房间ID</th>
				        <th>完成注册日期</th>
				        <th>成功开播日期</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list list?if_exists as item>
		        <tr title="">
		        	<td>${item.parentIdInt! }</td>
			        <td>${item.referrerRoomId! }</td>
			        <td>${item.idInt! }</td>
			        <td>${item.oneselfRoomId! }</td>
			        <td>${item.createTime}</td>
			        <td>${item.datas! }</td>
		        </tr>
		        </#list>
		        </tbody>
		    </table>
	    </form>
	    <div class="pagin">
	        <@plugins.page action="user.html?idInt=${(idInt)! }&roomId=${(roomId)! }&userIdInt=${(userIdInt)! }&pageIndex=" />
	    </div>
	</div>
    
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>