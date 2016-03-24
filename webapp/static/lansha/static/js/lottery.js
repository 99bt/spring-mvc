/**
 * 通用js lib
 * v1.0
 */
$(function () {
    wzry = function () {
        //this.download();
        this.award();
    };
    wzry.prototype = {
        //下载
        download: function () {
            $(window).scroll(function () {
                var sH = $(window).scrollTop();
                if (sH > 100) {
                    $(".download").addClass("fixed");
                } else {
                    $(".download").removeClass("fixed");
                }
            })
        },
        //获奖记录
        award: function () {
            $(".lottery-pool span").on("click", function () {
//                if (IS_LOGIN == 1) {
                    $(".awards").toggle();
//                } else {
//                    $(".popup-common,.popup-bg").show();
//                    $(".popup-common .popup-text").html("成功注册登录后才可领取哦");
//
//                    $(".btn-true").show().addClass('fl').html("登录");
//                    $(".btn-false").show().html("注册");
//                    $(".btn-true").on("click", function () {
//                        $(".popup-common,.popup-bg").hide();
//                        //loginShow();
//                        window.location.href = DOWN_URL;
//                    });
//                    $(".btn-false").on("click", function () {
//                        $(".popup-common,.popup-bg").hide();
//                        //regShow();
//                        window.location.href = DOWN_URL;
//                    })
//                }
            });
            //领取奖品
            $(".awards-list a").on("click", function () {
//                if (IS_LOGIN != 1) {
//                    $(".popup-common,.popup-bg").show();
//                    $(".popup-common .popup-text").html("成功注册登录后才可领取哦");
//
//                    $(".btn-true").show().addClass('fl').html("登录");
//                    $(".btn-false").show().html("注册");
//                    $(".btn-true").on("click", function () {
//                        $(".popup-common,.popup-bg").hide();
//                        //loginShow();
//                        window.location.href = DOWN_URL;
//                    });
//                    $(".btn-false").on("click", function () {
//                        $(".popup-common,.popup-bg").hide();
//                        //regShow();
//                        window.location.href = DOWN_URL;
//                    })
//                    return false;
//                }
//                $(".popup-common,.popup-bg").show();
//                $(".popup-common .popup-text").html("奖品已到账，请登录蓝鲨APP到个人中心领取");
//                $(".btn-true").html("未安装请下载");
//                $(".btn-false").html("关闭");
//                $('.btn-true, .btn-false').show();
//                $(".btn-false").on("click", function () {
//                    $(".popup-common,.popup-bg").hide();
//                });
//                $(".btn-true").on("click", function () {
//                    window.location.href = DOWN_URL;
//                });

            	$(".popup-common,.popup-bg").show();
            	$(".popup-common .popup-text").html("奖品已到账，请登录蓝鲨APP到个人中心领取");
                $(".btn-true").html("赶快去下载吧").removeClass('fl').css({'display':'block','margin':'0 auto'});
                $(".btn-false").hide();
                $(".btn-true").on("click", function () {
                    $(".popup-common,.popup-bg").hide();
                    location.href=DOWN_URL;
                });
            });
            //分享
            $("#sharebtn").on("click", function () {
                $(".popup-common,.popup-bg").show();
                $(".popup-common .popup-text").html("好东西就应该分享，点击右上角分享给朋友吧");
                $(".btn-true").html("关闭").removeClass('fl').css({'display':'block','margin':'0 auto'});
                $(".btn-false").hide();
                $(".btn-true").on("click", function () {
                    $(".popup-common,.popup-bg").hide();
                    return false;
                });
            });
            //goLive
            $('.game-one a').on('click',function(){
                var status = $(this).data('status') || null;
                if(status=='nolive'){
                    $(".popup-common,.popup-bg").show();
                    $(".popup-common .popup-text").html("当前房间主播不在线");
                    $(".btn-true").html("关闭").removeClass('fl').css({'display':'block','margin':'0 auto'});
                    $(".btn-false").hide();
                    $(".btn-true").on("click", function () {
                        $(".popup-common,.popup-bg").hide();
                    });
                    return false;
                }else{
                    return true;
                }
            });
            //goLogin
            $('#goLogin').on("click", function () {
                if(IS_LOGIN==1){
                    $(".popup-common,.popup-bg").show();
                    $(".popup-common .popup-text").html("您已经登录");
                    $(".btn-true").html("关闭").removeClass('fl').css({'display':'block','margin':'0 auto'});
                    $(".btn-false").hide();
                    $(".btn-true").on("click", function () {
                        $(".popup-common,.popup-bg").hide();
                    });
                    return false;
                }
            });
        }
    };
});
/**抽奖**/
var lottery={
    index:-1,   //当前转动到哪个位置，起点位置
    count:0,    //奖池奖品位置数
    timer: 'lottery_timer',    //setTimeout的ID，用clearTimeout清除
    speed:150,   //初始转动速度
    times:0,    //转动次数
    cycle:60,   //转动基本次数
    prize:0,    //中奖位置
    init:function(id){
        if ($("#"+id).find(".lottery-unit").length>0) {
            $lottery = $("#"+id);
            $units = $lottery.find(".lottery-unit");
            this.obj = $lottery;
            this.count = $units.length;
            $lottery.find(".lottery-unit-"+this.index).addClass("active");
        }
    },
    roll:function(){
        var index = this.index;
        var count = this.count;
        var lottery = this.obj;
        $(lottery).find(".lottery-unit-"+index).removeClass("active");
        index += 1;
        if (index>count-1) {
            index = 0;
        }
        $(lottery).find(".lottery-unit-"+index).addClass("active");
        this.index=index;
        return false;
    },
    stop:function(index){
        this.prize=index;
        return false;
    }
};

