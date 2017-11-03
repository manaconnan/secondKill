package com.mazexiang.domain.service.impl;

import com.mazexiang.domain.bean.Seckill;
import com.mazexiang.domain.bean.SuccessKilled;
import com.mazexiang.domain.dao.SeckillDao;
import com.mazexiang.domain.dao.SuccessKillDao;
import com.mazexiang.domain.dto.Exposer;
import com.mazexiang.domain.dto.SeckillExecution;
import com.mazexiang.domain.enums.SeckillStateEnum;
import com.mazexiang.domain.exception.RepeatKillException;
import com.mazexiang.domain.exception.SeckillClosedException;
import com.mazexiang.domain.exception.SeckillException;
import com.mazexiang.domain.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKillDao successKillDao;
    //md5盐值字符串，用于混淆md5
    private final String salt ="sdgdzaipha%*%$$@^^dfvsjfOD7YADQ934";

    public List<Seckill> getSeckillList() {
        List<Seckill> seckills = seckillDao.queryAll(0, 4);
        return seckills;
    }

    public Seckill getSeckillById(Long seckillId) {

        return   seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(Long seckillId) {
        Seckill seckill = getSeckillById(seckillId);
        if (seckill==null){
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        String md5 = getMd5(seckillId);
        System.out.println("-------------------------------------------------------");
        System.out.println("============>md5的值是： " + md5);
        System.out.println("-------------------------------------------------------");
        System.out.println("-------------------------------------------------------");
        System.out.println("============>startTime的值是： " + startTime);
        System.out.println("-------------------------------------------------------");
        System.out.println("============>endTime的值是： " + endTime);
        System.out.println("============>nowTime的值是： " + nowTime);
        System.out.println("============>nowTime.getTime()的值是： " + nowTime.getTime());
        if (nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }

        return new Exposer(true,md5,seckillId);
    }
    private String getMd5(Long seckillId){
        String base = seckillId+"/"+salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillClosedException {
       if(md5==null||!md5.equals(getMd5(seckillId))){
           throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀业务逻辑：减库存+记录购买行为
        Date nowTime = new Date();
      try {
          //减库存
          int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
          if(updateCount<=0){
              //没有更新到记录，说明秒杀结束
              throw new SeckillClosedException("seckill has closed");
          }else {
              //记录购买行为
              int insertCount = successKillDao.insertSuccessKilled(seckillId, userPhone);
              if(insertCount<=0){
                  //重复秒杀哦 根据唯一联合主键：seckillId，userPhone
                  throw new RepeatKillException("seckill repeated");
              }else {
                  //秒杀成功
                  SuccessKilled successKilled = successKillDao.queryByIdWithSeckill(seckillId, userPhone);

                  return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
              }
          }
      } //出现异常，让mysql执行rollback操作
      catch (SeckillClosedException e1){
            throw  e1;
      }catch (RepeatKillException e2){
          throw e2;
      }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常转化为运行期异常
             throw new SeckillException("seckill inner error"+e.getMessage());
      }
    }
}
