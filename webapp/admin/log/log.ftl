<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>

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
		location.href='log-delete.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&entity.type=${(entity.type)! }&entity.value=${(entity.value)! }&pageIndex=${pageIndex! }&ids=' + id;
	}
}

function deleteAll(){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要删除的数据");
	}else{
		if(confirm("确定需要删除？")){
			var form2 = document.getElementById("form2");
			form2.action = "log-delete.html?pageIndex=${pageIndex! }";
			form2.submit();
		}
	}
}
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">管理信息</a></li>
		    <li><a href="#">日志管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div id="usual1" class="usual"> 
	    <div class="itab">
	  	<ul> 
		    <li><a href="#tab1" class="selected">系统日志</a></li>
		    <li><a href="log-admin.html">后台登录日志</a></li> 
		    <li><a href="log-mt.html">短信日志</a></li>
		    <li><a href="log-email.html">邮件日志</a></li>
		    <li><a href="log-user.html">前台登录日志</a></li> 
	  	</ul>
	    </div>
	    </div>
    </div>
    
    <div class="rightinfo"  style="overflow:auto;height:500px;" id="contentDiv">
		<form id="form1" action="log.html" method="post">
	    <div class="tools">
	    	<ul class="toolbar">
	        	<li onclick="deleteAll()"><span><img src="${staticPath! }/static/images/t03.png${staticVersion! }" /></span>删除</li>
	        	类型：<input name="entity.type" value="${(entity.type)! }" type="text" class="scinput-150" />
	        	内容：<input name="entity.value" value="${(entity.value)! }" type="text" class="scinput-150" />
	        	时间：<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
	        	<input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
	        	<input name="" type="submit" class="scbtn" value="查询"/>
	        </ul>
	    </div>
    	</form>
	    
		<form id="form2" action="" method="post">
			<input type="hidden" name="startTime" value="${(startTime?date)! }" />
			<input type="hidden" name="endTime" value="${(endTime?date)! }" />
			<input type="hidden" name="entity.type" value="${(entity.type)! }" />
		    <table class="tablelist">
		    	<thead>
			    	<tr>
				        <th><input name="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
				        <th>类型</th>
				        <th>内容</th>
				        <th>创建时间<i class="sort"><img src="${staticPath! }/static/images/px.gif${staticVersion! }" /></i></th>
				        <th>操作</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list list?if_exists as item>
		        <tr title="${item.value! }">
			        <td><input name="ids" type="checkbox" value="${item.id! }" /></td>
			        <td>${item.type! }</td>
			        <td><@plugins.cutOff cutStr="${item.value! }" cutLen="80" /></td>
			        <td>${item.createTime! }</td>
			        <td>
			        	<a href="log-view.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&entity.type=${(entity.type)! }&entity.value=${(entity.value)! }&pageIndex=${pageIndex! }&id=${item.id! }" class="tablelink">查看</a>
						<a href="javascript:doDelete('${item.id! }')" class="tablelink"> 删除</a>
					</td>
		        </tr>
		        </#list>
		        </tbody>
		    </table>
	    </form>
    
	    <div class="pagin">
	        <@plugins.page action="log.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&entity.type=${(entity.type)! }&entity.value=${(entity.value)! }&pageIndex=" />
	    </div>
	</div>
    
    <script type="text/javascript">
	    jQuery("#contentDiv").height(jQuery("#mainFrame", window.parent.parent.document).height() - 120);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>