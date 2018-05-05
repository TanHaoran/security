package com.jerry.security.core.social.weixin.connect;

import com.jerry.security.core.social.weixin.api.Weixin;
import com.jerry.security.core.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * Created with IntelliJ IDEA.
 * User: Jerry
 * Date: 2018/5/5
 * Time: 15:11
 * Description:
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {

    private String openId;

    public WeixinAdapter() {
    }

    /**
     * 因为setConnectionValues()中调用getUserInfo()方法时需要openId，所以要把openId传进来
     * @param openId
     */
    public WeixinAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(Weixin api) {
        return true;
    }

    @Override
    public void setConnectionValues(Weixin api, ConnectionValues values) {
        WeixinUserInfo profile = api.getUserInfo(openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(Weixin api) {
        return null;
    }

    @Override
    public void updateStatus(Weixin api, String message) {
        //do nothing
    }

}
