<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>直播报表</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>

<@plugins.msg />
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">统计报表</a></li>
		    <li><a href="#">直播报表</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="rightinfo" style="overflow:auto;height:500px" id="contentDiv">
			<form action="live.html" method="post" name="form1" id="form1">
		    	<ul class="seachform">
		        	<li>
		        		<label>房间ID：</label><input name="roomId" type="text" value="${roomId! }" class="scinput-150" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  autocomplete="off" />
		        	</li>
		        	<li>
		        		<label>签约：</label>
		        		<div class="vocation" style="width:150px;">
		        		<select class="select3" name="examine" >
                            <option value="3" <#if examine==3>selected</#if>>全部</option>
                            <option value="1" <#if examine==1>selected</#if> >是</option>
                            <option value="0" <#if examine==0>selected</#if> >否</option>
			        	</select>
		        		</div>
		        	</li>
		        	<li>
		        		<label>开始日期：</label><input type="text" name="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
		        	</li>
		        	<li>
		        		<label>结束日期：</label><input type="text" name="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" readonly/>
		        	</li>
		        	<li><label>开始时间：</label>
		        		<div class="vocation" style="width:150px;">
			        		<select class="select3" name="startHour" id="startHour">
				        		<#list listHour as h>
				        			<option value='${h}' <#if startHour?default('')== h>selected</#if> >${h}</option>
				        		</#list>
			        		</select>
		        		</div>
		        	</li>
		        	<li><label>结束时间：</label>
		        		<div class="vocation" style="width:150px;">
			        		<select class="select3" name="endHour" id="endHour">
				        		<#list listHour as h>
				        			<option value='${h}' <#if endHour?default('')== h>selected</#if> >${h}</option>
				        		</#list>
			        		</select>
		        		</div>
		        	</li>
                    <input type="hidden" name="sort" id="sort" value="${one!}"/>
		        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="查询" onclick="form1.submit();"/></li>
		        	<input name="" type="button" class="scbtn" value="导出" onclick="exportData()"/>
		        </ul>
			    <table class="tablelist">
			    	<thead>
				    	<tr>
					        <th>日期</th>
					        <th>房间ID</th>
					        <th>签约</th>
					        <th>主播名称</th>
					        <th>游戏名称</th>
					        <th>日票</th>
                            <th>直播时长</th>
                            <#--<img src="${staticPath! }/static/images/up.png" class="pointer" id="up_list_one" title="向上"/></th>-->
					        <#--<th>虾米<img src="${staticPath! }/static/images/up.png" class="pointer" id="up_list_two" title="向上"/> </th>-->
					        <#--<th>鲜花<img src="${staticPath! }/static/images/up.png" class="pointer" id="up_list_three" title="向上"/></th>-->
					        <#--<th>关注<img src="${staticPath! }/static/images/up.png" class="pointer" id="up_list_four" title="向上"/></th>-->
					        <#--<th>直播时长<img src="${staticPath! }/static/images/up.png" class="pointer" id="up_list_five" title="向上"/></th>-->
					        <#--<th>有效天数<img src="${staticPath! }/static/images/up.png" class="pointer" id="up_list_six" title="向上"/></th>-->
					        <th>操作</th>
				        </tr>
			        </thead>
			        <tbody>
				        <#list list?if_exists as item>
                        <tr title="${item.roomName! }">
                            <td>${item.timeFrame!}</td>
                            <td>${item.idInt! }</td>
                            <td><#if item.sign=="0">
                            		<span style="color:red">未签约</span>
                                <#else>
                                	已签约
                            	</#if>
                            </td>
                            <td>${item.nickname! }</td>
                            <td>${item.gameName! }</td>
                            <td>${item.number!}</td>
                            <#--<td>${item.xiamiNumber! }</td>-->
                            <#--<td>${item.flowerNubmer! }</td>-->
                            <#--<td>${item.relationNumber! }</td>-->
                            <td>${item.playTime! }</td>
                            <#--<td>${item.vaildDays! }</td>-->
						        <td>
						        	<a href="live-detail.html?pageIndex=${pageIndex! }&roomId=${roomId!}&id=${item.roomId! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&startHour=${startHour! }&endHour=${endHour! }&examine=${examine!}" class="tablelink">详情</a>
								</td>
					        </tr>
				        </#list>
			        </tbody>
			    </table>
		    </form>
		</div>
    </div>
    <script type="text/javascript">
    	jQuery("#contentDiv").width(jQuery("#mainFrame", window.parent.parent.document).width() - 45).height(jQuery("#mainFrame", window.parent.parent.document).height() - 65);
		$('.tablelist tbody tr:odd').addClass('odd');
		$(document).ready(function() {
			$(".select3").uedSelect({
				width : 150
			});

            $("#up_list_one").click(function(){
                $("#sort").val("one")
                form1.submit();
            });
            $("#up_list_two").click(function(){
                $("#sort").val("two")
                form1.submit();
            });
            $("#up_list_three").click(function(){
                $("#sort").val("three")
                form1.submit();
            });
            $("#up_list_four").click(function(){
                $("#sort").val("four")
                form1.submit();
            });
            $("#up_list_five").click(function(){
                $("#sort").val("five")
                form1.submit();
            });
            $("#up_list_six").click(function(){
                $("#sort").val("six");
                form1.submit();
            });

        });
		function exportData(){
			location.href='live-export.html?roomId=${roomId! }&startTime=${(startTime?date)! }&endTime=${(endTime?date)! }&startHour=${startHour! }&endHour=${endHour! }&examine=${examine!}';
		}
	</script>
</body>
</html>