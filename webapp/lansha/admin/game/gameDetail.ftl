<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏类别管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/validate.js${staticVersion! }"></script>
<@plugins.msg />
<@plugins.ImgStyle name="img1" width="150" height="150" />
<script>
<#-- 保存 -->
function save(){
	var name = $("#name").val();
	if(name == ""){
		addFieldError("名称不能为空");
		return false;
	}
	
	var status = $("#status").val();
	if(status == ""){
		addFieldError("请选择状态");
		return false;
	}
	
	var orderId = $("#orderId").val();
	if(orderId == ""){
		addFieldError("排序号不能为空");
		return false;
	}
	
	$("#form1").submit();	
}
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">游戏管理</a></li>
		    <li><a href="#">游戏类别管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
    <form action="gameCategory-save.html" id="form1" method="post" enctype="multipart/form-data">
    	<input type="hidden" id="id" name="ywGameType.id" value="${(ywGameType.id)!}" />
	    <div class="formtitle"><span class="sp">基本信息</span></div>
		    <ul class="forminfo">
			    <li><label><font color="red">*</font>名称：</label><input value="${(ywGameType.name)! }" id="name" name="ywGameType.name" type="text" class="dfinput-345" /></li>
			    <li><label><font color="red">*</font>状态：</label>
			    	<div class="vocation">
				    	<select class="select3" name="ywGameType.status" id="status">
				    		<option value="">--请选择--</option>
				            <option value='1' <#if (ywGameType.status)?default('')== '1'>selected</#if> >开启</option>
				            <option value='2' <#if (ywGameType.status)?default('')== '2'>selected</#if> >禁用</option>
			    		</select>
				    </div>
			    </li>
			    <li><label><font color="red">*</font>排序号：</label><input value="${(ywGameType.orderId)! }" id="orderId" name="ywGameType.orderId" type="text" class="dfinput-345" /></li>
			    <li>
			    	<label>&nbsp;</label>
			    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
			    	<input name="" type="button" class="btn" value="返回" onclick="location.href='gameCategory.html?entity.name=${(entity.name)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=${pageIndex! }'"/>
			    </li>
		    </ul>
	    </div>
    </form>
	</div>
	</div>
</body>
<script>
jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
$(document).ready(function() {
	$(".select3").uedSelect({
		width : 150
	});
});
</script>
</html>