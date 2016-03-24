<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html>
<head lang="en">
    <@lansha.meta keywords=""/>
    <title>${platFormName! }-新生月抽奖</title>
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/jquery.xBox.min.css${staticVersion! }"/>
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/lottery.css${staticVersion! }">
</head>

<body>
<#-- conent start -->
<div class="lottery-header">
    <div class="layout clearfix">
        <a class="lottery-logo fl" href="${contextPath! }/index.html">蓝鲨TV</a>
        <#if userIsLogin>
       		<ul class="lottery-link fr" data-islogin="1">
           		<a class="lottery-reg lottery-user" href="${contextPath! }/user/center.html">${userLogin.nickname! }</a>
            	<a class="lottery-login" href="logout.html">退出</a>
        	</ul>
        <#else>
        	<ul class="lottery-link fr" data-islogin="0">
            	<a class="lottery-reg" href="${contextPath! }/register.html">注册</a>
            	<a class="lottery-login" href="javascript:location.href=LOGIN_URL">登录</a>
        	</ul>
        </#if>
    </div>
</div>
<div class="lottery-main">
    <div class="layout clearfix">
        <div class="lottery-left fl">
          <#if userIsLogin>
        	<h2 class="lottery-chance">您当前有<span id="chance-num" data-apiurl="remainTimes.html" alt="0">-</span>次抽奖机会</h2>
          <#else>
        	<h2 class="lottery-noLogin"><a href="javascript:location.href=LOGIN_URL">登录</a>即可参与抽奖</h2>
          </#if>
            <div class="lottery-ask clearfix"><span>如何获得更多获奖机会？</span></div>
            <div class="lottery-flash">
                <div class="lottery-dial">
                    <div class="rotate-bg"></div>
                    <div class="dial-btn"></div>
                    <div class="lottery-star"><img data-apiurl="action.html" src="${staticPath! }/static/lansha/static/images/rotate-static.png" id="lotteryBtn"  alt="1"></div>
                </div>
            </div>
        </div>
        <#-- 中奖了 -->
        <div class="popup-bg"></div>
        <div class="lottery-popup">
            <h2>恭喜你中奖！</h2>
            <h3>获得<span></span>一个，请正确填写真实个人信息，便于客服人与发放奖励。</h3>
            <ul class="address-list">
            	<form id="lotteryForm" method="post" action="receiver.html">
                	<li class="mgt20"><input id="receiverID" name="receiver.realname"  class="lottery-int01" type="text" value="" maxlength="10" placeholder="请输入收件人姓名" /></li>
                	<li class="mgt20"><input id="receiverAddresse" name="receiver.address"  class="lottery-int01" type="text" maxlength="40" value="" placeholder="请输入收货地址" /></li>
                	<li class="mgt20"><input id="receiverPhone" name="receiver.telphone"  class="lottery-int01" type="text" maxlength="11" value="" placeholder="请输入收件人电话" /></li>
                	<li><a class="lottery-int01 lottery-btn01">确定</a></li>
                </form>
            </ul>
        </div>
        <div class="lottery-virtual">
            <h2>恭喜你中奖！</h2>
            <h3>获得<span></span>，请在我的中奖记录里查询。</h3>
            <ul class="address-list">
                <li><a class="lottery-int01 lottery-btn02">确定</a></li>
            </ul>
        </div>
        <#-- 中奖了 end -->
        <div class="lottery-right fr">
            <div class="lottery-tips"></div>
            <div class="lottery-invite mgt15">
                <p>邀请注册获得更多抽奖机会</p>
                <div class="lottery-copy mgt15 clearfix">
                    <input class="lottery-int fl" type="text" data-apiurl="${staticPath! }/static/lansha/static/flash/ZeroClipboard.swf" value="${userActivityLink! }" />
                    <span class="copy-btn fl">复制</span>
                </div>
            </div>
            <div class="lottery-show">
                <div class="show-slide clearfix">
                    <span class="show-one cur fl">中奖展示窗</span>
                    <span class="show-two fl">我的中奖纪录</span>
                </div>
                <div class="show-list">
                    <div class="slide-one" id="history" data-apiurl="list.html">
                        <ul>

                        </ul>
                    </div>
                    <div class="slide-one" id="mylist" class="prize-my" style="display: none;" data-apiurl="mylist.html" data-linkurl="${contextPath! }/user/record.html" data-loginurl="javascript:location.href=LOGIN_URL">
                        <ol >
                            
                        </ol>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="layout">
        <h3 class="lottery-title">活动规则</h3>
        <h4 class="lottery-time">
            活动时间：即日起 至 2016-1-28 23:59:59
        </h4>
        <div class="lottery-text">
            <p>一、如何获得抽奖次数：</p>
            <p class="lottery-num">1.完成注册可获得1次抽奖机会</p>
            <p class="lottery-num">2.完成注册后的第2、3、4天，每天登录可获得1次抽奖机会，且在直播间内第1次领取虾米，可再获得1次抽奖机会</p>
            <p class="lottery-num">3.将自己活动页面生成的链接分享给朋友，朋友通过此链接完成注册，你和朋友都可以获得1次抽奖机会；若之后你朋友又邀请其他朋友成功注册，你、你朋友、你朋友的朋友都能获得1次抽奖机会；依此类推扩散，三度人脉为一轮</p>
            <p class="lottery-num">4.你邀请的朋友在完成注册后又成为了蓝鲨主播，则你和朋友都可额外获得5次抽奖机会</p>
        </div>
        <div class="lottery-text">
            <p>二、虾米礼物随中奖即时到账，其他虚拟奖品在用户中奖后由客服人员在10个工作日内进行发放</p>
        </div>
        <div class="lottery-text">
            <p>三、所有实物奖励则根据用户所填写的个人信息于中奖15个工作日内进行发放，如有信息不全或信息有误则视为用户放弃领奖</p>
        </div>
        <div class="lottery-text">
            <p>四、本活动严禁使用任何作弊手段进行恶意获取抽奖机会等行为，一经发现取消所有奖励</p>
        </div>
    </div>
    <div class="layout">
        <h3 class="lottery-title">小贴士</h3>
        <div class="lottery-text">
            <p>疯狂金字塔分享奖励模式指的是：如果你通过分享链接邀请你朋友完成注册，当那个朋友又邀请了其他人完成注册时，你同样也能获得相应的抽奖机会哦，以此类推直至三度人脉位一轮。这意味着随着你邀请的朋友越来越多，这些朋友所邀请其他人带来的抽奖机会奖励，站在金字塔顶的你同样能享受到。你还在等什么呢？快去邀请小伙伴们吧！</p>
        </div>
        <div class="lottery-text">
            <p>一、邀请朋友时，分享链接一定要用自己在活动页面所复制的哦</p>
        </div>
        <div class="lottery-text">
            <p>二、虾米礼物可以赠送主播</p>
        </div>
        <div class="lottery-text">
            <p>三、当赢得虚拟奖品的时候可以在我的中奖纪录里查询</p>
        </div>
        <div class="lottery-text">
            <p>四、当赢得实物奖品的时候一定要记得填写个人信息才能顺利获得奖励</p>
        </div>
        <div class="lottery-text">
            <p><br /></p>
            <p>本活动最终解释权归蓝鲨TV拥有，若有问题可以加官方QQ交流群：527573589，找客服人员寻求帮助。</p>
        </div>
    </div>
    <div class="lottery-footer">
        <div class="lottery-flogo">
            <a href="index.html"></a>
        </div>
        <p><@lansha.footTitle /></p>
    </div>
    <ul class="lottery-maplink">
        <li class="maplink-one">活动介绍</li>
        <li class="maplink-one">抽奖转盘</li>
        <li class="maplink-one">活动规则</li>
        <li class="maplink-one">小贴士</li>
        <li class="maplink-one">返回顶层</li>
    </ul>
</div>
<#-- conent end -->
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/ZeroClipboard.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.xBoxV1.2.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.nicescroll.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jQueryRotate.2.2.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.easing.min.js"></script>
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/lottery.js"></script>
<script type="text/javascript">
    var Android = navigator.userAgent.match(/Android/i);
    var iPhone = navigator.userAgent.match(/iPhone/i);
    var iPad = navigator.userAgent.match(/iPad/i);
    if (Android || iPhone|| iPad) {
        $('.lottery-maplink').remove();
        $('body').addClass('mb');
    }
</script>
</body>
</html>