<#import "/lansha/web/common/lansha.ftl" as lansha>
<!DOCTYPE html>
<html lang="en">
<head>
    <@lansha.meta />
    <title>${platFormName! }-注册账号</title>
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/common.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/user.css${staticVersion! }">
    <link rel="stylesheet" href="${staticPath! }/static/lansha/static/css/jquery.xBox.min.css${staticVersion! }">
</head>
<body>
<@lansha.loginHead title= "注册${platFormName! }帐号"/>
<div class="container login clearfix">
    <div class="layout">
        <div class="login-left fl">
            <form id="frmUserReg" action="doregister.html">
            <input type="hidden" name="type" id="type" value="0">
            <div class="input-box">
                <div class="clearfix">
                    <p>手机号<span class="infoArea fr iMPTEL"></span></p>
                    <input class="reg-haveNum fl" id="mTel" type="text" placeholder="请输入手机号" maxlength="11" data-tips="请输入您的手机号码" autocomplete="off"  onkeydown="if(event.keyCode==13) {document.getElementById('SendCode').click();return false;}">
                    <input type="hidden" name="ywUser.username" value="" id="tTel" autocomplete="off">
                    <a class="have-code fr" id="SendCode"  data-rcode="1" data-apiurl="rcode.html">发送验证码</a>
                </div>
                <div>
                <p>验证码<span class="infoArea fr iRcode"></span></p>
                <input type="text" name="rcode" id="rcode" placeholder="请输入手机验证码" maxlength="6" data-tips="请输入手机验证码" autocomplete="off" onkeydown="if(event.keyCode==13) {document.getElementById('nickname').focus();return false;}">
                </div>
                <div>
                <p>昵称<span class="infoArea fr iNickname"></span></p>
                <input type="text" name="ywUser.nickname" id="nickname" placeholder="请输入您的昵称(8位汉字或16位字母数字下划线的组合)" maxlength="16" data-tips="请输入您的昵称(8位汉字或16位字母数字下划线的组合)" autocomplete="off"  onkeydown="if(event.keyCode==13) {document.getElementById('password').focus();return false;}">
                </div>
                <div class="clearfix" id="gendercol" >
                    <p class="fl">性别</p>
                    <label class="checkbox checked" data-value="1" ><span class="fl checkbox-icon"></span><span class="fl">男</span></label>
                    <label class="checkbox" data-value="2"><span class="fl checkbox-icon"></span><span class="fl">女</span></label>
                    <input type="hidden" name="ywUser.sex" id="gender" value="1" autocomplete="off" >
                </div>
                <div class="file clearfix">
                    <p class="fl">头像</p>
                    
                    <div class="regheader">
	                    <img class="fl file-img" id="headerFace" src="<#if (ywUser.headpic)?default("") == "">${staticPath! }/static/lansha/upload/default.png${staticVersion! }<#else>${uploadPath! }${(ywUser.headpic)!}</#if>" onerror="this.src='${staticPath! }/static/lansha/upload/default.png${staticVersion! }'" alt="上传图片">
                        <!--<span class="hide-file"></span>-->
                        <a class="fl file-input" id="isupload" data-apiurl='${contextPath}/uploadImg.html'>正在初始化</a>
                    </div>
                    
                    <input type="hidden" name="ywUser.headpic" id="headerFaceVal" value="${(ywUser.headpic)!}" autocomplete="off" />
                    <div class="wrongface"></div>
                </div>
                <div>
                    <p>设置密码<span class="infoArea fr iPassword">请输入密码</span></p>
                    <input type="password"  id="password" placeholder="请输入密码" data-tips="请输入6-16位密码" autocomplete="off" onkeydown="if(event.keyCode==13) {document.getElementById('password2').focus();return false;}">
                </div>
                <div>
                    <p>确认密码<span class="infoArea fr iPassword2"></span></p>
                    <input type="password"  id="password2" placeholder="请确认密码" data-tips="请再次输入密码" autocomplete="off" onkeydown="if(event.keyCode==13) {document.getElementById('regsubmit').click();return false;}">
                </div>
                <input type="hidden" name="ywUser.password" id="pwdhide" value="" autocomplete="off">
                <a class="login-sub fl" href="javascript:;" id="regsubmit">注册</a>
                <p class="law clearfix">注册即默认同意蓝鲨TV <a href="pc-agreement.html" target="_blank">《用户注册协议》</a></p>
                <span class="find-pwd fl">已有帐号？</span><a class="have-reg fl" href="login.html">立即登录</a>
            </div>
            </form>
        </div>
        <div class="login-right  fl">
            <@lansha.othreLogin />
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
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jq_input.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.md5.min.js"></script>
<script type="text/javascript" src="${staticPath! }/static/lansha/static/js/jquery.YWPlaceholder.min.js"></script>

<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/upload/plupload.full.min.js"></script>
<script type="text/javascript" src="${jsPath! }/static/lansha/static/js/user.js${staticVersion! }"></script>
<script type="text/javascript">
    var PIC_PATH = '../';
    //调用用户注册
    $(function(){
        new LSUser.register().init();
    });
</script>
</body>
</html>