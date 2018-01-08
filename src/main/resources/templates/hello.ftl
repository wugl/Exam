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

            <li class="active"><i class="fa fa-home"></i>${title!""}</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

        <!--------------------------
          | Your Page Content Here |
          -------------------------->
        <div class="box">
            <div class="box-header">

            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <h3>您好， ${name}，欢迎使用测评系统！</h3>
            </div>
        </div>

        <!-- 加载编辑器的容器 -->
        <script id="container" name="content" type="text/plain">这里写你的初始化内容</script>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->



<#include "inc/footer1.ftl"/>
<!-- 实例化编辑器 -->
<script type="text/javascript">
    //var ue = UE.getEditor('container');
    //$(document).attr("title","${title}");
</script>
</body>
</html>