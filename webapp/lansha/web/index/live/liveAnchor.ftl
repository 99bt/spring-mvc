<#import "/lansha/web/common/lansha.ftl" as lansha>
<#import "/lansha/web/index/live/liveCommon.ftl" as live>
<#import "/common/jquery-plugins.ftl" as plugins>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>${room.name! }-${platFormName!}</title>
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/style.css${staticVersion! }">
</head>
<script>
    var Android = navigator.userAgent.match(/Android/i);
    var iPhone = navigator.userAgent.match(/iPhone/i);
    var iPad = navigator.userAgent.match(/iPad/i);
    if (Android || iPhone|| iPad) {
        window.location="${contextPath! }/wap/live.html?id=${id! }";
    }
</script>
<body>
<@lansha.head index="" isBoxLogin="true" style="live-details-header"/>
<#assign liveUrl = "${hostPath! }${contextPath! }${appsetting.getLivePath(room.idInt)! }" />
<#--
pn=${(loginUser.id)! }
-->
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="1px" height="1px" id="catobj"
        codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab" style="position: absolute;top: 0px; z-index: -1000;";>
    <param name="movie" value="${staticPath! }/static/lansha/static/flash/cat.swf${staticVersion! }" />
    <param name="quality" value="high" />
    <param name="wmode" value="Opaque" />
    <param name="allowFullScreen" value="true" />
    <param name="bgcolor" value="#865ca7" />
    <param name="allowScriptAccess" value="always" />
    <embed src="${staticPath! }/static/lansha/static/flash/cat.swf${staticVersion! }" quality="high" bgcolor="#865ca7"
        width="1" height="1" name="catobj" align="middle"
        play="true" loop="false" quality="high" wmode="Opaque" allowFullScreen="true" allowScriptAccess="always"
        type="application/x-shockwave-flash"
        pluginspage="http://www.macromedia.com/go/getflashplayer">
    </embed>
</object>

<div class="container">
    <@live.left type="1"/>
    
    <div class="liveBlock">
        <!--播放器-->
        <div class="live-main">
        	<a href="${store! }/activity/anchor-recruit.html" target="_blank">
                <div class="live-main-recruit" style="height: 172px;background: url('${uploadPath! }/activity/rootbanner.jpg${staticVersion! }') center top no-repeat;">
                </div>
            </a>
            <div class="room-intro clearfix">
                <span class="room-pic fl"><img src="${staticPath! }/static/lansha/static/images/dianshiji.png"  onerror="this.src='${staticPath! }/static/lansha/upload/default.png${staticVersion! }'"  alt="主播头像" width="80" height="80" /></span>
                <div class="room-text fl">
                    <h3><@plugins.cutOff cutStr="${room.name! }" cutLen="16" /></h3>
                </div>
                <a href="javascript:;" class="room-title-rightBtn fr" id="goplay">开播设置</a>
                <a href="${contextPath! }/user/myroom.html" class="room-details room-title-rightBtn fr">房间信息</a>
            </div>
            <!--播放器-->
            <div id="playerArea">
                <div class="noLive <#if (room.online)?default(0) == 1>hide</#if>">
                    <a href="${contextPath! }/liveList.html" class="noLive-btn">返回直播大厅</a>
                    <p class="noLive-tips">本房间暂无直播，更多精彩直播推荐</p>
                    <div class="noLive-rec">
                        <ul class="live-list clearfix">
                         <#list roomList?if_exists as item>
			            	<#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
			                <li class="game-one fl">
			                    <a href="${url! }" class="game-pic">
			                        <img src="${uploadPath! }${item.liveImg! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';" alt="" />
			                        <div class="live-icon"><img src="${uploadPath! }${item.userIcon! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png';" alt="" /></div>
			                        <div class="live-bg"></div>
			                        <div class="live-begin"></div>
			                    </a>
			                    <h3 class="room-name mgt10"><a href="${url! }">${item.name! }</a></h3>
	                    		<div class="room-topic clearfix">
	                        		<div class="fl"><i class="icon-sprite icon-people fl"></i>${item.nickname! }</div>
	                        		<div class="fr"><i class="icon-sprite icon-gamename fl"></i>${item.gameName! }</div>
	                        		<div class="fl"><i class="icon-sprite icon-eye fl"></i>${item.onLineNumber! }</div>
	                    		</div>
	                    		<i class="show-line-b"></i>
			                </li>
			            </#list>
                        </ul>
                    </div>
                </div>
                <!--在需要刷新时，显示erroe-live 去掉hide-->
                <div class="error-live hide">
                    <a href="${contextPath! }/liveList.html" class="noLive-btn">返回直播大厅</a>
                    <div class="refresh clearfix">
                        <h3>${platFormName!}</h3>
                        <img src="${staticPath! }/static/lansha/static/images/erroe-icon.png" alt="感叹号">
                        <p class="tips">获取节目信息失败，请刷新页面</p>
                        <a href="javascript:refreshVedio()" class="fl">点击刷新</a>
                        <a href="${contextPath! }/suggestion.html" class="fl">反馈问题</a>
                    </div>
                </div>
                <!--在需要刷新时，显示erroe-live 去掉hide-->
        		<div id="liveplayer"></div>
            </div>
            <!--播放器 end-->
            <div class="room-present clearfix"  id="userArea">
                <div class="room-coin fr">
                	<div class="fl">
                		我的虾米：<span class="col-main" id="myMoney">${stock! }</span>
                	</div>
                </div>
            </div>
            <div class="game-box only-livelist">
                <div class="live-title-box">
                    <div class="live-title">
                        <div class="titleDiv">
                            <hr class="left-line" />
                            <hr class="right-line" />
                            <span>热门推荐</span>
                        </div>
                    </div>
                </div>
                <div class="live-auto">
	                <ul class="live-list clearfix">
	            	<#list list?if_exists as item>
		            	<#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
                        <#assign userIcon = "${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }" />
		                <li class="game-one fl<#if (item_index + 1) % 4 ==0> last</#if>" >
                           <a href="${url! }" class="game-pic"  target="_blank">
                              <img class="lazy" src="${uploadPath! }${item.liveImg! }"  onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png${staticVersion! }';" alt="${item.name! }" width="280" height="156" />
                              <div class="live-icon"><img src="${item.userIcon!userIcon }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt="${item.name! }" /></div>
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
            </div>
            <div class="details-footer">
            	<@lansha.footTitle />
            </div>
        </div>
        
        <!--主播开播信息 主播用-->
        <div class="room-popup" style="display: none;">
            <h4 class="popup-title">直播信息</h4>
            <div class="popup-text">
                <p class="popup-tips">推流地址已做变更请重新配置，谢谢</p>
                <ul class="change-roomlist">
                    <li class="change-roomone clearfix">
                        <span class="change-name fl">rtmp地址:</span>
                        <input type="text" class="change-int01" id="rtmp_address" readonly="readonly" value="${room.rtmp! }" />
                        <span class="copy-btn" id="copy_rtmp_address">复制</span>
                    </li>
                    <li class="change-roomone clearfix">
                        <span class="change-name fl">直播码:</span>
                        <input type="text" class="change-int01" id="rtmp_code" readonly="readonly" value="${room.roomId! }" />
                        <span class="copy-btn" id="copy_rtmp_code">复制</span>
                    </li>
                    <li class="change-roomone clearfix">
                        <span class="change-name fl">最大比率:</span>
                        1500
                    </li>
                    <li class="change-roomone clearfix">
                        <span class="change-name fl">分辨率:</span>
                        1920X1080
                    </li>
                    <li class="change-roomone clearfix">
                        <span class="change-name fl">FPS:</span>
                        25FPS
                    </li>
                    <li class="change-roomone clearfix">
                        <span class="change-close btn">关闭</span>
                    </li>
                </ul>
            </div>
        </div>
        <!--主播开播信息 主播用 end-->
        <!--playend-->
        <@live.chat anchor=true/>
    </div>
