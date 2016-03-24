<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>
<script type="text/javascript">
$(document).ready(function() {
	$(".select3").uedSelect({
		width : 150
	});
});

function doSave(){
	var username = document.getElementById("username");
	if(username.value == ""){
		addFieldError("用户名不能为空");
		return;
	}
	
	var from1 = document.getElementById("form1");
	form1.submit();
}
</script>
<@plugins.msg />
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">系统管理</a></li>
		    <li><a href="#">管理员管理</a></li>
	    </ul>
    </div>
    <div class="rightinfo"  style="overflow:auto;" id="contentDiv">
    <form id="form1" action="adminUser-save.html" method="post">
	    <input type="hidden" name="id" value="${id! }" />
	    <input type="hidden" name="entity.id" value="${id! }" />
	    
	    <div class="formbody">
	    <div class="formtitle"><span class="sp">基本信息</span></div>
		    <ul class="forminfo">
			    <li><label>用户名：</label><input name="entity.username" id="username" value="${entity.username! }" type="text" class="dfinput"<#if entity.id?exists> readonly /><i>自动生成，不可维护</i><#else>/></#if></li>
			    <li><label>状态：</label>
			    	<label><input type="radio" name="entity.state" value="1" checked/> 正常</label>
			    	<label><input type="radio" name="entity.state" value="2" <#if entity.state?default("1") == "2">checked</#if>/> 停用</label>
			    </li>
			    <li><label>真实姓名：</label><input name="entity.realname" type="text" class="dfinput" value="${entity.realname! }" /><i></i></li>
			    <li><label>邮箱：</label><input name="entity.email" type="text" class="dfinput" value="${entity.email! }" /><i></i></li>
			    <li><label>手机号码：</label><input name="entity.telphone" type="text" class="dfinput" value="${entity.telphone! }" /><i></i></li>
			    <li><label>创建时间：</label><input name="entity.createTime" value="${(entity.createTime?datetime)! }" type="text" class="dfinput" readonly/><i>自动生成，不可维护</i></li>
			    <li><label>最后登录：</label><input name="entity.loginTime" type="text" class="dfinput" value="${entity.loginTime?default("(未登录)") }" disabled/><i>自动生成，不可维护</i></li>
			    <li><label>登录IP：</label><input name="entity.loginIp" type="text" class="dfinput" value="${entity.loginIp! }" readonly/><i>自动生成，不可维护</i></li>
			    <li><label>角　色：</label>
				    <div class="vocation">
				    	<select class="select3" name="roleId">
				    	<option value="">===请选择===</option>
			    		<#list list?if_exists as item>
			    			<option value="${item.id! }" <#if item.id == (roleId)?default('')>selected</#if>>${item.name! }</option>
			    		</#list>
			    		</select>
				    </div>
			    </li>
			    
			    <li>
			    	<label>&nbsp;</label>
			    <#if !entity.isSys?default(false)>
		    		<input name="" type="button" class="btn" value="确认保存" onclick="doSave()"/>
		    	</#if>
			    	<input name="" type="button" class="btn" value="返回" onclick="location.href='adminUser.html'"/>
			    </li>
		    </ul>
	    </div>
    	</div>
	 </form>
	 </div>
	 <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 60);
    </script>
</body>
</html>
