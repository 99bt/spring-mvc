<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta />
    <title>${platFormName! }-登录</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/user.css${staticVersion! }">
</head>
<body>
<@lansha.head index=""/>
<#assign userIsLogin = userIsLogin?default(false) />
<div class="container login clearfix">
    <div class="layout">
        <div class="login-left fl">
            <form id="frmUserLogin" action="dologin.html">
            <input type="hidden" name="backUrl" id="backUrl" value="${backUrl! }" />
            <div class="input-box">
                <div>
                    <p>手机号<span class="infoArea fr iMPTEL"></span></p>
                    <input type="text" id="mTel" name="ywUser.username" maxlength="11" placeholder="请输入手机号" data-tips="请输入您的手机号" onkeydown="if(event.keyCode==13) {jQuery('#password').focus();return false;}" autocomplete="off">
                </div>
                <div>
                    <p>密码<span class="infoArea fr iPassword"></span></p>
                    <input type="password"  id="password" placeholder="请输入密码" data-tips="请输入6-16位密码" onkeydown="if(event.keyCode==13) {jQuery('#frmSubmit').click();return false;}" autocomplete="off">
                </div>
				<input type="hidden" name="ywUser.password" id="pwdhide" value="">
                <a class="login-sub fl" id="frmSubmit" href="javascript:;">登录</a>
                <span class="find-pwd fl">还没有帐号？</span><a class="have-reg fl" href="${contextPath! }/register.html">立即注册</a>
                <a class="find-pwd fr" href="findPwd.html">忘记密码？</a>
            </div>
            </form>
        </div>
        <div class="login-right  fl">
            <@lansha.othreLogin />
        </div>
    </div>
</div>
<@lansha.foot />

<#-- 页尾js -->
<@lansha.footjs />
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.md5.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.YWPlaceholder.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>
<script type="text/javascript">
    var PIC_PATH = '../';
    //调用用户注册
    $(function(){
        new LSUser.login().init();
    });
</script>
</body>
</html>