package ru.referon.whac_a_mole

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.referon.whac_a_mole.databinding.ActivityScoresBinding

class ScoresActivity : AppCompatActivity() {

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityScoresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = Color.parseColor("#9EE753")
        window.statusBarColor = Color.parseColor("#FF000000")
        val intentMenu = Intent(this,MainActivity::class.java)
        val intentGame = Intent(this,GameActivity::class.java)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        var cType = GET.getString("myScore", "0") ?: "0"
        var maxScore = GET.getString("maxScore", "0") ?: "0"

        binding.yourScores.text = "You swatted $cType moles"
        binding.maxScores.text = "Record: $maxScore"
        if (cType.toInt() >= maxScore.toInt()){
            SET.putString("maxScore", cType)
            SET.apply()
        }
        binding.menu.setOnClickListener {
            finish()
            startActivity(intentMenu)
        }
        binding.playReturn.setOnClickListener {
            finish()
            startActivity(intentGame)
        }
    }
}