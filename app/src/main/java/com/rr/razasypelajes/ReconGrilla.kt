package com.rr.razasypelajes

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

class ReconGrilla(var context: Reconocimiento) : ReconMode {
    private lateinit var recycler : RecyclerView
    private var adapter : AdapterGrid = AdapterGrid(context)

    override fun runRecon() {
        context.setContentView(R.layout.recon_grid)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler = context.findViewById(R.id.gridRecycler)
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(context, 3)
    }
}