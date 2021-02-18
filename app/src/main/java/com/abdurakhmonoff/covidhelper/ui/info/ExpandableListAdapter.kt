package com.abdurakhmonoff.covidhelper.ui.info

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.abdurakhmonoff.covidhelper.R
import kotlin.math.sign

class ExpandableListAdapter(private val context: Context, private val titleList:List<String>, private val dataList:HashMap<String,String>):BaseExpandableListAdapter() {
    override fun getGroupCount(): Int = titleList.size

    override fun getChildrenCount(groupPosition: Int): Int = 1

    override fun getGroup(groupPosition: Int): String {
        return titleList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String? {
        return dataList[titleList[groupPosition]]
    }

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var convertView = convertView
        val listTitle = getGroup(groupPosition)
        if (convertView==null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.expandable_list_group,null)
        }
        val title = convertView?.findViewById<TextView>(R.id.tv_title)
        title?.text = listTitle
        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var convertView = convertView
        val listTitle = getChild(groupPosition,childPosition)
        if (convertView==null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.expandable_list_item,null)
        }
        val title = convertView?.findViewById<TextView>(R.id.tv_item)
        title?.text = listTitle
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}