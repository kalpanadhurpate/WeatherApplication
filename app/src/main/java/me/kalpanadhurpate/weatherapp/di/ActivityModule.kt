package me.kalpanadhurpate.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import me.kalpanadhurpate.weatherapp.ui.CityListAdapter


@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @ActivityScoped
    @Provides
    fun provideTopHeadlineAdapter() = CityListAdapter(ArrayList())

}