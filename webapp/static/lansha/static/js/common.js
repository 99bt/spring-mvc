/**
 * LSLIVE common.js
 * YOWANT FE Team
 * v20160321
 */
if(BASE_PATH==undefined){
    var BASE_PATH = './';
}
var LSLive = function () {
};
LSLive.prototype = {
    init: function () {
        this.search();
        this.userMenu();
        this.showRCCode();
        this.goTop();
        this.Collection();
        //this.headerFixed();
    },
    substr: function (value, num) {
        var textNum = $.trim(value.length);
        if (textNum > num) {
            return value.substr(0, num) + "...";
        } else {
            return value;
        }
    },
    removeHTMLTag: function (str) {
        str = str.replace(/<[^>]+>/g, '');
        str = str.replace(/&nbsp;/g, '');
        str = str.replace(/[\r\n]/g, ' ')
        return str;
    },
    search: function () {
        $('#search_keyword').on('keydown', function (event) {
            var _kwd = $('#search_keyword').val();
            if (event.keyCode == 13) {
                $('#search_go').click();
                return false;
            }
            ;
        });
        $('#search_go').on('click', function () {
            var _kwd = $('#search_keyword').val();
            if (_kwd == '') {
                $.xbox.tips('请输入搜索的关键字！');
                return false;
            } else {
                var url = $('.header-search').data('gourl');
                window.location.href = url + "?name=" + _kwd;
            }
        });
    },
    loadJS: function (jsFile) {
        $.getScript(jsFile);
    },
    userMenu: function () {
        //用户登录以后的下拉菜单
        var userMenuTimeout = null;
        $("#logined").hover(function () {
            clearTimeout(userMenuTimeout);
            $(".user-lest").show();
        }, function () {
            userMenuTimeout = setTimeout(function () {
                $(".user-lest").hide();
            }, 200);
        });
    },
    //下载框的显示和隐藏
    showRCCode: function () {
        $(document).on('mouseenter', '.downloadbtn', function () {
            $(this).next($(".download-box")).toggle();
        });
        $(document).on('mouseenter', '.download-box', function () {
            $(this).toggle();
        });
        $(document).on('mouseenter', '.recommend-download', function () {
            $(this).next().toggle();
        });
        $(document).on('mouseleave', '.downloadbtn', function () {
            $(this).next($(".download-box")).toggle();
        });
        $(document).on('mouseleave', '.download-box', function () {
            $(this).toggle();
        });
        $(document).on('mouseleave', '.recommend-download', function () {
            $(this).next().toggle();
        });

        $(".recommend-one:last").css("margin-right", "0px");
    },
    goTop: function () {
        var sTop;
        $(window).scroll(function () {
            gotopshow();
        });
        function gotopshow() {
            sTop = $(window).scrollTop();
            if (sTop > 680) {
                $(".go-Top").show();
            } else if (sTop < 680) {
                $(".go-Top").hide();
            }
        }

        $(".go-Top").click(function () {
            $('html,body').animate({scrollTop: 0}, 300);
        });
        $(window).resize(function () {
            //gotopLeft();
        });
        function gotopLeft() {
            var layout = $(".layout").offset().left;
            var winW = $(window).width();
            var t = winW - layout + 20;
            $(".gotop").css({left: t});
        }
    },
    Collection: function () {
        $("#collection").click(function (event) {
            var ctrl = (navigator.userAgent.toLowerCase()).indexOf('mac') != -1 ? 'Command/Cmd' : 'CTRL';
            try {
                if (document.all) { //IE类浏览器
                    try {
                        window.external.toString(); //360浏览器不支持window.external，无法收藏
                        window.alert("360浏览器等不支持主动加入收藏。\n您可以尝试通过浏览器菜单栏 或快捷键 ctrl+D 试试。");
                    } catch (e) {
                        try {
                            window.external.addFavorite(window.location, document.title);
                        } catch (e) {
                            window.external.addToFavoritesBar(window.location, document.title); //IE8
                        }
                    }
                } else if (window.sidebar) { //firfox等浏览器
                    window.sidebar.addPanel(document.title, window.location, "");
                } else {
                    alert('您可以尝试通过快捷键' + ctrl + ' + D 加入到收藏夹~');
                }
            } catch (e) {
                alert('您可以尝试通过快捷键' + ctrl + ' + D 加入到收藏夹~');
            }
        });
        $(".file-img").mouseenter(
            function () {
                var calssName = "." + $(this).data("tips");
                console.log(calssName);
                $(calssName).show();
            }
        );
        $(".IDcard").mouseleave(function () {
            $(this).hide();
        });
        $(".IDcard-example").hover(function () {
            $(this).children(".IDcard").show();
        }, function () {
            $(this).children(".IDcard").hide();
        })
    },
    lazyload: function (obj) {
        $(obj).lazyload({
            effect: "fadeIn"
        });
    },
    LiveGetMore: function (LiveGetMore_APIURL) {
        var roomList = null;
        var page = 2;
        $(".more").on("click", function () {
            var pagesize = $(this).data('pagesize') ||0;
            $.ajax({
                url: LiveGetMore_APIURL,
                type: 'GET',
                dataType: 'json',
                data: {pageIndex: page},
                success: (function (data) {
                    roomList = data.data;
                    if (roomList != null) {
                        var newDatas = '';
                        $.each(roomList, function (i) {
                            var strLast = '';
                            if ((i + 1) % 4 == 0 && i != 0) {
                                strLast = 'last';
                            }
                            newDatas = newDatas + '<li class="game-one fl ' + strLast + ' "><a href="' + roomList[i].roomURL + '" class="game-pic"><img src="' + roomList[i].roomImg + '" onerror="javascript:this.src=\''+BASE_PATH+'static/images/nopic.png\';" alt="" width="280" height="156" /><div class="live-icon"><img src="' + roomList[i].avatar + '" onerror="javascript:this.src=\''+BASE_PATH+'static/images/live-icon.png\';" alt="" /></div><div class="live-bg"></div><div class="live-begin"></div></a><h3 class="room-name"><a href="' + roomList[i].roomURL + '" class="room-name-title">' + roomList[i].roomName + '</a><a href="' + roomList[i].gameURL + '" class="fr room-game-name">' + roomList[i].gameName + '</a></h3><div class="room-topic clearfix"><div class="fl">' + roomList[i].bogerName + '</div><div class="fr"><i class="icon-sprite icon-people fl"></i>' + roomList[i].viewNum + '</div></div><i class="show-line-b"></i></li>';
                        });
                        $(".game-list").append(newDatas);

                        if(roomList.length<pagesize){
                            $(".more").hide();
                            $(".noMoreData").show().addClass("hide3s");
                        }
                    } else {
                        $(".more").hide();
                        $(".noMoreData").show().addClass("hide3s");
                    }
                })
            });
            page++;
        });
    },
    gameCenterMore: function (gameCenterMoreAPIURL, gameLiveURL) {
        $(".game-more[ data-listnum = '7']").show();
        var page = 2;
        $(".more").on("click", function () {
            $.ajax({
                url: gameCenterMoreAPIURL,
                type: 'GET',
                async: false,
                dataType: 'json',
                data: {pageIndex: page},
                success: (function (data) {
                    var dataLists = '';
                    var num = 1;
                    var lastClass = '';
                    for (x in data) {
                        for (i = 0; i < data[x].gameList.length; i++) {
                            if (i % 4 == 3 && i != 0) {
                                lastClass = " last";
                            } else {
                                lastClass = "";
                            }
                            if (i < 8) {
                                dataLists = dataLists + '<li class="' + data[x].gameList[i].className + '' + lastClass + '"><a href="live-detail.html?' + data[x].gameList[i].liveAddress + '" class="game-pic" target="_blank"> <img src="' + data[x].gameList[i].gameImg + '" onerror="javascript:this.src=' + "'" + BASE_PATH +'static/images/nopic.png' + "'" + ';" alt="' + data[x].gameList[i].liveTitle + '" width="280" height="156"/><div class="live-icon"> <img src="' + data[x].gameList[i].livePic + '" alt=""></div> <div class="live-bg"></div><div class="live-begin"></div></a> <h3 class="room-name mgt10"><a href="live-detail.html?' + data[x].gameList[i].liveAddress + '">' + data[x].gameList[i].liveTitle + '</a></h3> <div class="room-topic clearfix"><div class="fl"><i class="icon-sprite icon-pople fl"></i>' + data[x].gameList[i].liveName + '</div><div class="fr"><i class="icon-sprite icon-gamename fl"></i>' + data[x].gameList[i].gameNume + '</div><div class="fr"><i class="icon-sprite icon-eye fl"></i>' + data[x].gameList[i].viewNum + '</div></div><i class="show-line-b"></i> </li>';
                                num = i;
                            }
                        }
                        text = ' <div class="game-box"><div class="game-title clearfix"><h3 class="game-name fl"><a href="' + gameLiveURL + data[x].addressId + '">' + data[x].nameGame + '</a></h3> <p class="game-people fl">共有<span class="col-main">' + data[x].liveNum + '</span>个主播正在直播</p> <span class="game-more fr" data-listnum="' + num + '"><a href="' + gameLiveURL + data[x].addressId + '" class="fr">更多>></a></span> </div> <ul class="game-list clearfix">' + dataLists + '</ul>';
                        $(".game-center").append(text);
                        $(".game-more[ data-listnum = '7']").show();
                        dataLists = '';
                    }
                    if (data == "") {
                        $(".more").hide();
                        $(".noMoreData").show().addClass("hide3s");
                    }
                })
            });
            page = page + 1;
        });
    },
    header_live: function (API) {
        $(".recommend-nlink a").on("click", function () {
            _header_live();

        });
        function _header_live() {
            var ids = $.trim($('#randroomIds').val());
            $.ajax({
                url: API,
                type: 'GET',
                async: false,
                dataType: 'json',
                data: {randIds: ids},
                success: function (data) {
                    var html = '';
                    var fl = 'fr';
                    if (data.status = 1) {
                        for (x in data.data.roomList) {
                            if (x % 2 == 0) {
                                fl = "fl";
                            } else {
                                fl = "fr";
                            }
                            html += '<li class="recommend-none ' + fl + '"><a href="' + data.data.roomList[x].url + '"> <img src="' + data.data.roomList[x].img + '" onerror="javascript:this.src=\'' + BASE_PATH + 'static/images/nopic.png\';" width="137" height="78" alt="' + data.data.roomList[x].title + '"/> <p>' + data.data.roomList[x].title + '</p></a></li>'
                        }
                        $('#randroomIds').val(data.data.randRoomIds);
                        $(".recommend-nlist ul ").html(html);
                    } else {
                        $.xbox.tips(data.msg);
                    }
                }
            });
        };
    }
};
$(function () {
    //初始化页面
    new LSLive().init();
    new LSLive().header_live($(".recommend-nlink").data("action"));
    //抽奖显示隐藏
    $(".lottery-select").on("click",function(){
        if( $('.lottery-box').css("display")=='none' ){
            $('.container').removeClass('login');
            $('.lottery-box').show(500);
            $('html,body').animate({scrollTop: 0},300);
        } else {
            $('html,body').animate({scrollTop: 0},300);
            $('.lottery-box').hide(500);
            $('.container').addClass('login');
        }
    });
    //关闭中奖提示
    $(".close").on("click", function () {
        $(".tips-box").hide();
    });
});