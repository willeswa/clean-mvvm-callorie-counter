package app.monkpad.caloriecounter.presentation.searchscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import app.monkpad.caloriecounter.R
import app.monkpad.caloriecounter.databinding.SearchFragmentBinding
import app.monkpad.caloriecounter.framework.CalorieCounterViewModelFactory
import app.monkpad.caloriecounter.utils.Utility

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels {
        CalorieCounterViewModelFactory
    }

    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.calculating.observe(viewLifecycleOwner, {
            if (it) {
                val food = binding.editTextFoodName.editText?.text.toString().trimEnd()
                val action = SearchFragmentDirections.actionSearchToDetails(food)
                if (food.isNotEmpty()) {
                    findNavController().navigate(action)
                } else {
                    viewModel.finishCalculating()
                    Utility.showToastMessage(resources.getString(R.string.bad_food_error),
                        requireContext())
                }
            }
        })
    }
}