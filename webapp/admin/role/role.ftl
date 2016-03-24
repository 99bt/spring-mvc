<#-- 角色 -->
<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<@plugins.msg />
<script type="text/javascript">
<#-- 全选 -->
function checkAll(){
	var b = $("input[id='allIds']").attr('checked');
	$("input[name='ids']").attr('checked', b == "checked");
}
</script>
</head>

<body>
	<div class="place">
	    <span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">管理信息</a></li>
		    <li><a href="#">角色管理</a></li>
    	</ul>
    </div>
    <div class="formbody">
	    <div id="usual1" class="usual"> 
	    <div class="itab">
	  	<ul> 
		    <li><a href="#tab1" class="selected">角色管理</a></li> 
	  	</ul>
    </div> 
    
    <div class="rightinfo"  style="overflow:auto;height:500px;" id="contentDiv">
	  	<div id="tab1" class="tabson">
		    <div class="tools">
		    	<ul class="toolbar">
		        	<li onclick="location.href='role-view.html'"><span><img src="${staticPath! }/static/images/t01.png${staticVersion! }" /></span>新增</li>
		        </ul>
		    </div>
		    
		    <table class="tablelist">
		    	<thead>
			    	<tr>
				        <th><input id="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
				        <th>角色名</th>
				        <th>状态</th>
				        <th>类型</th>
				        <th>排序号</th>
				        <th>创建时间<i class="sort"><img src="${staticPath! }/static/images/px.gif${staticVersion! }" /></i></th>
				        <th>操作</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list list?if_exists as item>
		        <tr title="${item.value! }">
			        <td><input name="ids" type="checkbox" value="${item.id! }" /></td>
			        <td>${item.name! }</td>
			        <td><#if item.state == "1">正常<#elseif item.state == "2"><label style="color:red">停用</label></#if></td>
			        <td><#if item.isSys><label style="color:red">系统内置</label><#else>用户创建</#if></td>
			        <td>${item.displayOrder! }</td>
			        <td>${item.createTime! }</td>
			        <td>
			        <#if item.isSys>
			        	<a href="role-view.html?id=${item.id! }" class="tablelink">查看</a>
			        <#else>
			        	<a href="role-view.html?id=${item.id! }" class="tablelink">修改</a>
			        	<a href="role-delete.html?ids=${item.id! }" class="tablelink" onclick="return confirm('确定需要删除？')">删除</a>
			        </#if>
			        </td>
		        </tr>
		        </#list>
		        </tbody>
		    </table>
		</div>
	</div>
    
    <script type="text/javascript">
    jQuery("#contentDiv").height(jQuery("#mainFrame", window.parent.parent.document).height() - 120);
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
    </div>
</body>

</html>
