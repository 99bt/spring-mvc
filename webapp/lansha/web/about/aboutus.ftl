<#import "/lansha/web/common/lansha.ftl" as lansha>
<html lang="en">
<head>
	<@lansha.meta />
    <title>关于我们 - ${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/center.css">
    <!--[if lt IE 9]>
    <script ="${staticPath! }/static/lansha/static/js/html5.min.js"></script>
    <![endif]-->
</head>
<body class="fffbf8-body">
<@lansha.head index=""/>
<div class="container">
    <div class="layout  help-box clearfix">
        <@lansha.aboutUs index="1"/>
        <div class="help-right fr" style="min-height: 400px;">
            <div class="help-title">关于我们</div>
            <p class="help-p"><br/>蓝鲨TV是杭州掌鲨科技有限公司研发的弹幕式手机游戏直播平台，于2015年12月上线，
                同时支持苹果和安卓手机的视频直播服务，无需电脑就可以用手机进行直播。</p>
            <p class="help-p">蓝鲨TV专注于手机游戏直播服务，内容主要涵盖各类原创手机游戏直播，实现让玩家随时随地通过手机直播来实时分享自己的手游乐趣。</p>
            <p class="help-p">媒体联络：mt@lansha.tv <br/>
                客服热线：kf@lansha.tv
            </p>
        </div>

    </div>
</div>
<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script src="${staticPath! }/static/lansha/static/js/jquery-1.11.1.min.js"></script>
<script src="${staticPath! }/static/lansha/static/js/common.js"></script>
<script src="${staticPath! }/static/lansha/static/js/jquery.SuperSlide.2.1.1.js"></script>
<script>
    jQuery(".picScroll-left").slide({titCell:".hd ul",mainCell:".bd ul",autoPage:true,effect:"left",autoPlay:true,vis:4});
</script>
</body>
</html>