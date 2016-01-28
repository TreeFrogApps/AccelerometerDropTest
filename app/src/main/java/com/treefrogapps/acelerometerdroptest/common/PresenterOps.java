package com.treefrogapps.acelerometerdroptest.common;

/**
 * Presenter Operations Interface
 */
public interface PresenterOps<ViewInterface> {

    void onCreate(ViewInterface view);

    void onConfigurationChange(ViewInterface view);

    void onDestroy(boolean isChangingConfigurations);
}
