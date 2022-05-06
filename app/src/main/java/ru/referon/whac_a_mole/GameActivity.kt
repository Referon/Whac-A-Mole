package ru.referon.whac_a_mole

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import ru.referon.whac_a_mole.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor
    var textGameTime = 30
    var countdown = 3
    var lastMole = -1
    var scoresCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.navigationBarColor = Color.parseColor("#9EE753")
        window.statusBarColor = Color.parseColor("#FF000000")

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        val list = listOf<ImageView>(
            binding.mole1,
            binding.mole2,
            binding.mole3,
            binding.mole4,
            binding.mole5,
            binding.mole6,
            binding.mole7,
            binding.mole8,
            binding.mole9,
        )

        object : CountDownTimer(3000, 1000) {
            override fun onTick(l: Long) {
                binding.time.visibility = View.GONE
                binding.scores.visibility = View.GONE
                binding.lol1.visibility = View.GONE
                binding.lol2.visibility = View.GONE
                binding.lol3.visibility = View.GONE
                binding.countdown.text = countdown.toString()
                countdown--

            }

            override fun onFinish() {
                startGame(list, binding.scores)
                gameTime(binding.time)
                binding.countdown.visibility = View.GONE
                binding.time.visibility = View.VISIBLE
                binding.scores.visibility = View.VISIBLE
                binding.lol1.visibility = View.VISIBLE
                binding.lol2.visibility = View.VISIBLE
                binding.lol3.visibility = View.VISIBLE
            }
        }.start()
    }

    fun startGame(list: List<ImageView>, scores: TextView) {
        object : CountDownTimer(60000, 500) {
            override fun onTick(l: Long) {
                if (lastMole != -1) {
                    list[lastMole].visibility = View.INVISIBLE
                }
                val rand = (0..8).random()
                println("START______________")
                list[rand].visibility = View.VISIBLE
                list[rand].startAnimation(
                    AnimationUtils.loadAnimation(this@GameActivity, R.anim.view_animation)
                )
                list[rand].setOnClickListener {
                    scoresCount++
                    it.visibility = View.INVISIBLE
                    scores.text = scoresCount.toString()
                }
                lastMole = rand
            }

            override fun onFinish() {
            }
        }.start()
    }

    fun gameTime(time: TextView) {
        object : CountDownTimer(30000, 1000) {
            override fun onTick(l: Long) {
                textGameTime--
                time.text = textGameTime.toString() + " s"
            }

            override fun onFinish() {
                val intent = Intent(this@GameActivity, ScoresActivity::class.java)
                SET.putString("myScore", "$scoresCount")
                SET.apply()
                finish()
                startActivity(intent)
            }
        }.start()
    }
}