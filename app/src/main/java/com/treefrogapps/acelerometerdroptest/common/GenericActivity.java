package com.treefrogapps.acelerometerdroptest.common;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.treefrogapps.acelerometerdroptest.presenter.Presenter;


/**
 * Generic Base Activity - used to initialise presenter layer, and other generic tasks
 * common to all applicable activities
 * <p/>
 * Generics used to define the ViewInterface and Presenter Class references.  'Generic' objects
 * can 'extend' interfaces using the 'extend' keyword, instead of implements - the compiler doesn't
 * follow usual convention of 'implements' with interfaces when used in conjunction with Generics
 *
 * @param <ViewInterface>       Generic view interface (defined when Generic Activity is extended)
 * @param <PresenterViewOps>    Generic PresenterViewOps interface (defined when Generic Activity is extended)
 * @param <PresenterInstance>   Generic Presenter Class (defined with Generic Activity is extended)
 *                        'Presenter' extends just the interface required by Generic Activity
 *                        the interface Presenter uses is the MVP.PresenterInterface (which
 *                        extends PresenterOperations)
 */
public abstract class GenericActivity<ViewInterface,
                                      PresenterViewOps,
                                      PresenterInstance extends PresenterOps<ViewInterface>>
                                      extends AppCompatActivity implements ContextView {

    private static final String TAG = GenericActivity.class.getSimpleName();

    /**
     * Presenter Instance Class Variable
     */
    private PresenterInstance mPresenter;

    /**
     * Create new RetainedFragmentManager - this creates a Fragment that has: setRetainInstance(true)
     * and holds a Presenter object in a HashMap
     */
    private final RetainedFragmentManager mRetainedFragmentManager
            = new RetainedFragmentManager(this.getSupportFragmentManager());


    /**
     * Method to initialise/reinitialise Presenter Layer and its reference to the View Layer
     * (not to be confused with the Activity Life Cycle Hook Method with the same name)
     *
     * @param view          Generic View Interface
     * @param presenter     Generic as Class object (required to have access to class method newInstance())
     */
    public void onCreate(ViewInterface view, Class<PresenterInstance> presenter){


        if (mRetainedFragmentManager.firstTimeIn()){

            Log.d(TAG, "Initialising Presenter");

            initialisePresenter(view, presenter);

        } else {

            Log.d(TAG, "Reinitialising Presenter");

            reinitialisePresenter(view);
        }

    }

    /**
     * Method to initialise the Generic Presenter Layer
     *
     * @param view          Generic View Interface
     * @param presenter     Generic as Class object (required to have access to class method newInstance())
     *                      the generic Presenter is implicit reference - when Generic Activity is extended
     *                      the Explicit reference is made (example - see App Activity)
     */
    public void initialisePresenter(ViewInterface view, Class<PresenterInstance> presenter){

        try {
            mPresenter = presenter.newInstance();

            mRetainedFragmentManager.putObject(Presenter.TAG, mPresenter);

            mPresenter.onCreate(view);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to reinitialise the Generic Presenter Layer, uses the Retained Fragment Manager /
     * Retained Fragment to retrieve the Presenter from a HashMap and passed onto the Presenter
     * to establish a new Weak Reference to the View Layer
     *
     * @param view Generic View Interface
     */
    public void reinitialisePresenter(ViewInterface view){

        mPresenter = mRetainedFragmentManager.getObject(Presenter.TAG);

        mPresenter.onConfigurationChange(view);
    }


    @SuppressWarnings("unchecked")
    public PresenterViewOps getPresenter(){

       return (PresenterViewOps) mPresenter;
    }

    public RetainedFragmentManager getmRetainedFragmentManager(){
        return mRetainedFragmentManager;
    }


    /**
     * Method to return a Context
     *
     * @return current Activity context
     */
    @Override
    public Context getActivityContext() {
        return this;
    }

    /**
     * Method to return a Context
     *
     * @return current Application context
     */
    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    /**
     * Method to return a Fragment Manager instance
     *
     * @return fragment manager instance
     */
    @Override
    public FragmentManager getFragManager() {
        return getSupportFragmentManager();
    }
}
