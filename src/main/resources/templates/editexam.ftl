<#include "inc/header1.ftl"/>
<#include "inc/topnav1.ftl"/>
<#include "inc/leftsidebar1.ftl"/>
<style>
    .select, .details-control {
        width: 30px;
        cursor: pointer;
    }
</style>

<div class="content-wrapper">

    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
        ${title!""}
            <#--<small>Optional description</small>-->
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i> 首页</a></li>
            <li><a href="/exam">试卷管理</a></li>
            <li class="active">${title!""}</li>
        </ol>
    </section>
    <section class="content container-fluid">

        <div class="box">
            <div class="box-header">
            <#--<h3 class="box-title">${title!""}</h3>-->
            </div>
            <div class="box-body">
                <form id="eForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="e_name" class="col-sm-2 control-label">标题:</label>
                        <div class="col-sm-10">
                            <input type="text" name="name" class="form-control" placeholder="标题" id="e_name"
                                   value="<#if exam??>${exam.name}</#if>">

                            <input type="hidden" name="id" class="form-control" placeholder="" id="e_id"
                                   value="<#if exam??>${exam.id}</#if>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="e_exam_date" class="col-sm-2 control-label">考试日期:</label>
                        <div class="col-sm-10">
                            <input type="text" name="examDate" class="form-control" placeholder="考试日期" id="e_exam_date"
                                   value="<#if exam??>${exam.examDate?string('yyyy-MM-dd HH:mm')}</#if>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="e_exam_time" class="col-sm-2 control-label">考试时长:</label>
                        <div class="col-sm-10">
                            <input type="number" name="totalTime" class="form-control" placeholder="考试时长(分钟)"
                                   id="e_exam_time" value="<#if exam??>${exam.totalTime}</#if>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="e_total_score" class="col-sm-2 control-label">总分数:</label>
                        <div class="col-sm-10">
                            <input type="number" name="totalScore" class="form-control" placeholder="总分数"
                                   id="e_total_score" value="<#if exam??>${exam.totalScore}</#if>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="e_pass_score" class="col-sm-2 control-label">及格分数:</label>
                        <div class="col-sm-10">
                            <input type="number" name="passScore" class="form-control" placeholder="及格分数"
                                   id="e_pass_score" value="<#if exam??>${exam.passScore}</#if>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">已添加的题目:</label>
                        <div class="col-sm-10">
                            <table id="addedQuestion" class="table table-bordered table-striped  table-hover"
                                   width="100%"></table>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"> </label>
                        <div class="col-sm-10">
                            <a class="btn btn-info margin " id="btn_add_question"><i class="fa fa-plus"></i>增减题目</a>
                        </div>
                    </div>

                </form>
                <div id="msg-success" class="callout callout-info alert-dismissable" style="display: none;">
                    <button type="button" class="close" id="ok-close" aria-hidden="true">&times;</button>
                    <h4><i class="icon fa fa-info"></i> <#if type=="1">添加<#elseif type=="2">编辑</#if>成功!</h4>
                    <p id="msg-success-p"></p>
                </div>
                <div id="msg-error" class="callout callout-warning alert-dismissable" style="display: none;">
                    <button type="button" class="close" id="error-close" aria-hidden="true">&times;</button>
                    <h4><i class="icon fa fa-warning"></i> 出错了!</h4>
                    <p id="msg-error-p"></p>
                </div>

                <button type="button" class="btn btn-primary btn-e-add-submit pull-right">确认</button>
            </div>
        </div>
    </section>
    <!-- /.content -->
</div>


<div class="modal  fade" id="question-list">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-name">题目列表</h4>
            </div>
            <div class="modal-body">


                <table id="example1" class="table table-bordered table-striped  table-hover " cellspacing="0"
                       width="100%">
                    <thead>
                    <tr>
                        <th>选择</th>
                        <th>id</th>

                        <th>类型</th>
                        <th>标题</th>
                        <th></th>

                    </tr>
                    </thead>

                    <tfoot>
                    <tr>
                        <th>选择</th>
                        <th>id</th>
                        <th>类型</th>
                        <th>标题</th>
                        <th></th>

                    </tr>
                    </tfoot>
                </table>
            </div>


            <div class="modal-footer">
            <#--<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>-->
                <button type="button" class="btn btn-primary btn-e-add-question-submit" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<#include "inc/footer1.ftl"/>
<script>
    $(function () {
        //$('body').removeClass("sidebar-collapse").addClass("layout-boxed");


        var selectedData = ${questions!"[]"};
        var type =${type};
        var questionList = $('#example1').on('init.dt', function () {
            console.log("initailze complete");
            $('input').iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%' // optional
            });
        }).DataTable({
            "stateSave": true,
            "language": {
                "url": " /dataTables.Chinese.lang.json"
                // "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Chinese.json"
            },
//            "processing": true,
//            "serverSide": true,
            "ajax": "/question/getAll",
            "columns": [
                {
                    "data": "id",
                    "className": "select",
                    "render": function (data, type, row, meta) {
                        return '<div class="checkbox icheck"><label><input value=' + data + ' type="checkbox" /></label></div>';
                    },
                    // "defaultContent": '<div class="checkbox icheck"><label><input type="checkbox" /></label></div>',
                    "orderable": false
                },
                {"data": "id"},
                {"data": "type"},
                {"data": "title"},
                {
                    "className": 'details-control',
                    "orderable": false,
                    "data": null,
                    "defaultContent": '详情'
                }
            ],
            "createdRow": function (row, data, index) {
                //  console.log(data);
                // console.log(row);
                if (selectedData.length > 0)
                    $.each(selectedData, function (i, e) {
                        if (e.id == data.id) {
                            $(row).find('input').iCheck('check');
                        }
                    });


            }
        });


        var originalData;
        //console.log(originalData);
