<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>广告管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>

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
		location.href='adManage-delete.html?entity.name=${(entity.name)! }&entity.clientType=${(entity.clientType)! }&pageIndex=${pageIndex! }&ids=' + id;
	}
}

function deleteAll(){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要删除的数据");
	}else{
		if(confirm("确定需要删除？")){
			var form1 = document.getElementById("form1");
			form1.action = "adManage-delete.html?pageIndex=${pageIndex! }";
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
		    <li><a href="#">游戏管理</a></li>
		    <li><a href="#">广告管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="adManage.html" method="post" name="form1" id="form1">
	    	<ul class="seachform">
	    		<li onclick="location.href='adManage-detail.html?entity.name=${(entity.name)! }&entity.clientType=${(entity.clientType)! }&pageIndex=${pageIndex! }'" class=".toolbar li"><span><img src="${staticPath! }/static/images/t01.png${staticVersion! }" /></span>新增</li>
	        	<li onclick="deleteAll()" class=".toolbar li"><span><img src="${staticPath! }/static/images/t03.png${staticVersion! }" /></span>删除</li>
	        	<li>
	        		<label>标题：</label><input name="entity.name" type="text" value="${(entity.name)! }" class="scinput-150" />
	        			端口：
				    	<select class="select3" name="entity.clientType" class="scinput-150" style="height:30px;border:1px solid #a7b5bc" >
				    		<option value="">全部</option>
				            <option value='0' <#if (entity.clientType)?default('')== '0'>selected</#if> >pc</option>
				            <option value='1' <#if (entity.clientType)?default('')== '1'>selected</#if> >移动端</option>
			    		</select>
	        	</li>
	        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询" onclick="form1.submit();"/></li>
	        </ul>
		    <table class="tablelist">
		    	<thead>
			    	<tr>
				        <th><input id="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
				        <th>标题</th>
				        <th>类型</th>
				        <th>端口</th>
				        <th>房间id</th>
				        <th>链接地址</th>
				        <th>小图</th>
				        <th>大图</th>
				        <th>排序号</th>
				        <th>创建日期</th>
				        <th>操作</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list list?if_exists as item>
		        <tr title="${item.name! }">
			        <td><input name="ids" type="checkbox" value="${item.id! }" /></td>
			        <td>${item.name! }</td>
			        <td><#if item.type == '0'>房间<#elseif item.type == '1'>广告</#if></td>
			        <td><#if item.clientType == '0'>pc端<#elseif item.clientType == '1'>移动端</#if></td>
			        <td>${item.roomId! }</td>
			        <td>${item.linkUrl! }</td>
			        <td style="line-height:4px">
			        	<img width="30px" height="30px" src="<#if item.img?default("") == "">${uploadPath! }${(item.room.liveImg)! }<#else>${uploadPath! }${item.img! }</#if>" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';" onclick="javascript:window.open(this.src)" />
			        </td>
			        <td><#if item.bigImg?default("") != ""><a href="${uploadPath! }${item.bigImg! }" class="tablelink" target="_blank">查看</a><#else>-</#if></td>
			        <td>${item.orderId! }</td>
			        <td>${(item.createTime?datetime)! }</td>
			        <td>
			        	<a href="adManage-detail.html?entity.name=${(entity.name)! }&entity.clientType=${(entity.clientType)! }&id=${item.id! }&pageIndex=${pageIndex! }" class="tablelink">修改</a> |
						<a href="javascript:doDelete('${item.id! }')" class="tablelink">删除</a>
					</td>
		        </tr>
		        </#list>
		        </tbody>
		    </table>
	    </form>
	    <div class="pagin">
	        <@plugins.page action="adManage.html?entity.name=${(entity.name)! }&entity.clientType=${(entity.clientType)! }&pageIndex=" />
	    </div>
	</div>
    
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>