package com.mazexiang.domain.dao;

import com.mazexiang.domain.bean.SuccessKilled;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface SuccessKillDao {
    int insertSuccessKilled(@Param("seckillId") Long seckillId,@Param("userPhone")Long userPhone);
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") Long seckillId,@Param("userPhone")Long userPhone);
}
