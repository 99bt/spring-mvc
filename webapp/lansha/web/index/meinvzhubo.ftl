<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="Yowant FE Team" />
    <@lansha.meta keywords="蓝鲨TV，天天酷跑，我的世界，自由之战，王者荣耀，穿越火线，全民枪战，节奏大师，虚荣，手机游戏直播，手游直播，手游赛事直播，手游主播"
		description="蓝鲨TV - 全民手游直播平台，专注手机游戏直播，包含天天酷跑，自由之战，王者荣耀，穿越火线，节奏大师，虚荣等各类热门手游直播，让用户随时随地可以开直播、看直播！" />
    <title>美女主播专题 - ${platFormName! }</title>
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/jquery.xBox.min.css">
    <style>
        body {background: url('http://oss.lansha.tv/activity/upload/meinvzhubo/topbg.png') center top no-repeat;min-height: 800px;}
        .warp {width: 1180px;margin: 0 auto;position: relative;}
        .topbar {height: 240px;}
        .topbar .fl {background: url("http://oss.lansha.tv/activity/upload/meinvzhubo/left_bg.png") 0 10px no-repeat;width: 519px;height: 224px;margin-top: 16px;}
        .topbar .fr {height: 240px;width: 300px;background: url("http://oss.lansha.tv/activity/upload/meinvzhubo/zm_bg.png") right 100px no-repeat;}
        .topbar .fr .btn {float: right;margin-top: 170px;}
        .player {width: 893px;height: 540px;background: #23272b;}
        .remlist {overflow:hidden;background: #23272b;padding: 15px 10px 5px 10px;}
        .remlist li {width: 234px;height: 163px;margin-bottom: 10px;}
        .remlist li img {width: 234px;height: 163px;}
        .boger-rem {margin: 20px auto;}
        .boger-rem .title {font-size: 16px;font-size: 26px;color: #3c3c3c;}
        .boger-rem .title img {margin-right: 5px;}
        .boger-rem .rembogers {padding: 10px 0px;}
        .rembogers .boger-big {width: 631px;height: 421px;float: left;margin-right: 10px;position: relative;}
        .rembogers .boger-big img {width: 631px;height: 421px;}
        .flag {position: absolute;right: 0px;top:0px;z-index: 9;width: 66px;height: 66px;background: url("http://oss.lansha.tv/activity/upload/meinvzhubo/flag.png") 0 0 no-repeat;}
        .uname {height: 50px;line-height: 50px;width: 100%;background: #f6be2b;color: #FFF;position: absolute;bottom: 0px;left: 0px;z-index: 9;text-align: center;display: none;}
        .rembogers a:hover .uname {display: block;}
        .rembogers .boger-small {width: 261px;height: 206px;float: left;margin: 0px 10px 10px 0;position: relative;}
        .rembogers .boger-small img {width: 261px;height: 206px;}
        .rembogers .boger-middle {width: 305px;height: 240px;float: left;margin-right: 20px;position: relative;}
        .rembogers .boger-middle img {width: 305px;height: 240px;}
        .rembogers .boger-rectangle {width: 534px;height: 242px;float: left;margin-left: 10px;position: relative;}
        .rembogers .boger-rectangle img {width: 534px;height: 242px;}
        .rembogers .boger-small .uname, .rembogers .boger-middle .uname {height: 30px;line-height: 30px;}
        .rembogers .boger-small.last, .rembogers .boger-middle.last {margin-right: 0px;}
        .live-list {margin: 20px auto;}
        .live-list .title {font-size: 16px;font-size: 26px;color: #3c3c3c;margin-bottom: 10px;}
        .live-list .title img {margin-right: 5px;}
        .live-list .title a {font-size: 14px;}
        .live-list li {float: left;margin-right: 12px;width: 186px;height: 128px;}
        .live-list li:last-child {margin-right: 0px;}
        .live-list li img {width: 186px;height: 105px;}
        .ewm {position: absolute;right: -90px;top: 240px;width: 68px;height: 120px;background: url("http://oss.lansha.tv/activity/upload/meinvzhubo/2wm.png") 0px -3px no-repeat;}
        .ewm img {width: 68px;height: 68px;}
    </style>
</head>

<body style="">
	<@lansha.head index="1"/>

	${name! }
    
    <!--最新主播-->
    <div class="warp live-list clearfix">
        <div class="title"><img src="${uploadPath! }/activity/upload/meinvzhubo/dian.png">最新主播 <span class="fr"><a href="${contextPath! }/liveList.html">更多>></a></span></div>
        <ul>
        <#list list?if_exists as item>
            <li>
                <a href="${contextPath! }/${item.idInt! }"><img src="${uploadPath! }${item.liveImg! }" alt="${item.name! }" title="${item.name! }" onerror="javascript:this.src='${staticPath! }/static/lansha/static/images/nopic.png';"></a>
                <p>${item.name! }</p>
            </li>
        </#list>
        </ul>
    </div>

	<@lansha.foot />
    
	<#-- 页尾js -->
	<@lansha.footjs />
</body>
</html>