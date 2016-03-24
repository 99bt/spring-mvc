/**
 * Created by xhxu on 16/3/21.
 */

var lottimer = 0;
var lottery = {
    index: -1,   //当前转动到哪个位置，起点位置
    count: 0,    //奖池奖品位置数
    timer: 'lottery_timer',    //setTimeout的ID，用clearTimeout清除
    speed: 20,   //初始转动速度
    times: 0,    //转动次数
    cycle: 60,   //转动基本次数
    prize: 0,    //中奖位置
    init: function (id) {
        if ($("#" + id).find(".lottery-unit").length > 0) {
            $lottery = $("#" + id);
            $units = $lottery.find(".lottery-unit");
            this.obj = $lottery;
            this.count = $units.length;
            $lottery.find(".lottery-unit-" + this.index).addClass("active");
        }
    },
    roll: function () {
        var index = this.index;
        var count = this.count;
        var lottery = this.obj;
        $(lottery).find(".lottery-unit-" + index).removeClass("active");
        index += 1;
        if (index > count - 1) {
            index = 0;
        }
        $(lottery).find(".lottery-unit-" + index).addClass("active");
        this.index = index;
        return false;
    },
    stop: function (index) {
        this.prize = index;
        return false;
    }
};

function roll() {
    lottery.times += 1;
    lottery.roll();
    if (lottery.times > lottery.cycle + 10 && lottery.prize == lottery.index) {
        clearTimeout(lottery.timer);
        lottery.prize = -1;
        lottery.times = 0;
        click = false;
        //效果结束
        setTimeout(function () {
            LotteryEnd(lottery.prize);
        }, 500);
    } else {
        if (lottery.times < lottery.cycle) {
            lottery.speed -= 10;
        } else {
            if (lottery.times > lottery.cycle + 10 && ((lottery.prize == 0 && lottery.index == 7) || lottery.prize == lottery.index + 1)) {
                lottery.speed += 110;
            } else {
                lottery.speed += 20;
            }
        }
        if (lottery.speed < 40) {
            lottery.speed = 40;
        }
        ;
        lottery.timer = setTimeout(roll, lottery.speed);
    }
    return false;
}
var click = false;
window.onload = function () {
    //自动隐藏抽奖
    lottimer = setTimeout(function () {
        $('.lottery-select').click();
    }, 3000);
    //抽奖
    lottery.init('lottery');
    $(".lottery_btn").click(function () {
        //停止自动关闭
        clearTimeout(lottimer);
        if (click) {
            return false;
        } else {
            var lottery_num = $.trim($('#lottery_num').text()) || 0;
            if (lottery_num>0) {
                lottery_num = (lottery_num - 1 > 0) ? lottery_num - 1 : 0;
                $("#lottery_num").html(lottery_num);
                click = true;
                lottery.speed = 150;
                lottery.prize = 3;
                roll();
                return false;
            } else {
                alert("次数不够了");
            }
        }
    });
};
function LotteryEnd() {
    $("#lottery_num").html(0);
    $(".tips-box").show();
}