package com.example.ramyeon_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ramyeon_app.databinding.BoardMainRecyclerItemBinding
import com.example.ramyeon_app.databinding.FragmentBoardMainBinding

class BoardMainFragment : Fragment() {

    lateinit var boardMainFragmentBinding : FragmentBoardMainBinding

    val boardListTitle = arrayOf(
        "전체글",  "레시피",  "명예의 전당"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        boardMainFragmentBinding = FragmentBoardMainBinding.inflate(inflater)
        boardMainFragmentBinding.boardMainToolbar.title = "게시판 이름"


        boardMainFragmentBinding.boardMainToolbar.inflateMenu(R.menu.board_main_menu)   // 메뉴 설정
        boardMainFragmentBinding.boardMainToolbar.setOnMenuItemClickListener{
           when (it.itemId) {
               // 게시판 메뉴 아이콘을 누른 경우
               R.id.board_main_menu_board_list -> {
                   val boardListBuilder = AlertDialog.Builder(requireContext())
                   boardListBuilder.setTitle("게시판 선택")
                   boardListBuilder.setNegativeButton("Cancel", null)
                   boardListBuilder.setItems(boardListTitle, null)
                   boardListBuilder.show()
                   true
               }

               // 글 쓰기 아이콘을 누른 경우
               R.id.board_main_menu_write -> {
                   val act = activity as BoardMainActivity
                   act.fragmentController("board_write", true, true)
                   true
               }
               else -> false
           }
        }

        // recycler view 관련
        val boardMainRecyclerAdapter = BoardMainRecyclerAdapter()
        boardMainFragmentBinding.boardMainRecycler.adapter = boardMainRecyclerAdapter

        boardMainFragmentBinding.boardMainRecycler.layoutManager = LinearLayoutManager(requireContext())
        boardMainFragmentBinding.boardMainRecycler.addItemDecoration(DividerItemDecoration(requireContext(), 1))    // 항목 별 구분 선

        return boardMainFragmentBinding.root
    }

    inner class BoardMainRecyclerAdapter : RecyclerView.Adapter<BoardMainRecyclerAdapter.ViewHolderClass>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val boardMainRecyclerItemBinding = BoardMainRecyclerItemBinding.inflate(layoutInflater)
            val holder = ViewHolderClass(boardMainRecyclerItemBinding)

            val layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,    // 가로
                ViewGroup.LayoutParams.WRAP_CONTENT     // 세로 길이
            )

            boardMainRecyclerItemBinding.root.layoutParams = layoutParams
            boardMainRecyclerItemBinding.root.setOnClickListener(holder)

            return holder
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        }

        override fun getItemCount(): Int {
            return 30
        }

        inner class ViewHolderClass(boardMainRecyclerItemBinding: BoardMainRecyclerItemBinding)
            : RecyclerView.ViewHolder(boardMainRecyclerItemBinding.root), View.OnClickListener {
                //  게시글 클릭 시 내용 보기
                override fun onClick(v: View?) {
                    val act =  activity as BoardMainActivity
                    act.fragmentController("board_read", true, true)
                }
            }
    }

}