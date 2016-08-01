package pengliu.me.vo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by peng on 16-4-15.
 */
public class BlogVo
{
    private Integer id;
    private String title;
    private String summary;
    private String content;
    private Timestamp updateTime;
    private Timestamp createTime;
    private Integer showCount;
    private String userName;
    private String status;
    private CategoryVo categoryVo;
    private String format;
    private String url;
    private Integer commentCount;
    private Set<TagVo> tagVos = new HashSet<TagVo>();

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Timestamp getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime)
    {
        this.updateTime = updateTime;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    public Integer getShowCount()
    {
        return showCount;
    }

    public void setShowCount(Integer showCount)
    {
        this.showCount = showCount;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public CategoryVo getCategoryVo() {
        return categoryVo;
    }

    public void setCategoryVo(CategoryVo categoryVo) {
        this.categoryVo = categoryVo;
    }

    public Set<TagVo> getTagVos() {
        return tagVos;
    }

    public void setTagVos(Set<TagVo> tagVos) {
        this.tagVos = tagVos;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
