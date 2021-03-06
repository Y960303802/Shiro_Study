package com.xizi.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Role implements Serializable {
    private String id;
    private String name;

    //定义权限集合
    private List<Perms> perms;


}
