<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>主播招募中 - ${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/center.css${staticVersion! }">
    <!--[if lt IE 9]>
    <script ="${staticPath! }/static/lansha/static/js/html5.min.js"></script>
    <![endif]-->
</head>
<body class="fffbf8-body">
<@lansha.head index="1"/>

<div class="container">
    <div class="layout  help-box clearfix">
        <div class="help-left fl">
			<@lansha.helpLeft index="1"/>
        </div>
        <div class="help-right fr">
            <!--登录后链接到实名认证-->
            <div class="help-title">主播招募中</div>
            <img class="help-ban" src="${staticPath! }/static/lansha/static/images/helpBanner.jpg" alt="">
            <p class="help-p">霹雳一声雷，蓝鲨来登场！！各位乡亲们姐妹们注意了，为了配合平台运营与新功能上线特招募有限数额的高待遇主播，新主播招募开始了：</p>
            <p class="help-p">没有在线观众人数、互动弹幕数量、礼物赠送数量、粉丝关注数等额外要求。只要按照运营规定在特定时间段播出，就能按时拿底薪哦；试播期间收到的虾米礼物在正式开播后还能兑换成现金奖励；试播主播转到正式主播还能有各种福利哦！还不快快来参加（截至到数额招满为止）</p>
            <p class="help-p">以下是仅有的条件(等于没有条件0.0)
                <br/>1. 智能手机
                <br/>2. 一台电脑
                <br/>3. 一个爱玩手机游戏的你，游戏内容《天天酷跑》、《虚荣》、《自由之战》、《全民枪战》等等等等等-_-!!!</p>
            <p class="help-p">下列要求不一定要有，但是有了会让我更快爱上你
                <br/>1. 熟练的游戏
                <br/>2. 美貌的容颜
                <br/>3. 一个让我熟悉你的摄像头</p>
            <p class="help-p"><b>官方主播招募QQ群：460074331</b></p>
            <div class="help-title">如何申请当主播</div>
            <div class="help-num clearfix"><span class="fl">01</span>
                <p class="fl">网速要求</p>
            </div>
            <p class="help-p">首先要拥有上传速度超过1M的流畅网络，才能够保证良好的直播体验。
                <br/> 网速测试地址：
                <a href="http://www.speedtest.cn/" target="_blank">http://www.speedtest.cn/</a></p>
            <div class="help-num clearfix"><span class="fl">02</span>
                <p class="fl">注册和登录蓝鲨TV</p>
            </div>
            <p class="help-p">（1） 打开蓝鲨TV的网站
                <br/> （2） 注册蓝鲨TV的账号
                <br/> （3） 填写真实有效的信息完成注册。</p>
            <div class="help-num clearfix"><span class="fl">03</span>
                <p class="fl">申请主播</p>
            </div>
            <p class="help-p">(1) 点击右上角用户名进入个人中心
                <br/> (2) 点击我要做主播开始主播实名信息认证流程
                <br/> (3) 填写和上传有效真实个人实名认证信息
                <br/> (4) 完成填写开始等待审核
                <br/> (5) 审核成功后，就可以进入个人中心-我的房间-进入直播间，开始进行直播了
            </p>
        </div>

    </div>
</div>
<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script src="${staticPath! }/static/lansha/static/js/jquery-1.11.1.min.js"></script>
<script src="${staticPath! }/static/lansha/static/js/common.js"></script>
<script src="${staticPath! }/static/lansha/static/js/jquery.SuperSlide.2.1.1.js"></script>
<script>
    jQuery(".picScroll-left").slide({titCell:".hd ul",mainCell:".bd ul",autoPage:true,effect:"left",autoPlay:true,vis:4});
</script>
</body>
</html>