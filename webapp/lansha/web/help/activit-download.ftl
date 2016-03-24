<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>${platFormName! }-下载</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css">
    <style>
    body { background: url("${staticPath! }/static/lansha/static/images/app-download.jpg") no-repeat center 0; }
        .app-download { height: 734px; }
        .be-anchor { margin: 555px 0 0 239px; width: 153px; height: 31px; display: block; }
        .app-box { padding: 112px 73px 0 0; width: 440px; }
        .app-link { width: 289px; }
        .app-one { height: 69px; margin-bottom: 7px; display: block; }
        .app-type { width: 73px; height: 69px; text-indent: -9999em; border-radius: 3px 0 0 3px; overflow: hidden; background: url("${staticPath! }/static/lansha/static/images/app-icon.png") no-repeat 0 0; }
        .app-android .app-type { background-position: 0 0; }
        .app-iOS .app-type { background-position: 0 -69px; }
        .app-android .app-name { background: url("${staticPath! }/static/lansha/static/images/android-text.png") no-repeat center center; }
        .app-iOS .app-name { background: url("${staticPath! }/static/lansha/static/images/iOS-text.png") no-repeat center center; }
        .app-name { width: 216px; height: 69px; border-radius: 0 3px 3px 0; }
        .app-color01 .app-type { background-color: #ffae00; }
        .app-color01 .app-name { background-color: #ffc600; }
        .app-color02 .app-type { background-color: #fd6d04; }
        .app-color02 .app-name { background-color: #ff862d; }
        .app-color03 .app-type { background-color: #28adde; }
        .app-color03 .app-name { background-color: #2ecee6; }
        .app-color04 .app-type { background-color: #6cc839; }
        .app-color04 .app-name { background-color: #74d93c; }
        .app-code img { display: block; width: 145px; height: 145px; }
        .app-tips { font: 20px/40px "Microsoft YaHei"; color: #fff; text-align: center; }
        .anchor-box { margin-top: 120px; }
        .footer { height: auto; margin-top: 0; min-width: 1180px; width: 100%; }
        .footer .footerNav { border-top: none;}
    </style>
    <!--[if lt IE 9]>
    <script ="${staticPath! }/static/lansha/static/js/html5.min.js"></script>
    <![endif]-->
</head>
<body>
<@lansha.head index=""/>
<div class="container app-download">
    <div class="layout clearfix">
        <a class="be-anchor fl" href="${store! }/activity/anchor-recruit.html"></a>
        <div class="app-box fr">
            <div class="tv-box">
                <div class="clearfix">
                    <div class="app-link fl">
                        <a class="app-one app-android app-color01 clearfix" href="${lanshaAppAndroid! }">
                            <span class="app-type fl">安卓版</span>
                            <p class="app-name fl"></p>
                        </a>
                        <a class="app-one app-iOS app-color02 clearfix" href="${lanshaAppIos! }">
                            <span class="app-type fl">iPhone版</span>
                            <p class="app-name fl"></p>
                        </a>
                    </div>
                    <div class="app-code fr">
                        <img src="${uploadPath! }/app/${lanshaLogo! }-type1.png${staticVersion! }" alt="蓝鲨TV客户端" width="145" height="145">
                    </div>
                </div>
                <h4 class="app-tips">观看各类精彩手游直播的贴身管家</h4>
            </div>
            <div class="anchor-box">
                <div class="clearfix">
                    <div class="app-link fl">
                        <a class="app-one app-android app-color03 clearfix" href="${lanshaAppLiveAndroid! }">
                            <span class="app-type fl">安卓版</span>
                            <p class="app-name fl"></p>
                        </a>
                        <a class="app-one app-iOS app-color04 clearfix" href="${lanshaAppLiveIos! }">
                            <span class="app-type fl">iPhone版</span>
                            <p class="app-name fl"></p>
                        </a>
                    </div>
                    <div class="app-code fr">
                        <img src="${uploadPath! }/app/${lanshaLogo! }-type2.png${staticVersion! }" alt="蓝鲨录-直播工具" width="145" height="145">
                    </div>
                </div>
                <h4 class="app-tips">适用于iOS9.0以下/Android4.4及以上</h4>
            </div>
        </div>
    </div>
</div>
<@lansha.foot />
<@lansha.footjs />
</body>
</html>