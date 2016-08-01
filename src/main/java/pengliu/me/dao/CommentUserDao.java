package pengliu.me.dao;

import org.springframework.stereotype.Repository;
import pengliu.me.domain.CommentUser;
import pengliu.me.utils.CommonUtil;

import java.util.List;

/**
 * Created by peng on 16-6-22.
 */
@Repository
public class CommentUserDao extends BaseDaoHibernate4<CommentUser>
{
    public void createCommentUser(String commentUserName, String email, String url, String remoteIp)
    {
        CommentUser commentUser = new CommentUser();
        commentUser.setName(commentUserName);
        commentUser.setEmail(email);
        commentUser.setBlogUrl(url);
        commentUser.setRemoteIp(remoteIp);
        commentUser.setUpdateTime(CommonUtil.getTimeStampNow());
        this.persist(commentUser);
    }

    public CommentUser findCommentUserByEmail(String email)
    {
//        return this.get("email", email);
        return this.findUniqueBy("email", email);
    }

    public CommentUser findCommentUserByNickName(String nickName)
    {
//        return this.get("name", nickName);
        return this.findUniqueBy("name", nickName);
    }
}
