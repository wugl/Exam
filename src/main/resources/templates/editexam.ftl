<#include "inc/header1.ftl"/>
<#include "inc/topnav1.ftl"/>
<#include "inc/leftsidebar1.ftl"/>

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
        <div id="msg-success" class="callout callout-info alert-dismissable" style="display: none;">
            <button type="button" class="close" id="ok-close" aria-hidden="true">&times;</button>
            <h4><i class="icon fa fa-info"></i> 添加成功!</h4>
            <p id="msg-success-p"></p>
        </div>
        <div id="msg-error" class="callout callout-warning alert-dismissable" style="display: none;">
            <button type="button" class="close" id="error-close" aria-hidden="true">&times;</button>
            <h4><i class="icon fa fa-warning"></i> 出错了!</h4>
            <p id="msg-error-p"></p>
        </div>
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

                            <input type="hidden" name="id" class="form-control" placeholder="" id="e_id"  value="<#if exam??>${exam.id}</#if>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="e_exam_date" class="col-sm-2 control-label">考试日期:</label>
                        <div class="col-sm-10">
                            <input type="text" name="date" class="form-control" placeholder="考试日期" id="e_exam_date"  value="<#if exam??>${exam.examDate}</#if>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="e_exam_time" class="col-sm-2 control-label">考试时长:</label>
                        <div class="col-sm-10">
                            <input type="number" name="time" class="form-control" placeholder="考试时长(分)"
                                   id="e_exam_time"  value="<#if exam??>${exam.totalTime}</#if>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="e_total_score" class="col-sm-2 control-label">总分数:</label>
                        <div class="col-sm-10">
                            <input type="number" name="totalscore" class="form-control" placeholder="总分数"
                                   id="e_total_score"  value="<#if exam??>${exam.totalScore}</#if>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="e_pass_score" class="col-sm-2 control-label">及格分数:</label>
                        <div class="col-sm-10">
                            <input type="number" name="passscore" class="form-control" placeholder="及格分数"
                                   id="e_pass_score"  value="<#if exam??>${exam.passScore}</#if>">
                        </div>
                    </div>


                    <a class="btn btn-info margin " id="btn_new_exam"><i class="fa fa-plus"></i>新增试题</a>

                </form>

                <button type="button" class="btn btn-primary btn-e-add-submit pull-right">确认</button>
            </div>
        </div>
    </section>
    <!-- /.content -->
</div>


<#include "inc/footer1.ftl"/>
<script>
    $(function () {
        //$('body').removeClass("sidebar-collapse").addClass("layout-boxed");

        var type =${type};


        $('#e_exam_date').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1
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


//
//        if(!checkNum($('#q_score').val()) || $('#q_score').val()>100){
//            isValidate=false;
//            errorMsg='分数请填写数字，不能大于100';
//        }
//        if ($('#q_title').val() == '' || name.getContent() == '' ){
//            isValidate=false;
//            errorMsg='请填写标题和内容';
//        }

            if (!isValidate) {
                $('#modal-edit .modal-body .info').text(errorMsg);
                return;
            }


            var url, data;
//        var fname = name.getContent().replace(/\+/g, "%2B").replace(/\&/g,"%26");
//        var fcomment = comment.getContent().replace(/\+/g, "%2B").replace(/\&/g,"%26");

            if (type == 1) {
                url = '/exam/add';
                //data = data.replace(/\&/g,"%26");
//            data = 'title=' + $('#q_title').val() + '&comment=' + fcomment  + '&name=' + fname + '&type=' + $('#questionType').val()
//                    +'&optionsContent='+optionsContent+'&answer='+optionAnswer+'&score='+$('#q_score').val();
            }

            if (type == 2) {
                url = "/exam/update";
//            data = 'title=' + $('#q_title').val() + '&comment=' + fcomment  + '&name=' + fname + '&type=' + $('#questionType').val() + '&id=' + $('#q_id').val()+
//                    '&optionsContent='+optionsContent+'&answer='+optionAnswer+'&score='+$('#q_score').val();
            }
            // $('#qt_id').val('111');

            //return;
            $('.btn-e-add-submit').prop("disabled", true);
            $.ajax({
                type: 'POST',
                url: url,
                data: $('#eForm').serialize(),
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
                }
            })
        });
    });
</script>

</body>
</html>