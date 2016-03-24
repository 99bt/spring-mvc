<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>礼物管理</title>
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
	}
	
	var status = $("#status").val();
	if(status == ""){
		addFieldError("请选择状态");
		return false;
	}
	
	var orderId = $("#orderId").val();
	if(orderId == ""){
		addFieldError("排序号不能为空");
		return false;
	}else if(isNaN(orderId)){
		addFieldError("排序号只能为数字");
		return false;
	}
	
	var bi = $("#bi").val();
	if(bi == ""){
		addFieldError("币值不能为空");
		return false;
	}else if(isNaN(bi)){
		addFieldError("币值只能为数字");
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
		    <li><a href="#">游戏管理</a></li>
		    <li><a href="#">礼物管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
    <form action="gift-save.html" id="form1" method="post" enctype="multipart/form-data">
    	<input type="hidden" id="id" name="entity.id" value="${(entity.id)!}" />
    	<input type="hidden" id="names" name="name" value="${(name)!}" />
	    <div class="formtitle"><span class="sp">基本信息</span></div>
		    <ul class="forminfo">
			    <li><label><font color="red">*</font>名称：</label><input value="${(entity.name)! }" id="name" name="entity.name" type="text" class="dfinput-345" /></li>
			    <li><label><font color="red">*</font>币值：</label><input value="${(entity.bi)! }" id="bi" name="entity.bi" type="text" class="dfinput-345" /></li>
			    <li><label><font color="red">*</font>状态：</label>
			    	<div class="vocation">
				    	<select class="select3" name="entity.status" id="status">
				    		<option value="">--请选择--</option>
				            <option value='0' <#if (entity.status)?default('')== '0'>selected</#if> >停用</option>
				            <option value='1' <#if (entity.status)?default('')== '1'>selected</#if> >启用</option>
			    		</select>
				    </div>
			    </li>
			    <li><label><font color="red">*</font>排序号：</label><input value="${(entity.orderid)! }" id="orderId" name="entity.orderid" type="text" class="dfinput-345" /></li>
			   	<li>
			   		<label>图标：</label>
                    <img class="fl file-img" id="headerFace" src="<#if (entity.img)?default("") == "">${staticPath! }/static/lansha/upload/default.png${staticVersion! }<#else>${uploadPath! }${(entity.img)!}</#if>" onerror="this.src='${staticPath! }/static/lansha/upload/default.png${staticVersion! }'" alt="上传图片">
                    <label><input class="fl file-input" id="isupload" type="button" value="上传图标"></label>
                    <input type="hidden" name="entity.img" id="headerFaceVal" value="${(entity.img)!}" autocomplete="off" />
			   	</li>
			    <li>
			    	<label>&nbsp;</label>
			    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
			    	<input name="" type="button" class="btn" value="返回" onclick="location.href='gift.html?name=${(name)! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=${pageIndex! }'"/>
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