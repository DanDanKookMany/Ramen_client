package com.example.ramyeon_app

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.ramyeon_app.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    lateinit var signupFragmentBinding : FragmentSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        signupFragmentBinding = FragmentSignupBinding.inflate(inflater)
        signupFragmentBinding.signupToolbar.title = "signup"

        signupFragmentBinding.signupNextbtn.setOnClickListener {
            var signupId = signupFragmentBinding.signupId.text.toString()   // id 가져오기  (text에서 입력한 내용을 가져오는데 editable이라는 객체로 가져오므로 toString 처리)
            val signupPw = signupFragmentBinding.signupPw.text.toString()   // pw 가져오기

            // 아무것도 입력하지 않은 경우
            if(signupId == null || signupId.length == 0) {
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setTitle("ID ERROR")
                dialogBuilder.setMessage("ID를 입력해주세요")
                dialogBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int -> signupFragmentBinding.signupId.requestFocus() }
                dialogBuilder.show()
                return@setOnClickListener
            }

            if(signupPw == null || signupPw.length == 0) {
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setTitle("PW ERROR")
                dialogBuilder.setMessage("Password를 입력해주세요")
                dialogBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int -> signupFragmentBinding.signupPw.requestFocus() }
                dialogBuilder.show()
                return@setOnClickListener
            }

            val act = activity as MainActivity

            act.userId = signupId
            act.userPw = signupPw

            act.fragmentController("nickname", true, true)
        }

        return signupFragmentBinding.root
    }
}