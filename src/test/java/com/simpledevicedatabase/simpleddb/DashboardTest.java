package com.simpledevicedatabase.simpleddb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.simpledevicedatabase.simpleddb.web.DashboardController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DashboardTest {

    @Autowired
    private DashboardController controller;
    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}