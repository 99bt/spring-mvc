<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>直播报表</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

<@plugins.msg />

</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">活动管理 </a></li>
		    <li><a href="#">用户抽奖</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;height:500px" id="contentDiv">
			<form action="user.html" method="post" name="form1" id="form1">
		    	<ul class="seachform">
		        	<li>
		        		<label>活动名称：</label><input name="name" type="text" value="${name! }" class="scinput-150" />
		        	</li>
		        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询" onclick="form1.submit();"/></li>
		        </ul>
			    <table class="tablelist">
			    	<thead>
				    	<tr>
				    		<th>序号</th>
					        <th>活动名称</th>
					        <th>开始时间</th>
					        <th>状态</th>
					        <th>创建时间</th>
					        <th>操作</th>
				        </tr>
			        </thead>
			        <tbody>
				        <#list list?if_exists as item >
					        <tr title="${(item.name)! }">
					        	<td>${(item_index+1)+(pageIndex?number-1)*15 }</td>
						        <td>${(item.name)! }</td>
						        <td>${(item.startTime?datetime)! }</td>
						        <td>
						        	<#if item.status?default("1") == "1">
						        		<font style="color:green;">上线</font>
						        	<#elseif item.status?default("1") == "0">
						        		<font style="color:red;">下线</font>
						        	</#if>
						        </td>
						        <td>${(item.createTime?datetime)! }</td>
						        <td>
						        	<a href="activity-details.html?currentIndex=${pageIndex! }&entity.itemId=${(item.id)! }&name=${name! }&entity.itemName=${(item.name)! }" class="tablelink">查看</a> 
								</td>
					        </tr>
				        </#list>
			        </tbody>
			    </table>
		    </form>
		    <div class="pagin">
		        <@plugins.page action="user.html?name=${name! }&pageIndex=" />
		    </div>
		</div>
    </div>
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>