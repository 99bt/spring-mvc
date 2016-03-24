<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>礼物发放详情</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<@plugins.msg />
<@plugins.ImgStyle name="img1" width="150" height="150" />
<script>
	<#-- 发货 -->
	function doGift(id){
		if(confirm("确定要发货吗？")){
			var form1 = document.getElementById("form1");
			var remark = $("#remark").val();
			var itemId = $("#itemId").val();
			form1.action = "gift-doGift.html?status=1&id="+ id +"&giftStock.remark="+ remark +"&entity.itemId = "+itemId+"&pageIndex=${pageIndex! }&name=${entity.itemName! }&currentIndex=${currentIndex! }";
			form1.submit();
		}
	}
	<#-- 审核不通过 -->
	function doGiftNoPass(id){
		if(confirm("确定拒绝吗？")){
			var remark = $("#remark").val();
			if(remark == ""){
				addFieldError("请填写拒绝原因");
				return;
			}
			var form1 = document.getElementById("form1");
			var itemId = $("#itemId").val();
			form1.action = "gift-doGift.html?status=2&id="+ id + "&giftStock.remark="+ remark +"&entity.itemId = "+itemId+"&pageIndex=${pageIndex! }&name=${entity.itemName! }&currentIndex=${currentIndex! }";
			form1.submit();
		}
	}
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">礼物发放</a></li>
		    <li><a href="#">礼物发放详情</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
    <form action="gift-doGift.html" id="form1" method="post" enctype="multipart/form-data">
    	<input type="hidden" id="itemId" name="entity.itemId" value="${(entity.itemId)!}" />
    	<input type="hidden" name="entity.status" value="${(entity.status)!}" />
    	<input type="hidden" name="entity.giftName" value="${(entity.giftName)!}" />
    	<input type="hidden" name="startTime" value="${(startTime?date)!}" />
		<input type="hidden" name="endTime" value="${(endTime?date)!}" />
    	<input type="hidden" name="pageIndex" value="${pageIndex!}" />
    	<input type="hidden" name="currentIndex" value="${currentIndex!}" />
    	<input type="hidden" name="name" value="${(name)!}" />
    	<input type="hidden" name="entity.userName" value="${(entity.userName)!}" />
	    <div class="formtitle"><span class="sp">礼物发放详情</span></div>
	    <table with="100%">
	    	<tr>
	    	<td>
	    		<ul class="forminfo">
			    <li><label>用户昵称：</label>
			    	<label>
			    		${giftStock.nickName! }
			    	</label>
			    </li>
			    <li><label>中奖ID：</label>
			    	<label>
			    		${giftStock.id! }
			    	</label>
			    </li>
				<li><label>中奖时间：</label>
					<nobr>
						<label>
							${giftStock.createTime! }
						</label>
					</nobr>
				</li>
				<li><label>奖品名称：</label>
					<label>
						${giftStock.giftName! }
					</label>
				</li>
				<li><label>QQ号码：</label>
					<label>
						${giftStock.qq! }
					</label>
				</li>
				<li><label>收货人：</label>
					<label>
						${giftStock.realName! }
					</label>
				</li>
				<li><label>收货地址：</label>
					<label>
						${giftStock.address! }
					</label>
				</li>
				<li><label>手机号码：</label>
					<nobr>
						<label>
							${giftStock.mobileDesc! }
						</label>
					</nobr>	
				</li>
				<li><label>奖励发放：</label>
					<label>
						${giftStock.statusName! }
					</label>
				</li>
				<li><label>备注：</label>
			    	<textarea id="remark" cols=120 rows=20 class="textinput" <#if giftStock.status?default("") != "0" >readonly</#if> >${giftStock.remark! }</textarea>
				</li>
			    <li>
			    	<label>&nbsp;</label>
			    	<#if giftStock.status?default('0') == '0'>
		        	<input name="" type="button" class="btn" value="确认发货" onclick="doGift('${giftStock.id! }')"/>
		        	<input name="" type="button" class="btn" value="拒绝" onclick="doGiftNoPass('${giftStock.id! }')"/>
		        	<#else>
		        		
		        	</#if>
			    	<input name="" type="button" class="btn" value="返回" onclick="location.href='gift-list.html?currentIndex=${currentIndex! }&entity.userName=${(entity.userName)! }&name=${name! }&entity.giftName=${(entity.giftName)! }&entity.itemName=${(entity.itemName)! }&entity.itemId=${(giftStock.itemId)! }&entity.status=${(entity.status)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=${pageIndex! }'"/>
			    </li>
		    </ul>
	    	</td>	    		
	    	<td vertical-align="top">
	    	<#if giftStock.type?default("") == "4" >
	    	<div style=" margin-left:20px;height:410px;border-left:1px #000 solid">
	    		<ul class="forminfo">
			    <li><strong>收件人信息</strong>
			    	
			    </li>
			    <li><label>收件人:</label>
			    	<label>
			    		${giftStock.realName! }
			    	</label>
			    </li>
				<li><label>联系电话：</label>
					<label>
						${giftStock.mobile! }
					</label>
				</li>
				<li>
					<label>收件地址：</label>
					<label>${giftStock.address! }</label>
				</li>
		    	</ul>
		    </div>
		    </#if>
	    	</td>
	    	</tr>
	    </table>
    </form>
	</div>
	</div>
</body>
<script>
jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
</script>
</html>