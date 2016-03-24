<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
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
		    <li><a href="#">管理信息</a></li>
		    <li><a href="#">系统管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div id="usual1" class="usual"> 
	    <div class="itab">
	  	<ul> 
		    <li><a href="system.html">参数管理</a></li> 
		    <li><a href="#tab1" class="selected">接口调用</a></li> 
		    <li><a href="system-mcode.html">微代码</a></li> 
		    <li><a href="system-database.html">数据库连接池</a></li> 
	  	</ul>
    </div> 
    
    <div class="formbody">
    	<div class="formtitle"><span class="sp">库存存量</span></div>
	    <ul class="forminfo">
		    <li>
		    	短信存量(条)：<input type="text" value="${(mtStock)! }" class="scinput-150" style="width:300px" readonly/>
		    </li>
	    </ul>
		    
	    <div class="formtitle"><span class="sp">缓存刷新</span></div>
	    <ul class="forminfo">
		    <li>
		    	<input name="" type="button" class="btn" value="缓存刷新" onclick="if(confirm('确定需要刷新？')) location.href='system-call.html?id=all-cache'"/> 
		    	<input name="" type="button" class="btn" value="刷新短信配置" onclick="if(confirm('确定需要刷新？')) location.href='system-call.html?id=MT'"/> 
		    	<input name="" type="button" class="btn" value="刷新邮件配置" onclick="if(confirm('确定需要刷新？')) location.href='system-call.html?id=EMAIL'"/>
                <input name="" type="button" class="btn" value="刷新日票排行" onclick="if(confirm('确定需要刷新？')) location.href='system-call.html?id=TICKET'"/>
            </li>
	    </ul>
	    <div class="formtitle"><span class="sp">主播积分统计</span></div>
	    <ul class="forminfo">
		    <li>
		    	统计时间：<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
		    	<input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
		    	<input name="" type="button" class="btn" value="点击统计" onclick="if(confirm('确定需要调用？')) location.href='system-call.html?id=ROOM_RANKING&startTime=' + $('#startTime').val()+'&endTime=' + $('#endTime').val()"/> 
		    	<font style="color:red">每天凌晨3点触发</font>
		    </li>
	    </ul>
    </div>
	</div>
</body>
</html>
