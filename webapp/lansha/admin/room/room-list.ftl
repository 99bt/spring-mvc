<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>房间管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/ui-dialog.css${staticVersion! }" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/dialog-min.js${staticVersion! }"></script>

<@plugins.msg />
<script type="text/javascript">
<#-- 全选 -->
function checkAll(){
	var b = $("input[id='allIds']").attr('checked');
	$("input[name='ids']").attr('checked', b == "checked");
}

<#-- 设置为热门房间 -->
function doHot(id){
	if(confirm("确定需要设置为热门房间？")){
		location.href='room-hot-setup.html?pageIndex=${pageIndex! }&id=' + id;
	}
}
function updateHot(id,type){
	var yes = "${staticPath! }/static/images/yes.jpg";
	var no = "${staticPath! }/static/images/no.jpg";
	
	var ids =id;
    var type=type;
	var val = $("#hot"+id+"_"+type).attr("src");
	if(val == yes){
		$("#hot"+id+"_"+type).attr("src", no);
		<#-- ajax 修改 -->
		$.ajax({ url: "room-hot-setup.html?id="+id+"&hot=0&type="+type});
	}else{
        $("#hot"+id+"_"+type).attr("src", yes);
		<#-- ajax 修改 -->
		$.ajax({ url: "room-hot-setup.html?id="+id+"&hot=1&type="+type});
	}
}

<#-- 禁播 -->
function doBanned(id){
	if(confirm("确定需要禁播？")){
	   dialogMsg(id);
	}
}