//        $.ajax({
//            method: 'GET',
//            url: '/question/getAll',
//            success: function (data) {
//                //console.log(data);
//                originalData = data.data;
//            }
//        });

        var secondTable;

        var addedQuestions = function () {
            if (secondTable) secondTable.destroy();
            secondTable = $('#addedQuestion').DataTable({
                "language": {
                    "url": " /dataTables.Chinese.lang.json"
                    // "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Chinese.json"
                },
                "data": selectedData,
                "paging": true,
                "searching": true,
                "autoWidth": true,
                "columns": [
                    {"data": "id", title: "id"},
                    {"data": "type", title: "类型"},
                    {"data": "title", title: "标题"},
                    {
                        "className": 'details-control',
                        "orderable": false,
                        "data": null,
                        "defaultContent": '详情'
                    }

                ]
            });
        };

        $('#example1').on('click', 'td.details-control', function (e) {
            toggleDetail(this, questionList);

        });

        function toggleDetail(elem, table) {
            var tr = $(elem).closest('tr');
            var row = table.row(tr);

            // console.log(row.data());

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                // Open this row
                row.child(format(row.data())).show();
                tr.addClass('shown');
            }
        }

        $('#addedQuestion').on('click', 'td.details-control', function (e) {
            toggleDetail(this, secondTable);

        });

        function format(d) {


            var result = ' <div class="box box-default" style="line-height: 30px;">' +
                    '<div class="box-header with-border">' +
                    d.name + '（' + d.score + '分）' +
                    '</div>' +
                    ' <div class="box-body">';
            $.each(d.options, function (i, e) {
                result += '<div>' + String.fromCharCode((65 + i)) + "、" + e.content + '</div>';
                //console.log(e);
            });
            result += '<div class="text-red">答案：' + d.answer + '</div>'
            result += '<div  >点评：' + d.comment + '</div>'
            result += '</div>' +

                    '</div>';
            return result;
        }

//http://www.51xuediannao.com/js/jquery/icheck.html
        $('#example1 ').on('ifClicked', ' [type="checkbox"]', function (e) {

            //console.log(e.target.checked);
            var checked = e.target.checked;
            console.log(checked);
            //$(this).toggleClass('selected');
            //console.log('tr');
            //var that = this;
            var id = $(this).val();

            $.each(originalData, function (i, e) {
                console.log(id);
                if (e.id == id) {
                    if (!checked) {
                        selectedData.push(e);
                        //console.log(addedQuestions)

                    } else {
                        selectedData = selectedData.filter(function (e, i) {
                            return e.id != id;

                        });

                    }

                    addedQuestions();

                }

            });
            console.log(selectedData);
        });


        if (selectedData.length > 0) {
            addedQuestions();
            //console.log(selectedData);
        }
        $('#e_exam_date').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1,
            startDate: new Date().Format("yyyy-MM-dd hh:mm:ss")
        });
        $('#btn_add_question').on('click', function () {
            //console.log(questionList.data());
            originalData = questionList.data();
            //console.log(originalData);

            $('#question-list').modal('show');


        });

        $("#ok-close").click(function () {
            $("#msg-success").hide(100);
        });
        $("#error-close").click(function () {
            $("#msg-error").hide(100);
        });


        $('.btn-e-add-submit').on('click', function (e) {


            var isValidate = true;
            var errorMsg = '';
            var currentTotalScore = 0;
            $.each(selectedData, function (i, e) {
                //console.log(e);
                currentTotalScore += e.score;

            });


            if ($('#e_name').val() == '' || $('#e_exam_date').val() == '') {
                isValidate = false;
                errorMsg = '请填写标题和考试日期';
            }
            else if (!checkNum($('#e_exam_time').val()) || !checkNum($('#e_total_score').val()) || !checkNum($('#e_pass_score').val())) {
                isValidate = false;
                errorMsg = '请输入数字';
            }
            else if (selectedData.length == 0) {
                isValidate = false;
                errorMsg = '请选择题目';
            } else if (currentTotalScore != $('#e_total_score').val()) {
                isValidate = false;
                errorMsg = '总分值不对';
            } else if ($('#e_total_score').val() < $('#e_pass_score').val()) {
                isValidate = false;
                errorMsg = '总分值要大于通过分数';
            }


            if (!isValidate) {

                $("#msg-error").hide(10);
                $("#msg-error").show(100);
                $("#msg-error-p").html(errorMsg);
                return;
            }


            var url, data;


            if (type == 1) {
                url = '/exam/add';

            }

            if (type == 2) {
                url = "/exam/update";

            }
            // $('#qt_id').val('111');


            var qs = $.map(selectedData, function (e, i) {
                return e.id;
            }).join('|');
            console.log(qs);
            //return;
            $('.btn-e-add-submit').prop("disabled", true);


            //return;

            console.log($('#eForm').serialize());
            $.ajax({
                type: 'POST',
                url: url,
                data: $('#eForm').serialize() + '&questions=' + qs,
                success: function (data) {
                    console.log(data);
                    $('.btn-e-add-submit').prop("disabled", false);
                    if (data.code == '100') {
                        //window.location.reload();
                        $("#msg-error").hide(100);
                        $("#msg-success").show(100);
                        $("#msg-success-p").html(data.msg);
                    }
                    else {
                        $("#msg-error").hide(10);
                        $("#msg-error").show(100);
                        $("#msg-error-p").html(data.msg);
                    }
                },
                error: function (e) {
                    $('.btn-e-submit').prop("disabled", false);
                    $("#msg-error").hide(10);
                    $("#msg-error").show(100);
                    $("#msg-error-p").html(e);

                }
            })
        });
    });
</script>

</body>
</html>