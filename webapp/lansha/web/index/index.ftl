<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta keywords="热门游戏直播、高清手游直播、手游直播平台、手游直播、手游攻略"
		description="蓝鲨TV，中国领先的手游直播平台，提供在线高清观看流畅的游戏直播，包含CF直播、天天酷跑直播、王者荣耀直播、全民枪战直播、自由之战直播、球球大作战直播等热门手游直播。" />
    <title>${platFormName! }-全民手游直播平台</title>
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/index.css${staticVersion! }">
    <script>
        var Android = navigator.userAgent.match(/Android/i);
        var iPhone = navigator.userAgent.match(/iPhone/i);
        var iPad = navigator.userAgent.match(/iPad/i);
        if (Android || iPhone|| iPad) {
            window.location="${contextPath! }/wap/index.html";
        }
    </script>
</head>
<body style="background-image: url('${bannerBackground! }')">
<@lansha.head index="1"/>

<div class="container">
    <div class="index-banner">
        <div class="layout clearfix">
            <div class="playerArea" id="playerArea">
                <div id="liveplayer"></div>
            </div>
            <div class="listArea">
                <div class="side swiper-container" data-size="8">
                    <ul class="swiper-wrapper">
            		<#list banners?if_exists as item>
                        <li class="video-list swiper-slide">
                            <img src="<#if item.img?default('') == ''>${uploadPath! }${item.room.liveImg! }<#else>${uploadPath! }${item.img! }</#if>" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';" <#if item.room ??>alt="${item.room.name! }"</#if> class="roomCovers greyimg" />
                            <span class="mask"></span>
                            <i class="video-list-arrow"></i>
                            <a href="javascript:;"></a>                            
                        </li>
                    </#list>
                    </ul>
                    <!-- 如果需要导航按钮 -->
                    <div class="arrow-left"></div>
                    <div class="arrow-right"></div>
                </div>
            </div>
        </div>
    </div>
	<ul class="fixLink">
		<li class="downloadRcode">
            <img src="${uploadPath! }/app/${lanshaLogo! }-type1.png${staticVersion! }" alt="下载app">
        </li>
        <li><a href="${store! }/activity/anchor-recruit.html"><em class="gotopSprite gotop02 fr"></em><span class="fr">我要做主播</span></a></li>
        <li><a target="_blank" href="${contextPath! }/suggestion.html"><em class="gotopSprite gotop03 fr"></em><span class="fr">意见反馈</span></a></li>
        <li><a target="_blank" href="${contextPath! }/appdownload.html"><em class="gotopSprite gotop04 fr"></em><span class="fr">下载APP</span></a></li>
        <li class="go-Top"><em class="gotopSprite gotop01 fr"></em><span class="fr">回到顶部</span></li>
    </ul>
    <div class="index-black">
        <div class="layout clearfix">
            <div class="index-box fl clearfix">
                <h3>大神主播</h3>
                <ul class="index-black-ul">
                    <#list bestRooms?if_exists as item>
                    <li class="fl<#if item_index == 2> last</#if>">
                        <a href="${contextPath! }${appsetting.getLivePath(item.idInt)! }">
                            <div class="live-bg"></div>
                            <div class="play-mask"></div>
                            <img src="${uploadPath! }${item.liveImg! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';"  alt="" width="185" height="108">
                        </a>
                        <p>${item.name! }</p>
                    </li>
                    </#list>
                </ul>
            </div>
            <div class="index-box fl clearfix">
                <h3 class="icon1">美女主播</h3>
                <ul class="index-black-ul">
                <#list girlRooms?if_exists as item>
                    <li class="fl<#if item_index == 2> last</#if>">
                        <a href="${contextPath! }${appsetting.getLivePath(item.idInt)! }">
                            <div class="live-bg"></div>
                            <div class="play-mask"></div>
                            <img src="${uploadPath! }${item.liveImg! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';"  alt=""  width="185" height="108">
                        </a>
                        <p>${item.name! }</p>
                    </li>
                </#list>
                </ul>
            </div>
        </div>
    </div>
      <div class="layout clearfix">
        <div class="game-box clearfix">
            <div class="recommend-con fl">
                <div class="game-title clearfix">
                    <h3 class="game-name">
                        <span class="icon icon1"></span>
                        推荐游戏
                        <span class="r-span-more fr"><a href="gameCenter.html">全部分类&gt;</a></span>
                    </h3>
                </div>
                <ul class="recommend-list clearfix">
                
            <#list hotGames?if_exists as item>
                <li class="recommend-one fl<#if (item_index+1)%6 == 0> last</#if>">
                    <a class="recommend-pic" href="gameLive.html?id=${item.id! }" target="_blank">
                        <i></i>
                        <img src="${uploadPath! }${item.advertSmall! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic01.png${staticVersion! }';" alt="${item.name! }" width="139" height="155" />
                    </a> 
                    <p class="recommend-download"><a href="gameLive.html?id=${item.id! }">${item.name! }</a></p>                   
                </li>
            </#list>
                </ul>
            </div>
            <div class="active-con fl">
                <div class="game-title clearfix">
                    <h3 class="game-name">
                        <span class="icon icon2"></span>
                        精彩活动
                    </h3>
                </div>
                <#list activityPushList?if_exists as push>
                <div class="active-con-li">
                    <a href="${push.activityUrl! }" ><img src="${push.indexImg! }" onerror="javascript:this.src='${contextPath! }/static/lansha/static/images/active_index_img.jpg'" width="271" height="115" alt="${title! }" /><i></i></a>
                </div>
                </#list>
            </div>
        </div>
        <div class="game-box">
             <div class="game-title clearfix">
                <h3 class="game-name">
                   	 正在直播
                    <span class="game-num"><em>${count! }</em>个主播正在直播</span>
                    <span class="icon"></span>
                    <span class="r-span-more fr"><a href="${contextPath! }/liveList.html">更多&gt;</a></span>
                    <a href="javascript:;" data-api="${contextPath! }/getNewLive.html" class=" r-span fr">新</a>
                    <a href="javascript:;" data-api="${contextPath! }/getPushLive.html" class=" r-span fr">荐</a>
                    <a href="javascript:;" data-api="${contextPath! }/getHotLive.html" class=" r-span on fr">热</a>
                </h3>
            </div>
            <ul class="game-list index-game-index clearfix">
            <#list rooms?if_exists as item>
            	<#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
            	<#assign userIcon = "${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }" />
                <li class="game-one fl<#if (item_index + 1) % 4 ==0> last</#if>">
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
        
        <#list hotGameRooms?if_exists as game>
        <div class="game-box">
            <div class="game-title clearfix">
                <h3 class="game-name">
                    ${game.gameName! }
                    <span class="game-num"><em>${game.count! }</em>个主播正在直播</span>
                    <span class="icon icon3"></span>
                    <span class="r-span-more fr"><a href="${contextPath! }/gameLive.html?id=${game.gameId! }">更多&gt;</a></span>
                    <a href="javascript:;" data-api="${contextPath! }/getNewLive.html?id=${game.gameId! }" class=" r-span fr">新</a>
                    <a href="javascript:;" data-api="${contextPath! }/getPushLive.html?id=${game.gameId! }" class=" r-span fr">荐</a>
                    <a href="javascript:;" data-api="${contextPath! }/getHotLive.html?id=${game.gameId! }" class=" r-span on fr">热</a>
                </h3>
            </div>
            <!--推荐-->
            <ul class="game-list index-game-index clearfix">
            <#list game.roomList?if_exists as item>
            <#assign tourl = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
            <#assign userIcon = "${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }" />
            <li class="game-one fl<#if (item_index + 1) % 4 ==0> last</#if>">
                    <a href="${tourl! }" class="game-pic" target="_blank">
                        <img class="lazy"  src="${uploadPath! }${item.liveImg! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';" alt="" width="280" height="156" />
                        <div class="live-icon"><img src="${uploadPath! }${item.userIcon! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt=""/></div>
                        <div class="live-bg"></div>
                        <div class="live-begin"></div>
                    </a>
                    <h3 class="room-name">
                        <a href="${tourl! }" class="room-name-title">${item.name! }</a>
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
</div>

