package com.junpu.calculation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.junpu.calculation.databinding.FragmentOralBinding

/**
 * Oral Calculation Page.
 * @author junpu
 * @date 2020/7/17
 */
class OralFragment : Fragment() {

    private val viewModel: OralViewModel by activityViewModels()
    private var binding: FragmentOralBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOralBinding.inflate(
            inflater,
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
        viewModel.newRound()
        binding?.btnSubmit?.setOnClickListener {
            viewModel.submit {
                findNavController().navigate(R.id.action_oralFragment_to_resultFragment)
            }
        }
    }
}