<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>联系我们 - ${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/center.css">
    <!--[if lt IE 9]>
    <script ="${staticPath! }/static/lansha/static/js/html5.min.js"></script>
    <![endif]-->
</head>
<body class="fffbf8-body">
<@lansha.head index=""/>

<div class="container">
    <div class="layout  help-box clearfix">
        <@lansha.aboutUs index="2"/>
        <div class="help-right fr" style="min-height: 400px;">
            <div class="help-title">联系我们</div>
            <p class="help-p"><br/>客服QQ：2716491563 <br/>
                客服邮箱：kf@lansha.tv<br/>
                公司地址：杭州市余杭区文一西路1382号绿岸科创园9楼<br/>
                联系电话：0571-88518513
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