<@lansha.foot />

<script type="text/javascript">
    /** 首页推荐数据 */
    var index_rooms = new Array();
    
    <#list banners?if_exists as item>
    	<#assign type = item.type?default("1")>
	    <#if type == "0">
		    var temp = {
		        'type': ${type},
		        'onlineCount': '${item.room.onLineNumber?default(0)}',
		        'gameName': '',
		        'roomID': '${(item.room.roomId)! }',
		        'idInt': '${(item.room.idInt)! }',
		        'url': '${(item.room.liveHost)! }'
		    };
	    <#elseif type == "1">
		    var temp = {
		        'type': ${type! },
		        'cover': "${uploadPath! }${item.bigImg! }",
		        'onlineCount': '',
		        'gameName': '',
		        'roomID': '',
		        'url': '${item.linkUrl! }'
		    };
	    </#if>
	    index_rooms.push(temp);
    </#list>
    
    function onChangeBanner(value){
    	if (value.type == 0) {
            <#-- 切换直播房间 -->
            //this.loadPlayer(value.roomID);
            //$.xbox.tips('切换直播间');
            $(".playerArea").children().remove();
            $(".playerArea").append("<div id='liveplayer'></div>");
            swfobject.embedSWF("${staticPath! }/static/lansha/static/flash/play.swf${staticVersion! }", "liveplayer", '100%', '100%', "9.0.0", null,null, params, objAttr,function(){
		        //if(thisMovie() && thisMovie().changeVideoSize){
		        //    thisMovie().changeVideoSize(mW, mH);
		        //}
		    });
		    rtmp = value.url;
		    videoURL = value.roomID;
		    idInt = value.idInt;
        }
        <#-- 99为推荐活动 -->
        if (value.type == 1) {
            var str = "<a target='_blank' href='" + value.url + "'><img class='eventPic' src=" + value.cover + "></a>";
            $(".playerArea").children().remove();
            $(".playerArea").html(str);
        }
    }
</script>

<#-- 页尾js -->
<@lansha.footjs />

<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.swiper.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/swfobject.min.js"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/index.js${staticVersion! }"></script>

<script type="text/javascript">
    var rtmp="", videoURL="", idInt="";
    
    //loader player
    var mW = 0, mH = 0;
    mW = $('#playerArea').width(), mH = mW/16*9;

    function thisMovie() {
        return document["media_player_box"];
    }
    function getSetting() {
        var settings_object = {
            rtmp : rtmp,
            videoURL : videoURL,
            width : mW,
            height :  mH,
            bufferTime : ${playCache?default(3) },
            isIndex: true
        };
        return settings_object;
    }
	function skipUrl(){
		location.href="${contextPath! }/"+idInt;
	}
    //加载播放器
    var params = {
        width: mW,
        height: mH,
        menu: "false",
        quality: "high",
        allowFullScreen: "true",
        bgcolor: "#191919",
        allowScriptAccess: "always",
        wmode: "Opaque"
    };
    var objAttr = {
        id: "media_player_box"
    }
    //加载banner
    onChangeBanner(index_rooms[0]);
</script>
</body>
</html>