</div>
<#-- 页尾js -->
<@lansha.footjs />

<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/swfobject.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.nicescroll.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/ZeroClipboard.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.md5.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/emoji.min.js${staticVersion! }"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/room.js${staticVersion! }"></script>
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/mini-user.js${staticVersion! }"></script>
<script type="text/javascript">
    var roomID = '${room.idInt! }';
    var FAV_APIURL = '${contextPath! }/live-relation.html?id=${room.id! }'; //收藏接口
	//INIT
	
    var getGiftCount = <#if firstGetGift>1<#else>0</#if>;   //当日是否已领过虾米
    new LSLiveRoom(null,null, FAV_APIURL).init();
	
    var MIN_WIDTH = 600;
    var mW = 0, mH = 0;
    
    function thisMovie() {
        return document["media_player_box"];
    }
    function getSetting() {
        var settings_object = {
            rtmp : "${(room.liveHost)! }",
            videoURL : "${(room.roomId)! }",
            width : mW,
            height :  mH,
            bufferTime : ${playCache?default(3) },
            isIndex: false,
            online:${room.online!}
        };
        return settings_object;
    }    

    $(function () {
        //加载播放器
        mW = $('.live-main').width(), mH = mW/16*9;
        $("#playerArea").css('height',mH+40);
        var params = {
            width: mW,
            height: mH,
            menu: "false",
            quality: "high",
            allowFullScreen: "true",
            bgcolor: "#865ca7",
            allowScriptAccess: "always",
            wmode: "Opaque"
        };
        var objAttr = {
            id: "media_player_box"
        }
        swfobject.embedSWF("${staticPath! }/static/lansha/static/flash/play.swf${staticVersion! }", "liveplayer", '100%', '100%', "9.0.0", null,null, params , objAttr,function(){
        });
        //主播 goplay
        $('#goplay').on('click', function() {
            $('.room-popup').show();
        });
        $(".change-close").on('click', function() {
            $('.room-popup').hide();
        });

        //分享
        window._bd_share_config = {
            "common": {
                "bdText": "${platFormName!}全民手游直播，快来与主播${room.nickname! }一起互动！",
                "bdUrl": '${liveUrl! }',
                "bdSize": "24",
                "bdstyle": 1
            }, "share": {}
        };
        with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];
    });
</script>
</body>
</html>