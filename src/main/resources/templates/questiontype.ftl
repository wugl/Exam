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
            <li><a href="/"><i class="fa fa-home"></i> 首页</a></li>
            <li class="active"> ${title!""}</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

        <div class="box">
            <div class="box-header">
                <a class="btn btn-info pull-left margin qt_new"><i class="fa fa-plus"></i>新增</a>
                <#--<a href="/questionType/getExcel" class="btn btn-info  margin"><i class="fa fa-fw fa-file-excel-o"></i>导出excel</a>-->
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <table id="example1" class="table table-bordered table-striped  table-hover">
                    <thead>
                    <tr>
                        <th style="width: 30px;">id</th>
                        <th>名称</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#if questionTypes?exists>
                        <#list questionTypes as key>
                        <tr>
                            <td>${key.id}</td>
                            <td>${key.name}</td>
                            <td>
                                <a class="btn-sm qt_edit" data-id="${key.id}" data-name="${key.name}"><i
                                        class="fa fa-edit"></i></a>
                                <a class="btn-sm qt_remove" data-id="${key.id}" data-name="${key.name}"><i
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

<div class="modal modal-default fade" id="modal-ensure">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-name">确认</h4>
            </div>
            <div class="modal-body">
                <form id="qtDelForm">
                    <div class="form-group  ">


                        <input type="hidden" name="id" class="form-control" placeholder="" id="">
                    </div>
                </form>
                <p style="text-align: center;">One fine body&hellip;</p>
                <div class="info text"></div>
            </div>
            <div class="modal-footer">
            <#--<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>-->
                <button type="button" class="btn btn-primary btn-qt-del-submit">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal  fade" id="modal-edit">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-name">新增题型</h4>
            </div>
            <div class="modal-body">
                <form id="qtForm">
                    <div class="form-group  ">
                        <label for="qt_name" class="control-label">题型名称:</label>
                        <input type="text" name="name" class="form-control" placeholder="名称" id="qt_name">
                        <input type="hidden" name="id" class="form-control" placeholder="" id="qt_id">
                    </div>
                </form>
                <div class="info text-red"></div>
            </div>
            <div class="modal-footer">
            <#--<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>-->
                <button type="button" class="btn btn-primary btn-qt-add-submit">确认</button>
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
//        $('#example1 tbody').on( 'click', 'tr', function () {
//            console.log( table.row( this ).data() );
//        } );

        var type;
        $('.qt_new').on('click', function (e) {
            type = 1;
            $('#qt_name').val('');
            $('#qt_id').val('');
            $('#modal-edit .modal-name').text("新增题型");
            $('#modal-edit').modal('show');


        });
        $('.qt_edit').on('click', function (e) {
            type = 2;
            //console.log(this.dataset.id);
            //console.log(this.dataset.name);
            $('#modal-edit .modal-name').text("编辑题型");
            $('#qt_name').val(this.dataset.name);
            $('#qt_id').val(this.dataset.id);
            $('#modal-edit').modal('show');


        });
        $('.qt_remove').on('click', function (e) {
            //console.log(this.dataset.id);
            //console.log(this.dataset.name);
            $("#modal-ensure input[name='id']").val(this.dataset.id);
            $('#modal-ensure .modal-body p').html("确认删除<span style='color:red;'>" + this.dataset.name + "</span>?");
            $('#modal-ensure').modal('show');
        });
        $('.btn-qt-add-submit').on('click', function (e) {
            if ($('#qt_name').val().trim() == '') {
                $('#modal-edit .modal-body .info').text("请填写完整");
                return;
            }
            //console.log(type);
            var url;
            if (type == 1)
                url = '/questionType/add';
            if (type == 2)
                url = "/questionType/update";
           // $('#qt_id').val('111');
            $('.btn-qt-add-submit').prop("disabled", true);
            $.ajax({
                type: 'POST',
                url: url,
                data: $('#qtForm').serialize(),
                success: function (data) {
                    //console.log(data);
                    $('.btn-qt-add-submit').prop("disabled", false);
                    if (data.code == '100') {
                        window.location.reload();
                    }
                    else{
                        $('#modal-edit .modal-body .info').text(data.msg);
                    }
                },
                error:function (e) {
                    $('.btn-qt-add-submit').prop("disabled", false);
                    $('#modal-edit .modal-body .info').text(e);
                }
            })
        });
        $('.btn-qt-del-submit').on('click', function (e) {
            $('.btn-qt-del-submit').prop("disabled", true);
            $.ajax({
                type: 'POST',
                url: '/questionType/del',
                data: $('#qtDelForm').serialize(),
                success: function (data) {
                    $('.btn-qt-del-submit').prop("disabled", false);
                    if (data.code == '100') {

                        window.location.reload();
                    }else {
                        $('#modal-ensure .modal-body .info').text(data.msg);
                    }
                },
                error:function (e) {
                    $('.btn-qt-del-submit').prop("disabled", false);
                    $('#modal-ensure .modal-body .info').text(e);
                }

            })
        })

    })
</script>
</body>
</html>