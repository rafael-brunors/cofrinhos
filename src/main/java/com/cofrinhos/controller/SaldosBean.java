package com.cofrinhos.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.cofrinhos.colecao.ColecaoTotalPorDia;
import com.cofrinhos.negocio.ArrecadacaoRN;
import com.cofrinhos.negocio.DepositoRN;

@Named
@RequestScoped
public class SaldosBean implements Serializable{

	private static final long serialVersionUID = 5183708296379406367L;

	@Inject
	private ArrecadacaoRN arrecadacaoRN;
	
	@Inject
	private DepositoRN depositoRN;
	
	private BigDecimal saldoArrecadacoes;
	private BigDecimal saldoDepositos;
	private BigDecimal total;
	
	private LineChartModel lineChartArrecadacoes;
	private LineChartModel lineChartDepositos;
	
	@PostConstruct
	public void init() {
		saldoArrecadacoes = arrecadacaoRN.saldo();
		saldoDepositos = depositoRN.saldo();
		if(saldoArrecadacoes == null)
			saldoArrecadacoes = new BigDecimal("0.00");
		if(saldoDepositos == null)
			saldoDepositos = new BigDecimal("0.00");
		total = saldoArrecadacoes.subtract(saldoDepositos);
		
		createLines();
	}
	
	private void createLines() {
		lineChartArrecadacoes = initArrecadacoesModel();
		lineChartArrecadacoes.setTitle("Arrecadações");
		lineChartArrecadacoes.setLegendPosition("e");
		lineChartArrecadacoes.setShowPointLabels(true);
		lineChartArrecadacoes.getAxes().put(AxisType.X, new CategoryAxis("Data"));
		lineChartArrecadacoes.getAxis(AxisType.Y).setLabel("R$");
		
		lineChartDepositos = initDepositosModel();
		lineChartDepositos.setTitle("Depósitos");
		lineChartDepositos.setLegendPosition("e");
		lineChartDepositos.setShowPointLabels(true);
		lineChartDepositos.getAxes().put(AxisType.X, new CategoryAxis("Data"));
		lineChartDepositos.getAxis(AxisType.Y).setLabel("R$");
	}
	
	private LineChartModel initArrecadacoesModel() {
        LineChartModel model = new LineChartModel();
        ChartSeries arrecadacoes = new ChartSeries();
        arrecadacoes.setLabel("Arrecadações");
        
        List<ColecaoTotalPorDia> arrecadacoesFind = arrecadacaoRN.listaTotalPorDia();
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

        for(int i = arrecadacoesFind.size() - 1; i >= 0; i--) {
			arrecadacoes.set(dt.format(arrecadacoesFind.get(i).getData()),
					arrecadacoesFind.get(i).getValor());
		}
        
        model.addSeries(arrecadacoes);
        return model;
    }
	
	private LineChartModel initDepositosModel() {
        LineChartModel model = new LineChartModel();
        ChartSeries depositos = new ChartSeries();
        depositos.setLabel("Depósitos");
        
        List<ColecaoTotalPorDia> depositosFind = depositoRN.listaTotalPorDia();
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        
        for(int i = depositosFind.size() - 1; i >= 0; i--) {
        	
        	depositos.set(dt.format(depositosFind.get(i).getData()),
					depositosFind.get(i).getValor());
		}
        
        model.addSeries(depositos);
        return model;
    }

	public BigDecimal getSaldoArrecadacoes() {
		return saldoArrecadacoes;
	}

	public BigDecimal getSaldoDepositos() {
		return saldoDepositos;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public LineChartModel getLineChartArrecadacoes() {
		return lineChartArrecadacoes;
	}

	public LineChartModel getLineChartDepositos() {
		return lineChartDepositos;
	}
}
