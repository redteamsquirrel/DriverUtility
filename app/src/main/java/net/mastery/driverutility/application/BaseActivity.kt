package net.mastery.driverutility.application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.mastery.driverutility.databinding.ActivityMainBinding


open class BaseActivity : AppCompatActivity() {

  lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root

    setContentView(view)
  }

}