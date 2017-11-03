package com.mazexiang.domain.dao;

import com.mazexiang.domain.bean.Seckill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface SeckillDao {
    int reduceNumber(@Param("seckillId")Long seckillId,@Param("killTime")Date killTime);
    Seckill queryById(Long seckilId);
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
