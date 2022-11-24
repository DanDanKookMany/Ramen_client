package com.example.ramyeon_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.ramyeon_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainActivityBinding: ActivityMainBinding
    lateinit var curFragment : Fragment

    // 사용자 정보
    var userId = ""
    var userPw = ""
    var userNickName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemClock.sleep(1000)
        setTheme(R.style.Theme_Ramyeon_app)

        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)

        fragmentController("login", false, false)
    }

    fun fragmentController(name: String, add: Boolean, animate: Boolean) {
        // login이 들어왔을 때 처리
        when(name) {
            "login" -> {
                    curFragment = LoginFragment()
            }

            "signup" -> {
                    curFragment = SignupFragment()
            }

            "nickname" -> {
                curFragment = NicknameFragment()
            }
        }

        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.main_container, curFragment)

        if(add) {
            trans.addToBackStack(name)
        }

        if(animate) {
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }

        trans.commit()
    }
}