package own.clean.buswaybill.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import own.clean.buswaybill.entity.Bus;
import own.clean.buswaybill.entity.Route;
import own.clean.buswaybill.model.BusModel;
import own.clean.buswaybill.model.RouteModel;
import own.clean.buswaybill.repos.BusRepos;
import own.clean.buswaybill.service.CrudService;
import own.clean.buswaybill.service.impl.CrudServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BusController.class)
class BusControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    private CrudService crudService;
    Bus record_1 = new Bus(1L, "01AB083C");
    Bus record_2 = new Bus(2L, "01AB084C");
    Bus record_3 = new Bus(3L, "01AB085C");

    @Test
    void getAllBusRecords() throws Exception {
        List<Bus> records = new ArrayList<>(Arrays.asList(record_1, record_2, record_3));
        Mockito.when(crudService.findAll()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/bus")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect((MockMvcResultMatchers.jsonPath("$[0].stateNumber", is("01AB083C"))));

    }

    @Test
    void getrouteRecordById() throws Exception {
        Mockito.when(crudService.findById(record_1.getId())).thenReturn(java.util.Optional.of(record_1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/bus/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stateNumber", Matchers.is("01AB083C")));
    }

    @Test
    void createrouteRecord() throws Exception {
        BusModel busModel = BusModel.builder()
                .stateNumber("01AB086C")
                .build();
        Bus bus = new Bus(4L, busModel.getStateNumber());
        Mockito.when(crudService.save(busModel)).thenReturn(bus);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/bus")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(busModel));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath( "$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stateNumber",Matchers.is("01AB086C")));
    }

    @Test
    void updaterouteRecord() throws Exception {
        BusModel busModel = BusModel.builder()
                .stateNumber("60AB830C")
                .build();


        Mockito.when(crudService.findById(record_1.getId())).thenReturn(java.util.Optional.of(record_1));
        record_1.setStateNumber(busModel.getStateNumber());
        Mockito.when(crudService.update(busModel,record_1)).thenReturn(record_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/bus")
                .param("id", String.valueOf(record_1.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath( "$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stateNumber",Matchers.is("60AB830C")));
    }

    @Test
    void deleterouteById() throws Exception {

        Mockito.when(crudService.findById(record_2.getId())).thenReturn(Optional.of(record_2));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/bus/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}