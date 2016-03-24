<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta />
    <title>${platFormName! }新春活动</title>
    <link rel="stylesheet" type="text/css" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <style>
        /** 新春主播活动 **/
        body{background: url("${staticPath! }/static/lansha/static/images/fahongbao/body_bg.jpg${staticVersion! }") center top no-repeat #2b2f4c !important;}
        .container {width: 1000px;min-height: 200px;margin: 0 auto;}
        .firstDiv {width: 100%;height: 600px;}
        .hd_warp .titleImg {margin: 0 auto;text-align: center}
        .info {color: #FFF;margin-bottom: 60px;}
        .info p {line-height: 36px;font-size: 16px;}
        .info.first {margin-top: 280px;margin-bottom: 40px;}
        .hdfooter {height: 200px;width: 100%;margin-top: 200px;background: url("${staticPath! }/static/lansha/static/images/fahongbao/hd_footer.png${staticVersion! }") center bottom no-repeat;}
    </style>
    <!--[if lt IE 9]>
    <script ="${staticPath! }/static/lansha/static/js/html5.min.js${staticVersion! }"></script>
    <![endif]-->
</head>
<body>
<!--仅首页的header添加header-index 如下-->
<div class="header-space"></div>
<@lansha.head index=""/>
<div class="container">
    <div class="firstDiv"></div>
    <div class="hd_warp">
        <div class="titleImg" >
            <img src="${staticPath! }/static/lansha/static/images/fahongbao/hd_title01.png${staticVersion! }" alt="佳节红包疯狂送">
        </div>
        <div class="info first">
            <p>1.活动时间：除夕夜（2016.02.07）、正月初一（2016.02.08）、正月初二(2016.02.09)、情人节(2016.02.14)</p>
            <p>2.每晚8点在网站和APP首页广告位第一个直播间内发放支付宝红包口令，任何人（主播和非主播）都可以参与领取 ，第一个直播间将会是前一天直播成绩最好并能当场在线的主播，由官方设置</p>
            <p>3.支付宝红包口令为那位主播的直播间ID+任意一位数字，请大家自己猜测（如：直播间ID为10123，则口令为101230-101239中任意一个）</p>
            <p>4.每晚8点的支付宝红包总金额为888，666不等</p>
            <p>5.用户可以猜测此口令，在支付宝手机APP中输入口令就可以领取</p>
        </div>
        <div class="titleImg" >
            <img src="${staticPath! }/static/lansha/static/images/fahongbao/hd_title02.png${staticVersion! }" alt="晚上开播就加薪">
        </div>
        <div class="info ">
            <p>1.活动时间：2016.02.05 – 2016.02.14</p>
            <p>2.每晚8点至10点主播薪资增加5元/小时</p>
            <p>3.奖金会在主播薪资结算的时候进行发放</p>
        </div>
        <div class="titleImg" >
            <img src="${staticPath! }/static/lansha/static/images/fahongbao/hd_title03.png${staticVersion! }" alt="开播最长送奖金">
        </div>
        <div class="info ">
            <p>1.活动时间；2016.02.05活动上线 – 2016.02.14 23:59:59</p>
            <p>2.在活动时间内开播累计时长最多的1-10名主播送奖金：</p>
            <p>第1名    奖金888元</p>
            <p>第2名    奖金666元</p>
            <p>第3名    奖金333元</p>
            <p>第4名至10名    奖金100元</p>
        </div>
    </div>
</div>
<div class="hdfooter">

</div>
<@lansha.footjs />
</body>
</html>