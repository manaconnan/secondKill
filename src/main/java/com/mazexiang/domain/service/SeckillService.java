package com.mazexiang.domain.service;

import com.mazexiang.domain.bean.Seckill;
import com.mazexiang.domain.dto.Exposer;
import com.mazexiang.domain.dto.SeckillExecution;
import com.mazexiang.domain.exception.RepeatKillException;
import com.mazexiang.domain.exception.SeckillClosedException;
import com.mazexiang.domain.exception.SeckillException;

import java.util.List;

public interface SeckillService {

    /**查询所有秒杀记录
     * @return
     */
    List<Seckill>  getSeckillList();

    /**查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getSeckillById(Long seckillId);

    /**
     * 秒杀开启时，输出秒杀地址
     * 否则，显示秒杀倒计时
     * 不应该把秒杀地址提前暴漏
     * @param seckillId
     */
    Exposer exportSeckillUrl(Long seckillId);

    /**执行秒杀操作
     * @param seckillID
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(Long seckillID,Long userPhone, String md5)
    throws SeckillException,RepeatKillException,SeckillClosedException;

}
