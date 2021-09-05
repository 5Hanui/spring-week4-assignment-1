package com.codesoom.assignment;

import com.codesoom.assignment.applications.ProductService;
import com.codesoom.assignment.domains.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@DataJpaTest
public class ProductControllerWebTest {
    private String NAME = "Test Name";
    private String MAKER = "Test Maker1";
    private Integer PRICE = 1000;
    private String IMAGE = "Image1";
    private String NEW_NAME = "Test2";
    private String UPDATED_NAME = "Test1_updated";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService ProductService;

    @BeforeEach
    void setUp(){
        Product source = new Product(NAME, MAKER, PRICE, IMAGE);
        ProductService.createProduct(source);
    }

    @Test
    @Order(2)
    void list() throws Exception{
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andDo(print());
    }

    //@Test
    void getProduct() throws Exception{
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(NAME)));
    }

    @Test
    @Order(1)
    void createProduct() throws Exception{
        Product newProduct = new Product();
        newProduct.setName(NEW_NAME);
        newProduct.setPrice(PRICE);
        newProduct.setImage(IMAGE);
        String content = objectMapper.writeValueAsString(newProduct);
        mockMvc.perform(post("/products").content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(NEW_NAME));
    }

    //@Test
    void updateProduct() throws Exception{
        Product newProduct = new Product();
        newProduct.setName(UPDATED_NAME);

        String content = objectMapper.writeValueAsString(newProduct);
        mockMvc.perform(put("/products/1").content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(UPDATED_NAME));
    }

    //@Test
    void deleteProduct() throws Exception{
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}
