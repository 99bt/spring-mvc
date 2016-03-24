/**
 * LSLive User
 * Yowant FE Team
 * v2.3 201603016
 */
LSUser = function () {
    this.version = '201603016';
    this.MAX_UPLOAD = '2mb';
    this.OUT_TIME = 5;
};
LSUser.prototype = {
    //校验手机号
    isMPhone: function (phoneNumber) {
        var reg_MPnum = /^0{0,1}(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$/;
        var isMPhone = true;
        if (phoneNumber == "" || !reg_MPnum.test(phoneNumber)) {
            isMPhone = false;
        }
        return isMPhone;
    },
    isNickname: function (nickname) {
        var regNickNameHZ = /^[\u4E00-\u9FA5]{1,8}$/;
        var regNickName = /^[\u4E00-\u9FA50-9A-Za-z_]+$/;
        var isNickname = true;
        var length = 0, isCn = true, len = nickname.length;
        if (len < 1) {
            isNickname = false;
        } else {
            for (var i = 0; i < len; i++) {
                if (/^[\u4E00-\u9FA5]$/.test(nickname[i])) {
                    length += 2;
                } else {
                    isCn = false;
                    length += 1;
                }
            }
            if (isCn) {
                if (length > 16) {
                    isNickname = false;
                }
            } else {
                if (length > 16 || !/^[\u4E00-\u9FA50-9A-Za-z_]+$/.test(nickname)) {
                    isNickname = false;
                }
            }
        }
        return isNickname;
    },
    isIdCard: function (IdCard) {
        var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
        var isIdCard = true;
        if (!regIdCard.test(IdCard)) {
            isIdCard = false;
        }
        ;
        return isIdCard;
    },
    inputTips: function () {
        $('.input-box input').focus(function () {
                var tips = $(this).data('tips');
                $(this).addClass('on');
                $(this).parent().find('p .infoArea').removeClass('error');
                $(this).parent().find('p .infoArea').html(tips);
                $(this).parent().find('p .infoArea').show();
            })
            .blur(function () {
                $(this).removeClass('on');
                $(this).parent().find('p .infoArea').removeClass('error');
                $(this).parent().find('p .infoArea').html('');
            });
    },
    checkFileType: function (file) {
        var fileName = file.name;
        var fileType = fileName.substring(fileName.lastIndexOf('.'), fileName.length).toLowerCase();
        var isImage = true;
        if (fileType != '.jpg' && fileType != '.png' && fileType != '.jpeg' && fileType != '.gif') {
            isImage = false;
        }
        return isImage;
    },
    upload: function (browse_button, callback, button_text) {
        var _this = this;
        var _button_text = button_text || '';
        var apiurl = $.trim($('#' + browse_button).data('apiurl'));
        if (apiurl == '' || apiurl == undefined) {
            $.xbox.tips('上传接口配置错误');
        }
        ;
        var uploader = new plupload.Uploader({
            runtimes: 'html5,html4',
            browse_button: browse_button, //选择图片按钮ID
            dragdrop: false,
            auto_start: true,
            multi_selection: false,
            url: apiurl,
            flash_swf_url: BASE_PATH + 'static/js/upload/Moxie.swf',
            filter: {
                max_file_size: _this.MAX_UPLOAD,
                mime_types: [
                    {title: "图片文件", extensions: "jpg,png,jpeg,gif"}
                ]
            },
            init: {
                Init: function () {
                    if (_button_text != '') {
                        $('#' + browse_button).text(_button_text);
                    } else {
                        $('#' + browse_button).text('上传');
                    }
                },
                PostInit: function () {
                },
                //添加文件
                FilesAdded: function (uploader, files) {
                    plupload.each(files, function (file) {
                        if (!_this.checkFileType(file)) {
                            uploader.removeFile(file);
                            $.xbox.tips("图片格式错误");
                        } else {
                            uploader.start();
                            return false;
                        }
                    });
                },
                //上传进度
                UploadProgress: function (uploader, file) {
                },
                //上传完成
                FileUploaded: function (uploader, file, info) {
                    var fileInfo = $.parseJSON(info.response);
                    if (fileInfo.status == 0) {
                        $.xbox.tips(fileInfo.failed);
                        return false;
                    }
                    switch (callback) {
                        case 'modifyInfo':
                            $('#headerFace').attr('src', fileInfo.url);
                            $('#headerFaceVal').val(fileInfo.url);
                            $.xbox.tips("上传成功");
                            break;
                        case 'register':
                            $('#headerFace').attr('src', fileInfo.url);
                            $('#headerFaceVal').val(fileInfo.url);
                            $.xbox.tips("上传成功");
                            break;
                        case 'SMRZ_zm':
                            $('#img_cardzm').attr('src', fileInfo.url);
                            $('#img_cardzm_val').val(fileInfo.url);
                            $.xbox.tips("上传成功");
                            break;
                        case 'SMRZ_fm':
                            $('#img_cardfm').attr('src', fileInfo.url);
                            $('#img_cardfm_val').val(fileInfo.url);
                            $.xbox.tips("上传成功");
                            break;
                        case 'SMRZ_sc':
                            $('#img_cardsc').attr('src', fileInfo.url);
                            $('#img_cardsc_val').val(fileInfo.url);
                            $.xbox.tips("上传成功");
                            break;
                        default:
                            $.xbox.tips("上传成功");
                    }
                },
                Error: function (uploader, err) {
                    $.xbox.tips("上传失败");
                }
            }
        });
        uploader.init();
    },
    outTimeGo: function (goURL, outTime, msgPre) {
        var wait = outTime;

        function ddTime() {
            if (wait == 0) {
                window.location.href = goURL;
            } else {
                wait--;
                setTimeout(function () {
                    ddTime()
                }, 1000);
            }
        }

        $.xbox.tips(msgPre, (outTime + 1) * 1000);
        ddTime();
    },
    /**
     * 刷新验证码
     * @param CaptchaImageID  验证码显示的图片ID
     */
    refreshCaptchaImage: function (CaptchaImageID) {
        var sSrc = $('#' + CaptchaImageID).data('apiurl');
        var d = new Date();
        $('#' + CaptchaImageID).attr('src', sSrc + '?t=' + d.getTime());
    },
    /**
     * 发送短信验证码
     * @param sendObj 发送按钮对象.如: '.sendCode'
     * @param telPhoneObj 手机号对象.如: '.telPhone'
     * @param tipsObj 提示信息对象.如:'.tips',若为null 则使用xbox.tips提示
     * @param sendTime 发送间隔
     * @returns {boolean}
     */
    sendSMSRcode: function (sendObj, telPhoneObj, tipsObj, sendTime) {
        var lsuser = new LSUser();
        var telphone = $(telPhoneObj).val() || '';
        var API_URL = $.trim($(sendObj).data('apiurl')) || null;
        var isRcode = $.trim($(sendObj).data('rcode')) || 0;
        var captchaCode = '';

        if (API_URL == '' || API_URL == undefined) {
            $.xbox.tips('验证码接口配置错误！');
            return false;
        }
        if ($(sendObj).hasClass('disabled')) {
            return false;
        }
        if (lsuser.isMPhone(telphone)) {
            if (isRcode == 1) {
                $.xbox.showMask();
                $('.RcodeBox').show();
                $('#wrongText').text('');

                $('#checkRcode,#captchaImage, #changeCaptchaImage,#cancelBtn').unbind('click');
                //刷新验证码
                $('#captchaImage, #changeCaptchaImage').on('click', function () {
                    lsuser.refreshCaptchaImage('captchaImage');
                });
                $('#captchaInput').val('');
                $('#captchaImage').click();
                //关闭发送站内验证码
                $('#cancelBtn').on('click', function () {
                    $('#xbox_mask_div').remove();
                    $('.RcodeBox').hide();
                });
                //检查输入的验证码
                $('#checkRcode').on('click', function () {
                    captchaCode = $.trim($('#captchaInput').val()) || '';
                    if (captchaCode == '' || captchaCode.length > 6) {
                        $('#wrongText').text('验证码输入错误');
                    } else {
                        $('#wrongText').text('');
                        $(telPhoneObj).attr("disabled", true).addClass("disabled");
                        lsuser.sendSMS(telPhoneObj, sendObj, sendTime, tipsObj, API_URL, telphone, captchaCode);
                    }
                });
            } else {
                $(telPhoneObj).attr("disabled", true).addClass("disabled");
                lsuser.sendSMS(telPhoneObj, sendObj, sendTime, tipsObj, API_URL, telphone, captchaCode);
            }
        } else {
            if (tipsObj == null) {
                $.xbox.tips('请输入正确的手机号');
            } else {
                $(tipsObj).addClass('error').text("请输入正确的手机号").show();
            }
            return false;
        }
    },
    /**
     * 发送短信
     * @param telPhoneObj  手机号码输入框
     * @param sendObj   发送的对象
     * @param sendTime  发送间隔时间
     * @param tipsObj   提示对象
     * @param API_URL   短信接口地址
     * @param telphone  发送的手机号
     * @param captchaCode   验证码
     */
    sendSMS: function (telPhoneObj, sendObj, sendTime, tipsObj, API_URL, telphone, captchaCode) {
        var t_dt = null;
        var sendWait = sendTime;

        function dtime() {
            if (sendWait == 0) {
                $(telPhoneObj).attr("disabled", false).removeClass("disabled");
                $(sendObj).attr("disabled", false).removeClass('disabled').text("发送验证码");
                if (tipsObj != null) {
                    $(tipsObj).text('').addClass('error').hide();
                }
                sendWait = sendTime;
            } else {
                $(telPhoneObj).attr("disabled", true).addClass("disabled");
                $(sendObj).attr("disabled", true).addClass('disabled').text("重新发送(" + sendWait + ")");
                sendWait--;
                t_dt = setTimeout(function () {
                    dtime()
                }, 1000);
            }
        }

        dtime();
        $.ajax({
            type: "POST",
            url: API_URL,
            data: {telphone: telphone, captchaCode: captchaCode},
            dataType: "json",
            success: function (data) {
                if (data.status == '1') {
                    if (tipsObj == null) {
                        $.xbox.tips('短信发送成功，请注意查收！');
                    } else {
                        $(tipsObj).text('短信发送成功，请注意查收！').show();
                    }
                    $('#cancelBtn').click();

                } else {
                    if (tipsObj == null) {
                        $.xbox.tips(data.failed);
                    } else {
                        $('#wrongText').text(data.failed);
                        $('#captchaImage').click();
                    }
                    clearTimeout(t_dt);
                    $(telPhoneObj).attr("disabled", false).removeClass('disabled');
                    $(sendObj).attr("disabled", false).removeClass('disabled').text("发送验证码");
                    return false;
                }
            }
        });
    }
}
/**
 * 注册验证
 */
LSUser.register = function () {
    _this = this;
    _this.sendTime = 60;
    _this.OUT_TIME = 1;
};
LSUser.register.prototype = {
    init: function () {
        var lsuser = new LSUser;
        this.op();
        lsuser.upload('isupload', 'register', '上传头像');
    },
    op: function () {
        var _this = this;
        var lsuser = new LSUser;
        lsuser.inputTips();
        $('#gendercol .checkbox').on('click', function () {
            $('#gendercol .checkbox').removeClass('checked');
            $(this).addClass('checked');
            $('#gender').val($(this).data('value'));
        });
        //发送验证码
        $("#SendCode").on("click", function () {
            lsuser.refreshCaptchaImage('captchaImage');
            lsuser.sendSMSRcode('#SendCode', '#mTel', '.iMPTEL', _this.sendTime);
        });
        //提交按钮
        $('#regsubmit').on('click', function () {
            var API_URL = $('#frmUserReg').attr('action');
            var theBtn = $(this);
            if (API_URL == '' || API_URL == undefined) {
                $.xbox.tips('API_URL配置错误！');
                return false;
            }
            ;
            if (theBtn.hasClass('disabled')) {
                return false;
            }
            ;
            $('#tTel').val($.trim($('#mTel').val()));
            if (_this.check()) {
                theBtn.addClass('disabled');
                /** 验证通过 */
                    //md5 加密密码
                $('#pwdhide').val($.md5($.trim($('#password').val())));
                $.ajax({
                    type: "POST",
                    url: API_URL,
                    data: $('#frmUserReg').serialize(),
                    dataType: "json",
                    success: function (data) {
                        if (data.status == '1') {
                            if (data.url != '') {
                                lsuser.outTimeGo(data.url, _this.OUT_TIME, '恭喜您，注册成功！');
                            }
                            if (typeof regSuccess == "function") {//行为分析代码
                                eval("regSuccess()");
                            }
                        } else {
                            $.xbox.tips(data.failed);
                            theBtn.removeClass('disabled');
                            if (typeof regFailed == "function") {//行为分析代码
                                eval("regFailed()");
                            }
                            return false;
                        }
                    }
                });
            } else {
                theBtn.removeClass('disabled');
                return false;
            }
        });
    },
    //提交检测
    check: function () {
        var _this = this;
        var isPass = true;
        var lsuser = new LSUser;
        var telphone = $.trim($('#mTel').val());
        var nickname = $.trim($('#nickname').val());
        var checkTel = lsuser.isMPhone(telphone);
        var password = $.trim($("#password").val());
        var password2 = $.trim($("#password2").val());
        if ($.trim($("#rcode").val()) == "") {
            $(".iRcode").addClass('error').text('请输入手机验证码').show();
            isPass = false;
        } else {
            $(".iRcode").text('').hide();
        }
        if (!lsuser.isNickname(nickname)) {
            $(".iNickname").addClass('error').text('请输入正确的昵称(8个汉字或16个字母数字下划线的组合)').show();
            isPass = false;
        } else {
            $('.iNickname').removeClass('error').text("").hide();
        }
        if (telphone == '') {
            $('.iMPTEL').addClass('error').text("请输入您的手机号").show();
            isPass = false;
        } else if (!checkTel) {
            $('.iMPTEL').addClass('error').text("请输入正确的手机号").show();
            isPass = false;
        } else {
            $('.iMPTEL').removeClass('error').text("").hide();
        }
        if (password.length < 6 || password.length > 16) {
            $(".iPassword").addClass('error').text('输入密码有误，请输入6～16位的密码').show();
            isPass = false;
        } else {
            $(".iPassword").text('').hide();
        }
        if (password2 != password) {
            $(".iPassword2").addClass('error').text("输入密码不一致").show();
            isPass = false;
        } else {
            $(".iPassword2").text('').hide();
        }
        return isPass;
    }
}
/**
 * 登录验证
 */
LSUser.login = function () {
    this.OUT_TIME = 1;
};
LSUser.login.prototype = {
    init: function () {
        this.op();
    },
    op: function () {
        var _this = this;
        var lsuser = new LSUser;
        $('.input-box input').focus(function () {
                var tips = $(this).data('tips');
                $(this).addClass('on');
                $(this).parent().find('p .infoArea').removeClass('error');
                $(this).parent().find('p .infoArea').html(tips);
                $(this).parent().find('p .infoArea').show();
            })
            .blur(function () {
                $(this).removeClass('on');
                $(this).parent().find('p .infoArea').removeClass('error');
                $(this).parent().find('p .infoArea').html('');
            });
        $('#frmSubmit').on('click', function () {
            var API_URL = $.trim($('#frmUserLogin').attr('action'));
            var theBtn = $(this);
            if (API_URL == '' || API_URL == undefined) {
                $.xbox.tips('API_URL配置错误！');
                return false;
            }
            if (theBtn.hasClass('disabled')) {
                return false;
            }
            if (_this.check()) {
                theBtn.addClass('disabled');
                /** 验证通过 **/
                    //md5 加密密码
                $('#pwdhide').val($.md5($.trim($('#password').val())));
                $.ajax({
                    type: "POST",
                    url: API_URL,
                    data: $('#frmUserLogin').serialize(),
                    dataType: "json",
                    success: function (data) {
                        if (data.status == '1') {
                            $.xbox.tips('恭喜您，登录成功！');
                            if (typeof loginSuccess == "function") {//行为分析代码
                                eval("loginSuccess()");
                            }
                            if (data.url != '') {
                                lsuser.outTimeGo(data.url, _this.OUT_TIME, '恭喜您，登录成功！');
                            }
                            btnThis.removeClass('disabled');
                        } else {
                            $.xbox.tips(data.failed);
                            theBtn.removeClass('disabled');
                            if (typeof loginFailed == "function") {//行为分析代码
                                eval("loginFailed()");
                            }
                            return false;
                        }
                    }
                });
            } else {
                theBtn.removeClass('disabled');
                return false;
            }
        });
    },
    check: function () {
        var isPass = true;
        var lsuser = new LSUser;
        var telphone = $.trim($('#mTel').val());
        var password = $.trim($('#password').val());
        var checkTel = lsuser.isMPhone(telphone);
        if (telphone == '') {
            $('.iMPTEL').addClass('error').text("请输入您的手机号").show();
            isPass = false;
        } else if (!checkTel) {
            $('.iMPTEL').addClass('error').text("请输入正确的手机号").show();
            isPass = false;
        } else {
            $('.iMPTEL').removeClass('error').text("").hide();
        }
        if (password == '') {
            $('.iPassword').addClass('error').text("请输入您的密码").show();
            isPass = false;
        } else if (password.length < 6 || password.length > 16) {
            $('.iPassword').addClass('error').text("请输入正确密码（6-16位）").show();
            isPass = false;
        } else {
            $('.iPassword').removeClass('error').text("").hide();
        }
        ;
        return isPass;
    }
}
/**
 * 找回密码
 */
LSUser.findPwd = function () {
    _this = this;
    _this.sendTime = 60;
    _this.OUT_TIME = 1;
};
LSUser.findPwd.prototype = {
    init: function () {
        this.op();
    },
    op: function () {
        var _this = this;
        var lsuser = new LSUser;
        lsuser.inputTips();
        //发送验证码
        $("#SendCodeFPWD").on("click", function () {
            lsuser.sendSMSRcode('#SendCodeFPWD', '#mTel', '.iMPTEL', _this.sendTime);
        });
        $('#frmFindPwdSubmit').on('click', function () {
            var API_URL = $.trim($('#frmFindPwd').attr('action'));
            var btnThis = $(this);
            if (API_URL == '' || API_URL == undefined) {
                $.xbox.tips('API_URL配置错误！');
                return false;
            }
            ;
            if (btnThis.hasClass('disabled')) {
                return false;
            }
            ;
            $('#tTel').val($.trim($('#mTel').val()));
            if (_this.check()) {
                btnThis.addClass('disabled');
                /** 验证通过 */
                    //md5 加密密码
                $('#pwdhide').val($.md5($.trim($('#password').val())));
                $.ajax({
                    type: "POST",
                    url: API_URL,
                    data: $('#frmFindPwd').serialize(),
                    dataType: "json",
                    success: function (data) {
                        if (data.status == '1') {
                            if (data.url != '') {
                                lsuser.outTimeGo(data.url, _this.OUT_TIME, '恭喜您，密码修改成功！');
                            }
                        } else {
                            $.xbox.tips(data.failed);
                            btnThis.removeClass('disabled');
                            return false;
                        }
                    }
                });
            } else {
                btnThis.removeClass('disabled');
                return false;
            }
        });
    },
    check: function () {
        var _this = this;
        var isPass = true;
        var lsuser = new LSUser;
        var telphone = $.trim($('#mTel').val());
        var rcode = $.trim($('#rcode').val());
        var password = $.trim($('#password').val());
        var password2 = $.trim($('#password2').val());
        var checkTel = lsuser.isMPhone(telphone);
        if (telphone == '') {
            $('.iMPTEL').addClass('error').text("请输入您的手机号").show();
            isPass = false;
        } else if (!checkTel) {
            $('.iMPTEL').addClass('error').text("请输入正确的手机号").show();
            isPass = false;
        } else {
            $('.iMPTEL').removeClass('error').text("").hide();
            isPass = true;
        }
        ;
        if (rcode == '') {
            $('.iRcode').addClass('error').text("请输入接收到的验证码").show();
            isPass = false;
        } else if (rcode.length > 6) {
            $('.iRcode').addClass('error').text("请输入正确的验证码").show();
            isPass = false;
        } else {
            $('.iRcode').removeClass('error').text("").hide();
            isPass = true;
        }
        ;
        if (password == '') {
            $('.iPassword').addClass('error').text("请输入新密码").show();
            isPass = false;
        } else if (password.length < 6 || password.length > 16) {
            $('.iPassword').addClass('error').text("请输入正确密码（6-16位）").show();
            isPass = false;
        } else {
            $('.iPassword').removeClass('error').text("").hide();
            isPass = true;
        }
        ;
        if (password2 == '') {
            $('.iPassword2').addClass('error').text("请再次输入新密码").show();
            isPass = false;
        } else if (password2 != password) {
            $('.iPassword2').addClass('error').text("两次输入的密码不匹配").show();
            isPass = false;
        } else {
            $('.iPassword2').removeClass('error').text("").hide();
            isPass = true;
        }
        ;
        return isPass;
    }
}
/**
 * 修改资料
 */
LSUser.modifyInfo = function () {
    this.OUT_TIME = 1;
};
LSUser.modifyInfo.prototype = {
    init: function () {
        var lsuser = new LSUser;
        this.op();
        lsuser.upload('isupload', 'modifyInfo');
    },
    op: function () {
        var _this = this;
        var lsuser = new LSUser;
        //修改昵称
        $(".change-btn").on("click", function () {
            $(this).hide();
            $(this).parent(".realname-one").find(".realName").addClass("cur").removeAttr("disabled");
            $(this).parent(".realname-one").find(".change-certain").show();
            $(this).parent(".realname-one").find(".change-cancel").show();
        });
        $(".change-cancel").on("click", function () {
            $(this).hide();
            $(this).parent(".realname-one").find(".change-certain").hide();
            $(this).parent(".realname-one").find(".realName").removeClass("cur").attr("disabled", true);
            $(this).parent(".realname-one").find(".change-btn").show();
        });
        //选择性别
        $('#gendercol .checkbox').on('click', function () {
            $('#gendercol .checkbox').removeClass('checked');
            $(this).addClass('checked');
            $('#gender').val($(this).data('value'));
        });
        //绑定回车提交
        $('#realName').bind('keyup', function (event) {
            if (event.keyCode == "13") {
                $('#saveNickName').click();
                return false;
            }
        });
        //保存昵称
        $('#saveNickName').on('click', function () {
            var btnThis = $(this);
            var API_URL = $('#frmUserNickName').attr('action');
            if (API_URL == '' || API_URL == undefined) {
                $.xbox.tips('API_URL配置错误！');
                return false;
            }
            ;
            var nickname = $.trim($('#realName').val());
            if (!btnThis.hasClass('disabled')) {
                btnThis.addClass('disabled');
                if (lsuser.isNickname(nickname)) {
                    /** 验证通过 */
                    $.ajax({
                        type: "POST",
                        url: API_URL,
                        data: $('#frmUserNickName').serialize(),
                        dataType: "json",
                        success: function (data) {
                            if (data.status == '1') {
                                if (data.url != '') {
                                    lsuser.outTimeGo(data.url, _this.OUT_TIME, '恭喜您，昵称修改成功！');
                                }
                            } else {
                                $.xbox.tips(data.failed);
                                btnThis.removeClass('disabled');
                                return false;
                            }
                        }
                    });
                } else {
                    $.xbox.tips('请输入正确的昵称(8个汉字或16个字母数字下划线的组合)');
                    btnThis.removeClass('disabled');
                    return false;
                }
            }
        });
        //保存基本资料
        $('#frmUserInfo_submit').on('click', function () {
            var btnThis = $(this);
            var API_URL = $('#frmUserInfo').attr('action');
            if (API_URL == '' || API_URL == undefined) {
                $.xbox.tips('API_URL配置错误！');
                return false;
            }
            if (!btnThis.hasClass('disabled')) {
                btnThis.addClass('disabled');
                $.ajax({
                    type: "POST",
                    url: API_URL,
                    data: $('#frmUserInfo').serialize(),
                    dataType: "json",
                    success: function (data) {
                        if (data.status == '1') {
                            if (data.url != '') {
                                lsuser.outTimeGo(data.url, _this.OUT_TIME, '恭喜您，资料修改成功！');
                            }
                            btnThis.removeClass('disabled');
                        } else {
                            $.xbox.tips(data.failed);
                            btnThis.removeClass('disabled');
                            return false;
                        }
                    }
                });
            }
        });
    },
    //复制推广链接
    copyCode: function (event) {
        if (window.clipboardData) {
            $(".user-copy02").click(function () {
                window.clipboardData.setData("Text", $("input.user-copy01").val());
                $.xbox.tips("推广链接已复制到剪切板！");
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
                $.xbox.tips("推广链接已复制到剪切板！");
            });
        }
    }
    //end
}
/**
 * 修改密码
 */
LSUser.modifyPWD = function () {
    _this = this;
    _this.OUT_TIME = 1;
};
LSUser.modifyPWD.prototype = {
    init: function () {
        this.op();
    },
    op: function () {
        var _this = this;
        var lsuser = new LSUser;
        $('.realname-one input').focus(function () {
                $(this).addClass('on');
            })
            .blur(function () {
                $(this).removeClass('on');
            })
        //提交
        $('#frmChangePWD_submit').on('click', function () {
            var API_URL = $('#frmChangePWD').attr('action');
            if (API_URL == '' || API_URL == undefined) {
                $.xbox.tips('API_URL配置错误！');
                return false;
            }
            var btnThis = $(this);
            if (!btnThis.hasClass('disabled')) {
                btnThis.addClass('disabled');
                if (_this.check()) {
                    /** 验证通过 */
                        //md5 加密密码
                    $('#pwdhide').val($.md5($.trim($('#pwd').val())));
                    $('#pwdhide_old').val($.md5($.trim($('#oldpwd').val())));
                    $.ajax({
                        type: "POST",
                        url: API_URL,
                        data: $('#frmChangePWD').serialize(),
                        dataType: "json",
                        success: function (data) {
                            if (data.status == '1') {
                                if (data.url != '') {
                                    lsuser.outTimeGo(data.url, _this.OUT_TIME, '恭喜您，成功修改密码！');
                                }
                                ;
                            } else {
                                $.xbox.tips(data.failed);
                                btnThis.removeClass('disabled');
                                return false;
                            }
                        }
                    });
                } else {
                    btnThis.removeClass('disabled');
                    return false;
                }
            }
        });
    },
    check: function () {
        var _this = this;
        var isPass = true;
        var old_pwd = $.trim($("#oldpwd").val());
        var pwd = $.trim($("#pwd").val());
        var pwd2 = $.trim($("#pwd2").val());
        if (old_pwd == "") {
            $.xbox.tips('请输入您的旧密码');
            isPass = false;
        } else if (old_pwd.length < 6 || old_pwd.length > 16) {
            $.xbox.tips('您输入的旧密码有错');
            isPass = false;
        } else if (pwd == "") {
            $.xbox.tips('请输入6-16位的新密码！');
            isPass = false;
        } else if (pwd.length < 6 || pwd.length > 16) {
            $.xbox.tips('请输入6-16位的新密码！');
            isPass = false;
        } else if (pwd2 != pwd) {
            $.xbox.tips('两次输入的密码不匹配！');
            isPass = false;
        }
        return isPass;
    }
}
/**
 * 实名认证
 */
LSUser.SMRZ = function () {
    _this = this;
    _this.OUT_TIME = 1;
};
LSUser.SMRZ.prototype = {
    init: function () {
        var lsuser = new LSUser;
        this.op();
        lsuser.upload('isupload_zm', 'SMRZ_zm');
        lsuser.upload('isupload_fm', 'SMRZ_fm');
        lsuser.upload('isupload_sc', 'SMRZ_sc');
    },
    op: function () {
        var _this = this;
        var lsuser = new LSUser;
        $('.realname-one input, .realname-one textarea').focus(function () {
                $(this).addClass('on');
            })
            .blur(function () {
                $(this).removeClass('on');
            })
        //日期选择
        $('#enddate').on('click', function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd', lang: 'zh-cn', firstDayOfWeek: 1, minDate: '%y-%M-#{%d+1}'});
        });
        //提交
        $('#frmSMRZ_submit').on('click', function () {
            var btnThis = $(this);
            var API_URL = $('#frmSMRZ').attr('action');
            if (API_URL == '' || API_URL == undefined) {
                $.xbox.tips('API_URL配置错误！');
                return false;
            }
            if (!btnThis.hasClass('disabled')) {
                btnThis.addClass('disabled');
                if (_this.check()) {
                    /** 验证通过 */
                    $.ajax({
                        type: "POST",
                        url: API_URL,
                        data: $('#frmSMRZ').serialize(),
                        dataType: "json",
                        success: function (data) {
                            if (data.status == '1') {
                                if (data.url != '') {
                                    lsuser.outTimeGo(data.url, _this.OUT_TIME, '恭喜您，申请成功，等待审核！');
                                }
                                ;
                                btnThis.removeClass('disabled');
                            } else {
                                $.xbox.tips(data.failed);
                                btnThis.removeClass('disabled');
                                return false;
                            }
                        }
                    });
                } else {
                    btnThis.removeClass('disabled');
                    return false;
                }
            }
        });
    },
    check: function () {
        var _this = this;
        var lsuser = new LSUser;
        var isPass = true;
        var realName = $.trim($("#realName").val());
        var cardNo = $.trim($("#cardNo").val());
        var enddate = $.trim($("#enddate").val());
        var img_cardzm_val = $('#img_cardzm_val').val();
        var img_cardfm_val = $('#img_cardfm_val').val();
        var img_cardsc_val = $('#img_cardsc_val').val();
        var RoomName = $.trim($('#roomName').val());
        var roomNote = $.trim($('#roomNote').text());
        var gameSelect = $('#gameSelect').val();
        if (realName == "") {
            $.xbox.tips('请输入您姓名');
            isPass = false;
        } else if (!lsuser.isNickname(realName)) {
            $.xbox.tips('请输入真实的姓名');
            isPass = false;
        } else if (cardNo == '') {
            $.xbox.tips('请输入18位身份证号码！');
            isPass = false;
        } else if (!lsuser.isIdCard(cardNo)) {
            $.xbox.tips('请输入正确的18位身份证号码！');
            isPass = false;
        } else if (enddate == '') {
            $.xbox.tips('请输入身份证到期时间！');
            isPass = false;
        } else if (img_cardzm_val == '' || img_cardfm_val == '' || img_cardsc_val == '') {
            $.xbox.tips('请上传身份证正反面及手持身份证照片');
            isPass = false;
        } else if (RoomName.length < 1 || RoomName.length > 30) {
            $.xbox.tips('请输入1-30字以内的房间名称！');
            isPass = false;
        } else if (gameSelect == 0) {
            $.xbox.tips('请选择你要直播的游戏！');
            isPass = false;
        } else if (roomNote.length > 200) {
            $.xbox.tips('请输入200字以内的房间公告！');
            isPass = false;
        }
        return isPass;
    }
}
/**
 * 修改房间信息
 */
