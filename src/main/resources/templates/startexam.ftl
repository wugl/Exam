<#include "inc/header1.ftl"/>
<style>
    .icheckbox_minimal-blue, .iradio_minimal-blue {
        margin-right: 10px;
    }

    #timecount {
        color: white;
        z-index: 10000000;
        width: 160px;
        line-height: 30px;

        background-color: red;
        position: fixed;
        top: 40px;
        right: 10px;
        padding: 5px;
        border-radius: 3px;
        box-shadow: 1px 8px 17px #333333;
    }

</style>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="  pull-right" id="timecount">
        已用时间：
        <div class="eclipse"></div>
        还剩时间：
        <div class="left"></div>
        <div class="info"></div>

    </div>
    <!-- Main content -->
    <section class="content container-fluid" id="main-content" style="line-height: 30px;">
        <form id="exam-form">
            <h3 class="name" style="font-weight: bold;text-align: center;">
            <#--${exam.name}-->
            </h3>
            <div class="sub" style="text-align: center;">
            <#--考试时间：${exam.examDate?string('yyyy-MM-dd HH:mm')}，考试时长：${exam.totalTime}分钟，总分：${exam.totalScore}，通过：${exam.passScore}-->
            </div>
            <div class="questions">
            <#--<#list exam.questions as item>-->
            <#--<div class="box box-default" style="line-height: 30px;">-->
                <#--<div class='box-header with-border'>-->
                <#--${item_index+1}、（${item.score}分）${item.name}-->
                <#--</div>-->
                <#--<div class='box-body'>-->
                    <#--<#list item.options as option>-->
                        <#--<div>-->
                        <#--${option_index}、${option.content}-->
                        <#--</div>-->
                    <#--</#list>-->

                <#--</div>-->

            <#--</div>-->
        <#--</#list>-->
            </div>
        </form>
        <div id="msg-success" class="callout callout-info alert-dismissable" style="display: none;">
            <button type="button" class="close" id="ok-close" aria-hidden="true">&times;</button>
            <h4><i class="icon fa fa-info"></i> 提交成功!</h4>
            <p id="msg-success-p"></p>
        </div>
        <div id="msg-error" class="callout callout-warning alert-dismissable" style="display: none;">
            <button type="button" class="close" id="error-close" aria-hidden="true">&times;</button>
            <h4><i class="icon fa fa-warning"></i> 出错了!</h4>
            <p id="msg-error-p"></p>
        </div>
        <button type="button" class="pull-right btn btn-primary btn-e-submit">提交</button>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<div class="template-answer" style="display: none;">
    <script name="answer" type="text/plain"></script>
</div>



<#include "inc/footer1.ftl"/>

<script type="text/javascript">
    $(function () {
        var exam =${exam};

        $('#main-content .name').html(exam.name);
        $('#main-content .sub').html("考试时间：" + new Date(exam.examDate).Format("yyyy-MM-dd hh:mm") + "，考试时长：" + exam.totalTime + '分钟，总分：' + exam.totalScore + "，通过：" + exam.passScore);

        //$('#main-content .questions').html('');

        $.each(exam.questions, function (i, obj) {


            var html = "<div class='box box-default'><div  class='box-header with-border'>" + (i + 1) + "、（" + obj.score + '分）' + obj.name + "</div><div class='box-body'>";
            //;
            if (obj.type == '问答题') {
                var $answer = $('.template-answer').clone();
                $answer.find('script').attr('id', 'answer_' + obj.id);
                $answer.find('script').attr('name', '' + obj.id);
                var myhtml = $answer.html();
                html += myhtml;

            }
            else {
                $.each(obj.options, function (index, ele) {
                    var type = 'radio';
                    //console.log(obj);
                    if (obj.type == '单选题') {
                        type = 'radio';
                    }
                    if (obj.type == '多选题') {
                        type = 'checkbox';
                    }
                    html += "<div><input type='" + type + "' style='margin-right: 10px;'  name='" + obj.id + "' value='" + String.fromCharCode((65 + index)) + "'>" + String.fromCharCode((65 + index)) + "、" + ele.content + "</div>"

                });
            }
            //html+="<input type=hidden value='"+exam.id+"' name='examId'>";
            html += "</div></div>"
            $('#main-content .questions').append(html);


        });
        //$('#main-content .questions').after("<input type=hidden value='"+exam.id+"' name='examId'>");

        var textAnswer = [];
        $("script[id^='answer_']").each(function (i, e) {
            //console.log(i);
            var id = $(this).attr('id');
            textAnswer[i] = UE.getEditor(id);

        });
        $('input').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue',
            increaseArea: '20%' // optional
        });

        var totalTime = exam.totalTime * 60;

        function formatTime(time) {
            var hour = parseInt(time / 3600);
            var minute = parseInt((time % 3600) / 60);
            var second = time % 60;
            var result = '';
            if (hour > 0) result += (hour + '小时');
            if (minute > 0 || hour > 0) result += (minute + '分钟');
            result += (second + '秒');
            return result;

        }

        //console.log(totalTime);

        var pass = 0;
        var timeInterval = setInterval(function () {
            totalTime--;
            pass++;
            if (totalTime == 0) {
                clearInterval(timeInterval);
                $('.btn-e-submit').trigger('click');
                $('#timecount .info').html('时间已到，自动提交！');
            }
            $('#timecount .left').html(formatTime(totalTime))
            $('#timecount .eclipse').html(formatTime(pass))
            //console.log(formatTime());

        }, 1000);

        var questionIds = [];
        var answers = {};

        $('.btn-e-submit').on('click', function () {
            $(exam.questions).each(function (i, e) {
                if (e.type == '单选题' || e.type == '多选题') {
                    questionIds.push(e.id);
                }
                answers[e.id] = '';
            });
            //console.log(answers);
            var isValidate = true;
            $.each(questionIds, function (i, e) {
                //console.log($('input[name="'+e+'"]:checked').val())
                if (!$('input[name="' + e + '"]:checked').val()) {
                    isValidate = false;
                }

            });
            $.each(textAnswer, function (i, e) {
                //console.log(e.getContent());
                if (e.getContent() == '')
                    isValidate = false;

            });
            console.log(isValidate);
            if (!isValidate && totalTime>0) {
                $("#msg-error").hide(10);
                $("#msg-error").show(100);
                $("#msg-error-p").html("还有题没有做完！");
                return;
            }
            var values = $('#exam-form').serialize().split('&');
            $.each(values, function (i, e) {
                //console.log(e);
                var qId = e.split("=")[0];
                var qValue = e.split("=")[1];
                if (answers[qId] == '') {
                    answers[qId] = qValue;
                } else {
                    answers[qId] += ('|' + qValue);
                }
            });
            console.log(answers);


            $('.btn-e-submit').prop("disabled", true);
            $.ajax({
                url: '/examsubmit',
                method: 'POST',
                data: 'answer=' + JSON.stringify(answers) + '&examId=' + exam.id,
                success: function (data) {
                    $('.btn-e-submit').prop("disabled", false);
                    console.log(data);
                    console.log(data.msg);
                    data = JSON.parse(data);
                    if (data.code == '100') {
                        $("#msg-error").hide(100);
                        $("#msg-success").show(100);
                        $("#msg-success-p").html(data.msg);

                        setTimeout(function(){
                            location.href='/';
                        },2000);
                    } else {
                        $("#msg-success").hide(10);
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

            });
            //    console.log($('#exam-form').serialize());
        })

    })
</script>
</body>
</html>