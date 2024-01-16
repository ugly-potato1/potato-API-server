package potato.server.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import potato.server.product.dto.request.ProductCreateRequest;
import potato.server.product.dto.request.ProductUpdateRequest;
import potato.server.product.repository.ProductRepository;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ProductAPITest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProductRepository productRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void createProductTest() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest("New Product", new BigDecimal("100.00"), "This is a new product.", 100);
        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateProductTest() throws Exception {
        Long productId = 1L; // assuming the product with id 1 exists
        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setProductId(productId);
        request.setTitle("Updated Product");
        request.setPrice(new BigDecimal("150.00"));
        request.setDescription("This is an updated product.");
        request.setStock(150);
        mockMvc.perform(put("/api/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void getProductTest() throws Exception {
        Long productId = 1L; // assuming the product with id 1 exists
        mockMvc.perform(get("/api/product/" + productId))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProductTest() throws Exception {
        Long productId = 1L; // assuming the product with id 1 exists
        mockMvc.perform(delete("/api/product/" + productId))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllProductsTest() throws Exception {
        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk());
    }

    // Convert object to JSON string
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}