package com.example.ramyeon_app

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ramyeon_app.databinding.FragmentBoardMainBinding
import com.example.ramyeon_app.databinding.FragmentBoardReadBinding

class BoardReadFragment : Fragment() {

    lateinit var boardReadFragmentBinding : FragmentBoardReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        boardReadFragmentBinding = FragmentBoardReadBinding.inflate(inflater)
        boardReadFragmentBinding.boardReadToolbar.title =  "게시글 내용"

        val navIcon = requireContext().getDrawable(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        boardReadFragmentBinding.boardReadToolbar.navigationIcon = navIcon

        // android Q 버전 이상인 경우 처리
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            boardReadFragmentBinding.boardReadToolbar.navigationIcon?.colorFilter = BlendModeColorFilter(Color.parseColor("#FFFFFF"), BlendMode.SRC_ATOP)
        }
        // android Q 버전 미만
        else {
            boardReadFragmentBinding.boardReadToolbar.navigationIcon?.setColorFilter(
                Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP
            )
        }

        // 뒤로 가기 버튼 눌렀을 때
        boardReadFragmentBinding.boardReadToolbar.setNavigationOnClickListener{
            val act = activity as BoardMainActivity
            act.fragmentRemoveBackStack("board_read")   // 뒤로가기 처리
        }

        boardReadFragmentBinding.boardReadToolbar.inflateMenu(R.menu.board_read_menu)

        // 뒤로 가기 버튼 눌렀을 때
        boardReadFragmentBinding.boardReadToolbar.setOnMenuItemClickListener{
            when(it.itemId) {
                // 글 변경 아이콘 클릭
                R.id.board_read_menu_modify -> {
                    val act = activity as BoardMainActivity
                    act.fragmentController("board_modify", true ,true)
                    true
                }

                // 글 삭제 아이콘 클릭
                R.id.board_read_menu_delete -> {
                    val act =activity as BoardMainActivity
                    act.fragmentRemoveBackStack("board_read")
                    true
                }

                else -> false
            }
        }

        return boardReadFragmentBinding.root
    }

}