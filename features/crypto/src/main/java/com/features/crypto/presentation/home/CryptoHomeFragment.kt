package com.features.crypto.presentation.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.CoinModel
import com.features.crypto.R
import com.features.crypto.databinding.FragmentCryptoHomeFragmentBinding
import com.features.crypto.model.UiAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class CryptoHomeFragment : Fragment() {

    private var _binding: FragmentCryptoHomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CryptoHomeViewModel by viewModel()
    private var isPullRoRefresh = false

    companion object {
        private const val SHARE_TYPE = "image/png"
    }

    private val epoxyController by lazy {
        CryptoUiController(
            onCryptoClicked = onCryptoClicked,
            onClickedShare = onClickedShare
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCryptoHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupListener(viewModel.accept)
    }

    private val onCryptoClicked: (CoinModel) -> Unit = {
        val direction = CryptoHomeFragmentDirections.showItemDetail(it.uuid)
        findNavController().navigate(direction)
    }

    private val onClickedShare: (String) -> Unit = { shareUrl ->
        lifecycleScope.launch {

            binding.progressBar.visibility = View.VISIBLE

            val bitmapDeferred = async {
                viewModel.getBitmap(requireContext(), R.drawable.ic_invite_friends)
            }

            val bitmap = bitmapDeferred.await()

            bitmap?.let { _bitmap ->
                shareInviteFriends(_bitmap)
            }

            binding.progressBar.visibility = View.GONE

        }

    }

    private fun shareInviteFriends(bitmap: Bitmap) {
        val bitmapPath = MediaStore.Images.Media.insertImage(
            requireContext().getContentResolver(),
            bitmap,
            getString(com.example.core.R.string.home_text_invite_friends),
            getString(com.example.core.R.string.home_text_invite_frienss_details)
        )
        val bitmapUri: Uri = Uri.parse(bitmapPath)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = SHARE_TYPE
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
        intent.putExtra(
            Intent.EXTRA_TEXT,
            requireContext().getString(com.example.core.R.string.share_invite_friends_url)
        )
        startActivity(Intent.createChooser(intent, null))
    }

    private fun setupListener(onQueryChanged: (UiAction.Search) -> Unit) = with(binding) {

        swipeRefreshLayout.setOnRefreshListener {

            isPullRoRefresh = true
            searchEditText.setText("")
            onQueryChanged(UiAction.Search(query = ""))

        }

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO) {
                val query = searchEditText.text.toString()

                query.trim().let {

                    onQueryChanged(UiAction.Search(query = query))
                    dismissKeyboard()
                }
                true
            } else {
                false
            }
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                val query = s.toString().trim()

                binding.deleteIcon.isVisible = query.isNotEmpty()

                onQueryChanged(UiAction.Search(query = query))

            }
        })

        deleteIcon.setOnClickListener {
            binding.searchEditText.setText("")
        }

        searchEditText.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    binding.deleteIcon.visibility = View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
            //check load state
            epoxyController.addLoadStateListener { loadState ->

                val isLoading = loadState.source.refresh is LoadState.Loading
                epoxyController.isLoading = isLoading && !isPullRoRefresh

                val isLoadMore = loadState.append is LoadState.Loading
                epoxyController.isLoadMore = isLoadMore

                showSwipeRefreshLoading(isLoading)

            }

        }

    }

    private fun showSwipeRefreshLoading(loading: Boolean) {
        if (isPullRoRefresh && loading) {
            binding.swipeRefreshLayout.isRefreshing = true
        } else {
            binding.swipeRefreshLayout.isRefreshing = false
            isPullRoRefresh = false
        }
    }

    private fun observeViewModel() = with(viewModel) {
        lifecycleScope.launchWhenCreated {

            coinsPagingDataFlow.collectLatest { homeUiPagingData ->
                epoxyController.submitData(homeUiPagingData)
            }

        }
    }

    private fun setupRecyclerView() = with(binding) {

        itemListRecyclerView.apply {
            adapter = epoxyController.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    val View.keyboardIsVisible: Boolean
        get() = WindowInsetsCompat
            .toWindowInsetsCompat(rootWindowInsets)
            .isVisible(WindowInsetsCompat.Type.ime())

    fun dismissKeyboard() {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchEditText.getWindowToken(), 0)
    }

}