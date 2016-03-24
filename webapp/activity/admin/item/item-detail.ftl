<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>活动管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

<@plugins.msg />
<script>
	<#-- 保存 -->
	function save(){
		if($("#name").val() == ""){
			addFieldError("活动名称不能为空");
			return false;
		}
		if($("#middleGift").val() == ""){
			addFieldError("每人奖品价值降低概率值不能为空");
			return false;
		}
		if($("#maxGift").val() == ""){
			addFieldError("每人奖品价值上限不能为空");
			return false;
		}
    	//表单提交
    	$("#form1").submit();
	}
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">活动</a></li>
		    <li><a href="#">活动管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
		    <form action="activity-save.html" id="form1" method="post" enctype="multipart/form-data">
		    	<input type="hidden" name="name" value="${name!}" />
		    	<input type="hidden" name="pageIndex" value="${pageIndex!}" />
		    	<input type="hidden" name="entity.id" value="${(entity.id)!}" />
			    <div class="formtitle"><span class="sp">活动信息</span></div>
				    <ul class="forminfo">
					    <li><label style="width:150px"><font color="red">*</font>活动名称：</label>
					    	<input value="${(entity.name)! }" id="name" name="entity.name" type="text" class="dfinput-345" maxlength=100/>
					    </li>
					    <li><label style="width:150px"><font color="red">*</font>每人奖品价值最低概率值：</label>
					    	<input value="${(entity.middleGift)! }" id="middleGift" name="entity.middleGift" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" maxlength=10/>
						</li>
					    <li><label style="width:150px"><font color="red">*</font>每人奖品价值上限：</label>
					    	<input value="${(entity.maxGift)! }" id="maxGift" name="entity.maxGift" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" maxlength=10/>
						</li>
						<li><label style="width:150px">开始时间：</label>
					    	<input value="${(entity.startTime)?date }" id="startTime" name="entity.startTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" class="scinput-150" readonly/>
					    	<font color="red">开始时间为空时默认当前时间</font>
					    </li>
					    <li><label style="width:150px">状态：</label>
					    	<label><input type="radio" name="entity.status" value="1" checked/>上线</label>
					    	<label><input type="radio" name="entity.status" value="0" <#if entity.status?default("1") == "0">checked</#if>/>下线</label>
					    </li>
					    <li><label style="width:150px">活动主页：</label>
					    	<input value="${(entity.indexUrl)! }" id="indexUrl" name="entity.indexUrl" type="text" class="dfinput-345" />
					    	<font color="red">格式(http://)</font>
						</li>
					    <li><label style="width:150px">备注：</label>
					    	<input value="${(entity.remark)!}" id="remark" name="entity.remark" type="text" class="dfinput-345" maxlength=200/>
					    </li>
					    <li>
					    	<label style="width:150px">&nbsp;</label>
					    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
					    	<input name="" type="button" class="btn" value="返回" onclick="location.href='activity.html?name=${name!}&pageIndex=${pageIndex! }'"/>
					    </li>
				    </ul>
			    </div>
		    </form>
		</div>
	</div>
</body>
<script>
jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
</script>
</html>
