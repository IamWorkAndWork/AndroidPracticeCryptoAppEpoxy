package com.features.crypto.presentation.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.core.Constants
import com.example.core.model.ResultState
import com.example.domain.model.CoinDetailsModel
import com.features.crypto.R
import com.features.crypto.databinding.FragmentItemDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CryptoDetailsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentItemDetailBinding? = null

    private val binding get() = _binding!!

    private val args: CryptoDetailsFragmentArgs by navArgs()

    private val viewModel: CryptoDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            BottomSheetDialogFragment.STYLE_NORMAL,
            com.example.core.R.style.CustomBottomSheetDialogTheme
        );
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root
        return rootView
    }

    override fun onStart() {
        super.onStart()
        dialog?.also {
            val bottomSheet =
                dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from<View>(it)
                behavior.peekHeight =
                    (resources.displayMetrics.heightPixels * 0.7).toInt()
                view?.requestLayout()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchCoinDetails(uuid = args.itemUuid)

        observeViewModel()
        setupListener()

    }

    private fun setupListener() {
        binding.goToWebSiteButton?.setOnClickListener {

            (viewModel.coinDetailsModel.value as? ResultState.Success)?.data?.let {

                val builder = CustomTabsIntent.Builder();
                val customTabsIntent = builder.build();
                customTabsIntent.launchUrl(requireContext(), Uri.parse(it.coinrankingUrl));

            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() = with(viewModel) {

        coinDetailsModel.observe(viewLifecycleOwner) { result ->

            when (result) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Error -> {
                    hideLoading()
                }
                is ResultState.Success -> {
                    hideLoading()
                    renderDetails(result.data)
                }
            }

        }

    }

    private fun renderDetails(model: CoinDetailsModel) = with(binding) {

        iconImageView?.load(model.iconUrl) {
            crossfade(Constants.CROSSFADE_DELAY)
            error(R.drawable.ic_baseline_photo)
        }

        nameTextView?.text = model.nameAndSymbol

        detailsPriceTextView?.text = model.price
        detailsMargetCapTextView?.text = model.marketCap

        detailsTextView?.text = model.description

        labelMargetCapTextView?.text = requireContext().getString(R.string.home_market_cap)
        labelPriceTextView?.text = requireContext().getString(R.string.home_price)

    }

    private fun hideLoading() {
        binding.progressBar?.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBar?.visibility = View.VISIBLE
    }


}