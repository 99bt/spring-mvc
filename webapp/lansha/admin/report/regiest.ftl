<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head >
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>联盟总览</title>
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
		width : 150
	});
});
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">统计报表</a></li>
		    <li><a href="#">注册量统计</a></li>
	    </ul>
    </div>
    <#--<div class="formbody">
	    <div id="usual1" class="usual"> 
		    <div class="itab">
			  	<ul> 
				    <li><a href="#tab1" class="selected">tab1</a></li>
				    <li><a href="dataReport-total.html">tab2</a></li>
			  	</ul>
	    	</div>
	    </div>
    </div>-->
    <div class="formbody">
    <div class="rightinfo"  style="overflow:auto;height:500px" id="contentDiv">
		<form action="regiest.html" method="post" name="form1" id="form1">
	    	<ul class="seachform">
	        	<li style="float:right;"><input name="" type="button" onclick="getData()" style="width:60px;" class="scbtn" value="查询"/></li>
	        	<li style="float:right"><label>注册日期：</label>
		        	<input type="text" name="startTime" id="startTime" value="${(startTime?date)! }" onclick="WdatePicker();" class="scinput-150" style="width:90px" readonly/>
		        	-<input type="text" name="endTime" id="endTime" value="${(endTime?date)! }" onclick="WdatePicker();" class="scinput-150" style="width:90px" readonly/>
		        	<@plugins.dateUtil url="regiest.html" />
	        	</li>
	        	<li id="regCounts">共计用户：${regCounts! }</li>
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
			if(startTime == ""){
				alert("请选择开始日期");
				return;
			}
			if(endTime == ""){
				alert("请选择结束日期");
				return;
			}
			$.ajax({
		    type: 'POST',
		    dataType: 'JSON',
		    url: 'regiest-statistics.html?startTime=' + startTime + '&endTime='+endTime,
		    success : function(result){
		      $("#regCounts").text("共计用户：" + result.regCounts);
		      <#-- 构建曲线 -->
		      $('#container').highcharts({
		        chart:{
		          type: 'line'
		        },
		        credits: {
		           enabled: false
		        },
		        <#-- 主标题 -->
		        title:{
		          text: '注册用户量线形图',
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
		            text: '单位(个)'
		          },
		          <#-- 标线属性 -->
		          plotLines: [{
		            value: 0,
		            width: 1,
		            color: '#808080'
		          }]
		        },
		        <#-- 数据点的提示框 -->
		        <#-- formatter格式化函数 -->
		        tooltip: {
		          	crosshairs: [{width:1,color:'#C0C0C0'}],
            		shared: true
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
