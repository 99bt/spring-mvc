<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
	<@lansha.meta />
    <title>${platFormName! }</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/jquery.xBox.min.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/center.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/style.css${staticVersion! }">
</head>
<body>
<@lansha.head index="1" showdiv="1"/>

<div class="container">
    <div class="layout  help-box user-center clearfix">
    	<#-- 用户菜单 -->
        <@lansha.userLeft index="1"/>
        
        <div class="help-right fr">
            <@lansha.userCenter />
            
            <div class="user-con">
                <div class="user-conTitle clearfix">
                    <a class="fl" href="center.html">基本资料</a>
                    <a class="fl" href="password.html">修改密码</a>
                    <a class="fl" href="telphoneAuthe.html">绑定手机</a>
                    <a class="fl" href="becomeAnchor.html">主播认证</a>
                    <a class="fl cur" href="#">邀请朋友</a>
                </div>
                <div class="user-recommend mgt20 clearfix">
                	<h3 class="fl">邀请朋友加入蓝鲨:</h3>
                    <input type="text" id="forUrl" class="user-copy01 fl" value="${(url)!}/lasha/register.html?pn=${(user.id)!}"  data-apiurl="${staticPath! }/static/lansha/static/flash/ZeroClipboard.swf"  readonly />
                    <input type="button" value="复制" class="user-copy02 fl" />
                    <p class="fl">已成功邀请<span>${(countRegister)!}</span>个人<span>${(countHost)!}</span>个主播</p>
                </div>
    &nbsp;
            </div>
        </div>
    </div>
</div>

<@lansha.foot />
<#-- 页尾js -->
<@lansha.footjs />
<script>
    $(function () {
    	if (window.clipboardData) {
            $(".user-copy02").click(function () {
                window.clipboardData.setData("Text", $("input.user-copy01").val());
                $.xbox.tips("邀请链接已复制到剪切板！");
            });
        } else {
            swfUrl = $(".user-copy01").data("apiurl");
            ZeroClipboard.config({
                swfPath: swfUrl
            });
            var client = new ZeroClipboard($(".user-copy02"));
            client.on("copy", function (event) {
                var clipboard = event.clipboardData;
                clipboard.setData("text/plain", $("input.user-copy01").val());
                $.xbox.tips("邀请链接已复制到剪切板！");
            });
        }
    })
</script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/ZeroClipboard.min.js${staticVersion! }"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/upload/plupload.full.min.js"></script>
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>
</body>
</html>