package org.gpn.cup.vkservice.controller;

import lombok.AllArgsConstructor;
import org.gpn.cup.vkservice.domain.APIRequest;
import org.gpn.cup.vkservice.domain.Person;
import org.gpn.cup.vkservice.domain.vkApi.VkAPIResponse;
import org.gpn.cup.vkservice.service.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VkController {

    private VkService vkService;

    @Autowired
    public VkController(VkService vkService) {
        this.vkService = vkService;
    }

    @GetMapping("/person")
    public ResponseEntity<?> getPersonAndGroupMembership(@RequestBody APIRequest request) {

        VkAPIResponse response = vkService.getUserAndGroupMembershipByIDs(
                request.getAccessToken(), request.getUserID(), request.getGroupID());

        Optional<Person> person = response.getBody();

        if (person.isEmpty()) {
            return ResponseEntity.badRequest().body(response.getErrorObject());
        }

        return ResponseEntity.ok(response.getPerson());
    }
}
