package com.sameera.displaysocial.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sameera.displaysocial.viewmodel.SearchViewModel
import com.sameera.displaysocial.activity.R
import com.sameera.displaysocial.adapter.UserSearchRecyclerAdapter
import com.sameera.displaysocial.model.User
import com.sameera.displaysocial.model.UserData
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var userSearchRecyclerAdapter: UserSearchRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        val llm = LinearLayoutManager(requireContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        userSearchRecyclerAdapter = UserSearchRecyclerAdapter(requireContext(), emptyList())
        recyclerView.adapter = userSearchRecyclerAdapter
        initObserver(root)
        return root
    }

    private fun initObserver(view: View) {
        searchViewModel.userDataLiveData.observe(viewLifecycleOwner, Observer<List<User>> {
            view.emptyTextView.visibility = if(it.isNullOrEmpty()) View.VISIBLE else View.GONE
            userSearchRecyclerAdapter.users = it
            userSearchRecyclerAdapter.notifyDataSetChanged()
        })

        view.searchEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchViewModel.searchUsers(requireContext(), s.toString())
            }

        })
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }
}