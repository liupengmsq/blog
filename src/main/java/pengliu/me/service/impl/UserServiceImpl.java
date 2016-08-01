package pengliu.me.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pengliu.me.common.CommonConstant;
import pengliu.me.dao.UserDao;
import pengliu.me.domain.User;
import pengliu.me.exception.UserNotExistException;
import pengliu.me.service.UserService;
import pengliu.me.utils.CommonUtil;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;

//    public boolean canLogin(User user)
//    {
//        User returnedUser = userDao.findUserByName(user.getName());
//        if(returnedUser == null)
//        {
//            return false;
//        }
//
//        return returnedUser.getPassword().equals(user.getPassword());
//    }
//
//    public void updateLoginTime(String name) throws UserNotExistException
//    {
//        User returnedUser = userDao.findUserByName(name);
//        if(returnedUser != null)
//        {
//            returnedUser.setLastLoginTime(CommonUtil.getTimeStampNow());
//            this.userDao.persist(returnedUser);
//            return;
//        }
//        throw new UserNotExistException("Can not find user by name: " + name);
//    }

    public User getAdminUser() throws UserNotExistException
    {
        User user = this.userDao.findUserByName(CommonConstant.ADMIN_USER_NAME);
        if(user == null)
        {
            throw new UserNotExistException("Can not find admin user by name: " + CommonConstant.ADMIN_USER_NAME);
        }
        return user;
    }
}
