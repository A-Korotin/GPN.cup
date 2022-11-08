package org.gpn.cup.vkservice.domain.vkApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkAPIError {
    @JsonProperty("error_code")
    private Integer errorCode;

    @JsonProperty("error_msg")
    private String errorMessage;
}
