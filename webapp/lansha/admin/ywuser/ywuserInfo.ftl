<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>

<@plugins.msg />
<script>
	<#-- 保存 -->
	function save(){
		var username = $("#username").val();
		if(username == ""){
			addFieldError("用户名不能为空");
			return false;
		}
		var nickname = $("#nickname").val();
		if(nickname == ""){
			addFieldError("昵称不能为空");
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
		    <li><a href="#">会员管理</a></li>
		    <li><a href="#">会员管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
		    <form action="ywuser-save.html" id="form1" method="post" enctype="multipart/form-data">
		    	<input type="hidden" name="name" value="${name!}" />
		    	<input type="hidden" name="username" value="${username!}" />
		    	<input type="hidden" name="idInt" value="${idInt!}" />
		    	<input type="hidden" name="startTime" value="${(startTime?date)!}" />
		    	<input type="hidden" name="endTime" value="${(endTime?date)!}" />
		    	<input type="hidden" name="pageIndex" value="${pageIndex!}" />
		    	<input type="hidden" name="entity.id" value="${(entity.id)!}" />
		    	<input type="hidden" name="entity.idInt" value="${(entity.idInt)!}" />
			    <div class="formtitle"><span class="sp">会员信息</span></div>
				    <ul class="forminfo">
					    <li><label><font color="red">*</font>用户名：</label>
					    	<input value="${(entity.username)! }" id="username" name="entity.username" type="text" class="dfinput-345" maxlength=30 autocomplete="off"/>
					    </li>
					    <li><label>会员ID：</label>
					    	<label><#if (entity.idInt)?default(0) == 0><font color="red">(自动生成)</font><#else>${(entity.idInt)! }</#if></label>
					    </li>
					    <li><label><font color="red">*</font>昵称：</label>
					    	<input value="${(entity.nickname)! }" id="nickname" name="entity.nickname" type="text" class="dfinput-345" maxlength=30 autocomplete="off"/>
					    </li>
					    <li><label>密码：</label>
					    	<input value="" id="password" name="entity.password" type="password" class="dfinput-345" maxlength=30 autocomplete="off" /><font>空默认不修改</font>
					    </li>
					    <li><label><font color="red">*</font>性别：</label>
					    	<label><input type="radio" name="entity.sex" value="1" checked/> 男</label>
			    			<label><input type="radio" name="entity.sex" value="2" <#if (entity.sex)?default(1) == 2>checked</#if>/> 女</label>
					    </li>
					    <li><label><font color="red">*</font>官方类型：</label>
					    	<label><input type="radio" name="entity.officialType" value="" <#if (entity.officialType)?default("") == "">checked</#if>/> 普通</label>					  
					    	<label><input type="radio" name="entity.officialType" value="1" <#if (entity.officialType)?default("") == "1">checked</#if>/> 官方</label>
			    			<label><input type="radio" name="entity.officialType" value="2" <#if (entity.officialType)?default("") == "2">checked</#if>/> 超管</label>
					    </li>
		    			<#if (entity.headpic)?default('') != ''>
					    <li><label>头像预览：</label>
							<div class="screenDiv" style="width:10px, height=10px">
				    			<a title="点击查看原图" href="#" onclick="javascript:window.open('${(entity.headpic)!}');">
				    				<img id="headpicImg1" src="${(entity.headpic)!}" />
				    			</a>
					    	</div>
					    </li>
					    <@plugins.ImgStyle name="headpicImg1" width="80" height="80" />
		    			</#if>
					    <li><label>头像：</label>
							<div class="screenDiv" style="width:10, height=10">
				    			<input type="hidden" name="entity.headpic" id="headpic" value="${(entity.headpic)!}">
				    			<input type="file" id="headpicImg" name="headpicImg" accept=".gif,.jpeg,.jpg,.png" />
				    			<font>大小:80*80px</font>
					    	</div>
					    </li>
					    <li><label>备注：</label>
					    	<textarea id="remark" name="entity.remark" class="textinput" style="width:600px; height:120px"/>${(entity.remark)! }</textarea>
						</li>
					    <li><label>在线时长：</label>
					    	<label>${(entity.timeLengthStr)!}</label>
					    	<input type="hidden" name="entity.timeLength" value="${(entity.timeLength)!}" />
						</li>
					    <li><label>虾米：</label>
					    	<label>${(entity.stock)?default(0)}</label>
					    	<input type="hidden" name="entity.stock" value="${(entity.stock)?default(0)}" />
						</li>
					    <li><label>账号状态：</label>
					    	<label><#if (entity.userStatus)?default(0) == 1>正常<#elseif (entity.userStatus)?default(0) == 2><font color="#00CED1">冻结<#elseif (entity.userStatus)?default(0) == 3><font color="red">封号</font><#else>删除</#if></label>
					    	<input type="hidden" name="entity.userStatus" value="${(entity.userStatus)!}" />
						</li>
					    <li><label>会员级别：</label>
					    	<label><#if (entity.userType)?default(0) == 1>主播<#else>普通用户</#if></label>
					    	<input type="hidden" name="entity.userType" value="${(entity.userType)!}" />
						</li>
					    <li><label>注册渠道：</label>
					    	<label>${(entity.regChannel)!}</label>
					    	<input type="hidden" name="entity.regChannel" value="${(entity.regChannel)!}" />
						</li>
					    <li><label>是否实名认证：</label>
					    	<label><#if (entity.authe)?default(0) == 1>是<#else>否</#if></label>
					    	<input type="hidden" name="entity.authe" value="${(entity.authe)!}" />
						</li>
					    <li>
					    	<label>&nbsp;</label>
					    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
					    	<input name="" type="button" class="btn" value="返回" onclick="location.href='ywuser.html?name=${name!}&username=${username! }&idInt=${idInt!}&startTime=${(startTime?date)!}&endTime=${(endTime?date)!}&pageIndex=${pageIndex! }'"/>
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
