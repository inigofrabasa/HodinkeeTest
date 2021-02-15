package com.inigofrabasa.hodinkeetest

import android.app.Application
import com.inigofrabasa.hodinkeetest.di.ApplicationModule
import com.inigofrabasa.hodinkeetest.di.ApplicationComponent
import com.inigofrabasa.hodinkeetest.di.DaggerApplicationComponent
import com.inigofrabasa.hodinkeetest.di.DatabaseModule

class HodinkeeApplication : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .databaseModule(DatabaseModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}