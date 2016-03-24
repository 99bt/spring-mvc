<#-- 系统管理 -->
<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
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
		    <li><a href="#">管理信息</a></li>
		    <li><a href="#">系统管理</a></li>
    	</ul>
    </div>
    <div class="formbody">
	    <div id="usual1" class="usual"> 
	    <div class="itab">
	  	<ul> 
		    <li><a href="system.html">参数管理</a></li> 
		    <li><a href="system-call.html">接口调用</a></li> 
		    <li><a href="system-mcode.html">微代码</a></li> 
		    <li><a href="#tab1" class="selected">数据库连接池</a></li> 
	  	</ul>
    </div> 

    <div class="rightinfo"  style="overflow:auto;height:500px;"  id="contentDiv">
		<#list list?if_exists as item>
			${item! }<br />
		</#list>
	</div>
    
    <script type="text/javascript">
    jQuery("#contentDiv").height(jQuery("#mainFrame", window.parent.parent.document).height() - 110);
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>

</html>