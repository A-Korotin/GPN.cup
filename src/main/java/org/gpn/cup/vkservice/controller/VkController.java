package org.gpn.cup.vkservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.gpn.cup.vkservice.domain.APIRequest;
import org.gpn.cup.vkservice.domain.Person;
import org.gpn.cup.vkservice.domain.vkApi.VkAPIError;
import org.gpn.cup.vkservice.domain.vkApi.VkAPIResponse;
import org.gpn.cup.vkservice.service.VkAPIErrorService;
import org.gpn.cup.vkservice.service.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Api("Main endpoint")
public class VkController {

    private final VkService vkService;

    private final VkAPIErrorService errorService;


    @Autowired
    public VkController(VkService vkService, VkAPIErrorService errorService) {
        this.vkService = vkService;
        this.errorService = errorService;
    }

    @GetMapping(value = "/person", produces = "application/json")
    @ApiOperation(value = "Get person and their group participation by person ID and group ID",
            response = Person.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK.", response = Person.class),
            @ApiResponse(code = 400, message = "Invalid request body or missing vk_access_token header",
                    response = VkAPIError.class),
            @ApiResponse(code = 401, message = "Invalid vk_access_token", response = VkAPIError.class),
            @ApiResponse(code = 404, message = "Not found: invalid user_id and/or group_id", response = VkAPIError.class)
    })
    public ResponseEntity<?> getPersonAndGroupMembership(
            @RequestHeader("vk_service_token") String serviceToken,
            @RequestBody APIRequest request) {


        VkAPIResponse response = vkService.getUserAndGroupMembershipByIDs(
                serviceToken, request.getUserID(), request.getGroupID());

        Optional<Person> person = response.getBody();

        if (person.isEmpty()) {
            return errorService.determineResponseEntity(response.getErrorObj());
        }

        return ResponseEntity.ok(response.getPerson());
    }
}
