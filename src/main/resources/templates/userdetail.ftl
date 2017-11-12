<#include "inc/header1.ftl"/>
<#include "inc/topnav1.ftl"/>
<#include "inc/leftsidebar1.ftl"/>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            Page Header
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
                <h3 class="box-title">用户信息</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body no-padding">
                <table class="table table-striped">
                <#--<tr>-->
                <#--<th style="width: 10px">#</th>-->
                <#--<th>Task</th>-->
                <#--<th>Progress</th>-->
                <#--<th style="width: 40px">Label</th>-->
                <#--</tr>-->
                    <tr>
                        <th  style="width: 80px">姓名</th>
                        <td>${user.name}</td>


                    </tr>
                    <tr>
                        <th>电话</th>
                        <td>${user.phone}</td>

                    </tr>
                    <tr>
                        <th>邮箱</th>
                        <td>${user.email}</td>

                    </tr>
                    <tr>
                        <th>类型</th>
                        <td>
                        <#if user.type=='STUDENT'>学生
                        <#elseif user.type=='TEACHER'>老师
                        <#elseif user.type=='MANAGER'>管理员

                        </#if>
                        </td>

                    </tr>
                    <tr>
                        <th>加入时间</th>
                        <td>${user.regTime}</td>

                    </tr>
                </table>
            </div>
            <!-- /.box-body -->
        </div>
        <!-- /.box -->
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->



<#include "inc/footer1.ftl"/>

<script type="text/javascript">

</script>
</body>
</html>