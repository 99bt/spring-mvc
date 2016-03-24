/**
 * Created by llllllllllller on 2015/11/27.
 * 要替换的input需要定宽高
 *  check.a(
 {
     onCheckBoxImgUrl : "./images/return-true.png" ,
     unCheckBoxImgUrl : "./images/return-false.png",
     unRadioImgUrl : "./images/check-bg.png",
     onRadioImgUrl : "./images/checked-bg.png"
 }
 );
 */
var checkInput = $(".checkInput"),
    himSelf,
    W = [],
    H = [],
    T = [],
    L = [],
    Type = [],
    span = "",
    unspan = "",
    img1 = "",
    img2 = "",
    img3 = "",
    img4 = "",
    isChecked = [],
    checked = '';
$.each(checkInput, function (i) {
    himSelf = $(this);
    W.push(himSelf.width());
    H.push(himSelf.height());
    T.push(himSelf.offset().top);
    L.push(himSelf.offset().left);
    E = himSelf.attr("type");
    Type.push(E);
    himSelf.attr("data-link", i).attr("data-type", E);
    checked = himSelf.attr("checked");
    if (E == "checkbox" && checked == "checked") {isChecked.push("uncheckbox");} else if (E == "radio" && checked == "checked") {isChecked.push("radioed")} else {isChecked.push(" ")}
});
var check = {
    a: function (style) {
        img1 = style.onCheckBoxImgUrl = style.onCheckBoxImgUrl || "";
        img2 = style.unCheckBoxImgUrl = style.unCheckBoxImgUrl || "";
        img3 = style.onRadioImgUrl = style.onRadioImgUrl || "";
        img4 = style.unRadioImgUrl = style.unRadioImgUrl || "";
        checkInput.attr("style", '/* make the div translucent */opacity: 0;  /* Firefox, Safari(WebKit), Opera)filter: "alpha(opacity=0)";/* IE 8 */filter: alpha(opacity=0);  /* IE 4-7 */zoom: 1;  /* needed in IE up to version 7, or set width or height to trigger "hasLayout" */"');
        for (i = 0; i < checkInput.length; i++) {
            if (Type[i] == "radio") {
                style.onImgUrl = style.onRadioImgUrl
            } else if (Type[i] == "checkbox") {
                style.onImgUrl = style.onCheckBoxImgUrl
            }
            span = "<span class='addCheckSpan " + Type[i] + " " + isChecked[i] + "' " + " data-link=" + [i] + " data-typename=" + Type[i] + " style=" + '"' + "display: block; width: " + W[i] + "px;height: " + H[i] + "px; position: absolute;top:" + T[i] + "px;left:" + L[i] + "px;background: url(" + "'" + style.onImgUrl + "'" + " ) no-repeat center;background-size: cover;cursor:pointer;" + '"' + " > </span>";
            $("body").append(span);
        }
        unspan = "<style class='checkstyle'>.uncheckbox{background:url(" + style.unCheckBoxImgUrl + ") !important;background-size: cover !important;} .radioed{background:url(" + style.unRadioImgUrl + ") !important;background-size: cover !important;} </style>";
        $("head").append(unspan);
        $(".addCheckSpan").on("click", function () {
            tt = $(this);
            dataTypeName = $(this).data("typename");
            dataLink = $(this).data("link");
            linkInput = $("input[data-link = " + dataLink + "]");
            nameState = linkInput.attr("name");
            if (dataTypeName == "radio") {
                $("input[data-type = 'radio' ]").attr("data-typename", "false");
                $("span[data-typename = 'radio' ]").removeClass("radioed");
                $(":input[data-link = " + dataLink + "]").attr("data-typename", "true");
                tt.addClass("radioed");
                return false;
            } else if (dataTypeName == "checkbox" && nameState != "false") {
                linkInput.attr("name", "false");
                tt.toggleClass("uncheckbox");
                return false;
            } else if (dataTypeName == "checkbox" && nameState == "false") {
                linkInput.attr("name", "true");
                tt.toggleClass("uncheckbox");
                return false;
            }
        });
    },
    compatible: function () {
        var cop = "<style class='mnb' >.checkbox{filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + '"' + img1 + '"' + ",sizingMethod='scale'); -ms-filter:" + '"' + '"' + "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + '"' + img1 + '"' + ", sizingMethod='scale' " + ")};" + " .radio{filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + '"' + img3 + '"' + ",sizingMethod='scale'); -ms-filter:" + '"' + '"' + "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + '"' + img3 + '"' + ", sizingMethod='scale')};.radioed{filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + '"' + img4 + '"' + ",sizingMethod='scale'); -ms-filter:" + '"' + '"' + "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + '"' + img4 + '"' + ", sizingMethod='scale')};.uncheckbox{filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + '"' + img2 + '"' + ",sizingMethod='scale'); -ms-filter:" + '"' + '"' + "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + '"' + img2 + '"' + ", sizingMethod='scale')}</style>";
        $("head").append(cop);
    },
    has_placeholder: function (x) {
        var width = 0,
            height = 0,
            top = 0,
            left = 0,
            input = [],
            fontSize = 0;
        $.each(x, function (i) {
            if ($(this).attr("type") == "password" || $(this).attr("type") == "text") {
                $(this).attr("data-inputnum", i);
                width = $(this).innerWidth();
                height = $(this).innerHeight();
                top = $(this).offset().top;
                left = $(this).offset().left;
                color = $(this).css("color");
                fontSize = $(this).css("line-height");
                placeholder = $(this).attr("placeholder");
                $(this).attr("placeholder", "");
                if (placeholder != "") {
                    $(this).attr("data-reserveplh", placeholder);
                } else if (placeholder == "") {
                    placeholder = $(this).data("reserveplh");
                }
                if (height / 3 > 20) {
                    fontSize = 20;
                } else if (height / 3 < 10) {
                    fontSize = 10;
                } else {
                    fontSize = height / 3;
                }
                input.push(this);
                var div = '<div class="placeholderDiv" data-inputnum="' + [i] + '" style="cursor:text;position: absolute;top:' + top + 'px;left:' + left + 'px;                     padding: 2px;width:' + width + 'px;height:' + height + 'px;line-height:' + height + 'px;font-family: Microsoft YaHei' + ';font-size:' + fontSize + 'px;">' + placeholder + '</div>';
                $(div).appendTo("body");
            }
        });
    },
    hide_placeholder: function () {
        $(".placeholderDiv").on("click", function () {
            var data = $(this).data("inputnum"),
                thisinput = $(this);
            $(this).hide();
            $("input[data-inputnum =" + data + "]").focus().blur(function () {
                if ($(this).val() == "") {
                    thisinput.show();
                } else {
                    thisinput.hide();
                }
            });
            return false;
        });

        $("input").focus(function () {
            var num = $(this).data("inputnum");
            $(".placeholderDiv[data-inputnum =" + num + "]").hide();
        }).blur(function () {
            var num = $(this).data("inputnum"),
                val = $(this).val();
            if (val == "") {
                $(".placeholderDiv[data-inputnum =" + num + "]").show();
            } else {
                $(".placeholderDiv[data-inputnum =" + num + "]").hide();
            }

        });
    },
    placeholder: function () {
        var allinput = $("input");
        check.has_placeholder(allinput);
        check.hide_placeholder();
    },
    hasHidePlaceholder: function (bindName, type) {
        $("input").each(function () {
            if ($(this).is(":visible")) {
                $(this).attr("data-visible", 1);
            } else {
                $(this).attr("data-visible", 0)
            }
        });
        var xx = $("input[data-visible = '1']");
        check.has_placeholder(xx);
        check.hide_placeholder();
        $(bindName).on(type, function () {
            $("input").each(function () {
                if ($(this).is(":visible")) {
                    $(this).attr("data-visible", 1);
                } else {
                    $(this).attr("data-visible", 0)
                }
            });
            $(".placeholderDiv").remove();
            xx = $("input[data-visible = '1']");
            check.has_placeholder(xx);
            check.hide_placeholder();
            xx.val("");
        });
    }
};