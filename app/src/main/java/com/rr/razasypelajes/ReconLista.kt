package com.rr.razasypelajes

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class ReconLista(var context: Reconocimiento) : ReconMode {
    private lateinit var recycler : RecyclerView
    private var adapter : AdapterList = AdapterList(context)

    override fun runRecon() {
        context.setContentView(R.layout.recon_list)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler = context.findViewById(R.id.listRecycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
    }
}