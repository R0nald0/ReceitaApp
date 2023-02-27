package com.example.receitas.presentation.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.receitas.R
import com.example.receitas.databinding.ActivitySalvarEditarBinding
import com.example.receitas.databinding.CadastrarReceitaLayoutBinding
import com.example.receitas.presentation.model.ReceitaViewCreate
import com.example.receitas.presentation.viewmodel.MainViewModel
import com.example.receitas.shared.HelperCamera
import com.example.receitas.shared.constant.Const
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalvarEditarActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivitySalvarEditarBinding.inflate(layoutInflater)
    }
    private val mainViewModel : MainViewModel by viewModels()

    private lateinit var resultaContract :  ActivityResultLauncher<String>
    private lateinit var permission :  ActivityResultLauncher<String>
    private lateinit var acessCamera :   ActivityResultLauncher<Intent>
    private   var image :Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        virificaResultContracts()
        initObservables()
        initListeners()


        binding.btnImgReceita.setOnClickListener {
            exibirDialog()
        }

        binding.btnSalvar.setOnClickListener {
            val tituloReceita =binding.edtNomeReceita.text.toString()
            val descricao =binding.edtIntreucoesReceita.text.toString()
            val ingredientes =binding.edtIngredientesReceita.text.toString()
            val tempo = binding.edtTempoPreparo.text.toString()


            val receita =ReceitaViewCreate(tituloReceita,image.toString(),"${tempo}min",descricao, listOf(ingredientes))
            mainViewModel.criarReceita(receita)
        }
    }

    private fun virificaResultContracts() {
        permission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it){
                takePicture()
            }else{
                showToast("Permissão Negada")
            }
        }
        acessCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it != null){
                val uri =HelperCamera.adicionarFotoPorCamera(it,this)
                  if (uri != null){
                     image = uri
                      Picasso.get().load(image).into(binding.imgReceitaCadastro)
                  }else{
                      showToast("Nenhuma imagem ")
                  }
            }else{
                showToast("Nenhuma imagem foi tirada")
            }
        }
        resultaContract = registerForActivityResult(ActivityResultContracts.GetContent()){
            if (it !=null ){
                image =it
                showToast(it.toString())
                Picasso.get().load(image).into(binding.imgReceitaCadastro)
            }else{
                showToast("Nenhuma imagem selecionada")
            }
        }
    }


    private fun initListeners() {
        mainViewModel.resultadoOperacaoDb.observe(this){
            if (it.sucesso){
                finish()
                showToast(it.mensagem)

            }else{
                showToast(it.mensagem)
            }
        }
    }


    private fun initObservables() {
        with(binding){
            if (edtNomeReceita.text.toString().isEmpty()) edtNomeReceita.error = "Digite o nome da receita"
              else null
            if (edtTempoPreparo.text.toString().isEmpty()) edtTempoPreparo.error = "insira o tempo de preparo"
             else  null

            if (edtIntreucoesReceita.text.toString().isEmpty()) edtIntreucoesReceita.error = "insira as intrucões receita"
             else null

            if (edtIngredientesReceita.text.toString().isEmpty()) edtIngredientesReceita.error = "insira os ingrediente da receitas"
            else null

        }
    }

    private fun showToast(messege : String){
      Toast.makeText(this, messege, Toast.LENGTH_LONG).show()
    }

    fun exibirDialog(){
        val diolog = BottomSheetDialog(this, R.style.BottomSheetDialogg).apply {
            this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }

        val bottomSheetDiaolog : CadastrarReceitaLayoutBinding =
            CadastrarReceitaLayoutBinding.inflate(layoutInflater,null,false)
        diolog.setContentView(bottomSheetDiaolog.root)
        diolog.show()

        bottomSheetDiaolog.imgBtnGaleria.setOnClickListener {
              resultaContract.launch("image/*")
             diolog.dismiss()
        }
        bottomSheetDiaolog.imgBtnCamera.setOnClickListener {
             requistarPermissao(android.Manifest.permission.CAMERA)
            diolog.dismiss()
        }

    }

    private fun requistarPermissao(permissoa:String){
        permission.launch(permissoa)
    }
    private fun takePicture (){
         val intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            acessCamera.launch(intent)
    }
}