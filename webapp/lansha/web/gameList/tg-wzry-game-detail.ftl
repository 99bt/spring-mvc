<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta />
    <Meta name="keywords" content="${entity.seo! }">
    <Meta name="description" content="${entity.seo! }">
    <title>${(entity.name)! }-${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/game_new.css${staticVersion! }">
</head>
<body>
<@lansha.head index="3"/>
<div class="container <#if (luck)?default('1')== '0'>login</#if>" style="background: url('${staticPath! }/static/lansha/upload/tuiguang_lottery_bg.jpg') no-repeat center top">
      <!--登录状态用 class="container login" -->
    <div class="tips-box">
        <span class="gift-icon"></span>
        <p class="tips-title">提示 <a href="javascript:;" class="close fr">x</a></p>
        <p class="tips-con">恭喜您！获得1Q币<br/>点下方按钮进入直播间点礼包领取 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
        <a href="activityToLive.html?id=${id! }" class="tips-go">进入直播间</a>
    </div>
    <#if (luck)?default('0')== '1'>
    <!--未登录用户可见 start-->
    <a href="javascript:;" class="lottery-select <#if (luck)?default('1')== '0'>hide</#if>"></a>
    <div class="lottery-box <#if (luck)?default('1')== '0'>hide</#if>">
        <div class="lottery-con clearfix">
            <div class="lottery fl" id="lottery">
                <ul>
                    <li class="lottery-unit lottery-unit-0">
                        <img src="${staticPath! }/static/lansha/upload/lottery/lottery_0.png" alt="">
                        <i></i>
                    </li>
                    <li class="lottery-unit lottery-unit-1">
                        <img src="${staticPath! }/static/lansha/upload/lottery/lottery_1.png" alt="">
                        <i></i>
                    </li>
                    <li class="lottery-unit lottery-unit-2">
                        <img src="${staticPath! }/static/lansha/upload/lottery/lottery_10.png" alt="">
                        <i></i>
                        <span class="corner corner1"></span>
                    </li>
                    <li class="lottery-unit lottery-unit-3">
                        <img src="${staticPath! }/static/lansha/upload/lottery/lottery_3.png" alt="">
                        <i></i>
                    </li>
                    <li class="lottery-unit lottery-unit-9">
                        <img src="${staticPath! }/static/lansha/upload/lottery/lottery_11.png" alt="">
                        <i></i>
                        <span class="corner corner2"></span>
                    </li>
                    <li class="lottery_btn" id="lottery_btn" data-apiurl="../api/api.php?act=cj">
                        <a href="javascript:;">当前有<em id="lottery_num">1</em>次抽奖机会</a>
                    </li>
                    <li class="lottery-unit lottery-unit-4">
                        <img src="${staticPath! }/static/lansha/upload/lottery/lottery_4.png" alt="">
                        <i></i>
                    </li>

                    <li class="lottery-unit lottery-unit-8">
                        <img src="${staticPath! }/static/lansha/upload/lottery/lottery_8.png" alt="">
                        <i></i>
                    </li>
                    <li class="lottery-unit lottery-unit-7">
                        <img src="${staticPath! }/static/lansha/upload/lottery/lottery_7.png" alt="">
                        <i></i>
                    </li>
                    <li class="lottery-unit lottery-unit-6">
                        <img src="${staticPath! }/static/lansha/upload/lottery/lottery_6.png" alt="">
                        <i></i>
                    </li>
                    <li class="lottery-unit lottery-unit-5">
                        <img src="${staticPath! }/static/lansha/upload/lottery/lottery_5.png" alt="">
                        <i></i>
                    </li>
                </ul>
            </div>
            <div class="lottery-show fl">
                <img src="${staticPath! }/static/lansha/upload/lottery_show_p.png" alt="">
                <div class="qr-box">
                    <img src="${uploadPath! }/app/${lanshaLogo! }-type1.png${staticVersion! }" alt="二维码">
                </div>
            </div>
        </div>
    </div>
    <!--未登录用户可见 end-->
    </#if>
    <div class="details-title">
        <div class="left-show fl">
            <p class="title-gamename">${entity.name! }</p>
            <img src="${uploadPath! }${entity.icon! }" class="title-gameicon" alt="游戏图标">
            <#if gameDownLoadSwitch>            
             <a href="<#if (entity.iosUrl)?default('')==''>appdownload.html<#else>${(entity.iosUrl)! }</#if>" class="title-gamedownload ios fl">下载iPhone版</a>
             <a href="<#if (entity.androidUrl)?default('')==''>appdownload.html <#else> ${entity.androidUrl! }</#if>" class="title-gamedownload and fl">下载Android版</a>
            </#if>
        </div>
        <div class="center-show fl">
            ${entity.resource! }
        </div>
        <div class="right-show fl">
            <img src="${staticPath! }/static/lansha/upload/tuiguang_right_show.png" alt="">
        </div>
    </div>
  
    
    <div class="layout details-box clearfix">
        <div class="details-box-title clearfix">
            <a href="tg_wzry_gameLive.html?id=${id! }&luck=${luck! }&tolive=1" class="details-btn fl Live">在线直播</a>
            <a href="tg_wzry_gameDetail.html?id=${id! }&luck=${luck! }" class="details-btn fl details-cur Details">游戏详情</a>
        </div>
        <p class="details-box-game">${(entity.name)! }游戏截图</p>

        <div class="slider">
            <div class="picScroll-left">
                <#if screens?size?default(0) !=0 >
                <div class="hd">
                    <div class="next slideBtn"></div>
                    <div class="prev slideBtn"></div>
                </div>
                </#if>
                <div class="bd">
                    <ul class="picList">
                        <#list screens?if_exists as screen>
                        <li>
                            <div class="pic"><a href="${uploadPath! }${screen! }${staticVersion! }" target="_blank"><img src="${uploadPath! }${screen! }${staticVersion! }" /></a></div>
                        </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
        <div class="details-box-gameCon">
            <p class="details-gameCon-game">${(entity.name)! }游戏详情</p>
            <p class="details">${(entity.briefIntro)! }</p>
        </div>
    </div>
</div>
<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<!--[if lt IE 9]>
  <script src="https://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<![endif]-->
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/lottery_tg.js"></script>
</body>
</html>