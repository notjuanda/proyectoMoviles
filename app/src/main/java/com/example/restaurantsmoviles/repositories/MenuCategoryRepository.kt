package com.example.restaurantsmoviles.repositories

import com.example.restaurantsmoviles.api.ApiService
import com.example.restaurantsmoviles.model.MenuCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MenuCategoryRepository {

    private val retrofit = RetrofitRepository.getRetrofitInstance()
    private val service: ApiService = retrofit.create(ApiService::class.java)

    fun getMenuCategories(restaurantId: Int, success: (List<MenuCategory>?) -> Unit, failure: (Throwable) -> Unit) {
        service.getMenuCategories(restaurantId).enqueue(object : Callback<List<MenuCategory>> {
            override fun onResponse(call: Call<List<MenuCategory>>, response: Response<List<MenuCategory>>) {
                success(response.body())
            }

            override fun onFailure(call: Call<List<MenuCategory>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertMenuCategory(menuCategory: MenuCategory, success: (MenuCategory?) -> Unit, failure: (Throwable) -> Unit) {
        service.insertMenuCategory(menuCategory).enqueue(object : Callback<MenuCategory> {
            override fun onResponse(call: Call<MenuCategory>, response: Response<MenuCategory>) {
                success(response.body())
            }

            override fun onFailure(call: Call<MenuCategory>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getMenuCategory(id: Int, success: (MenuCategory?) -> Unit, failure: (Throwable) -> Unit) {
        service.getMenuCategory(id).enqueue(object : Callback<MenuCategory?> {
            override fun onResponse(call: Call<MenuCategory?>, response: Response<MenuCategory?>) {
                success(response.body())
            }

            override fun onFailure(call: Call<MenuCategory?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateMenuCategory(menuCategory: MenuCategory, success: (MenuCategory?) -> Unit, failure: (Throwable) -> Unit) {
        val menuCategoryId = menuCategory.id ?: 0
        service.updateMenuCategory(menuCategory, menuCategoryId).enqueue(object : Callback<MenuCategory> {
            override fun onResponse(call: Call<MenuCategory>, response: Response<MenuCategory>) {
                success(response.body())
            }

            override fun onFailure(call: Call<MenuCategory>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteMenuCategory(id: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        service.deleteMenuCategory(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                success()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}
