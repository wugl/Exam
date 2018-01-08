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
            <li class="active">${title!""}</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">
        <div class="box">
            <div class="box-header">
            <#--<a class="btn btn-info pull-left margin qt_new"><i class="fa fa-plus"></i>新增</a>-->
                <#--<a href="${request.contextPath}/exam/getExcel" class="btn btn-info  margin"><i-->
                        <#--class="fa fa-fw fa-file-excel-o"></i>导出excel</a>-->
            </div>
            <div class="box-body">
                <table id="example1" class="table table-bordered table-striped  table-hover " cellspacing="0"
                       width="100%">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>测评日期</th>
                        <th>测评时长</th>
                        <th>总分数</th>
                        <th>及格分数</th>
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
                            <td>${key.examDate?string('yyyy-MM-dd HH:mm')}</td>
                            <td>${key.totalTime}</td>
                            <td>${key.totalScore}</td>
                            <td>${key.passScore}</td>
                            <td>


                                <a class="btn-sm q_edit" data-id="${key.id}" target="_blank"
                                   href="${request.contextPath}/startexam?examId=${key.id}"><span
                                        class="glyphicon glyphicon-pencil"></span> 开始答题</a>
                            <#--<a class="btn-sm q_remove" data-id="${key.id}" data-type="${key.typeId}"-->
                            <#--data-title="${key.title}""><i-->
                            <#--class="fa fa-remove"></i></a>-->
                            </td>
                        </tr>
                        </#list>
                    </#if>


                    </tbody>
                    <tfoot>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>测评日期</th>
                        <th>测评时长</th>
                        <th>总分数</th>
                        <th>及格分数</th>
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



<#include "inc/footer1.ftl"/>

<script type="text/javascript">
    $(function () {
        var table = $('#example1').DataTable({
            "stateSave": true,
            "language": {
                "url": "${request.contextPath}/dataTables.Chinese.lang.json"
                // "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Chinese.json"
            }
        })
    })
</script>
</body>
</html>