<#import "/lansha/wap/common/lanshawap.ftl" as commons>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <meta name="author" content="Yowant FE Team" />
    <meta name="Description" content="${platFormName! }-下载" />
    <meta name="Keywords" content="${platFormName! }-下载" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no" />
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <title>${platFormName! }-下载</title>
    <@commons.headcss />
</head>

<body>
<!--conent start-->
<section class="clearfix">
    <div class="download-logo clearfix">
        <img src="${staticPath! }/lansha/wap/static/images/lansha-icon.png" alt="蓝鲨直播" class="fl">
        <p class="fl">${name! }  </p>
    </div>
    <#if url?default("") == "">
    	<a href="javascript:;" class="download-btn gray">敬请期待</a>
    <#else>
    	<a href="${url! }" class="download-btn">下载安装</a>
    </#if>
    <div class="download-fixed-bg">
        <div class="tips-box fr">
            <p>点击右上角菜单<br/>
                在默认浏览器中打开并安装应用</p>
        </div>
    </div>
</section>
<!--conent end-->
 <@commons.footcommonjs />
<script>
    if(is_weixin()){
        $(".download-fixed-bg").show();
    }
</script>
<!--script end-->
</body>
</html>