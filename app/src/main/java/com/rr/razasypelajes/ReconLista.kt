package com.rr.razasypelajes

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

class ReconLista(var context: Reconocimiento) : ReconMode {
    private lateinit var recycler : RecyclerView
    private lateinit var enlarged : ImageView
    private var adapter : AdapterList = AdapterList(context)

    override fun runRecon() {
        context.setContentView(R.layout.recon_list)
        initRecyclerView()
        initEnlarged()
    }

    private fun initRecyclerView() {
        recycler = context.findViewById(R.id.listRecycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
    }

    private fun initEnlarged() {
        enlarged = context.findViewById(R.id.listEnlarged)
    }

    override fun enlarge(view: ImageView) {
        enlarged.setImageDrawable(view.drawable)
        enlarged.visibility = View.VISIBLE
    }
}