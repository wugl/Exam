$(function () {

    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }

    var _store = {
        /**
         * Get a prestored setting
         *
         * @param String name Name of of the setting
         * @returns String The value of the setting | null
         */
        get: function (name) {
            if (typeof (Storage) !== 'undefined') {
                return localStorage.getItem(name)
            } else {
                window.alert('Please use a modern browser to properly view this template!')
            }
        },

        /**
         * Store a new settings in the browser
         *
         * @param String name Name of the setting
         * @param String val Value of the setting
         * @returns void
         */
        set: function (name, val) {
            if (typeof (Storage) !== 'undefined') {
                localStorage.setItem(name, val)
            } else {
                window.alert('浏览器不支持localStorage，请使用最新版本!')
            }
        },
        remove: function (name) {
            if (typeof (Storage) !== 'undefined') {
                localStorage.removeItem(name)
            } else {
                window.alert('浏览器不支持localStorage，请使用最新版本!')
            }
        },
        clear: function () {
            if (typeof (Storage) !== 'undefined') {
                localStorage.clear()
            } else {
                window.alert('浏览器不支持localStorage，请使用最新版本!')
            }
        }
    };
    window._store = _store;

    var checkNum = function (input) {
        var re = /^[1-9]+[0-9]*]*$/; ///^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/

        return re.test(input);
    }
    window.checkNum = checkNum;

    window.isMobile = function () {
        var sUserAgent = navigator.userAgent.toLowerCase();
        var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
        var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
        var bIsMidp = sUserAgent.match(/midp/i) == "midp";
        var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
        var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
        var bIsAndroid = sUserAgent.match(/android/i) == "android";
        var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
        var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";

        if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {

            return true
        } else {

            return false;
        }
    };

    $('#logout').on('click', function (e) {

        $.post('/logout', function (data) {
            //console.log(data)
            if (data.code == '100') {
                _store.set('user', '');
                window.location.href = "/";
            }
        })

    });

    $('#profile').on('click', function (e) {
        // console.log(e);

    });

    $('.sidebar-menu>li').removeClass("active");
    //console.log(location.href);

    var pathUrl = location.href.split('/')[3].replace(/#/g, "");
    //console.log(pathUrl);
    if (pathUrl != '')
        $('.sidebar-menu>li').each(function () {
            var that = this;

            //console.log(this);
            // if($(this).find('a')) {
            var href = $(this).find('a').attr('href');
            //console.log(href);
            if (href && href.substr(1)==pathUrl) {
                $(that).addClass('active');
            }
            // }
        })


})