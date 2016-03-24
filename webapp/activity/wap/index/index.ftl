<#import "/lansha/wap/common/lanshawap.ftl" as lansha>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <@lansha.meta keywords="手游直播、游戏直播、直播、手游主播、主播、王者荣耀、最强王者、王者、钻石、穿越火线、天天酷跑、节奏大师、自由之战、炉石传说"/>
    <title>${platFormName! }-王者荣耀</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/wzry_wap/static/css/jquery.xBox.min.css${staticVersion! }"/>    
	<link rel="stylesheet" href="${staticPath! }/static/lansha/wzry_wap/static/css/common.css${staticVersion! }" />
    <script>
    	<#-- 登录状态 1已登录 0未登录 -->
        var IS_LOGIN = isLogin; 
        <#-- 下载地址-->
        var DOWN_URL = '${contextPath! }/doAppDownload.html?type=1';
    </script>    
</head>

<body>

<#-- conent start -->
<div class="banner">
    <div class="banner-pic autoImg"><img src="${staticPath! }/static/lansha/wzry_wap/static/images/wzry_banner.jpg" alt=""/></div>
    <div class="user-box">
        <#if userIsLogin>
        	<span>${userLogin.username! }</span>
        <#else>
        	<span data-apiurl="login.html" id="login">登录</span>|<span data-apiurl="register.html" id="reg">注册</span>
        </#if>
    </div>
</div>
<div class="lottery-box common-border">
    <div class="lottery-main">
        <!-- 奖池 -->
        <div class="lottery" id="lottery">
            <ul>
                <li class="lottery-unit lottery-unit-0">
                    <img src="${staticPath! }/static/lansha/wzry_wap/upload/lottery_0.png" alt="">
                    <i></i>
                </li>
                <li class="lottery-unit lottery-unit-1">
                    <img src="${staticPath! }/static/lansha/wzry_wap/upload/lottery_1.png" alt="">
                    <i></i>
                </li>
                <li class="lottery-unit lottery-unit-2">
                    <img src="${staticPath! }/static/lansha/wzry_wap/upload/lottery_10.png" alt="">
                    <i></i>
                    <span class="corner corner1"></span>
                </li>
                <li class="lottery-unit lottery-unit-3">
                    <img src="${staticPath! }/static/lansha/wzry_wap/upload/lottery_3.png" alt="">
                    <i></i>
                </li>

                <li class="lottery-unit lottery-unit-9">
                    <img src="${staticPath! }/static/lansha/wzry_wap/upload/lottery_11.png" alt="">
                    <i></i>
                    <span class="corner corner2"></span>
                </li>
                <li class="lottery_btn" id="lottery_btn" data-apiurl="action.html">
                    <a href="javascript:;">当前有<em id="lottery_num">${userStock! }</em>次抽奖机会</a>
                </li>
                <li class="lottery-unit lottery-unit-4">
                    <img src="${staticPath! }/static/lansha/wzry_wap/upload/lottery_4.png" alt="">
                    <i></i>
                </li>

                <li class="lottery-unit lottery-unit-8">
                    <img src="${staticPath! }/static/lansha/wzry_wap/upload/lottery_8.png" alt="">
                    <i></i>
                </li>
                <li class="lottery-unit lottery-unit-7">
                    <img src="${staticPath! }/static/lansha/wzry_wap/upload/lottery_7.png" alt="">
                    <i></i>
                </li>
                <li class="lottery-unit lottery-unit-6">
                    <img src="${staticPath! }/static/lansha/wzry_wap/upload/lottery_6.png" alt="">
                    <i></i>
                </li>
                <li class="lottery-unit lottery-unit-5">
                    <img src="${staticPath! }/static/lansha/wzry_wap/upload/lottery_5.png" alt="">
                    <i></i>
                </li>
            </ul>
        </div>
    </div>
    <div class="awards mgt8" style="display:block">
        <h4 class="awards-title">获奖记录</h4>
        <ul class="awards-list" id="awards-list">
        	<li class="clearfix" id="awards_n1" style="display: none;"><span class="fl">获得2个Q币</span><a href="javascript:;" class="get fr">领取</a></li>
            <#list myGiftStockList?if_exists as item>
            	<li class="clearfix">
        			<#if item.status?default('0') == '4'>
        				<span class="fl">${(item.createTime?string("yyyy-MM-dd"))!}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;获得一个${item.giftName! }</span>
        			<#else>
        				<span class="fl">${(item.createTime?string("yyyy-MM-dd"))!}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;获得${item.giftName! }</span>
        			</#if>
        			<#if loginMethod?default('') == '0'>
        				<a href="javascript:;" class="look fr">查看</a>
        			<#else>
        				<a href="javascript:;" class="get fr">领取</a>
        			</#if>  			
        		</li>     		
        	</#list>
        </ul>
    </div>
