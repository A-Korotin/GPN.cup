package org.gpn.cup.vkservice.domain.vkApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VkAPIError {
    @JsonProperty("error_code")
    private Integer errorCode;

    @JsonProperty("error_msg")
    private String errorMessage;
}
