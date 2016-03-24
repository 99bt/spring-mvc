<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
</head>
<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    </ul>
    </div>
    
    <div class="mainindex">
    <div class="welinfo">
	    <span><img src="${staticPath! }/static/images/sun.png" alt="天气" /></span>
	    <b>您好 ${adminLogin.username! }，欢迎使用${platFormName!}</b>
    </div>
    <div class="welinfo">
	    <iframe width="280" scrolling="no" height="25" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=34&icon=1&num=3" class="weather-wrap"></iframe>
    </div>
    <div class="welinfo">
	    <span><img src="${staticPath! }/static/images/time.png" alt="时间" /></span>
	    <i>您上次登录的时间：${adminLogin.loginTime?datetime}</i> （不是您登录的？<a href="${contextPath! }/admin/changePwd.html">请点这里</a>）
    </div>
    </div>
</body>
</html>
