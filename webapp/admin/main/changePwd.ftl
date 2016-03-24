<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript">
function doSave(){
	var pwd = $("#pwd");
	var newPwd = $("#newPwd");
	var rnewPwd = $("#rnewPwd");
	if(pwd.val() == ""){
		addFieldError("原密码不能空");
	}else if(newPwd.val() == ""){
		addFieldError("新密码不能空");
	}else if(newPwd.val() != rnewPwd.val()){
		addFieldError("原密码和新密码不一致");
	}else{
		var from1 = document.getElementById("form1");
		form1.submit();
	}
	
}
</script>
<@plugins.msg />
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">管理信息</a></li>
		    <li><a href="#">用户管理</a></li>
	    </ul>
    </div>
    
    <form id="form1" action="changePwd.html" method="post">
	    <div class="formbody">
	    <div class="formtitle"><span class="sp">修改密码</span></div>
		    <ul class="forminfo">
			    <li><label>原密码：</label><input name="pwd" id="pwd" value="${pwd! }" type="password" class="dfinput-345" autocomplete="off"/></li>
			    <li><label>新密码：</label><input name="newPwd" id="newPwd" value="${newPwd! }" type="password" class="dfinput-345" autocomplete="off"/></li>
			    <li><label>重复密码：</label><input id="rnewPwd" value="${newPwd! }" type="password" class="dfinput-345" autocomplete="off"/></li>
			    
			    <li>
			    	<label>&nbsp;</label>
			    	<input name="" type="button" class="btn" value="修改" onclick="doSave()"/> 
			    </li>
		    </ul>
	    </div>
    	</div>
	 </form>
</body>
</html>
