package com.softsquared.template.kotlin.src.main.mainHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.softsquared.template.kotlin.R


class listFragment : Fragment() {

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val database = Firebase.database
        val myRef = database.getReference("postRoom")

        val dataList : ArrayList<MainData> = ArrayList()

        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_list,container,false)

        var rv = rootView.findViewById(R.id.list_recyclerView!!)as RecyclerView
        var rvAdapter = ListAdapter(dataList,requireContext())
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        myRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                var postRoom =  dataSnapshot.getValue(MainData::class.java)
                var name = postRoom?.name
                var Content = postRoom?.content

                dataList.add(MainData(name.toString(), "위치 비밀",10,Content.toString()))
                rvAdapter.notifyDataSetChanged()
            }
            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })


        return rootView


    }


}