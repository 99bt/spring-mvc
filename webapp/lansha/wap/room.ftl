<#import "/lansha/wap/common/lanshawap.ftl" as commons>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>${room.name! }-${platFormName!}</title> 
    <@commons.head />
    <@commons.headcss />  
</head>

<body>
<!--conent start-->
<!--播放器-->
<section class="play">
    <div class="watchNum" id="top_area">
        <a href="index.html" class="fl back"></a><p class="fr">在线观众：${room.onLineNumber! }</p>
    </div>

    <div class="playerArea">
        <div class="error <#if room.online == 1>hide</#if>" id="error">
            主播正在来的路上~
        </div>
        <div id="video-show">
            <i class="playBtn" id="playBtn"></i>
            <img src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw=="/>
        </div>
        <div class="load-container load1" id="loading">
            <div class="loader">Loading...</div>
        </div>
        <!--http://183.134.12.62/sohu.vodnew.lxdns.com/sohu/s26h23eab6/241/194/6uzYFfTWTtGiazf8jUH88A.mp4-->
        <!--http://wslive-hls.kascend.com/chushou_live/7cdd326781cd44dfa8f4a130c9ca0dc8/playlist.m3u8 ${room.hls! }-->
        <video class="videoBlock" id="videoPlayer" src="${room.hls! }" x-webkit-airplay="true" webkit-playsinline="true" controls="" autobuffer="true">
            您的手机不支持html5标签
        </video>
    </div>
</section>
<!---->
<section class="tabs">
    <a href="javascript:;" class="active">聊天</a><a href="javascript:;">主播详情</a><a href="javascript:;">精彩直播</a>
</section>
<section id="tabs-container" class="swiper-container">
    <div class="swiper-wrapper">
        <div class="swiper-slide">
            <div class="content-slide">
                <div class="talk-area">
                    <img class="avatar" src="${uploadPath! }${room.userIcon! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt="">
                    <input type="text" class="talk-input" value="" placeholder="说点什么吧...">
                    <a href="${contextPath! }/doAppDownload.html?type=1" class="btn">发送</a>
                </div>
                <ul class="talk-list" id="talk-list">
                  <#list chatInfoList?if_exists as item>
                    <#if item.userName?default('') == ""> 
                     <li>${item.content! }</li>
                   <#else>
                     <li>${item.userName! }：<span class="talk-content">${item.content! }</span></li>                   
                   </#if>
                  </#list>
                </ul>
            </div>
        </div>
        <div class="swiper-slide">
            <div class="content-slide">
                <div class="boger-info">
                    <img class="avatar" src="${uploadPath! }${room.userIcon! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt="">
                    <p class="uname">${room.nickname! }</p>
                    <p class="room-game">正在直播: <em>${room.gameName! }</em></p>
                </div>
                <div class="doarea">
                    <a href="${contextPath! }/doAppDownload.html?type=1" class="btn"><img src="${staticPath! }/lansha/wap/static/images/icon-like.png" alt="">订阅</a>
                    <a href="javascript:;" class="btn" id="share"><img src="${staticPath! }/lansha/wap/static/images/icon-share.png" alt="">分享</a>
                </div>
                <div class="room-note">
                    <div class="title">直播公告</div>
                    <div class="content">
                        ${room.notice! }
                    </div>
                </div>
            </div>
        </div>
        <div class="swiper-slide">
            <div class="content-slide">
                <!--推荐视频-->
                <ul class="live-list mgt10 clearfix">
                  <#list rooms?if_exists as item> 
                     <#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />                                         
                    <li>
                        <a href="${url!}">
                            <div class="mask"></div>
                            <img class="room-pic" src="${uploadPath! }${item.liveImg! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';" alt="">
                        </a>
                        <div class="room-info">
                            <a href="live.html?id=${item.idInt! }"><img class="avatar" src="${uploadPath! }${item.userIcon! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt=""></a>
                            <a href="live.html?id=${item.idInt! }"><h2 class="">${item.name! }</h2></a>
                            <p>
                                <span class="fl uname">${item.nickname! }</span>
                                <span class="fr viewnum">${item.onLineNumber! }</span>
                            </p>
                        </div>
                    </li>
                  </#list>
                </ul>

            </div>
        </div>
    </div>

    <div class="gift-area">
        <a href="${contextPath! }/doAppDownload.html?type=1" class="gift01"><img src="${staticPath! }/lansha/wap/static/images/gift01.png" alt=""></a>
        <a href="${contextPath! }/doAppDownload.html?type=1" class="gift02"><img src="${staticPath! }/lansha/wap/static/images/gift02.png" alt=""></a>
        <a href="${contextPath! }/doAppDownload.html?type=1" class="gift03"><img src="${staticPath! }/lansha/wap/static/images/gift03.png" alt=""></a>
    </div>
</section>

<!--下载bar-->
<section class="download-bar">
    <div class="fl left">
        <img class="fl" src="${staticPath! }/lansha/wap/upload/appicon.png" alt="蓝鲨tv">
        <p class="fl">更多精彩游戏直播 <br>
            尽在手机蓝鲨TV</p>
    </div>
    <div class="right"><a href="${contextPath! }/doAppDownload.html?type=1" class="download-btn">下载即送Q币</a></div>
</section>
<!--分享-->
<div class="fixed-bg">
    <div class="bdsharebuttonbox">
        <div class="ff fl clearfix">
            <div class="san fl clearfix">
                <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                <p>新浪微博</p>
            </div>
            <div class="san fl clearfix">
                <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                <p>微信好友</p>
            </div>
            <div class="san fl clearfix">
                <a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a>
                <p>QQ好友</p>
            </div>
        </div>
        <div class="ff fl clearfix">
            <div class="san fl clearfix">
                <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
                <p>QQ空间</p>
            </div>
            <div class="san fl clearfix">
                <a href="#" class="bds_copy" data-cmd="copy" title="分享到复制网址"></a>
                <p>拷贝</p>
            </div>
        </div>
        <span class="close-btn">取消</span>
    </div>
</div>

<!--conent end-->
<!--script start-->

<@commons.footcommonjs />

<script>
    //ROOM
    var RoomImg = '${uploadPath! }${room.liveImg! }';
    $(function() {
        new LSWAP().init();
        new LSWAP.room().init();
    });
    //share
    $("#share").on("click",function(){
        $(".fixed-bg").show();
    });
    $(".close-btn").on("click",function(){
        $(".fixed-bg").hide();
    });
    window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"1","bdMiniList":false,"bdPic":"","bdStyle":"1","bdSize":"32"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];

</script>

<!--script end-->
<!--counter-->
<div class="counter">
</div>
</body>
</html>