LSUser.modRoomInfo = function () {
    _this = this;
    _this.OUT_TIME = 1;
};
LSUser.modRoomInfo.prototype = {
    init: function () {
        this.op();
    },
    op: function () {
        var _this = this;
        var lsuser = new LSUser;
        $('.room-box input, .room-box textarea').focus(function () {
                $(this).addClass('on');
            })
            .blur(function () {
                $(this).removeClass('on');
            })
        $('#saveModRoomInfo').on('click', function () {
            var btnThis = $(this);
            var API_URL = $('#frmModRoomInfo').attr('action');
            if (API_URL == '' || API_URL == undefined) {
                $.xbox.tips('API_URL配置错误！');
                return false;
            }
            if (btnThis.hasClass('disabled')) {
                return false;
            }
            if (_this.check()) {
                btnThis.addClass('disabled');
                /** 验证通过 */
                $.ajax({
                    type: "POST",
                    url: API_URL,
                    data: $('#frmModRoomInfo').serialize(),
                    dataType: "json",
                    success: function (data) {
                        if (data.status == '1') {
                            if (data.url != '') {
                                lsuser.outTimeGo(data.url, _this.OUT_TIME, '恭喜您，房间信息修改成功！');
                            }
                            ;
                            btnThis.removeClass('disabled');
                        } else {
                            $.xbox.tips(data.failed);
                            btnThis.removeClass('disabled');
                            return false;
                        }
                    }
                });
            } else {
                btnThis.removeClass('disabled');
                return false;
            }
        });
    },
    check: function () {
        var isPass = true;
        var RoomName = $('#roomName').val();
        var roomNote = $('#roomNote').text();
        if (RoomName.length < 1 || RoomName.length > 30) {
            $.xbox.tips('请输入1-30字以内的房间名称！');
            isPass = false;
        } else if (roomNote.length > 200) {
            $.xbox.tips('请输入200字以内的房间公告！');
            isPass = false;
        }
        return isPass;
    }
}
/**
 * 房间添加／删除管理员
 */
