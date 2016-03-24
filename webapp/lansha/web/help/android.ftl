<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>蓝鲨TV手机游戏直播教程-安卓篇 - ${platFormName! }</title>
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
			<@lansha.helpLeft index="3"/>
        </div>
        <div class="help-right fr">
            <div class="help-title">如何安装及使用Android版主播工具</div>
            <div class="help-num clearfix"><span class="fl">01</span><p class="fl">确定您是蓝鲨主播</p></div>
            <p class="help-p">1. 注册并登录蓝鲨TV</p>
            <p class="help-p">2. 申请直播</p>
            <img src="${staticPath! }/static/lansha/upload/and_new_1.png" alt="">
            <p class="help-p">备注：具体按提示流程操作即可</p>
            <p class="help-p">3. 成功通过主播审核认证</p>
            <img src="${staticPath! }/static/lansha/upload/and_new_2.png" alt="">
            <p class="help-p">备注：所有直播申请，3个工作日内完成审核。如有疑问，请咨询官方客服QQ：2716491563</p>
            <div class="help-num clearfix"><span class="fl">02</span><p class="fl">如何使用Android版主播工具（支持安卓5.0及以上系统）</p></div>
            <p class="help-p">1. 下载并登陆：下载地址http://lansha.tv/appdownload.html 下完成后打开蓝鲨录APP，登录主播账号</p>
            <img src="${staticPath! }/static/lansha/upload/and_new_3.png" alt="">
            <p class="help-p">2. 填写直播信息：填写直播公告、填写直播标题、选择直播的游戏、选择横竖屏直播、选择直播源清晰度</p>
            <img src="${staticPath! }/static/lansha/upload/and_new_4.png" alt="">
            <p class="help-p">3. 直播中：进行视频互动<br/>
                标注：①直播的游戏；②观众人数；③虾米数；④开/关隐私模式；⑤展开或缩略聊天内容；<br/>
                ⑥输入文字聊天；⑦开/关聊天内容显示；⑧开/关摄像头
            </p>
            <img src="${staticPath! }/static/lansha/upload/and_new_5.jpg" alt="">
            <p class="help-p">4. 开始直播游戏：返回手机主页，选择一款游戏开始直播<br/>
                标注：⑨蓝鲨录悬浮图标（点击可展开）；⑩返回直播页；11.开/关摄像头； 12.开/关聊天悬浮框；<br/>13.开/关隐私模式；14.聊天悬浮框

            </p>
            <img src="${staticPath! }/static/lansha/upload/and_new_6.png" alt="">

            <p class="help-p"><b class="blue">蓝鲨TV官方交流群：211423196 </b></p>
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