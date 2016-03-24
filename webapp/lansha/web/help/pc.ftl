<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>蓝鲨TV手机游戏直播教程-PC篇 - ${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/center.css${staticVersion! }">
</head>
<body class="fffbf8-body">
<@lansha.head index="1"/>

<div class="container">
    <div class="layout  help-box clearfix">
        <div class="help-left fl">
			<@lansha.helpLeft index="4"/>
        </div>
        <div class="help-right fr">
            <div class="help-title">蓝鲨TV用电脑开播教程</div>
            <p class="help-p">电脑开播流程繁琐，对电脑配置有一定要求，所以我们建议您使<em>蓝鲨录（直播工具）</em> 直接在手机上开播！除非你的手机版本不支持蓝鲨录的情况下，再选择用电脑开播。<br/>
                <em>蓝鲨录（直播工具）下载地址 <a href="http://lansha.tv/appdownload.html"  target="_blank"> http://lansha.tv/appdownload.html</a></em>
            </p>
            <div class="help-num clearfix"><span class="fl">01</span><p class="fl">苹果手机直播准备（手机传屏到电脑）：</p></div>
            <p class="help-p">1.下载所有工具：<a href="http://pan.baidu.com/s/1c1pgiu0"  target="_blank">http://pan.baidu.com/s/1c1pgiu0 </a></p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_1.png" alt="">
            <p class="help-p">2.下载完成后解压并打开Airplay传屏大师，选择对应的苹果系统版本</p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_2.png" alt="">
            <p class="help-p">3.拿出你的苹果手机在界面底部向上滑动，找到Airplay选项点开后选择镜像就能在电脑上看到你的手机屏幕了，<em>需要直播电脑与苹果手机在同一网络下，才会有Airplay选项！</em></p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_3.jpg" alt="">
            <div class="help-num clearfix"><span class="fl">02</span><p class="fl">安卓手机直播准备（手机传屏到电脑）：</p></div>
            <p class="help-p">1.下载所有工具：<a href="http://pan.baidu.com/s/1c1pgiu0"  target="_blank">http://pan.baidu.com/s/1c1pgiu0 </a></p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_4.png" alt="">
            <p class="help-p">2.在手机上安装Mobizen手机端，使用USB线连接手机与电脑，手机开启USB调试模式，打开Mobizen注册邮箱和密码（用于电脑端登陆）</p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_5.png" alt="">
            <p class="help-p">3.在电脑上安装Mobizen电脑端，用手机上的注册的邮箱进行登陆，设置六位数的二次认证。</p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_6.png" alt="">
            <p class="help-p">4.点击开始链接，开始在电脑上显示手机画面（鼠标移至软件四角可拉大调节显示画面）</p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_7.jpg" alt="">
            <p class="help-p"><em>当将手机画面成功传到电脑上之后，就可以用OBS直播工具将画面传倒你的直播间了。</em></p>
            <div class="help-num clearfix"><span class="fl">03</span><p class="fl">OBS使用教程：</p></div>
            <p class="help-p">1.解压并安装OBS直播工具（在之前下载所有工具的时候就已经下载好了）</p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_8.png" alt="">
            <p class="help-p">如果不能安装，提示以下内容，请先下载并安装DirectX驱动之后再安装OBS。</p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_9.png" alt="">
            <p class="help-p">DirectX下载地址：<a href="http://pan.baidu.com/s/1c1pgiu0"  target="_blank">http://pan.baidu.com/s/1c1pgiu0</a></p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_10.png" alt="">
            <p class="help-p">2.成功安装并打开OBS直播工具，对推流地址进行设定，具体操作：<br/>
                点击设定按钮弹出设置面板，选择广播设定栏目。可以看见在设置栏中有<em>FMS URL和播放路径/串码流</em>两个输入框。
            </p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_11.png" alt="">
            <img src="${staticPath! }/static/lansha/upload/pc_new_12.png" alt="">
            <p class="help-p">
                然后打开蓝鲨TV网站，进入我的直播间内（确保您已经通过认证审核成为主播）
                进入直播间之后点击开播按钮并分别将 <em> rtmp地址复制到OBS的FMS URL</em>栏目中，将<em>直播码复制到OBS的播放路径/串码流</em>栏目中，点击确定。
            </p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_13.png" alt="">
            <p class="help-p">3.设置OBS画面传输，点影像将基本解析度改为1280x720，FPS改为25，启动时停用Aero打钩</p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_14.png" alt="">
            <p class="help-p">4.右键单击来源面板，选择显示器获取，将获取鼠标、获取窗口图层和兼容模式都打钩
                然后将区域打钩，点击选择区域，将选框拉到你的游戏画面一样按回车就行了</p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_15.png" alt="">
            <p class="help-p">5.设置完一切之后，点击开始串流按钮就可以进行直播了！另外点击编辑场景可以将直播画面进行放大缩小除掉黑边框。点击麦克风和喇叭可以关掉和开启麦克分和游戏声音。</p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_16.png" alt="">
            <p class="help-p"><b class="blue">有任何问题请加：蓝鲨TV官方交流群：211423196 </b></p>
            <p class="help-p"><em>关于提升、降低画面清晰度和流畅度</em></p>
            <p class="help-p">在编码中将品质调节成10，将最大比特率调高，可以对直播清晰度进行提升。但是这样做对网络上行带宽的压力很大，调得越高会越卡，如果你网络好就把这些调高，反之就把这些调低。一般家用20m电信宽带，最大比特率调节至2000以下，公司宽带最大比特率可以调节得更高一些。</p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_17.png" alt="">
            <p class="help-p"><em>关于提升、降低CPU使用率和直播流畅度</em></p>
            <p class="help-p">用OBS直播工具开播是很耗电脑CPU和内存的，如果你的电脑硬件配置不是很好可以在这里进行调节来降低电脑已经的压力，让直播画面更流程。默认为veryfast，往上选择是CPU耗能降低画面质量也降低，往下选择是CPU耗能增高画面质量也变好。</p>
            <img src="${staticPath! }/static/lansha/upload/pc_new_18.png" alt="">

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