LSUser.manager = function (RoomID, add_API, del_API) {
    _this = this;
    _this.roomID = RoomID;
    _this.add_API = add_API;
    _this.del_API = del_API;
    _this.OUT_TIME = 2;
};
LSUser.manager.prototype = {
    init: function () {
        this._add();
        this._remove();
    },
    _add: function () {
        var lsuser = new LSUser();
        $(".add-btn-2").on("click", function () {
            var uname = $(".input-2").val();
            if (uname != "") {
                $.ajax({
                    cache: true,
                    type: "POST",
                    url: _this.add_API,
                    data: {uname: uname, rid: _this.roomID},
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        if (data.status == '1') {
                            lsuser.outTimeGo(data.url, _this.OUT_TIME, data.msg);
                        } else {
                            $.xbox.tips(data.msg);
                            return false;
                        }
                    }
                });
            } else {
                $.xbox.tips("请输入用户昵称");
                return false;
            }
        });
    },
    _remove: function () {
        var lsuser = new LSUser();
        $(document).on("click", ".delManager", function () {
            var remove_img_id = $(this).next("img").data("uid");
            $.ajax({
                cache: true,
                type: "POST",
                url: _this.del_API,
                data: {id: remove_img_id, rid: _this.roomID},
                async: false,
                dataType: "json",
                success: function (data) {
                    if (data.status == '1') {
                        lsuser.outTimeGo(data.url, _this.OUT_TIME, data.msg);
                    } else {
                        $.xbox.tips(data.msg);
                        return false;
                    }
                }
            });

        });
    },
    op: function () {
        var _this = this;
        //添加管理
        $('#frmAddmanger_submit').on('click', function () {
            var btnThis = $(this);
            if (!btnThis.hasClass('disabled')) {
                var manageName = $.trim($('#manageName').val());
                if (manageName == "") {
                    $.xbox.tips('请输入要添加为房间管理员的用户名');
                    return false;
                } else {
                    var manageConfirm = confirm('确定将 ' + manageName + ' 添加为房间管理员？');
                    if (manageConfirm == true) {
                        btnThis.addClass('disabled');
                        _this.add();
                        btnThis.removeClass('disabled');
                    }
                }
            }
        });
        //删除管理
        $(document).on("click", ".delManager", function () {
            var thisManage = $.trim($(this).data('mgname'));
            var mid = $.trim($(this).data('mid'));
            var cancelConfirm = confirm('确定取消' + thisManage + '的管理员身份吗?');
            if (cancelConfirm == true && thisManage != '') {
                _this.del(mid);
            }
        });
    },
    getList: function (API_URL) {
        var _this = this;
        var dataLists = '';
        $.ajax({
            type: "GET",
            url: API_URL,
            data: {roomID: _this.roomID},
            dataType: "json",
            success: function (data) {
                if (data.managerList.length) {
                    for (ii = 0; ii < data.managerList.length; ii++) {
                        dataLists = dataLists + '<dd><span class="manage-p01">' + data.managerList[ii].nickname + '</span><span class="manage-p02">' + data.managerList[ii].createTime + '</span><span class="manage-p02">' + data.managerList[ii].timeLength + '</span><span class="manage-p02">' + data.managerList[ii].bi + '个鲨鱼币</span><span class="manage-p02"><i class="btn cancel-manage delManager" data-mgname="' + data.managerList[ii].nickname + '" data-mid="' + data.managerList[ii].id + '">下管</i></span></dd>';
                    }
                    $('.manage-dd').html(dataLists);
                } else {
                    $(".manage-dd").html('<dd><span>暂无管理员</span></dd>');
                }
            }
        });
    },
    add: function () {
        var _this = this;
        var API_URL = $.trim($('#frmAddmanger').attr('action'));
        if (API_URL == '') {
            $.xbox.tips('apiurl配置错误！');
            return false;
        }
        //添加管理员
        $.ajax({
            type: "POST",
            url: API_URL,
            data: $("#frmAddmanger").serialize(),
            dataType: "json",
            success: function (data) {
                if (data.status == 1) {
                    $.xbox.tips('添加管理成功！');
                    _this.getList(_this.GetListAPI);
                } else {
                    $.xbox.tips(data.failed);
                }
            }
        });
    },
    del: function (mid) {
        var _this = this;
        $.ajax({
            type: "POST",
            url: _this.DelAPIURL,
            data: {id: mid},
            dataType: "json",
            success: function (data) {
                if (data.status == 1) {
                    $.xbox.tips('下管成功！');
                    _this.getList(_this.GetListAPI);
                } else {
                    $.xbox.tips(data.failed);
                }
            }
        });
    }
}
LSUser.bindMP = function () {
    this.sendTime = 60;
    this.OUT_TIME = 1;
};
LSUser.bindMP.prototype = {
    init: function () {
        this.op();
    },
    op: function () {
        var _this = this;
        var lsuser = new LSUser;
        $('.user-int01').focus(function () {
                $(this).addClass('on');
            })
            .blur(function () {
                $(this).removeClass('on');
            });
        //发送验证码
        $("#SendMPCode").on("click", function () {
            lsuser.sendSMSRcode('#SendMPCode', '#mphome', null, _this.sendTime);
        });
        $('#frmBDMP_submit').on('click', function () {
            var btnThis = $(this);
            var API_URL = $('#frmbindMP').attr('action');
            if (API_URL == '' || API_URL == undefined) {
                $.xbox.tips('API_URL配置错误！');
                return false;
            }
            if (btnThis.hasClass('disabled')) {
                return false;
            }
            if (_this.check()) {
                btnThis.addClass('disabled');
                /** 验证通过 */
                $.ajax({
                    type: "POST",
                    url: API_URL,
                    data: $('#frmbindMP').serialize(),
                    dataType: "json",
                    success: function (data) {
                        if (data.status == '1') {
                            if (data.url != '') {
                                lsuser.outTimeGo(data.url, _this.OUT_TIME, '恭喜您，手机认证成功！');
                            }
                            ;
                            btnThis.removeClass('disabled');
                        } else {
                            $.xbox.tips(data.failed);
                            btnThis.removeClass('disabled');
                            return false;
                        }
                    }
                });
            } else {
                btnThis.removeClass('disabled');
                return false;
            }
        });
    },
    check: function () {
        var isPass = true;
        var lsuser = new LSUser;
        var realmphone = $.trim($('#realmphone').val());
        var rcode = $.trim($('#rcode').val());
        if (realmphone.length < 1 || realmphone.length > 30) {
            $.xbox.tips('请输入正确的手机号！');
            isPass = false;
        } else if (!lsuser.isMPhone(realmphone)) {
            $.xbox.tips('请输入正确的手机号！');
            isPass = false;
        } else if (rcode.length > 6 || rcode.length < 1) {
            $.xbox.tips('请输入正确的验证码！');
            isPass = false;
        }
        return isPass;
    }
}
//房间管理

