package com.example.ramyeon_app

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.ramyeon_app.databinding.FragmentLoginBinding
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class LoginFragment : Fragment() {

    lateinit var  loginFragmentBinding : FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loginFragmentBinding = FragmentLoginBinding.inflate(inflater)
        loginFragmentBinding.loginToolbar.title = "LOGIN"   // title 설정

        loginFragmentBinding.loginSignupBtn.setOnClickListener{
            val act = activity as MainActivity
            act.fragmentController("signup", true, true)
        }

        loginFragmentBinding.loginLoginBtn.setOnClickListener{

            val loginId = loginFragmentBinding.loginId.text.toString()  // 입력 아이디 값
            val loginPw = loginFragmentBinding.loginPw.text.toString()  // 입력 비밀번호 값
            val check = loginFragmentBinding.loginAutologin.isChecked   // 자동 로그인 체크 여부

            var loginAuto = 0
            if (check) {
                loginAuto = 1
            } else {
                loginAuto = 0
            }

            // id 유효성 검사
            if(loginId == null || loginId.length == 0) {
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setTitle("아이디 오류")
                dialogBuilder.setMessage("아이디를 제대로 입력해 주세요")
                dialogBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int -> loginFragmentBinding.loginId.requestFocus() }
                dialogBuilder.show()
                return@setOnClickListener
            }

            // pw 유효성 검사
            if(loginPw == null || loginPw.length == 0) {
                val dialogBuilder = AlertDialog.Builder(requireContext())
                dialogBuilder.setTitle("비밀번호 오류")
                dialogBuilder.setMessage("비밀번호를 제대로 입력해 주세요")
                dialogBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int -> loginFragmentBinding.loginPw.requestFocus() }
                dialogBuilder.show()
                return@setOnClickListener
            }

            thread {
                var client = OkHttpClient()
                val site = "http://192.168.1.42:8080/ramyeon_server/login_user.jsp"

                val builder = FormBody.Builder()
                builder.add("user_id", loginId)
                builder.add("user_pw", loginPw)
                val formBody = builder.build()

                val request = Request.Builder().url(site).post(formBody).build()
                val response = client.newCall(request).execute()

                if(response.isSuccessful) {
                    // 응답 결과 추출
                    val result_text = response.body?.string()!!.trim() // 서버로 부터 받아온 결과물
                    // Log.d("text", result_text)
                    if(result_text == "fail") {
                        activity?.runOnUiThread{
                            val dialogBuilder = AlertDialog.Builder(requireContext())
                            dialogBuilder.setTitle("Fail to Login")
                            dialogBuilder.setMessage("아이디 혹은 비밀번호가 잘못되었습니다.")
                            dialogBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                loginFragmentBinding.loginId.setText("")    // 빈 공간으로 초기화
                                loginFragmentBinding.loginPw.setText("")    // 빈 공간으로 초기화
                                loginFragmentBinding.loginAutologin.isChecked = false
                                loginFragmentBinding.loginId.requestFocus()
                            }
                            dialogBuilder.show()
                        }
                    } else {
                        activity?.runOnUiThread{
                            val dialogBuilder = AlertDialog.Builder(requireContext())
                            dialogBuilder.setTitle("Login Success")
                            dialogBuilder.setMessage("환영합니다.")
                            dialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                // 사용자 정보를 preferences에 저장하기
                                val pref = activity?.getSharedPreferences("login_data", Context.MODE_PRIVATE)
                                val editor = pref?.edit()

                                editor?.putInt("login_user_idx", Integer.parseInt(result_text)) // 서버에서 받는 데이터는 String타입 이므로 parseInt
                                editor?.putInt("login_auto_login", loginAuto)
                                editor?.commit()

                                val boardMainIntent = Intent(requireContext(), BoardMainActivity::class.java)
                                 startActivity(boardMainIntent)
                                activity?.finish()  // 로그인 관련 액티비티 종료 - 뒤로가기 눌러도 안나옴
                            }
                            dialogBuilder.show()
                        }
                    }
                }
                // 통신이 비정상일 때
                else {
                    activity?.runOnUiThread{
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setTitle("login Error")
                        dialogBuilder.setMessage("로그인 에러 발생")
                        dialogBuilder.setPositiveButton("확인",  null)
                        dialogBuilder.show()
                    }
                }
            }
        }

        return loginFragmentBinding.root
    }
}