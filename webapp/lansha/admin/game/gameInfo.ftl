<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏维护</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>

<@plugins.msg />
<@plugins.ImgStyle name="img1" width="150" height="150" />
<script>
	<#-- 保存 -->
	function save(){
		var name = $("#name").val();
		if(name == ""){
			addFieldError("名称不能为空");
			return false;
		}		
    	//表单提交
    	$("#form1").submit();
	}
	
	var file_to=0;
	function add_inp(){
		var screenDiv = $("#screenDiv");
		
		screenDiv.append("<input type='file' name='screenImg"+file_to+"' id='file_"+file_to+"'>")
		screenDiv.append("<input value='删除' type='button' id='file_del_"+file_to+"' onclick='del_inp("+file_to+")' />");
		screenDiv.append("<br id='file_br_"+file_to+"'/>");
		
        file_to++;
	        
	}
	function del_inp(file_id){
		var screenDiv = document.getElementById("screenDiv");
	    screenDiv.removeChild(document.getElementById("file_"+file_id));
	    screenDiv.removeChild(document.getElementById("file_del_"+file_id));
	    screenDiv.removeChild(document.getElementById("file_br_"+file_id));
	}
	
	function del_screen(index){
		//删除
		$("#screen_a_"+index).remove();
		$("#screen_"+index).remove();
		$("#screen_del_"+index).remove();
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
	    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
		    <form action="game-save.html" id="form1" method="post" enctype="multipart/form-data">
		    	<input type="hidden" name="name" value="${name!}" />
		    	<input type="hidden" name="startTime" value="${(startTime?date)!}" />
		    	<input type="hidden" name="endTime" value="${(endTime?date)!}" />
		    	<input type="hidden" name="pageIndex" value="${pageIndex!}" />
		    	<input type="hidden" name="entity.id" value="${(entity.id)!}" />
		    	<input type="hidden" name="entity.screen" value="${(entity.screen)!}" />
			    <div class="formtitle"><span class="sp">游戏信息</span></div>
				    <ul class="forminfo">
					    <li><label style="width:150px"><font color="red">*</font>名称：</label>
					    	<input value="${(entity.name)! }" id="name" name="entity.name" type="text" class="dfinput-345" maxlength=30/>
					    </li>
					    <li><label style="width:150px">SEO：</label><textarea cols="10" rows="10" class="textinput" name="entity.seo" id="seo">${(entity.seo)! }</textarea></li>
					    
					    <li><label style="width:150px">类型：</label>
					    	<div class="vocation">
						    	<select class="select3" name="entity.typeId" id="state">
					    			<option value="">---请选择游戏类型---</option>
						    		<#list typeList?if_exists as item>
					    			<option value="${item.id}" <#if item.id == (entity.typeId)?default("")>selected</#if>>${(item.name)!}</option>
					    			</#list>
					    		</select>
						    </div>
					    </li>
					    <li><label style="width:150px">android下载地址：</label>
					    	<input value="${(entity.androidUrl)! }" id="androidUrl" name="entity.androidUrl" type="text" class="dfinput-345" maxlength=200/>
						</li>
					    <li><label style="width:150px">ios下载地址：</label>
					    	<input value="${(entity.iosUrl)! }" id="iosUrl" name="entity.iosUrl" type="text" class="dfinput-345" maxlength=200/>
						</li>
						
		    			<#if (entity.icon)?default('') != ''>
					    <li><label style="width:150px">游戏图标预览：</label>
							<div class="screenDiv" style="width:10, height=10">
				    			<a href="#" onclick="javascript:window.open('${uploadPath! }${(entity.icon)!}');">
				    				<img id="iconImg1" src="${uploadPath! }${(entity.icon)!}" />
				    				<@plugins.ImgStyle name="iconImg1" width="150" height="150" />
				    			</a>
					    	</div>
					    </li>
		    			</#if>
					    <li><label style="width:150px">游戏图标上传：</label>
							<div class="screenDiv" style="width:10, height=10">
				    			<input type="hidden" name="entity.icon" id="icon" value="${(entity.icon)!}">
				    			<input type="file" id="iconImg" name="iconImg" accept=".gif,.jpeg,.jpg,.png" />
				    			<font>大小:100*100px</font>
					    	</div>
					    </li>
					    
		    			<#if (entity.advert)?default('') != ''>
					    <li><label style="width:150px">游戏图预览：</label>
							<div class="screenDiv" style="width:10, height=10">
				    			<a href="#" onclick="javascript:window.open('${uploadPath! }${(entity.advert)!}');">
				    				<img id="advertImg1" src="${uploadPath! }${(entity.advert)!}" />
				    				<@plugins.ImgStyle name="advertImg1" width="150" height="150" />
				    			</a>
					    	</div>
					    </li>
		    			</#if>
					    <li><label style="width:150px">游戏图上传：</label>
							<div class="screenDiv" style="width:10, height=10">
				    			<input type="hidden" name="entity.advert" id="advert" value="${(entity.advert)!}">
				    			<input type="file" id="advertImg" name="advertImg" accept=".gif,.jpeg,.jpg,.png" />
				    			<font>大小:380*190px</font>
					    	</div>
					    </li>
					    <#if (entity.advertSmall)?default('') != ''>
					    <li><label style="width:150px">游戏图(小)预览：</label>
							<div class="screenDiv" style="width:10, height=10">
				    			<a href="#" onclick="javascript:window.open('${uploadPath! }${(entity.advertSmall)!}');">
				    				<img id="advertSmallImg1" src="${uploadPath! }${(entity.advertSmall)!}" />
				    				<@plugins.ImgStyle name="advertSmallImg1" width="150" height="150" />
				    			</a>
					    	</div>
					    </li>
		    			</#if>
					    <li><label style="width:150px">游戏图(小)上传：</label>
							<div class="screenDiv" style="width:10, height=10">
				    			<input type="hidden" name="entity.advertSmall" id="advertSmall" value="${(entity.advertSmall)!}">
				    			<input type="file" id="advertSmallImg" name="advertSmallImg" accept=".gif,.jpeg,.jpg,.png" />
				    			<font>大小:336*224px</font>
					    	</div>
					    </li>
					    <#if (entity.background)?default('') != ''>
					    <li><label style="width:150px">游戏背景图预览：</label>
							<div class="screenDiv" style="width:10, height=10">
				    			<a href="#" onclick="javascript:window.open('${uploadPath! }${(entity.background)!}');">
				    				<img id="backgroundImg1" src="${uploadPath! }${(entity.background)!}" />
				    				<@plugins.ImgStyle name="backgroundImg1" width="150" height="150" />
				    			</a>
					    	</div>
					    </li>
		    			</#if>
					    <li><label style="width:150px">游戏背景图上传：</label>
							<div class="screenDiv" style="width:10, height=10">
				    			<input type="hidden" name="entity.background" id="background" value="${(entity.background)!}">
				    			<input type="file" id="backgroundImg" name="backgroundImg" accept=".gif,.jpeg,.jpg,.png" />
				    			<font>大小:1920*500px</font>
					    	</div>
					    </li>
		    			<#if (entity.screen)?default('') != ''>
					    <li><label style="width:150px">截图预览：</label>
							<div class="screenDiv" style="width:10, height=10">
								<#list (entity.screens)?if_exists as item>
				    			<a id="screen_a_${item_index}" href="#" onclick="javascript:window.open('${uploadPath! }${item!}');">
				    				<img id="screen${item_index}Img1" src="${uploadPath! }${item!}" />
				    				<@plugins.ImgStyle name="screen${item_index}Img1" width="150" height="150" />
				    			</a>
			    				<input type="hidden" name="entity.screens" id="screen_${item_index}" value="${item!}" />
			    				<input value="删除" type="button" id="screen_del_${item_index}" onclick="del_screen('${item_index!}')" />
				    			</#list>
					    	</div>
					    </li>
		    			</#if>
					    <li><label style="width:150px">截图上传${uploadPath! }：</label>
					    	<label>
						    	<div id="screenDiv"></div>
								<input type="button" value="添加上传截图" class="btn" onclick="add_inp()" />
								<font>大小:270p*360px</font>
							</label>
							
					    </li>
                    <#if (entity.mobileBanner)?default('') != ''>
                        <li><label style="width:150px">移动端banner预览：</label>
                            <div class="screenDiv" style="width:10, height=10">
                                <a href="#" onclick="javascript:window.open('${uploadPath! }${(entity.mobileBanner)!}');">
                                    <img id="mobileImage1" src="${uploadPath! }${(entity.mobileBanner)!}" />
                                    <@plugins.ImgStyle name="mobileImage1" width="150" height="150" />
                                </a>
                            </div>
                        </li>
                    </#if>
                        <li><label style="width:150px">移动端banner：</label>
                            <div class="screenDiv" style="width:10, height=10">
                                <input type="hidden" name="entity.mobileBanner" id="mobileBanner" value="${(entity.mobileBanner)!}">
                                <input type="file" id="mobileImage" name="mobileImage" accept=".gif,.jpeg,.jpg,.png" />
                                <font>大小:750*240px</font>
                            </div>
                        </li>



                         <li><label style="width:150px">游戏视频(video,img 标签)</label>
                             <textarea cols="5" rows="5" class="textinput" name="entity.resource" id="seo">${(entity.resource)! }</textarea>
                         </li>

		    			<#if (entity.qrcode)?default('') != ''>
					    <li><label style="width:150px">二维码预览：</label>
							<div class="screenDiv" style="width:10, height=10">
				    			<a href="#" onclick="javascript:window.open('${uploadPath! }${(entity.qrcode)!}');">
				    				<img id="qrcodeImg1" src="${uploadPath! }${(entity.qrcode)!}" />
				    				<@plugins.ImgStyle name="qrcodeImg1" width="150" height="150" />
				    			</a>
					    	</div>
					    </li>
					    <input type="hidden" name="entity.qrcode" value="${(entity.qrcode)}">
		    			</#if>
					    <li><label style="width:150px"><font color="red">*</font>状态：</label>
					    	<div class="vocation">
						    	<select class="select3" name="entity.status" id="state">
					    			<option value="1" <#if 1 == (entity.status)?default(1)>selected</#if>>上线</option>
					    			<option value="2" <#if 2 == (entity.status)?default(1)>selected</#if>>下线</option>
					    		</select>
						    </div>
					    </li>
					    <li><label style="width:150px">排序号：</label>
					    	<input value="${(entity.orderId)?default(0) }" id="orderId" name="entity.orderId" type="text" class="dfinput-345" maxlength=4 onkeyup="this.value=this.value.replace(/\D/g,'')"/>
					    </li>
					    <li><label style="width:150px">介绍：</label>
					    	<@plugins.Kindeditor name="entity.briefIntro" />
					    	<textarea id="briefIntro" name="entity.briefIntro" class="textinput" style="width:800px; height:300px"/>${(entity.briefIntro)! }</textarea>
						</li>
					    <li>
					    	<label style="width:150px">&nbsp;</label>
					    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
					    	<input name="" type="button" class="btn" value="返回" onclick="location.href='game.html?name=${name!}&startTime=${(startTime?date)!}&endTime=${(endTime?date)!}&pageIndex=${pageIndex! }'"/>
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
