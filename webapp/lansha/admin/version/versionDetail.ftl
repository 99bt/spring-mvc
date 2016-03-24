<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>APP版本管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/validate.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>


<@plugins.msg />
<@plugins.ImgStyle name="img1" width="150" height="150" />
<script>
<#-- 保存 -->
function save(){
	var name = $("#name").val();
	if(name == ""){
		addFieldError("包名不能为空");
		return false;
	}
	var status = $("#version").val();
	if(status == ""){
		addFieldError("版本号不能为空");
		return false;
	}
	var onlineTime = $("#onlineTime").val();
	if(onlineTime == ""){
		addFieldError("请选择上线时间");
		return false;
	}
	var osType = $("#osType").val();
	if(osType == ""){
		addFieldError("请选择操作系统");
		return false;
	}
	var status = $("#status").val();
	if(status == ""){
		addFieldError("请选择状态");
		return false;
	}
	var isForce = $("#isForce").val();
	if(isForce == ""){
		addFieldError("请选择是否强制更新");
		return false;
	}
	var address = $("#address").val();
	if(address == ""){
		addFieldError("请输入地址");
		return false;
	}
	var size = $("#size").val();
	if(size == ""){
		addFieldError("请输入大小");
		return false;
	}<#--else if(isNaN(size)){
		addFieldError("大小的值只能为数字");
		return false;
	}else if(size>2147483646){
		addFieldError("大小的值不能大于2147483646");
		return false;
	}-->
	var updateLog = $("#updateLog").val();
	if(updateLog == ""){
		addFieldError("请输入更新日志");
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
		    <li><a href="#">系统管理</a></li>
		    <li><a href="#">APP版本管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
    <form action="version-save.html" id="form1" method="post" enctype="multipart/form-data">
    	<input type="hidden" id="id" name="entity.id" value="${(entity.id)!}" />
    	<input type="hidden" id="names" name="name" value="${(name)!}" />
    	<input type="hidden" id="osTypes" name="osType" value="${(osType)!}" />
    	<input type="hidden" id="startTime" name="startTime" value="${(startTime?date)! }" />
    	<input type="hidden" id="endTime" name="endTime" value="${(endTime?date)! }" />
	    <div class="formtitle"><span class="sp">基本信息</span></div>
		    <ul class="forminfo">
			    <li><label><font color="red">*</font>包名：</label><input value="${(entity.name)! }" id="name" name="entity.name" type="text" class="dfinput-345" /></li>
			    <li><label><font color="red">*</font>版本号：</label><input value="${(entity.version)! }" id="version" name="entity.version" type="text" class="dfinput-345" /></li>
			    <li><label><font color="red">*</font>上线时间：</label><input value="${((entity.onlineTime)?date)! }" id="onlineTime" name="entity.onlineTime" type="text" onclick="WdatePicker();" class="scinput-150" readonly /></li>
			    
			     <li><label><font color="red">*</font>操作系统：</label>
			    	<div class="vocation">
				    	<select class="select3" name="entity.osType" id="osType">
				    		<option value="">--请选择--</option>
				            <option value='1' <#if (entity.osType)?default('')== '1'>selected</#if> >android</option>
				            <option value='2' <#if (entity.osType)?default('')== '2'>selected</#if> >ios</option>
			    		</select>
				    </div>
			    </li>
			     <li><label><font color="red">*</font>客户端类型：</label>
			    	<div class="vocation">
				    	<select class="select3" name="entity.appType" id="appType">
				            <option value='0' <#if (entity.appType)?default('0')== '0'>selected</#if> >普通</option>
				            <option value='1' <#if (entity.appType)?default('')== '1'>selected</#if> >蓝鲨录</option>
			    		</select>
				    </div>
			    </li>
			    <li><label><font color="red">*</font>状态：</label>
			    	<div class="vocation">
				    	<select class="select3" name="entity.status" id="status">
				    		<option value="">--请选择--</option>
				            <option value='0' <#if (entity.status)?default('')== '0'>selected</#if> >删除</option>
				            <option value='1' <#if (entity.status)?default('')== '1'>selected</#if> >正常</option>
			    		</select>
				    </div>
			    </li>
			    <li><label><font color="red">*</font>是否强制更新：</label>
			    	<div class="vocation">
				    	<select class="select3" name="entity.isForce" id="isForce">
				    		<option value="">--请选择--</option>
				            <option value='0' <#if (entity.isForce)?default('')== '0'>selected</#if> >否</option>
				            <option value='1' <#if (entity.isForce)?default('')== '1'>selected</#if> >是</option>
			    		</select>
				    </div>
			    </li>
			    <li><label><font color="red">*</font>地址：</label><input value="${(entity.address)! }" id="address" name="entity.address" type="text" class="dfinput-345" /></li>
			    <li><label><font color="red">*</font>大小：</label><input value="${(entity.size)! }" id="size" name="entity.size" type="text" class="dfinput-345" /></li>
			   	<li>
			   		<label><font color="red">*</font>更新日志：</label><textarea name="entity.updateLog" id="updateLog" style="font-size:9pt;line-height:1.5; resize: none;width:347px;height:200px;border-top: solid 1px #a7b5bc;border-left: solid 1px #a7b5bc;border-right: solid 1px #ced9df;border-bottom: solid 1px #ced9df;" >${(entity.updateLog)! }</textarea>
			   	</li>
			    <li>
			    	<label>&nbsp;</label>
			    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
			    	<input name="" type="button" class="btn" value="返回" onclick="location.href='version.html?name=${(name)! }&osType=${osType! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=${pageIndex! }'"/>
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
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/upload/moxie.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.xBox.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jq_input.js"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/upload/plupload.full.min.js"></script>
</html>