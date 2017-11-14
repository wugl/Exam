<#include "inc/header1.ftl"/>
<#include "inc/topnav1.ftl"/>
<#include "inc/leftsidebar1.ftl"/>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
        ${title!""}
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
                <a class="btn btn-info pull-left margin e_new" href="/exam/new"><i class="fa fa-plus"></i>新增</a>
            <#--<a href="/questionType/getExcel" class="btn btn-info  margin"><i class="fa fa-fw fa-file-excel-o"></i>导出excel</a>-->
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <table id="example1" class="table table-bordered table-striped  table-hover">
                    <thead>
                    <tr>
                        <th style="width: 30px;">id</th>
                        <th>名称</th>
                        <th>考试日期</th>
                        <th>总时间</th>
                        <th>总分数</th>
                        <th>通过分数</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#if exams?exists>
                        <#list exams as key>
                        <tr class="row-item" data-id="${key.id}">
                            <td>${key.id}</td>
                            <td>
                            ${key.name}
                            </td>
                            <td>
                            ${key.examDate}
                            </td>
                            <td>
                            ${key.totalTime}
                            </td>
                            <td>
                            ${key.totalScore}
                            </td>
                            <td>
                            ${key.passScore}
                            </td>
                            <td>
                                <a class="btn-sm q_edit" data-id="${key.id}" data-type="${key.typeId}"><i
                                        class="fa fa-edit"></i></a>
                                <a class="btn-sm q_remove" data-id="${key.id}" data-type="${key.typeId}"
                                   data-title="${key.title}""><i
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
                        <th>考试日期</th>
                        <th>总时间</th>
                        <th>总分数</th>
                        <th>通过分数</th>
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
                <form id="eDelForm">
                    <div class="form-group  ">

                        <input type="hidden" name="id" class="form-control" placeholder="" id="">
                    </div>
                </form>
                <p style="text-align: center;"></p>
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
                <h4 class="modal-name">新增题目</h4>
            </div>
            <div class="modal-body">
                <form id="eForm">
                    <div class="form-group">
                        <label for="e_name" class="control-label">标题:</label>
                        <input type="text" name="name" class="form-control" placeholder="标题" id="e_name">

                        <input type="hidden" name="id" class="form-control" placeholder="" id="e_id">
                    </div>
                    <div class="form-group">
                        <label for="e_exam_date" class="control-label">考试日期:</label>
                        <input type="text" name="score" class="form-control" placeholder="考试日期" id="e_exam_date">

                    </div>
                    <div class="form-group">
                        <label for="e_total_score" class="control-label">总分数:</label>
                        <input type="number" name="score" class="form-control" placeholder="分数" id="e_total_score">

                    </div>
                    <div class="form-group">
                        <label for="e_pass_score" class="control-label">及格分数:</label>
                        <input type="number" name="score" class="form-control" placeholder="分数" id="e_pass_score">

                    </div>
                    <div class="form-group">
                        <label for="e_exam_time" class="control-label">考试时长:</label>
                        <input type="number" name="score" class="form-control" placeholder="考试时长" id="e_exam_time">

                    </div>

                </form>
                <div class="info text-red"></div>
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


<div class="modal  fade" id="modal-preview">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-name">题目预览</h4>
            </div>
            <div class="modal-body" style="line-height:30px;">
                <div class="title" style="display: none;"></div>
                <div class="name" style="font-weight: bold;"></div>
                <div class="options"></div>
                <div class="answer text-red""></div>
            <div class="comment"></div>
            <div class="info text-red"></div>

        </div>
        <div class="modal-footer">
        <#--<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>-->
                <#--<button type="button" class="btn btn-primary btn-q-add-submit">确认</button>-->
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


//        $('.e_new').on('click',function () {
//            $('#modal-edit').modal('show');
//        })


    })

</script>
</body>
</html>