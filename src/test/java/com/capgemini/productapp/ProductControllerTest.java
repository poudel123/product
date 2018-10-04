package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	@Test
	public void testAddProduct() throws Exception{
		
		String content = "{\r\n" + 
				"  \"productId\": \"123\",\r\n" + 
				"  \"productName\": \"sofa\",\r\n" + 
				"  \"productCategory\": \"furniture\",\r\n" + 
				"  \"productPrice\": \"1200\"\r\n" + 
				"}";
		when(productService.addProduct(Mockito.isA(Product.class))).thenReturn(new Product(123,"sofa","furniture",1200.0));
		mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(content).accept(MediaType.APPLICATION_JSON))
			 .andExpect(status().isOk())
			 .andExpect(jsonPath("$.productId").exists())
			 .andExpect(jsonPath("$.productName").exists())
			 .andExpect(jsonPath("$.productCategory").exists())
			 .andExpect(jsonPath("$.productPrice").exists())
			 .andExpect(jsonPath("$.productId").value("123"))
			 .andExpect(jsonPath("$.productName").value("sofa"))
			 .andExpect(jsonPath("$.productCategory").value("furniture"))
			 .andExpect(jsonPath("$.productPrice").value("1200.0"))
			 .andDo(print());
	}		
	
}