LSUser.rank = function () {
};
LSUser.rank.prototype = {
    init: function () {
        this.op();
    },
    op: function () {
        $('#mySelect').change(function () {
            var url = window.location.href;
            if (url.indexOf("?") != -1) {
                url = url.split("?")[0];
            }
            var value = $(this).children('option:selected').val();  //这就是selected的值
            url = url + "?time=" + value;
            window.location.href = url; //页面跳转并传参
        })

    }
}

LSUser.RoomAdmin = function (manage_add_api, manage_del_api) {
    this.manage_del_api = manage_del_api;
    this.manage_add_api = manage_add_api;
};
LSUser.RoomAdmin.prototype = {
    init: function () {
        var _this = this;
        $('.input-2').on('keydown', function (event) {
            if (event.keyCode == 13) {
                $('.add-btn-2').click();
                return false;
            }
        });
        $('.delManager').on('click', function () {
            var uid = $(this).data('uid');
            var uname = $.trim($(this).parents().children('p').html());
            $.xbox.msgBox("确定要取消用户[ " + uname + " ]的管理员权限？", "设置管理员", null, null, true, function () {
                $.ajax({
                    type: 'POST',
                    url: _this.manage_del_api + '?id=' + uid,
                    dataType: 'json',
                    success: function (data) {
                        $.xbox.closeMsgBox();
                        if (data.status == '1') {
                            $.xbox.tips('删除成功!');
                        } else {
                            $.xbox.tips(data.failed);
                        }
                        setTimeout(function () {
                            window.location.reload(true);
                        }, 1000);
                    }
                });
            });

        });
        $('.add-btn-2').on('click', function () {
            var uname = $.trim($('.input-2').val());
            if (uname == '') {
                $.xbox.tips('请输入要添加为房间管理员的用户名');
                return false;
            }
            $.xbox.msgBox("确定要设置用户[ " + uname + " ]为管理员?", "设置管理员", null, null, true, function () {
                $.ajax({
                    type: 'POST',
                    url: _this.manage_add_api + '?manageName=' + uname,
                    dataType: 'json',
                    success: function (data) {
                        $.xbox.closeMsgBox();
                        if (data.status == '1') {
                            $.xbox.tips('添加成功!');
                        } else {
                            $.xbox.tips(data.failed);
                        }
                        setTimeout(function () {
                            window.location.reload(true);
                        }, 1000);
                    }
                });
            });

        });
    }
};

