<#include "inc/header1.ftl"/>
<style>
    .icheckbox_minimal-blue, .iradio_minimal-blue {
        margin-right: 10px;
    }
</style>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->

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
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->



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
            $.each(obj.options, function (index, ele) {
                var type='radio';
                //console.log(obj);
                if(obj.type=='单选题'){
                    type='radio';
                }
                if(obj.type=='多选题'){
                    type='checkbox';
                }
                html += "<div><input type='"+type+"' style='margin-right: 10px;'  name='q_" + obj.id + "' value='" + String.fromCharCode((65 + index)) + "'>" + String.fromCharCode((65 + index)) + "、" + ele.content + "</div>"

            });

            html += "</div></div>"
            $('#main-content .questions').append(html);

        });

        $('input').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue',
            increaseArea: '20%' // optional
        });

    })
</script>
</body>
</html>