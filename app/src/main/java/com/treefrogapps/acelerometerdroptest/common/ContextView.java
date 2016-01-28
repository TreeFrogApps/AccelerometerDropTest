package com.treefrogapps.acelerometerdroptest.common;

import android.content.Context;
import android.support.v4.app.FragmentManager;

/**
 * Context View Interface
 */
public interface ContextView {

    Context getActivityContext();

    Context getAppContext();

    FragmentManager getFragManager();
}