LSUser.RoomBlack = function (BLACK_SAVE_API, BLACK_DEL_API) {
    this.blackSaveAPI = BLACK_SAVE_API;
    this.blackDelAPI = BLACK_DEL_API;
};
LSUser.RoomBlack.prototype = {
    init: function () {
        var _this = this;
        $('.input-2').on('keydown', function (event) {
            if (event.keyCode == 13) {
                $('.add-btn-2').click();
                return false;
            }
        });
        $('.delManager').on('click', function () {
            var uid = $(this).data('uid');
            var uname = $.trim($(this).parents().children('p').html());
            $.xbox.msgBox("确定要解除用户[ " + uname + " ]的禁言?", "解禁", null, null, true, function () {
                $.ajax({
                    type: 'POST',
                    url: _this.blackDelAPI + '?bid=' + uid,
                    dataType: 'json',
                    success: function (data) {
                        $.xbox.closeMsgBox();
                        if (data.status == '1') {
                            $.xbox.tips('解除禁言成功!');
                        } else {
                            $.xbox.tips(data.failed);
                        }
                        setTimeout(function () {
                            window.location.reload(true);
                        }, 1000);
                    }
                });
            });
        });
        $('.add-btn-2').on('click', function () {
            var uname = $.trim($('.input-2').val());
            if (uname == '') {
                $.xbox.tips('请输入禁言用户的用户昵称');
                return false;
            }
            $.xbox.msgBox("确定要禁言用户[ " + uname + " ]?", "禁言", null, null, true, function () {
                $.ajax({
                    type: 'POST',
                    url: _this.blackSaveAPI + '?blackName=' + uname,
                    dataType: 'json',
                    success: function (data) {
                        $.xbox.closeMsgBox();
                        if (data.status == '1') {
                            $.xbox.tips('禁言成功!');
                        } else {
                            $.xbox.tips(data.failed);
                        }
                        setTimeout(function () {
                            window.location.reload(true);
                        }, 1000);
                    }
                });
            });

        });
    }
};