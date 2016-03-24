<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript">
function doSave(){
	var from1 = document.getElementById("form1");
	form1.submit();
}
</script>
<@plugins.msg />
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">管理信息</a></li>
		    <li><a href="#">系统管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div id="usual1" class="usual"> 
	    <div class="itab">
	  	<ul> 
		    <li><a href="#tab1" class="selected">参数管理</a></li> 
		    <li><a href="system-call.html">接口调用</a></li> 
		    <li><a href="system-mcode.html">微代码</a></li> 
		    <li><a href="system-database.html">数据库连接池</a></li> 
	  	</ul>
    </div> 
    <div class="rightinfo" style="overflow:auto;height:500px;"  id="contentDiv">
    <form id="form1" action="system-save.html" method="post">
	    <input type="hidden" name="entity.id" value="${entity.id! }" />
	    <input type="hidden" name="pageIndex" value="${pageIndex! }" />
	    <input type="hidden" name="typeId" value="${typeId! }" />
	    
	    <div class="formbody">
	    <div class="formtitle"><span class="sp">基本信息</span></div>
		    <ul class="forminfo">
			    <li><label>参数名称：</label><input name="entity.name" value="${entity.name! }" readonly type="text" class="dfinput-345" /><i>自动生成，不可维护</i></li>
			    <li><label>排序号：</label><input name="entity.orderid" value="${entity.orderid! }" readonly type="text" class="dfinput-345" /><i>自动生成，不可维护</i></li>
			    <li><label>内容：</label>
			    	<textarea name="entity.nowvalue" id="nowvalue" class="textinput" style="width:700px; height:200px">${entity.nowvalue! }</textarea>
			    </li>
			    <li><label>说明：</label><textarea name="entity.description" class="textinput" style="width:600px; height:80px">${entity.description! }</textarea></li>
			    
			    <li>
			    	<label>&nbsp;</label>
			    	<input name="" type="button" class="btn" value="确认保存" onclick="doSave()"/> 
			    	<input name="" type="button" class="btn" value="返回" onclick="location.href='system.html?pageIndex=${pageIndex! }&typeId=${typeId! }'"/>
			    </li>
		    </ul>
	    </div>
    	</div>
	 </form>
	 </div>
	 
	<script type="text/javascript">
	jQuery("#contentDiv").height(jQuery("#mainFrame", window.parent.parent.document).height() - 110);
	</script>
</body>
</html>
