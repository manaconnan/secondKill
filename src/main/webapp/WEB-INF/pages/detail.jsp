<%--
  Created by IntelliJ IDEA.
  User: mazexiang
  Date: 2017/9/26
  Time: 下午4:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>秒杀详情页</title>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="panel-heading">
               <h2>${seckill.name}</h2>
            </div>
            <div class="panel-body">
                <h3 class="text-danger">
                    <%--显示time图标--%>
                    <span class="glyphicon glyphicon-time"></span>
                    <%-- 展示倒计时--%>
                    <span class="glyphicon" id="seckill-box"></span>
                </h3>
            </div>
        </div>
    </div>
    <%--登陆弹出层 ，输入电话--%>
    <div id="killPhoneModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title text-center">
                        <span class="glyphicon glyphicon-phone"></span>秒杀电话：
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" name="killPhone" id="killPhoneKey"
                                placeholder="填写手机号码^o^" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <%--验证信息--%>
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        <span class="glyphicon glyphicon-backward"></span>
                        取消
                    </button>
                    <span id="killPhoneMessage" class="glyphicon"></span>

                    <button type="button" id="killPhoneBtn" class="btn btn-success">
                        <span class="glyphicon glyphicon-phone"></span>
                        Submit
                    </button>
                </div>
            </div>
        </div>
    </div>

</body>
<%--jquery cookie操作插件--%>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<%--jquery countdown 倒计时操作插件--%>
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.js"></script>
<%--开始编辑交互逻辑--%>
<%--这里千万不能写成<script src="" type="text/javascript"/> 会出错--%>
<script  type="text/javascript">


    var killPhone = $.cookie('killPhone');
    var seckillId = ${seckill.seckillId};
    var startTime = ${seckill.startTime.time};
    var endTime = ${seckill.endTime.time};
     $(function () {
             //手机验证和登陆,计时交互
             // 在cookie中查找手机号
         registerPhone(killPhone);
         $.get('/seckill/time/now',{},function (result) {
            if(typeof result !=undefined &&result['success']){
                var nowTime = result['data'];
                countdown(seckillId,nowTime,startTime,endTime);
            }else {
                console.log('result: '+result);
            }
         });
     });


    function registerPhone(Phone) {
        //验证手机号
        if(!validatePhone(Phone)){
            //绑定phone
            //控制输出
            var killPhoneModal = $('#killPhoneModal' );
            killPhoneModal.modal({
                //显示弹出层
                show:true,
                backdrop:'static',//禁止位置关闭
                keyboard:false// 关闭键盘事件
            });
            $('#killPhoneBtn').click(function () {
                var inputPhone = $('#killPhoneKey').val();

                if(validatePhone(inputPhone)){
                    //电话写入cookie y有效时间 7天
                    $.cookie('killPhone', inputPhone,{expires:7,path:'/seckill'});
                    //刷新页面
                    window.location.reload();
                }else {
                    $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                }
                //已经登陆
            });

        };
    }

    //验证手机号码是否合法
    function validatePhone(phone) {
        if(typeof phone =="undefined"){
            return false;
        }else {
            if( phone.length == 11 &&!isNaN(phone)){
                return true;
            }else {
                return false;
            }
        }


    }
   function countdown(seckillId,nowTime,startTime,endTime) {
       var seckillBox = $('#seckill-box');
       if(nowTime>endTime){
           seckillBox.html('秒杀结束！');
       }else if(nowTime < startTime){
           //seckillBox.html('秒杀未开始！');
           var killTime = new Date(startTime+1000);
           //countdown插件
           seckillBox.countdown(killTime,function (event) {
               //时间格式
               var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
               seckillBox.html(format);
               //时间完成后回调事件
           }).on('finish.countdown',function () {
               //获取秒杀地址，控制实现逻辑，执行秒杀
               handlerSeckill(seckillId,seckillBox);

           });
       }else {
           handlerSeckill(seckillId,seckillBox);
       }
   }
   function handlerSeckill(seckillId,node) {
       //获取秒杀地址，控制实现逻辑，执行秒杀
       var url =  '/seckill/'+seckillId+'/exposer';
        node.hide()
            .html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button> ')//将节点之前的内容隐藏，新创建一个秒杀点击按钮
        $.post(url,{},function (result) {
            //在回调函数中执行交互流程

            console.log(result);
            if(typeof result != undefined && result['success']){
                var exposer = result['data'];
                if (exposer['exposed']){
                    //开启秒杀
                    //获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = '/seckill/'+seckillId+'/'+md5+'/execution';
                    console.log("killUrl: "+killUrl);
                    //绑定一次点击事件
                    $('#killBtn').one('click',function () {
                        //执行秒杀请求的操作
                        //禁用按钮
                        $(this).addClass('disabled');

                        //发送请求
                        $.post(killUrl,{},function (result) {
                            if(typeof result!=undefined&&result['success']){
                                var killReult = result['data'];
                                var state = killReult['state'];
                                var stateInfo = killReult['stateInfo'];
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }
                        });
                    });
                    node.show();
                }else {
                    //用户浏览器系统时间不准确，秒杀未开始
//                    var now = exposer['now'];
//                    var start = exposer['start'];
//                    var end = exposer['end'];
//                    countdown(seckillId,now,start,end);
                 }
            }
        });
   }


</script>

</html>
