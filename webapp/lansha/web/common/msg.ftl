<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta />
    <title>${platFormName! }-错误信息</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <style type="text/css">
        .conent {width: 1120px;min-height: 520px;margin:60px auto;overflow:hidden;text-align: center;color: #999;}
        .conent img {margin: 60px 0px;}
        .conent h2 {margin-top: 30px;font-size: 50px;line-height: 50px;}
        .conent h3 {margin-top: 20px;font-size: 22px;text-align: center;}
        .conent h5 {margin-top: 100px;}
        .conent .btn {display: block;padding: 6px 20px;text-align: center;width: 80px;height: 22px;line-height: 22px;color: #FFF;background: #3ca2f0;margin:0 auto;border-radius: 5px;}
    </style>
</head>
<body>
<@lansha.head index=""/>
<!--conent start-->
    <div class="conent clearfix">
        <img src="${staticPath! }/static/lansha/static/images/nodata.png${staticVersion! }" height="82" width="82" />
        <!-- <h2 class="errCode">404</h2> -->
        <h3>
        <#if action.hasActionErrors()>
		  	<#list actionErrors as item>
		  		<#if item?exists>
					${item?j_string}
				</#if>
			</#list>
		</#if>
		<#if action.hasActionMessages()>
		  	<#list actionMessages as item>
		  		<#if item?exists>
					${item?j_string}
				</#if>
			</#list>
		</#if>
		<#if action.hasFieldErrors()>
			<#list fieldErrors.keySet() as item>
				<#if item?exists>
					<#list fieldErrors.get(item) as list>
						<#if list?exists>
							${list?j_string}
						</#if>
					</#list>
				</#if>
			</#list>
		</#if>
        </h3>
        <h5><a href="javascript:;" onclick="history.go(-1);" class="btn">返回</a></h5>
    </div>
<!--conent end-->

<@lansha.foot />

<#-- 页尾js -->
<@lansha.footjs />
</body>
</html>