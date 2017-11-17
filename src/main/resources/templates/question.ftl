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
            <li class="active">${title!""}</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

        <div class="box">
            <div class="box-header">
                <a class="btn btn-info pull-left margin q_new"><i class="fa fa-plus"></i>新增</a>
            <#--<a href="/questionType/getExcel" class="btn btn-info  margin"><i class="fa fa-fw fa-file-excel-o"></i>导出excel</a>-->
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <table id="example1" class="table table-bordered table-striped  table-hover">
                    <thead>
                    <tr>
                        <th style="width: 30px;">id</th>
                        <th>类型</th>
                        <th>标题</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#if questions?exists>
                        <#list questions as key>
                        <tr class="row-item" data-id="${key.id}">
                            <td>${key.id}</td>
                            <td>

                                <#if questionTypes?exists>
                                    <#list questionTypes as type>
                                    <#if type.id==key.typeId>${type.name}</#if>
                                </#list>
                                </#if>

                            </td>
                            <td>${key.title}</td>
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
                        <th>类型</th>
                        <th>标题</th>
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
                <form id="qDelForm">
                    <div class="form-group  ">

                        <input type="hidden" name="id" class="form-control" placeholder="" id="">
                    </div>
                </form>
                <p style="text-align: center;"></p>
                <div class="info text-red"></div>
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
                <form id="qForm">
                    <div class="form-group">
                        <label for="q_title" class="control-label">标题:</label>
                        <input type="text" name="title" class="form-control" placeholder="标题" id="q_title">

                        <input type="hidden" name="id" class="form-control" placeholder="" id="q_id">
                    </div>
                    <div class="form-group">
                        <label for="q_score" class="control-label">分数:</label>
                        <input type="number" name="score" class="form-control" placeholder="分数" id="q_score">

                    </div>
                    <div class="form-group  ">
                        <label for="q_name" class="control-label">内容:</label>

                        <script id="namecontainer" name="name" type="text/plain">内容</script>

                    </div>
                    <div class="form-group ">
                        <label for="questionType" class="control-label">类型:</label>
                        <select class="form-control" name="questionType" id="questionType">
                        <#if questionTypes?exists>
                            <#list questionTypes as key>
                                <option value="${key.id}">${key.name}</option>
                            </#list>
                        </#if>

                        </select>
                    </div>
                    <a class="btn btn-info margin" id="btn_new_option"><i class="fa fa-plus"></i>新增选项</a>
                    <div class="form-group  ">
                        <label for="q_name" class="control-label">点评:</label>
                        <script id="commentcontainer" name="comment" type="text/plain">点评</script>
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
                <div class="answer text-red"></div>
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

<script id="optionTemplate" type="text/x-jsrender">

                     <div class="form-group option-group">
                        <label  class="control-label">选项{{:name}}:</label>
                        <div>
                            <div class="input-group" style="padding-left:0;">
                                <span class="input-group-addon">
                                {{if type==1}}
                                    <input   type="radio"  name="option" value="{{:name}}">
                                {{else type==2}}
                                    <input type="checkbox"   name="option" value="{{:name}}">
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

<div class="template-answer" style="display: none;">
    <div class="form-group answer-group">
        <label class="control-label">答题:</label>
        <script name="answer" type="text/plain"></script>
    </div>
</div>


<#include "inc/footer1.ftl"/>

