<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>房间管理</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/baidu.css" rel="stylesheet">
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

<@plugins.msg />
<script>
	<#-- 保存 -->
	function save(){
		if($("#name").val() == ""){
			addFieldError("房间名称不能为空");
			return false;
		}
		if($("#gameId").val() == ""){
			addFieldError("游戏不能为空");
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
		    <li><a href="#">主播管理</a></li>
		    <li><a href="#">房间管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
		    <form action="room-hot-save.html" id="form1" method="post" enctype="multipart/form-data">
		    	<input type="hidden" id="id" name="roomHot.id" value="${roomHot.id!}" />
		    	<input type="hidden" name="roomHot.createTime" value="${roomHot.createTime!}" />
		    	<input type="hidden" name="pageIndex" value="${pageIndex!}" />
			    <div class="formtitle"><span class="sp">热门房间信息</span></div>
				    <ul class="forminfo">
				    	<li><label><font color="red">*</font>房间名称：</label>
				    		<@plugins.search text="rootName" value="roomId" url="room-hot-search.html" />
			        		<input type="text" id="rootName" name="roomHot.roomName" value="${roomHot.roomName!}" style="width:130px;" class="scinput-150" autocomplete="off">
					    	<input type="hidden" id="roomId" name="roomHot.roomId" value="${roomHot.roomId!}" />
					    </li>
					    <li><label>排序号：</label>
					    	<input value="${roomHot.orderId! }" id="orderId" name="roomHot.orderId" type="text" class="dfinput-345" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d.]/g,'')" maxlength=7/>
						</li>
					    <li>
					    	<label>&nbsp;</label>
					    	<input name="" type="button" class="btn" value="保存" onclick="save()"/>
					    	<input name="" type="button" class="btn" value="返回" onclick="location.href='room-hot.html?pageIndex=${pageIndex! }'"/>
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
