package org.gpn.cup.vkservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class APIRequest {
    private String userID;
    private String groupID;

    @JsonCreator
    public APIRequest(@JsonProperty(value = "user_id",required = true) String userID,
                      @JsonProperty(value = "group_id", required = true) String groupID) {
        this.userID = userID;
        this.groupID = groupID;
    }
}
