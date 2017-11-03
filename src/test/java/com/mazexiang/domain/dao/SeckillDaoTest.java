package com.mazexiang.domain.dao;

import com.mazexiang.domain.bean.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;


/**
 * 配置spring和junit的整合
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        Date killtime =new Date();
        int i = seckillDao.reduceNumber(1000L, killtime);
        System.out.println("-------------------------------------------------------");
        System.out.println("============>i的值是： " + i);

    }

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println("-------------------------------------------------------");
        System.out.println("============>seckill的值是： " + seckill);

    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0, 10);
        System.out.println("-------------------------------------------------------");
        System.out.println("============>seckills的值是： " + seckills);
    }

}