</div>
<div class="chance-explain" style="margin: 10px auto;">
    <ul class="circle-list clearfix">
        <!--下载地址-->
        <li class="circle-one fl" style="margin-left: 10%;"><a class="autoImg" href="${contextPath! }/doAppDownload.html?type=1"><img src="${staticPath! }/static/lansha/wzry_wap/static/images/circle02.png" alt="" /></a></li>
        <li class="circle-one fr" style="margin-right: 10%;"><a class="autoImg" href="javascript:;" id="sharebtn"><img src="${staticPath! }/static/lansha/wzry_wap/static/images/circle03.png" alt="" /></a></li>
    </ul>
</div>
<div class="common-border rule-box">
    <div class="autoImg"><img src="${staticPath! }/static/lansha/wzry_wap/static/images/movie-title02.png" alt=""/></div>
    <p class="rule-text">
        1.活动时间：即日起至活动结束<br/>
        2.如何获得更多抽奖次数：
    </p>
    <dl class="rule-list">
        <dt class="rule-cross">
            <span>序号</span>
            <span>方法</span>
            <span>获得抽奖机会</span>
        </dt>
        <!--<dd class="rule-cross">
            <span>1</span>
            <span>成功完成注册</span>
            <span>2次</span>
        </dd>-->
        <dd class="rule-cross">
            <span>1</span>
            <span>下载并登录蓝鲨TV客户端</span>
            <span>5次</span>
        </dd>
        <!--<dd class="rule-cross">
            <span>3</span>
            <span>分享至朋友圈</span>
            <span>1次</span>
        </dd>-->
        <dd class="rule-cross">
            <span>2</span>
            <span>成功邀请好友注册（金字塔分享奖励模式）</span>
            <span>1次/人(可获得抽奖次数无上限）</span>
        </dd>
    </dl>
    <div class="rule-text">备注：邀请朋友时，通过邀请链接完成的注册才有效</div>
    <div class="rule-text">3. 疯狂金字塔分享奖励模式举例：你通过分享链接邀请2个人完成注册，这2个人分别邀请了3个人，被邀请的这3个人又分别邀请了4个人，那么你可以获得32次抽奖机会</div>
    <div class="rule-text">4. 奖励发放：</div>
    <div class="rule-text rule-child clearfix">(1)直播礼物即时到账，可以赠送给喜欢的主播</div>
    <div class="rule-text rule-child clearfix">(2)所有中奖的游戏点券、首充、稀有英雄以Q币形式发放，在中奖后10个工作日内由工作人员发放。</div>
    <div class="rule-text rule-child clearfix">(3)实物奖励，根据用户填写的收货信息，在中奖15个工作日内发放（若信息不全或有误，则视为放弃领奖）
    </div>
    <div class="rule-text">5. 本活动严禁使用任何作弊手段进行恶意获取抽奖机会等行为，一经发现取消所有奖励，蓝鲨TV保留该活动的所有解释权</div>
    <div class="rule-text">6. 如有问题，请加官方QQ群：211423196，咨询客服人员</div>
</div>
<div class="download clearfix">
    <a href="${contextPath! }/doAppDownload.html?type=1&name=${name! }">
        <span class="autoImg fl"><img src="${staticPath! }/static/lansha/wzry_wap/static/images/logo.png" alt=""/></span>
        <span class="fl"><b>蓝鲨TV全民手游直播平台</b><br/>下载客户端获取5次抽奖机会</span>
        <span class="fr">免费下载</span>
    </a>
</div>
<!--登录注册popup-->
<div class="popup-bg"></div>
<div class="popup-box">
    <div class="popup-close"></div>
    <h2 class="popup-slide">
        <span class="cur" data-slide="reg">注册</span>
        <span data-slide="login">登录</span>
    </h2>
    <div class="popup-cbox clearfix">
        <div class="popup-reg clearfix">
            <ul class="login-list fl">
                <form id="frmUserRegMini" action="register.html">
                    <li class="login-one mgt15 clearfix">
                        <p><span class="infoArea fr miniiMPTEL"></span></p>
                        <input class="login-int01 login-int02 fl" id="miniTel" type="text" placeholder="请输入手机号" maxlength="11" data-tips="请输入您的手机号码">
                        <input type="hidden" name="ywUser.username" value="" id="miniTel1">
                        <a class="login-code fr" id="SendCode" type="button" data-rcode="1" data-apiurl="${contextPath! }/rcode.html">发送<br />验证码</a>
                    </li>
                    <li class="login-one mgt15 clearfix">
                        <p><span class="infoArea fr iRcodeMini"></span></p>
                        <input class="login-int01" type="text" name="rcode" id="rcode" placeholder="验证码" maxlength="6" data-tips="请输入验证码">
                    </li>
                    <li class="login-one mgt15 clearfix">
                        <p><span class="infoArea fr iPasswordM"></span></p>
                        <input class="login-int01" type="password" id="miniPassword" placeholder="设置密码" data-tips="请输入6-16位密码">
                    </li>
                    <input type="hidden" name="ywUser.password" id="pwdrehide" value="">
                    <input type="hidden" name="ywUser.mark" value="2" id="regMark">
                    <li class="login-one mgt15 clearfix">
                        <p><span class="infoArea fr iPasswordM2"></span></p>
                        <input class="login-int01" type="password" id="miniRePassword" placeholder="再次输入密码" data-tips="请再次输入密码">
                    </li>
                    <li class="login-one mgt8 clearfix">
                        <p class="login-agreement mgt15">
                           		 注册即默认同意<a href="${contextPath! }/agreement.html" target="_blank">蓝鲨TV《用户注册协议》</a>
                        </p>
                    </li>
                    <li class="login-one mgt15 clearfix">
                        <a class="login-btn" id="regsubmitMini" href="javascript:;">注册</a>
                        <p class="login-tips">已有账号？<a href="#">立即登录</a></p>
                    </li>
                </form>
            </ul>
        </div>
        <div class="popup-login clearfix" style="display: none;">
            <ul class="login-list">
                <form id="frmUserLoginMini" action="login.html">
                    <li class="login-one mgt15 clearfix">
                        <p><span class="infoArea loginiMPTEL"></span></p>
                        <input class="login-int01" type="text" id="loginMinTel" name="ywUser.username" maxlength="11" placeholder="请输入手机号" data-tips="请输入您的手机号"/>
                    </li>
                    <li class="login-one mgt15 clearfix">
                        <p><span class="infoArea loginiPassword"></span></p>
                        <input class="login-int01" type="password" id="loginMinpassword" placeholder="请输入密码" data-tips="请输入6-16位密码"/>
                    </li>
                    <input type="hidden" name="ywUser.password" id="pwdhidelogin" value="">
                    <input type="hidden" name="ywUser.mark" value="2" id="loginMark">
                    <li class="login-one mgt30 clearfix">
                        <a class="login-btn" id="frmSubmitMini" href="javascript:;">登录</a>
                        <p class="login-tips">没有账号？<a href="#">立即注册</a></p>
                    </li>
                </form>
            </ul>
        </div>
    </div>
</div>
<div class="popup-common">
    <div class="popup-close"></div>
    <div class="popup-cbox clearfix"></div>
    <h4 class="popup-text"></h4>
    <div class="popup-btns mgt20 clearfix">
        <a class="this-btn btn-true fl" href="javascript:;"></a>
        <a class="this-btn btn-false fr" href="javascript:;"></a>
    </div>
</div>
<!--发送站内验证码-->
<div class="popup-bg01"></div>
<div class="RcodeBox">
    <span>请输入验证码完成本次发送</span>
    <span class="wrongText" id="wrongText"></span>
    <span><input id="captchaInput" type="text" class="picCode" maxlength="6"></span>
    <div>
        <img id="captchaImage" data-apiurl="${contextPath! }/web/validate.html" width="80" class="codePic">
        <a class="changePic" id="changeCaptchaImage">看不清,换一个</a>
    </div>
    <div class="popup-btns mgt20 clearfix">
        <a class="this-btn btn-true fl" id="checkRcode" href="javascript:;">确认</a>
        <a class="this-btn btn-false fr" id="cancelBtn" href="javascript:;">取消</a>
    </div>
</div>
<!--conent end-->
<!--script start-->
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.xBox.min.js"></script>
<script>
    $(function () {
        new wzry();
    })
</script>
<@lansha.wxapijs />
<!--script end-->
</body>
</html>

<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?c6d8792206bf641e845a9c40aa9505a8";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>