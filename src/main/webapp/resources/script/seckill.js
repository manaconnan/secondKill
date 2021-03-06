
//存放主要的交互逻辑js代码
//javascript模块化

var seckill  = {
    //封装秒杀相关的ajax的url
    URL : {

    },
    //验证手机号
    validatePhone : function (phone) {
      if(phone && phone.length==11&&!isNaN(phone)){
          return true;
      }else {
       return false;
      }
    },


    //详情页秒杀逻辑
    detail : {

        //详情页初始化
        init : function (params) {


            //手机验证和登陆,计时交互
            // 在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
             alert(seckill.validatePhone(killPhone));
            //验证手机号
            if(!seckill.validatePhone(killPhone)){
                alert("aewr");
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
                    if(seckill.validatePhone(killPhone)){
                        //电话写入cookie y有效时间 7天
                        $.cookie('killPhone', inputPhone,{expires:7,path:'/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>>').show(300);
                    }
                    //已经登陆
                });

            }
        }


    }
}