function dialogMsg(id){
		var d = dialog({
			title: '请输入禁用原因',
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
				alert("请输入禁用原因");
				return false;
			}
			this.title('正在提交..');
			location.href='room-banned.html?pageIndex=${pageIndex! }&roomName=${roomName! }&idInt=${idInt! }&sign=${sign! }&username=${username! }&gameName=${gameName! }&ids=' + id+'&reason='+reason;			
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




<#-- 开播 -->
function doLaunch(id){
	if(confirm("确定需要开播？")){
		location.href='room-launch.html?pageIndex=${pageIndex! }&roomName=${roomName! }&idInt=${idInt! }&sign=${sign! }&username=${username! }&gameName=${gameName! }&ids=' + id;
	}
}

<#-- 删除 -->
function doDelete(id){
	if(confirm("确定需要删除？")){
		location.href='room-delete.html?pageIndex=${pageIndex! }&roomName=${roomName! }&idInt=${idInt! }&sign=${sign! }&username=${username! }&gameName=${gameName! }&ids=' + id;
	}
}

function deleteAll(){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要删除的数据");
	}else{
		if(confirm("确定需要删除？")){
			var form1 = document.getElementById("form1");
			form1.action = "room-delete.html?pageIndex=${pageIndex! }";
			form1.submit();
		}
	}
}

var re = /^[0-9]+[0-9]*]*$/;
function updateNumber(url,eid){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要修改的房间");
	}else{
		var num = $('#' + eid).val();
		if (!re.test(num)) 
		{ 
		       alert("请输入正确数字"); 
		       $('#' + eid).focus(); 
		       return;
		}
		if(confirm("确定需要修改？")){
			var form1 = document.getElementById("form1");
			form1.action = url + ".html?pageIndex=${pageIndex! }&num=" + num;
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
		    <li><a href="#tab1" class="selected">房间管理</a></li>
		    <li><a href="room-hot.html">热门房间</a></li>
            <li><a href="room-dashen.html">大神房间</a></li>
            <li><a href="room-nvshen.html">女神房间</a></li>
	  	</ul>
    </div>
    <div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="room.html" method="post" name="form1" id="form1">
	    	<ul class="seachform">
	    		<li onclick="location.href='room-detail.html?pageIndex=${pageIndex! }&roomName=${roomName! }&idInt=${idInt! }&username=${username! }&gameName=${gameName! }'" class=".toolbar li"><span><img src="${staticPath! }/static/images/t01.png${staticVersion! }" /></span>新增</li>
	        	<li onclick="deleteAll()" class=".toolbar li"><span><img src="${staticPath! }/static/images/t03.png${staticVersion! }" /></span>删除</li>
	        	<li>
	        		<label>房间名称：</label>
		        	<input type="text" name='roomName' id="roomName" value="${roomName! }" class="scinput-150"/>
	        	</li>
	        	<li>
	        		<label>是否签约：</label>
	        		<div class="vocation">
			        	<select class="select3" name='sign' id="sign">
					    	<option value="">---请选择---</option>
					    	<option value="0" <#if sign?default('') == '0'>selected</#if>>未签约</option>
					    	<option value="1" <#if sign?default('') == '1'>selected</#if>>已签约</option>
			    		</select>
		    		</div>
        		</li>
	        	<li>
	        		<label>房间ID：</label>
		        	<input type="text" name='idInt' id="idInt" value="${idInt! }" class="scinput-150" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" autocomplete="off" />
	        	</li>
	        	<li>
	        		<label>主播用户名：</label>
		        	<input type="text" name='username' id="username" value="${username! }" class="scinput-150"/>
	        	</li>
	        	<li>
	        		<label>游戏名称：</label>
		        	<input type="text" name='gameName' id="gameName" value="${gameName! }" class="scinput-150"/>
	        	</li>
	        	<li><label>&nbsp;</label><input name="" type="submit" class="scbtn" value="查询"/></li>
	        </ul><br/>
	    	<ul class="seachform">
	    	     <li>
	        		<label>观众基数：</label>
		        	<input type="text" id="updateBaseNumber" value="" class="scinput-150"/>
	        	</li>
	        	<li onclick="updateNumber('room-update-basenum','updateBaseNumber')" class=".toolbar li"><span></span>批量修改</li>
	    	     <li>
	        		<label>观众倍数：</label>
		        	<input type="text" id="updateMultipleNumber" value="" class="scinput-150"/>
	        	</li>
	        	<li onclick="updateNumber('room-update-multiplenum','updateMultipleNumber')" class=".toolbar li"><span></span>批量修改</li>
	    	</ul>
	    <table class="tablelist">
	    	<thead>
		    	<tr>
			        <th><input id="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
			        <th>房间ID</th>
			        <th>房间名称</th>
			        <th>签约</th>
			        <th>主播用户名</th>
			        <th>主播名称</th>
			        <th>游戏名称</th>
			        <th>粉丝数</th>
			        <th>观众基数</th>
			        <th>观众倍数</th>
			        <th>直播时长</th>
			        <th>举报人数</th>
			        <th>状态</th>
			        <th>排序号<i class="sort"><img src="${staticPath! }/static/images/px.gif${staticVersion! }" /></i></th>
			        <th>创建时间</th>
			        <th>备注</th>
			        <th>热门</th>
                    <th>大神</th>
                    <th>女神</th>
			        <th>操作</th>
		        </tr>
	        </thead>
	        <tbody>
	        <#list list?if_exists as item>
	        <tr title="${item.name! }">
		        <td><input name="ids" type="checkbox" value="${item.id! }" /></td>
		        <td>${item.idInt! }</td>
		        <td>${item.name! }</td>
		        <td><#if item.sign?default("0") == "0">未签约<#elseif item.sign?default("0") == "1">已签约</#if></td>
		        <td>${item.username! }</td>
		        <td>${item.nickname! }</td>
		        <td>${item.gameName! }</td>
		        <td>${item.fans! }</td>
		        <td>${item.baseNumber! }</td>
		        <td>${item.multipleNumber! }</td>
		        <td>${item.playTime! }</td>
		        <td>${item.reportNum! }</td>
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
		        <td title="${(item.remarks)!}"><@plugins.cutOff cutStr="${(item.remarks)! }" cutLen="10" /></td>		        
		        <td>
		        	<img id="hot${item.id! }_1" src="${staticPath! }/static/images/<#if item.isHotRoom?default(0) == 0>no<#elseif item.isHotRoom?default(0) == 1>yes</#if>.jpg" onclick="updateHot('${item.id! }','1')" />
		        </td>
                <td>
                    <img id="hot${item.id! }_2" src="${staticPath! }/static/images/<#if item.daShen?default(0) == 0>no<#elseif item.daShen?default(0) == 1>yes</#if>.jpg" onclick="updateHot('${item.id! }','2')" />
                </td>
                <td>
                    <img id="hot${item.id! }_3" src="${staticPath! }/static/images/<#if item.nvShen?default(0) == 0>no<#elseif item.nvShen?default(0) == 1>yes</#if>.jpg" onclick="updateHot('${item.id! }','3')" />
                </td>
		        <td>
		        	<a href="room-detail.html?pageIndex=${pageIndex! }&id=${item.id! }&roomName=${roomName! }&idInt=${idInt! }&sign=${sign! }&username=${username! }&gameName=${gameName! }" class="tablelink">修改</a> |
		        	<#if item.online == 2>
		        		<a href="javascript:doLaunch('${item.id! }')" class="tablelink">开播</a> |
		        	<#else>
			        	<a href="javascript:doBanned('${item.id! }')" class="tablelink">禁播</a> |
		        	</#if>
					<a href="javascript:doDelete('${item.id! }')" class="tablelink">删除</a>
				</td>
	        </tr>
	        </#list>
	        </tbody>
	    </table>
	    </form>
	    <div class="pagin">
	        <@plugins.page action="room.html?roomName=${roomName! }&idInt=${idInt! }&sign=${sign! }&username=${username! }&gameName=${gameName! }&pageIndex=" />
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
	</script>
</body>
</html>