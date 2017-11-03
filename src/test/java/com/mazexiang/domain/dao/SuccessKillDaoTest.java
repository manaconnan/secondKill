package com.mazexiang.domain.dao;

import com.mazexiang.domain.bean.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class SuccessKillDaoTest {
    @Resource
    private SuccessKillDao successKillDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        int i = successKillDao.insertSuccessKilled(1000L, 12311111111L);
        System.out.println("-------------------------------------------------------");
        System.out.println("============>i的值是： " + i);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessKilled successKilled = successKillDao.queryByIdWithSeckill(1000L, 12311111111L);
        System.out.println("-------------------------------------------------------");
        System.out.println("============>successKilled的值是： " + successKilled.getSeckill());

    }

}