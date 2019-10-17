package com.simpledevicedatabase.simpleddb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SimpleDeviceDatabase.class)
public class RestAPITest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    public void shouldReturnSingleDeviceModel() throws Exception {
        this.mockMvc.perform(get("/api/deviceModels/1"))
            .andExpect(status().isOk())
            .andExpect(content().json("{'name' : 'OptiPlex 3060','id' : 1,'_links' : {'self' : {'href' : 'http://127.0.0.1:8080/api/deviceModels/1'},'deviceModel' : {'href' : 'http://127.0.0.1:8080/api/deviceModels/1'},'devices' : {'href' : 'http://127.0.0.1:8080/api/deviceModels/1/devices'}}}"));
    }
}