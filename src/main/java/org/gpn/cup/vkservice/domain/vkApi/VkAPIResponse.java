package org.gpn.cup.vkservice.domain.vkApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.gpn.cup.vkservice.domain.Person;

import java.util.List;
import java.util.Optional;

@Data
public class VkAPIResponse {
    @JsonProperty("response")
    private Person person;

    @JsonProperty("error")
    private VkAPIError generalError;

    @JsonProperty("execute_errors")
    private List<VkAPIError> executeErrors;

    private boolean validResponse() {
        return generalError == null && executeErrors == null;
    }

    public Optional<Person> getBody() {
        if (!validResponse())
            return Optional.empty();

        return Optional.of(person);
    }

    public VkAPIError getErrorObj() {
        // execute errors list always contains 1 element
        return generalError != null ? generalError :  executeErrors.get(0);
    }
}
