package com.junpu.calculation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.junpu.calculation.databinding.FragmentPrepareBinding

/**
 * Prepare Page.
 * @author junpu
 * @date 2020/7/17
 */
class PrepareFragment : Fragment() {

    private val viewModel: OralViewModel by activityViewModels()
    private var binding: FragmentPrepareBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentPrepareBinding>(
            inflater,
            R.layout.fragment_prepare,
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
        binding?.btnStart?.setOnClickListener {
            findNavController().navigate(R.id.action_prepareFragment_to_oralFragment)
        }
    }

}