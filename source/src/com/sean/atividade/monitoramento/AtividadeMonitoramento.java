package com.sean.atividade.monitoramento;

import static com.sean.util.Constantes.IMAGEM;
import static com.sean.util.Constantes.INICIAR;
import static com.sean.util.Constantes.PAUSAR;
import static com.sean.util.Constantes.STATUS;
import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sean.R;
import com.sean.atividade.Atividade;
import com.sean.net.tarefa.TarefaAlterarMonitoramento;
import com.sean.net.tarefa.TarefaImagemAtual;
import com.sean.net.tarefa.TarefaObterStatus;

public class AtividadeMonitoramento extends Atividade {

	private ImageView imagemAtual;
	private Button botaoStatus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atividade_monitoramento);

		this.imagemAtual = (ImageView)findViewById(R.id.atividade_principal_imagem_atual);
		this.botaoStatus = (Button)findViewById(R.id.atividade_principal_botao_status);

		obterStatusMonitoramento();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.atividade_principal, menu);
		return true;
	}

	@Override
	protected void onResume() {
		obterStatusMonitoramento();
		try {
			getImagemAtual().setImageDrawable(new BitmapDrawable(openFileInput("imagem.jpg")));
		} catch (Exception e) {
			Log.d("erro", e.toString());
		}
		super.onResume();
	}

	public void obterStatusMonitoramento() {
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Conectando ao servidor SEMON...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		TarefaObterStatus tarefaObterStatus = new TarefaObterStatus(this, progressDialog);
		tarefaObterStatus.execute(STATUS);
	}

	public void alterarStatusMonitoramento(View componente) {

		String tipoInicial = botaoStatus.getText().toString();

		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Conectando ao servidor SEMON...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		
		Log.d("tipo", tipoInicial);

		if (tipoInicial.equalsIgnoreCase("Iniciar")) {
			TarefaAlterarMonitoramento TarefaIniciar;
			TarefaIniciar = new TarefaAlterarMonitoramento(this, progressDialog, "Iniciando o Monitoramento do SEMON...");
			TarefaIniciar.execute(INICIAR);
			mostrarAlerta("Monitoramento Iniciado");
			botaoStatus.setText("Pausar");
		}
		else {
			TarefaAlterarMonitoramento TarefaPausar;
			TarefaPausar = new TarefaAlterarMonitoramento(this, progressDialog, "Pausando o Monitoramento do SEMON...");
			TarefaPausar.execute(PAUSAR);
			mostrarAlerta("Monitoramento Pausado");
			botaoStatus.setText("Iniciar");
		}
	}

	public void obterImagemAtual(View componente) {
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Conectando ao servidor SEMON...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		TarefaImagemAtual tarefaImagemAtual = new TarefaImagemAtual(this, progressDialog);
		tarefaImagemAtual.execute(IMAGEM);
	}

	public void sair(View componente) {
		finish();
	}

	public ImageView getImagemAtual() {
		return imagemAtual;
	}

	public Button getBotaoStatus() {
		return botaoStatus;
	}
}
