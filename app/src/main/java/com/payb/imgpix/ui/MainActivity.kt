package com.payb.imgpix.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.payb.imgpix.ImgPixApplication
import com.payb.imgpix.R
import com.payb.imgpix.di.component.DaggerImgPixComponent
import com.payb.imgpix.di.component.ImgPixComponent
import com.payb.imgpix.di.module.ImgPixModule

/**
 * MainActivity class that serves as the default Navigation Host for fragments
 * in the Navigation Graph
 */
class MainActivity : AppCompatActivity() {

    private lateinit var imgPixComponent: ImgPixComponent

    @SuppressLint("MissingSuperCall")
    public override fun onCreate(savedInstanceState: Bundle?) {
        superOnCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getActivityComponent().inject(this)
    }

    internal fun superOnCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Method to get the activity component
     * @return activityComponent of type [ImgPixComponent]
     */
    fun getActivityComponent(): ImgPixComponent {

        imgPixComponent = DaggerImgPixComponent.builder()
            .imgPixModule(ImgPixModule(this))
            .appComponent(getApplicationInstance().getComponent())
            .build()

        return imgPixComponent
    }

    /**
     * Method to fetch the [ImgPixApplication] instance
     * @return [ImgPixApplication] instance
     */
    fun getApplicationInstance(): ImgPixApplication {
        return ImgPixApplication.get(this)
    }

}