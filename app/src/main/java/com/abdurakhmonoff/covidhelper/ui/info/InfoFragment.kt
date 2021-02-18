package com.abdurakhmonoff.covidhelper.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.abdurakhmonoff.covidhelper.R
import com.abdurakhmonoff.covidhelper.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private lateinit var titleList: List<String>
    private lateinit var dataList: HashMap<String, String>
    private var lastExpandedPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        setDataForExpandableListView()

        val elv = binding.questionsList

        elv.setAdapter(ExpandableListAdapter(requireContext(), titleList, dataList))
        elv.setOnGroupExpandListener { grPos ->
            if (lastExpandedPosition != -1 && grPos != lastExpandedPosition) {
                elv.collapseGroup(lastExpandedPosition)
            }
            lastExpandedPosition = grPos
        }

        return binding.root
    }

    private fun setDataForExpandableListView() {
        titleList = listOf(
            "What does COVID-19 stand for?",
            "What are the types of coronavirus?",
            "Which is the official name of the coronavirus disease?",
            "Is COVID-19 caused by a virus or a bacteria?",
            "How do viruses get their name?",
            "When did the coronavirus disease receive its name?",
            "How many different human coronaviruses are there?",
            "What is the differences between tuberculosis and COVID-19?",
            "What is the difference between the coronavirus and the influenza virus?",
            "Can the coronavirus survive on surfaces?"
        )
        dataList = hashMapOf()
        dataList[titleList[0]] =
            "'CO' stands for corona, 'VI' for virus, and 'D' for disease. Formerly, this disease was referred to as '2019 novel coronavirus' or '2019-nCoV.' The COVID-19 virus is a new virus linked to the same family of viruses as Severe Acute Respiratory Syndrome (SARS) and some types of common cold."
        dataList[titleList[1]] =
            "Coronaviruses are a large family of viruses that are known to cause illness ranging from the common cold to more severe diseases such as Middle East Respiratory Syndrome (MERS) and Severe Acute Respiratory Syndrome (SARS)."
        dataList[titleList[2]] =
            "The final name of the disease will be provided by the International Classification of Diseases (ICD). WHO is also proposing '2019-nCoV' as an interim name of the virus. The final decision on the official name of the virus will be made by the International Committee on Taxonomy of Viruses."
        dataList[titleList[3]] =
            "The coronavirus disease (COVID-19) is caused by a virus, NOT by bacteria."
        dataList[titleList[4]] =
            "Viruses are named based on their genetic structure to facilitate the development of diagnostic tests, vaccines and medicines. Virologists and the wider scientific community do this work, so viruses are named by the International Committee on Taxonomy of Viruses (ICTV)."
        dataList[titleList[5]] =
            "The International Committee on Taxonomy of Viruses (ICTV) announced “severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)” as the name of the new virus on 11 February 2020. This name was chosen because the virus is genetically related to the coronavirus responsible for the SARS outbreak of 2003. While related, the two viruses are different."
        dataList[titleList[6]] =
            "Six species of human coronaviruses are known, with one species subdivided into two different strains, making seven strains of human coronaviruses altogether."
        dataList[titleList[7]] =
            "Tuberculosis (TB) and COVID-19 are both infectious diseases that attack primarily the lungs. Both diseases have similar symptoms such as cough, fever and difficulty breathing. TB, however, has a longer incubation period with a slower onset of disease."
        dataList[titleList[8]] =
            "The speed of transmission is an important point of difference between the two viruses. Influenza has a shorter median incubation period (the time from infection to appearance of symptoms) and a shorter serial interval (the time between successive cases) than COVID-19 virus. The serial interval for COVID-19 virus is estimated to be 5-6 days, while for influenza virus, the serial interval is 3 days. This means that influenza can spread faster than COVID-19."
        dataList[titleList[9]] =
            "It is not certain how long the virus that causes COVID-19 survives on surfaces, but it seems likely to behave like other coronaviruses. A recent review of the survival of human coronaviruses on surfaces found large variability, ranging from 2 hours to 9 days (11)."
    }

}