/**
 * LSLIVE index.js
 * YOWANT FE Team
 * v1.0
 */
//首页
var index_rooms_Li = 0;
LS_index = function () {
};
LS_index.prototype = {
    init: function () {
        $(".video-list").eq(0).addClass('video-list-selected');
        var mySwiper = new Swiper('.swiper-container', {
            direction: 'vertical',
            slidesPerView: 5,
            loop: false,
            speed: 500
        });
        $('.arrow-left').on('click', function (e) {
            e.preventDefault()
            mySwiper.slidePrev();
        })
        $('.arrow-right').on('click', function (e) {
            e.preventDefault()
            mySwiper.slideNext();
        });
        this.op();
    },
    op: function () {
        var _this = this;
        $(".video-list").click(function (event) {
            var num = $(this).index(".video-list");
            $(".video-list").removeClass('video-list-selected');
            $(this).addClass('video-list-selected');
            index_rooms_Li = $(this).index(".video-list");
            _this.changeBanner(index_rooms[num]);
        });
        $(".video-list").hover(function () {
            $(this).removeClass('video-list-selected');
            $(this).addClass('video-list-selected');
            $(".video-list").eq(index_rooms_Li).addClass('video-list-selected');
        }, function () {
            $(this).removeClass('video-list-selected');
            $(".video-list").eq(index_rooms_Li).addClass('video-list-selected');
        });
        //切换列表类型
        $('.game-box .game-name a').on('click', function () {
            _this = $(this);
            _this.parents().children('.r-span').removeClass('on');
            _this.addClass('on');
            //getData
            var api_url = $.trim(_this.data('api'));
            $.ajax({
                url: api_url,
                type: 'GET',
                async: false,
                dataType: 'json',
                data: {},
                success: (function (data) {
                    if (data.status == 1) {
                        roomList = data.data;
                        if (roomList != null) {
                            var newDatas = '';
                            $.each(roomList, function (i) {
                                var strLast = '';
                                if ((i + 1) % 4 == 0 && i != 0) {
                                    strLast = 'last';
                                }
                                newDatas = newDatas + '<li class="game-one fl ' + strLast + ' "><a href="' + roomList[i].roomURL + '" class="game-pic"><img src="' + roomList[i].roomImg + '" onerror="javascript:this.src=\'' + BASE_PATH + 'static/images/nopic.png\';" alt="" width="280" height="156" /><div class="live-icon"><img src="' + roomList[i].avatar + '" onerror="javascript:this.src=\'' + BASE_PATH + 'static/images/live-icon.png\';" alt="" /></div><div class="live-bg"></div><div class="live-begin"></div></a><h3 class="room-name"><a href="' + roomList[i].roomURL + '" class="room-name-title">' + roomList[i].roomName + '</a><a href="' + roomList[i].gameURL + '" class="fr room-game-name">' + roomList[i].gameName + '</a></h3><div class="room-topic clearfix"><div class="fl">' + roomList[i].bogerName + '</div><div class="fr"><i class="icon-sprite icon-people fl"></i>' + roomList[i].viewNum + '</div></div><i class="show-line-b"></i></li>';
                            });
                            _this.parents().children('.index-game-index').html(newDatas);
                        } else {
                            $.xbox.tips('暂无数据');
                        }
                    } else {
                        $.xbox.tips(data.msg);
                    }
                })
            });
        });
    },
    //切换直播
    changeBanner: function (value) {
        onChangeBanner(value);
    }
};
$(function () {
    //首页
    new LS_index().init();
});