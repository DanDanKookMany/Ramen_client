package com.example.ramyeon_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.ramyeon_app.databinding.FragmentBoardModifyBinding

class BoardModifyFragment : Fragment() {
    lateinit var boardModifyFragmentBinding: FragmentBoardModifyBinding

    val spinnerData = arrayOf("게시판 1", "게시판 2", "게시판 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        boardModifyFragmentBinding = FragmentBoardModifyBinding.inflate(inflater)

        boardModifyFragmentBinding.boardModifyToolbar.title = "글 수정"
        boardModifyFragmentBinding.boardModifyToolbar.inflateMenu(R.menu.board_modify_menu)
        boardModifyFragmentBinding.boardModifyToolbar.setOnMenuItemClickListener{
            when(it.itemId) {
                // 카메라 아이콘 눌렀을 떄
                R.id.board_modify_menu_cam -> {
                    true
                }
                // 갤러리 아이콘 눌렀을 때
                R.id.board_modify_menu_gallery -> {
                    true
                }
                // upload 아이콘 눌렀을 때
                R.id.board_modify_menu_upload -> {
                    val act = activity as BoardMainActivity
                    act.fragmentRemoveBackStack("board_modify")
                    true
                }
                else -> false
            }
        }

        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerData)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        boardModifyFragmentBinding.boardModifyType.adapter = spinnerAdapter

        return boardModifyFragmentBinding.root
    }
}