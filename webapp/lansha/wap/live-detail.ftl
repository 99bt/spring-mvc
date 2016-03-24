<#import "/lansha/wap/common/lanshawap.ftl" as commons>
<!DOCTYPE html>
<html>
<head lang="en">
    <@commons.head />
    <@commons.headcss />
</head>

<body>
<!--conent start-->
<section class="live-detail clearfix">
    <div class="play">
        <div class="watchNum" id="top_area">
            <a href="${contextPath! }/wap/index.html" class="fl back"></a><p class="fr">在线观众：${room.onLineNumber! }</p>
        </div>
        <div class="playerArea">
        	<div class="error <#if error?default("")==""> hide</#if>" id="error">
              	  ${(error)! }
            </div>
            <video class="videoBlock" src="${room.hls! }" x-webkit-airplay="true" webkit-playsinline="true" autoplay="autoplay" controls="controls">
                您的手机不支持html5标签
            </video>
        </div>
    </div>
    <a href="${contextPath! }/doAppDownload.html?type=1" class="down"><img src="${staticPath! }/lansha/wap/static/images/down-icon.png" alt="下载图片"></a>
    <div class="live-man">
        <img class="fl"src="${(user.headpic)! }" onerror="this.src='${staticPath! }/lansha/wap/upload/live-manIcon.jpg${staticVersion! }'" width="66" height="66" alt="主播头像">
        <p class="live-name live-msg fl">${room.nickname! }</p>
        <a href="javascript:;" class="fr share"></a>
        <a href="${contextPath! }/doAppDownload.html?type=1" class="fr follow">${room.fans?default(0) }</a>
        <p class="live-id live-msg fl">直播间ID：<em>${id!}</em></p>
    </div>
    <div class="list-title clearfix">
        <h3 class="fl">相关推荐</h3>
    </div>
    <div class="list clearfix">
        <ul class="game-ul">
        <#list list?if_exists as item>
	        <#assign url = "${contextPath! }/${item.idInt! }" />
            <li class="game-one fl<#if (item_index + 1) % 2 ==0> last</#if>">
                <a href="${url! }" class="fl">
                   	<img src="${uploadPath! }${item.liveImg! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';">
                    <div class="live-user">
                        <p class="liver fl">${item.nickname! }</p>
                        <p class="watchNum fr">${item.onLineNumber! }</p>
                    </div>
                </a>
                <h3 class="fl">${item.name! }</h3>
            </li>
           </#list>
        </ul>
    </div>
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
    
    <div class="noMoreData" style="display: none;">已经到底了呦</div>
</section>
<!--conent end-->
<@commons.footcommonjs />
<script>
    $(function(){
        new p().index("${id!}",4);
        if (!is_weixin()) {
            $('.playerArea, .play').height(($(window).width()/16)*9);
        };
        $('video').on('click',function(){
            $('#top_area').toggle();
        });
        $(".share").on("click",function(){
            $('html, body').animate({scrollTop: $(".play").height()}, 'slow');
            $(".fixed-bg").show();
        });
        $(".close-btn").on("click",function(){
            $(".fixed-bg").hide();
        });
        var lineH = $(".error").height();
        $(".error").css("line-height",lineH+"px");
    });
    window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"1","bdMiniList":false,"bdPic":"","bdStyle":"1","bdSize":"32"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
</script>
<!--script end-->
</body>
</html>