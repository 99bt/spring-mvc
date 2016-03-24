<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<link href="${staticPath! }/static/css/ui-dialog.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/dialog-min.js${staticVersion! }"></script>

<@plugins.msg />
<script type="text/javascript">
<#-- 全选 -->
function checkAll(){
	var b = $("input[name='allIds']").attr('checked');
	$("input[name='ids']").attr('checked', b == "checked");
}

<#-- 删除 -->
function doDelete(id){
	if(confirm("确定需要删除？")){
		location.href='ywuser-remove.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&name=${(name)! }&username=${username! }&idInt=${(idInt)! }&pageIndex=${pageIndex! }&ids=' + id;
	}
}

function deleteAll(){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要删除的数据");
	}else{
		if(confirm("确定需要删除？")){
			var form2 = document.getElementById("form1");
			form2.action = "ywuser-remove.html?pageIndex=${pageIndex! }";
			form2.submit();
		}
	}
}

function updateStatus(id, status, name){
	var d = dialog({
		title: '请输入原因',
		width: 520,
		height: 130,
		content:
			'<ul class="forminfo">'
			+ '<li style="margin-top:20px;"><label>原因：</label><textarea id="remark" class="textinput" style="width:300px; height:80px"/></textarea>'
			+ '</li></ul>',
		okValue: '确定',
		ok: function () {
			var remark = $("#remark").val();
			if(remark == "" || remark == undefined){
				alert("请输入原因");
				return false;
			}
			this.title('正在提交..');
			
			if(confirm("确定要"+name+"？")) {
				$.ajax({ 
					url: 'ywuser-updateStatus.html?id='+id+'&status='+status+'&remark='+remark,
	                type: 'get',
	                async: false,
	                dataType: 'text',
	                success: function(data){
	                	if(data != "" && data == "1"){
	                    	addFieldError("操作成功");
	                    	
	                    	var statusTD = $("#statusTD_"+id);
	                    	//设置页面显示状态
	                    	if(status == "1"){
	                    		//恢复正常
	                    		statusTD.html("正常");
	                    	}else if(status == "2"){
	                    		//冻结
	                    		statusTD.html("<font color='#00CED1'>冻结</font>");
	                    	}else if(status == "3"){
	                    		//封号
	                    		statusTD.html("<font color='red'>封号</font>");
	                    	}
	                	}else{
	                		addFieldError("操作失败");
	                	}
	                }
				});
				this.close();
				this.remove();
				return false;
			}
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
function updateBlack(id, index){
	var black = $("#is_black_"+index);
	var blackValue = black.attr('data-value');
	$.ajax({ 
		url: 'ywuser-updateBlack.html?id='+id+'&isBlack='+blackValue,
        type: 'get',
        async: false,
        dataType: 'text',
        success: function(data){
        	if(data != "" && data == "1"){
            	addFieldError("操作成功");
            	if(blackValue == "0"){
	            	black.attr('data-value', 1);
	            	black.html("禁言");
            	}else{
            		black.attr('data-value', 0);
	            	black.html("解除禁言");
            	}
        	}else{
        		addFieldError("操作失败");
        	}
        }
	});
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
	    <div class="rightinfo"  style="overflow:auto;height:500px;" id="contentDiv">
			<form id="form1" action="ywuser.html" method="post">
			    <div class="tools">
			    	<ul class="toolbar">
			    		<li onclick="location.href='ywuser-info.html'" class=".toolbar li"><span><img src="${staticPath! }/static/images/t01.png${staticVersion! }" /></span>新增</li>
			        	<li onclick="deleteAll()"><span><img src="${staticPath! }/static/images/t03.png${staticVersion! }" /></span>删除</li>
			        	会员ID：<input name="idInt" value="${idInt! }" type="text" class="scinput-150" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  autocomplete="off" />
			        	会员昵称：<input name="name" value="${name! }" type="text" class="scinput-150" />
			        	用户名：<input name="username" value="${username! }" type="text" class="scinput-150" />
			        	注册时间：<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
			        	<input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
			        	<input name="" type="submit" class="scbtn" value="查询"/>
			        </ul>
			    </div>
			    <table class="tablelist">
			    	<thead>
				    	<tr>
					        <th><input name="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
					        <th>注册时间<i class="sort"><img src="${staticPath! }/static/images/px.gif${staticVersion! }" /></i></th>
					        <th>头像</th>
					        <th>昵称</th>
					        <th>会员ID</th>
					        <th>用户名</th>
					        <th>在线时长</th>
					        <th>虾米</th>
					        <th>用户状态</th>
					        <th>官方类型</th>
					        <th>token</th>
					        <th>操作</th>
				        </tr>
			        </thead>
			        <tbody>
			        <#list list?if_exists as item>
			        <tr title="${item.username! }">
				        <td><input name="ids" type="checkbox" value="${item.id! }" /></td>
				        <td>${item.createTime! }</td>
				        <td style="line-height:4px">
				        	<#if item.headpic?default("") != "">
				        	<a href="#" onclick="javascript:window.open('${item.headpic!}');">
				        		<img width="30px" height="30px" src="${item.headpic! }"/>
				        	</a>
				        	</#if>
				        </td>
				        <td>${item.nickname! }</td>
				        <td>${item.idInt! }</td>
				        <td>${item.username! }</td>
				        <td>${item.timeLengthStr! }</td>
				        <td>${item.stock?default(0) }</td>
				        <td id="statusTD_${item.id!}"><#if item.userStatus?default(0) == 1>正常<#elseif item.userStatus?default(0) == 2><font color="#00CED1">冻结<#elseif item.userStatus?default(0) == 3><font color="red">封号</font><#else>删除</#if></td>
				        <td><#if item.officialType?default("") == "">普通<#elseif item.officialType?default("") == "1">官方<#elseif item.officialType?default("") == "2">超管<#else>普通</#if></td>				        
				        <td>${item.token! }</td>
				        <td>
				        	<a href="ywuser-info.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&name=${(name)! }&username=${username! }&idInt=${(idInt)! }&pageIndex=${pageIndex! }&entity.id=${item.id! }" class="tablelink">修改</a> | 
				        	<a href="javascript:updateStatus('${item.id! }','1','恢复正常');" class="tablelink">恢复正常</a> | 
				        	<a href="javascript:updateStatus('${item.id! }','3','封号');" class="tablelink">封号</a> | 
				        	<a href="javascript:updateStatus('${item.id! }','2','冻结');" class="tablelink">冻结</a> | 
				        	<a href="javascript:updateBlack('${item.id! }','${item_index}');" data-value="<#if item.isBlack?default('0') == '1'>0<#else>1</#if>" class="tablelink" id="is_black_${item_index}"><#if item.isBlack?default('0') == '1'>解除禁言<#else>禁言</#if></a> | 
				        	<!--
							<a href="javascript:doDelete('${item.id! }')" class="tablelink">删除</a> | -->
							<a href="ywuser-weblogin.html?id=${item.id! }" class="tablelink" target="_blank">一键登录</a> |
							<a href="ywuser-app.html?id=${item.id! }" class="tablelink" target="_blank">app推送</a>
						</td>
			        </tr>
			        </#list>
			        </tbody>
			    </table>
		    </form>
	    
		    <div class="pagin">
		        <@plugins.page action="ywuser.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&name=${name! }&username=${username! }&idInt=${(idInt)! }&pageIndex=" />
		    </div>
		</div>
    </div>
    <script type="text/javascript">
	    jQuery("#contentDiv").height(jQuery("#mainFrame", window.parent.parent.document).height() - 120);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>