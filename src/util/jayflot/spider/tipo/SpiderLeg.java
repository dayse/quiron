package util.jayflot.spider.tipo;
import java.util.ArrayList;

import br.blog.arruda.plot.opt.tipo.PlotType;


/**
 * PlotSpider com opções relativas ao pluging Spider (grafico de Radar/Spider)
 * @author arruda
 */
public class SpiderLeg {

	
	private ArrayList<SpiderLegData> data;
	
	public SpiderLeg(ArrayList<String> data_labels){
		this.data = new ArrayList<SpiderLegData>();
		this.setData(data_labels);
	}
	/**
	 * Cria um conjunto de dados das 'pernas' do grafico,
	 * dado os nomes das labels de cada uma 
	 * @param labels
	 */
	public void setData(ArrayList<String> labels){
		for (String label : labels) {
			this.data.add(new SpiderLegData(label));
		}
	}

	
}
