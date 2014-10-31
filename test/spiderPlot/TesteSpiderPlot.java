package spiderPlot;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.xml.JRPenFactory.Left;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import service.controleTransacao.FabricaDeAppService;
import service.exception.AplicacaoException;
import util.Constantes;
import util.JPAUtil;
import br.blog.arruda.plot.Plot;
import br.blog.arruda.plot.data.PlotData;
import br.blog.arruda.plot.opt.PlotOptions;
import util.jayflot.spider.*;
import util.jayflot.spider.data.SpiderPlotData;
import util.jayflot.spider.opt.SpiderPlotGrid;
import util.jayflot.spider.opt.SpiderPlotSeries;
import util.jayflot.spider.tipo.PlotSpider;
import util.jayflot.spider.tipo.SpiderConnection;
import util.jayflot.spider.tipo.SpiderHighlight;
import util.jayflot.spider.tipo.SpiderLeg;

/**
 * Classe para testes dos metodos necessarios ao funcionamento das classes 
 * de auxílio do gráfico de Radar/Spider
 * @author felipe.arruda
 *
 */
public class TesteSpiderPlot {


	@BeforeClass
	public void setupClass(){
		try {
			System.out.println("-----------------------------> Startando a JPA...");
			JPAUtil.JPAstartUp();
			System.out.println("-----------------------------> JPA startada com sucesso!");


		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		setConstantes();
	}
	
	@AfterClass
	public void tearDown(){
		try {
			System.out.println("-----------------------------> desligando a JPA...");
			JPAUtil.closeEntityManager();
			System.out.println("-----------------------------> JPA desligada com sucesso!");


		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("AfterMethod");
		
	}
	
	public void setConstantes(){
		String sep = java.io.File.separator;
		String base_path = System.getProperty("user.dir") + sep + "WebContent";
		
        Constantes.CAMINHO_ABSOLUTO_ARQUIVOS_CARGA = base_path + sep + Constantes.CAMINHO_ARQUIVOS_CARGA + sep;
        Constantes.CAMINHO_ABSOLUTO_ARQUIVO_USUARIOS_CARGA = base_path + sep + Constantes.CAMINHO_ARQUIVO_USUARIOS_CARGA + sep;
		
	}

	/**
	 * Gera um conjunto de n elementos, 
	 * trocando alguns de seus elementos por algum fator de i
	 * @param i
	 * @return
	 */
	public ArrayList<Double[]> gerarData(int i, int n){
		ArrayList<Double[]> dataArrayList = new ArrayList<Double[]>();
		for (int j = 0; j < n; j++) {
			Double [] data_elem = {(double)( j ), (double)( (j%(n/i) + 1) * 10 ) };
			dataArrayList.add(data_elem);
		}
		
		return dataArrayList;
	}
	
	public ArrayList<String> gerarLegsLabel(int num_parametros){
		ArrayList<String> legs_labels = new ArrayList<String>();
		for (int i = 0; i < num_parametros; i++) {
			legs_labels.add("P00"+i+1);
		}
		return legs_labels;
		
	}
	
	public PlotSpider gerarSpiderSeries(){

		PlotSpider spiderSeries = new PlotSpider();
		spiderSeries.setPointSize(5.0);
		spiderSeries.setConnection(new SpiderConnection(5.0));
		spiderSeries.setActive(true);
		spiderSeries.setHighlight(new SpiderHighlight("area"));
		spiderSeries.setLegs(new SpiderLeg(gerarLegsLabel(8)));
		spiderSeries.setScaleMode("static");
		spiderSeries.setLegMin(0.0);
		spiderSeries.setLegMax(100.0);
		spiderSeries.setSpiderSize(0.5);
		
		return spiderSeries;
		
	}
	

	/**
	 * Retorna o valor esperado do SpiderPlot data como Json.
	 */
	public String printSpiderData(ArrayList<SpiderPlotData> plotDatas){
		Gson gson = new Gson();
		String json="";
		
		//print all datas:
		json+="[";
		for(SpiderPlotData data: plotDatas){
			String temp = gson.toJson(data,data.getClass());
			json+=temp+",";
		}
		json+="]";
		
		return json;
	}
	
	@Test
	public void testeCargaBasicaDeUsuarios() throws AplicacaoException {
		
		// DATAS
		ArrayList<SpiderPlotData> plotDatas = new ArrayList<SpiderPlotData>();
		
		
		SpiderPlotData plotData1 = new SpiderPlotData();
		
		plotData1.setSpider(new PlotSpider(true, true));
		
		ArrayList<Double[]> data1 = gerarData(1, 8);
		plotData1.setData(data1);
		plotData1.setLabel("Necessidade do Paciente");

		
		SpiderPlotData plotData2 = new SpiderPlotData();
		
		plotData2.setSpider(new PlotSpider(true, true));
		
		ArrayList<Double[]> data2 = gerarData(2, 8);
		plotData2.setData(data2);
		plotData2.setLabel("Bactrim");
		
		
		SpiderPlotData plotData3 = new SpiderPlotData();
		
		plotData3.setSpider(new PlotSpider(true, true));
		
		ArrayList<Double[]> data3 = gerarData(3, 8);
		plotData3.setData(data3);
		plotData3.setLabel("Amox");
		

		plotDatas.add(plotData1);
		plotDatas.add(plotData2);
		plotDatas.add(plotData3);
		
		// SERIES
		
		SpiderPlotSeries plotSeries = new SpiderPlotSeries();
		plotSeries.setSpider(gerarSpiderSeries());
		
		// GRID
		
		SpiderPlotGrid plotGrid = new SpiderPlotGrid();
		plotGrid.setHoverable(true);
		plotGrid.setClickable(true);
		plotGrid.setMode("radar");
		
		// OPTIONS
		PlotOptions plotOptions = new PlotOptions();
		plotOptions.setSeries(plotSeries);
		plotOptions.setGrid(plotGrid);
		plotOptions.setX2axis(null);
		plotOptions.setXaxis(null);
		plotOptions.setY2axis(null);
		plotOptions.setYaxis(null);
		
		
		// PLOT
		SpiderMainPlot spiderPlot = new SpiderMainPlot();
		
		spiderPlot.setSpiderDatas(plotDatas);
		spiderPlot.setOptions(plotOptions);
		
		AssertJUnit.assertTrue(
				"SpiderMainPlot não esta usando a classe SpiderData na hora de imprimir!", 
					spiderPlot.getPrintData().equals(printSpiderData(plotDatas)));
		

		AssertJUnit.assertTrue(
				"SpiderMainPlot.data não tem o texto 'spider'", 
					spiderPlot.getPrintData().contains("spider"));

		System.out.println(spiderPlot.getPrintOptions());
		AssertJUnit.assertTrue(
				"SpiderMainPlot.data não tem o texto 'spider'", 
					spiderPlot.getPrintOptions().contains("spider"));
		
		
	}
	
	
}
