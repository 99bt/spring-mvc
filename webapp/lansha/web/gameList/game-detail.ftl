<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta />
    <Meta name="keywords" content="${entity.seo! }">
    <Meta name="description" content="${entity.seo! }">
    <title>${(entity.name)! }-${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/game.css${staticVersion! }">
</head>
<body>
<@lansha.head index="3"/>
<div class="container">
    <div class="game-details" style="background: url('${uploadPath! }${(entity.background)! }') center top no-repeat;">
        <div class="details-title">
            <img class="details-gameIcon fl" src="${uploadPath! }${(entity.icon)! }" onerror="this.src='${staticPath! }/static/lansha/static/images/nopic.png${staticVersion! }'" alt="游戏图标" />
            <div class="details-titleCon fl">
                <h3 class="details-gameName fl">${(entity.name)! }</h3>
                <#if gameDownLoadSwitch>
                <div class="details-gameDownload fl">
                    <p class="fl">游戏下载：</p>
                    <a href="<#if (entity.androidUrl)?default('')==''>appdownload.html <#else> ${(entity.androidUrl)! }</#if>" class="details-downloadbtn fr android">下载Android版</a>
                    <a href="<#if (entity.iosUrl)?default('')==''>appdownload.html<#else>${(entity.iosUrl)! }</#if>" class="details-downloadbtn fr IOs">下载iPhone版</a>
                </div>
                </#if>
            </div>
            <#if gameDownLoadSwitch>
            <div class="details-QRCode fr">
                <img class="gameQRCode fl" src="${uploadPath! }${entity.qrcode! }${staticVersion! }" onerror="this.src='${staticPath! }/static/lansha/static/images/qrcode.jpg${staticVersion! }'" alt="游戏二维码" />
                <span class="fr bendArrow"></span>
                <p class="fr scanCode">手机扫一扫<br/>立即下载开玩！</p>
            </div>
            </#if>
        </div>
    </div>
    <div class="layout details-box clearfix">
        <div class="details-box-title clearfix">
            <a href="gameLive.html?id=${id! }" class="details-btn fl Live">在线直播</a>
            <a href="gameDetail.html?id=${id! }" class="details-btn fl details-cur Details">游戏详情</a>
        </div>
        <p class="details-box-game">${(entity.name)! }游戏截图</p>

        <div class="slider">
            <div class="picScroll-left">
               <#if screens?size?default(0) !=0 >
                <div class="hd">
                    <div class="next slideBtn"></div>
                    <div class="prev slideBtn"></div>
                </div>
                </#if>
                <div class="bd">
                    <ul class="picList">
                        <#list screens?if_exists as screen>
                        <li>
                            <div class="pic"><img src="${uploadPath! }${screen! }${staticVersion! }" /></div>
                        </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
        <div class="details-box-gameCon">
            <p class="details-gameCon-game">${(entity.name)! }游戏详情</p>
            <p class="details">${(entity.briefIntro)! }</p>
        </div>
    </div>
</div>
<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.SuperSlide.2.1.1.js"></script>
<script>
    jQuery(".picScroll-left").slide({titCell:".hd ul",mainCell:".bd ul",autoPage:true,effect:"left",autoPlay:true,vis:4});
</script>
</body>
</html>