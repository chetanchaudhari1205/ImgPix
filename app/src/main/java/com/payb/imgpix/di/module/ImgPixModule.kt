package com.payb.imgpix.di.module

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.payb.imgpix.di.annotation.ActivityContext
import com.payb.imgpix.framework.endpoint.ImgPixApiConfig
import com.payb.imgpix.framework.endpoint.ImgPixApiUrl
import com.payb.imgpix.framework.repository.ImgPixRepository
import com.payb.imgpix.framework.repository.ImgPixRepositoryContract
import com.payb.imgpix.framework.service.ImgPixApi
import com.payb.imgpix.framework.service.ImgPixService
import com.payb.imgpix.framework.service.ImgPixServiceContract
import com.payb.imgpix.framework.viewmodel.ImgPixViewModel
import com.payb.imgpix.framework.viewmodel.ImgPixViewModelContract
import com.payb.imgpix.utils.DialogFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ImgPixModule(private val activity: Activity) {

    @Provides
    @ActivityContext
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun provideDialogFactory(): DialogFactory {
        return DialogFactory()
    }

    @Provides
    fun provideImgPixViewModel(imgPixRepository: ImgPixRepositoryContract): ImgPixViewModelContract =
        ImgPixViewModel(imgPixRepository)

    @Provides
    fun provideImgPixRepository(
        imgPixService: ImgPixServiceContract
    ): ImgPixRepositoryContract =
        ImgPixRepository(imgPixService, activity)

    @Provides
    fun provideImgPixService(
        imgPixApiUrl: ImgPixApiUrl,
        imgPixApi: ImgPixApi
    ): ImgPixServiceContract =
        ImgPixService(imgPixApiUrl, imgPixApi)

    @Provides
    fun providesImgPixApiUrl(imgPixApiConfig: ImgPixApiConfig): ImgPixApiUrl =
        imgPixApiConfig.getImgPixApiParam()

    @Provides
    fun providesImgPixApiConfig(): ImgPixApiConfig = ImgPixApiConfig(activity)

    @Provides
    fun hasNetwork(): Boolean {
        var isConnected = false
        val connectivityManager =
            provideContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    @Provides
    fun createCache(): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return getCache(cacheSize)
    }

    fun getCache(cacheSize: Long): Cache {
        return Cache(provideContext().cacheDir, cacheSize)
    }

    /**
     * Provides the Retrofit object with custom HttpClient for response caching
     * @param imgPixApiUrl [ImgPixApiUrl]
     * @return the Retrofit instance [Retrofit]
     */
    @Provides
    @Reusable
    fun provideRetrofitInterface(imgPixApiUrl: ImgPixApiUrl): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .cache(createCache())
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork())
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(imgPixApiUrl.imgPixApiBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesImgPixApi(retrofit: Retrofit): ImgPixApi = retrofit.create(ImgPixApi::class.java)

}