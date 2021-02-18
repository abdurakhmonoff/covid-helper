package com.abdurakhmonoff.covidhelper.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abdurakhmonoff.covidhelper.R
import com.abdurakhmonoff.covidhelper.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val app = requireNotNull(activity).application
        ViewModelProvider(this, MainViewModelFactory(app)).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.apply {
            viewCovidStatsBtn.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToStatsFragment())
            }
            learnCovidSymptomsBtn.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Coronavirus symptoms")
                    .setMessage(
                        "Most common symptoms:" +
                                "\n - fever" +
                                "\n - dry cough" +
                                "\n - tiredness" +
                                "\n\n" +
                                "Less Common symptoms:" +
                                "\n - aches and pains" +
                                "\n - sore throat" +
                                "\n - diarrhoea" +
                                "\n - conjunctivitis" +
                                "\n - headache" +
                                "\n - loss of taste or smell" +
                                "\n - a rash on skin, or discolouration of fingers or toes" +
                                "\n\n" +
                                "Serious symptoms:" +
                                "\n - difficulty breathing or shortness of breath" +
                                "\n - chest pain or pressure" +
                                "\n - loss of speech or movement"
                    )
                    .setPositiveButton("OK", null)
                    .show()
            }
            learnMoreCovidBtn.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToInfoFragment())
            }
        }

        return binding.root
    }

}