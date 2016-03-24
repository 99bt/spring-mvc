<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>房间管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/baidu.css" rel="stylesheet">
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
		location.href='room-nvshen-delete.html?pageIndex=${pageIndex! }&ids=' + id;
	}
}

function deleteAll(){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要删除的数据");
	}else{
		if(confirm("确定需要删除？")){
			var form1 = document.getElementById("form1");
			form1.action = "room-nvshen-delete.html?pageIndex=${pageIndex! }";
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
		    <li><a href="#">主播管理</a></li>
		    <li><a href="#">房间管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div id="usual1" class="usual"> 
	    <div class="itab">
	  	<ul>
            <li><a href="room.html">房间管理</a></li>
            <li><a href="room-hot.html" >热门房间</a></li>
            <li><a href="room-dashen.html" >大神房间</a></li>
            <li><a href="#tab1" class="selected">女神房间</a></li>

	  	</ul>
    </div>
    <div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="room-nvshen.html" method="post" name="form1" id="form1">
	    	<ul class="seachform">
	    	<#--
	    		<li onclick="location.href='room-hot-detail.html?pageIndex=${pageIndex! }'" class=".toolbar li"><span><img src="${staticPath! }/static/images/t01.png${staticVersion! }" /></span>新增</li>
	    	-->
	        	<li onclick="deleteAll()" class=".toolbar li"><span><img src="${staticPath! }/static/images/t03.png${staticVersion! }" /></span>删除</li>
	        </ul>
		    <table class="tablelist">
		    	<thead>
			    	<tr>
				        <th><input id="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
				        <th>房间名称</th>
				        <th>房间ID</th>
				        <th>主播名称</th>
				        <th>游戏名称</th>
				        <th>粉丝数</th>
				        <th>观众基数</th>
				        <th>在线人数倍数</th>
				        <th>开播时间</th>
				        <th>举报人数</th>
				        <th>状态</th>
				        <th>排序号<i class="sort"><img src="${staticPath! }/static/images/px.gif${staticVersion! }" /></i></th>
				        <th>创建时间</th>
				        <th>操作</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list listMapRootHot?if_exists as item>
			        <tr title="${item.name! }">
				        <td><input name="ids" type="checkbox" value="${item.id! }" /></td>
				        <td>${item.name! }</td>
				        <td>${item.idInt! }</td>
				        <td>${item.nickName! }</td>
				        <td>${item.gameName! }</td>
				        <td>${(item.fans)?default(0)}</td>
				        <td>${item.baseNumber! }</td>
				        <td>${item.multipleNumber! }</td>
				        <td>${item.startTime! }</td>
				        <td>${(item.reportNum)?default(0)}</td>
				        <td>
				        	<#if item.online == 0>
				        		<font color="red">下线</font>
				        	<#elseif item.online == 1>
				        		<font color="green">上线</font>
				        	<#elseif item.online == 2>
				        		<font color="red">禁播</font>
				        	<#elseif item.online == 4>
				        		<font color="red">删除</font>
				        	</#if>
				        </td>
				        <td>${item.orderId! }</td>
				        <td>${(item.createTime?string("yyyy-MM-dd HH:mm"))!}</td>
				        <td>
				        	<a href="room-nvshen-detail.html?id=${item.id! }&pageIndex=${pageIndex! }" class="tablelink">修改</a> |
							<a href="javascript:doDelete('${item.id! }')" class="tablelink">删除</a>
						</td>
			        </tr>
		        </#list>
		        </tbody>
		    </table>
	    </form>
	    <div class="pagin">
	        <@plugins.page action="room-nvshen.html?pageIndex=" />
	    </div>
	</div>
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>

</html>