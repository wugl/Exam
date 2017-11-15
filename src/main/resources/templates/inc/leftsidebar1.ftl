<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <!-- Sidebar user panel (optional) -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="/img/avatar04.png" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info" style="line-height: 35px;">
                <p>${user.name}</p>
                <!-- Status -->
                <#--<a href="#"><i class="fa fa-circle text-success"></i> 在线</a>-->
            </div>
        </div>

        <!-- Sidebar Menu -->
        <ul class="sidebar-menu" data-widget="tree">
            <li class="header"></li>
            <!-- Optionally, you can add icons to the links -->
        <#if user.type=='STUDENT'>
            <li class="active"><a href="/myexam"><i class="fa fa-circle-o text-aqua"></i></i> <span>我的考试</span></a>
            </li>
            <li class="active"><a href="/myexamhistory"><i class="fa fa-circle-o text-aqua"></i></i> <span>考试记录</span></a>
            </li>
        </#if>
        <#if user.type=='MANAGER'>
            <li class="active"><a href="/usermanager"><i class="fa fa-circle-o text-aqua"></i></i> <span>用户管理</span></a>
            </li>
        </#if>
        <#if user.type=='TEACHER'>
            <li><a href="/questionType"><i class="fa fa-circle-o text-aqua"></i> <span>题型管理</span></a></li>
            <li><a href="/question"><i class="fa fa-circle-o text-aqua"></i> <span>题目管理</span></a></li>
            <li><a href="/exam"><i class="fa fa-circle-o text-aqua"></i> <span>试卷管理</span></a></li>
            <li class="active"><a href="/examhistory"><i class="fa fa-circle-o text-aqua"></i></i> <span>考试记录</span></a>
            </li>
        </#if>
            <#--<li class="treeview">-->
                <#--<a href="#"><i class="fa fa-circle-o text-aqua"></i></i> <span>Multilevel</span>-->
                    <#--<span class="pull-right-container">-->
                <#--<i class="fa fa-angle-left pull-right"></i>-->
              <#--</span>-->
                <#--</a>-->
                <#--<ul class="treeview-menu">-->
                    <#--<li><a href="#">Link in level 2</a></li>-->
                    <#--<li><a href="#">Link in level 2</a></li>-->
                <#--</ul>-->
            <#--</li>-->
        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>