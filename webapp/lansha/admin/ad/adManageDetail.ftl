<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>广告管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<@plugins.msg />
<@plugins.ImgStyle name="img1" width="150" height="150" />
<@plugins.ImgStyle name="img2" width="150" height="150" />
<script>
	/**
	 *  正则表达式校验字符串
	 *  @param string str  字符串
	 *  @param object RegExp  正则表达式
	 *  return  校验通过返回 true 不通过 返回false
	 */
	function RegExpJug(str, RegExp) {
	    if (RegExp.test(str)) {
	        return true;
	    } else {
	        return false;
	    }
	}
	function cutover(t){
		var type = t.value;
		if(type == '0'){
			$("#liRoom").show();
			$("#liBigImg").hide();
			$("#warn").hide();
			$("#bigImgView").hide();
			//清空file
			var file = $("#bannerBigImg");
			file.after(file.clone().val("")); 
			file.remove();
			//清空img
			$("#img2").attr("src", "");
			
			$("#linkUrl").val("");
			$("#liLink").hide();
		}
		if(type == '1'){
			$("#liRoom").hide();
			$("#liBigImg").show();
			$("#warn").show();
			$("#bigImgView").show();
			$("#roomId").val("");
			$("#liLink").show();
		}
	}
	<#-- 保存 -->
	function save(){
		var name = $("#name").val();
		if(name == "" || name.length > 30){
			addFieldError("标题为空或者长度超过30");
			return false;
		}

		var tt = $('input:radio:checked').val();
		if(tt == "1"){
			var img = $("#img").val(),
			bannerImg = $("#bannerImg").val();
			if(img == "" && bannerImg == ""){
				addFieldError("小图不能为空");
				return false;
			}
			
			var bigImg = $("#bigImg").val(),
				bannerBigImg = $("#bannerBigImg").val();
			if(img == "" && bannerBigImg == ""){
				addFieldError("大图不能为空");
				return false;
			}
		}
		
		if(tt == "0"){
			var roomId = $("#roomId").val();
			var roomId_reg = /^[1-9]\d*$/;
			if(roomId == ""){
				addFieldError("房间id不能为空");
				return false;
			}
			if(roomId.length >8){
				addFieldError("房间id不能大于8位");
				return false;
			}
			if(RegExpJug(roomId,roomId_reg) != true && roomId != 0){
				addFieldError("房间id请填写正整数");
				return;
			}
		}
		if(tt == "1"){
			var linkUrl = $("#linkUrl").val();
			if(linkUrl == "" || linkUrl.length > 100){
				addFieldError("链接地址为空或者长度超过100");
				return false;
			}
		}
		
		var orderId = $("#orderId").val();
		var reg = new RegExp("^[0-9]*$");
		if(orderId == "" || orderId.length > 5){
			addFieldError("排序号为空或者长度超过5");
			return false;
		}
		if(!reg.test($("#orderId").val())){
	        addFieldError("排序号请输入数字!");
	        return false;
   	 	}
		
    	//表单提交
    	$("#form1").submit();
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
    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
    <form action="adManage-save.html" id="form1" method="post" enctype="multipart/form-data">
    	<input type="hidden" id="id" name="ywBanner.id" value="${(ywBanner.id)! }" />
    	<input type="hidden" name="entity.name" value="${(entity.name)! }" />
    	<input type="hidden" name="entity.clientType" value="${(entity.clientType)! }" />
    	<input type="hidden" name="pageIndex" value="${pageIndex! }" />
	    <div class="formtitle"><span class="sp">广告信息</span></div>
		    <ul class="forminfo">
			    <li><label><font color="red">*</font>类型：</label>
			    	<label><input onclick="cutover(this)" type="radio" name="ywBanner.type" id="room" value="0" <#if (ywBanner.type)?default('0') == '0'>checked</#if> />房间</label>
					<label><input onclick="cutover(this)" type="radio" name="ywBanner.type" id="ad" value="1" <#if (ywBanner.type)?default('0') == '1'>checked</#if> />广告</label>
				</li>
			    <li><label><font color="red">*</font>端口：</label>
			    	<label><input type="radio" name="ywBanner.clientType" value="0" <#if (ywBanner.clientType)?default('0') == '0'>checked</#if> />pc端</label>
					<label><input type="radio" name="ywBanner.clientType" value="1" <#if (ywBanner.clientType)?default('0') == '1'>checked</#if> />移动端</label>
				</li>
			    <li><label><font color="red">*</font>标题：</label><input value="${(ywBanner.name)! }" id="name" name="ywBanner.name" type="text" class="dfinput-345" /></li>
    			<#if (ywBanner.img)?default('') != ''>
			    <li><label>小图预览：</label>
					<div class="screenDiv" style="width:10, height=10">
		    			<a href="#" onclick="javascript:window.open('${uploadPath! }${(ywBanner.img)! }');">
		    				<img name="img1" src="${uploadPath! }${(ywBanner.img)!}" id="img1" />
		    			</a>
			    	</div>
			    </li>
    			</#if>
			    <li><label><font color="red" id = "warn" <#if (ywBanner.type)?default('0') == '0' >style="display : none"</#if>>*</font>小图：</label>
					<div class="screenDiv" style="width:10, height=10">
		    			<input type="hidden" name="ywBanner.img" id="img" value="${(ywBanner.img)! }">
		    			<input type="file" id="bannerImg" name="bannerImg" accept=".gif,.jpeg,.jpg,.png" />
			    	</div>
			    </li>
			    <#if (ywBanner.bigImg)?default('') != ''>
			    <li id="bigImgView"><label>大图预览：</label>
					<div class="screenDiv" style="width:10, height=10">
		    			<a href="#" onclick="javascript:window.open('${uploadPath! }${(ywBanner.bigImg)! }');">
		    				<img name="img2" src="${uploadPath! }${(ywBanner.bigImg)!}" id="img2" />
		    			</a>
			    	</div>
			    </li>
    			</#if>
			    <li id = "liBigImg" <#if (ywBanner.type)?default('0') == '0' >style="display : none"</#if>><label><font color="red">*</font>大图：</label>
					<div class="screenDiv" style="width:10, height=10">
		    			<input type="hidden" name="ywBanner.bigImg" id="bigImg" value="${(ywBanner.bigImg)! }">
		    			<input type="file" id="bannerBigImg" name="bannerBigImg" accept=".gif,.jpeg,.jpg,.png" />
			    	</div>
			    </li>
	    		<li id="liLink" <#if (ywBanner.type)?default('0') == '0' >style="display : none"</#if>><label><font color="red">*</font>链接地址：</label><input value="${(ywBanner.linkUrl)! }" id="linkUrl" name="ywBanner.linkUrl" type="text" class="dfinput-345" /></li>
	    		<li id="liRoom" <#if (ywBanner.type)?default('0') == '1' >style="display : none"</#if>><label><font color="red">*</font>房间id：</label><input value="${(ywBanner.roomId)! }" id="roomId" name="ywBanner.roomId" type="text" class="dfinput-345" maxlength=8/></li>
			    </li>
			    <li><label><font color="red">*</font>排序号：</label>
			    	<input value="${(ywBanner.orderId)! }" id="orderId" name="ywBanner.orderId" type="text" class="dfinput-345" maxlength=5/>
				</li>
				<li><label>内容：</label><textarea cols=120 rows=20 class="textinput">${(ywBanner.content)! }</textarea></li>
			    <li>
			    	<label>&nbsp;</label>
			    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
			    	<input name="" type="button" class="btn" value="返回" onclick="location.href='adManage.html?entity.name=${(entity.name)! }&entity.clientType=${(entity.clientType)! }&pageIndex=${pageIndex! }'"/>
			    </li>
		    </ul>
	    </div>
    </form>
	</div>
	</div>
</body>
<script>
jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
</script>
</html>