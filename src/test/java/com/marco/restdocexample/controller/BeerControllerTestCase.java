package com.marco.restdocexample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.restdocexample.model.BeerDto;
import com.marco.restdocexample.services.BeerService;
import com.marco.restdocexample.web.controller.BeerController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.marco.restdocexample.web.mappers")
public class BeerControllerTestCase {

    @MockBean
    BeerService service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    BeerDto validBeerGet;

//    BeerDto validBeerPost;


    @Test
    public void ottieniUnaBirra() throws Exception {

        validBeerGet = createBeer();
        given(service.getBeerById(any(UUID.class))).willReturn(validBeerGet);

        mockMvc.perform(get("/api/v1/beer/{beerId}" , validBeerGet.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(document("v1/beer", pathParameters(parameterWithName("beerId").description("UUID of desired beer to get"))))
        //        .andExpect( jsonPath("$.id", is(validBeer.getId().toString())))
        ;

    }

    @Test
    public void inserisciUnaBirra() throws Exception {

        BeerDto beerDto = createBeer();
        beerDto.setId(null);

        BeerDto savedBeer = BeerDto.builder().id(UUID.randomUUID()).beerName("mia birra").beerStyle("My Style").build();

        String beerToJson = mapper.writeValueAsString(beerDto);

        given(service.saveNewBeer(any())).willReturn(savedBeer);

        mockMvc.perform(post("/api/v1/beer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerToJson))
                .andExpect(status().isCreated()).andDo(document("v1/beer"));

    }
    @Test
    public void aggiornaUnaBirra() throws Exception {

        BeerDto beerDto = createBeer();

        beerDto.setId(null);
        String beerToJson = mapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/"+ UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(beerToJson))
                .andExpect(status().isNoContent());

        then(service).should().updateBeer(any(), any());
    }

    private BeerDto createBeer(){
        return BeerDto.builder().id(UUID.randomUUID()).beerName("Birra").beerStyle("Pale Ale").upc(1L).price(new BigDecimal("10")).build();
    }

}
