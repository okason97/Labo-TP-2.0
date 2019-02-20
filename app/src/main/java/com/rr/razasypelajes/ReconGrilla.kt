package com.rr.razasypelajes

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

class ReconGrilla(var context: Reconocimiento) : ReconMode {
    private lateinit var recycler : RecyclerView
    private lateinit var enlarged : ImageView
    private var adapter : AdapterGrid = AdapterGrid(context)

    override fun runRecon() {
        context.setContentView(R.layout.recon_grid)
        initRecyclerView()
        initEnlarged()
    }

    private fun initRecyclerView() {
        recycler = context.findViewById(R.id.gridRecycler)
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(context, 3)
    }

    private fun initEnlarged() {
        enlarged = context.findViewById(R.id.gridEnlarged)
    }

    override fun enlarge(view: ImageView) {
        enlarged.setImageDrawable(view.drawable)
        enlarged.visibility = View.VISIBLE
    }
}