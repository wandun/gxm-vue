package cn.tycoding.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author  TyCoding
 * @date 2018/10/17
 */
@Data
@TableName(value = "tb_user")
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String salt;
    private String avatar;
    private String introduce;
    private String remark;
    /** 上次登录时间 **/
    private Date lastLoginTime;
}
