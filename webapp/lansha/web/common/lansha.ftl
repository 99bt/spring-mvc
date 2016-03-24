<#-- 页头meta -->
<#macro meta keywords="" description="">
	<#-- 平台名称 -->
	<#assign platFormName = platFormName?default("") />
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="Yowant FE Team" />
    <#if description != "">
    	<meta name="Description" content="${description! }" />
   	</#if>
    <#if keywords != "">
   		<meta name="Keywords" content="${keywords! }" />
   	</#if>
    <meta property="wb:webmaster" content="9deea644efed966b" />
    <meta property="qc:admins" content="245115376764163016467164506045" />
    <meta property="qc:admins" content="202031257764163016467" />
    <meta name="baidu-site-verification" content="iglYtks4sV" />
	<script type="text/javascript">
	    var STATIC_PATH = '${contextPath! }';
	    var BASE_PATH = '${staticPath! }/static/lansha/';
	    var LOGIN_URL = '${contextPath! }/login.html?backUrl=${httpUrl! }';
	    var isLogin = <#if userIsLogin?default(false)>1<#else>0</#if>;
	</script>
	<link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/jquery.xBox.min.css${staticVersion! }">
	<!-- start Dplus --><script type="text/javascript">!function(a,b){if(!b.__SV){var c,d,e,f;window.dplus=b,b._i=[],b.init=function(a,c,d){function g(a,b){var c=b.split(".");2==c.length&&(a=a[c[0]],b=c[1]),a[b]=function(){a.push([b].concat(Array.prototype.slice.call(arguments,0)))}}var h=b;for("undefined"!=typeof d?h=b[d]=[]:d="dplus",h.people=h.people||[],h.toString=function(a){var b="dplus";return"dplus"!==d&&(b+="."+d),a||(b+=" (stub)"),b},h.people.toString=function(){return h.toString(1)+".people (stub)"},e="disable track track_links track_forms register unregister get_property identify clear set_config get_config get_distinct_id track_pageview register_once track_with_tag time_event people.set people.unset people.delete_user".split(" "),f=0;f<e.length;f++)g(h,e[f]);b._i.push([a,c,d])},b.__SV=1.1,c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.php?token=72160be13e82a3952a1a",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}}(document,window.dplus||[]),dplus.init("72160be13e82a3952a1a");</script><!-- end Dplus -->
</#macro>

<#-- 页头 -->
<#macro head index="" isBoxLogin="false" style="layout" showdiv="0">
	<#-- 是否登录 -->
	<#assign userIsLogin = userIsLogin?default(false) />
	<#-- 是否主播 -->
	<#assign isAnchor = userIsAnchor?default(false) />
	<#--
	-->
	<#assign userLogin = userLogin?default("") />
<!--仅首页的header添加header-index 如下-->
<#if showdiv == "0">
<div class="header-space"></div>
<div class="header header-nofix">
<#else>
<div class="header">
</#if>
	<div class="header-opacity"></div>
	<div class="header-zindex ${style! } clearfix">
        <a href="${contextPath! }/index.html" class="logo fl" title="${platFormName! }">${platFormName! }</a>
        <ul class="nav fl">
            <li<#if index == "1"> class="cur"</#if>><a href="${contextPath! }/index.html">首页<i class="show-line"></i></a></li>
            <li<#if index == "2"> class="cur"</#if>><a href="${contextPath! }/liveList.html">直播<i class="show-line"></i></a></li>
            <li<#if index == "3"> class="cur"</#if>><a href="${contextPath! }/gameCenter.html">游戏<i class="show-line"></i></a></li>
        </ul>
        <div class="header-recommend fl">
            <a class="recommend-link" href="${contextPath! }/seelive.html">逛逛</a>
            <div class="recommend-nlist">
                <h5 class="recommend-nlink" data-action="${contextPath! }/randLive.html"><a href="javascript:;">换一换</a></h5>
                <ul class="clearfix" >
                <#list randRooms?if_exists as item>
            	<#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
                    <li class="recommend-none <#if (item_index + 1) % 2 ==0>fr<#else>fl</#if>">
                        <a href="${url! }">
                            <img src="${uploadPath! }${item.liveImg! }"  onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';"  width="137" height="78" alt=""/>
                            <p>${item.name! }</p>
                        </a>
                    </li>
                </#list>
                <input id="randroomIds" type="hidden" value="${randIds! }" />
                </ul>
            </div>
        </div>
        <div class="header-search fl" data-gourl="${contextPath! }/dosearch.html">
            <input type="text" class="search-text fl" id="search_keyword"  placeholder="搜索房间,主播,游戏" />
            <input type="button" class="search-btn fl" value="" id="search_go"/>
        </div>
        <!--未登录-->
    	<#if isBoxLogin?default("")=="true">
	    	<div class="user-box fr <#if userIsLogin>hide</#if>" id="nologin">
	            <a class="login-this fl"  href="javascript:;">登录</a>
	            <a class="reg-this fl" href="javascript:;">注册</a>
	        </div>
    	<#else>
	    	<div class="user-box fr <#if userIsLogin>hide</#if>" id="nologin">
		        <a class="login-this fl"  href="javascript:location.href=LOGIN_URL">登录</a>
		        <a class="reg-this fl" href="${contextPath! }/register.html">注册</a>
	        </div>
        </#if>
    	<!--登录后-->
        <div class="user-box fr<#if !userIsLogin> hide</#if>" id="logined">
            <a href="${contextPath! }/user/center.html"><span id="nickname"><#if userIsLogin>${userLogin.nickname! }</#if></span></a>
            <span> | </span>
            <a href="${contextPath! }/logout.html">退出登录</a>
            <div class="user-lest">
                <ul>
                    <li><a href="${contextPath! }/user/center.html"><span class="icon1  fl"></span> 我的信息</a></li>
                    <li><a href="${contextPath! }/user/myAttention.html"><span class="icon2  fl"></span> 我的关注</a></li>
                    <li><a href="${contextPath! }/user/history.html"><span class="icon3  fl"></span> 观看历史</a></li>
                    <li><a href="${contextPath! }/logout.html"><span class="icon4  fl"></span> 退出登录</a></li>
                </ul>
            </div>
        </div>
        <!--登录后 end-->
    	
        <ul class="headerBtn fr">
	        <li>
	            <i class="btnicon camera fl"></i>
        		<#if isAnchor>
        		<#-- 主播 -->
                	<a href="${contextPath! }/user/myroom.html" target="_blank">我要开播</a>
        		<#elseif userIsLogin?default(false)>
        		<#-- 已登录 -->
                	<a href="${contextPath! }/user/becomeAnchor.html" target="_blank">做主播</a>
                <#else>
        		<#-- 未登录 -->
                	<a href="${store! }/activity/anchor-recruit.html" target="_blank">做主播</a>
        		</#if>
            </li>
            <li>
                <i class="btnicon download fl"></i>
                <a href="${contextPath! }/appdownload.html" target="_blank">下载</a>
            </li>
        </ul>
    </div>
</div>
</#macro>

<#-- 登录页头 -->
<#macro loginHead title="">
	<#-- 是否登录 -->
	<#assign userIsLogin = userIsLogin?default(false) />
	<#-- 是否主播 -->
	<#assign isAnchor = userIsAnchor?default(false) />
	<#--
	-->
	<#assign userLogin = userLogin?default("") />
<!--仅首页的header添加header-index 如下-->
<div class="header-space"></div>
<div class="header header-nofix">
	<div class="header-opacity"></div>
	<div class="header-zindex layout clearfix">
        <a href="${contextPath! }/index.html" class="logo fl" title="${platFormName! }">${platFormName! }</a>
        <ul class="nav fl">
            <li><a href="${contextPath! }/index.html">首页<i class="show-line"></i></a></li>
            <li><a href="${contextPath! }/liveList.html">直播<i class="show-line"></i></a></li>
            <li><a href="${contextPath! }/gameCenter.html">游戏<i class="show-line"></i></a></li>
        </ul>
        <div class="header-recommend fl">
            <a class="recommend-link" href="${contextPath! }/seelive.html">逛逛</a>
            <div class="recommend-nlist">
                <h5 class="recommend-nlink" data-action="${contextPath! }/randLive.html"><a href="javascript:;">换一换</a></h5>
                <ul class="clearfix" >
                <#list randRooms?if_exists as item>
            	<#assign url = "${contextPath! }${appsetting.getLivePath(item.idInt)! }" />
                    <li class="recommend-none <#if (item_index + 1) % 2 ==0>fr<#else>fl</#if>">
                        <a href="${url! }">
                            <img src="${uploadPath! }${item.liveImg! }"  onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';"  width="137" height="78" alt=""/>
                            <p>${item.name! }</p>
                        </a>
                    </li>
                </#list>
                <input id="randroomIds" type="hidden" value="${randIds! }" />
                </ul>
            </div>
        </div>
        <div class="header-search fl" data-gourl="${contextPath! }/dosearch.html">
            <input type="text" class="search-text fl" id="search_keyword"/>
            <input type="button" class="search-btn fl" value="" id="search_go"/>
        </div>
    	
        <ul class="headerBtn fr">
	        <li>
	            <i class="btnicon camera fl"></i>
        		<#if isAnchor>
        		<#-- 主播 -->
                	<a href="${contextPath! }/user/myroom.html" target="_blank">我要开播</a>
        		<#elseif userIsLogin?default(false)>
        		<#-- 已登录 -->
                	<a href="${contextPath! }/user/becomeAnchor.html" target="_blank">做主播</a>
                <#else>
        		<#-- 未登录 -->
                	<a href="${store! }/activity/anchor-recruit.html" target="_blank">做主播</a>
        		</#if>
            </li>
            <li>
                <i class="btnicon download fl"></i>
                <a href="${contextPath! }/appdownload.html" target="_blank">下载</a>
            </li>
        </ul>
    </div>
</div>
</#macro>
<#macro userCenter >
    <div class="user-title clearfix">
    	<div class="clearfix">
            <img class="user-faces fl" src="${(user.headpic)!}" onerror="this.src='${staticPath! }/static/lansha/upload/default.png${staticVersion! }'" alt="用户头像">
            <div class="user-namecon fl">
                <h3 class="user-name">${(user.nickname)!}&nbsp;&nbsp;ID&nbsp;${(user.idInt)!}</h3>
                <p class="user-money ">虾米: <em>${stock! }</em> 个</p>
            </div>
        </div>
    </div>
</#macro>

<#-- 页尾 -->
<#macro foot>
<div class="footer">
    <div class="footerNav">
        <div class="layout footerBox clearfix">
            <img src="${contextPath! }/static/lansha/static/images/footer_login.png" class="footer-logo fl" alt="尾部logo">
            <ul class="footer-ul fl">
                <li class="clearfix">
                    <span class="fl">直播帮助</span>
                    <a href="${contextPath! }/help-center.html">如何申请主播</a>
                    <a href="${contextPath! }/help-android.html">安卓手机直播教程</a>
                    <a href="${contextPath! }/help-iOS.html">苹果手机直播教程</a>
                </li>
                <li class="clearfix">
                    <span class="fl">关于我们</span>
                    <a href="${contextPath! }/about-aboutus.html">关于蓝鲨直播</a>
                    <a href="${contextPath! }/about-contactus.html">联系我们</a>
                </li>
                <li class="clearfix">
                    <span class="fl">官方Q群</span>
                    <a href="javascript:;">1群：211423196</a>
                    <a href="javascript:;">2群：540054104</a>
                    <a href="javascript:;">3群：517137399</a>
                </li>
                <li class="clearfix">
                    <span class="fl">关注我们</span>
                    <a href="javascript:;" class="wechat  fl"><img src="${contextPath! }/static/lansha/upload/weChat.png" alt="微信二维码"></a>
                    <a href="javascript:;" class="wechat weibo fl">
                        <img src="${contextPath! }/static/lansha/upload/weibo.png" alt="微博二维码">
                    </a>
                </li>
            </ul>
            <div class="QR-box fl">
                <p>APP下载</p>
                <img src="${uploadPath! }/app/${lanshaLogo! }-type1.png${staticVersion! }" alt="app二维码" width="124" height="124">
            </div>
            <div class="QR-box fl">
                <p>直播工具下载</p>
                <img src="${uploadPath! }/app/${lanshaLogo! }-type2.png${staticVersion! }" alt="工具下载二维码" width="124" height="124">
            </div>
        </div>
    </div>
</div>
</#macro>

<#-- 页尾文案 -->
<#macro footTitle>
    ${appsetting.getOption("LANSHA.FOOT.ABOUT")! }
</#macro>

<#-- 页尾js -->
<#macro footjs>
<!--[if lt IE 9]>
	<script src="https://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<![endif]-->
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.xBox.min.js"></script>
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/common.js${staticVersion! }"></script>
<#--
<script src="http://s95.cnzz.com/z_stat.php?id=1256981703&web_id=1256981703" language="JavaScript"></script>
-->
<!-- start Dplus Define -->
<script type="text/javascript">!function(a,b){var c,d;window.__dplusDefineCacheQueue=[],b.define=function(){window.__dplusDefineCacheQueue.push(Array.prototype.slice.call(arguments))},c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.define.php",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}(document,window.dplus);</script>
<!-- end Dplus Define -->
<script> 
<#--埋码-->
${actionAnalysisCode! }
</script>

</#macro>

<#-- 用户中心left菜单 -->
<#macro userLeft index="">
<div class="help-left fl">
    <h3 >个人中心</h3>
    <ul>
   <#if isAnchor>
   <li class="myMessage<#if index="1"> help-cur</#if>"><a href="center.html">我的资料</a></li>
        <li class="myAttention<#if index="2"> help-cur</#if>"><a href="myAttention.html">我的关注</a></li>
        <li class="myHistory<#if index="3"> help-cur</#if>"><a href="${contextPath! }/user/history.html">观看历史</a></li>
       <!--  <li class="myAdvices<#if index="4"> help-cur</#if>"><a href="#">我的消息</a></li> -->
        <li class="myPrize<#if index="4"> help-cur</#if>"><a href="${contextPath! }/user/record.html">我的奖品</a></li>
        <li class="myRoom <#if index="5"> help-cur</#if>"><a href="${contextPath! }/user/myroom.html">我的直播间</a></li>
    <!--     <li class="manage<#if index="6"> help-cur</#if>"><a href="${contextPath! }/user/manage.html">我的管理员</a></li> -->
       <li class="myNumRank<#if index="7"> help-cur</#if>"><a href="${contextPath! }/user/rank.html">积分排行</a></li>
       <li class="myHaveMoney<#if index="8"> help-cur</#if>"><a href="${contextPath! }/user/bankInfo.html">银行资料</a></li>
   <#else>
   <li class="myMessage<#if index="1"> help-cur</#if>"><a href="center.html">我的资料</a></li>
        <li class="myAttention<#if index="2"> help-cur</#if>"><a href="myAttention.html">我的关注</a></li>
        <li class="myHistory<#if index="3"> help-cur</#if>"><a href="${contextPath! }/user/history.html">观看历史</a></li>
       <!--  <li class="myAdvices<#if index="4"> help-cur</#if>"><a href="#">我的消息</a></li> -->
        <li class="myPrize<#if index="4"> help-cur</#if>"><a href="${contextPath! }/user/record.html">我的奖品</a></li>
        <li class="myRoom<#if index="5"> help-cur</#if>"><a href="${contextPath! }/user/todoAnchor.html">我要直播</a></li>
   </#if>
    </ul>
</div>
</#macro>

<#-- 用户中心left菜单 -->
<#macro helpLeft index="">
<h3 >直播帮助</h3>
            <ul>
                <li class="help-li <#if index=='1'> help-cur</#if>"><a href="help-center.html">如何申请主播</a></li>
                <li class="help-li <#if index=='2'> help-cur</#if>""><a href="help-iOS.html">蓝鲨录苹果手机开播教程</a></li>
                <li class="help-li <#if index=='3'> help-cur</#if>""><a href="help-android.html" >蓝鲨录安卓手机开播教程</a></li>
                <li class="help-li <#if index=='4'> help-cur</#if>""><a href="help-pc.html">电脑开播教程（不推荐）</a></li>
            </ul>
</#macro>

<#-- 用户中心left菜单 -->
<#macro aboutUs index="">
        <div class="help-left fl">
            <h3 >关于我们</h3>
            <ul>
                <li class="help-li help-li-first"><a href="about-aboutus.html">关于我们</a></li>
                <li class="help-li"><a href="about-contactus.html">联系我们</a></li>
            </ul>
        </div>
</#macro>

<#-- 第三方登陆 -->
<#macro othreLogin>
    <p>第三方帐号直接登录</p>
    <!--下方链接，如未开通加上样式：gray-->
    <a class="wechat gray fl" href="javascript:;" target="_blank">微信账号登录</a>
    <a class="qq gray fl" href="<#--${contextPath! }/oauth2/qqlogin.html-->#">QQ账号登录</a>
    <a class="sina gray fl" href="javascript:;" target="_blank">新浪微博登录</a>
</#macro>

<#--定时发送请求防止session超时-->
<#macro noTimeout >
<script type="text/javascript">
	function noTimeout(){
		$.ajax({type: 'POST',url: "${contextPath! }/web/blank.html"});
		setTimeout(function(){
			noTimeout();
		}, 20*60*1000);
	}
	setTimeout(function(){
		noTimeout();
	}, 20*60*1000);
</script>
</#macro>


<#--注册协议内容 -->
<#macro agreementContent >
	<h1>蓝鲨TV《用户注册协议》</h1>
    <p>本《用户注册协议》是您（下称"用户"或"您"，是指通过蓝鲨TV注册使用服务的自然人、法人或其他组织）与杭州乐鲨科技有限公司（下称"蓝鲨TV"）之间在使用蓝鲨TV出品的互联网产品之前，注册蓝鲨TV帐号时签署的协议。</p>
    <h2>1、重要须知---在签署本协议之前，蓝鲨TV正式提醒用户：</h2>
    <p>1.1、您应认真阅读（未成年人应当在监护人陪同下阅读）、充分理解本《用户注册协议》中各条款，包括免除或者限制蓝鲨TV责任的免责条款及对用户的权利限制条款。</p>
    <p>1.2、除非您接受本协议，否则用户无权也无必要继续接受蓝鲨TV的服务，可以退出本次注册。用户点击同意并继续使用蓝鲨TV的服务，视为用户已完全的接受本协议。</p>
    <p>1.3、本协议一经签署，具有法律效力，请您慎重考虑是否接受本协议。</p>
    <p>1.4、在您签署本协议之后，此文本可能因国家政策、产品以及履行本协议的环境发生变化而进行修改，修改后的协议发布在本网站上，若您对修改后的协议有异议的，请立即停止登录、使用蓝鲨TV产品及服务，若您登录或继续使用蓝鲨TV产品，视为对修改后的协议予以认可。</p>
    <h2>2、关于“蓝鲨TV账号”</h2>
    <p>2.1、用户在接受本协议之后，有权选择其手机号码合作为用户的蓝鲨TV账号，并自行设置符合安全要求的密码。用户设置的账号、密码是用户用以登录蓝鲨TV产品，接受蓝鲨TV服务，持有相关虚拟产品的凭证。</p>
    <p>2.2、蓝鲨TV账号性质上是蓝鲨TV提供服务授予用户的身份识别凭证，蓝鲨TV账号是蓝鲨TV相应计算机软件作品的一部分，即蓝鲨TV将相关产品计算机软件著作权授权给注册用户的授权凭证。</p>
    <p>2.3、蓝鲨TV账号还是用户持有、使用相关虚拟产品的身份凭证。用户若需要接受蓝鲨TV提供的增值服务，蓝鲨TV账号同时也是用户支付费用、接受增值服务的凭证。</p>
    <p>2.4、用户在注册了蓝鲨TV账号并不意味获得全部蓝鲨TV产品的授权，仅仅是取得了接受蓝鲨TV服务的身份，用户在登录相关网页、加载应用、下载安装软件时需要另行签署单个产品的授权协议。</p>
    <p>2.5、蓝鲨TV账号仅限于在蓝鲨TV网站上注册用户本人使用，禁止赠与、借用、租用、转让或售卖。如果蓝鲨TV发现使用者并非账号初始注册人，有权在未经通知的情况下回收该账号而无需向该账号使用人承担法律责任，由此带来的包括并不限于用户通讯中断、用户资料和道具等清空等损失由用户自行承担。</p>
    <p>2.6、用户应当妥善保管自己的蓝鲨TV账号、密码，用户就其账号及密码项下之一切活动负全部责任，包括用户数据的修改，虚拟道具的损失以及其他所有的损失由用户自行承担。用户须重视蓝鲨TV账号密码保护。用户如发现他人未经许可使用其账号时立即通知蓝鲨TV。</p>
    <p>2.7、用户蓝鲨TV账号在丢失或遗忘密码后，可遵照蓝鲨TV的申诉途径及时申诉请求找回账号。用户应不断提供能增加账号安全性的个人密码保护资料。用户可以凭初始注册资料及个人密码保护资料填写申诉单向蓝鲨TV申请找回账号，蓝鲨TV的密码找回机制仅负责识别申诉单上所填资料与系统记录资料的正确性，而无法识别申诉人是否系真正账号权使用人。对用户因被他人冒名申诉而致的任何损失，蓝鲨TV不承担任何责任，用户知晓蓝鲨TV账号及密码保管责任在于用户，蓝鲨TV并不承诺蓝鲨TV账号丢失或遗忘密码后用户一定能通过申诉找回账号。</p>
    <p>2.8、用户保证注册蓝鲨TV账号时填写的身份信息及个人资料是真实的，不得以虚假、冒用的居民身份信息、企业注册信息、组织机构代码信息进行注册或认证，任何非法、不真实、不准确的用户信息所产生的责任由用户承担。用户应不断更新注册资料，符合及时、详尽、真实、准确的要求。所有原始键入的资料将引用用户的账号注册资料。如果因用户的注册信息或认证信息不真实而引起的问题，以及对问题发生所带来的后果，蓝鲨TV不负任何责任。</p>
    <p>2.9、蓝鲨TV账号的所有权属于蓝鲨TV。蓝鲨TV有权根据运营需要，发布蓝鲨TV平台规则规范。</p>
    <p>2.10、如用户违反法律法规、蓝鲨TV各服务协议或平台规则等规定，蓝鲨TV有权根据相关规则进行违规判定，并采取相应限制或处罚措施，包括但不限于：限制或冻结用户对蓝鲨TV账号的使用，限制或停止某项单独服务（如视频直播），扣除保证金，扣减虚拟产品或相关服务费用并根据实际情况决定是否恢复使用。</p>
    <p>2.11、为了充分利用蓝鲨TV账号资源，若用户存在长期未登录使用蓝鲨TV账号的情形，蓝鲨TV有权对该账号进行回收、替换等终止使用操作。</p>
    <p>2.12、蓝鲨TV依照平台规则限制、冻结、回收、替换或终止用户对蓝鲨TV账号的使用，可能会给用户造成一定的损失，该损失由用户自行承担，蓝鲨TV不承担任何责任。</p>
    <h2>3、用户不得从事以下行为：</h2>
    <p>3.1、利用蓝鲨TV服务产品发表、传送、传播、储存危害国家安全、国家统一、社会稳定的内容，或侮辱诽谤、色情、暴力、引起他人不安及任何违反国家法律法规政策的内容或者设置含有上述内容的网名、角色名。</p>
    <p>3.2、利用蓝鲨TV服务发表、传送、传播、储存侵害他人知识产权、商业机密权、肖像权、隐私权等合法权利的内容。</p>
    <p>3.3、进行任何危害计算机网络安全的行为，包括但不限于：使用未经许可的数据或进入未经许可的服务器/账户；未经允许进入公众计算机网络或者他人计算机系统并删除、修改、增加存储信息；未经许可，企图探查、扫描、测试本“软件”系统或网络的弱点或其它实施破坏网络安全的行为；企图干涉、破坏本“软件”系统或网站的正常运行，故意传播恶意程序或病毒以及其他破坏干扰正常网络信息服务的行为；伪造TCP/IP数据包名称或部分名称。</p>
    <p>3.4、进行任何破坏蓝鲨TV服务公平性或者其他影响应用正常秩序的行为，如主动或被动刷分、合伙作弊、使用外挂或者其他的作弊软件、利用BUG（又叫“漏洞”或者“缺陷”）来获得不正当的非法利益，或者利用互联网或其他方式将外挂、作弊软件、BUG公之于众。</p>
    <p>3.5、进行任何诸如发布广告、销售商品的商业行为，或者进行任何非法的侵害蓝鲨TV利益的行为，如贩卖蓝鲨TV平台虚拟货币、外挂、道具等。</p>
    <p>3.6、进行其他任何违法以及侵犯其他个人、公司、社会团体、组织的合法权益的行为。</p>
    <h2>4、蓝鲨TV声明</h2>
    <p>4.1、用户须明白，在使用蓝鲨TV服务可能存在有来自任何他人的包括威胁性的、诽谤性的、令人反感的或非法的内容或行为或对他人权利的侵犯（包括知识产权）的匿名或冒名的信息的风险，用户须承担以上风险，蓝鲨TV对服务不作担保，不论是明确的或隐含的，包括所有有关信息真实性、适当性、适于某一特定用途、所有权和非侵权性的默示担保和条件，对因此导致任何因用户不正当或非法使用服务产生的直接、间接、偶然、特殊及后续的损害，不承担任何责任。</p>
    <p>4.2、使用蓝鲨TV服务必须遵守国家有关法律和政策等，维护国家利益，保护国家安全，并遵守本条款，对于用户违法或违反本协议的使用(包括但不限于言论发表、传送等)而引起的一切责任，由用户负全部责任。</p>
    <p>4.3、蓝鲨TV的服务同大多数因特网产品一样，易受到各种安全问题的困扰，包括但不限于：</p>
    <p>1）透露详细个人资料，被不法分子利用，造成现实生活中的骚扰；</p>
    <p>2）哄骗、破译密码；</p>
    <p>3）下载安装的其它软件中含有“特洛伊木马”等病毒，威胁到个人计算机上信息和数据的安全，继而威胁对本服务的使用。对于发生上述情况的，用户应当自行承担责任。</p>
    <p>4.4、用户须明白，蓝鲨TV为了服务整体运营的需要，有权在公告通知后修改或中断、中止或终止服务而不需通知您的权利，而无须向第三方负责或承担任何赔偿责任。</p>
    <p>4.5、用户理解，互联网技术的不稳定性，可能导致政府政策管制、病毒入侵、黑客攻击、服务器系统崩溃或者其他现今技术无法解决的风险发生可能导致蓝鲨TV服务中断或账号道具损失，用户对此非人为因素引起的损失由用户承担责任。</p>
    <h2>5、知识产权</h2>
    <p>5.1、蓝鲨TV的服务包括蓝鲨TV运营的网站、网页应用、软件以及内涵的文字、图片、视频、音频等元素，蓝鲨TV服务标志、标识以及专利权，蓝鲨TV对此享有上述知识产权。</p>
    <p>5.2、用户不得对蓝鲨TV服务涉及的相关网页、应用、软件等产品进行反向工程、反向汇编、反向编译等。</p>
    <p>5.3、用户使用蓝鲨TV服务只能在本《用户协议》以及相应的授权许可协议授权的范围使用蓝鲨TV知识产权，未经授权超范围使用的构成对蓝鲨TV的侵权。</p>
    <p>5.4、用户在使用蓝鲨TV服务时发表上传的文字、图片、视频、软件以及表演等用户原创的信息，此部分信息的知识产权归用户，但用户的发表、上传行为是对蓝鲨TV服务平台的授权，用户确认其发表、上传的信息非独占性、永久性的授权，该授权可转授权。蓝鲨TV可将前述信息在蓝鲨TV旗下的服务平台上使用，可再次编辑后使用，也可以由蓝鲨TV授权给合作方使用。</p>
    <h2>6、隐私保护</h2>
    <p>6.1、请用户注意勿在使用蓝鲨TV服务中透露自己的各类财产帐户、银行卡、信用卡、第三方支付账户及对应密码等重要资料，否则由此带来的任何损失由用户自行承担。</p>
    <p>6.2、用户的蓝鲨TV账号、密码属于保密信息，蓝鲨TV应当采取积极的措施保护用户账号、密码的安全。</p>
    <p>6.3、用户的注册信息作为蓝鲨TV的商业秘密进来保护。但用户同时明白，互联网的开放性以及技术更新非常快，非蓝鲨TV可控制的因素导致用户泄漏的，蓝鲨TV不承担责任。</p>
    <p>6.4、用户在使用蓝鲨TV服务时不可将自认为隐私的信息发表、上传至蓝鲨TV，也不可将该等信息通过蓝鲨TV的服务传播给其他人，若用户的行为引起的隐私泄漏，由用户承担责任。</p>
    <p>6.5、蓝鲨TV在提供服务时可能会搜集用户信息，蓝鲨TV会明确告知用户，通常信息仅限于用户姓名、性别、年龄、出生日期、身份证号、家庭住址、教育程度、公司情况、所属行业、兴趣爱好等。</p>
    <p>6.6、就下列相关事宜的发生，蓝鲨TV不承担任何法律责任：</p>
    <p>1）蓝鲨TV根据法律规定或相关政府、司法机关的要求提供您的个人信息；</p>
    <p>2）由于用户将用户密码告知他人或与他人共享注册账户，由此导致的任何个人信息的泄漏，或其他非因蓝鲨TV原因导致的个人信息的泄漏；</p>
    <p>3）任何由于黑客攻击、电脑病毒侵入造成的信息泄漏；</p>
    <p>4）因不可抗力导致的信息泄漏；</p>
    <h2>7、其他条款</h2>
    <p>7.1、本协议的签署地点为，若用户与蓝鲨TV因履行本协议发生争议的，双方均应本着友好协商的原则加以解决。协商解决未果，任何一方均可以提请当地人民法院诉讼解决。</p>
    <p>7.2、本协议由蓝鲨TV公布在网站上，对蓝鲨TV具有法律约束力；用户一经点击接受或者直接登录的行为视为对本协议的接受，对用户具有法律约束力。</p>
    <p>7.3、蓝鲨TV旗下具体的网站、网页应用、软件等的使用由用户和蓝鲨TV的业务平台另行签署相关软件授权及服务协议。</p>
</#macro>
