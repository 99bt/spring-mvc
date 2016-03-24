<#import "/lansha/web/common/lansha.ftl" as lansha>
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
    <title>${gameName! }-下载</title>
    <link rel="stylesheet" href="${staticPath! }/lansha/wap/static/css/common.css" />
    <link rel="stylesheet" href="${staticPath! }/lansha/wap/static/css/mobile.css" />
</head>

<body>
<!--conent start-->
<section class="clearfix">
    <div class="download-logo clearfix">
        <img src="${gameIco! }" alt="${gameName! }" class="fl">
        <p class="fl">${gameName! }</p>
    </div>
    <#if url?default('') == ''>
    <a href="javascript:" class="download-btn gray">敬请期待</a>
    <#else>
    <a href="${url! }" class="download-btn">下载安装</a>
    </#if>
    <div class="fixed-bg">
        <div class="tips-box fr">
            <p>点击右上角菜单<br/>
                在默认浏览器中打开并安装应用</p>
        </div>
    </div>
</section>
<!--conent end-->
<!--script start-->
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${jsPath! }/lansha/wap/static/js/common.js"></script>
<script>
    function is_weixin() {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    }
    var isWeixin = is_weixin();
    if(isWeixin){
        $(".fixed-bg").show();
    }
</script>
<!--script end-->
</body>
</html>