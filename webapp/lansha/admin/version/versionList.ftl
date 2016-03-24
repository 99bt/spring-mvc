<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>APP版本管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

<@plugins.msg />
<script type="text/javascript">
<#-- 全选 -->
function checkAll(){
	var b = $("input[id='allIds']").attr('checked');
	$("input[name='ids']").attr('checked', b == "checked");
}

<#-- 删除 -->
function doDelete(id){
	if(confirm("确定需要删除？")){
		location.href='version-delete.html?pageIndex=${pageIndex! }&name=${(name)! }&ids=' + id;
	}
}

function deleteAll(){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要删除的数据");
	}else{
		if(confirm("确定需要删除？")){
			var form1 = document.getElementById("form1");
			form1.action = "version-delete.html?pageIndex=${pageIndex! }";
			form1.submit();
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
		    <li><a href="#">APP版本管理</a></li>
		    <li><a href="#">APP版本详情</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="version.html" method="post" name="form1" id="form1">
	    	<ul class="seachform">
	    		<li onclick="location.href='version-detail.html?pageIndex=${pageIndex! }&name=${(name)! }'" class=".toolbar li"><span><img src="${staticPath! }/static/images/t01.png${staticVersion! }" /></span>新增</li>
	        	<li onclick="deleteAll()" class=".toolbar li"><span><img src="${staticPath! }/static/images/t03.png${staticVersion! }" /></span>删除</li>
	        	<li>
	        		<label>包名：</label><input name="name" style="height:30px;line-height:30px;border:1px solid #a7b5bc" type="text" value="${(name)! }"  />
	        		操作系统：
				    	<select class="select3" name="osType" id="osType"  class="scinput-150" style="height:30px;border:1px solid #a7b5bc" >
				    		<option value="">--请选择--</option>
				            <option value='1' <#if (osType)?default('')== '1'>selected</#if> >android</option>
				            <option value='2' <#if (osType)?default('')== '2'>selected</#if> >ios</option>
			    		</select>
				 
	        		上线时间：<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
			        	<input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
	        	</li>
	        	
	        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询" onclick="form1.submit();"/></li>
	        </ul>
		    <table class="tablelist">
		    	<thead>
			    	<tr>
				        <th><input id="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
				        <th>包名</th>
				        <th>版本号</th>
				        <th>操作系统</th>
				        <th>客户端</th>
				        <th>状态</th>
				        <th>地址</th>
				        <th>大小</th>
				        <th>是否强制更新</th>
				        <th>上线时间</th>
				        <th>操作</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list list?if_exists as item>
		        <tr title="${item.name! }">
			        <td><input name="ids" type="checkbox" value="${item.id! }" /></td>
			        <td>${item.name! }</td>
			        <td>${item.version! }</td>
			        <td><#if item.osType == '1'>android<#elseif item.osType == '2'>ios</#if></td>
			        <td><#if item.appType?default("0") == "1">蓝鲨录<#else>普通</#if></td>
			        <td><#if item.status == '0'><font color="red">删除</font><#elseif item.status == '1'><font color="green">正常</font></#if></td>
			        <td>${item.address! }</td>
			        <td>${item.size! }</td>
			        <td><#if item.isForce?default("0") == '0'>否<#elseif item.isForce?default("0") == '1'>是</#if></td>
			        <td>${(item.onlineTime?date)! }</td>
			        <td>
			        	<a href="version-detail.html?pageIndex=${pageIndex! }&id=${item.id! }&osType=${osType! }&name=${(name)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }" class="tablelink">修改</a> |
						<a href="javascript:doDelete('${item.id! }')" class="tablelink">删除</a>
					</td>
		        </tr>
		        </#list>
		        </tbody>
		    </table>
	    </form>
	    <div class="pagin">
	        <@plugins.page action="version.html?name=${name! }&osType=${osType! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=" />
	    </div>
	</div>
    
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>