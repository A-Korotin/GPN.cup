package org.gpn.cup.vkservice.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @ApiModelProperty(
            notes = "First name of the person",
            name = "first_name",
            required = true
    )
    @JsonProperty("first_name")
    private String firstName;

    @ApiModelProperty(
            notes = "Second name of the person",
            name = "second_name",
            required = true
    )
    @JsonProperty("last_name")
    private String lastName;

    @ApiModelProperty(
            notes = "Middle name of the Person",
            name = "middle_name",
            required = true
    )
    @JsonProperty("middle_name")
    @JsonSetter(nulls = Nulls.SKIP)
    private String middleName = "";

    @ApiModelProperty(
            notes = "Represents person membership in group",
            name = "is_member",
            required = true
    )
    @JsonProperty("member")
    @JsonAlias("is_member")
    private Boolean isMember;
}
