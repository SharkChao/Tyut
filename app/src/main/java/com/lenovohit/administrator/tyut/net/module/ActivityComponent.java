package com.lenovohit.administrator.tyut.net.module;

import com.lenovohit.administrator.tyut.activity.LoginActivity;
import com.lenovohit.administrator.tyut.activity.SplashActivity;
import com.lenovohit.administrator.tyut.fragment.one.AcademicFragment;
import com.lenovohit.administrator.tyut.fragment.one.NoticeFragment;
import com.lenovohit.administrator.tyut.fragment.one.SchhoolNewsFragment;
import com.lenovohit.administrator.tyut.fragment.one.SchoolPlayFragment;
import com.lenovohit.administrator.tyut.fragment.two.AllScoreActivity;
import com.lenovohit.administrator.tyut.fragment.two.BuScoreActivity;
import com.lenovohit.administrator.tyut.fragment.two.CengKeActivity;
import com.lenovohit.administrator.tyut.fragment.two.CurrentScoreActivity;
import com.lenovohit.administrator.tyut.fragment.two.KeBiaoActivity;
import com.lenovohit.administrator.tyut.fragment.two.LastScoreActivity;
import com.lenovohit.administrator.tyut.fragment.two.XueFenActivity;
import com.lenovohit.administrator.tyut.net.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Administrator on 2017/2/28.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LoginActivity activity);
    void inject(SchhoolNewsFragment fragment);
    void inject(AcademicFragment fragment);
    void inject(SchoolPlayFragment fragment);
    void inject(NoticeFragment fragment);
    void inject(SplashActivity activity);
    void inject(CurrentScoreActivity activity);
    void inject(BuScoreActivity activity);
    void inject(AllScoreActivity activity);
    void inject(LastScoreActivity activity);
    void inject(XueFenActivity activity);
    void inject(KeBiaoActivity activity);
    void inject(CengKeActivity activity);

}
