package pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private int year;
    @JsonProperty("released")
    private String released;
    @JsonProperty("language")
    private String language;

    @JsonProperty("Ratings")
    List<Rating> allRatings;


}
