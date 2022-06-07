package com.hwan2272.masecomms.controller;

import com.hwan2272.masecomms.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
//@SpringBootTest
@WebMvcTest(ProductController.class)
//@ExtendWith({ SpringExtension.class })
class ProductControllerTest {

    //@Autowired
    MockMvc mockMvc;

    @MockBean
    ModelMapper mMapper;

    @MockBean
    ProductService productService;

    //@Autowired
    //RestDocumentationResultHandler document;

    static final String prefixUri ="/product";

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void getProduct() throws Exception {
        String testProductId = "P-001";

        this.mockMvc.perform(
                get(String.format("%s/%s",  prefixUri, testProductId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("product-get",
                        responseFields(
                                fieldWithPath("[].seq").description("The seq of the output"),
                                fieldWithPath("[].productId").description("The productId of the output"),
                                fieldWithPath("[].productName").description("The productName of the output"),
                                fieldWithPath("[].unitStock").description("The unitStock of the output"),
                                fieldWithPath("[].unitPrice").description("The unitPrice of the output"),
                                fieldWithPath("[].note").description("The note of the output"),
                                //fieldWithPath("[].name").description("The name of the output"),
                                //fieldWithPath("[].email").description("The email of the output"),
                                //fieldWithPath("[].date").description("The date of the output"))
                                fieldWithPath("[].createdAt").description("The createdAt of the output"))));

        /*this.mockMvc.perform(get("/product")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        responseFields(
                                fieldWithPath("[].id").description("The id of the output"),
                                fieldWithPath("[].name").description("The name of the output"),
                                fieldWithPath("[].email").description("The email of the output"),
                                fieldWithPath("[].date").description("The date of the output"))));*/
    }
}