package com.rr.razasypelajes

class ReconGrilla(var context: Reconocimiento) : ReconMode {
    override fun setView() {
        context.setContentView(R.layout.recon_grid)
    }
}