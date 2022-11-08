package org.gpn.cup.vkservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gpn.cup.vkservice.domain.APIRequest;
import org.gpn.cup.vkservice.domain.Person;
import org.gpn.cup.vkservice.domain.vkApi.VkAPIError;
import org.gpn.cup.vkservice.domain.vkApi.VkAPIResponse;
import org.gpn.cup.vkservice.service.VkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(VkController.class)
@ContextConfiguration(classes = {VkService.class, RestTemplate.class, VkController.class})
class VkControllerTest {

    private static final int INVALID_ACCESS_TOKEN_ERROR_CODE = 5;

    @Autowired
    private VkController controller;

    @Autowired
    private MockMvc mockMvc;

    @Value("${test.vk.service.token}")
    private String token;

    private <T> String toJson(T value) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(value);
    }

    private <T> T fromJson(String jsonString, Class<T> clazz) throws JsonProcessingException {
        return new ObjectMapper().readValue(jsonString, clazz);
    }

    private APIRequest composeValidRequest() {
        APIRequest request = new APIRequest();
        request.setUserID("188577108");
        request.setGroupID("itmostudents");
        return request;
    }

    private String getValidRequestBody() throws JsonProcessingException {

        APIRequest request = composeValidRequest();

        return toJson(request);
    }

    @Test
    public void checkContext() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void checkOk() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/person")
                .content(getValidRequestBody())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("vk_service_token", token))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Person person = fromJson(responseBody, Person.class);

        assertThat(person.getFirstName()).isEqualTo("Alexey");
        assertThat(person.getMiddleName()).isEqualTo("");
        assertThat(person.getLastName()).isEqualTo("Korotin");
        assertThat(person.getIsMember()).isEqualTo(true);
    }

    @Test
    public void checkInvalidTokenAccess() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/person")
                        .content(getValidRequestBody())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("vk_service_token", "invalid_token"))
                .andExpect(status().isBadRequest())
                .andReturn();

        VkAPIError response = fromJson(result.getResponse().getContentAsString(), VkAPIError.class);

        assertThat(response.getErrorCode()).isEqualTo(INVALID_ACCESS_TOKEN_ERROR_CODE);
    }

    @Test
    public void checkNoTokenAccess() throws Exception {
        mockMvc.perform(
                get("/person")
                        .content(getValidRequestBody())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}