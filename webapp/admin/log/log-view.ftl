<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">管理信息</a></li>
		    <li><a href="#">日志管理</a></li>
	    </ul>
    </div>
    
    <div class="formbody">
    <div class="formtitle"><span class="sp">基本信息</span></div>
	    <ul class="forminfo">
		    <li><label>类型：</label><input value="${(log.type)! }" type="text" class="dfinput-345" disabled /></li>
		    <li><label>时间：</label><input value="${(log.createTime)! }" type="text" class="dfinput-345" disabled /></li>
		    <li><label>内容：</label>
		    	<textarea class="textinput" style="width:600px; height:120px" readonly>${(log.value)! }</textarea>
		    </li>
		    <li>
		    	<label>&nbsp;</label>
		    	<input name="" type="button" class="btn" value="返回" onclick="location.href='log.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&entity.type=${(entity.type)! }&entity.value=${(entity.value)! }&pageIndex=${pageIndex! }'"/>
		    </li>
	    </ul>
    </div>
	</div>
</body>
</html>
