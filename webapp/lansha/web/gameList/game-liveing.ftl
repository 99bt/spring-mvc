<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta />
    <#list list?if_exists as game>
    <Meta name="keywords" content="${game.seo! }">
    <Meta name="description" content="${game.seo! }">
    <title>${game.name! }-${platFormName! }</title>
    </#list>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/game.css${staticVersion! }">
</head>
<body>
<@lansha.head index="3"/>
<div class="container">
    <#list list?if_exists as game>
    <div class="game-details" style="background: url('${uploadPath! }${(game.background)! }${staticVersion! }') center top no-repeat;">
        <div class="details-title">
            <img class="details-gameIcon fl" src="${uploadPath! }${game.icon! }" onerror="this.src='${staticPath! }/static/lansha/static/images/nopic.png${staticVersion! }'" alt="游戏图标" />
            <div class="details-titleCon fl">
                <h3 class="details-gameName fl">${game.name! }</h3>
                <#if gameDownLoadSwitch>
                <div class="details-gameDownload fl">
                    <p class="fl">游戏下载：</p>
                    <a href="<#if (game.androidUrl)?default('')==''>appdownload.html <#else> ${game.androidUrl! }</#if>" class="details-downloadbtn fr android">下载Android版</a>
                    <a href="<#if (game.iosUrl)?default('')==''>appdownload.html<#else>${(game.iosUrl)! }</#if>" class="details-downloadbtn fr IOs">下载iPhone版</a>
                </div>
                </#if>
            </div>
            <#if gameDownLoadSwitch>           
            <div class="details-QRCode fr">
                <img class="gameQRCode fl" src="${uploadPath! }${game.qrcode! }${staticVersion! }" onerror="this.src='${staticPath! }/static/lansha/static/images/qrcode.jpg${staticVersion! }'" alt="游戏二维码" />
                <span class="fr bendArrow"></span>
                <p class="fr scanCode">手机扫一扫<br/>立即下载开玩！</p>
            </div>
            </#if>
        </div>
    </div>
    <div class="layout details-box clearfix">
        <div class="details-box-title clearfix">
            <a href="gameLive.html?id=${id! }" class="details-btn fl details-cur Live">在线直播</a>
            <a href="gameDetail.html?id=${id! }" class="details-btn fl Details">游戏详情</a>
        </div>
        <ul class="">
            <#list game.ywUserRooms?if_exists as item>
            <#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />        
            <li class="game-one fl <#if (item_index + 1) % 4 ==0>last</#if>">
                    <a href="${url! }" class="game-pic" target="_blank">
                        <img class="lazy"  src="${uploadPath! }${item.liveImg! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';" alt="" width="280" height="156" />
                        <div class="live-icon"><img src="${item.userIcon! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt=""/></div>
                        <div class="live-bg"></div>
                        <div class="live-begin"></div>
                    </a>
                    <h3 class="room-name">
                        <a href="${url! }" class="room-name-title">${item.name! }</a>
                        <a href="${contextPath! }/gameLive.html?id=${item.gameId! }" class="fr room-game-name">${item.gameName! }</a>
                    </h3>
                    <div class="room-topic clearfix">
                        <div class="fl">${item.nickname! }</div>
                        <div class="fr"><i class="icon-sprite icon-people fl"></i>${item.onLineNumber! }</div>
                    </div>
                    <i class="show-line-b"></i>
            </li>
            </#list>
        </ul>
    </div>
    </#list>
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