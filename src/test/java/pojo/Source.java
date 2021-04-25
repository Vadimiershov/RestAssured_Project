package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {

    private String id;
    private String name;

}
