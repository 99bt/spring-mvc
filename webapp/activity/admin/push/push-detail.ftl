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
		if($("#title").val() == ""){
			addFieldError("活动标题不能为空");
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
		    <form action="activity-push-save.html" id="form1" method="post" enctype="multipart/form-data">
		    	<input type="hidden" name="name" value="${name!}" />
		    	<input type="hidden" name="pageIndex" value="${pageIndex!}" />
		    	<input type="hidden" name="entity.id" value="${(entity.id)!}" />
			    <div class="formtitle"><span class="sp">活动信息</span></div>
				    <ul class="forminfo">
					    <li><label style="width:150px"><font color="red">*</font>活动标题：</label>
					    	<input value="${(entity.title)! }" id="title" name="entity.title" type="text" class="dfinput-345" maxlength=100/>
					    </li>
                    <#if (entity.indexImg)?default('') != ''>
                        <li><label style="width:150px">移动端banner预览：</label>
                            <div class="screenDiv" style="width:10, height=10">
                                <a href="#" onclick="javascript:window.open('${uploadPath! }${(entity.indexImg)!}');">
                                    <img id="mobileImage1" src="${uploadPath! }${(entity.indexImg)!}" />
                                    <@plugins.ImgStyle name="mobileImage1" width="150" height="150" />
                                </a>
                            </div>
                        </li>
                    </#if>
                        <li><label style="width:150px">图片：</label>
                            <div class="screenDiv" style="width:10, height=10">
                                <input type="hidden" name="entity.indexImg" id="mobileBanner" value="${(entity.indexImg)!}">
                                <input type="file" id="indexImg" name="indexImg" accept=".gif,.jpeg,.jpg,.png" />
                                <font>大小:270*360px</font>
                            </div>
                        </li>


                        <li><label style="width:150px">链接</label>
                            <textarea cols="2" rows="5" class="textinput" name="entity.activityUrl" id="seo">${(entity.activityUrl)! }</textarea>
                        </li>
                        <li><label style="width:150px"><font color="red">*</font>排序号：</label>
                            <input value="${(entity.orderId)! }" id="middleGift" name="entity.orderId" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" maxlength=10/>
                        </li>
					    <li><label style="width:150px">备注：</label>
                            <textarea cols="5" rows="10" class="textinput" name="entity.remark" id="seo">${(entity.remark)! }</textarea>
					    </li>
					    <li>
					    	<label style="width:150px">&nbsp;</label>
					    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
					    	<input name="" type="button" class="btn" value="返回" onclick="location.href='activity-push.html?pageIndex=${pageIndex! }'"/>
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
