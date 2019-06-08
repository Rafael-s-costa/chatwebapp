package sparktest.chatwebapp.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDTO {

	private int id;
    private String content;
    //To be changed to user DTO
    private String from;
    private String to;
}
