package micro.Todo.App.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    Long id;
    String username;
    String email;
}
