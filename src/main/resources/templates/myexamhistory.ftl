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
            <li class="active">${title!""}</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

        <table id="example1" class="table table-bordered table-striped  table-hover " cellspacing="0"
               width="100%">
            <thead>
            <tr>
                <th>id</th>
                <th>名称</th>
                <th>考试日期</th>
                <th>考试时长</th>
                <th>总分数</th>
                <th>及格分数</th>

                <th>答题人</th>


                <th>答题日期</th>
                <th>得分</th>
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
                    <td>${key.examDate?string('yyyy-MM-dd HH:mm')}</td>
                    <td>${key.totalTime}</td>
                    <td>${key.totalScore}</td>
                    <td>${key.passScore}</td>

                        <th>${key.answerName}</th>

                    <td>${key.answerDate?string('yyyy-MM-dd HH:mm')}</td>
                    <td>${key.studentScore}

                        <#--<a class="btn-sm q_edit" data-id="${key.id}" target="_blank" href="/startexam?examId=${key.id}"><span class="glyphicon glyphicon-pencil"></span> 开始答题</a>-->

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
                <th>考试时长</th>
                <th>总分数</th>
                <th>及格分数</th>

                <th>答题人</th>

                <th>答题日期</th>
                <th>得分</th>

            </tr>
            </tfoot>
        </table>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->
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
                <h2 class="myscore text-red" style="text-align: center;"></h2>
                <div class="answerDate " style="text-align: center;"></div>
                <div class="questions"></div>


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

        var exams = ${examsJson};
        var table = $('#example1').DataTable({
            "stateSave": true,
            "language": {
                "url": " /dataTables.Chinese.lang.json"
                // "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Chinese.json"
            }
        });


        $('.row-item').on('click', function (e) {
            var id = this.dataset.id;
            console.log('row');
            var exam;
            $.each(exams, function (i, e) {
                console.log(e);
                if (e.id == id) {
                }
                exam = e;

            });


            $('#modal-preview .modal-body .name').html(exam.name);
            $('#modal-preview .modal-body .sub').html("考试时间：" + new Date(exam.examDate).Format("yyyy-MM-dd hh:mm") + "，考试时长：" + exam.totalTime + '分钟，总分：' + exam.totalScore + "，通过：" + exam.passScore);
//                            $('#modal-preview .modal-body .answer').html("答案：" + data.data.question.answer);
//                            $('#modal-preview .modal-body .comment').html("点评：" + data.data.question.comment);
//                            var answers = data.data.question.answer.split('|');
//                            //console.log(answers.length);

            $('#modal-preview .modal-body .myscore').html('总得分：' + exam.studentScore);
            $('#modal-preview .modal-body .answerDate').html('答题人：'+exam.answerName+'， 答题时间：' + new Date(exam.answerDate).Format("yyyy-MM-dd hh:mm") + '');
            $('#modal-preview .modal-body .questions').html('');
            $.each(exam.questions, function (i, obj) {


                var html = "<div class='box box-default'><div  class='box-header with-border'>" + (i + 1) + "、（" + obj.score + '分）' + obj.name + (obj.type == "问答题" ? "" : ("（" + obj.answer + "）")) + "</div><div class='box-body'>";
                //;
                if (obj.type == "问答题") {
                    html += "<div>参考答案：" + obj.answer + "</div>"
                }

                $.each(obj.options, function (index, ele) {
                    html += "<div>" + String.fromCharCode((65 + index)) + "、" + ele.content + "</div>"

                });
                html += '<div>我的答案： ' + obj.studentAnswer + '</div>',
                        html += '<div>点评：' + obj.comment + '</div>',

                        html += "</div></div>"
                $('#modal-preview .modal-body .questions').append(html);
            });


            $('#modal-preview .modal-body .info').text("");
            $('#modal-preview').modal('show');

        });
    })
</script>
</body>
</html>