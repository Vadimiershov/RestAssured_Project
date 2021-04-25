package pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BorrowedBooksBYUser {

    @JsonProperty("book_id")
    private String BookID;
    @JsonProperty("user_id")
    private String UserID;
    @JsonProperty("name")
    private String Name;



}


