package pengliu.me.vo;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.converter.TimeStringConverter;

import java.sql.Timestamp;

/**
 * Created by peng on 16-6-22.
 */
public class CommentVo
{
    private Integer id;
    private String userName;
    private String userEmail;
    private String userUrl;
    private String userremoteIp;
    private String content;
    private Timestamp createTime;
    private Integer blogId;
    private Boolean rememberMe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getUserremoteIp() {
        return userremoteIp;
    }

    public void setUserremoteIp(String userremoteIp) {
        this.userremoteIp = userremoteIp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
