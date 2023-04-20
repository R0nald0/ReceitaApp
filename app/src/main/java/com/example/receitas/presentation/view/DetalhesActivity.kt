package com.example.receitas.presentation.view

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.MenuProvider
import com.example.receitas.R
import com.example.receitas.databinding.ActivityDetalhesBinding
import com.example.receitas.domain.results.AppStateRequest
import com.example.receitas.presentation.model.ReceitaView
import com.example.receitas.presentation.viewmodel.DetalhesViewModel
import com.example.receitas.shared.constant.Const
import com.example.receitas.shared.dialog.AlertDialogCustom
import com.example.receitas.shared.extension.showToast
import com.example.receitas.shared.extension.showsnackBar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalhesActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetalhesBinding
    private val viewModel :DetalhesViewModel by viewModels()
    private lateinit var  receitaView : ReceitaView
    private val alertDialog by lazy {
        AlertDialogCustom(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title=""

        initObserver()
        initBinds()
        createMenu()

    }

    fun initBinds(){
        with(binding) {

            imgBtnEditar.setOnClickListener {
                goToSaveEditReceita()
            }
            imgBtnDeletar.setOnClickListener {
                 val receitaAtaul = getIntentExtraReceitaName()
              showDialog(receitaAtaul)
            }

            imgBtnAddUserList.setOnClickListener {
                viewModel.addReceitaToUserList(receitaView)
            }

        }
    }

    override fun onRestart() {
        super.onRestart()
        Const.exibilog("Restart")
    }

    override fun onResume() {
        super.onResume()
        Const.exibilog("Resume")
    }

    override fun onStart() {
        super.onStart()
        alertDialog.fecharDialog()
        viewModel.getReceitaByName(getIntentExtraReceitaName())
    }
    private fun goToSaveEditReceita(){
        val  receita = receitaView
        val intent =Intent(this,SalvarEditarActivity::class.java)
        intent.putExtra(Const.TEXT_INTENT_EXTRAS_RECEITA,receita)
        startActivity(intent)
    }

    fun initObserver(){
     viewModel.resultReceitaLiveData?.observe(this){
         /*if (it != null){
             receitaView =it
             getViewReceita()
         }*/
         receitaView = it.getOrThrow()
         getViewReceita()
     }
        viewModel.resultOperacaoLiveDataAddReceita.observe(this){
          if (it.sucesso) showsnackBar(
              this@DetalhesActivity.binding.imgBtnAddUserList ,
             "Receita adicionada a sua lista"
          )
           else showToast(it.mensagem)
        }
        viewModel.resultOperacaoLiveDataDelete.observe(this){
            if (it.sucesso){
                finish()
               applicationContext.showToast(it.mensagem)
            }else{
                applicationContext.showToast(it.mensagem)
            }
        }
        viewModel.appStateRequest.observe(this){
            when(it){
                AppStateRequest.loading ->{
                    binding.scrollView2.visibility = View.GONE
                    binding.idImgReceitaDetalhes.visibility = View.GONE
                    alertDialog.exibirDiaolog()
                }
                AppStateRequest.loaded ->{
                    binding.scrollView2.visibility = View.VISIBLE
                    binding.idImgReceitaDetalhes.visibility = View.VISIBLE
                    alertDialog.fecharDialog()
                }
                else -> {}
            }
        }
 }
    fun getIntentExtraReceitaName():ReceitaView{
        val extra = intent.extras
        val receita  = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            extra?.getParcelable(Const.TEXT_INTENT_EXTRAS_RECEITA,ReceitaView::class.java)
        } else {
            extra?.getParcelable<ReceitaView>(Const.TEXT_INTENT_EXTRAS_RECEITA) as ReceitaView
        }

        if (receita != null)return receita
         else{
             finish()
            showToast("erro ao carregar dados")
            throw  Exception("erro ao carregar dados")
         }

    }

    fun showDialog(receitaView: ReceitaView){
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Deseja excluir a Receita de ${receitaView.titulo}?")
            .setPositiveButton("Sim"){ di,p ->
                    viewModel.deletar(receitaView)
                di.dismiss()
            }.setNegativeButton("Não"){di,pos->
                di.dismiss()
            }.create().show()
    }

   private  fun getViewReceita() {
        with(binding) {

            val image = receitaView.checkImg()
            if (image != null)Picasso.get().load(Uri.parse(image)).into(idImgReceitaDetalhes)
            else Picasso.get().load(R.drawable.demos).into(idImgReceitaDetalhes)

             if (receitaView.isUserList){
                imgBtnAddUserList.visibility = View.GONE

                 val imagem = receitaView.checkImg()
                 if (imagem != null)Picasso.get().load(Uri.parse(imagem)).into(idImgReceitaDetalhes)
                 else Picasso.get().load(R.drawable.demos).into(idImgReceitaDetalhes)
            }
            else {
                imgBtnEditar.visibility = View.GONE
                imgBtnDeletar.visibility =View.GONE
                Picasso.get().load(receitaView.ImageUrl).into(idImgReceitaDetalhes)
            }

            txvReceitaInstrcoes.text = receitaView.instrucao
            TxvTempoReceitaDetalhes.text = "${receitaView.tempo}min"
            TxvTituloReceitaDetalhes.text = receitaView.titulo.uppercase()
            idTxvIngredientesReceitaDetalhes.text =receitaView.ingredientes

        }
    }

    fun createMenu(){
        addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                  val myMenu = menuInflater.inflate(R.menu.menu_detalhe_receita,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
               return when(menuItem.itemId) {
                   R.id.share_receita -> {
                       val shareIntent= Intent( Intent.ACTION_SEND)
                        shareIntent.type = "image/png"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT,receitaView.titulo)
                        shareIntent.putExtra(Intent.EXTRA_TEXT,
                           "Instrucao : ${receitaView.instrucao}\n" +
                                   "Ingredientes:\n ${receitaView.ingredientes}\n"

                        )
                        shareIntent.putExtra(Intent.EXTRA_STREAM,receitaView.Imagem)
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                       startActivity(Intent.createChooser(shareIntent,"Item receita"))
                       true
                   }
                  else -> false
               }
            }

        })
    }

}
