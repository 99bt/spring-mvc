<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta keywords="天天酷跑,我的世界,自由之战，全民枪战，王者荣耀，天龙八部，天天炫舞，全民奇迹，全民超神，大话西游等热门手游高清直播,${platFormName! },全民手游直播平台" />
    <title>${platFormName! }-全民手游直播平台</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/game_new.css${staticVersion! }">
</head>
<body>
<@lansha.head index="3"/>
<div class="container">
    <div class="layout">
        <div class="game-box mgt10">
            <h3 class="game-slide clearfix">
                <span class="icon"></span>
                全部游戏
                <span class="game-num"><em>${count?default(0)}</em> 款手机游戏</span>
            </h3>
            <ul class="game-alllist clearfix">
                <#list list?if_exists as game>
                <li class="game-allone fl <#if (game_index + 1) % 3 ==0>last</#if>">
                    <div class="game-allpic">
                        <a href="gameLive.html?id=${game.id! }"><img width="380" height="190" class="lazy" data-original="<#if game.advert?default("") == "">${staticPath! }/static/lansha/static/images/nopic.png${staticVersion! }<#else>${uploadPath! }${game.advert! }${staticVersion! }</#if>" alt="${game.name! }" /></a>
                        <div class="games-hover">
                            <div class="games-opacity"></div>
                            <h4><a href="gameLive.html?id=${game.id! }">进入专区&gt;</a></h4>
                        </div>
                    </div>
                    <div class="game-alltext clearfix">
                        <a class="game-allname fl" href="gameLive.html?id=${game.id! }">${game.name! }</a>
                        <p class="game-allnum fl">共有<span class="col-main">${game.ywUserRooms?size }</span>主播</p>
                    </div>
                </li>
                </#list>
            </ul>
        </div>
    </div>
</div>
<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.lazyload.min.js"></script>
<script>
    new LSLive().lazyload("img.lazy");
</script>
</body>
</html>