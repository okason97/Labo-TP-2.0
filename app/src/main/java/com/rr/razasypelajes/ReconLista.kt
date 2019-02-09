package com.rr.razasypelajes

class ReconLista(var context: Reconocimiento) : ReconMode {
    override fun setView() {
        context.setContentView(R.layout.recon_list)
    }
}