<script type="text/javascript">
    $(function () {

        var name = UE.getEditor('namecontainer');
        var comment = UE.getEditor('commentcontainer');
        var index = 0;
        var tmpl = $.templates("#optionTemplate");
        var qType = 1;
        $('#modal-edit').on('click', '#btn_new_option', function (e) {


            var html = tmpl.render({name: String.fromCharCode((65 + index)), type: qType, value: ""});//1,单选，2，多选
            //console.log(html);
            index++;
            $(this).before(html);
            $('input').iCheck({
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue',
                increaseArea: '20%' // optional
            });


        });

        var answerContainer;
        $('#questionType').on('change', function (e) {
            index = 0;
            //console.log(answerContainer);
            if (answerContainer) {
                $('#qForm .answer-group').hide();
                //answerContainer.destroy();
                console.log(answerContainer);
            }
            $('#qForm .option-group').remove();
            $('#btn_new_option').hide();
            //console.log($(this).val());
            //console.log($(this).find("option:selected").text());
            if ($(this).find("option:selected").text() == '单选题') {
                $('#btn_new_option').show();
                qType = 1;
            }
            if ($(this).find("option:selected").text() == '多选题') {
                $('#btn_new_option').show();
                qType = 2;
            }
            if ($(this).find("option:selected").text() == '问答题') {
                qType = 3;
                if (answerContainer) {
                    $('#qForm .answer-group').show();
                } else {
                    var $answer = $('.template-answer').clone();
                    $answer.find('script').attr('id', 'answercontainer');
                    var myhtml = $answer.html();
                    $('#btn_new_option').after(myhtml);
                    answerContainer = UE.getEditor('answercontainer');
                }

            }

        });
        $('#modal-edit').on('click', '.remove_option', function (e) {
            e.preventDefault();
            $(this).parent().parent().parent().parent().remove();

            $('#modal-edit .option-group').each(function (i, e) {
                $(this).find('.control-label').text("选项" + (i + 1) + ":");
                $(this).find('.option-name').attr("name", "option" + (i + 1));

                //console.log(i + ":" + e)

            });
            index--;

        });

        var type;//1,新增题目2,编辑题目

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
            $('#q_title').val('');
            $('#q_id').val('');
            $('#modal-edit .modal-name').text("新增题目");
            $('#modal-edit .modal-body .info').text("");
            $('#modal-edit .option-group').remove();
            comment.setContent('');
            name.setContent('');
            $('#q_score').val('');
            $('#modal-edit').modal('show');


        });

        $('.row-item').on('click', function (e) {
            //console.log('row');

            $.ajax({
                type: 'GET',
                url: '/question/getById',
                data: 'id=' + this.dataset.id,
                success: function (data) {
                    console.log(data);
                    if (data.code == '100') {
                        $('#modal-preview .modal-body .name').html('（' + data.data.question.score + '分）' + data.data.question.name);
                        $('#modal-preview .modal-body .title').html(data.data.question.title);
                        $('#modal-preview .modal-body .answer').html("答案：" + data.data.question.answer);
                        $('#modal-preview .modal-body .comment').html("点评：" + data.data.question.comment);
                        var answers = data.data.question.answer.split('|');
                        //console.log(answers.length);
                        if (data.data.question.type == '问答题') {
                            $('#modal-preview .modal-body .options').hide();
                        }
                        //if (data.data.question=='单选题' || data.data.question=='多选题')
                        else {
                            $('#modal-preview .modal-body .options').show();
                            $('#modal-preview .modal-body .options').html('');
                            $.each(data.data.options, function (i, obj) {

                                var html = "<div>" + String.fromCharCode((65 + i)) + "、" + obj.content + "</div>";
                                $('#modal-preview .modal-body .options').append(html);

                            });
                        }
                    }
                    else {
                        $('#modal-preview .modal-body .info').text(data.msg);
                    }
                }

            })
            $('#modal-preview .modal-body .info').text("");
            $('#modal-preview').modal('show');

        });
        $('.q_edit').on('click', function (e) {
            console.log('edit');
            type = 2;
            //console.log(this.dataset.id);
            e.stopPropagation();

            $('#modal-edit .modal-name').text("编辑题目");
//            $('#q_name').val(this.dataset.name);
//            $('#q_id').val(this.dataset.id);
//            $('#questionType').val(this.dataset.type);
            $.ajax({
                type: 'GET',
                url: '/question/getById',
                data: 'id=' + this.dataset.id,
                success: function (data) {
                    //console.log(data);
                    if (data.code == '100') {
                        name.setContent(data.data.question.name);
                        $('#q_title').val(data.data.question.title);
                        $('#q_id').val(data.data.question.id);
                        $('#q_score').val(data.data.question.score);
                        $('#questionType').val(data.data.question.typeId);
                        comment.setContent(data.data.question.comment);
                        //console.log(data.data.question.answer);

                        //console.log(answers.length);
                        $('#qForm .option-group').remove();
                        if (data.data.question.type == '单选题') qType = 1;
                        if (data.data.question.type == '多选题') qType = 2;
                        if (data.data.question.type == '问答题') qType = 3;
                        if (qType == 1 || qType == 2) {
                            $('#btn_new_option').show();
                            $('#qForm .answer-group').hide();
                            var answers = data.data.question.answer.split('|');
                            $.each(data.data.options, function (i, obj) {

                                var html = tmpl.render({
                                    name: String.fromCharCode((65 + i)),
                                    type: qType,
                                    value: obj.content
                                });
                                $('#btn_new_option').before(html);

                                var isChecked = $.inArray(String.fromCharCode((65 + i)), answers) > -1;
                                if (isChecked) {
                                    //console.log($('#btn_new_option').prev().find("input[name='option']"));
                                    $('#btn_new_option').prev().find("input[name='option']").attr('checked', 'true');
                                }
                                $('input').iCheck({
                                    checkboxClass: 'icheckbox_minimal-blue',
                                    radioClass: 'iradio_minimal-blue',
                                    increaseArea: '20%' // optional
                                });
                            })
                        }
                        if (qType == 3) {
                            $('#btn_new_option').hide();
                            //console.log(answerContainer);

                            if (!answerContainer) {
                                var $answer = $('.template-answer').clone();
                                $answer.find('script').attr('id', 'answercontainer');
                                var myhtml = $answer.html();
                                $('#btn_new_option').after(myhtml);
                                answerContainer = UE.getEditor('answercontainer');
                            }
                            $('#qForm .answer-group').show();
                            //console.log(answerContainer);
                            answerContainer.setContent(data.data.question.answer);
                        }
                    }
                    else {
                        $('#modal-edit .modal-body .info').text(data.msg);
                    }
                }

            })
            $('#modal-edit .modal-body .info').text("");
            $('#modal-edit').modal('show');


        });
        $('.q_remove').on('click', function (e) {
            e.stopPropagation();
            //console.log(this.dataset.id);
            //console.log(this.dataset.title);
            $("#modal-ensure input[name='id']").val(this.dataset.id);
            $('#modal-ensure .modal-body p').html("确认删除<span style='color:red;'>" + this.dataset.title + "</span>?");
            $('#modal-ensure .modal-body .info').text("");
            $('#modal-ensure').modal('show');
        });
        $('.btn-q-add-submit').on('click', function (e) {

            var $options = $('#qForm .option-name');
            var optionsContent = '';
            var isValidate = true;
            var errorMsg = '';
            $options.each(function (i, e) {
                if ($(this).val() == '') {

                    isValidate = false;
                    errorMsg = '选项名称不能为空';
                    return;
                }
                if (i == $options.length - 1)
                    optionsContent += $(this).val();
                else
                    optionsContent += $(this).val() + '|||';


            });
            if ($options.length > 0 && $('#qForm [name="option"]:checked').length == 0) {
                isValidate = false;
                errorMsg = '请勾选正确答案';
            }


            //console.log($('#qForm [name="option"]:checked')[0]);
            //return;
            if (qType == 3 && answerContainer.getContent() == '') {
                isValidate = false;
                errorMsg = '请填写答案';
            }

            if (!checkNum($('#q_score').val()) || $('#q_score').val() > 100) {
                isValidate = false;
                errorMsg = '分数请填写数字，不能大于100';
            }
            if ($('#q_title').val() == '' || name.getContent() == '') {
                isValidate = false;
                errorMsg = '请填写标题和内容';
            }

            if (!isValidate) {
                $('#modal-edit .modal-body .info').text(errorMsg);
                return;
            }
            var optionAnswer = '';
            $('#qForm [name="option"]:checked').each(function (i, e) {
                var optionItem = $(this).val();
                if (i == $('#qForm [name="option"]:checked').length - 1)
                    optionAnswer += $(this).val();
                else
                    optionAnswer += $(this).val() + '|';

            });
            console.log(optionsContent);
            console.log(optionAnswer);
            console.log(type);

            var url, data;
            var fname = name.getContent().replace(/\+/g, "%2B").replace(/\&/g, "%26");
            var fcomment = comment.getContent().replace(/\+/g, "%2B").replace(/\&/g, "%26");
            var fanswer = answerContainer.getContent().replace(/\+/g, "%2B").replace(/\&/g, "%26");

            if (type == 1) {
                url = '/question/add';
                //data = data.replace(/\&/g,"%26");
                data = 'title=' + $('#q_title').val() + '&comment=' + fcomment + '&name=' + fname + '&type=' + $('#questionType').val()
                        + '&optionsContent=' + optionsContent + '&answer=' + (qType == 3 ? fanswer : optionAnswer) + '&score=' + $('#q_score').val();
            }

            if (type == 2) {
                url = "/question/update";
                data = 'title=' + $('#q_title').val() + '&comment=' + fcomment + '&name=' + fname + '&type=' + $('#questionType').val() + '&id=' + $('#q_id').val() +
                        '&optionsContent=' + optionsContent + '&answer=' + (qType == 3 ? fanswer : optionAnswer) + '&score=' + $('#q_score').val();
            }
            // $('#qt_id').val('111');
            console.log(data);
            //return;
            $('.btn-q-add-submit').prop("disabled", true);
            $.ajax({
                type: 'POST',
                url: url,
                data: data,
                success: function (data) {
                    console.log(data);
                    $('.btn-q-add-submit').prop("disabled", false);
                    if (data.code == '100') {
                        window.location.reload();
                    }
                    else {
                        $('#modal-edit .modal-body .info').text(data.msg);
                    }
                },
                error: function (e) {
                    $('.btn-q-add-submit').prop("disabled", false);
                    $('#modal-edit .modal-body .info').text(e);
                }
            })
        });

        $('.btn-q-del-submit').on('click', function (e) {
            $('.btn-q-del-submit').prop("disabled", true);
            $.ajax({
                type: 'POST',
                url: '/question/del',
                data: $('#qDelForm').serialize(),
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
        })

    })
</script>
</body>
</html>