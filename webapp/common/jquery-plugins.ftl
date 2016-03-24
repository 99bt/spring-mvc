<#-- 提示信息 -->
<#macro msg>
<script>
		<#if action.hasActionErrors()>
			var htmlErrContent = "";
		  	<#list actionErrors as item>
		  		<#if item?exists>
					htmlErrContent+="${item?j_string}";
				</#if>
			</#list>
			addFieldError(htmlErrContent);
		</#if>
		<#if action.hasActionMessages()>
			var htmlContent = "";
		  	<#list actionMessages as item>
		  		<#if item?exists>
				htmlContent+="${item?j_string}";
				</#if>
			</#list>
			addFieldError(htmlContent);
		</#if>
		<#if action.hasFieldErrors()>
			<#list fieldErrors.keySet() as item>
				<#if item?exists>
					var htmlContent = "";
					<#list fieldErrors.get(item) as list>
						<#if list?exists>
						htmlContent+="${list?j_string}";
						</#if>
					</#list>
					addFieldError(htmlContent, document.getElementById("${item! }"));
				</#if>
			</#list>
		</#if>
		
		function addFieldError(msg, elem){
			if(typeof Effects == 'undefined'){
				alert(msg);
			}else{
				Effects.messageBox(msg);
			}
		}
</script>
</#macro>

<#-- 分页 -->
<#macro page action="" dele="">
	<script type="text/javascript" src="${staticPath! }/static/page/Page.js${staticVersion! }"></script>
	<form id="page_form" name="page_form" method="post">
	<div class="message">
	<#if dele?default("") == "true">
		<label><input id="allIds" type="checkbox" value="" onclick="checkAll();" style='vertical-align:middle;'/> 全选</label>
    	<input name="" type="button" class="scbtn" value="删除" onclick='deleteAll()' style="width: 50px; height:25px; margin-left: 10px"/>
    </#if>
	 	共&nbsp;<i class="blue">${pageDto.totalNum! }</i>&nbsp;条记录，当前显示第&nbsp;<i class="blue">${pageDto.currentPage! }&nbsp;</i>页</div>
	<ul class="paginList" id="page"></ul>
	<#--<input type="hidden" name="pageIndex" value="${page.currentPage! }"/>-->
	</form>
	<script language="javascript">
		var pages = new Page(${pageDto.currentPage! }, ${pageDto.totalNum! }, ${pageDto.rowNum?default(20)}, 5, 2);
		pages.run("page", "${action}", pages);
	</script>
</#macro>

