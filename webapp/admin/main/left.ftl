<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>


</head>

<body style="background:#f0f9fd;">
<div class="lefttop"><span></span>目录导航</div>
<dl class="leftmenu">
	<#list models?keys as item>
	<dd>
	    <div class="title">
	    	<span><img src="${staticPath! }${models[item][0].img! }" /></span>${models[item][0].name! }
	    </div>
    	<ul class="menuson">
    	<#list models[item][1]?if_exists as entity>
	        <li><cite></cite><a href="${contextPath! }${entity.url! }" target="mainFrame">${entity.name! }</a><i></i></li>
	    </#list>
        </ul>
    </dd>
    </#list>
</dl>
</body>
</html>
