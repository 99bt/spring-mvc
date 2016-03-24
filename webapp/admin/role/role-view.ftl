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
	var name = document.getElementById("name");
	if(name.value == ""){
		addFieldError("角色名不能为空");
		return;
	}
	var displayOrder = document.getElementById("displayOrder");
	if(displayOrder.value == ""){
		addFieldError("排序号不能为空");
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
		    <li><a href="#">角色管理</a></li>
	    </ul>
    </div>
    
    <form id="form1" action="role-update.html" method="post">
	    <input type="hidden" name="id" value="${id! }" />
	    <input type="hidden" name="entity.id" value="${id! }" />
	    
	    <div class="formbody">
	    <div class="formtitle"><span class="sp">基本信息</span></div>
	    <div style="overflow:auto;height:500px" id="contentDiv">
		    <ul class="forminfo">
			    <li><label>类型：</label><#if entity.isSys?default(false)><label style="color:red">系统内置</label><#else>用户创建</#if></li>
			    <li><label>角色名：</label><input name="entity.name" id="name" value="${entity.name! }" type="text" class="dfinput-345" maxlength="30"/></li>
			    <li><label>状态：</label>
			    	<label><input type="radio" name="entity.state" value="1" checked/> 正常</label>
			    	<label><input type="radio" name="entity.state" value="2" <#if entity.state?default("1") == "2">checked</#if>/> 停用</label>
			    </li>
			    <li><label>排序号：</label><input name="entity.displayOrder" id="displayOrder" value="${entity.displayOrder?default(0) }" type="text" class="dfinput-345" maxlength="2"/></li>
			    <li><label>首页地址：</label><input name="entity.indexUrl" id="indexUrl" value="${entity.indexUrl?default('') }" type="text" class="dfinput-345" /></li>
			    <li><label>创建时间：</label><input name="entity.createTime" value="${(entity.createTime?datetime)! }" type="text" class="dfinput-345" readonly/><i>自动生成，不可维护</i></li>
		    </ul>
		    
		    <ul class="forminfo">
	    	<li><label>模块：</label><#-- <label><input type="checkbox" onclick="$(\"input[name='ids']\").attr('checked', $(this).attr('checked'));"/> 全选</label>--></li>
			<#list data?keys as item>
				<li><label style="width:120px"><input type="checkbox" name="ids" id="model1_${data[item][0].id! }" onclick="$('.model2_${data[item][0].id! }').attr('checked', $(this).attr('checked') == 'checked');"  value="${data[item][0].id! }" <#if list.contains(data[item][0].id)>checked</#if>/>&nbsp;<img src="${staticPath! }${data[item][0].img! }" />&nbsp;${data[item][0].name! }</label></li>
				<li>
					<#list data[item][1]?if_exists as entity>
						<label style="width:120px"><input type="checkbox" name="ids" class="model2_${data[item][0].id! }" onclick="if($(this).attr('checked') == 'checked') $('#model1_${data[item][0].id! }').attr('checked', 'checked');" value="${entity.id! }" <#if list.contains(entity.id)>checked</#if>/>&nbsp;
							<a href="${contextPath}${entity.url! }" target="_blank">${entity.name! }</a></label>
					</#list>
				<li>
			</#list>
			
			    <li>
			    	<label>&nbsp;</label>
		        	<#if !((entity.isSys)?default(false))>
			    		<input name="" type="button" class="btn" value="确认保存" onclick="doSave()"/>
			    	</#if>
			    	<input name="" type="button" class="btn" value="返回" onclick="location.href='role.html'"/>
			    </li>
		    </ul>
    	</div>
	 </form>
	</div>
</body>
<script>
jQuery("#contentDiv").height(jQuery("#mainFrame", window.parent.parent.document).height() - 120);
</script>
</html>