function roll(){
    lottery.times += 1;
    lottery.roll();
    if (lottery.times > lottery.cycle+10 && lottery.prize==lottery.index) {
        clearTimeout(lottery.timer);
        lottery.prize=-1;
        lottery.times=0;
        click=false;
        //效果结束
        setTimeout(function(){
            LotteryEnd(lottery.prize);
        },500);
    }else{
        if (lottery.times<lottery.cycle) {
            lottery.speed -= 10;
        }else{
            if (lottery.times > lottery.cycle+10 && ((lottery.prize==0 && lottery.index==7) || lottery.prize==lottery.index+1)) {
                lottery.speed += 110;
            }else{
                lottery.speed += 20;
            }
        }
        if (lottery.speed<40) {
            lottery.speed=40;
        }
        //console.log('times: '+lottery.times+'^^^^^^'+'speed: '+lottery.speed+'^^^^^^^'+'prize: '+lottery.prize);
        lottery.timer = setTimeout(roll,lottery.speed);
    }
    return false;
}
var click=false;
window.onload=function(){
    lottery.init('lottery');
    window.prize = -1;
    //resize
    var lw = $('.lottery').width();
    var zoomsize = lw/284;  //283*193
    $('.lottery').css({'height':zoomsize*198});
    $('.lottery-unit, .lottery_btn').css({'height':zoomsize*65});
    $('.lottery-unit ').css({'line-height':zoomsize*65+'px'});
    $('.lottery-unit i').css({'height':zoomsize*65-4,'width':$('.lottery-unit ').width()-4});
    $('.videoBlock').css({'height':$('.videoBlock').width()/16*9});

    $("#lottery_btn").click(function(){
        if (click) {
            return false;
        }else{
            var lottery_num = $.trim($('#lottery_num').text()) ||0;
            if(lottery_num>0){
                lottery_num = (lottery_num-1>0) ? lottery_num-1 : 0;
                $("#lottery_num").html(lottery_num);
            }else{
                //alert('你没有抽奖机会了');
                $(".popup-common,.popup-bg").show();
                if(IS_LOGIN){
                    $(".popup-common .popup-text").html("抽奖机会已用完，成功邀请好友注册可获得更多抽奖机会，详情查看活动规则");
                    $(".btn-true").html("关闭").removeClass('fl').css({'display':'block','margin':'0 auto'});
                    $(".btn-false").hide();
                    $(".btn-true").unbind('click');
                    $(".btn-true").on("click", function () {
                        $(".popup-common,.popup-bg").hide();
                        return false;
                    });
                }else{
                    $(".popup-common .popup-text").html("你已获得 <span class='colred'>1个Q币</span>，请下载APP后领取");
                    $("#regMark").val(1);
                    $("#loginMark").val(1);
                    $(".btn-true").html("赶快去下载吧").removeClass('fl').css({'display':'block','margin':'0 auto'});
                    $(".btn-false").hide();
                    $(".btn-true").on("click", function () {
                        $(".popup-common,.popup-bg").hide();
                        //regShow();
                        location.href=DOWN_URL;
                    });
                }
                return false;
            }

            var API_URL = $(this).data('apiurl');
            if(IS_LOGIN==1){
                //GetGift
                $.ajax({
                    type: "POST",
                    url: API_URL,
                    data: {},
                    dataType: "json",
                    success: function (data) {
                        if (data.status == '1') {
                            window.prize = data.data.states-1;
                            window.giftName = data.data.name;
                            lottery.prize = window.prize;
                            roll();
                            click=true;
                        } else {
                            window.prize = -1;
                            $(".popup-common,.popup-bg").show();
                            $(".popup-common .popup-text").html("网络错误,请刷新后重试");
                            $(".btn-true").html("关闭").removeClass('fl').css({'display':'block','margin':'0 auto'});
                            $(".btn-false").hide();
                            $(".btn-true").on("click", function () {
                                $(".popup-common,.popup-bg").hide();
                                window.location.href = window.location.href;
                            });
                        }
                    }
                });
            }else{
                lottery.prize = 3;
                roll();
                click=true;
            }
            return false;
        }
    });
};
function LotteryEnd(){
    if(IS_LOGIN=='1'){
        $(".popup-common,.popup-bg").show();
        $(".popup-common .popup-text").html("恭喜你，获得 <span class='colred'>"+window.giftName+"</span>，请登录蓝鲨APP领取");
        $(".btn-true").html("未安装请下载").addClass('fl');
        $(".btn-false").html("关闭");
        $('.btn-true, .btn-false').show();
        $(".btn-false, .popup-close").on("click", function () {
            window.location.href = window.location.href;
        });
        $(".btn-true").on("click", function () {
            window.location.href = DOWN_URL;
        });
        return false;
    }else{
        $(".popup-common,.popup-bg").show();
        $(".popup-common .popup-text").html("恭喜你，获得<span class='colred'>1个Q币</span>，请下载蓝鲨APP后领取");
        $("#regMark").val(1);
        $("#loginMark").val(1);
        $(".btn-true").html("赶快去下载吧").removeClass('fl').css({'display':'block','margin':'0 auto'});
        $(".btn-false").hide();
        $(".btn-true").on("click", function () {
            $(".popup-common,.popup-bg").hide();
            //regShow();
            location.href=DOWN_URL;
        });
        $(".btn-false").on("click", function () {
            $(".popup-common,.popup-bg").hide();
        });
        $("#lottery_num").html(0);
        $('#lpID').html(1).removeClass('hide');
        //$('#awards-list').html('<li class="clearfix"><span class="fl">2016-01-20 13:26 获得1个Q币</span><a href="javascript:;" class="get fr">领取</a></li>');
        $('#awards-list #awards_n1').show();
    }
}