package pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BorrowedBook {

    @JsonProperty("id")
    private String ID;
    @JsonProperty("name")
    private String NAME;
    private String disabled;





}
