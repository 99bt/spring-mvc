<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta keywords="全民直播大厅,${platFormName! },全民手游直播平台" description="全民直播大厅,${platFormName! },全民手游直播平台" />
    <title>${platFormName! }-全民手游直播平台</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/game_new.css${staticVersion! }">
</head>
<body>
<@lansha.head index="2"/>
<div class="container mgt30">
    <div class="layout">
        <div class="game-box">
            <h3 class="game-slide clearfix">
                <span class="icon"></span>
                全部直播
                <span class="game-num"><em>${count! }</em> 个主播正在直播</span>
                <a href="liveList.html?show=2" class=" r-span<#if show =='2'> on</#if> fr">新</a>
                <a href="liveList.html?show=0" class=" r-span<#if show =='0'> on</#if> fr">荐</a>
                <a href="liveList.html?show=1" class=" r-span<#if show =='1'> on</#if> fr">热</a>
            </h3>
            <ul class="game-list clearfix">
                <#list list?if_exists as item>
                <#assign userIcon = "${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }" />
                <li class="game-one fl<#if (item_index + 1) % 4 ==0> last</#if>" >
                    <a href="${contextPath! }${appsetting.getLivePath(item.idInt)! }" class="game-pic"  target="_blank">
                        <img class="lazy" data-original="${uploadPath!  }${item.liveImg!  }"  onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png${staticVersion! }';" alt="${item.name! }" width="280" height="156" />
                        <div class="live-icon"><img src="${item.userIcon!userIcon }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt="${item.name! }" /></div>
                        <div class="live-bg"></div>
                        <div class="live-begin"></div>
                    </a>
                    <h3 class="room-name">
                        <a href="${contextPath! }${appsetting.getLivePath(item.idInt)! }" class="room-name-title">${item.name! }</a>
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
            <div class="more" <#if list?size?default(0) <= pageDto.rowNum?default(24) && list?size?default(0) == count >style='display : none'</#if> id="" data-apiurl="" data-type="1" data-pagesize="8">加载更多</div>
            <div class="noMoreData" style="display: none;">已经到底了呦</div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/common.js${staticVersion! }"></script>
<script>
    new LSLive().lazyload("img.lazy");
    new LSLive().LiveGetMore('getLiveList.html?show=${show! }');
</script>
<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
</body>
</html>