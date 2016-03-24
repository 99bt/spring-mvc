<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript">
$(function(){
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})	
});
</script>
</head>

<body style="background:url(${staticPath! }/static/images/topbg.gif) repeat-x;">
    <div class="topleft">
    	<a href="${contextPath! }/admin/index.html" target="_parent"><img src="${staticPath! }/static/images/logo.png" title="系统首页" /></a>
    </div>
        
    <ul class="nav">
    <#--
	    <li><a href="" target="_parent" class="selected"><img src="/admin/main/images/icon01.png" title="工作台" /><h2>资源中心</h2></a></li>
	    <li><a href="imgtable.html" target="rightFrame"><img src="/admin/main/images/icon02.png" title="模型管理" /><h2>模型管理</h2></a></li>
	    <li><a href="imglist.html"  target="rightFrame"><img src="/admin/main/images/icon03.png" title="模块设计" /><h2>模块设计</h2></a></li>
	    <li><a href="tools.html"  target="rightFrame"><img src="/admin/main/images/icon04.png" title="常用工具" /><h2>常用工具</h2></a></li>
	    <li><a href="computer.html" target="rightFrame"><img src="/admin/main/images/icon05.png" title="文件管理" /><h2>文件管理</h2></a></li>
	    <li><a href="tab.html"  target="rightFrame"><img src="/admin/main/images/icon06.png" title="系统设置" /><h2>系统设置</h2></a></li>
	-->
    </ul>

    <div class="topright">    
	    <ul>
	    	<li><a id="message" style="color:#FFFF00;" href="#" ></a></i>
		    <li><span><img src="${staticPath! }/static/images/help.png" title="密码修改" class="helpimg"/></span><a href="changePwd.html" target="mainFrame">密码修改</a></li>
		    <li><a href="#">关于</a></li>
		    <li><a href="${contextPath! }/admin/logout.html" onclick="return confirm('确定需要退出？')" target="_parent" title="点击退出系统">退出</a></li>
	    </ul>
	    <div class="user">
		    <span>${adminLogin.username! }</span>
		    <#--<i>消息</i>
		    <b>5</b>-->
	    </div>    
    </div>
</body>
</html>
