<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主播审核</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/ui-dialog.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/dialog-min.js${staticVersion! }"></script>

<@plugins.msg />
</head>
<body>
<div class="place">
	<span>位置：</span>
    <ul class="placeul">
	    <li><a href="#">首页</a></li>
	    <li><a href="#">主播管理</a></li>
	    <li><a href="#">主播审核</a></li>
    </ul>
</div>
<div class="formbody">
<div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
	<form action="audit.html" method="post" name="form1" id="form1">
        <ul class="seachform">
        	<li>
        		<label>真实姓名：</label>
	        	<input type="text" name='entity.realname' id="realname" value="${(entity.realname)! }" class="scinput-150"/>
        	</li>
        	<li>
        		<label>用户ID：</label>
	        	<input type="text" name='userIdInt' id="userIdInt" value="${(userIdInt)! }" class="scinput-150" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" autocomplete="off" />
        	</li>
        	<li>
        		<label>审核状态：</label>
        		<div class="vocation">
		        	<select class="select3" name='entity.status' id="status">
				    	<option value="">---请选择---</option>
				    	<option value="0" <#if (entity.status!)?default('') == '0'>selected</#if>>未审核</option>
				    	<option value="1" <#if (entity.status!)?default('') == '1'>selected</#if>>审核通过</option>
				    	<option value="2" <#if (entity.status!)?default('') == '2'>selected</#if>>审核不通过</option>
		    		</select>
	    		</div>
        	</li>
        	<li>
        		<label>时间：</label>
        		<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
			    <input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
			</li>
        	<li><label>&nbsp;</label><input name="" type="submit" class="scbtn" value="查询"/></li>
        </ul>
	    <table class="tablelist">
	    	<thead>
		    	<tr>
			        <th>用户ID</th>
			        <th>用户名</th>
			        <th>真实姓名</th>
			        <th>身份证号码</th>
			        <th>手持身份证正面</th>
			        <th>身份证正面</th>
			        <th>身份证反面</th>
			        <th>身份证到期时间</th>
			        <th>创建时间<i class="sort"><img src="${staticPath! }/static/images/px.gif${staticVersion! }" /></i></th>
			        <th>审核人</th>
			        <th>审核时间</th>
			        <th>审核状态</th>
			        <th>拒绝原因</th>
			        <th>操作</th>
		        </tr>
	        </thead>
	        <tbody>
	        <#list list?if_exists as item>
	        <tr>
		        <td>${item.userIdInt! }</td>
		        <td>${item.username! }</td>
		        <td>${item.realname! }</td>
		        <td>${item.identitycard! }</td>
		        <td><#if item.pic1?default("") != ""><a href="#" onclick="javascript:window.open('audit-pic.html?name=1&id=${item.id!}');" class="tablelink">查看</a></#if></td>
		        <td><#if item.pic2?default("") != ""><a href="#" onclick="javascript:window.open('audit-pic.html?name=2&id=${item.id!}');" class="tablelink">查看</a></#if></td>
		        <td><#if item.pic3?default("") != ""><a href="#" onclick="javascript:window.open('audit-pic.html?name=3&id=${item.id!}');" class="tablelink">查看</a></#if></td>
		        <td>${(item.expirationTime?date)! }</td>
		        <td>${item.createTime! }</td>
		        <td>${item.aduitName! }</td>
		        <td>${item.aduitTime! }</td>
		        <td><font color="<#if item.status?default('') == '1'>green<#elseif item.status?default('') == '2'>red</#if>">${item.statusName! }</font></td>
		        <td title="${item.remark! }"><@plugins.cutOff cutStr="${item.remark! }" cutLen="5" /></td>
		        <td>
		        	<#if item.status?default('0') == '0'>
		        	<a class="tablelink" href="javascript:doAudit('${item.id! }')">审核通过</a> |
		        	<a class="tablelink" href="javascript:doAuditNoPass('${item.id! }')">拒绝</a> |
		        	<#else>
		        	已审核 |
		        	</#if>
		        	<a href="audit-view.html?id=${item.id! }&pageIndex=${pageIndex! }&entity.realname=${(entity.realname)! }&userIdInt=${(userIdInt)! }&entity.status=${(entity.status)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }" class="tablelink">查看</a>
		        </td>
	        </tr>
	        </#list>
	        </tbody>
	    </table>
    </form>
    <div class="pagin">
        <@plugins.page action="audit.html?entity.realname=${(entity.realname)! }&userIdInt=${(userIdInt)! }&entity.status=${(entity.status)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=" />
    </div>
</div>
    
<script type="text/javascript">
jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
$('.tablelist tbody tr:odd').addClass('odd');
$(document).ready(function() {
	$(".select3").uedSelect({
		width : 150
	});
});
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
		dialogMsg(id);
	}
}


function dialogMsg(id){
	var d = dialog({
		title: '请输入拒绝原因',
		width: 670,
		height: 200,
		content:
			'<ul class="forminfo">'
			+ '<li style="margin-top:30px;"><label>原因：</label><textarea id="reason" cols=120 rows=15 class="textinput"/></textarea></li>'
		+'</ul>',
		okValue: '确定',
		ok: function () {
				var reason = $("#reason").val();
				if(reason == "" || reason == undefined){
					alert("请输入原因");
					return false;
				}
				this.title('正在提交..');
				var form1 = document.getElementById("form1");
				form1.action = "audit-doAudit.html?status=2&id="+ id + "&ywUserRoomApply.remark="+ reason +"&pageIndex=${pageIndex! }";
				form1.submit();
				return false;
		},
		cancelValue: '取消',
		cancel: function () {
			this.close();
			this.remove();
			return false;
		}
	});
	d.showModal();
}
</script>
</body>
</html>
