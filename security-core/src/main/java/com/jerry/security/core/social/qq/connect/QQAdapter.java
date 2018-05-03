package com.jerry.security.core.social.qq.connect;

import com.jerry.security.core.social.qq.api.QQ;
import com.jerry.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/3
 * Time: 20:28
 * Description: 用做自己写的获取QQ个性化的用户信息和Spring Social标准的数据结构间做适配
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * 测试当前Api是否可用
     *
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
     * Connection数据和Api数据做适配
     *
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo qqUserInfo = api.getUserInfo();

        // 将Api获取的QQ用户信息设置到Connection中
        values.setDisplayName(qqUserInfo.getNickname());
        values.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        // QQ没有个人主页，所以ProfileUrl为空就行
        values.setProfileUrl(null);
        values.setProviderUserId(qqUserInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        // 绑定解绑的时候使用
        return null;
    }

    /**
     * 更新个人状态(QQ没有这个)
     *
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(QQ api, String message) {
        // do nothing
    }
}
