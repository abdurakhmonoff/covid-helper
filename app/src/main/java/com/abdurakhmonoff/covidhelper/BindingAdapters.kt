package com.abdurakhmonoff.covidhelper

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdurakhmonoff.covidhelper.models.domainmodels.Country
import com.abdurakhmonoff.covidhelper.ui.stats.CountryListAdapter
import com.abdurakhmonoff.covidhelper.ui.stats.CurrentCountryStatus
import com.squareup.picasso.Picasso

@BindingAdapter("android:countryList")
fun countryList(recyclerView: RecyclerView, countryList:List<Country>?){
    val adapter = recyclerView.adapter as CountryListAdapter
    adapter.submitList(countryList)
}

@BindingAdapter("android:countryImage")
fun setImage(imageView: ImageView,url:String?){
    url?.let {
        val uri = url.toUri().buildUpon().scheme("https").build()
        Picasso.get().load(uri).placeholder(R.drawable.flag_placeholder).error(R.drawable.image_not_found).into(imageView)
    }
}

@BindingAdapter("android:progressStatus")
fun loadingCurrentCountry(progressBar: ProgressBar, statusCurrent: CurrentCountryStatus?){
    when(statusCurrent){
        CurrentCountryStatus.DONE -> progressBar.visibility = View.GONE
        CurrentCountryStatus.ERROR -> progressBar.visibility = View.GONE
        CurrentCountryStatus.LOADING -> progressBar.visibility = View.VISIBLE
    }
}