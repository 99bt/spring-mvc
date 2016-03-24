<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>礼物发放</title>
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
	    <li><a href="#">活动</a></li>
	    <li><a href="#">礼物发放</a></li>
    </ul>
</div>
<div class="formbody">
<div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
    <form action="gift-import.html" method="post"  enctype="multipart/form-data" name="form2" id="form2">
        <input type="hidden" name="itemId"  id="itemId" value="${(entity.itemId)!}" />
        <input type="file" name="uploadFile" id="fileName"/>
    </form>

<form action="gift-list.html" method="post"  enctype="multipart/form-data" name="form1" id="form1">
	<input type="hidden" name="name" value="${(name)!}" />
	<input type="hidden" name="entity.itemId"  id="itemId" value="${(entity.itemId)!}" />
	<input type="hidden" name="entity.itemName"  id="itemId" value="${(entity.itemName)!}" />
	<input type="hidden" name="currentIndex" value="${currentIndex!}" />
        <ul class="seachform">
        	<li>
        		<label>活动名称：</label>
	        	<label><font color="red">${(entity.itemName)! }</font></label>
        	</li>
        	<li>
        		<label>领取人账号：</label>
	        	<input type="text" name='entity.userName' id="userName" value="${(entity.userName)! }" class="scinput-150"/>
        	</li>
        	<li>
        		<label>礼品名称：</label>
	        	<input type="text" name='entity.giftName' id="giftName" value="${(entity.giftName)! }" class="scinput-150"/>
        	</li>
        	<li>
        		<label>审核状态：</label>
        		<div class="vocation">
		        	<select class="select3" name='entity.status' id="status">
				    	<option value="">---请选择---</option>
				    	<option value="0" <#if (entity.status!)?default('') == '0'>selected</#if>>等待发货</option>
				    	<option value="1" <#if (entity.status!)?default('') == '1'>selected</#if>>已发货</option>
				    	<option value="2" <#if (entity.status!)?default('') == '2'>selected</#if>>审核不通过</option>
		    		</select>
	    		</div>
        	</li>        	
        </ul>
        <br/>
        <ul class="seachform">
        	<li>
        		<label>时间：</label>
        		<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
			    <input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
			</li>
        	<li><label>&nbsp;</label><input name="" type="submit" class="scbtn" value="查询"/></li>
        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="返回活动" onclick="location.href='gift.html?pageIndex=${currentIndex! }&name=${name! }'"/></li>
        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="导出" onclick="exportData()"/></li>
        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="批量发货" onclick="doBatchGifts(1)"/></li>
        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="批量拒绝" onclick="doBatchGifts(2)"/></li>
            <li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="导入发货" onclick="doExcel()"/></li>



        </ul>
	    <table class="tablelist">
	    	<thead>
		    	<tr>
		    		<th><input name="allIds" type="checkbox" value="" onclick="checkAll();"/></th>
			        <th>礼品名称</th>
			        <th>领取人账号</th>
			        <th>QQ号码</th>
			        <th>真实姓名</th>
			        <th>发货状态</th>
			        <th>礼品类型</th>
			        <th>中奖时间<i class="sort"><img src="${staticPath! }/static/images/px.gif${staticVersion! }" /></i></th>			        
			        <th>IP地址</th>	
			        <th>操作人</th>
			        <th>备注</th>		        
			        <th>操作</th>
		        </tr>
	        </thead>
	        <tbody>
	        <#list list?if_exists as item>
	        <tr>	        	
	        	<td><input name="ids" type="checkbox" value="${item.id! }" /></td>
		        <td>${item.giftName! }</td>
		        <td>${item.userName! }</td>
		        <td>${item.qq! }</td>
		        <td>${item.realName! }</td>
		        <td><font color="<#if item.status?default('') == '1'>green<#elseif item.status?default('') == '2'>red</#if>">${item.statusName! }</font></td>        	        
		        <td>${item.typeName! }</td>
		        <td>${item.createTime! }</td>
		        <td>${item.ip! }</td>
		        <td>${item.adminName! }</td>
		        <td title="${(item.remark!)?replace("<font[^>]*>|</font[^>]*>","","ri")}"><@plugins.cutOff cutStr="${item.remark! }" cutLen="25" /></td>
		        <td>		        	
		        	<#if item.status?default('0') == '0'>
		        	<a href="gift-view.html?pageIndex=${pageIndex! }&currentIndex=${currentIndex! }&id=${item.id! }&entity.userName=${(entity.userName)! }&name=${name! }&entity.giftName=${(entity.giftName)! }&entity.status=${(entity.status)! }&entity.itemName=${(item.itemName)! }&entity.itemId=${(item.itemId)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }" class="tablelink">发货</a> |
		        	<a class="tablelink" href="javascript:doGiftNoPass('${item.id! }')">拒绝</a> |
		        	<#elseif item.status?default('') == '2'>
		        	审核不通过 |
		        	<#else>
		        	已发货 |
		        	</#if>
		        	<a href="gift-view.html?pageIndex=${pageIndex! }&entity.userName=${(entity.userName)! }&currentIndex=${currentIndex! }&id=${item.id! }&name=${name! }&entity.giftName=${(entity.giftName)! }&entity.status=${(entity.status)! }&entity.itemName=${(item.itemName)! }&entity.itemId=${(item.itemId)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }" class="tablelink">查看</a>
		        </td>	        
	        </tr>
	        </#list>
	        </tbody>
	    </table>
