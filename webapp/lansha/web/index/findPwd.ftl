<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta />
    <title>${platFormName! }-找回密码</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/user.css${staticVersion! }">
</head>
<body class="fffbf8-body">
<@lansha.loginHead title= "找回账号密码"/>

<div class="container login clearfix">
    <div class="layout">
        <div class="login-left fl">
            <form id="frmFindPwd" action="doupdatePassword.html">
             <input type="hidden" name="type" id="type" value="1">
            <div class="input-box">
                <div class="clearfix">
                    <p>手机号<span class="infoArea fr iMPTEL"></span></p>
                    <input class="reg-haveNum fl" id="mTel"  type="text" placeholder="请输入手机号" maxlength="11" data-tips="请输入您的手机号码">
                   	<input type="hidden" name="ywUser.mobile" value="" id="tTel">
                   	<!--
                   		<button class="have-code fr" id="SendCodeFPWD" type="button" data-apiurl="rcode.html?type=1">发送验证码</button>
                   	-->
                    
               		<a class="have-code fr" id="SendCodeFPWD" data-rcode="1" data-apiurl="rcode.html?type=1">发送验证码</a>
                </div>
                <div>
                    <p>验证码<span class="infoArea fr iRcode"></span></p>
                    <input type="text" name="rcode" id="rcode" maxlength="6" placeholder="请输入接收到的手机验证码" data-tips="请输入接收到的手机验证码">
                </div>
                <div>
                    <p>设置新密码<span class="infoArea fr iPassword"></p>
                    <input type="password"  maxlength="16" id="password" placeholder="请输入6-16位新密码" data-tips="请输入6-16位新密码">
                </div>
                <div>
                    <p>确认密码<span class="infoArea fr iPassword2"></p>
                    <input type="password"  maxlength="16" id="password2" placeholder="请再次输入新密码" data-tips="请再次输入新密码">
                </div>
                <input type="hidden" name="ywUser.password" id="pwdhide" value="">
                <a class="login-sub fl" href="javascript:;" id="frmFindPwdSubmit">确定</a>
                <!--<a class="have-reg fl" href="reg.html">还没有帐号？立即注册</a>-->
                <!--<a class="find-pwd fr" href="findPwd.html">忘记密码？</a>-->
            </div>
            </form>
        </div>
        <div class="login-right fl">
            <p>第三方帐号直接登录</p>
            <a class="wechat gray fl" href="#">微信账号登录</a>
            <a class="qq gray fl" href="#">QQ账号登录</a>
            <a class="sina gray fl" href="#">新浪微博登录</a>
        </div>
    </div>
</div>
<@lansha.foot />
<!--发送站内验证码-->
<div class="RcodeBox">
    <span>请输入验证码完成本次发送</span>
    <span class="wrongText" id="wrongText"></span>
    <span><input id="captchaInput" name="captchaCode" type="text" class="picCode" maxlength="6"></span>
    <div>
        <img id="captchaImage" data-apiurl="${contextPath! }/web/validate.html" class="codePic">
        <a class="changePic" id="changeCaptchaImage">看不清,换一个</a>
    </div>
    <a class="allBtn confirmBtn" id="checkRcode">确认</a>
    <a class="allBtn cancelBtn" id="cancelBtn">取消</a>
    <div class="clear"></div>
</div>
<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.md5.min.js"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>
<script type="text/javascript">
    var PIC_PATH = '../';
    //调用用户注册
    $(function(){
        new LSUser.findPwd().init();
    });
</script>
</body>
</html>