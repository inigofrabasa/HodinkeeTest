package com.inigofrabasa.hodinkeetest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory
@Inject constructor(private val generators: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(viewmodelClass: Class<T>): T {
        val creator = generators[viewmodelClass] ?:
        generators.asIterable().firstOrNull { viewmodelClass.isAssignableFrom(it.key) }?.value ?:
        throw IllegalArgumentException("Unknown ViewModel class $viewmodelClass")

        return try {
            @Suppress("UNCHECKED_CAST")
            creator.get() as T
        }
        catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}