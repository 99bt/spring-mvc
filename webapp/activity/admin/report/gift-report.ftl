<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head >
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>礼物报表</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<link href="${staticPath! }/static/css/select.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/select-ui.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/highcharts.js${staticVersion! }"></script>

<@plugins.msg />
<script type="text/javascript">
$(document).ready(function() {
	$(".select3").uedSelect({
		width : 200
	});
});
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">报表统计</a></li>
		    <li><a href="#">礼物报表</a></li>
	    </ul>
    </div>
    <div class="formbody">
    	<div class="itab">
		  	<ul> 
		  		<li><a href="#tab1" class="selected">奖品发放</a></li> 
			    <li><a href="giftReport-stock.html?entity.itemId=${(entity.itemId)! }&name=${name! }&pageIndex=${pageIndex! }" >礼物库存</a></li>			    
		  	</ul>
	    </div>
    	<div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="giftReport-view.html" method="post" name="form1" id="form1">
	    	<ul class="seachform">	 
	    		<li>
	        		<label>奖品名称：</label>
	        		<div class="vocation">
		        	<select class="select3" name='entity.name' id="name">
				    	<#list list?if_exists as item>		        			        	
				    		<option value="${item.id! }" <#if (item.orderId!)?default(0) == 3>selected</#if>>${item.name! }</option>
				    	</#list>
		    		</select>
		    		</div>
	        	</li>       	
	        	<li><label>中奖日期：</label>
		        	<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" style="width:90px" readonly/>
		        	-<input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" style="width:90px" readonly/>
		        	<@plugins.dateUtil url="giftReport-view.html?entity.itemId=${(entity.itemId)! }&pageIndex=${pageIndex! }" />
	        	</li>
	        	<li><input name="" type="button" onclick="getData()" style="width:60px;" class="scbtn" value="查询"/></li>
	        	<li><label>&nbsp;</label><input name="" type="button" class="scbtn" value="返回活动" onclick="location.href='giftReport.html?name=${name! }&pageIndex=${pageIndex! }'"/></li>
	        </ul>
	        <div id="container" style="height:450px"></div>
	    </form>
		</div>
    
    <script type="text/javascript">
    	jQuery("#contentDiv").height(jQuery("#mainFrame", window.parent.parent.document).height() - 70);
		
		$('.tablelist tbody tr:odd').addClass('odd');
		
		var chart;		
		//获取数据
		function getData(){
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var giftId = $("#name").val(); 
			$.ajax({
		    type: 'POST',
		    dataType: 'JSON',
		    url: 'giftReport-statistics.html?entity.itemId=${(entity.itemId)!}&entity.id='+giftId+'&startTime=' + startTime + '&endTime='+endTime,
		    success : function(result){
		      <#-- 构建曲线 -->
		      $('#container').highcharts({
		        chart:{
		          type: 'column'
		        },
		        colors: ['#E10601','#058DC7', '#50B432',  '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
		        credits: {
		           enabled: false
		        },
		        <#-- 主标题 -->
		        title:{
		          text: result["giftName"],
		          <#-- center -->
		          x: -20
		        },
		        <#-- 横坐标 -->  
		        xAxis: {
		        	<#-- 动态解析 -->
		          categories: result["listXdata"]
		        },
		        <#-- 纵坐标 -->
		        yAxis: {
		          min: 0,
		          allowDecimals:false,
		          title: {
		            text: '单位(件)'
		          },
		          labels: { overflow: 'justify' }
		        },
		        <#-- 数据点的提示框 -->
		        <#-- formatter格式化函数 -->
		        tooltip: {
		          	headerFormat: '<span style="font-size:10px">{point.key}</span><table>', 
		          	pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' + '<td style="padding:0"><b>{point.y}</b></td></tr>', 
		          	footerFormat: '</table>',
		          	shared: true, 
		          	useHTML: true
		        },
		        plotOptions: { 
		        	series: {
		        			 dataLabels: { 
		        			 	enabled: true,
		        			 	format: '{y}'
		        			 }
		        		 }
		        }, 
		        legend: {
		          layout: 'vertical',
		          align: 'right',
		          verticalAlign: 'top',
		          x: -10,
		          y: 100,
		          borderWidth: 0
		        },
		        <#-- 动态解析 -->
		        series:result["listYdata"]
		      });      
		    }  
		  });
		}
		
		$(function(){
		    getData();
		});
		
	</script>
</body>
</html>
