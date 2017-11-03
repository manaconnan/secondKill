package com.mazexiang.domain.web;

import com.mazexiang.domain.bean.Seckill;
import com.mazexiang.domain.dto.Exposer;
import com.mazexiang.domain.dto.SeckillExecution;
import com.mazexiang.domain.dto.SeckillResult;
import com.mazexiang.domain.enums.SeckillStateEnum;
import com.mazexiang.domain.exception.RepeatKillException;
import com.mazexiang.domain.exception.SeckillClosedException;
import com.mazexiang.domain.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("seckill")
public class SeckillController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        //list.sjp+model = ModelAndView
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list",seckillList);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId")Long seckillId, Model model){
        if(seckillId==null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getSeckillById(seckillId);
        if (seckill==null){
           return "forward:/seckill/list";//同上 转发到list页面
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }


    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result=new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage());
            result=new SeckillResult<Exposer>(false,e.getMessage());
        }

            return result;

    }
    @ResponseBody
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @CookieValue(value = "killPhone",required = false) Long userPhone,
                                                   @PathVariable("md5") String md5){
        if(userPhone==null){
            return new SeckillResult<SeckillExecution>(false,"用户未注册");
        }
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
            result = new SeckillResult<SeckillExecution>(true,seckillExecution);
        }catch (RepeatKillException e){
            SeckillExecution execution =new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            result = new SeckillResult<SeckillExecution>(true,execution);
        }catch (SeckillClosedException e ){
            SeckillExecution execution =new SeckillExecution(seckillId, SeckillStateEnum.END);
            result = new SeckillResult<SeckillExecution>(true,execution);
        } catch (Exception e){
            logger.error(e.getMessage());
            SeckillExecution execution =new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            result=new SeckillResult<SeckillExecution>(true,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult(true,now.getTime());
    }
}
