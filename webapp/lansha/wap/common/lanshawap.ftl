<#-- 页头meta -->
<#macro meta keywords="" description="">
	<#-- 平台名称 -->
	<#assign platFormName = platFormName?default("") />
    <meta charset="UTF-8">
    <meta name="author" content="Yowant FE Team"/>
    <meta name="Description" content="${description! }" />
    <meta name="Keywords" content="${keywords! }" />
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no"/>
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
	<script type="text/javascript">
	    var BASE_PATH = '${staticPath! }/static/lansha/';
	    var LOGIN_URL = '${contextPath! }/login.html?backUrl=${httpUrl! }';
	    var isLogin = <#if userIsLogin?default(false)>1<#else>0</#if>;
	</script>
	<!-- start Dplus -->
	<script type="text/javascript">!function(a,b){if(!b.__SV){var c,d,e,f;window.dplus=b,b._i=[],b.init=function(a,c,d){function g(a,b){var c=b.split(".");2==c.length&&(a=a[c[0]],b=c[1]),a[b]=function(){a.push([b].concat(Array.prototype.slice.call(arguments,0)))}}var h=b;for("undefined"!=typeof d?h=b[d]=[]:d="dplus",h.people=h.people||[],h.toString=function(a){var b="dplus";return"dplus"!==d&&(b+="."+d),a||(b+=" (stub)"),b},h.people.toString=function(){return h.toString(1)+".people (stub)"},e="disable track track_links track_forms register unregister get_property identify clear set_config get_config get_distinct_id track_pageview register_once track_with_tag time_event people.set people.unset people.delete_user".split(" "),f=0;f<e.length;f++)g(h,e[f]);b._i.push([a,c,d])},b.__SV=1.1,c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.php?token=72160be13e82a3952a1a",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}}(document,window.dplus||[]),dplus.init("72160be13e82a3952a1a");</script>
	<!-- end Dplus -->
</#macro>

<#-- 页尾js -->
<#macro footcommonjs>
<!--script start-->
<script src="${staticPath! }/static/lansha/static/js/jquery-1.11.1.min.js"></script>
<script src="${staticPath! }/lansha/wap/static/js/zepto.min.js"></script>
<script src="${staticPath! }/lansha/wap/static/js/jquery.swiper.min.js?v=20160313"></script>
<script src="${jsPath! }/lansha/wap/static/js/common.js${staticVersion! }"></script>
<!-- start Dplus Define -->
<script type="text/javascript">!function(a,b){var c,d;window.__dplusDefineCacheQueue=[],b.define=function(){window.__dplusDefineCacheQueue.push(Array.prototype.slice.call(arguments))},c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.define.php",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}(document,window.dplus);</script>
<!-- end Dplus Define -->
<script> 
<#--埋码-->
${actionAnalysisCode! }
</script>
<!--script end-->
</#macro>

<#macro footjs>
<!--script start-->
<script src="${staticPath! }/static/lansha/static/js/jquery-1.11.1.min.js"></script>
<script src="${staticPath! }/static/lansha/static/js/jquery.md5.min.js"></script>
<script src="${staticPath! }/static/lansha/static/js/jquery.xBox.min.js"></script>

<script src="${jsPath! }/static/lansha/wzry_wap/static/js/common.js${staticVersion! }"></script>
<script src="${jsPath! }/static/lansha/wzry_wap/static/js/mini-user.js${staticVersion! }"></script>
<!-- start Dplus Define -->
<script type="text/javascript">!function(a,b){var c,d;window.__dplusDefineCacheQueue=[],b.define=function(){window.__dplusDefineCacheQueue.push(Array.prototype.slice.call(arguments))},c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.define.php",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}(document,window.dplus);</script>
<!-- end Dplus Define -->
<script> 
<#--埋码-->
${actionAnalysisCode! }
</script>
<!--script end-->
</#macro>

<#-- 页头 -->
<#macro head>
	 <meta charset="UTF-8">
    <meta name="author" content="Yowant FE Team" />
    <meta name="Description" content="${platFormName! }" />
    <meta name="Keywords" content="${platFormName! }" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no" />
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <title>${platFormName! }</title>
	<!-- start Dplus -->
	<script type="text/javascript">!function(a,b){if(!b.__SV){var c,d,e,f;window.dplus=b,b._i=[],b.init=function(a,c,d){function g(a,b){var c=b.split(".");2==c.length&&(a=a[c[0]],b=c[1]),a[b]=function(){a.push([b].concat(Array.prototype.slice.call(arguments,0)))}}var h=b;for("undefined"!=typeof d?h=b[d]=[]:d="dplus",h.people=h.people||[],h.toString=function(a){var b="dplus";return"dplus"!==d&&(b+="."+d),a||(b+=" (stub)"),b},h.people.toString=function(){return h.toString(1)+".people (stub)"},e="disable track track_links track_forms register unregister get_property identify clear set_config get_config get_distinct_id track_pageview register_once track_with_tag time_event people.set people.unset people.delete_user".split(" "),f=0;f<e.length;f++)g(h,e[f]);b._i.push([a,c,d])},b.__SV=1.1,c=a.createElement("script"),c.type="text/javascript",c.charset="utf-8",c.async=!0,c.src="//w.cnzz.com/dplus.php?token=72160be13e82a3952a1a",d=a.getElementsByTagName("script")[0],d.parentNode.insertBefore(c,d)}}(document,window.dplus||[]),dplus.init("72160be13e82a3952a1a");</script>
	<!-- end Dplus -->
</#macro>

<#-- 头标签 -->
<#macro headtag>
	<div class="header">
        <a href="index.html" class="logo fl"></a>
        <p class="fr">全民手游直播平台</p>
    </div>
</#macro>

<#-- 页头CSS -->
<#macro headcss>
	<link rel="stylesheet" href="${staticPath! }/lansha/wap/static/css/common.css${staticVersion! }" />
    <link rel="stylesheet" href="${staticPath! }/lansha/wap/static/css/swiper.min.css?v=20160313">    
</#macro>

<#-- 微信接口js -->
<#macro wxapijs>
	<#if (wxParams.shareControl?default("")).toUpperCase()=="ON">
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
	<script type="text/javascript">
		wx.config({
			debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : "${wxParams.appId! }", // 必填，公众号的唯一标识
			timestamp : "${wxParams.timestamp! }", // 必填，生成签名的时间戳
			nonceStr : "${wxParams.noncestr! }", // 必填，生成签名的随机串
			signature : "${wxParams.signature! }",// 必填，签名，见附录1
			jsApiList : ['onMenuShareTimeline',
		             'onMenuShareAppMessage',
		             'onMenuShareQQ',
		             'onMenuShareWeibo',
		             'onMenuShareQZone' ]// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		wx.ready(function (){
			//分享接口
			// 1. 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareTimeline({
				title : "${wxParams.shareTitle! }", // 分享标题
				link : "${wxParams.shareLink! }", // 分享链接
				imgUrl : "${wxParams.imgUrl! }", // 分享图标
				type : '', // 分享类型,music、video或link，不填默认为link
				dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
				trigger : function(res) {
					// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
					//alert('用户点击发送给朋友');
				},
				success : function() {
					// 用户确认分享后执行的回调函数
					//alert('用户点击成功发送给朋友');
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					//alert('已取消');
				},
				fail : function(res) {
					//alert(JSON.stringify(res));
				}
			});
			
			// 2. 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareAppMessage({
				title : "${wxParams.shareTitle! }", // 分享标题
				desc : "${wxParams.shareDesc! }", // 分享描述
				link : "${wxParams.shareLink! }", // 分享链接
				imgUrl : "${wxParams.imgUrl! }", // 分享图标
				type : '', // 分享类型,music、video或link，不填默认为link
				dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
				trigger : function(res) {
					// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
					//alert('用户点击发送给朋友');
				},
				success : function() {
					// 用户确认分享后执行的回调函数
					//alert('用户点击成功发送给朋友');
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					//alert('已取消');
				},
				fail : function(res) {
					//alert(JSON.stringify(res));
				}
			});
			
			// 3. 监听“分享到QQ”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareQQ({
				title : "${wxParams.shareTitle! }", // 分享标题
				desc : "${wxParams.shareDesc! }", // 分享描述
				link : "${wxParams.shareLink! }", // 分享链接
				imgUrl : "${wxParams.imgUrl! }", // 分享图标
				trigger : function(res) {
					// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
					//alert('用户点击分享到QQ');
				},
				success : function() {
					// 用户确认分享后执行的回调函数
					//alert('用户点击成功分享到QQ');
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					//alert('已取消');
				},
				fail : function(res) {
					//alert(JSON.stringify(res));
				}
			});
			
			// 4. 监听“分享到腾讯微博”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareWeibo({
				title : "${wxParams.shareTitle! }", // 分享标题
				desc : "${wxParams.shareDesc! }", // 分享描述
				link : "${wxParams.shareLink! }", // 分享链接
				imgUrl : "${wxParams.imgUrl! }", // 分享图标
				trigger : function(res) {
					// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
					//alert('用户点击分享到腾讯微博');
				},
				success : function() {
					// 用户确认分享后执行的回调函数
					//alert('用户点击成功分享到腾讯微博');
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					//alert('已取消');
				},
				fail : function(res) {
					//alert(JSON.stringify(res));
				}
			});
			
			// 5. 监听“分享到QQ空间”按钮点击、自定义分享内容及分享结果接口
			wx.onMenuShareQZone({
				title : "${wxParams.shareTitle! }", // 分享标题
				desc : "${wxParams.shareDesc! }", // 分享描述
				link : "${wxParams.shareLink! }", // 分享链接
				imgUrl : "${wxParams.imgUrl! }", // 分享图标
				trigger : function(res) {
					// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
					//alert('用户点击分享到QQ空间');
				},
				success : function() {
					// 用户确认分享后执行的回调函数
					//alert('用户点击成功分享到QQ空间');
				},
				cancel : function() {
					// 用户取消分享后执行的回调函数
					//alert('已取消');
				},
				fail : function(res) {
					//alert(JSON.stringify(res));
				}
			});
		});
	</script>
	</#if>
</#macro>

