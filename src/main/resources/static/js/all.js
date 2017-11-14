$(function () {

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

    $('#logout').on('click', function (e) {

        $.post('/logout', function (data) {
            console.log(data)
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