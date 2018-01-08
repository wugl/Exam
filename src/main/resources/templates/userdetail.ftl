<#include "inc/header1.ftl"/>
<#include "inc/topnav1.ftl"/>
<#include "inc/leftsidebar1.ftl"/>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
        ${title!""}
            <#--<small>Optional description</small>-->
        </h1>
        <ol class="breadcrumb">
            <li><a href="${request.contextPath}/"><i class="fa fa-home"></i> 首页</a></li>
            <li class="active">${title!""}</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

        <div class="box">
            <div class="box-header">
                <#--<h3 class="box-name">用户信息</h3>-->
            </div>
            <!-- /.box-header -->
            <div class="box-body no-padding">
                <table class="table table-striped">
                <#--<tr>-->
                <#--<th style="width: 10px">#</th>-->
                <#--<th>Task</th>-->
                <#--<th>Progress</th>-->
                <#--<th style="width: 40px">Label</th>-->
                <#--</tr>-->
                    <tr>
                        <th  style="width: 80px">姓名</th>
                        <td>${user.name}</td>


                    </tr>
                    <tr>
                        <th>电话</th>
                        <td>${user.phone}</td>

                    </tr>
                    <tr>
                        <th>邮箱</th>
                        <td>${user.email}</td>

                    </tr>
                    <tr>
                        <th>类型</th>
                        <td>
                        <#if user.type=='STUDENT'>学生
                        <#elseif user.type=='TEACHER'>老师
                        <#elseif user.type=='MANAGER'>管理员

                        </#if>
                        </td>

                    </tr>
                    <tr>
                        <th>加入时间</th>
                        <td>${user.regTime}</td>

                    </tr>
                </table>

                <a class="btn  btn-info u-edit">
                    <i class="fa fa-edit"></i> 修改信息
                </a>
                <#--<a class="btn-sm qt_edit"><i-->
                        <#--class="fa fa-edit"></i></a>-->
            </div>
            <!-- /.box-body -->
        </div>
        <!-- /.box -->
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<div class="modal  fade" id="modal-edit">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-name">修改信息</h4>
            </div>
            <div class="modal-body">
                <form id="uForm" class="form-horizontal">
                    <div class="form-group  ">
                        <label for="u_name" class="col-sm-2 control-label">姓名:</label>
                        <div class="col-sm-10">
                            <input type="text" name="name" class="form-control" placeholder="姓名" id="u_name">
                            <input type="hidden" name="id" class="form-control" placeholder="" id="u_id">
                            <input type="hidden" name="type" class="form-control" placeholder="" id="u_type">
                        </div>
                    </div>
                    <#--<div class="form-group ">-->
                        <#--<label for="questionType" class="col-sm-2 control-label">类型:</label>-->
                        <#--<div class="col-sm-10">-->
                            <#--<select class="form-control" name="type" id="u_type">-->
                                <#--<option value="1">学生</option>-->
                                <#--<option value="2">老师</option>-->
                                <#--<option value="3">管理员</option>-->
                            <#--</select>-->
                        <#--</div>-->
                    <#--</div>-->
                    <div class="form-group  ">
                        <label for="u_password" class="col-sm-2 control-label">密码:</label>
                        <div class="col-sm-10">
                            <input type="password" name="password" class="form-control" placeholder="密码"
                                   id="u_password">
                        </div>
                    </div>
                    <div class="form-group  ">
                        <label for="u_email" class="col-sm-2 control-label">邮箱:</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" class="form-control" placeholder="邮箱" id="u_email">
                        </div>

                    </div>
                    <div class="form-group  ">
                        <label for="u_phone" class="col-sm-2 control-label">手机:</label>
                        <div class="col-sm-10">
                            <input type="text" name="phone" class="form-control" placeholder="手机" id="u_phone">
                        </div>
                    </div>

                </form>
                <div class="info text-red "></div>
            </div>
            <div class="modal-footer">
            <#--<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>-->
                <button type="button" class="btn btn-primary btn-u-add-submit">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<#include "inc/footer1.ftl"/>

<script type="text/javascript">
    $(function(){

        var user = ${userJson};

        $('.u-edit').on('click', function (e) {
          //  console.log(userType);
            var userType = user.type == 'STUDENT' ? 1 : user.type == 'TEACHER' ? 2 : 3;
           // $('#modal-edit .modal-name').text("修改信息");
            $('#u_name').val(user.name);
            $('#u_id').val(user.id);
            $('#u_email').val(user.email);
            $('#u_phone').val(user.phone);
            $('#u_password').val('');
            $('#u_type').val(userType);
            $('#modal-edit .modal-body .info').text('');
            $('#modal-edit').modal('show');


        });

        $('.btn-u-add-submit').on('click', function (e) {
            if ($('#u_name').val().trim() == '' || $('#u_password').val().trim() == '') {
                $('#modal-edit .modal-body .info').text("请填写姓名和密码");
                return;
            }

            //console.log($('#uForm').serialize());
            var data = 'name='+$('#u_name').val().trim()+'&id='+user.id+'&type='+$('#u_type').val()+'&password='+md5($('#u_password').val().trim())+'&email='+$('#u_email').val()+'&phone='+$('#u_phone').val();
            //console.log(data);
            //return;
            $.ajax({
                type: 'POST',
                url: "${request.contextPath}/user/update",
                data: data,
                success: function (data) {
                    //console.log(data);
                    if (data.code == '100') {
                        window.location.reload();
                    }
                    else {
                        $('#modal-edit .modal-body .info').text(data.msg);
                    }
                }
            })
        });
    })

</script>
</body>
</html>