<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<@plugins.msg />
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">管理信息</a></li>
		    <li><a href="#">日志管理</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div id="usual1" class="usual"> 
	    <div class="itab">
	  	<ul> 
		    <li><a href="log.html">系统日志</a></li>
		    <li><a href="log-admin.html">后台登录日志</a></li>
		    <li><a href="log-mt.html">短信日志</a></li> 
		    <li><a href="#" class="selected">邮件日志</a></li>
		    <li><a href="log-user.html">前台登录日志</a></li>
	  	</ul>
	    </div>
	    </div>
    </div>
    
	<form id="form1" action="log-email.html" method="post">
    <div class="tools">
    	<ul class="toolbar">
        	发送时间：<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
        	<input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
        	<input name="" type="submit" class="scbtn" value="查询"/>
        </ul>
    </div>
	</form>
	    
    <div class="rightinfo" style="overflow:auto;height:500px;" id="contentDiv">
		<form id="form2" action="" method="post">
			<input type="hidden" name="startTime" value="${(startTime?date)! }" />
			<input type="hidden" name="endTime" value="${(endTime?date)! }" />
		    <table class="tablelist">
		    	<thead>
			    	<tr>
				        <th width="10%">邮箱</th>
				        <th width="10%">发送时间</th>
				        <th width="10%">标题</th>
				        <th>发送内容</th>
				        <th width="10%">状态</th>
				        <th width="10%">错误消息</th>
			        </tr>
		        </thead>
		        <tbody>
		        <#list list?if_exists as item>
		        <tr>
			        <td>${item.email! }</td>
			        <td>${item.time! }</td>
			        <td>${item.title! }</td>
			        <td>${item.content! }</td>
			        <td><#if item.status! == "0">未验证<#elseif item.status! == "1"><span style="color:red">已验证</span><#else><span style="color:red">发送失败
			        	<a href="javascript:void(0)" onclick="sendEmail('${item.id! }')">| 重新发送</a></span></#if></td>
			        <td>${item.error! }</td>
		        </tr>
		        </#list>
		        </tbody>
		    </table>
	    </form>
    
	    <div class="pagin">
	        <@plugins.page action="log-email.html?startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=" />
	    </div>
	</div>
    
    <script type="text/javascript">
    	function sendEmail(id){
    		if(!confirm("确定需要重新发送？")){
    			return;
    		}
    		$.ajax({
				url: 'log-email-send.html',
				data:{
					id: id
				},
			    type:'post',
			    cache:false,
			    success:function(data) {
			    	if(data == "success"){
			     		addFieldError("发送成功");
			    	}else{
				     	addFieldError(data);
			    	}
			     },
			     error: function(data){
			     	addFieldError("连接错误");
			        return false;
			     }
			});
    	}
    	
	    jQuery("#contentDiv").height(jQuery("#mainFrame", window.parent.parent.document).height() - 170);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>