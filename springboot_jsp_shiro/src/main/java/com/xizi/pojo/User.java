package com.xizi.pojo;


        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import lombok.experimental.Accessors;

        import java.io.Serializable;
        import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class User implements Serializable {

    private String id;
    private String username;
    private String password;
    private String salt;

    //定义角色集合
    private List<Role> roles;
}
