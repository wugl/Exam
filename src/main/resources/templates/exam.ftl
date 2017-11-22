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
        <#--<li><a href="/exam">试卷管理</a></li>-->
            <li class="active">${title!""}</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

        <div class="box">
            <div class="box-header">
                <a class="btn btn-info pull-left margin e_new" href="${request.contextPath}/exam/new"><i class="fa fa-plus"></i>新增</a>
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
                            ${key.examDate?string('yyyy-MM-dd HH:mm')}
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
                                <a class="btn-sm e_edit" data-id="${key.id}" href="${request.contextPath}/exam/edit?id=${key.id}"><i
                                        class="fa fa-edit"></i></a>
                                <a class="btn-sm e_remove" data-id="${key.id}" data-name="${key.name}"><i
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
                <div class="info text-red"></div>
            </div>
            <div class="modal-footer">
            <#--<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>-->
                <button type="button" class="btn btn-primary btn-e-del-submit">确认</button>
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
                <h4 class="modal-name">试卷预览</h4>
            </div>
            <div class="modal-body" style="line-height:30px;">
                <div class="title" style="display: none;"></div>
                <h3 class="name" style="font-weight: bold;text-align: center;"></h3>
                <div class="sub" style="text-align: center;"></div>
                <div class="questions"></div>
                <#--<div class="answer text-red"></div>-->

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
                    "url": "${request.contextPath}/dataTables.Chinese.lang.json"
                    // "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Chinese.json"
                }
            });

            $('.row-item').on('click', function (e) {
                //console.log('row');

                $.ajax({
                    type: 'GET',
                    url: '${request.contextPath}/exam/getById',
                    data: 'id=' + this.dataset.id,
                    success: function (data) {
                        //console.log(data);
                        if (data.code == '100') {
                           $('#modal-preview .modal-body .name').html( data.data.name);
                            $('#modal-preview .modal-body .sub').html("考试时间："+new Date(data.data.examDate).Format("yyyy-MM-dd hh:mm")+"，考试时长："+data.data.totalTime+'分钟，总分：' + data.data.totalScore + "，及格："+data.data.passScore);
//                            $('#modal-preview .modal-body .answer').html("答案：" + data.data.question.answer);
//                            $('#modal-preview .modal-body .comment').html("点评：" + data.data.question.comment);
//                            var answers = data.data.question.answer.split('|');
//                            //console.log(answers.length);
                            $('#modal-preview .modal-body .questions').html('');
                            $.each(data.data.questions, function (i, obj) {


                                var html = "<div class='box box-default'><div  class='box-header with-border'>"+(i+1)+"、（" + obj.score + '分）' + obj.name+(obj.type=="问答题"?"":("（"+obj.answer+"）")) +"</div><div class='box-body'>";
                                //;
                                if(obj.type=="问答题"){
                                    html+="<div>参考答案："+obj.answer+"</div>"
                                }
                                $.each(obj.options,function(index,ele){
                                    html+="<div>"+String.fromCharCode((65 + index)) + "、" + ele.content + "</div>"

                                });
                                html+="<div>点评："+obj.comment+"</div>"
                                html+="</div></div>"
                                $('#modal-preview .modal-body .questions').append(html);

                            });
                        }
                        else {
                            $('#modal-preview .modal-body .info').text(data.msg);
                        }
                    }

                })
                $('#modal-preview .modal-body .info').text("");
                $('#modal-preview').modal('show');

            });

            $('.e_remove').on('click', function (e) {
                e.stopPropagation();
                //console.log(this.dataset.id);
                //console.log(this.dataset.title);
                $("#modal-ensure input[name='id']").val(this.dataset.id);
                $('#modal-ensure .modal-body p').html("确认删除<span style='color:red;'>" + this.dataset.name + "</span>?");
                $('#modal-ensure .modal-body .info').text("");
                $('#modal-ensure').modal('show');
            });

            $('.btn-e-del-submit').on('click', function (e) {
                $('.btn-q-del-submit').prop("disabled", true);
                $.ajax({
                    type: 'POST',
                    url: '${request.contextPath}/exam/del',
                    data: $('#eDelForm').serialize(),
                    success: function (data) {
                        $('.btn-q-del-submit').prop("disabled", false);
                        if (data.code == '100') {

                            window.location.reload();
                        } else {
                            $('#modal-ensure .modal-body .info').text(data.msg);
                        }
                    },
                    error: function (e) {
                        $('.btn-q-del-submit').prop("disabled", false);
                        $('#modal-ensure .modal-body .info').text(e);
                    }
                })
            });

        })

    </script>
    </body>
    </html>