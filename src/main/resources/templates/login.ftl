<#include "inc/header1.ftl"/>
<style>
    .login-box, .register-box {
        -webkit-transition: -webkit-transform .3s ease-in-out, width .3s ease-in-out;
        -moz-transition: -moz-transform .3s ease-in-out, width .3s ease-in-out;
        -o-transition: -o-transform .3s ease-in-out, width .3s ease-in-out;
        transition: transform .3s ease-in-out, width .3s ease-in-out;
    }

</style>
<div class="content-wrapper">
    <section class="content container-fluid">
        <div class="login-box">
            <div class="login-logo">
                <a href="##">用户登陆</a>
            </div>
            <!-- /.login-logo -->
            <div class="login-box-body">
                <p class="login-box-msg">Sign in to start your session</p>

                <form id="loginForm">
                    <div class="form-group has-feedback">
                        <input type="text" name="name" class="form-control" placeholder="用户名" id="userName">
                        <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <input type="password" name="password" class="form-control" placeholder="密码">
                        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                    </div>
                    <div class="form-group ">
                        <select class="form-control" name="userType" id="userType">
                            <option value="1">学生</option>
                            <option value="2">老师</option>
                            <option value="3">管理员</option>
                        </select>
                    </div>
                    <div class="row">
                        <div class="col-xs-8">
                            <div class="checkbox icheck">
                                <label>
                                    <input type="checkbox" id="remeber"> 记住我
                                </label>
                            </div>
                        </div>
                        <!-- /.col -->
                        <div class="col-xs-4">
                            <button type="submit" class="btn btn-primary btn-block btn-flat" id="loginBtn">登陆</button>
                        </div>
                        <!-- /.col -->
                    </div>
                    <div id="msg-success" class="alert alert-info alert-dismissable" style="display: none;">
                        <button type="button" class="close" id="ok-close" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-info"></i> 登录成功!</h4>
                        <p id="msg-success-p"></p>
                    </div>
                    <div id="msg-error" class="alert alert-warning alert-dismissable" style="display: none;">
                        <button type="button" class="close" id="error-close" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-warning"></i> 出错了!</h4>
                        <p id="msg-error-p"></p>
                    </div>
                </form>



            <#--<a href="#">I forgot my password</a><br>-->
                没有账号？ <a href="##" class="text-center" id="goReg">注册</a>

            </div>
            <!-- /.login-box-body -->
        </div>
        <div class="register-box" style="display: none">
            <div class="register-logo">
                <a href="###">用户注册</a>
            </div>

            <div class="register-box-body">
                <p class="login-box-msg">Register a new membership</p>

                <form id="regForm">
                    <div class="form-group has-feedback">
                        <input type="text" class="form-control" placeholder="用户名" name="name">
                        <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <input type="email" class="form-control" placeholder="邮件" name="email">
                        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <input type="password" class="form-control" placeholder="密码" name="password">
                        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <input type="password" class="form-control" placeholder="重输密码" name="repassword">
                        <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
                    </div>
                    <div class="row">
                        <div class="col-xs-8">
                            <div class="checkbox icheck">
                                <label>
                                    <input type="checkbox"> 我同意 <a href="#">条款</a>
                                </label>
                            </div>
                        </div>
                        <!-- /.col -->
                        <div class="col-xs-4">
                            <button type="submit" class="btn btn-primary btn-block btn-flat" id="regBtn">注册</button>
                        </div>
                        <!-- /.col -->
                    </div>
                </form>

                已有账号?<a href="##" class="text-center" id="goLogin">登陆</a>
            </div>
            <!-- /.form-box -->
        </div>
        <!-- /.register-box -->
    </section>
    <!-- /.content -->
</div>


<#include "inc/footer1.ftl"/>
<script>
    $(function () {
        $('#userName').val(_store.get('userName'));
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
        $("#ok-close").click(function(){
            $("#msg-success").hide(100);
        });
        $("#error-close").click(function(){
            $("#msg-error").hide(100);
        });
        $('#loginBtn').on('click', function (e) {
            console.log($('#loginForm').serialize());
            var name = $.trim($("#loginForm input[name='name']").val());
            var password = $.trim($("#loginForm input[name='password']").val());
            var type = $('#userType').val();

            if ( name == '') {

                $("#msg-error").hide(10);

                $("#msg-error").show(100);

                $("#msg-error-p").html("用户名不能为空");

                $("#email").focus();

                return false;

            }

            if (password == '') {

                $("#msg-error").hide(10);

                $("#msg-error").show(100);

                $("#msg-error-p").html("密码不能为空");

                $("#passwd").focus();

                return false;

            }

            $("#loginBtn").prop("disabled", true);
            //e.preventDefault();
           // console.log($('#loginForm').serialize());

            $.ajax({
                type: 'POST',
                url: '/login',
                data: 'name='+name+'&password='+md5(password)+'&userType='+type,
                success: function (data) {
                    console.log(data);
                    if (data.code == '100') {
                        _store.set('user', JSON.stringify(data.data));
                        if ($('#remeber').prop("checked")) {
                            _store.set('userName', data.data.name);
                        } else {
                            _store.set('userName', '');
                        }
                        var redirect_url = '/';
                        $("#msg-error").hide(100);
                        $("#msg-success").show(100);
                        $("#msg-success-p").html(data.msg);
                        window.setTimeout(function () {
                            location.href='/';
                        },2000);

                       // location.href = redirect_url;
                    } else {
                        $("#msg-error").hide(10);
                        $("#msg-error").show(100);
                        $("#msg-error-p").html(data.msg);
                        $("#loginBtn").prop("disabled", false);
                    }
                },
                error:function(jqXHR){
                    $("#msg-error").hide(10);
                    $("#msg-error").show(100);
                    $("#msg-error-p").html("发生错误："+jqXHR.status);
                    $("#loginBtn").prop("disabled", false);
                }
            });
        });
        $('#regBtn').on('click', function (e) {
            e.preventDefault();


        });
        $('#goReg').on('click', function (e) {
            $('.login-box ').slideUp(function () {
                $(' .register-box').slideDown();
            });

        });
        $('#goLogin').on('click', function (e) {
            $('.register-box ').slideUp(function () {
                $(' .login-box').slideDown();
            });
        });
    })
    ;

    //  $(document).attr("title", "${title}");
</script>
</body>
</html>