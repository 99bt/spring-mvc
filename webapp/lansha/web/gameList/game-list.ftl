<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta keywords="天天酷跑,我的世界,自由之战，全民枪战，王者荣耀，天龙八部，天天炫舞，全民奇迹，全民超神，大话西游等热门手游高清直播,${platFormName! },全民手游直播平台" />
    <title>${platFormName! }-全民手游直播平台</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/game.css${staticVersion! }">
</head>
<body>
<@lansha.head index="3"/>
<div class="container">
    <div class="layout">
        <div class="game-box game-center mgt10">
            <h3 class="game-slide clearfix">
                <a class="game-slideone cur fl" href="gameList.html">热门游戏</a>
                <a class="game-slideone fl" href="gameCenter.html">全部游戏</a>
                <p class="fr">
                    <#list ywGameHots?if_exists as ywGameHot>
                		<a href="gameLive.html?id=${ywGameHot.gameId! }" target="_blank">${ywGameHot.gameName! }</a>
               		</#list>
                </p>
            </h3>
            <div class="game-box">
            	<#list list?if_exists as game>
                <div class="game-title clearfix">
                    <h3 class="game-name fl"><a href="gameLive.html?id=${game.id! }">${game.name! }</a></h3>
                    <p class="game-people fl"><span class="col-main">${game.ywUserRooms?size }</span>个主播正在直播</p>
                    <span class="game-more fr" data-listnum="7"><a href="gameLive.html?id=${game.id! }" class="fr">更多>></a></span>
                </div>
                <ul class="game-list clearfix">
                    <#list game.ywUserRooms?if_exists as item>
                	<#if item_index < 8>
                    <li class="game-one fl <#if (item_index + 1) % 4 ==0>last</#if>">
                        <a href="${contextPath! }${appsetting.getLivePath(item.idInt)! }" class="game-pic">
                            <img class="lazy" src="${uploadPath! }${item.liveImg! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png${staticVersion! }';" alt="${item.name! }" width="280" height="156" />
                        	<div class="live-icon"><img src="${item.userIcon! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt="${item.name! }" /></div>
                            <div class="live-bg"></div>
                            <div class="live-begin"></div>
                        </a>
                        <h3 class="room-name"><a href="${contextPath! }${appsetting.getLivePath(item.idInt)! }">${item.name! }</a></h3>
                        <div class="room-topic clearfix">
                            <div class="fl"><i class="icon-sprite icon-people fl"></i>${item.nickname! }</div>
                            <div class="fr"><i class="icon-sprite icon-gamename fl"></i>${item.gameName! }</div>
                            <div class="fl"><i class="icon-sprite icon-eye fl"></i>${item.onLineNumber! }</div>
                        </div>
                        <i class="show-line-b"></i>
                    </li>
                    </#if>
               		</#list>
                </ul>
                </#list>
            </div>
        </div>
        <div class="more " <#if list?size?default(0) <= pageDto.rowNum?default(3) && list?size?default(0) == count >style='display : none'</#if>>加载更多</div>
        <div class="noMoreData" style="display: none;">已经到底了呦</div>
    </div>
</div>
<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script>
    $(".game-more[ data-listnum = '7']").show();
        var page = 2;
        var gameLiveURL = "gameLive.html?";
        $(".more").on("click", function () {
            $.ajax({
                url: 'getGameList.html',
                type: 'GET',
                async: false,
                dataType: 'json',
                data: {pageIndex: page},
                success: (function (data) {
                    var dataLists = '';
                    var num = 1;
                    var lastClass = '';
                    for (x in data) {
                        for (i = 0; i < data[x].gameList.length; i++) {
                            if (i % 4 == 3 && i != 0) {
                                lastClass = " last";
                            } else {
                                lastClass = "";
                            }
                            if(i < 8){
	                            dataLists = dataLists + '<li class="' + data[x].gameList[i].className + '' + lastClass + '"><a href="${contextPath! }'+ data[x].gameList[i].liveAddress +'" class="game-pic"> <img src="' + data[x].gameList[i].gameImg + '" onerror="javascript:this.src=\'${staticPath! }/static/lansha/static/images/nopic.png${staticVersion! }\';" alt="" /><div class="live-icon"> <img src="' + data[x].gameList[i].livePic + '" alt="" onerror="javascript:this.src=\'${staticPath! }/static/lansha/static/images/live-icon.png\';" ></div> <div class="live-bg"></div> <div class="live-begin"></div></a> <h3 class="room-name mgt10"><a href="${contextPath! }'+ data[x].gameList[i].liveAddress +'">' + data[x].gameList[i].liveTitle + '</a></h3> <div class="room-topic clearfix"> <div class="fl"><i class="icon-sprite icon-people fl"></i>' + data[x].gameList[i].liveName + '</div> <div class="fr"><i class="icon-sprite icon-gamename fl"></i>' + data[x].gameList[i].gameNume + '</div><div class="fl"><i class="icon-sprite icon-eye fl"></i>' + data[x].gameList[i].viewNum + '</div></div><i class="show-line-b"></i> </li>';
	                            num = i;
                            }
                        }
                        text = ' <div class="game-box"><div class="game-title clearfix"><h3 class="game-name fl"><a href="'+ gameLiveURL+data[x].addressId +'">'+data[x].nameGame +'</a></h3> <p class="game-people fl"><span class="col-main">'+data[x].liveNum +'</span>个主播正在直播</p> <span class="game-more fr" data-listnum="'+ num +'"><a href="'+gameLiveURL+data[x].addressId +'" class="fr">更多>></a></span> </div> <ul class="game-list clearfix">'+ dataLists+'</ul>';
                        $(".game-center").append(text);
                        $(".game-more[ data-listnum = '7']").show();
                        dataLists = '';
                    }
                    if (data == "" || ${count! }/${pageDto.rowNum?default(3) } <= page) {
                        $(".more").hide();
                        $(".noMoreData").show().addClass("hide3s");
                    }
                })
            });
           
            page = page + 1;
        });
</script>
</body>
</html>