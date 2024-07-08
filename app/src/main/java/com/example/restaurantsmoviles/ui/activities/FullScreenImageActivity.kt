package com.example.restaurantsmoviles.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantsmoviles.databinding.ActivityFullScreenImageBinding
import com.example.restaurantsmoviles.model.Photo
import com.example.restaurantsmoviles.ui.adapters.FullScreenImageAdapter

class FullScreenImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullScreenImageBinding
    private lateinit var photoList: List<Photo>
    private var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        photoList = intent.getSerializableExtra("PHOTO_LIST") as? List<Photo> ?: emptyList()
        currentPosition = intent.getIntExtra("CURRENT_POSITION", 0)

        val adapter = FullScreenImageAdapter(this, photoList)
        binding.viewPager.adapter = adapter
        binding.viewPager.setCurrentItem(currentPosition, false)

        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}
