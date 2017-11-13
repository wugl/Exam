<#include "inc/header1.ftl"/>
<#include "inc/topnav1.ftl"/>
<#include "inc/leftsidebar1.ftl"/>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
        ${title!""}
            <small>Optional description</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
            <li class="active">Here</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

        <!--------------------------
          | Your Page Content Here |
          -------------------------->

        <h1>Hello ${hello}</h1>
        <p></p>

        <!-- 加载编辑器的容器 -->
        <script id="container" name="content" type="text/plain">这里写你的初始化内容</script>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->



<#include "inc/footer1.ftl"/>
<!-- 实例化编辑器 -->
<script type="text/javascript">
    var ue = UE.getEditor('container');
    //$(document).attr("title","${title}");
</script>
</body>
</html>