<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主播审核详情</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<@plugins.msg />
<@plugins.ImgStyle name="img1" width="150" height="150" />
<script>
	<#-- 审核通过 -->
	function doAudit(id){
		if(confirm("确定要审核通过吗？")){
			var form1 = document.getElementById("form1");
			form1.action = "audit-doAudit.html?status=1&id="+ id +"&pageIndex=${pageIndex! }";
			form1.submit();
		}
	}
	<#-- 审核不通过 -->
	function doAuditNoPass(id){
		if(confirm("确定拒绝吗？")){
			var remark = $("#remark").val();
			if(remark == ""){
				addFieldError("请填写拒绝原因");
				return;
			}
			var form1 = document.getElementById("form1");
			form1.action = "audit-doAudit.html?status=2&id="+ id + "&ywUserRoomApply.remark="+ remark +"&pageIndex=${pageIndex! }";
			form1.submit();
		}
	}
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">主播管理</a></li>
		    <li><a href="#">主播审核详情</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
    <form action="audit.html" id="form1" method="post" enctype="multipart/form-data">
    	<input type="hidden" name="userIdInt" value="${(userIdInt)!}" />
    	<input type="hidden" name="entity.realname" value="${(entity.realname)!}" />
    	<input type="hidden" name="entity.status" value="${(entity.status)!}" />
    	<input type="hidden" name="startTime" value="${(startTime?date)!}" />
		<input type="hidden" name="endTime" value="${(endTime?date)!}" />
    	<input type="hidden" name="pageIndex" value="${pageIndex!}" />
	    <div class="formtitle"><span class="sp">审核信息</span></div>
		    <ul class="forminfo">
			    <li><label>用户名：</label><input readonly value="${ywUserRoomApply.username! }" id="username" name="ywUserRoomApply.username" type="text" class="dfinput-345" /></li>
			    <li><label>真实姓名：</label><input readonly value="${ywUserRoomApply.realname! }" id="realname" name="ywUserRoomApply.realname" type="text" class="dfinput-345" /></li>
			    <li><label>身份证号码：</label><input readonly value="${ywUserRoomApply.identitycard! }" id="identitycard" name="ywUserRoomApply.identitycard" type="text" class="dfinput-345" /></li>
			    <li><label>手持身份证正面：</label>
					<div class="screenDiv">
		    			<a href="#" onclick="javascript:window.open('audit-pic.html?name=1&id=${ywUserRoomApply.id!}');">
		    				<img name="img1" height="300px" width="500px" src="audit-pic.html?name=1&id=${ywUserRoomApply.id!}" id="pic1" />
		    			</a>
			    	</div>
			    </li>
			    <li><label>身份证正面：</label>
					<div class="screenDiv">
		    			<a href="#" onclick="javascript:window.open('audit-pic.html?name=2&id=${ywUserRoomApply.id!}');">
		    				<img name="img1" height="300px" width="500px" src="audit-pic.html?name=2&id=${ywUserRoomApply.id!}" id="pic2" />
		    			</a>
			    	</div>
			    </li>
			    <li><label>身份证反面：</label>
					<div class="screenDiv">
		    			<a href="#" onclick="javascript:window.open('audit-pic.html?name=3&id=${ywUserRoomApply.id!}');">
		    				<img name="img1" height="300px" width="500px" src="audit-pic.html?name=3&id=${ywUserRoomApply.id!}" id="pic3" />
		    			</a>
			    	</div>
			    </li>
			    <li><label>身份证到期时间：</label><input readonly value="${ywUserRoomApply.expirationTime?date! }" id="expirationTime" name="ywUserRoomApply.expirationTime" type="text" class="dfinput-345" /></li>
				<li><label>创建时间：</label><input readonly value="${ywUserRoomApply.createTime! }" id="createTime" name="ywUserRoomApply.createTime" type="text" class="dfinput-345" /></li>
				<li><label>审核人：</label><input readonly value="${ywUserRoomApply.aduitName! }" id="aduitName" name="ywUserRoomApply.aduitName" type="text" class="dfinput-345" /></li>
				<li><label>审核时间：</label><input readonly value="${ywUserRoomApply.aduitTime! }" id="aduitTime" name="ywUserRoomApply.aduitTime" type="text" class="dfinput-345" /></li>
				<li><label>审核状态：</label><input readonly value="${ywUserRoomApply.statusName! }" type="text" class="dfinput-345" /></li>
			    <li><label>拒绝原因：</label>
			    	<textarea id="remark" cols=120 rows=20 class="textinput" <#if ywUserRoomApply.remark?default("") != "" || ywUserRoomApply.status?default("") != "0" >readonly</#if> >${ywUserRoomApply.remark! }</textarea>
				</li>
			    <li>
			    	<label>&nbsp;</label>
			    	<#if ywUserRoomApply.status?default('0') == '0'>
		        	<input name="" type="button" class="btn" value="审核通过" onclick="doAudit('${ywUserRoomApply.id! }')"/>
		        	<input name="" type="button" class="btn" value="拒绝" onclick="doAuditNoPass('${ywUserRoomApply.id! }')"/>
		        	<#else>
		        	<input name="" type="button" class="btn" value="已审核" />
		        	</#if>
			    	<input name="" type="button" class="btn" value="返回" onclick="location.href='audit.html?entity.realname=${(entity.realname)! }&userIdInt=${(userIdInt)! }&entity.status=${(entity.status)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=${pageIndex! }'"/>
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