<#-- 文本拷贝 -->
<#macro copy>
function copyToClipboard(txt) {
    if (window.clipboardData) {
        window.clipboardData.clearData();
        window.clipboardData.setData("Text", txt);
    } else if (navigator.userAgent.indexOf("Opera") != -1) {
        window.location = txt;
    } else if (window.netscape) {
        try {
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
        } catch (e) {
            addFieldError("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
        }
        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
        if (!clip) return;
        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
        if (!trans) return;
        trans.addDataFlavor('text/unicode');
        var str = new Object();
        var len = new Object();
        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
        var copytext = txt;
        str.data = copytext;
        trans.setTransferData("text/unicode", str, copytext.length * 2);
        var clipid = Components.interfaces.nsIClipboard;
        if (!clip) return false;
        clip.setData(trans, null, clipid.kGlobalClipboard);
    }else{
    	addFieldError("您的浏览器不支持剪帖板功能！");
    	return;
    }
	addFieldError("已经成功复制到剪帖板上！");
}
</#macro>

<#-- 截取指定长度的字符串 -->
<#macro cutOff cutStr="" cutLen="10">
	<#if (cutStr?exists) && (cutStr?length gte (cutLen?number +1))>
		${cutStr?substring(0,cutLen?number)}...
	<#else>
		${cutStr}
	</#if>
</#macro>

<#-- 限宽限高css样式 -->
<#macro ImgStyle name='' width='100' height='100'>
<style>
	#${name}{ width:expression((this.width > ${width}) ? "${width}px" : true);height:expression((this.height > ${height}) ? "${height}px" : true); max-height:${height}px; max-width:${width}px; border:0;}
</style>
</#macro>

<#-- 提示 -->
<#macro title str="">placeholder="${str! }" title="${str! }"</#macro>


<#-- 上传 -->
<#macro upload id="1" img="" defImg="" style="" width="100" height="100">
	<@ImgStyle name="img_${id! }" width="${width}" height="${height}"/>
	<img src="${img! }" onerror="this.src='${defImg! }'" name="img_${id! }" onclick="fileId='${id! }';$('#file1').click()" id="img_${id! }" style="${style! }"/>
</#macro>

<#-- 多文件上传 -->
<#macro multiUpload filesize='100 MB' action='' fileTypes='*.*' description='所有文件' functionName="show">
<script type="text/javascript" src="${contextPath!}/static/js/swfupload/common/swfupload.js${staticVersion! }"></script>
<link rel="stylesheet" type="text/css" href="${contextPath!}/static/js/swfupload/multi/css/icons.css${staticVersion! }">
<link rel="stylesheet" type="text/css" href="${contextPath!}/static/js/swfupload/multi/css/UploadDialog.css${staticVersion! }">
<link rel="stylesheet" type="text/css" href="${contextPath!}/static/js/swfupload/multi/resources/css/ext-all.css${staticVersion! }">
<script type="text/javascript" src="${contextPath!}/static/js/swfupload/multi/js/ext-base.js${staticVersion! }"></script>
<script type="text/javascript" src="${contextPath!}/static/js/swfupload/multi/js/ext-all.js${staticVersion! }"></script>
<script type="text/javascript" src="${contextPath!}/static/js/swfupload/multi/js/UploadDialog.js${staticVersion! }"></script>

<script type="text/javascript">
	getDialog = function () {
		var dialog = null;
		dialog = new UploadDialog({
			uploadUrl : '',
			filePostName : 'uploadFile',
			flashUrl : '${contextPath!}/static/js/swfupload/common/swfupload.swf${staticVersion! }',
			fileSize : '${filesize}',
			fileTypes : '${fileTypes}',
			fileTypesDescription : '${description}',
			scope : this
		});
		return dialog;
	};
	
	function ${functionName}(type, title){
		var dialog = getDialog();
		dialog.uploadUrl = "${action! }" + type;
		dialog.title = title;
		dialog.show();
	}
</script>
</#macro>

<#-- 富文本编辑框 -->
<#macro Kindeditor name param='' editor="editor" readonlyMode="false">
<script type="text/javascript" src="${staticPath! }/static/js/Kindeditor/kindeditor-min.js${staticVersion! }"></script>
<script>
	var ${editor};
	jQuery("body").ready(function(){
		if(${editor} && !+[1,]){
			${editor}.remove();
			${editor} = null;
		}
		KindEditor.basePath = '${staticPath! }/static/js/Kindeditor/';
		jQuery.getScript('${staticPath! }/static/js/Kindeditor/kindeditor-min.js${staticVersion! }', function() {
			${editor} = KindEditor.create('textarea[name="${name}"]',{
				readonlyMode : ${readonlyMode},
				resizeType : 1,
				allowPreviewEmoticons : false,
				uploadJson : '${contextPath! }/web/kindeditorImgUpload.html;jsessionid=${request.session.id}',
				allowImageUpload : true,
				afterBlur: function(){this.sync();},
				filterMode : false
				<#if param=='default'>
				<#elseif param=''>
				,items : [
					'source', '|', 'cut', 'copy', 'paste', 'plainpaste', '|', 'fontname', 'fontsize', '|', 
					'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', 'clearhtml', 'quickformat', 'selectall', '|', 'emoticons', 'image', 'link', '|', 'fullscreen']
				<#else>
			    ,items : [${param}]
			    </#if>
			});
		});
	});
</script>
</#macro>
<#macro callServer>
<script>
$(document).ready(function(){
	<#-- 为了防止session超时。每隔20分钟提交一次数据 -->
	setTimeout(function(){
		callServer();
	}, 20 * 60 * 1000);
});

function callServer(){
	$.ajax({url:"${contextPath! }/web/blank.html;jsessionid=${request.session.id}"});
	setTimeout(function(){
		callServer();
	}, 20 * 60 * 1000);
}
</script>
</#macro>

<#-- 动态搜索 -->
<#macro search text="" value="" url="">
	<link href="${staticPath! }/static/css/select4.css${staticVersion! }" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${staticPath! }/static/js/select4.js${staticVersion! }"></script>
	
	<script>
		jQuery("body").ready(function(){
			$("#${text}").select4({"ajax_url":"${url}", "search_id": "${value}"});
		});
	</script>
</#macro>

<#-- 时间控件 -->
<#macro dateUtil url="?" form="form1" startTime="startTime" endTime="endTime">
<script type="text/javascript">
function setTime(day){
	var now = new Date();
	var startTime, endTime, startDay, endDay, time;
	if(day == 0){//所有时间段
		startTime = "";
		endTime = "";
	} else if (day == 1){//昨天 
		time = now.getTime();
		time = time - 1 * 24 * 60 * 60 * 1000;
		
		startDay = now;
		startDay.setTime(time);
		startTime = startDay.getFullYear() + "-" + getMonth(startDay.getMonth())+ "-" + startDay.getDate()
		
		endDay = now;
		endDay.setTime(time);
		endTime = endDay.getFullYear() + "-" + getMonth(endDay.getMonth()) + "-" + endDay.getDate()
	} else if (day == 2){//过去一周内
		time = now.getTime();
		
		startDay = new Date();
		startDay.setTime(time - 7 * 24 * 60 * 60 * 1000);
		startTime = startDay.getFullYear() + "-" + getMonth(startDay.getMonth())+ "-" + startDay.getDate()
		
		endDay = new Date();
		endDay.setTime(time - 1 * 24 * 60 * 60 * 1000);
		endTime = endDay.getFullYear() + "-" + getMonth(endDay.getMonth()) + "-" + endDay.getDate()
	} else if (day == 3){//本月的
		var year = now.getFullYear();
		var month = getMonth(now.getMonth());
        //获取当月最后一天日
        var day = new Date(year,month,0);   
    	
		startTime = year + '-' + month + '-01';  
    	endTime = year + '-' + month + '-' + day.getDate();
	} else if (day == 4){//上月的
		var year = (now.getMonth() == 0) ? (now.getFullYear() - 1) : now.getFullYear();  
    	var month = (now.getMonth() == 0) ? 11 : now.getMonth() - 1; 
        //获取当月最后一天日
        var day = new Date(year,month,0);
    	
		startTime = year + '-' + getMonth(month) + '-01';  
    	endTime = year + '-' + getMonth(month) + '-' + getDayOfMonth(year,month);
	}
	
	$("#${startTime! }").val(startTime);
	$("#${endTime! }").val(endTime);
	
	var form1 = document.getElementById("${form! }");
	form1.action = "${url! }";
	form1.submit();
}

function getMonth(i){
	i = i +1;
	if(i< 10){
		return "0" + i;
	}else{
		return i;
	}
}
//根据年月计算指定月份天数
function getDayOfMonth(year, month) {  
    if (typeof year == 'undefined') { year = (new Date()).getFullYear(); }  
    if (typeof month == 'undefined') { month = (new Date()).getMonth(); }  
    var Feb = (year % 4 == 0) ? 29 : 28;  
    var aM = new Array(31, Feb, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);  
    return  aM[month];  
};
</script>
<a href="#" onclick="setTime(1)">昨日</a> <a href="#" onclick="setTime(2)">七天</a> <a href="#" onclick="setTime(3)">本月</a> <a href="#" onclick="setTime(4)">上月</a>
</#macro>

<#macro optionSelect>
	<ul id='nav'> 
		<li>
			<a href='javascript:void(0)' style='width: 100px; '>操作</a> 
			<ul> 
				<#nested>
			</ul> 
		</li> 
	</ul>
	<script type='text/javascript'>
		jQuery("body").ready(function(){
			var sfEls = document.getElementById('nav').getElementsByTagName('li'); 
			for (var i=0; i<sfEls.length; i++) {
				sfEls[i].onmouseover=function() {
					this.className+=(this.className.length>0? ' ': '') + 'sfhover'; 
				} 
				sfEls[i].onMouseDown=function() {
					this.className+=(this.className.length>0? ' ': '') + 'sfhover'; 
				} 
				sfEls[i].onMouseUp=function() {
					this.className+=(this.className.length>0? ' ': '') + 'sfhover'; 
				} 
				sfEls[i].onmouseout=function() {
					this.className=this.className.replace(new RegExp('( ?|^)sfhover\\b'), ''); 
				} 
			} 
		});
	</script>
</#macro>