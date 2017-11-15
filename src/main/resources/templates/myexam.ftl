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
                    <td>${key.examDate}</td>
                    <td>${key.totalTime}</td>
                    <td>${key.totalScore}</td>
                    <td>${key.passScore}</td>
                    <td>
                    <#--<a class="btn-sm q_edit" data-id="${key.id}" data-type="${key.typeId}"><i-->
                                <#--class="fa fa-edit"></i></a>-->
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
                <th>考试日期</th>
                <th>考试时长</th>
                <th>总分数</th>
                <th>及格分数</th>
                <th>操作</th>

            </tr>
            </tfoot>
        </table>
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
                "url": " /dataTables.Chinese.lang.json"
                // "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Chinese.json"
            }
        })
    })
</script>
</body>
</html>