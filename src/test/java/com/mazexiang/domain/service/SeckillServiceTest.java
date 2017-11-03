package com.mazexiang.domain.service;

import com.mazexiang.domain.bean.Seckill;
import com.mazexiang.domain.dto.Exposer;
import com.mazexiang.domain.dto.SeckillExecution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class SeckillServiceTest {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        System.out.println("-------------------------------------------------------");
        System.out.println("============>的值是： " + seckillList);
    }

    @Test
    public void getSeckillById() throws Exception {
        Seckill seckillById = seckillService.getSeckillById(1001L);
        System.out.println("-------------------------------------------------------");
        System.out.println("============>seckillById的值是： " + seckillById);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1002L);
        System.out.println("-------------------------------------------------------");
        System.out.println("============>exposer的值是： " + exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        SeckillExecution seckillExecution = seckillService.executeSeckill(1002L, 123994712464L, "34b14cc8816df22fadf822b1bacd1b89");
        System.out.println("-------------------------------------------------------");
        System.out.println("============>seckillExecution的值是： " + seckillExecution);
    }


}