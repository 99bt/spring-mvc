<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>签约主播管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

<@plugins.msg />
<script type="text/javascript">
<#-- 全选 -->
function checkAll(){
	var b = $("input[id='allIds']").attr('checked');
	$("input[name='ids']").attr('checked', b == "checked");
}


function updateHot(id){
	var yes = "${staticPath! }/static/images/yes.jpg";
	var no = "${staticPath! }/static/images/no.jpg";
	
	var ids =id;
	var val = $("#hot"+id).attr("src");
	if(val == yes){
		$("#hot"+id).attr("src", no);
		<#-- ajax 修改 -->
		$.ajax({ url: "room-hot-setup.html?id="+id+"&hot=0"});
	}else{
		$("#hot"+id).attr("src", yes);
		<#-- ajax 修改 -->
		$.ajax({ url: "room-hot-setup.html?id="+id+"&hot=1"});
	}
}



<#-- 删除 -->
function doDelete(id){
	if(confirm("确定需要删除？")){
		location.href='contract-delete.html?pageIndex=${pageIndex! }&roomId=${roomId! }&ids=' + id;
	}
}

function deleteAll(){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要删除的数据");
	}else{
		if(confirm("确定需要删除？")){
			var form1 = document.getElementById("form1");
			form1.action = "contract-delete.html?pageIndex=${pageIndex! }";
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
		    <li><a href="#">签约主播管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div id="usual1" class="usual"> 
	    <div class="itab">
	  	<ul> 
		    <li><a href="#tab1" class="selected">签约主播管理</a></li>
	  	</ul>
    </div>
    <div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="contract.html" method="post" name="form1" id="form1">
	    	<ul class="seachform">
	    		<li onclick="location.href='contract-detail.html'" class=".toolbar li"><span><img src="${staticPath! }/static/images/t01.png${staticVersion! }" /></span>新增</li>
	        	<li onclick="deleteAll()" class=".toolbar li"><span><img src="${staticPath! }/static/images/t03.png${staticVersion! }" /></span>删除</li>
	        	<li>
	        		<label>房间Id：</label>
		        	<input type="text" name="roomId" id="roomId" value="${roomId! }" class="scinput-150"/>
	        	</li>

	        	<li>
	        		<label>手机号：</label>
		        	<input type="text" name="mobile" id="mobile" value="${mobile!}" class="scinput-150" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" autocomplete="off" />
	        	</li>

                <li>
                    <label>日期：</label><input type="text" name="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
                        <input type="text" name="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
                </li>
                <li>
                    <label>是否考核：</label>
                    <div class="vocation" style="width:150px;">
                        <select class="select3" name="examine" >

                            <option value="3" <#if examine==3>selected</#if>>全部
                            </option>
                            <option value="1" <#if examine==1>selected</#if> >
                                是
                            </option>
                            <option value="0" <#if examine==0>selected</#if> >
                                否
                            </option>
                        </select>
                    </div>
                </li>

	        	<li><label>&nbsp;</label><input name="" type="submit" class="scbtn" value="查询"/></li>
                <li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="导出" onclick="exportData()"/></li>
	        </ul>
	    <table class="tablelist">
	    	<thead>
		    	<tr>
			        <th><input id="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
			        <th>房间ID</th>
			        <th>手机号</th>
			        <th>真实姓名</th>
			        <th>游戏名称</th>
			        <th>签约时间</th>
			        <th>时长指标</th>
			        <th>初始薪资</th>
			        <th>初始指标</th>
                    <th>负责人</th>
                    <th>考核</th>
			        <th>备注</th>
			        <th>操作</th>
		        </tr>
	        </thead>
	        <tbody>
	        <#list list?if_exists as item>
	        <tr title="">
		        <td><input name="ids" type="checkbox" value="${item.roomId!}" /></td>
		        <td>${item.roomId! }</td>
		        <td>${item.mobile! }</td>
		        <td>${item.userName! }</td>
		        <td>${item.gameName! }</td>
                <td>${(item.startTime?string("yyyy-MM-dd"))!}至${(item.endTime?string("yyyy-MM-dd"))!}</td>
		        <td>${item.timeTarget! }</td>
		        <td>${item.salary! }</td>
		        <td>${item.ticketTarget! }</td>
		        <td>${item.manager! }</td>
                <#if item.examine == 0>
                    <td>不考核</td>
                <#else>
                    <td>考核</td>
                </#if>
                <td>${item.remark! }</td>
                <td><a href="contract-detail.html?pageIndex=${pageIndex! }&userId=${item.userId! }&roomId=${item.roomId! }" class="tablelink">修改</a>|
                 <a href="javascript:doDelete('${item.roomId! }')" class="tablelink">删除</a>
                </td>
	         </tr>
	        </#list>
	        </tbody>
	    </table>
	    </form>
	    <div class="pagin">
	        <@plugins.page action="contract.html?roomId=${roomId! }&mobile=${mobile! }&startTime=${startTime!}&endTime=${endTime!}&examine=${examine!}&pageIndex=" />
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
        <#-- 数据导出-->
        function exportData(){
            location.href='contract-export.html?roomId=${roomId! }&mobile=${mobile! }&startTime=${startTime!}&endTime=${endTime!}&examine=${examine!}&currentIndex=${currentIndex! }';
        }

    </script>
</body>
</html>