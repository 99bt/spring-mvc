<#import "/lansha/wap/common/lanshawap.ftl" as commons>
<!DOCTYPE html>
<html>
<head lang="en">
    <@commons.head />
    <@commons.headcss />
</head>

<body>
<!--conent start-->
<section class="index-topbar">
    <div class="logo">
       <a href="index.html">
         <img src="${staticPath! }/lansha/wap/static/images/logo.png" alt="">
       </a>
    </div>
    <div class="slgo">全民手游直播平台</div>
</section>
<!--导航-->
<section class="index-nav">
    <div class="navs">
	    <div class="swiper-wrapper">
	        <div class="swiper-slide"><a href="index.html">推荐</a></div>
	        <div class="swiper-slide <#if entity??><#else>on</#if>"><a href="liveList.html">全部直播</a></div>
	        <#list hotGamesDH?if_exists as item>
	          <div class="swiper-slide <#if entity??><#if item.id?default('')==entity.id>on</#if></#if>"><a href="liveList.html?id=${item.id! }">${item.name! }</a></div>
	        </#list>
	    </div>
    </div>
    <div class="move-item"></div>
</section>
<#if entity??>
<!--游戏头部-->
<section class="game-header">
    <div class="mask"></div>
    <img src="${uploadPath! }${entity.background! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';"/>
    <div class="game-info">
        <img src="${uploadPath! }${entity.icon! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';" alt="" class="game-icon">
        <p class="game-name">${entity.name! }</p>
        <p class="game-roomsnum">正在直播: ${totalNum}</p>
    </div>
</section>
</#if>
<!--幻灯片-->
<section class="siderbar hide">
    <div class="swiper-wrapper">
        <div class="swiper-slide"><img src="${staticPath! }/lansha/wap/upload/banner1.png" /></div>
        <div class="swiper-slide"><img src="${staticPath! }/lansha/wap/upload/banner1.png" /></div>
    </div>
    <div class="pagination"></div>
</section>
<!--直播列表-->
<section class="recommend-live clearfix">
    <ul class="live-list mgt10 clearfix">   
      <#list roomlist?if_exists as item> 
         <#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />    
        <li>
            <a href="${url! }">
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
<!--conent end-->
<!--script start-->
<@commons.footcommonjs />

<script>
    new LSWAP().init();
    new LSWAP().getData('${contextPath! }/wap/nextLiveList.html<#if entity??>?id=${entity.id! }</#if>','.live-list');
</script>
<!--script end-->
<!--counter-->
<div class="counter">
</div>
</body>
</html>