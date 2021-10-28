package com.hadar.findmygif.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hadar.findmygif.R
import com.hadar.findmygif.ui.adapters.GifAdapter
import com.hadar.findmygif.ui.models.Gif
import com.hadar.findmygif.ui.viewmodels.GifsViewModel

class GifsFragment : Fragment() {
    private var gifAdapter = GifAdapter(ArrayList())
    private lateinit var input: String
    private var gifsViewModel: GifsViewModel? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            input = s.toString()
            if (input == "") {
                gifsViewModel?.getTrendingGifs()
            } else {
                gifsViewModel?.onTextChanged(s.toString())
            }
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gifs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGifsViewModel()
        observe()
        initRecyclerView()

        val searchEditText: EditText = view.findViewById(R.id.search_edit_text)
        searchEditText.addTextChangedListener(textWatcher)
    }

    private fun initGifsViewModel() {
        gifsViewModel = ViewModelProvider(this)[GifsViewModel::class.java]
    }

    private fun observe() {
        gifsViewModel?.gifModelListLiveData?.observe(viewLifecycleOwner, Observer { gifsList ->
            if (gifsList != null) {
                gifAdapter.setData(gifsList)
            }

            setRecyclerViewVisibility()
        })

        gifsViewModel?.expandGifLiveEvent?.observe(viewLifecycleOwner, { gifUrlTitlePair ->
            if (gifUrlTitlePair?.first != null && gifUrlTitlePair.second != null) {
                val action =
                    GifsFragmentDirections.actionGifsFragmentToFullScreenGifFragment(
                        gifUrlTitlePair.first!!,
                        gifUrlTitlePair.second!!
                    )
                view?.findNavController()?.navigate(action)
            }
        })
    }


    private fun initRecyclerView() {
        recyclerView = requireView().findViewById(R.id.recycler_view)
        emptyView = requireView().findViewById(R.id.empty_view)
        recyclerView.adapter = gifAdapter

        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = GridLayoutManager(this.context, 2)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this.context, 4)
        }

        gifAdapter.setOnItemClickListener(object : GifAdapter.ClickListener {
            override fun onItemClick(gif: Gif?) {
                gifsViewModel?.onGifItemClicked(gif)
            }
        })
    }

    private fun setRecyclerViewVisibility() {
        if (gifAdapter.isEmpty()) {
            emptyView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

}