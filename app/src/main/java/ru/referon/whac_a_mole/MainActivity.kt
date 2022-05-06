package ru.referon.whac_a_mole

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.referon.whac_a_mole.databinding.ActivityMainBinding
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, GameActivity::class.java)
        var id = 0
        window.navigationBarColor = Color.parseColor("#9EE753")
        window.statusBarColor = Color.parseColor("#FF000000")

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        getRandomText(binding.text)
        binding.text.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.text_animation)
        )

        var maxScore = GET.getString("maxScore", "0")

        binding.topScore.text = "Record score: $maxScore"

        binding.mainMole.startAnimation(
            AnimationUtils.loadAnimation(this,R.anim.button_amination)
        )
        binding.mainMole.setOnClickListener { it.visibility = View.GONE }
        binding.startGameBtn.setOnClickListener {
            finish()
            startActivity(intent)
        }


    }

    fun getRandomText(textView: TextView){
        val listText = listOf(
            getString(R.string.text1),
            getString(R.string.text2),
            getString(R.string.text3),
            getString(R.string.text4),
        )
        val rand = (0..3).random()
        textView.text = listText[rand]
    }
}