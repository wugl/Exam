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
            <li><a href="#"><i class="fa fa-home"></i> 首页</a></li>
            <li class="active">Here</li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="content container-fluid">

        <div class="box">
            <div class="box-header">
                <a class="btn btn-info pull-left margin u_new"><i class="fa fa-plus"></i>新增</a>
                <a href="/user/getExcel" class="btn btn-info  margin"><i
                        class="fa fa-fw fa-file-excel-o"></i>导出excel</a>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <table id="example1" class="table table-bordered table-striped  table-hover">
                    <thead>
                    <tr>
                        <th style="width: 30px;">id</th>
                        <th>名称</th>
                        <th>类型</th>
                        <th>邮箱</th>
                        <th>手机</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#if allUsers?exists>
                        <#list allUsers as key>
                        <tr>
                            <td>${key.id}</td>
                            <td>${key.name}</td>
                            <td>
                                <#if key.type=='STUDENT'>学生
                                <#elseif key.type=='TEACHER'>老师
                                <#elseif key.type=='MANAGER'>管理员
                                </#if>
                            </td>

                            <td>${key.email}</td>
                            <td>${key.phone}</td>
                            <td>
                                <a class="btn-sm u_edit" data-id="${key.id}" data-name="${key.name}"
                                   data-type="${key.type}"
                                   data-email="${key.email}" data-phone="${key.phone}"><i
                                        class="fa fa-edit"></i></a>
                                <a class="btn-sm u_remove" data-id="${key.id}" data-name="${key.name}"
                                   data-type="${key.type}"
                                   data-email="${key.email}" data-phone="${key.phone}"><i
                                        class="fa fa-remove"></i></a>
                            </td>
                        </tr>
                        </#list>
                    </#if>


                    </tbody>
                    <tfoot>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>类型</th>
                        <th>邮箱</th>
                        <th>手机</th>
                        <th>操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>


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
                <h4 class="modal-name">新增用户</h4>
            </div>
            <div class="modal-body">
                <form id="uForm" class="form-horizontal">
                    <div class="form-group  ">
                        <label for="u_name" class="col-sm-2 control-label">姓名:</label>
                        <div class="col-sm-10">
                            <input type="text" name="name" class="form-control" placeholder="姓名" id="u_name">
                            <input type="hidden" name="id" class="form-control" placeholder="" id="u_id">
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="questionType" class="col-sm-2 control-label">类型:</label>
                        <div class="col-sm-10">
                            <select class="form-control" name="type" id="u_type">
                                <option value="1">学生</option>
                                <option value="2">老师</option>
                                <option value="3">管理员</option>
                            </select>
                        </div>
                    </div>
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
<div class="modal modal-default fade" id="modal-ensure">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-name">确认</h4>
            </div>
            <div class="modal-body">
                <form id="uDelForm">
                    <div class="form-group  ">

                        <input type="hidden" name="id" class="form-control" placeholder="" id="">
                    </div>
                </form>
                <p style="text-align: center;"></p>
                <div class="info"></div>
            </div>
            <div class="modal-footer">
            <#--<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>-->
                <button type="button" class="btn btn-primary btn-u-del-submit">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<#include "inc/footer1.ftl"/>

<script type="text/javascript">


    $(function () {
        var table = $('#example1').DataTable({
            "stateSave": true,
            "language": {
                "url": " dataTables.Chinese.lang.json"
                // "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Chinese.json"
            }
        });


        var type;
        $('.u_new').on('click', function (e) {
            type = 1;
            $('#u_name').val('');
            $('#u_id').val('');
            $('#u_email').val('');
            $('#u_password').val('');
            $('#u_phone').val('');
            $('#modal-edit .modal-name').text("新增用户");
            $('#modal-edit .modal-body .info').text('');
            $('#modal-edit').modal('show');


        });
        $('.u_edit').on('click', function (e) {
            type = 2;
            console.log(this.dataset.id);
            console.log(this.dataset.name);

            var userType = this.dataset.type == 'STUDENT' ? 1 : this.dataset.type == 'TEACHER' ? 2 : 3;

            $('#modal-edit .modal-name').text("编辑用户");
            $('#u_name').val(this.dataset.name);
            $('#u_id').val(this.dataset.id);
            $('#u_email').val(this.dataset.email);
            $('#u_phone').val(this.dataset.phone);
            $('#u_password').val(this.dataset.password);
            $('#u_type').val(userType);
            $('#modal-edit .modal-body .info').text('');
            $('#modal-edit').modal('show');


        });
        $('.u_remove').on('click', function (e) {
            console.log(this.dataset.id);
            console.log(this.dataset.name);
            $("#modal-ensure input[name='id']").val(this.dataset.id);
            $('#modal-ensure .modal-body p').html("确认删除<span style='color:red;'>" + this.dataset.name + "</span>?");
            $('#modal-ensure .modal-body .info').text("");
            $('#modal-ensure').modal('show');
        });

        $('.btn-u-add-submit').on('click', function (e) {
            if ($('#u_name').val().trim() == '' || $('#u_password').val().trim() == '') {
                $('#modal-edit .modal-body .info').text("请填写姓名和密码");
                return;
            }
            console.log(type);
            var url;
            if (type == 1)
                url = '/user/add';
            if (type == 2)
                url = "/user/update";
            // $('#qt_id').val('111');
            console.log($('#uForm').serialize());
            $.ajax({
                type: 'POST',
                url: url,
                data: $('#uForm').serialize(),
                success: function (data) {
                    console.log(data);
                    if (data.code == '100') {
                        window.location.reload();
                    }
                    else {
                        $('#modal-edit .modal-body .info').text(data.msg);
                    }
                }
            })
        });
        $('.btn-u-del-submit').on('click', function (e) {
            $.ajax({
                type: 'POST',
                url: '/user/del',
                data: $('#uDelForm').serialize(),
                success: function (data) {
                    if (data.code == '100') {

                        window.location.reload();
                    } else {
                        $('#modal-ensure .modal-body .info').text(data.msg);
                    }
                }
            })
        })
    });

</script>
</body>
</html>