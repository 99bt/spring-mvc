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
		    <li><a href="#">统计报表</a></li>
		    <li><a href="#">意见反馈报表</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;height:500px" id="contentDiv">
			<form action="suggestion.html" method="post" name="form1" id="form1">
		    	<ul class="seachform">
		        	<li>
		        		<label>标题：</label><input name="title" type="text" value="${title! }" class="scinput-150" />
		        	</li>
		        	<li>
		        		<label>内容：</label><input name="name" type="text" value="${name! }" class="scinput-150" />
		        	</li>
		        	<li>
		        		<label>类型：</label>
		        		<select class="select3" name="type" id="type" class="scinput-150" style="height:30px;border:1px solid #a7b5bc" >
				    		<option value="">--全部类型--</option>
			        		<#list listSuggestionType?if_exists as item>
			        			<option value='${item.thisId! }' <#if type?default('') == item.thisId >selected</#if> >${item.content! }</option>
			        		</#list>
			    		</select>
		        	</li>
		        	<li>
		        		<label>开始日期：</label><input type="text" name="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
		        	</li>
		        	<li>
		        		<label>结束日期：</label><input type="text" name="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
		        	</li>
		        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询" onclick="form1.submit();"/></li>
		        	<!-- <input name="" type="button" class="scbtn" value="导出" onclick="exportData()"/> -->
		        </ul>
			    <table class="tablelist">
			    	<thead>
				    	<tr>
					        <th>用户名称</th>
					        <th>用户IP</th>
					        <th>意见类型</th>
					        <th>意见标题</th>
					        <th>意见内容</th>
					        <th>反馈时间</th>
					        <th>操作</th>
				        </tr>
			        </thead>
			        <tbody>
				        <#list list?if_exists as item>
					        <tr>
						        <td>${item.userName! }</td>
						        <td>${item.ip! }</td>
						        <td>${item.type! }</td>
						        <td title="${item.title! }"><@plugins.cutOff cutStr="${item.title! }" cutLen="100" /></td>
						        <td title="${item.content! }"> <@plugins.cutOff cutStr="${item.content! }" cutLen="50" /></td>
						        <td>${(item.createTime?datetime)! }</td>
						        <td>
						        	<a href="suggestion-detail.html?pageIndex=${pageIndex! }&name=${name! }&title=${title! }&type=${type! }&id=${item.id! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }" class="tablelink">详情</a>
						        </td>
					        </tr>
				        </#list>
			        </tbody>
			    </table>
		    </form>
		    <div class="pagin">
		        <@plugins.page action="suggestion.html?type=${type! }&title=${title !}&name=${name! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&pageIndex=" />
		    </div>
		</div>
    </div>
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>