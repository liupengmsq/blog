package pengliu.me.dao;

import org.springframework.jca.cci.core.InteractionCallback;
import org.springframework.stereotype.Repository;
import pengliu.me.domain.Comment;
import pengliu.me.domain.CommentUser;

import java.util.List;

/**
 * Created by peng on 16-6-22.
 */
@Repository
public class CommentDao extends BaseDaoHibernate4<Comment>
{
}
