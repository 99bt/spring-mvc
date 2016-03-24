<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>意见反馈信息</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>

<@plugins.msg />
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">统计报表</a></li>
		    <li><a href="#">意见反馈报表</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;" id="contentDiv">
	    	<div class="formtitle"><span class="sp">意见反馈详情</span></div>
			    <ul class="forminfo">
			    	<li><label>用户名称：</label>
		        		<input value="${entity.userName! }" type="text" class="scinput-150" />
				    </li>
				    <li><label>用户IP：</label>
				    	<input value="${entity.ip! }" type="text" class="dfinput-345" />
					</li>
				    <li><label>意见类型：</label>
				    	<input value="${entity.type! }" type="text" class="dfinput-345" />
				    </li>
				    <li><label>意见标题：</label>
			            <textarea class="textinput" style="width:320px;height:62px;">${entity.title! }</textarea>
					</li>
					<li><label>意见内容：</label>
						<textarea class="textinput" style="width:320px;height:62px;">${entity.content! }</textarea>
					</li>
					<li><label>意见内容：</label>
						<input value="${(entity.createTime?datetime)! }" type="text" class="dfinput-345" />
					</li>
				    <li>
				    	<label>&nbsp;</label>
				    	<input name="" type="button" class="btn" value="返回" onclick="location.href='suggestion.html?type=${type! }&title=${title !}&name=${name! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=${pageIndex! }'"/>
				    </li>
			    </ul>
		    </div>
		</div>
	</div>
</body>
<script>
jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
</script>
</html>