</form>
    <div class="pagin">
        <@plugins.page action="gift-list.html?entity.userName=${(entity.userName)! }&currentIndex=${currentIndex! }&entity.giftName=${(entity.giftName)! }&entity.itemName=${(entity.itemName)! }&entity.itemId=${(entity.itemId)! }&name=${name! }&entity.status=${(entity.status)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=" />
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
<#-- 全选 -->
function checkAll(){
	var b = $("input[name='allIds']").attr('checked');
	$("input[name='ids']").attr('checked', b == "checked");
}

<#-- 发货 -->
function doGift(id){
	if(confirm("确定吗？")){
		var form1 = document.getElementById("form1");
		form1.action = "gift-doGift.html?status=1&id="+ id +"&entity.itemId=${(entity.itemId)! }&currentIndex=${currentIndex! }&entity.itemName=${(entity.itemName)! }&pageIndex=${pageIndex! }";
		form1.submit();
	}
}
<#-- 审核不通过 -->
function doGiftNoPass(id){
	if(confirm("确定拒绝吗？")){
		dialogMsg(id);
	}
}
function doExcel(){
    if(confirm("确定导入吗？")){
        var fileUrl = $("#fileName").val();
        var suffix = fileUrl.substring(fileUrl.lastIndexOf('.')+1, fileUrl.length);
        if((suffix != "xls") && (suffix != "xlsx")){
            alert("请选择excel文件！");
            document.getElementById("form2").reset();//表单内容设置为空
            return false;
        }
        var startIndex = fileUrl.lastIndexOf('\\');
        var endIndex = fileUrl.lastIndexOf('.');
        var fileName = fileUrl.substring(startIndex+1, endIndex);


        if(fileName == "" || fileName == undefined){
            alert("选择文件");
            return false;
        }
        var form2 = document.getElementById("form2");
        form2.action = "gift-import.html";
        form2.submit();
    }
}

<#-- 数据导出-->
function exportData(){
	location.href='gift-export.html?entity.userName=${(entity.userName)! }&currentIndex=${currentIndex! }&entity.giftName=${(entity.giftName)! }&entity.itemName=${(entity.itemName)! }&entity.itemId=${(entity.itemId)! }&entity.status=${(entity.status)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }';
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
				form1.action = "gift-doGift.html?status=2&id="+ id + "&giftStock.remark="+ reason +"&pageIndex=${pageIndex! }";
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

function doBatchGifts(status){
	var i = $("input[type='checkbox'][name='ids']:checked").length;
	if(i <= 0){
		addFieldError("请先选择需要审核的数据");
	}else{
		var okValue = "";
		var actionUrl = "";		
		if(status=='1'){
			okValue = "确定发货";
			actionUrl = "gift-doBatchGifts.html?pageIndex=${pageIndex! }";
		}else{
			okValue = "确定拒绝发货";
			actionUrl = "gift-doBatchRejectGifts.html?pageIndex=${pageIndex! }";
		}
		var d = dialog({
			title: '批量审核',
			width: 670,
			height: 200,
			content:
				'<ul class="forminfo">'
				+ '<li style="margin-top:30px;"><label>备注：</label><textarea id="batchReason" cols=120 rows=15 class="textinput"/></textarea></li>'
				+'</ul>',
			okValue: okValue,
			ok: function () {
				var reason = $("#batchReason").val();
				if(reason == "" || reason == undefined){
					alert("请输入内容");
					return false;
				}
				if(confirm(okValue+"吗?")){
					this.title('正在提交..');
					var form1 = document.getElementById("form1");
					form1.action = actionUrl+"&giftStock.remark="+ reason;
					form1.submit();
					return false;
				}
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
}

function delHtmlTag(str)
{
	return str.replace(/<[^>]+>/g,"");//去掉所有的html标记
}
</script>
</body>
</html>
