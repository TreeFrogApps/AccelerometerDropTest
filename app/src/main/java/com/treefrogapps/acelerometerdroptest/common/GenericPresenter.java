package com.treefrogapps.acelerometerdroptest.common;

import android.util.Log;

/**
 * Generic Presenter - used to initialise the model and pass a reference to the Model layer
 * so interactions can be performed
 *
 * @param <PresenterInterface>  The PresenterInterface
 * @param <ModelInterface>  The Model Interface
 * @param <Model>  The Model instance (which implements the ModelInterface which extends ModelOps Interface)
 */
public class GenericPresenter<PresenterInterface,
                              ModelInterface,
                              Model extends ModelOps<PresenterInterface>> {

    private static final String TAG = GenericPresenter.class.getSimpleName();

    /**
     * Model Instance Class variable
     */
    private Model mModel;


    public void onCreate(Class<Model> model, PresenterInterface presenter){

        Log.d(TAG, "onCreate Called");


        /**
         * Initialise the Model layer. Create a new instance and pass
         * in the presenter reference.
         */
        try {

            mModel = model.newInstance();

            mModel.onCreate(presenter);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to return a reference to the Model layer interface methods
     *
     * @return Model layer interface reference
     */
    @SuppressWarnings("unchecked")
    public ModelInterface getModel(){
        return (ModelInterface) mModel;
    }
}
