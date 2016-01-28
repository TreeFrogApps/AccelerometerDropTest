package com.treefrogapps.acelerometerdroptest.common;

/**
 * Model Operation Interface
 */
public interface ModelOps<PresenterInterface> {

    void onCreate(PresenterInterface presenter);

    void onDestroy();
}
