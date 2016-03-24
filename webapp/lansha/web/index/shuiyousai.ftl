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
    <title>蓝鲨杯万元现金水友赛 - ${platFormName! }</title>
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/jquery.xBox.min.css">
    <style>
        .warp {width: 1180px;margin: 0 auto;position: relative;}
        .d-1 {width: 100%;height: 465px;background: url('http://oss.lansha.tv/activity/upload/shuiyousai/bm_bg1.png') center top no-repeat;}
        .d-2 {width: 100%;height:465px;background: url('http://oss.lansha.tv/activity/upload/shuiyousai/bm_bg2.png') center top no-repeat;}
        .d-3 {width: 100%;height:550px;background: url('http://oss.lansha.tv/activity/upload/shuiyousai/bm_bg3.png') center top no-repeat;}
        .d-4 {width: 100%;height:420px;background: url('http://oss.lansha.tv/activity/upload/shuiyousai/bm_bg4.png') center top no-repeat;}
        .d-1 .baomingbtn {display: block;position: absolute;width: 220px;height: 77px;right: 388px;top:360px;background: url('http://oss.lansha.tv/activity/upload/shuiyousai/baoming-btn.png') center top no-repeat;text-indent: -9999px;}
        .popbox {width: 1180px;height: 722px;position: fixed;top:50%;left: 50%;margin: -361px 0 0 -590px;z-index: 9000;background: url("http://oss.lansha.tv/activity/upload/shuiyousai/popbox.png") 0 0 no-repeat;display: none;}
        .popbox .closebox {width: 44px; height: 60px;position: absolute;right: 198px;top: 96px;text-indent: -9999px; cursor: pointer;}
        .popbox .mail {color: #fff;width:780px;height: 240px;position: absolute;right: 198px;top: 205px; }
        .popbox .mail .title {margin-bottom: 30px;}
        .popbox .mail p {width: 100%;text-indent: 68px;line-height: 30px;font-size: 14px;}
        .popbox .mail .title, .popbox .mail .first {text-indent: 0px;}
    </style>
</head>

<body style="">
	<@lansha.head index="1"/>
	
    <div class="d-1">
        <div class="warp">
            <a href="javascript:;" class="baomingbtn">立即报名</a>
        </div>
    </div>
    <div class="d-2">
        <div class="warp">
        </div>
    </div>
    <div class="d-3">
        <div class="warp">
        </div>
    </div>
    <div class="d-4">
        <div class="warp">
        </div>
    </div>
    <div class="popbox">
        <div class="closebox">x</div>
        <div class="mail">
            <p class="title">邮件标题：《王者荣耀》水友赛</p>
            <p class="first">邮件内容：战队名称：</p>
            <p>所在专区（安卓或ios）：</p>
            <p>队长信息：QQ号；联系电话；区服（默认）：手Q区</p>
            <p>队员1信息：QQ号；联系电话；区服（默认）：手Q区</p>
            <p>队员1信息：QQ号；联系电话；区服（默认）：手Q区</p>
            <p>备选队员信息（可选）：QQ号；联系电话；区服（默认）：手Q区</p>
            <p>队长发送邮件后请加官方工作人员QQ：1097576102，确认报名是否成功</p>
        </div>
    </div>
    
	<#-- 页尾js -->
	<@lansha.footjs />
	<script type="text/javascript">
	    $(function(){
	        $('.closebox').click(function(){
	            $.xbox.removeMask();
	            $('.popbox').hide();
	        });
	    })
	    $('.baomingbtn').click(function(){
	        $.xbox.showMask();
	        $('.popbox').show();
	    })
	</script>
</body>
</html>