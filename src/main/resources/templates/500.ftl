<#include "inc/header1.ftl"/>


<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            500 Error Page
        </h1>
        <ol class="breadcrumb">
            <li><a href="${request.contextPath}/"><i class="fa fa-home"></i> 首页</a></li>
            <#--<li><a href="#">Examples</a></li>-->
            <li class="active">500 error</li>
        </ol>
    </section>

    <!-- Main content -->
    <section class="content">

        <div class="error-page">
            <h2 class="headline text-red">500</h2>

            <div class="error-content"  style="padding-top: 30px;>
                <h3><i class="fa fa-warning text-red"></i> 服务器错误！</h3>

                <p>
                    <a href="${request.contextPath}/">返回首页</a>
                </p>


            </div>
        </div>
        <!-- /.error-page -->

    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->


<#include "inc/footer1.ftl"/>

<script>
    $(function () {
        //$('body').removeClass("sidebar-mini").addClass("sidebar-collapse");
    })
</script>
</body>
</html>