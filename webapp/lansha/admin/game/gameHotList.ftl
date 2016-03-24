<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${staticPath! }/static/css/admin_style.css${staticVersion! }" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticPath! }/static/js/jquery-1.7.1.min.js${staticVersion! }"></script>
<script type="text/javascript" src="${staticPath! }/static/js/calendar/WdatePicker.js${staticVersion! }"></script>

<@plugins.msg />
<script type="text/javascript">
<#-- 删除 -->
function doDelete(id){
	if(confirm("确定需要删除？")){
		location.href='game-hot-remove.html?pageIndex=${pageIndex! }&id=' + id;
	}
}
//推荐
function updateOrderId(id){
	var orderId = $("#orderId_"+id).val();
	var orderIdOld = $("#orderIdOld_"+id);
	if(orderId != "" && orderId != orderIdOld.val()){
		<#-- ajax 修改 -->
		$.ajax({ url: "game-hot-updateOrder.html?id="+id+"&orderId="+orderId});
		orderIdOld.val(orderId);
	}
}
</script>
</head>
<body>
	<div class="place">
    	<span>位置：</span>
	    <ul class="placeul">
		    <li><a href="#">首页</a></li>
		    <li><a href="#">游戏管理</a></li>
		    <li><a href="#">游戏维护</a></li>
	    </ul>
    </div>
    <div class="formbody">
	    <div class="itab">
		  	<ul> 
			    <li><a href="game.html" >游戏</a></li>
			    <li><a href="#tab1" class="selected">推荐游戏</a></li> 
		  	</ul>
	    </div>
	    <div class="rightinfo"  style="overflow:auto;height:500px;" id="contentDiv">
			<form id="form1" action="game.html" method="post">
			    <table class="tablelist">
			    	<thead>
				    	<tr>
					        <th>游戏名称</th>
					        <th>排序号</th>
					        <th>推荐时间</th>
					        <th>操作</th>
				        </tr>
			        </thead>
			        <tbody>
			        <#list list?if_exists as item>
			        <tr>
				        <td>${item.gameName! }</td>
				        <td>
				        	<input type="text" id="orderId_${item.id!}" value="${item.orderId!}" onblur="updateOrderId('${item.id! }')" class="dfinput-345"/>
				        	<input type="hidden" id="orderIdOld_${item.id!}" value="${item.orderId!}"/>
				        </td>
				        <td>${((item.createTime)?datetime)! }</td>
				        <td>
							<a href="javascript:doDelete('${item.id! }')" class="tablelink"> 删除</a>
						</td>
			        </tr>
			        </#list>
			        </tbody>
			    </table>
		    </form>
	    
		    <div class="pagin">
		        <@plugins.page action="game-hot.html?pageIndex=" />
		    </div>
		</div>
    </div>
    <script type="text/javascript">
	    jQuery("#contentDiv").height(jQuery("#mainFrame", window.parent.parent.document).height() - 120);
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>