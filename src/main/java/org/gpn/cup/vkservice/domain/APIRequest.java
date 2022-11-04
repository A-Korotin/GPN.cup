package org.gpn.cup.vkservice.domain;

import lombok.Data;

@Data
public class APIRequest {
    private String userID;
    private String groupID;
    private String accessToken;
}
