<!-- Main Header -->
<header class="main-header">

    <!-- Logo -->
    <a href="##" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>A</b>LT</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg">
            <#--<b>Admin</b>LTE-->
            考试系统
        </span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">

                <!-- User Account Menu -->
                <li class="dropdown user user-menu">
                    <!-- Menu Toggle Button -->
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <!-- The user image in the navbar-->
                        <img src="/img/avatar04.png" class="user-image" alt="User Image">
                        <!-- hidden-xs hides the username on small devices so only the image appears. -->
                        <span class="hidden-xs">
                            <#--${user.type}-->
                        <#if user.type=='STUDENT'>学生
                            <#elseif user.type=='TEACHER'>老师
                            <#elseif user.type=='MANAGER'>管理员

                            </#if>
                        </span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- The user image in the menu -->
                        <li class="user-header">
                            <img src="/img/avatar04.png" class="img-circle" alt="User Image">

                            <p>
                                ${user.name}
                                    <#--- Web Developer-->
                                <small>加入时间：${user.regTime?string('yyyy-MM-dd HH:mm:ss')}</small>
                            </p>
                        </li>

                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <a href="/userdetail" class="btn btn-default btn-flat" id="profile">用户信息</a>
                            </div>
                            <div class="pull-right">
                                <a href="##" class="btn btn-default btn-flat" id="logout">注销</a>
                            </div>
                        </li>
                    </ul>
                </li>

            </ul>
        </div>
    </nav>
</header>