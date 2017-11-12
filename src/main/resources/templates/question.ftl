<#include "inc/header1.ftl"/>
<#include "inc/topnav1.ftl"/>
<#include "inc/leftsidebar1.ftl"/>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            Page Header
            <small>Optional description</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
            <li class="active">Here</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

        <div class="box">
            <div class="box-header">
                <a class="btn btn-info pull-left margin q_new"><i class="fa fa-plus"></i>新增</a>
                <a href="/questionType/getExcel" class="btn btn-info  margin"><i class="fa fa-fw fa-file-excel-o"></i>导出excel</a>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <table id="example1" class="table table-bordered table-striped  table-hover">
                    <thead>
                    <tr>
                        <th style="width: 30px;">id</th>
                        <th>类型</th>
                        <th>名称</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#if questionTypes?exists>
                        <#list questionTypes as key>
                        <tr>
                            <td>${key.id}</td>
                            <td>类型</td>
                            <td>${key.name}</td>
                            <td>
                                <a class="btn q_edit" data-id="${key.id}" data-name="${key.name}"><i
                                        class="fa fa-edit"></i></a>
                                <a class="btn q_remove" data-id="${key.id}" data-name="${key.name}"><i
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
                <h4 class="modal-title">确认</h4>
            </div>
            <div class="modal-body">
                <form id="qDelForm">
                    <div class="form-group  ">

                        <input type="hidden" name="id" class="form-control" placeholder="" id="">
                    </div>
                </form>
                <p style="text-align: center;">One fine body&hellip;</p>
                <div class="info"></div>
            </div>
            <div class="modal-footer">
            <#--<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>-->
                <button type="button" class="btn btn-primary btn-q-del-submit">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="modal  fade" id="modal-edit">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增题型</h4>
            </div>
            <div class="modal-body">
                <form id="qForm">
                    <div class="form-group  ">
                        <label for="q_name" class="control-label">题目名称:</label>
                        <input type="text" name="name" class="form-control" placeholder="名称" id="q_name">
                        <input type="hidden" name="id" class="form-control" placeholder="" id="q_id">
                    </div>
                    <div class="form-group ">
                        <label for="questionType" class="control-label">题目类型:</label>
                        <select class="form-control" name="questionType" id="questionType">
                        <#if questionTypes?exists>
                            <#list questionTypes as key>
                                <option value="${key.id}">${key.name}</option>
                            </#list>
                        </#if>

                        </select>
                    </div>
                    <a class="btn btn-info margin" id="btn_new_option"><i class="fa fa-plus"></i>新增选项</a>
                </form>
                <div class="info"></div>
            </div>
            <div class="modal-footer">
            <#--<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>-->
                <button type="button" class="btn btn-primary btn-q-add-submit">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>


<script id="optionTemplate" type="text/x-jsrender">

                     <div class="form-group option-group">
                        <label  class="control-label">选项{{:name}}:</label>
                        <div>
                            <div class="input-group" style="padding-left:0;">
                                <span class="input-group-addon">
                                {{if type==1}}
                                    <input type="radio" name="option">
                                {{else type==2}}
                                    <input type="checkbox"  >
                                {{/if}}
                                </span>
                                <input type="text" class="form-control option-name" placeholder="选项名称" name="option{{:name}}" value="{{:value}}">
                                <span class="input-group-btn">
                                    <button class="btn btn-info remove_option"><i class="fa fa-remove"></i></button>
                                 </span>
                            </div>
                        </div>
                    </div>

</script>

<#include "inc/footer1.ftl"/>

<script type="text/javascript">
    $(function () {

        var index = 1;
        var tmpl = $.templates("#optionTemplate");
        $('#modal-edit').on('click', '#btn_new_option', function (e) {
            var html = tmpl.render({name: index,type:1,value:""});//1,单选，2，多选
            //console.log(html);
            index++;
            $(this).before(html);


        });
        $('#modal-edit').on('click', '.remove_option', function (e) {
            e.preventDefault();
            $(this).parent().parent().parent().parent().remove();

            $('#modal-edit .option-group').each(function (i, e) {
                $(this).find('.control-label').text("选项" + (i+1) + ":");
                $(this).find('.option-name').attr("name", "option" + (i+1));

                console.log(i+":"+e)

            });
            index--;

        });

        var type;

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
        $('.q_new').on('click', function (e) {
            type = 1;
            $('#qt_name').val('');
            $('#qt_id').val('');
            $('#modal-edit .modal-title').text("新增题目");
            $('#modal-edit').modal('show');


        });
        $('.q_edit').on('click', function (e) {
            type = 2;
            console.log(this.dataset.id);
            console.log(this.dataset.name);
            $('#modal-edit .modal-title').text("编辑题目");
            $('#q_name').val(this.dataset.name);
            $('#q_id').val(this.dataset.id);
            $('#modal-edit').modal('show');


        });
        $('.q_remove').on('click', function (e) {
            console.log(this.dataset.id);
            console.log(this.dataset.name);
            $("#modal-ensure input[name='id']").val(this.dataset.id);
            $('#modal-ensure .modal-body p').text("确认删除" + this.dataset.name + "?");
            $('#modal-ensure').modal('show');
        });
        $('.btn-q-add-submit').on('click', function (e) {
            if ($('#q_name').val().trim() == '') return;
            console.log(type);
            var url;
            if (type == 1)
                url = '/question/add';
            if (type == 2)
                url = "/question/update";
            // $('#qt_id').val('111');
            $.ajax({
                type: 'POST',
                url: url,
                data: $('#qForm').serialize(),
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
        $('.btn-q-del-submit').on('click', function (e) {
            $.ajax({
                type: 'POST',
                url: '/question/del',
                data: $('#qDelForm').serialize(),
                success: function (data) {
                    if (data.code == '100') {

                        window.location.reload();
                    } else {
                        $('#modal-ensure .modal-body .info').text(data.msg);
                    }
                }
            })
        })

    })
</script>
</body>
</html>