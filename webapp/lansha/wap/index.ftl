<#import "/lansha/wap/common/lanshawap.ftl" as commons>
<!DOCTYPE html>
<html>
<head lang="en">
    <@commons.head />
    <@commons.headcss />
</head>
<script type="text/javascript">
	function liveto(id){
         location.href='live.html?id='+id;	
    }
</script>
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
	        <div class="swiper-slide on"><a href="index.html">推荐</a></div>
	        <div class="swiper-slide"><a href="liveList.html">全部直播</a></div>
	        <#list hotGamesDH?if_exists as item>
	          <div class="swiper-slide"><a href="liveList.html?id=${item.id! }">${item.name! }</a></div>
	        </#list>
	    </div>
     </div>
    <div class="move-item"></div>
</section>
<!--幻灯片-->
<section class="siderbar">
    <div class="swiper-wrapper">
        <#list bannerList?if_exists as item>
         <div class="swiper-slide"><img src="${uploadPath! }${item.img! }"  <#if item.type?default('') == '1'>onclick="location.href='${item.linkUrl! }'"<#elseif item.type?default('') == '0'>onclick="liveto(${item.roomId! })"</#if>  onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';"/></div>
        </#list>
    </div>
    <div class="pagination"></div>
</section>
<!--推荐主播-->
<section class="recommend-boger clearfix">
    <div class="swiper-wrapper">
        <#list rooms?if_exists as item> 
         <#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
        <div class="swiper-slide">
            <a href="${url! }">
                <img src="${uploadPath! }${item.userIcon! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/live-icon.png${staticVersion! }';" alt="">
                <p>${item.nickname! }</p>
            </a>
        </div>
        </#list>
    </div>
</section>
<!--直播推荐-->
<section class="recommend-live clearfix">
    <div class="rlive-title clearfix">
        <h2 class="fl">热门推荐</h2>
        <span class="fr"><a href="liveList.html">更多<img src="${staticPath! }/lansha/wap/static/images/move-item-btn.png" alt=""></a></span>
    </div>
    <ul class="live-list mgt10 clearfix">
        <#list hotRooms?if_exists as item> 
        <li>
           <#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
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

<!--分类显示 -->
<#list hotGamesDH?if_exists as item1>
  <#if item1.ywUserRooms?size?default(0) !=0>
   <section class="recommend-live clearfix">
    <div class="rlive-title clearfix">
        <h2 class="fl">${item1.name! }</h2>
        <span class="fr"><a href="liveList.html?id=${item1.id! }">更多<img src="${staticPath! }/lansha/wap/static/images/move-item-btn.png" alt=""></a></span>       
    </div>
    <ul class="live-list mgt10 clearfix">
        <#list item1.ywUserRooms?if_exists as item> 
        <li>
           <#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
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
  
  </#if>
 

</#list>



<!--下载bar-->
<div class="download-bar">
    <div class="fl left">
        <img class="fl" src="${staticPath! }/lansha/wap/upload/appicon.png" alt="蓝鲨tv">
        <p class="fl">更多精彩游戏直播 <br>
            尽在手机蓝鲨TV</p>
    </div>
    <div class="right"><a href="${contextPath! }/doAppDownload.html?type=1" class="download-btn">下载即送Q币</a></div>
</div>
<!--conent end-->
<!--script start-->

<@commons.footcommonjs />
<script>
    new LSWAP().init();
   <!-- new LSWAP().getData('${contextPath! }/wap/nextHotRooms.html','.live-list'); -->
</script>
<!--script end-->
<!--counter-->
<div class="counter">
</div>
</body>
</html>