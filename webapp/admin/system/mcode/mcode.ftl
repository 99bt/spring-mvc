<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function doSave(){
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
		    <li><a href="#">管理信息</a></li>
		    <li><a href="#">系统管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div id="usual1" class="usual"> 
	    <div class="itab">
	  	<ul> 
		    <li><a href="#tab1" class="selected">微代码</a></li> 
	  	</ul>
    </div> 
    
    <form id="form1" action="system-mcode-save.html" method="post">
    	<input type="hidden" name="entity.id" value="${entity.id! }" />
    	<input type="hidden" name="id" value="${id! }" />
		
		<div class="formbody">
	    <div class="formtitle"><span class="sp">基本信息</span></div>
		    <ul class="forminfo">
			    <li><label>KEY：</label><input name="entity.thisId" value="${entity.thisId! }" type="text" class="dfinput-345" /></li>
			    <li><label>是否启用：</label>
			    	<label><input type="radio" name="entity.isUsing" value="true" checked/> 启用</label>
			    	<label><input type="radio" name="entity.isUsing" value="false" <#if !entity.isUsing?default(false)>checked</#if>/> 禁用</label>
			   	</li>
			    <li><label>排序号：</label><input name="entity.displayOrder" value="${entity.displayOrder! }" type="text" class="dfinput-345" /></li>
			    <li><label>内容：</label><textarea name="entity.content" class="textinput" style="width:600px; height:80px">${entity.content! }</textarea></li>
			    
			    <li>
			    	<label>&nbsp;</label>
			    	<input name="" type="button" class="btn" value="确认保存" onclick="doSave()"/> 
			    	<input name="" type="button" class="btn" value="返回" onclick="location.href='system-mcode.html?id=${id! }'"/>
			    </li>
		    </ul>
	    </div>
    	</div>    	
	</form>
</body>
</html>