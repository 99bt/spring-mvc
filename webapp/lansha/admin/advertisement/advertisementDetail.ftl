<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>直播间广告</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/validate.js${staticVersion! }"></script>

<style>
	.fl { float:left; }
	.file-input {width: 78px;height: 28px;border: 1px solid #b0b0b0;text-align: center;color: #b0b0b0;font: 12px/28px "Microsoft YaHei";background: #fff;margin-right: 15px;cursor: pointer;}
	.file-img {width:100px;height:100px;margin-left:15px;margin-right:15px;margin-bottom:20px; }
</style>
<@plugins.msg />
<@plugins.ImgStyle name="img1" width="150" height="150" />
<script>
<#-- 保存 -->
function save(){
	var name = $("#name").val();
	if(name == ""){
		addFieldError("名称不能为空");
		return false;
	}else if(name.length>20){
		addFieldError("名称字符长度不能长于20字符");
		return false;
	}
	var link = $("#link").val();
	if(link == ""){
		addFieldError("链接地址不能为空");
		return false;
	}
	
	var status = $("#status").val();
	if(status == ""){
		addFieldError("请选择状态");
		return false;
	}
	var type = $("#type").val();
	if(type == ""){
		addFieldError("请选择类型");
		return false;
	}
	
	var rate = $("#rate").val();
	if(rate == ""){
		addFieldError("权重不能为空");
		return false;
	}else if(isNaN(rate)){
		addFieldError("权重只能为数字");
		return false;
	}else if(rate <0 || rate>10){
		addFieldError("权重值0~10之间");
		return false;
	}
	var headerFaceVal = $("#headerFaceVal").val();
	if(headerFaceVal == ""){
		addFieldError("请上传图片");
		return false;
	}
	
	var remark = $("#remark").val();
	if(remark.length>50){
		addFieldError("备注字符长度不能长于50字符");
		return false;
	}
	$("#form1").submit();	
}

</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		   <li><a href="#">主播管理</a></li>
		    <li><a href="#">直播间广告</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
    <form action="room-advertisement-save.html" id="form1" method="post" enctype="multipart/form-data">
    	<input type="hidden" id="id" name="entity.id" value="${(entity.id)!}" />
    	<input type="hidden" id="names" name="name" value="${(name)!}" />
	    <div class="formtitle"><span class="sp">基本信息</span></div>
		    <ul class="forminfo">
			    <li><label><font color="red">*</font>名称：</label><input value="${(entity.name)! }" id="name" name="entity.name" type="text" class="dfinput-345" /></li>
			    <li><label><font color="red">*</font>链接地址：</label><input value="${(entity.link)! }" id="link" name="entity.link" type="text" class="dfinput-345" /></li>
			    <li><label><font color="red">*</font>权重：</label><input value="${(entity.rate)! }" id="rate" name="entity.rate" type="text" class="dfinput-345" /></li>
			    <li><label><font color="red">*</font>状态：</label>
			    	<div class="vocation">
				    	<select class="select3" name="entity.status" id="status">
				    		<option value="">--请选择--</option>
				            <option value='0' <#if (entity.status)?default('')== '0'>selected</#if> >下线</option>
				            <option value='1' <#if (entity.status)?default('')== '1'>selected</#if> >在线</option>
			    		</select>
				    </div>
			    </li>
			    <li><label><font color="red">*</font>类型：</label>
			    	<div class="vocation">
				    	<select class="select3" name="entity.type" id="type">
				    		<option value="">--请选择--</option>
				            <option value='1' <#if (entity.type)?default('')== '1'>selected</#if> >房间广告</option>
			    		</select>
				    </div>
			    </li>
			   	<li>
			   		<label><font color="red">*</font>图片：</label>
                    <img class="fl file-img" id="headerFace" src="<#if (entity.img)?default("") == "">${staticPath! }/static/lansha/upload/default.png${staticVersion! }<#else>${uploadPath! }${(entity.img)!}</#if>" onerror="this.src='${staticPath! }/static/lansha/upload/default.png${staticVersion! }'" alt="上传图片">
                    <label><input class="fl file-input" id="isupload" type="button" value="上传图标"></label>
                    <input type="hidden" name="entity.img" id="headerFaceVal" value="${(entity.img)!}" autocomplete="off" />
			   	</li>
			   	  <li><label>备注：</label><input value="${(entity.remark)! }" id="remark" name="entity.remark" type="text" class="dfinput-345" /></li>
			    <li>
			    	<label>&nbsp;</label>
			    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
			    	<input name="" type="button" class="btn" value="返回" onclick="location.href='advertisement.html?name=${(name)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=${pageIndex! }'"/>
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
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.xBox.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jq_input.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.md5.min.js"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/upload/plupload.full.min.js"></script>
<script type="text/javascript">
    var PIC_PATH = '../';
    //调用用户注册
    $(function(){
        var apiurl = '${contextPath}/uploadImg.html';
        var uploader = new plupload.Uploader({
            runtimes : 'html5,flash,html4',
            browse_button : 'isupload', //选择图片按钮ID
            dragdrop: false,
            auto_start: true,
            multi_selection: false,
            url : apiurl,
            flash_swf_url : './upload/Moxie.swf',
            filters: {
                max_file_size : '10m',
                mime_types: [
                    {title : "Image files", extensions : "jpg,png,jpeg"}
                ]
            },
            init: {
                PostInit: function() {
                },
                //添加文件
                FilesAdded: function(up, files) {
                    plupload.each(files, function(file) {
                        if (!checkFileType(file)) {
                            up.removeFile(file);
                            $.xbox.tips("图片格式错误");
                        }else{
                            uploader.start();
                            return false;
                        }
                    });
                },
                //上传进度
                UploadProgress: function(up, file) {},
                //上传完成
                FileUploaded: function(up, file, info) {
                    var fileInfo = $.parseJSON(info.response);
                    if(fileInfo.status==0)
                    {
                        $.xbox.tips(fileInfo.failed);
                        return false;
                    }
                    $('#headerFace').attr('src',fileInfo.url);
                    $('#headerFaceVal').val(fileInfo.url);
                    $.xbox.tips("上传成功");  
                },
                Error: function(up, err) {
                    $.xbox.tips("上传失败");
                }
            }
        });
        uploader.init();

	    function checkFileType(file) {
	        var fileName = file.name;
	        var fileType = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
	        var isImage = true;
	        if (fileType != '.jpg' && fileType != '.png' && fileType != '.jpeg') {
	            isImage = false;
	        }
	        return isImage;
	    }
            
    });
</script>
</html>