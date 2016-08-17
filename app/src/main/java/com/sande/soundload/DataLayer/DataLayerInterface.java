package com.sande.soundload.DataLayer;

import com.sande.soundload.loginActivity_MVP.LoginPresenterInterface;

/**
 * Created by Sandeep on 17-08-2016.
 */
public interface DataLayerInterface {

    public void getAppUserDetails(LoginPresenterInterface loginPresenter, String accessToken);


}
