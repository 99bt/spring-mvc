<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>活动管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/ui-dialog.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/dialog-min.js${staticVersion! }"></script>

<@plugins.msg />
<script>
	<#-- 保存 -->
	function save(){
		if($("#name").val() == ""){
			addFieldError("礼品名称不能为空");
			return false;
		}
		if($("#money").val() == ""){
			addFieldError("礼品价值不能为空");
			return false;
		}
		if($("#bi").val() == ""){
			addFieldError("奖品数量不能为空");
			return false;
		}
    	//表单提交
    	$("#form1").submit();
	}
	
	function addStock(){
		var d = dialog({
			title: '加库存',
			width: 400,
			height: 100,
			content:
				'<ul class="forminfo">'
				+ '<li style="margin-top:30px;"><label>库存量：</label><input id="addStock" class="scinput-150" type="text" maxlength="10" autocomplete="off"/>'
				+ '</li></ul>',
			okValue: '确定',
			ok: function () {
				var addStock = $("#addStock").val();
				if(addStock == "" || addStock == undefined){
					alert("请填写库存量");
					return false;
				}
				if(!(/^[1-9]*[1-9][0-9]*$/.test( addStock ))){
					alert("只能输入非0开头的正整数");
					return false;
				}
				if(confirm("确定要提交？")) {
					var from1 = document.getElementById("form1");
					this.title('正在提交..');
					$(".ui-dialog-autofocus").attr("disabled",true);
					form1.action = "activity-gift-stock.html?id=${(entity.id)!}&activityId=${activityId!}&name=${name!}&stock=" + addStock;
					form1.submit();
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
	
	function reduceStock(){
		var d = dialog({
			title: '减库存',
			width: 400,
			height: 100,
			content:
				'<ul class="forminfo">'
				+ '<li style="margin-top:30px;"><label>库存量：</label><input id="reduceStock" class="scinput-150" type="text" maxlength="10" autocomplete="off"/>'
				+ '</li></ul>',
			okValue: '确定',
			ok: function () {
				var reduceStock = parseInt($("#reduceStock").val());
				if(reduceStock == "" || reduceStock == undefined){
					alert("请填写库存量");
					return false;
				}
				if(!(/^[1-9]*[1-9][0-9]*$/.test( reduceStock ))){
					alert("只能输入非0开头的正整数");
					return false;
				}
				var stock =  parseInt($("#stock").val());
				if( stock < reduceStock){
					alert("当前库存不足");
					return false;
				}
				
				if(confirm("确定要提交？")) {
					var from1 = document.getElementById("form1");
					this.title('正在提交..');
					$(".ui-dialog-autofocus").attr("disabled",true);
					form1.action = "activity-gift-stock.html?id=${(entity.id)!}&activityId=${activityId!}&name=${name!}&stock=-" + reduceStock;
					form1.submit();
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
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">活动</a></li>
		    <li><a href="#">礼品管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
		    <form action="activity-gift-save.html" id="form1" method="post" enctype="multipart/form-data">
		    	<input type="hidden" name="name" value="${name!}" />
		    	<input type="hidden" name="pageIndex" value="${pageIndex!}" />
		    	<input type="hidden" name="activityId" value="${activityId!}" />
		    	<input type="hidden" name="entity.itemId" value="${entity.itemId!}" />
		    	<input type="hidden" name="entity.id" value="${(entity.id)!}" />
			    <div class="formtitle"><span class="sp">礼品信息</span></div>
				    <ul class="forminfo">
					    <li><label style="width:150px">活动名称：</label>
					    	<input value="${(activityItem.name)! }" type="text" class="dfinput-345" readonly/>
					    	<font color="red">不可修改</font>
					    </li>
					    <li><label style="width:150px"><font color="red">*</font>礼品名称：</label>
					    	<input value="${(entity.name)! }" id="name" name="entity.name" type="text" class="dfinput-345" maxlength=100/>
					    </li>
					    <li><label style="width:150px"><font color="red">*</font>礼品类型：</label>
					    	<div class="vocation">
						    	<select class="select3" name="entity.type" id="type">
						            <option value='0' <#if (entity.type)?default('0')== '0'>selected</#if> >谢谢惠顾</option>
						            <option value='1' <#if (entity.type)?default('0')== '1'>selected</#if> >蓝鲨币</option>
						            <option value='2' <#if (entity.type)?default('0')== '2'>selected</#if> >礼包</option>
						            <option value='3' <#if (entity.type)?default('0')== '3'>selected</#if> >充值卡</option>
						            <option value='4' <#if (entity.type)?default('0')== '4'>selected</#if> >实物</option>
					    		</select>
						    </div>
					    </li>
					    <#if (entity.img)?default('') != ''>
					    <li><label style="width:150px">礼品图标预览：</label>
							<div class="screenDiv" style="width:10px, height=10px">
				    			<a title="点击查看原图" href="#" onclick="javascript:window.open('${uploadPath! }${(entity.img)!}');">
				    				<img id="img1" src="${uploadPath! }${(entity.img)!}" />
				    			</a>
					    	</div>
					    </li>
					    <@plugins.ImgStyle name="img1" width="80" height="80" />
		    			</#if>
					    <li><label style="width:150px">礼品图标：</label>
							<div class="screenDiv" style="width:10, height=10">
				    			<input type="hidden" name="entity.img" id="img" value="${(entity.img)!}">
				    			<input type="file" id="iconImg" name="iconImg" accept=".gif,.jpeg,.jpg,.png" />
				    			<!-- <font>大小:80*80px</font> -->
					    	</div>
					    </li>
					    <li><label style="width:150px"><font color="red">*</font>价值：</label>
					    	<input value="${(entity.money)! }" id="money" name="entity.money" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" maxlength=10/>
						</li>
					    <li><label style="width:150px"><font color="red">*</font>奖品数量：</label>
					    	<input value="${(entity.bi)! }" id="bi" name="entity.bi" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength=10/>
							<font color="red">正整数</font>
						</li>
					    <li><label style="width:150px">总数量：</label>
					    	<input value="${(entity.number)! }" id="number" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength=10 readonly/>
							<!--<font color="red">正整数</font>-->
						</li>
					    <li><label style="width:150px">库存：</label>
					    	<input value="${(entity.stock)! }" id="stock" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength=10 readonly/>
							<!--<font color="red">不可维护</font>-->
						</li>
					    <li><label style="width:150px"><font color="red">*</font>状态：</label>
					    	<label><input type="radio" name="entity.status" value="1" checked/>上线</label>
					    	<label><input type="radio" name="entity.status" value="0" <#if entity.status?default("1") == "0">checked</#if>/>下线</label>
					    </li>
					    <li><label style="width:150px">排序号：</label>
					    	<input value="${(entity.orderId)! }" id="orderId" name="entity.orderId" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength=10/>
					    	<font color="red">正整数</font>
						</li>
					    <li><label style="width:150px">备注：</label>
					    	<input value="${(entity.remark)!}" id="remark" name="entity.remark" type="text" class="dfinput-345" maxlength=200/>
					    </li>
					    <li>
					    	<label style="width:50px">&nbsp;</label>
					    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
					    	<input name="" type="button" class="btn" value="增库存" onclick="addStock();"/>
					    	<input name="" type="button" class="btn" value="减库存" onclick="reduceStock();"/>
					    	<input name="" type="button" class="btn" value="返回" onclick="location.href='activity-gift-list.html?name=${name! }&activityId=${activityId! }&pageIndex=${pageIndex! }'"/>
					    </li>
				    </ul>
			    </div>
		    </form>
		</div>
	</div>
</body>
<script>
jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
$(document).ready(function() {
	$(".select3").uedSelect({
		width : 150
	});
});
</script>
</html>
