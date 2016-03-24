/**
 * LSLIVE room.js
 * YOWANT FE Team
 * v0.1
 */
var LSLiveRoom = function (getOnlineNumTime, getOnlineAPIURL, fav_APIURL) {
    _this = this;
    _this.getOnlineNumTime = getOnlineNumTime;
    _this.getOnline_API = getOnlineAPIURL;
    _this.fav_APIURL = fav_APIURL
};
LSLiveRoom.prototype = {
    init: function () {
        this.fixHeight();
        this.op();
        this.clipboard();
        this.calcAuto();
        this.gameBoxFix();
        this.fixPlayerSize();
    },
    op: function () {
        var _this = this;
        var mTimeout = "";
        var mphoneTimeout = "";
        //判断在线状态
        if (isLogin) {
            $('#userArea_nologin').addClass('hide');
            $('#userArea').removeClass('hide');
        } else {
            $('#userArea_nologin').removeClass('hide');
            $('#userArea').addClass('hide');
        }
        //用户登录后启动在线计时
        if (isLogin && getGiftCount != 0) {
            $('.chest').removeClass('chest-active');
            $('.chest').addClass('chest-countdown');
            settime();
        }
        /*直播公告切换*/
        $(".msg-box-sel").on("click", function () {
            $(this).addClass("on").siblings().removeClass("on");
            if ($(this).data("sel") == 1) {
                $(".slide-notice").hide();
                $(".slide-notice2").show();
            } else {
                $(".slide-notice").show();
                $(".slide-notice2").hide();
            }
        });

        //广告关闭
        $(".r-close").on("click", function () {
            var r = $(".recommend-code");
            r.remove();
            _this.fixHeight();
            _this.fixPlayerSize();
            //_this.calcAuto();
            _this.gameBoxFix();
        });
        //$(".msg-box-text a:nth-child(2)").on("click", function () {
        //    $(".slide-notice").show();
        //});
        //滚屏清屏按钮显示
        $(".talk-box").hover(function () {
                if (!$(this).hasClass('on')) {
                    $(this).addClass('on');
                    $("#chat_option").removeClass('hide');
                } else {
                    $(this).removeClass('on');
                    $("#chat_option").addClass('hide');
                }
            })
            .blur(function () {
                $(this).removeClass('on');
            });
        //清屏
        $(".chat-clear").on("click", function () {
            $("#chat_list").html("");
        })
        //是否滚屏 open.开启滚屏 close.禁止滚屏
        $(".chat-scroll").on("click", function () {
            var chatScroll = $(".chat-scroll");
            var isScroll = $(".chat-scroll").attr("data-chatscroll");
            if (isScroll == "open") {
                chatScroll.attr("data-chatscroll", "close");
                chatScroll.addClass("chat-noscroll").find("span").html("禁止滚屏");
            } else {
                chatScroll.attr("data-chatscroll", "open");
                chatScroll.removeClass("chat-noscroll").find("span").html("开启滚屏");
            }
        });
        //主播去开播
        $('#goplay').on('click', function () {
            $('.room-popup').show();
        });
        $(".change-close").on('click', function () {
            $('.room-popup').hide();
        });
        //聊天输入
        $('#talk_input').keydown(function (event) {
            if (event.keyCode == 13) {
                if ($('#talk_input').val() != '') {
                    sendMsg();
                    talk_CD();
                } else {
                    $.xbox.tips('请输入聊天内容');
                }
                ;
                return false;
            }
            ;
        });
        //滚动固定头部
        $(window).scroll(function () {
            var sTop = $(window).scrollTop();
            if (sTop > 0) {
                $('.header').css({position: 'fixed'});
                $(".container").css("marginTop", "71px")
            } else {
                $('.header').css('position', '');
                $(".container").css("marginTop", 0)
            }
        });
        //右侧订阅
        $('#follow').on('click', function () {
            if (_this.checkLogin()) {
                if (!$(this).hasClass('on')) {
                    //订阅
                    _this.follow($(this), roomID, 1);
                } else {
                    //取消
                    _this.follow($(this), roomID, 2);
                }
            } else {
                loginShow();
            }
        });
        //滚动条
        $(".live-main").niceScroll({
            touchbehavior: false,
            cursorcolor: "#999999",
            cursoropacitymin: 0.3,
            cursoropacitymax: 0.8,
            cursorwidth: 3,
            horizrailenabled: false,
            cursorborderradius: 5,
            autohidemode: 'cursor',
            cursorborder: 'solid 1px #999',
            railpadding: {
                top: 0,
                right: 1,
                left: 1,
                bottom: 0
            },
            railoffset: {
                top: 0,
                left: -2
            }
        });
        //定位播放器位置
        var WH = $(window).height();
        var room_intro = parseInt($(".room-intro").css("height"));
        var live_main_recruit = parseInt($(".live-main-recruit").css("height")) - 6;

        $(".live-main").getNiceScroll(0).doScrollTop(live_main_recruit);
        //窗口重设大小
        $(window).resize(function () {
            _this.fixHeight();
            _this.fixPlayerSize();
            //_this.calcAuto();
            _this.gameBoxFix();
        })
    },
    checkLogin: function () {
        if (isLogin) {
            return true;
        } else {
            return false;
        }
    },
    //复制分享地址
    clipboard: function () {
        if (window.clipboardData) {
            //分享复制
            $("#share-address-btn").click(function () {
                window.clipboardData.setData("Text", $("#share-address").val());
                $.xbox.tips("复制成功！");
                //$(".share-box").hide();
            });
            //推流
            $("#copy_rtmp_address").click(function () {
                window.clipboardData.setData("Text", $("#rtmp_address").val());
                $.xbox.tips("复制成功！");
            });
            $("#copy_rtmp_code").click(function () {
                window.clipboardData.setData("Text", $("#rtmp_code").val());
                $.xbox.tips("复制成功！");
            });
        } else {
            //分享复制
            ZeroClipboard.config({
                swfPath: BASE_PATH + "static/flash/ZeroClipboard.swf"
            });
            var client = new ZeroClipboard($("#share-address-btn"));
            client.on("copy", function (event) {
                var clipboard = event.clipboardData;
                clipboard.setData("text/plain", $("#share-address").val());
                $.xbox.tips("复制成功！");
                //$(".share-box").hide();
            });
            //推流
            var client_address = new ZeroClipboard($("#copy_rtmp_address"));
            client_address.on("copy", function (event) {
                var clipboard = event.clipboardData;
                clipboard.setData("text/plain", $("#rtmp_address").val());
                $.xbox.tips("复制成功！");
            });
            var client_roomcode = new ZeroClipboard($("#copy_rtmp_code"));
            client_roomcode.on("copy", function (event) {
                var clipboard = event.clipboardData;
                clipboard.setData("text/plain", $("#rtmp_code").val());
                $.xbox.tips("复制成功！");
            });
        }
        ;
    },
    //直播间左右高度
    fixHeight: function () {
        var fixH = $(window).height() - $(".header").height(), sendBox = $(".talk-send").outerHeight(), slideBox = $(".slide-box").height();
        $(".leftbar,.rightbox").height(fixH);
        $(".live-main").height(fixH);
        $(".talk-list").height(fixH - $('.rightbox .recommend-code').height() - sendBox - slideBox - 24);
    },
    fixPlayerSize: function () {
        var strWidth = $('.live-main').width();
        var strHeight = strWidth / 16 * 9;
        $("#playerArea").css('height', strHeight + 40);
    },
    getOnlineNum: function (RoomID) {
        var _this = this;
        setTimeout(function () {
            $.ajax({
                type: "GET",
                url: _this.getOnline_API,
                data: {},
                dataType: "json",
                success: function (data) {
                    if (data.status == 1) {
                        $('#onlineNum em').text(data.num);
                    }
                    _this.getOnlineNum(RoomID);
                },
                error: function () {
                    _this.getOnlineNum(RoomID);
                }
            });
        }, this.getOnlineNumTime);
    },
    follow: function (obj, roomid, oType) {
        var _this = this;
        if (this.checkLogin) {
            $.ajax({
                type: "POST",
                url: _this.fav_APIURL,
                data: {roomid: roomID, isfollow: oType},
                dataType: "json",
                success: function (data) {
                    if (data.status == 1) {
                        var fNum = parseInt($('#favNum').text()) || 0;
                        if (oType == 1) {
                            $.xbox.tips('订阅成功!');
                            $('#follow').addClass('on');
                            $('#follow').text('已订阅');
                            $('#favNum').text(fNum + 1);
                            if (typeof follow == "function") {//行为分析代码
                                eval("follow()");
                            }
                        } else if (oType == 2) {
                            $.xbox.tips('成功取消订阅！');
                            $('#follow').removeClass('on');
                            $('#follow').text('订阅');
                            $('#favNum').text(fNum - 1);
                            if (typeof cancelFollow == "function") {//行为分析代码
                                eval("cancelFollow()");
                            }
                        }
                    } else {
                        $('.iMPTEL').text(data.failed).addClass('error').show();
                        return false;
                    }
                }
            });
        } else {
            //$.xbox.tips('您还未登录！',null,null,null,true);
            loginShow();
        }
    },
    //calcAuto
    calcAuto: function () {
        var rWidth = parseInt($(".leftbar").css("width"));
        //宽度计算
        $(".right-close").on("click", function (event) {
            var calcCenter = function () {
                var divW = $(".live-auto").width() + 20;
                var listW = $(".live-auto .game-one").width() + parseInt($(".live-auto .game-one").css('marginLeft')) + parseInt($(".live-auto .game-one").css('marginRight'));
                var num = parseInt(divW / listW);
                var autoW = divW - listW * num;
                $(".live-auto").find(".live-list").css("marginLeft", autoW / 2 + "px");
                $(".live-title-box").css({
                    "paddingLeft": "0" + ((autoW / 2) ) + "px",
                    "paddingRight": "0" + ((autoW / 2)) + "px"
                });
                var lineW = $(".live-title").width() - 115;
                $(".titleDiv").find("hr").css("width", lineW / 2 + "px");
                var strWidth = $('.live-main').width();
                var strHeight = strWidth / 16 * 9;
                $("#playerArea").css('height', strHeight);
            }
            if ($(".rightBlock").is(":visible")) {
                var addWidth = $(".rightbox").width();
                var newWidth = $(".live-main").width() + addWidth;
                var scrollLeft = parseInt($("#ascrail2000").css("left"));
                $(".rightbox").animate({width: '0px'}, function () {
                        $(".rightBlock").hide();
                        calcCenter();
                    }
                );
                $(".live-main").animate({marginRight: '0px'});
                $(".right-close").addClass("open").animate({right: "8.5px"});
                $("#ascrail2000").animate({
                    left: scrollLeft + addWidth + "px"
                });
            } else {
                var newWidth = $(".live-main").width() - rWidth;
                var scrollLeft = parseInt($("#ascrail2000").css("left"));
                $("#ascrail2000").animate({
                    left: scrollLeft - rWidth + "px"
                });
                $(".live-main").animate({
                        marginRight: rWidth + "px"
                    },
                    function () {
                        calcCenter();
                    }
                );
                $(".rightbox").animate({marginRight: '0px', width: rWidth + "px"});
                $(".right-close").animate({right: "12px"});
                $(".rightBlock").show();
                $(".right-close").removeClass("open").animate({right: rWidth + 10 + "px"});
                var strWidth = $('.live-main').width();
                var strHeight = strWidth / 16 * 9;
                $("#playerArea").css('height', strHeight);
            }
        });
    },
    /** gameBox尺寸修改**/
    gameBoxFix: function () {
        //热门推荐
        var divW = $(".live-auto").width() + 20;
        var listW = $(".live-auto .game-one").width() + parseInt($(".live-auto .game-one").css('marginLeft')) + parseInt($(".live-auto .game-one").css('marginRight'));
        var num = parseInt(divW / listW);
        var autoW = divW - listW * num;
        $(".live-auto").find(".live-list").css("marginLeft", autoW / 2 + "px");
        $(".live-title-box").css({
            "paddingLeft": "0" + ((autoW / 2) ) + "px",
            "paddingRight": "0" + ((autoW / 2)) + "px"
        });
        var lineW = $(".live-title").width() - 115;
        $(".titleDiv").find("hr").css("width", lineW / 2 + "px");
    }
};
/** common function **/
/**滚动/禁止聊天记录 **/
function ScrollChatList() {
    var chatScroll = $('.chat-scroll');
    var isScroll = chatScroll.attr("data-chatscroll");
    if (isScroll != 'open') {
        $('#chat_list').scrollTop($('#chat_list')[0].scrollHeight);
    }
}
/** 在线倒计时 **/
function settime() {
    setTimeout(function () {
        time--;
        if (time == 0) {
            $('.chest').removeClass('chest-countdown');
            $('.chest').addClass('chest-active');
            $('.chest').removeClass('gray');
        } else if (time > 0) {
            var h = parseInt(time / 60);
            var s = time % 60;
            h = h < 10 ? "0" + h : h;
            s = s < 10 ? "0" + s : s;
            $(".chest #onlineTime").html(h + ":" + s);
            $('.chest').addClass('gray');
            settime();
        }
    }, 1 * 1000);
}
//判断登录状态 1.已登录连接中 0.跳转登录
$("#talk_input").on("click", function () {
    var ifLogin = parseInt($("#talk_input").attr('data-login'));
    if (ifLogin == 0) {
        loginShow();
    }
})
//获取免费鲜花礼物
function getFreeFlowerA() {
    FF_Timer = setInterval(function () {
        var num = parseInt($('#gift_flower_num').data('num'));
        if (num < MaxFlowerNum) {
            num++;
            $('#gift_flower_num').html(num);
            $('#gift_flower_num').data('num', num);
            //增加效果
            $(".f_plus-minus i").html("1");
            $(".f_plus-minus b").html("+");
            $(".f_plus-minus").css({
                "opacity": "1",
                "display": "block"
            }).animate({"top": "-35px"}, 200).animate({"opacity": "0"}, 100).animate({"top": "-8px"}, 1);
            if (num == 10) {
                clearInterval(FF_Timer);
                FF_Timer = null;
            }
        }
    }, getFlowerTime * 1000);
}