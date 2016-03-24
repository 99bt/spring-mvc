/**
 * LSLive WAP
 * Yowant FE Team
 * v20160319
 */
LSWAP = function () {
    this.version = '20160319';
};
LSWAP.prototype = {
    init: function () {
        /**列表图片高度**/
        var liw = $('.live-list li').width();
        $('.live-list .mask, .live-list .room-pic').height(liw / 16 * 9);
        /** 导航 **/
        var indexNavSwiper = new Swiper('.navs', {
            freeMode: true,
            slidesPerView: 'auto'
        });
        $(".move-item").on('touchstart mousedown', function (e) {
            e.preventDefault()
            indexNavSwiper.slideNext();
        });
        /** recommend-boger **/
        var recommendbogerSwiper = new Swiper('.recommend-boger', {
            freeMode: true,
            slidesPerView: 'auto'
        });
        /** siderbar **/
        var siderbarSwiper = new Swiper('.siderbar', {
            visibilityFullFit: true,
            loop: true,
            pagination: '.pagination'
        });
        $(window).resize(function () {
            /**列表图片高度**/
            var liw = $('.live-list li').width();
            $('.live-list .mask, .live-list .room-pic').height(liw / 16 * 9);
        });
        /** 顶部跳转到选中的Tab **/
        var onIndex = 0;
        var sliders = $('.navs .swiper-slide');
        var l = sliders.length;
        for (ii = 0; ii < l; ii++) {
            if ($(sliders[ii]).hasClass('on')) {
                onIndex = ii;
            }
        }
        if (typeof (indexNavSwiper.slideTo) == "function") {
            indexNavSwiper.slideTo(onIndex);
        }
        /** end **/
    },
    getData: function (url, insObj) {
        livePage = 2;
        $(document).scroll(function () {
            var top = parseInt(document.body.scrollTop); //$(document).scrollTop();
            var winH = $(window).height();
            var docH = $(document).height();

            if (docH == winH + top) {
                $.ajax({
                    url: url,
                    type: 'POST',
                    async: false,
                    dataType: 'json',
                    data: {pageIndex: livePage},
                    success: (function (data) {
                        var html = '';
                        for (x in data) {
                            html += '<li><a href="' + data[x].url + '"><div class="mask"></div><img class="room-pic" src="' + data[x].gameImg + '" onerror="javascript:this.src=\'data:img/jpg;base64,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			\';" alt=""></a><div class="room-info"><a href="' + data[x].url + '"><img class="avatar" src="' + data[x].avatar + '" onerror="javascript:this.src=\'data:img/jpg;base64,iVBORw0KGgoAAAANSUhEUgAAAKAAAACgCAIAAAAErfB6AAAACXBIWXMAAAsTAAALEwEAmpwYAAA5pGlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4KPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMwNjcgNzkuMTU3NzQ3LCAyMDE1LzAzLzMwLTIzOjQwOjQyICAgICAgICAiPgogICA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPgogICAgICA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIgogICAgICAgICAgICB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iCiAgICAgICAgICAgIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIgogICAgICAgICAgICB4bWxuczpzdFJlZj0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlUmVmIyIKICAgICAgICAgICAgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIKICAgICAgICAgICAgeG1sbnM6ZGM9Imh0dHA6Ly9wdXJsLm9yZy9kYy9lbGVtZW50cy8xLjEvIgogICAgICAgICAgICB4bWxuczpwaG90b3Nob3A9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGhvdG9zaG9wLzEuMC8iCiAgICAgICAgICAgIHhtbG5zOnRpZmY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vdGlmZi8xLjAvIgogICAgICAgICAgICB4bWxuczpleGlmPSJodHRwOi8vbnMuYWRvYmUuY29tL2V4aWYvMS4wLyI+CiAgICAgICAgIDx4bXA6Q3JlYXRvclRvb2w+QWRvYmUgUGhvdG9zaG9wIENDIDIwMTUgKE1hY2ludG9zaCk8L3htcDpDcmVhdG9yVG9vbD4KICAgICAgICAgPHhtcDpDcmVhdGVEYXRlPjIwMTYtMDEtMThUMTQ6MzE6MjIrMDg6MDA8L3htcDpDcmVhdGVEYXRlPgogICAgICAgICA8eG1wOk1vZGlmeURhdGU+MjAxNi0wMS0xOFQxNDozMjoyOSswODowMDwveG1wOk1vZGlmeURhdGU+CiAgICAgICAgIDx4bXA6TWV0YWRhdGFEYXRlPjIwMTYtMDEtMThUMTQ6MzI6MjkrMDg6MDA8L3htcDpNZXRhZGF0YURhdGU+CiAgICAgICAgIDx4bXBNTTpJbnN0YW5jZUlEPnhtcC5paWQ6Nzk2ZjBjMDQtNzE4YS00MDJjLTg1MzEtOGEyYzIwMDY1MWVmPC94bXBNTTpJbnN0YW5jZUlEPgogICAgICAgICA8eG1wTU06RG9jdW1lbnRJRD54bXAuZGlkOkY1MzA3OTEwQkJGNDExRTVCRTBCODhCMEE3M0IyMjdCPC94bXBNTTpEb2N1bWVudElEPgogICAgICAgICA8eG1wTU06RGVyaXZlZEZyb20gcmRmOnBhcnNlVHlwZT0iUmVzb3VyY2UiPgogICAgICAgICAgICA8c3RSZWY6aW5zdGFuY2VJRD54bXAuaWlkOkY1MzA3OTBEQkJGNDExRTVCRTBCODhCMEE3M0IyMjdCPC9zdFJlZjppbnN0YW5jZUlEPgogICAgICAgICAgICA8c3RSZWY6ZG9jdW1lbnRJRD54bXAuZGlkOkY1MzA3OTBFQkJGNDExRTVCRTBCODhCMEE3M0IyMjdCPC9zdFJlZjpkb2N1bWVudElEPgogICAgICAgICA8L3htcE1NOkRlcml2ZWRGcm9tPgogICAgICAgICA8eG1wTU06T3JpZ2luYWxEb2N1bWVudElEPnhtcC5kaWQ6RjUzMDc5MTBCQkY0MTFFNUJFMEI4OEIwQTczQjIyN0I8L3htcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD4KICAgICAgICAgPHhtcE1NOkhpc3Rvcnk+CiAgICAgICAgICAgIDxyZGY6U2VxPgogICAgICAgICAgICAgICA8cmRmOmxpIHJkZjpwYXJzZVR5cGU9IlJlc291cmNlIj4KICAgICAgICAgICAgICAgICAgPHN0RXZ0OmFjdGlvbj5zYXZlZDwvc3RFdnQ6YWN0aW9uPgogICAgICAgICAgICAgICAgICA8c3RFdnQ6aW5zdGFuY2VJRD54bXAuaWlkOjc5NmYwYzA0LTcxOGEtNDAyYy04NTMxLThhMmMyMDA2NTFlZjwvc3RFdnQ6aW5zdGFuY2VJRD4KICAgICAgICAgICAgICAgICAgPHN0RXZ0OndoZW4+MjAxNi0wMS0xOFQxNDozMjoyOSswODowMDwvc3RFdnQ6d2hlbj4KICAgICAgICAgICAgICAgICAgPHN0RXZ0OnNvZnR3YXJlQWdlbnQ+QWRvYmUgUGhvdG9zaG9wIENDIDIwMTUgKE1hY2ludG9zaCk8L3N0RXZ0OnNvZnR3YXJlQWdlbnQ+CiAgICAgICAgICAgICAgICAgIDxzdEV2dDpjaGFuZ2VkPi88L3N0RXZ0OmNoYW5nZWQ+CiAgICAgICAgICAgICAgIDwvcmRmOmxpPgogICAgICAgICAgICA8L3JkZjpTZXE+CiAgICAgICAgIDwveG1wTU06SGlzdG9yeT4KICAgICAgICAgPGRjOmZvcm1hdD5pbWFnZS9wbmc8L2RjOmZvcm1hdD4KICAgICAgICAgPHBob3Rvc2hvcDpDb2xvck1vZGU+MzwvcGhvdG9zaG9wOkNvbG9yTW9kZT4KICAgICAgICAgPHRpZmY6T3JpZW50YXRpb24+MTwvdGlmZjpPcmllbnRhdGlvbj4KICAgICAgICAgPHRpZmY6WFJlc29sdXRpb24+NzIwMDAwLzEwMDAwPC90aWZmOlhSZXNvbHV0aW9uPgogICAgICAgICA8dGlmZjpZUmVzb2x1dGlvbj43MjAwMDAvMTAwMDA8L3RpZmY6WVJlc29sdXRpb24+CiAgICAgICAgIDx0aWZmOlJlc29sdXRpb25Vbml0PjI8L3RpZmY6UmVzb2x1dGlvblVuaXQ+CiAgICAgICAgIDxleGlmOkNvbG9yU3BhY2U+NjU1MzU8L2V4aWY6Q29sb3JTcGFjZT4KICAgICAgICAgPGV4aWY6UGl4ZWxYRGltZW5zaW9uPjE2MDwvZXhpZjpQaXhlbFhEaW1lbnNpb24+CiAgICAgICAgIDxleGlmOlBpeGVsWURpbWVuc2lvbj4xNjA8L2V4aWY6UGl4ZWxZRGltZW5zaW9uPgogICAgICA8L3JkZjpEZXNjcmlwdGlvbj4KICAgPC9yZGY6UkRGPgo8L3g6eG1wbWV0YT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAKPD94cGFja2V0IGVuZD0idyI/Pvkw0XsAAAAgY0hSTQAAeiUAAICDAAD5/wAAgOkAAHUwAADqYAAAOpgAABdvkl/FRgAADUlJREFUeNrsnetX4sYbx0lCuEQQYbnfiqtEUJY9Pe22///LHrdbha7LRVpFkIACihAuSUhfcM7+fme7zYwKM5Mw39eDTvLJJM8881wYXddtVNYVS28BBUxFAVNRwFQUMBUFTEUBU1HAFDAVBUxFAVNRwFQUMBUFTEUBb6vs1rskVVWfnp4mk4ksy9PpdD6fLxYLRVGWy+X/nmuW5Xme53mXy+V2uwVB2NnZ8Xq9drvVbghjjQN/VVWHw2G/3398fByPxy/+Ozs7O3t7e2/evPH7/daAbW7Amqbd3d11u93BYPD/C3QN94VhAoFANBoNhUIcx1HAqDUej29ubrrdrqZpG/1HHMdFIpFUKuXxeChgFBoOh1dXV4PBAPH/DQQCmUzG7/dTwJvS09NTvV4fDocY5+D3+w8PD3d3dyngdWqxWFxeXnY6HULmE4vFDg8PHQ4HBbwGSZJUq9UURSFqVjzPZ7PZWCxGAb9ciqJUKpVer0fsDEOhUD6f53meAn62RqNRuVyezWaELxGn01ksFon9KhMK+Pb2tlqtvn5ry3GcIAiCIHAcx3Hcynehqqqm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\';" alt=""></a><a href="' + data[x].url + '"><h2>' + data[x].roomName + '</h2></a><p><span class="fl uname">' + data[x].liver + '</span><span class="fr viewnum">' + data[x].watchNum + '</span></p></div></li>';
                        }
                        $(insObj).append(html);
                        $(window).resize();
                        html = "";
                        if (!data.length) {
                            //'没有更多数据了'
                        }
                        livePage += 1;
                    })
                });
            }
        });
    }
};
LSWAP.room = function () {
};
LSWAP.room.prototype = {
    init: function () {
        /** page Init **/
        var ww = $(window).width();
        ww = ww <= 640 ? ww : 640;
        var nh = (ww / 16) * 9;
        $('#video-show img').css({'width': ww, 'height': nh});
        $('#videoPlayer').css({'width': ww, 'height': nh});
        $('.playerArea, .play').height(nh);
        $(".error").css("line-height", nh + "px");
        /**列表图片高度**/
        var liw = $('.live-list li').width();
        $('.live-list .mask, .live-list .room-pic').height(liw / 16 * 9);
        /** tabs **/
        var tabsSwiper = new Swiper('#tabs-container', {
            speed: 500,
            onSlideChangeStart: function () {
                $(".tabs .active").removeClass('active');
                $(".tabs a").eq(tabsSwiper.activeIndex).addClass('active');
                var i = tabsSwiper.activeIndex;
                if (i == 0) {
                    $('.gift-area').show();
                } else {
                    $('.gift-area').hide();
                }
                $('.talk-input').blur();
            }
        });
        $(".tabs a").on('touchstart mousedown', function (e) {
            var i = $(this).index();
            e.preventDefault();
            $(".tabs .active").removeClass('active');
            $(this).addClass('active');
            if (i == 0) {
                $('.gift-area').show();
            } else {
                $('.gift-area').hide();
            }
            tabsSwiper.slideTo(i);
            $('.talk-input').blur();
        });
        $(".tabs a").click(function (e) {
            $('.talk-input').blur();
            e.preventDefault()
        })
        this.op();
    },
    op: function () {
        $(window).resize(function () {
            /** page Init **/
            var ww = $(window).width();
            ww = ww <= 640 ? ww : 640;
            var nh = (ww / 16) * 9;
            $('#video-show img').css({'width': ww, 'height': nh});
            $('#videoPlayer').css({'width': ww, 'height': nh});
            $('.playerArea, .play').height(nh);
            $(".error").css("line-height", nh + "px");
            /**列表图片高度**/
            var liw = $('.live-list li').width();
            $('.live-list .mask, .live-list .room-pic').height(liw / 16 * 9);
        });

        $('#video-show img').attr('src', RoomImg);
        $('#videoPlayer').hide();

        $('#video-show').on('click', function () {
            $(this).hide();
            if (!document.getElementById('videoPlayer').pause()) {
                $('#loading').show();
                document.getElementById('videoPlayer').load();
                $('#top_area').addClass('hide');
            } else {
                document.getElementById('videoPlayer').play();
            }
        });
        //数据加载完成,可以播放了
        document.getElementById('videoPlayer').oncanplaythrough = function () {
            //$('#top_area').addClass('hide');
            document.getElementById('loading').style.display = 'none';
            document.getElementById('videoPlayer').style.display = 'block';
            document.getElementById('videoPlayer').play();
        };
        //暂停时
        document.getElementById('videoPlayer').onpause = function () {
            $('#playBtn').addClass('pause');
            $('#video-show').show();
            $('#top_area').removeClass('hide');
        };

    }
};
/** weixin **/
function is_weixin() {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        return true;
    } else {
        return false;
    }
}