package com.payb.imgpix.di.component

import com.payb.imgpix.di.annotation.PerActivity
import com.payb.imgpix.di.module.ImgPixModule
import com.payb.imgpix.ui.DetailFragment
import com.payb.imgpix.ui.ListFragment
import com.payb.imgpix.ui.MainActivity
import dagger.Component

@PerActivity
@Component(dependencies = [AppComponent::class], modules = [ImgPixModule::class])
interface ImgPixComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(listFragment: ListFragment)

    fun inject(detailFragment: DetailFragment)
}