package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.MainRepository
import com.example.myapplication.MyViewModelFactory
import com.example.myapplication.R
import com.example.myapplication.RetrofitService
import com.example.myapplication.model.Author

class MainFragment : Fragment(), LifecycleOwner {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var recyclerView: RecyclerView? = null
    private var authorListAdapter: AuthorListAdapter? = null
    private var authorList: ArrayList<Author> = ArrayList()
    private val retrofitService = RetrofitService.getInstance()
    lateinit var swipeContainer: SwipeRefreshLayout
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_list)
        swipeContainer = view.findViewById(R.id.swipe_container)
        progressBar = view.findViewById(R.id.progress_bar)

        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        );

        authorListAdapter = AuthorListAdapter(authorList)

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(MainRepository(retrofitService))
        )[MainViewModel::class.java]
        recyclerView?.adapter = authorListAdapter

        viewModel.getAuthorMutableLiveData().observe(viewLifecycleOwner, userListUpdateObserver)

        viewModel.getErrorMessage().observe(viewLifecycleOwner, Observer {
            progressBar.visibility = View.GONE
            swipeContainer.isRefreshing = false
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        swipeContainer.setOnRefreshListener {
            viewModel.populateList()
        }

        progressBar.visibility = View.VISIBLE
        viewModel.populateList()
    }

    private var userListUpdateObserver: Observer<ArrayList<Author>> =
        object : Observer<ArrayList<Author>> {

            override fun onChanged(authorArrayList: ArrayList<Author>) {
                progressBar.visibility = View.GONE
                swipeContainer.isRefreshing = false
                authorListAdapter?.updateUserList(authorArrayList)
            }
        }
}
