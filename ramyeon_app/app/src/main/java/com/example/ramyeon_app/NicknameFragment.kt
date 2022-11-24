package com.example.ramyeon_app

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.ramyeon_app.databinding.FragmentNicknameBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class NicknameFragment : Fragment() {

    lateinit var nickNameFragmentBinding : FragmentNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        nickNameFragmentBinding = FragmentNicknameBinding.inflate(inflater)

        nickNameFragmentBinding.nicknameToolbar.title = "set your nickname"

        nickNameFragmentBinding.nicknameSignupbtn.setOnClickListener{
            val nickNameInput = nickNameFragmentBinding.nicknameNickname.text.toString()

            if(nickNameInput == null || nickNameInput.length == 0) {
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setTitle("Nickname ERROR")
                dialogBuilder.setMessage("nickname을 입력해주세요")
                dialogBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int -> nickNameFragmentBinding.nicknameNickname.requestFocus() }
                dialogBuilder.show()
                return@setOnClickListener
            }

            val act = activity as MainActivity
            act.userNickName = nickNameInput

            // 잘 넘어왔는지 데이터 출력 해보기
//            Log.d("test", act.userId)
//            Log.d("test", act.userPw)
//            Log.d("test", act.userNickName)

            thread  {
                val client = OkHttpClient()

                // 실제 서버를 사면 그거를 넣으면 된다.
                val site = "http://192.168.1.42:8080/ramyeon_server/join_user.jsp"

                // 서버로 보낼 데이터
                var builder1 = FormBody.Builder()
                builder1.add("userId", act.userId)
                builder1.add("userPw", act.userPw)
                builder1.add("userNickName", act.userNickName)
                var formbody = builder1.build()

                var request = Request.Builder().url(site).post(formbody).build()
                var response = client.newCall(request).execute()

                if(response.isSuccessful) {
                    activity?.runOnUiThread{
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setTitle("가입 완료")
                        dialogBuilder.setMessage("가입이 완료되었습니다")
                        dialogBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int -> val mainIntent = Intent(requireContext(), MainActivity::class.java)
                             startActivity(mainIntent)
                             activity?.finish()      // 현재 액티비티 종료
                        }
                        dialogBuilder.show()
                    }
                } else {
                    activity?.runOnUiThread{
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setTitle("가입 오류")
                        dialogBuilder.setMessage("가입 오류 발생")
                        dialogBuilder.setPositiveButton("확인", null)
                        dialogBuilder.show()
                    }
                }
            }
        }

        return nickNameFragmentBinding.root
    }
}