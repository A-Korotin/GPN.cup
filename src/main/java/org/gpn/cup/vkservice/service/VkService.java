package org.gpn.cup.vkservice.service;

import org.gpn.cup.vkservice.domain.vkApi.VkAPIError;
import org.gpn.cup.vkservice.domain.vkApi.VkAPIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class VkService {

    private RestTemplate template;

    @Value("${vk.api.execute-uri}")
    private String executeURI;

    @Value("${vk.api.version}")
    private String APIversion;

    @Autowired
    public VkService(RestTemplate template) {
        this.template = template;
    }

    private String composeVkScript(String userID, String groupID) {
        return """
                var user = API.users.get({"user_id": "%s", "fields": "nickname"});
                var is_member = API.groups.isMember({"user_id": user@.id, "group_id": "%s"});
                return {"first_name": user@.first_name[0],\s
                        "last_name": user@.last_name[0],
                        "middle_name": user@.middle_name[0],\s
                        "is_member": !!is_member};
                                
                """.formatted(userID, groupID);
    }

    private String composeExecuteURI() {
        return executeURI + "?access_token={accessToken}&v=%s&code={script}".formatted(APIversion);
    }

    private VkAPIResponse timeoutResponse() {
        VkAPIResponse response = new VkAPIResponse();
        response.setGeneralError(new VkAPIError(0, "Request timed out"));
        return response;
    }

    public VkAPIResponse getUserAndGroupMembershipByIDs(String accessToken, String userID, String groupID) {
        String script = composeVkScript(userID, groupID);
        String uri = composeExecuteURI();

        ResponseEntity<VkAPIResponse> response;

        try {
            response = template.getForEntity(uri, VkAPIResponse.class, accessToken, script);
        } catch (ResourceAccessException e) { //request timeout
            return timeoutResponse();
        }

        return response.getBody();
    }
}
