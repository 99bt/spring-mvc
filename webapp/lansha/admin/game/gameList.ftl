<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

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
		location.href='game-remove.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&name=${(name)! }&pageIndex=${pageIndex! }&ids=' + id;
	}
}

function deleteAll(){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要删除的数据");
	}else{
		if(confirm("确定需要删除？")){
			var form2 = document.getElementById("form1");
			form2.action = "game-remove.html?pageIndex=${pageIndex! }";
			form2.submit();
		}
	}
}
//推荐
function updateHot(id){
		var enable = "${staticPath! }/static/images/yes.jpg";
		var disable = "${staticPath! }/static/images/no.jpg";
		
		var ids =id;
		var val = $("#hot"+id).attr("src");
		if(val == enable){
			$("#hot"+id).attr("src", disable);
			<#-- ajax 修改 -->
			$.ajax({ url: "game-updateHot.html?id="+id+"&hot=0"});
		}else{
			$("#hot"+id).attr("src", enable);
			<#-- ajax 修改 -->
			$.ajax({ url: "game-updateHot.html?id="+id+"&hot=1"});
		}
	}
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">游戏管理</a></li>
		    <li><a href="#">游戏维护</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="itab">
		  	<ul> 
			    <li><a href="#tab1" class="selected">游戏</a></li>
			    <li><a href="game-hot.html">推荐游戏</a></li> 
		  	</ul>
	    </div>
	    <div class="rightinfo"  style="overflow:auto;height:500px;" id="contentDiv">
			<form id="form1" action="game.html" method="post">
			    <div class="tools">
			    	<ul class="toolbar">
			    		<li onclick="location.href='game-info.html'" class=".toolbar li"><span><img src="${staticPath! }/static/images/t01.png${staticVersion! }" /></span>新增</li>
			        	<li onclick="deleteAll()"><span><img src="${staticPath! }/static/images/t03.png${staticVersion! }" /></span>删除</li>
			        	名称：<input name="name" value="${name! }" type="text" class="scinput-150" />
			        	时间：<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
			        	<input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
			        	<input name="" type="submit" class="scbtn" value="查询"/>
			        </ul>
			    </div>
			    <table class="tablelist">
			    	<thead>
				    	<tr>
					        <th><input name="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
					        <th>创建时间<i class="sort"><img src="${staticPath! }/static/images/px.gif${staticVersion! }" /></i></th>
					        <th>游戏名称</th>
					        <th>游戏图标</th>
					        <th>二维码</th>
					        <th>状态</th>
					        <th>推荐</th>
					        <th>排序号</th>
					        <th>操作</th>
				        </tr>
			        </thead>
			        <tbody>
			        <#list list?if_exists as item>
			        <tr title="${item.name! }">
				        <td><input name="ids" type="checkbox" value="${item.id! }" /></td>
				        <td>${item.createTime! }</td>
				        <td>${item.name! }</td>
				        <td style="line-height:4px">
				        	<#if item.icon?default("") != "">
				        	<a href="#" onclick="javascript:window.open('${uploadPath! }${item.icon!}');">
				        		<img width="30px" height="30px" src="${uploadPath! }${item.icon! }"/>
				        	</a>
				        	</#if>
				        </td>
				        <td style="line-height:4px">
				        	<#if item.qrcode?default("") != "">
				        	<a href="#" onclick="javascript:window.open('${uploadPath! }${item.qrcode!}');">
				        		<img width="30px" height="30px" src="${uploadPath! }${item.qrcode! }"/>
				        	</a>
				        	</#if>
				        </td>
				        <td>
							<#if item.status == 0>
				        		<font>删除</font>
				        	<#elseif item.status == 1>
				        		<font color="green">上线</font>
				        	<#elseif item.status == 2>
				        		<font color="red">下线</font>
				        	</#if>
						</td>
				        <td>
				        	<img id="hot${item.id! }" src="${staticPath! }/static/images/<#if item.isHotGame?default(0) == 0>no<#elseif item.isHotGame?default(0) == 1>yes</#if>.jpg" onclick="updateHot('${item.id! }')" />
				        </td>
				        <td>${item.orderId! }</td>
				        <td>
				        	<a href="game-info.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&name=${(name)! }&pageIndex=${pageIndex! }&id=${item.id! }" class="tablelink">修改</a> | 
							<a href="javascript:doDelete('${item.id! }')" class="tablelink"> 删除</a>
						</td>
			        </tr>
			        </#list>
			        </tbody>
			    </table>
		    </form>
	    
		    <div class="pagin">
		        <@plugins.page action="game.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&name=${name! }&pageIndex=" />
		    </div>
		</div>
    </div>
    <script type="text/javascript">
	    jQuery("#contentDiv").height(jQuery("#mainFrame", window.parent.parent.document).height() - 120);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>