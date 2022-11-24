package com.example.ramyeon_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.ramyeon_app.databinding.ActivityBoardMainBinding

class BoardMainActivity : AppCompatActivity() {

    lateinit var boardMainActivityBinding : ActivityBoardMainBinding
    lateinit var curFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boardMainActivityBinding = ActivityBoardMainBinding.inflate(layoutInflater)
        setContentView(boardMainActivityBinding.root)

        fragmentController("board_main", false, false)
    }

    fun fragmentController(name: String, add:Boolean, animate:Boolean) {
        when(name){
            "board_main" -> {
                curFragment = BoardMainFragment()
            }

            "board_read" -> {
                curFragment = BoardReadFragment()
            }

            "board_write" -> {
                curFragment = BoardWriteFragment()
            }

            "board_modify" -> {
                curFragment = BoardModifyFragment()
            }
        }

        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.board_main_container, curFragment)

        if(add) {
            trans.addToBackStack(name)
        }

        if(animate) {
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }

        trans.commit()
    }

    // 뒤로가기 처리 (스택에서 pop)
    fun fragmentRemoveBackStack(name: String) {
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}