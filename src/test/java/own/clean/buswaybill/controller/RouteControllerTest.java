package own.clean.buswaybill.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import own.clean.buswaybill.entity.Route;
import own.clean.buswaybill.model.RouteModel;
import own.clean.buswaybill.repos.RouteRepos;
import own.clean.buswaybill.service.CrudService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouteController.class)

class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private RouteRepos routeRepos;

    @MockBean
    private CrudService crudService;


    Route record_1 = new Route(1L ,"316","Olmazor - Chilonzor");
    Route record_2 = new Route(2L,"28","Olmazor - Tinchlik");
    Route record_3 = new Route(3L, "36", "Yunusobod - Chilonzor");

    @Test
    void getAllrouteRecords() throws Exception {
        List<Route> records = new ArrayList<>(Arrays.asList(record_1, record_2, record_3));
        Mockito.when(crudService.findRoutes()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/route")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].routeName", is("316")));
    }


    @Test
    void getrouteRecordById() throws Exception {
        Mockito.when(crudService.findRouteById(record_1.getId())).thenReturn(java.util.Optional.of(record_1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/route/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.routeName", Matchers.is("316")));
    }

    @Test
    void createrouteRecord() throws Exception {
        RouteModel routeModel = RouteModel.builder()
                .routeName("Yunusobod - Sever")
                .description("316")
                .build();
        Route route = new Route(4L,routeModel.getRouteName(), routeModel.getDescription());
        Mockito.when(crudService.saveRoute(routeModel)).thenReturn(route);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/route")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(routeModel));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath( "$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.routeName",Matchers.is("Yunusobod - Sever")));
    }

    @Test
    void updaterouteRecord() throws Exception {
        RouteModel routeModel = RouteModel.builder()
                .routeName("317")
                .description("Olmazor - Chilonzor")
                .build();


        Mockito.when(crudService.findRouteById(record_1.getId())).thenReturn(Optional.of(record_1));
        record_1.setDescription(routeModel.getDescription());
        record_1.setRouteName(routeModel.getRouteName());
        Mockito.when(crudService.updateRoute(routeModel,record_1)).thenReturn(record_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/route")
                .param("id", String.valueOf(record_1.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath( "$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.routeName",Matchers.is("317")));
    }

    @Test
    void deleterouteById() throws Exception {

        Mockito.when(crudService.findRouteById(record_2.getId())).thenReturn(Optional.of(record_2));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/route/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}