package com.junpu.calculation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.junpu.calculation.databinding.FragmentResultBinding

/**
 * Oral Calculation Result，Win or Lose.
 * @author junpu
 * @date 2020/7/17
 */
class ResultFragment : Fragment() {

    private val viewModel: OralViewModel by activityViewModels()
    private var binding: FragmentResultBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentResultBinding>(
            inflater,
            R.layout.fragment_result,
            container,
            false
        ).apply {
            vm = viewModel
            lifecycleOwner = activity
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnBack?.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_prepareFragment)
        }
    }
}