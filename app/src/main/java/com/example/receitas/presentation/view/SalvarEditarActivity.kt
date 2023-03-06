package com.example.receitas.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.receitas.R
import com.example.receitas.databinding.ActivitySalvarEditarBinding
import com.example.receitas.databinding.CadastrarReceitaLayoutBinding
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate
import com.example.receitas.presentation.viewmodel.SalvarEditarViewModel
import com.example.receitas.shared.HelperCamera
import com.example.receitas.shared.constant.Const
import com.example.receitas.shared.extension.showToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalvarEditarActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivitySalvarEditarBinding.inflate(layoutInflater)
    }
    private val mainViewModel : SalvarEditarViewModel by viewModels()

    private lateinit var resultaContract :  ActivityResultLauncher<String>
    private lateinit var permission :  ActivityResultLauncher<String>
    private lateinit var acessCamera :   ActivityResultLauncher<Intent>
    private  var image :Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        virificaResultContracts()
        initObservables()
        initListeners()


       /* binding.btnImgReceita.setOnClickListener {
            exibirDialog()
        }*/
        binding.imgReceitaCadastro.setOnClickListener {
            exibirDialog()
        }

        binding.btnSalvar.setOnClickListener {
            val receitaView = getIntentExtra()
            if (receitaView !=null){

                val tituloReceita =binding.edtNomeReceita.text.toString()
                val instrucoes =binding.edtIntreucoesReceita.text.toString()
                val ingredientes =binding.edtIngredientesReceita.text.toString()
                val tempo = binding.edtTempoPreparo.text.toString()

                receitaView.tempo=tempo
                receitaView.instrucao= instrucoes
                receitaView.titulo=tituloReceita
                receitaView.ingredientes =ingredientes
                receitaView.Imagem = image.toString()

                mainViewModel.editareceita(receitaView)
            }else{
                val tituloReceita =binding.edtNomeReceita.text.toString()
                val descricao =binding.edtIntreucoesReceita.text.toString()
                val ingredientes =binding.edtIngredientesReceita.text.toString()
                val tempo = binding.edtTempoPreparo.text.toString()

                val receitaCreate =ReceitaViewCreate(tituloReceita,image.toString(),tempo,descricao, ingredientes)
                mainViewModel.criarReceita(receitaCreate)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        getDataReceitaToEditText()
    }

    private fun virificaResultContracts() {
        permission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it){
                launchCamera()
            }else{
                showToast("Permissão Negada")
            }
        }
        acessCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it != null){
                val uri =HelperCamera.adicionarFotoPorCamera(it,this,)
                if (uri != null){
                    image = uri
                    Picasso.get().load(image).placeholder(R.drawable.red_image_search_24).into(binding.imgReceitaCadastro)
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
                applicationContext.showToast(it.toString())
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
                startActivity(Intent(this,MainActivity::class.java))
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
    private fun launchCamera (){
         val intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            acessCamera.launch(intent)
    }

    private fun getIntentExtra() : ReceitaView?{
        val bundle = intent.extras
      return if (bundle !=null){
            binding.btnSalvar.text ="Editar"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val receitaView = bundle.getParcelable(Const.TEXT_INTENT_EXTRAS_EDIT_RECEITA,ReceitaView::class.java)
                    return receitaView
                 } else {
                    val receitaView =  bundle.getParcelable<ReceitaView>(Const.TEXT_INTENT_EXTRAS_EDIT_RECEITA) as ReceitaView
                   return receitaView
                }
        }else{
            binding.btnSalvar.text ="Criar"
            return null
        }

    }
    fun getDataReceitaToEditText(){
          val receitaView =  getIntentExtra()
        if (receitaView != null){

            binding.edtNomeReceita.setText( receitaView.titulo)
            binding.edtIngredientesReceita.setText( receitaView.ingredientes)
            binding.edtIntreucoesReceita.setText( receitaView.instrucao)
            binding.edtTempoPreparo.setText( receitaView.tempo)
            if (receitaView.Imagem == "null")
                 else{Picasso.get().load(receitaView.Imagem).into(binding.imgReceitaCadastro)}

        }
    }
}