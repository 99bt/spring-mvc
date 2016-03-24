<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>搜索结果-${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/game_new.css${staticVersion! }">
</head>
<body>
<@lansha.head index="2"/>
<div class="container mgt30">
    <div class="layout">
        <div class="game-box seach-con mgt10">
            <h3 class="game-slide clearfix">
                <span class="icon seach-icon"></span>
                搜索结果
                <span class="game-num">${name! }，找到 <em>${count?default(0) }</em> 条相关个主播正在直播</span>
            </h3>
            <#if list ??>
            <ul class="game-list clearfix">
                <#list list?if_exists as item>
            	<#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
                <li class="game-one fl<#if (item_index + 1) % 4 ==0> last</#if>">
                    <a href="${url! }" class="game-pic">
                        <img class="lazy" data-original="${uploadPath! }${item.liveImg! }"  onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png${staticVersion! }';" alt="${item.name! }" width="280" height="156" />
                        <div class="live-icon"><img src="${item.userIcon! }"  onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt="${item.name! }" /></div>
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
            <div class="more" <#if list?size?default(0) <= pageDto.rowNum?default(24) && list?size?default(0) == count >style='display : none'</#if> id="" data-apiurl="" data-type="1" data-pagesize="8">加载更多</div>
            <div class="noMoreData" style="display: none;">已经到底了呦</div>
            <#else>
            <img src="${contextPath! }/static/lansha/static/images/seach_end_no.png" class="seach-noData" alt="">
            </#if>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/common.js${staticVersion! }"></script>
<script>
    new LSLive().lazyload("img.lazy");
    new LSLive().LiveGetMore('loadSearchMore.html?name=${name!}');
</script>
<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
</body>
</html>