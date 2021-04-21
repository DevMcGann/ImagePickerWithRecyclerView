package com.gsoft.gallerypicktest.ui.main

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.gsoft.gallerypicktest.R
import com.gsoft.gallerypicktest.databinding.FragmentMainBinding
import com.gsoft.gallerypicktest.ui.main.adapter.PickFotosAdapter


class MainFragment : Fragment(R.layout.fragment_main) {

    private var listaImagenes : MutableList<Uri> = mutableListOf()
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.rvLista.adapter = PickFotosAdapter(listaImagenes, requireContext())


        binding.bSeleccionar.setOnClickListener(){
            seleccionarImagenes()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1) {
            if (resultCode === Activity.RESULT_OK) {

                if (data?.clipData  != null) {
                    val count: Int = data?.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        listaImagenes?.add(imageUri)
                    }
                    binding.rvLista.adapter?.notifyDataSetChanged()
                }
            }
            if (data?.data != null) {
                val imageUri = data.data
                if (imageUri != null) {
                    listaImagenes.add(imageUri)
                }
            }
        }
        binding.rvLista.adapter?.notifyDataSetChanged()
    }

    private fun seleccionarImagenes() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Pictures: "), 1)
    }
}