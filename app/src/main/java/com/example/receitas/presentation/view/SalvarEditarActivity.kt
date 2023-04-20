package com.example.receitas.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.receitas.R
import com.example.receitas.databinding.ActivitySalvarEditarBinding
import com.example.receitas.databinding.CadastrarReceitaLayoutBinding
import com.example.receitas.domain.results.AppStateRequest
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.model.ReceitaViewCreate
import com.example.receitas.presentation.viewmodel.SalvarEditarViewModel
import com.example.receitas.shared.HelperCreateUri
import com.example.receitas.shared.constant.Const
import com.example.receitas.shared.dialog.AlertDialogCustom
import com.example.receitas.shared.extension.esconderTeclado
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
    private val alertDialog by lazy {
        AlertDialogCustom(this)
    }
    private lateinit var namePermission : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title =""

        initResultContracts()
        initObservers()

        binding.imgReceitaCadastro.setOnClickListener {view->
            view.esconderTeclado()
            exibirDialog()
        }

        binding.btnSalvar.setOnClickListener {

            val receitaView = getIntentExtra()
            if (receitaView !=null){
                  editar(receitaView)
            }else{
                salvar()
            }
        }
    }

    private fun salvar(){
        val tituloReceita =binding.edtNomeReceita.text.toString()
        val descricao =binding.edtIntreucoesReceita.text.toString()
        val ingredientes =binding.edtIngredientesReceita.text.toString()
        val tempo = binding.edtTempoPreparo.text.toString()
        var imageReceita = Uri.EMPTY
        if (image != null) imageReceita = image

        val receitaCreate =ReceitaViewCreate(tituloReceita,true,imageReceita,tempo,descricao, ingredientes)
        mainViewModel.verificarCampos(receitaCreate)
    }
    private fun editar(receitaView :ReceitaView){
        val tempo = binding.edtTempoPreparo.text.toString()

        receitaView.tempo= tempo
        receitaView.instrucao= binding.edtIntreucoesReceita.text.toString()
        receitaView.titulo=binding.edtNomeReceita.text.toString()
        receitaView.ingredientes =binding.edtIngredientesReceita.text.toString()
        if (image !=null) receitaView.Imagem = image

        mainViewModel.editareceita(receitaView)
    }

    override fun onStart() {
        super.onStart()
        getDataReceitaToEditText()
    }

    private fun initResultContracts() {
        permission = registerForActivityResult(ActivityResultContracts.RequestPermission()){isPermited ->
            if (isPermited){
                when(namePermission){
                    android.Manifest.permission.READ_EXTERNAL_STORAGE ->{
                        resultaContract.launch("image/*")
                    }
                    android.Manifest.permission.CAMERA ->{
                        launchCamera()
                    }
                }
            }else{
                showToast("Permissão Negada")
            }
        }
        acessCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it != null){
                val uri =HelperCreateUri.adicionarFotoPorCamera(it,this)
                if (uri != null){
                    image = uri
                   Picasso.get().load(uri).into(binding.imgReceitaCadastro)
                }else{
                    showToast("Nenhuma imagem")
                }
            }else{
                showToast("Nenhuma imagem foi tirada")
            }
        }
        resultaContract = registerForActivityResult(ActivityResultContracts.GetContent()){

            if (it !=null ){
                image = HelperCreateUri.salvarFotoGaleriaUri(it,this)
                Const.exibilog(image.toString())
                Picasso.get()
                    .load(image)
                    .into(binding.imgReceitaCadastro)
            }else{
                showToast("Nenhuma imagem selecionada")
            }
        }
    }

    private fun initObservers() {
        mainViewModel.resultadoOperacaoDb.observe(this){
            if (it.sucesso){
                finish()
                startActivity(Intent(this,MainActivity::class.java))
                showToast(it.mensagem)
            }else{
                showToast(it.mensagem)
            }
        }
        mainViewModel.verifiacaCampoLiveData.observe(this){
             it.sucesso
            if (it.sucesso){
                mainViewModel.criarReceita(it.receitaView)
            }else{
                showToast(getString(R.string.preencha_camopo))
                verifyFields()
            }
        }
        mainViewModel.statusApp.observe(this){
            when(it){
                AppStateRequest.loading ->{
                    alertDialog.exibirDiaolog()
                }
                AppStateRequest.loaded ->{
                    alertDialog.fecharDialog()
                }
                else -> {}
            }
        }
    }

    fun exibirDialog(){
        val diolog = BottomSheetDialog(this, R.style.BottomSheetDialogg).apply {}

        val bottomSheetDiaolog : CadastrarReceitaLayoutBinding =
            CadastrarReceitaLayoutBinding.inflate(layoutInflater)
        diolog.setContentView(bottomSheetDiaolog.root)
        diolog.show()

        bottomSheetDiaolog.imgBtnGaleria.setOnClickListener {
              namePermission = android.Manifest.permission.READ_EXTERNAL_STORAGE
              requistarPermissao(namePermission)
              diolog.dismiss()
        }
        bottomSheetDiaolog.imgBtnCamera.setOnClickListener {
            namePermission = android.Manifest.permission.CAMERA
             requistarPermissao(namePermission)
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
          binding.txvTituloUiReceita.text= "Editar Receita"

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val receitaView = bundle.getParcelable(Const.TEXT_INTENT_EXTRAS_RECEITA,ReceitaView::class.java)
                    return receitaView
                 } else {
                    val receitaView =  bundle.getParcelable<ReceitaView>(Const.TEXT_INTENT_EXTRAS_RECEITA) as ReceitaView
                   return receitaView
                }
        }else{
           binding.txvTituloUiReceita.text = "Cadastrar Receita"
            binding.btnSalvar.text ="Criar"
            return null
        }

    }
    private fun getDataReceitaToEditText(){
        val receitaView =  getIntentExtra()
        if (receitaView != null){

            binding.edtNomeReceita.setText( receitaView.titulo)
            binding.edtIngredientesReceita.setText( receitaView.ingredientes)
            binding.edtIntreucoesReceita.setText( receitaView.instrucao)
            binding.edtTempoPreparo.setText( receitaView.tempo)

            val image = receitaView.checkImg()
            if (image != null)Picasso.get().load(Uri.parse(image)).into(binding.imgReceitaCadastro)
            else Picasso.get().load(R.drawable.red_image_search_24).into(binding.imgReceitaCadastro)
        }
    }

    fun verifyFields(){
        with(binding){
            if (edtNomeReceita.text.toString().isEmpty()) edtNomeReceita.error = getString(R.string.alerta_nome_receita)
            else null
               if (edtTempoPreparo.text.toString().isEmpty()) edtTempoPreparo.error = getString(R.string.alerta_tempo_preparo)
            else  null

            if (edtIntreucoesReceita.text.toString().isEmpty()) edtIntreucoesReceita.error = getString(
                            R.string.alerta_instrucao)
            else null

            if (edtIngredientesReceita.text.toString().isEmpty()) edtIngredientesReceita.error = getString(
                            R.string.alerta_ingrediente_campo)
            else null
        }
    }
}