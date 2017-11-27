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
        <div class="box">
            <div class="box-header">
            <#--<a class="btn btn-info pull-left margin qt_new"><i class="fa fa-plus"></i>新增</a>-->
            <#--<a href="/questionType/getExcel" class="btn btn-info  margin"><i class="fa fa-fw fa-file-excel-o"></i>导出excel</a>-->
            </div>
            <!-- /.box-header -->
            <div class="box-body">

                <form class="form-horizontal">
                    <div class="box-body">
                        <div class="form-group col-sm-6">
                            <label for="inputEmail3" class="col-sm-2 control-label">起始</label>

                            <div class="col-sm-10">
                                <select class="form-control" name="startYear" id="start_year">
                                <#--<option value="1">学生</option>-->
                                    <#--<option value="2">老师</option>-->
                                    <#--<option value="3">管理员</option>-->
                                </select>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label for="inputPassword3" class="col-sm-2 control-label">结束</label>

                            <div class="col-sm-10">
                                <select class="form-control" name="endYear" id="end_year">

                                </select>
                            </div>
                        </div>

                    </div>
                    <!-- /.box-body -->
                <#--<div class="box-footer">-->
                <#--<button type="submit" class="btn btn-default">Cancel</button>-->
                <#--<button type="submit" class="btn btn-info pull-right">提交</button>-->
                <#--</div>-->
                    <!-- /.box-footer -->
                </form>
                <div id="chart" class="col-sm-6"><canvas id="canvas"></canvas></div>

            </div>
        </div>

    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->



<#include "inc/footer1.ftl"/>

<script type="text/javascript">
    $(function () {

        var thisYear = new Date().getFullYear();
        var startYear = 0, endYear = 0;
       // $('#start_year').append('<option value="">起始年份</option>');
        for (var i = -4; i < 1; i++) {

            $('#start_year').append('<option value="' + (thisYear + i) + '">' + (thisYear + i) + '</option>');
        }
        $('#start_year').on('change', function () {
            //console.log(this.value);
            startYear = this.value;

            if(startYear=='') return;
           // $('#end_year').html('<option value="">结束年份</option>');
            $('#end_year').html('');
            for (var j = startYear; j <= thisYear; j++) {
                $('#end_year').append('<option value="' + j + '">' + j + '</option>');
            }

        });
        $('#end_year').on('change', function () {
            endYear = this.value;
            $.ajax({
                url: '${request.contextPath}/exam/yearStatistics',
                method: 'GET',
                data: 'start=' + startYear + '&end=' + endYear,
                success: function (data) {
                    //console.log(data);
                    $('#chart').html('<canvas id="canvas"></canvas>');

                    var config = {
                        type: 'line',
                        data: {
                            labels: data.data.years,
                            datasets: [{
                                label: "年度平均分",
                                backgroundColor: '#00c0ef',
                                borderColor: '#00c0ef',
                                data: data.data.data,
                                fill: false,
                            }]
                        },
                        options: {
                            responsive: true,
                            title:{
                                display:true,
                                text:'平均分统计趋势'
                            },
                            tooltips: {
                                mode: 'index',
                                intersect: false,
                            },
                            hover: {
                                mode: 'nearest',
                                intersect: true
                            },
                            scales: {
                                xAxes: [{
                                    display: true,
                                    scaleLabel: {
                                        display: true,
                                        labelString: '年份'
                                    }
                                }],
                                yAxes: [{
                                    display: true,
                                    scaleLabel: {
                                        display: true,
                                        labelString: '平均分'
                                    }
                                }]
                            }
                        }
                    };

                    var ctx = document.getElementById("canvas").getContext("2d");
                    var myLine = new Chart(ctx, config);

                }
            });

        })


    })
</script>
</body>
</html>